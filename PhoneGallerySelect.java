package west.brian.myapplication;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;


public class PhoneGallerySelect extends AppCompatActivity {

    ImageView imageView;
    Button selectButton, backButton, sendButton, addButton;
    private static final int PICK_IMAGE = 100;
    Uri imageUri;
    public srobject2 sro = new srobject2();
    public Context c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_gallery_select);

        sro = (srobject2) getIntent().getSerializableExtra("sro");
        c = getApplicationContext();

        imageView = findViewById(R.id.imageView);
        selectButton = findViewById(R.id.selectBtn);
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        backButton = findViewById(R.id.backBtn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sro.empty();
                Intent intent = new Intent(c, MainActivity.class);
                startActivity(intent);
            }
        });

        sendButton = findViewById(R.id.sendBtn);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //File temp = new File(imageUri.getPath());
                //String s = temp.getAbsolutePath();
                sro.setReceivedStringToNull();
                sro.sendPic();
                while(sro.getReceivedString()==null){};
                Intent intent = new Intent(c, ResultsViewActivity.class);
                intent.putExtra("sro", sro);
                startActivity(intent);
            }

        });
        addButton = findViewById(R.id.addBtn);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //File temp = new File(imageUri.getPath());
                //String s = temp.getAbsolutePath();
                String s = getRealPathFromURI(imageUri);
                //sro.setFileString(s);
                /**File file = new File(s);
                //System.out.println("****************** " + s);
                while (!s.substring(0, s.length()).equals('/')) {
                    s = s.substring(0, s.length() - 1);
                }
                String nameToSend = s + "ceg.jpg";
                File newFile = new File(nameToSend);
                file.renameTo(newFile);
                sro.enqueue(nameToSend); **/
                sro.enqueue(s);
            }

        });
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);


    } //end onCreate method

    /**
     * Starts the Android gallery intent as a new activity.
     */
    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }  //end openGallery method

    /**
     * Part of the Android Gallery API used to set the image on the screen to the chosen image if the result is a valid image.
     * Retrieves the URI of the image to set the image.
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((resultCode == RESULT_OK) && (requestCode == PICK_IMAGE)) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
        MediaPlayer mp;
        mp = MediaPlayer.create(this, R.raw.om);
        mp.setLooping(true);
        mp.start();
    }  //end onActivityResult method

    /**
     * Returns the path to the image file that the user selected from the image gallery.
     * @param contentUri
     * @return result is a string representing the path to the image the user selects from the gallery
     */
    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(getApplicationContext(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

} //end PhoneGallerySelect class
