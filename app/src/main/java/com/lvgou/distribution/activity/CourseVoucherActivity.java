package com.lvgou.distribution.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.adapter.CourseVoucherAdapter;
import com.lvgou.distribution.adapter.VoucherAdapter;
import com.lvgou.distribution.inter.AdapterToFraImpl;
import com.lvgou.distribution.widget.XListView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/4/19.
 */

public class CourseVoucherActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usevoucher);
        initView();
        initDatas();
        initClick();
    }

    private ArrayList<HashMap<String, Object>> voucherListAll;

    private void initDatas() {
        voucherListAll = (ArrayList<HashMap<String, Object>>) mcache.getAsObject("coursevoucherList");
        voucher = (HashMap<String, Object>) mcache.getAsObject("isselectvoucher");
        if (voucher != null && voucherListAll != null) {
            if (voucher.containsKey("ID")) {
                for (int i = 0; i < voucherListAll.size(); i++) {
                    if (voucher.get("ID").toString().equals(voucherListAll.get(i).get("ID").toString())) {
                        voucherListAll.get(i).put("isCheck", true);
                    }
                }
                voucherAdapter.setData(voucherListAll, 1);
            } else {
                voucherAdapter.setData(voucherListAll, 0);
            }
        } else if (voucherListAll != null) {
            voucherAdapter.setData(voucherListAll, 0);

        }
        if (voucherListAll != null) {
            tv_num_available.setText(voucherListAll.size() + "");
        }
    }

    private RelativeLayout rl_back;//返回
    private TextView tv_title;//标题
    private XListView mListView;
    private CourseVoucherAdapter voucherAdapter;
    private View voucherHeader;
    private TextView tv_num_available;

    private void initView() {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("使用听课券");

        mListView = (XListView) findViewById(R.id.list_view);
        voucherAdapter = new CourseVoucherAdapter(this);
        mListView.setPullRefreshEnable(false);
        mListView.setPullLoadEnable(false);
//        mListView.stopLoadMore();
        mListView.setAutoLoadEnable(false);
//        mListView.setXListViewListener(this);
//        mListView.setRefreshTime(this.getTime());
        mListView.setDivider(null);
        mListView.setAdapter(voucherAdapter);
        voucherHeader = LayoutInflater.from(this).inflate(R.layout.voucher_header, null);
        tv_num_available = (TextView) voucherHeader.findViewById(R.id.tv_num_available);


        mListView.addHeaderView(voucherHeader);
    }

    private HashMap<String, Object> voucher = new HashMap<>();

    private void initClick() {
        rl_back.setOnClickListener(this);
        voucherAdapter.setAdapterToFraImpl(new AdapterToFraImpl() {
            @Override
            public void doSomeThing(HashMap<String, Object> info) {
                for (int i = 0; i < voucherListAll.size(); i++) {
                    if (voucherListAll.get(i).get("ID").toString().equals(info.get("ID").toString())) {
                        voucherListAll.get(i).put("isCheck", info.get("isCheck"));
                        if (info.get("isCheck").toString().equals("true")) {
                            voucher = info;
                        } else {
                            voucher = new HashMap<String, Object>();
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                mcache.put("isselectvoucher", voucher);
                finish();
                break;
        }
    }
}
