package com.lvgou.distribution.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lvgou.distribution.R;
import com.lvgou.distribution.adapter.BuyBatchClassAdapter;
import com.lvgou.distribution.adapter.ListenClassAdapter;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.inter.AdapterToActImpl;
import com.lvgou.distribution.presenter.TeacherDownloadListPresenter;
import com.lvgou.distribution.utils.DownloadManager;
import com.lvgou.distribution.utils.DownloadService;
import com.lvgou.distribution.utils.MyToast;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.TeacherDownloadListView;
import com.lvgou.distribution.widget.XListView;
import com.xdroid.common.utils.PreferenceHelper;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Administrator on 2017/4/27.
 * 批量下载
 */

public class BatchDownLoadActivity extends BaseActivity implements View.OnClickListener, TeacherDownloadListView {
    private TeacherDownloadListPresenter teacherDownloadListPresenter;
    private String distributorid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch_download);
        teacherDownloadListPresenter = new TeacherDownloadListPresenter(this);
        distributorid = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        initView();
        initDownLoad();
        initClick();
    }

    private ArrayList<HashMap<String, Object>> array;

    private void initDownLoad() {
        finishList = (ArrayList<HashMap<String, Object>>) mcache.getAsObject("downloadfinish" + distributorid);
        if (null == finishList) {
            finishList = new ArrayList<HashMap<String, Object>>();
        }
        array = (ArrayList<HashMap<String, Object>>) mcache.getAsObject("xiazaiduilie" + distributorid);
        if (null == array) {
            array = new ArrayList<HashMap<String, Object>>();
        }

        String sign = TGmd5.getMD5(distributorid);
        teacherDownloadListPresenter.teacherDownloadList(distributorid, sign);
    }

    private RelativeLayout rl_back;
    private TextView tv_title;
    private BuyBatchClassAdapter buyBatchClassAdapter;
    private XListView mListView;
    private TextView tv_godown;
    private RelativeLayout rl_all_check;//全选
    private ImageView im_all_check;
    private TextView tv_total;
    private View courseFooter;
    private RelativeLayout rl_none_one;


    private void initView() {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("批量下载");
        tv_godown = (TextView) findViewById(R.id.tv_godown);
        rl_all_check = (RelativeLayout) findViewById(R.id.rl_all_check);
        im_all_check = (ImageView) findViewById(R.id.im_all_check);
        tv_total = (TextView) findViewById(R.id.tv_total);
        tv_total.setVisibility(View.GONE);
        rl_none_one = (RelativeLayout) findViewById(R.id.rl_none_one);


        mListView = (XListView) findViewById(R.id.list_view);
        buyBatchClassAdapter = new BuyBatchClassAdapter(this);
        mListView.setPullRefreshEnable(false);
        mListView.setPullLoadEnable(false);
//        mListView.stopLoadMore();
        mListView.setAutoLoadEnable(false);
//        mListView.setXListViewListener(this);
        mListView.setRefreshTime(this.getTime());
        mListView.setDivider(null);
        mListView.setAdapter(buyBatchClassAdapter);
        courseFooter = LayoutInflater.from(this).inflate(R.layout.course_big_footer, null);
        mListView.addFooterView(courseFooter);
    }

    private ArrayList<HashMap<String, Object>> downListselecte;
    private ArrayList<HashMap<String, Object>> finishList;

    private boolean isAllselect = false;//控制选选的状态

    private void initClick() {
        rl_back.setOnClickListener(this);
        tv_godown.setOnClickListener(this);
        rl_all_check.setOnClickListener(this);


        buyBatchClassAdapter.setAdapterToActImpl(new AdapterToActImpl() {
            @Override
            public void doSomeThing(HashMap<String, Object> info) {
                downListselecte = new ArrayList<>();
                downListselecte.clear();
                double totallenth = 0;//选中的里面总共的大小
                array = (ArrayList<HashMap<String, Object>>) mcache.getAsObject("xiazaiduilie" + distributorid);
                if (array == null) {
                    array = new ArrayList<HashMap<String, Object>>();
                }
                for (int i = 0; i < downloadList.size(); i++) {
                    if (downloadList.get(i).get("ID").toString().equals(info.get("ID").toString())) {
                        downloadList.get(i).put("isCheck", info.get("isCheck"));
                    }
                    if (downloadList.get(i).get("isCheck").toString().equals("true")) {
                        downListselecte.add(downloadList.get(i));
                        totallenth = totallenth + Double.parseDouble(downloadList.get(i).get("AreaName").toString());
                    }
                }
                int allsize = downloadList.size();//所有的数量
                int downselectsize = downListselecte.size();//选中的数量
                int finishsize = finishList.size();//已经完成了的数量
                int downingsize = array.size();//正在下载中数量
                if (allsize == (downselectsize + finishsize + downingsize)) {
                    //全选了
                    isAllselect = true;
                    im_all_check.setBackgroundResource(R.mipmap.checkbox_check_icon);
                } else {
                    isAllselect = false;
                    im_all_check.setBackgroundResource(R.mipmap.checkbox_default_icon);
                }
                if (downselectsize > 0) {
                    //如果有选中的
                    tv_godown.setClickable(true);//
                    tv_godown.setEnabled(true);
                    tv_godown.setTextColor(Color.parseColor("#ffffff"));
                    tv_godown.setBackgroundResource(R.drawable.bg_radius_apply);
                    tv_total.setVisibility(View.VISIBLE);
                    tv_total.setText("已选中" + downselectsize + "个课程，" + "约" + totallenth + "M");
                } else {
                    //如果没有选中的
                    tv_godown.setClickable(false);
                    tv_godown.setEnabled(false);
                    tv_godown.setTextColor(Color.parseColor("#FCF9F4"));
                    tv_godown.setBackgroundResource(R.drawable.bg_radius_apply_no);
                    tv_total.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_godown://全部下载

                String SDPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/fengyouke/" + distributorid + "/";
                DownloadManager downloadManager = DownloadService.getDownloadManager(this);

                if (downListselecte != null) {
                    for (HashMap<String, Object> stringObjectHashMap : downListselecte) {
                        final HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("Theme", stringObjectHashMap.get("Theme"));
                        hashMap.put("Banner2", stringObjectHashMap.get("Banner2"));
                        hashMap.put("Comment", stringObjectHashMap.get("Comment"));
                        hashMap.put("isdown", "true");
//                        stringObjectHashMap.put("isdown", "true");
                        array.add(hashMap);
                        String downStr = stringObjectHashMap.get("Comment").toString();
                        String name = TGmd5.getMD5(downStr);
                        if (downStr.endsWith(".mp3") || downStr.endsWith(".MP3")) {
                            name = name + ".mp3";
                        } else {
                            name = name + ".mp4";
                        }
                        try {
                            downloadManager.addNewDownload(downStr, name, SDPath + name, true, true, new RequestCallBack<File>() {
                                @Override
                                public void onSuccess(ResponseInfo<File> responseInfo) {

                                }

                                @Override
                                public void onFailure(HttpException error, String msg) {
                                    for (int i = 0; i < array.size(); i++) {
                                        if (array.get(i).get("Comment").toString().equals(hashMap.get("Comment").toString())) {
                                            array.get(i).put("isdown", "false");
                                            break;
                                        }
                                    }
                                    mcache.put("xiazaiduilie" + distributorid, array);
                                    EventBus.getDefault().post("downloadclass");
                                }
                            });
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                    }
                }
                mcache.put("xiazaiduilie" + distributorid, array);
                EventBus.getDefault().post("downloadclass");
                buyBatchClassAdapter.setData(downloadList, finishList, array);
                buyBatchClassAdapter.notifyDataSetChanged();
                MyToast.show(this,"任务已添加至下载中^_^\n可到下载中查看进度喔");
//                showOneTextDialog("任务已添加至下载中^_^\n可到下载中查看进度喔");
                tv_godown.setClickable(false);
                tv_godown.setEnabled(false);
                tv_godown.setTextColor(Color.parseColor("#FCF9F4"));
                tv_godown.setBackgroundResource(R.drawable.bg_radius_apply_no);
                tv_total.setVisibility(View.GONE);
                break;
            case R.id.rl_all_check://全部选择
                //如果是第一次全部选择
                if (isAllselect) {
                    //取消全选
                    for (int i = 0; i < downloadList.size(); i++) {
                        downloadList.get(i).put("isCheck", "false");
                    }
                    downListselecte.clear();
                    im_all_check.setBackgroundResource(R.mipmap.checkbox_default_icon);
                    tv_godown.setClickable(false);
                    tv_godown.setEnabled(false);
                    tv_godown.setTextColor(Color.parseColor("#FCF9F4"));
                    tv_godown.setBackgroundResource(R.drawable.bg_radius_apply_no);
                    tv_total.setVisibility(View.GONE);
                } else {

                    //全选
                    downListselecte = new ArrayList<>();
                    downListselecte.clear();
                    for (int i = 0; i < downloadList.size(); i++) {
                        boolean iscandown = true;
                        for (HashMap<String, Object> stringObjectHashMap : array) {
                            if (stringObjectHashMap.get("Comment").toString().equals(downloadList.get(i).get("Comment").toString())) {
//                                downListselecte.remove(downloadList.get(i));
                                iscandown = false;
                                break;
                            }
                        }
                        for (HashMap<String, Object> stringObjectHashMap : finishList) {
                            if (stringObjectHashMap.get("Comment").toString().equals(downloadList.get(i).get("Comment").toString())) {
//                                downListselecte.remove(downloadList.get(i));
                                iscandown = false;
                                break;
                            }
                        }
                        if (iscandown) {
                            downloadList.get(i).put("isCheck", "true");
                            downListselecte.add(downloadList.get(i));
                        }
                    }
                    double totallenth = 0;//选中的里面总共的大小
                    for (HashMap<String, Object> stringObjectHashMap : downListselecte) {
                        if (stringObjectHashMap.get("AreaName") != null && !stringObjectHashMap.get("AreaName").toString().equals("")) {
                            totallenth = totallenth + Double.parseDouble(stringObjectHashMap.get("AreaName").toString());
                        }
                    }
                    im_all_check.setBackgroundResource(R.mipmap.checkbox_check_icon);
                    tv_godown.setClickable(true);//
                    tv_godown.setEnabled(true);
                    tv_godown.setTextColor(Color.parseColor("#ffffff"));
                    tv_godown.setBackgroundResource(R.drawable.bg_radius_apply);
                    tv_total.setVisibility(View.VISIBLE);
                    tv_total.setText("已选中" + downListselecte.size() + "个课程，" + "约" + totallenth + "M");
                }
                buyBatchClassAdapter.setData(downloadList, finishList, array);
                buyBatchClassAdapter.notifyDataSetChanged();
                isAllselect = !isAllselect;
                break;
        }
    }

    private ArrayList<HashMap<String, Object>> downloadList = new ArrayList<HashMap<String, Object>>();

    @Override
    public void OnTeacherDownloadListSuccCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
        try {
            JSONObject jsonObject = new JSONObject(respanse);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("result"));
            JSONArray jsonArray1 = jsonArray.getJSONArray(0);
            downloadList.clear();
            for (int i = 0; i < jsonArray1.length(); i++) {
                JSONObject jsoo = jsonArray1.getJSONObject(i);
                HashMap<String, Object> map1 = new HashMap<String, Object>();
                Iterator it = jsoo.keys();
                while (it.hasNext()) {
                    String key = (String) it.next();
                    String value = jsoo.getString(key);
                    map1.put(key, value);
                }
                map1.put("isCheck", "false");
                if (map1.get("AreaName") == null || map1.get("AreaName").toString().equals("") || map1.get("AreaName").toString().equals("null") || map1.get("AreaName").toString().equals("0")) {

                } else {
                    downloadList.add(map1);
                }
            }
            if (downloadList.size() == 0) {
//                showOneDialog();
                rl_none_one.setVisibility(View.VISIBLE);
            } else {
                rl_none_one.setVisibility(View.GONE);
                buyBatchClassAdapter.setData(downloadList, finishList, array);
                mListView.setAdapter(buyBatchClassAdapter);
                tv_godown.setClickable(false);
                tv_godown.setTextColor(Color.parseColor("#FCF9F4"));
                tv_godown.setBackgroundResource(R.drawable.bg_radius_apply_no);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void OnTeacherDownloadListFialCallBack(String state, String respanse) {
        Log.e("lhaksdfs", "********" + respanse);
        closeLoadingProgressDialog();
    }

    @Override
    public void closeTeacherDownloadListProgress() {

    }

}
