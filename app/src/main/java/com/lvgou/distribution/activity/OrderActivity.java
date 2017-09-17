package com.lvgou.distribution.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.adapter.OrderAdapter;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.dslv.DragSortListView;
import com.lvgou.distribution.entity.OrderEntity;
import com.lvgou.distribution.event.EventFactory;
import com.lvgou.distribution.inter.OnPushSpeedClickListener;
import com.lvgou.distribution.request.RequestTask;
import com.lvgou.distribution.utils.TGmd5;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.common.utils.PreferenceHelper;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.request.extension.XDroidRequest;
import com.xdroid.request.extension.config.DataType;
import com.xdroid.request.extension.impl.OnRequestListenerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Snow on 2016/3/28 0028.
 */
public class OrderActivity extends BaseActivity {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.drag_listView)
    private DragSortListView dragSortListView;
    @ViewInject(R.id.rl_publish)
    private RelativeLayout rl_publish;
    @ViewInject(R.id.tv_right)
    private TextView tv_right;
    @ViewInject(R.id.img_guide_one)
    private ImageView img_guide_one;
    @ViewInject(R.id.img_guide_two)
    private ImageView img_guide_two;

    private Dialog dialog;

    private String distributorid = "";

    private OrderAdapter orderAdapter;

    private List<OrderEntity> orderEntityList;

    private List<OrderEntity> orderExchangeEntityList;

    private List<OrderEntity> orderNotExchangeEntityList;

    private String ids = "";
    private String index = "";

    private String order_state = "false";//是否拖动排序
    private String guide_is_show = "";//是否显示引导

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ViewUtils.inject(this);
        tv_title.setText("货架管理");
        rl_publish.setVisibility(View.VISIBLE);

        distributorid = PreferenceHelper.readString(OrderActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        guide_is_show = PreferenceHelper.readString(OrderActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.IS_SHOW_ORDER, "false");
        if (guide_is_show.equals("false")){
            img_guide_one.setVisibility(View.VISIBLE);
        }
        if (checkNet()) {
            String sign = TGmd5.getMD5(distributorid);
            getData(distributorid, sign);
        }
        dragSortListView.setDropListener(onDrop);
        dragSortListView.setRemoveListener(onRemove);


    }

    @OnClick({R.id.rl_back, R.id.rl_publish, R.id.img_guide_one, R.id.img_guide_two})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_publish:
                if (order_state.equals("true")) {
                    String ids_ = ids.substring(0, ids.length() - 1);
                    String index_ = index.substring(0, index.length() - 1);
                    String sign_ = TGmd5.getMD5(distributorid + ids_ + index_);
                    saveData(distributorid, ids_, index_, sign_);
                } else if (order_state.equals("false")) {
                    orderState();
                    if (ids.length() != 0 && index.length() != 0) {
                        String ids_ = ids.substring(0, ids.length() - 1);
                        String index_ = index.substring(0, index.length() - 1);
                        String sign_ = TGmd5.getMD5(distributorid + ids_ + index_);
                        saveData(distributorid, ids_, index_, sign_);
                    } else {
                        MyToast.show(OrderActivity.this, "请添加商品");
                        EventFactory.saveOrder(0);
                    }
                }
                break;
            case R.id.img_guide_one:
                PreferenceHelper.write(OrderActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.IS_SHOW_ORDER, "ture");
                img_guide_one.setVisibility(View.GONE);
                img_guide_two.setVisibility(View.VISIBLE);
                break;
            case R.id.img_guide_two:
                img_guide_two.setVisibility(View.GONE);
                break;
        }
    }

    //监听器在手机拖动停下的时候触发
    private DragSortListView.DropListener onDrop =
            new DragSortListView.DropListener() {
                @Override
                public void drop(int from, int to) {//from to 分别表示 被拖动控件原位置 和目标位置
                    order_state = "true";
                    if (from != to) {
                        ids = "";
                        index = "";
                        OrderEntity item = (OrderEntity) orderAdapter.getItem(from);//得到listview的适配器
                        orderAdapter.remove(from);//在适配器中”原位置“的数据。
                        orderAdapter.insert(item, to);//在目标位置中插入被拖动的控件。
                        orderExchangeEntityList = orderAdapter.getAllData();
                        for (int i = 0; i < orderExchangeEntityList.size(); i++) {
                            OrderEntity orderEntity = orderExchangeEntityList.get(i);
                            ids += orderEntity.getId() + ",";
                            index += i + ",";
                        }
                    }
                }
            };

    //删除监听器，点击左边差号就触发。删除item操作。
    private DragSortListView.RemoveListener onRemove =
            new DragSortListView.RemoveListener() {
                //                @Override
                public void remove(final int which) {

                }
            };

    /**
     * 为排序的状态
     */
    public void orderState() {
        ids = "";
        index = "";
        orderNotExchangeEntityList = orderAdapter.getAllData();
        for (int i = 0; i < orderNotExchangeEntityList.size(); i++) {
            OrderEntity orderEntity = orderNotExchangeEntityList.get(i);
            ids += orderEntity.getId() + ",";
            index += i + ",";
        }
    }

    /**
     * 获取数据
     *
     * @param distributorid
     * @param sign
     */
    public void getData(String distributorid, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("distributorid", distributorid);
        maps.put("sign", sign);
        RequestTask.getInstance().getSortList(OrderActivity.this, maps, new OnRequestListener());
    }


    private class OnRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if (status.equals("1")) {
                    String result = jsonObject.getString("result");
                    JSONArray array = new JSONArray(result);
                    String data = array.get(0).toString();
                    JSONArray array_data = new JSONArray(data);
                    orderEntityList = new ArrayList<OrderEntity>();
                    if (array_data != null && array_data.length() > 0) {
                        for (int i = 0; i < array_data.length(); i++) {
                            JSONObject json_ = array_data.getJSONObject(i);
                            String id = json_.getString("ID");
                            String name = json_.getString("ShopName");
                            OrderEntity orderEntity = new OrderEntity(id, name);
                            orderEntityList.add(orderEntity);
                        }
                    }
                    orderAdapter = new OrderAdapter(orderEntityList, OrderActivity.this);
                    dragSortListView.setAdapter(orderAdapter);
                    dragSortListView.setDragEnabled(true);
                    OrderAdapter.setOnListItemClickListener(new OnPushSpeedClickListener<OrderEntity>() {
                        @Override
                        public void onPushSpeedListener(OrderEntity itemData, final int postion) {
                            dialog = new Dialog(OrderActivity.this,
                                    R.style.Mydialog);
                            View view1 = View.inflate(OrderActivity.this,
                                    R.layout.delete_shop_dialog, null);
                            Button sure = (Button) view1.findViewById(R.id.sure);
                            Button cancle = (Button) view1.findViewById(R.id.cancle);
                            sure.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String id = orderEntityList.get(postion).getId();
                                    String sign = TGmd5.getMD5(distributorid + id);
                                    doRemove(distributorid, id, sign);
                                    orderAdapter.remove(postion);
                                    dialog.dismiss();
                                }
                            });
                            cancle.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
                            dialog.setContentView(view1);
                            dialog.show();
                        }
                    });
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 保存 排序
     *
     * @param distributorid
     * @param ids
     * @param OrderIndexs
     * @param sign
     */
    public void saveData(String distributorid, String ids, String OrderIndexs, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("distributorid", distributorid);
        maps.put("ids", ids);
        maps.put("OrderIndexs", OrderIndexs);
        maps.put("sign", sign);
        RequestTask.getInstance().doSaveSort(OrderActivity.this, maps, new OnSaveRequestListener());

    }

    private class OnSaveRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObject_ = new JSONObject(response);
                String status = jsonObject_.getString("status");
                if (status.equals("1")) {
                    MyToast.show(OrderActivity.this, "排序成功");
                    EventFactory.saveOrder(0);
                    finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
    }

    /**
     * 移出商品操作
     *
     * @param distributorid
     * @param id
     * @param sign
     */
    public void doRemove(String distributorid, String id, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("distributorid", distributorid);
        maps.put("id", id);
        maps.put("sign", sign);

        RequestTask.getInstance().doRemove(OrderActivity.this, maps, new OnRemoveRequestListener());
    }

    private class OnRemoveRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if (status.equals("1")) {
                    MyToast.show(OrderActivity.this, "移出成功");
//                  EventFactory.saveOrder(0);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
