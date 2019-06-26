package com.example.endproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.endproject.levelOne.levelOne;
import com.example.endproject.levelOne.levelOneLeaderboard;
import com.example.endproject.levelTwo.levelTwo;
import com.example.endproject.levelTwo.levelTwoLeaderboard;

public class MainActivity extends Activity implements View.OnClickListener {
    TextView t1;
    String nickname = "";
    Intent lOneintent,lTwointent ,newPlayerIntent, lederboardIntent, lederboardIntent2;
    Button l1start, l2start, newPlayer, lederboardButton, lederboardButton2;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        t1 = findViewById(R.id.textView);
        lOneintent = new Intent(MainActivity.this, levelOne.class);
        l1start = findViewById(R.id.l1button);
        l1start.setOnClickListener(this);

        lTwointent = new Intent(MainActivity.this, levelTwo.class);
        l2start = findViewById(R.id.l2button);
        l2start.setOnClickListener(this);

        newPlayerIntent = new Intent(MainActivity.this, NewPlayer.class);
        newPlayer = findViewById(R.id.newPlayerButton);
        newPlayer.setOnClickListener(this);
        t1.setText("");

        lederboardIntent = new Intent(MainActivity.this, levelOneLeaderboard.class);
        lederboardButton = findViewById(R.id.lederboardB);
        lederboardButton.setOnClickListener(this);

        lederboardIntent2 = new Intent(MainActivity.this, levelTwoLeaderboard.class);
        lederboardButton2 = findViewById(R.id.lederboardB2);
        lederboardButton2.setOnClickListener(this);

        sharedPreferences = this.getSharedPreferences("A", Context.MODE_PRIVATE);

    }
    @Override
    public void onResume()
    {
        super.onResume();

        nickname = sharedPreferences.getString("Nickname","");
        //Log.d(TAG, "Mainnickname: "+ nickname+"\n");
        if(!TextUtils.isEmpty(nickname)) {
            t1.setText("Hello " + nickname + " !\nChoose lvl to play :)");
        }
    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("Nickname");
        editor.apply();
        t1.setText("");
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.l1button)
        {
            if(!TextUtils.isEmpty(nickname)) {
                startActivity(lOneintent);
            }
            else
            {
                Toast.makeText(this,"First you need to write your nickname",Toast.LENGTH_SHORT).show();
            }
        }
        else if(v.getId()==R.id.newPlayerButton)
        {
            startActivity(newPlayerIntent);
        }
        else if(v.getId()==R.id.lederboardB)
        {
            startActivity(lederboardIntent);
        }
        else if(v.getId()==R.id.l2button)
        {
            if(!TextUtils.isEmpty(nickname)) {
                startActivity(lTwointent);
            }
            else
            {
                Toast.makeText(this,"First you need to write your nickname",Toast.LENGTH_SHORT).show();
            }
        }
        else if(v.getId()==R.id.lederboardB2)
        {
            startActivity(lederboardIntent2);
        }
    }
}
