package com.lvgou.distribution.fragment;

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
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;

import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.ActDetailActivity;
import com.lvgou.distribution.activity.CollegeManagerActivity;
import com.lvgou.distribution.activity.FabuActActivity;
import com.lvgou.distribution.adapter.ActivityFragmentAdapter;
import com.lvgou.distribution.adapter.LiveFragmentAdapter;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.presenter.ActivityPresenter;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.ActivityView;
import com.lvgou.distribution.widget.XListView;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.xdroid.common.utils.PreferenceHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/7.
 * 活动
 */

public class ActivityFragment extends Fragment implements XListView.IXListViewListener, ActivityView, View.OnClickListener {

    private View view;
    private ActivityPresenter activityPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_activity, container, false);
        activityPresenter = new ActivityPresenter(this);
        initView();
        onRefresh();
        initClick();
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private View mLayout;
    private View mSearchlayout;
    private RelativeLayout rl_scroll_button;
    private XListView mListView;
    private ActivityFragmentAdapter activityFragmentAdapter;
    private ImageView im_fabu_activity;//发布活动按钮
    private RelativeLayout rl_none_one;

    private void initView() {
        rl_none_one = (RelativeLayout) view.findViewById(R.id.rl_none_one);
        mLayout = getActivity().findViewById(R.id.rl_title);
        mSearchlayout = getActivity().findViewById(R.id.rl_title);
        rl_scroll_button = (RelativeLayout) getActivity().findViewById(R.id.rl_scroll_button);

        im_fabu_activity = (ImageView) view.findViewById(R.id.im_fabu_activity);
        mListView = (XListView) view.findViewById(R.id.list_view);
        activityFragmentAdapter = new ActivityFragmentAdapter(getActivity());
        mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnable(true);
//        mListView.stopLoadMore();
        mListView.setAutoLoadEnable(false);
        mListView.setXListViewListener(this);
        mListView.setRefreshTime(((CollegeManagerActivity) getActivity()).getTime());
        mListView.setDivider(null);
        mListView.setAdapter(activityFragmentAdapter);
//        initListener();


    }


    private void initClick() {
        im_fabu_activity.setOnClickListener(this);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ActDetailActivity.class);
                intent.putExtra("activityid", dataList.get(position - 1).get("ID").toString());
                getActivity().startActivity(intent);
            }
        });

        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            //            boolean scrollFlag = false;// 标记是否滑动
            int lastVisibleItemPosition;// 标记上次滑动位置

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
               /* if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    scrollFlag = true;
                } else {
                    scrollFlag = false;
                }*/
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                if (scrollFlag) {
                if (firstVisibleItem > lastVisibleItemPosition) {
                    Log.e("sfasdfs", "上滑");
                    im_fabu_activity.setBackgroundResource(R.mipmap.feng_fabu_normal_icon);
                }
                if (firstVisibleItem < lastVisibleItemPosition) {
                    Log.e("sfasdfs", "下滑");
                    im_fabu_activity.setBackgroundResource(R.mipmap.feng_fabu_icon);
                }
//                }

                if (firstVisibleItem == lastVisibleItemPosition) {
                    return;
                }
                lastVisibleItemPosition = firstVisibleItem;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.im_fabu_activity://发布活动按钮点击
                Intent intent = new Intent(getActivity(), FabuActActivity.class);
                getActivity().startActivity(intent);
                break;
        }
    }

 /*   //------------
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
    }*/

    @Override
    public void onRefresh() {
        page = 1;
        dataList.clear();
        initDatas();
    }

    private int page = 1;

    @Override
    public void onLoadMore() {
        page++;
        initDatas();
    }

    private void initDatas() {
        String distributorid = PreferenceHelper.readString(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        String sign = TGmd5.getMD5(distributorid + "" + page);
        if (isRefresh) {
            ((CollegeManagerActivity) getActivity()).showLoadingProgressDialog(getActivity(), "");
        }
        activityPresenter.activityDatas(distributorid, page, sign);

    }


    @Override
    public void OnActivitySuccCallBack(String state, String respanse) {
        if (isRefresh) {
            ((CollegeManagerActivity) getActivity()).closeLoadingProgressDialog();
        }
        isRefresh = true;
        mListView.stopRefresh();
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

    private boolean isRefresh = false;

    @Override
    public void OnActivityFialCallBack(String state, String respanse) {
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
        private final WeakReference<ActivityFragment> mActivity;

        public mainHandler(ActivityFragment activity) {
            mActivity = new WeakReference<ActivityFragment>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            ActivityFragment activity = mActivity.get();
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
                activityFragmentAdapter.setData(dataList);
                activityFragmentAdapter.notifyDataSetChanged();
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
            activityFragmentAdapter.setData(dataList);
            activityFragmentAdapter.notifyDataSetChanged();
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

    /**
     * 在主线程接受 eventbus发送的事件
     *
     * @param action
     * @Subscribe 这个注解必须加上
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUIEvent(String action) {
        // 如果接受到注销的事件 就销毁自己
        //长按 录音按下
        if ("fabuactisuccess".equals(action)) {
            onRefresh();
        }

    }
}