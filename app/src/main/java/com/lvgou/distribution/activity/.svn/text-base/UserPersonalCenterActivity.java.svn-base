package com.lvgou.distribution.activity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.adapter.MenuAdapter;
import com.lvgou.distribution.bean.FengCircleDynamicBean;
import com.lvgou.distribution.bean.MayknowpersonBean;
import com.lvgou.distribution.bean.MenuBean;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.fragment.UserPersonConcernFragment;
import com.lvgou.distribution.fragment.UserPersonDynamicFragment;
import com.lvgou.distribution.fragment.UserPersonFansFragment;
import com.lvgou.distribution.fragment.UserPersonStudyFragment;
import com.lvgou.distribution.presenter.DistributormainPresenter;
import com.lvgou.distribution.utils.ImageUtils;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.CircleImageView;
import com.lvgou.distribution.view.HorizontalListView;
import com.lvgou.distribution.view.MoguLayout;
import com.lvgou.distribution.view.UserPersonalView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.common.utils.PreferenceHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/21.
 * 个人主页
 */
public class UserPersonalCenterActivity extends BaseActivity implements UserPersonalView, MoguLayout.LayoutScrollerListener, UserPersonStudyFragment.OnArticleSelectedListener, UserPersonDynamicFragment.OnArticleSelectedListener, UserPersonConcernFragment.OnArticleSelectedListener, UserPersonFansFragment.OnArticleSelectedListener {

    @ViewInject(R.id.img_back)
    private ImageView img_back;
    @ViewInject(R.id.id_viewpager)
    private ViewPager viewPager;
    @ViewInject(R.id.id_horizontalmenu)
    private HorizontalListView menu;
    @ViewInject(R.id.img_user_head)
    private CircleImageView img_user_head;
    @ViewInject(R.id.img_teacher_label)
    private ImageView img_teacher_label;
    @ViewInject(R.id.txt_user_name)
    private TextView txt_user_name;
    @ViewInject(R.id.img_cirlce_follow)
    private ImageView img_cirlce_follow;
    @ViewInject(R.id.top_cirlce_follow)
    private ImageView top_cirlce_follow;
    @ViewInject(R.id.img_user_gender)
    private ImageView img_user_gender;
    @ViewInject(R.id.img_user_autonym)
    private ImageView img_user_autonym;
    @ViewInject(R.id.rl_top_title)
    private RelativeLayout rl_top_title;
    @ViewInject(R.id.top_user_name)
    private TextView top_user_name;
    @ViewInject(R.id.scroll_layout)
    private MoguLayout scroll_layout;
    @ViewInject(R.id.txt_signature)
    private TextView txt_signature;
    @ViewInject(R.id.img_teacher_certified)
    private ImageView img_teacher_certified;
    private List<Fragment> fragmentList = new ArrayList<>();
    private String distributorid;
    List<MenuBean> menulist;
    int userType;
    String seeDistributorId = "";
    private String distributormain_sign = "";
    MenuAdapter menuAdapter;
    DistributormainPresenter distributormainPresenter;
    public static int followed = 0;
    int friendId = 0;
    private Dialog dialog_del_can, dialog_quit;// 取消，删除弹框
    private FengCircleDynamicBean adapterDynamicBean;
    private StateReceiver stateReceiver;
    private IntentFilter intentFilter;

    private ScrollView scrollView;
    private View layout_commentlist_emptyView;

    @Override
    public void showTopView(int y, int height) {
        int progress = (int) (new Float(y) * (255 / new Float(height)));//255
        rl_top_title.getBackground().setAlpha(progress);
        rl_top_title.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideTopView() {
        rl_top_title.setVisibility(View.GONE);
    }

    public class StateReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                FengCircleDynamicBean circleDynamicBean = (FengCircleDynamicBean) intent.getSerializableExtra("itemData");
                if (circleDynamicBean != null) {
                    if (circleDynamicBean.getDistributorID() == friendId) {
                        followed = 1;
                        img_cirlce_follow.setImageResource(R.mipmap.circle_follow_already);
                        top_cirlce_follow.setImageResource(R.mipmap.circle_follow_already);
                    } else {
                        img_cirlce_follow.setImageResource(R.mipmap.circle_add_follow);
                        top_cirlce_follow.setImageResource(R.mipmap.circle_add_follow);
                        followed = 0;
                    }
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_personalcenter);
        ViewUtils.inject(this);
        scroll_layout.setListener(this);
        distributorid = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);

        Intent intent = getIntent();
        seeDistributorId = String.valueOf(intent.getIntExtra("distributorid", 0));
        if(!distributorid.equals(seeDistributorId)){
            txt_signature.setEnabled(false);
            txt_signature.setCompoundDrawables(null, null,null,null);
        }
        distributormainPresenter = new DistributormainPresenter(this);
        distributormainPresenter.attach(this);
        showLoadingProgressDialog(UserPersonalCenterActivity.this, "");
        distributormain_sign = TGmd5.getMD5(distributorid + seeDistributorId);
        distributormainPresenter.distributormain(distributorid, seeDistributorId, distributormain_sign);
        menulist = new ArrayList();

        menuAdapter = new MenuAdapter(this);
        menuAdapter.setMenuList(menulist);
        menu.setAdapter(menuAdapter);
    }

    private void initFragments() {
        if (userType == 3) {
            UserPersonStudyFragment fragment_study = new UserPersonStudyFragment();
            fragmentList.add(fragment_study);
        }
        UserPersonDynamicFragment fragment_dynamic = new UserPersonDynamicFragment();
        fragmentList.add(fragment_dynamic);
        UserPersonConcernFragment fragment_concern = new UserPersonConcernFragment();
        fragmentList.add(fragment_concern);
        UserPersonFansFragment fragment_fans = new UserPersonFansFragment();
        fragmentList.add(fragment_fans);
    }

    @OnClick({R.id.img_cirlce_follow,R.id.txt_signature, R.id.img_back, R.id.bg_img_back, R.id.top_cirlce_follow})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.txt_signature:
                Intent intent_signa=new Intent(this,SignatureActivity.class);
                intent_signa.putExtra("signature",txt_signature.getText());
                startActivityForResult(intent_signa, 1);
                break;
            case R.id.img_back:
                Intent intent = new Intent();
                intent.setAction("com.distribution.tugou.state");
                intent.putExtra("itemData", adapterDynamicBean);
                sendBroadcast(intent);
                finish();
                break;
            case R.id.bg_img_back:
                Intent intent1 = new Intent();
                intent1.setAction("com.distribution.tugou.state");
                intent1.putExtra("itemData", adapterDynamicBean);
                sendBroadcast(intent1);
                finish();
                break;
            case R.id.img_cirlce_follow:
                if (followed == 1) {
                    showQuitDialog(String.valueOf(friendId));
                } else {
                    String sign = TGmd5.getMD5(distributorid + friendId);
                    distributormainPresenter.CircleFollow(distributorid, String.valueOf(friendId), sign);
                }
                break;
            case R.id.top_cirlce_follow:
                if (followed == 1) {
                    showQuitDialog(String.valueOf(friendId));
                } else {
                    String sign = TGmd5.getMD5(distributorid + friendId);
                    distributormainPresenter.CircleFollow(distributorid, String.valueOf(friendId), sign);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            txt_signature.setText(data.getStringExtra("signature"));
        }
    }

    /**
     * id
     * 删除取消弹框
     */
    public void showQuitDialog(final String id) {
        dialog_del_can = new Dialog(this, R.style.Mydialog);
        View view1 = View.inflate(this,
                R.layout.dialog_quit_show, null);
        Button sure = (Button) view1.findViewById(R.id.sure);
        Button cancle = (Button) view1.findViewById(R.id.cancle);
        TextView tv_title = (TextView) view1.findViewById(R.id.tv_title);
        tv_title.setText(getResources().getString(R.string.text_cancel_follow_hint));
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_del_can.dismiss();
                String sign = TGmd5.getMD5(distributorid + id);
                if (followed == 1) {
                    distributormainPresenter.CircleUnFollow(distributorid, String.valueOf(friendId), sign);
                } else {
                    distributormainPresenter.CircleFollow(distributorid, String.valueOf(friendId), sign);
                }
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_del_can.dismiss();
            }
        });
        dialog_del_can.setContentView(view1);
        dialog_del_can.show();
    }

    @Override
    public String onArticleSelected() {
        return distributorid;
    }

    @Override
    public String getseeDistributorId() {
        return seeDistributorId;
    }

    @Override
    public void updateFollowState(String Followed) {
        if (Followed.equals("1")) {
            followed = 1;
            img_cirlce_follow.setImageResource(R.mipmap.yiguanzhu);
            top_cirlce_follow.setImageResource(R.mipmap.yiguanzhu);
        } else {
            followed = 0;
            img_cirlce_follow.setImageResource(R.mipmap.circle_add_follow);
            top_cirlce_follow.setImageResource(R.mipmap.circle_add_follow);
        }
    }

    @Override
    public void distributormainResponse(String resonpse) {
        try {
            JSONObject jsonObject = new JSONObject(resonpse);
            int status = jsonObject.getInt("status");
            closeLoadingProgressDialog();
            if (status == 1) {
                menulist.clear();
                JSONArray jsonArray = jsonObject.getJSONArray("result");
                if (null != jsonArray && jsonArray.length() > 0) {
                    JSONObject jsonObject1 = (JSONObject) jsonArray.get(0);
                    if (jsonArray.length() > 2) {
                        String signature = (String) jsonArray.get(2);
                        if (!"".equals(signature) && signature != null) {
                            txt_signature.setText(signature);
                        } else {
                            txt_signature.setText(getResources().getString(R.string.text_hint_signature));
                        }
                    }
                    boolean isfollow = (boolean) jsonArray.get(1);
                    if (isfollow) {
                        followed = 1;
                        img_cirlce_follow.setImageResource(R.mipmap.yiguanzhu);
                        top_cirlce_follow.setImageResource(R.mipmap.yiguanzhu);
                    } else {
                        followed = 0;
                        img_cirlce_follow.setImageResource(R.mipmap.circle_add_follow);
                        top_cirlce_follow.setImageResource(R.mipmap.circle_add_follow);
                    }
                    MayknowpersonBean mayknowpersonBean = new MayknowpersonBean();
                    friendId = jsonObject1.getInt("ID");
                    adapterDynamicBean = new FengCircleDynamicBean();
                    adapterDynamicBean.setDistributorID(friendId);
                    adapterDynamicBean.setFollowed(String.valueOf(followed));
                    adapterDynamicBean.setID("");
                    mayknowpersonBean.setID(jsonObject1.getInt("ID"));
                    mayknowpersonBean.setLoginName(jsonObject1.getString("LoginName"));
                    mayknowpersonBean.setPassWord(jsonObject1.getString("PassWord"));
                    mayknowpersonBean.setState(jsonObject1.getInt("State"));
                    mayknowpersonBean.setRealName(jsonObject1.getString("RealName"));
                    mayknowpersonBean.setServiceRealName(jsonObject1.getString("ServiceRealName"));
                    mayknowpersonBean.setCompanyName(jsonObject1.getString("CompanyName"));
                    mayknowpersonBean.setMobile(jsonObject1.getString("Mobile"));
                    mayknowpersonBean.setParentID(jsonObject1.getInt("ParentID"));
                    mayknowpersonBean.setTuanBi(jsonObject1.getInt("TuanBi"));
                    mayknowpersonBean.setRatio(jsonObject1.getInt("Ratio"));
                    mayknowpersonBean.setStar(jsonObject1.getInt("Star"));
                    mayknowpersonBean.setCountryPath(jsonObject1.getString("CountryPath"));
                    mayknowpersonBean.setUserType(jsonObject1.getInt("UserType"));
                    mayknowpersonBean.setLoginCount(jsonObject1.getInt("LoginCount"));
                    mayknowpersonBean.setAttr(jsonObject1.getInt("Attr"));
                    mayknowpersonBean.setFengwenCount(jsonObject1.getInt("FengwenCount"));
                    mayknowpersonBean.setFollowCount(jsonObject1.getInt("FollowCount"));
                    mayknowpersonBean.setFansCount(jsonObject1.getInt("FansCount"));
                    mayknowpersonBean.setPicUrl(jsonObject1.getString("PicUrl"));
                    mayknowpersonBean.setCreateTime(jsonObject1.getString("CreateTime"));
                    mayknowpersonBean.setLastLoginTime(jsonObject1.getString("LastLoginTime"));
                    userType = mayknowpersonBean.getUserType();
                    txt_user_name.setText(mayknowpersonBean.getRealName());
                    top_user_name.setText(mayknowpersonBean.getRealName());
                    if (mayknowpersonBean.getAttr() == 1) {
                        img_user_gender.setImageResource(R.mipmap.icon_man_home);
                    } else {
                        img_user_gender.setImageResource(R.mipmap.icon_woman_home);
                    }
//                    if (mayknowpersonBean.getState() == 5) {
//                        img_user_autonym.setVisibility(View.VISIBLE);
//                    } else {
//                        img_user_autonym.setVisibility(View.GONE);
//                    }
//                    Glide.with(this).load(ImageUtils.getInstance().getPath(String.valueOf(mayknowpersonBean.getID())))//
//                            .placeholder(R.mipmap.ic_launcher)//
//                            .error(R.mipmap.ic_launcher)//
//                            .into(img_user_head);
                    DisplayImageOptions options = new DisplayImageOptions.Builder()
                            .showImageOnLoading(R.mipmap.faxian_user_head) //设置图片在下载期间显示的图片
                            .showImageForEmptyUri(R.mipmap.faxian_user_head)//设置图片Uri为空或是错误的时候显示的图片
                            .showImageOnFail(R.mipmap.faxian_user_head).build(); //设置图片加载/解码过程中错误时候显示的图片
                    String path = ImageUtils.getInstance().getPath(String.valueOf(mayknowpersonBean.getID()));
                    ImageLoader.getInstance().displayImage(path, img_user_head, options);
                    if (mayknowpersonBean.getUserType() == 3) {
                        img_teacher_certified.setVisibility(View.VISIBLE);
                        img_teacher_label.setVisibility(View.VISIBLE);
                        MenuBean menuBean = new MenuBean();
                        menuBean.setTitle("课程");
                        menuBean.setNumer(mayknowpersonBean.getTuanBi());
                        menulist.add(menuBean);
                        MenuBean menuBean1 = new MenuBean();
                        menuBean1.setTitle("动态");
                        menuBean1.setNumer(mayknowpersonBean.getFengwenCount());
                        menulist.add(menuBean1);
                        MenuBean menuBean2 = new MenuBean();
                        menuBean2.setTitle("关注");
                        menuBean2.setNumer(mayknowpersonBean.getFollowCount());
                        menulist.add(menuBean2);
                        MenuBean menuBean3 = new MenuBean();
                        menuBean3.setTitle("粉丝");
                        menuBean3.setNumer(mayknowpersonBean.getFansCount());
                        menulist.add(menuBean3);
                    } else {
                        MenuBean menuBean1 = new MenuBean();
                        menuBean1.setTitle("动态");
                        menuBean1.setNumer(mayknowpersonBean.getFengwenCount());
                        menulist.add(menuBean1);
                        MenuBean menuBean2 = new MenuBean();
                        menuBean2.setTitle("关注");
                        menuBean2.setNumer(mayknowpersonBean.getFollowCount());
                        menulist.add(menuBean2);

                        MenuBean menuBean3 = new MenuBean();
                        menuBean3.setTitle("粉丝");
                        menuBean3.setNumer(mayknowpersonBean.getFansCount());
                        menulist.add(menuBean3);
                        img_teacher_label.setVisibility(View.GONE);  img_teacher_certified.setVisibility(View.GONE);
                    }
                    menuAdapter.setMenuList(menulist);
                    menuAdapter.setAdaptercallback(new MenuAdapter.Adaptercallback() {
                        @Override
                        public void Itemclick(int position) {
                            viewPager.setCurrentItem(position);
                        }
                    });
                    menuAdapter.notifyDataSetChanged();
                }


                if (!distributorid.equals(String.valueOf(friendId))) {
                    img_cirlce_follow.setVisibility(View.VISIBLE);
                    top_cirlce_follow.setVisibility(View.VISIBLE);
                } else {
                    img_cirlce_follow.setVisibility(View.INVISIBLE);
                    top_cirlce_follow.setVisibility(View.INVISIBLE);
                }


                initFragments();
                viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

                    @Override
                    public int getCount() {
                        if (userType == 3) {
                            return 4;
                        } else {
                            return 3;
                        }
                    }

                    @Override
                    public Fragment getItem(int position) {
                        return fragmentList.get(position);
                    }
                });
                viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

                    @Override
                    public void onPageSelected(int position) {
                        super.onPageSelected(position);
                        ((MenuAdapter) menu.getAdapter()).setCurOrderItem(position);
                    }

                });
                viewPager.setOffscreenPageLimit(4);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void followResponse(String resonpse, String position) {
        try {
            JSONObject jsonObject = new JSONObject(resonpse);
            int status = (int) jsonObject.getInt("status");
            if (status == 1) {
                if (position.equals("circlefollow")) {
                    followed = 1;
                    img_cirlce_follow.setImageResource(R.mipmap.yiguanzhu);
                    top_cirlce_follow.setImageResource(R.mipmap.yiguanzhu);
                } else {
                    img_cirlce_follow.setImageResource(R.mipmap.circle_add_follow);
                    top_cirlce_follow.setImageResource(R.mipmap.circle_add_follow);
                    followed = 0;
                }
                adapterDynamicBean.setFollowed(String.valueOf(followed));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void excuteFailedCallBack(String resonpse) {
        closeLoadingProgressDialog();
        showStop(resonpse);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent();
        intent.setAction("com.distribution.tugou.state");
        intent.putExtra("itemData", adapterDynamicBean);
        sendBroadcast(intent);
        finish();
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (distributormainPresenter != null) {
            distributormainPresenter.dettach();
        }
    }


    public void showStop(String str) {
        dialog_quit = new Dialog(UserPersonalCenterActivity.this, R.style.Mydialog);
        View view1 = View.inflate(UserPersonalCenterActivity.this, R.layout.dialog_show_check_stop, null);
        Button sure = (Button) view1.findViewById(R.id.sure);
        TextView tv_title = (TextView) view1.findViewById(R.id.tv_title);
        tv_title.setText(str);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_quit.dismiss();
                finish();
            }
        });
        dialog_quit.setContentView(view1);
        dialog_quit.show();
    }

}
