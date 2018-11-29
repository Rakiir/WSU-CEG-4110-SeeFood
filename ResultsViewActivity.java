package dunham.dylan.dylandunhamseefood;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Vector;
import java.lang.Math;
import java.util.Random;

import dunham.dylan.dylandunhamseefood.FoodResultObj;
import dunham.dylan.dylandunhamseefood.MainActivity;
import west.brian.testing.R;
import dunham.dylan.dylandunhamseefood.srobject2;

public class ResultsViewActivity extends AppCompatActivity {

    private HorizontalScrollView hsv;
    private Vector<FoodResultObj> resultsVector = new Vector<>();
    private int vectorIterator = 0;
    private Button undoLastUpload;
    private ImageView imageView;
    private TextView result1, result2, resultTextView;
    private boolean isImageFitToScreen;
    public srobject2 sro = new srobject2();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_view);

        sro = (srobject2) getIntent().getSerializableExtra("sro");
        //sro.setContext(this);
        imageView = (ImageView) findViewById(R.id.imageView2);
        result1 = (TextView) findViewById(R.id.result1);
        result2 = (TextView) findViewById(R.id.result2);
        resultTextView = (TextView) findViewById(R.id.isItFoodResult);

        //Bitmap myBM = sro.getImage();
        FoodResultObj ob1 = new FoodResultObj();
        ob1.setImage(BitmapFactory.decodeFile(sro.getFileString()));
        String array[] = new String[3];

        String plsgod = sro.getReceivedString();
        array =  plsgod.split("XX");
        ob1.setStat1(array[0]);
        ob1.setStat2(array[1]);

        resultsVector.add(ob1);
        imageView.setImageBitmap(ob1.getImage());
        result1.setText(ob1.getStat1());
        result2.setText(ob1.getStat2());
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
                    FoodResultObj outputObj = resultsVector.get(vectorIterator);
                    result1.setText(outputObj.getStat1());
                    result2.setText(outputObj.getStat2());

                    String computedResult = computeResult(outputObj.getStat1(), outputObj.getStat2());
                    resultTextView.setText(computedResult);

                    imageView.setImageBitmap(outputObj.getImage());
                    resize();
                    vectorIterator += 1;
                } else {
                    //return to main menu if at end of uploaded pics
                    Intent intent = new Intent(ResultsViewActivity.this, MainActivity.class);
                    startActivity(intent);
                }

            }
        });




    }  //end onCreate method

    /**
     * The AI sees food if stat1 > stat2
     * The AI does not see food if stat2 > stat1
     * The farther apart the two passed variables are, the more confident it is.
     * @param stat1
     * @param stat2
     * @return the phrase to be output
     */
    public String computeResult(double stat1, double stat2) {
        String[] posArray = {
                "I swear by my forge, this food will be safe to eat, traveller.",
                "Prepared by the finest elves of Houose Telvanni, this will be a marvelous meal!",
                "This meal must have been made by magiccraft it is so divine!" };
        String[] negArray = {
                "Wherever there are elves, there are lies.  This here reeks of elvish trickery!",
                "Man cerig?!  That'll kill you young one!",
                "Whoever told you to eat this can go kiss an orc!",
                "I... you really want to eat it?  Well, you can certainly try..."};

        int confidence = Math.abs(stat1 - stat2);
        if (confidence <= 0.5) {
            return "It seems I've failed my perception check, I could not tell you if this is food or not.";
        } else {
            if (stat1 > stat2) {
                int random = (int) (Math.random() * (posArray.length + 1));
                return posArray[random];
            } else {
                int random = (int) (Math.random() * (negArray.length + 1));
                return negArray[random];
            }
        }
    }  //end computeResult method

    /**
     * Make image scale to screen size.
     * Source: https://stackoverflow.com/questions/24463691/how-to-show-imageview-full-screen-on-imageview-click
     */
    public void resize() {
        if (isImageFitToScreen) {
            isImageFitToScreen = false;
            imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            imageView.setAdjustViewBounds(true);
        } else {
            isImageFitToScreen = true;
            imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }  //end resize method


    } //End of ResutsViewActivity
}
