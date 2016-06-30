package com.hkcyl.notepad.view;

import android.view.View;

import com.hkcyl.notepad.bean.Reminder;

import java.util.List;

/**
 * Created by yonglong on 2016/6/24.
 */
public interface HomePagerView {

    //改变
    void showChanged();
    //数据展示
    void showNotes(List<Reminder> reminders);
    //显示View
    void show(View view);
    //隐藏View
    void hide(View view);


}