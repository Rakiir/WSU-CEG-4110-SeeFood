import os
import datetime
import glob
from flask import Flask, flash, request, redirect, url_for
from werkzeug.utils import secure_filename
import numpy as np
import tensorflow as tf
from PIL import Image
def see_food(strp):
###### Initialization code - we only need to run this once and keep in memory.
    sess = tf.Session()
    saver = tf.train.import_meta_graph('saved_model/model_epoch5.ckpt.meta')
    saver.restore(sess, tf.train.latest_checkpoint('saved_model/'))
    graph = tf.get_default_graph()
    x_input = graph.get_tensor_by_name('Input_xn/Placeholder:0')
    keep_prob = graph.get_tensor_by_name('Placeholder:0')
    class_scores = graph.get_tensor_by_name("fc8/fc8:0")
######
    print('seefood path: ' + strp)
    image_path = strp
    image = Image.open(image_path).convert('RGB')
    print('image got')
    image = image.resize((227, 227), Image.BILINEAR)
    print('image resized')
    img_tensor = [np.asarray(image, dtype=np.float32)]
    print('img_tensor set')
    #global sess
    #global class_scores
    #global x_input
    #global keep_prob
    scores = sess.run(class_scores, {x_input: img_tensor, keep_prob: 1.})
    print scores
    # if np.argmax = 0; then the first class_score was higher, e.g., the model sees food.
    # if np.argmax = 1; then the second class_score was higher, e.g., the model does not see food.
    #if np.argmax(scores) == 1:
        #print "No food here... :( "
    #else:
        #print "Oh yes... I see food! :D"
    #print('scores ran')
    og = strp.rsplit('/', 1)[1].lower()
    print(og)
    name = str(scores[0][0])+'XX' + str(scores[0][1])+ 'XX' + og
    print(name)
    filename = '/home/ubuntu/seefood-master/temp/' + name #+ extension
    print('generated name')
    os.rename(strp, filename)
    print('renamed')
    rstr = str(scores[0][0])+'XX' + str(scores[0][1])+ 'XX'
    return rstr
UPLOAD_FOLDER = '/home/ubuntu/seefood-master/temp'
ALLOWED_EXTENSIONS = set(['png', 'jpg',])
app = Flask(__name__)
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER
#def main(argv):
from flask import send_from_directory
#@app.route('/uploads/<filename>')
#def uploaded_file(filename):
    #return send_from_directory(app.config['UPLOAD_FOLDER'],
                               #filename)
def allowed_file(filename):
    return '.' in filename and \
           filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS
@app.route('/upload', methods=['GET', 'POST'])
def upload_file():
    if request.method == 'POST':
        # check if the post request has the file part
        if 'media' not in request.files:
            print('media not in request.files')
            #flash('No file part')
            #return redirect(request.url)
        file = request.files['media']
        # if user does not select file, browser also
        # submit an empty part without filename
        if file.filename == '':
            print('no file name')
            #flash('No selected file')
            #return redirect(request.url)
        if file and allowed_file(file.filename):
            print('securing name')
            filename = secure_filename(file.filename)
            print('saving')
            file.save(os.path.join(app.config['UPLOAD_FOLDER'], filename))
            print('preparing to see food')
            path = 'temp/' + filename
            returnstr = see_food(path)
            return returnstr
    return 'upload failed'
    print('failure')
@app.route('/getphoto')
def return_file():
    print("In get photo")
    num = request.args.get('type')
    val = int(num)
	print(val)
    dirSize = len([name for name in os.listdir('/home/ubuntu/seefood-master/temp/') if os.path.isfile(name)])
    if val>dirSize:
        name = os.listdir('/home/ubuntu/seefood-master/temp/')[dirSize-1]
    else:
        name = os.listdir('/home/ubuntu/seefood-master/temp/')[val]
	print(name)
    #print(path)
    #name = path.split('/')[4]
    return send_from_directory(app.config['UPLOAD_FOLDER'], name)
@app.route('/getFileAmount')
def return_num_of_files():
    DIR = '/home/ubuntu/seefood-master/temp/'
    num = str(len([name for name in os.listdir(DIR) if os.path.isfile(os.path.join(DIR, name))]))
    return num
@app.route('/getphotoname')
def return_file_path():
    print("In get photo name")
    num = request.args.get('type')
    val = int(num)
	print(int)
    dirSize = len([name for name in os.listdir('/home/ubuntu/seefood-master/temp/') if os.path.isfile(name)])
    if val>dirSize:
        name = os.listdir('/home/ubuntu/seefood-master/temp/')[dirSize-1]
    else:
        name = os.listdir('/home/ubuntu/seefood-master/temp/')[val]
    #print(path)
    #name = name.split('/')[4]
	print(name)
	
    return name
@app.route('/undo')
def undo():
    list_of_files = glob.glob('/path/to/folder/*') # * means all if need specific format then *.csv
    latest_file = max(list_of_files, key=os.path.getctime)
    os.remove(latest_file)
    return "Done";    
if __name__ == '__main__':
    app.run(debug= True, host='0.0.0.0')
