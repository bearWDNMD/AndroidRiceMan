package com.example.riceMan.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Rices(id Integer PRIMARY KEY AUTOINCREMENT,name varchar(20),price varchar(10),imgurl text,flag Integer)";
        String sql1= "CREATE TABLE News(title varchar(50)PRIMARY KEY,type varchar(20),time varchar(20),imgurl text,content text,url text,flag Integer)";
        db.execSQL(sql1);
        String sql2="CREATE TABLE Favs(title varchar(50),id varchar(20)PRIMARY KEY,time varchar(20),type Integer)";
        db.execSQL(sql2);
        String sql3="CREATE TABLE History(title varchar(50),id varchar(20)PRIMARY KEY, time varchar(20),type Integer)";
        db.execSQL(sql3);
        db.execSQL(sql);
        String sql4="CREATE TABLE Feedback(id Integer PRIMARY KEY AUTOINCREMENT,content text)";
        db.execSQL(sql4);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}
