package com.lvgou.distribution.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.bean.MenuBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/6.
 */
public class DetailsMenuAdapter extends BaseAdapter {
    private Context mContext;
    private int itemWidth = 0;
    private int curOrderItem = 0;
    private List<MenuBean> tab_names;
    private Adaptercallback adaptercallback;

    public DetailsMenuAdapter(Context context) {
        this.mContext = context;
        tab_names = new ArrayList<>();
    }

    public void setMenuList(List<MenuBean> list) {
        tab_names = list;
    }
    public List<MenuBean> getMenuList() {
        return  tab_names;
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
        convertView = View.inflate(mContext, R.layout.details_item_menu, null);
        LinearLayout order_listview_container=(LinearLayout)convertView.findViewById(R.id.order_listview_container);
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
        if(tab_names.get(position).getNumer()>0){
            txt_nummer.setVisibility(View.VISIBLE);
        }else{
            txt_nummer.setVisibility(View.GONE);
        }
        txt_nummer.setText(String.valueOf(tab_names.get(position).getNumer()));
        if (position == curOrderItem) {
            txt_nummer.setTextColor(mContext.getResources().getColor(R.color.text_color_333333));
            txtName.setTextColor(mContext.getResources().getColor(R.color.text_color_333333));
            line.setVisibility(View.VISIBLE);
        } else {
            txt_nummer.setTextColor(mContext.getResources().getColor(R.color.text_color_777777));
            txtName.setTextColor(mContext.getResources().getColor(R.color.text_color_777777));
            line.setVisibility(View.INVISIBLE);
        }
        if (position < tab_names.size() - 1) {
            imgSplit.setVisibility(View.VISIBLE);
        } else {
            imgSplit.setVisibility(View.GONE);
        }
        order_listview_container.setOnClickListener(new View.OnClickListener() {
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
