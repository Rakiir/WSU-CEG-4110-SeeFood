package west.brian.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import west.brian.myapplication.R;

public class MainActivity extends AppCompatActivity {
    public srobject2 sro = new srobject2();
    private static Context mAppContext;
    @Override

    /**
     * Creates main menu and initializes values
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Requests user permissions for storage, camera and internet access
        String[] PERMISSIONS = {
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE
        };
        ActivityCompat.requestPermissions(this, PERMISSIONS, 1);
        setContentView(R.layout.activity_main);
        //sro.setContext(getApplicationContext());
        sro.setURLUpload("http://18.224.158.93:5000/upload");
        sro.setURLGetImage("http://18.224.158.93:5000/getphoto");
        sro.setUrlGetImageName("http://18.224.158.93:5000/getphotoname");
        sro.setUrlFileAmount("http://18.224.158.93:5000/getFileAmount");
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        MediaPlayer mp;
        mp = MediaPlayer.create(this, R.raw.om);
        mp.setLooping(true);
        mp.start();

    }

    /**
     *StartCameraAct function starts CameraActivity to take picture for uploading on button click
    */
    public void StartCameraAct(View v){
        Intent intent = new Intent(getApplicationContext(), CameraActivity.class);
        intent.putExtra("sro", sro);
        startActivity(intent);
    }

    /**
    *StartPhGllyAct function starts phone gallery activity to select photo(s) already taken for uploading on button click
    */
    public void StartPhGllyAct(View v){
        Intent intent = new Intent(getApplicationContext(), PhoneGallerySelect.class);
        intent.putExtra("sro", sro);
        startActivity(intent);
    }

    /**
     *StartSrGllyAct function starts server gallery activity to let user view photos already uploaded to server on button click
     */
    public void StartSrGllyAct(View v){
        Intent intent = new Intent(getApplicationContext(), ServerActivity.class);
        intent.putExtra("sro", sro);
        startActivity(intent);
    }

}