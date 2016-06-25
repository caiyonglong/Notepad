package com.hkcyl.notepad.fragment;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.hkcyl.notepad.R;
import com.hkcyl.notepad.bean.EventBean;
import com.hkcyl.notepad.db.NoteDao;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 用于显示界面的 Pager ，通过设置不同的 adapter 实现布局复用
 */
public class HomePager extends BasePager  {

    @Bind(R.id.recycler_view)
    public RecyclerView recycler_view;
    private NoteDao dao;
    public EventAdapter eventAdapter;

    public HomePager(Activity mActivity) {
        super(mActivity);
    }

    @Override
    public View initView() {

        View view = View.inflate(mActivity, R.layout.pager_home, null);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void initData() {
        super.initData();


        //初始化数据
        dao = new NoteDao(mActivity);
        List eventList = new ArrayList<>();
        List<EventBean> alllist = dao.getAll();
       // mockList(eventList, alllist);

        // 初始化AgendaView的数据适配器
        eventAdapter = new EventAdapter(mActivity,alllist);
        recycler_view.setLayoutManager(new LinearLayoutManager(mActivity));
        recycler_view.setAdapter(eventAdapter);

    }
}
