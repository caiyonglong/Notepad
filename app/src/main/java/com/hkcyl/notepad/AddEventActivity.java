package com.hkcyl.notepad;

import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.hkcyl.notepad.bean.EventBean;
import com.hkcyl.notepad.db.NoteDao;
import com.hkcyl.notepad.utils.RemindHelper;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yonglong on 2016/6/25.
 */
public class AddEventActivity extends AppCompatActivity{

    public static final String EXTRA_REMINDER_ID = "id";
    private android.app.DatePickerDialog mDataPicker;
    private android.app.TimePickerDialog mStartTimePicker, mEndTimePicker;
    private long sTime= 0,eTime = 0;

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

    NoteDao dao;

    private boolean isVibrate = false;
    private String mTitle;



    @OnClick(R.id.alarm_start_time)
    void start() {
        getStartTimePickerDialog();

        mStartTimePicker.show();
    }

    @OnClick(R.id.alarm_end_time)
    void end() {
        getEndTimePickerDialog();
        mEndTimePicker.show();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        ButterKnife.bind(this);

        dao = new NoteDao(this);
        initedit();

    }
    private EventBean beanlists;
    private Boolean isEditModel = false;
    private void initedit() {
        Intent intent = getIntent();
        if (intent.getStringExtra("from")!=null){
            isEditModel =true;
            beanlists = new EventBean();
            beanlists.setmId(intent.getIntExtra("id",0));
            beanlists.setmTitle(intent.getStringExtra("title"));
            beanlists.setmDescription(intent.getStringExtra("content"));
            beanlists.setmStartTime(intent.getStringExtra("start_time"));
            beanlists.setmEndTime(intent.getStringExtra("end_time"));

            mTitle= beanlists.getmTitle();

            alarm_title.setText(beanlists.getmTitle());
            alarm_description.setText(beanlists.getmDescription());
            alarm_start_time.setText(beanlists.getmStartTime());
            alarm_end_time.setText(beanlists.getmEndTime());

            init("编辑");
        }else {
            init("新增");
        }

    }

    private void init(String title) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Setup Reminder Title EditText

        alarm_title.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTitle = s.toString().trim();
                alarm_title.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        //初始化日期
        setDate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_add, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            // On clicking the back arrow
            // Discard any changes
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.save_event:
                alarm_title.setText(mTitle);

                if (alarm_title.getText().toString().length() == 0){
                    alarm_title.setError(Html.fromHtml("<font color= 'blue'>标题不能为空</font>"));
                }

                else {
                    saveEvent();
                }

                return true;
            case R.id.clear_event:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveEvent() {
        if (isEditModel){
            Log.e("datas",beanlists.toString());
            beanlists.setmTitle(alarm_title.getText().toString() + "");
            beanlists.setmDescription(alarm_description.getText().toString() + "");
            beanlists.setmStartTime(alarm_start_time.getText().toString() + "");
            beanlists.setmEndTime(alarm_end_time.getText().toString() + "");
            dao.updateDataById(beanlists);

            if (sTime!=0&&eTime!=0){
                RemindHelper.setAlarmTime(getApplicationContext(),sTime);
             //   RemindHelper.setAlarmTime(getApplicationContext(),eTime,beanlists.getmId());

            }
        }else {
            EventBean beanlist = new EventBean();
            beanlist.setmTitle(alarm_title.getText().toString() + "");
            beanlist.setmDescription(alarm_description.getText().toString() + "");
            beanlist.setmStartTime(alarm_start_time.getText().toString() + "");
            beanlist.setmEndTime(alarm_end_time.getText().toString() + "");
            if (sTime!=0&&eTime!=0){

                RemindHelper.setAlarmTime(getApplicationContext(),sTime);
             //   RemindHelper.setAlarmTime(getApplicationContext(),eTime);

            }
            dao.insertEvent(beanlist);



        }
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }


    /**
     * 初始化日期
     */
    private void setDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日  EE");

        alarm_date.setText(df.format(calendar.getTime()));
    }

    /**
     * 获取开始时间选择器
     */
    private void getStartTimePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        mStartTimePicker = new android.app.TimePickerDialog(this, new android.app.TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                SimpleDateFormat df = new SimpleDateFormat("HH:mm");
                sTime = calendar.getTimeInMillis();
                alarm_start_time.setText("开始时间:  " + df.format(calendar.getTime()));

            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
    }

    /**
     * 获取结束时间选择器
     */
    private void getEndTimePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        mEndTimePicker = new android.app.TimePickerDialog(this, new android.app.TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                SimpleDateFormat df = new SimpleDateFormat("HH:mm");
                eTime = calendar.getTimeInMillis();
                alarm_end_time.setText("结束时间:  " + df.format(calendar.getTime()));

            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
    }

}