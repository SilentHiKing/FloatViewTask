package com.hiking.floattask.view;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2017/1/10.
 */
public class FloatView extends FloatViewDecorate {

    private WaterDropView mWaterDropView;
    private Context mContext;

    public FloatView(Context context){
        mWaterDropView = new WaterDropView(context);
        initData(context);
        mContext = context;
    }

    public WaterDropView getWaterDropView(){
        return mWaterDropView;
    }


    @Override
    public void setViewWidthAndHeight() {
        mParams.width = mParams.height = mWaterDropView.getViewWidth();

    }

    @Override
    public void add() {
        addFloatView(mWaterDropView);
    }


    @Override
    public void remove() {
        removeFloatView(mWaterDropView);
    }

    @Override
    public void update(Point point) {
        updateFloatView(mWaterDropView,point);
    }

    public void setGestureOperationListener(CusGestureOperationListener cusGestureOperationListener){
        mWaterDropView.setGestureOperationListener(cusGestureOperationListener);
    }
}
