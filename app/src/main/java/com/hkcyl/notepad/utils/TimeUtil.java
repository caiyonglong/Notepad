package com.hkcyl.notepad.utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by yonglong on 2016/7/8.
 */
public class TimeUtil {
    public static String getTime(long TimeInMillis) {
        Calendar calendar =Calendar.getInstance();
        calendar.setTimeInMillis(TimeInMillis);
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");

        Log.e("++++++",df.format(calendar.getTime()));

        return df.format(calendar.getTime());
    }
}

