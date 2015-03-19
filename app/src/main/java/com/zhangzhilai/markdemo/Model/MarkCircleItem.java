package com.zhangzhilai.markdemo.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by zhangzhilai on 3/19/15.
 * 单条标记圈信息对象
 */
public class MarkCircleItem implements Serializable{

    private static final long serialVersionUID = 7194425650480836368L;
    private String MarkId;                      //标记id
    private String MarkTitle;                   //标记标题
    private String MarkTime;                    //标记时间
    private String MarkContent;                 //标记内容
    private String MarkAddress;                 //标记地址
    private ArrayList<String> MarkImagePathList;//标记图片路径

    public String getMarkId() {
        return MarkId;
    }

    public void setMarkId(String markId) {
        MarkId = markId;
    }

    public String getMarkTime() {
        return MarkTime;
    }

    public void setMarkTime(String markTime) {
        MarkTime = markTime;
    }

    public String getMarkContent() {
        return MarkContent;
    }

    public void setMarkContent(String markContent) {
        MarkContent = markContent;
    }

    public String getMarkAddress() {
        return MarkAddress;
    }

    public void setMarkAddress(String markAddress) {
        MarkAddress = markAddress;
    }

    public ArrayList<String> getMarkImagePathList() {
        return MarkImagePathList;
    }

    public void setMarkImagePathList(ArrayList<String> markImagePathList) {
        MarkImagePathList = markImagePathList;
    }

    public String getMarkTitle() {
        return MarkTitle;
    }

    public void setMarkTitle(String markTitle) {
        MarkTitle = markTitle;
    }
}
