package com.zhangzhilai.markdemo.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhangzhilai.markdemo.Model.ImageBucket;
import com.zhangzhilai.markdemo.R;
import com.zhangzhilai.markdemo.Utils.ImageDisplayer;

import java.util.List;

/**
 * Created by zhangzhilai on 3/17/15.
 */
public class ImageBucketAdapter extends BaseAdapter {

    private List<ImageBucket> mDataList;
    private Context mContext;

    public ImageBucketAdapter(Context context, List<ImageBucket> dataList){
        this.mContext = context;
        this.mDataList = dataList;
    }

    @Override
    public int getCount(){
        return mDataList.size();
    }

    @Override
    public Object getItem(int position){
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        final ViewHolder mHolder;
        if (convertView == null){
            convertView = View.inflate(mContext, R.layout.item_bucket_list, null);
            mHolder = new ViewHolder();
            mHolder.coverIv = (ImageView) convertView.findViewById(R.id.cover);
            mHolder.titleTv = (TextView) convertView.findViewById(R.id.title);
            mHolder.countTv = (TextView) convertView.findViewById(R.id.count);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }

        final ImageBucket item = mDataList.get(position);

        if (item.getImageList() != null && item.getImageList().size() > 0){
            String thumbPath = item.getImageList().get(0).thumbnailPath;
            String sourcePath = item.getImageList().get(0).sourcePath;
            ImageDisplayer.getInstance(mContext).displayBmp(mHolder.coverIv, thumbPath,
                    sourcePath);
        } else {
            mHolder.coverIv.setImageBitmap(null);
        }

        mHolder.titleTv.setText(item.getBucketName());
        mHolder.countTv.setText(item.getCount() + "张");

        return convertView;
    }

    static class ViewHolder {
        private ImageView coverIv;
        private TextView titleTv;
        private TextView countTv;
    }

}
