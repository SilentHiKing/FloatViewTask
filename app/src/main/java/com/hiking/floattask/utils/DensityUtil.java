package com.hiking.floattask.utils;

import android.content.Context;
import android.util.TypedValue;

public class DensityUtil {
  
    /** 
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素) 
     */  
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dpValue * scale + 0.5f);  
    }  
  
    /** 
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 
     */  
    public static int px2dip(Context context, float pxValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (pxValue / scale + 0.5f);  
    }


    /*
     *此为第二种实现方式
     *
    public static int dipTpx(Context context, float dip) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources().getDisplayMetrics()
        );
    }

    public static int pxTdip(Context context, float px) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_PX, px, context.getResources().getDisplayMetrics()
        );
    }
    */


}