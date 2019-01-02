package com.db.kotin.main.ob.job;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import com.db.kotin.main.ob.MainActivity;
import com.db.kotin.main.ob.R;
import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.security.auth.login.LoginException;

public class MySyncJob extends Job {

    public static final String TAG = "MySyncJob";

    @NonNull
    @Override
    protected Result onRunJob(Params params) {
        Log.i(TAG, "onRunJob: 1");
        if (params.isPeriodic()) {
            Log.i(TAG, "onRunJob: 2");
            PendingIntent pendingIntent=PendingIntent.getActivity(getContext(),0,new Intent(getContext(), MainActivity.class),0);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                Log.i(TAG, "MySyncJob-onRunJob: ");
                Notification notification=new Notification.Builder(getContext())
                        .setContentTitle("JobDemo")
                        .setContentText("Periodic job run")
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setShowWhen(true)
                        .setColor(Color.GREEN)
                        .setLocalOnly(true)
                        .build();
                NotificationManagerCompat.from(getContext()).notify(new Random().nextInt(),notification);
                Log.i(TAG, "isPeriodic: true");
            }
        }else {
            Log.i(TAG, "isPeriodic: false");
        }
        return Result.SUCCESS;
    }
}
