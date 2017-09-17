package com.lvgou.distribution.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.UserPersonalCenterActivity;
import com.lvgou.distribution.adapter.Concern_FansAdapter;
import com.lvgou.distribution.entity.CircleUserEntity;
import com.lvgou.distribution.inter.OnClassifyPostionClickListener;
import com.lvgou.distribution.presenter.FansFollowListPresenter;
import com.lvgou.distribution.presenter.HomePagePresenter;
import com.lvgou.distribution.refresh.PullToRefreshBase;
import com.lvgou.distribution.refresh.PullToRefreshListView;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.GroupView;
import com.lvgou.distribution.viewholder.CircleUserViewHolder;
import com.umeng.analytics.MobclickAgent;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.functions.holder.ListViewDataAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


/**
 * Created by Administrator on 2016/9/13.
 * 个人主页 粉丝  列表
 */
public class UserPersonFansFragment extends Fragment implements GroupView,Concern_FansAdapter.OnClassifyPostionClickListener{
    boolean mIsUp;// 是否上拉加载
    private PullToRefreshListView pull_refresh_list;
    private RelativeLayout rl_none;
    public static ListView listView;
    OnArticleSelectedListener mListener;
    private LinearLayout ll_visibility;
    private String distributorid;
    private int pageindex = 1;
    private String seeDistributorId = "3721";
    FansFollowListPresenter fansFollowListPresenter;
//    private ListViewDataAdapter<CircleUserEntity> circleUserEntityListViewDataAdapter;
private Concern_FansAdapter concern_fansAdapter;
    private CircleUserEntity circleUserEntity = null;
    private Dialog dialog_del_can;// 取消，删除弹框
    private int total_page;// 总页数，当达到 最大页数，提示最后一页，不在请求

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_no_title_list, container, false);
        pull_refresh_list = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
        ll_visibility = (LinearLayout) view.findViewById(R.id.ll_visibilty);
        rl_none = (RelativeLayout) view.findViewById(R.id.rl_none);
        pageindex=1;
        listView = pull_refresh_list.getRefreshableView();
        mListener = (OnArticleSelectedListener) getActivity();
        distributorid = mListener.onArticleSelected();
        seeDistributorId = mListener.getseeDistributorId();
        fansFollowListPresenter = new FansFollowListPresenter(this);
        String sign = TGmd5.getMD5(distributorid + seeDistributorId + pageindex);
        fansFollowListPresenter.getFansList(distributorid, seeDistributorId, pageindex + "", sign);
        init();
        return view;
    }

    private void init() {
        pull_refresh_list.getLoadingLayoutProxy(false, true).setPullLabel("下拉加载更多");
        pull_refresh_list.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载");
        pull_refresh_list.getLoadingLayoutProxy(false, true).setReleaseLabel("释放开始加载");
        pull_refresh_list.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageindex = 1;
                String sign = TGmd5.getMD5(distributorid + seeDistributorId + pageindex);
                fansFollowListPresenter.getFansList(distributorid, seeDistributorId, pageindex + "", sign);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageindex++;
                if (pageindex <= total_page) {
                    String sign = TGmd5.getMD5(distributorid + seeDistributorId + pageindex);
                    fansFollowListPresenter.getFansList(distributorid, seeDistributorId, pageindex + "", sign);
                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pull_refresh_list.onRefreshComplete();
                        }
                    }, 1000);
                }
            }
        });
        initViewHolder();
    }

    public void initViewHolder() {
//        circleUserEntityListViewDataAdapter = new ListViewDataAdapter<CircleUserEntity>();
//        circleUserEntityListViewDataAdapter.setViewHolderClass(this, CircleUserViewHolder.class);
//        CircleUserViewHolder.setCircleUserEntityOnClassifyPostionClickListener(this);
//        listView.setAdapter(circleUserEntityListViewDataAdapter);
        concern_fansAdapter = new Concern_FansAdapter(getActivity());
        concern_fansAdapter.setOnClassifyPostionClick(this);
        listView.setAdapter(concern_fansAdapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void OnFamousSuccCallBack(String state, String respanse) {
        pull_refresh_list.onRefreshComplete();
        switch (Integer.parseInt(state)) {
            case 1://列表
                try {
                    JSONObject jsonObject = new JSONObject(respanse);
                    String status = jsonObject.getString("status");
                    if (status.equals("1")) {
                        String result = jsonObject.getString("result");
                        JSONArray array = new JSONArray(result);
                        String data = array.get(0).toString();
                        JSONObject jsonOb_data = new JSONObject(data);
                        total_page = jsonOb_data.getInt("DataPageCount");
                        if (pageindex == 1) {
                           concern_fansAdapter.getFengcircleData().clear();
                        }
                        String data_one = jsonOb_data.getString("Data");
                        Gson gson = new Gson();
                        List<CircleUserEntity> circleUserEntityList = gson.fromJson(data_one, new TypeToken<List<CircleUserEntity>>() {
                        }.getType());
                        if (circleUserEntityList != null && circleUserEntityList.size() > 0) {
                            showOrGone();
                            ll_visibility.setVisibility(View.VISIBLE);
                            concern_fansAdapter.setFengcircleData(circleUserEntityList);
                        } else {
                            showOrGone();
                            rl_none.setVisibility(View.VISIBLE);
                        }
                    } else {
                        showOrGone();
                        rl_none.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                try {
                    JSONObject jsonObject = new JSONObject(respanse);
                    String status = jsonObject.getString("status");
                    if (status.equals("1")) {
                        String result = jsonObject.getString("result");
                        JSONArray array = new JSONArray(result);
                        String data = array.get(0).toString();
                        JSONObject jsonOb_data = new JSONObject(data);
                        total_page = jsonOb_data.getInt("DataPageCount");
                        if (pageindex == 1) {
                            concern_fansAdapter.getFengcircleData().clear();
                        }
                        String data_one = jsonOb_data.getString("Data");
                        Gson gson = new Gson();
                        List<CircleUserEntity> circleUserEntityList = gson.fromJson(data_one, new TypeToken<List<CircleUserEntity>>() {
                        }.getType());
                        if (circleUserEntityList != null && circleUserEntityList.size() > 0) {
                            showOrGone();
                            ll_visibility.setVisibility(View.VISIBLE);
                            concern_fansAdapter.setFengcircleData(circleUserEntityList);
                        } else {
                            showOrGone();
                            rl_none.setVisibility(View.VISIBLE);
                        }
                    } else {
                        showOrGone();
                        rl_none.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 3://关注
                try {
                    JSONObject jsonObject = new JSONObject(respanse);
                    String status = jsonObject.getString("status");
                    if (status.equals("1")) {
                        MyToast.show(getActivity(), "关注成功");
                        circleUserEntity.setTuanBi("2");
                        concern_fansAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;
            case 4://取消关注
                try {
                    JSONObject jsonObject = new JSONObject(respanse);
                    String status = jsonObject.getString("status");
                    if (status.equals("1")) {
                        MyToast.show(getActivity(), "取消关注");
                        circleUserEntity.setTuanBi("1");
                        concern_fansAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    public void showOrGone() {
        ll_visibility.setVisibility(View.GONE);
        rl_none.setVisibility(View.GONE);
    }

    @Override
    public void OnFamousFialCallBack(String state, String respanse) {

    }

    @Override
    public void closeProgress() {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void onClassifyPostionClick(CircleUserEntity itemData, int postion) {
        Bundle bundle = new Bundle();
        switch (postion) {
            case 1:
                bundle.putInt("distributorid", Integer.parseInt(itemData.getID()));
                Intent intent = new Intent(getActivity(), UserPersonalCenterActivity.class);
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
                break;
            case 2:
                circleUserEntity = itemData;
                if (itemData.getTuanBi().equals("1")) {//加关注
                    String sign = TGmd5.getMD5(distributorid + itemData.getID());
                    fansFollowListPresenter.doFollow(distributorid, itemData.getID(), sign);
                } else {// 取消关注
                    showQuitDialog(itemData.getID());
                }
                break;
        }
    }

    /**
     * id
     * 删除取消弹框
     */
    public void showQuitDialog(final String id) {
        dialog_del_can = new Dialog(getActivity(), R.style.Mydialog);
        View view1 = View.inflate(getActivity(), R.layout.dialog_quit_show, null);
        Button sure = (Button) view1.findViewById(R.id.sure);
        Button cancle = (Button) view1.findViewById(R.id.cancle);
        TextView tv_title = (TextView) view1.findViewById(R.id.tv_title);
        tv_title.setText(getActivity().getResources().getString(R.string.text_cancel_follow_hint));
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_del_can.dismiss();
                String sign = TGmd5.getMD5(distributorid + id);
                fansFollowListPresenter.cancleFollow(distributorid, id, sign);
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

    public interface OnArticleSelectedListener {
        public String onArticleSelected();

        public String getseeDistributorId();
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getClass().getName());
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getClass().getName());
    }

}
