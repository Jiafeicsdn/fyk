package com.lvgou.distribution.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.adapter.NotUseVoucherAdapter;
import com.lvgou.distribution.adapter.VoucherAdapter;
import com.lvgou.distribution.inter.AdapterToFraImpl;
import com.lvgou.distribution.widget.XListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/7.
 * 系列课听课券列表
 */

public class UseVoucherActivity extends BaseActivity implements View.OnClickListener {

    private ArrayList<HashMap<String, Object>> voucherListAll;
    private String sizenum;
    private int courseSize = 0;
    private int commentSize = 0;
    private ArrayList<HashMap<String, Object>> linshiList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usevoucher);
        sizenum = getIntent().getStringExtra("sizenum");
        courseSize = getIntent().getIntExtra("courseSize", 0);
        commentSize = getIntent().getIntExtra("commentSize", 0);
        linshiList = (ArrayList<HashMap<String, Object>>) mcache.getAsObject("selectcoursevou");
        initView();
        initDatas();
        initClick();
    }

    private void initDatas() {
        voucherListAll = (ArrayList<HashMap<String, Object>>) mcache.getAsObject("voucherListavailable");
        if (voucherListAll == null) {
            voucherListAll = new ArrayList<>();
        }
        voucherAdapter.setData(voucherListAll);
        voucherAdapter.notifyDataSetChanged();
        for (int i = 0; i < voucherListAll.size(); i++) {
            if (voucherListAll.get(i).get("isCheck").toString().equals("true")) {
                //如果列表里有选择的,就将它添加进去
                voucherListselecter.add(voucherListAll.get(i));
            }
        }

    }

    private RelativeLayout rl_back;//返回
    private TextView tv_title;//标题
    private XListView mListView;
    private VoucherAdapter voucherAdapter;
    private View voucherHeader;
    private TextView tv_num_available;

    private void initView() {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("使用听课券");

        mListView = (XListView) findViewById(R.id.list_view);
        voucherAdapter = new VoucherAdapter(this, courseSize, commentSize);
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
        tv_num_available.setText(sizenum + "");
        mListView.addHeaderView(voucherHeader);

    }

    private ArrayList<HashMap<String, Object>> voucherListselecter = new ArrayList<>();

    private void initClick() {
        rl_back.setOnClickListener(this);
        voucherAdapter.setAdapterToFraImpl(new AdapterToFraImpl() {
            @Override
            public void doSomeThing(HashMap<String, Object> info) {
                voucherListselecter.clear();
                for (int i = 0; i < voucherListAll.size(); i++) {
                    if (voucherListAll.get(i).get("ID").toString().equals(info.get("ID").toString())) {
                        voucherListAll.get(i).put("isCheck", info.get("isCheck"));
                    }
                    if (voucherListAll.get(i).get("isCheck").toString().equals("true")) {
                        //如果列表里有选择的,就将它添加进去
                        voucherListselecter.add(voucherListAll.get(i));
                    }
                }


            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                int money = 0;
                /*Log.e("ajhfdkafds", "------------"+voucherListselecter );
                Log.e("ajhfdkafds", "============"+linshiList );*/
                ArrayList<HashMap<String, Object>> voucherListselecter22 = new ArrayList<>();
                ArrayList<HashMap<String, Object>> voucherListselecterss = new ArrayList<>();
                voucherListselecter22.addAll(voucherListselecter);
                for (int i = 0; i < linshiList.size(); i++) {
                    for (int i1 = 0; i1 < voucherListselecter22.size(); i1++) {
                        if (voucherListselecter22.get(i1).get("TeacherID").toString().equals(linshiList.get(i).get("ID").toString())){
                            //如果这个券对应这堂课
                            //增加money后暂时删除券，防止下面循环再次添加
                            if (Integer.parseInt(voucherListselecter22.get(i1).get("TuanBi").toString()) < Integer.parseInt(linshiList.get(i).get("CKTuanBi").toString())) {
                                money += Integer.parseInt(voucherListselecter22.get(i1).get("TuanBi").toString());
                            } else {
                                money += Integer.parseInt(linshiList.get(i).get("CKTuanBi").toString());
                            }
                            voucherListselecterss.add(voucherListselecter22.get(i1));
                            voucherListselecter22.remove(i1);
                            break;
                        }
                    }
                }
                Map<String, Integer> map2 = new HashMap<String, Integer>();
                for (HashMap<String, Object> hashMap : linshiList) {
                    map2.put(hashMap.get("ID").toString(), Integer.parseInt(hashMap.get("CKTuanBi").toString()));
                }
                List<Map.Entry<String, Integer>> infoIds2 = new ArrayList<Map.Entry<String, Integer>>(map2.entrySet());
//排序
                Collections.sort(infoIds2, new Comparator<Map.Entry<String, Integer>>() {
                    public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                        //return (o2.getValue() - o1.getValue());
                        return (o2.getValue()).compareTo(o1.getValue());
                    }
                });
                //---------
//                String couponid = "";//听课券id
                Map<String, Integer> map = new HashMap<String, Integer>();
                for (HashMap<String, Object> hashMap : voucherListselecter22) {
                    map.put(hashMap.get("ID").toString(), Integer.parseInt(hashMap.get("TuanBi").toString()));
                }
                List<Map.Entry<String, Integer>> infoIds = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
//排序
                Collections.sort(infoIds, new Comparator<Map.Entry<String, Integer>>() {
                    public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                        //return (o2.getValue() - o1.getValue());
                        return (o2.getValue()).compareTo(o1.getValue());
                    }
                });
                for (int i = 0; i < infoIds.size(); i++) {
                    if (Integer.parseInt(infoIds.get(i).getValue().toString()) < Integer.parseInt(infoIds2.get(i).getValue().toString())) {
                        money += Integer.parseInt(infoIds.get(i).getValue().toString());
                    } else {
                        money += Integer.parseInt(infoIds2.get(i).getValue().toString());
                    }
                }


                for (int i = 0; i < voucherListselecter22.size(); i++) {
                    for (Map.Entry<String, Integer> infoId : infoIds) {
                        if (infoId.getKey().toString().equals(voucherListselecter22.get(i).get("ID"))){
                            voucherListselecterss.add(voucherListselecter22.get(i));
                            break;
                        }
                    }
                }

                mcache.put("voucherListavailable", voucherListAll);
                //数据是使用Intent返回
                Intent intent = new Intent();
                //把返回数据存入Intent
                intent.putExtra("money", money + "");
                mcache.put("voucherListselecter", voucherListselecterss);
//                intent.putExtra("couponid", couponid + "");
                //设置返回数据
                UseVoucherActivity.this.setResult(RESULT_OK, intent);
                //关闭Activity
                UseVoucherActivity.this.finish();
                break;
        }
    }


    //返回键监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            int money = 0;
            Map<String, Integer> map2 = new HashMap<String, Integer>();
            for (HashMap<String, Object> hashMap : linshiList) {
                map2.put(hashMap.get("ID").toString(), Integer.parseInt(hashMap.get("CKTuanBi").toString()));
            }
            List<Map.Entry<String, Integer>> infoIds2 = new ArrayList<Map.Entry<String, Integer>>(map2.entrySet());
//排序
            Collections.sort(infoIds2, new Comparator<Map.Entry<String, Integer>>() {
                public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                    //return (o2.getValue() - o1.getValue());
                    return (o2.getValue()).compareTo(o1.getValue());
                }
            });
            //---------
//                String couponid = "";//听课券id
            Map<String, Integer> map = new HashMap<String, Integer>();
            for (HashMap<String, Object> hashMap : voucherListselecter) {
                map.put(hashMap.get("ID").toString(), Integer.parseInt(hashMap.get("TuanBi").toString()));
            }
            List<Map.Entry<String, Integer>> infoIds = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
//排序
            Collections.sort(infoIds, new Comparator<Map.Entry<String, Integer>>() {
                public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                    //return (o2.getValue() - o1.getValue());
                    return (o2.getValue()).compareTo(o1.getValue());
                }
            });
            for (int i = 0; i < infoIds.size(); i++) {
                if (Integer.parseInt(infoIds.get(i).getValue().toString()) < Integer.parseInt(infoIds2.get(i).getValue().toString())) {
                    money += Integer.parseInt(infoIds.get(i).getValue().toString());
                } else {
                    money += Integer.parseInt(infoIds2.get(i).getValue().toString());
                }
            }

            ArrayList<HashMap<String, Object>> voucherListselecterss = new ArrayList<>();
            for (int i = 0; i < voucherListselecter.size(); i++) {
                for (Map.Entry<String, Integer> infoId : infoIds) {
                    if (infoId.getKey().toString().equals(voucherListselecter.get(i).get("ID"))){
                        voucherListselecterss.add(voucherListselecter.get(i));
                        break;
                    }
                }
            }
            Log.e("khjasjkfd", "-----------"+voucherListselecter );
            mcache.put("voucherListavailable", voucherListAll);
            //数据是使用Intent返回
            Intent intent = new Intent();
            //把返回数据存入Intent
            intent.putExtra("money", money + "");
            mcache.put("voucherListselecter", voucherListselecterss);
//                intent.putExtra("couponid", couponid + "");
            //设置返回数据
            UseVoucherActivity.this.setResult(RESULT_OK, intent);
            //关闭Activity
            UseVoucherActivity.this.finish();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
