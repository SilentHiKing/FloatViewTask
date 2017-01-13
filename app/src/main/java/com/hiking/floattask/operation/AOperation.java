package com.hiking.floattask.operation;

import android.app.usage.UsageEvents;
import android.content.Context;
import android.hardware.input.InputManager;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;
import android.view.InputEvent;
import android.view.KeyEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2017/1/12.
 */
public abstract class AOperation {

    private static final String TAG = "AOperation";

    public Context mContext;


    public AOperation(Context context) {
        mContext = context;
    }

    public abstract void operate();

    /**
     * 此方法<uses-permission android:name="android.permission.INJECT_EVENTS"/>生效
     * apk的签名要和系统的一致
     * @param code
     */
    public void createInputEvent(int code){
        long now = SystemClock.uptimeMillis();
        KeyEvent eventDown = new KeyEvent(now,now,KeyEvent.ACTION_DOWN,code,0);
        KeyEvent eventUp = new KeyEvent(now,now,KeyEvent.ACTION_UP,code,0);
        injectInputEvent(eventDown);
        injectInputEvent(eventUp);

    }

    private void injectInputEvent(KeyEvent event) {


        boolean inputEvent = false;
        try {
            Class clazz = Class.forName("android.hardware.input.InputManager");
            Method m1 = clazz.getMethod("getInstance");
            InputManager inputManager = (InputManager)m1.invoke(null);

            Method m2 =clazz.getMethod("injectInputEvent", InputEvent.class,int.class);
            inputEvent = (boolean)m2.invoke(inputManager,event,0);

            Log.d(TAG,"inputEvent = "+inputEvent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
