package west.brian.myapplication;

import android.graphics.Bitmap;

public class FoodResultObj {

    private Bitmap img;
    private String stat1, stat2;

    public FoodResultObj() {
       // Bitmap img;
       // double stat1;
       // double stat2;
    }

    /**
     * Constructor to create a new object containing an image Bitmap and two Strings for the statistics.
     * @param newImg
     * @param newStat1
     * @param newStat2
     */
    public FoodResultObj(Bitmap newImg, String newStat1, String newStat2) {
        this.img = newImg;
        this.stat1 = newStat1;
        this.stat2 = newStat2;
    }

    public Bitmap getImage() {
        return this.img;
    }
    public void setImage(Bitmap newImg) {
        this.img = newImg;
    }

    public String getStat1() {
        return this.stat1;
    }
    public void setStat1(String newStat1) {
        this.stat1 = newStat1;
    }

    public String getStat2() {
        return this.stat2;
    }
    public void setStat2(String newStat2) {
        this.stat2 = newStat2;
    }

} //End FoodResultObj Class
