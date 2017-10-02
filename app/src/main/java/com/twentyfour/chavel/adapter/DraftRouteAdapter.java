package com.twentyfour.chavel.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.twentyfour.chavel.R;
import com.twentyfour.chavel.model.DraftModel;

import java.util.ArrayList;
import java.util.List;

public class DraftRouteAdapter extends RecyclerView.Adapter<DraftRouteAdapter.ViewHolder> {

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

        ImageView image_cover;
        ImageView image_map;

        public ViewHolder(View v) {
            super(v);

            image_cover = (ImageView) v.findViewById(R.id.image_cover);
            image_map = (ImageView) v.findViewById(R.id.image_map);

        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.image_cover:
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(v, getPosition());
                    }
                    break;

                case R.id.image_map:
                    if (mOnItemClickImageListener != null) {
                        mOnItemClickImageListener.onItemClickImage(v, getPosition());
                    }
                    break;



            }

        }
    }

    public DraftRouteAdapter(Context ctx, List<DraftModel> list) {
        this.ctx = ctx;
        this.list = list;

    }

    @Override
    public DraftRouteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_draft, parent, false);

        ViewHolder vh = new ViewHolder(v);

        Glide.with(ctx)
                .load("https://www.mrs2be.ie/wp-content/uploads/2012/11/kuoni-3-copy.jpg")
                .into(vh.image_cover);

        Glide.with(ctx)
                .load("https://maps.googleapis.com/maps/api/staticmap?center=Brooklyn+Bridge,New+York,NY&zoom=13&size=600x300&maptype=roadmap\n" +
                        "&markers=color:blue%7Clabel:S%7C40.702147,-74.015794&markers=color:green%7Clabel:G%7C40.711614,-74.012318\n" +
                        "&markers=color:red%7Clabel:C%7C40.718217,-73.998284\n" +
                        "&key=AIzaSyBdNRS-_L_jV5RgeSSigUHUHA-cgST2YH0")
                .into(vh.image_map);

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