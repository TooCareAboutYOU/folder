package com.cmake.main.myapplication.works;

import android.content.Context;
import android.support.annotation.NonNull;

import androidx.work.Data;
import androidx.work.ListenableWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

/**
 * Code of ZHANG/ 2018/10/12
 */
public class InputOutputWorker extends Worker {

    private static final String TAG = "InputOutputWorker";

    public InputOutputWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            //模拟耗时任务
            Thread.sleep(3000);
            Data inputData=getInputData();
            //获取输入的参数 ，并给outputData
            Data outputData=new Data.Builder().putString("key_name",inputData.getString("key_name")).build();
            setOutputData(outputData);
        }catch (Exception e){
            e.printStackTrace();
        }
        return Result.SUCCESS;
    }
}
