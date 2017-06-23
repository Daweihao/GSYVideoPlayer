package com.example.gsyvideoplayer;

import android.graphics.Bitmap;

import com.example.gsyvideoplayer.model.SwitchVideoModel;


/**
 * Created by hao on 22/6/17.
 */

public class MediaBean extends SwitchVideoModel {

    public String name;
    public String size;

    public Bitmap type;
    public Bitmap  thumbnail;

    public MediaBean(String name,String url,String size,Bitmap type,Bitmap thumbnail) {
        super(name, url);
        this.size = size;
        this.type = type;
        this.thumbnail = thumbnail;

    }


    public String getName(){
        return name;
    }
    public String getSize(){
        return size;
    }
    public Bitmap getType(){
        return type;
    }
    public Bitmap getThumbnail(){
        return thumbnail;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setSize(String size){
        this.size = size;
    }
    public void setThumbnail(Bitmap thumbnail){
        this.thumbnail = thumbnail;
    }
    public void setPath(Bitmap type){
        this.type = type;
    }
}
