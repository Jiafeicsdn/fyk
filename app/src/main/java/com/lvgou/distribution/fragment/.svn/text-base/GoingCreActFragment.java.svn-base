package com.lvgou.distribution.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.activity.EditActActivity;
import com.lvgou.distribution.utils.MyToast;

import android.widget.Toast;

import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.ActDetailActivity;
import com.lvgou.distribution.activity.MyAcActivity;
import com.lvgou.distribution.adapter.MyActivityAdapter;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.inter.AdapterToFraImpl;
import com.lvgou.distribution.presenter.ActivityForMePresenter;
import com.lvgou.distribution.presenter.ActivityStopPresenter;
import com.lvgou.distribution.presenter.DelActivityPresenter;
import com.lvgou.distribution.utils.PopWindows;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.ActivityForMeView;
import com.lvgou.distribution.view.ActivityStopView;
import com.lvgou.distribution.view.DelActivityView;
import com.lvgou.distribution.widget.XListView;
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

/**
 * Created by Administrator on 2017/3/30.
 */

public class GoingCreActFragment extends Fragment implements XListView.IXListViewListener, ActivityForMeView, ActivityStopView, DelActivityView {
    private ActivityForMePresenter activityForMePresenter;
    private ActivityStopPresenter activityStopPresenter;
    private DelActivityPresenter delActivityPresenter;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_allclass, container, false);
        activityForMePresenter = new ActivityForMePresenter(this);
        activityStopPresenter = new ActivityStopPresenter(this);
        delActivityPresenter = new DelActivityPresenter(this);
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

    private XListView mListView;
    private MyActivityAdapter myActivityAdapter;
    private RelativeLayout rl_none_one;

    private void initView() {
        rl_none_one = (RelativeLayout) view.findViewById(R.id.rl_none_one);
        mListView = (XListView) view.findViewById(R.id.list_view);
        myActivityAdapter = new MyActivityAdapter(getActivity());
        mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnable(true);
//        mListView.stopLoadMore();
        mListView.setAutoLoadEnable(false);
        mListView.setXListViewListener(this);
        mListView.setRefreshTime(((MyAcActivity) getActivity()).getTime());
        mListView.setDivider(null);
        mListView.setAdapter(myActivityAdapter);

    }

    private void initClick() {
        myActivityAdapter.setAdapterToFraImpl(new AdapterToFraImpl() {
            @Override
            public void doSomeThing(HashMap<String, Object> info) {
                showPopWindow(info);
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ActDetailActivity.class);
                intent.putExtra("activityid", dataList.get(position - 1).get("ID").toString());
                getActivity().startActivity(intent);
            }
        });
    }

    private TextView pop_tv_title;
    private TextView tv_edit_act;
    private TextView tv_stop_act;
    private TextView tv_delete_act;
    private TextView tv_cancel;

    private void showPopWindow(final HashMap<String, Object> info) {
        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.my_activity_button, null);
        pop_tv_title = (TextView) inflate.findViewById(R.id.tv_title);//标题
        pop_tv_title.setText(info.get("Title").toString());
        tv_edit_act = (TextView) inflate.findViewById(R.id.tv_edit_act);//编辑活动
        tv_stop_act = (TextView) inflate.findViewById(R.id.tv_stop_act);//停止活动
        tv_delete_act = (TextView) inflate.findViewById(R.id.tv_delete_act);//删除活动
        tv_cancel = (TextView) inflate.findViewById(R.id.tv_cancel);
        final PopWindows popWindows = new PopWindows(getActivity(), getActivity(), inflate);
        popWindows.showPopWindowBottom();
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindows.cleanPopAlpha();
            }
        });
        tv_edit_act.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (info.get("State").toString().equals("2")) {
                    //报名中
                    MyToast.makeText(getActivity(), "已结束不可编辑", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getActivity(), EditActActivity.class);
                    intent.putExtra("activityid", info.get("ID").toString());
                    startActivity(intent);
                }

                popWindows.cleanPopAlpha();

            }
        });
        tv_stop_act.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindows.cleanPopAlpha();
                String activityId = info.get("ID").toString();
                String distributorid = PreferenceHelper.readString(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
                String sign = TGmd5.getMD5("" + distributorid + activityId);
                ((MyAcActivity) getActivity()).showLoadingProgressDialog(getActivity(), "");
                activityStopPresenter.activityStop(distributorid, activityId, sign);
            }
        });
        tv_delete_act.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindows.cleanPopAlpha();
                showdelActDialog(info);


            }
        });
    }

    public void showdelActDialog(final HashMap<String, Object> info) {

        LinearLayout.LayoutParams pm = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.dialog_yesorno, null);//这里的R.layout.alertdialog即为你自定义的布局文件
        TextView text1 = (TextView) view.findViewById(R.id.text1);
        text1.setText("确定删除活动吗？");
        View view_line = view.findViewById(R.id.view_line);
        TextView yes = (TextView) view.findViewById(R.id.yes);
        yes.setText("删除");
        TextView cancle = (TextView) view.findViewById(R.id.cancle);
        cancle.setText("再想想");
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
                mAlertDialog.dismiss();
                String activityId = info.get("ID").toString();
                String distributorid = PreferenceHelper.readString(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
                String sign = TGmd5.getMD5("" + distributorid + activityId);
                ((MyAcActivity) getActivity()).showLoadingProgressDialog(getActivity(), "");
                delActivityPresenter.delActivity(distributorid, activityId, sign);
            }
        });
    }

    public ArrayList<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();
    public ArrayList<HashMap<String, Object>> dataListTmp = new ArrayList<HashMap<String, Object>>();
    private int page = 1;

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

    private boolean isFirst = false;

    private void initDatas() {
        String distributorid = PreferenceHelper.readString(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        String sign = TGmd5.getMD5(distributorid + 1 + "" + page);
        if (isFirst) {
            ((MyAcActivity) getActivity()).showLoadingProgressDialog(getActivity(), "");
        }
        activityForMePresenter.activityForMeDatas(distributorid, 1, page, sign);

    }

    @Override
    public void OnActivityForMeSuccCallBack(String state, String respanse) {
        if (isFirst) {
            ((MyAcActivity) getActivity()).closeLoadingProgressDialog();
        }
        mListView.stopRefresh();
        isFirst = true;
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
    public void OnActivityForMeFialCallBack(String state, String respanse) {
        if (isFirst) {
            ((MyAcActivity) getActivity()).closeLoadingProgressDialog();
        }
        Log.e("haskfasa", "----失败-------" + respanse);
        mListView.stopRefresh();
        isFirst = true;
        MyToast.makeText(getActivity(), "" + respanse, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void closeActivityForMeProgress() {

    }

    private final mainHandler mHandler = new mainHandler(this);

    private static class mainHandler extends Handler {
        private final WeakReference<GoingCreActFragment> mActivity;

        public mainHandler(GoingCreActFragment activity) {
            mActivity = new WeakReference<GoingCreActFragment>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            GoingCreActFragment activity = mActivity.get();
            if (activity != null) {
                activity.flushDataList(msg);
            }
        }
    }

    public void flushDataList(Message msg) {
        if (msg.what == 1) {
            dataList.addAll(dataListTmp);
            if (dataList != null) {
                myActivityAdapter.setData(dataList);
                myActivityAdapter.notifyDataSetChanged();
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
            myActivityAdapter.setData(dataList);
            myActivityAdapter.notifyDataSetChanged();
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


    @Override
    public void OnActivityStopSuccCallBack(String state, String respanse) {
        //活动停止成功
        ((MyAcActivity) getActivity()).closeLoadingProgressDialog();
//        onRefresh();
        EventBus.getDefault().post("editedactsuccess");
        MyToast.makeText(getActivity(), "停止成功！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnActivityStopFialCallBack(String state, String respanse) {
        //活动停止失败
        ((MyAcActivity) getActivity()).closeLoadingProgressDialog();
        MyToast.makeText(getActivity(), "" + respanse, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void closeActivityStopProgress() {

    }

    @Override
    public void OnDelActivitySuccCallBack(String state, String respanse) {
        //活动删除成功
        ((MyAcActivity) getActivity()).closeLoadingProgressDialog();
//        onRefresh();
        EventBus.getDefault().post("editedactsuccess");
        MyToast.makeText(getActivity(), "删除成功！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnDelActivityFialCallBack(String state, String respanse) {
        //活动删除失败
        ((MyAcActivity) getActivity()).closeLoadingProgressDialog();
        MyToast.makeText(getActivity(), "" + respanse, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void closeDelActivityProgress() {

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
        if ("editedactsuccess".equals(action)) {
            isFirst = false;
            onRefresh();
        }

    }
}