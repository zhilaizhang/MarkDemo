package com.zhangzhilai.markdemo.Views;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import android.util.AttributeSet;

import com.zhangzhilai.markdemo.Activity.ImageBucketChooseActivity;
import com.zhangzhilai.markdemo.Model.ImageItem;
import com.zhangzhilai.markdemo.R;
import com.zhangzhilai.markdemo.Utils.CustomConstants;
import com.zhangzhilai.markdemo.Utils.IntentConstants;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangzhilai on 3/17/15.
 */
public class MultiImagePickerView extends LinearLayout implements View.OnClickListener{

    private static final int TAKE_PICTURE = 0x000000;
    public static List<ImageItem> mDataList = new ArrayList<ImageItem>();
    private Activity mContext;
    private Button mGetImageBtn;
    private String mPath = "";


    public MultiImagePickerView(Context context) {
        super(context);
        initViews();
    }


    public MultiImagePickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    private void initViews() {
        LayoutInflater.from(getContext()).inflate(R.layout.multiimage_picker_layout, this);
        mGetImageBtn = (Button) findViewById(R.id.button_get_image);
        mGetImageBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_get_image:
                gotoGetImage();
                break;
        }
    }

    public void registerActivity(Activity activity) {
        mContext = activity;
    }

    private void gotoGetImage(){
        new AlertDialog.Builder(getContext()).setItems(R.array.select_image_type, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        Intent intent = new Intent(mContext, ImageBucketChooseActivity.class);
                        intent.putExtra(IntentConstants.EXTRA_CAN_ADD_IMAGE_SIZE, getAvailableSize());
                        mContext.startActivity(intent);
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
        mContext.startActivityForResult(openCameraIntent, TAKE_PICTURE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("onActivityResult");
        switch (requestCode) {
            case TAKE_PICTURE:
                if (mDataList.size() < CustomConstants.MAX_IMAGE_SIZE && resultCode == -1 && !TextUtils.isEmpty(mPath)) {
                    ImageItem item = new ImageItem();
                    item.sourcePath = mPath;
                    mDataList.add(item);
                }
                break;
        }
    }
}
