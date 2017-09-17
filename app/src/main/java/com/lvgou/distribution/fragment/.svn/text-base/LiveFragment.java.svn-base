package com.lvgou.distribution.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;

import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.CollegeManagerActivity;
import com.lvgou.distribution.activity.CourseIntrActivity;
import com.lvgou.distribution.adapter.BoutiqueFragmentAdapter;
import com.lvgou.distribution.adapter.LiveFragmentAdapter;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.presenter.LiveTeacherPresenter;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.LiveTeacherView;
import com.lvgou.distribution.widget.XListView;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.xdroid.common.utils.PreferenceHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/6.
 * 直播
 */

public class LiveFragment extends Fragment implements XListView.IXListViewListener, LiveTeacherView {
    private LiveTeacherPresenter liveTeacherPresenter;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_live, container, false);
        liveTeacherPresenter = new LiveTeacherPresenter(this);
        initView();
        onRefresh();
//        initDatas();
        initClick();

        return view;
    }


    private View mLayout;
    private View mSearchlayout;
    private RelativeLayout rl_scroll_button;
    private XListView mListView;
    private LiveFragmentAdapter liveFragmentAdapter;
    private RelativeLayout rl_none_one;

    private void initView() {
        rl_none_one = (RelativeLayout) view.findViewById(R.id.rl_none_one);
//        mLayout = getActivity().findViewById(R.id.rl_title);
//        mSearchlayout = getActivity().findViewById(R.id.rl_title);
//        rl_scroll_button = (RelativeLayout) getActivity().findViewById(R.id.rl_scroll_button);

        mListView = (XListView) view.findViewById(R.id.list_view);
        liveFragmentAdapter = new LiveFragmentAdapter(getActivity());
        mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnable(true);
//        mListView.stopLoadMore();
        mListView.setAutoLoadEnable(false);
        mListView.setXListViewListener(this);
        mListView.setRefreshTime(((CollegeManagerActivity) getActivity()).getTime());
        mListView.setDivider(null);
        mListView.setAdapter(liveFragmentAdapter);
//        initListener();
    }

    private void initClick() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent1 = new Intent(getActivity(), CourseIntrActivity.class);
                intent1.putExtra("id", dataList.get(position - 1).get("ID").toString());
                startActivity(intent1);
            }
        });
    }


   /* //------------
    private CollegeManagerActivity.MyOnTouchListener myOnTouchListener;

    private void initListener() {
        myOnTouchListener = new CollegeManagerActivity.MyOnTouchListener() {
            @Override
            public boolean dispatchTouchEvent(MotionEvent ev) {
                return dispathTouchEvent(ev);
            }
        };
        ((CollegeManagerActivity) getActivity()).registerMyOnTouchListener(myOnTouchListener);
    }*/

  /*  private boolean isDown = false;
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

    }*/

 /*   public void setMarginTop(int page) {
        RelativeLayout.LayoutParams layoutParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);
        layoutParam.setMargins(0, page, 0, 0);
        rl_scroll_button.setLayoutParams(layoutParam);
        rl_scroll_button.invalidate();
    }*/

    @Override
    public void onRefresh() {
        page = 1;
        dataList.clear();
        initDatas();
    }

    @Override
    public void onLoadMore() {
        page++;
        initDatas();
    }

    private int page = 1;
    private boolean isRefresh = false;

    private void initDatas() {
        String distributorid = PreferenceHelper.readString(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        String sign = TGmd5.getMD5(distributorid + "" + page);
        if (isRefresh) {
            ((CollegeManagerActivity) getActivity()).showLoadingProgressDialog(getActivity(), "");
        }
        liveTeacherPresenter.liveTeacherDatas(distributorid, page, sign);
    }

    @Override
    public void OnLiveTeacherSuccCallBack(String state, String respanse) {
        if (isRefresh) {
            ((CollegeManagerActivity) getActivity()).closeLoadingProgressDialog();
        }
        mListView.stopRefresh();
        isRefresh = true;
        try {
            JSONObject jsonObject = new JSONObject(respanse);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("result"));
            JSONObject jsonObject1 = new JSONObject(jsonArray.get(0).toString());
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
            if (dataListTmp.size() < Integer.parseInt(jsonObject1.get("ItemsPerPage").toString()) && Integer.parseInt(jsonObject1.get("ItemsPerPage").toString()) != 0) {
                mHandler.sendEmptyMessage(2);
            } else {
                mHandler.sendEmptyMessage(1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void OnLiveTeacherFialCallBack(String state, String respanse) {
        if (isRefresh) {
            ((CollegeManagerActivity) getActivity()).closeLoadingProgressDialog();
        }
        isRefresh = true;
        mListView.stopRefresh();
    }

    @Override
    public void closeProgress() {

    }

    private final mainHandler mHandler = new mainHandler(this);

    private static class mainHandler extends Handler {
        private final WeakReference<LiveFragment> mActivity;

        public mainHandler(LiveFragment activity) {
            mActivity = new WeakReference<LiveFragment>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            LiveFragment activity = mActivity.get();
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
                WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
                int width = wm.getDefaultDisplay().getWidth();
                liveFragmentAdapter.setData(dataList,width);
                liveFragmentAdapter.notifyDataSetChanged();
                mListView.setPullLoadEnable(true);
            } else {
                mListView.setPullLoadEnable(false);
            }
            if (dataList.size() == 0) {
                mListView.setPullLoadEnable(false);
                rl_none_one.setVisibility(View.VISIBLE);
            } else {
                rl_none_one.setVisibility(View.GONE);
            }
            dataListTmp.clear();
            mListView.stopRefresh();
        } else if (msg.what == 2) {
            dataList.addAll(dataListTmp);
            WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
            int width = wm.getDefaultDisplay().getWidth();
            liveFragmentAdapter.setData(dataList,width);
            liveFragmentAdapter.notifyDataSetChanged();
            dataListTmp.clear();
            if (dataList.size() == 0) {
                rl_none_one.setVisibility(View.VISIBLE);
            } else {
                rl_none_one.setVisibility(View.GONE);
            }
            mListView.setPullLoadEnable(false);
            mListView.stopLoadMore();
        }
    }
}