package com.rx.main;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 *
 */
public class FormBean extends BaseObservable {
    private String account;
    private String password;

    public FormBean(String account, String password) {
        this.account = account;
        this.password = password;
    }

    @Bindable
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
        notifyPropertyChanged(com.rx.main.BR.account);
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(com.rx.main.BR.password);
    }
}
