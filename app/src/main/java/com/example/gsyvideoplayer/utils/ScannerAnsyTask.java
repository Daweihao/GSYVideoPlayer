package com.example.gsyvideoplayer.utils;

import android.app.ListActivity;
import android.os.AsyncTask;

import com.example.gsyvideoplayer.MediaBean;

import java.util.ArrayList;
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
}
