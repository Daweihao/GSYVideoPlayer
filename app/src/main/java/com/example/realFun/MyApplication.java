package com.example.realFun;

import android.app.Application;
import android.content.Context;

/**
 * Created by hao on 26/6/17.
 */

public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate(){
        super.onCreate();
        context = getApplicationContext();
        GSYApplication.initialize(context);
    }
    public static Context getContext(){
        return context;
    }
}
