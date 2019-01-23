package com.utils.main;

import android.app.Application;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PhoneUtils;
import com.blankj.utilcode.util.Utils;


public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }

    private void loadLogcat(){
        LogUtils.Config config=LogUtils.getConfig(); //获取log配置
        config.setBorderSwitch(true); //设置Log总开关
        config.setConsoleSwitch(true); //设置 log 控制台开关
        config.setGlobalTag("MM-HelloWorld"); //设置 log 全局 tag
        config.setLogHeadSwitch(true); //设置 log 头部信息开关
        config.setLog2FileSwitch(true); //设置 log 文件开关
        config.setFileFilter(1); //设置 log 文件前缀
        config.setBorderSwitch(true); //设置 log 边框开关
        config.setConsoleFilter(1); //设置 log 控制台过滤器
        config.setStackDeep(1);  //设置 log 栈深度
    }

}
