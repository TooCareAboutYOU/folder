package com.litepal.main;

import org.litepal.Operator;

/**
 * Code of ZHANG/ 2018/10/15
 */
public class LitePalApplication extends org.litepal.LitePalApplication{


    @Override
    public void onCreate() {
        super.onCreate();
        Operator.initialize(this);
    }
}
