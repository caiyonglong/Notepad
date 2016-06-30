package com.hkcyl.notepad.presenter;

import android.content.Context;
import android.view.View;

import com.hkcyl.notepad.bean.Reminder;
import com.hkcyl.notepad.model.RemindModelImpl;
import com.hkcyl.notepad.model.RemindModel;
import com.hkcyl.notepad.view.HomePagerView;

import java.util.List;

/**
 * Created by yonglong on 2016/6/24.
 */
public class NotesPresenter{
    private HomePagerView homePagerView;
    private RemindModel remindModel;

    public NotesPresenter(HomePagerView homePagerView, Context context) {
        this.homePagerView = homePagerView;
        remindModel = new RemindModelImpl(context);
    }


    public void showReminds(){
        List<Reminder> reminders = remindModel.getRemindersList();
        homePagerView.showNotes(reminders);
    }
    public void show(View v){
        homePagerView.show(v);
    }
    public void hide(View v){
        homePagerView.hide(v);
    }

    public void showchanged(){
        homePagerView.showChanged();
    }




    /**
     * 数据操作
     * @param reminder
     */

    public void deleteRemind(Reminder reminder){
        remindModel.deleteReminder(reminder);
    }

    public void saveRemind(Reminder reminder){
        remindModel.saveReminder(reminder);
    }

    public void updateRemind(Reminder reminder){
        remindModel.updateReminder(reminder);
    }

    public List<Reminder> getAllReminder(){
        return  remindModel.getRemindersList();
    }

}
