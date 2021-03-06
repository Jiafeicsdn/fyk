package com.lvgou.distribution.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lvgou.distribution.presenter.DistributorHomePresenter;
import com.lvgou.distribution.utils.MyToast;

import android.widget.Toast;

import com.lvgou.distribution.R;
import com.lvgou.distribution.adapter.HotCourseGridAdapter;
import com.lvgou.distribution.adapter.SearchResultAdapter;
import com.lvgou.distribution.adapter.TeacgerViewAdapter;
import com.lvgou.distribution.adapter.TeacherSearchAdapter;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.fragment.LiveFragment;
import com.lvgou.distribution.presenter.StudySearchPresenter;
import com.lvgou.distribution.presenter.StudySearchloadPresenter;
import com.lvgou.distribution.utils.DensityUtil;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.utils.WorksGridView;
import com.lvgou.distribution.view.DistributorHomeView;
import com.lvgou.distribution.view.StudySearchView;
import com.lvgou.distribution.view.StudySearchloadView;
import com.lvgou.distribution.widget.FlowLayout;
import com.lvgou.distribution.widget.XListView;
import com.xdroid.common.utils.PreferenceHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import static android.text.TextUtils.TruncateAt.END;

/**
 * Created by Administrator on 2017/3/7.
 * 全局搜索页面
 */

public class CollegeSearchActivity extends BaseActivity implements View.OnClickListener, StudySearchloadView, StudySearchView, XListView.IXListViewListener, DistributorHomeView {
    private StudySearchloadPresenter studySearchloadPresenter;
    private StudySearchPresenter studySearchPresenter;
    private SearchResultAdapter searchResultAdapter;
    private DistributorHomePresenter distributorHomePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_search);
        studySearchloadPresenter = new StudySearchloadPresenter(this);
        studySearchPresenter = new StudySearchPresenter(this);
        distributorHomePresenter = new DistributorHomePresenter(this);
        initView();
        initClick();
        serachData();
        initSearchloadDatas();
    }


    private EditText et_title_search;//搜索编辑框
    private TextView search_cancel;//取消按钮
    private RelativeLayout et_search_delete;//删除搜索内容
    private FlowLayout flowlayout;//历史搜索记录
    private GridView hot_course;//热门课程
    private HotCourseGridAdapter hotCourseGridAdapter;
    private RelativeLayout rl_change;//换一换
    private ScrollView sv_search_before;
    private XListView mListView;
    private LinearLayout ll_search_result;//搜索之后
    private View teacherHeader;
    private RelativeLayout rl_teacher_list;//查找老师列表
    private View view_tline;
    private TextView tv_course_title;
    private WorksGridView grid_teacher;
    private ImageView delete_history;//清空历史搜索
    private RelativeLayout rl_none;//没有搜索到内容
    private RelativeLayout rl_search_before;

    private void initView() {
        et_title_search = (EditText) findViewById(R.id.et_title_search);
        search_cancel = (TextView) findViewById(R.id.search_cancel);
        et_search_delete = (RelativeLayout) findViewById(R.id.et_search_delete);
        flowlayout = (FlowLayout) findViewById(R.id.flowlayout);
        hot_course = (GridView) findViewById(R.id.hot_course);
        rl_change = (RelativeLayout) findViewById(R.id.rl_change);
        rl_search_before = (RelativeLayout) findViewById(R.id.rl_search_before);
        initFlowDatas();
        delete_history = (ImageView) findViewById(R.id.delete_history);

        rl_none = (RelativeLayout) findViewById(R.id.rl_none);
        sv_search_before = (ScrollView) findViewById(R.id.sv_search_before);
        ll_search_result = (LinearLayout) findViewById(R.id.ll_search_result);
        ll_search_result.setVisibility(View.GONE);
        sv_search_before.setVisibility(View.VISIBLE);
        et_search_delete.setVisibility(View.GONE);
        mListView = (XListView) findViewById(R.id.lv_search_result);
        searchResultAdapter = new SearchResultAdapter(this);
        mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnable(false);
//        mListView.stopLoadMore();
        mListView.setAutoLoadEnable(false);
        mListView.setXListViewListener(this);
        mListView.setRefreshTime(this.getTime());
        mListView.setDivider(null);
        searchResultAdapter.setData(new ArrayList<HashMap<String, Object>>());
        mListView.setAdapter(searchResultAdapter);
        et_title_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    et_search_delete.setVisibility(View.GONE);
                } else {
                    et_search_delete.setVisibility(View.VISIBLE);
                }
            }
        });

        hotCourseGridAdapter = new HotCourseGridAdapter(this);
        hotCourseGridAdapter.setDatas(new ArrayList<HashMap<String, Object>>());
        hot_course.setAdapter(hotCourseGridAdapter);

        teacherHeader = LayoutInflater.from(this).inflate(R.layout.search_teacher_header, null);
        rl_teacher_list = (RelativeLayout) teacherHeader.findViewById(R.id.rl_teacher_list);
        view_tline = teacherHeader.findViewById(R.id.view_tline);
        tv_course_title = (TextView) teacherHeader.findViewById(R.id.tv_course_title);
        mListView.addHeaderView(teacherHeader);
    }

    private ArrayList<String> searchList = new ArrayList<>();

    private void initFlowDatas() {
        searchList = (ArrayList<String>) mcache.getAsObject("college_search_list");
        if (searchList != null) {
            if (searchList.size() == 0) {
                rl_search_before.setVisibility(View.GONE);
            } else {

                initFlowView();
            }
        } else {
            rl_search_before.setVisibility(View.GONE);
        }

    }

    private void initFlowView() {
//        rl_search_before.setVisibility(View.VISIBLE);
        flowlayout.relayoutToCompress();
        flowlayout.removeAllViews();
        for (int i = searchList.size() - 1; i >= 0; i--) {
            int padding1 = DensityUtil.dip2px(CollegeSearchActivity.this, 8);
            int padding4 = DensityUtil.dip2px(CollegeSearchActivity.this, 8);
            int padding5 = DensityUtil.dip2px(CollegeSearchActivity.this, 16);
            int padding6 = DensityUtil.dip2px(CollegeSearchActivity.this, 16);
            ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(padding4, 0, padding4, padding6);
            final TextView tv = new TextView(CollegeSearchActivity.this);
            tv.setPadding(padding5, padding1, padding5, padding1);
            tv.setTextColor(Color.parseColor("#000000"));
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            tv.setText(searchList.get(i));
            tv.setGravity(Gravity.CENTER_VERTICAL);
            tv.setLines(1);
            tv.setMaxEms(10);
            tv.setEllipsize(END);
            tv.setBackgroundResource(R.drawable.bg_search_history_item);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    searchList.remove(tv.getText().toString().trim());
                    searchList.add(tv.getText().toString().trim());
                    mcache.put("college_search_list", searchList);
                    isFirst = false;
                    et_title_search.setText(tv.getText().toString().trim());
                    searchword = tv.getText().toString().trim();
                    searchResult(tv.getText().toString().trim());

                }
            });
            flowlayout.addView(tv, lp);
        }
    }

    private String searchword = "";

    private void initClick() {
        search_cancel.setOnClickListener(this);
        et_search_delete.setOnClickListener(this);
        rl_change.setOnClickListener(this);
        delete_history.setOnClickListener(this);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent1 = new Intent(CollegeSearchActivity.this, CourseIntrActivity.class);
                intent1.putExtra("id", dataList.get(position - 2).get("ID").toString());
                startActivity(intent1);
            }
        });
        hot_course.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent1 = new Intent(CollegeSearchActivity.this, CourseIntrActivity.class);
                intent1.putExtra("id", hotCourseDatas.get(position).get("ID").toString());
                startActivity(intent1);
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_cancel://取消
                CollegeSearchActivity.this.finish();
                break;
            case R.id.et_search_delete://删除搜索内容
                et_title_search.setText("");
                et_search_delete.setVisibility(View.GONE);
                break;
            case R.id.rl_change://换一换
                initSearchloadDatas();
                break;
            case R.id.delete_history://清空历史搜索
                showDelDialog();
                break;
        }
    }


    public void showDelDialog() {
        LinearLayout.LayoutParams pm = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_yesorno, null);//这里的R.layout.alertdialog即为你自定义的布局文件
        TextView text1 = (TextView) view.findViewById(R.id.text1);
        text1.setText("确定删除历史搜索记录吗？ ");
        View view_line = view.findViewById(R.id.view_line);
        TextView yes = (TextView) view.findViewById(R.id.yes);
        TextView cancle = (TextView) view.findViewById(R.id.cancle);
        cancle.setText("留着");
        final AlertDialog mAlertDialog = builder.create();
        mAlertDialog.getWindow().setBackgroundDrawableResource(R.color.touming);
        mAlertDialog.show();
        mAlertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        mAlertDialog.getWindow().setContentView(view, pm);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.dismiss();
            }
        });
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mcache.remove("college_search_list");
                if (null != searchList) {
                    searchList.clear();
                }
                rl_search_before.setVisibility(View.GONE);
                initFlowView();
                mAlertDialog.dismiss();
            }
        });
    }

    //搜索编辑框
    private void serachData() {
        et_title_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // 隐藏软键盘
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager.isActive()) {
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }

                if (searchList == null) {
                    searchList = new ArrayList<String>();
                }
                if (!v.getText().toString().trim().equals("")) {

                    if (searchList.contains(v.getText().toString().trim())) {
                        searchList.remove(v.getText().toString().trim());
                    } else if (searchList.size() == 15) {
                        searchList.remove(0);
                    }
                    searchList.add(v.getText().toString().trim());
                    rl_search_before.setVisibility(View.VISIBLE);
                    initFlowView();
                    mcache.put("college_search_list", searchList);
                    isFirst = false;
                    searchword = v.getText().toString().trim();
                    dataList.clear();
                    searchResult(v.getText().toString().trim());
                }
                return true;
            }
        });
    }

    private void initSearchloadDatas() {
        String distributorid = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        String sign = TGmd5.getMD5(distributorid);
        showLoadingProgressDialog(this, "");
        studySearchloadPresenter.studySearchloadDatas(distributorid, sign);
    }

    private ArrayList<HashMap<String, Object>> hotCourseDatas = new ArrayList<>();

    @Override
    public void OnStudySearchloadSuccCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
        try {

            JSONObject jsb = new JSONObject(respanse);
            JSONArray jsa = new JSONArray(jsb.getString("result"));
            if (jsa != null && !jsa.equals("")) {
                hotCourseDatas.clear();
                JSONArray jsonArray = jsa.getJSONArray(0);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsoo = jsonArray.getJSONObject(i);
                    HashMap<String, Object> map1 = new HashMap<String, Object>();
                    Iterator it = jsoo.keys();
                    while (it.hasNext()) {
                        String key = (String) it.next();
                        String value = jsoo.getString(key);
                        map1.put(key, value);
                    }
                    hotCourseDatas.add(map1);
                }
            }
            hotCourseGridAdapter.setDatas(hotCourseDatas);
            hotCourseGridAdapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void OnStudySearchloadFialCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
    }

    @Override
    public void closeStudySearchloadProgress() {

    }

    private int page = 1;

    //全局搜索
    private void searchResult(String searchword) {
        //隐藏以前的
        sv_search_before.setVisibility(View.GONE);
        //显示现在的
        ll_search_result.setVisibility(View.VISIBLE);
        String distributorid = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        String sign = TGmd5.getMD5(distributorid + searchword + page);
        showLoadingProgressDialog(this, "");
        studySearchPresenter.studySearchDatas(distributorid, searchword, page, sign);
    }

    public ArrayList<HashMap<String, Object>> teacherList = new ArrayList<HashMap<String, Object>>();
    private boolean isFirst = false;//判断是否是第一次加载数据，下拉刷新和上拉加载都不能更新讲师搜索结果了，除非是重新输入文字搜索

    @Override
    public void OnStudySearchSuccCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
        mListView.stopRefresh();
        try {
            JSONObject jsb = new JSONObject(respanse);
            JSONArray jsa = new JSONArray(jsb.getString("result"));
            if (jsa != null && !jsa.equals("")) {
                teacherList.clear();
                JSONArray jsonArray = jsa.getJSONArray(0);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsoo = jsonArray.getJSONObject(i);
                    HashMap<String, Object> map1 = new HashMap<String, Object>();
                    Iterator it = jsoo.keys();
                    while (it.hasNext()) {
                        String key = (String) it.next();
                        String value = jsoo.getString(key);
                        map1.put(key, value);
                    }
                    teacherList.add(map1);
                }
            }
            //  讲师搜索结果
            if (!isFirst) {
                if (teacherList.size() == 0) {
                    rl_teacher_list.setVisibility(View.GONE);
                    view_tline.setVisibility(View.GONE);
                } else {
                    rl_teacher_list.setVisibility(View.VISIBLE);
                    view_tline.setVisibility(View.VISIBLE);
                    initTeacherGtideView();
                }
            }


            // 	课程搜索结果
            JSONObject jsonObject1 = new JSONObject(jsa.get(1).toString());
            JSONArray jsonArray1 = new JSONArray(jsonObject1.getString("Items"));
            for (int i = 0; i < jsonArray1.length(); i++) {
                JSONObject jsoo = jsonArray1.getJSONObject(i);
                HashMap<String, Object> map1 = new HashMap<String, Object>();
                Iterator it = jsoo.keys();
                while (it.hasNext()) {
                    String key = (String) it.next();
                    String value = jsoo.getString(key);
                    map1.put(key, value);
                }
                dataListTmp.add(map1);
            }
            if (Integer.parseInt(jsonObject1.get("TotalItems").toString()) == 0) {
                tv_course_title.setVisibility(View.GONE);
                view_tline.setVisibility(View.GONE);
            } else {
                tv_course_title.setVisibility(View.VISIBLE);
                view_tline.setVisibility(View.VISIBLE);
                tv_course_title.setText("找到" + jsonObject1.get("TotalItems").toString() + "条相关课程");
            }
            if (!isFirst) {
                if (teacherList.size() == 0 && dataListTmp.size() == 0) {
                    rl_none.setVisibility(View.VISIBLE);
                    ll_search_result.setVisibility(View.GONE);
                } else {
                    rl_none.setVisibility(View.GONE);
                    ll_search_result.setVisibility(View.VISIBLE);
                }
            }
            isFirst = true;
            if (dataListTmp.size() < Integer.parseInt(jsonObject1.get("ItemsPerPage").toString())) {
                mHandler.sendEmptyMessage(2);
            } else {
                mHandler.sendEmptyMessage(1);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private final mainHandler mHandler = new mainHandler(this);


    private static class mainHandler extends Handler {
        private final WeakReference<CollegeSearchActivity> mActivity;

        public mainHandler(CollegeSearchActivity activity) {
            mActivity = new WeakReference<CollegeSearchActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            CollegeSearchActivity activity = mActivity.get();
            if (activity != null) {
                activity.flushDataList(msg);
            }
        }
    }

    public ArrayList<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();
    public ArrayList<HashMap<String, Object>> dataListTmp = new ArrayList<HashMap<String, Object>>();

    public void flushDataList(Message msg) {
        if (msg.what == 1) {
            dataList.addAll(dataListTmp);
            if (dataList != null) {
                searchResultAdapter.setData(dataList);
                searchResultAdapter.notifyDataSetChanged();
                mListView.setPullLoadEnable(true);
            } else {
                mListView.setPullLoadEnable(false);
            }

            dataListTmp.clear();
            mListView.stopRefresh();
        } else if (msg.what == 2) {
            dataList.addAll(dataListTmp);
            searchResultAdapter.setData(dataList);
            searchResultAdapter.notifyDataSetChanged();
            dataListTmp.clear();

            mListView.setPullLoadEnable(false);
            mListView.stopLoadMore();
        }
    }


    //讲师的gridview
    private void initTeacherGtideView() {
        grid_teacher = (WorksGridView) teacherHeader.findViewById(R.id.grid_teacher);
        setTeacherGridView();
        grid_teacher.setSelector(new ColorDrawable(Color.TRANSPARENT));
        grid_teacher.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if ((position == teacherList.size() - 1) && (teacherList.size() == 5)) {
                    //如果是最后一个，那就跳转到讲师搜索页面
                    Intent intent = new Intent(CollegeSearchActivity.this, TeacherSearchActivity.class);
                    intent.putExtra("searchword", searchword);
                    startActivity(intent);
                } else {
                    String distributorid = PreferenceHelper.readString(CollegeSearchActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
                    String sign = TGmd5.getMD5("" + distributorid + teacherList.get(position).get("ID").toString());
                    showLoadingProgressDialog(CollegeSearchActivity.this, "");
                    distributorHomePresenter.distributorHome(distributorid, teacherList.get(position).get("ID").toString(), sign);
                    /*if (teacherList.get(position).get("UserType").toString().equals("3")) {
                        Intent intent1 = new Intent(CollegeSearchActivity.this, TeacherHomeActivity.class);
                        intent1.putExtra("seeDistributorId", teacherList.get(position).get("ID").toString());
                        startActivity(intent1);
                    } else {
                        Intent intent1 = new Intent(CollegeSearchActivity.this, DistributorHomeActivity.class);
                        intent1.putExtra("seeDistributorId", teacherList.get(position).get("ID").toString());
                        startActivity(intent1);
                    }*/
                }
                /*HashMap<String, Object> dic2 = starLists.get(position);
                Intent intent = new Intent();
                intent.setClass(GuangChangActivity.this, UserInfoActivity_No2.class);
                intent.putExtra("sso_id", dic2.get("id").toString());
                startActivity(intent);*/

            }
        });
    }

    private void setTeacherGridView() {

        if (teacherList.size() == 5) {
            teacherList.add(new HashMap<String, Object>());
        }
        int size = teacherList.size();

        int length = 76;
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;
        int gridviewWidth = (int) (size * (length) * density);
        int itemWidth = (int) (length * density);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                gridviewWidth, LinearLayout.LayoutParams.MATCH_PARENT);
        grid_teacher.setLayoutParams(params); // 设置GirdView布局参数,横向布局的关键
        grid_teacher.setColumnWidth(itemWidth); // 设置列表项宽
//        gridsetRankPotentialActorGridViewView.setHorizontalSpacing(20); // 设置列表项水平间距
        grid_teacher.setStretchMode(GridView.NO_STRETCH);
        grid_teacher.setNumColumns(size); // 设置列数量=列表集合数
        TeacherSearchAdapter adapter = new TeacherSearchAdapter(this, teacherList);
        grid_teacher.setAdapter(adapter);
    }


    @Override
    public void OnStudySearchFialCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
        rl_none.setVisibility(View.VISIBLE);
        ll_search_result.setVisibility(View.GONE);
    }

    @Override
    public void closeStudySearchProgress() {

    }

    @Override
    public void onRefresh() {
        page = 1;
        dataList.clear();
        isFirst = false;
        searchResult(searchList.get(searchList.size() - 1));
    }

    @Override
    public void onLoadMore() {
        page++;
        searchResult(searchList.get(searchList.size() - 1));
    }

    @Override
    public void OnDistributorHomeSuccCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
        try {
            JSONObject jsb = new JSONObject(respanse);
            JSONArray jsa = new JSONArray(jsb.getString("result"));
            JSONObject distributorModeljsa = jsa.getJSONObject(0);
            if (distributorModeljsa.get("UserType").toString().equals("3")) {
                //如果是讲师
                Intent intent1 = new Intent(CollegeSearchActivity.this, TeacherHomeActivity.class);
                intent1.putExtra("seeDistributorId", distributorModeljsa.get("ID").toString());
                startActivity(intent1);
            } else {
                //普通导游
                Intent intent1 = new Intent(CollegeSearchActivity.this, DistributorHomeActivity.class);
                intent1.putExtra("seeDistributorId", distributorModeljsa.get("ID").toString());
                startActivity(intent1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void OnDistributorHomeFialCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
    }

    @Override
    public void closeDistributorHomeProgress() {

    }
}
