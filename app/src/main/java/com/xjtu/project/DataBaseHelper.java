package com.xjtu.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "BloodGlucose.db";
    public static final String TABLE_NAME = "BloodGlucose_Table";
    public static final String COL_1= "Name";
    public static final String COL_2= "BloodGlucose";
    public static final String COL_3= "Date";


    public DataBaseHelper(@Nullable Context context ) {
        super(context, DATABASE_NAME, null ,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME+" (NAME TEXT,BLOODGLUCOSE FLOAT,DATE TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    //插入给定的数据到当前使用的数据库中
    public Boolean insertData(String name, float bloodGlucose, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1 ,name);
        contentValues.put(COL_2,bloodGlucose);
        contentValues.put(COL_3,date);

        long result = db.insert(TABLE_NAME,null,contentValues);
        if (result != -1 )
            //如果插入数据成功则返回真
            return true;
        else
            return false;
    }
    //从数据库中获取所有信息
    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

}
