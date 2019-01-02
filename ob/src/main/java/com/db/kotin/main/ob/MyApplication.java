package com.db.kotin.main.ob;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.widget.Toast;

import com.db.kotin.main.ob.job.MyJobCreator;
import com.db.kotin.main.ob.job.Operation;
import com.evernote.android.job.JobManager;

/**
 * Code of ZHANG/ 2018/10/18
 */
public class MyApplication extends Application {

    private static MyApplication sApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication=this;
        Operation.init();
    }

    public static synchronized MyApplication getApplication(){ return sApplication;}


    public void showToast(@StringRes int resMsg){
        Toast.makeText(this, resMsg, Toast.LENGTH_SHORT).show();
    }

    public void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
