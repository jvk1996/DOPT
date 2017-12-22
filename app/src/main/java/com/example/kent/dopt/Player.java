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
    public static int kills;
    private int hp;

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

    public static void addKills(int n){
        kills += n;
    }

    public void takeDamage(int damage){
        hp -= damage;
    }

    public ImageView getImageViewHpBar(){
        return imageViewHpBar;
    }
}
