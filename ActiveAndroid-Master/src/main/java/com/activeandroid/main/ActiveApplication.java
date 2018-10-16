package com.activeandroid.main;

import android.app.Application;
import com.activeandroid.ActiveAndroid;

/**
 * Code of ZHANG/ 2018/10/15
 */
public class ActiveApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this,true);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
    }
}
