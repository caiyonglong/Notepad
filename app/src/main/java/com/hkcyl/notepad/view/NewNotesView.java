package com.hkcyl.notepad.view;

import android.view.View;

import com.hkcyl.notepad.bean.Reminder;

/**
 * Created by yonglong on 2016/6/30.
 */
public interface NewNotesView {

    //获取时间
    void getTime(View v);
    //获取日期
    void getDate(View v);
    //保存&更新
    void saveNote(Reminder reminder,int flag);
    //传递Reminder
    void showNote(Reminder reminder);
}
