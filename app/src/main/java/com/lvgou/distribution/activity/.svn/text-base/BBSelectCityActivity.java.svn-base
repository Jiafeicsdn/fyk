package com.lvgou.distribution.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Constants;
import com.lvgou.distribution.entity.ProvinceEntity;
import com.lvgou.distribution.inter.OnListItemClickListener;
import com.lvgou.distribution.viewholder.SelectProvinceHolder;
import com.xdroid.functions.holder.ListViewDataAdapter;
import com.xdroid.utils.AppManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Snow on 2016/7/15 0015.
 * 选择城市
 */
public class BBSelectCityActivity extends BaseActivity implements OnListItemClickListener<ProvinceEntity> {


    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.rl_select)
    private RelativeLayout rl_select;
    @ViewInject(R.id.tv_address)
    private TextView tv_name;
    @ViewInject(R.id.lv_list)
    private ListView lv_list;

    private ListViewDataAdapter<ProvinceEntity> provinceEntityListViewDataAdapter;

    private String province;
    private String son;
    private String index = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_address);
        ViewUtils.inject(this);

        tv_title.setText("预约");
        province = getTextFromBundle("city");
        son = getTextFromBundle("son");
        index = getTextFromBundle("index");
        tv_name.setText(province + "");

        initViewHolder();
        getData();
    }

    @OnClick({R.id.rl_back, R.id.rl_select})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_select:
                finish();
                break;
        }
    }

    public void initViewHolder() {
        provinceEntityListViewDataAdapter = new ListViewDataAdapter<ProvinceEntity>();
        provinceEntityListViewDataAdapter.setViewHolderClass(BBSelectCityActivity.this, SelectProvinceHolder.class);
        SelectProvinceHolder.setOnListItemClickListener(this);
        lv_list.setAdapter(provinceEntityListViewDataAdapter);
    }

    public void getData() {
        try {
            String address = son;
            JSONArray array = new JSONArray(address);
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

    @Override
    public void onListItemClick(ProvinceEntity itemData) {
        Constants.CITY_PATH = itemData.getDicpath();
        Constants.TOTAL_CITY = province + itemData.getName();
        finish();
    }
}