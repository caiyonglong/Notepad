package com.hkcyl.notepad.utils;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.NotificationCompat;

import com.hkcyl.notepad.AddEventActivity;
import com.hkcyl.notepad.MainActivity;
import com.hkcyl.notepad.R;

/**
 * Created by yonglong on 2016/6/27.
 */
public class RemindHelper {

    /**
     * 震动
     *
     * @param context
     * @param milliseconds
     */
    public static void Vibrate(Context context, long milliseconds) {
        Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
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
        NotificationManager manager = (NotificationManager) activity.getSystemService(Service.NOTIFICATION_SERVICE);



    }

    public static void setAlarmTime(Context context, long timeInMillis) {

        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent("android.alarm.notepad.action");
        intent.putExtra("value","test");

        PendingIntent sender = PendingIntent.getBroadcast(

                context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        int interval = 60 * 1000;//闹铃间隔， 这里设为1分钟闹一次，在第2步我们将每隔1分钟收到一次广播

        am.setRepeating(AlarmManager.RTC_WAKEUP, timeInMillis, interval, sender);

    }


}
