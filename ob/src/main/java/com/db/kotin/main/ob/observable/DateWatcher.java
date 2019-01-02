package com.db.kotin.main.ob.observable;

import android.annotation.SuppressLint;

/**
 * Code of ZHANG/ 2018/10/17
 */
public class DateWatcher {
    private DateWatcher(){}
    @SuppressLint("StaticFieldLeak")
    public static DateWatcher getInstance(){  return DateWatcherHolder.instance;  }
    private static class DateWatcherHolder{
        @SuppressLint("StaticFieldLeak")
        private static final DateWatcher instance=new DateWatcher();
    }

    // 一般来说，这应该是一个集合，但我这里只监听可能发生内存泄露的Activity，所以只用了一个observable
    private IObservable observable;

    /**
     * 添加被观察者
     */
    public void register(IObservable observable){
        this.observable=observable;
    }

    /**
     * 接触监听
     */
    public void unregister(IObservable observable){
        this.observable=null;
    }

    /**
     * 通知被观察者更新数据
     */
    public void notifyObservable(){
        if (this.observable !=null) {
            this.observable.update();
        }
    }

}
