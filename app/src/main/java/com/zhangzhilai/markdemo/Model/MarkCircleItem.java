package com.zhangzhilai.markdemo.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by zhangzhilai on 3/19/15.
 * 单条标记圈信息对象
 */
public class MarkCircleItem implements Serializable{

    private static final long serialVersionUID = 7194425650480836368L;
    private String markId;                      //标记id
    private String markTitle;                   //标记标题
    private String markTime;                    //标记时间
    private String markContent;                 //标记内容
    private String markAddress;                 //标记地址
    private ArrayList<ImageItem> MarkImageItemList;//标记图片

    public String getMarkId() {
        return markId;
    }

    public void setMarkId(String markId) {
        this.markId = markId;
    }

    public String getMarkTime() {
        return markTime;
    }

    public void setMarkTime(String markTime) {
        this.markTime = markTime;
    }

    public String getMarkContent() {
        return markContent;
    }

    public void setMarkContent(String markContent) {
        this.markContent = markContent;
    }

    public String getMarkAddress() {
        return markAddress;
    }

    public void setMarkAddress(String markAddress) {
        this.markAddress = markAddress;
    }

    public String getMarkTitle() {
        return markTitle;
    }

    public void setMarkTitle(String markTitle) {
        this.markTitle = markTitle;
    }

    public ArrayList<ImageItem> getMarkImageItemList() {
        return MarkImageItemList;
    }

    public void setMarkImageItemList(ArrayList<ImageItem> markImageItemList) {
        MarkImageItemList = markImageItemList;
    }
}
