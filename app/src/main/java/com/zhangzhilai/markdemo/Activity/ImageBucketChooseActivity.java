package com.zhangzhilai.markdemo.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.zhangzhilai.markdemo.Adapter.ImageBucketAdapter;
import com.zhangzhilai.markdemo.Model.ImageBucket;
import com.zhangzhilai.markdemo.R;
import com.zhangzhilai.markdemo.Utils.CustomConstants;
import com.zhangzhilai.markdemo.Utils.MarkUtils;
import com.zhangzhilai.markdemo.Utils.ImageFetcher;
import com.zhangzhilai.markdemo.Utils.IntentConstants;

import android.widget.AdapterView.OnItemClickListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangzhilai on 3/17/15.
 */
public class ImageBucketChooseActivity  extends Activity implements View.OnClickListener{
    private ImageFetcher mHelper;
    private List<ImageBucket> mDataList;
    private ListView mListView;
    private ImageBucketAdapter mAdapter;

    private Button mBucketBackButton;

    private int availableSize;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_bucket_choose);

        mHelper = ImageFetcher.getmInstance(getApplicationContext());
        initData();
        initView();
    }

    private void initData(){
        mDataList = new ArrayList<ImageBucket>();
        mDataList = mHelper.getImagesBucketList(false);
        availableSize = getIntent().getIntExtra(IntentConstants.EXTRA_CAN_ADD_IMAGE_SIZE, CustomConstants.MAX_IMAGE_SIZE);
    }

    private void initView(){
        mListView = (ListView) findViewById(R.id.listview);
        mBucketBackButton = (Button) findViewById(R.id.bucket_back_btn);
        mAdapter = new ImageBucketAdapter(this, mDataList);
        mListView.setAdapter(mAdapter);

        mBucketBackButton.setOnClickListener(this);
        mListView.setOnItemClickListener(new OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){

                selectOne(position);

                Intent intent = new Intent(ImageBucketChooseActivity.this, ImageChooseActivity.class);
                intent.putExtra(IntentConstants.EXTRA_IMAGE_LIST, (Serializable) mDataList.get(position).imageList);
                intent.putExtra(IntentConstants.EXTRA_BUCKET_NAME, mDataList.get(position).bucketName);
                intent.putExtra(IntentConstants.EXTRA_CAN_ADD_IMAGE_SIZE, availableSize);
                startActivityForResult(intent, MarkUtils.BUCKETCHOOSE_TO_IMAGECHOOSE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == MarkUtils.BUCKETCHOOSE_TO_IMAGECHOOSE && resultCode == MarkUtils.RESULT_OK){
            setResult(MarkUtils.RESULT_OK, data);
            finish();
        }
    }

    private void selectOne(int position){
        int size = mDataList.size();
        for (int i = 0; i != size; i++){
            if (i == position) {
                mDataList.get(i).selected = true;
            } else {
                mDataList.get(i).selected = false;
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bucket_back_btn:
                finish();
                break;
        }
    }
}
