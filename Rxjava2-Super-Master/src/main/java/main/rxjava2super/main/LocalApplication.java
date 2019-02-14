package main.rxjava2super.main;

import android.app.Application;
import android.os.Build;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * Code of ZHANG/ 2019/2/14
 */
public class LocalApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            ARouter.openDebug();
            ARouter.openLog();
        }

        ARouter.init(this);

    }
}
