package com.example.kent.dopt;
/**
 * Created by Kent on 17-Dec-17.
 */
import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.abs;

public class Enemy {
    private Context context;
    private List<Enemy> enemy;
    private ImageView image;
    private ImageView GhostImage;
    private ImageView hpBar;
    //Movement
    private float position_x,position_y,move_speed,MaxDistance,Start_x,Start_y;
    //For generate random number on position x
    private float min_x,max_x;
    //Setting
    private float enemySpawnIntervel;
    private float timer,timeCounter=0.01f;
    private float ratio;
    private int red,green,blue,alpha=0;
    private int health_point,maxHp,amount;
    private boolean alive;
    //EnemyStopTrigger
    private boolean pauseEnemy=false;
    private Random random = new Random();

    //Color and timer
    private ColorFilter filter;
    private ColorFilter GhostFilter;
    private  long startTime;

    public boolean notKilled = false;

    private float redDiff;
    private float greenDiff;
    private float blueDiff;
    private float damage;

    //private float boundary;

    public  Enemy(Context context, float position_x, float position_y, float move_speed, int health_point, float enemySpawnIntervel,float maxDistance){

        this.context=context;
        image=new ImageView(context);
        image.setImageResource(R.drawable.botton_plane);
        GhostImage=new ImageView(context);
        GhostImage.setImageResource(R.drawable.ghost);
        hpBar = new ImageView(context);
        hpBar.setImageResource(R.drawable.hp_bar);
        hpBar.setX(position_x);
        hpBar.setY(position_y);

        //set movemnt
        this.position_x=position_x;
        this.position_y=position_y;
        this.Start_x=position_x;
        this.Start_y=position_y;
        this.move_speed=move_speed;
        //set behavior and timer
        this.health_point=health_point;
        maxHp=health_point;
        this.enemySpawnIntervel=enemySpawnIntervel;
        this.timer=enemySpawnIntervel;
        this.alive=false;
        this.MaxDistance=maxDistance;
        //Random color when obj is created
        this.alpha=127;
        this.red=random.nextInt(250)+5;
        this.green=random.nextInt(250)+5;
        this.blue=random.nextInt(250)+5;
        //Start timer when obj is created
        this.startTime=System.currentTimeMillis();
        //Set initiate position
        image.setX(3000);
        GhostImage.setX(3000);
        hpBar.setX(3000);

        //this.boundary=boundary;
    }

    public void setPosition_x(float position_x){
        this.position_x=position_x;
    }
    public void setPosition_y(float position_y){
        this.position_y=position_y;
    }
    public void setMove_speed(float move_speed){
        this.move_speed=move_speed;
    }
    public void setHealth_point(int health_point){
        this.health_point=health_point;
    }
    public int getHealth_point(){
        return this.health_point;
    }
    public float getPosition_x(){
        return this.position_x;
    }
    public float getPosition_y(){
        return this.position_y;
    }
    public float getMove_speed(){
        return this.move_speed;
    }
    public void setAlpha(int alpha){this.alpha=alpha;}
    public int getRed(){
        return this.red;
    }
    public int getGreen(){
        return this.green;
    }
    public int getBlue(){
        return this.blue;
    }
    public ImageView getImage(){return this.image;}
    //here is to let u do the enemy physic
    public void physic(){
        if(alive==false){
            image.setX(3000);
            GhostImage.setX(3000);
            hpBar.setX(3000);
            position_y=Start_y;
            this.red=random.nextInt(250)+5;
            this.green=random.nextInt(250)+5;
            this.blue=random.nextInt(250)+5;
            setRandomEnemyMovementSpeed(0,1);
            //Log.d("Value tag:",this.red+" "+this.green+" "+this.blue);
            health_point=50;
        }
        else if(alive==true && pauseEnemy == false){
            position_y+=move_speed;
            filter = new LightingColorFilter(Color.argb(alpha,red,green,blue), 0);
            image.setColorFilter(filter);
            image.setX(position_x);
            image.setY(position_y);
            GhostFilter=new LightingColorFilter(Color.argb(alpha,255,255,255),0);
            GhostImage.setAlpha(alpha);
            GhostImage.setColorFilter(GhostFilter);
            GhostImage.setX(position_x);
            GhostImage.setY(position_y-(GhostImage.getDrawable().getIntrinsicHeight()/1)+20);
            hpBar.setX(position_x-(GhostImage.getDrawable().getIntrinsicHeight()/20));
            hpBar.setY(position_y-(GhostImage.getDrawable().getIntrinsicHeight()/1)+15);
            //if(health_point<=0){
              //  alive=false;
            //}
            if(position_y>MaxDistance){
                notKilled=true;
                reset();
                //damagePlayer(5);
            }
        }
    }
    public void reset(){
        alive=false;
        position_y=Start_y;
        this.red=random.nextInt(250)+5;
        this.green=random.nextInt(250)+5;
        this.blue=random.nextInt(250)+5;
        health_point=maxHp;
    }

    public void damagePlayer(int damage){
    }
    //add multiple enemy obj into Enemy ArrayLit
    public void EnemyEmitter(int amount, float min_x,float max_x,float position_y){
        this.amount=amount;
        enemy=new ArrayList<Enemy>();

        this.min_x=min_x;
        this.max_x=max_x;
        for(int a=0;a<amount;a++){

            Enemy clone = new Enemy(this.context,position_x,position_y,move_speed,this.health_point,enemySpawnIntervel,MaxDistance);
            enemy.add(clone);


        }
    }
    //a loop for run every enemy particle physic
    public void Enemyphysic(){
        if(pauseEnemy == false) {
            timer += timeCounter;
            if (timer >= enemySpawnIntervel) {
                for (int a = 0; a < amount; a++) {
                    if (enemy.get(a).alive == false) {
                        enemy.get(a).position_x = (randomNumber((int) min_x, (int) max_x));
                        timer = 0;
                        enemy.get(a).alive = true;
                        break;
                    }
                }
            }
            for (int a = 0; a < amount; a++) {
                enemy.get(a).physic();
            }
        }
    }
    //random number generator you can use on random color and position x of the enemy
    public int randomNumber(int min_range,int max_range){
        Random rn = new Random();
        int n = max_range - min_range + 1;
        int i = rn.nextInt() % n;
        return  min_range + i;
    }
    //Here for u to calculate the damage and rmb if die set alive to false
    public void damageCalculation(float red,float green, float blue, Player player){
        redDiff = abs(255 - abs(this.red- red))/255;
        greenDiff=abs(255 - abs(this.green- green))/255;
        blueDiff=abs(255 - abs(this.blue- blue))/255;
        damage=80*redDiff*greenDiff*blueDiff;
        takeDamage(Math.round(damage),player);
    }
    //get particle amount for tell the add the view amount into the layout
    public int getAmount(){return this.amount;}
    //get enemy emmiter obj for add in the layout
    public Enemy getEnemyObj(int index){
        return this.enemy.get(index);
    }
    //Set the timer to respawn a new enemy
    public void setTimer(float timer){
        this.timer=timer;
    }
    //Set all the enemy movement speed
    public void setAllEnemyMovementSpeed(float speed){
        for(int a=0;a<amount;a++){
            enemy.get(a).setMove_speed(speed);
        }
    }
    //Set enemy with random Speed
    public void setRandomEnemyMovementSpeed(int min_speed,int max_speed){
        float speed=randomNumber(min_speed,max_speed);
        if(speed<=0){
            speed=0.5f;
        }
        setMove_speed(speed);
    }
    //Set all enemy with random Speed
    public void setAllEnemyMovementSpeed(int min_speed,int max_speed){
        int speed=randomNumber(min_speed,max_speed);
        for(int a=0;a<amount;a++){
            enemy.get(a).setMove_speed(speed);
        }
    }
    //Set stop enemy boolean
    public void setPauseEnemy(boolean pauseEnemy){
        this.pauseEnemy=pauseEnemy;
    }
    //get EnemyImage imageview
    public ImageView getEnemyImage(){return GhostImage;}

    public ImageView getHpBar(){return hpBar;}

    public boolean getStatus(){
        return alive;
    }

    public void takeDamage(int damage, Player player){
        if(alive){
            this.health_point -= damage;
            if(health_point<=0){
                player.addKills();
                reset();
            }
        }
    }

    public int getEnemyWidth(){
        return GhostImage.getDrawable().getIntrinsicHeight();
    }

    public float getHpRatio(){
        ratio = (float)health_point/maxHp;
        if (ratio>=0){
            return ratio;
        }else return 0;

    }

}