package com.example.endproject.levelTwo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelperTwo extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "LEDERBOARDTWO";
    //private static final String TABLE_NAMETWO = "LEDERBOARDTWO";
    //public static final String COL1 = "Ni";
    public static final String COL1 = "NICKNAME";
    //public static final String COL2 = "TIME";
    public static final String COL2 = "PKT";
    public DatabaseHelperTwo(Context context)
    {

        super(context,TABLE_NAME,null,1);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_String = "CREATE TABLE " + TABLE_NAME + "(" + COL1 + " TEXT," + COL2 + " TEXT)";
       // String SQL_String2 = "CREATE TABLE " + TABLE_NAMETWO + "(" + COL1 + " TEXT,"+ COL2B +"TEXT)";
        db.execSQL(SQL_String);
       // db.execSQL(SQL_String2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAMETWO);
        onCreate(db);
    }
//    public boolean addData( String nickname, String pkt)
//    {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(COL1, nickname);
//        contentValues.put(COL2, pkt);
//
//        long result = db.insert(TABLE_NAME, null, contentValues);
//        //if date as inserted incorrectly it will return -1
//        if (result == -1) {
//            return false;
//        } else {
//            return true;
//        }

    public boolean addData( String nickname, String pkt)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, nickname);
        contentValues.put(COL2, pkt);

        long result = db.insert(TABLE_NAME, null, contentValues);
        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM LEDERBOARDTWO";
        Cursor data = db.rawQuery(query, null);
        return data;
    }
}

