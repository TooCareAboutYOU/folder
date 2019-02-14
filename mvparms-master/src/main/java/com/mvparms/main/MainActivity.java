package com.mvparms.main;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.view.ViewStub;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements IActivity{

    @Override
    public int initView() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        Toast.makeText(this, "初始化数据", Toast.LENGTH_SHORT).show();
    }


    public void ClickListeners(View view) {
        switch (view.getId()) {
            case R.id.acTv_ViewStub:{
                loadView();
                break;
            }
        }
    }

    private void loadView(){
        ViewStub viewStub=findViewById(R.id.view_stub);
        if (viewStub != null) {
            View view=viewStub.inflate();
            view.findViewById(R.id.acBtn_one).setOnClickListener(v -> finish());
            AppCompatEditText acEtTwo=view.findViewById(R.id.acEt_two);
            Toast.makeText(this, "加载ViewStub", Toast.LENGTH_SHORT).show();
        }
    }

}
