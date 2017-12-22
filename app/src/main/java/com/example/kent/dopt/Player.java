package com.example.kent.dopt;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

/**
 * Created by Kent on 17-Dec-17.
 */

public class Player {

    private ImageView imageViewHpBar;
    private int maxHp;
    private int hp;
    private  int kills;

    public Player(Context context,int width,int height){
        imageViewHpBar = new ImageView(context);
        imageViewHpBar.setImageResource(R.drawable.hp_bar);
        kills = 0;
        hp = 100;
        maxHp = hp;
    }

    public int getKills(){
        return kills;
    }

    public int getHp(){
        return hp;
    }

    public float getHpRatio(){
        return (float)hp/maxHp;
    }

    public void addKills(){
        kills += 1;
    }

    public void takeDamage(int damage){
        hp -= damage;
    }

    public ImageView getImageViewHpBar(){
        return imageViewHpBar;
    }
}
