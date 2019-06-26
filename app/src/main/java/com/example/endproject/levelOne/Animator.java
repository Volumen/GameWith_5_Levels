package com.example.endproject.levelOne;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.example.endproject.R;
import com.example.endproject.Sound;

import static android.content.ContentValues.TAG;

public class Animator extends View {
    private Runnable runnable;
    private Thread CounterThread;
    private Toast t1;
    private Sound s1;
    private float screenHeight = getResources().getDisplayMetrics().heightPixels;
    private float screenWidth = getResources().getDisplayMetrics().widthPixels;
    private Paint p;
    private Bitmap banana ,bananaResizedOne,bananaResizedTwo, monkey;
    private float x=screenWidth/2, y=screenHeight-200;
    private boolean bitmapOneExist = true, bitmapTwoExist = true, pewsoundOne = true,
            pewsoundTwo = true, endOfGame=false, showMessage = false;
    private int bananaOneX = 100, bananaOneY = 200,
        bananaTwoX = 260, bananaTwoY = 1120,
        bananasWidth = 50, bananasHeight = 50,
        holeX = 900, holeY = 1800, holeR = 40,
        i=0, collectedBananas = 0, t[], counter = 0, width = 40 ,r = 20;
    DatabaseHelper dbase;

    public Animator(final Context context) {
        super(context);

        banana = BitmapFactory.decodeResource(getResources(), R.drawable.banana);
        monkey = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.monkey),r*2,r*2,true);
        bananaResizedOne = Bitmap.createScaledBitmap(banana, bananasWidth, bananasHeight, true);
        bananaResizedTwo = Bitmap.createScaledBitmap(banana, bananasWidth, bananasHeight, true);

        dbase = new DatabaseHelper(context);

        p = new Paint();
        s1 = new Sound(context.getApplicationContext());
        t= new int[50];

        //TIMER
        runnable = new Runnable() {
            @Override
            public void run() {

                while (!endOfGame)
                {
                    counter++;
                    try {
                        Thread.sleep(1000);

                    }catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        };
         CounterThread = new Thread(runnable);
        CounterThread.start();

    }
    public void onDraw(Canvas canvas)
    {

        drawHole(canvas);
        canvas.drawBitmap(monkey,x-r,y-r,p);
        DrawBananas(canvas);
        p.setColor(Color.BLACK);
        p.setStrokeWidth(width);
        DrawObstacle(0,300,500,300,canvas); //1
        DrawObstacle(200,500,1100,500,canvas);//2
        DrawObstacle(0,700,700,700,canvas);//3
        DrawObstacle(900,700,900,1000,canvas);//4 vertical
        DrawObstacle(200,1020,920,1020,canvas);//5
        DrawObstacle(220, 1040, 220, 1200, canvas);//6 vertical
        DrawObstacle(200,1220,1100,1220,canvas);//7
        DrawObstacle(0,1350,900,1350,canvas);//8
        DrawObstacle(0,1600,100,1600,canvas);//9
        DrawObstacle(180,1600,1100,1600,canvas);//10
        DrawObstacle(180,100,180,280,canvas);//11 vertical

        p.setTextSize(50);
        canvas.drawText("Time:"+counter,50,50,p);
        canvas.drawText("Collected bananas: "+collectedBananas,500,70,p);
        DrawInfo(canvas);

        i=0; //because we need start from drawing first obstacle in next invalidate
    }
    public void GetXY(float x1,float y1)
    {
        if(x1>0.5||x1<-0.5||y1>0.5||y1<-0.5) { //to stop ball
            x = x - x1;
            y = y + y1;
            Borders();
        }
    }
    public void Borders()
    {
        if(x>=screenWidth-r) //right border
        {
            x=screenWidth-r;
            if(y>=screenHeight-r) {y = screenHeight - r;} //corner right down
            if(y<=r) {y =r;} //corner right up
            invalidate();
        }
        else if(y>=screenHeight-r ) //down border
        {
            y=screenHeight-r;
            if(x<=r) {x = r;} //corner left down
            invalidate();
        }
        else if(x<=r) //left border
        {
            x=r;
            if(y<=r){y=r;} //corner left up
            invalidate();
        }
        else if(y<=r) //up border
        {
            y=r;
            invalidate();
        }
        else {
            invalidate(); //if don't touch any border
        }
    }
    public void DrawBananas(Canvas canvas)
    {
        CheckBananas();
        if(bitmapOneExist && bitmapTwoExist) {
            canvas.drawBitmap(bananaResizedOne, bananaOneX, bananaOneY, p);
            canvas.drawBitmap(bananaResizedTwo, bananaTwoX, bananaTwoY, p);
            }
        else if (bitmapOneExist)
        {
            canvas.drawBitmap(bananaResizedOne, bananaOneX, bananaOneY, p);
        }
        else if(bitmapTwoExist)
        {
            canvas.drawBitmap(bananaResizedTwo, bananaTwoX, bananaTwoY, p);
        }

        invalidate();
    }
    public void CheckBananas()
    {
        if(x<=bananaOneX+bananasWidth/2 && x>=bananaOneX-bananasWidth/2 && y>=bananaOneY-bananasHeight/2 && y<=bananaOneY+bananasHeight/2 )
        {
            bananaResizedOne = null;
            bitmapOneExist = false;
            if(pewsoundOne)
            {
                s1.playCollectSound();
                pewsoundOne=false;
                collectedBananas++;
            }
        }
        else if (x<=bananaTwoX+bananasWidth && x>=bananaTwoX-bananasWidth/2 && y>=bananaTwoY-bananasHeight/+2 && y<=bananaTwoY+bananasHeight/2)
        {
            bananaResizedTwo = null;
            bitmapTwoExist = false;
            if(pewsoundTwo)
            {
                s1.playCollectSound();
                pewsoundTwo=false;
                collectedBananas++;
            }
        }
    }
    public void DrawObstacle(int x1, int y1, int x2, int y2, Canvas canvas)
    {
            t[i] = x1;
            t[i + 1] = y1;
            t[i + 2] = x2;
            t[i + 3] = y2;
            //Log.d(TAG, "I: " + i );
            i+=4;

            canvas.drawLine(x1, y1, x2, y2, p);
    }
    public void DrawInfo( Canvas canvas)
    {
        if(showMessage) {
            p.setTextSize(60);
            p.setColor(Color.WHITE);
            canvas.drawText("First you need to collect bananas!", 100, 600, p);
        }
        showMessage = false;
    }
    public void CheckObstacles(final levelOne l1)
    {
        Log.d(TAG, "x1: " + t[0] +"x3:" +t[4]+"y3:" +t[5]+" x4"+t[6]);

        //Checking all obstacles + 4 because(x1, y1, x2, y2)
        if(CheckH(t, 0) || CheckH(t, 4) || CheckH(t,8) || CheckV(t,12) || CheckH(t,16) || CheckV(t,20) || CheckH(t,24) || CheckH(t,28) || CheckH(t,32) || CheckH(t,36) || CheckV(t,40))
        {
            s1.playHitSound();
            l1.StopSensors();
            endOfGame = true;
            new AlertDialog.Builder(l1)
                    .setTitle("GameOver!")
                    .setMessage("Do you want to play again?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which)
                        {
                            l1.finish();
                            l1.Start();
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            l1.finish();
                        }
                    })
                    .show();

        }
    }
    public void CheckHole(final levelOne l1)
    {
        t1.makeText(l1, "Najpierw zbierz wszystkie banany!",Toast.LENGTH_SHORT);
        if (x <= holeX + holeR && x > holeX - holeR && y >= holeY - holeR && y <= holeY + holeR) {
            if(collectedBananas == 2)
            {
                l1.StopSensors();
                s1.playWinSound();
                l1.addTime(String.valueOf(counter));
                endOfGame = true;
                //win = true;
                new AlertDialog.Builder(l1)
                        .setTitle("You win!")
                        .setMessage("If your tim is enaugh good, will be saved in laderboard :)")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                l1.finish();
                            }
                        })
                        .show();
            }
            else if(collectedBananas<2)
            {
                showMessage=true;
            }
        }
    }
    public boolean CheckH( int t[], int l)
    {
        if(x+r>=t[l] && x-r<=t[l+2] && y+r>=t[l+1]-width/2 && y-r<=t[l+3]+width/2)//x1 , y1, x2, y2
            return true;
        else
            return false;
    }
    public boolean CheckV( int t[], int l)
    {
        if(x+r>=t[l]-width/2 && x-r<=t[l+2]+width/2 && y+r>=t[l+1] && y-r<=t[l+3])//x1 , y1, x2, y2
            return true;
        else
            return false;
    }
    public void drawHole(Canvas canvas)
    {
        p.setColor(Color.BLACK);
        canvas.drawCircle(holeX,holeY,holeR,p);
        p.setColor(Color.GRAY);
        canvas.drawCircle(holeX,holeY,holeR-5,p);
    }
}
