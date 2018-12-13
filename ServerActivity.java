package west.brian.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ServerActivity extends AppCompatActivity {
    private int index;
    private int numPics;
    public srobject2 sro = new srobject2();
    public Context c;
    private ImageView imView;
    private TextView stat1Tv, stat2Tv, resultTv;
    @Override

    /**
     * Gets the first image on server and displays image as well as initialize values on create
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sro = (srobject2) getIntent().getSerializableExtra("sro");
        setContentView(R.layout.activity_server);
        index = 0;
        stat1Tv = (TextView) findViewById(R.id.stat1);
        stat2Tv = (TextView) findViewById(R.id.stat2);
        resultTv = (TextView) findViewById(R.id.resultView);
        imView =  (ImageView) findViewById(R.id.imageView);
        sro.requestFileAmount();
        sro.getPic("0");
        while(!sro.getPicGotten()){}
        sro.setPicGotten(false);
        Bitmap bmap = sro.getImage();
        imView.setImageBitmap(bmap);
        String temp = sro.getReceivedFilePath();

        String array[] = temp.split("XX");
        stat1Tv.setText(array[0]);
        stat2Tv.setText(array[1]);
        temp = computeResult(Double.parseDouble(array[0]),Double.parseDouble(array[1]));
        resultTv.setText(temp);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        MediaPlayer mp;
        mp = MediaPlayer.create(this, R.raw.om);
        mp.setLooping(true);
        mp.start();

    }
    /**
     * Back function returns app to main menu
     */
    public void Back(View v){
       startActivity(new Intent(ServerActivity.this, MainActivity.class));
    }

    /**
     * Previous function get the previous image on server to display
     */
    public void Previous(View v){
        if(numPics == 0){
            numPics = sro.getFileAmount();
        }
        if (index == 0){
            index = numPics-1;
        }else{
            index--;
        }
        String temp1 = Integer.toString(index);
        sro.getPic(temp1);
        while(!sro.getPicGotten()){}
        sro.setPicGotten(false);
        Bitmap bmap = sro.getImage();
        imView.setImageBitmap(bmap);
        String temp = sro.getReceivedFilePath();
        String array[] = temp.split("XX");
        stat1Tv.setText(array[0]);
        stat2Tv.setText(array[1]);
        temp = computeResult(Double.parseDouble(array[0]),Double.parseDouble(array[1]));
        resultTv.setText(temp);
    }
    /**
     * Next function get the next image on server to display
     */
    public void Next(View v){
        if(numPics == 0){
            numPics = sro.getFileAmount();
        }
        if(index == numPics-1){
            index = 0;
        }else{
            index++;
        }
        String temp1 = Integer.toString(index);
        sro.getPic(temp1);
        while(!sro.getPicGotten()){}
        sro.setPicGotten(false);
        Bitmap bmap = sro.getImage();
        imView.setImageBitmap(bmap);
        String temp = sro.getReceivedFilePath();

        String array[] = temp.split("XX");
        stat1Tv.setText(array[0]);
        stat2Tv.setText(array[1]);
        temp = computeResult(Double.parseDouble(array[0]),Double.parseDouble(array[1]));
        resultTv.setText(temp);

    }

    /**
     * Computes the output of whether image is food from returned results
     */
    public String computeResult(double stat1, double stat2) {
        String[] posArray = {
                "\nI swear by my forge, this food will be safe to eat, traveller.",
                "\nPrepared by the finest elves of Houose Telvanni, this will be a marvelous meal!",
                "\nThis meal must have been made by magiccraft it is so divine!" };
        String[] negArray = {
                "\nWherever there are elves, there are lies.  This here reeks of elvish trickery!",
                "\nMan cerig?!  That'll kill you young one!",
                "\nWhoever told you to eat this can go kiss an orc!",
                "\nI... you really want to eat it?  Well, you can certainly try..."};

        double confidence = Math.abs(stat1 - stat2);
        if (confidence <= 0.5) {
            return "\nIt seems I've failed my perception check, I could not tell you if this is food or not.";
        } else {
            if (stat1 > stat2) {
                int random = (int) (Math.random() * (posArray.length - 1));
                return posArray[random];
            } else {
                int random = (int) (Math.random() * (negArray.length - 1));
                return negArray[random];
            }
        }
    }  //end computeResult method

}
