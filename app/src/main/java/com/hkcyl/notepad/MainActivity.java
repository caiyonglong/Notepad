package com.hkcyl.notepad;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.hkcyl.notepad.fragment.ContentFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    //侧滑菜单
    @Bind(R.id.nav_view)
    NavigationView mNavigationView;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    public static FloatingActionButton add_event;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        init();
        initFab();


        initLayoutView();

    }


    /**
     * 初始化 Fab
     */
    private void initFab() {
        add_event = (FloatingActionButton) findViewById(R.id.add_event);
        add_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEventActivity.class);
                intent.putExtra("type","mainToAdd");
                startActivity(intent);
              //  finish();
            }
        });

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
            Intent intent = new Intent(this,SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initLayoutView();
    }
}
