package com.lvgou.distribution.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lvgou.distribution.R;

/**
 * Created by Administrator on 2017/4/1.
 */

public class TeacherDataFragment extends BaseFragment {
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_teacher_intro, container, false);
        initView();
        initClick();
        return view;
    }


    private ScrollView sl_all;
    private TextView tv_teachet_instr;
    private TextView tv_instr_title;

    private void initView() {
        tv_instr_title = (TextView) view.findViewById(R.id.tv_instr_title);
        sl_all = (ScrollView) view.findViewById(R.id.sl_all);
        tv_teachet_instr = (TextView) view.findViewById(R.id.tv_teachet_instr);
        if (!instr.equals("")) {
            tv_teachet_instr.setText(instr);
            tv_instr_title.setVisibility(View.VISIBLE);
        } else {
            tv_instr_title.setVisibility(View.GONE);
        }

    }

    private String instr = "";

    public void setDatas(String instr) {
        this.instr = instr;
        if (null != tv_teachet_instr) {
            tv_teachet_instr.setText(instr);
        }
    }

    private void initClick() {

    }


    @Override
    public void pullToRefresh() {

    }

    @Override
    public void refreshComplete() {

    }

    @Override
    public View getScrollableView() {
        return sl_all;
    }
}
