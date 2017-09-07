package com.twentyfour.chavel.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.twentyfour.chavel.R;
import com.twentyfour.chavel.model.DraftModel;
import com.twentyfour.chavel.model.ModelPins;

import java.util.ArrayList;
import java.util.List;

public class DratfAdapter extends RecyclerView.Adapter<DratfAdapter.ViewHolder> {

    private List<DraftModel> list = new ArrayList<>();


    private Context ctx;
    private OnItemClickListener mOnItemClickListener;
    private OnItemClickImageListener mOnItemClickImageListener;

    public interface OnItemClickListener {
        void onItemClick(View viewj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public interface OnItemClickImageListener {
        void onItemClickImage(View viewj, int position);
    }

    public void setOnItemClickImageListener(final OnItemClickImageListener mOnItemClickImageListener) {
        this.mOnItemClickImageListener = mOnItemClickImageListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {



        public ViewHolder(View v) {
            super(v);

        }

        @Override
        public void onClick(View v) {

//            switch (v.getId()) {
//                case R.id.txt_pins:
//                    if (mOnItemClickListener != null) {
//                        mOnItemClickListener.onItemClick(v, getPosition());
//                    }
//                    break;
//
//                case R.id.rl_image:
//                    if (mOnItemClickImageListener != null) {
//                        mOnItemClickImageListener.onItemClickImage(v, getPosition());
//                    }
//                    break;
//
//
//
//            }

        }
    }
    public DratfAdapter(Context ctx, List<DraftModel> list) {
        this.ctx = ctx;
        this.list = list;

    }

    @Override
    public DratfAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pins, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final DraftModel c = list.get(position);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}