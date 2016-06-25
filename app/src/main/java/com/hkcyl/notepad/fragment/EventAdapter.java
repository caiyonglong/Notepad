package com.hkcyl.notepad.fragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hkcyl.notepad.R;
import com.hkcyl.notepad.bean.EventBean;

import java.util.List;

import butterknife.Bind;

/**
 * Created by yonglong on 2016/6/25.
 */
public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder>{

    public List<EventBean> beanList;
    private Context mContxt;

    public EventAdapter(Context mContxt,List<EventBean> beanList) {
        this.mContxt = mContxt;
        this.beanList =beanList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(mContxt).inflate(R.layout.list_item_card,parent,false);


        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(beanList.get(position).getmTitle()+"==");
        holder.start_time.setText(beanList.get(position).getmStartTime()+"-");
        holder.end_time.setText(beanList.get(position).getmEndTime()+"-");

    }

    @Override
    public int getItemCount() {
        return beanList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{


        public View view;
        public TextView title;
        public TextView start_time;
        public TextView end_time;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            title = (TextView) view.findViewById(R.id.event_title);
            start_time = (TextView) view.findViewById(R.id.event_st);
            end_time = (TextView) view.findViewById(R.id.event_et);
        }


    }

}
