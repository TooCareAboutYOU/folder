package com.reactivenet.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

import com.github.pwittchen.reactivenetwork.library.rx2.Connectivity;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ReactiveNetHelper {

    @SuppressLint("CheckResult")
    public static void connectActivity(Context context){
//       Observable observable= ReactiveNetwork.observeNetworkConnectivity(context);
        compse(ReactiveNetwork.observeNetworkConnectivity(context))
                .subscribe(new Consumer<Connectivity>() {
                    @Override
                    public void accept(Connectivity connectivity) throws Exception {
                        switch (connectivity.state()) {
                            case UNKNOWN:{
                                Toast.makeText(context, "未知网络 UNKNOWN", Toast.LENGTH_SHORT).show();
                                break;
                            }
                            case CONNECTED:{
                                Toast.makeText(context, "网络连接 CONNECTED", Toast.LENGTH_SHORT).show();
                                break;
                            }
                            case SUSPENDED:{
                                Toast.makeText(context, "网络不可用 SUSPENDED", Toast.LENGTH_SHORT).show();
                                break;
                            }
                            case CONNECTING:{
                                Toast.makeText(context, "网络连接中 CONNECTING", Toast.LENGTH_SHORT).show();
                                break;
                            }
                            case DISCONNECTED:{
                                Toast.makeText(context, "网络已断开 DISCONNECTED", Toast.LENGTH_SHORT).show();
                                break;
                            }
                            case DISCONNECTING:{
                                Toast.makeText(context, "正在断开连接 DISCONNECTING", Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }
                    }
                });
    }


    public static Observable compse(Observable observable){

        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }

}
