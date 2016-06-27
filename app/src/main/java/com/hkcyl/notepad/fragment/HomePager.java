package com.hkcyl.notepad.fragment;

import android.animation.Animator;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.Interpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.android.multiselector.ModalMultiSelectorCallback;
import com.bignerdranch.android.multiselector.MultiSelector;
import com.hkcyl.notepad.AddEventActivity;
import com.hkcyl.notepad.DetailActivity;
import com.hkcyl.notepad.MainActivity;
import com.hkcyl.notepad.R;
import com.hkcyl.notepad.bean.EventBean;
import com.hkcyl.notepad.db.NoteDao;
import com.hkcyl.notepad.presenter.Presenter;
import com.hkcyl.notepad.view.IEventView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 用于显示界面的 Pager ，通过设置不同的 adapter 实现布局复用
 */
public class HomePager extends BasePager implements IEventView, EventAdapter.OnItemClickListener {

    @Bind(R.id.recycler_view)
    public RecyclerView recycler_view;
    @Bind(R.id.noEvent)
    public TextView noEvent;
    private NoteDao dao;
    private Presenter presenter;
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
        presenter = new Presenter(this);

        // 初始化AgendaView的数据适配器
        eventAdapter = new EventAdapter(mActivity, getEvents());
        recycler_view.setLayoutManager(new LinearLayoutManager(mActivity));
        recycler_view.setAdapter(eventAdapter);
        eventAdapter.setOnItemClickListener(this);
        recycler_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                boolean is = Math.abs(dy) > 4;

                if (is) {
                    if (dy > 0) {
                        onScrollUp();
                    } else {
                        onScrolldown();
                    }
                }
            }


        });
    }

    private void onScrolldown() {
        show(MainActivity.add_event);
    }

    private void onScrollUp() {

        hide(MainActivity.add_event);
    }

    @Override
    public List<EventBean> getEvents() {
        //初始化数据
        dao = new NoteDao(mActivity);
        List<EventBean> alllist = dao.getAll();
        return alllist;
    }

    @Override
    public void setEvent(EventBean eventBean) {

    }


    /**
     * Hide the quick return view.
     * <p/>
     * Animates hiding the view, with the view sliding down and out of the screen.
     * After the view has disappeared, its visibility will change to GONE.
     *
     * @param view The quick return view
     */
    private void hide(final View view) {
        mIsHiding = true;
        ViewPropertyAnimator animator = view.animate()
                .translationY(view.getHeight())
                .setInterpolator(INTERPOLATOR)
                .setDuration(200);

        animator.setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                // Prevent drawing the View after it is gone
                mIsHiding = false;
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                // Canceling a hide should show the view
                mIsHiding = false;
                if (!mIsShowing) {
                    show(view);
                }
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });

        animator.start();
    }

    private static final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();

    boolean mIsShowing;
    boolean mIsHiding;

    /**
     * Show the quick return view.
     * <p/>
     * Animates showing the view, with the view sliding up from the bottom of the screen.
     * After the view has reappeared, its visibility will change to VISIBLE.
     *
     * @param view The quick return view
     */
    private void show(final View view) {
        mIsShowing = true;
        ViewPropertyAnimator animator = view.animate()
                .translationY(0)
                .setInterpolator(INTERPOLATOR)
                .setDuration(200);

        animator.setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mIsShowing = false;
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                // Canceling a show should hide the view
                mIsShowing = false;
                if (!mIsHiding) {
                    hide(view);
                }
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });

        animator.start();
    }

    @Override
    public void onItemClick(View view, int position) {

        Intent intent = new Intent(mActivity, AddEventActivity.class);
        intent.putExtra("from", "edit");
        intent.putExtra("id", getEvents().get(position).getmId());
        intent.putExtra("title", getEvents().get(position).getmTitle());
        intent.putExtra("content", getEvents().get(position).getmDescription());
        intent.putExtra("start_time", getEvents().get(position).getmStartTime());
        intent.putExtra("end_time", getEvents().get(position).getmEndTime());

        mActivity.startActivity(intent);
        //  Toast.makeText(mActivity,"click"+position,Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onItemLongClick(View view, final int position) {
        new AlertDialog.Builder(mActivity)
                .setTitle("提示")
                .setMessage("删除本条目")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dao.deleteDataById(getEvents().get(position));

                        initData();
                    }
                })
                .show();
    }
}
