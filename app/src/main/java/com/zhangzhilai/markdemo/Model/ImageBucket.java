package com.zhangzhilai.markdemo.Model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangzhilai on 3/17/15.
 * 相册对象
 */
public class ImageBucket implements Serializable{

    private static final long serialVersionUID = 7194425650480890968L;
    private int               count = 0;
    private String            bucketName;
    private List<ImageItem>   imageList;
    private boolean           selected = false;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public List<ImageItem> getImageList() {
        return imageList;
    }

    public void setImageList(List<ImageItem> imageList) {
        this.imageList = imageList;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
