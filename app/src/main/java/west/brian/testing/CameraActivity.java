package west.brian.testing;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CameraActivity extends AppCompatActivity {

    private Camera mCamera;
    private CameraPreview mPreview;
    public srobject2 sro = new srobject2();
    public Context c;
    private static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    private static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_main);
        // Create an instance of Camera
        mCamera = getCameraInstance();
        Camera.Parameters parameters = mCamera.getParameters();
        if (parameters.getSupportedFocusModes().contains(
                Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO)) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
        }
        mCamera.setParameters(parameters);
        mCamera.setDisplayOrientation(90);
        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(this, mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);
        sro = (srobject2) getIntent().getSerializableExtra("sro");
        //sro.setContext(getApplicationContext());
        c = getApplicationContext();
    }

    //takePic function takes picture on button click
    public void takePic(View v){
        mCamera.takePicture(null, null, mPicture);
    }
    //mPicture uploads photo taken on creation
    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            DateFormat df = new SimpleDateFormat("MMddyyyy_HHmmss");
            Date today = Calendar.getInstance().getTime();
            String reportDate = df.format(today);
            String FILE_NAME = reportDate+".jpg";
            String baseDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath();
            File file = new File(baseDir, FILE_NAME);

            try {
                FileOutputStream out = new FileOutputStream(file);
                out.write(data);
                out.close();
                sro.setFileString(file.getPath());
                sro.sendPic(c);
                //while(sro.getReceivedString()==null){
                   // System.out.println("here");
                //}

                Intent intent = new Intent(c, ResultsViewActivity.class);
                intent.putExtra("sro", sro);
                startActivity(intent);
            } catch (FileNotFoundException e) {

            } catch (IOException e) {

            }
        }
    };

    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }

    public void Back(View v){
        startActivity(new Intent(CameraActivity.this, MainActivity.class));
    }




}
