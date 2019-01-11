package com.databinding.main.bindingconversions;

import android.databinding.BindingConversion;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DataConversion {

    @BindingConversion
    public static String convertDate(Date date){
        return new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(date);
    }

}
