package com.example.endproject.levelOne;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.endproject.DatabaseHelper;

public class levelOne extends Activity implements SensorEventListener {
    private static final String TAG = "Nickname: ";
    SensorManager sensorManager;
    Sensor sensor;
    Animator a1;
    SharedPreferences sharedPreferences;

    DatabaseHelper dbase;
    //DatabaseNicknames dbnicknames;

    String nickname ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN); //hiding upper bar and go fullscreen

        a1 = new Animator(this);
        setContentView(a1);
        a1.setBackgroundColor(Color.parseColor("#BA4A00"));


        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        StartSensors();

        sharedPreferences = this.getSharedPreferences("A", Context.MODE_PRIVATE);
        nickname = sharedPreferences.getString("Nickname","");

       dbase = new DatabaseHelper(this);

    }
    public void addTime(String time)
    {
        dbase.addData(nickname,time);
    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();

    }
    public void Start()
    {
        Intent l11 = new Intent(levelOne.this , levelOne.class);
        startActivity(l11);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] values = event.values;
        float x2 = values[0];
        float y2 = values[1];
        a1.GetXY(x2,y2);
        a1.CheckObstacles(this);
        a1.CheckHole(this);
        //Log.d(TAG,"I'm checking");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    public void StopSensors()
    {
        sensorManager.unregisterListener(this);
    }
    public void StartSensors()
    {
        sensorManager.registerListener( this, sensor,SensorManager.SENSOR_DELAY_FASTEST);
    }
}
