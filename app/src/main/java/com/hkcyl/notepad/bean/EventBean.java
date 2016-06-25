package com.hkcyl.notepad.bean;

/**
 * Created by yonglong on 2016/6/24.
 */
public class EventBean {
    /**
     * 事件ID
     */
    private int mId;
    /**
     * 事件标题
     */
    private String mTitle;
    /**
     * 事件描述
     */
    private String mDescription;
    /**
     * 事件开始时间
     */
    private String mStartTime;
    /**
     * 事件结束时间
     */
    private String mEndTime;


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
}
