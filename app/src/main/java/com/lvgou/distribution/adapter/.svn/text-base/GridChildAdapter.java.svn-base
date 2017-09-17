package com.lvgou.distribution.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.lvgou.distribution.utils.MyToast;import android.widget.Toast;

import com.lvgou.distribution.R;
import com.lvgou.distribution.bean.ChildItem;
import com.lvgou.distribution.utils.ViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2016/10/19.
 */
public class GridChildAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<ChildItem> list;
    private ArrayList<String> isVISIBLE;
    private int groupPosition;
    private Map<Integer, Integer> aaa;

    public GridChildAdapter(Context context) {
        this.context = context;
    }

    public void setIsVISIBLE(ArrayList<String> isVISIBLE) {
        this.isVISIBLE = isVISIBLE;
        notifyDataSetChanged();
    }

    public void setData(List<ChildItem> list, Map<Integer, Integer> aaa, int groupposition) {
        if (list != null && !list.isEmpty()) {
            this.list = list;
            this.aaa = aaa;
            this.groupPosition = groupposition;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        ChildItem childItem = list.get(position);
        viewHolder = ViewHolder.getVH(convertView, context, R.layout.info_mark_list_item);
        TextView content = viewHolder.getView(R.id.content, TextView.class);
        RelativeLayout ll_all = viewHolder.getView(R.id.ll_all, RelativeLayout.class);
        content.setText(childItem.getTitle());
        if (isVISIBLE != null) {
            if (isVISIBLE.contains(list.get(position).getMarkerImgId() + "")) {
                content.setTextColor(Color.rgb(20,164,174));
                content.setBackgroundResource(R.drawable.border_green_radius);
            } else {
                content.setTextColor(Color.rgb(208,208,208));
                content.setBackgroundResource(R.drawable.border_gray);
            }
        }
        ll_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int markerImgId = list.get(position).getMarkerImgId();
                if (isVISIBLE.contains(markerImgId + "")) {

                    isVISIBLE.remove(markerImgId + "");
                    aaa.put(groupPosition, aaa.get(groupPosition) - 1);
                    if (isVISIBLE != null) {
                        GridChildAdapter.this.notifyDataSetChanged();
//                        myAdapter.setIsVISIBLE(ActorTagActivity.this.chkList);
                    }

                } else {
                    if (aaa.get(groupPosition) < 2) {
                        isVISIBLE.add(markerImgId + "");
                        aaa.put(groupPosition, aaa.get(groupPosition) + 1);
                        if (isVISIBLE != null) {
                            GridChildAdapter.this.notifyDataSetChanged();
//                            myAdapter.setIsVISIBLE(ActorTagActivity.this.chkList);
                        }
                    }else {
                        MyToast.makeText(context, "每一组最多只能选两个！", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return viewHolder.convertView;
    }
}
