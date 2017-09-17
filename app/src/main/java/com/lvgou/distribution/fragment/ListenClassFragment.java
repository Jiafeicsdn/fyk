package com.lvgou.distribution.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.utils.MyToast;

import android.widget.Toast;

import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.BaseActivity;
import com.lvgou.distribution.activity.BatchDownLoadActivity;
import com.lvgou.distribution.activity.CertificationActivity;
import com.lvgou.distribution.activity.CourseIntrActivity;
import com.lvgou.distribution.activity.MyClassesActivity;
import com.lvgou.distribution.activity.MyCourseActivity;
import com.lvgou.distribution.adapter.BuyBatchClassAdapter;
import com.lvgou.distribution.adapter.DownLoadAdapter;
import com.lvgou.distribution.adapter.ListenClassAdapter;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.inter.AdapterToActImpl;
import com.lvgou.distribution.inter.AdapterToFraImpl;
import com.lvgou.distribution.inter.AdapterToFraImplvideo;
import com.lvgou.distribution.presenter.MySchdulePresenter;
import com.lvgou.distribution.presenter.MyStudyListPresenter;
import com.lvgou.distribution.presenter.TeacherDownloadListPresenter;
import com.lvgou.distribution.utils.DownloadInfo;
import com.lvgou.distribution.utils.DownloadManager;
import com.lvgou.distribution.utils.DownloadService;
import com.lvgou.distribution.utils.MyToast;

import android.widget.Toast;

import com.lvgou.distribution.utils.PopWindows;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.MyScheduleView;
import com.lvgou.distribution.view.MyStudyListView;
import com.lvgou.distribution.view.TeacherDownloadListView;
import com.lvgou.distribution.widget.XListView;
import com.xdroid.common.utils.PreferenceHelper;
import com.lvgou.distribution.utils.MyToast;

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

import cn.aigestudio.downloader.bizs.DLManager;
import cn.aigestudio.downloader.interfaces.DLTaskListener;
import cn.aigestudio.downloader.interfaces.IDListener;
import cn.aigestudio.downloader.interfaces.SimpleDListener;

/**
 * Created by Administrator on 2017/3/24.
 * 我买的课
 */

public class ListenClassFragment extends Fragment implements XListView.IXListViewListener, MyStudyListView, MyScheduleView, View.OnClickListener {
    private View view;
    private MyStudyListPresenter myStudyListPresenter;
    private MySchdulePresenter mySchdulePresenter;

    private String distributorid;
    private ArrayList<HashMap<String, Object>> array;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_listen, container, false);
        myStudyListPresenter = new MyStudyListPresenter(this);
        mySchdulePresenter = new MySchdulePresenter(this);
        distributorid = PreferenceHelper.readString(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        ((MyCourseActivity) getActivity()).mcache.remove("gotobatchdownload");
        initView();
        onRefresh();
        initClick();
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        String gotobatchdownload = ((MyCourseActivity) getActivity()).mcache.getAsString("gotobatchdownload");
        if (gotobatchdownload == null || gotobatchdownload.equals("")) {

        } else if (gotobatchdownload.equals("true")) {
            onRefresh();
            ((MyCourseActivity) getActivity()).mcache.remove("gotobatchdownload");
        }

    }

    private XListView mListView;
    private ListenClassAdapter listenClassAdapter;
    private TextView tv_total_course;//课程总数
    private RelativeLayout rl_download;//批量下载
    private RelativeLayout rl_none_one;
    private RelativeLayout rl_title;

    private void initView() {
        rl_title = (RelativeLayout) view.findViewById(R.id.rl_title);
        rl_none_one = (RelativeLayout) view.findViewById(R.id.rl_none_one);
        tv_total_course = (TextView) view.findViewById(R.id.tv_total_course);
        rl_download = (RelativeLayout) view.findViewById(R.id.rl_download);
        mListView = (XListView) view.findViewById(R.id.list_view);
        listenClassAdapter = new ListenClassAdapter(getActivity());
        mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnable(true);
//        mListView.stopLoadMore();
        mListView.setAutoLoadEnable(false);
        mListView.setXListViewListener(this);
        mListView.setRefreshTime(((MyCourseActivity) getActivity()).getTime());
        mListView.setDivider(null);
        mListView.setAdapter(listenClassAdapter);

    }


    private void initClick() {
        rl_download.setOnClickListener(this);
        listenClassAdapter.setAdapterToFraImpl(new AdapterToFraImpl() {
            @Override
            public void doSomeThing(HashMap<String, Object> info) {
                if (info.get("TuanBi").toString().equals("4")) {
                    //如果这堂课不能下载，而且已经评分了
                    boolean isCanShow = true;
                    if (info.get("Comment") == null || info.get("Comment").toString().equals("")) {
                        //没有下载链接
                        if (info.get("State").toString().equals("1")) {
                            //没有学习
                            isCanShow = false;
                        } else if (!info.get("Grade").toString().equals("0")) {
                            //已经评分
                            isCanShow = false;
                        }
                    }
                    if (isCanShow) {
                        showPopWindow(info);
                    } else {
//                        MyMyToast.makeText(getActivity(), "没有更多可操作咯", Toast.LENGTH_SHORT).show();
                        MyToast.makeText(getActivity(), "没有更多可操作咯", Toast.LENGTH_SHORT).show();
                    }
                   /* if ((info.get("Comment") == null || info.get("Comment").toString().equals("")) &&( !info.get("Grade").toString().equals("0")||info.get("State").toString().equals("1"))) {
                        //没有下载链接，已经评分，未学习
                        MyToast.makeText(getActivity(), "没有更多可操作咯", Toast.LENGTH_SHORT).show();
                    } else {
                        showPopWindow(info);
                    }*/
                } else {
//                    MyMyToast.makeText(getActivity(), "没有更多可操作咯", Toast.LENGTH_SHORT).show();
                    MyToast.makeText(getActivity(), "没有更多可操作咯", Toast.LENGTH_SHORT).show();
//                    showTextDialog();
                }

            }
        });
        listenClassAdapter.setAdapterToFraImplvodeo(new AdapterToFraImplvideo() {
            @Override
            public void doSomeThing(HashMap<String, Object> info) {
                for (int i = 0; i < dataList.size(); i++) {
                    if (info.get("ID").toString().equals(dataList.get(i).get("ID").toString())) {
                        dataList.get(i).put("isplay", info.get("isplay"));
                    }
                }
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent1 = new Intent(getActivity(), CourseIntrActivity.class);
                intent1.putExtra("id", dataList.get(position - 1).get("TeacherID").toString());
                startActivity(intent1);
            }
        });

    }//


    @Override
    public void onClick(View v) {
        array = (ArrayList<HashMap<String, Object>>) ((MyCourseActivity) getActivity()).mcache.getAsObject("xiazaiduilie" + distributorid);
        if (array == null) {
            array = new ArrayList<>();
        }
        switch (v.getId()) {
            case R.id.rl_download://批量下载
                ((MyCourseActivity) getActivity()).mcache.put("gotobatchdownload", "true");
                Intent intent = new Intent(getActivity(), BatchDownLoadActivity.class);
                startActivity(intent);
                break;
        }
    }


    private TextView pop_tv_title;//底部pop标题
    private TextView tv_download;//底部pop下载课程
    private TextView tv_score;//底部pop课后评分
    private TextView tv_cancel;//底部pop取消


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
        pop_tv_title = (TextView) inflate.findViewById(R.id.tv_title);
        tv_download = (TextView) inflate.findViewById(R.id.tv_download);
        tv_score = (TextView) inflate.findViewById(R.id.tv_score);
        View view_score_line = inflate.findViewById(R.id.view_score_line);
        tv_cancel = (TextView) inflate.findViewById(R.id.tv_cancel);
        TextView tv_deletedown = (TextView) inflate.findViewById(R.id.tv_deletedown);
        View view_del_line = inflate.findViewById(R.id.view_del_line);
        View view_down_line = inflate.findViewById(R.id.view_down_line);
        pop_tv_title.setText(info.get("Theme").toString());
        if (info.get("Comment") == null || info.get("Comment").toString().equals("") || info.get("Comment").toString().equals("null")) {
            tv_download.setVisibility(View.GONE);
            view_down_line.setVisibility(View.GONE);
        } else {
            view_down_line.setVisibility(View.VISIBLE);
            tv_download.setVisibility(View.VISIBLE);
        }
        boolean isdownFinish = false;
        boolean isdowning=false;
        for (HashMap<String, Object> stringObjectHashMap : array) {
            if (stringObjectHashMap.get("Comment").toString().equals(info.get("Comment").toString())) {
                //已经在下载中
                isdownFinish = true;
                isdowning=true;
                break;
            }
        }

        for (HashMap<String, Object> stringObjectHashMap : finishList) {
            if (stringObjectHashMap.get("Comment").toString().equals(info.get("Comment").toString())) {
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
        if (!info.get("Grade").toString().equals("0") || info.get("State").toString().equals("1")) {
            //如果已经评分或者没有学习
            view_score_line.setVisibility(View.GONE);
            tv_score.setVisibility(View.GONE);
        }
        final PopWindows popWindows = new PopWindows(getActivity(), getActivity(), inflate);
        popWindows.showPopWindowBottom();
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindows.cleanPopAlpha();
            }
        });
        tv_score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //需要课程id
                popWindows.cleanPopAlpha();
                showEvaluateDialog(info);

            }
        });
        final boolean finalIsdowning = isdowning;
        tv_deletedown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除下载
                popWindows.cleanPopAlpha();
                if (finalIsdowning){
                    delDownloadDialog("课程下载中，确定要删除吗？", info);
                }else {
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
                    hashMap.put("Banner2", info.get("Banner2"));
                    hashMap.put("Comment", info.get("Comment"));
                    hashMap.put("isdown", "true");
                    array.add(hashMap);
                    ((MyCourseActivity) getActivity()).mcache.put("xiazaiduilie" + distributorid, array);
                    String SDPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/fengyouke/" + distributorid + "/";
                    String downStr = info.get("Comment").toString();
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
                    listenClassAdapter.setData(dataList);
                    listenClassAdapter.notifyDataSetChanged();
                } catch (DbException e) {
                    e.printStackTrace();
                }


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
                    DownloadInfo downloadInfo = downloadManager.getDownLoadInfo(info.get("Comment").toString());
                    downloadManager.removeDownload(downloadInfo);
                    boolean isdownloading = false;
                    for (HashMap<String, Object> stringObjectHashMap : array) {
                        if (stringObjectHashMap.get("Comment").toString().equals(info.get("Comment").toString())) {
                            //已经在下载中
                            isdownloading = true;
                            array.remove(stringObjectHashMap);

                            break;
                        }
                    }
                    if (!isdownloading) {
                        for (HashMap<String, Object> stringObjectHashMap : finishList) {
                            if (stringObjectHashMap.get("Comment").toString().equals(info.get("Comment").toString())) {
                                //已下载完成
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
                    listenClassAdapter.setData(dataList);
                    listenClassAdapter.notifyDataSetChanged();
                    EventBus.getDefault().post("downloadclass");

                } catch (DbException e) {
                    e.printStackTrace();
                }
                mAlertDialog.dismiss();
            }
        });
    }


    private Dialog dialog_evaluate;
    private int starNum = 5;
    private HashMap<String, Object> pfinfo;

    //评价弹框
    public void showEvaluateDialog(final HashMap<String, Object> info) {
        pfinfo = info;
        starNum = 5;
        dialog_evaluate = new Dialog(getActivity(), R.style.Mydialog);
        View view1 = View.inflate(getActivity(), R.layout.dailog_evaluate_classes, null);
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
                ((MyCourseActivity) getActivity()).showLoadingProgressDialog(getActivity(), "");
                String comment = et_evaluate_content.getText().toString().trim();
                String sign = TGmd5.getMD5(distributorid + info.get("ID").toString() + starNum + comment);
                mySchdulePresenter.doGradeSchedule(distributorid, info.get("ID").toString(), starNum + "", comment, sign);
                dialog_evaluate.dismiss();
            }
        });

        dialog_evaluate.setContentView(view1);
        dialog_evaluate.show();
    }

    public ArrayList<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();
    public ArrayList<HashMap<String, Object>> dataListTmp = new ArrayList<HashMap<String, Object>>();
    private int page = 1;
    private ArrayList<HashMap<String, Object>> finishList;

    @Override
    public void onRefresh() {
        if (listenClassAdapter != null) {
            listenClassAdapter.fragmentFresh();
        }
        page = 1;
        dataList.clear();
        //下载完成了的
        array = (ArrayList<HashMap<String, Object>>) ((MyCourseActivity) getActivity()).mcache.getAsObject("xiazaiduilie" + distributorid);
        if (null == array) {
            array = new ArrayList<HashMap<String, Object>>();
        }
        finishList = (ArrayList<HashMap<String, Object>>) ((MyCourseActivity) getActivity()).mcache.getAsObject("downloadfinish" + distributorid);
        if (null == finishList) {
            finishList = new ArrayList<HashMap<String, Object>>();
        }
        initDatas();


    }

    @Override
    public void onLoadMore() {
        page++;
        initDatas();
    }

    private void initDatas() {
        String sign = TGmd5.getMD5(distributorid + "" + page);
        ((MyCourseActivity) getActivity()).showLoadingProgressDialog(getActivity(), "");
        myStudyListPresenter.myStudyListDatas(distributorid, page, sign);

    }

    @Override
    public void OnMyStudyListSuccCallBack(String state, String respanse) {
        ((MyCourseActivity) getActivity()).closeLoadingProgressDialog();
        mListView.stopRefresh();
        try {
            JSONObject jsonObject = new JSONObject(respanse);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("result"));
            JSONObject jsonObject1 = new JSONObject(jsonArray.get(0).toString());
            String totalItems = jsonObject1.get("TotalItems").toString();
            tv_total_course.setText("共" + totalItems + "课程");
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
    public void OnMyStudyListFialCallBack(String state, String respanse) {
        ((MyCourseActivity) getActivity()).closeLoadingProgressDialog();
        mListView.stopRefresh();
    }

    @Override
    public void closeMyStudyListProgress() {

    }

    private final mainHandler mHandler = new mainHandler(this);

    @Override
    public void OnFamousSuccCallBack(String state, String respanse) {
        ((MyCourseActivity) getActivity()).closeLoadingProgressDialog();
//        onRefresh();
        for (int i = 0; i < dataList.size(); i++) {
            if (dataList.get(i).get("ID").toString().equals(pfinfo.get("ID").toString())){
                dataList.get(i).put("Grade",starNum+"");
                break;
            }
        }
        listenClassAdapter.setData(dataList);
        listenClassAdapter.notifyDataSetChanged();
        MyToast.makeText(getActivity(), "谢谢评分！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnFamousFialCallBack(String state, String respanse) {
        ((MyCourseActivity) getActivity()).closeLoadingProgressDialog();
        MyToast.makeText(getActivity(), "" + respanse, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void closeProgress() {

    }


    private static class mainHandler extends Handler {
        private final WeakReference<ListenClassFragment> mActivity;

        public mainHandler(ListenClassFragment activity) {
            mActivity = new WeakReference<ListenClassFragment>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            ListenClassFragment activity = mActivity.get();
            if (activity != null) {
                activity.flushDataList(msg);
            }
        }
    }

    public void flushDataList(Message msg) {
        if (msg.what == 1) {
            dataList.addAll(dataListTmp);
            if (dataList != null) {
                listenClassAdapter.setData(dataList);
                listenClassAdapter.notifyDataSetChanged();
                mListView.setPullLoadEnable(true);

            } else {

                mListView.setPullLoadEnable(false);
            }
            if (dataList.size() == 0) {
                mListView.setPullLoadEnable(false);
                rl_title.setVisibility(View.GONE);
                rl_none_one.setVisibility(View.VISIBLE);
            } else {
                rl_title.setVisibility(View.VISIBLE);
                rl_none_one.setVisibility(View.GONE);
            }
            dataListTmp.clear();
            mListView.stopRefresh();
        } else if (msg.what == 2) {
            dataList.addAll(dataListTmp);
            listenClassAdapter.setData(dataList);
            listenClassAdapter.notifyDataSetChanged();
            dataListTmp.clear();
            if (dataList.size() == 0) {
                rl_title.setVisibility(View.GONE);
                rl_none_one.setVisibility(View.VISIBLE);
            } else {
                rl_title.setVisibility(View.VISIBLE);
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
        if ("listenstopvoice".equals(action)) {
            listenClassAdapter.stopVoice();
        }
        if ("coursedownloadingfinish".equals(action)) {
            if (listenClassAdapter != null) {
                listenClassAdapter.setData(dataList);
                listenClassAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        listenClassAdapter.stopVoice();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        listenClassAdapter.destroyVideo();
        EventBus.getDefault().unregister(this);
    }
}
