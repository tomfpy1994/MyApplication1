package com.example.bunnyfung.mobileassignment;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.*;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.app.Activity;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Created by Iltsk on 30/6/2015.
 */
public class Graph extends Activity {

    SQLiteDatabase db;
    Cursor cursor=null;

    int rColor[]={0xffff0000, 0xffff7d00, 0xffffff00, 0xff00ff00,
            0xff00ffff, 0xff0000ff, 0xffff00ff, 0xff000000};
    float avgTime[]={95,45,70,140,215,25,240,149};
    String items[]={"CN1008","CN2186","HP1022","HP2055","HP1008","CN1099","HP2002","HP3377"};

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //inputData();
        setContentView(new Panel(this));
    }

    public void inputData(){
        try {
            db = this.openOrCreateDatabase("/data/data/com.example.bunnyfung.mobileassignment/eBidDB", MODE_PRIVATE, null);
            cursor = db.rawQuery("select jobStartTime, jobEndTime, prodNo " +
                    "from ServiceJob, Purchase " +
                    "where Purchase.serialNo=ServiceJob.serialNo", null);
            String temp;
            int arrayLength = 0;
            while (cursor.moveToNext()) {
                temp = cursor.getString(cursor.getColumnIndex("jobStartTime"));
                temp = cursor.getString(cursor.getColumnIndex("jobEndTime"));
                temp = cursor.getString(cursor.getColumnIndex("prodNo"));
                arrayLength++;
            }
            avgTime = new float[arrayLength];
            items = new String[arrayLength];

            cursor = db.rawQuery("select jobStartTime, jobEndTime, prodNo " +
                    "from ServiceJob, Purchase " +
                    "where Purchase.serialNo=ServiceJob" +
                    ".serialNo", null);
            String startTimeOfString, endTimeOfString;
            float startTimeOfInt, endTimeOfInt;
            int i = 0;
            while (cursor.moveToNext()) {
                startTimeOfString = cursor.getString(cursor.getColumnIndex("jobStartTime"));
                endTimeOfString = cursor.getString(cursor.getColumnIndex("jobEndTime"));
                startTimeOfInt = StringToTime(startTimeOfString);
                endTimeOfInt = StringToTime(endTimeOfString);
                if (endTimeOfInt < startTimeOfInt)
                    avgTime[i] = endTimeOfInt + 1440 - startTimeOfInt;
                else
                    avgTime[i] = endTimeOfInt - startTimeOfInt;
                items[i] = cursor.getString(cursor.getColumnIndex("prodNo"));
                i++;
            }
            db.close();
        }catch(Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            finish();
        }
    }

    public float StringToTime(String timeOfString){
        float timeOfInt=0;
        if(timeOfString.length()==0)
            timeOfInt=0;
        else
            timeOfInt=60*((10*(timeOfString.charAt(0)-48))+(timeOfString.charAt(1)-48))
                    +(10*(timeOfString.charAt(3)-48))+(timeOfString.charAt(4)-48);
        return timeOfInt;
    }

    class Panel extends View {

        public Panel(Context context) { super(context); }

        public void onDraw(Canvas c) {
            WindowManager windowManager = getWindowManager();
            Display display = windowManager.getDefaultDisplay();
            int screenWidth = display.getWidth();
            int screenHeight = display.getHeight();

            super.onDraw(c);
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL);

            paint.setColor(Color.WHITE);
            c.drawPaint(paint);

            float temp=0;
            for(int i=0;i<avgTime.length;i++){
                if(avgTime[i]>temp)
                    temp=avgTime[i];
            }

            final int loopTime=avgTime.length;
            final float largestHeight=temp;
            final float maxHeight=(largestHeight*6)/5;
            float shortenScope=1;
            while(screenHeight<maxHeight/shortenScope)
                shortenScope+=0.01;

            //For the whole graphic
            float graphicHeight=screenHeight*2/3;

            float left=(screenWidth/(loopTime+1))/2, top
                    , right=(left+(screenWidth/loopTime+1))/2
                    , bottom=graphicHeight-((graphicHeight-(largestHeight/shortenScope))/6);

            //The measuring number
            top=(bottom-(graphicHeight*((largestHeight/maxHeight)/shortenScope)))+25;
            for(int i=1;i<=4;i++) {
                paint.setColor(Color.BLACK);
                paint.setStyle(Paint.Style.FILL);
                paint.setTextSize(screenWidth/(4*loopTime));
                c.drawText(String.valueOf(largestHeight-(largestHeight*i/4)), 0, (bottom/4*i)+(top/i), paint);
            }
            c.drawText(String.valueOf(largestHeight), 0, top, paint);

            //The x and y axis
            paint.setAntiAlias(true);
            paint.setColor(Color.LTGRAY);
            paint.setStrokeWidth(3);
            c.drawLine((left+right)/2, top, (left+right)/2, bottom, paint);
            c.drawLine((left+right)/2, bottom, screenWidth, bottom, paint);

            left+=(screenWidth/(loopTime+1))/2;
            right+=(screenWidth/(loopTime+1))/2;

            for (int i = 0; i < loopTime; i++) {
                paint.setAntiAlias(true);
                paint.setStyle(Paint.Style.FILL);
                paint.setColor(rColor[i%rColor.length]);

                top=(bottom-(graphicHeight*((avgTime[i]/maxHeight)/shortenScope)))+25;
                c.drawRect(left, top, right, bottom, paint);
                left+=screenWidth/(loopTime+1);
                right+=screenWidth/(loopTime+1);
            }

            //For the text below
            bottom+=(screenWidth/10);
            for(int i=0;i<loopTime;i++){
                if(i%2==0){
                    paint.setColor(rColor[i]);
                    c.drawRect(screenWidth/10,bottom+(i/2*(screenWidth/10)),screenWidth*2/10,bottom+(screenWidth/10)+(i/2*(screenWidth/10)),paint);
                    paint.setTextSize(screenWidth/21);
                    paint.setColor(Color.BLACK);
                    c.drawText(items[i],(screenWidth*5/20),bottom+(screenWidth*5/80)+(i/2*(screenWidth/10)),paint);
                }else{
                    paint.setColor(rColor[i]);
                    c.drawRect(screenWidth*5/10,bottom+(i/2*(screenWidth/10)),screenWidth*6/10,bottom+(screenWidth/10)+(i/2*(screenWidth/10)),paint);
                    paint.setTextSize(screenWidth/21);
                    paint.setColor(Color.BLACK);
                    c.drawText(items[i],(screenWidth*13/20),bottom+(screenWidth*5/80)+(i/2*(screenWidth/10)),paint);
                }
            }
        }
    }
}
