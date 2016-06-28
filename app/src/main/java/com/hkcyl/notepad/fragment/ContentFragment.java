package com.hkcyl.notepad.fragment;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.hkcyl.notepad.MainActivity;
import com.hkcyl.notepad.R;
import com.hkcyl.notepad.bean.EventBean;
import com.hkcyl.notepad.db.NoteDao;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by yonglong on 2016/6/25.
 */
public class ContentFragment extends BaseFragment {

    @Bind(R.id.vp_content)
    ViewPager vpContent;
    private List<BasePager> mPageList;
    private NavigationView navigationView;//菜单栏
    private DrawerLayout drawerLayout;//DrawerLayout


    private  List<EventBean> eventList;
    private NoteDao dao;
    private HomePager homePager;



    @Override
    public View initView() {

        View view=View.inflate(mActivity, R.layout.fragment_content,null);

        ButterKnife.bind(this,view);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void initDate() {

        homePager = new HomePager(mActivity);


        //主界面添加数据
        mPageList= new ArrayList<>();

        mPageList.add(homePager);

        vpContent.setAdapter(new VpContentAdapter());

        //获取侧边栏
        MainActivity mainUi= (MainActivity) mActivity;
        buildHomePager();


    }

       /**
     * 主界面设置
     */
    private void buildHomePager(){
        homePager.initData();

    }


    /**
     * viewPager数据适配器
     */
    class VpContentAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mPageList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BasePager pager =mPageList.get(position);
            container.addView(pager.mRootView);
            return pager.mRootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mPageList.get(position).mRootView);
        }
    }

}
