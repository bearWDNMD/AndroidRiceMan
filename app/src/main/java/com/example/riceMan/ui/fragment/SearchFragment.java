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
import android.widget.SearchView;
import android.widget.Toast;

import com.example.riceMan.ui.activity.ArticleDetailActivity;
import com.example.riceMan.R;
import com.example.riceMan.adapter.itemAdapter;
import com.example.riceMan.entity.Rice;
import com.example.riceMan.db.DatabaseHelper;
import com.example.riceMan.utils.DatabaseUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class SearchFragment extends Fragment {

    private SearchView mSearchView;
    private ListView listView;
    private ArrayList<Rice> riceBeans=new ArrayList<>();
    private String mContentText;
    private itemAdapter iad;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_search, container, false);
        mSearchView = (SearchView) root.findViewById(R.id.searchView);
        listView=(ListView)root.findViewById(R.id.lv_load_search);
        mSearchView.setSubmitButtonEnabled(true);


        // 设置搜索文本监听
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getContext(), query, Toast.LENGTH_SHORT).show();
                int size = riceBeans.size();
                if(size>0){
                    riceBeans.clear();
                    iad.removeAllItem(size);
                }
                initView(query);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent=new Intent(getContext(), ArticleDetailActivity.class);
                        Rice rice=itemAdapter.list.get(i);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
                        Date date = new Date(System.currentTimeMillis());
                        DatabaseUtil.insert_History(rice.getName(),String.valueOf(rice.getId()),simpleDateFormat.format(date),0,getContext());

                        try {
                            DatabaseHelper dbHelper = new DatabaseHelper(getContext(), "riceKnow.db",null,1);
                            SQLiteDatabase db = dbHelper.getReadableDatabase();
                            Cursor cursor = db.query("Rices", null, "id=?", new String[]{String.valueOf(rice.getId())}, null, null, null, null);
                            while (cursor.moveToNext())
                            {
                                int x=cursor.getInt(cursor.getColumnIndex("flag"));
                                rice.setFlag(x);
                            }
                        }catch (Exception e){
                            Log.e("<<<<<<<","error");
                        }
                        intent.putExtra("rice",rice);
                        startActivity(intent);
                    }
                });
                return true;
            }

            // 当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });

        return root;
    }
    private void initView( String wd){

        try {
            DatabaseHelper dbHelper = new DatabaseHelper(getActivity(), "riceKnow.db",null,1);
            SQLiteDatabase db = dbHelper.getReadableDatabase();
                Cursor cursor = db.query("Rices", null, "name like '%"+wd+"%' ", null, null, null, null, null);
            Log.e("----------",String.valueOf(cursor.getCount()));
            if (cursor.moveToFirst())
            {
                do {
                    Rice rice=new Rice();
                    rice.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    rice.setFlag(cursor.getInt(cursor.getColumnIndex("flag")));
                    rice.setName(cursor.getString(cursor.getColumnIndex("name")));
                    rice.setPrice(cursor.getString(cursor.getColumnIndex("price")));
                    rice.setImgurl(cursor.getString(cursor.getColumnIndex("imgurl")));
                    riceBeans.add(rice);
                } while (cursor.moveToNext());

            }

        }catch (Exception e){
            Log.d("main>>>","error...");
        }

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                iad=new itemAdapter(riceBeans,getContext());
                listView.setAdapter(iad);
            }
        });

    }
}