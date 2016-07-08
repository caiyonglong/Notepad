package com.hkcyl.notepad;

import android.content.Intent;
import android.os.Bundle;
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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.hkcyl.notepad.bean.Reminder;
import com.hkcyl.notepad.presenter.NewNotesPresenter;
import com.hkcyl.notepad.utils.RemindHelper;
import com.hkcyl.notepad.view.NewNotesView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnItemSelected;

/**
 * Created by yonglong on 2016/6/25.
 */
public class AddRemindActivity extends AppCompatActivity implements NewNotesView {

    public static final String EXTRA_REMINDER_ID = "id";
    private android.app.TimePickerDialog mStartTimePicker;
    private long sTime = 0, eTime = 0;
    private NewNotesPresenter presenter;

    private Reminder reminder;
    //flag =1 新增,flag =2 更新
    private int flag = 1;
    private String mTitle;

    @Bind(R.id.alarm_title)
    EditText alarm_title;

    @Bind(R.id.alarm_date)
    TextView alarm_date;

    //开始时间
    @Bind(R.id.alarm_start_time)
    TextView alarm_start_time;

    //周期
    @Bind(R.id.alarm_interval)
    Spinner alarm_interval;

    //结束时间
    @Bind(R.id.alarm_end_time)
    TextView alarm_end_time;

    //描述
    @Bind(R.id.alarm_description)
    EditText alarm_description;


    @OnClick(R.id.alarm_start_time)
    void start() {
        presenter.getTime(alarm_start_time);
        mStartTimePicker.show();
    }

    @OnClick(R.id.alarm_end_time)
    void end() {
        presenter.getTime(alarm_end_time);
        mStartTimePicker.show();
    }

    @OnItemSelected(R.id.alarm_interval)
    void onItemSelected(int position) {

        Toast.makeText(this, "position: " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        ButterKnife.bind(this);

        presenter = new NewNotesPresenter(this, this);
        initedit();

    }

    private void initedit() {
        Intent intent = getIntent();
        String action = intent.getStringExtra("action");
        if (MainActivity.NOTES_EDIT.equals(action)) {
            flag = 2;
            reminder = (Reminder) intent.getSerializableExtra("reminder");
            presenter.shoNote(reminder);
            init("编辑");
        } else if (MainActivity.NOTES_NEW_REMIND.equals(action)) {
            flag = 1;
            reminder =new Reminder();
            init("新增");
        }

    }

    private void init(String title) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Setup Notes Title EditText

        alarm_title.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTitle = s.toString().trim();
                Log.e("mTitle",mTitle+"===");
                alarm_title.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        //初始化日期
        presenter.getDate(alarm_date);
        //setDate();
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
                if (alarm_title.getText().toString().length() == 0) {
                    alarm_title.setError(Html.fromHtml("<font color= 'blue'>标题不能为空</font>"));
                } else {

                    reminder.setmTitle(alarm_title.getText().toString().trim());
                    reminder.setmStartTime(sTime+"");
                    reminder.setmEndTime(eTime+"");
                    reminder.setmDescription(alarm_description.getText().toString() + "");
                    presenter.saveNote(reminder, flag);
//                    saveEvent();
                }

                return true;
            case R.id.clear_event:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void getTime(final View v) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        mStartTimePicker = new android.app.TimePickerDialog(this, new android.app.TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                SimpleDateFormat df = new SimpleDateFormat("HH:mm");
                if (v.getId() == R.id.alarm_start_time) {
                    sTime = calendar.getTimeInMillis();
                  //  reminder.setmStartTime(df.format(calendar.getTime()));
                    alarm_start_time.setText("开始时间:  " + df.format(calendar.getTime()));
                } else if (v.getId() == R.id.alarm_end_time) {
                    eTime = calendar.getTimeInMillis();

                   // reminder.setmEndTime(df.format(calendar.getTime()));
                    alarm_end_time.setText("结束时间:  " + df.format(calendar.getTime()));
                }


            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
    }

    @Override
    public void getDate(View v) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日  EE");

        alarm_date.setText(df.format(calendar.getTime()));
    }


    @Override
    public void saveNote(Reminder reminder, int flag) {
        if (sTime != 0 && eTime != 0) {
            RemindHelper.setAlarmTime(getApplicationContext(), sTime, reminder, (int) System.currentTimeMillis());
            RemindHelper.setAlarmTime(getApplicationContext(), sTime, reminder, (int) System.currentTimeMillis()-1);
            //      RemindHelper.setAlarmTime(getApplicationContext(),eTime,beanlists);
        }
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void showNote(Reminder reminder) {

        mTitle = reminder.getmTitle();
        alarm_title.setText(reminder.getmTitle());
        alarm_description.setText(reminder.getmDescription());
        alarm_start_time.setText(reminder.getmStartTime());
        alarm_end_time.setText(reminder.getmEndTime());
    }
}