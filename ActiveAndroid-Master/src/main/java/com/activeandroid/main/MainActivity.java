package com.activeandroid.main;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Model;
import com.activeandroid.main.entitys.Category;
import com.activeandroid.main.entitys.Item;
import com.activeandroid.query.Delete;
import com.activeandroid.query.From;
import com.activeandroid.query.Select;
import com.activeandroid.query.Update;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.Normalizer;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MMainActivity";

    private List<Category> execute;

    private AppCompatEditText mEtSearch,mEtUpdate,mEtDelete,mEdUpdateName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] strings={Permission.READ_EXTERNAL_STORAGE,Permission.WRITE_EXTERNAL_STORAGE};
        AndPermission.with(this)
                .runtime()
                .permission(strings)
                .onGranted(data -> {
                    Toast.makeText(this, "存储权限开启成功", Toast.LENGTH_SHORT).show();
                })
                .onDenied(data -> {
                    Toast.makeText(this, "存储权权限开启失败", Toast.LENGTH_LONG).show();
                })
                .start();


        mEtSearch=findViewById(R.id.acEt_Conditions_Search);
        mEtUpdate=findViewById(R.id.acEt_Conditions_Update);
        mEtDelete=findViewById(R.id.acEt_Conditions_Delete);
        mEdUpdateName=findViewById(R.id.acEt_Conditions_Name);
    }

    @SuppressLint("SimpleDateFormat")
    public void ClickMethod(View view) {
        switch (view.getId()) {
            case R.id.btn_Add:{
                //大量的数据插入时，用事务
                ActiveAndroid.beginTransaction();
                try {
                    for (int i = 1; i < 11; i++) {
                        int id = i; //new Random().nextInt(10);
                        Log.i(TAG, "用户ID: " + id);
                        From from = new Select().from(Category.class).where("userId=?", id);
                        if (!from.exists()) {
                            Category category = new Category();
                            SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd号 HH:mm:ss");
                            String dateString = format.format(new Date());
                            category.setUserId(id);

                            category.setName("I am from Main, and now is " + dateString);
                            category.save();
                            Item item = new Item();
                            item.setName(id + "Test");
                            item.setCategory(category);
                            item.save();
                        } else {
                            Toast.makeText(this, "用户数据已经存在", Toast.LENGTH_SHORT).show();
                        }
                    }
                    ActiveAndroid.setTransactionSuccessful();
                }finally {
                 ActiveAndroid.endTransaction();
                }

                printAllData();

                break;
            }
            case R.id.btn_Search:{
                if (!TextUtils.isEmpty(mEtSearch.getText().toString().trim())) {
                    execute=new Select().from(Category.class).where("userId=?",mEtSearch.getText().toString().trim()).execute();
                    for (int i = 0; i < execute.size(); i++) {
                        Log.i(TAG, "条件打印信息: "+execute.get(i).toString());
                    }
                    return;
                }
                printAllData();
                break;
            }
            case R.id.btn_Delete:{
                if (!TextUtils.isEmpty(mEtDelete.getText().toString().trim())) {
//                    int id=Integer.parseInt(mEtDelete.getText().toString().trim());
//                    From from=new Select().from(Category.class).where("userId=?",id);
//                    if (from.exists()) {
//                        Log.d(TAG, "用户存在");
//                        new Delete().from(Category.class).where("userId=?",id).execute();
//                    }

                    From from=new Select().from(Item.class).where("name=?",mEtDelete.getText().toString().trim());
                    if (from.exists()) {
                        Log.d(TAG, "用户存在");
                        new Delete().from(Item.class).where("name=?",mEtDelete.getText().toString().trim()).execute();
                    }
                    else {
                        Toast.makeText(this, "用户不存在", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this, "请输入需要删除的UserId", Toast.LENGTH_SHORT).show();
                }
                printAllData();
                break;
            }
            case R.id.btn_Update:{
                if (!TextUtils.isEmpty(mEtUpdate.getText().toString().trim()) && !TextUtils.isEmpty(mEdUpdateName.getText().toString().trim())) {
                    int userId=Integer.parseInt(mEtUpdate.getText().toString().trim());
                    From from=new Select().from(Category.class).where("userId=?",userId);
                    if (from.exists()) {
                        Log.d(TAG, "用户存在");
                        String name=mEdUpdateName.getText().toString().trim();
                        new Update(Category.class).set("name=?",name).where("userId=?",userId).execute();
                        Toast.makeText(this, "更新成功", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(this, "用户不存在", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this, "更新失败！", Toast.LENGTH_SHORT).show();
                }
                printAllData();
                break;
            }
        }
    }

    private void printAllData(){
        execute=new Select().from(Category.class).execute();
        for (int i = 0; i < execute.size(); i++) {
            Log.w(TAG, "打印信息: "+execute.get(i).toString());
        }
        mEtSearch.setText("");
        mEtUpdate.setText("");
        mEtDelete.setText("");
        mEdUpdateName.setText("");
    }
}
