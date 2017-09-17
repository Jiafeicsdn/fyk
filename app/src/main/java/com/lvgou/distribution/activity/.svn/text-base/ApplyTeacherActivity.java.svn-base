package com.lvgou.distribution.activity;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.bean.ApplyTeaacherBean;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.presenter.ApplyTeacherPresenter;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.ApplyTeacherView;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.common.utils.PreferenceHelper;
import com.xdroid.common.utils.StringUtils;
import com.lvgou.distribution.utils.MyToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Snow on 2016/9/22.
 * 申请讲师
 */
public class ApplyTeacherActivity extends BaseActivity implements ApplyTeacherView {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.ll_yet_authentication)
    private LinearLayout ll_yet_authentication;
    @ViewInject(R.id.tv_authentication)
    private TextView tv_yet_authentication;
    @ViewInject(R.id.ll_apply_teacher)
    private LinearLayout ll_apply_teacher;
    @ViewInject(R.id.et_name)
    private EditText et_chat;
    @ViewInject(R.id.radioGroup)
    private RadioGroup mRadioGroup;
    @ViewInject(R.id.radio_male)
    private RadioButton radio_have;
    @ViewInject(R.id.radio_female)
    private RadioButton radio_none;
    @ViewInject(R.id.rl_guonei)
    private RelativeLayout rl_guonei;
    @ViewInject(R.id.cb_guonei)
    private CheckBox cb_guonei;
    @ViewInject(R.id.rl_lingdui)
    private RelativeLayout rl_lingdui;
    @ViewInject(R.id.cb_lingdui)
    private CheckBox cb_linggui;
    @ViewInject(R.id.rl_net)
    private RelativeLayout rl_net;
    @ViewInject(R.id.cb_net)
    private CheckBox cb_net;
    @ViewInject(R.id.rl_difang)
    private RelativeLayout rl_difang;
    @ViewInject(R.id.cb_difang)
    private CheckBox cb_difang;
    @ViewInject(R.id.rl_shizhan)
    private RelativeLayout rl_shizhan;
    @ViewInject(R.id.cb_shizhan)
    private CheckBox cb_shizhan;
    @ViewInject(R.id.et_exparence)
    private EditText et_experience;
    @ViewInject(R.id.tv_authentication)
    private TextView tv_apply_teacher;
    @ViewInject(R.id.rl_apply_teacher)
    private RelativeLayout rl_apply_teacher;
    @ViewInject(R.id.rl_authentication)
    private RelativeLayout rl_authentication;
    @ViewInject(R.id.scroll_view)
    private ScrollView scroll_view;

    private String is_experience = "1";//有

    private String is_guonei = "0";
    private String is_lingdui = "0";
    private String is_net = "0";
    private String is_difang = "0";
    private String is_shizhan = "0";


    private int attr = 0;

    private String state = "";// 是否实名认证

    private List<String> list_classes = new ArrayList<String>();

    private ApplyTeacherPresenter applyTeacherPresenter;

    private String distributorid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_teacher);
        ViewUtils.inject(this);
        tv_title.setText("申请成为讲师");
        state = PreferenceHelper.readString(ApplyTeacherActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.STATE, "0");
        distributorid = PreferenceHelper.readString(ApplyTeacherActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        applyTeacherPresenter = new ApplyTeacherPresenter(this);


        if (state.equals("5")) {
            ll_apply_teacher.setVisibility(View.VISIBLE);
            ll_yet_authentication.setVisibility(View.GONE);
            rl_apply_teacher.setVisibility(View.VISIBLE);
            rl_authentication.setVisibility(View.GONE);
        } else {
            ll_apply_teacher.setVisibility(View.GONE);
            ll_yet_authentication.setVisibility(View.VISIBLE);
            rl_apply_teacher.setVisibility(View.GONE);
            rl_authentication.setVisibility(View.VISIBLE);
        }

        et_experience.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        //改变默认的单行模式
        et_experience.setSingleLine(false);
        //水平滚动设置为False
        et_experience.setHorizontallyScrolling(false);
        et_experience.setHint("请输入您的从业经历");


        initCheckBox();
    }


    @OnClick({R.id.rl_back, R.id.tv_authentication, R.id.rl_guonei, R.id.rl_lingdui, R.id.rl_net, R.id.rl_difang, R.id.rl_shizhan, R.id.tv_apply_teacher})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_authentication:
                openActivity(AuthenticationActivity.class);
                break;
            case R.id.rl_guonei:
                if (is_guonei.equals("0")) {
                    is_guonei = "1";
                    cb_guonei.setChecked(true);
                    cb_guonei.setBackgroundResource(R.mipmap.register_user_seleced);
                    list_classes.add("国内导游基础课程");
                } else if (is_guonei.equals("1")) {
                    is_guonei = "0";
                    cb_guonei.setChecked(false);
                    cb_guonei.setBackgroundResource(R.mipmap.register_user_default);
                    if (list_classes.contains("国内导游基础课程")) {
                        list_classes.remove("国内导游基础课程");
                    }
                }
                break;
            case R.id.rl_lingdui:
                if (is_lingdui.equals("0")) {
                    is_lingdui = "1";
                    cb_linggui.setChecked(true);
                    cb_linggui.setBackgroundResource(R.mipmap.register_user_seleced);
                    list_classes.add("领队课程");
                } else if (is_lingdui.equals("1")) {
                    is_lingdui = "0";
                    cb_linggui.setChecked(true);
                    cb_linggui.setBackgroundResource(R.mipmap.register_user_default);
                    if (list_classes.contains("领队课程")) {
                        list_classes.remove("领队课程");
                    }
                }
                break;
            case R.id.rl_net:
                if (is_net.equals("0")) {
                    is_net = "1";
                    cb_net.setChecked(true);
                    cb_net.setBackgroundResource(R.mipmap.register_user_seleced);
                    list_classes.add("互联网+课程");
                } else if (is_net.equals("1")) {
                    is_net = "0";
                    cb_net.setChecked(false);
                    cb_net.setBackgroundResource(R.mipmap.register_user_default);
                    if (list_classes.contains("互联网+课程")) {
                        list_classes.remove("互联网+课程");
                    }
                }
                break;
            case R.id.rl_difang:
                if (is_difang.equals("0")) {
                    is_difang = "1";
                    cb_difang.setChecked(true);
                    cb_difang.setBackgroundResource(R.mipmap.register_user_seleced);
                    list_classes.add("地方性课程");
                } else if (is_difang.equals("1")) {
                    is_difang = "0";
                    cb_difang.setChecked(false);
                    cb_difang.setBackgroundResource(R.mipmap.register_user_default);
                    if (list_classes.contains("地方性课程")) {
                        list_classes.remove("地方性课程");
                    }
                }
                break;
            case R.id.rl_shizhan:
                if (is_shizhan.equals("0")) {
                    is_shizhan = "1";
                    cb_shizhan.setChecked(true);
                    cb_shizhan.setBackgroundResource(R.mipmap.register_user_seleced);
                    list_classes.add("实战技能课程");
                } else if (is_shizhan.equals("1")) {
                    is_shizhan = "0";
                    cb_shizhan.setChecked(false);
                    cb_shizhan.setBackgroundResource(R.mipmap.register_user_default);
                    if (list_classes.contains("实战技能课程")) {
                        list_classes.remove("实战技能课程");
                    }
                }

                break;
            case R.id.tv_apply_teacher:
                String str_chat_num = et_chat.getText().toString().trim();
                String str_experience = et_experience.getText().toString().trim();
                if (StringUtils.isEmpty(str_chat_num)) {
                    MyToast.show(ApplyTeacherActivity.this, "请输入微信号");
                    scroll_view.smoothScrollTo(10, 450);
                    return;
                }
                if (list_classes.size() == 0) {
                    MyToast.show(ApplyTeacherActivity.this, "请选择擅长课程");
                    return;
                }
                if (StringUtils.isEmpty(str_experience)) {
                    MyToast.show(ApplyTeacherActivity.this, "请输入从业经历");
                    return;
                }
                String corse = list_classes.toString().trim().substring(1, list_classes.toString().trim().length() - 1);
                String sign = TGmd5.getMD5(distributorid + str_chat_num + is_experience + corse + str_experience);
                ApplyTeaacherBean applyTeaacherBean = new ApplyTeaacherBean(distributorid, str_chat_num, is_experience, corse, str_experience, sign);
                applyTeacherPresenter.applyTeacher(applyTeaacherBean);

                break;
        }
    }

    public void initCheckBox() {
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_male:
                        is_experience = "1";
                        break;
                    case R.id.radio_female:
                        is_experience = "2";
                        break;
                }
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        applyTeacherPresenter.attach(this);
        MobclickAgent.onResume(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        applyTeacherPresenter.dettach();
        MobclickAgent.onPause(this);
    }


    @Override
    public void showProgress() {

    }

    @Override
    public void closeProgress() {
        dismissProgressDialog();
    }

    @Override
    public void excuteSuccessCallBack(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            String satus = jsonObject.getString("status");
            if (satus.equals("1")) {
                openActivity(ApplySuccessActivity.class);
                finish();
            } else {
                MyToast.show(ApplyTeacherActivity.this, jsonObject.getString("message"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void excuteFailedCallBack(String s) {
        MyToast.show(ApplyTeacherActivity.this, "请求失败");
    }
}
