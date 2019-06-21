package com.example.endproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.endproject.databases.DatabaseHelper;

public class MainActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "Nickname: ";
    Bundle bundle;
    TextView t1;
    String nickname = "",nickname2;
    Intent lOneintent ,newPlayerIntent, lederboardIntent;
    Button start, newPlayer, lederboardButton;
    SharedPreferences sharedPreferences;
    String myPref = "";
    DatabaseHelper dbase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        t1 = findViewById(R.id.textView);
        //t1.setText("saa");
        lOneintent = new Intent(MainActivity.this, levelOne.class);
        start = findViewById(R.id.button);
        start.setOnClickListener(this);

        newPlayerIntent = new Intent(MainActivity.this, NewPlayer.class);
        newPlayer = findViewById(R.id.newPlayerButton);
        newPlayer.setOnClickListener(this);
        t1.setText("");

        lederboardIntent = new Intent(MainActivity.this, levelOneLeaderboard.class);
        lederboardButton = findViewById(R.id.lederboardB);
        lederboardButton.setOnClickListener(this);

        //dbase = new DatabaseHelper(this);
        //String name = dbase.getName();
        sharedPreferences = this.getSharedPreferences("A", Context.MODE_PRIVATE);
    }
    @Override
    public void onResume()
    {
        super.onResume();


        nickname = sharedPreferences.getString("Nickname","");
        Log.d(TAG, "Mainnickname: "+ nickname+"\n");
        nickname2 = String.valueOf(t1.getText());
        //nickname2 = nickname;
        if(!TextUtils.isEmpty(nickname)) {
            t1.setText("Hello " + nickname + " !\nChoose lvl to play :)");
        }
        //Log.d(TAG,"MainNickname: "+nickname+"\nNickname2: "+ nickname2);
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
//    @Override
//    public void onDestroy()
//    {
//        super.onDestroy();
//
//    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.button)
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
    }
}
