package com.lvgou.distribution.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.TaskDetialActivity;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.event.EventFactory;
import com.lvgou.distribution.utils.TGmd5;
import com.umeng.analytics.MobclickAgent;
import com.xdroid.common.utils.PreferenceHelper;


/**
 * Created by Snow on 2016/3/29 0029.
 * 规则
 */
public class FragmentRules extends Fragment implements View.OnClickListener {

    private TextView tv_exchange;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rules, container, false);
        tv_exchange = (TextView) view.findViewById(R.id.tv_exchange);
        tv_exchange.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_exchange:
                EventFactory.sendTransitionHomeTab(2);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
    }
}
