package com.hkcyl.notepad.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hkcyl.notepad.bean.Reminder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yonglong on 2016/6/24.
 */
public class NoteDao {


    private Context mContext;
    private NoteDBHelper helper;
    private SQLiteDatabase db;
    private String sql;

    public NoteDao(Context mContext) {
        this.mContext = mContext;
        helper = new NoteDBHelper(mContext,NoteDBHelper.DB_NAME,null,1);
        db = helper.getReadableDatabase();

    }

    /**
     * 关闭数据库
     */
    public  void deactivate() {
        if (null != db && db.isOpen()) {
            db.close();
        }
        db = null;
    }
    /**
     * 插入数据
     */
    public void insertReminder(Reminder reminder){
        sql = "insert into notes ( title,content,start_time,end_time,is_active,is_remind,interval ) values(?,?,?,?,?,?,?)";
        db.execSQL(sql,new Object[]{reminder.getmTitle(), reminder.getmDescription()
        , reminder.getmStartTime(), reminder.getmEndTime(),reminder.isActive()
        , reminder.isReminder(),reminder.getInterval()});

    }
    /**
     * 查询全部
     */
    public List<Reminder> getAll(){
        List<Reminder> beanList = new ArrayList<>();
        Cursor cursor = getCursor();
        if (cursor.moveToFirst()){
            do{
                Reminder bean = new Reminder();
                bean.setmId(cursor.getInt(0));
                bean.setmTitle(cursor.getString(1));
                bean.setmDescription(cursor.getString(2));
                bean.setmStartTime(cursor.getString(3));
                bean.setmEndTime(cursor.getString(4));
                bean.setActive(cursor.getString(5));
                bean.setReminder(cursor.getString(6));
                bean.setInterval(cursor.getInt(7));
                beanList.add(bean);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return beanList;

    }
    /**
     * 删除指定数据
     */
    public void deleteReminder(Reminder reminder) {
        db.delete(helper.TABLE_NAME, helper.ID + "=?", new String[]{String.valueOf(reminder.getmId())});
    }

    /**
     * 更新指定数据
     */
    public void updateReminder(Reminder reminder){
        ContentValues values=new ContentValues();
        values.put(helper.TITLE,reminder.getmTitle());
        values.put(helper.CONTENT,reminder.getmDescription());
        values.put(helper.START_TIME,reminder.getmStartTime());
        values.put(helper.END_TIME,reminder.getmEndTime());
        values.put(helper.IS_ACTIVE,reminder.isActive());
        values.put(helper.IS_REMIND,reminder.isReminder());
        values.put(helper.INTERVAL,reminder.getInterval());

        db.update(helper.TABLE_NAME ,values,helper.ID+"=?",new String[]{String.valueOf(reminder.getmId())});
    }
    /**
     * 获取游标
     */
    public Cursor getCursor(){
        String[] columns = new String[] {
                helper.ID,
                helper.TITLE,
                helper.CONTENT,
                helper.START_TIME,
                helper.END_TIME,
                helper.IS_ACTIVE,
                helper.IS_REMIND,
                helper.INTERVAL
        };
        return db.query(helper.TABLE_NAME, columns, null, null, null, null,
                null);
    }
}
