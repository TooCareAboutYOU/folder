package com.lifecycle.main;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ProxyMainActivity mIPresenter;

    private static final String TAG = "onLifeCycleChanged";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mIPresenter=new ProxyMainActivity(this);
        mIPresenter.setOnClickListener(this,findViewById(R.id.btn_Click),findViewById(R.id.btn_Click2),findViewById(R.id.btn_Click3));
        getLifecycle().addObserver(mIPresenter);
        Log.i(TAG, "onCreate: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getLifecycle().removeObserver(mIPresenter);
        Log.i(TAG, "onDestroy: ");
    }
}
