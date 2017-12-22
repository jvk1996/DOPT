package com.example.kent.dopt;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Math.abs;

public class MainActivity extends AppCompatActivity {


    private Bar greenbar,redbar,bluebar;
    private Background background;
    private ColorStreet colorStreet;
    private ColorButton red_button,green_button,blue_button;
    private ImageButton imgbutton;
    private Enemy enemy;
    private Handler handler = new Handler();
    private Timer timer = new Timer();
    private boolean red_buttonhold=false,green_buttonhold=false,blue_buttonhold=false;
    //create player
    private Player player;
    //setup scoreText
    private TextView scoreText;
    private ImageView hpBar;
    ImageView enemyHpbar;

    //values to calculate damage
    private float redDiff;
    private float greenDiff;
    private float blueDiff;
    private float damage;

    //Simple timer
    private float roundtimer=0;
    //Testing

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.relative);


        background=new Background(this,0,0,getScreenWidth(),getScreenHeight());
        colorStreet=new ColorStreet(this,getScreenWidth()/2,getScreenHeight()/20);

        redbar= new Bar(this,false,"red",4,getScreenWidth()/8,getScreenHeight()/18);
        greenbar= new Bar(this,true,"green",4,getScreenWidth()/2,redbar.getBar_y()+redbar.getHeight()+20);
        bluebar= new Bar(this,false,"blue",4,getScreenWidth()-(getScreenWidth()/6),getScreenHeight()/18);

        //Create enemy obj
        enemy=new Enemy(this,(background.getHeight()/2)-50,getScreenWidth()/2+50,0.5f,50,3.0f,
                colorStreet.getHeight()+colorStreet.getY());
        //Create enemy emitter
        enemy.EnemyEmitter(3,(getScreenWidth()/2)-50,(getScreenWidth()/2)+50,55);
        red_button=new ColorButton(this,"red",redbar.getBar_x()-75,redbar.getBar_y()+redbar.getHeight()+50);
        green_button=new ColorButton(this,"green",greenbar.getBar_x()+(greenbar.getWidth()/2)-75,redbar.getBar_y()+redbar.getHeight()+50);
        blue_button=new ColorButton(this,"blue",bluebar.getBar_x()-75,bluebar.getBar_y()+bluebar.getHeight()+50);
        //Kent part
        player = new Player(this,getScreenWidth(),getScreenHeight()/20);
        hpBar = new ImageView(this);
        hpBar.setImageResource(R.drawable.hp_bar);

        scoreSetup();

        //Add all view to layout
        layout.addView(background.getImage());
        layout.addView(redbar.getBar());
        layout.addView(redbar.getIndicator());
        layout.addView(greenbar.getBar());
        layout.addView(greenbar.getIndicator());
        layout.addView(bluebar.getBar());
        layout.addView(bluebar.getIndicator());
        layout.addView(colorStreet.getImage());
        layout.addView(red_button.getImageButton());
        layout.addView(green_button.getImageButton());
        layout.addView(blue_button.getImageButton());
        layout.addView(scoreText);
        for(int a=0;a<enemy.getAmount();a++){
            enemyHpbar = new ImageView(this);
            layout.addView(enemy.getEnemyObj(a).getImage());
            layout.addView(enemy.getEnemyObj(a).getEnemyImage());
            layout.addView(enemy.getEnemyObj(a).getHpBar());
            enemyHpbar = enemy.getEnemyObj(a).getHpBar();
            enemyHpSetup(enemyHpbar,enemy.getEnemyObj(a).getEnemyWidth());

        }
        layout.addView(hpBar);

        healthBarSetup();
        Toast.makeText(MainActivity.this,"Player hp:" + player.getHp(), Toast.LENGTH_SHORT).show();
        //layout.addView(enemy.getImage());

        button_onHold();
        //Game Loop
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(red_buttonhold) {
                            redbar.onHold();
                        }
                        if(green_buttonhold) {
                            greenbar.onHold();
                        }
                        if(blue_buttonhold) {
                            bluebar.onHold();
                        }

                        redbar.update();
                        redbar.physic();
                        red_button.physic();
                        greenbar.update();
                        greenbar.physic();
                        green_button.physic();
                        bluebar.update();
                        bluebar.physic();
                        blue_button.physic();
                        colorStreet.setRed((int)redbar.getColorPercent());
                        colorStreet.setGreen((int)greenbar.getColorPercent());
                        colorStreet.setBlue((int)bluebar.getColorPercent());
                        colorStreet.update();
                        enemy.Enemyphysic();

                        hpBar.setScaleX(player.getHpRatio());

                        for(int a=0;a<enemy.getAmount();a++){
                            enemyHpbar=enemy.getEnemyObj(a).getHpBar();
                            enemyHpbar.setScaleX(enemy.getEnemyObj(a).getHpRatio());
                            if(enemy.getEnemyObj(a).notKilled==true){
                                player.takeDamage(10);
                                enemy.getEnemyObj(a).notKilled=false;
                            }
                            //Log.d("Enemy ratio",enemy.getEnemyObj(a).getHpRatio()+" "+enemy.getEnemyObj(a).getHealth_point());
                        }
                        scoreText.setText(Integer.toString(player.getKills()));

                        physic();


                    }
                });
            }
        }, 0, 10);
    }
    //Check Whether bar button is press
    public void button_onHold(){
        red_button.getImageButton().setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if(redbar.getBarIsReady()) {
                        red_buttonhold = true;

                    }

                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    red_buttonhold=false;
                    redbar.setBarIsReady(false);
                    red_button.setButtonIsReady(false);


                }

                return false;
            }
        });
        green_button.getImageButton().setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if(greenbar.getBarIsReady()) {
                        green_buttonhold = true;

                    }
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    green_buttonhold=false;
                    greenbar.setBarIsReady(false);
                    green_button.setButtonIsReady(false);

                }

                return false;
            }
        });
        blue_button.getImageButton().setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(bluebar.getBarIsReady()) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        blue_buttonhold = true;
                        enemy.setPauseEnemy(true);
                    }
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    blue_buttonhold=false;
                    bluebar.setBarIsReady(false);
                    blue_button.setButtonIsReady(false);
                    enemy.setPauseEnemy(false);
                }

                return false;
            }
        });

    }
    public void physic(){
        if(player.getHp()<=0){
            Intent endscene = new Intent(this,endgame.class);
            startActivity(endscene);
            //Game over here
        }
        if(red_buttonhold||green_buttonhold||blue_buttonhold){
            enemy.setPauseEnemy(true);
        }
        else{
            enemy.setPauseEnemy(false);
        }
        if(!redbar.getBarIsReady()&&!greenbar.getBarIsReady()&&!bluebar.getBarIsReady()){
            enemy.setPauseEnemy(true);
            roundtimer+=0.01f;
            if(roundtimer>=1.0f) {
                for(int count = 0;count<enemy.getAmount();count++){
                    enemy.getEnemyObj(count).damageCalculation(redbar.getColorPercent(),
                            greenbar.getColorPercent(),
                            bluebar.getColorPercent());

                }
                redbar.resetAll();
                red_button.resetButton();
                greenbar.resetAll();
                green_button.resetButton();
                bluebar.resetAll();
                blue_button.resetButton();
                enemy.setPauseEnemy(false);
                roundtimer=0.0f;
            }
        }
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;

    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public void scoreSetup(){
        scoreText = new TextView(this);
        scoreText.setWidth(getScreenWidth());
        scoreText.setGravity(Gravity.CENTER_HORIZONTAL);
        scoreText.setText(Integer.toString(player.getKills()));
        scoreText.setTextSize(getScreenHeight()/20);
        scoreText.setTextColor(Color.parseColor("#FF0000"));

    }

    public void healthBarSetup(){
        hpBar.setScaleType(ImageView.ScaleType.FIT_XY);
        hpBar.getLayoutParams().width=getScreenWidth()-(int)(getScreenHeight()*0.15);
        hpBar.setX((int)(getScreenHeight()*0.15));
        hpBar.getLayoutParams().height=getScreenHeight()/20;
        hpBar.setPivotX(0);
        hpBar.setScaleX(player.getHpRatio());
    }

    public void enemyHpSetup(ImageView enemyHp, int width){
        enemyHp.setScaleType(ImageView.ScaleType.FIT_XY);
        enemyHp.getLayoutParams().height=(int)(width*0.1);
        enemyHp.getLayoutParams().width=(int)(width*1.5);
        enemyHp.setPivotX(0);
        enemyHp.setScaleX(enemy.getHpRatio());

    }
}
