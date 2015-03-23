package com.zhangzhilai.markdemo.Model;

import java.io.Serializable;

/**
 * Created by zhangzhilai on 3/17/15.
 * 图片对象
 */
public class ImageItem implements Serializable{

    private static final long serialVersionUID = -7188270558443739436L;
    public String  imageId;
    public String  thumbnailPath;           //缩略图路径
    public String  sourcePath;              //图片路径
    public boolean isSelected = false;

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public String getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
}
