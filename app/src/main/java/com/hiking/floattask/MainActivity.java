package com.hiking.floattask;

import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;


import com.hiking.floattask.bean.GestureBean;
import com.hiking.floattask.bean.GestureViewAdapter;
import com.hiking.floattask.manager.FloatWindowManager;
import com.hiking.floattask.utils.SPUtil;
import com.hiking.floattask.utils.SystemUtil;
import com.hiking.floattask.view.WaterDropView.Gesture;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    public static final String SERVICE_NAME = "com.hiking.floattask.FloatViewService";
    private RecyclerView mGestureView;
    private ArrayList<GestureBean> gestureList;
    private Switch mSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initView();
        initListener();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //关联侧滑界面
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if(SystemUtil.isServiceWork(MainActivity.this,SERVICE_NAME)
                        &&!mSwitch.isChecked()
                        ){
                    mSwitch.setChecked(true);
                }
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();






        //当然有时候我们想要用其他的logo替换返回箭头,来做一些其他的操作
        //toolbar.setHomeAsUpIndicator(android.R.drawable.ic_lock_power_off);

        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(intent, 1);
                Toast.makeText(this, "请先允许FloatBall出现在顶部", Toast.LENGTH_SHORT).show();
            }
        }

        Log.d("hello","nihoa");
    }

    private void initListener() {
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    startService();
                }else{
                    stopService();
                }

            }
        });
    }

    private void initData() {
        gestureList = new ArrayList<GestureBean>();
        for (Gesture gesture : Gesture.values()) {
            if(gesture.equals(Gesture.MOVE)){continue;}
            gestureList.add(new GestureBean(gesture.toString(),this));
        }

    }

    private void initView() {
        mGestureView = (RecyclerView) findViewById(R.id.gesture_view);
        mGestureView.setLayoutManager(new LinearLayoutManager(this));
        mGestureView.setAdapter(new GestureViewAdapter(gestureList));

        mSwitch = (Switch)findViewById(R.id.service_switch);
    }


    private void startService(){
        Intent i = new Intent(this,FloatViewService.class);
        startService(i);
    }
    private void stopService(){
        Intent i = new Intent(this,FloatViewService.class);
        stopService(i);
    }

    /*private void checkAccessibility() {
        // 判断辅助功能是否开启
        if (!AccessibilityOperation.isAccessibilitySettingsOn(this)) {
            // 引导至辅助功能设置页面
            startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
            Toast.makeText(this, "请先开启FloatBall辅助功能", Toast.LENGTH_SHORT).show();
        }
    }*/
}
