package com.example.realFun.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;

import com.example.realFun.PlayActivity;
import com.example.realFun.R;
import com.example.realFun.adapter.CardViewAdapter;
import com.example.realFun.model.MediaBean;
import com.example.realFun.utils.ScannerAnsyTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hao on 5/7/17.
 */

public class UploadCardView extends AppCompatActivity implements AdapterViewCompat.OnItemClickListener{
    public final static String TRANSITION = "TRANSITION";
    private DrawerLayout drawerLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    public List<MediaBean> mChildList;
    private ScannerAnsyTask mAsynTask;
    @BindView(R.id.mProgressBar1)
    ProgressBar mProgressBar;
    @BindView(R.id.fab1)
    FloatingActionButton fab;
    CardViewAdapter cardViewAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            getWindow().setEnterTransition(new Explode());
            getWindow().setExitTransition(new Explode());
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_video_1);
        initView();
        startScanTask();

        CardViewAdapter adapter = new CardViewAdapter(mChildList);

    }
    private void initView(){
        ButterKnife.bind(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);
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
                //mTitle.setText("local media list");
                recyclerView.setVisibility(View.VISIBLE);
                cardViewAdapter = new CardViewAdapter(mChildList);
//                cardViewAdapter.setRootView(activityListVideo);
                recyclerView.setAdapter(cardViewAdapter);
                cardViewAdapter.setOnItemClickListener(new CardViewAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view) {
//                        Toast.makeText(UploadCardView.this,position,Toast.LENGTH_LONG).show();
                        MediaBean bean = (MediaBean) view.getTag();
                        Log.d("bean after click",bean.toString());
                        Intent intent = new Intent(UploadCardView.this, PlayActivity.class);
                        intent.putExtra("media_url",bean.getUrl());
                        intent.putExtra("media_name",bean.getName());
                        intent.putExtra(UploadCardView.TRANSITION, true);
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                            Pair pair = new Pair<>(view, PlayActivity.IMG_TRANSITION);
                            ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                                    UploadCardView.this, pair);
                            ActivityCompat.startActivity(UploadCardView.this, intent, activityOptions.toBundle());
                        } else {
                            startActivity(intent);
                            UploadCardView.this.overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                        }
                    }
                });


            }
        }
    };

    @Override
    public void onItemClick(AdapterViewCompat<?> parent, View view, int position, long id) {
        MediaBean bean = (MediaBean) view.getTag();
        Intent intent = new Intent(UploadCardView.this, PlayActivity.class);
        intent.putExtra("media",bean);
        intent.putExtra(PlayActivity.TRANSITION, true);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Pair pair = new Pair<>(view, PlayActivity.IMG_TRANSITION);
            ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    UploadCardView.this, pair);
            ActivityCompat.startActivity(UploadCardView.this, intent, activityOptions.toBundle());
        } else {
            startActivity(intent);
            UploadCardView.this.overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
        }
    }

}
