package com.hkcyl.notepad.bean;

import java.io.Serializable;

/**
 * Created by yonglong on 2016/6/24.
 */
public class Reminder implements Serializable{
    /**
     * ID
     */
    private int mId;
    /**
     * 标题
     */
    private String mTitle;
    /**
     * 描述
     */
    private String mDescription;
    /**
     * 开始时间
     */
    private String mStartTime;
    /**
     * 结束时间
     */
    private String mEndTime;

    /**
     * 周期
     */
    private int interval;
    /**
     * 是否活动
     */
    private String isActive;
    /**
     * 是否是提醒
     */
    private String isReminder;

    public String isReminder() {
        return isReminder;
    }

    public void setReminder(String reminder) {
        isReminder = reminder;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmEndTime() {
        return mEndTime;
    }

    public void setmEndTime(String mEndTime) {
        this.mEndTime = mEndTime;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmStartTime() {
        return mStartTime;
    }

    public void setmStartTime(String mStartTime) {
        this.mStartTime = mStartTime;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public String isActive() {
        return isActive;
    }

    public void setActive(String active) {
        isActive = active;
    }
}
