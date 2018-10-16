package com.lifecycle.main;


import android.arch.lifecycle.LifecycleOwner;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class BlankFragment extends Fragment implements LifecycleOwner{

    public BlankFragment() {
    }

    public static BlankFragment newInstance() { return new BlankFragment(); }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }



}
