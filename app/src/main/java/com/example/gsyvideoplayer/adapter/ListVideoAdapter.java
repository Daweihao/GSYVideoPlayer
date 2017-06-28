package com.example.gsyvideoplayer.adapter;

import android.content.Context;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gsyvideoplayer.R;
import com.example.gsyvideoplayer.model.MediaBean;
import com.example.gsyvideoplayer.model.VideoModel;
import com.shuyu.gsyvideoplayer.listener.StandardVideoAllCallBack;
import com.shuyu.gsyvideoplayer.utils.FileUtils;
import com.shuyu.gsyvideoplayer.utils.ListVideoUtil;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.R.attr.id;
import static android.media.CamcorderProfile.get;

/**
 * Created by shuyu on 2016/11/11.
 */

public class ListVideoAdapter extends BaseAdapter {

    public final static String TAG = "TT2";

    private List<MediaBean> list = new ArrayList<>();
    private LayoutInflater inflater = null;
    private Context context;
    private HashMap<String,List<MediaBean>> videoAll;

    private ViewGroup rootView;
    private OrientationUtils orientationUtils;

    private boolean isFullVideo;

    private ListVideoUtil listVideoUtil;

    public ListVideoAdapter(Context context, ListVideoUtil listVideoUtil, HashMap<String,List<MediaBean>> hashMap) {
        super();
        this.context = context;
        this.listVideoUtil = listVideoUtil;
        this.videoAll = hashMap;


        inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public MediaBean getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return id;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.list_video_item, null);
            holder.videoContainer = (FrameLayout) convertView.findViewById(R.id.list_item_container);
            holder.playerBtn = (ImageView) convertView.findViewById(R.id.list_item_btn);
            holder.imageView = new ImageView(context);
            //holder.textView = (TextView) convertView.findViewById(R.id.list_item_text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //增加封面
        holder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        holder.imageView.setImageResource(Integer.parseInt(getItem(position).getThumbnail()));

        listVideoUtil.addVideoPlayer(position, holder.imageView, TAG, holder.videoContainer, holder.playerBtn);

        holder.playerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyDataSetChanged();
                //listVideoUtil.setLoop(true);
                listVideoUtil.setPlayPositionAndTag(position, TAG);
                //listVideoUtil.setCachePath(new File(FileUtils.getPath()));
                listVideoUtil.startPlay(getItem(position).getUrl());
            }
        });
        return convertView;
    }


    class ViewHolder {
        FrameLayout videoContainer;
        ImageView playerBtn;
        ImageView imageView;
        TextView textView;
    }

    public void setRootView(ViewGroup rootView) {
        this.rootView = rootView;
    }
}
