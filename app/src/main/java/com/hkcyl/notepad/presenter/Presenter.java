package com.hkcyl.notepad.presenter;

import android.content.Context;

import com.hkcyl.notepad.bean.EventBean;
import com.hkcyl.notepad.model.EventModelImpl;
import com.hkcyl.notepad.model.IEventModel;
import com.hkcyl.notepad.view.IEventView;

/**
 * Created by yonglong on 2016/6/24.
 */
public class Presenter {
    private IEventModel eventModel;
    private IEventView eventView;

    public Presenter(IEventView eventView) {
        this.eventView = eventView;

        eventModel = new EventModelImpl();
    }

    //供UI调用
    public void saveEvent(EventBean bean){
        eventModel.setEvent(bean);
    }
    //供UI调用
    public void getEvent(){
        //通过调用IEventView的方法来更新显示，设计模式运用
        //类似回调监听处理
        eventModel.getEvents();
    }


}
