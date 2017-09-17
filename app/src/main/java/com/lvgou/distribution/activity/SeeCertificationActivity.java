package com.lvgou.distribution.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.presenter.LoadDistributorExtendPresenter;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.LoadDistributorExtendView;
import com.xdroid.common.utils.PreferenceHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/4/18.
 */

public class SeeCertificationActivity extends BaseActivity implements View.OnClickListener, LoadDistributorExtendView {
    private LoadDistributorExtendPresenter loadDistributorExtendPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seecertification);
        loadDistributorExtendPresenter = new LoadDistributorExtendPresenter(this);
        initView();
        initClick();
        initDatas();
    }


    private RelativeLayout rl_back;
    //    private TextView tv_weixin;//微信
//    private TextView tv_idcard;//身份证
    private TextView tv_city;//城市
    private TextView tv_work_time;//从业时间
    private TextView tv_edu;//教育
    private TextView tv_cert_level;//证件等级
    private TextView tv_language;//语言类型
    private TextView tv_yewufanwei;//业务范围
    private TextView tv_kechengleixing;//课程类型
    private ImageView im_picture;//证件照片
    private TextView tv_sex;//性别

    private void initView() {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
//        tv_weixin = (TextView) findViewById(R.id.tv_weixin);
//        tv_idcard = (TextView) findViewById(R.id.tv_idcard);
        tv_sex = (TextView) findViewById(R.id.tv_sex);
        tv_city = (TextView) findViewById(R.id.tv_city);
        tv_work_time = (TextView) findViewById(R.id.tv_work_time);
        tv_edu = (TextView) findViewById(R.id.tv_edu);
        tv_cert_level = (TextView) findViewById(R.id.tv_cert_level);
        tv_language = (TextView) findViewById(R.id.tv_language);
        tv_yewufanwei = (TextView) findViewById(R.id.tv_yewufanwei);
        tv_kechengleixing = (TextView) findViewById(R.id.tv_kechengleixing);
        im_picture = (ImageView) findViewById(R.id.im_picture);

    }

    private void initClick() {
        rl_back.setOnClickListener(this);
    }

    private void initDatas() {
        String distributorid = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        String sign = TGmd5.getMD5(distributorid);
        showLoadingProgressDialog(this, "");
        loadDistributorExtendPresenter.loadDistributorExtend(distributorid, sign);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
        }
    }

    @Override
    public void OnLoadDistributorExtendSuccCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
        try {
            JSONObject jsb = new JSONObject(respanse);
            JSONArray jsa = new JSONArray(jsb.getString("result"));
            JSONObject jsonObject = jsa.getJSONObject(0);// 	导游数据
            JSONObject jsonObject1 = jsa.getJSONObject(1);// 	导游扩展表数据
            if (jsonObject1.get("Sex").toString().equals("1")) {
                tv_sex.setText("男");
            } else if (jsonObject1.get("Sex").toString().equals("2")) {
                tv_sex.setText("女");
            }
//            tv_weixin.setText(jsonObject1.get("WeiXin").toString().trim());
//            tv_idcard.setText(jsonObject1.get("IDCardNO").toString().trim());
            tv_city.setText(jsa.getString(2).trim());
            String workDay = jsonObject1.get("WorkDay").toString().trim();
            String[] ts = workDay.split("T");
            String[] split = ts[0].split("-");
            tv_work_time.setText(split[0] + "年" + split[1] + "月");
            // 	学历（1=研究生及以上、2=本科、3=专科、4=中专、5=高中、6=其它）
            if (jsonObject1.get("Education").toString().trim().equals("1")) {
                tv_edu.setText("研究生及以上");
            } else if (jsonObject1.get("Education").toString().trim().equals("2")) {
                tv_edu.setText("本科");
            } else if (jsonObject1.get("Education").toString().trim().equals("3")) {
                tv_edu.setText("专科");
            } else if (jsonObject1.get("Education").toString().trim().equals("4")) {
                tv_edu.setText("中专");
            } else if (jsonObject1.get("Education").toString().trim().equals("5")) {
                tv_edu.setText("高中");
            } else if (jsonObject1.get("Education").toString().trim().equals("6")) {
                tv_edu.setText("其它");
            } else {
                tv_edu.setText("");
            }
            //证件等级（1=初级、2=中级、3=高级、4=特级）
            if (jsonObject1.get("CertificateLevel").toString().trim().equals("1")) {
                tv_cert_level.setText("初级");
            } else if (jsonObject1.get("CertificateLevel").toString().trim().equals("2")) {
                tv_cert_level.setText("中级");
            } else if (jsonObject1.get("CertificateLevel").toString().trim().equals("3")) {
                tv_cert_level.setText("高级");
            } else if (jsonObject1.get("CertificateLevel").toString().trim().equals("4")) {
                tv_cert_level.setText("特级");
            } else {
                tv_cert_level.setText("");
            }
            // 	语言类型（1=中文导游、2=外语导游）
            if (jsonObject1.get("LanguageType").toString().trim().equals("1")) {
                tv_language.setText("中文导游");
            } else if (jsonObject1.get("LanguageType").toString().trim().equals("2")) {
                tv_language.setText("外语导游");
            } else {
                tv_language.setText("");
            }

            // 	业务范围（1=全陪，2=地接，4=领队（当有选择领队时，必须选择领队线路），8=景讲，16=计调，32=其它）
            int attr = Integer.parseInt(jsonObject.get("Attr").toString().trim());
            String yewuStr = "";
            if (attr > 0) {

                if ((attr & 1) == 1) {//全陪
                    yewuStr = yewuStr + "全陪/";
                }
                if ((attr & 2) == 2) {//地接
                    yewuStr = yewuStr + "地接/";
                }
                if ((attr & 4) == 4) {//领队

                    //领队线路（可多选）（位运算）：1=港澳台线路，2=日韩线路，4=东南亚线路，8=中东非线路，16=澳新线路，32=欧洲线路，64=地中海线路，128=南北美洲线路，256=极地线路
                    String lingduiStr = "";
                    int attrLine = Integer.parseInt(jsonObject.get("AttrLine").toString().trim());
                    if (attrLine > 0) {
                        if ((attrLine & 1) == 1) {//港澳台线路
                            lingduiStr = lingduiStr + "港澳台线路/";
                        }
                        if ((attrLine & 2) == 2) {//日韩线路
                            lingduiStr = lingduiStr + "日韩线路/";
                        }
                        if ((attrLine & 4) == 4) {//东南亚线路
                            lingduiStr = lingduiStr + "东南亚线路/";

                        }
                        if ((attrLine & 8) == 8) {//中东非线路
                            lingduiStr = lingduiStr + "中东非线路/";

                        }
                        if ((attrLine & 16) == 16) {//澳新线路
                            lingduiStr = lingduiStr + "澳新线路/";

                        }
                        if ((attrLine & 32) == 32) {//欧洲线路
                            lingduiStr = lingduiStr + "欧洲线路/";
                        }
                        if ((attrLine & 64) == 64) {//地中海线路
                            lingduiStr = lingduiStr + "地中海线路/";
                        }
                        if ((attrLine & 128) == 128) {//南北美洲线路
                            lingduiStr = lingduiStr + "南北美洲线路/";
                        }
                        if ((attrLine & 256) == 256) {//极地线路
                            lingduiStr = lingduiStr + "极地线路/";
                        }
                    }
                    lingduiStr = lingduiStr.substring(0, lingduiStr.length() - 1);
                    lingduiStr = "(" + lingduiStr + ")";
                    yewuStr = yewuStr + "领队" + lingduiStr + "/";
                }
                if ((attr & 8) == 8) {//景讲
                    yewuStr = yewuStr + "景讲/";
                }
                if ((attr & 16) == 16) {//计调
                    yewuStr = yewuStr + "计调/";
                }
                if ((attr & 32) == 32) {//其它
                    yewuStr = yewuStr + "其它/";
                }
                yewuStr = yewuStr.substring(0, yewuStr.length() - 1);
            }
            tv_yewufanwei.setText(yewuStr);
//课程类型（1=地方课程，2=领队课程，4=基础课程，8=实战课程，16=团型课程，32=语言课程，64=才艺课程，128=职业课程）
            int courseType = Integer.parseInt(jsonObject1.get("CourseType").toString().trim());
            String classStr = "";
            if (courseType > 0) {
                if ((courseType & 1) == 1) {//地方课程
                    classStr = classStr + "地方课程/";

                }
                if ((courseType & 2) == 2) {//领队课程
                    classStr = classStr + "领队课程/";
                }
                if ((courseType & 4) == 4) {//基础课程
                    classStr = classStr + "基础课程/";
                }
                if ((courseType & 8) == 8) {//实战课程
                    classStr = classStr + "实战课程/";
                }
                if ((courseType & 16) == 16) {//团型课程
                    classStr = classStr + "团型课程/";
                }
                if ((courseType & 32) == 32) {//语言课程
                    classStr = classStr + "语言课程/";
                }
                if ((courseType & 64) == 64) {//才艺课程
                    classStr = classStr + "才艺课程/";
                }
                if ((courseType & 128) == 128) {//职业课程
                    classStr = classStr + "职业课程/";
                }
                classStr = classStr.substring(0, classStr.length() - 1);
            }
            tv_kechengleixing.setText(classStr);
            Glide.with(SeeCertificationActivity.this).load(Url.ROOT + jsonObject.get("PicUrl").toString()).error(R.mipmap.pictures_no).into(im_picture);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void OnLoadDistributorExtendFialCallBack(String state, String respanse) {

    }

    @Override
    public void closeLoadDistributorExtendProgress() {

    }
}
