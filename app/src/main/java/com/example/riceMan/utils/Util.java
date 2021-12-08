package com.example.riceMan.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.riceMan.pojo.News;
import com.example.riceMan.pojo.Rice;

public class Util {
    public static void insert_History(String title,String id,String time,int type,Context context){
        DatabaseHelper dbHelper = new DatabaseHelper(context, "riceKnow.db",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put("time",time);
            values.put("title",title);
            values.put("id",id);
            values.put("type",type);
            db.insert("History",null,values);
        }catch (Exception e){
            Log.e("insert_History",e.toString());
        }
    }
    public static void del_History(String id,Context context){
        DatabaseHelper dbHelper = new DatabaseHelper(context, "riceKnow.db",null,1);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        db.delete("History","id=?",new String[]{String.valueOf(id)});
    }

    public static void updateFlag_new(int flag,String time, Context context){
        DatabaseHelper dbHelper = new DatabaseHelper(context, "riceKnow.db",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("flag", flag);
        db.update("News",values,"time=?",new String[]{time});
    }

    public static void insert_Favs(String title, String id, String time, int type, Context context){
        DatabaseHelper dbHelper = new DatabaseHelper(context, "riceKnow.db",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put("time",time);
            values.put("title",title);
            values.put("id",id);
            values.put("type",type);
            Log.i("插入Favs表的信息",values.toString());
            db.insert("Favs",null,values);
        }catch (Exception e){

        }
    }
    public static void updateFlag_rice(int flag, Integer id, Context context){
        DatabaseHelper dbHelper = new DatabaseHelper(context, "riceKnow.db",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("flag", flag);
        db.update("Rices",values,"id=?",new String[]{String.valueOf(id)});
    }

    public static void del_Favs(String id,Context context){
        DatabaseHelper dbHelper = new DatabaseHelper(context, "riceKnow.db",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("Favs","id=?",new String[]{String.valueOf(id)});
    }


    public static Rice query_rice(String id,Context context)
    {
        Rice rice=new Rice();
        DatabaseHelper dbHelper = new DatabaseHelper(context, "riceKnow.db",null,1);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("Rices", null, "id=?", new String[]{id}, null, null, null, null);
        if (cursor.moveToFirst())
        {
            do {
                rice.setId(cursor.getInt(cursor.getColumnIndex("id")));
                rice.setFlag(cursor.getInt(cursor.getColumnIndex("flag")));
                rice.setName(cursor.getString(cursor.getColumnIndex("name")));
                rice.setPrice(cursor.getString(cursor.getColumnIndex("price")));
                rice.setImgurl(cursor.getString(cursor.getColumnIndex("imgurl")));
            } while (cursor.moveToNext());

        }
        return rice;
    }
    public static boolean queryRiceIfExists(String name,Context context)
    {
        Rice rice=new Rice();
        DatabaseHelper dbHelper = new DatabaseHelper(context, "riceKnow.db",null,1);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("Rices", null, "name=?", new String[]{name}, null, null, null, null);
        if (cursor.moveToFirst())
        {
            do {
                rice.setId(cursor.getInt(cursor.getColumnIndex("id")));
                rice.setFlag(cursor.getInt(cursor.getColumnIndex("flag")));
                rice.setName(cursor.getString(cursor.getColumnIndex("name")));
                rice.setPrice(cursor.getString(cursor.getColumnIndex("price")));
                rice.setImgurl(cursor.getString(cursor.getColumnIndex("imgurl")));
            } while (cursor.moveToNext());

        }
        if (rice.getName()==null)
            return true;
        return false;
    }

    public static News query_new(String time, Context context)
    {
        News newBean=new News();
        DatabaseHelper dbHelper = new DatabaseHelper(context, "riceKnow.db",null,1);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("News", null, "time=?", new String[]{time}, null, null, null, null);
        if (cursor.moveToFirst())
        {
            do {

                newBean.setFlag(cursor.getInt(cursor.getColumnIndex("flag")));
                newBean.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                newBean.setType(cursor.getString(cursor.getColumnIndex("type")));
                newBean.setTime(cursor.getString(cursor.getColumnIndex("time"))); ;
                newBean.setContent(cursor.getString(cursor.getColumnIndex("content")));
                newBean.setUrl(cursor.getString(cursor.getColumnIndex("url")));
                newBean.setImgurl(cursor.getString(cursor.getColumnIndex("imgurl")));
            } while (cursor.moveToNext());

        }
        return newBean;
    }

    public static boolean insert_feedback(String content, Context context){
        DatabaseHelper dbHelper = new DatabaseHelper(context, "riceKnow.db",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put("content",content);
            Log.i("插入Feedback表的信息",values.toString());
            db.insert("Feedback",null,values);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
