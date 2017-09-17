package com.lvgou.distribution.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.NewFreeSmsActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lmn on 2016/5/27.
 */
public class GridViewAdapter extends BaseAdapter {
    private Context context;
    private List<Map<String, String>> list;
    LayoutInflater layoutInflater;
    private EditText mPhoneNum;
    private LinearLayout ll_layout;
    private Handler handler;

    public GridViewAdapter(Context context, List<Map<String, String>> list, Handler handler) {
        this.context = context;
        this.list = list;
        this.handler = handler;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();//注意此处
    }

    public List<Map<String, String>> getData() {
        return list;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void cleanAll() {
        list.clear();
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = layoutInflater.inflate(R.layout.grid_item, null);
            mPhoneNum = (EditText) convertView.findViewById(R.id.item);
            ll_layout = (LinearLayout) convertView.findViewById(R.id.ll_layout);

        mPhoneNum.setText(list.get(position).get("num"));
        if (list.get(position).get("color").equals("black")) {
            mPhoneNum.setTextColor(Color.BLACK);
        } else {
            mPhoneNum.setTextColor(Color.RED);
        }
        ll_layout.setBackgroundResource(0);
        if (list.size()-1 == NewFreeSmsActivity.FocusNum)
          mPhoneNum.requestFocus();
        mPhoneNum.addTextChangedListener(new TextWatcher() {
            private CharSequence temp;
            private int maxLen = 11;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                temp = charSequence;
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void afterTextChanged(Editable editable) {
                NewFreeSmsActivity.FocusNum = position;
                if (temp.length() == maxLen) {
                    if (isMobileNO(temp.toString())) {
                        list.get(position).put("color", "black");
                        list.get(position).put("num", temp.toString());
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("num", "");
                        map.put("color", "red");
                        list.add(map);
                        NewFreeSmsActivity.FocusNum = list.size() - 1;
                        handler.sendEmptyMessage(0);
                    } else System.out.println("wrong number");
                } else {
                    list.get(position).put("num", temp.toString());
                    list.get(position).put("color", "red");
//                    handler.sendEmptyMessage(0);
                }
            }
        });
        return convertView;
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
        /*
        移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
        联通：130、131、132、152、155、156、185、186
        电信：133、153、180、189、（1349卫通）
        总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
        */
        String telRegex = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(telRegex);
    }

    class ViewHodler {
        EditText mPhoneNum;
        LinearLayout ll_layout;
    }
}