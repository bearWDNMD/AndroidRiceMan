package com.example.riceMan.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.riceMan.pojo.News;
import com.example.riceMan.pojo.Rice;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class DataRequestUtil {
    public static void getRiceData(Context context) {

        ArrayList<Rice> riceArrayList =new ArrayList<>();
        try {
            String url="https://search.jd.com/Search?keyword=%E5%A4%A7%E7%B1%B3";
            Document document=Jsoup.connect(url).get();
            Elements goods=document.select("#J_goodsList");
            Elements rices=goods.select(".gl-item");
            for (int i = 1; i < rices.size(); i++) {
                Rice rice =new Rice();
                String img =rices.get(i).getElementsByTag("img").eq(0).attr("data-lazy-img");

                String imgurl="https:"+img;

                String name = rices.get(i).select(".p-name").select("em").text();

                String price =  "￥"+ rices.get(i).select(".p-price").select("i").text() ;

                rice.setImgurl(imgurl);
                rice.setName(name);
                rice.setPrice(price);
                riceArrayList.add(rice);
            }
            DatabaseHelper dbHelper = new DatabaseHelper(context, "riceKnow.db",null,1);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            for(Rice rice : riceArrayList){
                ContentValues values = new ContentValues();
                values.put("name", rice.getName());
                values.put("imgurl", rice.getImgurl());
                values.put("flag", rice.getFlag());
                values.put("price", rice.getPrice());
                if (Util.queryRiceIfExists(rice.getName(),context))
                    db.insert("rices",null,values);
            }
        } catch (Exception e) {
            Log.e("riceData",e.toString());
        }
    }


    public static void getNewsData(Context context) {
        ArrayList<News> newsArrayList = new ArrayList<>();
        try {
            String home_url = "http://damicyd.99114.com/Article1/l_101116_1.html";
            Document document = Jsoup.connect(home_url).get();
            Elements ul = document.select("div.t1000-art-list-left-con");
            Elements news = ul.select("dl");
            //Log.i("news",news+"");
            //Log.i("news",news.size()+"");

            for (int i = 0; i < news.size(); i++) {
                News newsItem = new News();
                String title = news.get(i).select("a").text();
                String content = news.get(i).select("dd").text();
                String time = news.get(i).select("span").text();
                /*news.get(i).select("div.entry-excerpt").text()*/
                String url = "http://damicyd.99114.com" + news.get(i).select("a").attr("href");
                /*news.get(i).select("i.date").text()*/
                String type = "新闻";
                newsItem.setUrl(url);
                newsItem.setTitle(title);
                newsItem.setContent(content);
                newsItem.setTime(time);
                newsItem.setType(type);
                //Log.i("url",url);
                Document document1 = Jsoup.connect(url).get();
                Elements imgdiv = document1.select("div[class=artDatilTxt clearfix]");
                Elements imgs = imgdiv.select("img");
                String imgurl = imgs.attr("src");
                //imgurl="https:"+imgurl;
                newsItem.setImgurl(imgurl);
                //Log.e("imgurl",imgurl);
                //Log.e("fenge","ffffff");
                DatabaseHelper databaseHelper = new DatabaseHelper(context, "riceKnow.db", null, 1);
                SQLiteDatabase db = databaseHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("title", title);
                values.put("content", content);
                values.put("time", time);
                values.put("flag", 0);
                values.put("url", url);
                values.put("type", type);
                values.put("imgurl", imgurl);
                News news1 = Util.query_new(time, context);
                //Log.i("查询新闻前查找新闻",news1.toString());
                if (news1.getUrl() == null)
                    db.insert("News", null, values);
            }
        } catch (Exception e) {
            Log.e("getNewsData", e.toString());
        }
    }

    public static void getConsultData(Context context) {
        ArrayList<News> newsArrayList = new ArrayList<>();
        try {
            String home_url = "https://www.maigoo.com/goomai/list_1248.html";
            Document document = Jsoup.connect(home_url).get();
            Elements ul = document.select("div[class=licont]");
            Elements ul2 = ul.select("ul[class=itembox]");
            Elements news = ul2.select("li[class=item]");
            for (int i = 0; i < news.size(); i++) {
                News newsItem = new News();
                String title = news.get(i).select("div.contbox").select("a").text();
                String content = news.get(i).select("div[class=description c999 line18em font14]").text();
                String url = news.get(i).select("div.contbox").select("a").attr("href");
                String time = news.get(i).select("div[class=attention ccc]").text();
                String type = "咨询";
                newsItem.setUrl(url);
                newsItem.setTitle(title);
                newsItem.setContent(content);
                newsItem.setTime(time);
                newsItem.setType(type);
                Document document1 = Jsoup.connect(url).get();

                Elements imgdiv = document1.select("div[class=leftlay]");
                //Elements imgs=imgdiv.select("img");
                String imgurl = imgdiv.select("img").attr("src");
                //imgurl="https:"+imgurl;

                newsItem.setImgurl(imgurl);

                DatabaseHelper databaseHelper = new DatabaseHelper(context, "riceKnow.db", null, 1);
                SQLiteDatabase db = databaseHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("title", title);
                values.put("content", content);
                values.put("time", time);
                values.put("flag", 0);
                values.put("url", url);
                values.put("type", type);
                values.put("imgurl", imgurl);
                News news1 = Util.query_new(time, context);
                //Log.i("查询新闻前查找新闻",news1.toString());
                if (news1.getUrl() == null)
                    db.insert("News", null, values);
            }
        } catch (Exception e) {
            Log.e("getNewsData", e.toString());
        }

    }

    public static void getManageData(Context context) {
        ArrayList<News> newsArrayList = new ArrayList<>();
        try {
            String home_url = "https://www.maigoo.com/goomai/list_8360.html";
            Document document = Jsoup.connect(home_url).get();
            Elements ul = document.select("div[class=licont]");
            Elements ul2 = ul.select("ul[class=itembox]");
            Elements news = ul2.select("li[class=item]");
            for (int i = 0; i < news.size(); i++) {
                if(i==10){
                    continue;
                }
                News newsItem = new News();
                String title = news.get(i).select("div.contbox").select("a").text();
                String content = news.get(i).select("div[class=description c999 line18em font14]").text();
                String url = news.get(i).select("div.contbox").select("a").attr("href");
                String time = news.get(i).select("div[class=attention ccc]").text();
                String type = "知道";
                newsItem.setUrl(url);
                newsItem.setTitle(title);
                newsItem.setContent(content);
                newsItem.setTime(time);
                newsItem.setType(type);
                //Log.i("url",url);
                //Document document1=Jsoup.connect(url).get();
                //Elements imgdiv=document1.select("div[class=leftlay]");
                //Elements imgs=imgdiv.select("img");
                //String imgurl=imgs.select("img").attr("src");
                //imgurl="https:"+imgurl;
                //newsItem.setImgurl(imgurl);
                //Log.e("imgurl",imgurl);
                //Log.e("fenge","ffffff");
                DatabaseHelper databaseHelper = new DatabaseHelper(context, "riceKnow.db", null, 1);
                SQLiteDatabase db = databaseHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("title", title);
                values.put("content", content);
                values.put("time", time);
                values.put("flag", 0);
                values.put("url", url);
                values.put("type", type);
                values.put("imgurl","https://image.maigoo.com/upload/images/20210708/17053152888_500x312.jpg_220_135.jpg");
                //values.put("imgurl",imgurl);
                News news1 = Util.query_new(time, context);
                if (news1.getUrl() == null)
                    db.insert("News", null, values);
            }
        } catch (Exception e) {
            Log.e("getNewsData", e.toString());
        }
    }
}
