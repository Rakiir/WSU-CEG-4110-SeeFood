//found example not using volleyplus: https://androidexample.com/Upload_File_To_Server_-_Android_Example/index.php?view=article_discription&aid=83
import volleyplus
//https://github.com/DWorkS/VolleyPlus---Source code for volley plus required for this to work, may require volley, doesn't say.
//https://stackoverflow.com/questions/14053338/save-bitmap-in-android-as-jpeg-in-external-storage-in-a-folder- further refereced
public class SRObject{
	//volley plus example: https://medium.com/@mujtahidah/upload-file-simplemultipartrequest-volley-android-f9ace8ec0dec
	private fileString;//String to file to send to server
	private receivedString;//string of file processed
	private paramNumberString;//String to request a file at x location in the directory on the server
	private urlUpload; //https://xxx.xxx.xxx.xxx:5000/upload
	private urlGetImage; //https://xxx.xxx.xxx.xxx:5000/getpic
	private receivedFilePath;//filepath of photo from online gallery
	public SRObject(){}
	
	
	public String getReceivedString(){
		return receivedString;
	}
	public void setFileString(String filePath){
			fileString = filePath;
	}
	public String getFileString(){
		return fileString;
	}
	public String getReceivedFilePath(){
		return receivedFilePath;
	}
	public void setURLUpload(String URL){
		urlUpload = URL;
	}
	public String getURLUpload(){
		return urlUpload;
	}
	public void setURLGetImage(String URL){
		urlGetImage = URL;
	}
	public String getURLGetImage(){
		return urlGetImage;
	}
	public String sendPic(){
		SimpleMultiPartRequest smr = new SimpleMultiPartRequest(Request.Method.POST, urlUpload,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						receivedString = response;              
					}
				}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				//Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
			}
		});
		//smr.addStringParam("param string", " data text");
		smr.addFile("param file", fileString); 

		RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		mRequestQueue.add(smr);
		
		
	}
	public void getPic(String typeParameter){
				
		ImageRequest getPic = new ImageRequest(Request.Method.GET, urlGetImage,
			new Response.Listener<Bitmap>() {
				@Override
				public void onResponse(Bitmap image) {
					String path = Environment.getExternalStorageDirectory().toString();
					OutputStream fOutputStream = null;
					File file = new File(path + "/Captures/", "flaskdownload.jpg");
					if (!file.exists()) {
						file.mkdirs();
					}

					try {
						fOutputStream = new FileOutputStream(file);

						capturedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOutputStream);

						fOutputStream.flush();
						fOutputStream.close();
						receivedFilePath = file.getAbsolutePath();
						MediaStore.Images.Media.insertImage(getContentResolver(), file.getAbsolutePath(), file.getName(), file.getName());
					} catch (FileNotFoundException e) {
						e.printStackTrace();
						//Toast.makeText(this, "Save Failed", Toast.LENGTH_SHORT).show();
						return;
					} catch (IOException e) {
						e.printStackTrace();
						//Toast.makeText(this, "Save Failed", Toast.LENGTH_SHORT).show();
						return;
					}
				}
			}, new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
				//Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
				}
			});
		smr.addStringParam("type", typeParameter);//Give an integer as a string to get file at that position in the directory.
		//smr.addFile("param file", imagePath);

		RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		mRequestQueue.add(getPic);
	}





}