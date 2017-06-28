package com.example.gsyvideoplayer.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.gsyvideoplayer.model.MediaBean;
import com.example.gsyvideoplayer.MyApplication;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by hao on 22/6/17.
 */

public class ScannerAnsyTask extends AsyncTask<Void, Integer, List<MediaBean>> {
    private ProgressBar mprogressBar;
    private Handler mHandler;
    private Activity mActivity;
    private HashMap<String,List<MediaBean>> mAllVideoTemp;
    private List<MediaBean> list ;

    public ScannerAnsyTask(){
        super();
    }

    public ScannerAnsyTask(Activity activity,ProgressBar progressBar){
        super();
        this.mActivity = activity;
        this.mprogressBar = progressBar;
    }

    @Override
    protected List<MediaBean> doInBackground(Void... voids) {
        List<MediaBean> list= getAllVideoInfo();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {

        }
        return list;
    }
    private List<MediaBean> getAllVideoInfo(){

        final HashMap<String,List<MediaBean>> allVideoTemp = new HashMap<>();// store all video thumbnails
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
                        String []projection = {MediaStore.Video.Thumbnails._ID,
                                MediaStore.Video.Thumbnails.DATA};
                        Cursor cursor = cr.query(MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI,
                                projection,
                                MediaStore.Video.Thumbnails._ID + "=?",
                                new String[]{videoId+""},null );
                        String thumbPath = "";
                        while (cursor.moveToNext()){
                            thumbPath = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Thumbnails.DATA));
                        }
                        cursor.close();
                        String dirPath = new File(path).getParentFile().getAbsolutePath();
                        /*if (allVideoTemp.containsKey(dirPath)){
                            List<MediaBean> data = allVideoTemp.get(dirPath);
                            data.add(new MediaBean(displayName,path,duration,size,thumbPath));
                            continue;
                        }else {
                            List<MediaBean> data = new ArrayList<>();
                            data.add(new MediaBean(displayName,path,duration,size,thumbPath));
                            allVideoTemp.put(dirPath,data);
                        }*/
                         List<MediaBean> data = allVideoTemp.get(dirPath);
                        data.add(new MediaBean(displayName,path,duration,size,thumbPath));

                    }
                    mCursor.close();
                }
                ArrayList<MediaBean> arrayList = new ArrayList<>();
                for (String key:allVideoTemp.keySet()){
                    arrayList.add((MediaBean) allVideoTemp.get(key));
                    Log.d("Video Info",key);
                }

        return arrayList;
    }
    @Override
    protected  void onProgressUpdate(Integer... values){
        super.onProgressUpdate(values);
    }
    @Override
    protected void onPostExecute(List<MediaBean> videoInfos){
        super.onPostExecute(videoInfos);
        Log.e("CTJ","最后的大小"+"ScannerAsynTask--View.GONE--");
        mprogressBar.setVisibility(View.GONE);
    }

}
