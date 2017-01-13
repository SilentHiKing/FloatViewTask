package com.hiking.floattask.utils;

import android.content.Context;
import android.graphics.Point;

import com.hiking.floattask.manager.GestureManager;

/**
 * Created by Administrator on 2017/1/7.
 */
public class PointCalUtil {

    /**
     * 计算a相对于b点的角度
     * @param a
     * @param b
     * @return 范围在-180~180
     */
    public static int getAngle(Point a,Point b) {
        return ((int)Math.toDegrees(Math.atan2(a.y - b.y, a.x - b.x)));
    }

    /**
     * 计算两点之间的距离
     * @param a
     * @param b
     * @return
     */
    public static int getInstance(Point a,Point b){
        int dx = Math.abs(a.x - b.x);
        int dy = Math.abs(a.y - b.y);
        return (int)Math.sqrt(Math.pow(dx, 2.0D) + Math.pow(dy, 2.0D));
    }

    public static Point getPostion(Context context){
        int mX= (int)SPUtil.get(context, GestureManager.MOVE_X,0);
        int mY= (int)SPUtil.get(context,GestureManager.MOVE_Y,0);
        return new Point(mX,mY);
    }
}
