package com.rx.main;


import android.databinding.BaseObservable;
import android.databinding.Bindable;
import com.rx.main.BR;
/**
 *
 */
public class UserBean extends BaseObservable{
    private String firstName;
    private String lastName;

    public UserBean(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Bindable
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
        notifyPropertyChanged(com.rx.main.BR.firstName);
    }


    @Bindable
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
        notifyPropertyChanged(com.rx.main.BR.lastName);
    }
}
