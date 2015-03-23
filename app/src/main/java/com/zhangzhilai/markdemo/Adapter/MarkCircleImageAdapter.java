package com.zhangzhilai.markdemo.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhangzhilai.markdemo.Model.ImageItem;
import com.zhangzhilai.markdemo.R;
import com.zhangzhilai.markdemo.Utils.ImageDisplayer;
import com.zhangzhilai.markdemo.Utils.ImageUtils;
import com.zhangzhilai.markdemo.Views.CircleImageView;

import java.util.ArrayList;

/**
 * Created by zhangzhilai on 3/19/15.
 */
public class MarkCircleImageAdapter extends BaseAdapter{

    public static final String TAG = "MarkCircleImageAdapter";
    private Context     mContext;
    private ImageItem   mImageItem;
    private ImageLoader mImageLoader;
    private ArrayList<ImageItem> mImageItemList;

    public MarkCircleImageAdapter(Context mContext, ArrayList<ImageItem> imageItemList) {
        this.mContext = mContext;
        this.mImageItemList = imageItemList;
        mImageLoader = ImageUtils.getImageLoader(mContext);
    }


    @Override
    public int getCount() {
        if(mImageItemList != null){
            return  mImageItemList.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        if(mImageItemList != null){
            return  mImageItemList.get(position);
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
        if(mImageItemList == null){
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

        mImageItem = mImageItemList.get(position);
        if(mImageItem == null){
            return null;
        }
        String imagePath = mImageItem.getSourcePath();
        String tempPath;
        if(imagePath.contains("http://") || imagePath.contains("file://")){
            tempPath = imagePath;
        } else {
            tempPath = "file://" + imagePath;
        }
        mImageLoader.displayImage(tempPath, viewHolder.mCircleImageView, ImageUtils.getDefaultImageOptions());
//        ImageDisplayer.getInstance(mContext).displayBmp(viewHolder.mCircleImageView, mImageItem.thumbnailPath, mImageItem.sourcePath);
        return view;
    }

    private class ViewHolder{
        CircleImageView mCircleImageView;
    }
}
