package com.zhangzhilai.markdemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;

import com.alibaba.fastjson.JSON;
import com.zhangzhilai.markdemo.Activity.ImageBucketChooseActivity;
import com.zhangzhilai.markdemo.Activity.ImageZoomActivity;
import com.zhangzhilai.markdemo.Adapter.ImagePublishAdapter;
import com.zhangzhilai.markdemo.Model.ImageItem;
import com.zhangzhilai.markdemo.Utils.CustomConstants;
import com.zhangzhilai.markdemo.Utils.MarkUtils;
import com.zhangzhilai.markdemo.Utils.IntentConstants;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangzhilai on 3/17/15.
 */
public class MarkEditActivity extends Activity implements View.OnClickListener{

    public  static final String TAG = "MarkEditActivity";

    public static List<ImageItem> mDataList = new ArrayList<ImageItem>();
    private Context mContext;

    private Button   mSaveButton;
    private Button   mGetImageBtn;
    private GridView mGridView;
    private ImageButton mBackButton;

    private ImagePublishAdapter mAdapter;

    private EditText mTitleEditText;
    private EditText mContentEditText;

    private String mPath = "";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_edit);
        mContext = this;
        initData();
        initViews();
        initListners();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveTempToPref();
    }

    private void initData() {
        if(mDataList.size() != 0){
            mDataList.clear();
        }
    }
    private void initViews() {
        mBackButton = (ImageButton) findViewById(R.id.back_btn);
        mSaveButton = (Button) findViewById(R.id.save_btn);
        mGetImageBtn = (Button) findViewById(R.id.button_get_image);

        mTitleEditText = (EditText) findViewById(R.id.markedit_title_edittext);
        mContentEditText = (EditText) findViewById(R.id.content_edittext);

        mGridView = (GridView) findViewById(R.id.gridview);
        mGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));

        mAdapter = new ImagePublishAdapter(this, mDataList);
        mGridView.setAdapter(mAdapter);
        refreshImageView();
    }

    private void refreshImageView(){
        if(mDataList.size() == 0 ){
            mGetImageBtn.setVisibility(View.VISIBLE);
            mGridView.setVisibility(View.GONE);
        } else {
            mGetImageBtn.setVisibility(View.GONE);
            mGridView.setVisibility(View.VISIBLE);
        }
    }

    private void initListners() {
        mBackButton.setOnClickListener(this);
        mSaveButton.setOnClickListener(this);
        mGetImageBtn.setOnClickListener(this);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "position:" + position + "getDataSize(): " + getDataSize());
                if (position == getDataSize()) {
                    gotoGetImage();
                } else {
                    Intent intent = new Intent(MarkEditActivity.this, ImageZoomActivity.class);
                    intent.putExtra(IntentConstants.EXTRA_IMAGE_LIST, (Serializable) mDataList);
                    intent.putExtra(IntentConstants.EXTRA_CURRENT_IMG_POSITION, position);
                    startActivityForResult(intent, MarkUtils.IMAGE_ZOOM);
                }
            }
        });
    }





    private void gotoGetImage(){
        new AlertDialog.Builder(mContext).setItems(R.array.select_image_type, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        Intent intent = new Intent(mContext, ImageBucketChooseActivity.class);
                        intent.putExtra(IntentConstants.EXTRA_CAN_ADD_IMAGE_SIZE, getAvailableSize());
                        startActivityForResult(intent, MarkUtils.TAKE_PICTURE_FROM_ALBUM);
                        break;
                    case 1:
                        takePhoto();
                        break;
                }
            }
        }).show();
    }

    private int getAvailableSize() {
        int availSize = CustomConstants.MAX_IMAGE_SIZE - mDataList.size();
        if (availSize >= 0) {
            return availSize;
        }
        return 0;
    }

    public void takePhoto() {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File vFile = new File(Environment.getExternalStorageDirectory() + "/myimage/", String.valueOf(System.currentTimeMillis()) + ".jpg");
        if (!vFile.exists()) {
            File vDirPath = vFile.getParentFile();
            vDirPath.mkdirs();
        } else {
            if (vFile.exists()) {
                vFile.delete();
            }
        }
        mPath = vFile.getPath();
        Uri cameraUri = Uri.fromFile(vFile);
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
        startActivityForResult(openCameraIntent, MarkUtils.TAKE_PICTURE_FROM_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "test onActivityResult");
        System.out.println("requestCode: " + requestCode + "resultCode" + resultCode);
        switch (requestCode) {
            case MarkUtils.TAKE_PICTURE_FROM_CAMERA:
                if (mDataList.size() < CustomConstants.MAX_IMAGE_SIZE && resultCode == -1 && !TextUtils.isEmpty(mPath)) {
                    ImageItem item = new ImageItem();
                    item.sourcePath = mPath;
                    mDataList.add(item);
                }
                refreshImageView();
                break;
            case MarkUtils.IMAGE_ZOOM:
                refreshImageView();
                break;
            case MarkUtils.TAKE_PICTURE_FROM_ALBUM:
                if(resultCode == MarkUtils.RESULT_OK){
                    List<ImageItem> incomingDataList = (List<ImageItem>) data.getSerializableExtra(IntentConstants.EXTRA_IMAGE_LIST);
                    ImageItem item = incomingDataList.get(0);
                    if (incomingDataList != null) {
                        mDataList.addAll(incomingDataList);
                    }
                }
                refreshImageView();
                break;
        }
    }


    private void saveTempToPref() {
        SharedPreferences sp = getSharedPreferences(CustomConstants.APPLICATION_NAME, MODE_PRIVATE);
        String prefStr = JSON.toJSONString(mDataList);
        sp.edit().putString(CustomConstants.PREF_TEMP_IMAGES, prefStr).commit();

    }

    private int getDataSize() {
        return mDataList == null ? 0 : mDataList.size();
    }



    private void getTempFromPref() {
        SharedPreferences sp = getSharedPreferences(CustomConstants.APPLICATION_NAME, MODE_PRIVATE);
        String prefStr = sp.getString(CustomConstants.PREF_TEMP_IMAGES, null);
        if (!TextUtils.isEmpty(prefStr)) {
            List<ImageItem> tempImages = JSON.parseArray(prefStr, ImageItem.class);
            mDataList = tempImages;
        }
    }

    private void removeTempFromPref() {
        SharedPreferences sp = getSharedPreferences(CustomConstants.APPLICATION_NAME, MODE_PRIVATE);
        sp.edit().remove(CustomConstants.PREF_TEMP_IMAGES).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_btn:
                finish();
                break;
            case R.id.save_btn:

                break;
            case R.id.button_get_image:
                gotoGetImage();
                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "test onResume");
        notifyDataChanged(); // 当在ImageZoomActivity中删除图片时，返回这里需要刷新
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "test onPause");
        saveTempToPref();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDataList.clear();

    }

    private void notifyDataChanged() {
        Log.d(TAG, "test notifyDataChanged");
        mAdapter.notifyDataSetChanged();
    }

}
