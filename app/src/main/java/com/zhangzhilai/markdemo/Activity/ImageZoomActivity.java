package com.zhangzhilai.markdemo.Activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhangzhilai.markdemo.MarkEditActivity;
import com.zhangzhilai.markdemo.Model.ImageItem;
import com.zhangzhilai.markdemo.R;
import com.zhangzhilai.markdemo.Utils.ImageUtils;
import com.zhangzhilai.markdemo.Utils.MarkUtils;

/**
 * Created by zhangzhilai on 3/17/15.
 */
public class ImageZoomActivity extends Activity implements View.OnClickListener{

    public  static final String TAG = "ImageZoomActivity";
    private Context         mContext;
    private ViewPager       mPager;
    private MyPageAdapter   mAdapter;
    private RelativeLayout  mPhotoRelativeLayout;
    private Button          mExitButton;
    private Button          mDeleteButton;

    private ImageLoader     mImageLoader;

    private List<ImageItem> mDataList;

    private int             mCurrentPosition;
    private int             mPageJumpFrom;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "test onCreate");
        setContentView(R.layout.activity_zoom);
        mContext = this;
        initViews();
        initData();
        initListeners();
    }



    private void initData() {
        mDataList = new ArrayList<ImageItem>();
        mImageLoader = ImageUtils.getImageLoader(mContext);
        mPageJumpFrom = getIntent().getIntExtra(MarkUtils.EXTRA_JUMP_FROM_PAGE, 0);
        mCurrentPosition = getIntent().getIntExtra(MarkUtils.EXTRA_CURRENT_IMG_POSITION, 0);
        mDataList = (List<ImageItem>)getIntent().getSerializableExtra(MarkUtils.EXTRA_IMAGE_LIST);
        mAdapter = new MyPageAdapter(mDataList);
        mPager.setAdapter(mAdapter);
        mPager.setCurrentItem(mCurrentPosition);
        if(mPageJumpFrom == MarkUtils.JUMP_FROM_MARK_CIRCLE){
            mDeleteButton.setVisibility(View.GONE);
        }

    }

    private void initViews(){
        mPhotoRelativeLayout = (RelativeLayout) findViewById(R.id.photo_relativeLayout);
        mPhotoRelativeLayout.setBackgroundColor(0x70000000);
        mDeleteButton = (Button) findViewById(R.id.photo_bt_del);
        mPager = (ViewPager) findViewById(R.id.viewpager);
        mExitButton = (Button) findViewById(R.id.photo_bt_exit);

    }

    private void initListeners() {
        mExitButton.setOnClickListener(this);
        mDeleteButton.setOnClickListener(this);
        mPager.setOnPageChangeListener(pageChangeListener);
    }

    private void removeImgs() {
        mDataList.clear();
    }

    private void removeImg(int location) {
        if (location + 1 <= mDataList.size()) {
            mDataList.remove(location);
        }
    }

    private OnPageChangeListener pageChangeListener = new OnPageChangeListener() {

        public void onPageSelected(int arg0) {
            mCurrentPosition = arg0;
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        public void onPageScrollStateChanged(int arg0) {

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.photo_bt_exit:
                backDataList();
//                finish();
                break;
            case R.id.photo_bt_del:
                if (mDataList.size() == 1) {
                    removeImgs();
                    backDataList();
//                    finish();
                } else {
                    removeImg(mCurrentPosition);
                    mPager.removeAllViews();
                    mAdapter.removeView(mCurrentPosition);
                    mAdapter.notifyDataSetChanged();
                }
                break;
        }
    }

    private void backDataList(){
        Intent intent = new Intent();
        intent.putExtra(MarkUtils.EXTRA_IMAGE_LIST, (Serializable)mDataList);
        setResult(MarkUtils.RESULT_OK, intent);
        finish();
    }

    class MyPageAdapter extends PagerAdapter {
        private List<ImageItem> dataList = new ArrayList<ImageItem>();
        private ArrayList<ImageView> mViews = new ArrayList<ImageView>();

        public MyPageAdapter(List<ImageItem> dataList) {
            this.dataList = dataList;
            int size = dataList.size();
            for (int i = 0; i != size; i++) {
                ImageView iv = new ImageView(ImageZoomActivity.this);
                Log.d(TAG, "test sourcePath: " + dataList.get(i).sourcePath);
                String tempPath = "";
                if(dataList.get(i).sourcePath.contains("http://") || dataList.get(i).sourcePath.contains("file://")){
                    tempPath = dataList.get(i).sourcePath;
                } else {
                    tempPath = "file://" + dataList.get(i).sourcePath;
                }

                mImageLoader.displayImage(tempPath, iv, ImageUtils.getDefaultImageOptions());
//                ImageDisplayer.getInstance(ImageZoomActivity.this).displayBmp(iv, null, dataList.get(i).sourcePath, false);
                iv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                mViews.add(iv);
            }
        }

        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        public Object instantiateItem(View arg0, int arg1) {
            ImageView iv = mViews.get(arg1);
            ((ViewPager) arg0).addView(iv);
            return iv;
        }

        public void destroyItem(View arg0, int arg1, Object arg2) {
            if (mViews.size() >= arg1 + 1) {
                ((ViewPager) arg0).removeView(mViews.get(arg1));
            }
        }

        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public int getCount() {
            return dataList.size();
        }

        public void removeView(int position) {
            if (position + 1 <= mViews.size()) {
                mViews.remove(position);
            }
        }

    }

}
