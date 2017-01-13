package com.hiking.floattask.view;



import android.graphics.Point;

import com.hiking.floattask.manager.GestureManager;

/**
 * Created by Administrator on 2017/1/7.
 */
public class CusGestureOperationListener implements WaterDropView.GestureOperationListener {

    private GestureManager mGestureManager;

    public CusGestureOperationListener(GestureManager gestureManager) {
        mGestureManager = gestureManager;
    }

    @Override
    public void onGestureOperate(WaterDropView.Gesture gesture) {

        if(!(mGestureManager ==null)){
            mGestureManager.doGesture(gesture);
        }

    }

    @Override
    public void onGestureOperate(WaterDropView.Gesture gesture, Point point) {
        if(!(mGestureManager ==null)){
            mGestureManager.doGesture(gesture,point);
        }
    }
}
