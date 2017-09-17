package com.lvgou.distribution.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.CollegeManagerActivity;
import com.lvgou.distribution.activity.CourseIntrActivity;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.constants.Url;
import com.xdroid.common.utils.PreferenceHelper;
import com.xdroid.common.utils.ScreenUtils;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/3/10.
 */

public class ShowCardFragment extends Fragment {

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_show_industry_list, container, false);
        initView();
        return view;
    }

    private HashMap<String, Object> stringObjectHashMap;

    public void setDatas(HashMap<String, Object> stringObjectHashMap) {
        this.stringObjectHashMap = stringObjectHashMap;

    }

    private ImageView im_picture;
    private TextView tv_content;
    private TextView live_state;
    private TextView tv_renshu;
    private RelativeLayout rl_fragment;

    private void initView() {
        im_picture = (ImageView) view.findViewById(R.id.im_picture);
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        ViewGroup.LayoutParams paralive = im_picture.getLayoutParams();
        int iheightlive = (int) ((width - ScreenUtils.dpToPx(getActivity(), 40)) * 34 / 67);
        paralive.height = iheightlive;// 控件的高强制设成
        im_picture.setLayoutParams(paralive);
        tv_content = (TextView) view.findViewById(R.id.tv_content);
        live_state = (TextView) view.findViewById(R.id.live_state);
        tv_renshu = (TextView) view.findViewById(R.id.tv_renshu);
        rl_fragment = (RelativeLayout) view.findViewById(R.id.rl_fragment);
        Glide.with(getActivity()).load(Url.ROOT + stringObjectHashMap.get("PicUrl").toString()).error(R.mipmap.pictures_no).into(im_picture);
        tv_content.setText(stringObjectHashMap.get("Title").toString());
        tv_renshu.setText(stringObjectHashMap.get("Price") + "人已报名");
        if (stringObjectHashMap.get("State").toString().equals("1")) {
            live_state.setText("报名中");
            live_state.setTextColor(Color.parseColor("#FEA41B"));
            live_state.setBackgroundResource(R.drawable.live_text_orange_bg);
        } else if (stringObjectHashMap.get("State").toString().equals("3")) {
            live_state.setText("直播中");
            live_state.setTextColor(Color.parseColor("#71C713"));
            live_state.setBackgroundResource(R.drawable.live_text_green_bg);
        }
        rl_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String isover = PreferenceHelper.readString(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.ISOVER, "false");
                String userstate = PreferenceHelper.readString(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.STATE, "0");
                if (!((CollegeManagerActivity) getActivity()).isRZDialog(userstate, isover)) {
                    ((CollegeManagerActivity) getActivity()).showRZDialog(userstate, isover);
                    return;
                } else {
                    Intent intent = new Intent(getActivity(), CourseIntrActivity.class);
                    intent.putExtra("id", stringObjectHashMap.get("LinkUrl").toString());
                    getActivity().startActivity(intent);
                }

            }
        });
    }
}
