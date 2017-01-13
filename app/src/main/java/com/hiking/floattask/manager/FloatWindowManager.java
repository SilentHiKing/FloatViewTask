package com.hiking.floattask.manager;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

import com.hiking.floattask.utils.PointCalUtil;
import com.hiking.floattask.utils.SPUtil;
import com.hiking.floattask.view.CusGestureOperationListener;
import com.hiking.floattask.view.FloatView;

/**
 * Created by Administrator on 2017/1/7.
 */
public class FloatWindowManager {

    private static final String TAG = FloatWindowManager.class.getSimpleName();
    public static final String SWITCH = "SWITCH";
    public static final String ON = "ON";
    public static final String OFF = "OFF";
    private static Context mContext;
    private static FloatWindowManager mFloatWindowManager;
    private WindowManager mWindowManager;
    private FloatView mFloatView;
    private LayoutParams mParams;


    /**
     * 必须调用初始化数据的方法initData(Context context)
     * @return
     */
    public synchronized static FloatWindowManager getInstance(){
        
        if (mFloatWindowManager == null){
            mFloatWindowManager = new FloatWindowManager();
        }
    
        return mFloatWindowManager;
    }

    public void initData(Context context){
        mContext = context;

        if(null == mFloatView){
            mFloatView = new FloatView(mContext);
            mFloatView.setGestureOperationListener(new CusGestureOperationListener(GestureManager.getInstance(mContext)));
        }
    }

    public void addFloatView(){
        Log.d(TAG,"addFloatView");
        SPUtil.put(mContext,SWITCH,ON);
        mFloatView.add();
    }

    public void removeFloatView(){

        SPUtil.put(mContext,SWITCH,OFF);
        mFloatView.remove();
    }

    public void updateFloatView(Point point){
        Log.d("GestureManager","update--------- "+point.toString());
        mFloatView.update(point);
    }


}
