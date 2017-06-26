package com.example.gsyvideoplayer.utils;

import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import com.example.gsyvideoplayer.MediaBean;
import com.example.gsyvideoplayer.MyApplication;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by hao on 22/6/17.
 */

public class ScannerAnsyTask extends AsyncTask<Void,Integer,List<MediaBean>> {
    private List<MediaBean> mMediaInfoList = new ArrayList<MediaBean>();

    @Override
    protected List<MediaBean> doInBackground(Void... voids) {
        return null;
    }
    private void getAllVideoInfo(final Context context){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String,List<MediaBean>> allVideoTemp = new HashMap<>();// store all video thumbnails
                Uri mVideoUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                final String []proj = {MediaStore.Video.Thumbnails._ID, MediaStore.Video.Thumbnails.DATA
                , MediaStore.Video.Media.DURATION, MediaStore.Video.Media.SIZE
                , MediaStore.Video.Media.DISPLAY_NAME, MediaStore.Video.Media.DATE_MODIFIED};
                ContentResolver cr = new MyApplication().getContentResolver();
                Cursor mCursor = cr.query(mVideoUri,
                        proj, MediaStore.Video.Media.MIME_TYPE+"=?",
                        new String[]{"Video.mp4"},
                        MediaStore.Video.Media.DATE_MODIFIED + "desc");
                if (mCursor!=null){
                    while (mCursor.moveToNext()){
                        int videoId = mCursor.getInt(mCursor.getColumnIndex(MediaStore.Video.Media._ID));
                        String path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Video.Media.DATA));
                        int duration = mCursor.getInt(mCursor.getColumnIndex(MediaStore.Video.Media.DURATION));
                        long size = mCursor.getLong(mCursor.getColumnIndex(MediaStore.Video.Media.SIZE))/1024;  //1024 1kb
                        if (size<0){
                            Log.e("dml","this video size <0" + path);
                            size = new File(path).length()/1024;
                        }
                        String displayName = mCursor.getString(mCursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME));
                        long modifyTime = mCursor.getLong(mCursor.getColumnIndex(MediaStore.Video.Media.DATE_MODIFIED));
                        //get ThumbNails according to id

                        MediaStore.Video.Thumbnails.getThumbnail(cr,videoId, MediaStore.Video.Thumbnails.MICRO_KIND,null);
                    }
                }

            }
        });
    }

}
