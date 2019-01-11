package com.databinding.main.viewmodels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.databinding.main.BR;

public class TestModel extends BaseObservable{
    private String name;
    private String mark;
    private String sex;

    @Bindable
    public String getName() { return name; }

    @Bindable
    public String getMark() { return mark; }

    @Bindable
    public String getSex() { return sex; }


    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    public void setMark(String mark) {
        this.mark = mark;
        notifyPropertyChanged(BR.mark);
    }

    public void setSex(String sex) {
        this.sex = sex;
        notifyPropertyChanged(BR.sex);
    }
}
