package com.hiking.floattask.view;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

import com.hiking.floattask.utils.PointCalUtil;

/**
 * Created by Administrator on 2017/1/10.
 */
public abstract class FloatViewDecorate {
    private static final String TAG = "GestureManager";
    private Context mContext;

    public static LayoutParams mParams;
    protected WindowManager mWindowManager;

    protected void initData(Context context){
        mContext =context;
        initWindowManager(context);
        initFloatLayoutParams();
    }

    protected  void initWindowManager(Context context){
        if (null == mWindowManager){
            mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }
    }

    protected void initFloatLayoutParams(){
        if (mParams ==null){
            mParams = new WindowManager.LayoutParams();
            mParams.gravity = Gravity.LEFT | Gravity.TOP;

            mParams.type = LayoutParams.TYPE_SYSTEM_ALERT;
            mParams.format = PixelFormat.RGBA_8888;
            mParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                    | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    | LayoutParams.FLAG_HARDWARE_ACCELERATED;

            Log.d(TAG,"initFloatLayoutParams");



        }
        setViewWidthAndHeight();




        /*mParams.height = mParams.width = mWaterDropView.getViewWidth();
        Log.d(TAG,"height "+ mWaterDropView.getViewWidth());
            *//*mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
            mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;*/
    }

    protected void addFloatView(View view){
        initPostion();
        view.setLayoutParams(mParams);
        mWindowManager.addView(view,mParams);
    }

    protected void removeFloatView(View view){
        mWindowManager.removeView(view);
    }

    protected void updateFloatView(View view,Point point){
        mParams.x = point.x;
        mParams.y = point.y;
        Log.d("GestureManager",point.toString());
        mWindowManager.updateViewLayout(view,mParams);
    }

    private void initPostion(){
        Point p = PointCalUtil.getPostion(mContext);
        mParams.x = p.x;
        mParams.y = p.y;
        Log.d(TAG,p.toString());
    }
    
    

    public abstract void setViewWidthAndHeight();

    public abstract void add();

    public abstract void remove();

    public abstract void update(Point point);
    
    
}
