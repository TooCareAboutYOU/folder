package com.rx.main;

import android.annotation.SuppressLint;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AnimationActivity extends AppCompatActivity {

    private ConstraintLayout rootView;
    private ConstraintSet applyConstraintSet=new ConstraintSet();
    private ConstraintSet resetConstraintSet=new ConstraintSet();


    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        ImageView imageView=findViewById(R.id.img);
        Animation animIn= AnimationUtils.loadAnimation(this,R.anim.set_alpha_scale_in);

        Animation animOut=AnimationUtils.loadAnimation(this,R.anim.set_alpha_scale_out);

        imageView.startAnimation(animIn);
        imageView.setOnClickListener(v -> imageView.startAnimation(animOut));
        Observable.interval(3, TimeUnit.SECONDS)
                .take(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                imageView.startAnimation(animOut);
            }
        });

        rootView=findViewById(R.id.cl_ContainerView);
        findViewById(R.id.img).setOnClickListener(v -> resetConstraintSet.applyTo(rootView));

        applyConstraintSet.clone(rootView);
        resetConstraintSet.clone(rootView);
    }
    public void ApplyClickListener(View view) {
        TransitionManager.beginDelayedTransition(rootView);

//        applyConstraintSet.setMargin(R.id.img1,ConstraintSet.START,0);
//        applyConstraintSet.setMargin(R.id.img1,ConstraintSet.TOP,0);
//        applyConstraintSet.setMargin(R.id.img1,ConstraintSet.END,0);
//        applyConstraintSet.setMargin(R.id.img1,ConstraintSet.BOTTOM,0);
//        applyConstraintSet.centerHorizontally(R.id.img1,R.id.cl_ContainerView);
//        applyConstraintSet.centerVertically(R.id.img1,R.id.cl_ContainerView);

        applyConstraintSet.connect(R.id.img1,ConstraintSet.START,R.id.cl_ContainerView,ConstraintSet.START,0);
        applyConstraintSet.connect(R.id.img1,ConstraintSet.TOP,R.id.cl_ContainerView,ConstraintSet.TOP,0);
        applyConstraintSet.connect(R.id.img1,ConstraintSet.END,R.id.cl_ContainerView,ConstraintSet.END,0);
        applyConstraintSet.connect(R.id.img1,ConstraintSet.BOTTOM,R.id.cl_ContainerView,ConstraintSet.BOTTOM,0);
        applyConstraintSet.applyTo(rootView);
    }

    public void ResetClickListener(View view) {
        resetConstraintSet.applyTo(rootView);
    }
}
