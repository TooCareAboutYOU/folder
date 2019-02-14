package main.rxjava2super.main;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * https://www.jianshu.com/p/fa1828d70192 第5页
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.acBtn_Download).setOnClickListener(v -> initDownLoad());
        findViewById(R.id.acBtn_Average).setOnClickListener(v -> initAverage());
        findViewById(R.id.acBtn_Search).setOnClickListener(v -> initSearch());
        findViewById(R.id.acBtn_Retrofit).setOnClickListener(v ->
                ARouter.getInstance().build(ARouterPath.RX_RETROFIT).navigation()
        );
    }

    private void initDownLoad() {
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_download, null);
        AppCompatTextView tvProgress = view.findViewById(R.id.acTv_Progress);
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; i < 100; i++) {
                    if (i % 20 == 0) {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            if (!emitter.isDisposed()) {
                                emitter.onError(e);
                            }
                        }
                        emitter.onNext(i);
                    }
                }
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

        DisposableObserver<Integer> disposableObserver = new DisposableObserver<Integer>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onNext(Integer integer) {
                tvProgress.setText("Current Progress " + integer);
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onError(Throwable e) {
                tvProgress.setText("DownLoad Error: " + e.getLocalizedMessage());
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onComplete() {
                tvProgress.setText("DownLoad Complete");
            }
        };

        view.findViewById(R.id.acBtn_Start).setOnClickListener(v -> {
            observable.subscribe(disposableObserver);
            compositeDisposable.add(disposableObserver);
            ((AppCompatButton) view.findViewById(R.id.acBtn_Start)).setVisibility(View.GONE);
        });

        builder.setOnDismissListener(dialog -> {
            if (disposableObserver != null) {
                compositeDisposable.remove(disposableObserver);
                compositeDisposable.clear();
            }
        });
        builder.setView(view);
        builder.create();
        builder.show();
    }

    boolean isFlagAverage=false;
    private void initAverage() {
        if (isFlagAverage) {
            ((AppCompatButton)findViewById(R.id.acBtn_Average)).setText("计算一段时间内数据的平均值");
            AverageClass.getInstance().clear();
        }else {
            AverageClass.getInstance().init(findViewById(R.id.acBtn_Average));
        }
        isFlagAverage=!isFlagAverage;
    }

    @SuppressLint("InflateParams")
    private void initSearch() {
        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
        View view=LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_search,null);
        SearchClass.getInstance().init(view.findViewById(R.id.acEt_SearchInfo),view.findViewById(R.id.acTv_ShowInfo));
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                SearchClass.getInstance().clear();
            }
        });
        builder.setView(view);
        builder.create();
        builder.show();

    }

}
