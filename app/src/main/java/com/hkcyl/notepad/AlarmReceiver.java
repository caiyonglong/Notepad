package com.hkcyl.notepad;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.hkcyl.notepad.bean.Reminder;
import com.hkcyl.notepad.utils.RemindHelper;
import com.hkcyl.notepad.utils.SharedPreferenceUtils;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Reminder reminder = (Reminder) intent.getSerializableExtra("reminder");
        Log.e("test",reminder.getmDescription()+"=====");
        Log.e("test",reminder.getmStartTime()+"=====");
        int mReceivedID = 0;

        if (SharedPreferenceUtils.getVibrate(context)) {
            RemindHelper.Vibrate(context, 1000);
        }
        String mTitle = reminder.getmTitle();

        // Create intent to open ReminderEditActivity on notification click
        Intent editIntent = new Intent(context, AddRemindActivity.class);
        editIntent.putExtra(AddRemindActivity.EXTRA_REMINDER_ID, Integer.toString(mReceivedID));
        PendingIntent mClick = PendingIntent.getActivity(context, mReceivedID, editIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        // Create Notification
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle(context.getResources().getString(R.string.app_name))
                .setTicker(mTitle)
                .setContentText(mTitle)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(mClick)
                .setAutoCancel(true)
                .setOnlyAlertOnce(false);

        NotificationManager nManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nManager.notify(mReceivedID, mBuilder.build());
    }


}
