package com.hkcyl.notepad.fragment;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.hkcyl.notepad.R;
import com.hkcyl.notepad.bean.EventBean;

import java.util.List;

import butterknife.Bind;

/**
 * Created by yonglong on 2016/6/25.
 */
public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder>{

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    public OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

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

    private ColorGenerator mColorGenerator = ColorGenerator.DEFAULT;
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        if (mOnItemClickListener != null) {
            holder.item_container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.item_container, position);
                }
            });

            holder.item_container.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickListener.onItemLongClick(holder.item_container, position);
                    return true;
                }
            });

        }

        TextDrawable textDrawable = TextDrawable.builder()
                .buildRoundRect(beanList.get(position).getmTitle().toString().substring(0,1),
                        mColorGenerator.getRandomColor(),10);
        holder.image.setImageDrawable(textDrawable);

        holder.title.setText(beanList.get(position).getmTitle()+"");
        holder.start_time.setText(beanList.get(position).getmStartTime()+"");
        holder.end_time.setText(beanList.get(position).getmEndTime()+"");

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
        public ImageView image;
        public RelativeLayout item_container;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            item_container = (RelativeLayout) view.findViewById(R.id.item_container);
            title = (TextView) view.findViewById(R.id.event_title);
            start_time = (TextView) view.findViewById(R.id.event_st);
            end_time = (TextView) view.findViewById(R.id.event_et);
            image = (ImageView) view.findViewById(R.id.thumbnail_image);
        }


    }

}
