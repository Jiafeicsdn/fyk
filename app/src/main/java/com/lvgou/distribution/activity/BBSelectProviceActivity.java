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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Snow on 2016/7/15 0015.
 * 选择城市
 */
public class BBSelectProviceActivity extends BaseActivity implements OnListItemClickListener<ProvinceEntity> {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.tv_address)
    private TextView tv_name;
    @ViewInject(R.id.lv_list)
    private ListView lv_list;

    private ListViewDataAdapter<ProvinceEntity> provinceEntityListViewDataAdapter;
    private String index = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_address);
        ViewUtils.inject(this);
        index = getTextFromBundle("index");
        tv_name.setText("省份");
        tv_title.setText("预约");
        String data_str = getDataJosn();
        initViewHolder();
        getData(data_str);

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
        provinceEntityListViewDataAdapter = new ListViewDataAdapter<ProvinceEntity>();
        provinceEntityListViewDataAdapter.setViewHolderClass(BBSelectProviceActivity.this, SelectProvinceHolder.class);
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
        Constants.CITY_PATH = itemData.getDicpath();
        Constants.TOTAL_CITY = itemData.getName();
        Bundle pBunndle = new Bundle();
        pBunndle.putString("index", index);
        pBunndle.putString("son", itemData.getSon());
        pBunndle.putString("city", itemData.getName());
        openActivity(BBSelectCityActivity.class, pBunndle);
        finish();
    }
}
