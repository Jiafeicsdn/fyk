package com.lvgou.distribution.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.request.RequestTask;
import com.lvgou.distribution.utils.TGmd5;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.common.utils.PreferenceHelper;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.request.extension.XDroidRequest;
import com.xdroid.request.extension.config.DataType;
import com.xdroid.request.extension.impl.OnRequestListenerAdapter;
import com.xdroid.utils.AppManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Snow on 2016/3/22 0022.
 * 我的任务 老版  可删
 */
public class MyTaskActivity extends BaseActivity {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.tv_integral)
    private TextView tv_integral;
    @ViewInject(R.id.tv_complete_num)
    private TextView tv_complete_num;
    @ViewInject(R.id.tv_status_sign)
    private TextView tv_status_sign;
    @ViewInject(R.id.tv_sign_poionts)
    private TextView tv_sign_poionts;
    @ViewInject(R.id.tv_complete_poionts)
    private TextView tv_complete_poionts;
    @ViewInject(R.id.tv_complete_status)
    private TextView tv_complete_status;
    @ViewInject(R.id.tv_share_poionts)
    private TextView tv_share_poionts;
    @ViewInject(R.id.tv_share_status)
    private TextView tv_share_status;
    @ViewInject(R.id.tv_people_num)
    private TextView tv_people_num;
    @ViewInject(R.id.tv_people_points)
    private TextView tv_people_points;
    @ViewInject(R.id.tv_news_num)
    private TextView tv_news_num;
    @ViewInject(R.id.tv_status_buy)
    private TextView tv_status_buy;
    @ViewInject(R.id.tv_set_name_poionts)
    private TextView tv_set_name_poionts;
    @ViewInject(R.id.tv_status_set_name)
    private TextView tv_status_set_name; // 实名认证
    @ViewInject(R.id.tv_bind_account_poionts)
    private TextView tv_bind_account_poionts;
    @ViewInject(R.id.tv_status_bind_account)
    private TextView tv_status_bind_account;
    @ViewInject(R.id.tv_baoming_poionts)
    private TextView tv_baoming_poionts;
    @ViewInject(R.id.tv_baoming_state)
    private TextView tv_baoming_state;
    @ViewInject(R.id.tv_make_any_poionts)
    private TextView tv_make_any_poionts;
    @ViewInject(R.id.tv_make_any_state)
    private TextView tv_status_make_any;
    @ViewInject(R.id.tv_deals_num)
    private TextView tv_deals_num;
    @ViewInject(R.id.tv_ten_poionts)
    private TextView tv_ten_poionts;
    @ViewInject(R.id.tv_status_ten)
    private TextView tv_status_ten;
    @ViewInject(R.id.tv_fifty_poionts)
    private TextView tv_fifty_poionts;
    @ViewInject(R.id.tv_status_fifty)
    private TextView tv_status_fifty;
    @ViewInject(R.id.tv_one_hundred_poionts)
    private TextView tv_one_hundred_poionts;
    @ViewInject(R.id.tv_status_one_hundred)
    private TextView tv_status_one_hundred;
    @ViewInject(R.id.tv_five_hundred_poionts)
    private TextView tv_five_hundred_poionts;
    @ViewInject(R.id.tv_status_five_hundred)
    private TextView tv_status_five_hundred;
    @ViewInject(R.id.tv_one_thousand_poionts)
    private TextView tv_one_thousand_poionts;
    @ViewInject(R.id.tv_status_one_thousand)
    private TextView tv_status_one_thousand;
    @ViewInject(R.id.tv_first_deal_points)
    private TextView tv_first_deal_points;
    @ViewInject(R.id.tv_status_first_deal)
    private TextView tv_status_first_deal;
    @ViewInject(R.id.tv_first_withdrawals_points)
    private TextView tv_first_withdrawals_points;
    @ViewInject(R.id.tv_status_first_withdrawals)
    private TextView tv_status_first_withdrawals;
    @ViewInject(R.id.tv_seek_tuanbi)
    private TextView tv_seek_tuanbi;
    @ViewInject(R.id.tv_exchange_tuanbi)
    private TextView tv_exchange_tuanbi;
    @ViewInject(R.id.rl_renzheng)
    private RelativeLayout rl_renzheng;
    @ViewInject(R.id.rl_binding_account)
    private RelativeLayout rl_binding_account;
    @ViewInject(R.id.rl_make_any)
    private RelativeLayout rl_make_any;
    @ViewInject(R.id.rl_baoming_tingke)
    private RelativeLayout rl_baoming;
    private String distributorid = "";
    private String index = "";

    String sign_staus;
    String per_deal_status;
    String share_status;
    String update_status;
    String upload_status;
    String bind_status;
    String tiyan_status;
    String renzheng_status;
    String make_any_status;
    String baoming_status;
    String product_id;
    String first_deal_status;
    String first_withdrawals_status;
    String deal_ten_status;
    String fifty_status;
    String one_hundred_status;
    String five_hundred_status;
    String thousand_status;
    String people_num;
    String people_potions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_task_one);
        ViewUtils.inject(this);
        index = getTextFromBundle("index");
        tv_title.setText("我的任务");
        rl_back.setVisibility(View.GONE);
    }

    @OnClick({R.id.rl_back, R.id.tv_complete_status, R.id.tv_status_set_name, R.id.tv_make_any_state, R.id.tv_baoming_state
            , R.id.tv_status_bind_account, R.id.tv_status_ten, R.id.tv_status_fifty, R.id.tv_status_one_hundred, R.id.tv_status_five_hundred, R.id.tv_status_one_thousand
            , R.id.tv_status_first_deal, R.id.tv_status_first_withdrawals, R.id.tv_seek_tuanbi, R.id.tv_exchange_tuanbi, R.id.rl_renzheng, R.id.rl_binding_account, R.id.rl_make_any, R.id.rl_baoming_tingke})
    public void OnClick(View view) {
        Bundle pBundle = new Bundle();
        switch (view.getId()) {
            case R.id.rl_back:
                if (index.equals("1")) {
                    pBundle.putString("selection_postion", "0");
                    openActivity(HomeActivity.class, pBundle);
                    finish();
                } else {
                    finish();
                }
                break;
            case R.id.tv_complete_status:// 每日成交
                if (per_deal_status.equals("3")) {
                    String sign = TGmd5.getMD5(distributorid + "2");
                    doTask(distributorid, "2", sign);
                }
                break;
            case R.id.tv_make_any_state:// 首单随时赚
                if (make_any_status.equals("3")) {
                    String sign = TGmd5.getMD5(distributorid + "13");
                    doTask(distributorid, "13", sign);
                }
                break;
            case R.id.tv_baoming_state:// 首次报名
                if (baoming_status.equals("3")) {
                    String sign = TGmd5.getMD5(distributorid + "14");
                    doTask(distributorid, "14", sign);
                }
                break;
            case R.id.tv_status_set_name:// 实名认证
                if (renzheng_status.equals("3")) {
                    String sign = TGmd5.getMD5(distributorid + "15");
                    doTask(distributorid, "15", sign);
                }
                break;
            case R.id.tv_status_bind_account:// 绑定账户
                if (bind_status.equals("3")) {
                    String sign = TGmd5.getMD5(distributorid + "5");
                    doTask(distributorid, "5", sign);
                }
                break;
            case R.id.tv_status_ten:// 完成10单
                if (deal_ten_status.equals("3")) {
                    String sign = TGmd5.getMD5(distributorid + "8");
                    doTask(distributorid, "8", sign);
                }
                break;
            case R.id.tv_status_fifty:// 完成50单
                if (fifty_status.equals("3")) {
                    String sign = TGmd5.getMD5(distributorid + "9");
                    doTask(distributorid, "9", sign);
                }
                break;
            case R.id.tv_status_one_hundred:// 完成100单
                if (one_hundred_status.equals("3")) {
                    String sign = TGmd5.getMD5(distributorid + "10");
                    doTask(distributorid, "10", sign);
                }
                break;
            case R.id.tv_status_five_hundred:// 完成500单
                if (five_hundred_status.equals("3")) {
                    String sign = TGmd5.getMD5(distributorid + "11");
                    doTask(distributorid, "11", sign);
                }
                break;
            case R.id.tv_status_one_thousand:// 完成1000单
                if (thousand_status.equals("3")) {
                    String sign = TGmd5.getMD5(distributorid + "12");
                    doTask(distributorid, "12", sign);
                }
                break;
            case R.id.tv_status_first_deal:// 首次完成
                if (first_deal_status.equals("3")) {
                    String sign = TGmd5.getMD5(distributorid + "6");
                    doTask(distributorid, "6", sign);
                }
                break;
            case R.id.tv_status_first_withdrawals:// 首次提现
                if (first_withdrawals_status.equals("3")) {
                    String sign = TGmd5.getMD5(distributorid + "7");
                    doTask(distributorid, "7", sign);
                }
                break;
            case R.id.tv_exchange_tuanbi:
                pBundle.putString("tips", "1");
                openActivity(TaskDetialActivity.class, pBundle);
                break;
            case R.id.tv_seek_tuanbi:
                openActivity(TuanBiDetialActivity.class);
                break;
            case R.id.rl_renzheng:
                openActivity(AuthenticationActivity.class);
                break;
            case R.id.rl_binding_account:
                openActivity(CashReceiveActivity.class);
                break;
            case R.id.rl_make_any:
                openActivity(ApplicationActivity.class);
                break;
            case R.id.rl_baoming_tingke:
                openActivity(GuiderNewCollegeActivity.class);
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        distributorid = PreferenceHelper.readString(MyTaskActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        if (distributorid.equals("") || distributorid.equals("null")) {
            openActivity(LoginActivity.class);
            finish();
        } else {
            if (checkNet()) {
                String sign = TGmd5.getMD5(distributorid);
                getData(distributorid, sign);
            }
        }
    }

    /**
     * 获取数据
     *
     * @param distributorid
     * @param sign
     */
    public void getData(String distributorid, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("distributorid", distributorid);
        maps.put("sign", sign);
        RequestTask.getInstance().getTaskList(MyTaskActivity.this, maps, new OnRequestListener());

    }

    private class OnRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if (status.equals("1")) {
                    String result = jsonObject.getString("result");
                    JSONArray array = new JSONArray(result);
                    int one_complete = 1;
                    if (array != null && array.length() > 0) {
                        String tuanbi = array.get(0).toString();
                        tv_integral.setText(tuanbi);
                        PreferenceHelper.write(MyTaskActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.TUANBI, tuanbi);
                        String sign_num = array.get(1).toString();
                        sign_staus = array.get(2).toString();
                        String per_deal_num = array.get(3).toString();
                        per_deal_status = array.get(4).toString();
                        people_num = array.get(27).toString();
                        people_potions = array.get(28).toString();

                        String share_num = array.get(29).toString();
                        share_status = array.get(30).toString();
                        tv_share_poionts.setText("+" + share_num + "团币");
                        if (share_status.equals("0")) {
                            tv_share_status.setText("未达成");
                            tv_share_status.setBackgroundResource(R.drawable.bg_red_shape);
                            tv_share_status.setTextColor(getResources().getColor(R.color.bg_daoliu_yellow_one));
                        } else if (sign_staus.equals("1")) {
                            tv_share_status.setText("已完成");
                            tv_share_status.setBackgroundResource(R.drawable.bg_task_white_shape);
                            tv_share_status.setTextColor(getResources().getColor(R.color.bg_notice_gray));
                            one_complete = one_complete + 1;
                        }

                        DecimalFormat decimalFormat = new DecimalFormat("##0.0");
                        tv_people_num.setText(people_num + "人");
                        tv_people_points.setText("+" + decimalFormat.format(Float.parseFloat(people_potions)));

                        tv_complete_poionts.setText("+" + per_deal_num + "团币");
                        if (per_deal_status.equals("1")) {
                            tv_complete_status.setText("已完成");
                            one_complete = one_complete + 1;
                            tv_complete_status.setBackgroundResource(R.drawable.bg_task_white_shape);
                            tv_complete_status.setTextColor(getResources().getColor(R.color.bg_notice_gray));
                        } else if (per_deal_status.equals("2")) {
                            tv_complete_status.setText("未达成");
                            tv_complete_status.setBackgroundResource(R.drawable.bg_red_shape);
                            tv_complete_status.setTextColor(getResources().getColor(R.color.bg_daoliu_yellow_one));
                        } else if (per_deal_status.equals("3")) {
                            tv_complete_status.setText("领取奖励");
                            tv_complete_status.setBackgroundResource(R.drawable.bg_task_yellow_shape);
                            tv_complete_status.setTextColor(getResources().getColor(R.color.bg_button_text_black));
                        }

                        tv_complete_num.setText("" + one_complete);

                        String bind_num = array.get(9).toString();
                        bind_status = array.get(10).toString();


                        String make_any_num = array.get(31).toString();
                        make_any_status = array.get(32).toString();
                        String baoming_num = array.get(33).toString();
                        baoming_status = array.get(34).toString();
                        String renzheng_num = array.get(35).toString();
                        renzheng_status = array.get(36).toString();

                        int two_complete = 0;
                        tv_set_name_poionts.setText("+" + renzheng_num + "团币");
                        if (renzheng_status.equals("1")) {
                            tv_status_set_name.setText("已完成");
                            tv_status_set_name.setBackgroundResource(R.drawable.bg_task_white_shape);
                            tv_status_set_name.setTextColor(getResources().getColor(R.color.bg_notice_gray));
                            two_complete = two_complete + 1;
                        } else if (renzheng_status.equals("2")) {
                            tv_status_set_name.setText("未达成");
                            tv_status_set_name.setBackgroundResource(R.drawable.bg_red_shape);
                            tv_status_set_name.setTextColor(getResources().getColor(R.color.bg_daoliu_yellow_one));
                        } else if (renzheng_status.equals("3")) {
                            tv_status_set_name.setText("领取奖励");
                            tv_status_set_name.setBackgroundResource(R.drawable.bg_task_yellow_shape);
                            tv_status_set_name.setTextColor(getResources().getColor(R.color.bg_button_text_black));
                        }


                        tv_bind_account_poionts.setText("+" + bind_num + "团币");
                        if (bind_status.equals("1")) {
                            tv_status_bind_account.setText("已完成");
                            tv_status_bind_account.setBackgroundResource(R.drawable.bg_task_white_shape);
                            tv_status_bind_account.setTextColor(getResources().getColor(R.color.bg_notice_gray));
                            two_complete = two_complete + 1;
                        } else if (bind_status.equals("2")) {
                            tv_status_bind_account.setText("未达成");
                            tv_status_bind_account.setBackgroundResource(R.drawable.bg_red_shape);
                            tv_status_bind_account.setTextColor(getResources().getColor(R.color.bg_daoliu_yellow_one));
                        } else if (bind_status.equals("3")) {
                            tv_status_bind_account.setText("领取奖励");
                            tv_status_bind_account.setBackgroundResource(R.drawable.bg_task_yellow_shape);
                            tv_status_bind_account.setTextColor(getResources().getColor(R.color.bg_button_text_black));
                        }

                        tv_make_any_poionts.setText("+" + make_any_num + "团币");
                        if (make_any_status.equals("1")) {
                            tv_status_make_any.setText("已完成");
                            tv_status_make_any.setBackgroundResource(R.drawable.bg_task_white_shape);
                            tv_status_make_any.setTextColor(getResources().getColor(R.color.bg_notice_gray));
                            two_complete = two_complete + 1;
                        } else if (make_any_status.equals("2")) {
                            tv_status_make_any.setText("未达成");
                            tv_status_make_any.setBackgroundResource(R.drawable.bg_red_shape);
                            tv_status_make_any.setTextColor(getResources().getColor(R.color.bg_daoliu_yellow_one));
                        } else if (make_any_status.equals("3")) {
                            tv_status_make_any.setText("领取奖励");
                            tv_status_make_any.setBackgroundResource(R.drawable.bg_task_yellow_shape);
                            tv_status_make_any.setTextColor(getResources().getColor(R.color.bg_button_text_black));
                        }

                        tv_baoming_poionts.setText("+" + baoming_num + "团币");
                        if (baoming_status.equals("1")) {
                            tv_baoming_state.setText("已完成");
                            tv_baoming_state.setBackgroundResource(R.drawable.bg_task_white_shape);
                            tv_baoming_state.setTextColor(getResources().getColor(R.color.bg_notice_gray));
                            two_complete = two_complete + 1;
                        } else if (baoming_status.equals("2")) {
                            tv_baoming_state.setText("未达成");
                            tv_baoming_state.setBackgroundResource(R.drawable.bg_red_shape);
                            tv_baoming_state.setTextColor(getResources().getColor(R.color.bg_daoliu_yellow_one));
                        } else if (baoming_status.equals("3")) {
                            tv_baoming_state.setText("领取奖励");
                            tv_baoming_state.setBackgroundResource(R.drawable.bg_task_yellow_shape);
                            tv_baoming_state.setTextColor(getResources().getColor(R.color.bg_button_text_black));
                        }

                        tv_news_num.setText("" + two_complete);

                        String three_complete = "";
                        String first_deal_num = array.get(11).toString();
                        first_deal_status = array.get(12).toString();
                        String first_withdrawals_num = array.get(13).toString();
                        first_withdrawals_status = array.get(14).toString();
                        String deal_ten_num = array.get(15).toString();
                        deal_ten_status = array.get(16).toString();
                        String fifty_num = array.get(17).toString();
                        fifty_status = array.get(18).toString();
                        String one_hundred_num = array.get(19).toString();
                        one_hundred_status = array.get(20).toString();
                        String five_hundred_num = array.get(21).toString();
                        five_hundred_status = array.get(22).toString();
                        String thousand_num = array.get(23).toString();
                        thousand_status = array.get(24).toString();

                        tv_first_deal_points.setText("+" + first_deal_num + "团币");
                        if (first_deal_status.equals("1")) {
                            tv_status_first_deal.setText("已完成");
                            tv_status_first_deal.setBackgroundResource(R.drawable.bg_task_white_shape);
                            tv_status_first_deal.setTextColor(getResources().getColor(R.color.bg_notice_gray));
                            three_complete = "已首次成交";
                        } else if (first_deal_status.equals("2")) {
                            tv_status_first_deal.setText("未达成");
                            tv_status_first_deal.setBackgroundResource(R.drawable.bg_red_shape);
                            tv_status_first_deal.setTextColor(getResources().getColor(R.color.bg_daoliu_yellow_one));
                        } else if (first_deal_status.equals("3")) {
                            tv_status_first_deal.setText("领取奖励");
                            tv_status_first_deal.setBackgroundResource(R.drawable.bg_task_yellow_shape);
                            tv_status_first_deal.setTextColor(getResources().getColor(R.color.bg_button_text_black));
                        }

                        tv_first_withdrawals_points.setText("+" + first_withdrawals_num + "团币");
                        if (first_withdrawals_status.equals("1")) {
                            tv_status_first_withdrawals.setText("已完成");
                            tv_status_first_withdrawals.setBackgroundResource(R.drawable.bg_task_white_shape);
                            tv_status_first_withdrawals.setTextColor(getResources().getColor(R.color.bg_notice_gray));
                            three_complete = "已首次提现";
                        } else if (first_withdrawals_status.equals("2")) {
                            tv_status_first_withdrawals.setText("未达成");
                            tv_status_first_withdrawals.setBackgroundResource(R.drawable.bg_red_shape);
                            tv_status_first_withdrawals.setTextColor(getResources().getColor(R.color.bg_daoliu_yellow_one));
                        } else if (first_withdrawals_status.equals("3")) {
                            tv_status_first_withdrawals.setText("领取奖励");
                            tv_status_first_withdrawals.setBackgroundResource(R.drawable.bg_task_yellow_shape);
                            tv_status_first_withdrawals.setTextColor(getResources().getColor(R.color.bg_button_text_black));
                        }

                        tv_ten_poionts.setText("+" + deal_ten_num + "团币");
                        if (deal_ten_status.equals("1")) {
                            tv_status_ten.setText("已完成");
                            tv_status_ten.setBackgroundResource(R.drawable.bg_task_white_shape);
                            tv_status_ten.setTextColor(getResources().getColor(R.color.bg_notice_gray));
                            three_complete = "已完成10单";
                        } else if (deal_ten_status.equals("2")) {
                            tv_status_ten.setText("未达成");
                            tv_status_ten.setBackgroundResource(R.drawable.bg_red_shape);
                            tv_status_ten.setTextColor(getResources().getColor(R.color.bg_daoliu_yellow_one));
                        } else if (deal_ten_status.equals("3")) {
                            tv_status_ten.setText("领取奖励");
                            tv_status_ten.setBackgroundResource(R.drawable.bg_task_yellow_shape);
                            tv_status_ten.setTextColor(getResources().getColor(R.color.bg_button_text_black));
                        }

                        tv_fifty_poionts.setText("+" + fifty_num + "团币");
                        if (fifty_status.equals("1")) {
                            tv_status_fifty.setText("已完成");
                            tv_status_fifty.setBackgroundResource(R.drawable.bg_task_white_shape);
                            tv_status_fifty.setTextColor(getResources().getColor(R.color.bg_notice_gray));
                            three_complete = "已完成50单";
                        } else if (fifty_status.equals("2")) {
                            tv_status_fifty.setText("未达成");
                            tv_status_fifty.setBackgroundResource(R.drawable.bg_red_shape);
                            tv_status_fifty.setTextColor(getResources().getColor(R.color.bg_daoliu_yellow_one));
                        } else if (fifty_status.equals("3")) {
                            tv_status_fifty.setText("领取奖励");
                            tv_status_fifty.setBackgroundResource(R.drawable.bg_task_yellow_shape);
                            tv_status_fifty.setTextColor(getResources().getColor(R.color.bg_button_text_black));
                        }

                        tv_one_hundred_poionts.setText("+" + one_hundred_num + "团币");
                        if (one_hundred_status.equals("1")) {
                            tv_status_one_hundred.setText("已完成");
                            tv_status_one_hundred.setBackgroundResource(R.drawable.bg_task_white_shape);
                            tv_status_one_hundred.setTextColor(getResources().getColor(R.color.bg_notice_gray));
                            three_complete = "已完成100单";
                        } else if (one_hundred_status.equals("2")) {
                            tv_status_one_hundred.setText("未达成");
                            tv_status_one_hundred.setBackgroundResource(R.drawable.bg_red_shape);
                            tv_status_one_hundred.setTextColor(getResources().getColor(R.color.bg_daoliu_yellow_one));
                        } else if (one_hundred_status.equals("3")) {
                            tv_status_one_hundred.setText("领取奖励");
                            tv_status_one_hundred.setBackgroundResource(R.drawable.bg_task_yellow_shape);
                            tv_status_one_hundred.setTextColor(getResources().getColor(R.color.bg_button_text_black));
                        }

                        tv_five_hundred_poionts.setText("+" + five_hundred_num + "团币");
                        if (five_hundred_status.equals("1")) {
                            tv_status_five_hundred.setText("已完成");
                            tv_status_five_hundred.setBackgroundResource(R.drawable.bg_task_white_shape);
                            tv_status_five_hundred.setTextColor(getResources().getColor(R.color.bg_notice_gray));
                            three_complete = "已完成500单";
                        } else if (five_hundred_status.equals("2")) {
                            tv_status_five_hundred.setText("未达成");
                            tv_status_five_hundred.setBackgroundResource(R.drawable.bg_red_shape);
                            tv_status_five_hundred.setTextColor(getResources().getColor(R.color.bg_daoliu_yellow_one));
                        } else if (five_hundred_status.equals("3")) {
                            tv_status_five_hundred.setText("领取奖励");
                            tv_status_five_hundred.setBackgroundResource(R.drawable.bg_task_yellow_shape);
                            tv_status_five_hundred.setTextColor(getResources().getColor(R.color.bg_button_text_black));
                        }

                        tv_one_thousand_poionts.setText("+" + thousand_num + "团币");
                        if (thousand_status.equals("1")) {
                            tv_status_one_thousand.setText("已完成");
                            tv_status_one_thousand.setBackgroundResource(R.drawable.bg_task_white_shape);
                            tv_status_one_thousand.setTextColor(getResources().getColor(R.color.bg_notice_gray));
                            three_complete = "已完成1000单";
                        } else if (thousand_status.equals("2")) {
                            tv_status_one_thousand.setText("未达成");
                            tv_status_one_thousand.setBackgroundResource(R.drawable.bg_red_shape);
                            tv_status_one_thousand.setTextColor(getResources().getColor(R.color.bg_daoliu_yellow_one));
                        } else if (thousand_status.equals("3")) {
                            tv_status_one_thousand.setText("领取奖励");
                            tv_status_one_thousand.setBackgroundResource(R.drawable.bg_task_yellow_shape);
                            tv_status_one_thousand.setTextColor(getResources().getColor(R.color.bg_button_text_black));
                        }
                        if (!three_complete.equals("")) {
                            tv_deals_num.setText(three_complete);
                        } else {
                            tv_deals_num.setText("已完成0单");
                        }
                    }
                } else if (status.equals("0")) {
                    MyToast.show(MyTaskActivity.this, jsonObject.getString("message"));
                    if (jsonObject.getString("message").equals("请登录")) {
                        openActivity(LoginActivity.class);
                        finish();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onRequestPrepare() {
            super.onRequestPrepare();
            showLoadingProgressDialog(MyTaskActivity.this, "");
        }

        @Override
        public void onRequestFailed(String error) {
            super.onRequestFailed(error);
            closeLoadingProgressDialog();
        }

        @Override
        public void onRequestFinish(String data) {
            super.onRequestFinish(data);
            closeLoadingProgressDialog();
        }
    }

    /**
     * 操作任务
     *
     * @param distributorid
     * @param type
     * @param sign
     */
    public void doTask(String distributorid, String type, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("distributorid", distributorid);
        maps.put("type", type);
        maps.put("sign", sign);

        RequestTask.getInstance().doTaskOperate(MyTaskActivity.this, maps, new OnDoTaskRequestListener());
    }

    private class OnDoTaskRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if (status.equals("0")) {
                    MyToast.show(MyTaskActivity.this, jsonObject.getString("message"));
                } else if (status.equals("1")) {
                    String result = jsonObject.getString("result");
                    JSONArray array = new JSONArray(result);
                    if (array != null && array.length() > 0) {
                        MyToast.show(MyTaskActivity.this, array.get(0).toString());
                    }
                    String sign = TGmd5.getMD5(distributorid);
                    getData(distributorid, sign);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void onPause() {
        super.onPause();
    }

    long back_pressed;

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            AppManager.getInstance().AppExit(getApplicationContext());
        } else {
            MyToast.show(MyTaskActivity.this, "再按一次退出应用！");
        }
        back_pressed = System.currentTimeMillis();
    }

}
