package com.lvgou.distribution.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.utils.MyToast;

import android.widget.Toast;

import com.lidroid.xutils.exception.DbException;
import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.ActDetailActivity;
import com.lvgou.distribution.activity.CourseIntrActivity;
import com.lvgou.distribution.activity.FamousTeacherDetialActivity;
import com.lvgou.distribution.activity.MyCourseActivity;
import com.lvgou.distribution.activity.RechargeMoneyActivity;
import com.lvgou.distribution.adapter.DiscussListAdapter;
import com.lvgou.distribution.bean.CommentZanBean;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.presenter.CommentTeacherPresenter;
import com.lvgou.distribution.presenter.DelCommentPresenter;
import com.lvgou.distribution.presenter.TeacherCommentListPresenter;
import com.lvgou.distribution.utils.DownloadInfo;
import com.lvgou.distribution.utils.DownloadManager;
import com.lvgou.distribution.utils.DownloadService;
import com.lvgou.distribution.utils.PopWindows;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.CommentTeacherView;
import com.lvgou.distribution.view.DelCommentView;
import com.lvgou.distribution.view.TeacherCommentListView;
import com.lvgou.distribution.widget.XListView;
import com.xdroid.common.utils.AnimationUtils;
import com.xdroid.common.utils.PreferenceHelper;
import com.xdroid.common.utils.StringUtils;
import com.lvgou.distribution.utils.MyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Administrator on 2017/4/5.
 * 课程详情-评论
 */

public class CourseDiscussFragment extends BaseFragment implements XListView.IXListViewListener, TeacherCommentListView, View.OnClickListener, CommentTeacherView, DelCommentView {
    private View view;
    private String teacherId = "";
    private TeacherCommentListPresenter teacherCommentListPresenter;
    private String distributorid = "";
    private CommentTeacherPresenter commentTeacherPresenter;
    private String tuan_one;
    private DelCommentPresenter delCommentPresenter;
    private String tuanbi_num;
    private String zanCount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_discuss, container, false);
        teacherCommentListPresenter = new TeacherCommentListPresenter(this);
        commentTeacherPresenter = new CommentTeacherPresenter(this);
        delCommentPresenter = new DelCommentPresenter(this);
        distributorid = PreferenceHelper.readString(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        tuan_one = PreferenceHelper.readString(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.TUANBI, "0");
        Bundle bundle = getArguments();//从activity传过来的Bundle
        if (bundle != null) {
            teacherId = bundle.getString("teacherId");
        }
        initView();
        onRefresh();
        initClick();
        return view;
    }

    private XListView mListView;
    private DiscussListAdapter discussListAdapter;
    private View courseFooter;
    private View courseheader;
    private ImageView im_score_one;
    private ImageView im_score_two;
    private ImageView im_score_three;
    private ImageView im_score_four;
    private ImageView im_score_five;
    private TextView tv_zanshang_num;
    private TextView tv_zanshagn;
    private RelativeLayout rl_dialog_root;
    private LinearLayout ll_dialog;
    private RelativeLayout rl_01;
    private EditText et_tuanbi;
    private TextView txt_tuanbi_num;
    private EditText et_content;
    private Button btn_zanshang;
    private RelativeLayout rl_none;

    private void initView() {
        rl_dialog_root = ((CourseIntrActivity) getActivity()).getRlDialogRoot();
        ll_dialog = ((CourseIntrActivity) getActivity()).getLlDialog();
        rl_01 = ((CourseIntrActivity) getActivity()).getRl01();
        et_tuanbi = ((CourseIntrActivity) getActivity()).getEttuanbi();
        txt_tuanbi_num = ((CourseIntrActivity) getActivity()).getTuanbinum();
        et_content = ((CourseIntrActivity) getActivity()).getEtcontent();
        btn_zanshang = ((CourseIntrActivity) getActivity()).getBtnzanshagn();

        mListView = (XListView) view.findViewById(R.id.list_view);
        discussListAdapter = new DiscussListAdapter(getActivity());
        mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnable(true);
//        mListView.stopLoadMore();
        mListView.setAutoLoadEnable(false);
        mListView.setXListViewListener(this);
        mListView.setRefreshTime(((CourseIntrActivity) getActivity()).getTime());
        mListView.setDivider(null);
        discussListAdapter.setData(new ArrayList<HashMap<String, Object>>());
        mListView.setAdapter(discussListAdapter);
        courseFooter = LayoutInflater.from(getActivity()).inflate(R.layout.course_intr_footer, null);
        mListView.addFooterView(courseFooter);
        courseheader = LayoutInflater.from(getActivity()).inflate(R.layout.course_comment_header, null);
        im_score_one = (ImageView) courseheader.findViewById(R.id.im_score_one);
        im_score_two = (ImageView) courseheader.findViewById(R.id.im_score_two);
        im_score_three = (ImageView) courseheader.findViewById(R.id.im_score_three);
        im_score_four = (ImageView) courseheader.findViewById(R.id.im_score_four);
        im_score_five = (ImageView) courseheader.findViewById(R.id.im_score_five);
        tv_zanshang_num = (TextView) courseheader.findViewById(R.id.tv_zanshang_num);
        tv_zanshagn = (TextView) courseheader.findViewById(R.id.tv_zanshagn);
        rl_none = (RelativeLayout) courseheader.findViewById(R.id.rl_none);
        mListView.addHeaderView(courseheader);
    }

    private void initClick() {
        tv_zanshagn.setOnClickListener(this);
        rl_01.setOnClickListener(this);
        btn_zanshang.setOnClickListener(this);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position > 1) {
                    if (distributorid.equals(dataList.get(position - 2).get("DistributorID").toString())) {
                        showDelPopwindow(dataList.get(position - 2).get("ID").toString());
                        removePosi = position - 2;
                    }
                }
            }
        });
    }

    private int removePosi = 0;

    private void showDelPopwindow(final String commentId) {
        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.my_class_alldel, null);
        TextView tv_title = (TextView) inflate.findViewById(R.id.tv_title);
        tv_title.setText("删除这条评论吗？");
        TextView tv_sure = (TextView) inflate.findViewById(R.id.tv_sure);
        tv_sure.setText("删除");
        TextView tv_cancel = (TextView) inflate.findViewById(R.id.tv_cancel);
        final PopWindows popWindows = new PopWindows(getActivity(), getActivity(), inflate);
        popWindows.showPopWindowBottom();
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sign = TGmd5.getMD5(distributorid + commentId);
                ((CourseIntrActivity) getActivity()).showLoadingProgressDialog(getActivity(), "");
                delCommentPresenter.delComment(distributorid, commentId, sign);

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

    public ArrayList<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();
    public ArrayList<HashMap<String, Object>> dataListTmp = new ArrayList<HashMap<String, Object>>();

    private boolean isRefresh = false;
    private int page = 1;

    private void initDatas() {
//        String distributorid = PreferenceHelper.readString(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        String sign = TGmd5.getMD5(teacherId + page);
        if (isRefresh) {
            ((CourseIntrActivity) getActivity()).showLoadingProgressDialog(getActivity(), "");
        }
        teacherCommentListPresenter.teacherCommentList(teacherId, page, sign);
    }

    @Override
    public void OnTeacherCommentListSuccCallBack(String state, String respanse) {
        if (isRefresh) {
            ((CourseIntrActivity) getActivity()).closeLoadingProgressDialog();
        }
        isRefresh = true;
        mListView.stopRefresh();
        try {
            JSONObject jsonObject = new JSONObject(respanse);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("result"));
            String pingfen = jsonArray.get(0).toString();
            if (pingfen.equals("0")) {
                im_score_one.setBackgroundResource(R.mipmap.unscore_icon);
                im_score_two.setBackgroundResource(R.mipmap.unscore_icon);
                im_score_three.setBackgroundResource(R.mipmap.unscore_icon);
                im_score_four.setBackgroundResource(R.mipmap.unscore_icon);
                im_score_five.setBackgroundResource(R.mipmap.unscore_icon);
            } else if (pingfen.equals("1")) {
                im_score_one.setBackgroundResource(R.mipmap.score_icon);
                im_score_two.setBackgroundResource(R.mipmap.unscore_icon);
                im_score_three.setBackgroundResource(R.mipmap.unscore_icon);
                im_score_four.setBackgroundResource(R.mipmap.unscore_icon);
                im_score_five.setBackgroundResource(R.mipmap.unscore_icon);
            } else if (pingfen.equals("2")) {
                im_score_one.setBackgroundResource(R.mipmap.score_icon);
                im_score_two.setBackgroundResource(R.mipmap.score_icon);
                im_score_three.setBackgroundResource(R.mipmap.unscore_icon);
                im_score_four.setBackgroundResource(R.mipmap.unscore_icon);
                im_score_five.setBackgroundResource(R.mipmap.unscore_icon);
            } else if (pingfen.equals("3")) {
                im_score_one.setBackgroundResource(R.mipmap.score_icon);
                im_score_two.setBackgroundResource(R.mipmap.score_icon);
                im_score_three.setBackgroundResource(R.mipmap.score_icon);
                im_score_four.setBackgroundResource(R.mipmap.unscore_icon);
                im_score_five.setBackgroundResource(R.mipmap.unscore_icon);
            } else if (pingfen.equals("4")) {
                im_score_one.setBackgroundResource(R.mipmap.score_icon);
                im_score_two.setBackgroundResource(R.mipmap.score_icon);
                im_score_three.setBackgroundResource(R.mipmap.score_icon);
                im_score_four.setBackgroundResource(R.mipmap.score_icon);
                im_score_five.setBackgroundResource(R.mipmap.unscore_icon);
            } else if (pingfen.equals("5")) {
                im_score_one.setBackgroundResource(R.mipmap.score_icon);
                im_score_two.setBackgroundResource(R.mipmap.score_icon);
                im_score_three.setBackgroundResource(R.mipmap.score_icon);
                im_score_four.setBackgroundResource(R.mipmap.score_icon);
                im_score_five.setBackgroundResource(R.mipmap.score_icon);
            }
            zanCount = jsonArray.get(1).toString();
            tv_zanshang_num.setText("已有" + zanCount + "人赏过");
            JSONObject jsonObject1 = new JSONObject(jsonArray.get(2).toString());
            JSONArray jsonArray1 = new JSONArray(jsonObject1.getString("Items"));
            String totalItems = jsonObject1.get("TotalItems").toString();
            ((CourseIntrActivity) getActivity()).notifyTitle(totalItems);
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
    public void OnTeacherCommentListFialCallBack(String state, String respanse) {
        Log.e("mksjhkss", "------" + respanse);
        if (isRefresh) {
            ((CourseIntrActivity) getActivity()).closeLoadingProgressDialog();
        }
        isRefresh = true;
        mListView.stopRefresh();
    }

    @Override
    public void closeTeacherCommentListProgress() {

    }


    private final mainHandler mHandler = new mainHandler(this);

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_zanshagn:
                openDialogZanShang();
                txt_tuanbi_num.setText("您剩余" + tuan_one + "团币");
                break;
            case R.id.rl_01:
                closeDialogZanShang();
                break;
            case R.id.btn_zanshang:
                tuanbi_num = et_tuanbi.getText().toString().trim();
                String content_zan = et_content.getText().toString().trim();
                if (StringUtils.isEmpty(tuanbi_num)) {
                    MyToast.show(getActivity(), "输入团币");
                    return;
                } else if (Integer.parseInt(tuanbi_num) > Integer.parseInt(tuan_one)) {

                    LinearLayout.LayoutParams pm = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    LayoutInflater inflater = LayoutInflater.from(getActivity());
                    View view1 = inflater.inflate(R.layout.dialog_lack_conis, null);//自定义的布局文件
                    TextView tv_sure = (TextView) view1.findViewById(R.id.tv_sure);
                    TextView tv_cancel = (TextView) view1.findViewById(R.id.tv_cancel);

                    final AlertDialog mAlertDialog = builder.create();
                    mAlertDialog.getWindow().setBackgroundDrawableResource(R.color.touming);
                    mAlertDialog.show();
                    mAlertDialog.getWindow().setContentView(view1, pm);
                    tv_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mAlertDialog.dismiss();
                        }
                    });
                    tv_sure.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //跳转到充值页面
                            Intent intent = new Intent(getActivity(), RechargeMoneyActivity.class);
                            startActivity(intent);
                            mAlertDialog.dismiss();
                        }
                    });
//                    MyToast.show(FamousTeacherDetialActivity.this, "团币不足");
                    return;
                } else {
                    if (content_zan.equals("")) {
                        content_zan = "您的演讲好棒,忍不住赞赏！";
                    } else {
                        content_zan = et_content.getText().toString().trim();
                    }
                    int tuanBi = Integer.parseInt(tuanbi_num);
                    String sign = TGmd5.getMD5(distributorid + teacherId + content_zan + tuanBi);
                    ((CourseIntrActivity) getActivity()).showLoadingProgressDialog(getActivity(), "");
                    commentTeacherPresenter.commentTeacher(distributorid, teacherId, content_zan, tuanBi, sign);
                    closeDialogZanShang();
                }
                break;
        }

    }

    // 弹出点赞对话框
    private void openDialogZanShang() {
        performDialogAnimation(rl_dialog_root,
                ll_dialog, true);
    }

    // 关闭点赞对话框
    private void closeDialogZanShang() {
        performDialogAnimation(rl_dialog_root,
                ll_dialog, false);

    }

    /**
     * 执行对话框动画
     *
     * @param rootView    背景View
     * @param contentView 内容View
     * @param isShow      true 执行显示动画 false 执行隐藏动画
     */
    protected void performDialogAnimation(final RelativeLayout rootView,
                                          final LinearLayout contentView, final Boolean isShow) {
        float[] floats = null;
        if (isShow) {
            rootView.setVisibility(View.VISIBLE);
            floats = new float[]{0.0f, 1.0f};
        } else {
            floats = new float[]{1.0f, 0.0f};
        }
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(rootView, "alpha", floats[0], floats[1]);
        alphaAnimator.setDuration(500);
        alphaAnimator.start();
        alphaAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (isShow) {
                    rootView.setVisibility(View.VISIBLE);
                } else {
                    rootView.setVisibility(View.GONE);
                }

            }
        });
        if (isShow) {
            AnimationUtils.expandingAnimation(contentView);
        } else {
            AnimationUtils.collapsingAnimation(contentView);
        }

    }

    @Override
    public void OnCommentTeacherSuccCallBack(String state, String respanse) {
        ((CourseIntrActivity) getActivity()).closeLoadingProgressDialog();
        MyToast.makeText(getActivity(), "赞赏成功", Toast.LENGTH_SHORT).show();
//        tuanbi_num
        tuan_one = Integer.parseInt(tuan_one) - Integer.parseInt(tuanbi_num) + "";
        onRefresh();
//        tuan_one = PreferenceHelper.readString(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.TUANBI, "0");

    }

    @Override
    public void OnCommentTeacherFialCallBack(String state, String respanse) {
        ((CourseIntrActivity) getActivity()).closeLoadingProgressDialog();
        MyToast.makeText(getActivity(), "" + respanse, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void closeCommentTeacherProgress() {

    }

    @Override
    public void OnDelCommentSuccCallBack(String state, String respanse) {
        ((CourseIntrActivity) getActivity()).closeLoadingProgressDialog();
//        dataList.remove(removePosi);
       /* if (dataList == null || dataList.size() == 0) {
            rl_none.setVisibility(View.VISIBLE);
        } else {
            rl_none.setVisibility(View.GONE);
        }*/
        onRefresh();
//        discussListAdapter.setData(dataList);
//        discussListAdapter.notifyDataSetChanged();

    }

    @Override
    public void OnDelCommentFialCallBack(String state, String respanse) {
        ((CourseIntrActivity) getActivity()).closeLoadingProgressDialog();
    }

    @Override
    public void closeDelCommentProgress() {

    }


    private static class mainHandler extends Handler {
        private final WeakReference<CourseDiscussFragment> mActivity;

        public mainHandler(CourseDiscussFragment activity) {
            mActivity = new WeakReference<CourseDiscussFragment>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            CourseDiscussFragment activity = mActivity.get();
            if (activity != null) {
                activity.flushDataList(msg);
            }
        }
    }

    public void flushDataList(Message msg) {
        if (msg.what == 1) {
            dataList.addAll(dataListTmp);
            if (dataList != null) {
                discussListAdapter.setData(dataList);
                discussListAdapter.notifyDataSetChanged();
                mListView.setPullLoadEnable(true);
            } else {
                mListView.setPullLoadEnable(false);
            }
            if (dataList.size() == 0) {
                Log.e("jagsdjfhs", "-----------1");
                mListView.setPullLoadEnable(false);
                rl_none.setVisibility(View.VISIBLE);
            } else {
                Log.e("jagsdjfhs", "-----------2");
                rl_none.setVisibility(View.GONE);
            }

            dataListTmp.clear();
            mListView.stopRefresh();
        } else if (msg.what == 2) {
            dataList.addAll(dataListTmp);
            discussListAdapter.setData(dataList);
            discussListAdapter.notifyDataSetChanged();
            dataListTmp.clear();
            if (dataList.size() == 0) {
                Log.e("jagsdjfhs", "-----------3");
                rl_none.setVisibility(View.VISIBLE);
            } else {
                Log.e("jagsdjfhs", "-----------4");
                rl_none.setVisibility(View.GONE);
            }
            mListView.setPullLoadEnable(false);
            mListView.stopLoadMore();
        }
    }

    @Override
    public void pullToRefresh() {

    }

    @Override
    public void refreshComplete() {

    }

    @Override
    public View getScrollableView() {
        return mListView;
    }
}
