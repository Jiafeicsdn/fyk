package com.lvgou.distribution.viewholder;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.lidroid.xutils.util.LogUtils;
import com.lvgou.distribution.R;
import com.lvgou.distribution.entity.PhoneEntity;
import com.lvgou.distribution.inter.OnPhoneClickListener;
import com.xdroid.common.utils.StringUtils;
import com.xdroid.functions.holder.ViewHolderBase;



/**
 * Created by Snow on 2016/4/19 0019.
 */
public class PhoneViewHolder extends ViewHolderBase<PhoneEntity> {

    private EditText et_phone;
    private static OnPhoneClickListener<PhoneEntity> phoneEntityOnPhoneClickListener;

    @Override
    public View createView(LayoutInflater layoutInflater) {
        View view = layoutInflater.inflate(R.layout.item_phone_gridview, null);
        et_phone = (EditText) view.findViewById(R.id.et_phone);
        return view;
    }

    @Override
    public void showData(int position, final PhoneEntity itemData) {
        if (!itemData.getPhone().equals("")) {
            et_phone.setText(itemData.getPhone());
            et_phone.setBackgroundResource(R.drawable.shape_phone_gray);
        } else {
            et_phone.setBackgroundResource(R.drawable.shape_phone_white);
//            et_phone.setHint("请输入手机号");
        }

        et_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//              if (et_phone.getText().length() == 11 && StringUtils.isPhone(et_phone.getText())) {
                if (phoneEntityOnPhoneClickListener != null) {
                    String phone = et_phone.getText().toString();
                    phoneEntityOnPhoneClickListener.onPhoneClickListener(phone, itemData);
                }
//             }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public static void setPhoneEntityOnPhoneClickListener(OnPhoneClickListener<PhoneEntity> phoneEntityOnPhoneClickListener) {
        PhoneViewHolder.phoneEntityOnPhoneClickListener = phoneEntityOnPhoneClickListener;
    }
}

