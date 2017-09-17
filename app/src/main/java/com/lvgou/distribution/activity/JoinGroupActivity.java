package com.lvgou.distribution.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.entity.MyGroupEntity;
import com.lvgou.distribution.refresh.PullToRefreshBase;
import com.lvgou.distribution.refresh.PullToRefreshGridView;
import com.lvgou.distribution.request.RequestTask;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.viewholder.MyGroupersViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.common.utils.PreferenceHelper;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.functions.holder.ListViewDataAdapter;
import com.xdroid.request.extension.XDroidRequest;
import com.xdroid.request.extension.config.DataType;
import com.xdroid.request.extension.impl.OnRequestListenerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Snow on 2016/3/21 0021.
 * 入团邀请
 */
public class JoinGroupActivity extends BaseActivity {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.tv_save_code)
    private TextView tv_save_code;
    @ViewInject(R.id.tv_team_num)
    private TextView tv_team_num;
    @ViewInject(R.id.tv_net_address)
    private TextView tv_net_address;
    @ViewInject(R.id.img_scanning)
    private ImageView imageView;

    @ViewInject(R.id.rl_my_group)
    private RelativeLayout rl_my_group;
    @ViewInject(R.id.rl_invite)
    private RelativeLayout rl_invite;

    @ViewInject(R.id.tv_group)
    private TextView tv_group;
    @ViewInject(R.id.tv_invite)
    private TextView tv_invite;
    @ViewInject(R.id.view_group)
    private View view_group;
    @ViewInject(R.id.view_invite)
    private View view_invite;
    private GridView gridView;
    @ViewInject(R.id.pull_refresh_grid)
    private PullToRefreshGridView mPullRefreshGridView;
    @ViewInject(R.id.ll_visibilty)
    private LinearLayout ll_visibility;
    @ViewInject(R.id.rl_none)
    private RelativeLayout rl_gone;
    @ViewInject(R.id.ll_my_group)
    private LinearLayout ll_my_group;
    @ViewInject(R.id.ll_invite_group)
    private LinearLayout ll_invite_group;

    private int pageIndex = 1;
    boolean mIsUp;// 是否上拉加载

    private int total_page;// 总页数，当达到 最大页数，提示最后一页，不在请求


    private ListViewDataAdapter<MyGroupEntity> myGroupEntityListViewDataAdapter;

    private String distributorid = "";

    private String img_path;
    DisplayImageOptions options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invited_group);
        ViewUtils.inject(this);
        tv_title.setText("入团邀请");
        distributorid = PreferenceHelper.readString(JoinGroupActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        tv_team_num.setText(distributorid);
        gridView = mPullRefreshGridView.getRefreshableView();
        initSelected();
        tv_invite.setTextColor(getResources().getColor(R.color.bg_new_guide_black));
        view_invite.setVisibility(View.VISIBLE);
        ll_invite_group.setVisibility(View.VISIBLE);
        if (checkNet()) {
            String sign = TGmd5.getMD5(distributorid);
            getData(distributorid, sign);
        }
        initViewHolder();

        mPullRefreshGridView.getLoadingLayoutProxy(false, true).setPullLabel("下拉加载更多");
        mPullRefreshGridView.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载");
        mPullRefreshGridView.getLoadingLayoutProxy(false, true).setReleaseLabel("释放开始加载");
        mPullRefreshGridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
                mIsUp = false;
                pageIndex = 1;
                String sign = TGmd5.getMD5(distributorid + pageIndex);
                getDataOne(distributorid, pageIndex + "", sign);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                mIsUp = true;
                if (pageIndex < total_page) {
                    pageIndex += 1;
                    String sign = TGmd5.getMD5(distributorid + pageIndex);
                    getDataOne(distributorid, pageIndex + "", sign);
                } else {
                    MyToast.show(JoinGroupActivity.this, "没有更多数据");
                    new CancleRefreshTask().execute();
                }
            }
        });
    }

    @OnClick({R.id.rl_back, R.id.tv_save_code, R.id.tv_team_num, R.id.tv_net_address, R.id.rl_invite, R.id.rl_my_group})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_save_code:
                List<String> urls = new ArrayList<String>();
                String img_path_ = Url.ROOT + img_path;
                urls.add(img_path_);
                if (urls.size() >= 1) {
                    MyToast.show(JoinGroupActivity.this, "正在保存");
                    downimage(urls);
                } else {
                    MyToast.show(JoinGroupActivity.this, "没有图片");
                }
                break;
            case R.id.tv_team_num:
                String text = tv_team_num.getText().toString().trim();
                ClipboardManager myClipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("text", text);
                myClipboard.setPrimaryClip(clipData);
                MyToast.show(JoinGroupActivity.this, "复制成功");
                break;
            case R.id.tv_net_address:
                String text_ = tv_net_address.getText().toString().trim();
                ClipboardManager myClipboard_ = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData_ = ClipData.newPlainText("text", text_);
                myClipboard_.setPrimaryClip(clipData_);
                MyToast.show(JoinGroupActivity.this, "复制成功");
                break;
            case R.id.rl_my_group:
                pageIndex = 1;
                initSelected();
                tv_group.setTextColor(getResources().getColor(R.color.bg_new_guide_black));
                view_group.setVisibility(View.VISIBLE);
                ll_my_group.setVisibility(View.VISIBLE);
                String sign = TGmd5.getMD5(distributorid + pageIndex);
                getDataOne(distributorid, pageIndex + "", sign);
                break;
            case R.id.rl_invite:
                initSelected();
                tv_invite.setTextColor(getResources().getColor(R.color.bg_new_guide_black));
                view_invite.setVisibility(View.VISIBLE);
                ll_invite_group.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void initSelected() {
        tv_invite.setTextColor(getResources().getColor(R.color.bg_bottom_gray));
        tv_group.setTextColor(getResources().getColor(R.color.bg_bottom_gray));
        view_group.setVisibility(View.GONE);
        view_invite.setVisibility(View.GONE);
        ll_my_group.setVisibility(View.GONE);
        ll_invite_group.setVisibility(View.GONE);
    }

    /**
     * 初始化ViewHolder
     */
    public void initViewHolder() {
        myGroupEntityListViewDataAdapter = new ListViewDataAdapter<MyGroupEntity>();
        myGroupEntityListViewDataAdapter.setViewHolderClass(this, MyGroupersViewHolder.class);
        gridView.setAdapter(myGroupEntityListViewDataAdapter);
    }

    /**
     * 有无数据页面显示
     */
    public void showRoGone() {
        ll_visibility.setVisibility(View.GONE);
        rl_gone.setVisibility(View.GONE);
    }

    /**
     * 入团邀请
     *
     * @param distributorid
     * @param sign
     */
    public void getData(String distributorid, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("distributorid", distributorid);
        maps.put("sign", sign);
        RequestTask.getInstance().doJoinGroup(JoinGroupActivity.this, maps, new OnRequestListener());
    }

    private class OnRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObjec = new JSONObject(response);
                String status = jsonObjec.getString("status");
                if (status.equals("1")) {
                    String result = jsonObjec.getString("result");
                    JSONArray array = new JSONArray(result);
                    img_path = array.get(0).toString();
                    options = new DisplayImageOptions.Builder()
                            .showStubImage(R.mipmap.bg_none_good)            // 设置图片下载期间显示的图片
                            .showImageForEmptyUri(R.mipmap.bg_none_good)    // 设置图片Uri为空或是错误的时候显示的图片
                            .showImageOnFail(R.mipmap.bg_none_good)            // 设置图片加载或解码过程中发生错误显示的图片
                            .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                            .cacheOnDisc(true)                            // 设置下载的图片是否缓存在SD卡中
                            .build();
                    ImageLoader.getInstance().displayImage(Url.ROOT + img_path, imageView, options);
                    String address = array.get(1).toString();
                    tv_net_address.setText(address);
                } else if (status.equals("0")) {
                    MyToast.show(JoinGroupActivity.this, jsonObjec.getString("message"));
                    if (jsonObjec.getString("message").equals("请登录")) {
                        openActivity(LoginActivity.class);
                        finish();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    public void getDataOne(String distributorid, String pageindex, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("distributorid", distributorid);
        maps.put("pageindex", pageindex);
        maps.put("sign", sign);
        RequestTask.getInstance().getMyGroupers(JoinGroupActivity.this, maps, new OnOneRequestListener());

    }

    private class OnOneRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                String result = jsonObject.getString("result");
                if (status.equals("1")) {
                    JSONArray array = new JSONArray(result);
                    if (array != null && array.length() > 0) {
                        String item = array.get(0).toString();
                        JSONObject json_result = new JSONObject(item);
                        String date = json_result.getString("Items");
                        total_page = json_result.getInt("TotalPages");
                        JSONArray array_data = new JSONArray(date);

                        if (mIsUp == false) {
                            myGroupEntityListViewDataAdapter.removeAll();
                        } else if (mIsUp == true) {
                            // 上拉加载 不做清空myGroupEntityListViewDataAdapter
                        }
                        if (array_data != null && array_data.length() > 0) {
                            showRoGone();
                            ll_visibility.setVisibility(View.VISIBLE);
                            for (int i = 0; i < array_data.length(); i++) {
                                JSONObject json_data = array_data.getJSONObject(i);
                                String id = json_data.getString("ID");
                                String name = json_data.getString("RealName");
                                String phone = json_data.getString("Mobile");
                                String time = json_data.getString("CreateTime");
                                MyGroupEntity myGroupEntity = new MyGroupEntity(id, time, phone, name);
                                myGroupEntityListViewDataAdapter.append(myGroupEntity);
                            }
                        } else {
                            showRoGone();
                            rl_gone.setVisibility(View.VISIBLE);
                        }
                    }
                } else {
                    MyToast.show(JoinGroupActivity.this, "请求数据失败");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onRequestPrepare() {
            super.onRequestPrepare();
            showLoadingProgressDialog(JoinGroupActivity.this, "");
        }

        @Override
        public void onRequestFailed(String error) {
            super.onRequestFailed(error);
            closeLoadingProgressDialog();
        }

        @Override
        public void onRequestFinish(String data) {
            super.onRequestFinish(data);
            mPullRefreshGridView.onRefreshComplete();
            closeLoadingProgressDialog();
        }
    }

    /**
     * 异步取消刷新
     */
    private class CancleRefreshTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mPullRefreshGridView.onRefreshComplete();
            super.onPostExecute(aVoid);
        }
    }
}
