package com.hkcyl.notepad.view;

import com.hkcyl.notepad.bean.EventBean;

import java.util.List;

/**
 * Created by yonglong on 2016/6/24.
 */
public interface IEventView {
    //给UI提供数据
    List<EventBean> getEvents();
    //从UI取数据
    void setEvent(EventBean eventBean);
}