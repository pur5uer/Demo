package com.example.demo;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.widget.Toast;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MusicService extends Service {
    public MusicService() {
    }
    IBinder mBinder;
    private MediaPlayer mp = new MediaPlayer();
    private int flag = 0;
    private int pausePosition;
    private boolean pauseJudge = false;


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //创建音乐Uri列表
        final List<Uri> musicUri = new ArrayList<Uri>();
        final List<String> musicName = new ArrayList<String>();
        musicUri.add(Uri.parse("http://www.getheading.xyz:81/music/01.%E9%9B%B2%E9%9B%80.mp3"));
        musicUri.add(Uri.parse("http://www.getheading.xyz:81/music/yu-yu%20-%20%E6%A1%9C%E5%94%84.mp3"));
        musicUri.add(Uri.parse("http://www.getheading.xyz:81/music/%E5%88%A5%E9%87%8E%E5%8A%A0%E5%A5%88%20-%20%E7%94%9F%E6%B4%BB.mp3"));
        musicName.add("ASCA - 雲雀");
        musicName.add("yu-yu - 桜唄");
        musicName.add("別野加奈 - 生活");
        flag = intent.getIntExtra("flag",0);
        //如果正在播放音乐
        if(mp.isPlaying()){
            //如果收到切歌信息,切歌
            if(intent.getBooleanExtra("changeJudge",false)){
                try {
                    mp.reset();
                    mp.setDataSource(MusicService.this, musicUri.get(flag));
                    mp.prepare();
                    mp.start();
                    pauseJudge = false;
                    Toast.makeText(MusicService.this,"正在播放：" +
                            musicName.get(flag) ,Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(MusicService.this,"加载失败",Toast.LENGTH_LONG).show();
                }
            }
            //如果没有收到切歌信息，暂停
            else{
                mp.pause();
                pauseJudge = true;
                pausePosition = mp.getCurrentPosition();
                Toast.makeText(MusicService.this,"暂停播放：" +
                        musicName.get(flag) ,Toast.LENGTH_LONG).show();
            }
        }

        //如果没有播放音乐
        else{
            //如果处于暂停状态
            if(pauseJudge){
                //如果收到切歌信息，切歌
                if(intent.getBooleanExtra("changeJudge",false)){
                    try {
                        mp.reset();
                        mp.setDataSource(MusicService.this, musicUri.get(flag));
                        mp.prepare();
                        mp.start();
                        Toast.makeText(MusicService.this,"正在播放：" +
                                musicName.get(flag) ,Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(MusicService.this,"加载失败",Toast.LENGTH_LONG).show();
                    }
                }
                //如果没有收到切歌信息，继续播放
                else{
                    mp.seekTo(pausePosition);
                    mp.getCurrentPosition();
                    mp.start();
                    pauseJudge = false;
                    Toast.makeText(MusicService.this,"继续播放：" +
                            musicName.get(flag) ,Toast.LENGTH_LONG).show();
                }
            }
            //如果不处于暂停状态，初始化并播放
            else{
                try {
                    mp.reset();
                    mp.setDataSource(MusicService.this, musicUri.get(flag));
                    mp.prepare();
                    mp.start();
                    Toast.makeText(MusicService.this,"正在播放：" +
                            musicName.get(flag) ,Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(MusicService.this,"加载失败",Toast.LENGTH_LONG).show();
                }
            }
        }
        flags = START_STICKY;
        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy(){
        if (mp.isPlaying()) {
            mp.stop();
        }
        mp.release();
        super.onDestroy();
    }
}

