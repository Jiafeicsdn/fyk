package com.lvgou.distribution.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.lvgou.distribution.utils.MyToast;import android.widget.Toast;

import com.anthonycr.grant.PermissionsManager;
import com.anthonycr.grant.PermissionsResultAction;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.PreferenceInject;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.entity.MoreFriendsEntity;
import com.lvgou.distribution.inter.OnClassifyPostionClickListener;
import com.lvgou.distribution.presenter.MyContactsListPresenter;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.GroupView;
import com.lvgou.distribution.viewholder.MoreFriendViewHolder;
import com.lvgou.distribution.viewholder.MyScheduleViewHolder;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.common.utils.PreferenceHelper;
import com.xdroid.common.utils.StringUtils;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.functions.holder.ListViewDataAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Administrator on 2016/10/21.
 * 关注，更多好友列表，包括 可能认识的人，与推荐认识的人
 */
public class MoreFriendListActivity extends BaseActivity implements GroupView, OnClassifyPostionClickListener<MoreFriendsEntity> {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.lv_listview)
    private ListView lv_list_view;
    @ViewInject(R.id.ll_visibilty)
    private LinearLayout ll_visibilty;
    @ViewInject(R.id.rl_none)
    private RelativeLayout rl_none;

    private ListViewDataAdapter<MoreFriendsEntity> moreFriendsEntityListViewDataAdapter;

    private MyContactsListPresenter myContactsListPresenter;


    private Dialog dialog_del_can;// 取消，删除弹框
    private Dialog dialog_stop;

    private String distributorid = "";

    private MoreFriendsEntity moreFriendsEntitOne = null;

    private String contacts_datas = "";

    private List<String> list_phones = new ArrayList<String>();

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1001:
                    if (list_phones.size() > 0) {
                        String str_phone = list_phones.toString().replace(", ", "");
                        if (str_phone.length() > 2) {
                            String sign = TGmd5.getMD5(distributorid + str_phone.substring(1, str_phone.length() - 2));
                            myContactsListPresenter.getContactsList(distributorid, str_phone.substring(1, str_phone.length() - 2), sign);
                        }
                    } else {
                        String sign = TGmd5.getMD5(distributorid + "");
                        myContactsListPresenter.getContactsList(distributorid, "", sign);
                    }
                    break;
                case 1003:
                    showStop();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_friend);
        ViewUtils.inject(this);
        tv_title.setText("更多好友");
        distributorid = PreferenceHelper.readString(MoreFriendListActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);

        myContactsListPresenter = new MyContactsListPresenter(this);


        initViewHolder();

        showLoadingProgressDialog(MoreFriendListActivity.this, "");
        PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(this,
                new String[]{Manifest.permission.READ_CONTACTS}, new PermissionsResultAction() {

                    @Override
                    public void onGranted() {
                        doRequest();
                    }

                    @Override
                    public void onDenied(String permission) {
                        doRequest();
                    }
                }
        );
    }

    public void doRequest() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                testReadAllContacts();
            }
        }).start();
    }

    @OnClick({R.id.rl_back})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }

    public void initViewHolder() {
        moreFriendsEntityListViewDataAdapter = new ListViewDataAdapter<MoreFriendsEntity>();
        moreFriendsEntityListViewDataAdapter.setViewHolderClass(this, MoreFriendViewHolder.class);
        MoreFriendViewHolder.setFriendsEntityOnClassifyPostionClickListener(this);
        lv_list_view.setAdapter(moreFriendsEntityListViewDataAdapter);
    }

    public void testReadAllContacts() {
        Cursor cursor = null;
        try {
            cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        } catch (Exception e) {
            Message message = new Message();
            message.what = 1001;
            handler.sendMessage(message);
        }
        if (cursor != null) {
            int contactIdIndex = 0;
            if (cursor.getCount() > 0) {
                contactIdIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID);
            }

            while (cursor.moveToNext()) {
                String contactId = cursor.getString(contactIdIndex);
            /*
             * 查找该联系人的phone信息
             */

                Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId,
                        null, null);
                int phoneIndex = 0;
                if (phones.getCount() > 0) {
                    phoneIndex = phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                }
                while (phones.moveToNext()) {
                    String phoneNumber = phones.getString(phoneIndex);
                    if (StringUtils.isPhone(phoneNumber)) {
                        list_phones.add(phoneNumber + "|");
                    }
                }
            }
        }

        Message message = new Message();
        message.what = 1001;
        handler.sendMessage(message);


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
                        String result = jsonObject.getString("result");
                        JSONArray array = new JSONArray(result);

                        /*****联系人好友*****/
                        String contacts_data = array.get(0).toString();
                        contacts_datas = array.get(0).toString();
                        JSONArray array_contact = new JSONArray(contacts_data);
                        if (array_contact != null && array_contact.length() > 0) {
                            if (array_contact.length() > 4) {
                                for (int i = 0; i < 4; i++) {
                                    JSONObject jsonObject_one = array_contact.getJSONObject(i);
                                    String num = array_contact.length() + "";
                                    String id = jsonObject_one.getString("ID");
                                    String PicUrl = jsonObject_one.getString("PicUrl");
                                    String RealName = jsonObject_one.getString("RealName");
                                    String isFollow = jsonObject_one.getString("TuanBi");
                                    String sex = jsonObject_one.getString("Attr");
                                    String FengwenCount = jsonObject_one.getString("FengwenCount");
                                    String FansCount = jsonObject_one.getString("FansCount");
                                    MoreFriendsEntity moreFriendsEntity = new MoreFriendsEntity(id, PicUrl, RealName, FengwenCount, FansCount, isFollow, "1", num, "", sex);
                                    moreFriendsEntity.setState(jsonObject_one.getInt("State"));
                                    moreFriendsEntity.setUserType(jsonObject_one.getInt("UserType"));
                                    moreFriendsEntityListViewDataAdapter.append(moreFriendsEntity);
                                }
                            } else {
                                for (int l = 0; l < array_contact.length(); l++) {
                                    JSONObject jsonObject_one = array_contact.getJSONObject(l);
                                    String num = array_contact.length() + "";
                                    String id = jsonObject_one.getString("ID");
                                    String PicUrl = jsonObject_one.getString("PicUrl");
                                    String RealName = jsonObject_one.getString("RealName");
                                    String isFollow = jsonObject_one.getString("TuanBi");
                                    String sex = jsonObject_one.getString("Attr");
                                    String FengwenCount = jsonObject_one.getString("FengwenCount");
                                    String FansCount = jsonObject_one.getString("FansCount");
                                    MoreFriendsEntity moreFriendsEntity = new MoreFriendsEntity(id, PicUrl, RealName, FengwenCount, FansCount, isFollow, "1", num, "", sex);
                                    moreFriendsEntity.setState(jsonObject_one.getInt("State"));
                                    moreFriendsEntity.setUserType(jsonObject_one.getInt("UserType"));
                                    moreFriendsEntityListViewDataAdapter.append(moreFriendsEntity);
                                }
                            }
                        }
                        /*****推荐好友*****/
                        String recommend_data = array.get(1).toString();
                        JSONArray array_recommend = new JSONArray(recommend_data);
                        if (array_recommend != null && array_recommend.length() > 0) {
                            if (array_recommend.length() > 4) {
                                for (int j = 0; j < 4; j++) {
                                    JSONObject jsonObject_one = array_recommend.getJSONObject(j);
                                    String num = array_contact.length() + "";
                                    String num_2 = array_recommend.length() + "";
                                    String id = jsonObject_one.getString("ID");
                                    String PicUrl = jsonObject_one.getString("PicUrl");
                                    String RealName = jsonObject_one.getString("RealName");
                                    String isFollow = jsonObject_one.getString("TuanBi");
                                    String sex = jsonObject_one.getString("Attr");
                                    String FengwenCount = jsonObject_one.getString("FengwenCount");
                                    String FansCount = jsonObject_one.getString("FansCount");
                                    MoreFriendsEntity moreFriendsEntity = new MoreFriendsEntity(id, PicUrl, RealName, FengwenCount, FansCount, isFollow, "2", num, num_2, sex);
                                    moreFriendsEntity.setState(jsonObject_one.getInt("State"));
                                    moreFriendsEntity.setUserType(jsonObject_one.getInt("UserType"));
                                    moreFriendsEntityListViewDataAdapter.append(moreFriendsEntity);
                                }
                            } else {
                                for (int k = 0; k < array_recommend.length(); k++) {
                                    JSONObject jsonObject_one = array_recommend.getJSONObject(k);
                                    String num = array_contact.length() + "";
                                    String num_2 = array_recommend.length() + "";
                                    String id = jsonObject_one.getString("ID");
                                    String PicUrl = jsonObject_one.getString("PicUrl");
                                    String RealName = jsonObject_one.getString("RealName");
                                    String sex = jsonObject_one.getString("Attr");
                                    String isFollow = jsonObject_one.getString("TuanBi");
                                    String FengwenCount = jsonObject_one.getString("FengwenCount");
                                    String FansCount = jsonObject_one.getString("FansCount");
                                    MoreFriendsEntity moreFriendsEntity = new MoreFriendsEntity(id, PicUrl, RealName, FengwenCount, FansCount, isFollow, "2", num, num_2, sex);
                                    moreFriendsEntity.setState(jsonObject_one.getInt("State"));
                                    moreFriendsEntity.setUserType(jsonObject_one.getInt("UserType"));
                                    moreFriendsEntityListViewDataAdapter.append(moreFriendsEntity);
                                }
                            }
                        }

                        if (array_contact.length() == 0 && array_recommend.length() == 0) {
                            showOrGone();
                            rl_none.setVisibility(View.VISIBLE);
                        } else {
                            showOrGone();
                            ll_visibilty.setVisibility(View.VISIBLE);
                        }
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
                        MyToast.show(this, "关注成功");
                        moreFriendsEntitOne.setIsFollow("2");
                        moreFriendsEntityListViewDataAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                try {
                    JSONObject jsonObject = new JSONObject(respanse);
                    String status = jsonObject.getString("status");
                    if (status.equals("1")) {
                        MyToast.show(this, "取消关注");
                        moreFriendsEntitOne.setIsFollow("1");
                        moreFriendsEntityListViewDataAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }


    public void showOrGone() {
        ll_visibilty.setVisibility(View.GONE);
        rl_none.setVisibility(View.GONE);
    }

    /**
     * 失败回调
     *
     * @param state
     * @param respanse
     */
    @Override
    public void OnFamousFialCallBack(String state, String respanse) {
        showOrGone();
        rl_none.setVisibility(View.VISIBLE);
    }


    /**
     * 取消弹框
     */
    @Override
    public void closeProgress() {
        closeLoadingProgressDialog();
    }

    @Override
    public void showProgress() {

    }


    /**
     * id
     * 删除取消弹框
     */
    public void showQuitDialog(final String id) {
        dialog_del_can = new Dialog(MoreFriendListActivity.this, R.style.Mydialog);
        View view1 = View.inflate(MoreFriendListActivity.this,
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


    /**
     * item 点击事件
     *
     * @param itemData
     * @param postion
     */
    @Override
    public void onClassifyPostionClick(MoreFriendsEntity itemData, int postion) {
        Bundle bundle = new Bundle();
        switch (postion) {
            case 1:
                if (itemData.getUserType()==3){
                    Intent intent1 = new Intent(this, TeacherHomeActivity.class);
                    intent1.putExtra("seeDistributorId",  itemData.getId()+"");
                    startActivity(intent1);
                }else {
                    Intent intent1 = new Intent(this, DistributorHomeActivity.class);
                    intent1.putExtra("seeDistributorId",  itemData.getId()+"");
                    startActivity(intent1);
                }
                /*bundle.putInt("distributorid", Integer.parseInt(itemData.getId()));
                openActivity(UserPersonalCenterActivity.class, bundle);*/
                break;
            case 2://更多
                if (itemData.getType().equals("1")) {
                    bundle.putString("contacts_datas", contacts_datas);
                    openActivity(MyContactsListActivity.class, bundle);
                } else {
                    openActivity(UserListActivity.class);
                }
                break;
            case 3:
                moreFriendsEntitOne = itemData;
                if (itemData.getIsFollow().equals("1")) {//加关注
                    String sign = TGmd5.getMD5(distributorid + itemData.getId());
                    myContactsListPresenter.doFollow(distributorid, itemData.getId(), sign);
                } else {// 取消关注
                    showQuitDialog(itemData.getId());
                }
                break;
        }
    }

    /**
     * 账号停用弹窗
     */
    public void showStop() {
        dialog_stop = new Dialog(MoreFriendListActivity.this, R.style.Mydialog);
        View view1 = View.inflate(MoreFriendListActivity.this, R.layout.dialog_show_check_stop, null);
        Button sure = (Button) view1.findViewById(R.id.sure);
        TextView tv_title = (TextView) view1.findViewById(R.id.tv_title);
        tv_title.setText("请设置获取联系人权限！");
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_stop.dismiss();
                finish();
            }
        });
        dialog_stop.setContentView(view1);
        dialog_stop.show();
    }


    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}