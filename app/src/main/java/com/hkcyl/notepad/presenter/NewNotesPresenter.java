package com.hkcyl.notepad.presenter;

import android.content.Context;
import android.view.View;

import com.hkcyl.notepad.bean.Reminder;
import com.hkcyl.notepad.model.RemindModel;
import com.hkcyl.notepad.model.RemindModelImpl;
import com.hkcyl.notepad.view.NewNotesView;

import java.util.List;

/**
 * Created by yonglong on 2016/6/30.
 */
public class NewNotesPresenter {
    private NewNotesView newNotesView;
    private RemindModel remindModel;
    public static int PRESENTER_NOTE_SAVE = 1;
    public static int PRESENTER_NOTE_UPDATER = 2;

    public NewNotesPresenter(NewNotesView newNotesView, Context context) {
        this.newNotesView = newNotesView;
        remindModel = new RemindModelImpl(context);
    }

    public void getTime(View v){
        newNotesView.getTime(v);
    }
    public void getDate(View v){
        newNotesView.getDate(v);
    }
    public void saveNote(Reminder reminder,int flag){

        newNotesView.saveNote(reminder,flag);
        //保存&更新数据
        if (flag ==PRESENTER_NOTE_SAVE){
            remindModel.saveReminder(reminder);
        }else if (flag ==PRESENTER_NOTE_UPDATER){
            remindModel.updateReminder(reminder);
        }
    }

    public void shoNote(Reminder reminder){
        newNotesView.showNote(reminder);
    }


//
//    /**
//     * 数据操作
//     * @param reminder
//     */
//
//    public void deleteRemind(Reminder reminder){
//        remindModel.deleteReminder(reminder);
//    }
//
//    public void saveRemind(Reminder reminder){
//        remindModel.saveReminder(reminder);
//    }
//
//    public void updateRemind(Reminder reminder){
//        remindModel.updateReminder(reminder);
//    }
//
//    public List<Reminder> getAllReminder(){
//        return  remindModel.getRemindersList();
//    }
}
