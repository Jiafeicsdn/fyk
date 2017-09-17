package com.lvgou.distribution.activity;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.entity.ClassEntity;
import com.lvgou.distribution.inter.OnClassifyPostionClickListener;
import com.lvgou.distribution.presenter.MySchdulePresenter;
import com.lvgou.distribution.refresh.PullToRefreshBase;
import com.lvgou.distribution.refresh.PullToRefreshListView;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.MyScheduleView;
import com.lvgou.distribution.viewholder.MyScheduleViewHolder;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.common.utils.PreferenceHelper;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.functions.holder.ListViewDataAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Snow on 2016/9/23.
 * 我的课程，个人中心
 */
public class MyClassesActivity extends BaseActivity implements MyScheduleView, OnClassifyPostionClickListener<ClassEntity> {


    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.ll_visibilty)
    private LinearLayout ll_visibility;
    @ViewInject(R.id.pull_refresh_list)
    private PullToRefreshListView pullToRefreshListView;
    @ViewInject(R.id.rl_none)
    private RelativeLayout rl_none;


    private ListView listView;
    private String distributorid;
    private int pageindex = 1;
    boolean mIsUp;// 是否上拉加载
    private int total_page;// 总页数，当达到 最大页数，提示最后一页，不在请求

    private int starNum = 5;
    private String comment = "";

    private Dialog dialog_evaluate;

    private ListViewDataAdapter<ClassEntity> classEntityListViewDataAdapter;

    private MySchdulePresenter mySchdulePresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_classes);
        ViewUtils.inject(this);
        tv_title.setText("我的课表");

        distributorid = PreferenceHelper.readString(MyClassesActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        listView = pullToRefreshListView.getRefreshableView();

        mySchdulePresenter = new MySchdulePresenter(this);

        initViewHolder();

        initCreateView();
        if (checkNet()) {
            String sign_one = TGmd5.getMD5(distributorid + pageindex);
            mySchdulePresenter.getMySchedule(distributorid, pageindex + "", sign_one);
        }
    }


    @OnClick({R.id.rl_back})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
        }
    }

    /**
     * 下拉刷新 抽取
     */
    public void initCreateView() {
        pullToRefreshListView.getLoadingLayoutProxy(false, true).setPullLabel("下拉加载更多");
        pullToRefreshListView.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载");
        pullToRefreshListView.getLoadingLayoutProxy(false, true).setReleaseLabel("释放开始加载");
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(MyClassesActivity.this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                pullToRefreshListView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                mIsUp = false;
                pageindex = 1;
                String sign = TGmd5.getMD5(distributorid + pageindex);
                mySchdulePresenter.getMySchedule(distributorid, pageindex + "", sign);

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                mIsUp = true;
                if (pageindex < total_page) {
                    pageindex += 1;
                    String sign = TGmd5.getMD5(distributorid + pageindex);
                    mySchdulePresenter.getMySchedule(distributorid, pageindex + "", sign);

                } else {
                    MyToast.show(MyClassesActivity.this, "没有更多数据");
                    new CancleRefreshTask().execute();
                }
            }
        });
    }


    public void initViewHolder() {
        classEntityListViewDataAdapter = new ListViewDataAdapter<ClassEntity>();
        classEntityListViewDataAdapter.setViewHolderClass(this, MyScheduleViewHolder.class);
        MyScheduleViewHolder.setClassEntityOnClassifyPostionClickListener(this);
        listView.setAdapter(classEntityListViewDataAdapter);

    }


    @Override
    public void OnFamousSuccCallBack(String state, String respanse) {
        switch (Integer.parseInt(state)) {
            case 1:
                try {
                    pullToRefreshListView.onRefreshComplete();
                    JSONObject jsonObject = new JSONObject(respanse);
                    String status = jsonObject.getString("status");
                    if (status.equals("1")) {
                        String result = jsonObject.getString("result");
                        JSONArray jsonArray = new JSONArray(result);
                        String data = jsonArray.get(0).toString();
                        JSONObject jsonObject_data = new JSONObject(data);
                        total_page = jsonObject_data.getInt("TotalPages");
                        String items = jsonObject_data.getString("Items");
                        if (mIsUp == false) {// 下拉刷新，清空listViewDataAdapter
                            classEntityListViewDataAdapter.removeAll();
                        } else if (mIsUp == true) {
                            // 上拉加载，不清空 listViewDataAdapter
                        }

                        JSONArray array_items = new JSONArray(items);
                        if (array_items != null && array_items.length() > 0) {
                            showOrGone();
                            ll_visibility.setVisibility(View.VISIBLE);
                            for (int i = 0; i < array_items.length(); i++) {
                                JSONObject jsonObject_ = array_items.getJSONObject(i);
                                String id = jsonObject_.getString("ID");
                                String teacherID = jsonObject_.getString("TeacherID");
                                String img_path = jsonObject_.getString("Banner2");
                                String name = jsonObject_.getString("TeacherName");
                                String title = jsonObject_.getString("Theme");
                                String time = jsonObject_.getString("ZBTime");
                                String state_ = jsonObject_.getString("State");
                                String grade = jsonObject_.getString("Grade");
                                ClassEntity classEntity = new ClassEntity(id, img_path, name, title, grade, state_, time, teacherID);
                                classEntityListViewDataAdapter.append(classEntity);
                            }
                        } else {
                            showOrGone();
                            rl_none.setVisibility(View.VISIBLE);
                        }
                    } else {
                        showOrGone();
                        rl_none.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                try {
                    JSONObject jsonObject_two = new JSONObject(respanse);
                    String status = jsonObject_two.getString("status");
                    if (status.equals("1")) {
                        MyToast.show(MyClassesActivity.this, "评分成功");
                        pageindex = 1;
                        String sign = TGmd5.getMD5(distributorid + pageindex);
                        mySchdulePresenter.getMySchedule(distributorid, pageindex + "", sign);
                    } else {
                        MyToast.show(MyClassesActivity.this, "评分失败");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void OnFamousFialCallBack(String state, String respanse) {
        switch (Integer.parseInt(state)) {
            case 1:
                MyToast.show(MyClassesActivity.this, "请求失败");
                break;
            case 2:
                MyToast.show(MyClassesActivity.this, "请求失败");
                break;
        }
    }

    @Override
    public void closeProgress() {

    }


    /**
     * 点击事件回调
     *
     * @param itemData
     * @param postion
     */
    @Override
    public void onClassifyPostionClick(ClassEntity itemData, int postion) {
        Bundle bundle = new Bundle();
        switch (postion) {
            case 1:
                bundle.putString("index", "0");
                bundle.putString("id", itemData.getTeacherID());
                openActivity(FamousTeacherDetialActivity.class, bundle);
                break;
            case 2:
                showEvaluateDialog(itemData.getId());
                break;
        }
    }


    /**
     * 有无数据页面显示
     */
    public void showOrGone() {
        ll_visibility.setVisibility(View.GONE);
        rl_none.setVisibility(View.GONE);
    }


    //评价弹框
    public void showEvaluateDialog(final String studyid) {
        dialog_evaluate = new Dialog(MyClassesActivity.this, R.style.Mydialog);
        View view1 = View.inflate(MyClassesActivity.this, R.layout.dailog_evaluate_classes, null);
        RatingBar ratingBar = (RatingBar) view1.findViewById(R.id.room_ratingbar);
        final TextView tv_levels = (TextView) view1.findViewById(R.id.tv_level);
        final EditText et_evaluate_content = (EditText) view1.findViewById(R.id.et_evaluate_content);
        et_evaluate_content.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        //改变默认的单行模式
        et_evaluate_content.setSingleLine(false);
        //水平滚动设置为False
        et_evaluate_content.setHorizontallyScrolling(false);
        et_evaluate_content.setHint("评论，100字以内");
        TextView tv_commit = (TextView) view1.findViewById(R.id.tv_commit);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (rating == 5.0) {
                    tv_levels.setText("极好");
                    starNum = 5;
                } else if (rating == 4.0) {
                    tv_levels.setText("推荐");
                    starNum = 4;
                } else if (rating == 3.0) {
                    tv_levels.setText("良好");
                    starNum = 3;
                } else if (rating == 2.0) {
                    tv_levels.setText("一般");
                    starNum = 2;
                } else if (rating == 1.0) {
                    tv_levels.setText("极差");
                    starNum = 1;
                }
            }
        });

        tv_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsUp = false;
                comment = et_evaluate_content.getText().toString().trim();
                String sign = TGmd5.getMD5(distributorid + studyid + starNum + comment);
                mySchdulePresenter.doGradeSchedule(distributorid, studyid, starNum + "", comment, sign);
                dialog_evaluate.dismiss();
            }
        });

        dialog_evaluate.setContentView(view1);
        dialog_evaluate.show();
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
            pullToRefreshListView.onRefreshComplete();
            super.onPostExecute(aVoid);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
