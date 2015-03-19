package com.zhangzhilai.markdemo.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhangzhilai.markdemo.R;
import com.zhangzhilai.markdemo.Utils.ImageUtils;
import com.zhangzhilai.markdemo.Views.CircleImageView;

import java.util.ArrayList;

/**
 * Created by zhangzhilai on 3/19/15.
 */
public class MarkCircleImageAdapter extends BaseAdapter{

    public static final String TAG = "MarkCircleImageAdapter";
    private Context mContext;
    private ArrayList<String> mImagePathList;
    private String mImageItemPath;
    private ImageLoader mImageLoader;

    public MarkCircleImageAdapter(Context mContext, ArrayList<String> imagePathList) {
        this.mContext = mContext;
        this.mImagePathList = imagePathList;
        mImageLoader = ImageUtils.getImageLoader(mContext);
    }


    @Override
    public int getCount() {
        if(mImagePathList != null){
            return  mImagePathList.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        if(mImagePathList != null){
            return  mImagePathList.get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;
        if(mImagePathList == null){
            return null;
        }
        if(view == null){
            view = LayoutInflater.from((Activity)mContext).inflate(R.layout.item_markcircle_images, null);
            viewHolder = new ViewHolder();
            viewHolder.mCircleImageView = (CircleImageView)view.findViewById(R.id.item_markcircle_imageview);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)view.getTag();
        }

        mImageItemPath = mImagePathList.get(position);
        mImageLoader.displayImage(mImageItemPath, viewHolder.mCircleImageView, ImageUtils.getDefaultImageOptions());

        return view;
    }

    private class ViewHolder{
        CircleImageView mCircleImageView;
    }
}
