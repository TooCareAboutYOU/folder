package com.db.kotin.main.ob.job;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Toast;

import com.db.kotin.main.ob.MyApplication;
import com.db.kotin.main.ob.observable.DateWatcher;
import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Code of ZHANG/ 2018/10/18
 */
public class Operation {

    @SuppressLint("StaticFieldLeak")
    private static CountDownTimer countDownTimer = null;
    private static HashMap<Integer, CountDownTimer> sCDTimerHash = null;

    @SuppressLint("StaticFieldLeak")
    private static JobManager mJobManager = null;

    private Operation() {
    }

    @SuppressLint("StaticFieldLeak")
    public static Operation getInstance() {
        return OperationHolder.instance;
    }

    private static class OperationHolder {
        @SuppressLint("StaticFieldLeak")
        private static final Operation instance = new Operation();
    }

    @SuppressLint("UseSparseArrays")
    public static void init() {
        Log.i(MySyncJob.TAG, "Operation init: ");
        JobManager.create(MyApplication.getApplication()).addJobCreator(new MyJobCreator());
        mJobManager = JobManager.instance();
        sCDTimerHash = new HashMap<>();
    }

    private void Judge(){
        if (mJobManager == null) {
            throw new NullPointerException("the Job is not initialize");
        }
    }


    // 设置执行此任务需满足的条件、间隔时间和关机重启后是否继续执行
    public void startScheduleJob(String tag) {
        Judge();
        int jobId = new JobRequest.Builder(tag)
                .setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
                .setRequiresDeviceIdle(true)
                .setRequiresCharging(true)
                .setPeriodic(JobRequest.MIN_INTERVAL)
                .setPersisted(true)
                .setRequirementsEnforced(true)  //设置只有此任务的执行条件被满足时，才执行此任务
                .build()
                .schedule();

        countDownTimer = new CountDownTimer(15 * 1000, 1000) { //  *60*
            @Override
            public void onTick(long millisUntilFinished) {
                Log.i(MySyncJob.TAG, "onTick: " + millisUntilFinished / 1000 + "----->>>>" + jobId);
            }

            @Override
            public void onFinish() {
                DateWatcher.getInstance().notifyObservable();
                remove(jobId);
            }
        }.start();
        sCDTimerHash.put(jobId, countDownTimer);

    }

    public void cancel(int jobId) {
        Judge();
        remove(jobId);
    }

    public void cancelAll(boolean isExit) {
        Judge();
        if (mJobManager != null) {
            Log.i(MySyncJob.TAG, "Operation cancel all");
            mJobManager.cancelAll();
            if (isExit) {
                mJobManager = null;
            }
            clearTimers();
        }
    }

    private void remove(int jobId) {
        if (mJobManager != null) {
            if (sCDTimerHash != null && sCDTimerHash.size() > 0 && sCDTimerHash.containsKey(jobId)) {
                boolean cancelJobId=mJobManager.cancel(jobId);
                sCDTimerHash.get(jobId).cancel();
                sCDTimerHash.remove(jobId);
                Log.d(MySyncJob.TAG, "移除 jobId=" + jobId +"\t\t状态："+cancelJobId + "\t\t" + "取消倒计时");
            }else {
                MyApplication.getApplication().showToast("任务不存在");
            }
        }
    }

    private static void clearTimers() {
        if (sCDTimerHash != null && sCDTimerHash.size() > 0) {
            for (Map.Entry<Integer, CountDownTimer> entry : sCDTimerHash.entrySet()) {
                sCDTimerHash.get(entry.getKey()).cancel();
                sCDTimerHash.remove(entry.getKey());
            }
        }
    }
}
