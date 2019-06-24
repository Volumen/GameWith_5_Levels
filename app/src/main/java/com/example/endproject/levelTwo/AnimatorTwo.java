package com.example.endproject.levelTwo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.endproject.levelOne.levelOne;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.ContentValues.TAG;

public class AnimatorTwo extends View {
    Runnable runnable;
    Thread movingObstacle;
    Paint p;
    int screenHeight = getResources().getDisplayMetrics().heightPixels;//1920
    int screenWidth = getResources().getDisplayMetrics().widthPixels;//1080
    int r = 20,lineWidth=40,spaceBeetwenObstacklesX=150;
    float xball = screenWidth/2,yball=1700;
    int xright [];
    int y [];
    int spaceBeetwenObstacklesY=300;
    int k=10;
    boolean moving=true;
    int pkt=0,pktHelp;
    int speed = 50;
    public AnimatorTwo(Context context) {
        super(context);
        p = new Paint();
        xright = new int[10];
        y = new int[10];
        for (int i = 0; i < 10; i++) {
            xright[i] = new Random().nextInt(800) + 100;
            y[i]=0;
        }
        for (int i = 0; i < 9; i++) {
            y[i + 1] = y[i] - spaceBeetwenObstacklesY;
            Log.d("TAH","Y["+i+"]: "+y[i]);
        }
        movingObstacles();
    }
    @Override
    public void onDraw(Canvas canvas)
    {
        p.setTextSize(50);
        p.setColor(Color.BLACK);
        p.setStrokeWidth(lineWidth);
        drawPoints(canvas);
        if(pkt>10 && pkt<=20)
        { speed = 40; }
        else if(pkt>20&&pkt<=30)
        { speed = 30; }
        else if(pkt>30) { speed = 20; }
        for(int i=0;i<k;i++) {

            drawObstacles(canvas, y[i], xright[i]);

            if(y[i]==2000)
            {
                xright[i] = new Random().nextInt(800) + 100;

                y[i]=smallestElement(k);
               // Log.d("TAG","MIN:" +smallestElement(k)+"y[0]: "+y[0]+" a: "+a+" spaceBeetwenO: "+spaceBeetwenObstacklesY );
            }
        }
        canvas.drawCircle(xball,yball,r,p);
    }
    public void drawPoints(Canvas canvas)
    {

        p.setColor(Color.BLUE);
        pkt=pktHelp/10;                //because after crossing obstacle pkt is equal to 10 or 11
        canvas.drawText("Points: "+pkt,700,100,p);
        p.setColor(Color.BLACK);
    }
    public int smallestElement(int k)
    {
        int smallest =0;
        for(int i = 0;i<k;i++)
        {
            if(smallest>y[i])
            {
                smallest=y[i];
            }
        }
        smallest=smallest-spaceBeetwenObstacklesY;
        return smallest;
    }
    public void CheckObstacles(final levelTwo l2) {
        // Log.d(TAG, "x1: " + t[0] +"x3:" +t[4]+"y3:" +t[5]+" x4"+t[6]);
        for (int i = 0; i < 10; i++) {
            if (y[i] > 1650 && y[i] < 1750) {
                System.out.println("y[" + i + "]: " + y[i]);
                if (xball - r <= xright[i] && yball - r <= y[i] + lineWidth / 2 || xball+r>=xright[i]+spaceBeetwenObstacklesX&&yball-r<=y[i]+lineWidth/2 ) {
                    moving=false;
                    movingObstacle.interrupt();
                    l2.StopSensors();
                    l2.addPKT(String.valueOf(pkt));
                    new AlertDialog.Builder(l2)
                            .setTitle("GameOver!")
                            .setMessage("Do you want play again?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    l2.recreate();
                                }
                            })
                            .show();
                }
                else if(y[i]==1700&&yball==1700)
                {
                    pktHelp++;
                }
            }
        }}
    public void drawObstacles(Canvas canvas,int y2, int xright2)
    {

        if(y2<screenHeight+100) {
            canvas.drawLine(0, y2, xright2, y2, p);//left obstacle
            canvas.drawLine(xright2 + spaceBeetwenObstacklesX, y2, screenWidth, y2, p);//right obstacle
        }
    }
    public void movingObstacles()
    {
            runnable = new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        if (moving == true) {
                            for (int i = 0; i < 10; i++) {
                                y[i] = y[i] + 5;
                            }
                        }
                        try {


                            Thread.sleep(speed);
                            invalidate();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

            };
            movingObstacle = new Thread(runnable);
            movingObstacle.start();
        }

    public void GetXY(float x1)
    {
        if(x1>0.5||x1<-0.5) {
            xball = xball - x1;
            Borders();
        }
    }
    public void Borders()
    {
        if(xball>=screenWidth-r) //right border
        {
            xball=screenWidth-r;
            invalidate();
        }
        else if(xball<=r) //left border
        {
            xball=r;
            invalidate();
        }
        else {
            invalidate(); //if don't touch any border
        }
    }
}
