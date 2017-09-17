package com.lvgou.distribution.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.util.LogUtils;
import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.FamousTeacherDetialActivity;
import com.lvgou.distribution.activity.UserPersonalCenterActivity;
import com.lvgou.distribution.adapter.HomePageAdapter;
import com.lvgou.distribution.bean.FengCircleDynamicBean;
import com.lvgou.distribution.bean.FengCircleImageBean;
import com.lvgou.distribution.bean.FengWenBean;
import com.lvgou.distribution.bean.FilterBean;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.entity.ClassEntity;
import com.lvgou.distribution.entity.ClassesBackEntity;
import com.lvgou.distribution.inter.OnClassifyPostionClickListener;
import com.lvgou.distribution.inter.OnListItemClickListener;
import com.lvgou.distribution.presenter.AllClassPresenter;
import com.lvgou.distribution.presenter.HomePagePresenter;
import com.lvgou.distribution.presenter.MySchdulePresenter;
import com.lvgou.distribution.refresh.PullToRefreshBase;
import com.lvgou.distribution.refresh.PullToRefreshListView;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.AllClassesView;
import com.lvgou.distribution.view.FengCircleView;
import com.lvgou.distribution.view.MyScheduleView;
import com.lvgou.distribution.viewholder.ClassesBackViewHolder;
import com.lvgou.distribution.viewholder.MyScheduleViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.functions.holder.ListViewDataAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/13.
 * 个人主页 课程  列表
 */
public class UserPersonStudyFragment extends Fragment implements AllClassesView, OnListItemClickListener<ClassesBackEntity> {
    boolean mIsUp;// 是否上拉加载
    private PullToRefreshListView pullToRefreshListView;
    public static ListView listView;
    OnArticleSelectedListener mListener;
    private int dataPageCount = 0;
    private int pageindex = 1;
    private String distributorid;
    private int starNum = 5;
    private String comment = "";

    private Dialog dialog_evaluate;
    private LinearLayout ll_visibility;
    private RelativeLayout rl_none;
    private int total_page;// 总页数，当达到 最大页数，提示最后一页，不在请求


    private AllClassPresenter allClassPresenter;

    private ListViewDataAdapter<ClassesBackEntity> classesBackEntityListViewDataAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_no_title_list, container, false);
        pullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
        listView = pullToRefreshListView.getRefreshableView();
        pageindex=1;
        ll_visibility = (LinearLayout) view.findViewById(R.id.ll_visibilty);
        rl_none = (RelativeLayout) view.findViewById(R.id.rl_none);
        mListener = (OnArticleSelectedListener) getActivity();


        distributorid = mListener.getseeDistributorId();


        allClassPresenter = new AllClassPresenter(this);

        initCreateView();
        initViewHolder();

        String sign = TGmd5.getMD5(distributorid + pageindex);
        allClassPresenter.getMyClass(distributorid, pageindex + "", sign);

        return view;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void initViewHolder() {
        classesBackEntityListViewDataAdapter = new ListViewDataAdapter<ClassesBackEntity>();
        classesBackEntityListViewDataAdapter.setViewHolderClass(this, ClassesBackViewHolder.class);
        ClassesBackViewHolder.setOnListItemClickListener(this);
        listView.setAdapter(classesBackEntityListViewDataAdapter);

    }


    public void initCreateView() {
        pullToRefreshListView.getLoadingLayoutProxy(false, true).setPullLabel("下拉加载更多");
        pullToRefreshListView.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载");
        pullToRefreshListView.getLoadingLayoutProxy(false, true).setReleaseLabel("释放开始加载");
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                // Update the LastUpdatedLabel
                pullToRefreshListView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                mIsUp = false;
                pageindex = 1;
                String sign = TGmd5.getMD5(distributorid + pageindex);
                allClassPresenter.getMyClass(distributorid, pageindex + "", sign);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                mIsUp = true;
                if (pageindex < total_page) {
                    pageindex += 1;
                    String sign = TGmd5.getMD5(distributorid + pageindex);
                    allClassPresenter.getMyClass(distributorid, pageindex + "", sign);
                } else {
                    MyToast.show(getActivity(), "没有更多数据");
                    new CancleRefreshTask().execute();
                }
            }
        });
    }

    /**
     * 有无数据页面显示
     */
    public void showRoGone() {
        ll_visibility.setVisibility(View.GONE);
        rl_none.setVisibility(View.GONE);
    }


    @Override
    public void OnRequestSuccCallBack(String state, String respanse) {
        switch (Integer.parseInt(state)) {
            case 3:
                try {
                    pullToRefreshListView.onRefreshComplete();
                    JSONObject jsonObject = new JSONObject(respanse);
                    String status = jsonObject.getString("status");
                    if (status.equals("1")) {
                        String result = jsonObject.getString("result");
                        JSONArray jsonArray = new JSONArray(result);
                        String data = jsonArray.get(0).toString();
                        JSONObject jsonObject_one = new JSONObject(data);
                        total_page = jsonObject_one.getInt("DataPageCount");
                        if (mIsUp == false) {
                            classesBackEntityListViewDataAdapter.removeAll();
                        } else {
                        }

                        String items = jsonObject_one.getString("Data");
                        JSONArray array = new JSONArray(items);
                        if (array != null && array.length() > 0) {
                            showRoGone();
                            ll_visibility.setVisibility(View.VISIBLE);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject json_ = array.getJSONObject(i);
                                String id_ = json_.getString("ID");
                                String img_path = json_.getString("Banner2");
                                String name = json_.getString("TeacherName");
                                String title = json_.getString("Theme");
                                String sign_num = json_.getString("People_Apply");
                                String tuanbi = json_.getString("BMTuanBi");
                                ClassesBackEntity classesBackEntity = new ClassesBackEntity(id_, tuanbi, sign_num, title, name, img_path);
                                classesBackEntityListViewDataAdapter.append(classesBackEntity);
                            }
                        } else {
                            showRoGone();
                            rl_none.setVisibility(View.VISIBLE);
                        }
                    } else {
                        showRoGone();
                        rl_none.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;
        }
    }

    @Override
    public void OnRequestFialCallBack(String state, String respanse) {
        switch (Integer.parseInt(state)) {
            case 3:
                MyToast.show(getActivity(), "请求失败");
                showRoGone();
                rl_none.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void closeProgress() {

    }


    @Override
    public void onListItemClick(ClassesBackEntity itemData) {
        Log.e("khsadfkjas", "---------"+itemData );
        Bundle bundle = new Bundle();
        bundle.putString("id", itemData.getId());
        bundle.putString("index", "0");
        Intent intent = new Intent(getActivity(), FamousTeacherDetialActivity.class);
        intent.putExtras(bundle);
        getActivity().startActivity(intent);
    }

    public interface OnArticleSelectedListener {
        public String onArticleSelected();

        public String getseeDistributorId();
    }


    /**
     * 异步取消刷新
     */
    private class CancleRefreshTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            pullToRefreshListView.onRefreshComplete();
            super.onPostExecute(aVoid);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getClass().getName());
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getClass().getName());
    }

}
