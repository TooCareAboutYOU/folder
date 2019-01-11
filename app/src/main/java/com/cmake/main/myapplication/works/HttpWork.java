package com.cmake.main.myapplication.works;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.cmake.main.myapplication.FirImApplication;

import androidx.work.ListenableWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

/**
 * Code of ZHANG/ 2018/10/12
 */
public class HttpWork extends Worker {

    private static final String TAG = "HttpWork";

    public HttpWork(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String str=this.getInputData().getString("demo");
        Log.w(TAG, Thread.currentThread()+"\t\t传值: "+str);
        return Result.SUCCESS;
    }
}
