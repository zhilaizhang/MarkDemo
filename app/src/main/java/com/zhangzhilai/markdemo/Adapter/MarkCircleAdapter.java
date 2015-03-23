package com.zhangzhilai.markdemo.Adapter;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhangzhilai.markdemo.Activity.ImageZoomActivity;
import com.zhangzhilai.markdemo.MarkEditActivity;
import com.zhangzhilai.markdemo.Model.ImageItem;
import com.zhangzhilai.markdemo.Model.MarkCircleItem;
import com.zhangzhilai.markdemo.R;
import com.zhangzhilai.markdemo.Utils.MarkUtils;
import com.zhangzhilai.markdemo.Views.ImageGridView;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by zhangzhilai on 3/19/15.
 * 标记圈适配器
 */
public class MarkCircleAdapter extends BindableAdapter<MarkCircleItem>{

    public static final String TAG = "MarkCircleAdapter";
    private Context mContext;
    private ArrayList<MarkCircleItem> mMarkCircleList;
    private ArrayList<ImageItem> mMarkImageItemList;

    private MarkCircleImageAdapter mMarkCircleAdapter;

    private String mMarkId;
    private String mMarkTitle;
    private String mMarkTime;
    private String mMarkContent;
    private String mMarkAddress;



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
        Log.d(TAG, "test newView position" + position);
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
        Log.d(TAG, "test bindView position" + position);

        MarkCircleItem mMarkCircleItem = mMarkCircleList.get(position);
        if(mMarkCircleItem == null || view == null){
            return;
        }
        initDatas(mMarkCircleItem);
        final ViewHolder viewHolder = (ViewHolder)view.getTag();
        if(!MarkUtils.textIsEmpty(mMarkContent) && mMarkImageItemList != null && mMarkImageItemList.size() != 0){ //有图片和内容
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

        viewHolder.markEditTextView.setOnClickListener(new mOnClickListner(position));
        viewHolder.markDeleteTextView.setOnClickListener(new mOnClickListner(position));
        viewHolder.markImageGridView.setOnItemClickListener(new OnAdapterItemClickListener(position));


        if(mMarkImageItemList != null && mMarkImageItemList.size() != 0){
            mMarkCircleAdapter = new MarkCircleImageAdapter(mContext, mMarkImageItemList);
            viewHolder.markImageGridView.setAdapter(mMarkCircleAdapter);
        }
    }

    class OnAdapterItemClickListener implements AdapterView.OnItemClickListener{

        private int positionParent;

        public OnAdapterItemClickListener(int position){
            this.positionParent = position;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            mMarkImageItemList = mMarkCircleList.get(positionParent).getMarkImageItemList();
            if(mMarkImageItemList != null && mMarkImageItemList.size() != 0){
                Intent intent = new Intent();
                intent.setClass(mContext, ImageZoomActivity.class);
                intent.putExtra(MarkUtils.EXTRA_JUMP_FROM_PAGE, MarkUtils.JUMP_FROM_MARK_CIRCLE);
                intent.putExtra(MarkUtils.EXTRA_IMAGE_LIST, (Serializable) mMarkImageItemList);
                intent.putExtra(MarkUtils.EXTRA_CURRENT_IMG_POSITION, position);
                mContext.startActivity(intent);
            }
        }
    }

    class mOnClickListner implements View.OnClickListener{

        private int position;
        public mOnClickListner(int position){
            this.position = position;
        }

        @Override
        public void onClick(View v) {
             switch (v.getId()){
                 case R.id.mark_edit_textview:
                    Intent intentToEdit = new Intent();
                    intentToEdit.putExtra(MarkUtils.EXTRA_MARK_CIRCLE_TO_MARK_EDIT, (Serializable)mMarkCircleList.get(position));
                    intentToEdit.setClass(mContext, MarkEditActivity.class);
                    mContext.startActivity(intentToEdit);
                     break;
                 case R.id.mark_delete_textview:
                     new AlertDialog.Builder(mContext).setTitle(R.string.string_is_delete).setPositiveButton(R.string.string_confirm, new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialog, int which) {
                             mMarkCircleList.remove(position);
                             notifyDataSetChanged();
                         }
                     }).setNegativeButton(R.string.string_cancel, new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialog, int which) {

                         }
                     }).show();

                     break;
             }
        }
    }

    private void initDatas(MarkCircleItem mMarkCircleItem) {
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
        mMarkImageItemList = mMarkCircleItem.getMarkImageItemList();
    }

    public void setList(ArrayList<MarkCircleItem> markCircleList){
        mMarkCircleList = markCircleList;
        notifyDataSetChanged();
    }

    public void setPosition(int position){
        Log.d(TAG, "test position1: " + position);
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
