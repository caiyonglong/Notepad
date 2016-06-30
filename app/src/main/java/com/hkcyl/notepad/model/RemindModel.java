package com.hkcyl.notepad.model;

import com.hkcyl.notepad.bean.Reminder;

import java.util.List;

/**
 * Created by yonglong on 2016/6/24.
 */
public interface RemindModel {

    /**
     * 获取所有数据
     * @return
     */
    List<Reminder> getRemindersList();

    /**
     * 新增数据
     */
    void saveReminder(Reminder reminder);

    /**
     * 删除数据
     */
    void deleteReminder(Reminder reminder);
    /**
     * 更新数据
     */
    void updateReminder(Reminder reminder);


}
