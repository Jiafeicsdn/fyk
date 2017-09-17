package com.lvgou.distribution.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.utils.MyToast;

import android.widget.Toast;

import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.presenter.MyTaskPresenter;
import com.lvgou.distribution.presenter.StudySharePresenter;
import com.lvgou.distribution.presenter.TaskOperatePresenter;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.MyTaskView;
import com.lvgou.distribution.view.StudyShareView;
import com.lvgou.distribution.view.TaskOperateView;
import com.xdroid.common.utils.PreferenceHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/4/24.
 */

public class MyTuanBiActivity extends BaseActivity implements View.OnClickListener, MyTaskView, TaskOperateView, StudyShareView {
    private MyTaskPresenter myTaskPresenter;
    private String distributorid = "";
    private TaskOperatePresenter taskOperatePresenter;
    private StudySharePresenter studySharePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tuanbi);
        myTaskPresenter = new MyTaskPresenter(this);
        taskOperatePresenter = new TaskOperatePresenter(this);
        studySharePresenter = new StudySharePresenter(this);
        distributorid = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        initView();
        initClick();
    }


    private RelativeLayout rl_back;
    private TextView tv_title;
    private RelativeLayout rl_tuanbi_detail;//团币明细
    private TextView tv_tuanbi_num;//我的团币
    private TextView tv_sctx_num;//上传头像的团币
    private TextView tv_recharge;//团币充值
    private TextView tv_sctx_state;//上传头像状态
    private TextView tv_smrz_num;//实名认证的团币
    private TextView tv_smrz_state;//实名认证状态
    private TextView tv_fbfq_num;//发布蜂圈的团币
    private TextView tv_fbfq_state;//发布蜂圈的状态
    private TextView tv_fqzf_num;//蜂圈转发的团币
    private TextView tv_fqzf_state;//蜂圈转发的状态
    private TextView tv_fxxy_num;//分享学院课程的团币
    private TextView tv_fxxy_state;//分享学院课程的状态
    private TextView tv_kcpf_num;//课程评分的团币
    private TextView tv_kcpf_state;//课程评分的状态
    private TextView tv_tttj_num;//头条推荐的团币
    private TextView tv_tttj_state;//头条推荐的状态
    private TextView tv_tjjs_num;//推荐讲师的团币
    private TextView tv_tjjs_state;//推荐讲师的状态
    private View view_sctx;
    private View view_smrz;
    private View view_fbfq;
    private View view_fqzf;
    private View view_fxxy;
    private View view_kcpf;
    private View view_tttj;
    private View view_tjjs;
    private RelativeLayout rl_tjjs;//推荐讲师
    private TextView tv_question;//团币问题
    private RelativeLayout rl_sctx;
    private RelativeLayout rl_smrz;
    private RelativeLayout rl_fbfq;
    private RelativeLayout rl_fqzf;
    private RelativeLayout rl_fxxy;

    private void initView() {
        rl_fxxy = (RelativeLayout) findViewById(R.id.rl_fxxy);
        rl_fqzf = (RelativeLayout) findViewById(R.id.rl_fqzf);
        rl_fbfq = (RelativeLayout) findViewById(R.id.rl_fbfq);
        rl_smrz = (RelativeLayout) findViewById(R.id.rl_smrz);
        rl_sctx = (RelativeLayout) findViewById(R.id.rl_sctx);
        tv_question = (TextView) findViewById(R.id.tv_question);
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("团币任务");
        rl_tuanbi_detail = (RelativeLayout) findViewById(R.id.rl_tuanbi_detail);
        tv_tuanbi_num = (TextView) findViewById(R.id.tv_tuanbi_num);
        tv_recharge = (TextView) findViewById(R.id.tv_recharge);
        tv_sctx_num = (TextView) findViewById(R.id.tv_sctx_num);
        tv_sctx_state = (TextView) findViewById(R.id.tv_sctx_state);
        view_sctx = findViewById(R.id.view_sctx);
        tv_smrz_num = (TextView) findViewById(R.id.tv_smrz_num);
        tv_smrz_state = (TextView) findViewById(R.id.tv_smrz_state);
        view_smrz = findViewById(R.id.view_smrz);
        tv_fbfq_num = (TextView) findViewById(R.id.tv_fbfq_num);
        tv_fbfq_state = (TextView) findViewById(R.id.tv_fbfq_state);
        view_fbfq = findViewById(R.id.view_fbfq);
        tv_fqzf_num = (TextView) findViewById(R.id.tv_fqzf_num);
        tv_fqzf_state = (TextView) findViewById(R.id.tv_fqzf_state);
        view_fqzf = findViewById(R.id.view_fqzf);
        tv_fxxy_num = (TextView) findViewById(R.id.tv_fxxy_num);
        tv_fxxy_state = (TextView) findViewById(R.id.tv_fxxy_state);
        view_fxxy = findViewById(R.id.view_fxxy);
        tv_kcpf_num = (TextView) findViewById(R.id.tv_kcpf_num);
        tv_kcpf_state = (TextView) findViewById(R.id.tv_kcpf_state);
        view_kcpf = findViewById(R.id.view_kcpf);
        tv_tttj_num = (TextView) findViewById(R.id.tv_tttj_num);
        tv_tttj_state = (TextView) findViewById(R.id.tv_tttj_state);
        view_tttj = findViewById(R.id.view_tttj);
        tv_tjjs_num = (TextView) findViewById(R.id.tv_tjjs_num);
        tv_tjjs_state = (TextView) findViewById(R.id.tv_tjjs_state);
        view_tjjs = findViewById(R.id.view_tjjs);
        rl_tjjs = (RelativeLayout) findViewById(R.id.rl_tjjs);

    }

    private void initClick() {
        rl_back.setOnClickListener(this);
        rl_tuanbi_detail.setOnClickListener(this);
        tv_recharge.setOnClickListener(this);
        tv_sctx_state.setOnClickListener(this);
        tv_smrz_state.setOnClickListener(this);
        tv_fbfq_state.setOnClickListener(this);
        tv_fqzf_state.setOnClickListener(this);
        tv_fxxy_state.setOnClickListener(this);
        tv_kcpf_state.setOnClickListener(this);
        tv_tttj_state.setOnClickListener(this);
        tv_tjjs_state.setOnClickListener(this);
        rl_tjjs.setOnClickListener(this);
        tv_question.setOnClickListener(this);
        rl_sctx.setOnClickListener(this);
        rl_smrz.setOnClickListener(this);
        rl_fbfq.setOnClickListener(this);
        rl_fqzf.setOnClickListener(this);
        rl_fxxy.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initDatas();
    }

    private void initDatas() {
        String sign = TGmd5.getMD5(distributorid);
        showLoadingProgressDialog(this, "");
        myTaskPresenter.myTaskList(distributorid, sign);
    }

    private int addTB = 0;

    @Override
    public void onClick(View v) {
        String sign;
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_tuanbi_detail://团币明细
                openActivity(TuanbiDetailActivity.class);
                break;
            case R.id.tv_recharge://团币充值
                Intent intent = new Intent(MyTuanBiActivity.this, RechargeMoneyActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_sctx:
                if (tv_sctx_state.getText().equals("未完成")) {
                    openActivity(PersonalCenterActivity.class);
                }
                break;
            case R.id.tv_sctx_state://上传头像领取奖励
                if (tv_sctx_state.getText().equals("未完成")) {
                    openActivity(PersonalCenterActivity.class);
                } else if (tv_sctx_state.getText().equals("领取奖励")) {
                    sign = TGmd5.getMD5(distributorid + 10);
                    showLoadingProgressDialog(this, "");
                    taskOperatePresenter.taskOperate(distributorid, 10, sign);
                }

                break;
            case R.id.rl_smrz:
                if (tv_smrz_state.getText().equals("未完成")) {
                    openActivity(CertificationActivity.class);
                }
                break;
            case R.id.tv_smrz_state://实名认证领取奖励
                if (tv_smrz_state.getText().equals("未完成")) {
                    openActivity(CertificationActivity.class);
                } else if (tv_smrz_state.getText().equals("领取奖励")) {
                    sign = TGmd5.getMD5(distributorid + 4);
                    showLoadingProgressDialog(this, "");
                    taskOperatePresenter.taskOperate(distributorid, 4, sign);
                }

                break;
            case R.id.rl_fbfq:
                if (tv_fbfq_state.getText().equals("未完成")) {
                    openActivity(PublishFengActivity.class);
                }
                break;
            case R.id.tv_fbfq_state://发布蜂圈动态领取奖励
                if (tv_fbfq_state.getText().equals("未完成")) {
                    openActivity(PublishFengActivity.class);
                } else if (tv_fbfq_state.getText().equals("领取奖励")) {
                    sign = TGmd5.getMD5(distributorid + 1);
                    showLoadingProgressDialog(this, "");
                    taskOperatePresenter.taskOperate(distributorid, 1, sign);
                }

                break;
            case R.id.rl_fqzf:
                if (tv_fqzf_state.getText().equals("未完成")) {
//                    openActivity(CirclrFengActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("selection_postion", "3");
                    openActivity(HomeActivity.class, bundle);
                }
                break;
            case R.id.tv_fqzf_state://蜂圈转发领取奖励
                if (tv_fqzf_state.getText().equals("未完成")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("selection_postion", "3");
                    openActivity(HomeActivity.class, bundle);
//                    openActivity(CirclrFengActivity.class);
                }
                break;
            case R.id.rl_fxxy:
                if (tv_fxxy_state.getText().equals("未完成")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("selection_postion", "0");
                    openActivity(HomeActivity.class, bundle);
//                    openActivity(CollegeManagerActivity.class);
                }
                break;
            case R.id.tv_fxxy_state://分享学院课程领取奖励
                if (tv_fxxy_state.getText().equals("未完成")) {
//                    openActivity(CollegeManagerActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("selection_postion", "0");
                    openActivity(HomeActivity.class, bundle);
                } else if (tv_fxxy_state.getText().equals("领取奖励")) {
                    sign = TGmd5.getMD5(distributorid);
                    showLoadingProgressDialog(this, "");
                    studySharePresenter.studyShare(distributorid, sign);
                }

                break;
            case R.id.tv_kcpf_state://课程评分领取奖励
                sign = TGmd5.getMD5(distributorid + 5);
                showLoadingProgressDialog(this, "");
                taskOperatePresenter.taskOperate(distributorid, 5, sign);
                break;
            case R.id.tv_tttj_state://头条推荐领取奖励
                break;
            case R.id.tv_tjjs_state://推荐讲师领取奖励
                break;
            case R.id.rl_tjjs://推荐讲师
                Intent intent1 = new Intent(MyTuanBiActivity.this, RecommendTeacherActivity.class);
                startActivity(intent1);
                break;
            case R.id.tv_question: //团币问题
                openActivity(TuanbiQuestionActivity.class);
                break;
        }
    }

    @Override
    public void OnMyTaskSuccCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
        try {
            JSONObject jsb = new JSONObject(respanse);
            JSONArray jsa = new JSONArray(jsb.getString("result"));
            tv_tuanbi_num.setText(jsa.get(0).toString());
            PreferenceHelper.write(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.TUANBI, jsa.get(0).toString());
            JSONArray jsonArray = jsa.getJSONArray(1);
            JSONArray jsonArray1 = jsonArray.getJSONArray(0);
            tv_sctx_num.setText("+" + jsonArray1.get(0).toString());
            tv_sctx_state.setVisibility(View.VISIBLE);
            tv_sctx_state.setClickable(false);
            view_sctx.setVisibility(View.VISIBLE);
            if (jsonArray1.get(1).toString().equals("0")) {
                //不显示状态
                tv_sctx_state.setVisibility(View.GONE);
                view_sctx.setVisibility(View.GONE);
            } else if (jsonArray1.get(1).toString().equals("1")) {
                //已完成
                tv_sctx_state.setText("已完成");
                tv_sctx_state.setTextColor(Color.parseColor("#a3a3a3"));
            } else if (jsonArray1.get(1).toString().equals("2")) {
                //未完成
                tv_sctx_state.setText("未完成");
                tv_sctx_state.setClickable(true);
                tv_sctx_state.setTextColor(Color.parseColor("#d5aa5f"));
            } else if (jsonArray1.get(1).toString().equals("3")) {
                //可领取
                tv_sctx_state.setClickable(true);
                tv_sctx_state.setText("领取奖励");
                tv_sctx_state.setTextColor(Color.parseColor("#fc4d30"));
            }
            JSONArray jsonArray2 = jsonArray.getJSONArray(1);
            tv_smrz_num.setText("+" + jsonArray2.get(0).toString());
            tv_smrz_state.setVisibility(View.VISIBLE);
            tv_smrz_state.setClickable(false);
            view_smrz.setVisibility(View.VISIBLE);
            if (jsonArray2.get(1).toString().equals("0")) {
                //不显示状态
                tv_smrz_state.setVisibility(View.GONE);
                view_smrz.setVisibility(View.GONE);
            } else if (jsonArray2.get(1).toString().equals("1")) {
                //已完成
                tv_smrz_state.setText("已完成");
                tv_smrz_state.setTextColor(Color.parseColor("#a3a3a3"));
            } else if (jsonArray2.get(1).toString().equals("2")) {
                //未完成
                tv_smrz_state.setText("未完成");
                tv_smrz_state.setClickable(true);
                tv_smrz_state.setTextColor(Color.parseColor("#d5aa5f"));
            } else if (jsonArray2.get(1).toString().equals("3")) {
                //可领取
                tv_smrz_state.setClickable(true);
                tv_smrz_state.setText("领取奖励");
                tv_smrz_state.setTextColor(Color.parseColor("#fc4d30"));
            }
            JSONArray jsonArray3 = jsonArray.getJSONArray(2);
            tv_fbfq_num.setText("+" + jsonArray3.get(0).toString());
            tv_fbfq_state.setVisibility(View.VISIBLE);
            tv_fbfq_state.setClickable(false);
            view_fbfq.setVisibility(View.VISIBLE);
            if (jsonArray3.get(1).toString().equals("0")) {
                //不显示状态
                tv_fbfq_state.setVisibility(View.GONE);
                view_fbfq.setVisibility(View.GONE);
            } else if (jsonArray3.get(1).toString().equals("1")) {
                //已完成
                tv_fbfq_state.setText("已完成");
                tv_fbfq_state.setTextColor(Color.parseColor("#a3a3a3"));
            } else if (jsonArray3.get(1).toString().equals("2")) {
                //未完成
                tv_fbfq_state.setText("未完成");
                tv_fbfq_state.setClickable(true);
                tv_fbfq_state.setTextColor(Color.parseColor("#d5aa5f"));
            } else if (jsonArray3.get(1).toString().equals("3")) {
                //可领取
                tv_fbfq_state.setClickable(true);
                tv_fbfq_state.setText("领取奖励");
                tv_fbfq_state.setTextColor(Color.parseColor("#fc4d30"));
            }
            JSONArray jsonArray4 = jsonArray.getJSONArray(3);
            tv_fqzf_num.setText("+" + jsonArray4.get(0).toString());
            tv_fqzf_state.setVisibility(View.VISIBLE);
            tv_fqzf_state.setClickable(false);
            view_fqzf.setVisibility(View.VISIBLE);
            if (jsonArray4.get(1).toString().equals("0")) {
                //不显示状态
                tv_fqzf_state.setVisibility(View.GONE);
                view_fqzf.setVisibility(View.GONE);
            } else if (jsonArray4.get(1).toString().equals("1")) {
                //已完成
                tv_fqzf_state.setText("已完成");
                tv_fqzf_state.setTextColor(Color.parseColor("#a3a3a3"));
            } else if (jsonArray4.get(1).toString().equals("2")) {
                //未完成
                tv_fqzf_state.setText("未完成");
                tv_fqzf_state.setClickable(true);
                tv_fqzf_state.setTextColor(Color.parseColor("#d5aa5f"));
            } else if (jsonArray4.get(1).toString().equals("3")) {
                //可领取
                tv_fqzf_state.setClickable(true);
                tv_fqzf_state.setText("领取奖励");
                tv_fqzf_state.setTextColor(Color.parseColor("#fc4d30"));
            }
            JSONArray jsonArray5 = jsonArray.getJSONArray(4);
            tv_fxxy_num.setText("+" + jsonArray5.get(0).toString());
            tv_fxxy_state.setVisibility(View.VISIBLE);
            tv_fxxy_state.setClickable(false);
            view_fxxy.setVisibility(View.VISIBLE);
            if (jsonArray5.get(1).toString().equals("0")) {
                //不显示状态
                tv_fxxy_state.setVisibility(View.GONE);
                view_fxxy.setVisibility(View.GONE);
            } else if (jsonArray5.get(1).toString().equals("1")) {
                //已完成
                tv_fxxy_state.setText("已完成");
                tv_fxxy_state.setTextColor(Color.parseColor("#a3a3a3"));
            } else if (jsonArray5.get(1).toString().equals("2")) {
                //未完成
                tv_fxxy_state.setText("未完成");
                tv_fxxy_state.setClickable(true);
                tv_fxxy_state.setTextColor(Color.parseColor("#d5aa5f"));
            } else if (jsonArray5.get(1).toString().equals("3")) {
                //可领取
                tv_fxxy_state.setClickable(true);
                tv_fxxy_state.setText("领取奖励");
                tv_fxxy_state.setTextColor(Color.parseColor("#fc4d30"));
            }
            JSONArray jsonArray6 = jsonArray.getJSONArray(5);
            tv_kcpf_num.setText("+" + jsonArray6.get(0).toString());
            tv_kcpf_state.setVisibility(View.VISIBLE);
            tv_kcpf_state.setClickable(false);
            view_kcpf.setVisibility(View.VISIBLE);
            if (jsonArray6.get(1).toString().equals("0")) {
                //不显示状态
                tv_kcpf_state.setVisibility(View.GONE);
                view_kcpf.setVisibility(View.GONE);
            } else if (jsonArray6.get(1).toString().equals("1")) {
                //已完成
                tv_kcpf_state.setText("已完成");
                tv_kcpf_state.setTextColor(Color.parseColor("#a3a3a3"));
            } else if (jsonArray6.get(1).toString().equals("2")) {
                //未完成
                tv_kcpf_state.setText("未完成");
                tv_kcpf_state.setTextColor(Color.parseColor("#d5aa5f"));
            } else if (jsonArray6.get(1).toString().equals("3")) {
                //可领取
                tv_kcpf_state.setClickable(true);
                tv_kcpf_state.setText("领取奖励");
                tv_kcpf_state.setTextColor(Color.parseColor("#fc4d30"));
            }
            JSONArray jsonArray7 = jsonArray.getJSONArray(6);
            tv_tttj_num.setText("+" + jsonArray7.get(0).toString());
            tv_tttj_state.setVisibility(View.VISIBLE);
            tv_tttj_state.setClickable(false);
            view_tttj.setVisibility(View.VISIBLE);
            if (jsonArray7.get(1).toString().equals("0")) {
                //不显示状态
                tv_tttj_state.setVisibility(View.GONE);
                view_tttj.setVisibility(View.GONE);
            } else if (jsonArray7.get(1).toString().equals("1")) {
                //已完成
                tv_tttj_state.setText("已完成");
                tv_tttj_state.setTextColor(Color.parseColor("#a3a3a3"));
            } else if (jsonArray7.get(1).toString().equals("2")) {
                //未完成
                tv_tttj_state.setText("未完成");
                tv_tttj_state.setTextColor(Color.parseColor("#d5aa5f"));
            } else if (jsonArray7.get(1).toString().equals("3")) {
                //可领取
                tv_tttj_state.setClickable(true);
                tv_tttj_state.setText("领取奖励");
                tv_tttj_state.setTextColor(Color.parseColor("#fc4d30"));
            }
            JSONArray jsonArray8 = jsonArray.getJSONArray(7);
            tv_tjjs_num.setText("+" + jsonArray8.get(0).toString());
            tv_tjjs_state.setVisibility(View.VISIBLE);
            tv_tjjs_state.setClickable(false);
            view_tjjs.setVisibility(View.VISIBLE);
            if (jsonArray8.get(1).toString().equals("0")) {
                //不显示状态
                tv_tjjs_state.setVisibility(View.GONE);
                view_tjjs.setVisibility(View.GONE);
            } else if (jsonArray8.get(1).toString().equals("1")) {
                //已完成
                tv_tjjs_state.setText("已完成");
                tv_tjjs_state.setTextColor(Color.parseColor("#a3a3a3"));
            } else if (jsonArray8.get(1).toString().equals("2")) {
                //未完成
                tv_tjjs_state.setText("未完成");
                tv_tjjs_state.setTextColor(Color.parseColor("#d5aa5f"));
            } else if (jsonArray8.get(1).toString().equals("3")) {
                //可领取
                tv_tjjs_state.setClickable(true);
                tv_tjjs_state.setText("领取奖励");
                tv_tjjs_state.setTextColor(Color.parseColor("#fc4d30"));
            } else if (jsonArray8.get(1).toString().equals("4")) {
                //可领取
                tv_tjjs_state.setText("去推荐");
                tv_tjjs_state.setTextColor(Color.parseColor("#fc4d30"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void OnMyTaskFialCallBack(String state, String respanse) {
        closeLoadingProgressDialog();

    }

    @Override
    public void closeMyTaskProgress() {

    }

    @Override
    public void OnTaskOperateSuccCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
        MyToast.makeText(this, "领取成功！", Toast.LENGTH_SHORT).show();
        initView();
        initDatas();
    }

    @Override
    public void OnTaskOperateFialCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
        MyToast.makeText(this, "" + respanse, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void closeTaskOperateProgress() {

    }

    @Override
    public void OnStudyShareSuccCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
        MyToast.makeText(this, "领取成功！", Toast.LENGTH_SHORT).show();
        initView();
        initDatas();
    }

    @Override
    public void OnStudyShareFialCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
    }

    @Override
    public void closeStudyShareProgress() {

    }
}
