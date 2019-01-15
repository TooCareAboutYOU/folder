package com.theme.main;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

@Route(path = "/service/CActivity")
public class CActivity extends AppCompatActivity {

    private static final String TAG = "CActivity";
    
    @Autowired()
    boolean isVa;

    @Autowired()
    String cashDeposit;

    @Autowired()
    String feeProcedurePay;

    @Autowired()
    String feeProcedureRepay;

    @Autowired()
    String repayAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c);
        ARouter.getInstance().inject(this);
    }

    public void ClickMethod(View view) {
//        startActivity(new Intent(this,DActivity.class));
        Log.i(TAG, "ClickMethod: "+isVa+"\t\t"+cashDeposit+"\t\t"+feeProcedurePay+"\t\t"+feeProcedureRepay+"\t\t"+repayAmount);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(MainActivity.TAG, "onDestroy: "+getClass().getCanonicalName());
    }
}
