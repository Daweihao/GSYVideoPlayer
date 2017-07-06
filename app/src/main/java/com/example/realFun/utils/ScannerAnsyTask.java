package com.example.realFun.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.realFun.model.MediaBean;
import com.example.realFun.MyApplication;
import com.example.realFun.view.UploadCardView;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by hao on 22/6/17.
 */

@SuppressWarnings("unused")
public class ScannerAnsyTask extends AsyncTask<Void, Integer, List<MediaBean>> {
    private ProgressBar mprogressBar;
    private Handler progressHandler;
    private Activity mActivity;
    private List<MediaBean> list =new ArrayList<>();

    public ScannerAnsyTask(){
        super();
    }

    public ScannerAnsyTask(Activity activity,ProgressBar progressBar){
        super();
        this.mActivity = activity;
        this.mprogressBar = progressBar;
    }

    @Override
    protected List<MediaBean> doInBackground(Void... params) {
        List<MediaBean> list = new ArrayList<>();
        list = getVideoFile(list, Environment.getExternalStorageDirectory());
        Log.e("Hao","最后大小"+"ScannerAnsynTask------第一条数据-----");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return list;
    }
    private List<MediaBean> getVideoFile(final List<MediaBean> list, File file) {

        file.listFiles(new FileFilter() {

            @Override
            public boolean accept(File file) {

                String name = file.getName();

                int i = name.indexOf('.');
                if (i != -1) {
                    name = name.substring(i);
                    if (name.equalsIgnoreCase(".mp4") || name.equalsIgnoreCase(".3gp") || name.equalsIgnoreCase(".wmv")
                            || name.equalsIgnoreCase(".ts") || name.equalsIgnoreCase(".rmvb")
                            || name.equalsIgnoreCase(".mov") || name.equalsIgnoreCase(".m4v")
                            || name.equalsIgnoreCase(".avi") || name.equalsIgnoreCase(".m3u8")
                            || name.equalsIgnoreCase(".3gpp") || name.equalsIgnoreCase(".3gpp2")
                            || name.equalsIgnoreCase(".mkv") || name.equalsIgnoreCase(".flv")
                            || name.equalsIgnoreCase(".divx") || name.equalsIgnoreCase(".f4v")
                            || name.equalsIgnoreCase(".rm") || name.equalsIgnoreCase(".asf")
                            || name.equalsIgnoreCase(".ram") || name.equalsIgnoreCase(".mpg")
                            || name.equalsIgnoreCase(".v8") || name.equalsIgnoreCase(".swf")
                            || name.equalsIgnoreCase(".m2v") || name.equalsIgnoreCase(".asx")
                            || name.equalsIgnoreCase(".ra") || name.equalsIgnoreCase(".ndivx")
                            || name.equalsIgnoreCase(".xvid")) {
                        MediaBean video = new MediaBean(null,null,null);
                        file.getUsableSpace();
                        video.setName(file.getName());
                        video.setUrl(file.getAbsolutePath());
                        video.getVideoThumbNail(file.getAbsolutePath());
                        Log.e("CJT", "�����Ĵ�С" + "ScannerAnsyTask---��Ƶ����--name--" + video.getUrl());
                        list.add(video);
                        return true;
                    }
                } else if (file.isDirectory()) {
                    getVideoFile(list, file);
                }
                return false;
            }
        });
        return list;
    }


//    private List<MediaBean> getAllVideoInfo(){
//
//        final HashMap<String,List<MediaBean>> allVideoTemp = new HashMap<>();// store all video thumbnails
//                Uri mVideoUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
//
//                final String []proj = {MediaStore.Video.Thumbnails._ID, MediaStore.Video.Thumbnails.DATA
//                , MediaStore.Video.Media.DURATION, MediaStore.Video.Media.SIZE
//                , MediaStore.Video.Media.DISPLAY_NAME, MediaStore.Video.Media.DATE_MODIFIED};
//                ContentResolver cr =  MyApplication.getContext().getContentResolver();
//                Cursor mCursor = cr.query(mVideoUri,
//                        proj, MediaStore.Video.Media.MIME_TYPE+"=?",
//                        new String[]{"Video.mp4"},
//                        MediaStore.Video.Media.DATE_MODIFIED + " desc");
//                if (mCursor!=null){
//                    while (mCursor.moveToNext()){
//                        int videoId = mCursor.getInt(mCursor.getColumnIndex(MediaStore.Video.Media._ID));
//                        String path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Video.Media.DATA));
//                        int duration = mCursor.getInt(mCursor.getColumnIndex(MediaStore.Video.Media.DURATION));
//                        long size = mCursor.getLong(mCursor.getColumnIndex(MediaStore.Video.Media.SIZE))/1024;  //1024 1kb
//                        if (size<0){
//                            Log.e("dml","this video size <0" + path);
//                            size = new File(path).length()/1024;
//                        }
//                        String displayName = mCursor.getString(mCursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME));
//                        long modifyTime = mCursor.getLong(mCursor.getColumnIndex(MediaStore.Video.Media.DATE_MODIFIED));
//                        //get ThumbNails according to id
//
//                        MediaStore.Video.Thumbnails.getThumbnail(cr,videoId, MediaStore.Video.Thumbnails.MICRO_KIND,null);
//                        String []projection = {MediaStore.Video.Thumbnails._ID,
//                                MediaStore.Video.Thumbnails.DATA};
//                        Cursor cursor = cr.query(MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI,
//                                projection,
//                                MediaStore.Video.Thumbnails._ID + "=?",
//                                new String[]{videoId+""},null );
//                        String thumbPath = "";
//                        while (cursor.moveToNext()){
//                            thumbPath = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Thumbnails.DATA));
//                        }
//                        cursor.close();
//                        String dirPath = new File(path).getParentFile().getAbsolutePath();
//                        /*if (allVideoTemp.containsKey(dirPath)){
//                            List<MediaBean> data = allVideoTemp.get(dirPath);
//                            data.add(new MediaBean(displayName,path,duration,size,thumbPath));
//                            continue;
//                        }else {
//                            List<MediaBean> data = new ArrayList<>();
//                            data.add(new MediaBean(displayName,path,duration,size,thumbPath));
//                            allVideoTemp.put(dirPath,data);
//                        }*/
//                         List<MediaBean> data = allVideoTemp.get(dirPath);
//                        data.add(new MediaBean(displayName,path,duration,size,thumbPath));
//
//                    }
//                    mCursor.close();
//                }
//                ArrayList<MediaBean> arrayList = new ArrayList<>();
//                for (String key:allVideoTemp.keySet()){
//                    arrayList.add((MediaBean) allVideoTemp.get(key));
//                    Log.d("Video Info",key);
//                }
//
//        return arrayList;
//    }
//    @Override
    protected  void onProgressUpdate(Integer... values){
        super.onProgressUpdate(values);
    }
    @Override
    protected void onPostExecute(List<MediaBean> videoInfos){
        super.onPostExecute(videoInfos);
        Log.e("CTJ","最后的大小"+"ScannerAsynTask--View.GONE--");
        mprogressBar.setVisibility(View.GONE);
        if (videoInfos == null){
            Toast.makeText(MyApplication.getContext(),"Your video file doesn't exist in your device!",Toast.LENGTH_LONG).show();
        }
    }


}
