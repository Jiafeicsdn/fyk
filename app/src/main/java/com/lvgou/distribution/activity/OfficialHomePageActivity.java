package com.lvgou.distribution.activity;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.adapter.ToutiaoFengwenAdapter;
import com.lvgou.distribution.bean.FengCircleDynamicBean;
import com.lvgou.distribution.bean.MayknowpersonBean;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.entity.CircleRecommentEntity;
import com.lvgou.distribution.inter.OnClassifyPostionOneClickListener;
import com.lvgou.distribution.presenter.UserDynamicPresenter;
import com.lvgou.distribution.refresh.PullToRefreshBase;
import com.lvgou.distribution.refresh.PullToRefreshScrollView;
import com.lvgou.distribution.utils.ImageUtils;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.CircleImageView;
import com.lvgou.distribution.view.DragScaleImageView;
import com.lvgou.distribution.view.ListViewForScrollView;
import com.lvgou.distribution.view.MytalklistView;
import com.lvgou.distribution.viewholder.CircleRecommentViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.common.utils.PreferenceHelper;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.functions.holder.ListViewDataAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 官方主页
 */
public class OfficialHomePageActivity extends BaseActivity implements MytalklistView,ToutiaoFengwenAdapter.ItemClickListener, View.OnClickListener {

    private TextView txt_user_name;
    @ViewInject(R.id.top_user_name)
    private TextView top_user_name;
    private ImageView img_user_gender;
    private ImageView img_user_autonym;
    private CircleImageView img_user_head;

    private ImageView img_back;
    @ViewInject(R.id.top_img_back)
    private ImageView top_img_back;
    private DragScaleImageView drag_imgview;
    private ImageView img_cirlce_follow;
    @ViewInject(R.id.top_cirlce_follow)
    private ImageView top_cirlce_follow;
    @ViewInject(R.id.rl_top_title)
    private RelativeLayout rl_top_title;
    @ViewInject(R.id.pull_refresh_scroller)
    private PullToRefreshScrollView pullToRefreshScrollView;

    private TextView txt_signature;
    private ScrollView scrollView;
    private String distributorid;
    private int pageindex = 1;
    private String distributormain_sign = "";
    boolean mIsUp;// 是否上拉加载
    private int total_page;// 总页数，当达到 最大页数，提示最后一页，不在请求
    String seeDistributorId = "";
    public static int followed = 0;
    private UserDynamicPresenter userDynamicPresenter;
    private FengCircleDynamicBean adapterDynamicBean;
//    private ListViewDataAdapter<CircleRecommentEntity> circleRecommentEntityListViewDataAdapter;
private ToutiaoFengwenAdapter fengwenAdapter;
    private CircleRecommentEntity circleRecommentEntity = null;
    private Dialog dialog_del_can;// 取消，删除弹框
    private View topView;
    private RelativeLayout empty_view;
    private boolean isflag = false;
    private ListViewForScrollView listView;
    private ViewTreeObserver mViewTreeObserver;
    private int topviewHeight;

    @TargetApi(Build.VERSION_CODES.M)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_official_detail);
        ViewUtils.inject(this);

        userDynamicPresenter = new UserDynamicPresenter(this);

        initView();
        scrollView = pullToRefreshScrollView.getRefreshableView();
        scrollView.addView(topView);
        mViewTreeObserver = drag_imgview.getViewTreeObserver();
        mViewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                // TODO Auto-generated method stub
                drag_imgview.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                topviewHeight = drag_imgview.getHeight() - 100;
                System.out.println("onGlobalLayout height=" + drag_imgview.getHeight());
            }
        });
        distributorid = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        Bundle bundle = getIntent().getExtras();
        seeDistributorId = bundle.getString("seeDistributorId");
        if(!distributorid.equals(seeDistributorId)){
            txt_signature.setEnabled(false);
            txt_signature.setCompoundDrawables(null, null,null,null);
        }
        initViewHolder();
        initCreateView();


//        showLoadingProgressDialog(OfficialHomePageActivity.this, "");

        distributormain_sign = TGmd5.getMD5(distributorid + seeDistributorId);
        userDynamicPresenter.distributormain(distributorid, seeDistributorId, distributormain_sign);

        String sign = TGmd5.getMD5(distributorid + seeDistributorId + pageindex);
        userDynamicPresenter.mytalklist(distributorid, seeDistributorId, pageindex, sign);

        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                int scrollY = scrollView.getScrollY();
                if (scrollY <= 0) {
                    rl_top_title.setVisibility(View.GONE);
                } else if (scrollY > 0 && scrollY <= topviewHeight && scrollY != 0) {
                    int progress = (int) (new Float(scrollY) * (255 / new Float(topviewHeight)));//255
                    rl_top_title.getBackground().setAlpha(progress);
                    rl_top_title.setVisibility(View.VISIBLE);
                }
            }
        });
//        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//
//            @Override
//            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                if (scrollY <= 0) {
//                    rl_top_title.setVisibility(View.GONE);
//                } else if (scrollY>0&&scrollY <= topviewHeight && scrollY != 0) {
//                    int progress = (int) (new Float(scrollY) * (255 / new Float(topviewHeight)));//255
//                    rl_top_title.getBackground().setAlpha(progress);
//                    rl_top_title.setVisibility(View.VISIBLE);
//                }
//            }
//
//        });


//        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//                switch (scrollState) {
//                    case SCROLL_STATE_IDLE:
//                        boolean toBottom = view.getLastVisiblePosition() == view.getCount() - 1;
//                        break;
//                }
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                int h = getScrollY();//滚动距离
//                if(h==0){
//                    rl_top_title.setVisibility(View.GONE);
//                }else if(h<=topviewHeight&&h!=0){
//                        int progress = (int) (new Float(h) * (255/new Float(topviewHeight)));//255
//                        rl_top_title.getBackground().setAlpha(progress);
//                        rl_top_title.setVisibility(View.VISIBLE);
//                    }
//                }
//        });
    }

//    public int getScrollY() {
//        View c = listView.getChildAt(0);
//        if (c == null) {
//            return 0;
//        }
//        int firstVisiblePosition = listView.getFirstVisiblePosition();
//        int top = c.getTop();
//        return top;
//    }

    public void initView() {
        topView = LayoutInflater.from(this).inflate(R.layout.user_home_top_view, null);
        drag_imgview = (DragScaleImageView) topView.findViewById(R.id.drag_imgview);
        img_cirlce_follow = (ImageView) topView.findViewById(R.id.img_cirlce_follow);
        txt_signature = (TextView) topView.findViewById(R.id.txt_signature);

        txt_signature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_signa = new Intent(OfficialHomePageActivity.this, SignatureActivity.class);
                intent_signa.putExtra("signature", txt_signature.getText());
                startActivityForResult(intent_signa, 1);
            }
        });
//        img_cirlce_follow.setVisibility(View.VISIBLE);
        img_cirlce_follow.setOnClickListener(this);
        txt_user_name = (TextView) topView.findViewById(R.id.txt_user_name);
        img_back = (ImageView) topView.findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        img_user_head = (CircleImageView) topView.findViewById(R.id.img_user_head);
        img_user_gender = (ImageView) topView.findViewById(R.id.img_user_gender);
        img_user_autonym = (ImageView) topView.findViewById(R.id.img_user_autonym);

        listView = (ListViewForScrollView) topView.findViewById(R.id.listview);
        empty_view = (RelativeLayout) topView.findViewById(R.id.rl_none);
    }

    @OnClick({R.id.top_cirlce_follow, R.id.top_img_back})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.top_img_back:
                finish();
                break;
            case R.id.top_cirlce_follow:
                if (followed == 1) {
                    showQuitDialog(String.valueOf(seeDistributorId));
                } else {
                    String sign = TGmd5.getMD5(distributorid + seeDistributorId);
                    userDynamicPresenter.CircleFollow(distributorid, seeDistributorId, sign);
                }
                break;
        }
    }

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
                    userDynamicPresenter.CircleUnFollow(distributorid, seeDistributorId, sign);
                } else {
                    userDynamicPresenter.CircleFollow(distributorid, seeDistributorId, sign);
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

    /**
     * 下拉刷新 抽取
     */
    public void initCreateView() {
        pullToRefreshScrollView.getLoadingLayoutProxy(false, true).setPullLabel("下拉加载更多");
        pullToRefreshScrollView.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载");
        pullToRefreshScrollView.getLoadingLayoutProxy(false, true).setReleaseLabel("释放开始加载");
        pullToRefreshScrollView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        pullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                String label = DateUtils.formatDateTime(OfficialHomePageActivity.this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                pullToRefreshScrollView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                mIsUp = false;
                pageindex = 1;
                String sign = TGmd5.getMD5(distributorid + seeDistributorId + pageindex);
                userDynamicPresenter.mytalklist(distributorid, seeDistributorId, pageindex, sign);

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                mIsUp = true;
                if (pageindex < total_page) {
                    pageindex += 1;
                    String sign = TGmd5.getMD5(distributorid + seeDistributorId + pageindex);
                    userDynamicPresenter.mytalklist(distributorid, seeDistributorId, pageindex, sign);

                } else {
                    MyToast.show(OfficialHomePageActivity.this, "没有更多数据");
                    new CancleRefreshTask().execute();
                }
            }
        });
    }


    public void initViewHolder() {
//        circleRecommentEntityListViewDataAdapter = new ListViewDataAdapter<CircleRecommentEntity>();
//        circleRecommentEntityListViewDataAdapter.setViewHolderClass(this, CircleRecommentViewHolder.class);
//        CircleRecommentViewHolder.setOnClassifyPostionClickListener(this);
//        listView.setAdapter(circleRecommentEntityListViewDataAdapter);
        fengwenAdapter=new ToutiaoFengwenAdapter(this);
        fengwenAdapter.setClickListener(this);
        listView.setAdapter(fengwenAdapter);
    }


    @Override
    public void onClassifyPostionClick(CircleRecommentEntity itemData, int postion, String state) {
        switch (Integer.parseInt(state)) {
            case 1:
                Intent intent = new Intent(this, NewRecomFengWenDetailsActivity.class);
                intent.putExtra("position", postion);
                intent.putExtra("itemData", itemData);
                startActivityForResult(intent, 0);
                break;
            case 2:
                circleRecommentEntity = itemData;
                if (itemData.getZaned().equals("1")) {
                    String sing_one = TGmd5.getMD5(distributorid + itemData.getID());
                    userDynamicPresenter.CircleunZan(distributorid, itemData.getID(), sing_one, postion);
                } else {
                    String sing_two = TGmd5.getMD5(distributorid + itemData.getID());
                    userDynamicPresenter.CircleZan(distributorid, itemData.getID(), sing_two, postion);
                }
                break;
            case 3:
                if (Integer.parseInt(itemData.getSourceDistributorID()) > 0) {
                    //个人首页
                    Intent intent_one = new Intent(this, UserPersonalCenterActivity.class);
                    intent_one.putExtra("distributorid", Integer.parseInt(itemData.getSourceDistributorID()));
                    startActivity(intent_one);
                } else {
                    // 官方
                    Intent intent_two = new Intent(this, OfficialHomePageActivity.class);
                    intent_two.putExtra("seeDistributorId", itemData.getDistributorID());
                    startActivity(intent_two);
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == 1) {
            if (data != null) {
                CircleRecommentEntity entity = (CircleRecommentEntity) data.getSerializableExtra("itemData");
                int position = data.getIntExtra("position", 0);
                CircleRecommentEntity itemData = fengwenAdapter.getCircleRecommentEntityList().get(position);
                itemData.setCommentCount(entity.getCommentCount());
                itemData.setZanCount(entity.getZanCount());
                itemData.setZaned(entity.getZaned());
                fengwenAdapter.notifyDataSetChanged();
            }
        } else {
            if (data != null) {
                txt_signature.setText(data.getStringExtra("signature"));
            }
        }
    }

    @Override
    public void mytalklistResponse(String resonpse) {
        try {
            closeLoadingProgressDialog();
            pullToRefreshScrollView.onRefreshComplete();
            JSONObject jsonObject = new JSONObject(resonpse);
            String status = jsonObject.getString("status");
            if (status.equals("1")) {
                String result = jsonObject.getString("result");
                JSONArray jsonArray = new JSONArray(result);
                String datas = jsonArray.get(0).toString();

                JSONObject jsonObject_data = new JSONObject(datas);

                total_page = jsonObject_data.getInt("DataPageCount");
                String data_one = jsonObject_data.getString("Data");
                if (mIsUp == false) {
                    fengwenAdapter.getCircleRecommentEntityList().clear();
                } else {

                }
                Gson gson=new Gson();
                List<CircleRecommentEntity> circleRecommentEntityList=gson.fromJson(data_one,new TypeToken<List<CircleRecommentEntity>>(){}.getType());

            if(circleRecommentEntityList!=null&&circleRecommentEntityList.size()>0){
                listView.setVisibility(View.VISIBLE);
                empty_view.setVisibility(View.GONE);
                fengwenAdapter.setFengcircleData(circleRecommentEntityList);
                fengwenAdapter.notifyDataSetChanged();
            } else {
                    if (mIsUp == false) {
                        listView.setVisibility(View.GONE);
                        empty_view.setVisibility(View.VISIBLE);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void excuteFailedCallBack(String resonpse) {

    }

    @Override
    public void zanResponse(String resonpse, int position) {
        try {
            JSONObject jsonObject_one = new JSONObject(resonpse);
            String status = jsonObject_one.getString("status");
            if (status.equals("1")) {
                circleRecommentEntity.setZaned("1");
                int zan = Integer.parseInt(circleRecommentEntity.getZanCount()) + 1;
                circleRecommentEntity.setZanCount(zan + "");
                fengwenAdapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void unzanResponse(String resonpse, int position) {
        try {
            JSONObject jsonObject_two = new JSONObject(resonpse);
            String status = jsonObject_two.getString("status");
            if (status.equals("1")) {
                circleRecommentEntity.setZaned("0");
                int zan = Integer.parseInt(circleRecommentEntity.getZanCount()) - 1;
                circleRecommentEntity.setZanCount(zan + "");
                fengwenAdapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void talkdelResponse(String resonpse) {

    }

    @Override
    public void distributormainResponse(String resonpse) {
        try {
            JSONObject jsonObject = new JSONObject(resonpse);
            int status = jsonObject.getInt("status");
            closeLoadingProgressDialog();
            if (status == 1) {
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
                    seeDistributorId = String.valueOf(jsonObject1.getInt("ID"));
                    adapterDynamicBean = new FengCircleDynamicBean();
                    adapterDynamicBean.setDistributorID(Integer.valueOf(seeDistributorId));
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
                    DisplayImageOptions options = new DisplayImageOptions.Builder()
                            .showImageOnLoading(R.mipmap.faxian_user_head) //设置图片在下载期间显示的图片
                            .showImageForEmptyUri(R.mipmap.faxian_user_head)//设置图片Uri为空或是错误的时候显示的图片
                            .showImageOnFail(R.mipmap.faxian_user_head).build(); //设置图片加载/解码过程中错误时候显示的图片
                    String path = ImageUtils.getInstance().getPath(String.valueOf(mayknowpersonBean.getID()));
                    ImageLoader.getInstance().displayImage(path, img_user_head, options);


                    if (!distributorid.equals(String.valueOf(seeDistributorId))) {
                        img_cirlce_follow.setVisibility(View.VISIBLE);
                        top_cirlce_follow.setVisibility(View.VISIBLE);
                    } else {
                        img_cirlce_follow.setVisibility(View.INVISIBLE);
                        top_cirlce_follow.setVisibility(View.INVISIBLE);
                    }
                }
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img_cirlce_follow:
                if (followed == 1) {
                    showQuitDialog(seeDistributorId);
                } else {
                    String sign = TGmd5.getMD5(distributorid + seeDistributorId);
                    userDynamicPresenter.CircleFollow(distributorid, seeDistributorId, sign);
                }
                break;
        }
    }

    /**
     * 异步取消刷新
     */
    private class CancleRefreshTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            pullToRefreshScrollView.onRefreshComplete();
            super.onPostExecute(aVoid);
        }

    }

    public String getPath(String distributorid) {
        int a = Integer.parseInt(distributorid) / 250000;
        int b = Integer.parseInt(distributorid) % 250000;
        int c = b / 500;
        int d = Integer.parseInt(distributorid) % 500;
        String path = Url.ROOT + "/UploadFile/Face/Distributor/" + a + "/" + c + "/" + d + ".jpg";
        return path;
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
