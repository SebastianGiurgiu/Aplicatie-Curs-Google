package com.example.prototip.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "Aliment.db";
    public static final String TABLE_NAME = "aliment_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "PROTEINE";
    public static final String COL_4 = "CARBOHIDRATI";
    public static final String COL_5 = "GRASIMI";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME,null,3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("create table " + TABLE_NAME + " ( ID INTEGER PRIMARY KEY AUTOINCREMENT , NAME TEXT , PROTEINE INTEGER , CARBOHIDRATI INTEGER , GRASIMI INTEGER )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name , Integer proteine , Integer carhohidrati , Integer grasimi){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,proteine);
        contentValues.put(COL_4,carhohidrati);
        contentValues.put(COL_5,grasimi);
        long result = db.insert(TABLE_NAME,null,contentValues);
        if (result == -1 )
            return  false;
        return  true;
    }

    public Integer deleteData(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,"NAME=?",new String[] {name});
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+ TABLE_NAME,null);
        return res;
    }
}
