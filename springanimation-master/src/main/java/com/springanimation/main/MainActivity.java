package com.springanimation.main;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.animation.DynamicAnimation;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SpringForce springForce=new SpringForce(0)
                .setDampingRatio(0.15f)
                .setStiffness(SpringForce.STIFFNESS_LOW);
        SpringAnimation anim=new SpringAnimation(findViewById(R.id.img_ball),
                SpringAnimation.TRANSLATION_Y).setSpring(springForce);
        findViewById(R.id.btn_bounce).setOnClickListener(view -> {
            anim.cancel();
            anim.setStartValue(-1000);
            anim.start();
        });
    }
}
