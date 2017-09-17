package com.lvgou.distribution.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Constants;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.entity.ProvinceEntity;
import com.lvgou.distribution.inter.OnClassifyPostionClickListener;
import com.lvgou.distribution.presenter.DetialHotCityPresenter;
import com.lvgou.distribution.presenter.GroupAllPresenter;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.DetialHotCityView;
import com.lvgou.distribution.view.GroupView;
import com.lvgou.distribution.viewholder.HotCityViewHolder;
import com.lvgou.distribution.viewholder.ProvinceGroupViewHolder;
import com.lvgou.distribution.viewholder.SelectCityViewHolder;
import com.xdroid.common.utils.PreferenceHelper;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.functions.holder.ListViewDataAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Snow on 2016/9/28.
 * 选择热门城市
 */
public class SelectHotCityActivity extends BaseActivity implements GroupView, OnClassifyPostionClickListener<ProvinceEntity> {


    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.grid_hot_city)
    private GridView grid_hot_city;
    @ViewInject(R.id.ll_01)
    private LinearLayout ll_01;
    @ViewInject(R.id.lv_list_province)
    private ListView lv_province;
    @ViewInject(R.id.ll_02)
    private LinearLayout ll_02;
    @ViewInject(R.id.lv_list_city)
    private ListView lv_city;


    private GroupAllPresenter groupAllPresenter;
    private ListViewDataAdapter<ProvinceEntity> listViewDataAdapter;
    private ListViewDataAdapter<ProvinceEntity> provinceEntityListViewDataAdapter;
    private ListViewDataAdapter<ProvinceEntity> cityEntityListViewDataAdapter;


    private String distributorid = "";

    private String str_hotcity = "";

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1002:
                    getData(str_hotcity);
                    break;
            }
            super.handleMessage(msg);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_hotcity);
        ViewUtils.inject(this);
        tv_title.setText("省份-城市");
        distributorid = PreferenceHelper.readString(SelectHotCityActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        groupAllPresenter = new GroupAllPresenter(SelectHotCityActivity.this);

        initViewHolder();

        /**
         * 异步执行，获取城市列表
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                getDataJosn();
            }
        }).start();

        String sign = TGmd5.getMD5(distributorid);
        groupAllPresenter.getHotCity(distributorid, sign);

    }


    @OnClick({R.id.rl_back})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
        }
    }

    public void initViewHolder() {
        listViewDataAdapter = new ListViewDataAdapter<ProvinceEntity>();
        listViewDataAdapter.setViewHolderClass(this, HotCityViewHolder.class);
        HotCityViewHolder.setClassifyEntityOnClassifyPostionClickListener(this);
        grid_hot_city.setAdapter(listViewDataAdapter);


        provinceEntityListViewDataAdapter = new ListViewDataAdapter<ProvinceEntity>();
        provinceEntityListViewDataAdapter.setViewHolderClass(SelectHotCityActivity.this, ProvinceGroupViewHolder.class);
        ProvinceGroupViewHolder.setOnListItemClickListener(this);
        lv_province.setAdapter(provinceEntityListViewDataAdapter);


        cityEntityListViewDataAdapter = new ListViewDataAdapter<ProvinceEntity>();
        cityEntityListViewDataAdapter.setViewHolderClass(SelectHotCityActivity.this, SelectCityViewHolder.class);
        SelectCityViewHolder.setOnListItemClickListener(this);
        lv_city.setAdapter(cityEntityListViewDataAdapter);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        groupAllPresenter.dettach();
    }


    @Override
    protected void onResume() {
        super.onResume();
        groupAllPresenter.attach(this);
    }


    /**
     * 读取本地文件
     *
     * @return
     */
    public void getDataJosn() {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("assets/" + "area_Father.json");
        InputStreamReader streamReader = new InputStreamReader(is);
        BufferedReader reader = new BufferedReader(streamReader);
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            while ((line = reader.readLine()) != null) {
                // stringBuilder.append(line);
                stringBuilder.append(line);
            }
            reader.close();
            reader.close();
            is.close();


            str_hotcity = stringBuilder.toString();

            Message message = new Message();
            message.what = 1002;
            mHandler.sendMessage(message);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void getData(String json_file) {
        try {
            JSONArray array = new JSONArray(json_file);
            if (array != null && array.length() > 0) {
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    String dicpath = object.getString("dicpath");
                    String name = object.getString("name");
                    String son = object.getString("son");
                    ProvinceEntity provinceEntity = new ProvinceEntity(i + "", son, name, dicpath);
                    provinceEntityListViewDataAdapter.append(provinceEntity);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void getCityData(String str) {
        try {
            String address = str;
            JSONArray array = new JSONArray(address);
            if (array != null && array.length() > 0) {
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    String dicpath = object.getString("dicpath");
                    String name = object.getString("name");
                    String son = object.getString("son");
                    ProvinceEntity provinceEntity = new ProvinceEntity(i + "", son, name, dicpath);
                    cityEntityListViewDataAdapter.append(provinceEntity);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void closeProgress() {

    }

    @Override
    public void showProgress() {

    }


    /**
     * 成功回调
     *
     * @param respanse
     */
    @Override
    public void OnFamousSuccCallBack(String state, String respanse) {
        switch (Integer.parseInt(state)) {
            case 2:
                try {
                    JSONObject jsonObject = new JSONObject(respanse);
                    String status = jsonObject.getString("status");
                    if (status.equals("1")) {
                        String result = jsonObject.getString("result");
                        JSONArray jsonArray = new JSONArray(result);
                        String hot_city = jsonArray.get(0).toString();
                        listViewDataAdapter.removeAll();
                        JSONArray json_hotcity = new JSONArray(hot_city);
                        if (json_hotcity != null && json_hotcity.length() > 0) {
                            for (int i = 0; i < json_hotcity.length(); i++) {
                                JSONObject jsonObject_hot = json_hotcity.getJSONObject(i);
                                String id_ = jsonObject_hot.getString("ID");
                                String name = jsonObject_hot.getString("CountryName");
                                String path = jsonObject_hot.getString("CountryPath");
                                ProvinceEntity provinceEntity = new ProvinceEntity(id_, "", name, path);
                                listViewDataAdapter.append(provinceEntity);
                            }
                        }
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
     * @param
     */
    @Override
    public void OnFamousFialCallBack(String state, String respanse) {
        MyToast.show(SelectHotCityActivity.this, "请求失败");
    }


    /**
     * 热门城市点击回调
     *
     * @param itemData
     * @param postion
     */
    @Override
    public void onClassifyPostionClick(ProvinceEntity itemData, int postion) {
        switch (postion) {
            case 1:
                listViewDataAdapter.notifyDataSetChanged();
                Constants.TOTAL_ADDRESS_GROUP = itemData.getName();
                Constants.COUNTRYPATH_GROUP = itemData.getDicpath();
                finish();
                break;
            case 2:
                ll_01.setVisibility(View.GONE);
                ll_02.setVisibility(View.VISIBLE);
                getCityData(itemData.getSon());
                break;
            case 3:
                Constants.TOTAL_ADDRESS_GROUP = itemData.getName();
                Constants.COUNTRYPATH_GROUP = itemData.getDicpath();
                finish();
                break;
        }
    }
}
