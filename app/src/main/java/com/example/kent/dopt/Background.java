package com.example.kent.dopt;
/**
 * Created by Kent on 17-Dec-17.
 */

import android.content.Context;
import android.widget.ImageView;

public class Background {
    private ImageView image;
    private float x,y,dx,WIDTH,HEIGHT;

    public  Background(Context context,float x,float y, int WIDTH, int HEIGHT){
        image= new ImageView(context);
        image.setImageResource(R.drawable.foreststreet);
        this.WIDTH=WIDTH;
        this.HEIGHT=HEIGHT;
        image.setScaleType(ImageView.ScaleType.FIT_XY);
        this.x=x;
        this.y=y;
        image.setX(x);
        image.setY(y);
    }
    public ImageView getImage(){
        return this.image;
    }
    public int getHeight(){
        return image.getDrawable().getIntrinsicHeight();
    }
    public int getWidth(){
        return image.getDrawable().getIntrinsicWidth();
    }
}

