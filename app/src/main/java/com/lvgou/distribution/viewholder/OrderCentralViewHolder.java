package com.lvgou.distribution.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.util.LogUtils;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.entity.OrderCentralChildEntity;
import com.lvgou.distribution.entity.OrderCentralEntity;
import com.lvgou.distribution.inter.OnListItemClickListener;
import com.lvgou.distribution.view.MyListView;
import com.xdroid.common.utils.PreferenceHelper;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.functions.holder.ListViewDataAdapter;
import com.xdroid.functions.holder.ViewHolderBase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;


/**
 * Created by Snow on 2016/3/15 0015.
 */
public class OrderCentralViewHolder extends ViewHolderBase<OrderCentralEntity> {

    private Context context;
    private TextView tv_time;
    private TextView tv_name;
    private TextView tv_order_num;
    private TextView tv_status;
    private TextView tv_money;
    private TextView tv_num_goods;
    private TextView tv_youjin;
    private TextView tv_order_time_one;
    private MyListView lv_list;
    private LinearLayout ll_item;
    private String user_type;

    private ListViewDataAdapter<OrderCentralChildEntity> childEntityListViewDataAdapter;

    private static OnListItemClickListener<OrderCentralEntity> onListItemClickListener;

    @Override
    public View createView(LayoutInflater layoutInflater) {
        context = layoutInflater.getContext();
        user_type = PreferenceHelper.readString(context, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.USER_TYPE);
        View view = layoutInflater.inflate(R.layout.item_order_centra, null);
        tv_time = (TextView) view.findViewById(R.id.tv_time);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_order_num = (TextView) view.findViewById(R.id.tv_order_num);
        tv_status = (TextView) view.findViewById(R.id.tv_status);
        tv_money = (TextView) view.findViewById(R.id.tv_money);
        tv_order_time_one = (TextView) view.findViewById(R.id.tv_order_time_one);
        tv_num_goods = (TextView) view.findViewById(R.id.tv_num_goods);
        lv_list = (MyListView) view.findViewById(R.id.lv_list);
        tv_youjin = (TextView) view.findViewById(R.id.tv_yougjin);
        ll_item = (LinearLayout) view.findViewById(R.id.ll_item);
        return view;
    }

    @Override
    public void showData(int position, final OrderCentralEntity itemData) {
        DecimalFormat decimalFormat = new DecimalFormat("##0.00");
        if (itemData.getOrder_time() != null && itemData.getOrder_time().length() > 0) {
            String[] str = itemData.getOrder_time().split("T");
            tv_time.setText(str[0]);
        }

        if (!user_type.equals("2")) {
            if (!itemData.getOrder_name().equals("") || !itemData.getOrder_name().equals("null")) {
                tv_name.setText("(" + itemData.getOrder_name() + ")");
            } else {
                tv_name.setText("");
            }
        } else {
            tv_name.setVisibility(View.INVISIBLE);
        }

        tv_order_num.setText(itemData.getOrder_number());
        if (itemData.getOrder_status().equals("1")) {
            tv_status.setText("待付款");
            tv_status.setTextColor(context.getResources().getColor(R.color.bg_button));
        } else if (itemData.getOrder_status().equals("2")) {
            tv_status.setText("待发货");
            tv_status.setTextColor(context.getResources().getColor(R.color.bg_button));
        } else if (itemData.getOrder_status().equals("3")) {
            tv_status.setText("待收货");
            tv_status.setTextColor(context.getResources().getColor(R.color.bg_button));
        } else if (itemData.getOrder_status().equals("4")) {
            tv_status.setText("交易成功");
            tv_status.setTextColor(context.getResources().getColor(R.color.bg_main_exchange));
        } else if (itemData.getOrder_status().equals("5")) {
            tv_status.setText("交易关闭");
            tv_status.setTextColor(context.getResources().getColor(R.color.bg_button));
        }

        tv_money.setText("¥" + decimalFormat.format(Float.parseFloat(itemData.getOrder_pay())));
        tv_youjin.setText("¥" + decimalFormat.format(Float.parseFloat(itemData.getTotal_youjin())));
        tv_num_goods.setText("共" + itemData.getQuntity() + "件商品");

        try {
            JSONArray jsonArray = new JSONArray(itemData.getOrder_list());
            childEntityListViewDataAdapter = new ListViewDataAdapter<OrderCentralChildEntity>();
            if (jsonArray != null && jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject json_child = jsonArray.getJSONObject(i);
                    String id = json_child.getString("ID");
                    String picUrl = json_child.getString("PicUrl");
                    String productName = json_child.getString("ProductName");
                    String sKUName = json_child.getString("SKUName");
                    String price_Real = json_child.getString("Price_Real");
                    String amount = json_child.getString("Amount");
                    OrderCentralChildEntity orderCentralChildEntity = new OrderCentralChildEntity(id, picUrl, productName, sKUName, price_Real, amount);
                    childEntityListViewDataAdapter.append(orderCentralChildEntity);
                }
                childEntityListViewDataAdapter.setViewHolderClass(context, OrderCentralChildViewHolder.class);
                lv_list.setAdapter(childEntityListViewDataAdapter);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onListItemClickListener != null) {
                    onListItemClickListener.onListItemClick(itemData);
                }
            }
        });
        lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (onListItemClickListener != null) {
                    onListItemClickListener.onListItemClick(itemData);
                }
            }
        });
    }

    public static void setOnListItemClickListener(OnListItemClickListener<OrderCentralEntity> onListItemClickListener) {
        OrderCentralViewHolder.onListItemClickListener = onListItemClickListener;
    }
}
