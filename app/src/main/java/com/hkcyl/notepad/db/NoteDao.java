package com.hkcyl.notepad.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hkcyl.notepad.bean.EventBean;

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
    public void insertEvent(EventBean eventBean){
        sql = "insert into notes ( title,content,start_time,end_time ) values(?,?,?,?)";
        db.execSQL(sql,new Object[]{eventBean.getmTitle(),eventBean.getmDescription()
        ,eventBean.getmStartTime(),eventBean.getmEndTime()});

    }
    /**
     * 查询全部
     */
    public List<EventBean> getAll(){
        List<EventBean> beanList = new ArrayList<>();
        Cursor cursor = getCursor();
        if (cursor.moveToFirst()){
            do{
                EventBean bean = new EventBean();
                bean.setmId(cursor.getInt(0));
                bean.setmTitle(cursor.getString(1));
                bean.setmDescription(cursor.getString(2));
                bean.setmStartTime(cursor.getString(3));
                bean.setmEndTime(cursor.getString(4));
                beanList.add(bean);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return beanList;

    }
    /**
     * 删除指定数据
     */
    public void deleteDataById(EventBean eventBean) {
        db.delete(helper.TABLE_NAME, helper.ID + "=?", new String[]{String.valueOf(eventBean.getmId())});
    }

    /**
     * 更新指定数据
     */
    public void updateDataById(int id,EventBean bean){
        ContentValues values=new ContentValues();
        values.put(helper.TITLE,bean.getmTitle());
        values.put(helper.CONTENT,bean.getmDescription());
        values.put(helper.START_TIME,bean.getmStartTime());
        values.put(helper.END_TIME,bean.getmEndTime());
        db.update(helper.TABLE_NAME ,values,helper.ID+"=?",new String[]{id+""});
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
                helper.END_TIME
        };
        return db.query(helper.TABLE_NAME, columns, null, null, null, null,
                null);
    }
}
