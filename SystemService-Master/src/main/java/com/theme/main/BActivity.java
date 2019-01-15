package com.theme.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

@Route(path = "/service/bActivity")
public class BActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
        ARouter.getInstance().inject(this);
    }

    public void ClickMethod(View view) {
        Toast.makeText(this, "点击了", Toast.LENGTH_SHORT).show();
        ARouter.getInstance().build("/service/CActivity")
                .withBoolean("isVa",true)
                .withString("cashDeposit","2500.00")
                .withString("feeProcedurePay","140.00")
                .withString("feeProcedureRepay","25.20")
                .withString("repayAmount","2334.80")
                .navigation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(MainActivity.TAG, "onDestroy: "+getClass().getCanonicalName());
    }
}
