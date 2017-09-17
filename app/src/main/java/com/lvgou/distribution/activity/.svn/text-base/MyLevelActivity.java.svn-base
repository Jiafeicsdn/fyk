package com.lvgou.distribution.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lvgou.distribution.R;
import com.lvgou.distribution.adapter.LevelAdapter;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.presenter.LevelDetailPresenter;
import com.lvgou.distribution.utils.ImageUtils;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.LevelDetailView;
import com.xdroid.common.utils.PreferenceHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/4/1.
 */

public class MyLevelActivity extends BaseActivity implements View.OnClickListener, LevelDetailView {
    private LevelDetailPresenter levelDetailPresenter;
    private String mylevel = "";
    private ArrayList<String> list = new ArrayList<String>(Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mylevel);
        levelDetailPresenter = new LevelDetailPresenter(this);
        mylevel = getIntent().getStringExtra("melevel");
        initView();
        initDatas();
        initClick();
    }


    private RelativeLayout rl_back;
    private TextView tv_title;
    private ListView mListView;
    private View levelHeader;
    private LevelAdapter levelAdapter;
    private ImageView im_nowlevel;
    private RelativeLayout rl_one;
    private RelativeLayout rl_two;
    private RelativeLayout rl_three;
    private RelativeLayout rl_four;
    private TextView tv_onetext;
    private TextView tv_twotext;
    private TextView tv_threetext;
    private TextView tv_fourtext;
    private ImageView one_point;
    private ImageView two_point;
    private ImageView three_point;
    private ImageView four_point;
    private ImageView im_picture;


    private void initView() {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("我的等级");
        mListView = (ListView) findViewById(R.id.lv_listview);
        levelHeader = LayoutInflater.from(this).inflate(R.layout.mylevel_header, null);
        mListView.addHeaderView(levelHeader);
        levelAdapter = new LevelAdapter(this, mylevel, list);
        mListView.setAdapter(levelAdapter);
        im_nowlevel = (ImageView) levelHeader.findViewById(R.id.im_nowlevel);
        if (mylevel.equals("0")) {
            im_nowlevel.setVisibility(View.GONE);
        } else if (mylevel.equals("1")) {
            im_nowlevel.setVisibility(View.VISIBLE);
            im_nowlevel.setBackgroundResource(R.mipmap.onelevel_light_icon);
        } else if (mylevel.equals("2")) {
            im_nowlevel.setVisibility(View.VISIBLE);
            im_nowlevel.setBackgroundResource(R.mipmap.twolevel_light_icon);
        } else if (mylevel.equals("3")) {
            im_nowlevel.setVisibility(View.VISIBLE);
            im_nowlevel.setBackgroundResource(R.mipmap.threelevel_light_icon);
        } else if (mylevel.equals("4")) {
            im_nowlevel.setVisibility(View.VISIBLE);
            im_nowlevel.setBackgroundResource(R.mipmap.fourlevel_light_icon);
        } else if (mylevel.equals("5")) {
            im_nowlevel.setVisibility(View.VISIBLE);
            im_nowlevel.setBackgroundResource(R.mipmap.fivelevel_light_icon);
        } else if (mylevel.equals("6")) {
            im_nowlevel.setVisibility(View.VISIBLE);
            im_nowlevel.setBackgroundResource(R.mipmap.sixlevel_light_icon);
        } else if (mylevel.equals("7")) {
            im_nowlevel.setVisibility(View.VISIBLE);
            im_nowlevel.setBackgroundResource(R.mipmap.sevenlevel_light_icon);
        } else if (mylevel.equals("8")) {
            im_nowlevel.setVisibility(View.VISIBLE);
            im_nowlevel.setBackgroundResource(R.mipmap.eightlevel_light_icon);
        } else if (mylevel.equals("9")) {
            im_nowlevel.setVisibility(View.VISIBLE);
            im_nowlevel.setBackgroundResource(R.mipmap.ninelevel_light_icon);
        } else if (mylevel.equals("10")) {
            im_nowlevel.setVisibility(View.VISIBLE);
            im_nowlevel.setBackgroundResource(R.mipmap.tenlevel_light_icon);
        }
        rl_one = (RelativeLayout) findViewById(R.id.rl_one);
        rl_two = (RelativeLayout) findViewById(R.id.rl_two);
        rl_three = (RelativeLayout) findViewById(R.id.rl_three);
        rl_four = (RelativeLayout) findViewById(R.id.rl_four);
        tv_onetext = (TextView) findViewById(R.id.tv_onetext);
        tv_twotext = (TextView) findViewById(R.id.tv_twotext);
        tv_threetext = (TextView) findViewById(R.id.tv_threetext);
        tv_fourtext = (TextView) findViewById(R.id.tv_fourtext);
        one_point = (ImageView) findViewById(R.id.one_point);
        two_point = (ImageView) findViewById(R.id.two_point);
        three_point = (ImageView) findViewById(R.id.three_point);
        four_point = (ImageView) findViewById(R.id.four_point);
        im_picture = (ImageView) levelHeader.findViewById(R.id.im_picture);

    }

    private void initClick() {
        rl_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
        }
    }

    private void initDatas() {
        String distributorid = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        String sign = TGmd5.getMD5("" + distributorid);
        showLoadingProgressDialog(this, "");
        levelDetailPresenter.levelDetail(distributorid, sign);
        Glide.with(MyLevelActivity.this).load(ImageUtils.getInstance().getPath(distributorid)).error(R.mipmap.teacher_default_head).into(im_picture);
    }

    @Override
    public void OnLevelDetailSuccCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
        try {
            JSONObject jsb = new JSONObject(respanse);
            JSONArray jsa = new JSONArray(jsb.getString("result"));
            JSONArray jsonArray = jsa.getJSONArray(1);
            if (jsonArray.length() == 1) {
                rl_one.setVisibility(View.VISIBLE);
                rl_two.setVisibility(View.GONE);
                rl_three.setVisibility(View.GONE);
                rl_four.setVisibility(View.GONE);
                JSONArray onearray = jsonArray.getJSONArray(0);
                if (onearray.get(1).toString().equals("1")) {
                    //完成的
                    tv_onetext.setTextColor(Color.parseColor("#d5aa5f"));
                    one_point.setBackgroundResource(R.drawable.bg_ovil_light);
                } else {
                    tv_onetext.setTextColor(Color.parseColor("#bababa"));
                    one_point.setBackgroundResource(R.drawable.bg_ovil_normal);
                }
                tv_onetext.setText(onearray.get(0).toString());
            } else if (jsonArray.length() == 2) {
                rl_one.setVisibility(View.VISIBLE);
                rl_two.setVisibility(View.VISIBLE);
                rl_three.setVisibility(View.GONE);
                rl_four.setVisibility(View.GONE);
                JSONArray onearray = jsonArray.getJSONArray(0);
                if (onearray.get(1).toString().equals("1")) {
                    //完成的
                    tv_onetext.setTextColor(Color.parseColor("#d5aa5f"));
                    one_point.setBackgroundResource(R.drawable.bg_ovil_light);
                } else {
                    tv_onetext.setTextColor(Color.parseColor("#bababa"));
                    one_point.setBackgroundResource(R.drawable.bg_ovil_normal);
                }
                tv_onetext.setText(onearray.get(0).toString());
                JSONArray twoarray = jsonArray.getJSONArray(1);
                if (twoarray.get(1).toString().equals("1")) {
                    //完成的
                    tv_twotext.setTextColor(Color.parseColor("#d5aa5f"));
                    two_point.setBackgroundResource(R.drawable.bg_ovil_light);
                } else {
                    tv_twotext.setTextColor(Color.parseColor("#bababa"));
                    two_point.setBackgroundResource(R.drawable.bg_ovil_normal);
                }
                tv_twotext.setText(twoarray.get(0).toString());
            } else if (jsonArray.length() == 3) {
                rl_one.setVisibility(View.VISIBLE);
                rl_two.setVisibility(View.VISIBLE);
                rl_three.setVisibility(View.VISIBLE);
                rl_four.setVisibility(View.GONE);
                JSONArray onearray = jsonArray.getJSONArray(0);
                if (onearray.get(1).toString().equals("1")) {
                    //完成的
                    tv_onetext.setTextColor(Color.parseColor("#d5aa5f"));
                    one_point.setBackgroundResource(R.drawable.bg_ovil_light);
                } else {
                    tv_onetext.setTextColor(Color.parseColor("#bababa"));
                    one_point.setBackgroundResource(R.drawable.bg_ovil_normal);
                }
                tv_onetext.setText(onearray.get(0).toString());
                JSONArray twoarray = jsonArray.getJSONArray(1);
                if (twoarray.get(1).toString().equals("1")) {
                    //完成的
                    tv_twotext.setTextColor(Color.parseColor("#d5aa5f"));
                    two_point.setBackgroundResource(R.drawable.bg_ovil_light);
                } else {
                    tv_twotext.setTextColor(Color.parseColor("#bababa"));
                    two_point.setBackgroundResource(R.drawable.bg_ovil_normal);
                }
                tv_twotext.setText(twoarray.get(0).toString());
                JSONArray threearray = jsonArray.getJSONArray(2);
                if (threearray.get(1).toString().equals("1")) {
                    //完成的
                    tv_threetext.setTextColor(Color.parseColor("#d5aa5f"));
                    three_point.setBackgroundResource(R.drawable.bg_ovil_light);
                } else {
                    tv_threetext.setTextColor(Color.parseColor("#bababa"));
                    three_point.setBackgroundResource(R.drawable.bg_ovil_normal);
                }
                tv_threetext.setText(threearray.get(0).toString());
            } else if (jsonArray.length() == 4) {
                rl_one.setVisibility(View.VISIBLE);
                rl_two.setVisibility(View.VISIBLE);
                rl_three.setVisibility(View.VISIBLE);
                rl_four.setVisibility(View.VISIBLE);
                JSONArray onearray = jsonArray.getJSONArray(0);
                if (onearray.get(1).toString().equals("1")) {
                    //完成的
                    tv_onetext.setTextColor(Color.parseColor("#d5aa5f"));
                    one_point.setBackgroundResource(R.drawable.bg_ovil_light);
                } else {
                    tv_onetext.setTextColor(Color.parseColor("#bababa"));
                    one_point.setBackgroundResource(R.drawable.bg_ovil_normal);
                }
                tv_onetext.setText(onearray.get(0).toString());
                JSONArray twoarray = jsonArray.getJSONArray(1);
                if (twoarray.get(1).toString().equals("1")) {
                    //完成的
                    tv_twotext.setTextColor(Color.parseColor("#d5aa5f"));
                    two_point.setBackgroundResource(R.drawable.bg_ovil_light);
                } else {
                    tv_twotext.setTextColor(Color.parseColor("#bababa"));
                    two_point.setBackgroundResource(R.drawable.bg_ovil_normal);
                }
                tv_twotext.setText(twoarray.get(0).toString());
                JSONArray threearray = jsonArray.getJSONArray(2);
                if (threearray.get(1).toString().equals("1")) {
                    //完成的
                    tv_threetext.setTextColor(Color.parseColor("#d5aa5f"));
                    three_point.setBackgroundResource(R.drawable.bg_ovil_light);
                } else {
                    tv_threetext.setTextColor(Color.parseColor("#bababa"));
                    three_point.setBackgroundResource(R.drawable.bg_ovil_normal);
                }
                tv_threetext.setText(threearray.get(0).toString());
                JSONArray fourarray = jsonArray.getJSONArray(3);
                if (fourarray.get(1).toString().equals("1")) {
                    //完成的
                    tv_fourtext.setTextColor(Color.parseColor("#d5aa5f"));
                    four_point.setBackgroundResource(R.drawable.bg_ovil_light);
                } else {
                    tv_fourtext.setTextColor(Color.parseColor("#bababa"));
                    four_point.setBackgroundResource(R.drawable.bg_ovil_normal);
                }
                tv_fourtext.setText(fourarray.get(0).toString());
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void OnLevelDetailFialCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
        Log.e("mjasdjss", "------" + respanse);
    }

    @Override
    public void closeLevelDetailProgress() {

    }
}
