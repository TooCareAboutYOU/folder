package com.databinding.main;

import android.databinding.BaseObservable;
import android.databinding.BindingConversion;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableArrayMap;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.databinding.main.bindingconversions.DataConversion;
import com.databinding.main.databinding.ActivityMainBinding;
import com.databinding.main.viewmodels.RecyclerViewBean;
import com.databinding.main.viewmodels.TestModel;
import com.databinding.main.viewmodels.UserBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mMainBinding;
    private  UserBean userBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mMainBinding.setMListener(new MyClickListeners());
        userBean=new UserBean();
        mMainBinding.setUser(userBean);

        loadRecyclerView();
    }

    public class MyClickListeners{

        private boolean isCheck = false;

        public void XmlOnClickListener(View view) {
            isCheck=!isCheck;

            mMainBinding.setIsChecked(isCheck);

            mMainBinding.setStr(isCheck ? "" : null);

            TestModel model = new TestModel();
            model.setName(isCheck ? "Hello World" : "World Hello");
            model.setMark(isCheck ? "I am from JAVA" : "哈哈哈哈");
            model.setSex(isCheck ? "男" : "女");
            mMainBinding.setTestModel(model);

            ObservableArrayList observableList=new ObservableArrayList();
            observableList.add("dataBindingList"+isCheck);
            observableList.add(12);
            mMainBinding.setListIndex(1);
            mMainBinding.setArrayList(observableList);

            ObservableArrayMap observableMap=new ObservableArrayMap();
            observableMap.put("hash1","dataBindingMap"+isCheck);
            observableMap.put("hash2",10086);
            observableMap.put("hash3","from 3");
            mMainBinding.setMapKey("hash3");
            mMainBinding.setHashMap(observableMap);

            Toast.makeText(MainActivity.this, "点击了"+isCheck+"\t\t"+userBean.getName(), Toast.LENGTH_SHORT).show();

            list.add(new RecyclerViewBean("第"+isCheck+"个","I am from "+isCheck,"Blog "+isCheck));
            mMyAdapter.notifyDataSetChanged();
            smoothPosition(mMyAdapter.getItemCount()-1);
        }

    }

    private List<RecyclerViewBean> list;
    private MyAdapter mMyAdapter;
    private void loadRecyclerView(){
        list=new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            list.add(new RecyclerViewBean("第"+i+"个","I am from "+i,"Blog "+i));
        }
        mMyAdapter = new MyAdapter();
        mMainBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
        mMainBinding.recyclerView.setAdapter(mMyAdapter);
        smoothPosition(mMyAdapter.getItemCount()-1);
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ItemViewHolder>{

        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            ViewDataBinding dataBinding=DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),R.layout.item_layout,viewGroup,false);
            return new ItemViewHolder(dataBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
            itemViewHolder.getDataBinding().setVariable(BR.bean,list.get(i));
            itemViewHolder.getDataBinding().executePendingBindings();
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class ItemViewHolder extends RecyclerView.ViewHolder{

            private ViewDataBinding mDataBinding;

            public ItemViewHolder(ViewDataBinding dataBinding) {
                super(dataBinding.getRoot());
                this.mDataBinding=dataBinding;
            }

            public ViewDataBinding getDataBinding() {
                return mDataBinding;
            }

            public void setDataBinding(ViewDataBinding dataBinding) {
                mDataBinding = dataBinding;
            }
        }
    }

    private void smoothPosition(int index){
        mMainBinding.recyclerView.smoothScrollToPosition(index);
    }



}
