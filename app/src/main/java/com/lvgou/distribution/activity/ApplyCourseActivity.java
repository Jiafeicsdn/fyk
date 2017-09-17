package com.lvgou.distribution.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.lvgou.distribution.utils.MyToast;
import android.widget.Toast;

import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.presenter.ApplyForStudyPresenter;
import com.lvgou.distribution.presenter.LabelLoadPresenter;
import com.lvgou.distribution.utils.PopWindows;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.ApplyForStudyView;
import com.lvgou.distribution.view.LabelLoadView;
import com.lvgou.distribution.wheelview.OnWheelChangedListener;
import com.lvgou.distribution.wheelview.OnWheelScrollListener;
import com.lvgou.distribution.wheelview.WheelView;
import com.lvgou.distribution.wheelview.adapter.AbstractWheelTextAdapter;
import com.lvgou.distribution.widget.CustomDatePicker;
import com.xdroid.common.utils.PreferenceHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * Created by Administrator on 2017/3/16.
 * 申请开课
 */

public class ApplyCourseActivity extends BaseActivity implements View.OnClickListener, LabelLoadView, ApplyForStudyView {
    private LabelLoadPresenter labelLoadPresenter;
    private ApplyForStudyPresenter applyForStudyPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        labelLoadPresenter = new LabelLoadPresenter(this);
        applyForStudyPresenter = new ApplyForStudyPresenter(this);
        setContentView(R.layout.activity_apply_course);
        initLabelDatas();
        initView();
        initClick();
        initDatePicker();

    }

    private void initLabelDatas() {
        String distributorid = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        String sign = TGmd5.getMD5(distributorid);
        showLoadingProgressDialog(this, "");
        labelLoadPresenter.labelLoadDatas(distributorid, sign);
    }

    private RelativeLayout rl_back;//返回
    private TextView tv_title;//标题
    private EditText et_course_title;//课程主题
    private TextView tv_classes_time;//开课时间
    private TextView tv_class_type;//课程类型
    private EditText et_suit_crowd;//适合人群
    private EditText et_simple_introduce;//课程介绍
    private TextView tv_text_num;
    private TextView tv_class_style;//开课形式
//    private EditText et_class_pay;//报名付币
//    private EditText et_wacth_pay;//课后团币
    private CheckBox cb_xieyi;//同意协议与否
    private TextView tv_click_xieyi;//蜂优客服务协议
    private TextView tv_fabu;//发布

    private void initView() {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("申请开课");
        et_course_title = (EditText) findViewById(R.id.et_course_title);
        tv_classes_time = (TextView) findViewById(R.id.tv_classes_time);
        tv_class_type = (TextView) findViewById(R.id.tv_class_type);
        et_suit_crowd = (EditText) findViewById(R.id.et_suit_crowd);
        et_simple_introduce = (EditText) findViewById(R.id.et_simple_introduce);
        tv_text_num = (TextView) findViewById(R.id.tv_text_num);
        //改变默认的单行模式
        et_simple_introduce.setSingleLine(false);
        //水平滚动设置为False
        et_simple_introduce.setHorizontallyScrolling(false);
        TextChangeListener();
        tv_class_style = (TextView) findViewById(R.id.tv_class_style);
//        et_class_pay = (EditText) findViewById(R.id.et_class_pay);
//        et_wacth_pay = (EditText) findViewById(R.id.et_wacth_pay);
        cb_xieyi = (CheckBox) findViewById(R.id.cb_xieyi);
        tv_click_xieyi = (TextView) findViewById(R.id.tv_click_xieyi);
        tv_fabu = (TextView) findViewById(R.id.tv_fabu);
    }

    private String xieyi = "1";//是否选择协议

    private void initClick() {
        rl_back.setOnClickListener(this);
        tv_classes_time.setOnClickListener(this);
        tv_class_type.setOnClickListener(this);
        tv_class_style.setOnClickListener(this);
        tv_click_xieyi.setOnClickListener(this);
        tv_fabu.setOnClickListener(this);
        cb_xieyi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    xieyi = "1";
                    cb_xieyi.setBackgroundResource(R.mipmap.check_xieyi_icon);
                } else {
                    xieyi = "0";
                    cb_xieyi.setBackgroundResource(R.mipmap.register_user_default);
                }
            }
        });
    }

    private View BriefIntroView;
    private Dialog harfdialog;
    private Button zhibo_btn, lubo_btn;
    private String kaikeStyle = "";

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back://返回
                finish();
                break;
            case R.id.tv_classes_time://开课时间
                customDatePicker2.show(zbTime);
                break;
            case R.id.tv_class_type://课程类型
                oneCurrentItem = 0;
                twoCurrentItem = 0;
                selectClassType();
                break;
            case R.id.tv_class_style://开课形式
                selectCourseStyle();
               /* BriefIntroView = LayoutInflater.from(this).inflate(R.layout.item_gender_select_popup, null);
                harfdialog = new Dialog(ApplyCourseActivity.this, R.style.alert_dialog);
                harfdialog.getWindow().setContentView(BriefIntroView);
                harfdialog.show();
                zhibo_btn = (Button) BriefIntroView.findViewById(R.id.gender_boy_btn);
                zhibo_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        harfdialog.dismiss();
                        kaikeStyle = "1";
                        tv_class_style.setText("直播");
                    }
                });
                lubo_btn = (Button) BriefIntroView.findViewById(R.id.gender_girl_btn);
                lubo_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        harfdialog.dismiss();
                        kaikeStyle = "0";
                        tv_class_style.setText("录播");
                    }
                });*/
                break;
            case R.id.tv_click_xieyi://蜂优客服务协议
                Bundle bundle = new Bundle();
                bundle.putString("url", "http://agent.quygt.com/user/applyforteacherprotocol.html ");
                bundle.putString("index", "0");
                openActivity(WebViewActivity.class, bundle);
                break;
            case R.id.tv_fabu://发布
                String title = et_course_title.getText().toString().trim();
                if (title == null || title.equals("")) {
                    MyToast.makeText(this, "请输入主题！", Toast.LENGTH_SHORT).show();
                    break;
                }
                String time = tv_classes_time.getText().toString().trim();
                if (time == null || time.equals("")) {
                    MyToast.makeText(this, "请选择开课时间！", Toast.LENGTH_SHORT).show();
                    break;
                }
                String type = tv_class_type.getText().toString().trim();
                if (type == null || type.equals("")) {
                    MyToast.makeText(this, "请选择课程类型！", Toast.LENGTH_SHORT).show();
                    break;
                }
                String suitpeo = et_suit_crowd.getText().toString().trim();
                if (suitpeo == null || suitpeo.equals("")) {
                    MyToast.makeText(this, "请输入适合人群！", Toast.LENGTH_SHORT).show();
                    break;
                }
                String introduce = et_simple_introduce.getText().toString().trim();
                if (introduce == null || introduce.equals("")) {
                    MyToast.makeText(this, "请介绍下你的课程！", Toast.LENGTH_SHORT).show();
                    break;
                }
                String style = tv_class_style.getText().toString().trim();
                if (style == null || style.equals("")) {
                    MyToast.makeText(this, "请选择开课形式！", Toast.LENGTH_SHORT).show();
                    break;
                }
               /* String classpay = et_class_pay.getText().toString().trim();
                if (classpay == null || classpay.equals("")) {
                    MyToast.makeText(this, "请输入报名费用！", Toast.LENGTH_SHORT).show();
                    break;
                }*/
                /*String watchpay = et_wacth_pay.getText().toString().trim();
                if (watchpay == null || watchpay.equals("")) {
                    MyToast.makeText(this, "请输入课后观看费用", Toast.LENGTH_SHORT).show();
                    break;
                }*/
                if (xieyi == null || xieyi.equals("")) {
                    MyToast.makeText(this, "请同意蜂优客服务协议！", Toast.LENGTH_SHORT).show();
                    break;
                }
                String zbtype = "1";
                String label = selectType.get("LabelPath").toString();
                if (type.equals("直播")) {
                    zbtype = "1";
                } else if (type.equals("录播")) {
                    zbtype = "2";
                }
                String distributorid = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
                String sign = TGmd5.getMD5(distributorid + title + time + label + suitpeo + introduce + zbtype + "0" + "0");
                showLoadingProgressDialog(this, "");
                applyForStudyPresenter.applyForStudy(distributorid, title, time, label, suitpeo, introduce, zbtype, "0", "0", sign);
                break;
        }
    }
    private int stylestr = 0;
    private List<String> styleData = new ArrayList<String>(Arrays.asList(" 直播 ", " 录播 "));
    private void selectCourseStyle() {
        stylestr = 0;
        View inflate = LayoutInflater.from(this).inflate(R.layout.popup_seledu, null);
        TextView tv_cancle = (TextView) inflate.findViewById(R.id.tv_cancle);//取消
        TextView tv_done = (TextView) inflate.findViewById(R.id.tv_done);//确定
        TextView tv_title = (TextView) inflate.findViewById(R.id.tv_title);//描述
        tv_title.setText("选择开课形式");
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
                tv_class_style.setText(styleData.get(stylestr).trim());
                popWindows.cleanPopAlpha();
            }
        });

        //----

        final WheelTextAdapter oneAdapter = new WheelTextAdapter(this, styleData);
        one_level.setVisibleItems(3);
        one_level.setViewAdapter(oneAdapter);
        one_level.setCurrentItem(0);
        setTextviewSize2(styleData.get(0), oneAdapter);
        one_level.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                // TODO Auto-generated method stub
                String currentText = (String) oneAdapter.getItemText(wheel.getCurrentItem());
                setTextviewSize2(currentText, oneAdapter);
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
                stylestr = wheel.getCurrentItem();
                setTextviewSize2(currentText, oneAdapter);
            }
        });
    }
    /**
     * 设置字体大小
     *
     * @param curriteItemText
     * @param adapter
     */
    public void setTextviewSize2(String curriteItemText, WheelTextAdapter adapter) {
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

    /*
distributorid 	是 	long 	导游ID
theme 	是 	string 	主题
starttime 	是 	string 	直播时间(2017-3-13 18:30)
label 	是 	string 	课程分类(103001,103002)
crowd 	是 	string 	适合人群
themeinfo 	是 	string 	主题简介
zbtype 	是 	int 	开课形式 1=直播，2=录播
apply 	是 	int 	报名团币 可为0
look 	是 	int 	查看团币 可为0
sign 	是 	string 	签名
     */

    private WheelView one_level;
    private WheelView two_level;
    private TextView tv_cancle_sex;
    private TextView tv_done_sex;
    private AddressTextAdapter oneAdapter;
    private AddressTextAdapter twoAdapter;
    private int oneCurrentItem = 0;
    private int twoCurrentItem = 0;
    private HashMap<String, Object> selectType = new HashMap<>();


    private void selectClassType() {
        View inflate = LayoutInflater.from(this).inflate(R.layout.popup_classlabel, null);
        one_level = (WheelView) inflate.findViewById(R.id.one_level);
        two_level = (WheelView) inflate.findViewById(R.id.two_level);
        tv_cancle_sex = (TextView) inflate.findViewById(R.id.tv_cancle_sex);
        tv_done_sex = (TextView) inflate.findViewById(R.id.tv_done_sex);
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
                try {
                    JSONObject jsa = list.get(oneCurrentItem).getJSONObject(twoCurrentItem);
                    selectType.put("LabelName", jsa.get("LabelName").toString());
                    selectType.put("LabelPath", jsa.get("LabelPath").toString());
                    tv_class_type.setText(jsa.get("LabelName").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                popWindows.cleanPopAlpha();
            }
        });
        //------
        oneAdapter = new AddressTextAdapter(this, groupData);
        one_level.setVisibleItems(5);
        one_level.setViewAdapter(oneAdapter);
        one_level.setCurrentItem(0);
        setTextviewSize("0", oneAdapter);
        //---------
        twoAdapter = new AddressTextAdapter(this, list.get(0));
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
                twoAdapter = new AddressTextAdapter(ApplyCourseActivity.this, list.get(wheel.getCurrentItem()));
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
     * 设置字体大小
     *
     * @param curriteItemText
     * @param adapter
     */
    public void setTextviewSize(String curriteItemText, AddressTextAdapter adapter) {
        ArrayList<View> arrayList = adapter.getTestViews();
        int size = arrayList.size();
        String currentText;
        for (int i = 0; i < size; i++) {
            TextView textvew = (TextView) arrayList.get(i);
            currentText = textvew.getText().toString();
            if (curriteItemText.equals(currentText)) {
                textvew.setTextSize(16);
            } else {
                textvew.setTextSize(14);
            }
        }
    }

    private class AddressTextAdapter extends AbstractWheelTextAdapter {
        /**
         * Constructor
         */
        public List<String> list;
        private ArrayList<View> arrayList = new ArrayList<View>();

        protected AddressTextAdapter(Context context, List<String> list) {
            super(context, R.layout.wheel_view_layout, NO_RESOURCE);
            this.list = list;
        }

        public ArrayList<View> getTestViews() {
            return arrayList;
        }

        protected AddressTextAdapter(Context context, JSONArray list) {
            super(context, R.layout.wheel_view_layout, NO_RESOURCE);
            this.list = new ArrayList<>();
            for (int j = 0; j < list.length(); j++) {
                try {
                    JSONObject jsList = list.getJSONObject(j);
                    HashMap<String, Object> map1 = new HashMap<String, Object>();
                    Iterator it = jsList.keys();
                    while (it.hasNext()) {
                        String key = (String) it.next();
                        String value = jsList.getString(key);
                        map1.put(key, value);
                    }
                    this.list.add(map1.get("LabelName").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);

            TextView textCity = (TextView) view.findViewById(R.id.textView);
            if (!arrayList.contains(textCity)) {
                arrayList.add(textCity);
            }
            textCity.setText(list.get(index));
            textCity.setTextSize(16);
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

    private String zbTime = "2017-01-01 10:30";// 开课时间
    private CustomDatePicker customDatePicker2;

    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        zbTime = sdf2.format(new Date());
        tv_classes_time.setText(sdf.format(new Date()));


        customDatePicker2 = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                zbTime = time;
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
                Calendar c = Calendar.getInstance();
                try {
                    c.setTime(simpleDateFormat.parse(time));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
                tv_classes_time.setText(simpleDateFormat2.format(c.getTimeInMillis()));
            }
        }, "1970-01-01 00:00", "2100-12-31 00:00"); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker2.showSpecificTime(true); // 显示时和分
        customDatePicker2.setIsLoop(true); // 允许循环滚动
    }

    public void TextChangeListener() {
        et_simple_introduce.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String length = et_simple_introduce.getText().length() + "";
                if (Integer.parseInt(length) >= 0 && Integer.parseInt(length) <= 500) {
                    tv_text_num.setText(length + "/500");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private List<String> groupData = new ArrayList<String>();//group的数据源
    private ArrayList<HashMap<String, Object>> childList = new ArrayList<>();
    private ArrayList<JSONArray> list = new ArrayList();

    @Override
    public void OnLabelLoadSuccCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
        try {
            JSONObject jsb = new JSONObject(respanse);
            JSONArray jsa = new JSONArray(jsb.getString("result"));
            JSONArray jsonArray = jsa.getJSONArray(0);
            groupData.clear();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsoo = jsonArray.getJSONObject(i);
                // 添加一级分类组名到Map集合
                groupData.add(jsoo.getString("LabelName"));
                // 得到二级数组
                JSONArray list_0 = jsoo.getJSONArray("labellist");

                childList.clear();
                // 遍历数组
                for (int j = 0; j < list_0.length(); j++) {
                    JSONObject jsList = list_0.getJSONObject(j);
                    HashMap<String, Object> map1 = new HashMap<String, Object>();
                    Iterator it = jsList.keys();
                    while (it.hasNext()) {
                        String key = (String) it.next();
                        String value = jsList.getString(key);
                        map1.put(key, value);
                    }
                    childList.add(map1);
                }
                list.add(list_0);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void OnLabelLoadFialCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
    }

    @Override
    public void closeLabelLoadProgress() {

    }

    //提交
    @Override
    public void OnApplyForStudySuccCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
        MyToast.makeText(this, "申请成功", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void OnApplyForStudyFialCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
        //还不是讲师,请申请讲师后重试
        MyToast.makeText(this, "" + respanse, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void closeApplyForStudyProgress() {

    }
}
