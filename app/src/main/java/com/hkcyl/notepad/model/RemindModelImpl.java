package com.hkcyl.notepad.model;

import android.content.Context;

import com.hkcyl.notepad.bean.Reminder;
import com.hkcyl.notepad.db.NoteDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yonglong on 2016/6/24.
 */
public class RemindModelImpl implements RemindModel {



    private List<Reminder> reminds = new ArrayList<>();
    private Context context;
    private NoteDao dao;

    public RemindModelImpl(Context context) {
        this.context = context;
        dao = new NoteDao(context);
    }

    @Override
    public List<Reminder> getRemindersList() {
        reminds =dao.getAll();

        return reminds;
    }

    @Override
    public void saveReminder(Reminder reminder) {
        dao.insertReminder(reminder);
    }

    @Override
    public void deleteReminder(Reminder reminder) {
        dao.deleteReminder(reminder);
    }

    @Override
    public void updateReminder(Reminder reminder) {
        dao.updateReminder(reminder);
    }


}
