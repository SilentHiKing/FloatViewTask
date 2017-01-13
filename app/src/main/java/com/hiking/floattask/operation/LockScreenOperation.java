package com.hiking.floattask.operation;

import android.content.Context;
import android.os.Parcelable;
import android.os.PowerManager;
import android.os.SystemClock;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2017/1/14.
 */
public class LockScreenOperation extends AOperation {
    private LockScreenOperation(Context context) {
        super(context);
    }
    public static LockScreenOperation mAOperation;
    public synchronized static LockScreenOperation getInstance(Context context) {
        if (mAOperation ==null){
            mAOperation = new LockScreenOperation(context);
        }
        return mAOperation;
    }

    @Override
    public void operate() {

        PowerManager pm = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);
        try {
            Method m = Class.forName("android.os.PowerManager").getMethod("goToSleep",long.class);
            m.invoke(pm,SystemClock.uptimeMillis());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}
