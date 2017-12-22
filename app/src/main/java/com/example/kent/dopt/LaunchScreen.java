package com.example.kent.dopt;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class LaunchScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_screen);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    public void startGame(View view){
        Intent game = new Intent(this,MainActivity.class);
        startActivity(game);
    }

    public void settingButton(View view){
        Toast.makeText(LaunchScreen.this,"Setting button pressed.",Toast.LENGTH_SHORT).show();
    }
}
