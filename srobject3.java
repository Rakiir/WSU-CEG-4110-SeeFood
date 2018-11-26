package west.brian.testing;

//found example not using volleyplus: https://androidexample.com/Upload_File_To_Server_-_Android_Example/index.php?view=article_discription&aid=83


import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.Time;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.cache.plus.ImageRequest;
import com.android.volley.error.VolleyError;
import com.android.volley.request.MultiPartRequest;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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
    private Bitmap image;



   // private Context context;
    public srobject2(){}

    public Bitmap getImage(){
      return image;
    }
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
     
      final OkHttpClient client = new OkHttpClient();
      String fileExtension = fileString.split(".");
      if(Objects.equals("png")){
        RequestBody requestBody = new MultipartBuilder()
                .type(MultipartBuilder.FORM)
                .addFormDataPart("media", "image.png",
                    RequestBody.create(MediaType.parse("image/png"), new File(fileString)))
                .build();

        Request request = new Request.Builder()
                .url(serverURL)
                .post(requestBody)
                .build();
        try (Response response = client.newCall(request).execute()) {
          if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
          receivedString = response.body().string();
        }
      }else{
        RequestBody requestBody = new MultipartBuilder()
                .type(MultipartBuilder.FORM)
                .addFormDataPart("media", "image.jpg",
                    RequestBody.create(MediaType.parse("image/jpg"), new File(fileString)))
                .build();

        Request request = new Request.Builder()
                .url(serverURL)
                .post(requestBody)
                .build();
        try (Response response = client.newCall(request).execute()) {
          if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
          receivedString = response.body().string();
        }
      }
    }
    public void getPic(String typeParameter, Context c){
      //https://www.youtube.com/watch?v=uVqr58BkI40
      //https://github.com/dazza5000/OkHttpDownloadImage/blob/master/app/src/main/java/net/tipit/okhttpspike/MainActivity.java
        String url = HttpUrl.parse(urlGetImageName).newBuilder()
          .addQueryParameter(typeParameter)
          .build().toString();

        final OkHttpClient client = new OkHttpClient();
        Request request1 = new Request.Builder()
                .url(url)
                .build();
        try (Response response = client.newCall(request1).execute()) {
          if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
          receivedFilePath = response.body().string();
        }
        
        
        
        String url2 = HttpUrl.parse(urlGetImage).newBuilder()
          .addQueryParameter(typeParameter)
          .build().toString();
        Request request2 = new Request.Builder()
                .url(url2)
                .build();

        Response response2 = null;
        //image = null;
        try {
            response2 = client.newCall(request2).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response2.isSuccessful()) {
            try {
                image = BitmapFactory.decodeStream(response2.body().byteStream());
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }

        }
}
