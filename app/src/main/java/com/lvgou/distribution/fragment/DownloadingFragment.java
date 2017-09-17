package com.lvgou.distribution.fragment;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.http.HttpHandler;
import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.MyCourseActivity;
import com.lvgou.distribution.adapter.DownLoadAdapter;
import com.lvgou.distribution.adapter.OpenClassAdapter;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.inter.AdapterToFraImpl;
import com.lvgou.distribution.utils.DownloadInfo;
import com.lvgou.distribution.utils.DownloadManager;
import com.lvgou.distribution.utils.DownloadService;
import com.lvgou.distribution.utils.PopWindows;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.widget.XListView;
import com.xdroid.common.utils.PreferenceHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

import cn.aigestudio.downloader.bizs.DLManager;

/**
 * Created by Administrator on 2017/3/24.
 * 下载中
 */

public class DownloadingFragment extends Fragment implements XListView.IXListViewListener, View.OnClickListener {
    private View view;
    private String distributorid = "";
    private ArrayList<HashMap<String, Object>> array;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_downloading, container, false);
        distributorid = PreferenceHelper.readString(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        EventBus.getDefault().register(this);
        initView();
        initDatas();
        initClick();

        return view;
    }


    private XListView mListView;
    public DownLoadAdapter downLoadAdapter;
    private View downloadHeader;
    private RelativeLayout rl_all_start;//全部开始
    private TextView tv_all_delete;//全部删除
    private RelativeLayout rl_none_one;
    private LinearLayout ll_download_all;
    private ImageView im_all_start;//全部开始的图标
    private TextView tv_all_start;

    private void initView() {
        rl_none_one = (RelativeLayout) view.findViewById(R.id.rl_none_one);
        mListView = (XListView) view.findViewById(R.id.list_view);
        downLoadAdapter = new DownLoadAdapter(getActivity());
        mListView.setPullRefreshEnable(false);
        mListView.setPullLoadEnable(false);
//        mListView.stopLoadMore();
        mListView.setAutoLoadEnable(false);
        mListView.setXListViewListener(this);
        mListView.setRefreshTime(((MyCourseActivity) getActivity()).getTime());
        mListView.setDivider(null);
        mListView.setAdapter(downLoadAdapter);
        downloadHeader = LayoutInflater.from(getActivity()).inflate(R.layout.download_class_header, null);
        ll_download_all = (LinearLayout) downloadHeader.findViewById(R.id.ll_download_all);
        rl_all_start = (RelativeLayout) downloadHeader.findViewById(R.id.rl_all_start);
        tv_all_delete = (TextView) downloadHeader.findViewById(R.id.tv_all_delete);
        im_all_start = (ImageView) downloadHeader.findViewById(R.id.im_all_start);
        tv_all_start = (TextView) downloadHeader.findViewById(R.id.tv_all_start);
        mListView.addHeaderView(downloadHeader);
    }


    public ArrayList<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();
    public ArrayList<HashMap<String, Object>> dataListTmp = new ArrayList<HashMap<String, Object>>();
    private int page = 1;


    @Override
    public void onRefresh() {
     /*   page = 1;
        dataList.clear();
        initDatas();*/
    }

    @Override
    public void onLoadMore() {
      /*  page++;
        initDatas();*/
    }

    private boolean isFirst = false;

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 在主线程接受 eventbus发送的事件
     *
     * @param action
     * @Subscribe 这个注解必须加上
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUIEvent(String action) {//EventBus.getDefault().post("teacherluyinjieshu");
        // 如果接受到注销的事件 就销毁自己
        //长按 录音按下
        if ("downloadclass".equals(action)) {
            initDatas();
        }
    }

    public void initDatas() {
        array = (ArrayList<HashMap<String, Object>>) ((MyCourseActivity) getActivity()).mcache.getAsObject("xiazaiduilie" + distributorid);
        if (array == null) {
            array = new ArrayList<>();
        }
        if (array.size() == 0) {
            rl_none_one.setVisibility(View.VISIBLE);
            ll_download_all.setVisibility(View.GONE);
        } else {
            rl_none_one.setVisibility(View.GONE);
            ll_download_all.setVisibility(View.VISIBLE);
        }

        downLoadAdapter.setData(array);
        downLoadAdapter.notifyDataSetChanged();

//        mListView.stopRefresh();
      /*
        String sign = TGmd5.getMD5(distributorid + "" + page);
        if (isFirst) {
            ((MyCourseActivity) getActivity()).showLoadingProgressDialog(getActivity(), "");
        }
//        teacherListForMePresenter.teacherListForMeDatas(distributorid, page, sign);*/

    }

    private void initClick() {
        rl_all_start.setOnClickListener(this);
        tv_all_delete.setOnClickListener(this);
        downLoadAdapter.setAdapterToFraImpl(new AdapterToFraImpl() {
            @Override
            public void doSomeThing(HashMap<String, Object> info) {
                //删光了
                rl_none_one.setVisibility(View.VISIBLE);
                ll_download_all.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_all_start://全部开始
                if (tv_all_start.getText().toString().equals("全部开始")) {
                    for (int i = 0; i < array.size(); i++) {
                        if (!array.get(i).get("isdown").toString().equals("false")) {
                            array.get(i).put("isdown", "true");
                            DownloadManager downloadManager = DownloadService.getDownloadManager(getActivity());
                            DownloadInfo downloadInfo = downloadManager.getDownLoadInfo(array.get(i).get("Comment").toString());
                            if (downloadInfo.getState().equals(HttpHandler.State.CANCELLED)) {
                                if (downloadInfo != null) {
                                    try {
                                        downloadManager.resumeDownload(downloadInfo, null);
                                        //                    downLoadAdapter.AllStop(1);
                                        ((MyCourseActivity) getActivity()).mcache.put("xiazaiduilie" + distributorid, array);
                                        downLoadAdapter.setData(array);
                                        downLoadAdapter.notifyDataSetChanged();
                                        /*im_all_start.setBackgroundResource(R.mipmap.all_stop_icom);
                                        tv_all_start.setText("全部暂停");*/
                                    } catch (DbException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    String SDPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/fengyouke/" + distributorid + "/";
                                    String downStr = array.get(i).get("Comment").toString();
                                    String name = TGmd5.getMD5(downStr);
                                    if (downStr.endsWith(".mp3") || downStr.endsWith(".MP3")) {
                                        name = name + ".mp3";
                                    } else {
                                        name = name + ".mp4";
                                    }
                                    try {
                                        downloadManager.addNewDownload(downStr, name, SDPath + name, true, true, null);
                                        ((MyCourseActivity) getActivity()).mcache.put("xiazaiduilie" + distributorid, array);
                                        downLoadAdapter.setData(array);
                                        downLoadAdapter.notifyDataSetChanged();

                                    } catch (DbException e) {
                                        e.printStackTrace();
                                    }
                                }

                            }
//                            ((MyCourseActivity) getActivity()).mcache.put("xiazaiduilie" + distributorid, array);
//                            downLoadAdapter.setData(array);
//                            downLoadAdapter.notifyDataSetChanged();
//                            im_all_start.setBackgroundResource(R.mipmap.all_stop_icom);
//                            tv_all_start.setText("全部暂停");

                        }
                        im_all_start.setBackgroundResource(R.mipmap.all_stop_icom);
                        tv_all_start.setText("全部暂停");
                    }
                } else {
                    DownloadManager downloadManager = DownloadService.getDownloadManager(getActivity());
                    try {
                        downloadManager.stopAllDownload();
                        for (int i = 0; i < array.size(); i++) {
                            if (!array.get(i).get("isdown").toString().equals("false")) {
                                array.get(i).put("isdown", "stop");

                            }
                        }
//                    downLoadAdapter.AllStop(2);
                        ((MyCourseActivity) getActivity()).mcache.put("xiazaiduilie" + distributorid, array);
                        downLoadAdapter.setData(array);
                        downLoadAdapter.notifyDataSetChanged();
                        im_all_start.setBackgroundResource(R.mipmap.all_start_icon);
                        tv_all_start.setText("全部开始");
                    } catch (DbException e) {
                        e.printStackTrace();
                    }

                }


                downLoadAdapter.setData(array);
                downLoadAdapter.notifyDataSetChanged();
                break;
            case R.id.tv_all_delete://全部删除
                showDelPopwindow();
                break;
        }
    }

    private void showDelPopwindow() {
        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.my_class_alldel, null);
        TextView tv_title = (TextView) inflate.findViewById(R.id.tv_title);
        tv_title.setText("确定全部删除吗？");
        TextView tv_sure = (TextView) inflate.findViewById(R.id.tv_sure);
        tv_sure.setText("全部删除");
        TextView tv_cancel = (TextView) inflate.findViewById(R.id.tv_cancel);
        final PopWindows popWindows = new PopWindows(getActivity(), getActivity(), inflate);
        popWindows.showPopWindowBottom();
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_none_one.setVisibility(View.VISIBLE);
                ll_download_all.setVisibility(View.GONE);

                DownloadManager downloadManager = DownloadService.getDownloadManager(getActivity());
                for (int i = 0; i < array.size(); i++) {
//                    if (array.get(i).get("isdown").equals("true")) {
                    //处于正在下载的要取消下载
                    DownloadInfo downloadInfo = downloadManager.getDownLoadInfo(array.get(i).get("Comment").toString());
                    try {
                        downloadManager.removeDownload(downloadInfo);
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
//                        DLManager.getInstance(getActivity()).dlCancel(array.get(i).get("Comment").toString());
//                    }
                }
                array.clear();
                ((MyCourseActivity) getActivity()).mcache.put("xiazaiduilie" + distributorid, array);
                downLoadAdapter.setData(array);
                downLoadAdapter.notifyDataSetChanged();
                popWindows.cleanPopAlpha();
            }

        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindows.cleanPopAlpha();
            }
        });
    }

}