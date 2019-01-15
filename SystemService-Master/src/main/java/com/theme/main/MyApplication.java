package com.theme.main;

import android.app.ActivityManager;
import android.app.Application;
import android.os.Build;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * Code of ZHANG/ 2018/10/17
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            ARouter.openDebug();
            ARouter.openLog();
        }

        ARouter.init(this);
    }
}
