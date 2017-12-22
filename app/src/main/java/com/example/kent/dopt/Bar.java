package com.example.kent.dopt; /**
 * Created by Kent on 17-Dec-17.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.widget.ImageView;

public class Bar {
    //Starting Position for reset bar and indicator position every round
    private float start_indicator_x,start_indicator_y;
    //
    private ImageView bar, indicator;
    private float bar_x, bar_y, indicator_x, indicator_y, indicator_centerx, indicator_centery;
    private Bitmap bar_image, indicator_image;
    private boolean landscape;
    private float indicator_speed = 1;
    private float colorPercent;
    private boolean increment_stat, decrement_stat, curren_stat;
    private float Width,Height;

    //Color filter for bar (for changing to black color is bar is not ready for manipulate)
    private ColorFilter filter;
    //Game Play
    private boolean barIsReady;

    public Bar(Context context, boolean landscape, String colorbar, float indicator_speed, float x, float y) {
        //Check which color of bar want to create
        if (colorbar == "green") {
            //set bar ImageView
            this.bar = new ImageView(context);
            //set the image in to the bar ImageView
            bar.setImageResource(R.drawable.green_bar);
            //set indicator ImageView
            this.indicator = new ImageView(context);
            //set the image into the Indicator ImageView
            indicator.setImageResource(R.drawable.green_indicator);
        } else if (colorbar == "red") {
            this.bar = new ImageView(context);
            bar.setImageResource(R.drawable.red_bar);
            this.indicator = new ImageView(context);
            indicator.setImageResource(R.drawable.red_indicator);
        } else if (colorbar == "blue") {
            this.bar = new ImageView(context);
            bar.setImageResource(R.drawable.blue_bar);
            this.indicator = new ImageView(context);
            indicator.setImageResource(R.drawable.blue_indicator);
        }
        this.landscape=landscape;
        //get the center of indicator and store into variable
        indicator_centerx = (float) indicator.getDrawable().getIntrinsicWidth() / 2;
        indicator_centery = ((float) indicator.getDrawable().getIntrinsicHeight() / 2);
        //if the bar is landscape
        if (landscape == true) {
            //let the development easy to set position on x-position
            this.bar_x = x - ((float) bar.getDrawable().getIntrinsicWidth() / 2.0f);
            this.bar_y = y;
            //set the middle of x-axis of indicator
            indicator_x = x - indicator_centerx - ((float) bar.getDrawable().getIntrinsicWidth() / 2);
            this.start_indicator_x=indicator_x;
            //set the middle of y-axis of indicator
            indicator_y = y - (indicator_centery - ((float) bar.getDrawable().getIntrinsicHeight() / 2));
            this.start_indicator_y=indicator_y;
        } else {
            this.bar_x = x;
            this.bar_y = y;
            indicator_x = bar_x - (indicator_centerx - ((float) bar.getDrawable().getIntrinsicWidth() / 2));
            this.start_indicator_x=indicator_x;
            indicator_y = bar_y + bar.getDrawable().getIntrinsicHeight() - indicator_centery;
            this.start_indicator_y=indicator_y;
        }

        this.barIsReady=true;
        this.indicator_speed=indicator_speed;

    }

    //set the speed of indicator
    public void setIndicator_speed(float indicator_speed) {
        this.indicator_speed = indicator_speed;
    }

    //get the bar imageView
    public ImageView getBar() {
        return this.bar;
    }
    public void update(){
        this.bar.setX(bar_x);
        this.bar.setY(bar_y);
        this.indicator.setX(indicator_x);
        this.indicator.setY(indicator_y);
    }
    //get the indicator imageView
    public ImageView getIndicator() {
        return this.indicator;
    }
    //Physic of the button(like manipulating one time color per round)
    public void physic(){
        if(!barIsReady){
            filter = new LightingColorFilter(Color.argb(0,80,80,80), 0);
            this.bar.setColorFilter(filter);
        }
    }
    //if button in hold
    public void onHold() {
        if(barIsReady=true) {
            if (landscape == true) {
                if (curren_stat == increment_stat) {
                    indicator_x += indicator_speed;
                    if (indicator_x >= (bar_x + bar.getDrawable().getIntrinsicWidth() - (indicator.getDrawable().getIntrinsicWidth() / 2))) {
                        decrement_stat = true;
                        curren_stat = decrement_stat;
                        increment_stat = false;
                    }
                } else if (curren_stat == decrement_stat) {
                    indicator_x -= indicator_speed;
                    if (indicator_x <= (bar_x) - (indicator.getDrawable().getIntrinsicWidth() / 2)) {
                        increment_stat = true;
                        curren_stat = increment_stat;
                        decrement_stat = false;
                    }
                }


            } else if (landscape == false) {
                if (curren_stat == increment_stat) {
                    indicator_y -= indicator_speed;
                    if (indicator_y <= (bar_y - (indicator.getDrawable().getIntrinsicHeight() / 2))) {
                        decrement_stat = true;
                        curren_stat = decrement_stat;
                        increment_stat = false;
                    }
                } else if (curren_stat == decrement_stat) {
                    indicator_y += indicator_speed;
                    if (indicator_y >= (bar_y) + bar.getDrawable().getIntrinsicHeight() - indicator.getDrawable().getIntrinsicHeight() / 2) {
                        increment_stat = true;
                        curren_stat = increment_stat;
                        decrement_stat = false;
                    }
                }

            }
        }

    }
    //get the rgb value
    public float getColorPercent(){
        if(landscape==true) {
            return (((indicator_x + indicator_centerx) - bar_x) * (100.0f / (float) bar.getDrawable().getIntrinsicWidth()))*255/100;
        }
        else if(landscape==false){
            return (100.0f-((indicator_y + indicator_centery) - bar_y) * (100.0f / (float) bar.getDrawable().getIntrinsicHeight()))*255/100;
        }
        return 0.0f;
    }
    public float getBar_x(){return bar_x;}
    public float getBar_y(){return bar_y;}
    public float getWidth(){return bar.getDrawable().getIntrinsicWidth();}
    public float getHeight(){return bar.getDrawable().getIntrinsicHeight();}
    public void setBarIsReady(boolean barIsReady){
        this.barIsReady=barIsReady;
    }
    public boolean getBarIsReady(){
        return this.barIsReady;
    }
    public void resetAll(){
        filter = new LightingColorFilter(Color.argb(0,255,255,255), 0);
        this.bar.setColorFilter(filter);
        this.indicator_x=start_indicator_x;
        this.indicator_y=start_indicator_y;
        this.barIsReady=true;
    }
}