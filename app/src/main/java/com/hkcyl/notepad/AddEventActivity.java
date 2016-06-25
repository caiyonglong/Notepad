package com.hkcyl.notepad;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hkcyl.notepad.bean.EventBean;
import com.hkcyl.notepad.db.NoteDao;

import java.text.Format;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yonglong on 2016/6/25.
 */
public class AddEventActivity extends AppCompatActivity {

    @Bind(R.id.alarm_title)
    EditText alarm_title;

    @Bind(R.id.alarm_date)
    TextView alarm_date;

    //开始时间
    @Bind(R.id.alarm_start_time)
    TextView alarm_start_time;

    //结束时间
    @Bind(R.id.alarm_end_time)
    TextView alarm_end_time;

    //描述
    @Bind(R.id.alarm_description)
    EditText alarm_description;
    //保存
    @Bind(R.id.tv_save)
    TextView tv_save;
    //取消
    @Bind(R.id.left_clear)
    ImageButton left_clear;

    NoteDao dao;

    @OnClick(R.id.left_clear)
    void clear(){
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
    @OnClick(R.id.tv_save)
    void save(){
        EventBean beanlist = new EventBean();
        beanlist.setmTitle(alarm_title.getText().toString()+"|");
        beanlist.setmDescription(alarm_description.getText().toString()+"=");
        beanlist.setmStartTime(alarm_start_time.getText().toString()+":20");
        beanlist.setmEndTime(alarm_end_time.getText().toString()+":30");
        dao.insertEvent(beanlist);

        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
    @OnClick(R.id.alarm_start_time)
    void start(){
        alarm_start_time.setText(getNowtime());
    }
    @OnClick(R.id.alarm_end_time)
    void end(){
        alarm_end_time.setText(getNowtime());
    }
    @OnClick(R.id.alarm_date)
    void date(){
        alarm_date.setText(getNowDate());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        ButterKnife.bind(this);
        dao = new NoteDao(this);

    }




    public String getNowDate(){
        String date= "";
        Calendar c = Calendar.getInstance();
        date = c.get(Calendar.YEAR)+"年"+
                c.get(Calendar.MONTH)+"月"+
                c.get(Calendar.DAY_OF_MONTH)+"日";
        return date;
    }
    public String getNowtime(){
        String date= "";
        Calendar c = Calendar.getInstance();
        date = c.get(Calendar.HOUR_OF_DAY)+":"+
                c.get(Calendar.MINUTE);
        return date;
    }
}
