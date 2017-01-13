package com.hiking.floattask.operation;

import android.content.Context;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2017/1/12.
 */
public class NotificationOperation extends AOperation {
    private NotificationOperation(Context context) {
        super(context);
    }
    public static NotificationOperation mAOperation;
    public synchronized static NotificationOperation getInstance(Context context) {
        if (mAOperation ==null){
            mAOperation = new NotificationOperation(context);
        }
        return mAOperation;
    }
    @Override
    public void operate() {
        expandNotification();
    }

    //折叠通知栏：
    public void collapsingNotification() {

        Object service = mContext.getSystemService("statusbar");

        if (null == service)

            return;

        try {

            Class<?> clazz = Class.forName("android.app.StatusBarManager");

            int sdkVersion = android.os.Build.VERSION.SDK_INT;

            Method collapse = null;

            if (sdkVersion <= 16) {

                collapse = clazz.getMethod("collapse");

            } else {

                collapse = clazz.getMethod("collapsePanels");

            }



            collapse.setAccessible(true);

            collapse.invoke(service);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }



    //展开通知栏：
    public void expandNotification() {

        Object service = mContext.getSystemService("statusbar");
        if (null == service)

            return;

        try {

            Class<?> clazz = Class.forName("android.app.StatusBarManager");

            int sdkVersion = android.os.Build.VERSION.SDK_INT;

            Method expand = null;

            if (sdkVersion <= 16) {

                expand = clazz.getMethod("expand");

            } else {

          /*

           * Android SDK 16之后的版本展开通知栏有两个接口可以处理

           * expandNotificationsPanel()

           * expandSettingsPanel()

           */

                //expand =clazz.getMethod("expandNotificationsPanel");

                expand = clazz.getMethod("expandSettingsPanel");

            }



            expand.setAccessible(true);

            expand.invoke(service);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }
}
