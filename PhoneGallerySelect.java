package com.wsu.caltabellotta.phonegalleryselect;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class PhoneGallerySelect extends AppCompatActivity {

    ImageView imageView;
    Button selectButton, backButton, sendButton;
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
                Intent intent = new Intent(c, MainActivity.class);
                startActivity(intent);
            }
        });

        sendButton = findViewById(R.id.backBtn);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sro.sendPic(c);
                Intent intent = new Intent(c, ResultsViewActivity.class);
                intent.putExtra("sro", sro);
                startActivity(intent);
            }
        });



    } //end onCreate method

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }  //end openGallery method

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((resultCode == RESULT_OK) && (requestCode == PICK_IMAGE)) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }  //end onActivityResult method

} //end PhoneGallerySelect class
