package com.hkcyl.notepad.fragment;

import android.animation.Animator;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.Interpolator;
import android.widget.TextView;

import com.hkcyl.notepad.AddRemindActivity;
import com.hkcyl.notepad.MainActivity;
import com.hkcyl.notepad.R;
import com.hkcyl.notepad.bean.Reminder;
import com.hkcyl.notepad.db.NoteDao;
import com.hkcyl.notepad.presenter.NotesPresenter;
import com.hkcyl.notepad.view.HomePagerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 用于显示界面的 Pager ，通过设置不同的 adapter 实现布局复用
 */
public class HomePager extends BasePager implements HomePagerView, EventAdapter.OnItemClickListener {

    @Bind(R.id.recycler_view)
    public RecyclerView recycler_view;
    @Bind(R.id.noEvent)
    public TextView noEvent;
    private NoteDao dao;
    private NotesPresenter presenter;
    public EventAdapter eventAdapter;
    public List<Reminder> reminders = new ArrayList<>();


    //动画加速器
    private static final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();

    //是否显示、隐藏
    private boolean mIsShowing;
    private boolean mIsHiding;

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
        presenter = new NotesPresenter(this,mActivity);
        reminders =presenter.getAllReminder();

        presenter.showReminds();


    }



    /**
     * 点击事件
     * @param view
     * @param position
     */

    @Override
    public void onItemClick(View view, int position) {

        Intent intent = new Intent(mActivity, AddRemindActivity.class);
        intent.putExtra("action", "edit");
        intent.putExtra("reminder",reminders.get(position));

        mActivity.startActivity(intent);
    }

    @Override
    public void onItemLongClick(View view, final int position) {
        new AlertDialog.Builder(mActivity)
                .setTitle("提示")
                .setIcon(R.drawable.ic_delete_white_24dp)
                .setMessage("删除本条目")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.deleteRemind(reminders.get(position));
                        presenter.showchanged();
                    }
                })
                .show();
    }


    @Override
    public void showChanged() {
       initData();
    }

    /**
     * 展示数据
     * @param reminders
     */

    @Override
    public void showNotes(List<Reminder> reminders) {

        // 初始化AgendaView的数据适配器
        eventAdapter = new EventAdapter(mActivity, reminders);
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
                        presenter.hide(MainActivity.add_event);
                    } else {
                        presenter.show(MainActivity.add_event);
                    }
                }
            }


        });
        show(MainActivity.add_event);
    }

    /**
     * 快速显示view
     * @param view
     */
    @Override
    public void show(final View view) {
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

    /**
     * 快速隐藏view
     * @param view
     */
    @Override
    public void hide(final View view) {
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


}
