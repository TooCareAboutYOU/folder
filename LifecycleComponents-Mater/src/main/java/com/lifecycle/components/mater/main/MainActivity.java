package com.lifecycle.components.mater.main;

import android.Manifest;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.lifecycle.components.mater.main.databinding.ActivityMainBinding;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MMainActivity";
    
    private ActivityMainBinding mMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainBinding=DataBindingUtil.setContentView(this, R.layout.activity_main);
        mMainBinding.btnStartService.setOnClickListener(v -> {

            String[] permissions=new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            };
            AndPermission.with(MainActivity.this)
                    .runtime()
                    .permission(permissions)
                    .onGranted(data -> {
                        Toast.makeText(MainActivity.this, "通过权限", Toast.LENGTH_SHORT).show();
                        LiveData<Location> locationLiveData=new LocationLiveData(this);
                        /*
                         * observe(LifecycleOwner owner, Observer<T> observer)
                         * 这个方法就是向LiveData中添加观察者，
                         * LiveData则可以通过LifecycleOwner来判断
                         * 当前传入的观察者是否是活跃的（也就是UI是否可见了）
                         */
                        locationLiveData.observe(this, location -> {
                            // update
                            //当LiveData中通过setValue()修改了数据时，
                            //这里将会受到修改后的数据
                            Toast.makeText(this, "数据改变..."+location.toString(), Toast.LENGTH_SHORT).show();
                            Log.i(TAG, "数据改变..."+location.toString());
                        });
                    })
                    .onDenied(data -> {
                        Toast.makeText(MainActivity.this, "禁止权限", Toast.LENGTH_SHORT).show();
                    });
        });
    }

    private LocationManager mLocationManager;


    public class LocationLiveData extends LiveData<Location> {

        private LocationListener mListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) { 
                setValue(location);
                Log.i(TAG, "onLocationChanged: "+location.toString());
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) { }

            @Override
            public void onProviderEnabled(String provider) { }

            @Override
            public void onProviderDisabled(String provider) { }
        };

        public LocationLiveData(Context context) {
            mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        }

        @Override
        protected void onActive() {
            super.onActive();
            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mListener);
        }

        @Override
        protected void onInactive() {
            super.onInactive();
            mLocationManager.removeUpdates(mListener);
        }
    }

}
