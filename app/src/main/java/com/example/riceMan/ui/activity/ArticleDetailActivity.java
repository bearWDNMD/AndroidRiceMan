package com.example.riceMan.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.riceMan.R;
import com.example.riceMan.entity.Rice;
import com.example.riceMan.utils.DatabaseUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ArticleDetailActivity extends AppCompatActivity {

    private Rice rice;
    private ImageView imageView;
    private TextView name;
    private TextView price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        initView();
        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home)
        {
            finish();
            return true;
        }
        if (item.getItemId()==R.id.share){
            String imgurl=rice.getImgurl();
            Intent share_intent = new Intent();
            share_intent.setAction(Intent.ACTION_SEND);//设置分享行为
            share_intent.setType("text/plain");//设置分享内容的类型
            share_intent.putExtra(Intent.EXTRA_SUBJECT, "share");//添加分享内容标题
            share_intent.putExtra(Intent.EXTRA_TEXT, imgurl);//添加分享内容
            //创建分享的Dialog
            share_intent = Intent.createChooser(share_intent, "share");
            startActivity(share_intent);
        }
        if(item.getItemId()==R.id.fav)
        {
            if (rice.getFlag()==0)
            {
                Toast.makeText(this,"收藏成功",Toast.LENGTH_SHORT).show();
                item.setTitle("取消收藏");
                item.setIcon(getResources().getDrawable(R.drawable.button2));
                rice.setFlag(1);
                DatabaseUtil.updateFlag_rice(rice.getFlag(),rice.getId(),ArticleDetailActivity.this);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
                Date date = new Date(System.currentTimeMillis());
                DatabaseUtil.insert_Favs(rice.getName(),String.valueOf(rice.getId()),simpleDateFormat.format(date),0,ArticleDetailActivity.this);
            }
            else {
                Toast.makeText(this,"取消收藏",Toast.LENGTH_SHORT).show();
                item.setTitle("收藏");
                item.setIcon(getResources().getDrawable(R.drawable.button1));
                rice.setFlag(0);
                //Log.e(">>>>>>>>>>>>>",wine.getId());
                DatabaseUtil.updateFlag_rice(rice.getFlag(),rice.getId(),ArticleDetailActivity.this);

                DatabaseUtil.del_Favs(String.valueOf(rice.getId()),ArticleDetailActivity.this);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //右侧菜单
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_favorite, menu);
        MenuItem menuItem=menu.findItem(R.id.fav);
        MenuItem menuItem1=menu.findItem(R.id.share);
        menuItem1.setIcon(getResources().getDrawable(R.drawable.ic_share_foreground));
        menuItem1.setTitle("分享");
        if(rice.getFlag()==0)
        {
            menuItem.setIcon(getResources().getDrawable(R.drawable.button1));
            menuItem.setTitle("收藏");
        } else {
            menuItem.setIcon(getResources().getDrawable(R.drawable.button2));
            menuItem.setTitle("取消收藏");
        }
        return true;
    }

    private void initView(){
        rice=(Rice) getIntent().getSerializableExtra("rice");
        Log.i("detail",rice.toString());
        imageView=(ImageView)findViewById(R.id.detail_image);
        name=(TextView)findViewById(R.id.detail_name);
        price=(TextView)findViewById(R.id.detail_price);
        Glide.with(this).load(rice.getImgurl()).into(imageView);
        name.setText(rice.getName());
        price.setText(rice.getPrice());
    }
}