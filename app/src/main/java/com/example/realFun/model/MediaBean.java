package com.example.realFun.model;


import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;

import java.io.Serializable;

import static android.R.attr.path;

/**
 * Created by hao on 22/6/17.
 */

public class MediaBean extends SwitchVideoModel implements Serializable {
    private static final long serialVersionUID = 7382351359868556980L;
    public String name;
    public Bitmap thumbnail;

    public MediaBean(String name,String url,Bitmap thumbnail) {
        super(name, url);
        this.thumbnail = thumbnail;

    }


    public String getName(){
        return name;
    }
    public Bitmap getThumbnail(){
        return url!= null ?getVideoThumbNail(url): thumbnail;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setThumbnail(Bitmap thumbnail){
        this.thumbnail = thumbnail;
    }
    @Override
    public String toString() {
        return "MediaBean [url=" +url + ", name=" + name + ", thumbnail=" + thumbnail + "]";
    }
    public Bitmap getVideoThumbNail(String filePath) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(filePath);
            bitmap = retriever.getFrameAtTime();
        }
        catch(IllegalArgumentException e) {
            e.printStackTrace();
        }
        catch (RuntimeException e) {
            e.printStackTrace();
        }
        finally {
            try {
                retriever.release();
            }
            catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }
}
