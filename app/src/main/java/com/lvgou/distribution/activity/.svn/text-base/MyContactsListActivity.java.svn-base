package com.lvgou.distribution.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.contact.CharacterParser;
import com.lvgou.distribution.contact.PinyinComparator;
import com.lvgou.distribution.contact.SideBar;
import com.lvgou.distribution.contact.SortGroupMemberAdapter;
import com.lvgou.distribution.entity.CircleUserEntity;
import com.lvgou.distribution.entity.ContactsListEntity;
import com.lvgou.distribution.entity.MoreFriendsEntity;
import com.lvgou.distribution.inter.OnClassifyPostionClickListener;
import com.lvgou.distribution.presenter.DistributorHomePresenter;
import com.lvgou.distribution.presenter.MyContactsListPresenter;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.DistributorHomeView;
import com.lvgou.distribution.view.GroupView;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.common.utils.PreferenceHelper;
import com.lvgou.distribution.utils.MyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2016/10/20.
 * 我的联系人列表
 */
public class MyContactsListActivity extends BaseActivity implements SectionIndexer, GroupView, OnClassifyPostionClickListener<ContactsListEntity>,DistributorHomeView {


    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.country_lvcountry)
    private ListView sortListView;
    @ViewInject(R.id.sidrbar)
    private SideBar sideBar;
    @ViewInject(R.id.dialog)
    private TextView dialog;
    @ViewInject(R.id.title_layout)
    private LinearLayout titleLayout;
    @ViewInject(R.id.title_layout_catalog)
    private TextView title;
    @ViewInject(R.id.title_layout_no_friends)
    private TextView tvNofriends;

    private int lastFirstVisibleItem = -1;
    private DistributorHomePresenter distributorHomePresenter;
    private SortGroupMemberAdapter adapter;
    private CharacterParser characterParser;
    private List<ContactsListEntity> SourceDateList = new ArrayList<ContactsListEntity>();
    private List<ContactsListEntity> SourceDateList_one = new ArrayList<ContactsListEntity>();

    private Dialog dialog_del_can;// 取消，删除弹框

    private PinyinComparator pinyinComparator;


    private MyContactsListPresenter myContactsListPresenter;


    private ContactsListEntity contactsListEntity_one = null;

    private String distributorid = "";

    private String contacts_datas = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        ViewUtils.inject(this);
        tv_title.setText("通讯录好友");
        distributorHomePresenter = new DistributorHomePresenter(this);
        distributorid = PreferenceHelper.readString(MyContactsListActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID, "0");
        contacts_datas = getTextFromBundle("contacts_datas");

        myContactsListPresenter = new MyContactsListPresenter(this);
        initData(contacts_datas);
        initViews();

    }


    @OnClick({R.id.rl_back})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
        }
    }


    public void initData(String datas) {
        try {
            JSONArray array_contact = new JSONArray(datas);
            if (array_contact != null && array_contact.length() > 0) {
                for (int i = 0; i < array_contact.length(); i++) {
                    JSONObject jsonObject_one = array_contact.getJSONObject(i);
                    String id = jsonObject_one.getString("ID");
                    String PicUrl = jsonObject_one.getString("PicUrl");
                    String RealName = jsonObject_one.getString("RealName");
                    String isFollow = jsonObject_one.getString("TuanBi");
                    String FengwenCount = jsonObject_one.getString("FengwenCount");
                    String FansCount = jsonObject_one.getString("FansCount");
                    ContactsListEntity moreFriendsEntity = new ContactsListEntity(id, RealName, PicUrl, FengwenCount, FansCount, isFollow, "");
                    SourceDateList_one.add(moreFriendsEntity);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initViews() {
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();
        sideBar.setTextView(dialog);

        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {

                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    sortListView.setSelection(position);
                }
            }
        });

        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
        SourceDateList = filledData(SourceDateList_one);
        Collections.sort(SourceDateList, pinyinComparator);
        adapter = new SortGroupMemberAdapter(this, SourceDateList);
        SortGroupMemberAdapter.setOnClassifyPostionClickListener(this);
        sortListView.setAdapter(adapter);
        sortListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int section = getSectionForPosition(firstVisibleItem);
                int nextSection = getSectionForPosition(firstVisibleItem + 1);
                int nextSecPosition = getPositionForSection(+nextSection);
                if (firstVisibleItem != lastFirstVisibleItem) {
                    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) titleLayout.getLayoutParams();
                    params.topMargin = 0;
                    titleLayout.setLayoutParams(params);
                    title.setText(SourceDateList.get(getPositionForSection(section)).getSortLetters());
                }
                if (nextSecPosition == firstVisibleItem + 1) {
                    View childView = view.getChildAt(0);
                    if (childView != null) {
                        int titleHeight = titleLayout.getHeight();
                        int bottom = childView.getBottom();
                        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) titleLayout.getLayoutParams();
                        if (bottom < titleHeight) {
                            float pushedDistance = bottom - titleHeight;
                            params.topMargin = (int) pushedDistance;
                            titleLayout.setLayoutParams(params);
                        } else {
                            if (params.topMargin != 0) {
                                params.topMargin = 0;
                                titleLayout.setLayoutParams(params);
                            }
                        }
                    }
                }
                lastFirstVisibleItem = firstVisibleItem;
            }
        });
    }

    private List<ContactsListEntity> filledData(List<ContactsListEntity> listEntities) {
        List<ContactsListEntity> mSortList = new ArrayList<ContactsListEntity>();
        for (int i = 0; i < listEntities.size(); i++) {
            ContactsListEntity sortModel = new ContactsListEntity();
            sortModel.setId(listEntities.get(i).getId());
            sortModel.setFangs_num(listEntities.get(i).getFangs_num());
            sortModel.setFengwen_num(listEntities.get(i).getFengwen_num());
            sortModel.setImg_path(listEntities.get(i).getImg_path());
            sortModel.setIsFollow(listEntities.get(i).getIsFollow());
            sortModel.setName(listEntities.get(i).getName());
            String pinyin = characterParser.getSelling(listEntities.get(i).getName());
            String sortString = pinyin.substring(0, 1).toUpperCase();

            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }
            mSortList.add(sortModel);
        }
        return mSortList;
    }


    @Override
    public Object[] getSections() {
        return null;
    }


    public int getSectionForPosition(int position) {
        return SourceDateList.get(position).getSortLetters().charAt(0);
    }


    public int getPositionForSection(int section) {
        for (int i = 0; i < SourceDateList.size(); i++) {
            String sortStr = SourceDateList.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }

    /**
     * id
     * 删除取消弹框
     */
    public void showQuitDialog(final String id) {
        dialog_del_can = new Dialog(MyContactsListActivity.this, R.style.Mydialog);
        View view1 = View.inflate(MyContactsListActivity.this,
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
                myContactsListPresenter.cancleFollow(distributorid, id, sign);
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
    protected void onResume() {
        super.onResume();
        myContactsListPresenter.attach(this);
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myContactsListPresenter.dettach();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }


    /**
     * 成功回调
     *
     * @param state
     * @param respanse
     */
    @Override
    public void OnFamousSuccCallBack(String state, String respanse) {
        switch (Integer.parseInt(state)) {
            case 1:
                try {
                    JSONObject jsonObject = new JSONObject(respanse);
                    String status = jsonObject.getString("status");
                    if (status.equals("1")) {
                        MyToast.show(this, "关注成功");
                        contactsListEntity_one.setIsFollow("2");
                        adapter.notifyDataSetChanged();
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
                        MyToast.show(this, "取消关注");
                        contactsListEntity_one.setIsFollow("1");
                        adapter.notifyDataSetChanged();
                    }
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
    public void OnFamousFialCallBack(String state, String respanse) {
        MyToast.show(this, "请求失败");
    }

    @Override
    public void closeProgress() {
        dismissProgressDialog();
    }

    @Override
    public void showProgress() {

    }


    /**
     * item 点击事件
     *
     * @param itemData
     * @param postion
     */
    @Override
    public void onClassifyPostionClick(ContactsListEntity itemData, int postion) {
        switch (postion) {
            case 1:// item 点击事件
                distributorid = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
                String sign2 = TGmd5.getMD5("" + distributorid + itemData.getId()+"");
                showLoadingProgressDialog(this, "");
                distributorHomePresenter.distributorHome(distributorid, itemData.getId()+"", sign2);
                /*if (itemData.()==3){
                    Intent intent1 = new Intent(this, TeacherHomeActivity.class);
                    intent1.putExtra("seeDistributorId",  itemData.getId()+"");
                    startActivity(intent1);
                }else {
                    Intent intent1 = new Intent(this, DistributorHomeActivity.class);
                    intent1.putExtra("seeDistributorId",  itemData.getId()+"");
                    startActivity(intent1);
                }*/
                MyToast.show(this, "详情页");
                break;
            case 2:// 关注与取消关注
                contactsListEntity_one = itemData;
                if (itemData.getIsFollow().equals("1")) {//加关注
                    String sign = TGmd5.getMD5(distributorid + itemData.getId());
                    myContactsListPresenter.doFollow(distributorid, itemData.getId(), sign);
                } else {// 取消关注
                    showQuitDialog(itemData.getId());
                }
                break;
        }
    }

    @Override
    public void OnDistributorHomeSuccCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
        try {
            JSONObject jsb = new JSONObject(respanse);
            JSONArray jsa = new JSONArray(jsb.getString("result"));
            JSONObject distributorModeljsa = jsa.getJSONObject(0);
            if (distributorModeljsa.get("UserType").toString().equals("3")) {
                //如果是讲师
                Intent intent1 = new Intent(MyContactsListActivity.this, TeacherHomeActivity.class);
                intent1.putExtra("seeDistributorId", distributorModeljsa.get("ID").toString());
                startActivity(intent1);
            } else {
                //普通导游
                Intent intent1 = new Intent(MyContactsListActivity.this, DistributorHomeActivity.class);
                intent1.putExtra("seeDistributorId", distributorModeljsa.get("ID").toString());
                startActivity(intent1);
            }
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
}
