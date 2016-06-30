package com.hkcyl.notepad.utils;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by yonglong on 2016/6/29.
 */
public class SharedPreferenceUtils  {

    /**
     * 判断是否通知
     * @param context
     * @return
     */
    public static Boolean getNotify(Context context){
        Boolean is=PreferenceManager.getDefaultSharedPreferences(context).getBoolean("pre_not",false);
        return is;
    }

    /**
     * 判断是否震动
     * @param context
     * @return
     */
    public static Boolean getVibrate(Context context){
        Boolean is=PreferenceManager.getDefaultSharedPreferences(context).getBoolean("pre_vib",false);
        return is;
    }
}
