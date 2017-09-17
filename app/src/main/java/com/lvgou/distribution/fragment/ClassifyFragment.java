package com.lvgou.distribution.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;

import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.CollegeManagerActivity;
import com.lvgou.distribution.activity.CourseIntrActivity;
import com.lvgou.distribution.adapter.ClassifyAdapter1;
import com.lvgou.distribution.adapter.ClassifyAdapter2;
import com.lvgou.distribution.adapter.LiveFragmentAdapter;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.presenter.StudyClassifyPresenter;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.StudyClassifyView;
import com.lvgou.distribution.widget.XListView;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.xdroid.common.utils.PreferenceHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/7.
 * 分类
 */

public class ClassifyFragment extends Fragment implements StudyClassifyView, XListView.IXListViewListener {

    private StudyClassifyPresenter studyClassifyPresenter;
    private View view;
    private ClassifyAdapter1 classifyAdapter1;
    private ClassifyAdapter2 classifyAdapter2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_classify, container, false);
        studyClassifyPresenter = new StudyClassifyPresenter(this);
        initView();
        onRefresh();
//        initDatas(linUrl);
        initClick();
        return view;
    }


    private View mLayout;
    private View mSearchlayout;
    private RelativeLayout rl_scroll_button;
    private ListView lv_list1;//左边标签列表
//    private ListView lv_list2;//右边列表
private XListView mListView;

    private void initView() {
//        mLayout = getActivity().findViewById(R.id.rl_title);
//        mSearchlayout = getActivity().findViewById(R.id.rl_title);
//        rl_scroll_button = (RelativeLayout) getActivity().findViewById(R.id.rl_scroll_button);
//        initListener();
        lv_list1 = (ListView) view.findViewById(R.id.lv_list1);
//        lv_list2 = (ListView) view.findViewById(R.id.lv_list2);
        classifyAdapter1 = new ClassifyAdapter1(getActivity());

        classifyAdapter1.setData(new ArrayList<HashMap<String, Object>>());

        lv_list1.setAdapter(classifyAdapter1);
//        lv_list2.setAdapter(classifyAdapter2);

        mListView = (XListView) view.findViewById(R.id.list_view);
        classifyAdapter2 = new ClassifyAdapter2(getActivity());
        classifyAdapter2.setData(new ArrayList<HashMap<String, Object>>());
        mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnable(false);
//        mListView.stopLoadMore();
        mListView.setAutoLoadEnable(false);
        mListView.setXListViewListener(this);
        mListView.setRefreshTime(((CollegeManagerActivity) getActivity()).getTime());
        mListView.setDivider(null);
        mListView.setAdapter(classifyAdapter2);
    }

    private boolean firstpage = false;
    private String linUrl = "";

    public void setCurrentPosition(String linUrl) {
        this.linUrl = linUrl;
        firstpage = true;
//        initDatas(linUrl);
    }
    public void setCurrentPosition2(String linUrl) {
        this.linUrl = linUrl;
        firstpage = true;
        onRefresh();
//        initDatas(linUrl);
    }

    private boolean isRefresh = false;

    private void initClick() {
        lv_list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                classifyAdapter1.setSelectPosition(position);
                firstpage = false;
                linUrl=dataListTmp.get(position).get("LabelPath").toString();
                onRefresh();
//                initDatas(linUrl);

            }
        });
    }


/*
    //------------
    private CollegeManagerActivity.MyOnTouchListener myOnTouchListener;

    private void initListener() {
        myOnTouchListener = new CollegeManagerActivity.MyOnTouchListener() {
            @Override
            public boolean dispatchTouchEvent(MotionEvent ev) {
                return dispathTouchEvent(ev);
            }
        };
        ((CollegeManagerActivity) getActivity()).registerMyOnTouchListener(myOnTouchListener);
    }

    private boolean isDown = false;
    private boolean isUp = false;
    private boolean mIsTitleHide = false;
    private boolean mIsAnim = false;
    private float lastX = 0;
    private float lastY = 0;

    private boolean dispathTouchEvent(MotionEvent event) {
        if (mIsAnim) {
            return false;
        }
        final int action = event.getAction();

        float x = event.getX();
        float y = event.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                lastY = y;
                lastX = x;
                return false;
            case MotionEvent.ACTION_MOVE:
                float dY = Math.abs(y - lastY);
                float dX = Math.abs(x - lastX);
                boolean down = y > lastY ? true : false;
                lastY = y;
                lastX = x;
                isUp = dX < 8 && dY > 8 && !mIsTitleHide && !down;
                isDown = dX < 8 && dY > 8 && mIsTitleHide && down;
                if (isUp) {
                    View view = this.mLayout;
                    float[] f = new float[2];
                    f[0] = 0.0F;
                    f[1] = -mSearchlayout.getHeight();
                    ObjectAnimator animator1 = ObjectAnimator.ofFloat(view, "translationY", f);
                    animator1.setInterpolator(new AccelerateDecelerateInterpolator());
                    animator1.setDuration(200);
                    animator1.start();
                    animator1.addListener(this);
                    if (y > mSearchlayout.getHeight()) {
                        setMarginTop(mLayout.getHeight() - mSearchlayout.getHeight());
                    } else {
                        setMarginTop((int) (mLayout.getHeight() - y));
                    }

                } else if (isDown) {
                    View view = this.mLayout;
                    float[] f = new float[2];
                    f[0] = -mSearchlayout.getHeight();
                    f[1] = 0F;
                    ObjectAnimator animator1 = ObjectAnimator.ofFloat(view, "translationY", f);
                    animator1.setDuration(200);
                    animator1.setInterpolator(new AccelerateDecelerateInterpolator());
                    animator1.start();
                    animator1.addListener(this);

                } else {
                    return false;
                }
                mIsTitleHide = !mIsTitleHide;
                mIsAnim = true;
                break;
            default:
                return false;
        }
        return false;

    }

    @Override
    public void onAnimationCancel(Animator arg0) {

    }


    @Override
    public void onAnimationEnd(Animator arg0) {
        if (isDown) {
            setMarginTop(mLayout.getHeight());
        }
        mIsAnim = false;
    }


    @Override
    public void onAnimationRepeat(Animator arg0) {

    }


    @Override
    public void onAnimationStart(Animator arg0) {

    }

    public void setMarginTop(int page) {
        RelativeLayout.LayoutParams layoutParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);
        layoutParam.setMargins(0, page, 0, 0);
        rl_scroll_button.setLayoutParams(layoutParam);
        rl_scroll_button.invalidate();
    }
*/

    private void initDatas(String label) {
        String distributorid = PreferenceHelper.readString(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        String sign = TGmd5.getMD5(distributorid + label);
        if (isRefresh) {
            ((CollegeManagerActivity) getActivity()).showLoadingProgressDialog(getActivity(), "");
        }
        studyClassifyPresenter.studyClassifyDatas(distributorid, label, sign);
    }


    @Override
    public void OnStudyClassifySuccCallBack(String state, String respanse) {
        if (isRefresh) {
            ((CollegeManagerActivity) getActivity()).closeLoadingProgressDialog();
        }
        mListView.stopRefresh();
        isRefresh = true;
        try {
            JSONObject jsonObject = new JSONObject(respanse);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("result"));
            JSONArray jsonArray1 = new JSONArray(jsonArray.get(0).toString());
            JSONArray jsonArray2 = new JSONArray(jsonArray.get(1).toString());
            dataListTmp.clear();
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
            dataList.clear();
            for (int i = 0; i < jsonArray2.length(); i++) {
                JSONObject jsoo = jsonArray2.getJSONObject(i);
                HashMap<String, Object> map1 = new HashMap<String, Object>();
                Iterator it = jsoo.keys();
                while (it.hasNext()) {
                    String key = (String) it.next();
                    String value = jsoo.getString(key);
                    map1.put(key, value);
                }
                dataList.add(map1);
            }
            classifyAdapter1.setData(dataListTmp);
            classifyAdapter1.notifyDataSetChanged();

            classifyAdapter2.setData(dataList);
            classifyAdapter2.notifyDataSetChanged();
            if (firstpage) {
                for (int i = 0; i < dataListTmp.size(); i++) {
                    if (dataListTmp.get(i).get("LabelPath").toString().equals(linUrl)) {
                        classifyAdapter1.setSelectPosition(i);
                    }
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();
    public ArrayList<HashMap<String, Object>> dataListTmp = new ArrayList<HashMap<String, Object>>();
//    public ArrayList<HashMap<String, Object>> dataListTmp2 = new ArrayList<HashMap<String, Object>>();

    @Override
    public void OnStudyClassifyFialCallBack(String state, String respanse) {
        if (isRefresh) {
            ((CollegeManagerActivity) getActivity()).closeLoadingProgressDialog();
        }
        isRefresh = true;

    }

    @Override
    public void closeProgress() {

    }

    @Override
    public void onRefresh() {
        initDatas(linUrl);
    }

    @Override
    public void onLoadMore() {

    }
}