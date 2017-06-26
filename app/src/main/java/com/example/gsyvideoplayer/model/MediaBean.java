package com.example.gsyvideoplayer.model;

import android.graphics.Bitmap;

import com.example.gsyvideoplayer.model.SwitchVideoModel;


/**
 * Created by hao on 22/6/17.
 */

public class MediaBean extends SwitchVideoModel {

    public String name;
    public long size;
    public int duration;
    public String thumbnail;

    public MediaBean(String name,String url,int duration,long size,String  thumbnail) {
        super(name, url);
        this.size = size;
        this.thumbnail = thumbnail;
        this.duration = duration;

    }


    public String getName(){
        return name;
    }
    public long getSize(){
        return size;
    }
    public String getThumbnail(){
        return thumbnail;
    }
    public int getDuration(){
        return duration;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setSize(long size){
        this.size = size;
    }
    public void setThumbnail(String thumbnail){
        this.thumbnail = thumbnail;
    }
    public void setDuration(int duration){
        this.duration = duration;
    }
}
