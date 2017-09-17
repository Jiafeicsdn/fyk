package com.lvgou.distribution.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.LiveDirectActivity;
import com.lvgou.distribution.activity.MoreReminderActivity;
import com.lvgou.distribution.adapter.DirectChatRowAdapter;
import com.lvgou.distribution.bean.AddUser;
import com.lvgou.distribution.bean.GetGroupMessage;
import com.lvgou.distribution.bean.GroupMessageExtData;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.driect.OnBannedListener;
import com.lvgou.distribution.entity.GroupMessageEntity;
import com.lvgou.distribution.presenter.IMFmPersenter;
import com.lvgou.distribution.presenter.IMPersenter;
import com.lvgou.distribution.presenter.StudentFragmentDashangPresenter;
import com.lvgou.distribution.refresh.PullToRefreshBase;
import com.lvgou.distribution.refresh.PullToRefreshListView;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.CustomeChatMenu;
import com.lvgou.distribution.view.IMFmView;
import com.lvgou.distribution.view.StudentFragmentDashangView;
import com.xdroid.common.utils.PreferenceHelper;
import com.lvgou.distribution.utils.MyToast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/13.
 */
public class StudentFragment extends Fragment implements IMFmView, DirectChatRowAdapter.ListnerNickNameCallback, StudentFragmentDashangView, OnBannedListener {
    private int pageindex_out = 1;
    boolean mIsUp;// 是否上拉加载
    private PullToRefreshListView pull_refresh_list;
    public DirectChatRowAdapter directChatRowAdapter;
    private int total_out_pages;
    private List<GroupMessageExtData> chatRowMessageEntities;
    public static ListView listView;
    OnArticleSelectedListener mListener;
    private int dm;
    private boolean ismIsUp = false;
    private int pageIndex = 1;
    private String groupId;
    private int db_pageIndex = 1;
    private List<GroupMessageExtData> groupMessageExtDatas;
    private CustomeChatMenu customechatmenu_one;
    private TextView txt_message_numer;
    private ImageView im_dashang;//打赏
    private StudentFragmentDashangPresenter studentFragmentDashangPresenter;
    private String studyid;//课程id
    private RelativeLayout no_senmessage;
    public View cannot_other;//语音的时候不能点击其他的

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_im_list, container, false);
        EventBus.getDefault().register(this);
        customechatmenu_one = (CustomeChatMenu) view.findViewById(R.id.customechatmenu_one);
        pull_refresh_list = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
        txt_message_numer = (TextView) view.findViewById(R.id.txt_message_numer);

        cannot_other = view.findViewById(R.id.cannot_other);
        cannot_other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ((LiveDirectActivity) getActivity()).setOnBannedListener(this);
        Bundle bundle = getArguments();//从activity传过来的Bundle
        if (bundle != null) {

            studyid = bundle.getString("studyid");
        }
        //禁言，停止输入
        no_senmessage = (RelativeLayout) view.findViewById(R.id.no_senmessage);
        no_senmessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        //打赏，学员交流这里，任何人都可以打赏
        im_dashang = (ImageView) view.findViewById(R.id.im_dashang);
        im_dashang.setVisibility(View.GONE);

        listView = pull_refresh_list.getRefreshableView();
        mListener = (OnArticleSelectedListener) getActivity();
        groupId = mListener.getGroupId();
        groupMessageExtDatas = new ArrayList<>();

        studentFragmentDashangPresenter = new StudentFragmentDashangPresenter(this);
        //当前用户登录id
        final String userId = PreferenceHelper.readString(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        init();
        txt_message_numer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onSelectbottom();
                txt_message_numer.setVisibility(View.GONE);
                listView.setSelection(listView.getCount() - 1);
            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        // 当不滚动时
                        // 判断滚动到底部
                        if (listView.getLastVisiblePosition() == (listView.getCount() - 1)) {
                            mListener.onSelectbottom();
                            txt_message_numer.setVisibility(View.GONE);
                        }
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                        EventBus.getDefault().post("oneclickvoicecancel");
                        customechatmenu_one.HideView();
                        customechatmenu_one.getLlbottomVoiceText().setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        im_dashang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //任何人都可以打赏的。
                showDaShang();
            }
        });
        return view;
    }

    private String dashangmonty = "";//打赏金额
    private RelativeLayout dashang_exit;
    private TextView dashang_mycount;
    private RelativeLayout two_money;
    private TextView tv_two;
    private TextView tv_two_count;
    private RelativeLayout five_money;
    private TextView tv_five;
    private TextView tv_five_count;
    private RelativeLayout ten_money;
    private TextView tv_ten;
    private TextView tv_ten_count;
    private RelativeLayout twenty_money;
    private TextView tv_twenty;
    private TextView tv_twenty_count;
    private RelativeLayout fifty_money;
    private TextView tv_fifty;
    private TextView tv_fifty_count;
    private RelativeLayout hundred_money;
    private TextView tv_hundred;
    private TextView tv_hundred_count;
    private EditText et_addmoney;
    private ImageView im_deletemoney;
    private TextView tv_dashang_true;
    private LinearLayout dashang_reminder;

    private void showDaShang() {
        LinearLayout.LayoutParams pm = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.dialog_dashang, null);//自定义的布局文件
        dashang_exit = (RelativeLayout) view.findViewById(R.id.dashang_exit);//退出弹窗
        dashang_mycount = (TextView) view.findViewById(R.id.dashang_mycount);//我剩余的金币
        two_money = (RelativeLayout) view.findViewById(R.id.two_money);//两金币
        tv_two = (TextView) view.findViewById(R.id.tv_two);//2
        tv_two_count = (TextView) view.findViewById(R.id.tv_two_count);//2币
        five_money = (RelativeLayout) view.findViewById(R.id.five_money);//五金币
        tv_five = (TextView) view.findViewById(R.id.tv_five);//5
        tv_five_count = (TextView) view.findViewById(R.id.tv_five_count);//5币
        ten_money = (RelativeLayout) view.findViewById(R.id.ten_money);//十金币
        tv_ten = (TextView) view.findViewById(R.id.tv_ten);//10
        tv_ten_count = (TextView) view.findViewById(R.id.tv_ten_count);//10币
        twenty_money = (RelativeLayout) view.findViewById(R.id.twenty_money);//二十金币
        tv_twenty = (TextView) view.findViewById(R.id.tv_twenty);//20
        tv_twenty_count = (TextView) view.findViewById(R.id.tv_twenty_count);//20币
        fifty_money = (RelativeLayout) view.findViewById(R.id.fifty_money);//五十金币
        tv_fifty = (TextView) view.findViewById(R.id.tv_fifty);//50
        tv_fifty_count = (TextView) view.findViewById(R.id.tv_fifty_count);//50币
        hundred_money = (RelativeLayout) view.findViewById(R.id.hundred_money);//一百金币
        tv_hundred = (TextView) view.findViewById(R.id.tv_hundred);//100
        tv_hundred_count = (TextView) view.findViewById(R.id.tv_hundred_count);//100币
        et_addmoney = (EditText) view.findViewById(R.id.et_addmoney);//手动编辑金币
        im_deletemoney = (ImageView) view.findViewById(R.id.im_deletemoney);//编辑金币后面的删除按钮
        tv_dashang_true = (TextView) view.findViewById(R.id.tv_dashang_true);//打赏
        dashang_reminder = (LinearLayout) view.findViewById(R.id.dashang_reminder);//打赏记录


        final AlertDialog mAlertDialog = builder.create();
        mAlertDialog.show();
        mAlertDialog.getWindow().setContentView(view, pm);
        mAlertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        dashang_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.dismiss();
            }
        });
        //两金币的点击
        two_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                two_money.setSelected(true);
                tv_two.setTextColor(Color.parseColor("#efefef"));
                tv_two_count.setTextColor(Color.parseColor("#efefef"));
                five_money.setSelected(false);
                tv_five.setTextColor(Color.parseColor("#d5aa5f"));
                tv_five_count.setTextColor(Color.parseColor("#d5aa5f"));
                ten_money.setSelected(false);
                tv_ten.setTextColor(Color.parseColor("#d5aa5f"));
                tv_ten_count.setTextColor(Color.parseColor("#d5aa5f"));
                twenty_money.setSelected(false);
                tv_twenty.setTextColor(Color.parseColor("#d5aa5f"));
                tv_twenty_count.setTextColor(Color.parseColor("#d5aa5f"));
                fifty_money.setSelected(false);
                tv_fifty.setTextColor(Color.parseColor("#d5aa5f"));
                tv_fifty_count.setTextColor(Color.parseColor("#d5aa5f"));
                hundred_money.setSelected(false);
                tv_hundred.setTextColor(Color.parseColor("#d5aa5f"));
                tv_hundred_count.setTextColor(Color.parseColor("#d5aa5f"));
                dashangmonty = "2";
            }
        });
        //五金币的点击
        five_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                two_money.setSelected(false);
                tv_two.setTextColor(Color.parseColor("#d5aa5f"));
                tv_two_count.setTextColor(Color.parseColor("#d5aa5f"));
                five_money.setSelected(true);
                tv_five.setTextColor(Color.parseColor("#efefef"));
                tv_five_count.setTextColor(Color.parseColor("#efefef"));
                ten_money.setSelected(false);
                tv_ten.setTextColor(Color.parseColor("#d5aa5f"));
                tv_ten_count.setTextColor(Color.parseColor("#d5aa5f"));
                twenty_money.setSelected(false);
                tv_twenty.setTextColor(Color.parseColor("#d5aa5f"));
                tv_twenty_count.setTextColor(Color.parseColor("#d5aa5f"));
                fifty_money.setSelected(false);
                tv_fifty.setTextColor(Color.parseColor("#d5aa5f"));
                tv_fifty_count.setTextColor(Color.parseColor("#d5aa5f"));
                hundred_money.setSelected(false);
                tv_hundred.setTextColor(Color.parseColor("#d5aa5f"));
                tv_hundred_count.setTextColor(Color.parseColor("#d5aa5f"));
                dashangmonty = "5";
            }
        });
        //十金币的点击
        ten_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                two_money.setSelected(false);
                tv_two.setTextColor(Color.parseColor("#d5aa5f"));
                tv_two_count.setTextColor(Color.parseColor("#d5aa5f"));
                five_money.setSelected(false);
                tv_five.setTextColor(Color.parseColor("#d5aa5f"));
                tv_five_count.setTextColor(Color.parseColor("#d5aa5f"));
                ten_money.setSelected(true);
                tv_ten.setTextColor(Color.parseColor("#efefef"));
                tv_ten_count.setTextColor(Color.parseColor("#efefef"));
                twenty_money.setSelected(false);
                tv_twenty.setTextColor(Color.parseColor("#d5aa5f"));
                tv_twenty_count.setTextColor(Color.parseColor("#d5aa5f"));
                fifty_money.setSelected(false);
                tv_fifty.setTextColor(Color.parseColor("#d5aa5f"));
                tv_fifty_count.setTextColor(Color.parseColor("#d5aa5f"));
                hundred_money.setSelected(false);
                tv_hundred.setTextColor(Color.parseColor("#d5aa5f"));
                tv_hundred_count.setTextColor(Color.parseColor("#d5aa5f"));
                dashangmonty = "10";
            }
        });
        //二十金币的点击
        twenty_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                two_money.setSelected(false);
                tv_two.setTextColor(Color.parseColor("#d5aa5f"));
                tv_two_count.setTextColor(Color.parseColor("#d5aa5f"));
                five_money.setSelected(false);
                tv_five.setTextColor(Color.parseColor("#d5aa5f"));
                tv_five_count.setTextColor(Color.parseColor("#d5aa5f"));
                ten_money.setSelected(false);
                tv_ten.setTextColor(Color.parseColor("#d5aa5f"));
                tv_ten_count.setTextColor(Color.parseColor("#d5aa5f"));
                twenty_money.setSelected(true);
                tv_twenty.setTextColor(Color.parseColor("#efefef"));
                tv_twenty_count.setTextColor(Color.parseColor("#efefef"));
                fifty_money.setSelected(false);
                tv_fifty.setTextColor(Color.parseColor("#d5aa5f"));
                tv_fifty_count.setTextColor(Color.parseColor("#d5aa5f"));
                hundred_money.setSelected(false);
                tv_hundred.setTextColor(Color.parseColor("#d5aa5f"));
                tv_hundred_count.setTextColor(Color.parseColor("#d5aa5f"));
                dashangmonty = "20";
            }
        });
        //五十金币的点击
        fifty_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                two_money.setSelected(false);
                tv_two.setTextColor(Color.parseColor("#d5aa5f"));
                tv_two_count.setTextColor(Color.parseColor("#d5aa5f"));
                five_money.setSelected(false);
                tv_five.setTextColor(Color.parseColor("#d5aa5f"));
                tv_five_count.setTextColor(Color.parseColor("#d5aa5f"));
                ten_money.setSelected(false);
                tv_ten.setTextColor(Color.parseColor("#d5aa5f"));
                tv_ten_count.setTextColor(Color.parseColor("#d5aa5f"));
                twenty_money.setSelected(false);
                tv_twenty.setTextColor(Color.parseColor("#d5aa5f"));
                tv_twenty_count.setTextColor(Color.parseColor("#d5aa5f"));
                fifty_money.setSelected(true);
                tv_fifty.setTextColor(Color.parseColor("#efefef"));
                tv_fifty_count.setTextColor(Color.parseColor("#efefef"));
                hundred_money.setSelected(false);
                tv_hundred.setTextColor(Color.parseColor("#d5aa5f"));
                tv_hundred_count.setTextColor(Color.parseColor("#d5aa5f"));
                dashangmonty = "50";
            }
        });
        //一百金币的点击
        hundred_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                two_money.setSelected(false);
                tv_two.setTextColor(Color.parseColor("#d5aa5f"));
                tv_two_count.setTextColor(Color.parseColor("#d5aa5f"));
                five_money.setSelected(false);
                tv_five.setTextColor(Color.parseColor("#d5aa5f"));
                tv_five_count.setTextColor(Color.parseColor("#d5aa5f"));
                ten_money.setSelected(false);
                tv_ten.setTextColor(Color.parseColor("#d5aa5f"));
                tv_ten_count.setTextColor(Color.parseColor("#d5aa5f"));
                twenty_money.setSelected(false);
                tv_twenty.setTextColor(Color.parseColor("#d5aa5f"));
                tv_twenty_count.setTextColor(Color.parseColor("#d5aa5f"));
                fifty_money.setSelected(false);
                tv_fifty.setTextColor(Color.parseColor("#d5aa5f"));
                tv_fifty_count.setTextColor(Color.parseColor("#d5aa5f"));
                hundred_money.setSelected(true);
                tv_hundred.setTextColor(Color.parseColor("#efefef"));
                tv_hundred_count.setTextColor(Color.parseColor("#efefef"));
                dashangmonty = "100";
            }
        });
        et_addmoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                two_money.setSelected(false);
                tv_two.setTextColor(Color.parseColor("#d5aa5f"));
                tv_two_count.setTextColor(Color.parseColor("#d5aa5f"));
                five_money.setSelected(false);
                tv_five.setTextColor(Color.parseColor("#d5aa5f"));
                tv_five_count.setTextColor(Color.parseColor("#d5aa5f"));
                ten_money.setSelected(false);
                tv_ten.setTextColor(Color.parseColor("#d5aa5f"));
                tv_ten_count.setTextColor(Color.parseColor("#d5aa5f"));
                twenty_money.setSelected(false);
                tv_twenty.setTextColor(Color.parseColor("#d5aa5f"));
                tv_twenty_count.setTextColor(Color.parseColor("#d5aa5f"));
                fifty_money.setSelected(false);
                tv_fifty.setTextColor(Color.parseColor("#d5aa5f"));
                tv_fifty_count.setTextColor(Color.parseColor("#d5aa5f"));
                hundred_money.setSelected(false);
                tv_hundred.setTextColor(Color.parseColor("#d5aa5f"));
                tv_hundred_count.setTextColor(Color.parseColor("#d5aa5f"));
                dashangmonty = "";
            }
        });
        dashang_reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                MyToast.show(getActivity(), "打赏记录");
//                Bundle bundle = new Bundle();
//                bundle.putStringArrayList("image_urls", "");
                Intent intent = new Intent(getActivity(), MoreReminderActivity.class);
//                intent.putExtras(bundle);
                getActivity().startActivity(intent);
            }
        });
        et_addmoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    dashangmonty = "";
                    im_deletemoney.setVisibility(View.GONE);
                } else {
                    dashangmonty = s.toString();
                    im_deletemoney.setVisibility(View.VISIBLE);
                }

            }
        });
        im_deletemoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dashangmonty = "";
                et_addmoney.setText("");
            }
        });

        tv_dashang_true.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dashangmonty.equals("")) {
                    //如果没有输入和没有选中金币
                    LinearLayout.LayoutParams pm = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    LayoutInflater inflater = LayoutInflater.from(getActivity());
                    View view = inflater.inflate(R.layout.dialog_dashang_reminder, null);//自定义的布局文件
                    TextView tv_reminder_sure = (TextView) view.findViewById(R.id.tv_reminder_sure);

                    final AlertDialog mAlertDialog = builder.create();
                    mAlertDialog.getWindow().setBackgroundDrawableResource(R.color.touming);
                    mAlertDialog.show();
                    mAlertDialog.getWindow().setContentView(view, pm);
                    tv_reminder_sure.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mAlertDialog.dismiss();
                        }
                    });

                } else {
                    //已经输入或者已经选好金币
                    String distributorid = PreferenceHelper.readString(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
                    String sign = TGmd5.getMD5(distributorid + studyid + 2);
                    studentFragmentDashangPresenter.dashang(distributorid, studyid, Integer.parseInt(dashangmonty), sign);

                    mAlertDialog.dismiss();

                }
            }
        });

    }


    public void hideCustomeMenu() {
        customechatmenu_one.setVisibility(View.GONE);
    }

    public void showCustomeMenu() {
        customechatmenu_one.setVisibility(View.VISIBLE);
    }

    public CustomeChatMenu getCustomeMenu() {
        return customechatmenu_one;
    }

    public TextView getTxt_message_numer() {
        return txt_message_numer;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    private void init() {
        pull_refresh_list.getLoadingLayoutProxy(false, true).setPullLabel("下拉加载更多");
        pull_refresh_list.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载");
        pull_refresh_list.getLoadingLayoutProxy(false, true).setReleaseLabel("释放开始加载");
        pull_refresh_list.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        pull_refresh_list.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
                                                   @Override
                                                   public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                                                       String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                                                       // Update the LastUpdatedLabel
                                                       pull_refresh_list.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                                                       ismIsUp = true;
                                                       //判断数据库是否有值
                                                       if (directChatRowAdapter != null && directChatRowAdapter.getCount() > 0) {
//                    List<GroupMessageEntity> groupMessageEntities=null;
//                    try {
//                        if(dm==2){
//                       groupMessageEntities = ((LiveDirectActivity) getActivity()).dbUtils.findFirst(Selector.from(GroupMessageEntity.class).where(
//                                "id", "=","").and("groupId", "=", groupId).and("creationTime", ">","").limit(10).offset(db_pageIndex*10));
//                    }else {
//                       groupMessageEntities = ((LiveDirectActivity) getActivity()).dbUtils.findFirst(Selector.from(GroupMessageEntity.class).where(
//                                "id", "=", directChatRowAdapter.getItem(0).getI()).and("groupId", "=", groupId).and("creationTime", ">","" ).limit(10).offset(db_pageIndex*10));
//                    }
//                    if (groupMessageEntities != null && groupMessageEntities.size() > 0) {
//                        if (groupMessageExtDatas != null) {
//                            groupMessageExtDatas.clear();
//                        }
//                        for (int i = 0; i < groupMessageEntities.size(); i++) {
//                            GroupMessageExtData groupMessageExtData = new GroupMessageExtData();
//                            groupMessageExtData.setSI(groupMessageEntities.get(i).getSenderId());
//                            groupMessageExtData.setGI(groupMessageEntities.get(i).getGroupId());
//                            groupMessageExtData.setCT(groupMessageEntities.get(i).getCreationTime());
//                            groupMessageExtData.setMT(groupMessageEntities.get(i).getMessageType());
//                            groupMessageExtData.setI(groupMessageEntities.get(i).getId());
//                            groupMessageExtData.setC(groupMessageEntities.get(i).getContent());
//                            groupMessageExtData.setT(groupMessageEntities.get(i).getThumbnailUrl());
//                            groupMessageExtData.setO(groupMessageEntities.get(i).getO());
//                            groupMessageExtData.setU(groupMessageEntities.get(i).getUrl());
//                            groupMessageExtData.setL(groupMessageEntities.get(i).getVoicetime());
//                            groupMessageExtData.setCI(groupMessageEntities.get(i).getCoverImage());
//                            groupMessageExtDatas.add(groupMessageExtData);
//                        }
//                        directChatRowAdapter.setMessageData(list);
//                        directChatRowAdapter.notifyDataSetChanged();
//                    } else {
                                                           GetGroupMessage getGroupMessage = new GetGroupMessage();
                                                           getGroupMessage.setGN(10);
                                                           getGroupMessage.setDM(dm);
                                                           if (directChatRowAdapter != null && directChatRowAdapter.getCount() > 0) {
                                                               getGroupMessage.setMI(directChatRowAdapter.getItem(0).getI());
                                                           }
                                                           //登录id
                                                           getGroupMessage.setSI(Integer.valueOf(mListener.onArticleSelected()));
                                                           getGroupMessage.setCUI(groupId);
                                                           getGroupMessage.setMGT(1);
                                                           getGroupMessage.setIILM(false);
                                                           IMFmPersenter imFmPersenter = new IMFmPersenter(StudentFragment.this);
                                                           imFmPersenter.GetGroupMessageExt(mListener.onArticleSelected(), getGroupMessage);
//                    }
//                }catch(DbException e){
//                    e.printStackTrace();
//                }
                                                       } else {
                                                           pull_refresh_list.onRefreshComplete();
                                                       }
                                                   }

                                                   @Override
                                                   public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                                                       mIsUp = true;
                                                       if (pageindex_out < total_out_pages) {
                                                           pageindex_out += 1;
                                                       } else {
                                                           MyToast.show(getActivity(), "没有更多数据");
                                                       }
                                                       new Handler().postDelayed(new Runnable() {
                                                           @Override
                                                           public void run() {
                                                               pull_refresh_list.onRefreshComplete();
                                                               directChatRowAdapter.notifyDataSetChanged();
                                                           }
                                                       }, 2000);
                                                   }
                                               }

        );

        initViewHolder();
    }

    public void initViewHolder() {
        chatRowMessageEntities = new ArrayList<GroupMessageExtData>();
        directChatRowAdapter = new DirectChatRowAdapter(getActivity(), chatRowMessageEntities, "student");
        directChatRowAdapter.setTeacherId(mListener.getTeachId());
        directChatRowAdapter.setListnerCallback(this);
        listView.setAdapter(directChatRowAdapter);
        listView.setSelection(listView.getCount() - 1);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * 数据绑定，测试
     */
    public void initData(int dm, String guideId) {
        this.dm = dm;
        GetGroupMessage getGroupMessage = new GetGroupMessage();
        getGroupMessage.setGN(10);
        getGroupMessage.setDM(dm);
        getGroupMessage.setMI("");
        //讲师id
        getGroupMessage.setSI(Integer.valueOf(mListener.onArticleSelected()));
        getGroupMessage.setCUI(groupId);
        getGroupMessage.setMGT(0);
        getGroupMessage.setIILM(true);
        IMFmPersenter imFmPersenter = new IMFmPersenter(this);
        imFmPersenter.GetGroupMessageExt(guideId, getGroupMessage);
    }


    /**
     * 获取用户名称
     */
    @Override
    public void getNickName(String id) {
        mListener.getNickName(id);
    }

    @Override
    public void showdialog(String uid) {
        mListener.showDialog(uid);
    }


    public interface OnArticleSelectedListener {
        public String onArticleSelected();

        public String getGroupId();

        public void onSelectbottom();

        public void getNickName(String id);

        public String getTeachId();

        public void showDialog(String uid);

    }

    @Override
    public AddUser getParamenters() {
        return null;
    }

    List<GroupMessageExtData> list;

    @Override
    public void GetGroupMessageExt_response(String s) {
        pull_refresh_list.onRefreshComplete();
        directChatRowAdapter.notifyDataSetChanged();


        //数据的解析
        try {
            JSONObject jsonObject = new JSONObject(s);
            int e1 = jsonObject.getInt("e1");
            if (e1 == 0) {
                JSONArray jsonArray = jsonObject.getJSONArray("d");
                Gson gson = new Gson();
                list = new ArrayList<>();
                try {
                    list = gson.fromJson(String.valueOf(jsonArray),
                            new TypeToken<List<GroupMessageExtData>>() {
                            }.getType());
                } catch (Exception e) {
                    // TODO: handle exception
                }
//                groupId = list.get(0).getGI();
                directChatRowAdapter.setMessageData(list);
                directChatRowAdapter.notifyDataSetChanged();
                if (!ismIsUp) {
//                    insert();
                    listView.setSelection(listView.getCount() - 1);
                } else {
                    if (list.size() > 0) {
                        listView.setSelection(list.size());
                    } else {
                        listView.setSelection(0);
                    }
                }
            }

        } catch (Exception e) {
            System.out.print(e);
        }

    }

    /**
     * 数据的插入
     */
    public void insert() {
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                find(list.get(i));
            }
        }
    }

    /**
     * 数据查询
     */
    public void find(GroupMessageExtData groupMessageExtData) {
        try {
            // 查询符合条件的数据
            GroupMessageEntity entity = ((LiveDirectActivity) getActivity()).dbUtils.findFirst(Selector.from(GroupMessageEntity.class).where(
                    "id", "=", groupMessageExtData.getI()).and("groupId", "=", groupMessageExtData.getGI()));
            if (entity != null) {
                ((LiveDirectActivity) getActivity()).dbUtils.update(entity);
            } else {
                GroupMessageEntity groupMessageEntity = new GroupMessageEntity();
                groupMessageEntity.setId(groupMessageExtData.getI());
                groupMessageEntity.setSenderId(groupMessageExtData.getSI());
                groupMessageEntity.setGroupId(groupMessageExtData.getGI());
                groupMessageEntity.setCreationTime(groupMessageExtData.getCT());
                groupMessageEntity.setVoicetime(groupMessageExtData.getL());
                groupMessageEntity.setUrl(groupMessageExtData.getU());
                groupMessageEntity.setMessageType(groupMessageExtData.getMT());
                groupMessageEntity.setCoverImage(groupMessageExtData.getCI());
                groupMessageEntity.setO(groupMessageExtData.getO());
                groupMessageEntity.setThumbnailUrl(groupMessageExtData.getT());
                groupMessageEntity.setContent(groupMessageExtData.getC());
                try {
                    ((LiveDirectActivity) getActivity()).dbUtils.save(groupMessageEntity);
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
//            ((LiveDirectActivity) getActivity()).dbUtils.delete(entity);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (directChatRowAdapter != null) {
            MediaPlayer mediaPlayer = directChatRowAdapter.getMediaPlayer();
            mediaPlayer.reset();
            mediaPlayer = null;
        }
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void OnStudentFragmentDashangSuccCallBack(String state, String respanse) {
        MyToast.show(getActivity(), "打赏成功!");

        CustomeChatMenu.onBottomMenuClickListener.OnBottomMenuClickListener(2, "打赏: " + dashangmonty);
    }

    @Override
    public void OnStudentFragmentDashangFialCallBack(String state, String respanse) {
        MyToast.show(getActivity(), "打赏失败!");
    }

    @Override
    public void closeProgress() {

    }

    @Override
    public void excuteSuccessCallBack(String s) {

    }

    @Override
    public void excuteFailedCallBack(String s) {
        pull_refresh_list.onRefreshComplete();
        directChatRowAdapter.notifyDataSetChanged();
    }


    @Override
    public void setViewVisibility(String vis) {
        if (vis.equals("visibility")) {
            customechatmenu_one.getEtContent().setHint("");
            no_senmessage.setVisibility(View.VISIBLE);
        } else if (vis.equals("gone")) {
            no_senmessage.setVisibility(View.GONE);
            cannot_other.setVisibility(View.GONE);
            customechatmenu_one.getEtContent().setHint("这里输入文字");
        }
    }

    /**
     * 在主线程接受 eventbus发送的事件
     *
     * @param action
     * @Subscribe 这个注解必须加上
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUIEvent(String action) {
        // 如果接受到注销的事件 就销毁自己
        //长按 录音按下
        if ("studentluyinjieshu".equals(action)) {
            directChatRowAdapter.onStopVoice();
        }
    }
}
