package com.rx.main;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxAdapterView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RxBindingActivity extends AppCompatActivity {

    private static final String TAG = "RxBindingActivity";
    
    private AppCompatEditText mAcEdAccount,mAcEdPassword;
    private AppCompatButton mAcBtnLogin,mAcBtnSendClickEvent,mAcBtnCountDown,mAcBtnJump;
    private ListView mListView;

    private static int sizeLength = 5;
    private ArrayList<String> mStringArrayList = new ArrayList<>();
    private Observable<Object> tObservable = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_binding);
        mAcEdAccount=findViewById(R.id.Ac_EdAccount);
        mAcEdPassword=findViewById(R.id.Ac_EdPassword);
        mAcBtnLogin=findViewById(R.id.Ac_BtnLogin);
        mAcBtnSendClickEvent=findViewById(R.id.Ac_BtnSendClickEvent);
        mAcBtnCountDown=findViewById(R.id.Ac_BtnCountDown);
        findViewById(R.id.Ac_BtnJump).setOnClickListener(v -> startActivity(new Intent(this,DataBindingActivity.class)));
        for (int i = 0; i < sizeLength; i++) {
            mStringArrayList.add("第" + i + "条数据");
        }
        mListView.setAdapter(new MyAdapter());
        loadRx();
    }

    @SuppressLint("CheckResult")
    private void loadRx() {
//        TextChanges();

        LoginMethod();

        Interval();

        RxAdapterView.itemClicks(mListView).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {

                Observable<String> observable=Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                        emitter.onNext("发送数据:"+mStringArrayList.get(integer));
                        emitter.onComplete();
                    }
                });


                Observer<String> observer=new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "onSubscribe: "+d.isDisposed());
                    }

                    @Override
                    public void onNext(String s) {
                        Observable<String> observable1=Observable.just(s);
                        observable1.subscribe(new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {
                                Log.i(TAG, "accept: "+s+"\t\t"+System.currentTimeMillis());
                            }
                        });

                        ArrayList<String> arrayList=new ArrayList<String>(){{
                            add("数据一");add("数据二");add("数据三");add("数据四");
                        }};
                        Observable.fromIterable(arrayList).subscribe(new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {
                                Log.i(TAG, "打印表单: "+s);
                            }
                        });
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: "+e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete: ");
                    }
                };

                observable.subscribe(observer);
            }
        });
    }

    @SuppressLint("CheckResult")
    private void Interval() {
        tObservable = RxView.clicks(mAcBtnCountDown)
                .throttleFirst(sizeLength, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Toast.makeText(RxBindingActivity.this, o.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

        tObservable.subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                Observable.interval(0, 1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                        .map(new Function<Long, Integer>() {
                            @Override
                            public Integer apply(Long aLong) throws Exception {
                                return sizeLength - aLong.intValue();
                            }
                        })
                        .take(sizeLength)
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Integer>() {
                            @Override
                            public void accept(Integer integer) throws Exception {
                                Toast.makeText(RxBindingActivity.this, "倒计时:" + integer, Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    @SuppressLint("CheckResult")
    private void TextChanges() {
        RxTextView.textChanges(mAcEdAccount).subscribe(new Consumer<CharSequence>() {
            @Override
            public void accept(CharSequence charSequence) throws Exception {
                Toast.makeText(RxBindingActivity.this, charSequence, Toast.LENGTH_SHORT).show();
            }
        });
        RxTextView.textChanges(mAcEdPassword).map(new Function<CharSequence, String>() {
            @Override
            public String apply(CharSequence charSequence) throws Exception {
                return String.valueOf(charSequence);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Toast.makeText(RxBindingActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("CheckResult")
    private void LoginMethod() {
        Observable.combineLatest(
                RxTextView.textChanges(mAcEdAccount).map(new Function<CharSequence, String>() {
                    @Override
                    public String apply(CharSequence charSequence) throws Exception {
                        return String.valueOf(charSequence);
                    }
                }),
                RxTextView.textChanges(mAcEdPassword).map(new Function<CharSequence, String>() {
                    @Override
                    public String apply(CharSequence charSequence) throws Exception {
                        return String.valueOf(charSequence);
                    }
                }),
                new BiFunction<String, String, Boolean>() {
                    @Override
                    public Boolean apply(String s, String s2) throws Exception {
                        return "admin".equals(s) && "123".equals(s2);
                    }
                }
        )
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            mAcBtnLogin.setEnabled(true);
                            RxView.clicks(mAcBtnLogin).subscribe(new Consumer<Object>() {
                                @Override
                                public void accept(Object o) throws Exception {

                                }
                            });
                        } else {
                            mAcBtnLogin.setEnabled(false);
                        }
                    }
                });
    }

    //推荐使用这种
    public void DataBindingActivity(View view) {
        Intent intent=new Intent("com.rx.main.dataBinding");
        intent.setClassName("com.rx.main","com.rx.main.DataBindingActivity");
        intent.setComponent(new ComponentName("com.rx.main","com.rx.main.DataBindingActivity"));
        Bundle bundle=new Bundle();
        bundle.putString("name",mAcEdAccount.getText().toString().trim());
        bundle.putString("pwd",mAcEdPassword.getText().toString().trim());
        intent.putExtras(bundle);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }else {

        }
    }


    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return sizeLength;
        }
        @Override
        public Object getItem(int position) {
            return mStringArrayList.get(position);
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @SuppressLint({"InflateParams", "ViewHolder"})
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(RxBindingActivity.this).inflate(R.layout.item_listview, null);
            AppCompatTextView textView = view.findViewById(R.id.Ac_Tv);
            textView.setText(mStringArrayList.get(position));
            return view;
        }
    }

    @SuppressLint("CheckResult")
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tObservable != null) {
            tObservable.unsubscribeOn(AndroidSchedulers.mainThread()); //防止泄漏
        }
    }

}
