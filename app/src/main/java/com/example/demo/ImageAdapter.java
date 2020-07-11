package com.example.demo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
    private Context context;
    public ImageAdapter(Context c){
        context = c;
    }

    public int getCount(){
        return mThumbIds.length;
    }

    public Object getItem(int position){
        return null;
    }

    public long getItemId(int position){
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        ImageView imageView;
        if(convertView == null){
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(346,195));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(15,15,15,15);
        }
        else{
            imageView = (ImageView) convertView;
        }
        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    private Integer[] mThumbIds = {
            R.drawable.p1, R.drawable.p2,
            R.drawable.p3, R.drawable.p4,
            R.drawable.p5, R.drawable.p6,
            R.drawable.p7, R.drawable.p8,
            R.drawable.p9, R.drawable.p10,
            R.drawable.p11, R.drawable.p12,
            R.drawable.p13, R.drawable.p14,
            R.drawable.p15, R.drawable.p16,
            R.drawable.p17, R.drawable.p18,
            R.drawable.p19, R.drawable.p20,
    };
}
