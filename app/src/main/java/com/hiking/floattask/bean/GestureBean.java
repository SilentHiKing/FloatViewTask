package com.hiking.floattask.bean;

import android.content.Context;

import com.hiking.floattask.manager.GestureManager;
import com.hiking.floattask.utils.SPUtil;
import com.hiking.floattask.view.WaterDropView.Gesture;

/**
 * Created by Administrator on 2017/1/9.
 */
public class GestureBean {
    private String gesture;
    private String gesFunction;
    private int gesIcon;

    public GestureBean(String gesture, Context context) {
        this.gesture = gesture;
        if (gesture.equals(Gesture.SINGLE_CLICK.toString())) {
            gesFunction = (String) SPUtil.get(context, gesture, GestureManager.FUNCTION_BACK);
        } else if (gesture.equals(Gesture.DOUBLE_CLICK.toString())) {
            gesFunction = (String) SPUtil.get(context, gesture, GestureManager.FUNCTION_HOME);
        } else if (gesture.equals(Gesture.LONG_PRESS.toString())) {
            gesFunction = (String) SPUtil.get(context, gesture, GestureManager.FUNCTION_LONGPRESS);
        } else if (gesture.equals(Gesture.FLING_UP.toString())) {
            gesFunction = (String) SPUtil.get(context, gesture, GestureManager.FUNCTION_RECENTS);
        } else if (gesture.equals(Gesture.FLING_DOWN.toString())) {
            gesFunction = (String) SPUtil.get(context, gesture, GestureManager.FUNCTION_NOTIFICATIONS);
        } else if (gesture.equals(Gesture.FLING_LEFT.toString())) {
            gesFunction = (String) SPUtil.get(context, gesture, GestureManager.FUNCTION_NO);
        } else if (gesture.equals(Gesture.FLING_RIGHT.toString())) {
            gesFunction = (String) SPUtil.get(context, gesture, GestureManager.FUNCTION_NO);
        }

    }

    public String getGesture() {
        return gesture;
    }

    public void setGesture(String gesture) {
        this.gesture = gesture;
    }

    public String getGesFunction() {
        return gesFunction;
    }

    public void setGesFunction(String gesFunction) {
        this.gesFunction = gesFunction;
    }

    public int getGesIcon() {
        return gesIcon;
    }

    public void setGesIcon(int gesIcon) {
        this.gesIcon = gesIcon;
    }
}
