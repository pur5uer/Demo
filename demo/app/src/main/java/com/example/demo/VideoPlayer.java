package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VideoPlayer extends AppCompatActivity {
    MediaPlayer mp;
    SurfaceView surfaceView;
    private int pausePosition;
    private int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        surfaceView = (SurfaceView)findViewById(R.id.surfaceView);
        mp = new MediaPlayer();
        Intent intent;
        //创建并初始化视频列表
        final List<String> videoName = new ArrayList<String>();
        List<Uri> videoUri = new ArrayList<Uri>();
        videoName.add("Duca - たいせつなきみのために、ぼくにできるいちばんのこと");  videoName.add("Clean Bandit,Zara Larsson - Symphony");
        videoName.add("蔡恩雨 - Burn");  videoName.add("Delacey - Dream It Possible");
        videoName.add("Aimer - 茜さす");  videoName.add("SKOTT - Mermaid");
        videoName.add("Ludovico Einaudi - experience");  videoName.add("Joshua Hyslop - Let It Go");
        videoName.add("Nightwish - While Your Lips Are Still Red");  videoName.add("Nightwish - Amaranth");
        videoName.add("Aimer - Black Bird (映画ver.)");  videoName.add("英雄联盟 - It’s Me & You");
        videoName.add("Frances - Set Sail 歌词版");  videoName.add("NIKIIE - South Wind");
        videoName.add("MYTH & ROID - shadowgraph (Short Ver.)");  videoName.add("ヨルシカ - パレード");
        videoName.add("Within Temptation - Memories");  videoName.add("Aimer - Falling Alone");
        videoName.add("Ólafur Arnalds - 3055");  videoName.add("AKINO - 月のもう半分 (Short Ver.)");
        videoUri.add(Uri.parse("http://www.getheading.xyz:81/video/Duca%20-%20%E3%81%9F%E3%81%84%E3%81%9B%E3%81%A4%E3%81%AA%E3%81%8D%E3%81%BF%E3%81%AE%E3%81%9F%E3%82%81%E3%81%AB%E3%80%81%E3%81%BC%E3%81%8F%E3%81%AB%E3%81%A7%E3%81%8D%E3%82%8B%E3%81%84%E3%81%A1%E3%81%B0%E3%82%93%E3%81%AE%E3%81%93%E3%81%A8.mp4"));
        videoUri.add(Uri.parse("http://www.getheading.xyz:81/video/Clean%20Bandit,Zara%20Larsson%20-%20Symphony.mp4"));
        videoUri.add(Uri.parse("http://www.getheading.xyz:81/video/%E8%94%A1%E6%81%A9%E9%9B%A8%20-%20Burn.mp4"));
        videoUri.add(Uri.parse("http://www.getheading.xyz:81/video/Delacey%20-%20Dream%20It%20Possible.mp4"));
        videoUri.add(Uri.parse("http://www.getheading.xyz:81/video/Aimer%20-%20%E8%8C%9C%E3%81%95%E3%81%99.mp4"));
        videoUri.add(Uri.parse("http://www.getheading.xyz:81/video/SKOTT%20-%20Mermaid.mp4"));
        videoUri.add(Uri.parse("http://www.getheading.xyz:81/video/Ludovico%20Einaudi%20-%20%E6%9C%80%E7%BE%8E%E5%9C%9F%E8%80%B3%E5%85%B6%E5%AE%A3%E4%BC%A0%E7%89%87%EF%BC%88Watchtower%C2%A0of%C2%A0Turkey%EF%BC%89.mp4"));
        videoUri.add(Uri.parse("http://www.getheading.xyz:81/video/Joshua%20Hyslop%20-%20Let%20It%20Go.mp4"));
        videoUri.add(Uri.parse("http://www.getheading.xyz:81/video/Nightwish%20-%20While%20Your%20Lips%20Are%20Still%20Red.mp4"));
        videoUri.add(Uri.parse("http://www.getheading.xyz:81/video/Nightwish%20-%20Amaranth.mp4"));
        videoUri.add(Uri.parse("http://www.getheading.xyz:81/video/Aimer%20-%20Black%20Bird%20(%E6%98%A0%E7%94%BBver.).mp4"));
        videoUri.add(Uri.parse("http://www.getheading.xyz:81/video/%E8%8B%B1%E9%9B%84%E8%81%94%E7%9B%9F%20-%20It%E2%80%99s%20Me%20&%20You.mp4"));
        videoUri.add(Uri.parse("http://www.getheading.xyz:81/video/Frances%20-%20Set%C2%A0Sail%20%E6%AD%8C%E8%AF%8D%E7%89%88.mp4"));
        videoUri.add(Uri.parse("http://www.getheading.xyz:81/video/NIKIIE%20-%20South%20Wind.mp4"));
        videoUri.add(Uri.parse("http://www.getheading.xyz:81/video/MYTH%20&%20ROID%20-%20shadowgraph%20(Short%20Ver.).mp4"));
        videoUri.add(Uri.parse("http://www.getheading.xyz:81/video/%E3%83%A8%E3%83%AB%E3%82%B7%E3%82%AB%20-%20%E3%83%91%E3%83%AC%E3%83%BC%E3%83%89.mp4"));
        videoUri.add(Uri.parse("http://www.getheading.xyz:81/video/Within%20Temptation%20-%20Memories.mp4"));
        videoUri.add(Uri.parse("http://www.getheading.xyz:81/video/Aimer%20-%20Falling%20Alone.mp4"));
        videoUri.add(Uri.parse("http://www.getheading.xyz:81/video/%C3%93lafur%20Arnalds%20-%203055.mp4"));
        videoUri.add(Uri.parse("http://www.getheading.xyz:81/video/AKINO%20-%20%E6%9C%88%E3%81%AE%E3%82%82%E3%81%86%E5%8D%8A%E5%88%86%20(Short%20Ver.).mp4"));
        ImageButton pauseOrUndo = (ImageButton)findViewById(R.id.PauseOrUndo);
        pauseOrUndo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果正在播放，暂停
                if(mp.isPlaying()){
                    mp.pause();
                    Toast.makeText(VideoPlayer.this, "暂停播放："
                            + videoName.get(position), Toast.LENGTH_LONG).show();
                }
                //如果暂停播放，继续播放
                else{
                    mp.start();
                    Toast.makeText(VideoPlayer.this, "继续播放："
                            + videoName.get(position), Toast.LENGTH_LONG).show();
                }
            }
        });
        //定位视频
        intent = getIntent();
        position = intent.getIntExtra("Position",0);
        //播放视频
        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setDisplay(surfaceView.getHolder());
                mp.start();
            }
        });
        try{
            mp.setDataSource(VideoPlayer.this,videoUri.get(position));
            mp.prepareAsync();
            mp.seekTo(pausePosition);
            mp.getCurrentPosition();
            Toast.makeText(VideoPlayer.this,"正在播放：" + videoName.get(position),
                    Toast.LENGTH_LONG).show();
        }
        catch (IOException e){
            e.printStackTrace();
            Toast.makeText(VideoPlayer.this,"加载失败",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(mp.isPlaying()){
            mp.stop();
        }
        mp.release();
    }
}
