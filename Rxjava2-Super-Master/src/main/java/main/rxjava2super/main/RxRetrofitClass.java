package main.rxjava2super.main;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import main.rxjava2super.main.beans.NewsEntity;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class RxRetrofitClass {

    private static final String TAG = "RxRetrofitClass";

    private RxRetrofitClass(){}
    @SuppressLint("StaticFieldLeak")
    public static RxRetrofitClass getInstance(){  return RxRetrofitClassHolder.instance;  }
    private static class RxRetrofitClassHolder{
        @SuppressLint("StaticFieldLeak")
        private static final RxRetrofitClass instance=new RxRetrofitClass();
    }

    private CompositeDisposable mCompositeDisposable=new CompositeDisposable();

    private Observable<NewsEntity> getObservable(String category, int page){
        ApiService api=new Retrofit.Builder()
                .baseUrl("http://gank.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(ApiService.class);
        return api.getNewsInfo(category, 10, page);
    }

    private interface ApiService{
        @GET("api/data/{category}/{count}/{page}")
        Observable<NewsEntity> getNewsInfo(@Path("category") String category,
                                           @Path("count") int count,
                                           @Path("page") int page);
    }

    public void refreshArticle(int page, RecyclerView.Adapter adapter, List<NewsEntity.ResultsBean> list, SwipeRefreshLayout refreshLayout){
        Observable<List<NewsEntity.ResultsBean>> observable=Observable.just(page)
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<Integer, ObservableSource<List<NewsEntity.ResultsBean>>>() {
            @Override
            public ObservableSource<List<NewsEntity.ResultsBean>> apply(Integer integer) throws Exception {
                Observable<NewsEntity> androidNews=getObservable("Android",page);
                Observable<NewsEntity> iOSNews=getObservable("iOS",page);
                Log.i(TAG, "apply--->>> "+page);
                return Observable.zip(androidNews, iOSNews, new BiFunction<NewsEntity, NewsEntity, List<NewsEntity.ResultsBean>>() {
                    @Override
                    public List<NewsEntity.ResultsBean> apply(NewsEntity androidEntity, NewsEntity iOSEntity) throws Exception {
                        Log.i(TAG, "apply: "+androidEntity.getResults().size()+"\t\t"+iOSEntity.getResults().size());
                        List<NewsEntity.ResultsBean> result=new ArrayList<>();
                        result.addAll(androidEntity.getResults());
                        result.addAll(iOSEntity.getResults());
                        return result;
                    }
                });
            }
        });

        DisposableObserver<List<NewsEntity.ResultsBean>> disposableObserver=new DisposableObserver<List<NewsEntity.ResultsBean>>() {
            @Override
            public void onNext(List<NewsEntity.ResultsBean> resultsBeans) {
                Log.i(TAG, "onNext: ");
                list.clear();
                list.addAll(resultsBeans);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError: "+e.getMessage());
                if (refreshLayout.isRefreshing()) {
                    refreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete: ");
                if (refreshLayout.isRefreshing()) {
                    refreshLayout.setRefreshing(false);
                }
            }
        };
        observable.observeOn(AndroidSchedulers.mainThread()).subscribe(disposableObserver);
        mCompositeDisposable.add(disposableObserver);
    }

    public void destroy(){
        Log.i(TAG, "destroy: ");
        mCompositeDisposable.clear();
    }



}
