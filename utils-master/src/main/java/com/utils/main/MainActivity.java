package com.utils.main;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.IntentUtils;
import com.bumptech.glide.Glide;
import com.utils.main.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MMainActivity";

    private ActivityMainBinding mMainBinding;

    DataModel mDataModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainBinding= DataBindingUtil.setContentView(this, R.layout.activity_main);
        mMainBinding.setClickListener(new MyClickListener());
//        mMainBinding.setWhatsUp("123");
//        Log.i(TAG, "onCreate: "+mMainBinding.getWhatsUp());
        mDataModel=new DataModel();
        mMainBinding.setWhatsUp(mDataModel);
    }

    public class MyClickListener{

        private boolean isShowBar=true;

        public void clickEvent(View view){
            switch (view.getId()) {
                case R.id.btn_activity:{
                    Log.e(TAG, "clickEvent: Activity");
//                    mMainBinding.setTimeDate(new Date());
                    mMainBinding.setHideView(!mMainBinding.getHideView());
                    break;
                }
                case R.id.btn_app:{
                    Log.e(TAG, "clickEvent: App" );
                    Toast.makeText(MainActivity.this, "获取："+mDataModel.getWhatsup(), Toast.LENGTH_SHORT).show();
                    break;
                }
                case R.id.btn_bar:{
                    Log.e(TAG, "clickEvent: Bar");
                    StringBuilder stringBuilder=new StringBuilder();
//                    stringBuilder.append("获取状态栏高度(px)：").append(BarUtils.getStatusBarHeight()).append("\n");
//                    stringBuilder.append("获取状态栏高度(dp)：").append(ConvertUtils.px2dp(BarUtils.getStatusBarHeight())).append("\n");

//                    isShowBar=BarUtils.isStatusBarVisible(MainActivity.this);
                    isShowBar=!isShowBar;

//                    stringBuilder.append("判断状态栏是否可见：").append(isShowBar).append("\n");
//                    BarUtils.setStatusBarVisibility(MainActivity.this,isShowBar);
//                    stringBuilder.append("设置状态栏是否可见：").append(isShowBar).append("\n");


                    if (isShowBar) {
                        BarUtils.setStatusBarColor(MainActivity.this,getResources().getColor(R.color.colorPrimary));
                        stringBuilder.append("设置状态栏颜色2：").append("colorPrimaryDark=#3F51B5").append("\n");
                    }else {

                        BarUtils.setStatusBarColor(MainActivity.this,getResources().getColor(R.color.colorAccent));
                        stringBuilder.append("设置状态栏颜色1：").append("colorAccent=#FF4081").append("\n");
                    }

//                    stringBuilder.append("获取 ActionBar 高度：").append(BarUtils.getActionBarHeight(MainActivity.this)).append("\n");
//                    BarUtils.setNotificationBarVisibility(isShowBar);
//                    stringBuilder.append("设置通知栏是否可见：").append(isShowBar).append("\n");
//                    stringBuilder.append("获取 ActionBar 高度：").append(BarUtils.getActionBarHeight(MainActivity.this)).append("\n");
//                    stringBuilder.append("获取导航栏高度：").append(BarUtils.getNavBarHeight()).append("\n");
//
//                    BarUtils.setNavBarVisibility(MainActivity.this,isShowBar);
//                    stringBuilder.append("设置导航栏是否可见：").append(isShowBar).append("\n");
//
//                    stringBuilder.append("获取导航栏高度：").append(BarUtils.getNavBarHeight()).append("\n");
//
//                    stringBuilder.append("获取导航栏高度：").append(BarUtils.getNavBarHeight()).append("\n");述职PPT-技术部-张帅.pptx
                    Log.i(TAG, "clickEvent: "+stringBuilder.toString());

                    break;
                }
                case R.id.btn_img: {
                    mMainBinding.setImgUrl("https://ws1.sinaimg.cn/large/0065oQSqly1fytdr77urlj30sg10najf.jpg");
                    break;
                }
                case R.id.btn_uninstallAPP:{
                    String packetName="com.db.kotin.main.ob";
                    Intent intent=null;
//                    if (isShowBar) {
//                        Toast.makeText(MainActivity.this, "启动-Ob", Toast.LENGTH_SHORT).show();
//                        intent=IntentUtils.getLaunchAppIntent(packetName);
//                    }else {
//                        Toast.makeText(MainActivity.this, "卸载-Ob", Toast.LENGTH_SHORT).show();
//                        intent=IntentUtils.getUninstallAppIntent(packetName);
//                    }
//                    getAll();
//                    startActivity(intent);
                    isShowBar=!isShowBar;
                    break;
                }
            }
        }
    }


    public class DataModel extends BaseObservable{
        private String whatsup;

        @Bindable
        public String getWhatsup() {
            return whatsup;
        }

        public void setWhatsup(String whatsup) {
            this.whatsup = whatsup;
            notifyPropertyChanged(BR.whatsup);
        }
    }



    private void getAll(){
        HashMap<String, String[]> map=new HashMap<String, String[]>();
        List<String> appList=new ArrayList<String>();
        List<String> systemApp=new ArrayList<String>();
        List<String> packetNameList=new ArrayList<>();

        PackageManager pm=getPackageManager();
        Intent query=new Intent(Intent.ACTION_MAIN);
        query.addCategory("android.intent.category.LAUNCHER");
        @SuppressLint("WrongConstant") List<ResolveInfo> resolves= pm.queryIntentActivities(query, PackageManager.GET_ACTIVITIES);
        for (int i = 0; i < resolves.size(); i++) {
            ResolveInfo info= resolves.get(i);

  //判断是否为系统级应用
            if((info.activityInfo.applicationInfo.flags&ApplicationInfo.FLAG_SYSTEM)<=0){
                /*安装的应用*/
                String packagename=info.loadLabel(pm).toString();
                packetNameList.add(info.activityInfo.packageName+"\t\t"+info.activityInfo.name);
                String[] permission;
                try {
                    permission = pm.getPackageInfo(info.activityInfo.packageName, PackageManager.GET_PERMISSIONS).requestedPermissions;//获取权限列表
                    appList.add(packagename);
                    map.put(packagename, permission);
                } catch (PackageManager.NameNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            } else{
                /*系统应用*/
                String packagename=info.loadLabel(pm).toString();
                packetNameList.add(info.activityInfo.packageName+"\t\t"+info.activityInfo.name);
                String[] permission;
                try {
                    permission = pm.getPackageInfo(info.activityInfo.packageName, PackageManager.GET_PERMISSIONS).requestedPermissions;//获取权限列表
                    systemApp.add(packagename);
                    map.put(packagename, permission);
                } catch (PackageManager.NameNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        appList.addAll(systemApp);
        StringBuilder stringBuilder=new StringBuilder();
        for (String s : packetNameList) {
            stringBuilder.append(s).append("\n");
//            startActivity(IntentUtils.getUninstallAppIntent(s));
        }

        Log.i(TAG, "包名: "+stringBuilder.toString());
    };

}

