package com.example.riceMan.ui.activity;

import android.os.Bundle;
import android.util.SparseArray;
import android.view.Menu;
import android.widget.RadioGroup;

import com.example.riceMan.R;
import com.example.riceMan.ui.fragment.ConsultFragment;
import com.example.riceMan.ui.fragment.DataFragment;
import com.example.riceMan.ui.fragment.HeadlineFragment;
import com.example.riceMan.ui.fragment.KnowFragment;
import com.example.riceMan.ui.fragment.ManageFragment;
import com.example.riceMan.utils.DataRequestUtil;
import com.google.android.material.navigation.NavigationView;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    private RadioGroup mTabRadioGroup;
    private AppBarConfiguration mAppBarConfiguration;
    private SparseArray<Fragment> mFragmentSparseArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initView();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,
                R.id.nav_search, R.id.nav_favorites, R.id.nav_share,
                R.id.nav_history, R.id.nav_version, R.id.nav_feedback)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void initView(){
        mTabRadioGroup = findViewById(R.id.tabs_rg);
        mFragmentSparseArray = new SparseArray<>();
        HeadlineFragment headlineFragment=HeadlineFragment.newInstance("头条");
        KnowFragment knowFragment = KnowFragment.newInstance("知道");
        ConsultFragment consultFragment= ConsultFragment.newInstance("咨询");
        ManageFragment manageFragment=ManageFragment.newInstance("经营");
        DataFragment dataFragment=DataFragment.newInstance("数据");
        mFragmentSparseArray.append(R.id.headline_tab, headlineFragment);
        mFragmentSparseArray.append(R.id.manage_tab, manageFragment);
        mFragmentSparseArray.append(R.id.consult_tab, consultFragment);
        mFragmentSparseArray.append(R.id.know_tab, knowFragment);
        mFragmentSparseArray.append(R.id.data_tab, dataFragment);
        mTabRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        mFragmentSparseArray.get(checkedId)).commit();
            }
        });
        // 默认显示第一个
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
                mFragmentSparseArray.get(R.id.headline_tab)).commit();

        new Thread(new Runnable() {
            @Override
            public void run() {
                DataRequestUtil.getNewsData(MainActivity.this);
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                DataRequestUtil.getKnowData(MainActivity.this);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                DataRequestUtil.getConsultData(MainActivity.this);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                DataRequestUtil.getRiceData(MainActivity.this);
            }
        }).start();
    }
}