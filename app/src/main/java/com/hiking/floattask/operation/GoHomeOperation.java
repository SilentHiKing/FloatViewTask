package com.hiking.floattask.operation;

import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;

/**
 * Created by Administrator on 2017/1/12.
 */
public class GoHomeOperation extends AOperation {
    private GoHomeOperation(Context context) {
        super(context);
    }
    public static GoHomeOperation mAOperation;
    public synchronized static GoHomeOperation getInstance(Context context) {
        if (mAOperation ==null){
            mAOperation = new GoHomeOperation(context);
        }
        return mAOperation;
    }




    @Override
    public void operate() {
        Intent backHome = new Intent(Intent.ACTION_MAIN);
        backHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        backHome.addCategory(Intent.CATEGORY_HOME);
        mContext.startActivity(backHome);
        //createInputEvent(KeyEvent.KEYCODE_HOME);
    }
}
