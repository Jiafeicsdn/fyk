package com.lvgou.distribution.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.entity.GroupSignEntity;
import com.lvgou.distribution.event.EventFactory;
import com.lvgou.distribution.inter.OnListItemClickListener;
import com.lvgou.distribution.inter.ScrollViewListener;
import com.lvgou.distribution.presenter.DetialHotCityPresenter;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.DetialHotCityView;
import com.lvgou.distribution.view.MyListView;
import com.lvgou.distribution.view.ObservableScrollView;
import com.lvgou.distribution.viewholder.GroupSignUpViewHolder;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.xdroid.common.utils.PreferenceHelper;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.functions.holder.ListViewDataAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/11/9.
 * 带团详情页
 */
public class PublishGroupCarryDetialActivity extends BaseActivity implements DetialHotCityView, OnListItemClickListener<GroupSignEntity>, View.OnTouchListener {

    @ViewInject(R.id.rl_all)
    private RelativeLayout rl_all;
    @ViewInject(R.id.scroll_view)
    private ObservableScrollView scroll_view;
    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.rl_publish)
    private RelativeLayout rl_publish;
    @ViewInject(R.id.ll_tankuang)
    private LinearLayout ll_tankuang;
    @ViewInject(R.id.rl_online_custom)
    private RelativeLayout rl_online_custom;
    @ViewInject(R.id.rl_rexian)
    private RelativeLayout rl_cancle_delete;
    @ViewInject(R.id.tv_state)
    private TextView tv_cancle_state;
    @ViewInject(R.id.view_line)
    private View view_line;
    @ViewInject(R.id.tv_publish_title)
    private TextView tv_publish_title;
    @ViewInject(R.id.img_status)
    private ImageView img_status;
    @ViewInject(R.id.rl_group_out)
    private RelativeLayout rl_group_out;
    @ViewInject(R.id.img_group_out)
    private ImageView img_group_out;
    @ViewInject(R.id.ll_group_out)
    private LinearLayout ll_group_out;
    @ViewInject(R.id.tv_out_group_time)
    private TextView tv_out_group_time;
    @ViewInject(R.id.tv_out_group_days)
    private TextView tv_out_group_days;
    @ViewInject(R.id.tv_destination)
    private TextView tv_destination;
    @ViewInject(R.id.tv_grouptype)
    private TextView tv_grouptype;
    @ViewInject(R.id.tv_people_num)
    private TextView tv_people_num;
    @ViewInject(R.id.tv_guider_price)
    private TextView tv_guider_price;
    @ViewInject(R.id.tv_travel_intro)
    private TextView tv_travel_intro;
    @ViewInject(R.id.rl_guider_requerment)
    private RelativeLayout rl_guider_requerment;
    @ViewInject(R.id.img_guider_requerment)
    private ImageView img_guider_requerment;
    @ViewInject(R.id.ll_guider_requerment)
    private LinearLayout ll_guider_requerment;
    @ViewInject(R.id.tv_guider_nums)
    private TextView tv_guider_nums;
    @ViewInject(R.id.tv_guider_stars)
    private TextView tv_guider_stars;
    @ViewInject(R.id.tv_guider_sex)
    private TextView tv_guider_sex;
    @ViewInject(R.id.tv_guider_age)
    private TextView tv_guider_age;
    @ViewInject(R.id.tv_guider_type)
    private TextView tv_guider_type;
    @ViewInject(R.id.tv_guider_language)
    private TextView tv_guider_language;
    @ViewInject(R.id.tv_guider_other)
    private TextView tv_guider_other;
    @ViewInject(R.id.rl_publishe_people)
    private RelativeLayout rl_publishe_people;
    @ViewInject(R.id.img_publishe_people)
    private ImageView img_publishe_people;
    @ViewInject(R.id.ll_publish_people)
    private LinearLayout ll_publish_people;
    @ViewInject(R.id.tv_name)
    private TextView tv_name;
    @ViewInject(R.id.tv_phone)
    private TextView tv_phone;
    @ViewInject(R.id.tv_trval_name)
    private TextView tv_trval_name;
    @ViewInject(R.id.tv_additional)
    private TextView tv_additional;
    @ViewInject(R.id.tv_button)
    private TextView tv_button;
    @ViewInject(R.id.tv_sign_num)
    private TextView tv_sign_num;
    @ViewInject(R.id.rl_requirement_yet)
    private RelativeLayout rl_requirement_yet;
    @ViewInject(R.id.tv_num_yet)
    private TextView tv_num_yet;
    @ViewInject(R.id.lv_signup)
    private MyListView lv_signup;
    @ViewInject(R.id.load_more_progressbar)
    private ProgressBar progressBar;
    @ViewInject(R.id.ll_signup)
    private LinearLayout ll_signup;
    @ViewInject(R.id.rl_signup)
    private RelativeLayout rl_signup;
    @ViewInject(R.id.rl_dialog_share_root)
    private RelativeLayout rl_dialog_share_root;
    @ViewInject(R.id.ll_dialog_share_cotent)
    private LinearLayout ll_dialog_share_cotent;
    @ViewInject(R.id.rl_qq)
    private RelativeLayout rl_qq;
    @ViewInject(R.id.rl_qzone)
    private RelativeLayout rl_qzone;
    @ViewInject(R.id.rl_weixin)
    private RelativeLayout rl_weixin;
    @ViewInject(R.id.rl_pengyou)
    private RelativeLayout rl_pengyou;
    @ViewInject(R.id.rl_dismiss)
    private RelativeLayout rl_dismiss;
    @ViewInject(R.id.view_margrn_top)
    private View view_margrn_top;
    @ViewInject(R.id.view_one)
    private View view_one;

    private DetialHotCityPresenter detialHotCityPresenter;

    private Dialog dialog_del_can;// 取消，删除弹框

    private ListViewDataAdapter<GroupSignEntity> groupSignEntityListViewDataAdapter;

    private String distributorid = "";

    private String seekid = "";

    private int totalPages = 0;

    private int pageIndex = 1;

    private String groupType = "";// 1 表示 从全部 列表进入，2：表示从 带团列表进入

    private String isZhanKaiOne = "0";// 0 展开，1收起

    private String isZhanKaiTwo = "0";// 0 展开 1 收起

    private String isZhanKaiThree = "0"; // 0 展开，1 收起

    private String isPublisher = "0";// 是否是发布者

    private String is_show = "1";


    private String is_pull_data = "true";
    private String state_show = "";

    float x1 = 0;
    float y1 = 0;
    float x2 = 0;
    float y2 = 0;
    private boolean islast_one = false;

    private int data_length = 0;


    private String is_edit = "1"; // 编辑内容

    private String is_edit_again = "1"; // 重新编辑

    private String share_content = "派团找团信息共享平台，5W+导游在线实时接团";
    private String share_url = "http://agent.quygt.com/seek/seekdetail/";
    private String share_title = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_group_detial);
        ViewUtils.inject(this);
        tv_title.setText("派团信息");

        seekid = getTextFromBundle("seekid");


        distributorid = PreferenceHelper.readString(PublishGroupCarryDetialActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);


        initViewHolder();

        lv_signup.setFocusable(false);
        detialHotCityPresenter = new DetialHotCityPresenter(this);

        if (checkNet()) {
            showLoadingProgressDialog(PublishGroupCarryDetialActivity.this, "");
            String sign = TGmd5.getMD5(distributorid + seekid);
            detialHotCityPresenter.getDetialHotCity(distributorid, seekid, sign);
        }

        /**
         * 判断 ScrollView  是否滚动到底部
         */
        scroll_view.setScrollViewListener(new ScrollViewListener() {
            @Override
            public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy, boolean isLsat) {
                islast_one = isLsat;
            }
        });


        scroll_view.setOnTouchListener(this);
    }

    @OnClick({R.id.rl_back, R.id.tv_button, R.id.rl_publish, R.id.rl_group_out, R.id.rl_guider_requerment, R.id.rl_publishe_people, R.id.rl_online_custom, R.id.rl_rexian,
            R.id.rl_share_bottom, R.id.btn_commit, R.id.rl_qq, R.id.rl_qzone, R.id.rl_weixin, R.id.rl_pengyou, R.id.rl_dismiss, R.id.rl_all})
    public void OnClick(View view) {
        UMImage image = new UMImage(PublishGroupCarryDetialActivity.this, R.mipmap.paituan_logo);
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_button:
                if (tv_button.getText().equals("我要报名")) {
                    String sign_baoming = TGmd5.getMD5(distributorid + seekid);
                    detialHotCityPresenter.doSignUp(distributorid, seekid, sign_baoming);
                } else if (tv_button.getText().equals("编辑内容")) {
                    if (is_edit.equals("2")) {
                        Bundle bundle = new Bundle();
                        bundle.putString("postion", "2");
                        bundle.putString("seekid", seekid);
                        openActivity(PublishGroupActivity.class, bundle);
                    } else if (is_edit.equals("1")) {
                        MyToast.show(this, "已有人报名，不允许编辑内容");
                    }
                } else if (tv_button.getText().equals("重新编辑")) {
                    if (is_edit_again.equals("2")) {
                        Bundle bundle = new Bundle();
                        bundle.putString("postion", "2");
                        bundle.putString("seekid", seekid);
                        openActivity(PublishGroupActivity.class, bundle);
                    } else if (is_edit_again.equals("1")) {
                        MyToast.show(this, "已有人报名，不允许编辑内容");
                    }
                }
                break;
            case R.id.rl_publish:
                if (is_show.equals("1")) {
                    is_show = "2";
                    ll_tankuang.setVisibility(View.VISIBLE);
                    if ("1".equals(state_show)) {
                        tv_cancle_state.setText("删除");
                        ll_tankuang.setVisibility(View.VISIBLE);
                        rl_online_custom.setVisibility(View.GONE);
                        view_margrn_top.setVisibility(View.VISIBLE);

                    } else if ("2".equals(state_show)) {
                        rl_publish.setVisibility(View.VISIBLE);
                        tv_cancle_state.setText("取消");
                        ll_tankuang.setVisibility(View.VISIBLE);

                    } else if ("3".equals(state_show)) {
                        tv_cancle_state.setText("删除");
                        ll_tankuang.setVisibility(View.VISIBLE);
                        rl_online_custom.setVisibility(View.GONE);
                        view_margrn_top.setVisibility(View.VISIBLE);
                    }
                } else if (is_show.equals("2")) {
                    is_show = "1";
                    ll_tankuang.setVisibility(View.GONE);
                }
                break;
            case R.id.rl_group_out:
                if (isZhanKaiOne.equals("0")) {
                    isZhanKaiOne = "1";
                    ll_group_out.setVisibility(View.GONE);
                    img_group_out.setBackgroundResource(R.mipmap.icon_pull_down);
                } else if (isZhanKaiOne.equals("1")) {
                    isZhanKaiOne = "0";
                    ll_group_out.setVisibility(View.VISIBLE);
                    img_group_out.setBackgroundResource(R.mipmap.icon_pull_up);
                }
                break;
            case R.id.rl_guider_requerment:
                if (isZhanKaiTwo.equals("0")) {
                    isZhanKaiTwo = "1";
                    ll_guider_requerment.setVisibility(View.GONE);
                    img_guider_requerment.setBackgroundResource(R.mipmap.icon_pull_down);
                } else if (isZhanKaiTwo.equals("1")) {
                    isZhanKaiTwo = "0";
                    ll_guider_requerment.setVisibility(View.VISIBLE);
                    img_guider_requerment.setBackgroundResource(R.mipmap.icon_pull_up);
                }
                break;
            case R.id.rl_publishe_people:
                if (isZhanKaiThree.equals("0")) {
                    isZhanKaiThree = "1";
                    ll_publish_people.setVisibility(View.GONE);
                    img_publishe_people.setBackgroundResource(R.mipmap.icon_pull_down);
                } else if (isZhanKaiThree.equals("1")) {
                    isZhanKaiThree = "0";
                    ll_publish_people.setVisibility(View.VISIBLE);
                    img_publishe_people.setBackgroundResource(R.mipmap.icon_pull_up);
                }
                break;
            case R.id.rl_online_custom:
                openDialogShare();
                break;
            case R.id.rl_rexian:
                ll_tankuang.setVisibility(View.GONE);
                if (tv_cancle_state.getText().equals("删除")) {
                    showQuitDialog("1");
                } else if (tv_cancle_state.getText().equals("取消")) {
                    showQuitDialog("2");
                }
                break;
            case R.id.rl_qq:
                UMWeb web3 = new UMWeb(share_url + seekid);
                web3.setTitle(share_title);//标题
                web3.setThumb(image);  //缩略图
                web3.setDescription("蜂优客国内领先的导游移动工作平台！");//描述
                new ShareAction(PublishGroupCarryDetialActivity.this)
                        .setPlatform(SHARE_MEDIA.QQ)
                        .setCallback(umShareListener)
                        .withMedia(web3)
                        .share();
                /*new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.QQ)
                        .setCallback(umShareListener)
                        .withTitle(share_title)
                        .withText(share_content)
                        .withTargetUrl(share_url + seekid)
                        .withMedia(image)
                        .share();*/
                closeDialogShare();
                break;
            case R.id.rl_qzone:
                UMWeb web2 = new UMWeb(share_url + seekid);
                web2.setTitle(share_title);//标题
                web2.setThumb(image);  //缩略图
                web2.setDescription(share_content);//描述
                new ShareAction(PublishGroupCarryDetialActivity.this)
                        .setPlatform(SHARE_MEDIA.QZONE)
                        .setCallback(umShareListener)
                        .withMedia(web2)
                        .share();
                /*new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.QZONE)
                        .setCallback(umShareListener)
                        .withTitle(share_title)
                        .withText(share_content)
                        .withTargetUrl(share_url + seekid)
                        .withMedia(image)
                        .share();*/
                closeDialogShare();
                break;
            case R.id.rl_weixin:
                UMWeb web1 = new UMWeb(share_url + seekid);
                web1.setTitle(share_title);//标题
                web1.setThumb(image);  //缩略图
                web1.setDescription(share_content);//描述
                new ShareAction(PublishGroupCarryDetialActivity.this)
                        .setPlatform(SHARE_MEDIA.WEIXIN)
                        .setCallback(umShareListener)
                        .withMedia(web1)
                        .share();
                /*new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.WEIXIN)
                        .setCallback(umShareListener)
                        .withTitle(share_title)
                        .withText(share_content)
                        .withTargetUrl(share_url + seekid)
                        .withMedia(image)
                        .share();*/
                closeDialogShare();
                break;
            case R.id.rl_pengyou:
                UMWeb web = new UMWeb(share_url + seekid);
                web.setTitle(share_title);//标题
                web.setThumb(image);  //缩略图
                web.setDescription(share_content);//描述
                new ShareAction(PublishGroupCarryDetialActivity.this)
                        .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setCallback(umShareListener)
                        .withMedia(web)
                        .share();
                /*new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setCallback(umShareListener)
                        .withTitle(share_title)
                        .withText(share_content)
                        .withTargetUrl(share_url + seekid)
                        .withMedia(image)
                        .share();*/
                closeDialogShare();
                break;
            case R.id.rl_dismiss:
                ll_tankuang.setVisibility(View.GONE);
                closeDialogShare();
                break;
            case R.id.rl_all:
                ll_tankuang.setVisibility(View.GONE);
                break;
        }
    }


    public void initViewHolder() {
        groupSignEntityListViewDataAdapter = new ListViewDataAdapter<GroupSignEntity>();
        groupSignEntityListViewDataAdapter.setViewHolderClass(this, GroupSignUpViewHolder.class);
        GroupSignUpViewHolder.setOnListItemClickListener(this);
        lv_signup.setAdapter(groupSignEntityListViewDataAdapter);
    }


    @Override
    public void closeProgress() {
        closeLoadingProgressDialog();
    }

    /**
     * 成功回调
     *
     * @param respanse
     */

    @Override
    public void OnFamousSuccCallBack(String state, String respanse) {
        switch (Integer.parseInt(state)) {
            case 1:

                String sign_ = TGmd5.getMD5(seekid + pageIndex);
                detialHotCityPresenter.getSinUpList(seekid, pageIndex + "", sign_);

                try {
                    JSONObject jsonObject = new JSONObject(respanse);
                    String status = jsonObject.getString("status");
                    if (status.equals("1")) {
                        String result = jsonObject.getString("result");
                        JSONArray array = new JSONArray(result);

                        /****************派团信息*********************/
                        String data = array.get(0).toString();

                        /****************是否是发布者****** 0=否 1=是 ***********/
                        isPublisher = array.get(1).toString();


                        /****************是否报名****** 0=否 1=是 ***********/
                        String isSignUp = array.get(2).toString();


                        /****************报名记录实体对象*****************/
                        String signUpEntity = array.get(3).toString();

                        /****************否达要求 0=否 1=是 *****************/
                        String isReach = array.get(4).toString();

                        /**************** 多个原因以英文逗号","隔开 *****************/
                        String reasons = array.get(5).toString();


                        JSONObject jsonObject_data = new JSONObject(data);
                        String Title = jsonObject_data.getString("Title");
                        share_title = jsonObject_data.getString("Title");
                        String TravelTime = jsonObject_data.getString("TravelTime");
                        String Day = jsonObject_data.getString("Day");
                        String CountryName = jsonObject_data.getString("CountryName");
                        String Destination = jsonObject_data.getString("Destination");
                        String GroupType_one = jsonObject_data.getString("GroupType");
                        String PeopleCount = jsonObject_data.getString("PeopleCount");
                        String Price = jsonObject_data.getString("Price");
                        String TravelIntro = jsonObject_data.getString("TravelIntro");
                        String TourCount = jsonObject_data.getString("TourCount");
                        String Star = jsonObject_data.getString("Star");
                        String Sex = jsonObject_data.getString("Sex");
                        String TourAge = jsonObject_data.getString("TourAge");
                        String Language = jsonObject_data.getString("Language");
                        String DistributorType = jsonObject_data.getString("DistributorType");
                        String Other = jsonObject_data.getString("Other");
                        String DistributorName = jsonObject_data.getString("DistributorName");
                        String Mobile = jsonObject_data.getString("Mobile");
                        String Travel = jsonObject_data.getString("Travel");
                        String PublisherOther = jsonObject_data.getString("PublisherOther");
                        String State = jsonObject_data.getString("State");
                        tv_publish_title.setText(Title);


                        JSONObject json_carry = new JSONObject(signUpEntity);
                        String state_carry = json_carry.getString("State");
                        String isEmploy = json_carry.getString("IsEmploy");
                        String trvalTime = json_carry.getString("TravelTime");
                        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date start_date = null;
                        Date end_date = null;
                        try {
                            end_date = dfs.parse(dfs.format(new Date()));
                            start_date = dfs.parse(trvalTime.split("T")[0] + " " + trvalTime.split("T")[1]);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        switch (Integer.parseInt(isEmploy)) {
                            case 1:
                                tv_button.setText("已录用");
                                tv_button.setBackgroundResource(R.drawable.bg_conner_groupgray_shape);
                                img_status.setBackgroundResource(R.mipmap.icon_employment_already);
                                break;
                            case 0:
                                if (state_carry.equals("4") || (state_carry.equals("2") && (start_date.getTime() - end_date.getTime()) / 1000 < 0)) {
                                    tv_button.setText("未录用");
                                    tv_button.setBackgroundResource(R.drawable.bg_conner_groupgray_shape);
                                    img_status.setBackgroundResource(R.mipmap.icon_employment_yet);
                                } else if (state_carry.equals("1") || state_carry.equals("3") || (state_carry.equals("2") && (start_date.getTime() - end_date.getTime()) / 1000 > 0)) {
                                    tv_button.setText("已申请");
                                    tv_button.setBackgroundResource(R.drawable.bg_conner_groupgray_shape);
                                    img_status.setBackgroundResource(R.mipmap.icon_group_apply);
                                }
                                break;
                        }


                        String time_one = TravelTime.split("T")[0];

                        String str[] = time_one.split("-");
                        tv_out_group_time.setText(str[0] + "年" + str[1] + "月" + str[2] + "日");
                        tv_out_group_days.setText(Day + "天");
                        tv_destination.setText(CountryName + "," + Destination);
                        if (GroupType_one.equals("1")) {
                            tv_grouptype.setText("纯玩团");
                        } else if (GroupType_one.equals("2")) {
                            tv_grouptype.setText("豪华团");
                        } else if (GroupType_one.equals("3")) {
                            tv_grouptype.setText("购物团");
                        } else if (GroupType_one.equals("4")) {
                            tv_grouptype.setText("品质团");
                        } else if (GroupType_one.equals("5")) {
                            tv_grouptype.setText("低价团");
                        } else if (GroupType_one.equals("6")) {
                            tv_grouptype.setText("零负团");
                        }

                        tv_people_num.setText(PeopleCount);
                        tv_guider_price.setText(Price);
                        tv_travel_intro.setText(TravelIntro);
                        tv_guider_nums.setText(TourCount + "人");
                        if (Star.equals("1")) {
                            tv_guider_stars.setText("一星");
                        } else if (Star.equals("2")) {
                            tv_guider_stars.setText("二星");
                        } else if (Star.equals("3")) {
                            tv_guider_stars.setText("三星");
                        } else if (Star.equals("4")) {
                            tv_guider_stars.setText("四星");
                        } else if (Star.equals("5")) {
                            tv_guider_stars.setText("五星");
                        }

                        if (Sex.equals("0")) {
                            tv_guider_sex.setText("不限");
                        } else if (Sex.equals("1")) {
                            tv_guider_sex.setText("男");
                        } else if (Sex.equals("2")) {
                            tv_guider_sex.setText("女");
                        }

                        if (TourAge.equals("0")) {
                            tv_guider_age.setText("不限");
                        } else if (TourAge.equals("1")) {
                            tv_guider_age.setText("1年以上");
                        } else if (TourAge.equals("2")) {
                            tv_guider_age.setText("2年以上");
                        } else if (TourAge.equals("3")) {
                            tv_guider_age.setText("3年以上");
                        } else if (TourAge.equals("4")) {
                            tv_guider_age.setText("4年以上");
                        } else if (TourAge.equals("5")) {
                            tv_guider_age.setText("5年以上");
                        }

                        if (DistributorType.equals("1")) {
                            tv_guider_type.setText("地接");
                        } else if (DistributorType.equals("2")) {
                            tv_guider_type.setText("全陪");
                        } else if (DistributorType.equals("3")) {
                            tv_guider_type.setText("地接+全陪");
                        }

                        tv_guider_language.setText(Language);
                        tv_guider_other.setText(Other);
                        tv_trval_name.setText(Travel);
                        tv_additional.setText(PublisherOther);
                        tv_name.setText(DistributorName);
                        tv_phone.setText(Mobile);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                try {
                    JSONObject json_sign = new JSONObject(respanse);
                    String status = json_sign.getString("status");
                    if (status.equals("1")) {
                        progressBar.setVisibility(View.GONE);
                        String result = json_sign.getString("result");
                        JSONArray jsonArray = new JSONArray(result);
                        String data = jsonArray.get(0).toString();
                        JSONObject jsonObject_one = new JSONObject(data);
                        totalPages = jsonObject_one.getInt("TotalPages");
                        String items = jsonObject_one.getString("Items");
                        JSONArray array_one = new JSONArray(items);
                        data_length = array_one.length();
                        if (array_one != null && array_one.length() > 0) {
                            for (int i = 0; i < array_one.length(); i++) {
                                JSONObject jsonObject = array_one.getJSONObject(i);
                                String id = jsonObject.getString("ID");
                                String imgg_path = jsonObject.getString("DistributorID");
                                String name = jsonObject.getString("DistributorName");
                                String star = jsonObject.getString("Star");
                                String isEmploy = jsonObject.getString("IsEmploy");
                                String time = jsonObject.getString("CreateTime");
                                GroupSignEntity groupSignEntity = new GroupSignEntity(id, isEmploy, time, star, name, imgg_path, isPublisher);
                                groupSignEntityListViewDataAdapter.append(groupSignEntity);
                            }
                            tv_sign_num.setText("(已报名" + array_one.length() + "人)");
                        } else {
                            ll_signup.setVisibility(View.GONE);
                            rl_signup.setVisibility(View.GONE);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 3: // 取消派团
                try {
                    JSONObject jsonObject_cancle = new JSONObject(respanse);
                    String status = jsonObject_cancle.getString("status");
                    if (status.equals("1")) {
                        MyToast.show(PublishGroupCarryDetialActivity.this, "取消成功");
                        EventFactory.upDateGroupSend(0);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 4:// 删除派团
                try {
                    JSONObject jsonObject_delete = new JSONObject(respanse);
                    String status = jsonObject_delete.getString("status");
                    if (status.equals("1")) {
                        MyToast.show(PublishGroupCarryDetialActivity.this, "删除成功");
                        EventFactory.upDateGroupSend(0);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 5:// 报名派团
                try {
                    JSONObject jsonObject_delete = new JSONObject(respanse);
                    String status = jsonObject_delete.getString("status");
                    if (status.equals("1")) {
                        MyToast.show(PublishGroupCarryDetialActivity.this, "报名成功");
                        tv_button.setText("已报名");
                        tv_button.setBackgroundResource(R.drawable.bg_conner_groupgray_shape);
                        EventFactory.upDateGroupSend(0);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }


    @Override
    public void OnFamousFialCallBack(String state, String respanse) {
        MyToast.show(PublishGroupCarryDetialActivity.this, "请求失败");
    }


    // 弹出分享对话框
    private void openDialogShare() {
        performDialogAnimation(rl_dialog_share_root,
                ll_dialog_share_cotent, true);
    }

    // 关闭分享对话框
    private void closeDialogShare() {
        performDialogAnimation(rl_dialog_share_root,
                ll_dialog_share_cotent, false);

    }

    /**
     * 删除取消弹框
     *
     * @param str
     */
    public void showQuitDialog(final String str) {
        dialog_del_can = new Dialog(PublishGroupCarryDetialActivity.this, R.style.Mydialog);
        View view1 = View.inflate(PublishGroupCarryDetialActivity.this,
                R.layout.dialog_quit_show, null);
        Button sure = (Button) view1.findViewById(R.id.sure);
        Button cancle = (Button) view1.findViewById(R.id.cancle);
        TextView tv_title = (TextView) view1.findViewById(R.id.tv_title);

        if (str.equals("1")) {
            tv_title.setText("确定删除吗?");
        } else if (str.equals("2")) {
            tv_title.setText("确定取消吗?");
        }
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_del_can.dismiss();

            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_del_can.dismiss();
                if (str.equals("1")) {
                    String sign_delete = TGmd5.getMD5(distributorid + seekid);
                    detialHotCityPresenter.deletePublishGroup(distributorid, seekid, sign_delete);
                } else if (str.equals("2")) {
                    String sign_cancle = TGmd5.getMD5(distributorid + seekid);
                    detialHotCityPresenter.canclePublishGroup(distributorid, seekid, sign_cancle);
                }
            }
        });
        dialog_del_can.setContentView(view1);
        dialog_del_can.show();
    }


    /**
     * 底部报名列表点击事件回调
     *
     * @param itemData
     */
    @Override
    public void onListItemClick(GroupSignEntity itemData) {
        if (isPublisher.equals("1")) {
            Bundle bundle = new Bundle();
            bundle.putString("seekid", seekid);
            bundle.putString("applyid", itemData.getId());
            openActivity(ApplyerInfoActivity.class, bundle);
        }
    }


    /**
     * 底部，listView  加载更多操作
     *
     * @param v
     * @param event
     * @return
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                x2 = event.getX();
                y2 = event.getY();
                if (y1 - y2 > 100 && islast_one == true) {
                    if (pageIndex < totalPages) {
                        pageIndex += 1;
                        progressBar.setVisibility(View.VISIBLE);
                        String sign_ = TGmd5.getMD5(seekid + pageIndex);
                        detialHotCityPresenter.getSinUpList(seekid, pageIndex + "", sign_);
                    }
                }
                break;
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                y1 = event.getY();
                break;
        }
        return false;
    }


    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        detialHotCityPresenter.attach(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        detialHotCityPresenter.dettach();
    }
}