package com.hiking.floattask;

import android.accessibilityservice.AccessibilityService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.accessibility.AccessibilityEvent;

import com.hiking.floattask.manager.FloatWindowManager;

public class FloatViewService extends Service {

    FloatWindowManager mFloatWindowManager;




    @Override
    public void onCreate() {
        mFloatWindowManager = FloatWindowManager.getInstance();
        mFloatWindowManager.initData(this);
        mFloatWindowManager.addFloatView();
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        mFloatWindowManager.removeFloatView();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
