package com.lvgou.distribution.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.adapter.ThemeGridViewAdapter;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.entity.ApplyClassEntity;
import com.lvgou.distribution.entity.ClassifyEntity;
import com.lvgou.distribution.presenter.PersonalPresenter;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.GroupView;
import com.lvgou.distribution.view.PersonalView;
import com.lvgou.distribution.widget.CustomDatePicker;
import com.xdroid.common.utils.PreferenceHelper;
import com.xdroid.common.utils.StringUtils;
import com.lvgou.distribution.utils.MyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Administrator on 2016/11/25.
 * 申请开课
 */
public class ApplyClassActivity extends BaseActivity implements PersonalView {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.tv_name)
    private TextView tv_name;
    @ViewInject(R.id.et_intro)
    private EditText et_intro;
    @ViewInject(R.id.tv_time)
    private TextView tv_time;
    @ViewInject(R.id.rl_time)
    private RelativeLayout rl_time;
    @ViewInject(R.id.et_theme)
    private EditText et_theme;
    @ViewInject(R.id.tv_type)
    private TextView tv_type;
    @ViewInject(R.id.rl_type)
    private RelativeLayout rl_type;
    @ViewInject(R.id.et_theme_info)
    private EditText et_theme_intro;
    @ViewInject(R.id.et_proper_people)
    private EditText et_proper_people;
    @ViewInject(R.id.et_signup_fee)
    private EditText et_signup_fee;
    @ViewInject(R.id.et_seek_fee)
    private EditText et_seek_fee;
    @ViewInject(R.id.tv_commit)
    private TextView tv_commit;

    private String userName = "";
    private String distributorid = "";

    private PersonalPresenter personalPresenter;

    private String zbTime = "2016-12-09 10:30";// 开课时间
    private String theme_label = "";
    private List<String> list_themetype = new ArrayList<String>();
    private CustomDatePicker customDatePicker2;
    List<ClassifyEntity> themeTypeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_classes);
        ViewUtils.inject(this);
        distributorid = PreferenceHelper.readString(ApplyClassActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID, "0");

        personalPresenter = new PersonalPresenter(this);
        String sign = TGmd5.getMD5(distributorid);
        personalPresenter.getHotTag(distributorid, sign);
        userName = getTextFromBundle("name");
        tv_name.setText(userName);

//        SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm");
//        String now_time = format.format(new Date());
//        zbTime = format.format(new Date());
//        tv_time.setText(judgeTime(now_time.split(" ")[0]) + now_time.split(" ")[1]);
        initDatePicker();

    }

    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm", Locale.CHINA);
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        zbTime = sdf2.format(new Date());
        tv_time.setText(sdf.format(new Date()));


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
                SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm", Locale.CHINA);
                tv_time.setText(simpleDateFormat2.format(c.getTimeInMillis()));
            }
        }, "1970-01-01 00:00", "2100-12-31 00:00"); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker2.showSpecificTime(true); // 显示时和分
        customDatePicker2.setIsLoop(true); // 允许循环滚动
    }

    @OnClick({R.id.rl_back, R.id.tv_commit, R.id.rl_time, R.id.rl_type})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_type:
                shopthemeTypeDialog();
                break;
            case R.id.rl_time:
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
//                zbTime = sdf.format(new Date());
                customDatePicker2.show(zbTime);
                break;
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_commit:
                String intro_str = et_intro.getText().toString();
                String theme_str = et_theme.getText().toString();
                String theme_info_str = et_theme_intro.getText().toString();
                String proper_people_str = et_proper_people.getText().toString();
                String signup_fee_str = et_signup_fee.getText().toString();
                String seek_fee_str = et_seek_fee.getText().toString();
                if (StringUtils.isEmpty(intro_str)) {
                    MyToast.show(ApplyClassActivity.this, "请输入讲师简介");
                    return;
                } else if (StringUtils.isEmpty(theme_str)) {
                    MyToast.show(ApplyClassActivity.this, "请输入课程主题");
                    return;
                } else if (StringUtils.isEmpty(theme_label)) {
                    MyToast.show(ApplyClassActivity.this, "请选择主题类型");
                    return;
                } else if (StringUtils.isEmpty(theme_info_str)) {
                    MyToast.show(ApplyClassActivity.this, "请输入主题简介");
                    return;
                } else if (StringUtils.isEmpty(proper_people_str)) {
                    MyToast.show(ApplyClassActivity.this, "请输入适合人群");
                    return;
                }
                else if (StringUtils.isEmpty(signup_fee_str)) {
                    MyToast.show(ApplyClassActivity.this, "请输入报名所需团币");
                    return;
                } else if (StringUtils.isEmpty(seek_fee_str)) {
                    MyToast.show(ApplyClassActivity.this, "请输入课后观看所需团币");
                    return;
                }
                else {
                    String sign_ = TGmd5.getMD5(distributorid + intro_str + zbTime + theme_str + theme_label + theme_info_str + proper_people_str + signup_fee_str + seek_fee_str);
                    ApplyClassEntity applyClassEntity = new ApplyClassEntity(distributorid, intro_str, zbTime, theme_str, theme_label, theme_info_str, proper_people_str, signup_fee_str, seek_fee_str, sign_);
                    personalPresenter.doApplyClass(applyClassEntity);
                }

                break;
        }
    }

    /**
     * 选择语言对话框
     */
    private Dialog dialog_themeType;
    ThemeGridViewAdapter themeGridViewAdapter;

    public void shopthemeTypeDialog() {
        dialog_themeType = new Dialog(ApplyClassActivity.this,
                R.style.style_custom_dialog);
        View view_sex = View.inflate(ApplyClassActivity.this,
                R.layout.dialog_select_themetype, null);
        TextView tv_done_sex = (TextView) view_sex
                .findViewById(R.id.tv_done_sex);
        TextView tv_cancle_sex = (TextView) view_sex
                .findViewById(R.id.tv_cancle_sex);
        GridView them_gridview = (GridView) view_sex.findViewById(R.id.them_gridview);
        themeGridViewAdapter = new ThemeGridViewAdapter(this, themeTypeList);
        them_gridview.setAdapter(themeGridViewAdapter);
        them_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (themeGridViewAdapter.getItem(position).isChecked()) {
                    themeGridViewAdapter.getItem(position).setIsChecked(false);
                } else {
                    themeGridViewAdapter.getItem(position).setIsChecked(true);
                }
                themeGridViewAdapter.notifyDataSetChanged();
            }
        });

        tv_done_sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_themeType.dismiss();
                StringBuffer stringBuffer = new StringBuffer();
                StringBuffer theme_label_id=new StringBuffer();
                int k = 0;
                for (int i = 0; i < themeTypeList.size(); i++) {
                    if (themeGridViewAdapter.getThemeTypeList().get(i).isChecked() == true) {
                        if (k != 0) {
                            stringBuffer.append(",");
                            theme_label_id.append(",");
                        }
                        stringBuffer.append(themeTypeList.get(i).getName());
                        theme_label_id.append(themeTypeList.get(i).getId());
                        k++;
                    }
                }
                theme_label = theme_label_id.toString();
                tv_type.setText(stringBuffer.toString());
                tv_type.setTextColor(getResources().getColor(R.color.bg_new_guide_black));
            }
        });
        tv_cancle_sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_themeType.dismiss();
            }
        });


        dialog_themeType.setContentView(view_sex);
        dialog_themeType.show();
        WindowManager m2 = getWindowManager();
        Display d2 = m2.getDefaultDisplay(); // 为获取屏幕宽、高
        WindowManager.LayoutParams p2 = dialog_themeType
                .getWindow().getAttributes(); // 获取对话框当前的参数值
        p2.width = (int) (d2.getWidth()); // 宽度设置为全屏
        dialog_themeType.getWindow().setAttributes(p2); // 设置生效
    }

    /**
     * 成功回调
     *
     * @param state
     * @param respanse
     */
    @Override
    public void OnRequestSuccCallBack(String state, String respanse) {
        switch (Integer.parseInt(state)) {
            case 4:
                try {
                    themeTypeList.clear();
                    JSONObject jsonObject = new JSONObject(respanse);
                    String message = jsonObject.getString("message");
                    String result = jsonObject.getString("result");
                    JSONArray array = new JSONArray(result);
                    if (array != null && array.length() > 0) {
                        String data = array.get(0).toString();
                        JSONArray jsonArray = new JSONArray(data);
                        if (jsonArray != null && jsonArray.length() > 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject_ = jsonArray.getJSONObject(i);
                                String id = jsonObject_.getString("ID");
                                String name = jsonObject_.getString("String1");
                                ClassifyEntity classifyEntity = new ClassifyEntity(id, name, "");
                                themeTypeList.add(classifyEntity);
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 5:
                try {
                    JSONObject jsonObject = new JSONObject(respanse);
                    final String message = jsonObject.getString("message");
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            MyToast.show(ApplyClassActivity.this, message);
                        }
                    });

                    finish();
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
    public void OnRequestFialCallBack(String state, String respanse) {
        switch (Integer.parseInt(state)) {
            case 4:
                break;
            case 5:
                try {
                    JSONObject jsonObject = new JSONObject(respanse);
                    final String message = jsonObject.getString("message");
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            MyToast.show(ApplyClassActivity.this, message);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    /**
     * 取消弹框
     */
    @Override
    public void closeProgress() {

    }

    /**
     * 到时候，这个类。可以抽取出来，到 BaseActivity 里面或者单独的工具类
     * 然后全局搜索，这个方法名，替换掉 即可
     * <p/>
     * 方法说明：日期转换
     * <p/>
     * 2016-6-31  ---> 2016年6月31日
     *
     * @param time
     */
    public String judgeTime(String time) {
        LogUtils.e("time" + time);
        String strDate = "";
        String month = "";
        String day = "";
        String str[] = time.split("-");
        if (str[1].length() == 1) {
            month = "0" + str[1];
        } else if (str[1].length() == 2) {
            month = str[1];
        }

        if (str[2].length() == 1) {
            day = "0" + str[2];
        } else if (str[2].length() == 2) {
            day = str[2];
        }
        strDate = str[0] + "年" + month + "月" + day + "日";
        return strDate;
    }
}
