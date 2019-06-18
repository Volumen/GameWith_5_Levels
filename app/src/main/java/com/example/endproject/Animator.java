package com.example.endproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import static android.content.ContentValues.TAG;

public class Animator extends View {
    Toast t1;
    int t[] ;
    Sound s1;
    float screenHeight = getResources().getDisplayMetrics().heightPixels;
    float screenWidth = getResources().getDisplayMetrics().widthPixels;
    Paint p;
    int r=20;
    Bitmap banana ,bananaResizedOne,bananaResizedTwo;
    int width=40;
    float x=screenWidth/2, y=screenHeight-200;
    Boolean bitmapOneExist = true, bitmapTwoExist = true, pewsoundOne = true, pewsoundTwo = true;
    int i=0;
    int holeX=900, holeY=1800, holeR=40;
    int collectedBananas = 0;
    boolean showMessage = false;
    int bananaOneX = 100, bananaOneY = 200, bananaTwoX = 260, bananaTwoY = 1120, bananasWidth = 50, bananasHeight = 50;

    public Animator(Context context) {
        super(context);

        banana = BitmapFactory.decodeResource(getResources(), R.drawable.banana);
        bananaResizedOne = Bitmap.createScaledBitmap(banana, bananasWidth, bananasHeight, true);
        bananaResizedTwo = Bitmap.createScaledBitmap(banana, bananasWidth, bananasHeight, true);

        p = new Paint();
        s1 = new Sound(context.getApplicationContext());
        t= new int[50];


    }
    public void onDraw(Canvas canvas)
    {
        drawHole(canvas);
        p.setColor(Color.BLACK);
        canvas.drawCircle(x,y,r,p);
        p.setColor(Color.parseColor("#34495E"));
        canvas.drawCircle(x,y,r-3,p);
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
        canvas.drawText("Collected bananas: "+collectedBananas,500,70,p);
        DrawInfo(canvas);

        i=0;
    }
    public void GetXY(float x1,float y1)
    {
        if(x1>0.5||x1<-0.5||y1>0.5||y1<-0.5) {
            x = x - x1;
            y = y + y1;
            Borders();
            Log.d(TAG, "x: " + x + "y: " + y);
            Log.d(TAG, "width: " + width );
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
        if(bitmapOneExist == true && bitmapTwoExist == true) {
            canvas.drawBitmap(bananaResizedOne, bananaOneX, bananaOneY, p);
            canvas.drawBitmap(bananaResizedTwo, bananaTwoX, bananaTwoY, p);
            }
        else if (bitmapOneExist == true)
        {
            canvas.drawBitmap(bananaResizedOne, bananaOneX, bananaOneY, p);
        }
        else if(bitmapTwoExist == true)
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
            if(pewsoundOne==true)
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
            if(pewsoundTwo==true)
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
        if(showMessage==true) {
            p.setTextSize(60);
            p.setColor(Color.WHITE);
            canvas.drawText("First you need to collect bananas!", 100, 600, p);

//        new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//
//                        }
//                    },   5000);
//        }
        }
        showMessage = false;
    }
    public void CheckObstacles(final levelOne l1)
    {
        Log.d(TAG, "x1: " + t[0] +"x3:" +t[4]+"y3:" +t[5]+" x4"+t[6]);

        //Checking all obstacles + 4 because(x1, y1, x2, y2)
        if(CheckH(t,0)==true || CheckH(t,4)==true || CheckH(t,8)==true || CheckV(t,12)==true || CheckH(t,16)==true || CheckV(t,20)==true || CheckH(t,24)==true || CheckH(t,28)==true || CheckH(t,32)==true || CheckH(t,36)==true || CheckV(t,40)==true)
        {
            s1.playHitSound();
            l1.StopSensors();
            new AlertDialog.Builder(l1)
                    .setTitle("Przegrałeś!")
                    .setMessage("Chcesz zagrać jeszcze raz?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which)
                        {
                            reset();
                            l1.StartSensors();
                        }
                    })
                    .show();
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
    public void CheckHole(final levelOne l1)
    {
        t1.makeText(l1, "Najpierw zbierz wszystkie banany!",Toast.LENGTH_SHORT);
            if (x <= holeX + holeR && x > holeX - holeR && y >= holeY - holeR && y <= holeY + holeR) {
                if(collectedBananas == 2)
                {
                l1.StopSensors();
                new AlertDialog.Builder(l1)
                        .setTitle("Wygrałeś!")
                        .setMessage("Chcesz zagrać jeszcze raz?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                reset();
                                l1.StartSensors();
                            }
                        })
                        .show();
                }
                else if(collectedBananas<2)
                {

                    showMessage=true;
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//
//                        }
//                    },   1000);

                }
            }
    }
    public void reset()
    {
        collectedBananas=0;
        x=screenWidth/2;
        y=screenHeight-150;
        invalidate();
        bitmapOneExist = true;
        bitmapTwoExist = true;
        pewsoundOne = true;
        pewsoundTwo = true;
    }
    private void toastMessage(String message)
    {
       // Toast.makeText(l1,message,Toast.LENGTH_SHORT).show();
    }
}
