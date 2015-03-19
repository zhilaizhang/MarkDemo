package com.zhangzhilai.markdemo.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhangzhilai.markdemo.Model.MarkCircleItem;
import com.zhangzhilai.markdemo.R;
import com.zhangzhilai.markdemo.Utils.MarkUtils;
import com.zhangzhilai.markdemo.Views.ImageGridView;

import java.util.ArrayList;

/**
 * Created by zhangzhilai on 3/19/15.
 * 标记圈适配器
 */
public class MarkCircleAdapter extends BindableAdapter<MarkCircleItem>{

    public static final String TAG = "MarkCircleAdapter";
    private Context mContext;
    private ArrayList<MarkCircleItem> mMarkCircleList;
    private MarkCircleItem mMarkCircleItem;
    private MarkCircleImageAdapter mMarkCircleAdapter;

    private String mMarkId;
    private String mMarkTitle;
    private String mMarkTime;
    private String mMarkContent;
    private String mMarkAddress;
    private ArrayList<String> mMarkImagePathList;


    public MarkCircleAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public int getCount() {
        if(mMarkCircleList != null){
            return mMarkCircleList.size();
        } else {
            return 0;
        }
    }

    @Override
    public MarkCircleItem getItem(int position) {
        if(mMarkCircleList != null){
            return mMarkCircleList.get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View newView(LayoutInflater inflater, int position, ViewGroup container) {
        View view;
        ViewHolder viewHolder = new ViewHolder();
        view = inflater.inflate(R.layout.item_mark_circle, null);
        viewHolder.markTypeImageView = (ImageView)view.findViewById(R.id.mark_type_imageview);
        viewHolder.markTitleTextView = (TextView)view.findViewById(R.id.mark_title_textview);
        viewHolder.markTimeTextView = (TextView)view.findViewById(R.id.mark_time_textview);
        viewHolder.markContentTextView = (TextView)view.findViewById(R.id.mark_content_textview);
        viewHolder.markImageGridView = (ImageGridView)view.findViewById(R.id.mark_image_gridview);
        viewHolder.markAddressTextView = (TextView)view.findViewById(R.id.mark_address_textview);
        viewHolder.markEditTextView = (TextView)view.findViewById(R.id.mark_edit_textview);
        viewHolder.markDeleteTextView = (TextView)view.findViewById(R.id.mark_delete_textview);

        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void bindView(MarkCircleItem item, int position, View view) {
        mMarkCircleItem = mMarkCircleList.get(position);
        if(mMarkCircleItem == null || view == null){
            return;
        }
        initDatas();
        final ViewHolder viewHolder = (ViewHolder)view.getTag();
        if(!MarkUtils.textIsEmpty(mMarkContent) && mMarkImagePathList != null && mMarkImagePathList.size() != 0){ //有图片和内容
            viewHolder.markTypeImageView.setBackgroundResource(R.drawable.icon_all);
            viewHolder.markContentTextView.setVisibility(View.VISIBLE);
            viewHolder.markImageGridView.setVisibility(View.VISIBLE);

            viewHolder.markContentTextView.setText(mMarkContent);
        } else if(!MarkUtils.textIsEmpty(mMarkContent)){ //有内容无图片
            viewHolder.markTypeImageView.setBackgroundResource(R.drawable.icon_no_picture);
            viewHolder.markContentTextView.setVisibility(View.VISIBLE);
            viewHolder.markImageGridView.setVisibility(View.GONE);

            viewHolder.markContentTextView.setText(mMarkContent);
        } else {                                                                                                        //没有图片和内容
            viewHolder.markTypeImageView.setBackgroundResource(R.drawable.icon_no_picture_content);
            viewHolder.markContentTextView.setVisibility(View.GONE);
            viewHolder.markImageGridView.setVisibility(View.GONE);
        }

        viewHolder.markTitleTextView.setText(mMarkTitle);
        viewHolder.markTimeTextView.setText(mMarkTime);
        viewHolder.markAddressTextView.setText(mMarkAddress);

        if(mMarkImagePathList != null && mMarkImagePathList.size() != 0){
            mMarkCircleAdapter = new MarkCircleImageAdapter(mContext, mMarkImagePathList);
            viewHolder.markImageGridView.setAdapter(mMarkCircleAdapter);
        }

    }

    private void initDatas() {
        mMarkId = mMarkCircleItem.getMarkId();
        mMarkTitle = mMarkCircleItem.getMarkTitle();
        if(mMarkTitle == null){
            mMarkTitle = "";
        }
        mMarkTime = mMarkCircleItem.getMarkTime();
        if(mMarkTime == null){
            mMarkTime = "";
        }
        mMarkContent = mMarkCircleItem.getMarkContent();
        if(mMarkContent == null){
            mMarkContent = "";
        }
        mMarkAddress = mMarkCircleItem.getMarkAddress();
        if(mMarkAddress == null){
            mMarkAddress = "";
        }
        mMarkImagePathList = mMarkCircleItem.getMarkImagePathList();
    }

    public void setList(ArrayList<MarkCircleItem> markCircleList){
        mMarkCircleList = markCircleList;
        notifyDataSetChanged();
    }

    private class ViewHolder{
        private ImageView markTypeImageView;
        private TextView  markTitleTextView;
        private TextView  markTimeTextView;
        private TextView  markContentTextView;
        private ImageGridView markImageGridView;
        private TextView  markAddressTextView;
        private TextView  markEditTextView;
        private TextView  markDeleteTextView;
    }
}
