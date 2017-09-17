package com.lvgou.distribution.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.bean.MenuBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2015/11/23.
 */
public class MerchantMenuAdapter extends BaseAdapter {
    private Context mContext;
    private int itemWidth = 0;
    private int curOrderItem = 0;
    private List<String> tab_names;
    private Adaptercallback adaptercallback;

    public MerchantMenuAdapter(Context context) {
        this.mContext = context;
        tab_names = new ArrayList<>();
    }

    public void setMenuList(List<String> list) {
        tab_names = list;
    }

    public void setAdaptercallback(Adaptercallback adaptercallback) {
        this.adaptercallback = adaptercallback;
    }

    @Override
    public int getCount() {
        return tab_names.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        convertView = View.inflate(mContext, R.layout.merchant_menuadapter_view, null);
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        itemWidth = width / tab_names.size();

//        if (itemWidth > 0) {
//            convertView.findViewById(R.id.order_listview_container)
//                    .getLayoutParams().width = itemWidth;
//        }
        TextView txtName = (TextView) convertView
                .findViewById(R.id.order_listview_txtName);
        RelativeLayout layout_click = (RelativeLayout) convertView.findViewById(R.id.layout_click);
        layout_click.getLayoutParams().width=itemWidth;
        View line = convertView.findViewById(R.id.line);
        txtName.setText(tab_names.get(position));
        if (position == curOrderItem) {
            txtName.setTextColor(mContext.getResources().getColor(R.color.bg_daoliu_yellow_one));
            line.setVisibility(View.VISIBLE);
        } else {
            txtName.setTextColor(mContext.getResources().getColor(R.color.bg_gray_two));
            line.setVisibility(View.INVISIBLE);
        }
        layout_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurOrderItem(position);
                adaptercallback.Itemclick(position);
            }
        });
        return convertView;

    }

    public void setCurOrderItem(int position) {
        this.curOrderItem = position;
        notifyDataSetChanged();
    }

    public interface Adaptercallback {
        public void Itemclick(int position);
    }
}
