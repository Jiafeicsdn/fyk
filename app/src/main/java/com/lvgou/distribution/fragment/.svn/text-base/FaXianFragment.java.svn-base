package com.lvgou.distribution.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.AllTopicActivity;
import com.lvgou.distribution.activity.CirclrFengActivity;
import com.lvgou.distribution.activity.FaXianAdapter;
import com.lvgou.distribution.activity.MessageListActivity;
import com.lvgou.distribution.activity.NewDynamicDetailsActivity;
import com.lvgou.distribution.activity.PublishFengActivity;
import com.lvgou.distribution.activity.TopicDetailsActivity;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.event.EventFactory;
import com.lvgou.distribution.inter.AdapterToFraImpl;
import com.lvgou.distribution.presenter.FengFindCirclePresenter;
import com.lvgou.distribution.presenter.FindCircleTopPresenter;
import com.lvgou.distribution.presenter.UnreadCountPresenter;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.FengFindCircleView;
import com.lvgou.distribution.view.FindCircleTopView;
import com.lvgou.distribution.view.UnreadCountView;
import com.lvgou.distribution.widget.XListView;
import com.xdroid.common.utils.PreferenceHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Administrator on 2017/4/14.
 * 发现
 */

public class FaXianFragment extends Fragment implements XListView.IXListViewListener, FindCircleTopView, FengFindCircleView, View.OnClickListener, UnreadCountView {
    private View view;
    private FindCircleTopPresenter findCircleTopPresenter;
    private FengFindCirclePresenter fengFindCirclePresenter;
    private JSONArray jsonObject3 = new JSONArray();
    private String distributorid;
    private String huaTiId = "";
    private UnreadCountPresenter unreadCountPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_faxian, container, false);
        findCircleTopPresenter = new FindCircleTopPresenter(this);
        fengFindCirclePresenter = new FengFindCirclePresenter(this);
        unreadCountPresenter = new UnreadCountPresenter(this);
        distributorid = PreferenceHelper.readString(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        initView();
        onRefresh();
        initClick();
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private XListView mListView;
    private FaXianAdapter faXianAdapter;
    private View huaTiHeader;//话题
    private RelativeLayout rl_huati_more;//更多话题
    private View seleteHeader; //每日精选头部
    private ImageView huati_picture;//话题图片
    private TextView tv_title;//话题标题
    private TextView tv_readnum;//话题阅读数量
    private TextView tv_commentnum;//话题讨论数量
    private ImageView im_fabu;//发布
    private LinearLayout ly_unread_msg;
    private TextView txt_unread_title;

    private void initView() {
        ly_unread_msg = (LinearLayout) view.findViewById(R.id.ly_unread_msg);
        txt_unread_title = (TextView) view.findViewById(R.id.txt_unread_title);
        im_fabu = (ImageView) view.findViewById(R.id.im_fabu);
        mListView = (XListView) view.findViewById(R.id.list_view);
        faXianAdapter = new FaXianAdapter(getActivity());
        mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnable(true);
//        mListView.stopLoadMore();
        mListView.setAutoLoadEnable(false);
        mListView.setXListViewListener(this);
        mListView.setRefreshTime(((CirclrFengActivity) getActivity()).getTime());
        mListView.setDivider(null);
        faXianAdapter.setData(new ArrayList<HashMap<String, Object>>(), lastSelectId, jsonObject3);
        mListView.setAdapter(faXianAdapter);
        //话题
        huaTiHeader = LayoutInflater.from(getActivity()).inflate(R.layout.activity_huati_header, null);
        RelativeLayout rl_hot_topic = (RelativeLayout) huaTiHeader.findViewById(R.id.rl_hot_topic);
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        ViewGroup.LayoutParams para = rl_hot_topic.getLayoutParams();
        int iheight = (int) (width * 26 / 75);
        para.height = iheight;
        rl_hot_topic.setLayoutParams(para);
        huati_picture = (ImageView) huaTiHeader.findViewById(R.id.huati_picture);
        tv_title = (TextView) huaTiHeader.findViewById(R.id.tv_title);
        tv_readnum = (TextView) huaTiHeader.findViewById(R.id.tv_readnum);
        tv_commentnum = (TextView) huaTiHeader.findViewById(R.id.tv_commentnum);
        rl_huati_more = (RelativeLayout) huaTiHeader.findViewById(R.id.rl_huati_more);
        mListView.addHeaderView(huaTiHeader);
        //每日精选头部
        seleteHeader = LayoutInflater.from(getActivity()).inflate(R.layout.activity_selete_header, null);
        seleteHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
       /* TextView tv_selete_title = (TextView) seleteHeader.findViewById(R.id.tv_selete_title);
        final String TEXT_STRING = "我是百度，我怕谁！？";
        final String TEXT_KEY = "百度";
        int startIndex = TEXT_STRING.indexOf(TEXT_KEY);
        int endIndex = startIndex + TEXT_KEY.length();
        SpannableStringBuilder builder = new SpannableStringBuilder(TEXT_STRING);

        builder.setSpan(new ClickSpannable(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("askjhks", "===========");
                    }
                }), startIndex, endIndex,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_selete_title.setMovementMethod(LinkMovementMethod.getInstance());
        tv_selete_title.setText(builder);*/

        mListView.addHeaderView(seleteHeader);

    }

    private String prePageLastDataObjectId = "";
    private String lastSelectId = "";

    @Override
    public void OnFindCircleTopSuccCallBack(String state, String respanse) {
        dataList.clear();
        mListView.stopRefresh();
        try {
            JSONObject jsonObject = new JSONObject(respanse);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("result"));
            JSONObject jsonObject1 = new JSONObject(jsonArray.get(0).toString());
            Glide.with(getActivity()).load(Url.ROOT + jsonObject1.get("PicUrl").toString()).error(R.mipmap.pictures_no).into(huati_picture);
            tv_title.setText("#" + jsonObject1.get("Title").toString() + "#");
            tv_readnum.setText("阅读" + jsonObject1.get("Hits").toString());
            tv_commentnum.setText("讨论" + jsonObject1.get("CommentCount").toString());
            huaTiId = jsonObject1.get("ID").toString();
            jsonObject3 = new JSONArray(jsonArray.get(2).toString());
            JSONArray jsonObject2 = new JSONArray(jsonArray.get(1).toString());
            for (int i = 0; i < jsonObject2.length(); i++) {
                JSONObject jsoo = jsonObject2.getJSONObject(i);
                HashMap<String, Object> map1 = new HashMap<String, Object>();
                Iterator it = jsoo.keys();
                while (it.hasNext()) {
                    String key = (String) it.next();
                    String value = jsoo.getString(key);
                    map1.put(key, value);
                }
                dataList.add(map1);
            }
            if (dataList.size() > 0) {
                dataList.get(dataList.size() - 1).put("Extra", "xianshifaxiande");
//                lastSelectId = dataList.get(dataList.size() - 1).get("ID").toString();
                lastSelectId = "xianshifaxiande";
            }
            String sign = TGmd5.getMD5(distributorid + "" + "" + prePageLastDataObjectId + page);
//            findCircleTopPresenter.findCircleTop(distributorid, sign);
            fengFindCirclePresenter.fengFindCircle(distributorid, "", "", prePageLastDataObjectId, page, sign);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void OnFindCircleTopFialCallBack(String state, String respanse) {

        ((CirclrFengActivity) getActivity()).closeLoadingProgressDialog();
        mListView.stopRefresh();
    }

    @Override
    public void closeFindCircleTopProgress() {

    }

    /**
     * SpannableStringBuilder 点击事件
     */
   /* private class ClickSpannable extends ClickableSpan implements
            View.OnClickListener {

        private View.OnClickListener onClickListener;

        public ClickSpannable(View.OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
        }

        @Override
        public void onClick(View widget) {
            onClickListener.onClick(widget);
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            // 去掉下划线
            ds.setUnderlineText(false);
            // 设置文字颜色
            ds.setColor(Color.parseColor("#d5aa5f"));
        }

    }*/
    private void initClick() {
        im_fabu.setOnClickListener(this);
        ly_unread_msg.setOnClickListener(this);
        faXianAdapter.setAdapterToFraImpl(new AdapterToFraImpl() {
            @Override
            public void doSomeThing(HashMap<String, Object> info) {
                for (int i = 0; i < dataList.size(); i++) {
                    if (dataList.get(i).get("ID").toString().equals(info.get("ID").toString())) {
                        dataList.get(i).put("ZanCount", info.get("ZanCount"));
                        dataList.get(i).put("Zaned", info.get("Zaned"));
                        dataList.get(i).put("Followed", info.get("Followed"));
//                        break;
                    }
                }
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //蜂文详情
                if (position >= 3) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), NewDynamicDetailsActivity.class);
                    intent.putExtra("position", 0);
                    intent.putExtra("talkId", dataList.get(position - 3).get("ID").toString());
                    getActivity().startActivity(intent);
                }

            }
        });
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            //            boolean scrollFlag = false;// 标记是否滑动
            int lastVisibleItemPosition;// 标记上次滑动位置

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
               /* if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    scrollFlag = true;
                } else {
                    scrollFlag = false;
                }*/
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                if (scrollFlag) {
                if (firstVisibleItem > lastVisibleItemPosition) {
                    Log.e("sfasdfs", "上滑");
                    im_fabu.setBackgroundResource(R.mipmap.feng_fabu_normal_icon);
                }
                if (firstVisibleItem < lastVisibleItemPosition) {
                    Log.e("sfasdfs", "下滑");
                    im_fabu.setBackgroundResource(R.mipmap.feng_fabu_icon);
                }
//                }

                if (firstVisibleItem == lastVisibleItemPosition) {
                    return;
                }
                lastVisibleItemPosition = firstVisibleItem;
            }
        });
        huati_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TopicDetailsActivity.class);
                intent.putExtra("topicId", huaTiId);
                startActivity(intent);
            }
        });
        rl_huati_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //更多话题
                Intent intent = new Intent(getActivity(), AllTopicActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onRefresh() {
        page = 1;
        dataList.clear();
        initDatas();

    }

    @Override
    public void onLoadMore() {
        page++;
        initDatas2();
    }

    public ArrayList<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();
    public ArrayList<HashMap<String, Object>> dataListTmp = new ArrayList<HashMap<String, Object>>();
    private int page = 1;

    private void initDatas() {
        String sign = TGmd5.getMD5(distributorid);
        ((CirclrFengActivity) getActivity()).showLoadingProgressDialog(getActivity(), "");
        findCircleTopPresenter.findCircleTop(distributorid, sign);
        String unreadsign = TGmd5.getMD5(distributorid + 1);
        unreadCountPresenter.unreadCount(distributorid, 1, unreadsign);
    }

    private void initDatas2() {
        ((CirclrFengActivity) getActivity()).showLoadingProgressDialog(getActivity(), "");
        String sign = TGmd5.getMD5(distributorid + "" + "" + prePageLastDataObjectId + page);
        findCircleTopPresenter.findCircleTop(distributorid, sign);
        fengFindCirclePresenter.fengFindCircle(distributorid, "", "", prePageLastDataObjectId, page, sign);
    }

    @Override
    public void OnFengFindCircleSuccCallBack(String state, String respanse) {
        ((CirclrFengActivity) getActivity()).closeLoadingProgressDialog();
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
            if (dataListTmp.size() > 0) {
                prePageLastDataObjectId = dataListTmp.get(dataListTmp.size() - 1).get("ID").toString();
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
    public void OnFengFindCircleFialCallBack(String state, String respanse) {
        ((CirclrFengActivity) getActivity()).closeLoadingProgressDialog();
    }

    @Override
    public void closeFengFindCircleProgress() {

    }

    private final mainHandler mHandler = new mainHandler(this);

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.im_fabu://发布
                //发布蜂文
                Intent intent = new Intent(getActivity(), PublishFengActivity.class);
//                Intent intent=new Intent(getActivity(),PublishFenwenActivity.class);
                startActivity(intent);
//                openActivity(PublishFenwenActivity.class);
                break;
            case R.id.ly_unread_msg:
                ((CirclrFengActivity) getActivity()).changeTopView(0);
//                MyToast.makeText(getActivity(),getActivity().getParent().getClass().toString(),Toast.LENGTH_LONG).show();
                Intent intent2 = new Intent(getActivity(), MessageListActivity.class);
                startActivity(intent2);
                ly_unread_msg.setVisibility(View.GONE);
                EventBus.getDefault().post("unreradmessagefollow");
                break;
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
        if ("unreradmessagefaxian".equals(action)) {
            ly_unread_msg.setVisibility(View.GONE);
        }
        if ("publishfengsuccess".equals(action)) {
            onRefresh();
        }
    }

    @Override
    public void OnUnreadCountSuccCallBack(String state, String respanse) {
        //未读消息
        try {
            JSONObject jsonObject = new JSONObject(respanse);

            JSONArray jsonArray = jsonObject.getJSONArray("result");
            int uncomment = (int) jsonArray.get(0);

            int unlike = (int) jsonArray.get(1);
            int classNum = (int) jsonArray.get(3);
            int systemNum = (int) jsonArray.get(4);
            int fengyouNum = (int) jsonArray.get(5);
            PreferenceHelper.write(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.COMMENT_NUM, uncomment + "");
            PreferenceHelper.write(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.ZAN_NUM, unlike + "");
            PreferenceHelper.write(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.CENTER_NUMBER, String.valueOf(classNum + systemNum + fengyouNum));
            /**
             * 更新我的角标
             */
            EventFactory.updateHomeCenter("0");

            // 公告角标
            int gonggao_num = uncomment + unlike;
            if (gonggao_num > 0) {
                ((CirclrFengActivity) getActivity()).changeTopView(gonggao_num);
                ly_unread_msg.setVisibility(View.VISIBLE);
                txt_unread_title.setText("您有" + gonggao_num + "条新消息");
            } else {
                ly_unread_msg.setVisibility(View.GONE);
            }
        } catch (Exception e) {

        }
    }

    @Override
    public void OnUnreadCountFialCallBack(String state, String respanse) {
        Log.e("kahsdfk", "*********" + respanse);
    }

    @Override
    public void closeUnreadCountProgress() {

    }

    private static class mainHandler extends Handler {
        private final WeakReference<FaXianFragment> mActivity;

        public mainHandler(FaXianFragment activity) {
            mActivity = new WeakReference<FaXianFragment>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            FaXianFragment activity = mActivity.get();
            if (activity != null) {
                activity.flushDataList(msg);
            }
        }

    }

    public void flushDataList(Message msg) {
        if (msg.what == 1) {
            dataList.addAll(dataListTmp);
            if (dataList != null) {
                faXianAdapter.setData(dataList, lastSelectId, jsonObject3);
                faXianAdapter.notifyDataSetChanged();
                mListView.setPullLoadEnable(true);
            } else {
                mListView.setPullLoadEnable(false);
            }

            dataListTmp.clear();
            mListView.stopRefresh();
        } else if (msg.what == 2) {
            dataList.addAll(dataListTmp);
            faXianAdapter.setData(dataList, lastSelectId, jsonObject3);
            faXianAdapter.notifyDataSetChanged();
            dataListTmp.clear();

            mListView.setPullLoadEnable(false);
            mListView.stopLoadMore();
        }
    }
}
