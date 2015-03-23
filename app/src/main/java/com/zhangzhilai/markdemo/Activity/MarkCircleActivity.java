package com.zhangzhilai.markdemo.Activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhangzhilai.markdemo.Adapter.MarkCircleAdapter;
import com.zhangzhilai.markdemo.Model.ImageItem;
import com.zhangzhilai.markdemo.Model.MarkCircleItem;
import com.zhangzhilai.markdemo.R;
import com.zhangzhilai.markdemo.Utils.MarkUtils;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by zhangzhilai on 3/19/15.
 * 标记圈
 */
public class MarkCircleActivity extends Activity implements OnItemClickListener, View.OnClickListener {

    public static final String TAG = "MarkCircleActivity";
    private Context mContext;
    private PullToRefreshListView mPullToRefreshListView;
    private ListView mListView;
    private LinearLayout mNoMarkLinearLayout;
    private MarkCircleAdapter mMarkCircleAdapter;
    private ArrayList<MarkCircleItem> mMarkCircleList;
    private MarkCircleItem mMarkCircleItem;
    private TextView       mTitleTextView;
    private TextView       mBackTextView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_circle);
        mContext = this;
        initViews();
        initListeners();
        setData();
    }



    private void initViews() {
        mBackTextView  = (TextView)findViewById(R.id.back_text);
        mTitleTextView = (TextView)findViewById(R.id.title);
        mTitleTextView.setText(R.string.string_mark_circle_title);
        mPullToRefreshListView = (PullToRefreshListView)findViewById(R.id.pull_to_refresh_listview);
        initAdapter();
        initPullRefreshListView();

    }

    private void initListeners() {
        mBackTextView.setOnClickListener(this);
    }

    private void initAdapter(){
        mMarkCircleAdapter = new MarkCircleAdapter(mContext);
    }

    protected void initPullRefreshListView() {
        setPullUpRefreshEnable(true);
        mPullToRefreshListView.setShowIndicator(false);
        mPullToRefreshListView.setOnRefreshListener(pullRefreshListener);

        ILoadingLayout pullFromStartLoadingLayout = mPullToRefreshListView.getLoadingLayoutProxy(true, false);
        pullFromStartLoadingLayout.setPullLabel(getStr(R.string.pull_down_RefreshingLabel));
        pullFromStartLoadingLayout.setRefreshingLabel(getStr(R.string.pull_down_RefreshingLabel));
        pullFromStartLoadingLayout.setReleaseLabel(getStr(R.string.pull_down_RefreshingLabel));

        ILoadingLayout pullFromEndLoadingLayout = mPullToRefreshListView.getLoadingLayoutProxy(false, true);
        pullFromEndLoadingLayout.setPullLabel(getStr(R.string.pull_up_RefreshingLabel));
        pullFromEndLoadingLayout.setRefreshingLabel(getStr(R.string.pull_up_RefreshingLabel));
        pullFromEndLoadingLayout.setReleaseLabel(getStr(R.string.pull_up_RefreshingLabel));
        mListView = mPullToRefreshListView.getRefreshableView();
        mListView.setItemsCanFocus(true);
        mListView.setCacheColorHint(Color.WHITE);
        mListView.setSelector(android.R.color.transparent);
        mListView.setAdapter(mMarkCircleAdapter);
        mListView.setOnItemClickListener(this);
    }

    private void setData(){
        mMarkCircleList = new ArrayList<MarkCircleItem>();
        MarkCircleItem mMarkCircleItem1 = new MarkCircleItem();
        mMarkCircleItem1.setMarkId("123");
        mMarkCircleItem1.setMarkTitle("长泰广场");
        mMarkCircleItem1.setMarkTime("2015.3.18");
        mMarkCircleItem1.setMarkContent("长泰广场,上海首座引入“PASEO”散步道开放式大型购物中心,集娱乐、购物、休闲、餐饮、商务为一体,汇聚全球时尚,引领国际潮流。");
        mMarkCircleItem1.setMarkAddress("浦东祖冲之路金科路");
        mMarkCircleList.add(mMarkCircleItem1);
        MarkCircleItem mMarkCircleItem2 = new MarkCircleItem();
        mMarkCircleItem2.setMarkId("123");
        mMarkCircleItem2.setMarkTitle("传奇广场");
        mMarkCircleItem2.setMarkTime("2015.3.19");
        mMarkCircleItem2.setMarkAddress("上海市浦东新区碧波路635号");
        mMarkCircleList.add(mMarkCircleItem2);
        MarkCircleItem mMarkCircleItem3 = new MarkCircleItem();
        mMarkCircleItem3.setMarkId("123");
        mMarkCircleItem3.setMarkTitle("沃尔玛");
        mMarkCircleItem3.setMarkContent("沃尔玛公司（Wal-Mart Stores, Inc.）（NYSE：WMT）是一家美国的世界性连锁企业，以营业额计算为全球最大的公司，其控股人为沃尔顿家族。总部位于美国阿肯色州的本顿维尔。 ");
        ArrayList<ImageItem> imagePathList = new ArrayList<ImageItem>();
        ImageItem imageItem = new ImageItem();
        imageItem.setSourcePath("http://img5.imgtn.bdimg.com/it/u=1041153494,1223721837&fm=23&gp=0.jpg");
        imagePathList.add(imageItem);
        imageItem = new ImageItem();
        imageItem.setSourcePath("http://img0.imgtn.bdimg.com/it/u=125652165,2870162493&fm=23&gp=0.jpg");
        imagePathList.add(imageItem);
        imageItem = new ImageItem();
        imageItem.setSourcePath("http://img1.imgtn.bdimg.com/it/u=651986609,1848241502&fm=23&gp=0.jpg");
        imagePathList.add(imageItem);
        imageItem = new ImageItem();
        imageItem.setSourcePath("http://img3.imgtn.bdimg.com/it/u=3791936296,679007535&fm=23&gp=0.jpg");
        imagePathList.add(imageItem);
        imageItem = new ImageItem();
        imageItem.setSourcePath("http://img0.imgtn.bdimg.com/it/u=2391252400,1093025192&fm=23&gp=0.jpg");
        imagePathList.add(imageItem);
        imageItem = new ImageItem();
        imageItem.setSourcePath("http://img0.imgtn.bdimg.com/it/u=3305723592,2742771748&fm=23&gp=0.jpg");
        imagePathList.add(imageItem);
        mMarkCircleItem3.setMarkImageItemList(imagePathList);
        mMarkCircleItem3.setMarkTime("2015.3.20");
        mMarkCircleItem3.setMarkAddress("上海市浦东新区施镇路");
        mMarkCircleList.add(mMarkCircleItem3);

        MarkCircleItem mMarkCircleItem4 = new MarkCircleItem();
        mMarkCircleItem4.setMarkId("123");
        mMarkCircleItem4.setMarkTitle("沃尔玛88");
        mMarkCircleItem4.setMarkContent("sfdfasdfsdf沃尔玛公司（Wal-Mart Stores, Inc.）（NYSE：WMT）是一家美国的世界性连锁企业，以营业额计算为全球最大的公司，其控股人为沃尔顿家族。总部位于美国阿肯色州的本顿维尔。 ");
        ArrayList<ImageItem> imagePathList1 = new ArrayList<ImageItem>();
        ImageItem imageItem1 = new ImageItem();
        imageItem1.setSourcePath("file:///mnt/sdcard/HJApp/HJDict/images/bbs_banner.png");
        imagePathList1.add(imageItem1);
        imageItem1 = new ImageItem();
        imageItem1.setSourcePath("file:///mnt/sdcard/HJApp/HJNCE/images/bbs_banner.png");
        imagePathList1.add(imageItem1);
        imageItem1 = new ImageItem();
        imageItem1.setSourcePath("file:///mnt/sdcard/HJApp/wordgamesexperience/images/bbs_banner.png");
        imagePathList1.add(imageItem1);
        imageItem1 = new ImageItem();
        imageItem1.setSourcePath("file:///mnt/sdcard/HJApp/HJJPNews/Image/wh5106.jpg");
        imagePathList1.add(imageItem1);
        imageItem1 = new ImageItem();
        imageItem1.setSourcePath("file:///mnt/sdcard/HJApp/HJJPNews/Image/dongman7170.jpg");
        imagePathList1.add(imageItem1);
        imageItem1 = new ImageItem();
        imageItem1.setSourcePath("file:///mnt/sdcard/HJApp/HJJPNews/Image/ch5844.jpg");
        imagePathList1.add(imageItem1);
        mMarkCircleItem4.setMarkImageItemList(imagePathList1);
        mMarkCircleItem4.setMarkTime("2015.3.21");
        mMarkCircleItem4.setMarkAddress("上海市浦东新区施镇路46664");
        mMarkCircleList.add(mMarkCircleItem4);

        listToJson(mMarkCircleList);
//        MarkCircleItem mMarkCircleItem5 = new MarkCircleItem();
//        mMarkCircleItem5.setMarkId("123");
//        mMarkCircleItem5.setMarkTitle("沃尔玛88");
//        mMarkCircleItem5.setMarkContent("sfdfasdfsdf沃尔玛公司（Wal-Mart Stores, Inc.）（NYSE：WMT）是一家美国的世界性连锁企业，以营业额计算为全球最大的公司，其控股人为沃尔顿家族。总部位于美国阿肯色州的本顿维尔。 ");
//        ArrayList<ImageItem> imagePathList2 = new ArrayList<ImageItem>();
//        ImageItem imageItem2 = new ImageItem();
//        imageItem2.setSourcePath("file:///mnt/sdcard/HJApp/HJDict/images/bbs_banner.png");
//        imagePathList2.add(imageItem2);
//        imageItem2 = new ImageItem();
//        imageItem2.setSourcePath("file:///mnt/sdcard/HJApp/HJNCE/images/bbs_banner.png");
//        imagePathList2.add(imageItem2);
//        imageItem2 = new ImageItem();
//        imageItem2.setSourcePath("file:///mnt/sdcard/HJApp/wordgamesexperience/images/bbs_banner.png");
//        imagePathList2.add(imageItem2);
//        imageItem2 = new ImageItem();
//        imageItem2.setSourcePath("file:///mnt/sdcard/HJApp/HJJPNews/Image/wh5106.jpg");
//        imagePathList2.add(imageItem2);
//        imageItem2 = new ImageItem();
//        imageItem2.setSourcePath("file:///mnt/sdcard/HJApp/HJJPNews/Image/dongman7170.jpg");
//        imagePathList1.add(imageItem2);
//        imageItem2 = new ImageItem();
//        imageItem2.setSourcePath("file:///mnt/sdcard/HJApp/HJJPNews/Image/ch5844.jpg");
//        imagePathList2.add(imageItem2);
//        mMarkCircleItem5.setMarkImageItemList(imagePathList2);
//        mMarkCircleItem5.setMarkTime("2015.3.21");
//        mMarkCircleItem5.setMarkAddress("上海市浦东新区施镇路46664");

//        mMarkCircleList.add(mMarkCircleItem5);

        mMarkCircleAdapter.setList(mMarkCircleList);
    }

    private void listToJson(ArrayList<MarkCircleItem> markCircleList){
        Gson gson = new Gson();
        String markCircleString = gson.toJson(markCircleList);
        MarkUtils.saveString2File(markCircleString, MarkUtils.FILE_NAME);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_text:
                finish();
            break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mMarkCircleAdapter.setPosition(position);
    }

    /**
     * 设置为BOTH或者只能下拉刷新
     *
     * @param enable
     */
    protected void setPullUpRefreshEnable(boolean enable) {
        if (enable) {
            mPullToRefreshListView.setMode(Mode.BOTH);
        } else {
            mPullToRefreshListView.setMode(Mode.PULL_FROM_START);
        }
    }

    /**
     * 设置为BOTH或者只能上拉刷新
     *
     * @param enable
     */
    protected void setPullDownRefreshEnable(boolean enable) {
        if (enable) {
            mPullToRefreshListView.setMode(Mode.BOTH);
        } else {
            mPullToRefreshListView.setMode(Mode.PULL_FROM_END);
        }
    }

    protected String getStr(int resId) {
        return getText(resId).toString();
    }

    private OnRefreshListener2<ListView> pullRefreshListener = new OnRefreshListener2<ListView>() {

        @Override
        public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

        }

        @Override
        public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

        }

    };

}
