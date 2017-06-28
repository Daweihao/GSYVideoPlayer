package com.example.gsyvideoplayer.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gsyvideoplayer.adapter.ListVideoAdapter;
import com.example.gsyvideoplayer.model.MediaBean;
import com.example.gsyvideoplayer.R;
import com.example.gsyvideoplayer.utils.ScannerAnsyTask;
import com.shuyu.gsyvideoplayer.utils.ListVideoUtil;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;


public class UploadVideo extends AppCompatActivity {

    public List<MediaBean> mChildList;
    public ProgressBar mProgressBar;
    private ScannerAnsyTask mAsynTask;
    public HashMap<String,List<MediaBean>> mChildMap;
    private TextView mTitle;

    @BindView(R.id.video_list)
    ListView videoList;
    @BindView(R.id.video_full_container)
    FrameLayout videoFullContainer;
    @BindView(R.id.activity_list_video)
    RelativeLayout activityListVideo;

    ListVideoUtil listVideoUtil;
    ListVideoAdapter listVideoAdapter;
    int lastVisibleItem;
    int firstVisibleItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            getWindow().setEnterTransition(new Explode());
            getWindow().setExitTransition(new Explode());
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_video);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        startScanTask();

        listVideoUtil = new ListVideoUtil(this);
        listVideoUtil.setFullViewContainer(videoFullContainer);
        listVideoUtil.setHideStatusBar(true);
        //listVideoUtil.setHideActionBar(true);
        listVideoUtil.setNeedLockFull(true);

        listVideoAdapter = new ListVideoAdapter(this, listVideoUtil,mChildMap);
        listVideoAdapter.setRootView(activityListVideo);
        videoList.setAdapter(listVideoAdapter);

        //listVideoUtil.setShowFullAnimation(true);
        //listVideoUtil.setAutoRotation(true);
        //listVideoUtil.setFullLandFrist(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Upload Action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void startScanTask() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mAsynTask = new ScannerAnsyTask(getParent(),mProgressBar);
                mAsynTask.execute();
                try {
                    mChildList = mAsynTask.get();
                    Log.e("CJT","-------------mAnsyTask.getStatus()---="+mAsynTask.getStatus());
                    if (mChildList !=null && mChildList.size()>0){
                        mHandler.sendEmptyMessage(0x101);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private Handler mHandler = new Handler(){
        public void handleMessage(Message msg){
            if (msg.what == 0x101){
                mTitle.setText("local media list");

            }
        }
    };


}
