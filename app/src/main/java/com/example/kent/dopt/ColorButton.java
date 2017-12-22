package com.example.kent.dopt;
/**
 * Created by Kent on 17-Dec-17.
 */

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.widget.ImageButton;

public class ColorButton {
    private ImageButton imageButton;
    //A pen to draw the color
    private ColorFilter filter;
    //color setting
    private float red,green,blue;
    //Button is ready to manipulate the color bar
    private boolean ButtonIsReady;
    private float Width,Height;
    public ColorButton(Context context, String button_name, float x, float y){

        if(button_name=="red") {
            imageButton = new ImageButton(context);
            imageButton.setImageResource(R.drawable.red_button);
        }else if (button_name == "green"){
            imageButton = new ImageButton(context);
            imageButton.setImageResource(R.drawable.green_button);
        }else if (button_name =="blue"){
            imageButton = new ImageButton(context);
            imageButton.setImageResource(R.drawable.blue_button);
        }
        //Set position
        imageButton.setX(x);
        imageButton.setY(y);
        //This to make the clickable area in to rounded shape
        imageButton.setBackgroundResource(R.drawable.roundcorner);

        this.ButtonIsReady=true;
    }
    public void physic(){
        if(!ButtonIsReady){
            filter=new LightingColorFilter(Color.argb(0,80,80,80),0);
            imageButton.setColorFilter(filter);
        }
    }
    //get imageButton to add into the layout
    public ImageButton getImageButton(){
        return imageButton;
    }
    //get width of the button
    public float getWidth(){
        return imageButton.getDrawable().getIntrinsicWidth();
    }
    //get height of the button
    public float getHeight(){
        return imageButton.getDrawable().getIntrinsicHeight();
    }
    //set button is ready
    public void setButtonIsReady(boolean buttonIsReady){
        this.ButtonIsReady=buttonIsReady;
    }

    public void resetButton(){
        filter=new LightingColorFilter(Color.argb(0,255,255,255),0);
        imageButton.setColorFilter(filter);
        ButtonIsReady=true;
    }
}
