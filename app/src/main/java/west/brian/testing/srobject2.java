package west.brian.testing;

//found example not using volleyplus: https://androidexample.com/Upload_File_To_Server_-_Android_Example/index.php?view=article_discription&aid=83


import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.cache.plus.ImageRequest;
import com.android.volley.error.VolleyError;
import com.android.volley.request.MultiPartRequest;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Random;

import static android.content.ContentValues.TAG;


//https://github.com/DWorkS/VolleyPlus---Source code for volley plus required for this to work, may require volley, doesn't say.
//https://stackoverflow.com/questions/14053338/save-bitmap-in-android-as-jpeg-in-external-storage-in-a-folder- further refereced
public class srobject2 implements Serializable {
    //volley plus example: https://medium.com/@mujtahidah/upload-file-simplemultipartrequest-volley-android-f9ace8ec0dec
    private String fileString;//String to file to send to server
    private String receivedString;//string of file processed
    private String paramNumberString;//String to request a file at x location in the directory on the server
    private String urlUpload; //https://xxx.xxx.xxx.xxx:5000/upload
    private String urlGetImage; //https://xxx.xxx.xxx.xxx:5000/getpic
    private String urlGetImageName; //https://xxx.xxx.xxx.xxx:5000/getpicname
    private String receivedFilePath;//filepath of photo from online gallery
    private String fileNameHelper;



   // private Context context;
    public srobject2(){}


    public String getReceivedString(){
        return receivedString;
    }
    public String getUrlGetImageName() {
        return urlGetImageName;
    }

    public void setUrlGetImageName(String urlGetImageName) {
        this.urlGetImageName = urlGetImageName;
    }
    //public void setContext(Context context) {
      //  this.context = context;
    //}
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
    public void sendPic(Context c){

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

        RequestQueue mRequestQueue = Volley.newRequestQueue(c.getApplicationContext());
        mRequestQueue.add(smr);


    }
    public void getPic(String typeParameter, Context c){

        SimpleMultiPartRequest smr2 = new SimpleMultiPartRequest(Request.Method.POST, urlUpload,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        fileNameHelper = response;
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        smr2.addStringParam("type", typeParameter);
        smr2.addFile("param file", fileString);
        RequestQueue mRequestQueue = Volley.newRequestQueue(c.getApplicationContext());
        mRequestQueue.add(smr2);
        MultiPartRequest<Bitmap> getPic = new MultiPartRequest<Bitmap>(Request.Method.GET, urlGetImage,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap image) {
                        String root = Environment.getExternalStorageDirectory().toString();
                        File myDir = new File(root + "/captures");
                        myDir.mkdirs();
                        String fname = fileNameHelper;
                        File file = new File(myDir, fname);
                        if (file.exists())
                            file.delete();
                        try {
                            FileOutputStream out = new FileOutputStream(file);
                            image.compress(Bitmap.CompressFormat.JPEG, 90, out);
                            out.flush();
                            out.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Response<Bitmap> parseNetworkResponse(NetworkResponse response) {
                return null;
            }
        };
        getPic.addStringParam("type", typeParameter);//Give an integer as a string to get file at that position in the directory.
        //smr.addFile("param file", imagePath);


        mRequestQueue.add(getPic);
    }



}
