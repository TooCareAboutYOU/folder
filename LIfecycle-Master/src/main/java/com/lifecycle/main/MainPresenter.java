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
        Log.i(TAG, ".onCreate: ");
    }

    @Override
    public void onStart(LifecycleOwner owner) {
        Log.i(TAG, ".onStart: ");
    }

    @Override
    public void onResume(LifecycleOwner owner) {
        Log.i(TAG, ".onResume: ");
    }

    @Override
    public void onPause(LifecycleOwner owner) {
        Log.i(TAG, ".onPause: ");
    }

    @Override
    public void onStop(LifecycleOwner owner) {
        Log.i(TAG, ".onStop: ");
    }

    @Override
    public void onDestroy(LifecycleOwner owner) {
        Log.i(TAG, ".onDestroy: ");
    }

    @Override
    public void onLifeCycleChanged(LifecycleOwner owner, Lifecycle.Event event) {
        Log.i(TAG, "onLifeCycleChanged: ");
    }
}
