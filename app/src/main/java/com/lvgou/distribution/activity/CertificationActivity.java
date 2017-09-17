package com.lvgou.distribution.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lvgou.distribution.utils.MyToast;

import android.widget.Toast;

import com.anthonycr.grant.PermissionsManager;
import com.anthonycr.grant.PermissionsResultAction;
import com.bumptech.glide.Glide;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Constants;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.luban.Luban;
import com.lvgou.distribution.presenter.DelImgPresenter;
import com.lvgou.distribution.presenter.LoadDistributorExtendPresenter;
import com.lvgou.distribution.presenter.SaveDistributorExtendPresenter;
import com.lvgou.distribution.request.RequestTask;
import com.lvgou.distribution.utils.PermissionManager;
import com.lvgou.distribution.utils.PopWindows;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.DelImgView;
import com.lvgou.distribution.view.LoadDistributorExtendView;
import com.lvgou.distribution.view.SaveDistributorExtendView;
import com.lvgou.distribution.wheelview.OnWheelChangedListener;
import com.lvgou.distribution.wheelview.OnWheelScrollListener;
import com.lvgou.distribution.wheelview.WheelView;
import com.lvgou.distribution.wheelview.adapter.AbstractWheelTextAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xdroid.common.utils.FunctionUtils;
import com.xdroid.common.utils.PreferenceHelper;
import com.xdroid.common.utils.StringUtils;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.request.extension.XDroidRequest;
import com.xdroid.request.extension.config.DataType;
import com.xdroid.request.extension.impl.OnRequestListenerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/4/7.
 * 实名认证
 */

public class CertificationActivity extends BaseActivity implements View.OnClickListener, LoadDistributorExtendView, SaveDistributorExtendView, DelImgView {
    private LoadDistributorExtendPresenter loadDistributorExtendPresenter;
    private SaveDistributorExtendPresenter saveDistributorExtendPresenter;
    private DelImgPresenter delImgPresenter;
    private String step = "stepone";
    private WheelTextAdapter twoAdapter;
    private WheelTextAdapter oneAdapter;
    private final static int REQUEST_CODE_CAMERA = 0x44;//拍照
    private final static int REQUEST_CODE_GALLERY = 0x33;//相册
    private String imagePath = null;//当前图片路径
    private String distributorid;
    private int attr = 0;//业务范围（1=全陪，2=地接，4=领队（当有选择领队时，必须选择领队线路），8=景讲，16=计调，32=其它）
    private int attrLine = 0;//领队线路（可多选）（位运算）：1=港澳台线路，2=日韩线路，4=东南亚线路，8=中东非线路，16=澳新线路，32=欧洲线路，64=地中海线路，128=南北美洲线路，256=极地线路
    private int courseType = 0;////课程类型（1=地方课程，2=领队课程，4=基础课程，8=实战课程，16=团型课程，32=语言课程，64=才艺课程，128=职业课程）
    private int quanpeiStr = 0;
    private int dijieStr = 0;
    private int lingduiStr = 0;
    private int jingjiangStr = 0;
    private int jidiaoStr = 0;
    private int qitaStr = 0;
    private int gangaotaiStr = 0;
    private int rihanStr = 0;
    private int dongnanyaStr = 0;
    private int zhongdongfeiStr = 0;
    private int aoxinStr = 0;
    private int ouzhouStr = 0;
    private int dizhonghaiStr = 0;
    private int meizhouStr = 0;
    private int jidiStr = 0;
    private int difangStr = 0;
    private int lingduiStr1 = 0;
    private int jichuStr = 0;
    private int shizhanStr = 0;
    private int tuanxingStr = 0;
    private int yuyanStr = 0;
    private int caiyiStr = 0;
    private int zhiyeStr = 0;
    private String userstate;
    private String nowpath = "";
    private String nowaddress = "";

    /*
    distributorid 	是 	string 	导游Id
weixin 	是 	string 	微信号
idCardNo 	是 	string 	身份证号码
countryPath 	是 	string 	所在城市
workDay 	是 	string 	从业时间
education 	是 	string 	学历（1=研究生及以上、2=本科、3=专科、4=中专、5=高中、6=其它）
certificateLevel 	是 	string 	证件等级（1=初级、2=中级、3=高级、4=特级）
languageType 	是 	string 	语言类型（1=中文导游、2=外语导游）
attr 	是 	string 	业务范围（1=全陪，2=地接，4=领队（当有选择领队时，必须选择领队线路），8=景讲，16=计调，32=其它）
attrLine 	是 	string 	领队线路（可多选）（位运算）：1=港澳台线路，2=日韩线路，4=东南亚线路，8=中东非线路，16=澳新线路，32=欧洲线路，64=地中海线路，128=南北美洲线路，256=极地线路
courseType 	是 	string 	课程类型（1=地方课程，2=领队课程，4=基础课程，8=实战课程，16=团型课程，32=语言课程，64=才艺课程，128=职业课程）
picUrl 	是 	string 	资质证件照片路径
sign 	是 	string 	签名
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certification);
        loadDistributorExtendPresenter = new LoadDistributorExtendPresenter(this);
        saveDistributorExtendPresenter = new SaveDistributorExtendPresenter(this);
        delImgPresenter = new DelImgPresenter(this);
        initView();
        distributorid = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        String sign = TGmd5.getMD5(distributorid);

        String state = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.STATE, "0");
        if (!state.equals("1")) {
            showLoadingProgressDialog(this, "");
            loadDistributorExtendPresenter.loadDistributorExtend(distributorid, sign);
        }
        Constants.TOTAL_ADDRESS = "";
        Constants.COUNTRYPATH = "";
        step = "stepone";
        sl_stepone.setVisibility(View.VISIBLE);//第一步显示
        sl_steptwo.setVisibility(View.GONE);//第二步隐藏
        ll_stepthree.setVisibility(View.GONE);//第三步隐藏
        tv_title.setText("3步让我们更了解你(1)");
        initClick();
    }

    private RelativeLayout rl_back;//返回
    private TextView tv_one_next;//第一步的下一步
    private ScrollView sl_stepone;//第一个的ScrollView
    private ScrollView sl_steptwo;//第二个的ScrollView
    //    private EditText et_weixin;//微信号编辑
//    private EditText et_idcard;//身份证号编辑
    private TextView tv_city;//城市节点
    private TextView tv_work_time;//从业时间
    private TextView tv_edu;//学历
    private TextView tv_cert_level;//证件等级
    private TextView tv_language;//语言
    private TextView tv_title;//头部

    //--------------step2
    private TextView tv_dijie;//地接
    private TextView tv_dijiao;//地调
    private TextView tv_quanpei;//全陪
    private TextView tv_lingdui;//领队
    private TextView tv_jingjiang;//景讲
    private TextView tv_qita;//其它
    private LinearLayout ll_lingdui;//领队下方扩展
    private RelativeLayout rl_gangaotai;//港澳台
    private ImageView im_gangaotai;
    private RelativeLayout rl_rihan;//日韩
    private ImageView im_rihan;
    private RelativeLayout rl_dongnanya;//东南亚
    private ImageView im_dongnanya;
    private RelativeLayout rl_zhongdongfei;//中东非
    private ImageView im_zhongdongfei;
    private RelativeLayout rl_aoxin;//澳新
    private ImageView im_aoxin;
    private RelativeLayout rl_ouzhou;//欧洲
    private ImageView im_ouzhou;
    private RelativeLayout rl_dizhonghai;//地中海
    private ImageView im_dizhonghai;
    private RelativeLayout rl_meizhou;//美洲
    private ImageView im_meizhou;
    private RelativeLayout rl_jidi;//极地
    private ImageView im_jidi;
    private TextView tv_class_difang;//地方课程
    private TextView tv_class_lingdui;//领队课程
    private TextView tv_class_jichu;//基础课程
    private TextView tv_class_tuanxing;//团型课程
    private TextView tv_class_yuyan;//语言课程
    private TextView tv_class_caiyi;//才艺课程
    private TextView tv_class_shizhan;//实战课程
    private TextView tv_class_zhiye;//职业课程
    private TextView tv_two_next;//第二步的下一步
    //----------step3
    private LinearLayout ll_stepthree;//第三步
    private ImageView im_picture;//证件照
    private ImageView im_add_pic;//添加证件照
    private TextView tv_submit;//提交
    private RelativeLayout mDialogRootRelativeLayout;
    private LinearLayout mDialogCotentLinearLayout;
    private TextView tv_select_gallery;//从相册选择
    private TextView tv_select_camera;//拍照
    private TextView tv_cancel;//取消
    private TextView tv_sex;//性别
    private RelativeLayout rl_delete;//删除照片

    private ImageView rl_back_icon;

    private void initView() {
        rl_delete = (RelativeLayout) findViewById(R.id.rl_delete);
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_one_next = (TextView) findViewById(R.id.tv_one_next);
        sl_stepone = (ScrollView) findViewById(R.id.sl_stepone);
        sl_steptwo = (ScrollView) findViewById(R.id.sl_steptwo);
        tv_sex = (TextView) findViewById(R.id.tv_sex);//性别
//        et_weixin = (EditText) findViewById(R.id.et_weixin);//微信号编辑
//        et_idcard = (EditText) findViewById(R.id.et_idcard);//身份证号编辑
        tv_city = (TextView) findViewById(R.id.tv_city);//城市节点
        tv_work_time = (TextView) findViewById(R.id.tv_work_time);//从业时间
        tv_edu = (TextView) findViewById(R.id.tv_edu);//学历
        tv_cert_level = (TextView) findViewById(R.id.tv_cert_level);//证件等级
        tv_language = (TextView) findViewById(R.id.tv_language);//语言
        //-------------step2
        tv_dijie = (TextView) findViewById(R.id.tv_dijie);//地接
        tv_dijiao = (TextView) findViewById(R.id.tv_dijiao);//地调
        tv_quanpei = (TextView) findViewById(R.id.tv_quanpei);//全陪
        tv_lingdui = (TextView) findViewById(R.id.tv_lingdui);//领队
        tv_jingjiang = (TextView) findViewById(R.id.tv_jingjiang);//景讲
        tv_qita = (TextView) findViewById(R.id.tv_qita);//其它
        ll_lingdui = (LinearLayout) findViewById(R.id.ll_lingdui);//领队下方扩展
        rl_gangaotai = (RelativeLayout) findViewById(R.id.rl_gangaotai);//港澳台
        im_gangaotai = (ImageView) findViewById(R.id.im_gangaotai);
        rl_rihan = (RelativeLayout) findViewById(R.id.rl_rihan);//日韩
        im_rihan = (ImageView) findViewById(R.id.im_rihan);
        rl_dongnanya = (RelativeLayout) findViewById(R.id.rl_dongnanya);//东南亚
        im_dongnanya = (ImageView) findViewById(R.id.im_dongnanya);
        rl_zhongdongfei = (RelativeLayout) findViewById(R.id.rl_zhongdongfei);//中东非
        im_zhongdongfei = (ImageView) findViewById(R.id.im_zhongdongfei);
        rl_aoxin = (RelativeLayout) findViewById(R.id.rl_aoxin);//澳新
        im_aoxin = (ImageView) findViewById(R.id.im_aoxin);
        rl_ouzhou = (RelativeLayout) findViewById(R.id.rl_ouzhou);//欧洲
        im_ouzhou = (ImageView) findViewById(R.id.im_ouzhou);
        rl_dizhonghai = (RelativeLayout) findViewById(R.id.rl_dizhonghai);//地中海
        im_dizhonghai = (ImageView) findViewById(R.id.im_dizhonghai);
        rl_meizhou = (RelativeLayout) findViewById(R.id.rl_meizhou);//美洲
        im_meizhou = (ImageView) findViewById(R.id.im_meizhou);
        rl_jidi = (RelativeLayout) findViewById(R.id.rl_jidi);//极地
        im_jidi = (ImageView) findViewById(R.id.im_jidi);
        tv_class_difang = (TextView) findViewById(R.id.tv_class_difang);//地方课程
        tv_class_lingdui = (TextView) findViewById(R.id.tv_class_lingdui);//领队课程
        tv_class_jichu = (TextView) findViewById(R.id.tv_class_jichu);//基础课程
        tv_class_tuanxing = (TextView) findViewById(R.id.tv_class_tuanxing);//团型课程
        tv_class_yuyan = (TextView) findViewById(R.id.tv_class_yuyan);//语言课程
        tv_class_caiyi = (TextView) findViewById(R.id.tv_class_caiyi);//才艺课程
        tv_class_shizhan = (TextView) findViewById(R.id.tv_class_shizhan);//实战课程
        tv_class_zhiye = (TextView) findViewById(R.id.tv_class_zhiye);//职业课程
        tv_two_next = (TextView) findViewById(R.id.tv_two_next);//第二步的下一步
        //-----------step3
        ll_stepthree = (LinearLayout) findViewById(R.id.ll_stepthree);//第三步
        im_picture = (ImageView) findViewById(R.id.im_picture);//证件照
        im_add_pic = (ImageView) findViewById(R.id.im_add_pic);//添加证件照
        tv_submit = (TextView) findViewById(R.id.tv_submit);
        mDialogRootRelativeLayout = (RelativeLayout) findViewById(R.id.rl_dialog_ios_7_root);
        mDialogCotentLinearLayout = (LinearLayout) findViewById(R.id.ll_dialog_ios_7_cotent);
        tv_select_gallery = (TextView) findViewById(R.id.tv_select_gallery);
        tv_select_camera = (TextView) findViewById(R.id.tv_select_camera);
        tv_cancel = (TextView) findViewById(R.id.tv_cancel);

        rl_back_icon = (ImageView) findViewById(R.id.rl_back_icon);

        userstate = PreferenceHelper.readString(CertificationActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.STATE, "0");

    }

    private void initClick() {
        rl_back.setOnClickListener(this);
        tv_one_next.setOnClickListener(this);
        tv_city.setOnClickListener(this);
        tv_work_time.setOnClickListener(this);
        tv_edu.setOnClickListener(this);
        tv_cert_level.setOnClickListener(this);
        tv_language.setOnClickListener(this);
        tv_dijie.setOnClickListener(this);
        tv_dijiao.setOnClickListener(this);
        tv_quanpei.setOnClickListener(this);
        tv_lingdui.setOnClickListener(this);
        tv_jingjiang.setOnClickListener(this);
        tv_qita.setOnClickListener(this);
        rl_gangaotai.setOnClickListener(this);
        rl_rihan.setOnClickListener(this);
        rl_dongnanya.setOnClickListener(this);
        rl_zhongdongfei.setOnClickListener(this);
        rl_aoxin.setOnClickListener(this);
        rl_ouzhou.setOnClickListener(this);
        rl_dizhonghai.setOnClickListener(this);
        rl_meizhou.setOnClickListener(this);
        rl_jidi.setOnClickListener(this);
        tv_class_difang.setOnClickListener(this);
        tv_class_lingdui.setOnClickListener(this);
        tv_class_jichu.setOnClickListener(this);
        tv_class_tuanxing.setOnClickListener(this);
        tv_class_yuyan.setOnClickListener(this);
        tv_class_caiyi.setOnClickListener(this);
        tv_class_shizhan.setOnClickListener(this);
        tv_class_zhiye.setOnClickListener(this);
        tv_two_next.setOnClickListener(this);
        im_add_pic.setOnClickListener(this);
        tv_submit.setOnClickListener(this);
        tv_select_gallery.setOnClickListener(this);
        tv_select_camera.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        tv_sex.setOnClickListener(this);
        rl_delete.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Constants.TOTAL_ADDRESS.equals("")) {
            if (nowaddress.equals("")) {
                tv_city.setText("");
                tv_city.setTextColor(Color.parseColor("#cccccc"));
            } else {
                Constants.TOTAL_ADDRESS = nowaddress;
                Constants.COUNTRYPATH = nowpath;
                tv_city.setText(nowaddress);
                tv_city.setTextColor(Color.parseColor("#000000"));
            }

        } else {
            tv_city.setText(Constants.TOTAL_ADDRESS);
            tv_city.setTextColor(Color.parseColor("#000000"));
        }
    }

    private int yewufanwei = 0;//用来限定只能选择业务范围三个
    private int zuiguanzhu = 0;//用来限定只能选择最关注的的课程三个
    private boolean tv_dijiestate = false;
    private boolean tv_dijiaostate = false;
    private boolean tv_quanpeistate = false;
    private boolean tv_lingduistate = false;
    private boolean tv_jingjiangstate = false;
    private boolean tv_qitastate = false;
    private boolean rl_gangaotaistate = false;
    private boolean rl_rihanstate = false;
    private boolean rl_dongnanyastate = false;
    private boolean rl_zhongdongfeistate = false;
    private boolean rl_aoxinstate = false;
    private boolean rl_ouzhoustate = false;
    private boolean rl_dizhonghaistate = false;
    private boolean rl_meizhoustate = false;
    private boolean rl_jidistate = false;
    private boolean tv_class_difangstate = false;
    private boolean tv_class_lingduistate = false;
    private boolean tv_class_jichustate = false;
    private boolean tv_class_tuanxingstate = false;
    private boolean tv_class_yuyanstate = false;
    private boolean tv_class_caiyistate = false;
    private boolean tv_class_shizhanstate = false;
    private boolean tv_class_zhiyestate = false;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                if (step.equals("stepone")) {
                    //如果在第一步页面就退出认证
                    finish();
                } else if (step.equals("steptwo")) {
                    //如果在第二步页面就返回到第一步去
                    sl_stepone.setVisibility(View.VISIBLE);//第一步显示
                    sl_steptwo.setVisibility(View.GONE);//第二步隐藏
                    ll_stepthree.setVisibility(View.GONE);//第三步隐藏
                    tv_title.setText("3步让我们更了解你(1)");
                    step = "stepone";
                    rl_back_icon.setImageResource(R.mipmap.circle_black_x);

                } else if (step.equals("stepthree")) {
                    //如果在第三步就返回第二步去
                    sl_stepone.setVisibility(View.GONE);//第一步隐藏
                    sl_steptwo.setVisibility(View.VISIBLE);//第二步显示
                    ll_stepthree.setVisibility(View.GONE);//第三步隐藏
                    tv_title.setText("3步让我们更了解你(2)");
                    step = "steptwo";
                    rl_back_icon.setImageResource(R.mipmap.rl_back);
                }

                break;
            case R.id.tv_one_next://第一步的下一步
               /* if (et_weixin.getText().toString().trim().equals("")) {
                    MyToast.makeText(this, "请填写您的微信号！", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (et_idcard.getText().toString().trim().equals("")) {
                    MyToast.makeText(this, "请填写您的身份证号！", Toast.LENGTH_SHORT).show();
                    break;
                }*/
                if (tv_sex.getText().toString().trim().equals("")) {
                    MyToast.makeText(this, "请选择性别！", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (tv_city.getText().toString().trim().equals("")) {
                    MyToast.makeText(this, "请选择所在城市！", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (tv_work_time.getText().toString().trim().equals("")) {
                    MyToast.makeText(this, "请选择您的从业时间！", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (tv_edu.getText().toString().trim().equals("")) {
                    MyToast.makeText(this, "请选择您的学历！", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (tv_cert_level.getText().toString().trim().equals("")) {
                    MyToast.makeText(this, "请选择您的证件等级！", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (tv_language.getText().toString().trim().equals("")) {
                    MyToast.makeText(this, "请选择您的语言状态！", Toast.LENGTH_SHORT).show();
                    break;
                }
                sl_stepone.setVisibility(View.GONE);//第一步隐藏
                sl_steptwo.setVisibility(View.VISIBLE);//第二步显示
                ll_stepthree.setVisibility(View.GONE);//第三步隐藏
                tv_title.setText("3步让我们更了解你(2)");
                step = "steptwo";
                rl_back_icon.setImageResource(R.mipmap.rl_back);
                break;
            case R.id.tv_city://选择城市节点
                nowpath = Constants.COUNTRYPATH;
                nowaddress = Constants.TOTAL_ADDRESS;
                Intent intent = new Intent(CertificationActivity.this, SelectProvinceActivity.class);
                intent.putExtra("nodistrict", 1);
                startActivity(intent);
//                openActivity(SelectProvinceActivity.class);
                break;
            case R.id.tv_work_time://从业时间选择
                yearData.clear();
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR) + 1; //获取当前年份
                int mimyear = 1960;
                for (int i = mimyear; i < mYear; i++) {
                    yearData.add("  " + i + "年");
                }
                selecteTime();
                break;
            case R.id.tv_edu://学历选择
                selectEdu();
                break;
            case R.id.tv_cert_level://证件等级选择
                selecteLevel();
                break;
            case R.id.tv_language://所属语言选择
                selecteLanguage();
                break;
            case R.id.tv_dijie://地接

                if (tv_dijiestate) {
                    //如果已经选择了
                    yewufanwei--;
                    tv_dijie.setTextColor(Color.parseColor("#bababa"));
                    tv_dijie.setBackgroundResource(R.drawable.bg_text_normal);
                    dijieStr = 0;
                } else {
                    if (yewufanwei == 3) {
                        MyToast.makeText(this, "最多只能选三个", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    //如果之前没选择
                    yewufanwei++;
                    tv_dijie.setTextColor(Color.parseColor("#d5aa5f"));
                    tv_dijie.setBackgroundResource(R.drawable.bg_text_cer_selecter);
                    dijieStr = 2;
                }
                tv_dijiestate = !tv_dijiestate;

                break;
            case R.id.tv_dijiao://计调
                if (tv_dijiaostate) {
                    //如果已经选择了
                    yewufanwei--;
                    tv_dijiao.setTextColor(Color.parseColor("#bababa"));
                    tv_dijiao.setBackgroundResource(R.drawable.bg_text_normal);
                    jidiaoStr = 0;
                } else {
                    if (yewufanwei == 3) {
                        MyToast.makeText(this, "最多只能选三个", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    //如果之前没选择
                    yewufanwei++;
                    tv_dijiao.setTextColor(Color.parseColor("#d5aa5f"));
                    tv_dijiao.setBackgroundResource(R.drawable.bg_text_cer_selecter);
                    jidiaoStr = 16;
                }
                tv_dijiaostate = !tv_dijiaostate;
                break;
            case R.id.tv_quanpei://全陪
                if (tv_quanpeistate) {
                    //如果已经选择了
                    yewufanwei--;
                    tv_quanpei.setTextColor(Color.parseColor("#bababa"));
                    tv_quanpei.setBackgroundResource(R.drawable.bg_text_normal);
                    quanpeiStr = 0;
                } else {
                    if (yewufanwei == 3) {
                        MyToast.makeText(this, "最多只能选三个", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    //如果之前没选择
                    yewufanwei++;
                    tv_quanpei.setTextColor(Color.parseColor("#d5aa5f"));
                    tv_quanpei.setBackgroundResource(R.drawable.bg_text_cer_selecter);
                    quanpeiStr = 1;
                }
                tv_quanpeistate = !tv_quanpeistate;
                break;
            case R.id.tv_lingdui://领队
                if (tv_lingduistate) {
                    //如果已经选择了
                    yewufanwei--;
                    tv_lingdui.setTextColor(Color.parseColor("#bababa"));
                    tv_lingdui.setBackgroundResource(R.drawable.bg_text_normal);
                    ll_lingdui.setVisibility(View.GONE);
                    lingduiStr = 0;
                    rl_gangaotaistate = false;
                    im_gangaotai.setBackgroundResource(R.mipmap.checkbox_normal_icon);
                    rl_rihanstate = false;
                    im_rihan.setBackgroundResource(R.mipmap.checkbox_normal_icon);
                    rl_dongnanyastate = false;
                    im_dongnanya.setBackgroundResource(R.mipmap.checkbox_normal_icon);
                    rl_zhongdongfeistate = false;
                    im_zhongdongfei.setBackgroundResource(R.mipmap.checkbox_normal_icon);
                    rl_aoxinstate = false;
                    im_aoxin.setBackgroundResource(R.mipmap.checkbox_normal_icon);
                    rl_ouzhoustate = false;
                    im_ouzhou.setBackgroundResource(R.mipmap.checkbox_normal_icon);
                    rl_dizhonghaistate = false;
                    im_dizhonghai.setBackgroundResource(R.mipmap.checkbox_normal_icon);
                    rl_meizhoustate = false;
                    im_meizhou.setBackgroundResource(R.mipmap.checkbox_normal_icon);
                    rl_jidistate = false;
                    im_jidi.setBackgroundResource(R.mipmap.checkbox_normal_icon);
                } else {
                    if (yewufanwei == 3) {
                        MyToast.makeText(this, "最多只能选三个", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    //如果之前没选择
                    yewufanwei++;
                    tv_lingdui.setTextColor(Color.parseColor("#d5aa5f"));
                    tv_lingdui.setBackgroundResource(R.drawable.bg_text_cer_selecter);
                    ll_lingdui.setVisibility(View.VISIBLE);
                    lingduiStr = 4;
                }
                tv_lingduistate = !tv_lingduistate;
                break;
            case R.id.tv_jingjiang://景讲
                if (tv_jingjiangstate) {
                    //如果已经选择了
                    yewufanwei--;
                    tv_jingjiang.setTextColor(Color.parseColor("#bababa"));
                    tv_jingjiang.setBackgroundResource(R.drawable.bg_text_normal);
                    jingjiangStr = 0;
                } else {
                    if (yewufanwei == 3) {
                        MyToast.makeText(this, "最多只能选三个", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    //如果之前没选择
                    yewufanwei++;
                    tv_jingjiang.setTextColor(Color.parseColor("#d5aa5f"));
                    tv_jingjiang.setBackgroundResource(R.drawable.bg_text_cer_selecter);
                    jingjiangStr = 8;
                }
                tv_jingjiangstate = !tv_jingjiangstate;
                break;
            case R.id.tv_qita://其它

                if (tv_qitastate) {
                    //如果已经选择了
                    yewufanwei--;
                    tv_qita.setTextColor(Color.parseColor("#bababa"));
                    tv_qita.setBackgroundResource(R.drawable.bg_text_normal);
                    qitaStr = 0;
                } else {
                    if (yewufanwei == 3) {
                        MyToast.makeText(this, "最多只能选三个", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    //如果之前没选择
                    yewufanwei++;
                    tv_qita.setTextColor(Color.parseColor("#d5aa5f"));
                    tv_qita.setBackgroundResource(R.drawable.bg_text_cer_selecter);
                    qitaStr = 32;
                }
                tv_qitastate = !tv_qitastate;
                break;
            case R.id.rl_gangaotai://港澳台
                if (rl_gangaotaistate) {
                    //如果之前选中
                    im_gangaotai.setBackgroundResource(R.mipmap.checkbox_normal_icon);
                    gangaotaiStr = 0;
                } else {
                    //之前没选中
                    im_gangaotai.setBackgroundResource(R.mipmap.checkbox_check_icon);
                    gangaotaiStr = 1;
                }
                rl_gangaotaistate = !rl_gangaotaistate;
                break;
            case R.id.rl_rihan://日韩
                if (rl_rihanstate) {
                    //如果之前选中
                    im_rihan.setBackgroundResource(R.mipmap.checkbox_normal_icon);
                    rihanStr = 0;
                } else {
                    //之前没选中
                    im_rihan.setBackgroundResource(R.mipmap.checkbox_check_icon);
                    rihanStr = 2;
                }
                rl_rihanstate = !rl_rihanstate;
                break;
            case R.id.rl_dongnanya://东南亚
                if (rl_dongnanyastate) {
                    //如果之前选中
                    im_dongnanya.setBackgroundResource(R.mipmap.checkbox_normal_icon);
                    dongnanyaStr = 0;
                } else {
                    //之前没选中
                    im_dongnanya.setBackgroundResource(R.mipmap.checkbox_check_icon);
                    dongnanyaStr = 4;
                }
                rl_dongnanyastate = !rl_dongnanyastate;
                break;
            case R.id.rl_zhongdongfei://中东非
                if (rl_zhongdongfeistate) {
                    //如果之前选中
                    im_zhongdongfei.setBackgroundResource(R.mipmap.checkbox_normal_icon);
                    zhongdongfeiStr = 0;
                } else {
                    //之前没选中
                    im_zhongdongfei.setBackgroundResource(R.mipmap.checkbox_check_icon);
                    zhongdongfeiStr = 8;
                }
                rl_zhongdongfeistate = !rl_zhongdongfeistate;
                break;
            case R.id.rl_aoxin://澳新
                if (rl_aoxinstate) {
                    //如果之前选中
                    im_aoxin.setBackgroundResource(R.mipmap.checkbox_normal_icon);
                    aoxinStr = 0;
                } else {
                    //之前没选中
                    im_aoxin.setBackgroundResource(R.mipmap.checkbox_check_icon);
                    aoxinStr = 16;
                }
                rl_aoxinstate = !rl_aoxinstate;
                break;
            case R.id.rl_ouzhou://欧洲
                if (rl_ouzhoustate) {
                    //如果之前选中
                    im_ouzhou.setBackgroundResource(R.mipmap.checkbox_normal_icon);
                    ouzhouStr = 0;
                } else {
                    //之前没选中
                    im_ouzhou.setBackgroundResource(R.mipmap.checkbox_check_icon);
                    ouzhouStr = 32;
                }
                rl_ouzhoustate = !rl_ouzhoustate;
                break;
            case R.id.rl_dizhonghai://地中海
                if (rl_dizhonghaistate) {
                    //如果之前选中
                    im_dizhonghai.setBackgroundResource(R.mipmap.checkbox_normal_icon);
                    dizhonghaiStr = 0;
                } else {
                    //之前没选中
                    im_dizhonghai.setBackgroundResource(R.mipmap.checkbox_check_icon);
                    dizhonghaiStr = 64;
                }
                rl_dizhonghaistate = !rl_dizhonghaistate;
                break;
            case R.id.rl_meizhou://美洲
                if (rl_meizhoustate) {
                    //如果之前选中
                    im_meizhou.setBackgroundResource(R.mipmap.checkbox_normal_icon);
                    meizhouStr = 0;
                } else {
                    //之前没选中
                    im_meizhou.setBackgroundResource(R.mipmap.checkbox_check_icon);
                    meizhouStr = 128;
                }
                rl_meizhoustate = !rl_meizhoustate;
                break;
            case R.id.rl_jidi://极地
                if (rl_jidistate) {
                    //如果之前选中
                    im_jidi.setBackgroundResource(R.mipmap.checkbox_normal_icon);
                    jidiStr = 0;
                } else {
                    //之前没选中
                    im_jidi.setBackgroundResource(R.mipmap.checkbox_check_icon);
                    jidiStr = 256;
                }
                rl_jidistate = !rl_jidistate;
                break;
            case R.id.tv_class_difang://地方课程
                if (tv_class_difangstate) {
                    //如果已经选择了
                    zuiguanzhu--;
                    tv_class_difang.setTextColor(Color.parseColor("#bababa"));
                    tv_class_difang.setBackgroundResource(R.drawable.bg_text_normal);
                    difangStr = 0;
                } else {
                    if (zuiguanzhu == 3) {
                        MyToast.makeText(this, "最多只能选三个", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    //如果之前没选择
                    zuiguanzhu++;
                    tv_class_difang.setTextColor(Color.parseColor("#d5aa5f"));
                    tv_class_difang.setBackgroundResource(R.drawable.bg_text_cer_selecter);
                    difangStr = 1;
                }
                tv_class_difangstate = !tv_class_difangstate;
                break;
            case R.id.tv_class_lingdui://领队课程
                if (tv_class_lingduistate) {
                    //如果已经选择了
                    zuiguanzhu--;
                    tv_class_lingdui.setTextColor(Color.parseColor("#bababa"));
                    tv_class_lingdui.setBackgroundResource(R.drawable.bg_text_normal);
                    lingduiStr1 = 0;
                } else {
                    if (zuiguanzhu == 3) {
                        MyToast.makeText(this, "最多只能选三个", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    //如果之前没选择
                    zuiguanzhu++;
                    tv_class_lingdui.setTextColor(Color.parseColor("#d5aa5f"));
                    tv_class_lingdui.setBackgroundResource(R.drawable.bg_text_cer_selecter);
                    lingduiStr1 = 2;
                }
                tv_class_lingduistate = !tv_class_lingduistate;
                break;
            case R.id.tv_class_jichu://基础课程
                if (tv_class_jichustate) {
                    //如果已经选择了
                    zuiguanzhu--;
                    tv_class_jichu.setTextColor(Color.parseColor("#bababa"));
                    tv_class_jichu.setBackgroundResource(R.drawable.bg_text_normal);
                    jichuStr = 0;
                } else {
                    if (zuiguanzhu == 3) {
                        MyToast.makeText(this, "最多只能选三个", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    //如果之前没选择
                    zuiguanzhu++;
                    tv_class_jichu.setTextColor(Color.parseColor("#d5aa5f"));
                    tv_class_jichu.setBackgroundResource(R.drawable.bg_text_cer_selecter);
                    jichuStr = 4;
                }
                tv_class_jichustate = !tv_class_jichustate;
                break;
            case R.id.tv_class_tuanxing://团型课程
                if (tv_class_tuanxingstate) {
                    //如果已经选择了
                    zuiguanzhu--;
                    tv_class_tuanxing.setTextColor(Color.parseColor("#bababa"));
                    tv_class_tuanxing.setBackgroundResource(R.drawable.bg_text_normal);
                    tuanxingStr = 0;
                } else {
                    if (zuiguanzhu == 3) {
                        MyToast.makeText(this, "最多只能选三个", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    //如果之前没选择
                    zuiguanzhu++;
                    tv_class_tuanxing.setTextColor(Color.parseColor("#d5aa5f"));
                    tv_class_tuanxing.setBackgroundResource(R.drawable.bg_text_cer_selecter);
                    tuanxingStr = 16;
                }
                tv_class_tuanxingstate = !tv_class_tuanxingstate;
                break;
            case R.id.tv_class_yuyan://语言课程
                if (tv_class_yuyanstate) {
                    //如果已经选择了
                    zuiguanzhu--;
                    tv_class_yuyan.setTextColor(Color.parseColor("#bababa"));
                    tv_class_yuyan.setBackgroundResource(R.drawable.bg_text_normal);
                    yuyanStr = 0;
                } else {
                    if (zuiguanzhu == 3) {
                        MyToast.makeText(this, "最多只能选三个", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    //如果之前没选择
                    zuiguanzhu++;
                    tv_class_yuyan.setTextColor(Color.parseColor("#d5aa5f"));
                    tv_class_yuyan.setBackgroundResource(R.drawable.bg_text_cer_selecter);
                    yuyanStr = 32;
                }
                tv_class_yuyanstate = !tv_class_yuyanstate;
                break;
            case R.id.tv_class_caiyi://才艺课程
                if (tv_class_caiyistate) {
                    //如果已经选择了
                    zuiguanzhu--;
                    tv_class_caiyi.setTextColor(Color.parseColor("#bababa"));
                    tv_class_caiyi.setBackgroundResource(R.drawable.bg_text_normal);
                    caiyiStr = 0;
                } else {
                    if (zuiguanzhu == 3) {
                        MyToast.makeText(this, "最多只能选三个", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    //如果之前没选择
                    zuiguanzhu++;
                    tv_class_caiyi.setTextColor(Color.parseColor("#d5aa5f"));
                    tv_class_caiyi.setBackgroundResource(R.drawable.bg_text_cer_selecter);
                    caiyiStr = 64;
                }
                tv_class_caiyistate = !tv_class_caiyistate;
                break;
            case R.id.tv_class_shizhan://实战课程
                if (tv_class_shizhanstate) {
                    //如果已经选择了
                    zuiguanzhu--;
                    tv_class_shizhan.setTextColor(Color.parseColor("#bababa"));
                    tv_class_shizhan.setBackgroundResource(R.drawable.bg_text_normal);
                    shizhanStr = 0;
                } else {
                    if (zuiguanzhu == 3) {
                        MyToast.makeText(this, "最多只能选三个", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    //如果之前没选择
                    zuiguanzhu++;
                    tv_class_shizhan.setTextColor(Color.parseColor("#d5aa5f"));
                    tv_class_shizhan.setBackgroundResource(R.drawable.bg_text_cer_selecter);
                    shizhanStr = 8;
                }
                tv_class_shizhanstate = !tv_class_shizhanstate;
                break;
            case R.id.tv_class_zhiye://职业课程
                if (tv_class_zhiyestate) {
                    //如果已经选择了
                    zuiguanzhu--;
                    tv_class_zhiye.setTextColor(Color.parseColor("#bababa"));
                    tv_class_zhiye.setBackgroundResource(R.drawable.bg_text_normal);
                    zhiyeStr = 0;
                } else {
                    if (zuiguanzhu == 3) {
                        MyToast.makeText(this, "最多只能选三个", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    //如果之前没选择
                    zuiguanzhu++;
                    tv_class_zhiye.setTextColor(Color.parseColor("#d5aa5f"));
                    tv_class_zhiye.setBackgroundResource(R.drawable.bg_text_cer_selecter);
                    zhiyeStr = 128;
                }
                tv_class_zhiyestate = !tv_class_zhiyestate;
                break;
            case R.id.tv_two_next://第二步的下一步
                if (yewufanwei == 0) {
                    MyToast.makeText(this, "至少选择一个业务范围！", Toast.LENGTH_SHORT).show();
                    break;
                }
                int lingduiluxian = gangaotaiStr + rihanStr + dongnanyaStr + zhongdongfeiStr + aoxinStr + ouzhouStr + dizhonghaiStr + meizhouStr + jidiStr;
                if (lingduiStr != 0 && lingduiluxian == 0) {
                    MyToast.makeText(this, "至少选择一个领队路线！", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (zuiguanzhu == 0) {
                    MyToast.makeText(this, "至少选择一个关注课程！", Toast.LENGTH_SHORT).show();
                    break;
                }
                sl_stepone.setVisibility(View.GONE);//第一步隐藏
                sl_steptwo.setVisibility(View.GONE);//第二步显示
                ll_stepthree.setVisibility(View.VISIBLE);//第三步显示
                tv_title.setText("3步让我们更了解你(3)");
                step = "stepthree";
                rl_back_icon.setImageResource(R.mipmap.rl_back);
                break;
            case R.id.im_add_pic://添加证件照
                openDialog();
                break;
            case R.id.tv_submit://提交
                if (picurl != null && picurl.equals("")) {
                    MyToast.makeText(this, "请上传证件照片！", Toast.LENGTH_SHORT).show();
                    break;
                }
                String sex = tv_sex.getText().toString().trim();
                int sexgander = 1;
                if (sex.equals("男")) {
                    sexgander = 1;
                } else if (sex.equals("女")) {
                    sexgander = 2;
                }
//                String weixinStr = et_weixin.getText().toString().trim();
//                String idcardStr = et_idcard.getText().toString().trim();
                String contryPath = Constants.COUNTRYPATH;
                String workTimeStr = tv_work_time.getText().toString().trim();
                String replaceTime = workTimeStr.replace("年", "-").replace("月", "-01");
                String mlan = "";
                if (tv_language.getText().toString().trim().equals("中文导游")) {
                    mlan = "1";
                } else if (tv_language.getText().toString().trim().equals("外语导游")) {
                    mlan = "2";
                }
                String myewu = (dijieStr + jidiaoStr + quanpeiStr + lingduiStr + jingjiangStr + qitaStr) + "";
                String mlingduiluxian = (gangaotaiStr + rihanStr + dongnanyaStr + zhongdongfeiStr + aoxinStr + ouzhouStr + dizhonghaiStr + meizhouStr + jidiStr) + "";
                String mcourseType = (difangStr + lingduiStr1 + jichuStr + tuanxingStr + yuyanStr + caiyiStr + shizhanStr + zhiyeStr) + "";
                Log.e("kjhsadfkjhs", "***********" + mlevel);
                String signto = "" + distributorid + sexgander + contryPath + replaceTime + meduStr + mlevel + mlan + myewu + mlingduiluxian + mcourseType + picurl;
                String sign = TGmd5.getMD5(signto);
                Log.e("kjhasjkhs", "***" + signto);
                Log.e("kjhasjkhs", "===" + sign);
                showLoadingProgressDialog(this, "");
                /*
                distributorid 	是 	string 	导游Id
weixin 	是 	string 	微信号
idCardNo 	是 	string 	身份证号码
countryPath 	是 	string 	所在城市
workDay 	是 	string 	从业时间
education 	是 	string 	学历（1=研究生及以上、2=本科、3=专科、4=中专、5=高中、6=其它）
certificateLevel 	是 	string 	证件等级（1=初级、2=中级、3=高级、4=特级）
languageType 	是 	string 	语言类型（1=中文导游、2=外语导游）
attr 	是 	string 	业务范围（1=全陪，2=地接，4=领队（当有选择领队时，必须选择领队线路），8=景讲，16=计调，32=其它）
attrLine 	是 	string 	领队线路（可多选）（位运算）：1=港澳台线路，2=日韩线路，4=东南亚线路，8=中东非线路，16=澳新线路，32=欧洲线路，64=地中海线路，128=南北美洲线路，256=极地线路
courseType 	是 	string 	课程类型（1=地方课程，2=领队课程，4=基础课程，8=实战课程，16=团型课程，32=语言课程，64=才艺课程，128=职业课程）
picUrl 	是 	string 	资质证件照片路径
sign 	是 	string 	签名saveDistributorExtend
                 */
                saveDistributorExtendPresenter.saveDistributorExtend(distributorid, sexgander, contryPath, replaceTime, meduStr, mlevel, mlan, myewu, mlingduiluxian, mcourseType, picurl, sign);
                break;
            case R.id.tv_select_gallery://从相册选择
                closeDialog();
                // 权限管理工具类
                permissionManager = new PermissionManager(new PermissionManager.PermissionListener() {
                    @Override
                    public void permissionGranted(int requestCode) {
                        if (requestCode == 99) {
                            PhotoPickerIntent intent = new PhotoPickerIntent(CertificationActivity.this);
                            intent.setPhotoCount(1);
                            intent.setShowCamera(true);
                            intent.setShowGif(true);
                            startActivityForResult(intent, REQUEST_CODE_GALLERY);
                        }
                    }
                });
                //申请读权限，和照相机权限
                permissionManager.checkPermission(99, CertificationActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA);
                break;
            case R.id.tv_select_camera://拍照
                PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(this,
                        new String[]{Manifest.permission.CAMERA}, new PermissionsResultAction() {
                            @Override
                            public void onGranted() {
                                imagePath = FunctionUtils.chooseImageFromCamera(CertificationActivity.this, REQUEST_CODE_CAMERA, "cameraImage");
                            }

                            @Override
                            public void onDenied(String permission) {
                            }
                        }
                );
                closeDialog();
                break;
            case R.id.tv_cancel://取消
                closeDialog();
                break;
            case R.id.tv_sex://选择性别
                selectSex();
//                selecteLanguage();
                break;
            case R.id.rl_delete://删除照片
                String sign2 = TGmd5.getMD5(picurl);
                showLoadingProgressDialog(this, "");
                delImgPresenter.delImg(picurl, sign2);
                break;

        }
    }

    private PermissionManager permissionManager;

    //添加照片
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void openDialog() {
        performDialogAnimation(mDialogRootRelativeLayout,
                mDialogCotentLinearLayout, true);
    }

    private void closeDialog() {
        performDialogAnimation(mDialogRootRelativeLayout,
                mDialogCotentLinearLayout, false);
    }

    private ArrayList<String> data_list = new ArrayList<String>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == -1) {
            switch (requestCode) {
                case REQUEST_CODE_CAMERA://拍照的
                    if (!StringUtils.isEmpty(imagePath)) {
                        showLoadingProgressDialog(CertificationActivity.this, "");
//                        compressWithRx(new File(imagePath));

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                compressWithRx(new File(imagePath));
                            }
                        }).start();
                    }
                    break;
                case REQUEST_CODE_GALLERY://相册的
                    if (data != null) {
                        data_list = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
                        imagePath = data_list.get(0);
                        if (!StringUtils.isEmpty(imagePath)) {
                            showLoadingProgressDialog(CertificationActivity.this, "");
//                            compressWithRx(new File(imagePath));
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    compressWithRx(new File(imagePath));
                                }
                            }).start();
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 压缩单张图片 RxJava 方式
     */
    private void compressWithRx(final File fileoo) {
        if (fileoo.exists()) {
            Luban.get(this)
                    .load(fileoo)
                    .putGear(Luban.THIRD_GEAR)
                    .asObservable()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnError(new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    })
                    .onErrorResumeNext(new Func1<Throwable, Observable<? extends File>>() {
                        @Override
                        public Observable<? extends File> call(Throwable throwable) {
                            return Observable.empty();
                        }
                    })
                    .subscribe(new Action1<File>() {
                        @Override
                        public void call(File file) {
                            if (fileoo.length() >= 300) {
                                String sign = TGmd5.getMD5("");
                                doUpload(file, sign);
                            } else {
                                String sign = TGmd5.getMD5("");
                                doUpload(fileoo, sign);
                            }

                        }
                    });
        } else {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    MyToast.show(CertificationActivity.this, "蜂优客没有系统权限，文件目录不存在");
                }
            });
        }
    }

    /**
     * 上传图片
     */
    public void doUpload(File file, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("picurl", file);
        maps.put("sign", sign);
        RequestTask.getInstance().doUploadIDCardImg(CertificationActivity.this, maps, new OnUploadRequestListener());
    }

    private String picurl = "";//活动图片路径
    private String meduStr = "";
    private String mlevel = "";

    @Override
    public void OnLoadDistributorExtendSuccCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
        try {
            JSONObject jsb = new JSONObject(respanse);
            JSONArray jsa = new JSONArray(jsb.getString("result"));
            JSONObject jsonObject = jsa.getJSONObject(0);// 	导游数据
            JSONObject jsonObject1 = jsa.getJSONObject(1);// 	导游扩展表数据
            /*if (null != jsonObject1.get("sex")) {
                if (jsonObject1.get("sex").toString().equals("1")) {
                    tv_sex.setText("男");
                } else if (jsonObject1.get("sex").toString().equals("2")) {
                    tv_sex.setText("女");
                }
            } else {
                tv_sex.setText("男");
            }*/
            if (jsonObject1.get("Sex").toString().equals("1")) {
                tv_sex.setText("男");
            } else if (jsonObject1.get("Sex").toString().equals("2")) {
                tv_sex.setText("女");
            }
            /*if (null != jsonObject1.get("WeiXin")) {
                et_weixin.setText(jsonObject1.get("WeiXin").toString().trim());
            } else {
                et_weixin.setText("");
            }
            if (null != jsonObject1.get("IDCardNO")) {
                et_idcard.setText(jsonObject1.get("IDCardNO").toString().trim());
            } else {
                et_idcard.setText("");
            }*/
            if (jsa.getString(2).trim().equals("")) {
                tv_city.setText("");
                tv_city.setTextColor(Color.parseColor("#cccccc"));
            } else {
                tv_city.setText(jsa.getString(2).trim());
                tv_city.setTextColor(Color.parseColor("#000000"));
                Constants.COUNTRYPATH = jsonObject.get("CountryPath").toString();
            }

            String workDay = jsonObject1.get("WorkDay").toString().trim();
            String[] ts = workDay.split("T");
            String[] split = ts[0].split("-");
            tv_work_time.setText(split[0] + "年" + split[1] + "月");
            // 	学历（1=研究生及以上、2=本科、3=专科、4=中专、5=高中、6=其它）
            if (jsonObject1.get("Education").toString().trim().equals("1")) {
                tv_edu.setText("研究生及以上");
                meduStr = "1";
            } else if (jsonObject1.get("Education").toString().trim().equals("2")) {
                tv_edu.setText("本科");
                meduStr = "2";
            } else if (jsonObject1.get("Education").toString().trim().equals("3")) {
                tv_edu.setText("专科");
                meduStr = "3";
            } else if (jsonObject1.get("Education").toString().trim().equals("4")) {
                tv_edu.setText("中专");
                meduStr = "4";
            } else if (jsonObject1.get("Education").toString().trim().equals("5")) {
                tv_edu.setText("高中");
                meduStr = "5";
            } else if (jsonObject1.get("Education").toString().trim().equals("6")) {
                tv_edu.setText("其它");
                meduStr = "6";
            } else {
                tv_edu.setText("");
            }
            //证件等级（1=初级、2=中级、3=高级、4=特级）
            if (jsonObject1.get("CertificateLevel").toString().trim().equals("1")) {
                tv_cert_level.setText("初级");
                mlevel = "1";
            } else if (jsonObject1.get("CertificateLevel").toString().trim().equals("2")) {
                tv_cert_level.setText("中级");
                mlevel = "2";
            } else if (jsonObject1.get("CertificateLevel").toString().trim().equals("3")) {
                tv_cert_level.setText("高级");
                mlevel = "3";
            } else if (jsonObject1.get("CertificateLevel").toString().trim().equals("4")) {
                tv_cert_level.setText("特级");
                mlevel = "4";
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
            attr = Integer.parseInt(jsonObject.get("Attr").toString().trim());
            if (attr > 0) {

                if ((attr & 1) == 1) {//全陪
                    quanpeiStr = 1;
                    tv_quanpei.setTextColor(Color.parseColor("#d5aa5f"));
                    tv_quanpei.setBackgroundResource(R.drawable.bg_text_cer_selecter);
                    yewufanwei++;
                    tv_quanpeistate = true;
                }
                if ((attr & 2) == 2) {//地接
                    tv_dijiestate = true;
                    dijieStr = 2;
                    tv_dijie.setTextColor(Color.parseColor("#d5aa5f"));
                    tv_dijie.setBackgroundResource(R.drawable.bg_text_cer_selecter);
                    yewufanwei++;
                }
                if ((attr & 4) == 4) {//领队
                    tv_lingduistate = true;
                    lingduiStr = 4;
                    tv_lingdui.setTextColor(Color.parseColor("#d5aa5f"));
                    tv_lingdui.setBackgroundResource(R.drawable.bg_text_cer_selecter);
                    yewufanwei++;
                    ll_lingdui.setVisibility(View.VISIBLE);
                }
                if ((attr & 8) == 8) {//景讲
                    tv_jingjiangstate = true;
                    jingjiangStr = 8;
                    tv_jingjiang.setTextColor(Color.parseColor("#d5aa5f"));
                    tv_jingjiang.setBackgroundResource(R.drawable.bg_text_cer_selecter);
                    yewufanwei++;
                }
                if ((attr & 16) == 16) {//计调
                    tv_dijiaostate = true;
                    jidiaoStr = 16;
                    tv_dijiao.setTextColor(Color.parseColor("#d5aa5f"));
                    tv_dijiao.setBackgroundResource(R.drawable.bg_text_cer_selecter);
                    yewufanwei++;
                }
                if ((attr & 32) == 32) {//其它
                    tv_qitastate = true;
                    qitaStr = 32;
                    tv_qita.setTextColor(Color.parseColor("#d5aa5f"));
                    tv_qita.setBackgroundResource(R.drawable.bg_text_cer_selecter);
                    yewufanwei++;
                }

            }
            /*
                 领队线路（可多选）（位运算）：1=港澳台线路，2=日韩线路，4=东南亚线路，8=中东非线路，16=澳新线路，32=欧洲线路，64=地中海线路，128=南北美洲线路，256=极地线路
             */
            attrLine = Integer.parseInt(jsonObject.get("AttrLine").toString().trim());
            if (attrLine > 0) {

                if ((attrLine & 1) == 1) {//港澳台线路
                    rl_gangaotaistate = true;
                    gangaotaiStr = 1;
                    im_gangaotai.setBackgroundResource(R.mipmap.checkbox_check_icon);

                }
                if ((attrLine & 2) == 2) {//日韩线路
                    rl_rihanstate = true;
                    rihanStr = 2;
                    im_rihan.setBackgroundResource(R.mipmap.checkbox_check_icon);
                }
                if ((attrLine & 4) == 4) {//东南亚线路
                    rl_dongnanyastate = true;
                    dongnanyaStr = 4;
                    im_dongnanya.setBackgroundResource(R.mipmap.checkbox_check_icon);
                }
                if ((attrLine & 8) == 8) {//中东非线路
                    rl_zhongdongfeistate = true;
                    zhongdongfeiStr = 8;
                    im_zhongdongfei.setBackgroundResource(R.mipmap.checkbox_check_icon);
                }
                if ((attrLine & 16) == 16) {//澳新线路
                    rl_aoxinstate = true;
                    aoxinStr = 16;
                    im_aoxin.setBackgroundResource(R.mipmap.checkbox_check_icon);
                }
                if ((attrLine & 32) == 32) {//欧洲线路
                    rl_ouzhoustate = true;
                    ouzhouStr = 32;
                    im_ouzhou.setBackgroundResource(R.mipmap.checkbox_check_icon);
                }
                if ((attrLine & 64) == 64) {//地中海线路
                    rl_dizhonghaistate = true;
                    dizhonghaiStr = 64;
                    im_dizhonghai.setBackgroundResource(R.mipmap.checkbox_check_icon);
                }
                if ((attrLine & 128) == 128) {//南北美洲线路
                    rl_meizhoustate = true;
                    meizhouStr = 128;
                    im_meizhou.setBackgroundResource(R.mipmap.checkbox_check_icon);
                }
                if ((attrLine & 256) == 256) {//极地线路
                    rl_jidistate = true;
                    jidiStr = 256;
                    im_jidi.setBackgroundResource(R.mipmap.checkbox_check_icon);
                }

            }
            //课程类型（1=地方课程，2=领队课程，4=基础课程，8=实战课程，16=团型课程，32=语言课程，64=才艺课程，128=职业课程）
            courseType = Integer.parseInt(jsonObject1.get("CourseType").toString().trim());
            if (courseType > 0) {
                if ((courseType & 1) == 1) {//地方课程
                    tv_class_difangstate = true;
                    difangStr = 1;
                    tv_class_difang.setTextColor(Color.parseColor("#d5aa5f"));
                    tv_class_difang.setBackgroundResource(R.drawable.bg_text_cer_selecter);
                    zuiguanzhu++;
                }
                if ((courseType & 2) == 2) {//领队课程
                    tv_class_lingduistate = true;
                    lingduiStr1 = 2;
                    tv_class_lingdui.setTextColor(Color.parseColor("#d5aa5f"));
                    tv_class_lingdui.setBackgroundResource(R.drawable.bg_text_cer_selecter);
                    zuiguanzhu++;
                }
                if ((courseType & 4) == 4) {//基础课程
                    tv_class_jichustate = true;
                    jichuStr = 4;
                    tv_class_jichu.setTextColor(Color.parseColor("#d5aa5f"));
                    tv_class_jichu.setBackgroundResource(R.drawable.bg_text_cer_selecter);
                    zuiguanzhu++;
                }
                if ((courseType & 8) == 8) {//实战课程
                    tv_class_shizhanstate = true;
                    shizhanStr = 8;
                    tv_class_shizhan.setTextColor(Color.parseColor("#d5aa5f"));
                    tv_class_shizhan.setBackgroundResource(R.drawable.bg_text_cer_selecter);
                    zuiguanzhu++;
                }
                if ((courseType & 16) == 16) {//团型课程
                    tv_class_tuanxingstate = true;
                    tuanxingStr = 16;
                    tv_class_tuanxing.setTextColor(Color.parseColor("#d5aa5f"));
                    tv_class_tuanxing.setBackgroundResource(R.drawable.bg_text_cer_selecter);
                    zuiguanzhu++;
                }
                if ((courseType & 32) == 32) {//语言课程
                    tv_class_yuyanstate = true;
                    yuyanStr = 32;
                    tv_class_yuyan.setTextColor(Color.parseColor("#d5aa5f"));
                    tv_class_yuyan.setBackgroundResource(R.drawable.bg_text_cer_selecter);
                    zuiguanzhu++;
                }
                if ((courseType & 64) == 64) {//才艺课程
                    tv_class_caiyistate = true;
                    caiyiStr = 64;
                    tv_class_caiyi.setTextColor(Color.parseColor("#d5aa5f"));
                    tv_class_caiyi.setBackgroundResource(R.drawable.bg_text_cer_selecter);
                    zuiguanzhu++;
                }
                if ((courseType & 128) == 128) {//职业课程
                    tv_class_zhiyestate = true;
                    zhiyeStr = 128;
                    tv_class_zhiye.setTextColor(Color.parseColor("#d5aa5f"));
                    tv_class_zhiye.setBackgroundResource(R.drawable.bg_text_cer_selecter);
                    zuiguanzhu++;
                }
            }
            picurl = jsonObject.get("PicUrl").toString();
            if (picurl != null && !picurl.equals("")) {
                Glide.with(CertificationActivity.this).load(Url.ROOT + picurl).error(R.mipmap.pictures_no).into(im_picture);
               /* im_picture.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("lxy", "iv_W = " + im_picture.getWidth() + ", iv_H = " + im_picture.getHeight());
                        int width = im_picture.getDrawable().getBounds().width();
                        int height = im_picture.getDrawable().getBounds().height();
                        Log.e("ashdfkjsf", "==========" + width);
                        Log.e("ashdfkjsf", "==========" + height);
                    }
                });*/

                if (userstate.equals("5") || userstate.equals("2")) {
                    //不能修改照片
                    rl_delete.setVisibility(View.GONE);
                    iscandel = false;
                } else {
                    rl_delete.setVisibility(View.VISIBLE);
                }
                im_add_pic.setVisibility(View.GONE);
            } else {
                im_add_pic.setVisibility(View.VISIBLE);
                rl_delete.setVisibility(View.GONE);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private boolean iscandel = true;

    @Override
    public void OnLoadDistributorExtendFialCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
    }

    @Override
    public void closeLoadDistributorExtendProgress() {

    }

    @Override
    public void OnSaveDistributorExtendSuccCallBack(String state, String respanse) {
        //保存成功
        closeLoadingProgressDialog();
        try {
            JSONObject jsb = new JSONObject(respanse);
            JSONArray jsa = new JSONArray(jsb.getString("result"));
            String isRenzheng = jsa.get(0).toString();
            PreferenceHelper.write(CertificationActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.STATE, isRenzheng);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (userstate.equals("5") || userstate.equals("2")) {
            //不能修改照片
            MyToast.makeText(this, "感谢您的支持，\n蜂优客有您更精彩！", Toast.LENGTH_SHORT).show();
            Bundle bundle = new Bundle();
            bundle.putString("selection_postion", "0");
            openActivity(HomeActivity.class, bundle);
            CertificationActivity.this.finish();
//            showOneDialog("感谢您的支持\n欢迎使用蜂优客！");
        } else {
            MyToast.makeText(this, "资料已提交, 请等待审核", Toast.LENGTH_SHORT).show();
            CertificationActivity.this.finish();
        }

//        CertificationActivity.this.finish();
//        Log.e("jksahkhss", "=====" + respanse);
    }

    public void showOneDialog(String str) {
        LinearLayout.LayoutParams pm = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_text, null);
        TextView text1 = (TextView) view.findViewById(R.id.text1);
        text1.setText(str);
        TextView yes = (TextView) view.findViewById(R.id.yes);
        final AlertDialog mAlertDialog = builder.create();
        mAlertDialog.getWindow().setBackgroundDrawableResource(R.color.touming);
        mAlertDialog.setCanceledOnTouchOutside(false);
        mAlertDialog.show();
        mAlertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        mAlertDialog.getWindow().setContentView(view, pm);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CertificationActivity.this.finish();
                mAlertDialog.dismiss();
            }
        });
    }

    @Override
    public void OnSaveDistributorExtendFialCallBack(String state, String respanse) {
        //保存失败
        closeLoadingProgressDialog();
        MyToast.makeText(this, "" + respanse, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void closeSaveDistributorExtendProgress() {

    }

    private class OnUploadRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            closeLoadingProgressDialog();
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if (status.equals("1")) {
                    String result = jsonObject.getString("result");
                    JSONArray array = new JSONArray(result);
                    im_add_pic.setVisibility(View.GONE);
                    if (iscandel) {
                        rl_delete.setVisibility(View.VISIBLE);
                    }
                    picurl = array.get(0).toString();
                    Glide.with(CertificationActivity.this).load(Url.ROOT + array.get(0).toString()).into(im_picture);
                    /*String data = array.get(0).toString();
                    JSONArray array1 = new JSONArray(data);
                    if (array1 != null && array1.length() > 0) {
                        for (int i = 0; i < array1.length(); i++) {
                            JSONObject jsonObject_ = array1.getJSONObject(i);
                            String result_imgpath = jsonObject_.getString("picUrl");
                            String smallPicUrl = jsonObject_.getString("smallPicUrl");
                            String width = jsonObject_.getString("width");
                            String hight = jsonObject_.getString("height");
                            String upload_urls = result_imgpath + "&" + smallPicUrl + "&" + width + "&" + hight + "|";
                            //
                            Log.e("jhsadfka", "------------"+upload_urls );
                        }
                        Message message = new Message();
                        message.what = 1002;
                        handler.sendMessage(message);
                    }*/
                } else if (status.equals("0")) {
                    MyToast.show(CertificationActivity.this, "上传失败");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onRequestPrepare() {
            super.onRequestPrepare();
//          showLoadingProgressDialog(PublishFenwenActivity.this, "");
        }

        @Override
        public void onRequestFailed(String error) {
            super.onRequestFailed(error);
            closeLoadingProgressDialog();
        }

        @Override
        public void onRequestFinish(String data) {
            super.onRequestFinish(data);
//          closeLoadingProgressDialog();
        }
    }

    private List<String> eduData = new ArrayList<String>(Arrays.asList("研究生及以上", "  本科  ", "  专科  ", "  高中  ", "  中专  ", "  其它  "));
    private List<String> levelData = new ArrayList<String>(Arrays.asList("  初级  ", "  中级  ", "  高级  ", "   特级  "));
    private List<String> lanData = new ArrayList<String>(Arrays.asList("中文导游", "外语导游"));
    private List<String> sexData = new ArrayList<String>(Arrays.asList(" 男 ", " 女 "));
    private List<String> monthData = new ArrayList<String>(Arrays.asList("  01月  ", "  02月  ", "  03月  ", "  04月  ", "  05月  ", "  06月  ", "  07月  ", "  08月  ", "  09月  ", "  10月  ", "  11月  ", "  12月  "));
    private List<String> yearData = new ArrayList<String>();
    private int edustr = 0;
    private int levelstr = 0;
    private int lanstr = 0;
    private int sexstr = 0;

    /**
     * 时间选择
     */
    private int oneCurrentItem = 0;
    private int twoCurrentItem = 0;

    private void selecteTime() {
        oneCurrentItem = 40;
        twoCurrentItem = 0;
        View inflate = LayoutInflater.from(this).inflate(R.layout.popup_classlabel, null);
        WheelView one_level = (WheelView) inflate.findViewById(R.id.one_level);
        final WheelView two_level = (WheelView) inflate.findViewById(R.id.two_level);
        TextView tv_cancle_sex = (TextView) inflate.findViewById(R.id.tv_cancle_sex);
        TextView tv_done_sex = (TextView) inflate.findViewById(R.id.tv_done_sex);
        TextView tv_title = (TextView) inflate.findViewById(R.id.tv_title);//描述
        tv_done_sex.setText("确定");
        tv_title.setText("选择从业时间");
        final PopWindows popWindows = new PopWindows(this, this, inflate);
        popWindows.showPopWindowBottom();
        tv_cancle_sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindows.cleanPopAlpha();
            }
        });
        tv_done_sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_work_time.setText(yearData.get(oneCurrentItem) + monthData.get(twoCurrentItem).trim());
                popWindows.cleanPopAlpha();
            }
        });
        //------
        oneAdapter = new WheelTextAdapter(this, yearData);
        one_level.setVisibleItems(5);
        one_level.setViewAdapter(oneAdapter);
        one_level.setCurrentItem(40);
        setTextviewSize("0", oneAdapter);
        //---------
        twoAdapter = new WheelTextAdapter(this, monthData);
        two_level.setVisibleItems(5);
        two_level.setViewAdapter(twoAdapter);
        two_level.setCurrentItem(0);
        setTextviewSize("0", twoAdapter);

        one_level.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                // TODO Auto-generated method stub
                String currentText = (String) oneAdapter.getItemText(wheel.getCurrentItem());
//                strProvince = currentText;
                setTextviewSize(currentText, oneAdapter);

//                String[] citys = mCitisDatasMap.get(currentText);
//                initCitys(citys);
                twoAdapter = new WheelTextAdapter(CertificationActivity.this, monthData);
                two_level.setVisibleItems(5);
                two_level.setViewAdapter(twoAdapter);
                two_level.setCurrentItem(0);
                setTextviewSize("0", twoAdapter);
            }
        });
        one_level.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                // TODO Auto-generated method stub
                String currentText = (String) oneAdapter.getItemText(wheel.getCurrentItem());
                oneCurrentItem = wheel.getCurrentItem();
                twoCurrentItem = 0;
                setTextviewSize(currentText, oneAdapter);
            }
        });
        two_level.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                // TODO Auto-generated method stub
                String currentText = (String) twoAdapter.getItemText(wheel.getCurrentItem());
                twoCurrentItem = wheel.getCurrentItem();
                setTextviewSize(currentText, twoAdapter);
            }
        });
    }

    /**
     * 性别选择
     */
    private void selectSex() {
        sexstr = 0;
        View inflate = LayoutInflater.from(this).inflate(R.layout.popup_seledu, null);
        TextView tv_cancle = (TextView) inflate.findViewById(R.id.tv_cancle);//取消
        TextView tv_done = (TextView) inflate.findViewById(R.id.tv_done);//确定
        TextView tv_title = (TextView) inflate.findViewById(R.id.tv_title);//描述
        tv_title.setText("选择您的性别");
        WheelView one_level = (WheelView) inflate.findViewById(R.id.one_level);
        final PopWindows popWindows = new PopWindows(this, this, inflate);
        popWindows.showPopWindowBottom();
        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindows.cleanPopAlpha();
            }
        });
        tv_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_sex.setText(sexData.get(sexstr).trim());
                popWindows.cleanPopAlpha();
            }
        });

        //----

        final WheelTextAdapter oneAdapter = new WheelTextAdapter(this, sexData);
        one_level.setVisibleItems(3);
        one_level.setViewAdapter(oneAdapter);
        one_level.setCurrentItem(0);
        setTextviewSize(sexData.get(0), oneAdapter);
        one_level.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                // TODO Auto-generated method stub
                String currentText = (String) oneAdapter.getItemText(wheel.getCurrentItem());
                setTextviewSize(currentText, oneAdapter);
            }
        });

        one_level.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                // TODO Auto-generated method stub
                String currentText = (String) oneAdapter.getItemText(wheel.getCurrentItem());
                sexstr = wheel.getCurrentItem();
                setTextviewSize(currentText, oneAdapter);
            }
        });
    }

    /**
     * 语言选择
     */
    private void selecteLanguage() {
        lanstr = 0;
        View inflate = LayoutInflater.from(this).inflate(R.layout.popup_seledu, null);
        TextView tv_cancle = (TextView) inflate.findViewById(R.id.tv_cancle);//取消
        TextView tv_done = (TextView) inflate.findViewById(R.id.tv_done);//确定
        TextView tv_title = (TextView) inflate.findViewById(R.id.tv_title);//描述
        tv_title.setText("选择您属于");
        WheelView one_level = (WheelView) inflate.findViewById(R.id.one_level);
        final PopWindows popWindows = new PopWindows(this, this, inflate);
        popWindows.showPopWindowBottom();
        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindows.cleanPopAlpha();
            }
        });
        tv_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_language.setText(lanData.get(lanstr));
                popWindows.cleanPopAlpha();
            }
        });

        //----

        final WheelTextAdapter oneAdapter = new WheelTextAdapter(this, lanData);
        one_level.setVisibleItems(3);
        one_level.setViewAdapter(oneAdapter);
        one_level.setCurrentItem(0);
        setTextviewSize(lanData.get(0), oneAdapter);
        one_level.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                // TODO Auto-generated method stub
                String currentText = (String) oneAdapter.getItemText(wheel.getCurrentItem());
//                strProvince = currentText;
                setTextviewSize(currentText, oneAdapter);
            }
        });

        one_level.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                // TODO Auto-generated method stub
                String currentText = (String) oneAdapter.getItemText(wheel.getCurrentItem());
                lanstr = wheel.getCurrentItem();
                setTextviewSize(currentText, oneAdapter);
            }
        });
    }

    /**
     * 等级选择
     */
    private void selecteLevel() {
        levelstr = 0;
        View inflate = LayoutInflater.from(this).inflate(R.layout.popup_seledu, null);
        TextView tv_cancle = (TextView) inflate.findViewById(R.id.tv_cancle);//取消
        TextView tv_done = (TextView) inflate.findViewById(R.id.tv_done);//确定
        TextView tv_title = (TextView) inflate.findViewById(R.id.tv_title);//描述
        tv_title.setText("选择您的证件等级");
        WheelView one_level = (WheelView) inflate.findViewById(R.id.one_level);
        final PopWindows popWindows = new PopWindows(this, this, inflate);
        popWindows.showPopWindowBottom();
        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindows.cleanPopAlpha();
            }
        });
        tv_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_cert_level.setText(levelData.get(levelstr));
                mlevel = (levelstr + 1) + "";
                popWindows.cleanPopAlpha();
            }
        });

        //----

        final WheelTextAdapter oneAdapter = new WheelTextAdapter(this, levelData);
        one_level.setVisibleItems(3);
        one_level.setViewAdapter(oneAdapter);
        one_level.setCurrentItem(0);
        setTextviewSize(levelData.get(0), oneAdapter);
        one_level.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                // TODO Auto-generated method stub
                String currentText = (String) oneAdapter.getItemText(wheel.getCurrentItem());
//                strProvince = currentText;
                setTextviewSize(currentText, oneAdapter);
            }
        });

        one_level.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                // TODO Auto-generated method stub
                String currentText = (String) oneAdapter.getItemText(wheel.getCurrentItem());
                levelstr = wheel.getCurrentItem();
                setTextviewSize(currentText, oneAdapter);
            }
        });
    }

    /**
     * 学历选择
     */
    private void selectEdu() {
        edustr = 0;
        View inflate = LayoutInflater.from(this).inflate(R.layout.popup_seledu, null);
        TextView tv_cancle = (TextView) inflate.findViewById(R.id.tv_cancle);//取消
        TextView tv_done = (TextView) inflate.findViewById(R.id.tv_done);//确定
        TextView tv_title = (TextView) inflate.findViewById(R.id.tv_title);//描述
        tv_title.setText("选择您的学历");
        WheelView one_level = (WheelView) inflate.findViewById(R.id.one_level);
        final PopWindows popWindows = new PopWindows(this, this, inflate);
        popWindows.showPopWindowBottom();
        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindows.cleanPopAlpha();
            }
        });
        tv_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_edu.setText(eduData.get(edustr));
                meduStr = (edustr + 1) + "";
                popWindows.cleanPopAlpha();
            }
        });

        //----

        final WheelTextAdapter oneAdapter = new WheelTextAdapter(this, eduData);
        one_level.setVisibleItems(3);
        one_level.setViewAdapter(oneAdapter);
        one_level.setCurrentItem(0);
        setTextviewSize(eduData.get(0), oneAdapter);
        one_level.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                // TODO Auto-generated method stub
                String currentText = (String) oneAdapter.getItemText(wheel.getCurrentItem());
//                strProvince = currentText;
                setTextviewSize(currentText, oneAdapter);
            }
        });

        one_level.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                // TODO Auto-generated method stub
                String currentText = (String) oneAdapter.getItemText(wheel.getCurrentItem());
                edustr = wheel.getCurrentItem();
                setTextviewSize(currentText, oneAdapter);
            }
        });
    }

    private class WheelTextAdapter extends AbstractWheelTextAdapter {
        /**
         * Constructor
         */
        public List<String> list;
        private ArrayList<View> arrayList = new ArrayList<View>();

        protected WheelTextAdapter(Context context, List<String> list) {
            super(context, R.layout.wheel_view_layout, NO_RESOURCE);
            this.list = list;
        }

        public ArrayList<View> getTestViews() {
            return arrayList;
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);

            TextView textCity = (TextView) view.findViewById(R.id.textView);
            if (!arrayList.contains(textCity)) {
                arrayList.add(textCity);
            }
            textCity.setTextSize(16);
            textCity.setText(list.get(index));

            return view;
        }

        public int getItemsCount() {
            return list.size();
        }

        @Override
        protected CharSequence getItemText(int index) {
            return list.get(index);
        }
    }

    /**
     * 设置字体大小
     *
     * @param curriteItemText
     * @param adapter
     */
    public void setTextviewSize(String curriteItemText, WheelTextAdapter adapter) {
        ArrayList<View> arrayList = adapter.getTestViews();
        int size = arrayList.size();
        String currentText;
        for (int i = 0; i < size; i++) {
            TextView textvew = (TextView) arrayList.get(i);
            currentText = textvew.getText().toString();
            Log.e("khaksfsa", "====" + textvew.getText());
            if (curriteItemText.equals(currentText)) {
                textvew.setTextSize(16);
                textvew.setTextColor(Color.parseColor("#000000"));
            } else {
                textvew.setTextSize(14);
                textvew.setTextColor(Color.parseColor("#a3a3a3"));
            }
        }
    }

    @Override
    public void OnDelImgSuccCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
        picurl = "";
        im_add_pic.setVisibility(View.VISIBLE);
        im_picture.setImageResource(R.drawable.bg_im_dash);
    }

    @Override
    public void OnDelImgFialCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
    }

    @Override
    public void closeDelImgProgress() {

    }
}
