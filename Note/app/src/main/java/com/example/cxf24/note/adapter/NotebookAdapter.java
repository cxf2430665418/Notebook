package com.example.cxf24.note.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cxf24.note.R;

import com.example.cxf24.note.entity.NotebookData;
import com.example.cxf24.note.ui.HomeActivity;
import com.example.cxf24.note.ui.NoteEditActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class NotebookAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<NotebookData> datas;
    private final HomeActivity aty;
    private LayoutInflater mLayoutInflater;
    private int currentHidePosition = -1;
    private boolean dataChange = false;
    private Context context;
    //private OnItemClickListener onItemClickListener;//声明一个接口的引用

    public NotebookAdapter(HomeActivity aty,List<NotebookData> datas) {
        super();
        this.datas = datas;
        this.aty = aty;
    }
    @Override
    public int getItemCount() {
        return datas.size();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = mLayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_note_re, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        NotebookData data = datas.get(position);

        if (null == data)
            return;
        final ViewHolder viewholder =(ViewHolder) holder;
        viewholder.note_title.setText(data.getTitle());
        viewholder.note_info.setText(data.getContent());
        viewholder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null)
                    onItemClickListener.onClick(position);
            }
        });
        viewholder.layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemLongClickListener != null)
                    onItemLongClickListener.onLongClick(position);
                return true;
            }
        });
    }
    public interface OnItemClickListener {
        void onClick(int position);
    }

    OnItemClickListener onItemClickListener;


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public interface OnItemLongClickListener {
        void onLongClick(int position);
    }

    OnItemLongClickListener onItemLongClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView note_title;
        TextView note_info;
        LinearLayout layout;
        //private ItemClickListener mListener;

        public ViewHolder(View itemView) {
            super(itemView);
            note_title = (TextView) itemView.findViewById(R.id.note_title);
            note_info = (TextView) itemView.findViewById(R.id.note_info);
            layout=(LinearLayout) itemView.findViewById(R.id.note_container);
            /**layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view ){

                }
            });*/

        }
    }
}