package com.hkcyl.notepad.model;

import com.hkcyl.notepad.bean.EventBean;

import java.util.List;

/**
 * Created by yonglong on 2016/6/24.
 */
public interface IEventModel {
    //从数据库中获取数据
    List<EventBean> getEvents();
    //存入数据
    void setEvent(EventBean eventBean);
}
