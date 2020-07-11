package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;


public class Music extends AppCompatActivity {
    private int flag = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        //创建音乐Uri列表
        final List<Uri> musicUri = new ArrayList<Uri>();
        final List<String> musicName = new ArrayList<String>();
        musicUri.add(Uri.parse("http://www.getheading.xyz:81/music/01.%E9%9B%B2%E9%9B%80.mp3"));
        musicUri.add(Uri.parse("http://www.getheading.xyz:81/music/yu-yu%20-%20%E6%A1%9C%E5%94%84.mp3"));
        musicUri.add(Uri.parse("http://www.getheading.xyz:81/music/%E5%88%A5%E9%87%8E%E5%8A%A0%E5%A5%88%20-%20%E7%94%9F%E6%B4%BB.mp3"));
        musicName.add("ASCA - 雲雀");
        musicName.add("yu-yu - 桜唄");
        musicName.add("別野加奈 - 生活");
        //定义按钮上一首、播放、下一首
        Button playPre = (Button) findViewById(R.id.playpre);
        Button play = (Button) findViewById(R.id.play);
        Button playNext = (Button) findViewById(R.id.playnext);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Music.this,MusicService.class);
                intent.putExtra("flag",flag);
                startService(intent);
            }
        });
        playPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag > 0)
                    flag--;
                else
                    flag = musicUri.size()-1;
                Intent intent = new Intent(Music.this,MusicService.class);
                intent.putExtra("flag",flag);
                intent.putExtra("changeJudge",true);
                startService(intent);
            }
        });
        playNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag < musicUri.size() - 1)
                    flag++;
                else
                    flag = 0;
                Intent intent = new Intent(Music.this,MusicService.class);
                intent.putExtra("flag",flag);
                intent.putExtra("changeJudge",true);
                startService(intent);
            }
        });
    }

    protected void onDestroy(){
        super.onDestroy();
    }
}