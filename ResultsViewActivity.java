package com.wsu.caltabellotta.seefood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import java.util.Vector;

public class ResultsViewActivity extends AppCompatActivity {

    private HorizontalScrollView hsv;
    private Vector<FoodResultObj> resultsVector = new Vector<>();
    private int vectorIterator = 0;
    private Button undoLastUpload;
    private ImageView imageView;
    private boolean isImageFitToScreen;
    private SRObject sro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_view);

        sro = new SRObject();
        imageView = (ImageView) findViewById(R.id.imageView2);


        Button returnMainMenu = findViewById(R.id.mainMenuBtn);
        returnMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultsViewActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Button undoLastUpload = findViewById(R.id.undoUploadBtn);
        undoLastUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sro.delete(resultsVector.get(vectorIterator);
            }
        });

        Button nextBtn = findViewById(R.id.nextButton);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vectorIterator != resultsVector.size()) {
                    vectorIterator += 1;
                    FoodResultObj outputObj = resultsVector.get(vectorIterator);
                    imageView.setImageBitmap(outputObj.getImage());
                    resize();
                }
                else {
                    //return to main menu if at end of uploaded pics
                    Intent intent = new Intent(ResultsViewActivity.this, MainActivity.class);
                    startActivity(intent);
                }

            }
        });
    }  //end onCreate method

    /**
     * Make image scale to screen size.
     * Source: https://stackoverflow.com/questions/24463691/how-to-show-imageview-full-screen-on-imageview-click
     */
    public void resize() {
        if(isImageFitToScreen) {
            isImageFitToScreen=false;
            imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            imageView.setAdjustViewBounds(true);
        }else{
            isImageFitToScreen=true;
            imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
    }  //end resize method





}  //End of ResutsViewActivity
