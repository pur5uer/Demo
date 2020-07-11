package com.example.demo;


import android.net.Uri;

import java.util.Calendar;

public class TimeR {
    private static int MORNING = -1;
    private static int AFTERNOON = 0;
    private static int NIGHT = 1;
    private int flag = 0;
    /**
     * 通过Calendar类获取时间
     */
    private Calendar calendar = Calendar.getInstance();//可以对每个时间域单独修改，对时间进行加减操作等
    private int year = calendar.get(Calendar.YEAR);
    private int month = calendar.get(Calendar.MONTH);
    private int date = calendar.get(Calendar.DATE);
    private int hour = calendar.get(Calendar.HOUR_OF_DAY);
    private int minute = calendar.get(Calendar.MINUTE);
    private int second = calendar.get(calendar.SECOND);
    public Uri R_uri(){
        int t = hour;
        Uri uri = Uri.parse("");
        if(t>0&&t<13)flag=MORNING;
        if(t>12&&t<18)flag=AFTERNOON;
        if(t>17&&t<25)flag=NIGHT;
        switch (flag){
            case -1:uri = Uri.parse("http://175.24.70.217:81/1/pic/%E8%8A%B1.jpg");break;
            case 0:uri = Uri.parse("http://175.24.70.217:81/1/pic/%E5%8D%88%E5%90%8E.jpg");break;
            case 1:uri = Uri.parse("http://175.24.70.217:81/1/pic/%E5%A4%AA%E7%A9%BA%E7%8E%AB%E7%91%B0.jpg");break;
            default:uri = Uri.parse("http://175.24.70.217:81/1/pic/%E8%8A%B1.jpg");break;
        }
        return uri;
    }
}
