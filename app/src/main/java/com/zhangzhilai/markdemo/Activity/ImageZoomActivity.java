package com.zhangzhilai.markdemo.Activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.zhangzhilai.markdemo.MarkEditActivity;
import com.zhangzhilai.markdemo.Model.ImageItem;
import com.zhangzhilai.markdemo.R;
import com.zhangzhilai.markdemo.Utils.ImageDisplayer;
import com.zhangzhilai.markdemo.Utils.IntentConstants;

/**
 * Created by zhangzhilai on 3/17/15.
 */
public class ImageZoomActivity extends Activity implements View.OnClickListener{

    public  static final String TAG = "ImageZoomActivity";

    private ViewPager mPager;
    private MyPageAdapter mAdapter;
    private int mCurrentPosition;
    private List<ImageItem> mDataList = new ArrayList<ImageItem>();

    private RelativeLayout mPhotoRelativeLayout;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom);

        mPhotoRelativeLayout = (RelativeLayout) findViewById(R.id.photo_relativeLayout);
        mPhotoRelativeLayout.setBackgroundColor(0x70000000);

        initData();

        Button photo_bt_exit = (Button) findViewById(R.id.photo_bt_exit);
        photo_bt_exit.setOnClickListener(this);
        Button photo_bt_del = (Button) findViewById(R.id.photo_bt_del);
        photo_bt_del.setOnClickListener(this);

        mPager = (ViewPager) findViewById(R.id.viewpager);
        mPager.setOnPageChangeListener(pageChangeListener);

        mAdapter = new MyPageAdapter(mDataList);
        mPager.setAdapter(mAdapter);
        mPager.setCurrentItem(mCurrentPosition);
    }

    private void initData() {
        mCurrentPosition = getIntent().getIntExtra(IntentConstants.EXTRA_CURRENT_IMG_POSITION, 0);
        mDataList = MarkEditActivity.mDataList;
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
                finish();
                break;
            case R.id.photo_bt_del:
                if (mDataList.size() == 1) {
                    removeImgs();
                    finish();
                } else {
                    removeImg(mCurrentPosition);
                    mPager.removeAllViews();
                    mAdapter.removeView(mCurrentPosition);
                    mAdapter.notifyDataSetChanged();
                }
                break;
        }
    }

    class MyPageAdapter extends PagerAdapter {
        private List<ImageItem> dataList = new ArrayList<ImageItem>();
        private ArrayList<ImageView> mViews = new ArrayList<ImageView>();

        public MyPageAdapter(List<ImageItem> dataList) {
            this.dataList = dataList;
            int size = dataList.size();
            for (int i = 0; i != size; i++) {
                ImageView iv = new ImageView(ImageZoomActivity.this);
                ImageDisplayer.getInstance(ImageZoomActivity.this).displayBmp(iv, null, dataList.get(i).sourcePath, false);
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
