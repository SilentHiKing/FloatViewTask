package com.hiking.floattask.manager;

import android.app.Application;
import android.content.Intent;

import com.hiking.floattask.FloatViewService;
import com.hiking.floattask.MainActivity;
import com.hiking.floattask.utils.SPUtil;
import com.hiking.floattask.utils.SystemUtil;

/**
 * Created by Administrator on 2017/1/14.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //
        String s = (String) SPUtil.get(this,FloatWindowManager.SWITCH,FloatWindowManager.OFF);
        if(s.equals(FloatWindowManager.ON)
                && !SystemUtil.isServiceWork(this, MainActivity.SERVICE_NAME)){

            startService(new Intent(this,FloatViewService.class));
        }

    }

}
