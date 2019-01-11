package com.lifecycle.main;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Code of ZHANG/ 2018/10/13
 */
public class ProxyMainActivity implements IPresenter {

    private static final String TAG = "onLifeCycleChanged";

    private Context mContext;

    public ProxyMainActivity(Context context) {
        this.mContext = context;
    }

    @Override
    public void onCreate(LifecycleOwner owner) {
        Log.d(TAG, ".onCreate: " + owner.getLifecycle().getCurrentState().name());
    }

    @Override
    public void onStart(LifecycleOwner owner) {
        Log.d(TAG, ".onStart: " + owner.getLifecycle().getCurrentState().name());
    }

    @Override
    public void onResume(LifecycleOwner owner) {
        Log.d(TAG, ".onResume: " + owner.getLifecycle().getCurrentState().name());
    }

    @Override
    public void onPause(LifecycleOwner owner) {
        Log.d(TAG, ".onPause: " + owner.getLifecycle().getCurrentState().name());
    }

    @Override
    public void onStop(LifecycleOwner owner) {
        Log.d(TAG, ".onStop: " + owner.getLifecycle().getCurrentState().name());
    }

    @Override
    public void onDestroy(LifecycleOwner owner) {
        Log.d(TAG, ".onDestroy: " + owner.getLifecycle().getCurrentState().name());
    }

    @Override
    public void onLifeCycleChanged(LifecycleOwner owner, Lifecycle.Event event) {
        Log.i(TAG, "当前状态: " + owner.getLifecycle().getCurrentState());
        Log.e(TAG, "对比Create: " + event.compareTo(Lifecycle.Event.ON_CREATE));
        Log.e(TAG, "对比onStart: " + event.compareTo(Lifecycle.Event.ON_START));
        Log.e(TAG, "对比onResume: " + event.compareTo(Lifecycle.Event.ON_RESUME));
        Log.e(TAG, "对比onPause: " + event.compareTo(Lifecycle.Event.ON_PAUSE));
        Log.e(TAG, "对比onStop: " + event.compareTo(Lifecycle.Event.ON_STOP));
        Log.e(TAG, "对比onDestroy: " + event.compareTo(Lifecycle.Event.ON_DESTROY));
        Log.w(TAG, "---------------------------------------------------------");
    }

    @Override
    public void setOnClickListener(Activity activity, View... view) {
        for (View subView : view) {
            subView.findViewById(subView.getId()).setOnClickListener(v -> {
                switch (subView.getId()) {
                    case R.id.btn_Click: {
                        Toast.makeText(mContext, "触发 btn_Click", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.btn_Click2: {
                        Toast.makeText(mContext, "触发 btn_Click2", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.btn_Click3:{
                        activity.finish();
                        break;
                    }
                }
            });
        }

    }
}
