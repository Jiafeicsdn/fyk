package com.lvgou.distribution.fragment;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.util.LogUtils;
import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.ApplySuccessActivity;
import com.lvgou.distribution.activity.ApplyTeacherActivity;
import com.lvgou.distribution.activity.UserPersonalCenterActivity;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.entity.TeacherSeatEntity;
import com.lvgou.distribution.inter.OnClassifyPostionClickListener;
import com.lvgou.distribution.presenter.TeacherSeatPresenter;
import com.lvgou.distribution.refresh.PullToRefreshBase;
import com.lvgou.distribution.refresh.PullToRefreshListView;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.CustomProgressDialog;
import com.lvgou.distribution.view.TeacherSeatView;
import com.lvgou.distribution.viewholder.TeacherSeatViewHolder;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.common.utils.PreferenceHelper;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.functions.holder.ListViewDataAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Snow on 2016/9/19.
 * 学院讲师列表
 */
public class TeacherSeatFragment extends Fragment implements View.OnClickListener, TeacherSeatView, OnClassifyPostionClickListener<TeacherSeatEntity> {


    private EditText et_search02;
    private ImageView img_search;
    private LinearLayout ll_visibilty;
    private PullToRefreshListView pullToRefreshListView;
    private RelativeLayout rl_none;
    private ImageView img_apply_teacher;

    private Dialog dialog_progress;

    private ListView listView;

    private int pageindex = 1;
    boolean mIsUp;// 是否上拉加载
    private int total_page;// 总页数，当达到 最大页数，提示最后一页，不在请求
    private String key = "";
    private String distributorid = "";


    private ListViewDataAdapter<TeacherSeatEntity> teacherSeatEntityListViewDataAdapter;

    private TeacherSeatPresenter teacherSeatPresenter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_teacher_seat, container, false);
        initView(view);

        distributorid = PreferenceHelper.readString(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID, "0");

        listView = pullToRefreshListView.getRefreshableView();

        teacherSeatPresenter = new TeacherSeatPresenter(this);


        initCreateView();
        serachData();

        initViewHolder();
        img_apply_teacher.setOnClickListener(this);
        key = et_search02.getText().toString().trim();

        showProgressDialog();
        String sign_one = TGmd5.getMD5(distributorid + pageindex + key);
        teacherSeatPresenter.getTeacherSeatData(distributorid, pageindex + "", key, sign_one);

        return view;
    }


    public void initView(View view) {
        et_search02 = (EditText) view.findViewById(R.id.et_search02);
        img_search = (ImageView) view.findViewById(R.id.img_search);
        ll_visibilty = (LinearLayout) view.findViewById(R.id.ll_visibilty);
        pullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
        rl_none = (RelativeLayout) view.findViewById(R.id.rl_none);
        img_apply_teacher = (ImageView) view.findViewById(R.id.img_apply_teacher);
    }


    public void initViewHolder() {
        teacherSeatEntityListViewDataAdapter = new ListViewDataAdapter<TeacherSeatEntity>();
        teacherSeatEntityListViewDataAdapter.setViewHolderClass(this, TeacherSeatViewHolder.class);
        TeacherSeatViewHolder.setOnClassifyPostionClickListener(this);
        listView.setAdapter(teacherSeatEntityListViewDataAdapter);
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
                String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                pullToRefreshListView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                mIsUp = false;
                pageindex = 1;
                key = et_search02.getText().toString().trim();
                String sign_one = TGmd5.getMD5(distributorid + pageindex + key);
                teacherSeatPresenter.getTeacherSeatData(distributorid, pageindex + "", key, sign_one);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                mIsUp = true;
                if (pageindex < total_page) {
                    pageindex += 1;
                    key = et_search02.getText().toString().trim();
                    String sign_one = TGmd5.getMD5(distributorid + pageindex + key);
                    teacherSeatPresenter.getTeacherSeatData(distributorid, pageindex + "", key, sign_one);
                } else {
                    MyToast.show(getActivity(), "没有更多数据");
                    new CancleRefreshTask().execute();
                }
            }
        });
    }


    /**
     * 搜索操作
     */
    public void serachData() {
        et_search02.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // 隐藏软键盘
                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager.isActive()) {
                    inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                }
                // 执行获取数据操作
                pageindex = 1;
                key = et_search02.getText().toString().trim();
                String sign_one = TGmd5.getMD5(distributorid + pageindex + key);
                teacherSeatPresenter.getTeacherSeatData(distributorid, pageindex + "", key, sign_one);

                return true;
            }
        });

        et_search02.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (et_search02.getText().length() == 0) {
                    img_search.setVisibility(View.VISIBLE);
                } else {
                    img_search.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    /**
     * 空数据显示与隐藏
     */
    public void showOrGone() {
        ll_visibilty.setVisibility(View.GONE);
        rl_none.setVisibility(View.GONE);
    }


    /**
     * 申请讲师按钮
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_apply_teacher:
                String sign = TGmd5.getMD5(distributorid);
                teacherSeatPresenter.getApplyState(distributorid, sign);
                break;
        }
    }


    /**
     * 成功回调
     *
     * @param state
     * @param respanse
     */
    @Override
    public void OnRequestSuccCallBack(String state, String respanse) {
        switch (Integer.parseInt(state)) {
            case 1:
                try {
                    pullToRefreshListView.onRefreshComplete();
                    JSONObject jsonObject = new JSONObject(respanse);
                    String state_ = jsonObject.getString("status");
                    if (state_.equals("1")) {
                        String result = jsonObject.getString("result");
                        JSONArray array = new JSONArray(result);

                        /************个人信息***********/
                        String info_data = array.get(1).toString().trim();
                        JSONObject json_info = new JSONObject(info_data);
                        String userType = json_info.getString("UserType");
                        if (userType.equals("2")) {
                            img_apply_teacher.setVisibility(View.VISIBLE);
                        } else {
                            img_apply_teacher.setVisibility(View.GONE);
                        }
                        PreferenceHelper.write(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.USER_TYPE, userType);
                        String State = json_info.getString("State");
                        PreferenceHelper.write(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.STATE, State);
                        String tuanbi_ = json_info.getString("TuanBi");
                        PreferenceHelper.write(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.TUANBI, tuanbi_);

                        /************列表信息**********/
                        String data = array.get(0).toString().trim();
                        JSONObject json_ = new JSONObject(data);
                        total_page = json_.getInt("TotalPages");
                        String items = json_.getString("Items");
                        if (mIsUp == false) {// 下拉刷新，清空listViewDataAdapter
                            teacherSeatEntityListViewDataAdapter.removeAll();
                        } else if (mIsUp == true) {
                            // 上拉加载，不清空 listViewDataAdapter
                        }

                        DisplayMetrics dm = new DisplayMetrics();
                        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
                        float density = dm.density;
                        JSONArray array_data = new JSONArray(items);
                        if (array_data != null && array_data.length() > 0) {
                            showOrGone();
                            ll_visibilty.setVisibility(View.VISIBLE);
                            for (int i = 0; i < array_data.length(); i++) {
                                JSONObject json_s = array_data.getJSONObject(i);
                                String id = json_s.getString("ID");
                                String name = json_s.getString("RealName");
                                String img_path = json_s.getString("PicUrl");
                                String state_is = json_s.getString("State");
                                String class_num = json_s.getString("TuanBi");
                                String student_num = json_s.getString("Star");
                                String studylist = json_s.getString("studylist");
                                TeacherSeatEntity teacherSeatEntity = new TeacherSeatEntity(id, img_path, name, state_is, student_num, class_num, studylist, density + "");
                                teacherSeatEntityListViewDataAdapter.append(teacherSeatEntity);
                            }
                        } else {
                            showOrGone();
                            rl_none.setVisibility(View.VISIBLE);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                try {
                    JSONObject jsonObject_one = new JSONObject(respanse);
                    String state_s = jsonObject_one.getString("status");
                    if (state_s.equals("1")) {
                        String result = jsonObject_one.getString("result");
                        JSONArray array = new JSONArray(result);
                        String array_data = array.get(0).toString();
                        if (array_data.equals("0")) {
                            Intent intent = new Intent(getActivity(), ApplyTeacherActivity.class);
                            getActivity().startActivity(intent);
                        } else if (array_data.equals("1")) {
                            Intent intent = new Intent(getActivity(), ApplySuccessActivity.class);
                            getActivity().startActivity(intent);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    /**
     * 失败回调
     *
     * @param state
     * @param respanse
     */
    @Override
    public void OnRequestFialCallBack(String state, String respanse) {
        closeProgressDialog();
        pullToRefreshListView.onRefreshComplete();
        MyToast.show(getActivity(), "请求失败");
    }

    @Override
    public void closeProgress() {
        closeProgressDialog();
    }

    public void showProgressDialog() {
        dialog_progress = createLoadingDialog(getActivity(), "");
        dialog_progress.show();
    }

    public void closeProgressDialog() {
        if (dialog_progress != null && dialog_progress.isShowing()) {
            dialog_progress.dismiss();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        teacherSeatPresenter.attach(this);
        MobclickAgent.onPageStart(getClass().getName());
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        teacherSeatPresenter.dettach();
        MobclickAgent.onPageEnd(getClass().getName());
    }

    /***
     * tiem 点击事件操作
     *
     * @param itemData
     * @param postion
     */
    @Override
    public void onClassifyPostionClick(TeacherSeatEntity itemData, int postion) {
        Bundle bundle = new Bundle();
        switch (postion) {
            case 1:
                bundle.putInt("distributorid", Integer.parseInt(itemData.getId()));
                Intent intent = new Intent(getActivity(), UserPersonalCenterActivity.class);
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
                break;
            case 3:
                bundle.putInt("distributorid", Integer.parseInt(itemData.getId()));
                Intent intent_one = new Intent(getActivity(), UserPersonalCenterActivity.class);
                intent_one.putExtras(bundle);
                getActivity().startActivity(intent_one);
                break;
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
            pullToRefreshListView.onRefreshComplete();
            super.onPostExecute(aVoid);
        }
    }

    /**
     * 得到自定义的progressDialog
     *
     * @param context
     * @param msg
     * @return
     */
    public static Dialog createLoadingDialog(Context context, String msg) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.dialog_loding, null);// 得到加载view
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
        // main.xml中的ImageView
        ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
        TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
        // 加载动画
        Animation hyperspaceJumpAnimation = android.view.animation.AnimationUtils.loadAnimation(context, R.anim.loading_animation);
        // 使用ImageView显示动画
        spaceshipImage.startAnimation(hyperspaceJumpAnimation);
        tipTextView.setText(msg);// 设置加载信息
        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog
        loadingDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);// 去掉dialog内容以外的黑色背景色
        loadingDialog.setCancelable(false);// 不可以用“返回键”取消
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT));// 设置布局
        return loadingDialog;
    }

}
