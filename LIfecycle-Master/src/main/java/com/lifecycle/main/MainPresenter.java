package com.lifecycle.main;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.util.Log;

/**
 * Code of ZHANG/ 2018/10/13
 */
public class MainPresenter implements IPresenter {

    private static final String TAG = "MainPresenter";

    private Context mContext;

    public MainPresenter(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate(LifecycleOwner owner) {
        Log.i(TAG, "MainPresenter.onCreate: "+this.getClass().toString());
    }

    @Override
    public void onStart(LifecycleOwner owner) {
        Log.i(TAG, "MainPresenter.onStart: "+this.getClass().toString());
    }

    @Override
    public void onResume(LifecycleOwner owner) {
        Log.i(TAG, "MainPresenter.onResume: "+this.getClass().toString());
    }

    @Override
    public void onPause(LifecycleOwner owner) {
        Log.i(TAG, "MainPresenter.onPause: "+this.getClass().toString());
    }

    @Override
    public void onStop(LifecycleOwner owner) {
        Log.i(TAG, "MainPresenter.onStop: "+this.getClass().toString());
    }

    @Override
    public void onDestroy(LifecycleOwner owner) {
        Log.i(TAG, "MainPresenter.onDestroy: "+this.getClass().toString());
    }

    @Override
    public void onLifeCycleChanged(LifecycleOwner owner, Lifecycle.Event event) {
        Log.i(TAG, "onLifeCycleChanged: "+this.getClass().toString());
    }
}
