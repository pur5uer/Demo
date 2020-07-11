package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

public class Clock extends AppCompatActivity {
    private Intent conIntent = null;//公用临时Intent
    public static boolean start_By_Pending = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);
        Button one = (Button)findViewById(R.id.button2);
        Button everyday = (Button)findViewById(R.id.button);
        Button cancel = (Button)findViewById(R.id.button3);
        //初始化timePicker
        final TimePicker t = (TimePicker)findViewById(R.id.timePicker);
        //调用checkPermission方法，若无“显示在其他应用上层”权限，则跳转至授权页面
        WindowPermissionCheck.checkPermission(Clock.this);

        //事件监听-只响一次
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Clock.this,ClockService.class);
                intent.putExtra("Hour",t.getCurrentHour());
                intent.putExtra("Minute",t.getCurrentMinute());
                intent.putExtra("flag",0); //只响一次的flag是0
                start_By_Pending = false;
                startService(intent);
                conIntent = intent;
            }
        });
        //事件监听-每天
        everyday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Clock.this,ClockService.class);
                intent.putExtra("Hour",t.getCurrentHour());
                intent.putExtra("Minute",t.getCurrentMinute());
                intent.putExtra("flag",1); //每天的flag是1
                start_By_Pending = false;
                startService(intent);
                conIntent = intent;
            }
        });
        //事件监听-取消当前所有闹钟
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(conIntent != null)
                    stopService(conIntent);
            }
        });
    }
}
