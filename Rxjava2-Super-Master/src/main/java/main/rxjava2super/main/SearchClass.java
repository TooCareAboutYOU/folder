package main.rxjava2super.main;

import android.annotation.SuppressLint;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class SearchClass {

    private static final String TAG = "SearchClass";

    private SearchClass(){}
    @SuppressLint("StaticFieldLeak")
    public static SearchClass getInstance(){  return SearchClassHolder.instance;  }
    private static class SearchClassHolder{
        @SuppressLint("StaticFieldLeak")
        private static final SearchClass instance=new SearchClass();
    }

    private PublishSubject<String> mPublishSubject;
    private CompositeDisposable mCompositeDisposable;

    @SuppressLint("CheckResult")
    public void init(AppCompatEditText editText, AppCompatTextView textView){
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                startSearch(s.toString());
            }
        });

        DisposableObserver<String> mDisposableObserver=new DisposableObserver<String>() {
            @Override
            public void onNext(String s) { textView.setText(s); }
            @Override
            public void onError(Throwable e) { }
            @Override
            public void onComplete() { }
        };

        mPublishSubject=PublishSubject.create();
        mPublishSubject.debounce(200, TimeUnit.MILLISECONDS)
                .filter(s -> s.length()>0)
                .switchMap((Function<String, ObservableSource<String>>) this::getSearchObservable)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mDisposableObserver);
        mCompositeDisposable=new CompositeDisposable();
        mCompositeDisposable.add(mDisposableObserver);
    }

    private void startSearch(String s) {
        mPublishSubject.onNext(s);
    }

    private Observable<String> getSearchObservable(final String query){
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Log.i(TAG, "开始请求，关键字为："+query);
                try {
                    Thread.sleep(100 + (long) (Math.random() * 500));
                }catch (InterruptedException e){
                    if (!emitter.isDisposed()) {
                        emitter.onError(e);
                    }
                }
                Log.d(TAG, "结束请求，关键词为："+query);
                emitter.onNext("完成搜索，关键词为："+query);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io());
    }

    public void clear(){
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

}
