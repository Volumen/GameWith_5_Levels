package com.example.endproject;


import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class Sound {
    private static SoundPool soundPool;
    private static int collectSound;
    private static int hitSound;
    private static int winSound;

    public Sound(Context context)
    {
        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);


        collectSound = soundPool.load(context,R.raw.pewpew,0);
        hitSound = soundPool.load(context,R.raw.hit,0);
        winSound = soundPool.load(context,R.raw.ta_da,0);
    }
    public void playCollectSound()
    {
        soundPool.play(collectSound, 1.0f,1.0f,1,0,1.0f);
    }
    public void playHitSound()
    {
        soundPool.play(hitSound, 1.0f,1.0f,1,0,1.0f);
    }
    public void playWinSound()
    {
        soundPool.play(winSound, 1.0f,1.0f,1,0,1.0f);
    }

}

