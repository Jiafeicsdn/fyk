package com.lvgou.distribution.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.bean.MenuBean;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2015/11/23.
 */
public class MenuAdapter extends BaseAdapter {
    private Context mContext;
    private int itemWidth = 0;
    private int curOrderItem = 0;
    private List<MenuBean> tab_names;
    private Adaptercallback adaptercallback;

    public MenuAdapter(Context context) {
        this.mContext = context;
        tab_names = new ArrayList<>();
    }

    public void setMenuList(List<MenuBean> list) {
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
        convertView = View.inflate(mContext, R.layout.item_menu, null);
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        itemWidth = width / tab_names.size();
        if (itemWidth > 0) {
            convertView.findViewById(R.id.order_listview_container)
                    .getLayoutParams().width = itemWidth;
        }
        TextView txtName = (TextView) convertView
                .findViewById(R.id.order_listview_txtName);
        TextView txt_nummer = (TextView) convertView.findViewById(R.id.txt_nummer);
        LinearLayout layout_click = (LinearLayout) convertView.findViewById(R.id.layout_click);
        ImageView imgSplit = (ImageView) convertView
                .findViewById(R.id.order_listview_imgSplit);
        ImageView imgIcon = (ImageView) convertView
                .findViewById(R.id.order_listview_icon);
        View line = convertView.findViewById(R.id.line);
        txtName.setText(tab_names.get(position).getTitle());
        txt_nummer.setText(String.valueOf(tab_names.get(position).getNumer()));
        if (position == curOrderItem) {
            txt_nummer.setTextColor(mContext.getResources().getColor(R.color.bg_daoliu_yellow_one));
            txtName.setTextColor(mContext.getResources().getColor(R.color.bg_daoliu_yellow_one));
            line.setVisibility(View.VISIBLE);
        } else {
            txt_nummer.setTextColor(mContext.getResources().getColor(R.color.bg_balck_three));
            txtName.setTextColor(mContext.getResources().getColor(R.color.bg_gray_two));
            line.setVisibility(View.INVISIBLE);
        }
        if (position < tab_names.size() - 1) {
            imgSplit.setVisibility(View.VISIBLE);
        } else {
            imgSplit.setVisibility(View.GONE);
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
