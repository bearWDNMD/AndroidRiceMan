package com.example.riceMan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.riceMan.R;
import com.example.riceMan.pojo.News;

import java.util.ArrayList;

public class ConsultAdapter extends BaseAdapter {
    public static ArrayList<News> list;
    private Context context;
    public ConsultAdapter(ArrayList<News> list, Context context)
    {
        this.context=context;
        this.list=list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null)
        {
            viewHolder= new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.consult,null,false);
            viewHolder.imageView= (ImageView) convertView.findViewById(R.id.iv_article_list_image);
            viewHolder.name= (TextView) convertView.findViewById(R.id.tv_article_list_name);
            viewHolder.price= (TextView) convertView.findViewById(R.id.tv_article_list_price);
            viewHolder.type= (TextView) convertView.findViewById(R.id.tv_article_list_type);
            convertView.setTag(viewHolder);
        }
        try {
            viewHolder= (ViewHolder)convertView.getTag();
            viewHolder.name.setText(list.get(position).getTitle());
            Glide.with(context).load(list.get(position).getImgurl()).into(viewHolder.imageView);
            viewHolder.type.setText(list.get(position).getTime());
            viewHolder.price.setText(list.get(position).getType());
        }
        catch (Exception e){

        }
        return convertView;
    }
    class ViewHolder
    {
        private ImageView imageView;
        private TextView name;
        private TextView price;
        private TextView type;
    }
}
