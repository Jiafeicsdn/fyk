package com.lvgou.distribution.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lvgou.distribution.R;
import com.lvgou.distribution.adapter.MoreReminderAdapter;
import com.lvgou.distribution.presenter.StudentFragmentDashangPresenter;
import com.lvgou.distribution.utils.ImageUtils;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.CircleImageView;
import com.lvgou.distribution.view.ProgressHUD;
import com.lvgou.distribution.view.StudentFragmentDashangView;
import com.lvgou.distribution.widget.XListView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * 打赏记录
 */
public class MoreReminderActivity extends BaseActivity implements StudentFragmentDashangView, View.OnClickListener, XListView.IXListViewListener {
    private int page = 1;
    private StudentFragmentDashangPresenter studentFragmentDashangPresenter;
    private View header;
    private MoreReminderAdapter moreReminderAdapter;
    private String studyid;
    protected ProgressHUD hud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_reminder);
//        hud=new ProgressHUD(this);
        Bundle bundle = getIntent().getExtras();
        studyid = bundle.getString("studyid");
        studentFragmentDashangPresenter = new StudentFragmentDashangPresenter(this);
        String sign = TGmd5.getMD5(studyid + page);
        hud = ProgressHUD.show(this, "加载中...", true, true, null);
        studentFragmentDashangPresenter.dashangDetail(studyid, page, sign);
        dataList.clear();
        initView();
        initClick();



       /* options = new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.icon_none_bee)            // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.icon_none_bee)    // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.icon_none_bee)            // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(false)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(false)                            // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(360))    // 设置成圆角图片
                .build();
        ImageLoader.getInstance().displayImage(ImageUtils.getInstance().getPath(distributorid), img_head, options);*/


    }

    private ImageView back;
    private XListView mListView;
    private CircleImageView header_img_head;
    private TextView tv_name_header;
    private TextView tv_total_number;


    private void initView() {
        header = LayoutInflater.from(this).inflate(R.layout.activity_reminder_header, null);
        back = (ImageView) findViewById(R.id.back);
        header_img_head = (CircleImageView) header.findViewById(R.id.img_head_header);
        tv_name_header = (TextView) header.findViewById(R.id.tv_name);
        tv_total_number = (TextView) header.findViewById(R.id.tv_total_number);


        mListView = (XListView) findViewById(R.id.list_view);

        moreReminderAdapter = new MoreReminderAdapter(this);
        mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnable(true);
//        mListView.stopLoadMore();
        mListView.setAutoLoadEnable(false);
        mListView.setXListViewListener(this);
        mListView.setRefreshTime(getTime());
        mListView.setDivider(null);
        mListView.setAdapter(moreReminderAdapter);
        mListView.addHeaderView(header);


    }

    private void initClick() {
        back.setOnClickListener(this);
    }

    @Override
    public void OnStudentFragmentDashangSuccCallBack(String state, String respanse) {
//        hud = ProgressHUD.show(this, "加载中...", true, true, null);
        if (hud.isShowing()) {
            hud.dismiss();
        }
        ArrayList<HashMap<String, Object>> arr_list = new ArrayList<HashMap<String, Object>>();
        dataListTmp.clear();
        try {
            JSONObject jsonObject = new JSONObject(respanse);
            if (jsonObject.getInt("status") == 1) {
                JSONArray jsonArray = new JSONArray(jsonObject.getString("result"));
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsoo = jsonArray.getJSONObject(i);
                    HashMap<String, Object> map1 = new HashMap<String, Object>();
                    Iterator it = jsoo.keys();
                    while (it.hasNext()) {
                        String key = (String) it.next();
                        String value = jsoo.getString(key);
                        map1.put(key, value);
                    }
                    arr_list.add(map1);
                }
                tv_total_number.setText(arr_list.get(0).get("TuanBi").toString());
                tv_name_header.setText(arr_list.get(0).get("RealName").toString());
                Glide.with(this).load(ImageUtils.getInstance().getPath(arr_list.get(0).get("ID").toString())).into(header_img_head);
                JSONObject jsonObject2 = new JSONObject(arr_list.get(1));
                JSONArray jsonArray2 = new JSONArray(jsonObject2.get("Items").toString());
                for (int i = 0; i < jsonArray2.length(); i++) {
                    JSONObject jsoo = jsonArray2.getJSONObject(i);
                    HashMap<String, Object> map1 = new HashMap<String, Object>();
                    Iterator it = jsoo.keys();
                    while (it.hasNext()) {
                        String key = (String) it.next();
                        String value = jsoo.getString(key);
                        map1.put(key, value);
                    }
                    dataListTmp.add(map1);
                }
                if (dataListTmp.size() < 8) {
                    mHandler.sendEmptyMessage(2);
                } else {
                    mHandler.sendEmptyMessage(1);
                }

            } else {
                Log.e("ajsfhdkahkfdhs", "查询失败");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private final mainHandler mHandler = new mainHandler(this);

    private static class mainHandler extends Handler {
        private final WeakReference<MoreReminderActivity> mActivity;

        public mainHandler(MoreReminderActivity activity) {
            mActivity = new WeakReference<MoreReminderActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            MoreReminderActivity activity = mActivity.get();
            if (activity != null) {
                activity.flushDataList(msg);
            }
        }
    }

    public ArrayList<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();
    public ArrayList<HashMap<String, Object>> dataListTmp = new ArrayList<HashMap<String, Object>>();

    public void flushDataList(Message msg) {
//        if (dataListTmp.isEmpty()) {
//            imageNoData.setVisibility(View.VISIBLE);
//            mListView.setVisibility(View.GONE);
//        } else {
//            imageNoData.setVisibility(View.GONE);
//            mListView.setVisibility(View.VISIBLE);
//        }
        if (msg.what == 1) {
//            dataList.clear();
            dataList.addAll(dataListTmp);
            if (dataList != null) {
                moreReminderAdapter.setData(dataList);
                moreReminderAdapter.notifyDataSetChanged();
                mListView.setPullLoadEnable(true);
            } else {
                mListView.setPullLoadEnable(false);
            }

            dataListTmp.clear();
           /* if (hud.isShowing()) {
                hud.dismiss();
            }*/
            mListView.stopRefresh();
        } else if (msg.what == 2) {
            dataList.addAll(dataListTmp);
            moreReminderAdapter.setData(dataList);
            moreReminderAdapter.notifyDataSetChanged();
            dataListTmp.clear();

            mListView.setPullLoadEnable(false);
         /*   if (hud.isShowing()) {
                hud.dismiss();
            }*/
            mListView.stopLoadMore();
        }
    }

    @Override
    public void OnStudentFragmentDashangFialCallBack(String state, String respanse) {
        Log.e("ahkfksaj", "----- "+respanse );
    }

    @Override
    public void closeProgress() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

    @Override
    public void onRefresh() {
        hud = ProgressHUD.show(this, "加载中...", true, true, null);
        page = 1;
        dataList.clear();
        String sign = TGmd5.getMD5(studyid + page);
        studentFragmentDashangPresenter.dashangDetail(studyid, page, sign);
    }

    @Override
    public void onLoadMore() {
        page++;
        String sign = TGmd5.getMD5(studyid + page);
        hud = ProgressHUD.show(this, "加载中...", true, true, null);
        studentFragmentDashangPresenter.dashangDetail(studyid, page, sign);
    }
}
