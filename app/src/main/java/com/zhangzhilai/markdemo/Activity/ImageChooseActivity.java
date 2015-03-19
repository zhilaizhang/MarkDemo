package com.zhangzhilai.markdemo.Activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhangzhilai.markdemo.Adapter.ImageGridAdapter;
import com.zhangzhilai.markdemo.MarkEditActivity;
import com.zhangzhilai.markdemo.Model.ImageItem;
import com.zhangzhilai.markdemo.R;
import com.zhangzhilai.markdemo.Utils.CustomConstants;
import com.zhangzhilai.markdemo.Utils.IMAGEUtils;
import com.zhangzhilai.markdemo.Utils.IntentConstants;


/**
 * Created by zhangzhilai on 3/17/15.
 */
public class ImageChooseActivity extends  Activity {

    public  static final String TAG = "ImageChooseActivity";

    private List<ImageItem> mDataList = new ArrayList<ImageItem>();
    private String mBucketName;
    private int mAvailableSize;
    private GridView mGridView;
    private TextView mBucketNameTv;
    private TextView mBackTv;
    private ImageGridAdapter mAdapter;
    private Button mFinishBtn;
    private HashMap<String, ImageItem> selectedImgs = new HashMap<String, ImageItem>();

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_image_choose);

        mDataList = (List<ImageItem>) getIntent().getSerializableExtra(IntentConstants.EXTRA_IMAGE_LIST);
        if (mDataList == null)
            mDataList = new ArrayList<ImageItem>();
        mBucketName = getIntent().getStringExtra(IntentConstants.EXTRA_BUCKET_NAME);

        if (TextUtils.isEmpty(mBucketName)) {
            mBucketName = "请选择";
        }
        mAvailableSize = getIntent().getIntExtra(IntentConstants.EXTRA_CAN_ADD_IMAGE_SIZE, CustomConstants.MAX_IMAGE_SIZE);

        initView();
        initListener();

    }

    private void initView() {
        mBucketNameTv = (TextView) findViewById(R.id.title);
        mBucketNameTv.setText(mBucketName);
        mBackTv = (TextView) findViewById(R.id.back_text);

        mGridView = (GridView) findViewById(R.id.gridview);
        mGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        mAdapter = new ImageGridAdapter(ImageChooseActivity.this, mDataList);
        mGridView.setAdapter(mAdapter);
        mFinishBtn = (Button) findViewById(R.id.finish_btn);

        mFinishBtn.setText("完成" + "(" + selectedImgs.size() + "/" + mAvailableSize + ")");
        mAdapter.notifyDataSetChanged();
    }

    private void initListener() {
        mFinishBtn.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(ImageChooseActivity.this, MarkEditActivity.class);
                intent.putExtra(IntentConstants.EXTRA_IMAGE_LIST, (Serializable) new ArrayList<ImageItem>(selectedImgs.values()));
//                startActivity(intent);
                setResult(IMAGEUtils.RESULT_OK, intent);
                finish();
            }

        });

        mGridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ImageItem item = mDataList.get(position);
                if (item.isSelected) {
                    item.isSelected = false;
                    selectedImgs.remove(item.imageId);
                } else {
                    if (selectedImgs.size() >= mAvailableSize) {
                        Toast.makeText(ImageChooseActivity.this, "最多选择" + mAvailableSize + "张图片", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    item.isSelected = true;
                    selectedImgs.put(item.imageId, item);
                }

                mFinishBtn.setText("完成" + "(" + selectedImgs.size() + "/" + mAvailableSize + ")");
                mAdapter.notifyDataSetChanged();
            }

        });

        mBackTv.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
