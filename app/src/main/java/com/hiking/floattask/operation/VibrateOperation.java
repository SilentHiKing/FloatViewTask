package com.hiking.floattask.operation;

import android.app.Service;
import android.content.Context;
import android.os.Vibrator;
import android.view.KeyEvent;

/**
 * Created by Administrator on 2017/1/12.
 */
public class VibrateOperation extends AOperation {
    private VibrateOperation(Context context) {
        super(context);
    }
    public static VibrateOperation mAOperation;
    public synchronized static VibrateOperation getInstance(Context context) {
        if (mAOperation ==null){
            mAOperation = new VibrateOperation(context);
        }
        return mAOperation;
    }




    @Override
    public void operate() {
        Vibrator vib = (Vibrator) mContext.getSystemService(Service.VIBRATOR_SERVICE);
        vib.cancel();
        vib.vibrate(36);

    }
}
