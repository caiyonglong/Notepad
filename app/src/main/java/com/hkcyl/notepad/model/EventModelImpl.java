package com.hkcyl.notepad.model;

import android.content.Context;

import com.hkcyl.notepad.MainActivity;
import com.hkcyl.notepad.bean.EventBean;
import com.hkcyl.notepad.db.NoteDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yonglong on 2016/6/24.
 */
public class EventModelImpl implements IEventModel {


    private List<EventBean> beanList;



    @Override
    public List<EventBean> getEvents() {

        beanList =new ArrayList<>();

        return beanList;
    }

    @Override
    public void setEvent(EventBean event) {

    }
}
