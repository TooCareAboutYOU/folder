package com.rx.main;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends ListActivity {

    private static final String TAG = "MMainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        //1.数据源
        String[] data = {
                "Create线程切换",
                "Map操作符",
                "Contact操作符",
                "FlatMap操作符",
                "Zip操作符",
                "Interval操作符",
                "Distinct操作符",
                "Filter操作符",
                "Buffer操作符",
                "Timer操作符",
                "Skip操作符",
                "Take操作符",
                "Single操作符",
                "DeBounce操作符",
                "Defer操作符",
                "Last操作符",
                "Marge操作符",
                "Reduce操作符",
                "Scan操作符",
                "Window操作符",
                "RxBinding监听",
                "ConstraintLayout弹性动画"
        };

        //2.适配器
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, data);
        setListAdapter(arrayAdapter);
        //设置ListView的选择行为：复选
        getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        switch (position) {
            case 0:CreateThread();break;
            case 1:MapOperator();break;
            case 2:ContactOperator();break;
            case 3:FlatMapOperator();break;
            case 4:ZipOperator();break;
            case 5:IntervalOperator();break;
            case 6:DistinctOperator();break;
            case 7:FilterOperator();break;
            case 8:BufferOperator();break;
            case 9:TimerOperator();break;
            case 10:SkipOperator();break;
            case 11:TakeOperator();break;
            case 12:SingleOperator();break;
            case 13:DeBounceOperator();break;
            case 14:DeferOperator();break;
            case 15:LastOperator();break;
            case 16:MargeOperator();break;
            case 17:ReduceOperator();break;
            case 18:ScanOperator();break;
            case 19:WindowOperator();break;
            case 20:startActivity(new Intent(MainActivity.this,DataBindingActivity.class));break;
            case 21:
                startActivity(new Intent(MainActivity.this,AnimationActivity.class));
                break;
        }

    }

    public void CreateThread() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) {
                Log.i(TAG, "subscribe: "+Thread.currentThread());
                Random random=new Random();
                emitter.onNext(random.nextInt(100));
                emitter.onComplete();
            }
        })
                .subscribeOn(Schedulers.newThread())
//                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) {
                        Log.i(TAG, "accept: "+integer+"\t\t"+Thread.currentThread());
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "onSubscribe: "+d.isDisposed()+"\t\t"+Thread.currentThread());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.i(TAG, "onNext: "+integer+"\t\t"+Thread.currentThread());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: "+e.getMessage()+"\t\t"+Thread.currentThread());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete: "+"\t\t"+Thread.currentThread());
                    }
                });
    }

    @SuppressLint("CheckResult")
    public void MapOperator() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) {
                Log.i(TAG, "subscribe: "+"\t\t"+Thread.currentThread());
                emitter.onNext("-100");
                emitter.onComplete();
            }
        }).map(new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                return Integer.valueOf(s);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) {
                        Log.i(TAG, "doOnNext -> accept: "+integer+"\t\t"+Thread.currentThread());
                    }
                }).subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) {
                        Log.i(TAG, "accept -> Integer: "+integer+"\t\t"+Thread.currentThread());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Log.e(TAG, "accept -> Throwable: "+throwable+"\t\t"+Thread.currentThread());
                    }
                });
    }

    @SuppressLint("CheckResult")
    public void ContactOperator() {
        Observable<String> cache=Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) {
                emitter.onNext("I am from local!");
                emitter.onComplete();
            }
        });

        Observable<String> network=Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) {
                emitter.onNext("I am from network!\n");
                emitter.onComplete();
            }
        });

        Observable.concat(cache,network)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) {
                        Log.i(TAG, "合并数据: "+s);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Log.i(TAG, "accept: "+throwable);
                    }
                });

    }

    @SuppressLint("CheckResult")
    public void FlatMapOperator() {
        //fflatMap 操作符可以将一个发射数据的 Observable 变换为多个 Observables ，
        // 然后将它们发射的数据合并后放到一个单独的 Observable
    }

    @SuppressLint("CheckResult")
    public void ZipOperator() {
        //在一个页面显示的数据来源于多个接口，这时候我们的 zip 操作符为我们排忧解难。
        //zip 操作符可以将多个 Observable 的数据结合为一个数据源再发射出去。
        Observable<String> observable1=Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) {
                emitter.onNext("one to ");
                emitter.onComplete();
            }
        });
        Observable<String> observable2=Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) {
                emitter.onNext("two");
                emitter.onComplete();
            }
        });
        Observable.zip(observable1, observable2, new BiFunction<String, String, String>() {
            @Override
            public String apply(String s, String s2) {
                return s.concat(s2);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) {
                        Log.i(TAG, "accept: "+s);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Log.i(TAG, "accept: "+throwable);
                    }
                });

    }

    private Disposable mDisposable;
    @SuppressLint("CheckResult")
    public void IntervalOperator() {
        mDisposable=Observable.interval(1, TimeUnit.SECONDS)
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) {
                        Log.i(TAG, "doOnNext: "+aLong);
                    }
                })
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) {
                        Log.i(TAG, "Long : "+aLong);
                        if (aLong==5L) {
                            Log.w(TAG, "结束!!!");
                            mDisposable.dispose();
                        }
                    }
                });

    }

    @SuppressLint("CheckResult")
    public void DistinctOperator() {
        Integer[] item={1,2,3,2,1,5,3,7};
        Flowable.fromArray(item)
                .distinct()
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) {
                        Log.i(TAG, "accept: "+integer);
                    }
                });
    }

    @SuppressLint("CheckResult")
    private void FilterOperator() {
        Observable.just(1,5,24,164,232,3415).filter(new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) {
                return integer*5>100;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {
                Log.i(TAG, "accept: "+integer);
            }
        });
    }

    @SuppressLint("CheckResult")
    private void BufferOperator(){
        //将 Observable 中的数据按 skip (步长) 分成最大不超过 count 的 buffer ，
        // 然后生成一个  Observable
        Observable.just(1,2,3,4,5,6,7,8,9)
                .buffer(3,3)
                .subscribe(new Consumer<List<Integer>>() {
                    @Override
                    public void accept(List<Integer> integers) {
                        Log.i(TAG, "长度: "+ integers.size()+"\n开始 》》》》》》》》》》");
                        for (Integer integer : integers) {
                            Log.i(TAG, "accept: "+integer);
                        }
                        Log.i(TAG, "结束《《《《《《《《《《");
                    }
                });

    }

    @SuppressLint("CheckResult")
    private void TimerOperator(){
        // 相当于一个定时任务，默认在新线程。只执行一次
        Observable.timer(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) {
                        Log.i(TAG, "打印: "+aLong);
                    }
                });
    }

    @SuppressLint("CheckResult")
    private void SkipOperator(){
        //接受一个 long 型参数 count ，代表跳过 count 个数目开始接收。
        Observable.just(1,2,3,4,5,6)
                .skip(2)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) {
                        Log.i(TAG, "accept: "+integer);
                    }
                });
    }

    @SuppressLint("CheckResult")
    private void TakeOperator(){
        // 接受一个 long 型参数 count ，代表至多接收 count 个数据。
        Observable.just(1,2,3,4,5,6)
                .take(2)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) {
                        Log.i(TAG, "accept: "+integer);
                    }
                });
    }

    @SuppressLint("CheckResult")
    private void SingleOperator(){
        Single.just(10).subscribe(new SingleObserver<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i(TAG, "onSubscribe: "+d.isDisposed());
            }

            @Override
            public void onSuccess(Integer integer) {
                Log.i(TAG, "onSuccess: "+integer);
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError: "+e.getMessage());
            }
        });
    }

    @SuppressLint("CheckResult")
    private void DeBounceOperator(){
        // 去除发送频率过快的项
        // 去除发送间隔时间小于 500 毫秒的发射事件
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("这个是第 1 条数据");
                Thread.sleep(400);
                emitter.onNext("这个是第 2 条数据");
                Thread.sleep(505);
                emitter.onNext("这个是第 3 条数据");
                Thread.sleep(100);
                emitter.onNext("这个是第 4 条数据");
                Thread.sleep(600);
                emitter.onNext("这个是第 5 条数据");
                Thread.sleep(50);
                emitter.onComplete();
            }
        }).debounce(500,TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) {
                        Log.i(TAG, "打印: "+s);
                    }
                });
    }

    @SuppressLint("CheckResult")
    private void DeferOperator(){
        // 每次订阅都会创建一个新的 Observable，并且如果没有被订阅，就不会产生新的 Observable。
        Observable.defer(new Callable<ObservableSource<Integer>>() {
            @Override
            public ObservableSource<Integer> call() throws Exception {
                return Observable.just(1,2,3);
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i(TAG, "onSubscribe: "+d.isDisposed());
            }

            @Override
            public void onNext(Integer integer) {
                Log.i(TAG, "onNext: "+integer);
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError: "+e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete: ");
            }
        });
    }

    @SuppressLint("CheckResult")
    private void LastOperator(){
        //仅取出可观察到的最后一个值，或者是满足某些条件的最后一项。
        Observable.just(12,34,56,78,90)
                .last(2)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i(TAG, "accept: "+integer);
                    }
                });
    }

    @SuppressLint("CheckResult")
    private void MargeOperator(){
        Observable.merge(Observable.just(1,2,3),Observable.just(4,5,6))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i(TAG, "accept: "+integer);
                    }
                });
    }

    @SuppressLint("CheckResult")
    private void ReduceOperator(){
        //累加
        Observable.just(1,2,3,4,5)
                .reduce(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer, Integer integer2) throws Exception {
                        return integer+integer2;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i(TAG, "accept: "+integer);
                    }
                });
    }

    @SuppressLint("CheckResult")
    private void ScanOperator(){
        //scan 操作符作用和上面的 reduce 一致，唯一区别是 reduce 是个只追求结果，
        // 而 scan 会把每一个步骤都输出。
        Observable.just(1,2,3,4,5).scan(new BiFunction<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer integer, Integer integer2) throws Exception {
                return integer+integer2;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.i(TAG, "accept: "+integer);
            }
        });
    }

    @SuppressLint("CheckResult")
    private void WindowOperator(){
        //按照实际划分窗口，将数据发送给不同的 Observable
        Observable.interval(1,TimeUnit.SECONDS)
                .take(15)
                .window(3,TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Observable<Long>>() {
                    @Override
                    public void accept(Observable<Long> longObservable) throws Exception {
                        longObservable.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<Long>() {
                                    @Override
                                    public void accept(Long aLong) throws Exception {
                                        Log.i(TAG, "accept: "+aLong);
                                    }
                                });
                    }
                });
    }

}
