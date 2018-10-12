package com.cmake.main.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.lib.folder.main.TestToast;

import cn.hugeterry.updatefun.UpdateFunGO;
import cn.hugeterry.updatefun.config.UpdateKey;

public class MainActivity extends AppCompatActivity {

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
            case R.id.btn_Touch:{
                UpdateFunGO.manualStart(this);
                break;
            }
            case R.id.btn_DownLoad:{
                UpdateKey.DialogOrNotification=UpdateKey.WITH_DIALOG; //通过Dialog来进行下载
                UpdateFunGO.showDownloadView(this);
                break;
            }
            case R.id.btn_Test:{
                Toast.makeText(this, "Test Demo 二", Toast.LENGTH_SHORT).show();
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
