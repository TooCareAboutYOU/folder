package com.cmake.main.myapplication;

import android.app.Application;
import android.content.Context;

/**
 * Code of ZHANG/ 2018/10/12
 */
public class FirImApplication extends Application {

    private static FirImApplication sImApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        sImApplication=this;
    }

    public static synchronized FirImApplication getInstance(){ return sImApplication; }



}
