package com.lvgou.distribution.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
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

import com.lvgou.distribution.utils.MyToast;

import android.widget.Toast;

import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.CourseIntrActivity;
import com.lvgou.distribution.activity.MyCourseActivity;
import com.lvgou.distribution.adapter.ListenClassAdapter;
import com.lvgou.distribution.adapter.OpenClassAdapter;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.inter.AdapterToFraImpl;
import com.lvgou.distribution.inter.AdapterToFraImplvideo;
import com.lvgou.distribution.presenter.TeacherListForMePresenter;
import com.lvgou.distribution.utils.DownloadInfo;
import com.lvgou.distribution.utils.DownloadManager;
import com.lvgou.distribution.utils.DownloadService;
import com.lvgou.distribution.utils.PopWindows;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.TeacherListForMeView;
import com.lvgou.distribution.widget.XListView;
import com.xdroid.common.utils.PreferenceHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Administrator on 2017/3/24.
 * 我开的课
 */

public class OpenClassFragment extends Fragment implements XListView.IXListViewListener, TeacherListForMeView {
    private View view;
    private TeacherListForMePresenter teacherListForMePresenter;
    private String distributorid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_openclass, container, false);
        teacherListForMePresenter = new TeacherListForMePresenter(this);
        distributorid = PreferenceHelper.readString(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        initView();
        onRefresh();
        initClick();
        EventBus.getDefault().register(this);
        return view;
    }

    private ArrayList<HashMap<String, Object>> array;
    private ArrayList<HashMap<String, Object>> finishList;

    private void initClick() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent1 = new Intent(getActivity(), CourseIntrActivity.class);
                intent1.putExtra("id", dataList.get(position - 1).get("ID").toString());
                startActivity(intent1);
            }
        });
        openClassAdapter.setAdapterToFraImpl(new AdapterToFraImpl() {
            @Override
            public void doSomeThing(HashMap<String, Object> info) {
                if (info.get("State").toString().equals("4")) {
                    //如果这堂课不能下载
                    if ((info.get("Content") == null || info.get("Content").toString().equals(""))) {
                        MyToast.makeText(getActivity(), "没有更多可操作咯", Toast.LENGTH_SHORT).show();
                    } else {
                        showPopWindow(info);
                    }
                } else {
                    MyToast.makeText(getActivity(), "没有更多可操作咯", Toast.LENGTH_SHORT).show();
                }
            }
        });
        openClassAdapter.setAdapterToFraImplvodeo(new AdapterToFraImplvideo() {
            @Override
            public void doSomeThing(HashMap<String, Object> info) {
                for (int i = 0; i < dataList.size(); i++) {
                    if (info.get("ID").toString().equals(dataList.get(i).get("ID").toString())) {
                        dataList.get(i).put("isplay", info.get("isplay"));
                    }
                }
            }
        });
    }

    private void showPopWindow(final HashMap<String, Object> info) {
        array = (ArrayList<HashMap<String, Object>>) ((MyCourseActivity) getActivity()).mcache.getAsObject("xiazaiduilie" + distributorid);
        if (null == array) {
            array = new ArrayList<HashMap<String, Object>>();
        }
        finishList = (ArrayList<HashMap<String, Object>>) ((MyCourseActivity) getActivity()).mcache.getAsObject("downloadfinish" + distributorid);
        if (null == finishList) {
            finishList = new ArrayList<HashMap<String, Object>>();
        }
        final View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.my_class_button, null);
        TextView pop_tv_title = (TextView) inflate.findViewById(R.id.tv_title);
        TextView tv_download = (TextView) inflate.findViewById(R.id.tv_download);
        TextView tv_score = (TextView) inflate.findViewById(R.id.tv_score);
        View view_score_line = inflate.findViewById(R.id.view_score_line);
        //我开的课里面没有评分
        view_score_line.setVisibility(View.GONE);
        tv_score.setVisibility(View.GONE);
        TextView tv_cancel = (TextView) inflate.findViewById(R.id.tv_cancel);
        TextView tv_deletedown = (TextView) inflate.findViewById(R.id.tv_deletedown);
        View view_del_line = inflate.findViewById(R.id.view_del_line);
        View view_down_line = inflate.findViewById(R.id.view_down_line);
        pop_tv_title.setText(info.get("Theme").toString());
        if (info.get("Content") == null || info.get("Content").toString().equals("") || info.get("Content").toString().equals("null")) {
            tv_download.setVisibility(View.GONE);
            view_down_line.setVisibility(View.GONE);
        } else {
            view_down_line.setVisibility(View.VISIBLE);
            tv_download.setVisibility(View.VISIBLE);
        }
        boolean isdownFinish = false;
        boolean isdowning = false;
        for (HashMap<String, Object> stringObjectHashMap : array) {
            if (stringObjectHashMap.get("Comment").toString().equals(info.get("Content").toString())) {
                //已经在下载中
                isdownFinish = true;
                isdowning = true;
                break;
            }
        }
        for (HashMap<String, Object> stringObjectHashMap : finishList) {
            if (stringObjectHashMap.get("Comment").toString().equals(info.get("Content").toString())) {
                //已下载完成
                isdownFinish = true;
                break;
            }
        }
        if (isdownFinish) {
            tv_deletedown.setVisibility(View.VISIBLE);
            view_del_line.setVisibility(View.VISIBLE);
            tv_download.setVisibility(View.GONE);
            view_down_line.setVisibility(View.GONE);
        }
        final PopWindows popWindows = new PopWindows(getActivity(), getActivity(), inflate);
        popWindows.showPopWindowBottom();
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindows.cleanPopAlpha();
            }
        });
        final boolean finalIsdowning = isdowning;
        tv_deletedown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除下载
                popWindows.cleanPopAlpha();
                if (finalIsdowning) {
                    delDownloadDialog("课程下载中，确定要删除吗？", info);
                } else {
                    delDownloadDialog("确定删除吗？", info);
                }

            }
        });
        tv_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                array = (ArrayList<HashMap<String, Object>>) ((MyCourseActivity) getActivity()).mcache.getAsObject("xiazaiduilie" + distributorid);
                if (array == null) {
                    array = new ArrayList<>();
                }

                try {
                    HashMap<String, Object> hashMap = new HashMap<String, Object>();
                    hashMap.put("Theme", info.get("Theme"));
                    hashMap.put("Banner2", info.get("Banner1"));
                    hashMap.put("Comment", info.get("Content"));
                    hashMap.put("isdown", "true");
                    array.add(hashMap);
                    ((MyCourseActivity) getActivity()).mcache.put("xiazaiduilie" + distributorid, array);
                    String SDPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/fengyouke/" + distributorid + "/";
                    String downStr = info.get("Content").toString();
                    String name = TGmd5.getMD5(downStr);
                    if (downStr.endsWith(".mp3") || downStr.endsWith(".MP3")) {
                        name = name + ".mp3";
                    } else {
                        name = name + ".mp4";
                    }

                    DownloadManager downloadManager = DownloadService.getDownloadManager(getActivity());
                    downloadManager.addNewDownload(downStr, name, SDPath + name, true, true, new RequestCallBack<File>() {
                        @Override
                        public void onSuccess(ResponseInfo<File> responseInfo) {

                        }

                        @Override
                        public void onFailure(HttpException error, String msg) {
                            for (int i = 0; i < array.size(); i++) {
                                if (array.get(i).get("Comment").toString().equals(info.get("Content").toString())) {
                                    array.get(i).put("isdown", "false");
                                    break;
                                }
                            }
                            ((MyCourseActivity) getActivity()).mcache.put("xiazaiduilie" + distributorid, array);
                            EventBus.getDefault().post("downloadclass");
                        }
                    });
                    EventBus.getDefault().post("downloadclass");
                    popWindows.cleanPopAlpha();
                    MyToast.makeText(getActivity(), "进入下载列表", Toast.LENGTH_SHORT).show();
                    openClassAdapter.setData(dataList);
                    openClassAdapter.notifyDataSetChanged();
                } catch (DbException e) {
                    e.printStackTrace();
                }
                /*try {
                    HashMap<String, Object> hashMap = new HashMap<String, Object>();
                    hashMap.put("Theme", info.get("Theme"));
                    hashMap.put("Banner2", info.get("Banner1"));
                    hashMap.put("Comment", info.get("Content"));
                    hashMap.put("isdown", "true");
                    array.add(hashMap);
                    ((MyCourseActivity) getActivity()).mcache.put("xiazaiduilie" + distributorid, array);
                    String SDPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/fengyouke/" + distributorid + "/";
                    String downStr = info.get("Content").toString();
                    String name = TGmd5.getMD5(downStr);
                    if (downStr.endsWith(".mp3") || downStr.endsWith(".MP3")) {
                        name = name + ".mp3";
                    } else {
                        name = name + ".mp4";
                    }

                    DownloadManager downloadManager = DownloadService.getDownloadManager(getActivity());
                    downloadManager.addNewDownload(downStr, name, SDPath + name, true, true, new RequestCallBack<File>() {
                        @Override
                        public void onSuccess(ResponseInfo<File> responseInfo) {

                        }

                        @Override
                        public void onFailure(HttpException error, String msg) {
                            for (int i = 0; i < array.size(); i++) {
                                if (array.get(i).get("Comment").toString().equals(info.get("Comment").toString())) {
                                    array.get(i).put("isdown", "false");
                                    break;
                                }
                            }
                            ((MyCourseActivity) getActivity()).mcache.put("xiazaiduilie" + distributorid, array);
                            EventBus.getDefault().post("downloadclass");
                        }
                    });
                    EventBus.getDefault().post("downloadclass");
                    popWindows.cleanPopAlpha();
                    MyToast.makeText(getActivity(), "进入下载列表", Toast.LENGTH_SHORT).show();
                    openClassAdapter.setData(dataList);
                    openClassAdapter.notifyDataSetChanged();
                } catch (DbException e) {
                    e.printStackTrace();
                }*/


            }
        });
    }

    public void delDownloadDialog(String str, final HashMap<String, Object> info) {
        LinearLayout.LayoutParams pm = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.dialog_yesorno, null);//这里的R.layout.alertdialog即为你自定义的布局文件
        TextView text1 = (TextView) view.findViewById(R.id.text1);
        text1.setText(str);
        TextView yes = (TextView) view.findViewById(R.id.yes);
        TextView cancle = (TextView) view.findViewById(R.id.cancle);
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
                try {
                    DownloadManager downloadManager = DownloadService.getDownloadManager(getActivity());
                    DownloadInfo downloadInfo = downloadManager.getDownLoadInfo(info.get("Content").toString());
                    if (downloadInfo!=null){
                        downloadManager.removeDownload(downloadInfo);
                        boolean isdownloading = false;
                        for (HashMap<String, Object> stringObjectHashMap : array) {
                            if (stringObjectHashMap.get("Comment").toString().equals(info.get("Content").toString())) {
                                //已经在下载中
                                isdownloading = true;
                                array.remove(stringObjectHashMap);

                                break;
                            }
                        }
                        if (!isdownloading) {
                            for (HashMap<String, Object> stringObjectHashMap : finishList) {
                                if (stringObjectHashMap.get("Comment").toString().equals(info.get("Content").toString())) {
                                    //已下载完成
//                                openClassAdapter.deletePlaying(info);
                                    finishList.remove(stringObjectHashMap);
                                    break;
                                }
                            }
                        }
                        if (isdownloading) {
                            ((MyCourseActivity) getActivity()).mcache.put("xiazaiduilie" + distributorid, array);
                        } else {
                            ((MyCourseActivity) getActivity()).mcache.put("downloadfinish" + distributorid, finishList);
                        }
                        openClassAdapter.setData(dataList);
                        openClassAdapter.notifyDataSetChanged();
                        EventBus.getDefault().post("downloadclass");
                    }


                } catch (DbException e) {
                    e.printStackTrace();
                }
                mAlertDialog.dismiss();
            }
        });
    }

    private XListView mListView;
    private OpenClassAdapter openClassAdapter;
    private RelativeLayout rl_none_one;

    private void initView() {
        rl_none_one = (RelativeLayout) view.findViewById(R.id.rl_none_one);
        mListView = (XListView) view.findViewById(R.id.list_view);
        openClassAdapter = new OpenClassAdapter(getActivity());
        mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnable(true);
//        mListView.stopLoadMore();
        mListView.setAutoLoadEnable(false);
        mListView.setXListViewListener(this);
        mListView.setRefreshTime(((MyCourseActivity) getActivity()).getTime());
        mListView.setDivider(null);
        mListView.setAdapter(openClassAdapter);
    }


    public ArrayList<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();
    public ArrayList<HashMap<String, Object>> dataListTmp = new ArrayList<HashMap<String, Object>>();
    private int page = 1;

    @Override
    public void onRefresh() {
        if (openClassAdapter != null) {
            openClassAdapter.fragmentFresh();
        }
        array = (ArrayList<HashMap<String, Object>>) ((MyCourseActivity) getActivity()).mcache.getAsObject("xiazaiduilie" + distributorid);
        if (null == array) {
            array = new ArrayList<HashMap<String, Object>>();
        }
        finishList = (ArrayList<HashMap<String, Object>>) ((MyCourseActivity) getActivity()).mcache.getAsObject("downloadfinish" + distributorid);
        if (null == finishList) {
            finishList = new ArrayList<HashMap<String, Object>>();
        }
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
        String sign = TGmd5.getMD5(distributorid + "" + page);
        if (isFirst) {
            ((MyCourseActivity) getActivity()).showLoadingProgressDialog(getActivity(), "");
        }
        teacherListForMePresenter.teacherListForMeDatas(distributorid, page, sign);

    }

    @Override
    public void OnTeacherListForMeSuccCallBack(String state, String respanse) {
        if (isFirst) {
            ((MyCourseActivity) getActivity()).closeLoadingProgressDialog();
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
                map1.put("progress", "0");
                map1.put("totalprogress", "0");
                map1.put("isplay", "false");
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
    public void OnTeacherListForMeFialCallBack(String state, String respanse) {
        if (isFirst) {
            ((MyCourseActivity) getActivity()).closeLoadingProgressDialog();
        }
        mListView.stopRefresh();
        isFirst = true;
        MyToast.makeText(getActivity(), "" + respanse, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void closeTeacherListForMeProgress() {

    }

    private final mainHandler mHandler = new mainHandler(this);

    private static class mainHandler extends Handler {
        private final WeakReference<OpenClassFragment> mActivity;

        public mainHandler(OpenClassFragment activity) {
            mActivity = new WeakReference<OpenClassFragment>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            OpenClassFragment activity = mActivity.get();
            if (activity != null) {
                activity.flushDataList(msg);
            }
        }
    }

    public void flushDataList(Message msg) {
        if (msg.what == 1) {
            dataList.addAll(dataListTmp);
            if (dataList != null) {
                openClassAdapter.setData(dataList);
                openClassAdapter.notifyDataSetChanged();
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
            openClassAdapter.setData(dataList);
            openClassAdapter.notifyDataSetChanged();
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
        if ("openstopvoice".equals(action)) {
            openClassAdapter.stopVoice();
        }
        if ("coursedownloadingfinish".equals(action)) {
            if (openClassAdapter != null) {
                openClassAdapter.setData(dataList);
                openClassAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        openClassAdapter.stopVoice();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        openClassAdapter.destroyVideo();
        EventBus.getDefault().unregister(this);
    }
}