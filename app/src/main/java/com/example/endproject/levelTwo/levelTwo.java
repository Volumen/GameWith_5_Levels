package com.example.endproject.levelTwo;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.WindowManager;

public class levelTwo extends Activity implements SensorEventListener {
    AnimatorTwo a1;
    SensorManager sensorManager;
    Sensor sensor;
    DatabaseHelperTwo databaseHelper;
    SharedPreferences sharedPreferences;
    String nickname;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN); //hiding upper bar and go fullscreen

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        StartSensors();

        a1 = new AnimatorTwo(this);
        a1.setBackgroundColor(Color.parseColor("#85B1E5EC"));
        setContentView(a1);

        databaseHelper = new DatabaseHelperTwo(this);

        sharedPreferences = this.getSharedPreferences("A", Context.MODE_PRIVATE);
        nickname = sharedPreferences.getString("Nickname","");

    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] values = event.values;
        float x2 = values[0];
        a1.GetXY(x2);
        a1.CheckObstacles(this);

    }
    public void addPKT(String pkt)
    {
        databaseHelper.addData(nickname,pkt);
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
