package com.lvgou.distribution.fragment;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.FamousTeacherDetialActivity;
import com.lvgou.distribution.constants.Constants;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.entity.ClassesBackEntity;
import com.lvgou.distribution.entity.ClassifyEntity;
import com.lvgou.distribution.inter.OnClassifyPostionClickListener;
import com.lvgou.distribution.inter.OnListItemClickListener;
import com.lvgou.distribution.presenter.AllClassPresenter;
import com.lvgou.distribution.refresh.PullToRefreshBase;
import com.lvgou.distribution.refresh.PullToRefreshListView;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.AllClassesView;
import com.lvgou.distribution.view.CustomProgressDialog;
import com.lvgou.distribution.viewholder.ClassesBackViewHolder;
import com.lvgou.distribution.viewholder.GridBackViewHolder;
import com.lvgou.distribution.viewholder.GridBackViewOneHolder;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.common.utils.PreferenceHelper;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.functions.holder.ListViewDataAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Snow on 2016/9/19.
 * 学院课程
 */
public class AllClassesFragment extends Fragment implements OnListItemClickListener<ClassesBackEntity>, OnClassifyPostionClickListener<ClassifyEntity>, View.OnClickListener, AllClassesView {


    private LinearLayout ll_gridview;
    private GridView grid_view;
    private RelativeLayout rl_biaotilan;
    private TextView tv_classify_name;
    private RelativeLayout rl_by_date;
    private RelativeLayout rl_by_signup;
    private TextView tv_by_date;
    private TextView tv_by_signup;
    private View view_date;
    private View view_signup;

    private LinearLayout ll_visibilty;
    private PullToRefreshListView pullToRefreshListView;
    private RelativeLayout rl_none;


    private ListView lv_list;
    private String distributorid = "";

    boolean mIsUp;// 是否上拉加载
    private int total_page;// 总页数，当达到 最大页数，提示最后一页，不在请求

    private int pageindex = 1;
    private String tag = "";
    private String teacher_id = "0";
    private String type = "1";


    private String start_price = "";
    private String end_price = "";
    private AllClassPresenter allClassPresenter;

    private Dialog dialog_progress;

    private ListViewDataAdapter<ClassesBackEntity> classesBackEntityListViewDataAdapter;
    private ListViewDataAdapter<ClassifyEntity> classifyGridViewEntityListViewDataAdapter;// 一级分类
    private String state = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view_ = inflater.inflate(R.layout.activity_class_back, container, false);
        initView(view_);

        lv_list = pullToRefreshListView.getRefreshableView();
        distributorid = PreferenceHelper.readString(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);


        allClassPresenter = new AllClassPresenter(this);


        tv_by_date.setTextColor(getResources().getColor(R.color.bg_balck_three));
        view_date.setVisibility(View.VISIBLE);
        tv_by_signup.setTextColor(getResources().getColor(R.color.bg_gray_three));
        view_signup.setVisibility(View.INVISIBLE);


        initCreateView();
        initViewHolder();


        showProgressDialog();
        String sign_ = TGmd5.getMD5(distributorid);
        allClassPresenter.getHotTag(distributorid, sign_);

        String sign = TGmd5.getMD5(teacher_id + tag + pageindex + type);
        allClassPresenter.getAllClassesData(teacher_id, tag, pageindex + "", type, sign);

        rl_by_date.setOnClickListener(this);
        rl_by_signup.setOnClickListener(this);
        rl_biaotilan.setOnClickListener(this);

        ll_gridview.setVisibility(View.VISIBLE);
        rl_biaotilan.setVisibility(View.GONE);

        lv_list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem >= 2) {
                    ll_gridview.setVisibility(View.GONE);
                    rl_biaotilan.setVisibility(View.VISIBLE);
                    tv_classify_name.setText(Constants.SELECTE_POSITION08);
                }
            }
        });

        return view_;
    }


    public void initView(View view_) {
        ll_gridview = (LinearLayout) view_.findViewById(R.id.ll_gridview);
        grid_view = (GridView) view_.findViewById(R.id.grid_view);
        rl_biaotilan = (RelativeLayout) view_.findViewById(R.id.rl_biaotilan);
        tv_classify_name = (TextView) view_.findViewById(R.id.tv_classify_name);
        rl_by_date = (RelativeLayout) view_.findViewById(R.id.rl_by_date);
        rl_by_signup = (RelativeLayout) view_.findViewById(R.id.rl_signup);
        tv_by_date = (TextView) view_.findViewById(R.id.tv_by_date);
        tv_by_signup = (TextView) view_.findViewById(R.id.tv_by_signup);
        view_date = (View) view_.findViewById(R.id.view_by_date);
        view_signup = (View) view_.findViewById(R.id.view_signup);
        ll_visibilty = (LinearLayout) view_.findViewById(R.id.ll_visibilty);
        pullToRefreshListView = (PullToRefreshListView) view_.findViewById(R.id.pull_refresh_list);
        rl_none = (RelativeLayout) view_.findViewById(R.id.rl_none);
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
                String sign = TGmd5.getMD5(teacher_id + tag + pageindex + "" + type);
                allClassPresenter.getAllClassesData(teacher_id, tag, pageindex + "", type, sign);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                mIsUp = true;
                if (pageindex < total_page) {
                    pageindex += 1;
                    String sign = TGmd5.getMD5(teacher_id + tag + pageindex + "" + type);
                    allClassPresenter.getAllClassesData(teacher_id, tag, pageindex + "", type, sign);
                } else {
                    MyToast.show(getActivity(), "没有更多数据");
                    new CancleRefreshTask().execute();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_by_date:
                type = "1";
                pageindex = 1;
                tv_by_date.setTextColor(getResources().getColor(R.color.bg_balck_three));
                view_date.setVisibility(View.VISIBLE);
                tv_by_signup.setTextColor(getResources().getColor(R.color.bg_gray_three));
                view_signup.setVisibility(View.INVISIBLE);
                showProgressDialog();
                classesBackEntityListViewDataAdapter.removeAll();
                String sign = TGmd5.getMD5(teacher_id + tag + pageindex + type);
                allClassPresenter.getAllClassesData(teacher_id, tag, pageindex + "", type, sign);
                break;
            case R.id.rl_signup:
                type = "2";
                pageindex = 1;
                tv_by_date.setTextColor(getResources().getColor(R.color.bg_gray_three));
                view_date.setVisibility(View.INVISIBLE);
                tv_by_signup.setTextColor(getResources().getColor(R.color.bg_balck_three));
                view_signup.setVisibility(View.VISIBLE);
                showProgressDialog();
                classesBackEntityListViewDataAdapter.removeAll();
                String sign_ = TGmd5.getMD5(teacher_id + tag + pageindex + type);
                allClassPresenter.getAllClassesData(teacher_id, tag, pageindex + "", type, sign_);
                break;
            case R.id.rl_biaotilan:
                lv_list.setSelection(0);
                rl_biaotilan.setVisibility(View.GONE);
                ll_gridview.setVisibility(View.VISIBLE);
                break;
        }
    }


    public void initViewHolder() {
        classesBackEntityListViewDataAdapter = new ListViewDataAdapter<ClassesBackEntity>();
        classesBackEntityListViewDataAdapter.setViewHolderClass(this, ClassesBackViewHolder.class);
        ClassesBackViewHolder.setOnListItemClickListener(this);
        lv_list.setAdapter(classesBackEntityListViewDataAdapter);

        classifyGridViewEntityListViewDataAdapter = new ListViewDataAdapter<ClassifyEntity>();
        classifyGridViewEntityListViewDataAdapter.setViewHolderClass(this, GridBackViewOneHolder.class);
        GridBackViewOneHolder.setClassifyEntityOnClassifyPostionClickListener(this);
        grid_view.setAdapter(classifyGridViewEntityListViewDataAdapter);

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


    /**
     * 有无数据页面显示
     */
    public void showRoGone() {
        ll_visibilty.setVisibility(View.GONE);
        rl_none.setVisibility(View.GONE);
    }


    /**
     * 分类点击事件
     *
     * @param itemData
     * @param postion
     */
    @Override
    public void onClassifyPostionClick(ClassifyEntity itemData, int postion) {
        ll_gridview.setVisibility(View.GONE);
        rl_biaotilan.setVisibility(View.VISIBLE);
        classifyGridViewEntityListViewDataAdapter.notifyDataSetChanged();
        tv_classify_name.setText(itemData.getName());
        if (itemData.getName().equals("全部")) {
            tag = "";
        } else {
            tag = itemData.getId();
        }
        pageindex = 1;
        classesBackEntityListViewDataAdapter.removeAll();
        String sign = TGmd5.getMD5(teacher_id + tag + pageindex + type);
        allClassPresenter.getAllClassesData(teacher_id, tag, pageindex + "", type, sign);
    }


    /**
     * item 点击事件
     *
     * @param itemData
     */
    @Override
    public void onListItemClick(ClassesBackEntity itemData) {
        Bundle bundle = new Bundle();
        bundle.putString("id", itemData.getId());
        bundle.putString("index", "0");
        Intent intent = new Intent(getActivity(), FamousTeacherDetialActivity.class);
        intent.putExtras(bundle);
        getActivity().startActivity(intent);
    }


    /**
     * 请求成功返回值
     *
     * @param state
     * @param respanse
     */
    @Override
    public void OnRequestSuccCallBack(String state, String respanse) {
        switch (state) {
            case "1":
                try {
                    pullToRefreshListView.onRefreshComplete();
                    JSONObject jsonObject = new JSONObject(respanse);
                    String status = jsonObject.getString("status");
                    if (status.equals("1")) {
                        String result = jsonObject.getString("result");
                        JSONArray jsonArray = new JSONArray(result);
                        String data = jsonArray.get(0).toString();
                        JSONObject jsonObject_one = new JSONObject(data);
                        total_page = jsonObject_one.getInt("TotalPages");
                        if (mIsUp == false) {
                            classesBackEntityListViewDataAdapter.removeAll();
                        } else {
                        }

                        String items = jsonObject_one.getString("Items");
                        JSONArray array = new JSONArray(items);
                        if (array != null && array.length() > 0) {
                            showRoGone();
                            ll_visibilty.setVisibility(View.VISIBLE);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject json_ = array.getJSONObject(i);
                                String id_ = json_.getString("ID");
                                String img_path = json_.getString("Banner2");
                                String name = json_.getString("TeacherName");
                                String title = json_.getString("Theme");
                                int State = json_.getInt("State");
                                String sign_num = json_.getString("People_Apply");
                                String tuanbi = json_.getString("BMTuanBi");
                                ClassesBackEntity classesBackEntity = new ClassesBackEntity(id_, tuanbi, sign_num, title, name, img_path);
                                classesBackEntity.setState(State);
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
            case "2":
                try {
                    JSONObject jsonObject1 = new JSONObject(respanse);
                    String state_one = jsonObject1.getString("status");
                    classifyGridViewEntityListViewDataAdapter.removeAll();
                    if (state_one.equals("1")) {
                        String result = jsonObject1.getString("result");
                        JSONArray array = new JSONArray(result);
                        String data = array.get(0).toString();
                        JSONArray array_one = new JSONArray(data);
                        ClassifyEntity classifyEntity_ = new ClassifyEntity("0", "全部", "");
                        classifyGridViewEntityListViewDataAdapter.append(classifyEntity_);
                        if (array_one != null && array_one.length() > 0) {
                            for (int i = 0; i < array_one.length(); i++) {
                                JSONObject json_ = array_one.getJSONObject(i);
                                String id = json_.getString("ID");
                                String name = json_.getString("String1");
                                ClassifyEntity classifyEntity = new ClassifyEntity(id, name, "");
                                classifyGridViewEntityListViewDataAdapter.append(classifyEntity);
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    /**
     * 请求失败返回值
     *
     * @param state
     * @param respanse
     */
    @Override
    public void OnRequestFialCallBack(String state, String respanse) {
        closeProgressDialog();
        MyToast.show(getActivity(), "请求失败");
    }

    /**
     * 取消弹窗
     */
    @Override
    public void closeProgress() {
        closeProgressDialog();
    }


    @Override
    public void onResume() {
        super.onResume();
        allClassPresenter.attach(this);
        MobclickAgent.onPageStart(getClass().getName());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        allClassPresenter.dettach();
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getClass().getName());
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
