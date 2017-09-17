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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * Created by Snow on 2016/3/7 0007.
 * 选择省
 */
public class SelectProvinceActivity extends BaseActivity implements OnListItemClickListener<ProvinceEntity> {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.tv_address)
    private TextView tv_name;
    @ViewInject(R.id.lv_list)
    private ListView lv_list;

    private ListViewDataAdapter<ProvinceEntity> provinceEntityListViewDataAdapter;
    private int nodistrict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_address);
        ViewUtils.inject(this);
        nodistrict = getIntent().getIntExtra("nodistrict", 0);
        tv_name.setText("省份");
        tv_title.setText("选择城市");
        String data_str = getDataJosn();
        initViewHolder();
        getData(data_str);

    }

    @OnClick({R.id.rl_back})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                Constants.TOTAL_ADDRESS="";
                Constants.COUNTRYPATH="";
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
            finish();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    public void initViewHolder() {
        provinceEntityListViewDataAdapter = new ListViewDataAdapter<ProvinceEntity>();
        provinceEntityListViewDataAdapter.setViewHolderClass(SelectProvinceActivity.this, SelectProvinceHolder.class);
        SelectProvinceHolder.setOnListItemClickListener(this);
        lv_list.setAdapter(provinceEntityListViewDataAdapter);
    }

    /**
     * 读取本地文件
     *
     * @return
     */
    public String getDataJosn() {
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

            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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

    @Override
    public void onListItemClick(ProvinceEntity itemData) {
        Constants.TOTAL_ADDRESS = itemData.getName();
        Constants.COUNTRYPATH = itemData.getDicpath();
        Constants.TOTAL_CITY = itemData.getDicpath();
        Intent intent =new Intent(SelectProvinceActivity.this,SelectCityActivity.class);
       /* Bundle pBunndle = new Bundle();
        pBunndle.putString("son", itemData.getSon());
        pBunndle.putString("city", itemData.getName());
        pBunndle.putInt("nodistrict",nodistrict);*/
        //        openActivity(SelectCityActivity.class, pBunndle);
        intent.putExtra("son",itemData.getSon());
        intent.putExtra("city",itemData.getName());
        intent.putExtra("nodistrict",nodistrict);
        startActivity(intent);

        finish();
    }
}
