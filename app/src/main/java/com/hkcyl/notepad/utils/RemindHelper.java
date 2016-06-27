package com.hkcyl.notepad.utils;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.Service;
import android.os.Vibrator;

/**
 * Created by yonglong on 2016/6/27.
 */
public class RemindHelper {

    /**
     * 震动
     *
     * @param activity
     * @param milliseconds
     */
    public static void Vibrate(final Activity activity, long milliseconds) {
        Vibrator vib = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(milliseconds);
    }


    public static void Vibrate(final Activity activity, long[] pattern, boolean isRepeat) {
        Vibrator vib = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(pattern, isRepeat ? 1 : -1);
    }

    /**
     * 通知
     */
    public static void notify(final Activity activity){
        NotificationManager notificationManager = (NotificationManager) activity.getSystemService(Service.NOTIFICATION_SERVICE);
        notificationManager.notify();

    }
}
