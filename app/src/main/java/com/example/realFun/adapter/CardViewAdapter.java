package com.example.realFun.adapter;

import android.content.Context;
import android.media.Image;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.realFun.R;
import com.example.realFun.model.MediaBean;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by hao on 5/7/17.
 */

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.ViewHolder> implements View.OnClickListener {
    public final static String TAG = "CardViewAdapter";
    private Context mContext;
    private List<MediaBean> mMediaBeanList;
    OnItemClickListener mListener=null;


    @Override
    public void onClick(View v) {
        if (mListener != null){

            mListener.onItemClick(v);//getTag 获得position
        }
    }



    public CardViewAdapter(List<MediaBean> mediaBeanList){
        mMediaBeanList = mediaBeanList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        if (mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.video_item,parent,false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(this);
        return  vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        MediaBean mediaBean = mMediaBeanList.get(position);
        holder.videoName.setText(mediaBean.getName());
        holder.thumbnail.setImageBitmap(mediaBean.getThumbnail());
        Log.d("CardViewHolder", "onBindViewHolder: "+position);
        holder.mediaBean = mediaBean;
        holder.cardView.setTag(getItem(position));
        Log.d("CardViewHolder","onBindViewHolder: "+holder.cardView.getTag());


    }



    @Override
    public int getItemCount() {
        return mMediaBeanList ==null ? 0: mMediaBeanList.size();
    }

    public MediaBean getItem(int position) {
        return mMediaBeanList.get(position);
    }

    public static interface OnItemClickListener{
        void onItemClick(View view);
        }


    public void setOnItemClickListener(OnItemClickListener mItemClickListener) {
        this.mListener =mItemClickListener;
    }
    public  static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView thumbnail;
        TextView videoName;
        MediaBean mediaBean;
        private OnItemClickListener mListener;
        public ViewHolder(View view){
            super(view);
            cardView = (CardView) view;
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            videoName = (TextView) view.findViewById(R.id.video_name);


        }


    }

}
