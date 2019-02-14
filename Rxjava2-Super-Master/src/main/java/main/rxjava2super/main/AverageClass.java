package main.rxjava2super.main;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class AverageClass {

    private static final String TAG = "AverageClass";

    private PublishSubject<Double> publishSubject;
    private CompositeDisposable compositeDisposable;
    private SourceHandler mSourceHandler;

    private AverageClass() {
    }

    @SuppressLint("StaticFieldLeak")
    public static AverageClass getInstance() {
        return AverageClassHolder.instance;
    }

    private static class AverageClassHolder {
        @SuppressLint("StaticFieldLeak")
        private static final AverageClass instance = new AverageClass();
    }

    public void init(AppCompatButton tvView) {
        publishSubject = PublishSubject.create();
        compositeDisposable = new CompositeDisposable();
        mSourceHandler = new SourceHandler();

        DisposableObserver<List<Double>> disposableObserver = new DisposableObserver<List<Double>>() {
            @Override
            public void onNext(List<Double> doubles) {
                double result = 0;
                if (doubles.size() > 0) {
                    for (Double aDouble : doubles) {
                        result += aDouble;
                    }
                    result = result / doubles.size();
                }
                Log.d(TAG, "更新平均温度：" + result);
                tvView.setText("过去3秒收到了" + doubles.size() + "个数据， 平均温度为：" + result);
            }

            @Override
            public void onError(Throwable e) { }

            @Override
            public void onComplete() { }
        };
        publishSubject.buffer(3000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
        mSourceHandler.sendEmptyMessage(0);
    }

    private void updateTemperature(double temperature) {
        Log.d(TAG, "温度测量结果：" + temperature);
        publishSubject.onNext(temperature);
    }


    @SuppressLint("HandlerLeak")
    private class SourceHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            double temperature = Math.random() * 25 + 5;
            updateTemperature(temperature);
            sendEmptyMessageDelayed(0, 250 + (long) (250 * Math.random()));
        }
    }

    public void clear() {
        Log.d(TAG, "clear: Reset");
        mSourceHandler.removeCallbacksAndMessages(null);
        compositeDisposable.clear();
    }

}
