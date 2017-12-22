package com.example.kent.dopt;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

/**
 * Created by Kent on 17-Dec-17.
 */

public class Text {
    private TextView textViewtext;
    private float pos_x,pos_y;

    public Text(Context context,String text, float x, float y){
        textViewtext = new TextView(context);
        textViewtext.setX(x-textViewtext.getWidth()/2);
        textViewtext.setY(y);
        this.pos_x=x;
        this.pos_y=y;
        textViewtext.setText(text);
        textViewtext.setTextSize(20);
        textViewtext.setTextColor(Color.parseColor("#FFFFFF"));
    }

    public TextView getTextViewtext(){
        return textViewtext;
    }
}
