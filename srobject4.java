package dunham.dylan.dylandunhamseefood;

//found example not using volleyplus: https://androidexample.com/Upload_File_To_Server_-_Android_Example/index.php?view=article_discription&aid=83


import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.Time;
import android.util.Log;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.ResponseBody;

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




    private int fileAmount;



    private String urlFileAmount; //https://xxx.xxx.xxx.xxx:5000/getFileAmount
    private String receivedFilePath;//filepath of photo from online gallery
    private String fileNameHelper;
    private Bitmap image;


    // private Context context;

    public srobject2() {
    }
    public void setUrlFileAmount(String urlFileAmount) {
        this.urlFileAmount = urlFileAmount;
    }
    public int getFileAmount() {
        return fileAmount;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getReceivedString() {
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
    public void setFileString(String filePath) {
        fileString = filePath;
    }

    public String getFileString() {
        return fileString;
    }

    public String getReceivedFilePath() {
        return receivedFilePath;
    }

    public void setURLUpload(String URL) {
        urlUpload = URL;
    }

    public String getURLUpload() {
        return urlUpload;
    }

    public void setURLGetImage(String URL) {
        urlGetImage = URL;
    }

    public String getURLGetImage() {
        return urlGetImage;
    }
    public void requestFileAmount(){


        final OkHttpClient client = new OkHttpClient();
        Request request1 = new Request.Builder()
                .url(urlFileAmount)
                .build();
        client.newCall(request1).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override public void onResponse(Call call, Response response1) throws IOException {
                try (ResponseBody responseBody = response1.body()) {
                    if (!response1.isSuccessful()) throw new IOException("Unexpected code " + response1);

                    Headers responseHeaders = response1.headers();
                    for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                        System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    }
                    String temp = responseBody.string();
                    fileAmount = Integer.parseInt(temp);

                }
            }
        });
    }
    public void sendPic(Context c) {

        final OkHttpClient client = new OkHttpClient();
        String split[] = fileString.split("\\.");
        String fileExtension = split[1];
        if (fileExtension.equals("png")) {
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("media", "image.png",
                            RequestBody.create(MediaType.parse("image/png"), new File(fileString)))
                    .build();

            Request request = new Request.Builder()
                    .url(urlUpload)
                    .post(requestBody)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override public void onResponse(Call call, Response response) throws IOException {
                    try (ResponseBody responseBody = response.body()) {
                        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                        /*Headers responseHeaders = response.headers();
                        for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                            System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                        }*/

                        receivedString = responseBody.string();
                    }
                }
            });
           /* try {
                Response response = client.newCall(request).execute();
                //if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                receivedString = response.body().string();
            } catch (IOException e) {
                System.out.println(e);
            }*/
        } else {
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("media", "image.jpg",
                            RequestBody.create(MediaType.parse("image/jpg"), new File(fileString)))
                    .build();

            Request request = new Request.Builder()
                    .url(urlUpload)
                    .post(requestBody)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override public void onResponse(Call call, Response response) throws IOException {
                    try (ResponseBody responseBody = response.body()) {
                        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                        /*Headers responseHeaders = response.headers();
                        for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                            System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                        }*/

                        receivedString = responseBody.string();
                    }
                }
            });
            /*try {
                Response response = client.newCall(request).execute();
                //if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                receivedString = response.body().string();
            } catch (IOException e) {
                System.out.println(e);
            }*/
        }
    }

    public void getPic(String typeParameter, Context c) {
        //https://www.youtube.com/watch?v=uVqr58BkI40
        //https://github.com/dazza5000/OkHttpDownloadImage/blob/master/app/src/main/java/net/tipit/okhttpspike/MainActivity.java
        String url = HttpUrl.parse(urlGetImageName).newBuilder()
                .addQueryParameter("type", typeParameter)
                .build().toString();

        final OkHttpClient client = new OkHttpClient();
        Request request1 = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request1).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override public void onResponse(Call call, Response response1) throws IOException {
                try (ResponseBody responseBody = response1.body()) {
                    if (!response1.isSuccessful()) throw new IOException("Unexpected code " + response1);

                    Headers responseHeaders = response1.headers();
                    for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                        System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    }

                    receivedFilePath = responseBody.string();
                }
            }
        });
        /*try {
            Response response = client.newCall(request1).execute();
            //if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            receivedFilePath = response.body().string();
        } catch (IOException e) {
            System.out.println(e);
        }*/


        String url2 = HttpUrl.parse(urlGetImage).newBuilder()
                .addQueryParameter("type", typeParameter)
                .build().toString();
        Request request2 = new Request.Builder()
                .url(url2)
                .build();

        Response response2 = null;
        client.newCall(request2).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override public void onResponse(Call call, Response response2) throws IOException {
                try (ResponseBody responseBody = response2.body()) {
                    if (!response2.isSuccessful()) throw new IOException("Unexpected code " + response2);

                    Headers responseHeaders = response2.headers();
                    for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                        System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    }
                    image = BitmapFactory.decodeStream(response2.body().byteStream());

                }
            }
        });
        //image = null;
        /*try {
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

        }*/
    }
}
