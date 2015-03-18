package com.zhangzhilai.markdemo.Utils;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore.Images.Media;
import android.provider.MediaStore.Images.Thumbnails;
import com.zhangzhilai.markdemo.Model.ImageBucket;
import com.zhangzhilai.markdemo.Model.ImageItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangzhilai on 3/17/15.
 * 相册操作类
 */
public class ImageFetcher {

    private Context mContext;
    private static ImageFetcher mInstance;

    private HashMap<String, ImageBucket> mImageBuckets;
    private HashMap<String, String> mThumbnailList;


    private boolean mHasBuildImagesBucketList = false;

    public ImageFetcher(Context context){
        mContext = context;
        mImageBuckets = new HashMap<String, ImageBucket>();
        mThumbnailList = new HashMap<String, String>();
    }

    public static ImageFetcher getmInstance(Context context){
        if(mInstance == null){
            synchronized (ImageFetcher.class){
                mInstance = new ImageFetcher(context);
            }
        }
        return mInstance;
    }

    /**
     * 得到图片集
     *
     * @param refresh
     * @return
     */
    public List<ImageBucket> getImagesBucketList(boolean refresh){

        if (refresh || (!refresh && !mHasBuildImagesBucketList)){
            buildImagesBucketList();
        }

        List<ImageBucket> tmpList = new ArrayList<ImageBucket>();
        Iterator<Map.Entry<String, ImageBucket>> iterator = mImageBuckets.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, ImageBucket> entry = (Map.Entry<String, ImageBucket>) iterator.next();
            tmpList.add(entry.getValue());
        }
        return tmpList;
    }

    /**
     * 构建缩略图索引和相册索引
     */
    private void buildImagesBucketList() {
         Cursor cursor = null;
         try{
             // 构造缩略图索引
             getThumbnail();

             // 构造相册索引
             String columns[] = new String[] { Media._ID, Media.BUCKET_ID, Media.DATA, Media.BUCKET_DISPLAY_NAME };
             // 得到一个游标
             cursor = mContext.getContentResolver().query(
                     Media.EXTERNAL_CONTENT_URI, columns, null, null, null);
             if (cursor.moveToFirst()){
                 // 获取指定列的索引
                 int photoIDIndex = cursor.getColumnIndexOrThrow(Media._ID);
                 int photoPathIndex = cursor.getColumnIndexOrThrow(Media.DATA);
                 int bucketDisplayNameIndex = cursor.getColumnIndexOrThrow(Media.BUCKET_DISPLAY_NAME);
                 int bucketIdIndex = cursor.getColumnIndexOrThrow(Media.BUCKET_ID);

                 do{
                     String id = cursor.getString(photoIDIndex);
                     String path = cursor.getString(photoPathIndex);
                     String bucketName = cursor.getString(bucketDisplayNameIndex);

                     ImageBucket bucket = mImageBuckets.get(bucketName);

                     if (bucket == null) {
                         bucket = new ImageBucket();
                         mImageBuckets.put(bucketName, bucket);
                         bucket.imageList = new ArrayList<ImageItem>();
                         bucket.bucketName = bucketName;
                     }
                     bucket.count++;
                     ImageItem imageItem = new ImageItem();
                     imageItem.imageId = id;
                     imageItem.sourcePath = path;
                     imageItem.thumbnailPath = mThumbnailList.get(id);
                     bucket.imageList.add(imageItem);
                 }
                 while (cursor.moveToNext());
             }
             mHasBuildImagesBucketList = true;
         }
         finally {

         }
    }

    private void getThumbnail(){
        Cursor cursor = null;
        try{
            String[] projection = { Thumbnails.IMAGE_ID, Thumbnails.DATA };
            cursor = mContext.getContentResolver().query(Thumbnails.EXTERNAL_CONTENT_URI, projection, null, null, null);
            getThumbnailColumnData(cursor);
        }
        finally{
            cursor.close();
        }
    }

    /**
     * 从数据库中得到所有缩略图id和路径
     *
     * @param cur
     */
    private void getThumbnailColumnData(Cursor cur){
        if (cur.moveToFirst()){
            int image_id;
            String image_path;
            int image_idColumn = cur.getColumnIndex(Thumbnails.IMAGE_ID);
            int dataColumn = cur.getColumnIndex(Thumbnails.DATA);
            do{
                image_id = cur.getInt(image_idColumn);
                image_path = cur.getString(dataColumn);

                mThumbnailList.put("" + image_id, image_path);
            }
            while (cur.moveToNext());
        }
    }

}
