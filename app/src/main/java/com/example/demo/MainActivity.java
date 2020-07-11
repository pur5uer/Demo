package com.example.demo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    //判断是否在电池限制白名单
    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean isIgnoringBatteryOptimizations() {
        boolean isIgnoring = false;
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        if (powerManager != null) {
            isIgnoring = powerManager.isIgnoringBatteryOptimizations(getPackageName());
        }
        return isIgnoring;
    }

    //申请加入白名单
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void requestIgnoreBatteryOptimizations() {
        try {
            @SuppressLint("BatteryLife") Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //调用checkPermission方法，若无“显示在其他应用上层”权限，则跳转至授权页面
        WindowPermissionCheck.checkPermission(MainActivity.this);
        //如果Android版本高于Android M
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            //如果不在白名单，申请加入
        if(!isIgnoringBatteryOptimizations())
            requestIgnoreBatteryOptimizations();
        //三个按钮
        Button video_button = (Button)findViewById(R.id.video_button);
        Button music_button = (Button)findViewById(R.id.music_button);
        Button clock_button = (Button)findViewById(R.id.clock_button);
        //设置事件响应
        video_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Video.class);
                startActivity(i);
            }
        });
        music_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Music.class);
                startActivity(i);
            }
        });
        clock_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Clock.class);
                startActivity(i);
            }
        });
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //新建的xml文件
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        //根据不同的id点击不同按钮控制activity需要做的事件
        switch (item.getItemId())
        {
            case R.id. setting_item:
                Intent i = new Intent(MainActivity.this,Setting.class);
                startActivity(i);
                break;
            case R.id. logout_item:
                finish();
                break;
        }
        return true;
    }
}
