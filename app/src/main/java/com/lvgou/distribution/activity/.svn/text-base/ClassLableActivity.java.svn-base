package com.lvgou.distribution.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.adapter.MyListTestAdapter;
import com.lvgou.distribution.bean.ChildItem;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.presenter.LabelLoadPresenter;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.LabelLoadView;
import com.xdroid.common.utils.PreferenceHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/17.
 */

public class ClassLableActivity extends BaseActivity implements LabelLoadView, View.OnClickListener {
    private LabelLoadPresenter labelLoadPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_label);
        labelLoadPresenter = new LabelLoadPresenter(this);
        initLabelDatas();
        initView();
        initClick();
    }

    private RelativeLayout rl_back;//取消
    private TextView tv_title;//标题
    private RelativeLayout rl_sure;//确定
    private ListView expandList;
    private MyListTestAdapter myAdapter;

    private void initView() {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("课程类型");
        rl_sure = (RelativeLayout) findViewById(R.id.rl_sure);
        expandList = (ListView) findViewById(R.id.expandlist);
        myAdapter = new MyListTestAdapter(this);
        expandList.setAdapter(myAdapter);
        registerForContextMenu(expandList);//给ExpandListView添加上下文菜单
    }

    private void initClick() {
        rl_back.setOnClickListener(this);
        rl_sure.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back://返回
                finish();
                break;
            case R.id.rl_sure:
                break;
        }
    }

    private void initLabelDatas() {
        String distributorid = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        String sign = TGmd5.getMD5(distributorid);
        showLoadingProgressDialog(this, "");
        labelLoadPresenter.labelLoadDatas(distributorid, sign);
    }

    private List<String> groupData = new ArrayList<String>();//group的数据源
    private Map<Integer, List<ChildItem>> childData = new HashMap<Integer, List<ChildItem>>();//child的数据源
    private Map<Integer, Integer> aaa = new HashMap<>();
    private ArrayList<String> chkList = new ArrayList<String>();

    @Override
    public void OnLabelLoadSuccCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
        try {
            JSONObject jsb = new JSONObject(respanse);
            JSONArray jsa = new JSONArray(jsb.getString("result"));
            JSONArray jsonArray = jsa.getJSONArray(0);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsoo = jsonArray.getJSONObject(i);
                // 添加一级分类组名到Map集合
                groupData.add(jsoo.getString("LabelName"));
                // 得到二级数组
                JSONArray list_0 = jsoo.getJSONArray("labellist");
                List<ChildItem> childItemName = new ArrayList<ChildItem>();
                childItemName.clear();
                // 遍历数组
                for (int j = 0; j < list_0.length(); j++) {
                    JSONObject jsList = list_0.getJSONObject(j);
                    // 得到二级分组条目标题
                    String twoName = jsList.getString("LabelName");
                    // 得到二级分组条目ID
                    int twoId = 0;
                    String str = jsList.getString("LabelPath");
                    if (str != null) {
                        try {
                            twoId = Integer.valueOf(str).intValue();
                        } catch (Exception e) {
                        }
                    }
                    // 把条目标题、ID通过构造添加到对象
                    ChildItem childDataName = new ChildItem(twoName, twoId);
                    // 把对象添加到集合
                    childItemName.add(childDataName);
                }
                childData.put(i, childItemName);
            }
            if (chkList.size() < 1) {
                chkList = (ArrayList<String>) mcache.getAsObject("actorTagList");
            }
            if (ClassLableActivity.this.chkList != null) {
                myAdapter.setIsVISIBLE(ClassLableActivity.this.chkList);
            } else {
                chkList = new ArrayList<String>();
            }
            for (int i = 0; i < groupData.size(); i++) {
                aaa.put(i, 0);
                for (ChildItem childItem : childData.get(i)) {
                    if (chkList.contains(childItem.getMarkerImgId() + "")) {
                        aaa.put(i, aaa.get(i) + 1);
                    }
                }
            }
            mHandler.sendEmptyMessage(1);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private final mainHandler mHandler = new mainHandler(this);

    private static class mainHandler extends Handler {
        private final WeakReference<ClassLableActivity> mActivity;

        public mainHandler(ClassLableActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            ClassLableActivity activity = mActivity.get();
            if (activity != null) {
                activity.flushDataList(msg);
            }
        }
    }

    public void flushDataList(Message msg) {
        if (msg.what == 1) {
            myAdapter.setData(groupData, childData, aaa);
        }
    }


    @Override
    public void OnLabelLoadFialCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
    }

    @Override
    public void closeLabelLoadProgress() {

    }

}
