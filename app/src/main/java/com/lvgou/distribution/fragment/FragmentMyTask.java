package com.lvgou.distribution.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.util.LogUtils;
import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.ApplicationActivity;
import com.lvgou.distribution.activity.AuthenticationActivity;
import com.lvgou.distribution.activity.GuideCradMnagerActivity;
import com.lvgou.distribution.activity.HomeActivity;
import com.lvgou.distribution.activity.HomePageActivity;
import com.lvgou.distribution.activity.InvateGuidersActivity;
import com.lvgou.distribution.activity.MyClassesActivity;
import com.lvgou.distribution.activity.PublishFenwenActivity;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.entity.TaskItemEntity;
import com.lvgou.distribution.event.EventFactory;
import com.lvgou.distribution.inter.OnClassifyPostionClickListener;
import com.lvgou.distribution.presenter.MyTaskListPresenter;
import com.lvgou.distribution.refresh.PullToRefreshBase;
import com.lvgou.distribution.refresh.PullToRefreshListView;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.GroupView;
import com.lvgou.distribution.viewholder.TaskListViewHolder;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.common.utils.DialogUtils;
import com.xdroid.common.utils.ListUtils;
import com.xdroid.common.utils.PreferenceHelper;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.eventbus.EventBus;
import com.xdroid.functions.holder.ListViewDataAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Snow on 2016/10/17.
 * 团币  任务
 */
public class FragmentMyTask extends Fragment implements GroupView, OnClassifyPostionClickListener<TaskItemEntity> {

    private ListView lv_list;
    private View view_one;
    private PullToRefreshListView pullToRefreshListView;
    private String distributorid = "";

    private ListViewDataAdapter<TaskItemEntity> taskItemEntityListViewDataAdapter;

    private MyTaskListPresenter myTaskListPresenter;

    private String state = "";

    private TaskItemEntity taskItemEntity = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_pullview, null);
        pullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
        view_one = (View) view.findViewById(R.id.view_one);
        lv_list = pullToRefreshListView.getRefreshableView();
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.MANUAL_REFRESH_ONLY);
        distributorid = PreferenceHelper.readString(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        state = PreferenceHelper.readString(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.STATE);
        myTaskListPresenter = new MyTaskListPresenter(this);
        view_one.setVisibility(View.VISIBLE);
        initViewViewHolder();

        String sign = TGmd5.getMD5(distributorid);
        myTaskListPresenter.geMyTaskList(distributorid, sign);

        return view;

    }

    public void initViewViewHolder() {
        taskItemEntityListViewDataAdapter = new ListViewDataAdapter<TaskItemEntity>();
        taskItemEntityListViewDataAdapter.setViewHolderClass(this, TaskListViewHolder.class);
        TaskListViewHolder.setOnClassifyPostionClickListener(this);
        lv_list.setAdapter(taskItemEntityListViewDataAdapter);
    }


    /**
     * 成功回调
     * 分享课程 - 学院首页
     * 蜂圈发布 - 发布蜂文
     * 蜂圈评论、点赞、转发 - 蜂圈首页
     * <p/>
     * 实名认证 -  实名认证页面
     * 课程评分 - 我的课表
     * 随时赚 - 随时赚
     * 发布蜂文、评论、点赞 - 蜂圈首页
     *
     * @param state
     * @param respanse
     */
    @Override
    public void OnFamousSuccCallBack(String state, String respanse) {

        switch (Integer.parseInt(state)) {
            case 1:
                try {
                    JSONObject jsonObject = new JSONObject(respanse);
                    String status = jsonObject.getString("status");
                    if (status.equals("1")) {
                        String result = jsonObject.getString("result");
                        JSONArray jsonArray = new JSONArray(result);
                        Log.e("askjfdhkahs", "-------------" + jsonArray);
                      /*  TaskItemEntity taskItemEntity_0 = new TaskItemEntity("0", "0", "4", "邀请好友/被邀请");

                        taskItemEntityListViewDataAdapter.append(taskItemEntity_0);*/
                        //[121,[[1,2],[1,2],[1,2],[5,0],[2,0],[5,0],[10,1]]]
                        // result":[3,0,1,[[1,2],[1,2],[1,2],[1,2],[1,2],[1,1],[5,0],[2,0],[5,0]],[[10,2],[2,2],[5,2],[2,3],[2,2],[2,3],[5,0]]]}
                        /************每日任务********/
                        String one = jsonArray.get(1).toString();
                        JSONArray array_one = new JSONArray(one);
                        Log.e("askjfdhkahs", "++++++++++++++++" + array_one);

                        String day_1 = array_one.get(0).toString();
                        JSONArray day_child_one = new JSONArray(day_1);
                        String str_01 = day_child_one.get(0).toString();// 团币
                        String str_001 = day_child_one.get(1).toString();// 状态
                        TaskItemEntity taskItemEntity_1 = new TaskItemEntity("0", str_01, str_001, "分享学院课程");
                        taskItemEntityListViewDataAdapter.append(taskItemEntity_1);

                        String day_2 = array_one.get(1).toString();
                        JSONArray day_child_two = new JSONArray(day_2);
                        String str_02 = day_child_two.get(0).toString();
                        String str_002 = day_child_two.get(1).toString();
                        TaskItemEntity taskItemEntity_2 = new TaskItemEntity("0", str_02, str_002, "发布蜂圈动态");
                        taskItemEntityListViewDataAdapter.append(taskItemEntity_2);

                        String day_3 = array_one.get(2).toString();
                        JSONArray day_child_3 = new JSONArray(day_3);
                        String str_03 = day_child_3.get(0).toString();
                        String str_003 = day_child_3.get(1).toString();
                        TaskItemEntity taskItemEntity_3 = new TaskItemEntity("0", str_03, str_003, "蜂圈转发");
                        taskItemEntityListViewDataAdapter.append(taskItemEntity_3);

                        String day_4 = array_one.get(3).toString();
                        JSONArray day_child_4 = new JSONArray(day_4);
                        String str_04 = day_child_4.get(0).toString();
                        String str_004 = day_child_4.get(1).toString();
                        TaskItemEntity taskItemEntity_4 = new TaskItemEntity("0", str_04, str_004, "蜂圈动态被推头条");
                        taskItemEntityListViewDataAdapter.append(taskItemEntity_4);

                        String day_5 = array_one.get(4).toString();
                        JSONArray day_child_5 = new JSONArray(day_5);
                        String str_05 = day_child_5.get(0).toString();
                        String str_005 = day_child_5.get(1).toString();
                        TaskItemEntity taskItemEntity_5 = new TaskItemEntity("0", str_05, str_005, "课程评分");
                        taskItemEntityListViewDataAdapter.append(taskItemEntity_5);


                        String day_6 = array_one.get(5).toString();
                        JSONArray day_child_6 = new JSONArray(day_6);
                        String str_06 = day_child_6.get(0).toString();
                        String str_006 = day_child_6.get(1).toString();
                        TaskItemEntity taskItemEntity_6 = new TaskItemEntity("0", str_06, str_006, "找团派团");
                        taskItemEntityListViewDataAdapter.append(taskItemEntity_6);

                        String day_7 = array_one.get(6).toString();
                        JSONArray day_child_7 = new JSONArray(day_7);
                        String str_07 = day_child_7.get(0).toString();
                        String str_007 = day_child_7.get(1).toString();
                        TaskItemEntity taskItemEntity_7 = new TaskItemEntity("0", str_07, str_007, "实名认证");
                        taskItemEntityListViewDataAdapter.append(taskItemEntity_7);

                      /*  String day_8 = array_one.get(7).toString();
                        JSONArray day_child_8 = new JSONArray(day_8);
                        String str_08 = day_child_8.get(0).toString();
                        String str_008 = day_child_8.get(1).toString();
                        TaskItemEntity taskItemEntity_8 = new TaskItemEntity("0", str_08, str_008, "课程评分");
                        taskItemEntityListViewDataAdapter.append(taskItemEntity_8);


                        String day_9 = array_one.get(8).toString();
                        JSONArray day_child_9 = new JSONArray(day_9);
                        String str_09 = day_child_9.get(0).toString();
                        String str_009 = day_child_9.get(1).toString();
                        TaskItemEntity taskItemEntity_9 = new TaskItemEntity("0", str_09, str_009, "找团派团");
                        taskItemEntityListViewDataAdapter.append(taskItemEntity_9);*/


                        /************新手任务********/
                        String two = jsonArray.get(4).toString();
                        JSONArray array_two = new JSONArray(two);


                        String new_1 = array_two.get(0).toString();
                        JSONArray new_child_1 = new JSONArray(new_1);
                        String str_10 = new_child_1.get(0).toString();
                        String str_010 = new_child_1.get(1).toString();
                        TaskItemEntity taskItemEntity_10 = new TaskItemEntity("0", str_10, str_010, "实名认证");
                        taskItemEntityListViewDataAdapter.append(taskItemEntity_10);

                        String new_2 = array_two.get(1).toString();
                        JSONArray new_child_2 = new JSONArray(new_2);
                        String str_11 = new_child_2.get(0).toString();
                        String str_011 = new_child_2.get(1).toString();
                        TaskItemEntity taskItemEntity_11 = new TaskItemEntity("0", str_11, str_011, "首次课程评分");
                        taskItemEntityListViewDataAdapter.append(taskItemEntity_11);


                        String new_3 = array_two.get(2).toString();
                        JSONArray new_child_3 = new JSONArray(new_3);
                        String str_12 = new_child_3.get(0).toString();
                        String str_012 = new_child_3.get(1).toString();
                        TaskItemEntity taskItemEntity_12 = new TaskItemEntity("0", str_12, str_012, "首次完成随时赚");
                        taskItemEntityListViewDataAdapter.append(taskItemEntity_12);

                        String new_4 = array_two.get(3).toString();
                        JSONArray new_child_4 = new JSONArray(new_4);
                        String str_13 = new_child_4.get(0).toString();
                        String str_013 = new_child_4.get(1).toString();
                        TaskItemEntity taskItemEntity_13 = new TaskItemEntity("0", str_13, str_013, "首次发布蜂圈动态");
                        taskItemEntityListViewDataAdapter.append(taskItemEntity_13);


                        String new_5 = array_two.get(4).toString();
                        JSONArray new_child_5 = new JSONArray(new_5);
                        String str_14 = new_child_5.get(0).toString();
                        String str_014 = new_child_5.get(1).toString();
                        TaskItemEntity taskItemEntity_14 = new TaskItemEntity("0", str_14, str_014, "首次蜂圈评论");
                        taskItemEntityListViewDataAdapter.append(taskItemEntity_14);

                        String new_6 = array_two.get(5).toString();
                        JSONArray new_child_6 = new JSONArray(new_6);
                        String str_15 = new_child_6.get(0).toString();
                        String str_015 = new_child_6.get(1).toString();
                        TaskItemEntity taskItemEntity_15 = new TaskItemEntity("0", str_15, str_015, "首次蜂圈点赞");
                        taskItemEntityListViewDataAdapter.append(taskItemEntity_15);

                        String new_7 = array_two.get(6).toString();
                        JSONArray new_child_7 = new JSONArray(new_7);
                        String str_16 = new_child_7.get(0).toString();
                        String str_016 = new_child_7.get(1).toString();
                        TaskItemEntity taskItemEntity_16 = new TaskItemEntity("0", str_16, str_016, "首次找团派团");
                        taskItemEntityListViewDataAdapter.append(taskItemEntity_16);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                try {
                    JSONObject jsonObject_one = new JSONObject(respanse);
                    String status = jsonObject_one.getString("status");
                    if (status.equals("1")) {
                        String result = jsonObject_one.getString("result");
                        JSONArray jsonArray = new JSONArray(result);
                        MyToast.show(getActivity(), jsonArray.get(0).toString());
                        String tuanbi_new = jsonArray.get(1).toString();
                        PreferenceHelper.write(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.TUANBI, tuanbi_new);

                        EventFactory.updateTuanbiTask(0);

                        taskItemEntity.setStstus("1");
                        taskItemEntityListViewDataAdapter.notifyDataSetChanged();

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
    public void OnFamousFialCallBack(String state, String respanse) {
        MyToast.show(getActivity(), "请求失败");
    }

    @Override
    public void closeProgress() {

    }

    @Override
    public void showProgress() {

    }
    public Dialog guide_dialog;
    public void showGuideDialog(final String state,String str) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        guide_dialog = new Dialog(getActivity(), R.style.Mydialog);// 创建自定义样式dialog
        View view = inflater.inflate(R.layout.dialog_hint_daoyou, null);// 得到加载view
        TextView txt_title = (TextView) view.findViewById(R.id.txt_title);
        txt_title.setText(str);// 设置加载信息
        Button btn_ok = (Button) view.findViewById(R.id.btn_ok);
        Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guide_dialog.dismiss();
                Intent intent_five = new Intent(getActivity(), GuideCradMnagerActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("state", state);
                intent_five.putExtras(bundle);
                getActivity().startActivity(intent_five);
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guide_dialog.dismiss();
            }
        });
//        loadingDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);// 去掉dialog内容以外的黑色背景色
        guide_dialog.setCancelable(false);// 不可以用“返回键”取消
        guide_dialog.setContentView(view, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
        guide_dialog.show();
    }

    public Dialog hint_dialog;

    public void showHintDialog(String msg) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        hint_dialog = new Dialog(getActivity(), R.style.Mydialog);// 创建自定义样式dialog
        View view = inflater.inflate(R.layout.dialog_hint_view, null);// 得到加载view
        TextView txt_title = (TextView) view.findViewById(R.id.txt_title);
        txt_title.setText(msg);// 设置加载信息
        Button btn_ok = (Button) view.findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hint_dialog.dismiss();
            }
        });
//        loadingDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);// 去掉dialog内容以外的黑色背景色
        hint_dialog.setCancelable(false);// 不可以用“返回键”取消
        hint_dialog.setContentView(view, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
        hint_dialog.show();
    }

    @Override
    public void onClassifyPostionClick(TaskItemEntity itemData, int postion) {
        Bundle bundle = new Bundle();
        switch (postion) {
            case 1:
                if (itemData.getStstus().equals("2")) {
                    switch (itemData.getName()) {
                        case "分享学院课程": // 去分享
                            EventFactory.turnHomeCollege(1);
                            EventFactory.turnToClass(1);
                            getActivity().finish();
                            break;
                        case "发布蜂圈动态":// 去发布ne
                            Intent intent_one = new Intent(getActivity(), PublishFenwenActivity.class);
                            getActivity().startActivity(intent_one);
                            getActivity().finish();
                            break;
                        case "每日蜂圈评论": // 去评论
                            bundle.putString("selection_postion", "3");
                            Intent intent_three = new Intent(getActivity(), HomeActivity.class);
                            intent_three.putExtras(bundle);
                            getActivity().startActivity(intent_three);
                            getActivity().finish();
                            break;
                        case "蜂圈点赞":// 去点赞
                            bundle.putString("selection_postion", "3");
                            Intent intent_four = new Intent(getActivity(), HomeActivity.class);
                            intent_four.putExtras(bundle);
                            getActivity().startActivity(intent_four);
                            getActivity().finish();
                            break;
                        case "蜂圈转发":// 去转发
                            String guide_picurl = PreferenceHelper.readString(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.GUIDE_PICURL);
                            String state1 = PreferenceHelper.readString(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.STATE);
                            if (state.equals("1")){
                            if (guide_picurl.equals("") || guide_picurl == null && guide_picurl.length() == 0){
                                showGuideDialog(state,"请上传导游证！");
                            }else {
                                showHintDialog(getResources().getString(R.string.guide_certificate_audit));
                            }
                        }else if (state.equals("3")){
                            showGuideDialog(state,"还没有上传导游证，是否去上传？");
                        }else {
                                bundle.putString("selection_postion", "3");
                                Intent intent_five = new Intent(getActivity(), HomeActivity.class);
                                intent_five.putExtras(bundle);
                                getActivity().startActivity(intent_five);
                                getActivity().finish();
                            }


                            break;
                        case "实名认证":// 去认证
                            bundle.putString("index", "0");
                            bundle.putString("state", state);
                            Intent intent_six = new Intent(getActivity(), AuthenticationActivity.class);
                            intent_six.putExtras(bundle);
                            getActivity().startActivity(intent_six);
                            break;
                        case "首次课程评分":// 去评分
                            Intent intent_class = new Intent(getActivity(), MyClassesActivity.class);
                            intent_class.putExtras(bundle);
                            getActivity().startActivity(intent_class);
                            break;
                        case "首次完成随时赚":// 去赚钱
                            bundle.putString("index", "0");
                            Intent intent_seven = new Intent(getActivity(), ApplicationActivity.class);
                            intent_seven.putExtras(bundle);
                            getActivity().startActivity(intent_seven);
                            break;
                        case "首次发布蜂圈动态":// 去发布
                            bundle.putString("selection_postion", "3");
                            Intent intent_eight = new Intent(getActivity(), HomeActivity.class);
                            intent_eight.putExtras(bundle);
                            getActivity().startActivity(intent_eight);
                            getActivity().finish();
                            break;
                        case "首次蜂圈评论":// 去评论
                            bundle.putString("selection_postion", "3");
                            Intent intent_nine = new Intent(getActivity(), HomeActivity.class);
                            intent_nine.putExtras(bundle);
                            getActivity().startActivity(intent_nine);
                            getActivity().finish();
                            break;
                        case "首次蜂圈点赞":// 去点赞
                            bundle.putString("selection_postion", "3");
                            Intent intent_ten = new Intent(getActivity(), HomeActivity.class);
                            intent_ten.putExtras(bundle);
                            getActivity().startActivity(intent_ten);
                            getActivity().finish();
                            break;
                    }
                } else if (itemData.getStstus().equals("4")) {
                    bundle.putString("index", "0");
                    Intent intent = new Intent(getActivity(), InvateGuidersActivity.class);
                    intent.putExtras(bundle);
                    getActivity().startActivity(intent);
                }
                break;
            case 2:
                taskItemEntity = itemData;
                if (itemData.getName().equals("发布蜂圈动态")) {
                    String sign_one = TGmd5.getMD5(distributorid + "1");
                    myTaskListPresenter.doMyTaskList(distributorid, "1", sign_one);
                } else if (itemData.getName().equals("每日蜂圈评论")) {
                    String sign_one = TGmd5.getMD5(distributorid + "2");
                    myTaskListPresenter.doMyTaskList(distributorid, "2", sign_one);
                } else if (itemData.getName().equals("蜂圈点赞")) {
                    String sign_one = TGmd5.getMD5(distributorid + "3");
                    myTaskListPresenter.doMyTaskList(distributorid, "3", sign_one);
                } else if (itemData.getName().equals("实名认证")) {
                    String sign_one = TGmd5.getMD5(distributorid + "4");
                    myTaskListPresenter.doMyTaskList(distributorid, "4", sign_one);
                } else if (itemData.getName().equals("首次课程评分")) {
                    String sign_one = TGmd5.getMD5(distributorid + "5");
                    myTaskListPresenter.doMyTaskList(distributorid, "5", sign_one);
                } else if (itemData.getName().equals("首次完成随时赚")) {
                    String sign_one = TGmd5.getMD5(distributorid + "6");
                    myTaskListPresenter.doMyTaskList(distributorid, "6", sign_one);
                } else if (itemData.getName().equals("首次发布蜂圈动态")) {
                    String sign_one = TGmd5.getMD5(distributorid + "7");
                    myTaskListPresenter.doMyTaskList(distributorid, "7", sign_one);
                } else if (itemData.getName().equals("首次蜂圈评论")) {
                    String sign_one = TGmd5.getMD5(distributorid + "8");
                    myTaskListPresenter.doMyTaskList(distributorid, "8", sign_one);
                } else if (itemData.getName().equals("首次蜂圈点赞")) {
                    String sign_one = TGmd5.getMD5(distributorid + "9");
                    myTaskListPresenter.doMyTaskList(distributorid, "9", sign_one);
                }
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        myTaskListPresenter.attach(this);
        MobclickAgent.onPageStart(getClass().getName());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        myTaskListPresenter.dettach();

    }


    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getClass().getName());
    }
}
