package com.lib.folder.main;

import android.content.Context;
import android.widget.Toast;

/**
 * 用于第三方 bintray 应用内更新jar包
 */
public class TestToast {

    public static void toastUtil(Context context){
        Toast.makeText(context.getApplicationContext(),"恭喜！发布成功了",Toast.LENGTH_SHORT).show();
    }

}
