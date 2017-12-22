package com.example.kent.dopt;
/**
 * Created by Kent on 17-Dec-17.
 */
import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.widget.ImageView;

public class ColorStreet {
    private ImageView image;
    private float x,y;
    private int red,green,blue,alpha;
    private Paint paint;
    private ColorFilter filter;
    private float Width, Height;

    public  ColorStreet(Context context, float position_x, float position_y){
        this.image = new ImageView(context);
        //set the image in to the bar ImageView
        image.setImageResource(R.drawable.color_street);
        paint=new Paint();
        this.red=0;
        this.green=0;
        this.blue=0;
        this.alpha=0;
        this.x = position_x-(float)image.getDrawable().getIntrinsicWidth()/2;
        this.y=position_y;
        image.setX(this.x);
        image.setY(this.y);
        this.Width=image.getDrawable().getIntrinsicWidth();
        this.Height=image.getDrawable().getIntrinsicHeight();
    }
    public void setPosition(float position_x,float position_y){
        this.x = position_x-(float)image.getDrawable().getIntrinsicWidth()/2;
        this.y=position_y;
    }
    public void update(){

        filter = new LightingColorFilter(Color.argb(alpha,red,green,blue), 0);
        image.setColorFilter(filter);
    }
    public void setAlpha(int alpha){
        this.alpha=alpha;
    }
    public void setRed(int red){
        this.red=red;
    }
    public void setGreen(int green){
        this.green=green;
    }
    public void setBlue(int blue){
        this.blue=blue;
    }
    public ImageView getImage(){
        return this.image;
    }
    public float getWidthh(){
        return this.Width;
    }
    public float getHeight(){
        return this.Height;
    }
    public float getX(){
        return this.x;
    }
    public float getY(){
        return this.y;
    }
}
