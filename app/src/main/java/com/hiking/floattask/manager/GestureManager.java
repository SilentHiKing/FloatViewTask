package com.hiking.floattask.manager;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;

import com.hiking.floattask.utils.SPUtil;
import com.hiking.floattask.view.WaterDropView.Gesture;

/**
 * Created by Administrator on 2017/1/8.
 */
public class GestureManager {


    private static final String TAG = GestureManager.class.getSimpleName();
    public static final String FUNCTION_NO ="FUNCTION_NO";

    public static final String FUNCTION_LONGPRESS ="FUNCTION_MOVE";

    public static final String MOVE_X ="MOVE_X" ;
    public static final String MOVE_Y ="MOVE_Y" ;


    public static final String FUNCTION_HOME ="FUNCTION_HOME";
    public static final String FUNCTION_BACK ="FUNCTION_BACK";


    public static final String FUNCTION_RECENTS ="FUNCTION_RECENTS";
    public static final String FUNCTION_NOTIFICATIONS ="FUNCTION_NOTIFICATIONS";
    public static final String FUNCTION_LOCK_SCREEN ="FUNCTION_LOCK_SCREEN";




    private static Context mContext;

    private static GestureManager mGestureManager;


   

    public synchronized static GestureManager getInstance(Context context) {
        mGestureManager = new GestureManager(context);
        return mGestureManager;
    }

    private GestureManager(Context context) {
        mContext = context;
    }

    public void doGesture(Gesture gesture){

        Log.d(TAG,gesture.toString());
        String operation = null;

        switch (gesture){
            case LONG_PRESS:
                operation = (String) SPUtil.get(mContext,gesture.toString(),FUNCTION_LONGPRESS);
                break;
            case DOUBLE_CLICK:
                operation = (String) SPUtil.get(mContext,gesture.toString(),FUNCTION_HOME);
                break;
            case SINGLE_CLICK:
                operation = (String) SPUtil.get(mContext,gesture.toString(),FUNCTION_BACK);
                break;
            case FLING_UP:
                operation = (String) SPUtil.get(mContext,gesture.toString(),FUNCTION_RECENTS);
                break;
            case FLING_DOWN:
                operation = (String) SPUtil.get(mContext,gesture.toString(),FUNCTION_NOTIFICATIONS);
                break;
            case FLING_LEFT:
                operation = (String) SPUtil.get(mContext,gesture.toString(),FUNCTION_NO);
                break;
            case FLING_RIGHT:
                operation = (String) SPUtil.get(mContext,gesture.toString(),FUNCTION_NO);
                break;

        }
        operate(operation);
    }

    private void operate(String operation) {
        Log.d(TAG,operation);
        if(FUNCTION_NOTIFICATIONS.equals(operation)){
            OperationManager.getInstance(mContext).expandNotificationOperation();
        }
        else if(FUNCTION_BACK.equals(operation)){
            OperationManager.getInstance(mContext).goBackOperation();
        }
        else if(FUNCTION_RECENTS.equals(operation)){
            OperationManager.getInstance(mContext).showRecentsOperation();
        }
        else if(FUNCTION_HOME.equals(operation)){
            OperationManager.getInstance(mContext).goHomeOperation();
        }
        else if(FUNCTION_LONGPRESS.equals(operation)){
            OperationManager.getInstance(mContext).vibrateOperation();
        }
        else if(FUNCTION_NO.equals(operation)){
            //OperationManager.getInstance(mContext)
        }
        else if(FUNCTION_LOCK_SCREEN.equals(operation)){
            OperationManager.getInstance(mContext).lockScreenOperation();
        }
        /*else if(.equals(operation)){
            OperationManager.getInstance(mContext)
        }*/




    }

    /**
     * 悬浮球的移动
     * @param gesture
     * @param point
     */
    public void doGesture(Gesture gesture, Point point) {

        if (SPUtil.get(mContext,gesture.toString(),FUNCTION_LONGPRESS).toString().equals(FUNCTION_NO)){
            return;
        }
        Log.d(TAG,"move======== "+ point.toString());

        FloatWindowManager.getInstance().updateFloatView(point);
    }
}
