package com.cmake.main.myapplication;

import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;


import com.cmake.main.myapplication.works.HttpWork;
import com.reactivenet.main.ReactiveNetHelper;

import java.util.concurrent.TimeUnit;

import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import cn.hugeterry.updatefun.UpdateFunGO;
import cn.hugeterry.updatefun.config.UpdateKey;

public class MainActivity extends AppCompatActivity {

    public static final String key="keys";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UpdateKey.API_TOKEN = "ec7ac78f270d90936de45ffadcbfe5a1";
        UpdateKey.APP_ID = "com.cmake.main.myapplication";
//下载方式:
//UpdateKey.DialogOrNotification=UpdateKey.WITH_DIALOG;通过Dialog来进行下载
//UpdateKey.DialogOrNotification=UpdateKey.WITH_NOTIFITION;通过通知栏来进行下载(默认)
        UpdateFunGO.init(this);
    }

    public void ClickMethod(View view) {
        switch (view.getId()) {
            case R.id.btn_CheckNetWork:{
                ReactiveNetHelper.connectActivity(this);
                break;
            }
            case R.id.btn_Touch:{
                UpdateFunGO.manualStart(this);
                break;
            }
            case R.id.btn_DownLoad:{
                UpdateKey.DialogOrNotification=UpdateKey.WITH_DIALOG; //通过Dialog来进行下载
                UpdateFunGO.showDownloadView(this);
                break;
            }

            case R.id.btn_Test:
                //约束条件
                if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.M) {
                    Toast.makeText(this, "触发了", Toast.LENGTH_SHORT).show();
                    Constraints constraints=new Constraints.Builder()
                            .setRequiredNetworkType(NetworkType.CONNECTED)
                            .setRequiresBatteryNotLow(true) //不在电量不足时执行
                            .setRequiresCharging(true) // 在充电时执行
                            .setRequiresStorageNotLow(true) //不在存储容量不足时执行
                            .setRequiresDeviceIdle(true)  //在待机状态执行
                            .build();
                    //传入参数
                    Data data=new Data.Builder().putString("demo","Hello World").build();
                    //构造work
                    OneTimeWorkRequest httpWork=new OneTimeWorkRequest
                            .Builder(HttpWork.class)
                            .setConstraints(constraints)
                            .setInputData(data)
                            .build();

                    //任务链
                    //放入执行队列
                    WorkManager.getInstance().beginWith(httpWork).enqueue();

                    //周期任务
                    PeriodicWorkRequest periodicWorkRequest=new PeriodicWorkRequest
                            .Builder(HttpWork.class,15, TimeUnit.SECONDS)
                            .setConstraints(constraints)
                            .setInputData(data)
                            .build();
                break;
            }


            case R.id.btn_Add:{
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(key, "哈哈");
                editor.apply();
                Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.btn_Remove:{
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(key);
                editor.apply();
                Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.btn_Check:{
                if (!TextUtils.isEmpty(key)) {
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                    String msg=sharedPreferences.getString(key, "");
                    Toast.makeText(this,"是否存在key："+sharedPreferences.contains(key)+"\t\t"+ msg, Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        UpdateFunGO.onResume(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        UpdateFunGO.onStop(this);

    }
}
