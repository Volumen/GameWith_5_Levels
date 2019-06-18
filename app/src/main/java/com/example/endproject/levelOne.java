package com.example.endproject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

import static android.content.ContentValues.TAG;

public class levelOne extends Activity implements SensorEventListener {
    SensorManager sensorManager;
    Sensor sensor;
    Animator a1;

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

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] values = event.values;
        float x2 = values[0];
        float y2 = values[1];
        a1.GetXY(x2,y2);
        a1.CheckObstacles(this);
        a1.CheckHole(this);
        Log.d(TAG,"I'm checking");
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
