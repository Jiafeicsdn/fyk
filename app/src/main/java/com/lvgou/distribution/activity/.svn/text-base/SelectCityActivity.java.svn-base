package com.lvgou.distribution.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
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
import com.umeng.analytics.MobclickAgent;
import com.xdroid.functions.holder.ListViewDataAdapter;
import com.xdroid.utils.AppManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Snow on 2016/3/7 0007.
 * 选择城市
 */
public class SelectCityActivity extends BaseActivity implements OnListItemClickListener<ProvinceEntity> {


    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.tv_address)
    private TextView tv_name;
    @ViewInject(R.id.lv_list)
    private ListView lv_list;

    private ListViewDataAdapter<ProvinceEntity> provinceEntityListViewDataAdapter;

    private String province;
    private String son;
    private String index = "";
    private int nodistrict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_address);
        ViewUtils.inject(this);

        tv_title.setText("认证信息");
        province = getIntent().getStringExtra("city");
        son = getIntent().getStringExtra("son");
        index = getIntent().getStringExtra("index");
        nodistrict = getIntent().getIntExtra("nodistrict", 0);
        tv_name.setText(province + " -城市");

        initViewHolder();
        getData();
    }

    @OnClick({R.id.rl_back})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
//                Constants.TOTAL_ADDRESS = province;
                Constants.TOTAL_ADDRESS="";
                Constants.COUNTRYPATH="";
                Intent intent =new Intent(SelectCityActivity.this,SelectProvinceActivity.class);
                intent.putExtra("nodistrict",nodistrict);
                startActivity(intent);
//                openActivity(SelectProvinceActivity.class);
                finish();
                break;
        }
    }
    //返回键监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Constants.TOTAL_ADDRESS="";
            Constants.COUNTRYPATH="";
            Intent intent =new Intent(SelectCityActivity.this,SelectProvinceActivity.class);
            intent.putExtra("nodistrict",nodistrict);
            startActivity(intent);
//            openActivity(SelectProvinceActivity.class);
            finish();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    public void initViewHolder() {
        provinceEntityListViewDataAdapter = new ListViewDataAdapter<ProvinceEntity>();
        provinceEntityListViewDataAdapter.setViewHolderClass(SelectCityActivity.this, SelectProvinceHolder.class);
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
        Constants.TOTAL_ADDRESS = province + itemData.getName();
        Constants.COUNTRYPATH = itemData.getDicpath();
        if (nodistrict==1){
            finish();
        }else {
            Bundle pBunndle = new Bundle();
            pBunndle.putString("son", itemData.getSon());
            pBunndle.putString("sonson", son);
            pBunndle.putString("province", province);
            pBunndle.putString("city", itemData.getName());
            openActivity(SelectDistrictActivity.class, pBunndle);
            finish();
        }
    }

}
