package com.lvgou.distribution.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.utils.MyToast;

import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lvgou.distribution.R;
import com.lvgou.distribution.adapter.SeriesBatchAdapter;
import com.lvgou.distribution.adapter.SeriesListAdapter;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.inter.AdapterToFraImpl;
import com.lvgou.distribution.presenter.BatchBuyStudyPresenter;
import com.lvgou.distribution.presenter.BatchbBuyPresenter;
import com.lvgou.distribution.utils.DensityUtil;
import com.lvgou.distribution.utils.ScrollableLayout;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.BatchBuyStudyView;
import com.lvgou.distribution.view.BatchbBuyView;
import com.lvgou.distribution.widget.XListView;
import com.xdroid.common.utils.PreferenceHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/6.
 * 系列课
 */

public class SeriesClassActivity extends BaseActivity implements XListView.IXListViewListener, View.OnClickListener, BatchBuyStudyView, BatchbBuyView {
    private BatchBuyStudyPresenter batchBuyStudyPresenter;
    private BatchbBuyPresenter batchbBuyPresenter;
    private String linkUrl = "";
    private SeriesBatchAdapter seriesBatchAdapter;
    private int courseSize = 0;
    private int commentSize = 0;
    private String distributorid;
    private ArrayList<HashMap<String, Object>> linshiList;
    private String yHMoney = "0";//听课券减去的金额
    private int totalCktuanbi;//总共应付团币
    private String userTuanBi;
    //    private String couponid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series_class);
        batchBuyStudyPresenter = new BatchBuyStudyPresenter(this);
        batchbBuyPresenter = new BatchbBuyPresenter(this);
        linkUrl = getIntent().getStringExtra("linkUrl");
        mcache.remove("voucherListavailable");//初始化删除缓存中的听课券
        mcache.remove("voucherListselecter");//初始化删除缓存中的选中听课券
        userTuanBi = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.TUANBI);

        initView();
        initDatas();
        initClick();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private XListView mListView;
    private SeriesListAdapter seriesListAdapter;
    private ScrollableLayout sl_root;
    private float avatarTop;
    private TextView tv_spit;
    private RelativeLayout rl_back;//返回
    private TextView tv_banner;
    private ImageView iv_title;//头部图片
    private TextView tv_total_course;//
    private RelativeLayout rl_batch;//批量
    private RelativeLayout rl_buy;//底部的立即购买
    private TextView tv_gobuy;//立即购买
    private RelativeLayout rl_all_check;//全选
    private CheckBox cb_all_check;//全选的checkbox
    private RelativeLayout rl_button_gift;//底部听课券
    private TextView tv_total_money;//总共金币
    //    private RelativeLayout rl_tingkequan;//听课券，去听课券页面
//    private TextView tv_available;//可用听课券
    private TextView tv_cancle;
    private ImageView im_download;//批量小图标
    private TextView tv_piliangxiazai;
    private RelativeLayout rl_title;
    private float hearderMaxHeight;
    private RelativeLayout rl_text;

    private void initView() {
        tv_piliangxiazai = (TextView) findViewById(R.id.tv_piliangxiazai);
        im_download = (ImageView) findViewById(R.id.im_download);
        tv_cancle = (TextView) findViewById(R.id.tv_cancle);
        sl_root = (ScrollableLayout) findViewById(R.id.sl_root);
        tv_spit = (TextView) findViewById(R.id.tv_spit);
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        tv_banner = (TextView) findViewById(R.id.tv_banner);
        iv_title = (ImageView) findViewById(R.id.iv_title);
        rl_title = (RelativeLayout) findViewById(R.id.rl_title);
        rl_title.getBackground().setAlpha(0);
        tv_total_course = (TextView) findViewById(R.id.tv_total_course);
        rl_batch = (RelativeLayout) findViewById(R.id.rl_batch);
        rl_buy = (RelativeLayout) findViewById(R.id.rl_buy);
        rl_buy.setVisibility(View.GONE);
        rl_text = (RelativeLayout) findViewById(R.id.rl_text);
        tv_gobuy = (TextView) findViewById(R.id.tv_gobuy);
        rl_all_check = (RelativeLayout) findViewById(R.id.rl_all_check);
        cb_all_check = (CheckBox) findViewById(R.id.cb_all_check);
        rl_button_gift = (RelativeLayout) findViewById(R.id.rl_button_gift);
        tv_total_money = (TextView) findViewById(R.id.tv_total_money);
//        rl_tingkequan = (RelativeLayout) findViewById(R.id.rl_tingkequan);
//        tv_available = (TextView) findViewById(R.id.tv_available);


        mListView = (XListView) findViewById(R.id.list_view);
        seriesListAdapter = new SeriesListAdapter(this);
        mListView.setPullRefreshEnable(false);
        mListView.setPullLoadEnable(false);
//        mListView.stopLoadMore();
        mListView.setAutoLoadEnable(false);
        mListView.setXListViewListener(this);
        mListView.setRefreshTime(this.getTime());
        mListView.setDivider(null);
        seriesListAdapter.setData(new ArrayList<HashMap<String, Object>>());
        mListView.setAdapter(seriesListAdapter);
        sl_root.getHelper().setCurrentScrollableContainer(mListView);
        sl_root.setOnScrollListener(new ScrollableLayout.OnScrollListener() {
            @Override
            public void onScroll(int translationY, int maxY) {
                translationY = -translationY;
                if (avatarTop == 0) {
                    avatarTop = tv_spit.getTop();
                }
                if (0 > avatarTop + translationY) {
                    tv_spit.setVisibility(View.VISIBLE);
                } else {
                    tv_spit.setVisibility(View.GONE);
                }
                if (hearderMaxHeight == 0) {
                    hearderMaxHeight = rl_text.getTop();
                }
                int alpha = 0;
                int baseAlpha = 60;
                if (0 > avatarTop + translationY) {
                    alpha = Math.min(255, (int) (Math.abs(avatarTop + translationY) * (255 - baseAlpha) / (hearderMaxHeight - avatarTop) + baseAlpha));
                }
                float zz = (float) 215.0;
                int alp = (int) (255 * alpha / zz);
                rl_title.getBackground().setAlpha(alp);
                tv_spit.setTextColor(Color.argb(alp, 255, 255, 255));
            }
        });
    }

    public void doSomeThing(HashMap<String, Object> info) {
        mcache.remove("voucherListselecter");//初始化删除缓存中的选中听课券
        yHMoney="0";
        int size = 0;
        int isbuy = 0;
        totalCktuanbi = 0;
        int fubicoursesize = 0;
        teacherid = "";//课堂id clear
        linshiList = new ArrayList<>();
        linshiList.clear();
        ArrayList<HashMap<String, Object>> voucherListselecte = new ArrayList<>();
        ArrayList<HashMap<String, Object>> voucherListselectecom = new ArrayList<>();
        for (int i = 0; i < courseList.size(); i++) {
            if (courseList.get(i).get("ID").toString().equals(info.get("ID").toString())) {
                courseList.get(i).put("isCheck", info.get("isCheck"));
            }
            if (courseList.get(i).get("isCheck").toString().equals("true")) {
                totalCktuanbi += Integer.parseInt(courseList.get(i).get("CKTuanBi").toString());
                if (Integer.parseInt(courseList.get(i).get("CKTuanBi").toString()) > 0) {
                    fubicoursesize++;
                }
                size++;//如果有一个选中就+1
                linshiList.add(courseList.get(i));
                teacherid = teacherid + courseList.get(i).get("ID").toString() + ",";
                //如果有被选中的，获取id，对比普通券列表，将对应id的听课券存起来
                for (int i1 = 0; i1 < voucherListCom.size(); i1++) {
                    if (voucherListCom.get(i1).get("TeacherID").toString().equals(courseList.get(i).get("ID").toString())) {
                        voucherListselectecom.add(voucherListCom.get(i1));
                    }
                }


            } else if (Integer.parseInt(courseList.get(i).get("People_Apply").toString()) > 0) {
                //如果已经购买了的
                size++;
                isbuy++;
            }
        }
        voucherListselecte.addAll(voucherListselectecom);
//        voucherListselecte.addAll(voucherListAll);//全局券添加
        if (size == courseList.size()) {
            isallcheck = true;
            //如果全部选中
            cb_all_check.setBackgroundResource(R.mipmap.checkbox_check_icon);
        } else {
            //如果没有全部选中
            isallcheck = false;
            cb_all_check.setBackgroundResource(R.mipmap.checkbox_default_icon);
        }
        if (size > isbuy) {
            //如果有选中的
            tv_gobuy.setClickable(true);//
            tv_gobuy.setTextColor(Color.parseColor("#ffffff"));
            tv_gobuy.setBackgroundResource(R.drawable.bg_radius_apply);
            rl_button_gift.setVisibility(View.VISIBLE);
        } else {
            tv_gobuy.setClickable(false);
            tv_gobuy.setTextColor(Color.parseColor("#FCF9F4"));
            tv_gobuy.setBackgroundResource(R.drawable.bg_radius_apply_no);
            rl_button_gift.setVisibility(View.GONE);
        }
        int checknum = size - isbuy;
        courseSize = fubicoursesize;
        commentSize = voucherListselectecom.size();
        tv_total_money.setText("已选中" + checknum + "个课程，约" + totalCktuanbi + "团币");

        if (voucherListselecte.size() == 0) {
            if (fubicoursesize > 0) {
                voucherListselecte.addAll(voucherListAll);
                availableNum = voucherListselecte.size();
//                tv_available.setText("有" + voucherListselecte.size() + "张可用");
//                tv_available.setTextColor(Color.parseColor("#fc4d30"));
            } else {
                availableNum = 0;
//                tv_available.setText("暂无可用听课券");
//                tv_available.setTextColor(Color.parseColor("#bababa"));
            }
        } else {
            voucherListselecte.addAll(voucherListAll);
            availableNum = voucherListselecte.size();
//            tv_available.setText("有" + voucherListselecte.size() + "张可用");
//            tv_available.setTextColor(Color.parseColor("#fc4d30"));
        }
        mcache.put("voucherListavailable", voucherListselecte);
//        avaliaStr = tv_available.getText().toString().trim();
    }

    private int availableNum = 0;//可用听课券数量

    private void initClick() {
        rl_back.setOnClickListener(this);
        rl_batch.setOnClickListener(this);
        tv_gobuy.setOnClickListener(this);
        rl_all_check.setOnClickListener(this);
//        rl_tingkequan.setOnClickListener(this);
        tv_cancle.setOnClickListener(this);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (courseList.size() >= position) {

                    Intent intent1 = new Intent(SeriesClassActivity.this, CourseIntrActivity.class);
                    intent1.putExtra("id", courseList.get(position - 1).get("ID").toString());
                    startActivity(intent1);
                }
            }
        });

    }

    @Override
    public void onRefresh() {

    }


    @Override
    public void onLoadMore() {

    }

    private View courseFooter;
    private boolean isallcheck = false;

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_batch://批量处理
                int sizess = 0;
                for (HashMap<String, Object> hashMap : courseList) {
                    if (Integer.parseInt(hashMap.get("People_Apply").toString()) > 0) {
                        sizess++;
                    }
                }
                if (courseList.size() == 0) {
                    showOneDialog("没有课程哦^_^");
                } else if (sizess == courseList.size()) {
                    //如果没有可以选择的那么就
                    showOneDialog("所有课程都已购买^_^");
                } else {
                    sl_root.scrollTo(0, DensityUtil.dip2px(this, 150));
                    sl_root.isCanScroll(false);
                    rl_batch.setVisibility(View.GONE);
                    seriesBatchAdapter = new SeriesBatchAdapter(this);
                    seriesBatchAdapter.setData(courseList);
                    mListView.setAdapter(seriesBatchAdapter);
                    courseFooter = LayoutInflater.from(this).inflate(R.layout.course_big_footer, null);
                    mListView.addFooterView(courseFooter);
                    rl_buy.setVisibility(View.VISIBLE);
                    tv_gobuy.setClickable(false);//
                    tv_gobuy.setTextColor(Color.parseColor("#FCF9F4"));
                    tv_gobuy.setBackgroundResource(R.drawable.bg_radius_apply_no);
                    tv_cancle.setVisibility(View.VISIBLE);
                }

                break;
            case R.id.tv_gobuy://去批量购买
                showCKDialog();
                break;
            case R.id.rl_all_check://全选
                isallcheck = !isallcheck;
                teacherid = "";//课堂id clear
                if (isallcheck) {
                    mcache.remove("voucherListselecter");//初始化删除缓存中的选中听课券
                    yHMoney="0";
                    //临时列表，对课程按照团币大小进行排序
                    linshiList = new ArrayList<>();
                    linshiList.clear();
                    //全选
                    cb_all_check.setBackgroundResource(R.mipmap.checkbox_check_icon);
                    for (int i = 0; i < courseList.size(); i++) {
                        if (Integer.parseInt(courseList.get(i).get("People_Apply").toString()) > 0) {
                            //表示已经购买了的
                        } else {
                            courseList.get(i).put("isCheck", "true");
                            linshiList.add(courseList.get(i));
                            teacherid = teacherid + courseList.get(i).get("ID").toString() + ",";
                        }
                    }

                    tv_gobuy.setClickable(true);//
                    tv_gobuy.setTextColor(Color.parseColor("#ffffff"));
                    tv_gobuy.setBackgroundResource(R.drawable.bg_radius_apply);
                    int cktuanbi = 0;
                    int size = 0;
                    int selectSize = 0;
                    for (int i = 0; i < courseList.size(); i++) {
                        if (courseList.get(i).get("isCheck").toString().equals("true")) {
                            cktuanbi += Integer.parseInt(courseList.get(i).get("CKTuanBi").toString());
                            if (Integer.parseInt(courseList.get(i).get("CKTuanBi").toString()) > 0) {
                                size++;//如果有一个选中就+1
                            }
                            selectSize++;
                        }
                    }
                    courseSize = size;//用来控制听课券数量
                    commentSize = voucherListCom.size();
                    totalCktuanbi = cktuanbi;
                    tv_total_money.setText("已选中" + selectSize + "个课程，约" + cktuanbi + "团币");
                    rl_button_gift.setVisibility(View.VISIBLE);
                    ArrayList<HashMap<String, Object>> voucherListavailable = new ArrayList<HashMap<String, Object>>();
                    voucherListavailable.addAll(voucherListCom);
                    voucherListavailable.addAll(voucherListAll);
                    if (cktuanbi > 0) {
                        //如果有需要支付团币的
                        availableNum = voucherListavailable.size();
//                        tv_available.setText("有" + voucherListavailable.size() + "张可用");
//                        tv_available.setTextColor(Color.parseColor("#fc4d30"));
                        mcache.put("voucherListavailable", voucherListavailable);
                    }

                } else {
                    //取消全选
                    cb_all_check.setBackgroundResource(R.mipmap.checkbox_default_icon);
                    for (int i = 0; i < courseList.size(); i++) {
                        courseList.get(i).put("isCheck", "false");
                    }
                    tv_gobuy.setClickable(false);//
                    tv_gobuy.setTextColor(Color.parseColor("#FCF9F4"));
                    tv_gobuy.setBackgroundResource(R.drawable.bg_radius_apply_no);
                    courseSize = 0;
                    tv_total_money.setText("已选中" + 0 + "个课程，约" + 0 + "团币");
                    rl_button_gift.setVisibility(View.GONE);
                    availableNum = 0;
//                    tv_available.setText("暂无可用听课券");
//                    tv_available.setTextColor(Color.parseColor("#bababa"));
                    mcache.remove("voucherListavailable");

                }
                seriesBatchAdapter.setData(courseList);
                seriesBatchAdapter.notifyDataSetChanged();
//                avaliaStr = tv_available.getText().toString().trim();

                break;

            /*case R.id.rl_tingkequan://去听课券页面
                ArrayList<HashMap<String, Object>> voucherListAll = (ArrayList<HashMap<String, Object>>) mcache.getAsObject("voucherListavailable");
//                couponid = "";
                if (voucherListAll != null && voucherListAll.size() > 0) {
                    Intent intent = new Intent(SeriesClassActivity.this, UseVoucherActivity.class);
                    intent.putExtra("sizenum", voucherListAll.size() + "");
                    intent.putExtra("courseSize", courseSize);
                    intent.putExtra("commentSize", commentSize);
                    startActivityForResult(intent, 2345);
                }
                break;*/
            case R.id.tv_cancle://关闭批量听课
                sl_root.scrollTo(0, 0);
                sl_root.isCanScroll(true);
                rl_batch.setVisibility(View.VISIBLE);
                rl_button_gift.setVisibility(View.GONE);
//                seriesBatchAdapter = new SeriesBatchAdapter(this);
//                seriesBatchAdapter.setData(courseList);
                mListView.setAdapter(seriesListAdapter);
//                courseFooter = LayoutInflater.from(this).inflate(R.layout.course_big_footer, null);
                mListView.removeFooterView(courseFooter);
                rl_buy.setVisibility(View.GONE);
                tv_cancle.setVisibility(View.GONE);
                break;
        }

    }

    private TextView cktv_tuanbibuzu;
    private TextView cktv_realnum;
    private TextView cktv_pay;
    private TextView tv_available;

    private void showCKDialog() {

        LinearLayout.LayoutParams pm = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_danbiapply, null);//这里的R.layout.alertdialog即为你自定义的布局文件
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);//头部
        RelativeLayout rl_delete = (RelativeLayout) view.findViewById(R.id.rl_delete);
        rl_delete.setVisibility(View.VISIBLE);
        tv_title.setText("您剩余团币：" + userTuanBi);
        cktv_tuanbibuzu = (TextView) view.findViewById(R.id.tv_tuanbibuzu);//团币不足
        RelativeLayout rl_tingkequan = (RelativeLayout) view.findViewById(R.id.rl_tingkequan);//听课券
        tv_available = (TextView) view.findViewById(R.id.tv_available);//可用听课券
        cktv_realnum = (TextView) view.findViewById(R.id.tv_realnum);//实付
        TextView tv_shouldnum = (TextView) view.findViewById(R.id.tv_shouldnum);//应付bMTuanBi
        int cKTuanBi = totalCktuanbi - Integer.parseInt(yHMoney);
        if (Integer.parseInt(yHMoney) > 0) {
            tv_available.setText("-" + yHMoney + "币");
        } else {
            if (availableNum == 0) {
                tv_available.setText("暂无可用听课券");
            } else {
                tv_available.setText("有" + availableNum + "张可用");
            }
        }
        /*if (availableNum == 0) {
            tv_available.setText("暂无可用听课券");
        } else {
            tv_available.setText("有" + availableNum + "张可用");
        }*/
        tv_shouldnum.setText("应付" + totalCktuanbi + "币");
        if (cKTuanBi < 0) {
            cktv_realnum.setText("实付" + 0 + "币");
        } else {
            cktv_realnum.setText("实付" + cKTuanBi + "币");
        }

        TextView tv_recharge = (TextView) view.findViewById(R.id.tv_recharge);//去充值
        cktv_pay = (TextView) view.findViewById(R.id.tv_pay);//去支付
        if (Integer.parseInt(userTuanBi) < cKTuanBi) {
            cktv_tuanbibuzu.setVisibility(View.VISIBLE);
            cktv_pay.setClickable(false);
            cktv_pay.setEnabled(false);
            cktv_pay.setBackgroundResource(R.drawable.bg_radius_apply_no);
        } else {
            cktv_pay.setBackgroundResource(R.drawable.bg_radius_apply);
            cktv_tuanbibuzu.setVisibility(View.GONE);
            cktv_pay.setClickable(true);
            cktv_pay.setEnabled(true);
        }
        final AlertDialog mAlertDialog = builder.create();
        mAlertDialog.getWindow().setBackgroundDrawableResource(R.color.touming);
        mAlertDialog.show();
        mAlertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        mAlertDialog.getWindow().setContentView(view, pm);
        rl_tingkequan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //去听课券列表
                ArrayList<HashMap<String, Object>> voucherListAll = (ArrayList<HashMap<String, Object>>) mcache.getAsObject("voucherListavailable");
//                couponid = "";
                if (voucherListAll != null && voucherListAll.size() > 0) {
                    mcache.put("selectcoursevou", linshiList);
                    Intent intent = new Intent(SeriesClassActivity.this, UseVoucherActivity.class);
                    intent.putExtra("sizenum", voucherListAll.size() + "");
                    intent.putExtra("courseSize", courseSize);
                    intent.putExtra("commentSize", commentSize);
                    startActivityForResult(intent, 2345);
                }

            }
        });
        tv_recharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //去充值
                Intent intent = new Intent(SeriesClassActivity.this, RechargeMoneyActivity.class);
                startActivity(intent);
                mAlertDialog.dismiss();
            }
        });
        rl_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.dismiss();
            }
        });
        cktv_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //去支付
                mAlertDialog.dismiss();
                if (teacherid.endsWith(",")) {
                    teacherid = teacherid.substring(0, teacherid.length() - 1);
                }
                String[] split = teacherid.split(",");
                String couponid = "";
                ArrayList<HashMap<String, Object>> voucherListselecter = (ArrayList<HashMap<String, Object>>) mcache.getAsObject("voucherListselecter");
                if (voucherListselecter == null) {
                    voucherListselecter = new ArrayList<>();
                }
                Log.e("kajhsdkja", "----------" + linshiList);

                Map<String, Integer> map = new HashMap<String, Integer>();
                for (HashMap<String, Object> hashMap : linshiList) {
                    map.put(hashMap.get("ID").toString(), Integer.parseInt(hashMap.get("CKTuanBi").toString()));
                }
                List<Map.Entry<String, Integer>> infoIds = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
//排序
                Collections.sort(infoIds, new Comparator<Map.Entry<String, Integer>>() {
                    public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                        //return (o2.getValue() - o1.getValue());
                        return (o2.getValue()).compareTo(o1.getValue());
                    }
                });
                String payteacherId = "";
                for (String s : split) {
                    for (HashMap<String, Object> hashMap : voucherListselecter) {
                        if (hashMap.get("TeacherID").toString().equals(s)) {
                            payteacherId = payteacherId + s + ",";
                            couponid = couponid + hashMap.get("ID").toString() + ",";
                            for (int i = 0; i < infoIds.size(); i++) {
                                if (infoIds.get(i).getKey().equals(s)) {
                                    infoIds.remove(i);
                                    break;
                                }
                            }
                            break;
                        }

                    }

                }
                //如果这堂课没有普通券
                for (HashMap<String, Object> hashMap : voucherListselecter) {
                    if (hashMap.get("Type").toString().equals("4")) {
                        //如果是全局券
                        payteacherId = payteacherId + infoIds.get(0).getKey() + ",";
                        couponid = couponid + hashMap.get("ID").toString() + ",";
                        infoIds.remove(0);
//                        break;
                    }

                }

               /* if (payteacherId.contains(",")) {
                    payteacherId = payteacherId.substring(0, payteacherId.length() - 1);
                }*/
                if (payteacherId.equals("")) {
                    payteacherId = teacherid + ",";
                    for (String s : split) {
                        couponid = couponid + "0,";
                    }
                } else if (payteacherId.contains(",")) {
                    payteacherId = payteacherId.substring(0, payteacherId.length() - 1);
                    String[] split1 = payteacherId.split(",");
                    payteacherId = payteacherId + ",";
                    for (int i = 0; i < split.length; i++) {
                        boolean istrue = false;
                        for (String s : split1) {
                            if (s.equals(split[i])) {
                                istrue = true;
                            }
                        }
                        if (!istrue) {
                            payteacherId = payteacherId + split[i] + ",";
                            couponid = couponid + "0,";
                        }

                    }
                }
                if (couponid.endsWith(",")) {
                    couponid = couponid.substring(0, couponid.length() - 1);
                }
                if (payteacherId.endsWith(",")) {
                    payteacherId = payteacherId.substring(0, payteacherId.length() - 1);
                }
                String sign = TGmd5.getMD5(distributorid + payteacherId + couponid);
                batchbBuyPresenter.batchbBuy(distributorid, payteacherId, couponid, sign);

            }
        });
    }

    public void showOneDialog(String str) {
        MyToast.makeText(this, str, Toast.LENGTH_SHORT).show();
        /*LinearLayout.LayoutParams pm = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_oneclick, null);
        TextView yes = (TextView) view.findViewById(R.id.yes);
        TextView text1 = (TextView) view.findViewById(R.id.text1);
        yes.setText("知道了");
        text1.setText(str);
        final AlertDialog mAlertDialog = builder.create();
        mAlertDialog.getWindow().setBackgroundDrawableResource(R.color.touming);
        mAlertDialog.show();
        mAlertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        mAlertDialog.getWindow().setContentView(view, pm);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.dismiss();
            }
        });*/
    }


    private String teacherid = "";//在购买时使用

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 2345) {
            if (data != null) {
                yHMoney = data.getStringExtra("money");
                if (yHMoney == null || yHMoney.equals("")) {
                    yHMoney = "0";
                }
                int cKTuanBi = totalCktuanbi - Integer.parseInt(yHMoney);
                if (cKTuanBi < 0) {
                    cktv_realnum.setText("实付" + 0 + "币");
                } else {
                    cktv_realnum.setText("实付" + cKTuanBi + "币");
                }
                if (Integer.parseInt(userTuanBi) < cKTuanBi) {
                    cktv_tuanbibuzu.setVisibility(View.VISIBLE);
                    cktv_pay.setClickable(false);
                    cktv_pay.setEnabled(false);
                    cktv_pay.setBackgroundResource(R.drawable.bg_radius_apply_no);
                } else {
                    cktv_pay.setBackgroundResource(R.drawable.bg_radius_apply);
                    cktv_tuanbibuzu.setVisibility(View.GONE);
                    cktv_pay.setClickable(true);
                    cktv_pay.setEnabled(true);
                }
                if (Integer.parseInt(yHMoney) > 0) {
                    tv_available.setText("-" + yHMoney + "币");
                } else {
                    if (availableNum == 0) {
                        tv_available.setText("暂无可用听课券");
                    } else {
                        tv_available.setText("有" + availableNum + "张可用");
                    }
                }

            }
        }
        if (resultCode == RESULT_OK && requestCode == 5678) {
            mcache.remove("voucherListavailable");//初始化删除缓存中的听课券
            mcache.remove("voucherListselecter");//初始化删除缓存中的选中听课券
            userTuanBi = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.TUANBI);
            initView();
            sl_root.scrollTo(0, 0);
            sl_root.isCanScroll(true);
            rl_batch.setVisibility(View.VISIBLE);
            tv_cancle.setVisibility(View.GONE);
            initDatas();
        }
    }

    private void initDatas() {
        distributorid = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        String sign = TGmd5.getMD5(distributorid + linkUrl);
        showLoadingProgressDialog(this, "");
        batchBuyStudyPresenter.batchBuyStudy(distributorid, linkUrl, sign);
    }

    private ArrayList<HashMap<String, Object>> courseList = new ArrayList<>();

    private ArrayList<HashMap<String, Object>> voucherListAll = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> voucherListCom = new ArrayList<>();

    @Override
    public void OnBatchBuyStudySuccCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
        mListView.stopRefresh();
        try {
            JSONObject jso = new JSONObject(respanse);
            JSONArray jsa = new JSONArray(jso.getString("result"));
            JSONObject jsonObject = jsa.getJSONObject(3);//系列详情数据
            tv_spit.setText(jsonObject.get("SeriesName").toString());
//            tv_banner.setText(jsonObject.get("SeriesName").toString());
            Glide.with(SeriesClassActivity.this).load(Url.ROOT + jsonObject.get("PicUrl").toString()).into(iv_title);
            courseList.clear();
            JSONArray jsonArray = jsa.getJSONArray(0);//课程列表
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsoo = jsonArray.getJSONObject(i);
                HashMap<String, Object> map1 = new HashMap<String, Object>();
                Iterator it = jsoo.keys();
                while (it.hasNext()) {
                    String key = (String) it.next();
                    String value = jsoo.getString(key);
                    map1.put(key, value);
                }
                map1.put("isCheck", "false");
                courseList.add(map1);
            }
            tv_total_course.setText("共" + courseList.size() + "个课程");
            seriesListAdapter.setData(courseList);
            seriesListAdapter.notifyDataSetChanged();
            voucherListAll.clear();
            int sizess = 0;
            for (HashMap<String, Object> hashMap : courseList) {
                if (Integer.parseInt(hashMap.get("People_Apply").toString()) > 0) {
                    sizess++;
                }
            }
            if (sizess == courseList.size()) {
                //如果没有可以选择的那么就
                im_download.setBackgroundResource(R.mipmap.batch_download_normal);
                tv_piliangxiazai.setTextColor(Color.parseColor("#cccccc"));
            } else {
                im_download.setBackgroundResource(R.mipmap.batch_download_icon);
                tv_piliangxiazai.setTextColor(Color.parseColor("#d5aa5f"));
            }
            JSONArray jsonAll = jsa.getJSONArray(1);//全局听课券列表
            for (int i = 0; i < jsonAll.length(); i++) {
                JSONObject jsoo = jsonAll.getJSONObject(i);
                HashMap<String, Object> map1 = new HashMap<String, Object>();
                Iterator it = jsoo.keys();
                while (it.hasNext()) {
                    String key = (String) it.next();
                    String value = jsoo.getString(key);
                    map1.put(key, value);
                }
                map1.put("isCheck", "false");
                voucherListAll.add(map1);
            }
            voucherListCom.clear();
            JSONArray jsoncom = jsa.getJSONArray(2);//普通听课券列表
            for (int i = 0; i < jsoncom.length(); i++) {
                JSONObject jsoo = jsoncom.getJSONObject(i);
                HashMap<String, Object> map1 = new HashMap<String, Object>();
                Iterator it = jsoo.keys();
                while (it.hasNext()) {
                    String key = (String) it.next();
                    String value = jsoo.getString(key);
                    map1.put(key, value);
                }
                map1.put("isCheck", "true");
                voucherListCom.add(map1);
            }
//            mcache.put("voucherListAll", voucherListAll);
//            mcache.put("voucherListCom", voucherListCom);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void OnBatchBuyStudyFialCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
        mListView.stopRefresh();
    }

    @Override
    public void closeBatchBuyStudyProgress() {

    }

    @Override
    public void OnBatchbBuySuccCallBack(String state, String respanse) {
        //购买成功
        closeLoadingProgressDialog();
        Log.e("laksjkssd", "-----------" + respanse);
        rl_buy.setVisibility(View.GONE);
        rl_button_gift.setVisibility(View.GONE);
        Intent intent = new Intent(SeriesClassActivity.this, PaySuccessActivity.class);
        startActivityForResult(intent, 5678);
//        startActivity(intent);
    }

    @Override
    public void OnBatchbBuyFialCallBack(String state, String respanse) {
        //购买失败
        closeLoadingProgressDialog();
        Log.e("laksjkssd", "***********" + respanse);
        MyToast.makeText(this, "" + respanse, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void closeBatchbBuyProgress() {

    }
}
