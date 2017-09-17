package com.lvgou.distribution.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.lvgou.distribution.utils.MyToast;import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lvgou.distribution.R;
import com.lvgou.distribution.adapter.DistributorDynaAdapter;
import com.lvgou.distribution.adapter.TeacherDynaAdapter;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.fragment.TeacherDynaminFragment;
import com.lvgou.distribution.inter.AdapterToFraImpl;
import com.lvgou.distribution.presenter.CircleUnFollowPresenter;
import com.lvgou.distribution.presenter.DistributorHomePresenter;
import com.lvgou.distribution.presenter.HomeTalklistPresenter;
import com.lvgou.distribution.presenter.UseFollowPresenter;
import com.lvgou.distribution.utils.ImageUtils;
import com.lvgou.distribution.utils.ScrollableLayout;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.CircleUnFollowView;
import com.lvgou.distribution.view.DistributorHomeView;
import com.lvgou.distribution.view.HomeTalklistView;
import com.lvgou.distribution.view.UseFollowView;
import com.lvgou.distribution.widget.XListView;
import com.xdroid.common.utils.PreferenceHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Administrator on 2017/4/13.
 */

public class DistributorHomeActivity extends BaseActivity implements View.OnClickListener, XListView.IXListViewListener, DistributorHomeView, HomeTalklistView, UseFollowView, CircleUnFollowView {
    private String seeDistributorId;
    private String distributorid;
    private DistributorHomePresenter distributorHomePresenter;
    private UseFollowPresenter useFollowPresenter;
    private CircleUnFollowPresenter circleUnFollowPresenter;
    private String isfocus;
    private String realName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distributor);
        distributorHomePresenter = new DistributorHomePresenter(this);
        homeTalklistPresenter = new HomeTalklistPresenter(this);
        useFollowPresenter = new UseFollowPresenter(this);
        circleUnFollowPresenter = new CircleUnFollowPresenter(this);
        distributorid = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        seeDistributorId = getIntent().getStringExtra("seeDistributorId");
        if (seeDistributorId.equals(distributorid)) {
            isMe = true;
        } else {
            isMe = false;
        }
        initView();
        onRefresh();
        initClick();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initDatas();

    }

    private void initDatas() {
        distributorid = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        String sign = TGmd5.getMD5("" + distributorid + seeDistributorId);
        showLoadingProgressDialog(this, "");
        distributorHomePresenter.distributorHome(distributorid, seeDistributorId, sign);
    }

    private RelativeLayout rl_back;
    private XListView mListView;
    private DistributorDynaAdapter distributorDynaAdapter;
    private boolean isMe = false;
    private ScrollableLayout sl_root;
    private float avatarTop;
    private TextView tv_spit;//标题
    private float hearderMaxHeight;
    private RelativeLayout rl_title;
    private ImageView im_picture;//用户头像
    private ImageView iv_title;//title背景
    private TextView tv_focus_num;//关注数
    private RelativeLayout rl_focus;
    private TextView tv_fans_num;//粉丝数
    private RelativeLayout rl_fans;
    private ImageView im_certified;//认证讲师
    private TextView tv_name;//用户名
    private ImageView im_sex;//性别
    private ImageView im_level;//等级
    private TextView tv_teach_num;//教授人数
    private TextView tv_sign;//签名
    private RelativeLayout rl_sign;
    private ImageView im_focus;//关注
    private TextView tv_dongtai;//动态
    private ImageView im_sign;
    private RelativeLayout rl_none_one;

    private void initView() {

        rl_title = (RelativeLayout) findViewById(R.id.rl_title);
        rl_title.getBackground().setAlpha(0);
        rl_none_one = (RelativeLayout) findViewById(R.id.rl_none_one);

        im_picture = (ImageView) findViewById(R.id.im_picture);
        iv_title = (ImageView) findViewById(R.id.iv_title);
        tv_focus_num = (TextView) findViewById(R.id.tv_focus_num);
        rl_focus = (RelativeLayout) findViewById(R.id.rl_focus);
        tv_fans_num = (TextView) findViewById(R.id.tv_fans_num);
        rl_fans = (RelativeLayout) findViewById(R.id.rl_fans);
        im_certified = (ImageView) findViewById(R.id.im_certified);
        im_certified.setVisibility(View.GONE);
        tv_name = (TextView) findViewById(R.id.tv_name);
        im_sex = (ImageView) findViewById(R.id.im_sex);
        im_level = (ImageView) findViewById(R.id.im_level);
        tv_teach_num = (TextView) findViewById(R.id.tv_teach_num);
        tv_sign = (TextView) findViewById(R.id.tv_sign);
        rl_sign = (RelativeLayout) findViewById(R.id.rl_sign);
        im_sign = (ImageView) findViewById(R.id.im_sign);
        im_focus = (ImageView) findViewById(R.id.im_focus);
        im_focus.getBackground().setAlpha(255);
        if (isMe) {
            im_sign.setVisibility(View.VISIBLE);
            rl_sign.setClickable(true);
            rl_sign.setEnabled(true);
            im_focus.setVisibility(View.GONE);
        } else {
            im_sign.setVisibility(View.GONE);
            rl_sign.setClickable(false);
            rl_sign.setEnabled(false);
            im_focus.setVisibility(View.VISIBLE);
        }

        tv_dongtai = (TextView) findViewById(R.id.tv_dongtai);

        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        sl_root = (ScrollableLayout) findViewById(R.id.sl_root);
        tv_spit = (TextView) findViewById(R.id.tv_spit);

        mListView = (XListView) findViewById(R.id.list_view);
        distributorDynaAdapter = new DistributorDynaAdapter(this, isMe);
        mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnable(true);
//        mListView.stopLoadMore();
        mListView.setAutoLoadEnable(false);
        mListView.setXListViewListener(this);
        mListView.setRefreshTime(this.getTime());
        mListView.setDivider(null);
        mListView.setAdapter(distributorDynaAdapter);
        sl_root.getHelper().setCurrentScrollableContainer(mListView);
        sl_root.setOnScrollListener(new ScrollableLayout.OnScrollListener() {
            @Override
            public void onScroll(int translationY, int maxY) {
                translationY = -translationY;
                if (avatarTop == 0) {
                    avatarTop = tv_spit.getTop();
                }
                if (0 > avatarTop + translationY) {
                    tv_spit.setVisibility(View.VISIBLE);
                } else {
                    tv_spit.setVisibility(View.GONE);
                }
                if (hearderMaxHeight == 0) {
                    hearderMaxHeight = mListView.getTop();
                }
                int alpha = 0;
                int baseAlpha = 60;
                if (0 > avatarTop + translationY) {
                    alpha = Math.min(255, (int) (Math.abs(avatarTop + translationY) * (255 - baseAlpha) / (hearderMaxHeight - avatarTop) + baseAlpha));
                }
                float zz = (float) 215.0;
                int alp = (int) (255 * alpha / zz);
                rl_title.getBackground().setAlpha(alp);
                tv_spit.setTextColor(Color.argb(alp, 255, 255, 255));
                if (alp >= 225) {
                    alp = 255;
                }
                im_focus.getBackground().setAlpha(255 - alp);
            }
        });
    }

    private void initClick() {
        rl_back.setOnClickListener(this);
        rl_focus.setOnClickListener(this);
        im_focus.setOnClickListener(this);
        rl_sign.setOnClickListener(this);
        rl_fans.setOnClickListener(this);
        distributorDynaAdapter.setAdapterToFraImpl(new AdapterToFraImpl() {
            @Override
            public void doSomeThing(HashMap<String, Object> info) {
                for (int i = 0; i < dataList.size(); i++) {
                    if (dataList.get(i).get("ID").toString().equals(info.get("ID").toString())) {
                        if (info.get("ZanCount").equals("delete")) {
                            dataList.remove(i);
                            distributorDynaAdapter.notifyDataSetChanged();
                            break;
                        } else {
                            dataList.get(i).put("ZanCount", info.get("ZanCount"));
                            dataList.get(i).put("Zaned", info.get("Zaned"));
                        }

                        break;
                    }
                }
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(DistributorHomeActivity.this, NewDynamicDetailsActivity.class);
                intent.putExtra("position", 0);
                intent.putExtra("talkId", dataList.get(position - 1).get("ID").toString());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.im_focus:
                //去关注
                if (isfocus.equals("false")) {
                    //未关注
                    String sign = TGmd5.getMD5("" + distributorid + seeDistributorId);
                    showLoadingProgressDialog(this, "");
                    useFollowPresenter.useFollow(distributorid, seeDistributorId, sign);
                } else {
                    //已经关注
                    showialog();
                }
                break;
            case R.id.rl_focus://关注列表
                Intent intent = new Intent(DistributorHomeActivity.this, MyFocusActivity.class);
                intent.putExtra("seeDistributorId", seeDistributorId);
                intent.putExtra("realName", realName);
                startActivity(intent);
                break;
            case R.id.rl_sign:
                //修改个性签名
                openActivity(PersonalSignActivity.class);
                break;
            case R.id.rl_fans:
                Intent intent2 = new Intent(DistributorHomeActivity.this, MyFansActivity.class);
                intent2.putExtra("seeDistributorId", seeDistributorId);
                intent2.putExtra("realName", realName);
                startActivity(intent2);
                break;
        }
    }

    private void showialog() {
        LinearLayout.LayoutParams pm = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_yesorno, null);//这里的R.layout.alertdialog即为你自定义的布局文件
        TextView text1 = (TextView) view.findViewById(R.id.text1);
        text1.setText("确定抛弃TA吗？");
        View view_line = view.findViewById(R.id.view_line);
        TextView yes = (TextView) view.findViewById(R.id.yes);
        TextView cancle = (TextView) view.findViewById(R.id.cancle);
        cancle.setText("再想想");

        final AlertDialog mAlertDialog = builder.create();
        mAlertDialog.getWindow().setBackgroundDrawableResource(R.color.touming);
        mAlertDialog.show();
        mAlertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        mAlertDialog.getWindow().setContentView(view, pm);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.dismiss();
            }
        });
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sign = TGmd5.getMD5("" + distributorid + seeDistributorId);
                showLoadingProgressDialog(DistributorHomeActivity.this, "");
                circleUnFollowPresenter.circleUnFollow(distributorid, seeDistributorId, sign);
                mAlertDialog.dismiss();
            }
        });
    }

    @Override
    public void onRefresh() {
        page = 1;
        dataList.clear();
        initDatas2();
    }

    @Override
    public void onLoadMore() {
        page++;
        initDatas2();
    }

    private boolean isFirst = false;
    public ArrayList<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();
    public ArrayList<HashMap<String, Object>> dataListTmp = new ArrayList<HashMap<String, Object>>();
    private int page = 1;
    private HomeTalklistPresenter homeTalklistPresenter;

    private void initDatas2() {
        String sign = TGmd5.getMD5(distributorid + seeDistributorId + page);
        if (isFirst) {
            showLoadingProgressDialog(this, "");
        }
        homeTalklistPresenter.homeTalklist(distributorid, seeDistributorId, page, sign);

    }


    @Override
    public void OnDistributorHomeSuccCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
        try {
            JSONObject jsb = new JSONObject(respanse);
            JSONArray jsa = new JSONArray(jsb.getString("result"));
            JSONObject distributorModeljsa = jsa.getJSONObject(0);
            if (distributorModeljsa.get("Attr").toString().equals("1")) {
                //男
                im_sex.setBackgroundResource(R.mipmap.icon_man_home);
            } else if (distributorModeljsa.get("Attr").toString().equals("2")) {
                //女
                im_sex.setBackgroundResource(R.mipmap.icon_woman_home);
            }
            tv_teach_num.setText("学员" + distributorModeljsa.get("AttrLine").toString() + "人");
            realName = distributorModeljsa.get("RealName").toString();
            tv_name.setText(distributorModeljsa.get("RealName").toString());
            tv_spit.setText(distributorModeljsa.get("RealName").toString());
            tv_focus_num.setText(distributorModeljsa.get("FollowCount").toString());
            tv_fans_num.setText(distributorModeljsa.get("FansCount").toString());
            String star = distributorModeljsa.get("Star").toString();
            if (star.equals("1")) {
                im_level.setVisibility(View.VISIBLE);
                im_level.setBackgroundResource(R.mipmap.level_one_icon);
            } else if (star.equals("2")) {
                im_level.setVisibility(View.VISIBLE);
                im_level.setBackgroundResource(R.mipmap.level_two_icon);
            } else if (star.equals("3")) {
                im_level.setVisibility(View.VISIBLE);
                im_level.setBackgroundResource(R.mipmap.level_three_icon);
            } else if (star.equals("4")) {
                im_level.setVisibility(View.VISIBLE);
                im_level.setBackgroundResource(R.mipmap.level_four_icon);
            } else if (star.equals("5")) {
                im_level.setVisibility(View.VISIBLE);
                im_level.setBackgroundResource(R.mipmap.level_five_icon);
            } else if (star.equals("6")) {
                im_level.setVisibility(View.VISIBLE);
                im_level.setBackgroundResource(R.mipmap.level_six_icon);
            } else if (star.equals("7")) {
                im_level.setVisibility(View.VISIBLE);
                im_level.setBackgroundResource(R.mipmap.level_seven_icon);
            } else if (star.equals("8")) {
                im_level.setVisibility(View.VISIBLE);
                im_level.setBackgroundResource(R.mipmap.level_eight_icon);
            } else if (star.equals("9")) {
                im_level.setVisibility(View.VISIBLE);
                im_level.setBackgroundResource(R.mipmap.level_nine_icon);
            } else if (star.equals("10")) {
                im_level.setVisibility(View.VISIBLE);
                im_level.setBackgroundResource(R.mipmap.level_ten_icon);
            } else {
                im_level.setVisibility(View.GONE);
            }
//            Glide.with(DistributorHomeActivity.this).load(Url.ROOT + distributorModeljsa.get("PicUrl").toString()).into(iv_title);
            Glide.with(DistributorHomeActivity.this).load(ImageUtils.getInstance().getPath(seeDistributorId)).error(R.mipmap.teacher_default_head).into(im_picture);
            isfocus = jsa.get(1).toString();
            if (isfocus.equals("false")) {
                //未关注
                im_focus.setBackgroundResource(R.mipmap.focus_icon);
            } else {
                //已经关注
                im_focus.setBackgroundResource(R.mipmap.focusedss_icon);
            }

            String signStr = jsa.get(2).toString();
            tv_sign.setText(signStr);
            tv_dongtai.setText("动态 " + distributorModeljsa.get("FengwenCount").toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void OnDistributorHomeFialCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
    }

    @Override
    public void closeDistributorHomeProgress() {

    }

    @Override
    public void OnHomeTalklistSuccCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
        mListView.stopRefresh();
        isFirst = true;
        try {
            JSONObject jsonObject = new JSONObject(respanse);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("result"));
            JSONObject jsonObject1 = new JSONObject(jsonArray.get(0).toString());
            JSONArray jsonArray1 = new JSONArray(jsonObject1.getString("Data"));

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
           /* if (dataListTmp.size() < Integer.parseInt(jsonObject1.get("DataCount").toString())) {
                mHandler.sendEmptyMessage(2);
            } else {
                mHandler.sendEmptyMessage(1);
            }*/
            if (page == Integer.parseInt(jsonObject1.get("DataPageCount").toString())) {
                //如果当前页等于总页数，就没有下一页
                mHandler.sendEmptyMessage(2);
            } else {
                //有下一页
                mHandler.sendEmptyMessage(1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void OnHomeTalklistFialCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
    }

    @Override
    public void closeHomeTalklistProgress() {

    }

    private final mainHandler mHandler = new mainHandler(this);


    private static class mainHandler extends Handler {
        private final WeakReference<DistributorHomeActivity> mActivity;

        public mainHandler(DistributorHomeActivity activity) {
            mActivity = new WeakReference<DistributorHomeActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            DistributorHomeActivity activity = mActivity.get();
            if (activity != null) {
                activity.flushDataList(msg);
            }
        }
    }

    public void flushDataList(Message msg) {
        if (msg.what == 1) {
            Log.e("sjkdfhjkss", "-----------1" );
            dataList.addAll(dataListTmp);
            if (dataList != null) {
                distributorDynaAdapter.setData(dataList);
                distributorDynaAdapter.notifyDataSetChanged();
                mListView.setPullLoadEnable(true);
            } else {
                mListView.setPullLoadEnable(false);
            }
            if (dataList.size() == 0) {
                mListView.setPullLoadEnable(false);
                rl_none_one.setVisibility(View.VISIBLE);
            } else {
                mListView.setPullLoadEnable(true);
                rl_none_one.setVisibility(View.GONE);
            }

            dataListTmp.clear();
            mListView.stopRefresh();
        } else if (msg.what == 2) {
            Log.e("sjkdfhjkss", "-----------2" );
            dataList.addAll(dataListTmp);
            distributorDynaAdapter.setData(dataList);
            distributorDynaAdapter.notifyDataSetChanged();
            dataListTmp.clear();
            if (dataList.size() == 0) {
                rl_none_one.setVisibility(View.VISIBLE);
            } else {
                rl_none_one.setVisibility(View.GONE);
            }
            mListView.setPullLoadEnable(false);
            mListView.stopLoadMore();
        }
    }

    @Override
    public void OnUseFollowSuccCallBack(String state, String respanse) {
        //关注好友成功
        closeLoadingProgressDialog();
        isfocus = "true";
        im_focus.setBackgroundResource(R.mipmap.focusedss_icon);
    }

    @Override
    public void OnUseFollowFialCallBack(String state, String respanse) {
        //关注好友失败
        closeLoadingProgressDialog();
        MyToast.makeText(this, "" + respanse, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void closeUseFollowProgress() {

    }

    @Override
    public void OnCircleUnFollowSuccCallBack(String state, String respanse) {
        //取消关注好友失败
        closeLoadingProgressDialog();
        isfocus = "false";
        im_focus.setBackgroundResource(R.mipmap.focus_icon);
    }

    @Override
    public void OnCircleUnFollowFialCallBack(String state, String respanse) {
        //取消关注好友失败
        closeLoadingProgressDialog();
        MyToast.makeText(this, "" + respanse, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void closeCircleUnFollowProgress() {

    }
}
