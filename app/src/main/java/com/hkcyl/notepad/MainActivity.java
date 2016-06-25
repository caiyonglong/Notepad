package com.hkcyl.notepad;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.hkcyl.notepad.bean.EventBean;
import com.hkcyl.notepad.db.NoteDao;
import com.hkcyl.notepad.fragment.ContentFragment;
import com.hkcyl.notepad.presenter.Presenter;
import com.hkcyl.notepad.view.IEventView;
import com.wangjie.androidbucket.utils.ABTextUtil;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener, IEventView {

    //侧滑菜单
    @Bind(R.id.nav_view)
    NavigationView mNavigationView;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    //悬浮按钮
    @Bind(R.id.fab_layout)
    RapidFloatingActionLayout fab_layout;
    @Bind(R.id.fab_button_group)
    RapidFloatingActionButton fab_button_group;
    private RapidFloatingActionHelper rfabHelper;
    Presenter presenter;
    NoteDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initData();
        init();
        initFab();


        presenter = new Presenter(this);
        initLayoutView();

    }

    private void initData() {
        dao = new NoteDao(this);

    }

    /**
     * 初始化 Fab
     */
    private void initFab() {
        RapidFloatingActionContentLabelList rfaContent = new RapidFloatingActionContentLabelList(this);
        rfaContent.setOnRapidFloatingActionContentLabelListListener(this);
        List<RFACLabelItem> items = new ArrayList<>();
        items.add(new RFACLabelItem<Integer>()
                .setLabel("新建活动")
                .setResId(R.drawable.ic_access_alarms_white_24dp)
                .setIconNormalColor(0xffd84315)
                .setIconPressedColor(0xffbf360c)
                .setLabelSizeSp(14)
                .setWrapper(0)
        );

        rfaContent
                .setItems(items)
                .setIconShadowRadius(5)
                .setIconShadowColor(0xff888888)
                .setIconShadowDy(5)
        ;
        rfabHelper = new RapidFloatingActionHelper(
                this,
                fab_layout,
                fab_button_group,
                rfaContent
        ).build();

    }

    @Override
    public void onRFACItemLabelClick(int i, RFACLabelItem rfacLabelItem) {
        Toast.makeText(this, "Click Label !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRFACItemIconClick(int i, RFACLabelItem rfacLabelItem) {
        if (i == 0) {
            Intent intent = new Intent(MainActivity.this, AddEventActivity.class);
            intent.putExtra("type","mainToAdd");
            startActivity(intent);
            finish();
            Toast.makeText(this, "activity", Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * 得到侧滑菜单
     */
    public NavigationView getNavigationView(){
        return mNavigationView;
    }
    /**
     * 得到DrawerLayout
     */
    public DrawerLayout getDrawerLayout(){
        return mDrawerLayout;
    }


    /**
     * 初始化布局
     */
    private void initLayoutView() {
        //初始化 CalendarView,设置颜色
    //填充 Fragment
        FragmentManager manager =getFragmentManager();

        FragmentTransaction transaction = manager.beginTransaction();

        ContentFragment fragment = new ContentFragment();

        transaction.replace(R.id.main_frame,fragment, "CONTENT_FRAGMENT");

        transaction.commit();

    }

    private void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public List<EventBean> getEvents() {
        return dao.getAll();
    }

    @Override
    public void setEvent(EventBean eventBean) {
        dao.insertEvent(eventBean);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }
}
