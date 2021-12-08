package com.example.riceMan;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.riceMan.adapter.ConsultAdapter;
import com.example.riceMan.pojo.News;
import com.example.riceMan.utils.DatabaseHelper;
import com.example.riceMan.utils.Util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConsultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConsultFragment extends Fragment {


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_SHOW_TEXT = "text";
    private ListView listView;
    private ArrayList<News> newBeans=new ArrayList<>();
    private String mContentText;
    private int flag=0;


    public ConsultFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment BlankFragment.
     */
    public static ConsultFragment newInstance(String param1) {
        ConsultFragment fragment = new ConsultFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SHOW_TEXT, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mContentText = getArguments().getString(ARG_SHOW_TEXT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_consult, container, false);
        listView=(ListView)rootView.findViewById(R.id.lv_loadconsult);
        initView();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getContext(),NewsDetailActivity.class);
                News newBean= ConsultAdapter.list.get(i);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
                Date date = new Date(System.currentTimeMillis());
                Util.insert_History(newBean.getTitle(),newBean.getTime(),simpleDateFormat.format(date),1,getContext());

                try {
                    DatabaseHelper dbHelper = new DatabaseHelper(getContext(), "riceKnow.db",null,1);
                    SQLiteDatabase db = dbHelper.getReadableDatabase();
                    Cursor cursor = db.query("News", null, "time=?", new String[]{newBean.getTime()}, null, null, null, null);
                    while (cursor.moveToNext())
                    {
                        int x=cursor.getInt(cursor.getColumnIndex("flag"));
                        newBean.setFlag(x);
                    }
                }catch (Exception e){
                    Log.e("<<<<<<<","error");
                }
                intent.putExtra("newBean",newBean);
                startActivity(intent);
                Log.e("news",newBean.toString());
            }
        });
        return rootView;
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
                Cursor cursor = db.query("News", null, "type=?", new String[]{"咨询"}, null, null, null, null);
                if (cursor.moveToFirst())
                {
                    do {
                        News newBean=new News();
                        newBean.setFlag(cursor.getInt(cursor.getColumnIndex("flag")));
                        newBean.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                        newBean.setType(cursor.getString(cursor.getColumnIndex("type")));
                        newBean.setTime(cursor.getString(cursor.getColumnIndex("time"))); ;
                        newBean.setContent(cursor.getString(cursor.getColumnIndex("content")));
                        newBean.setUrl(cursor.getString(cursor.getColumnIndex("url")));
                        newBean.setImgurl(cursor.getString(cursor.getColumnIndex("imgurl")));
                        newBeans.add(newBean);
                        Log.e("news",newBean.toString());
                    } while (cursor.moveToNext());

                }
            }catch (Exception e){
                Log.d("main","error...");
            }

        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listView.setAdapter(new ConsultAdapter(newBeans,getActivity()));
            }
        });

    }
}