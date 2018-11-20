package com.wsu.caltabellotta.seefood;

import android.media.Image;

public class FoodResultObj {

    private Image img;
    private double stat1, stat2;

    public FoodResultObj() {
        Image img;
        double stat1;
        double stat2;
    }

    public FoodResultObj(Image newImg, double newStat1, double newStat2) {
        this.img = newImg;
        this.stat1 = newStat1;
        this.stat2 = newStat2;
    }

    public Image getImage() {
        return this.img;
    }
    public void setImage(Image newImg) {
        this.img = newImg;
    }

    public double getStat1() {
        return this.stat1;
    }
    public void setStat1(double newStat1) {
        this.stat1 = newStat1;
    }

    public double getStat2() {
        return this.stat2;
    }
    public void setStat2(double newStat2) {
        this.stat1 = newStat2;
    }

}  //End FoodResultObj Class
