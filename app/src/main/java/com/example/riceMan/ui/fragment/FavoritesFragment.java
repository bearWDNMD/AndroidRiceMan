package com.example.riceMan.ui.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.riceMan.ui.activity.ArticleDetailActivity;
import com.example.riceMan.ui.activity.NewsDetailActivity;
import com.example.riceMan.R;
import com.example.riceMan.adapter.HistoryAdapter;
import com.example.riceMan.entity.Favorite_item;
import com.example.riceMan.entity.News;
import com.example.riceMan.entity.Rice;
import com.example.riceMan.db.DatabaseHelper;
import com.example.riceMan.utils.DatabaseUtil;

import java.util.ArrayList;


public class FavoritesFragment extends Fragment {

    private ListView listView;
    private ArrayList<Favorite_item> favoriteItems=new ArrayList<Favorite_item>();
    private String mContentText;
    private Favorite_item fav;
    private int flag=0;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_favorites, container, false);
        listView=(ListView)root.findViewById(R.id.lv_load_fav);
        initView();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                fav= HistoryAdapter.list.get(i);
                if(fav.getType()==0)
                {
                    Intent intent=new Intent(getContext(), ArticleDetailActivity.class);
                    Rice rice= DatabaseUtil.query_rice(fav.getId(),getContext());
                    intent.putExtra("rice",rice);
                    startActivity(intent);
                }
                else {
                    Intent intent=new Intent(getContext(), NewsDetailActivity.class);
                    News newBean= DatabaseUtil.query_new(fav.getId(),getContext());
                    intent.putExtra("newBean",newBean);
                    Log.e("newsBean",newBean.toString());
                    startActivity(intent);
                }
                Log.e("*************",fav.toString());

            }
        });
        return root;
    }
    private void initView(){
        if (flag==1)
        {

        }
        else {
            flag=1;
            try {

                DatabaseHelper dbHelper = new DatabaseHelper(getActivity(), "riceKnow.db",null,1);
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                Cursor cursor;
                cursor = db.query("Favs", null, null, null, null, null, null, null);
                if (cursor.moveToFirst())
                {
                    do {
                        Favorite_item Fav=new Favorite_item();
                        Fav.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                        Fav.setType(cursor.getInt(cursor.getColumnIndex("type"))) ;
                        Fav.setId(cursor.getString(cursor.getColumnIndex("id")));
                        Fav.setTime(cursor.getString(cursor.getColumnIndex("time")));
                        favoriteItems.add(Fav);
                    } while (cursor.moveToNext());

                }

            }catch (Exception e){
                Log.d("main","error...");
            }

        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listView.setAdapter(new HistoryAdapter(favoriteItems,getContext()));
            }
        });

    }
}