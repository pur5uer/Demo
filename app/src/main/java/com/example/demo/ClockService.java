package com.example.demo;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class ClockService extends Service {
    public ClockService() {
    }
    private MediaPlayer mp = new MediaPlayer();
    private int flag = 0;
    private int Hour;
    private int Minute;
    private Calendar calendar = Calendar.getInstance();
    private long daySpan = 24 * 60 * 60 * 1000;
    private AlarmManager alarmManager;
    private Intent alarmIntent;
    private PendingIntent pendingIntent = null;
    IBinder iBinder;

    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent,int flags,int startId){

        if(Clock.start_By_Pending) //在Clock中定义一个静态布尔变量，每次点击设置为false，
            //pendingIntent发送后设置为true
            if(!mp.isPlaying()){
                //当用户触摸“只响一次”时
                if(flag == 0){
                    playMusic();
                    AlertDialog.Builder builder = new AlertDialog.Builder(ClockService.this);
                    builder.setMessage("该起床啦！");
                    builder.setNegativeButton("CANCEL", null);//触摸取消反而继续播放闹铃，并且对话框消失
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            stopMusic();
                            alarmManager.cancel(pendingIntent); //取消闹钟
                        }
                    });
                    final Dialog dialog = builder.create();
                    Objects.requireNonNull(dialog.getWindow()).setType(WindowManager.LayoutParams.TYPE_SYSTEM_DIALOG);
                    dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
                    dialog.show();
                }
                //当用户触摸“每天”时
                else{
                    playMusic();
                    AlertDialog.Builder builder = new AlertDialog.Builder(ClockService.this);
                    builder.setMessage("该起床啦！");
                    builder.setNegativeButton("CANCEL", null);//触摸取消反而继续播放闹铃，并且对话框消失
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            stopMusic();
                        }
                    });
                    final Dialog dialog = builder.create();
                    Objects.requireNonNull(dialog.getWindow()).setType(WindowManager.LayoutParams.TYPE_SYSTEM_DIALOG);
                    dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
                    dialog.show();
                    //再设置闹钟
                    setClock(intent);
                }
            }
        if(!Clock.start_By_Pending)
        setClock(intent);

        //告知用户闹钟信息
        if(flag == 0 && !Clock.start_By_Pending){
            Toast.makeText(getApplicationContext(),"闹钟设置成功，睡个好觉吧！" + '\n' +
                    "将于" + getNextTime(intent) + "响铃一次",Toast.LENGTH_LONG).show();
        }
        if(flag == 1 && !Clock.start_By_Pending){
            Toast.makeText(getApplicationContext(),"闹钟设置成功，睡个好觉吧！" + '\n' +
                    "将于" + getNextTime(intent) + "开始每天响铃",Toast.LENGTH_LONG).show();
        }
        Clock.start_By_Pending = true;
        return super.onStartCommand(intent,flags,startId);
    }

    //得到下个闹钟时间的方法
    public String getNextTime(Intent intent){
        flag = intent.getIntExtra("flag",0);
        Hour = intent.getIntExtra("Hour", calendar.get(Calendar.HOUR_OF_DAY));
        Minute = intent.getIntExtra("Minute",calendar.get(Calendar.MINUTE));
        //得到小时、分钟时间格式的字符串
        String H = ""+ Hour;String M = "" + Minute;
        if(Hour < 10) H = "0" + Hour;
        if(Minute < 10) M = "0" + Minute;
        //定点
        Date now = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//日期格式
        String s = dateFormat.format(now);String s1 = s + " " + H + ':' + M + ":00";
        //如果今天的已经过了，首次运行时间就改为明天
        if(System.currentTimeMillis() > strToDateLong(s1).getTime()){
            Date startTime = new Date(strToDateLong(s1).getTime() + daySpan);
            s1 = dateFormat.format(startTime) + " " + H + ':' + M + ":00";
        }
        return s1;
    }

    //设置闹钟的方法
    public void setClock(Intent intent){
        //AlarmManager定时任务
        long triggerAtMillis = SystemClock.elapsedRealtime() + strToDateLong(getNextTime(intent)).getTime() - System.currentTimeMillis();
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmIntent = new Intent(this,ClockService.class);
        pendingIntent = PendingIntent.getService(this,0,alarmIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        assert alarmManager != null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {// 6.0
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtMillis, pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//  4.4
            alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtMillis, pendingIntent);
        } else {
            alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtMillis, pendingIntent);
        }
    }

    //播放音乐的方法
    public void playMusic(){
        Uri uri = Uri.parse("android.resource://" + getApplicationContext().getPackageName() + "/" + R.raw.m1);
        try{
            mp.reset();
            mp.setDataSource(ClockService.this,uri);
            mp.prepare();
            mp.start();
            //Toast.makeText(ClockService.this,"success",Toast.LENGTH_LONG).show();
        }
        catch (IOException e){
            e.printStackTrace();
            Toast.makeText(ClockService.this,"failed",Toast.LENGTH_LONG).show();
        }
    }

    //停止音乐的方法
    public void stopMusic(){
        mp.stop();
    }

    @SuppressLint("SimpleDateFormat")
    public static Date strToDateLong(String strDate){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition position = new ParsePosition(0);
        Date strtodate = format.parse(strDate,position);
        return strtodate;
    }

    @Override
    public void onDestroy(){
        if(mp.isPlaying()){
            mp.stop();
            mp.release();
        }
        Toast.makeText(ClockService.this,"已取消当前所有闹钟！",Toast.LENGTH_LONG).show();
        super.onDestroy();
    }
}
