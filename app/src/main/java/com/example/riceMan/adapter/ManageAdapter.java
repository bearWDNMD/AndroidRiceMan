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
import com.example.riceMan.pojo.Rice;

import java.util.ArrayList;

public class ManageAdapter extends BaseAdapter {
    public static ArrayList<Rice> list;
    private Context context;
    public ManageAdapter(ArrayList<Rice> list, Context context)
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
            viewHolder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.shopping,null,false);
            viewHolder.imageView= (ImageView) convertView.findViewById(R.id.iv_article_list_image);
            viewHolder.name= (TextView) convertView.findViewById(R.id.tv_article_list_name);
            viewHolder.price= (TextView) convertView.findViewById(R.id.tv_article_list_price);
            convertView.setTag(viewHolder);
        }
        try {
            viewHolder= (ViewHolder)convertView.getTag();
            viewHolder.name.setText(list.get(position).getName());
            Glide.with(context).load(list.get(position).getImgurl()).into(viewHolder.imageView);
            viewHolder.price.setText(list.get(position).getPrice());
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
//        private TextView type;
    }
}
