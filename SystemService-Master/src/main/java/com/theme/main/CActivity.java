package com.theme.main;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class CActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c);
    }

    public void ClickMethod(View view) {
        startActivity(new Intent(this,DActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(MainActivity.TAG, "onDestroy: "+getClass().getCanonicalName());
    }
}
