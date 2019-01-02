package com.reactivenet.main;

import android.annotation.SuppressLint;
import android.content.Context;
import com.github.pwittchen.reactivenetwork.library.rx2.Connectivity;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ReactiveNetHelper {

    @SuppressLint("CheckResult")
    public static void connectActivity(Context context){
        ReactiveNetwork
                .observeNetworkConnectivity(context)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Connectivity>() {
                    @Override
                    public void accept(Connectivity connectivity) throws Exception {
                        switch (connectivity.state()) {

                        }
                    }
                });
    }

}
