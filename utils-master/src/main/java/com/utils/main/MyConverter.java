package com.utils.main;

import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MyConverter {

    @BindingAdapter("show")  //自定义属性名
    public static void showImage(ImageView imageView, String imgUrl){
        Glide.with(imageView).asBitmap().load(imgUrl).into(imageView);
    }

//    @BindingConversion
//    public static String convertData(Date date){
//        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
//        return sdf.format(date);
//    }


}
