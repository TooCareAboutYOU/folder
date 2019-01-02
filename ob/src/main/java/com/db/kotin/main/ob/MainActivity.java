package com.db.kotin.main.ob;

import android.annotation.SuppressLint;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.db.kotin.main.ob.job.MySyncJob;
import com.db.kotin.main.ob.job.Operation;
import com.db.kotin.main.ob.observable.DateWatcher;
import com.db.kotin.main.ob.observable.IObservable;

public class MainActivity extends AppCompatActivity implements IObservable {

    private AppCompatEditText mEtJobId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEtJobId=findViewById(R.id.AcEt_JobId);
        Operation.init();
    }

    @Override
    public void update() {
        Toast.makeText(this, "更新数据", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        DateWatcher.getInstance().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DateWatcher.getInstance().unregister(this);
    }

    @SuppressLint("ServiceCast")
    public void ClickMethod(View view) {

//        DateWatcher.getInstance().notifyObservable();

        switch (view.getId()) {
            case R.id.btn_Start:{
                Operation.getInstance().startScheduleJob(MySyncJob.TAG);
                break;
            }
            case R.id.btn_Stop:{
                if (!TextUtils.isEmpty(mEtJobId.getText().toString().trim())) {
                    int jobId=Integer.parseInt(mEtJobId.getText().toString().trim());
                    Operation.getInstance().cancel(jobId);
                }else {
                    Toast.makeText(this, "JobId 不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.btn_Clear:{
                Operation.getInstance().cancelAll(false);
                break;
            }
        }

    }

}
