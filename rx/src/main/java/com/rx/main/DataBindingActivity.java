package com.rx.main;

import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.rx.main.databinding.ActivityDataBindingBinding;


public class DataBindingActivity extends AppCompatActivity {

    private static final String TAG = "DataBindingActivity";

    private ActivityDataBindingBinding binding=null;
    private UserBean mUserBean=null;
    private FormBean mFormBean=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_binding);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_data_binding);

        String name=getIntent().getStringExtra("name");
        String pwd=getIntent().getStringExtra("pwd");

        mUserBean=new UserBean(name,pwd);
        binding.setUser(mUserBean);
        binding.setPresenter(new Presenter());

        mFormBean=new FormBean(name,pwd);
        binding.setFormBean(mFormBean);
        mFormBean.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                switch (propertyId) {
                    case com.rx.main.BR.account:
                        Toast.makeText(DataBindingActivity.this, "账号改变", Toast.LENGTH_SHORT).show();
                        break;
                    case com.rx.main.BR.password:
                        Toast.makeText(DataBindingActivity.this, "密码改变", Toast.LENGTH_SHORT).show();
                        break;
                }

            }
        });
    }

    public class Presenter{
        //方法引用 参数要求符合接口定义
        public void onTextChanged(CharSequence s, int start, int before, int count){
            Toast.makeText(DataBindingActivity.this, "触发了onTextChanged", Toast.LENGTH_SHORT).show();
            mUserBean.setFirstName(s.toString());
            //如果已经为该类继承了BaseObservable 就不需要再赋值了；
            //mBinding.setStudent(mStudent);
        }

        //方法引用，参数要求符合接口定义；
        public void onClick(View view){
            mUserBean.setFirstName("Presenter改变的数据\t\t");
        }

        //lambda自定义方法
        public void onClickListenerBinding(UserBean student){
            student.setFirstName("Presenter改变的数据：");
        }

        //lambda自定义方法
        public void onClickListenerBinding(FormBean formBean){
            formBean.setAccount("account");
            formBean.setPassword("123456");
        }
    }

    @Override
    protected void onDestroy() {
        if (mUserBean != null) {
            mUserBean = null;
        }
        if (mFormBean != null) {
            mFormBean=null;
        }

        super.onDestroy();
        if (binding != null) {
            binding.invalidateAll();
            binding=null;
        }
    }
}
