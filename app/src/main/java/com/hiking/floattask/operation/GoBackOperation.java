package com.hiking.floattask.operation;

import android.content.Context;
import android.view.KeyEvent;

/**
 * Created by Administrator on 2017/1/12.
 */
public class GoBackOperation extends AOperation {
    private GoBackOperation(Context context) {
        super(context);
    }
    public static GoBackOperation mAOperation;
    public synchronized static GoBackOperation getInstance(Context context) {
        if (mAOperation ==null){
            mAOperation = new GoBackOperation(context);
        }
        return mAOperation;
    }




    @Override
    public void operate() {
        createInputEvent(KeyEvent.KEYCODE_BACK);
    }
}
