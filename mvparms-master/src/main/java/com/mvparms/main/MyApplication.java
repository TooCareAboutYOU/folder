package com.mvparms.main;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


public class MyApplication extends Application {


    private static final String TAG = "MMyApplication";
    
    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {
                Log.i(TAG, "onActivityCreated: ");
                if (activity instanceof IActivity) {
                    activity.setContentView(((IActivity) activity).initView());
                    ((IActivity) activity).initData();
                }
            }

            @Override
            public void onActivityStarted(Activity activity) {
                Log.i(TAG, "onActivityStarted: ");
            }

            @Override
            public void onActivityResumed(Activity activity) {
                Log.i(TAG, "onActivityResumed: ");
            }

            @Override
            public void onActivityPaused(Activity activity) {
                Log.i(TAG, "onActivityPaused: ");
            }

            @Override
            public void onActivityStopped(Activity activity) {
                Log.i(TAG, "onActivityStopped: ");
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
                Log.i(TAG, "onActivitySaveInstanceState: ");
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                Log.i(TAG, "onActivityDestroyed: ");
                Toast.makeText(activity, "销毁："+activity.getComponentName().getShortClassName(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
