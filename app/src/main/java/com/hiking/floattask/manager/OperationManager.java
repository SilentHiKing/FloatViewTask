package com.hiking.floattask.manager;

import android.app.NotificationManager;
import android.content.Context;

import com.hiking.floattask.operation.GoBackOperation;
import com.hiking.floattask.operation.GoHomeOperation;
import com.hiking.floattask.operation.LockScreenOperation;
import com.hiking.floattask.operation.NotificationOperation;
import com.hiking.floattask.operation.VibrateOperation;

/**
 * Created by Administrator on 2017/1/12.
 */
public class OperationManager {

    private static OperationManager mOperationManager;
    Context mContext;

    private OperationManager(Context context) {
        mContext = context;
    }

    public synchronized static OperationManager getInstance(Context context) {
        if (mOperationManager ==null){
            mOperationManager = new OperationManager(context);
        }
        return mOperationManager;
    }

    public void expandNotificationOperation(){
        NotificationOperation.getInstance(mContext).operate();
    }
    
    public void goBackOperation(){
        GoBackOperation.getInstance(mContext).operate();
    }

    public void goHomeOperation(){
        GoHomeOperation.getInstance(mContext).operate();
    }

    public void showRecentsOperation() {
    }

    public void vibrateOperation() {
        VibrateOperation.getInstance(mContext).operate();
    }
    public void lockScreenOperation() {
        LockScreenOperation.getInstance(mContext).operate();
    }
}
