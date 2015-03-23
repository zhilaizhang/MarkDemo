package com.zhangzhilai.markdemo.Adapter;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhangzhilai.markdemo.Model.ImageItem;
import com.zhangzhilai.markdemo.R;
import com.zhangzhilai.markdemo.Utils.ImageDisplayer;

import com.zhangzhilai.markdemo.Utils.ImageUtils;
import com.zhangzhilai.markdemo.Utils.MarkUtils;

/**
 * Created by zhangzhilai on 3/17/15.
 */
public class ImagePublishAdapter  extends BaseAdapter {

    public static final String TAG = "ImagePublishAdapter";
    private Context         mContext;
    private List<ImageItem> mDataList;
    private ImageLoader     mImageLoader;
    private int             mFromPage = 0;     //标记从那个页面跳转过来的


    public ImagePublishAdapter(Context context, int comFromType ) {
        mContext  = context;
        mDataList = new ArrayList<ImageItem>();
        mFromPage = comFromType;
        mImageLoader = ImageUtils.getImageLoader(mContext);
    }

    public ImagePublishAdapter(Context context, List<ImageItem> dataList) {
        mContext  = context;
        mDataList = dataList;
    }

    public ImagePublishAdapter(Context context, List<ImageItem> dataList, int comFromType){
        mContext  = context;
        mDataList = dataList;
        mFromPage = comFromType;
        mImageLoader = ImageUtils.getImageLoader(mContext);
    }

    public int getCount() {
        // 多返回一个用于展示添加图标
        if (mDataList == null) {
            return 1;
        } else if (mDataList.size() == MarkUtils.MAX_IMAGE_SIZE) {
            return MarkUtils.MAX_IMAGE_SIZE;
        } else {
            return mDataList.size() + 1;
        }
    }

    public Object getItem(int position) {
        if (mDataList != null && mDataList.size() == MarkUtils.MAX_IMAGE_SIZE) {
            return mDataList.get(position);
        }else if (mDataList == null || position - 1 < 0 || position > mDataList.size()) {
            return null;
        } else {
            return mDataList.get(position - 1);
        }
    }

    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ViewHolder")
    public View getView(int position, View convertView, ViewGroup parent) {
        // 所有Item展示不满一页，就不进行ViewHolder重用了，避免了一个拍照以后添加图片按钮被覆盖的奇怪问题
        convertView = View.inflate(mContext, R.layout.item_publish, null);
        ImageView imageIv = (ImageView) convertView.findViewById(R.id.item_grid_image);

        if (isShowAddItem(position)) {
            imageIv.setImageResource(R.drawable.btn_add_pic);
            imageIv.setBackgroundResource(R.color.bg_gray);
        } else {
            final ImageItem item = mDataList.get(position);
//            if(mFromPage != MarkUtils.JUMP_MARK_CIRCLE_TO_MARK_EDIT){
//                Log.d(TAG, "test item.sourcePath: " + item.sourcePath);
//                mImageLoader.displayImage(item.sourcePath, imageIv, ImageUtils.getDefaultImageOptions());
//            } else {
//                ImageDisplayer.getInstance(mContext).displayBmp(imageIv, item.thumbnailPath, item.sourcePath);
//            }
            String imagePath = item.getSourcePath();
            String tempPath;
            if(imagePath.contains("http://") || imagePath.contains("file://")){
                tempPath = imagePath;
            } else {
                tempPath = "file://" + imagePath;
            }
            mImageLoader.displayImage(tempPath, imageIv, ImageUtils.getDefaultImageOptions());
        }

        return convertView;
    }

    private boolean isShowAddItem(int position) {
        int size = mDataList == null ? 0 : mDataList.size();
        return position == size;
    }

    public void setDataList(List<ImageItem> imageItemList){
        if(mDataList == null || imageItemList == null || imageItemList.size() == 0){
           return;
        }
        if(mDataList.size() != 0){
            mDataList.clear();
        }
        mDataList.addAll(imageItemList);
        notifyDataSetChanged();
    }
}
