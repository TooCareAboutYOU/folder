package com.theme.main;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.AlarmManager;
import android.app.AppOpsManager;
import android.app.DownloadManager;
import android.app.KeyguardManager;
import android.app.LoaderManager;
import android.app.NotificationManager;
import android.app.SearchManager;
import android.app.UiModeManager;
import android.app.WallpaperManager;
import android.app.usage.NetworkStatsManager;
import android.app.usage.StorageStatsManager;
import android.app.usage.UsageStatsManager;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.res.AssetManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.PrimitiveIterator;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MMainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = findViewById(R.id.TBar);
//        setSupportActionBar(toolbar);
//        toolbar.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, BActivity.class)));
    }


    private void init(){
        ActivityManager activityManager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        AlarmManager alarmManager= (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        AppOpsManager appOpsManager= (AppOpsManager) this.getSystemService(Context.APP_OPS_SERVICE);
        DownloadManager downloadManager = (DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);
        KeyguardManager keyguardManager= (KeyguardManager) this.getSystemService(Context.KEYGUARD_SERVICE);
        LoaderManager loaderManager=new LoaderManager() {
            @Override
            public <D> Loader<D> initLoader(int id, Bundle args, LoaderCallbacks<D> callback) {
                return null;
            }

            @Override
            public <D> Loader<D> restartLoader(int id, Bundle args, LoaderCallbacks<D> callback) {
                return null;
            }

            @Override
            public void destroyLoader(int id) {

            }

            @Override
            public <D> Loader<D> getLoader(int id) {
                return null;
            }

            @Override
            public void dump(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {

            }
        };
//        getLoaderManager()

        NotificationManager notificationManager= (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        SearchManager searchManager= (SearchManager) this.getSystemService(Context.SEARCH_SERVICE);
        UiModeManager uiModeManager= (UiModeManager) this.getSystemService(Context.UI_MODE_SERVICE);
        WallpaperManager wallpaperManager= (WallpaperManager) this.getSystemService(Context.WALLPAPER_SERVICE);

        if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.LOLLIPOP) {
            UsageStatsManager storageStatsManager = (UsageStatsManager) this.getSystemService(Context.USAGE_STATS_SERVICE);
        }

        if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.M) {
            NetworkStatsManager networkStatsManager = (NetworkStatsManager) this.getSystemService(Context.NETWORK_STATS_SERVICE);
        }


        if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.O) {
            StorageStatsManager storageStatsManager = (StorageStatsManager) this.getSystemService(Context.STORAGE_STATS_SERVICE);
        }


        AppWidgetManager storageStatsManager = (AppWidgetManager) this.getSystemService(Context.APPWIDGET_SERVICE);
        AssetManager assetManager=getAssets();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: " + getClass().getCanonicalName());
    }
}
