package com.lib.folder.main;

import android.content.Context;
import android.widget.Toast;

/**
 * Code of ZHANG/ 2018/10/11
 */
public class TestToast {

    public static void toastUtil(Context context){
        Toast.makeText(context.getApplicationContext(),"恭喜！发布成功了",Toast.LENGTH_SHORT).show();
    }

}
