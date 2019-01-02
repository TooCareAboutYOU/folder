package com.db.kotin.main.ob.job;

import android.util.Log;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;
/**
 * Code of ZHANG/ 2018/10/18
 */
public class MyJobCreator implements JobCreator{

    @Override
    public Job create(String tag) {
        switch (tag) {
            case MySyncJob.TAG:{
                Log.i(MySyncJob.TAG, "MyJobCreator create: ");
                return new MySyncJob();
                }
            default:{
                return null;
            }
        }
    }

}
