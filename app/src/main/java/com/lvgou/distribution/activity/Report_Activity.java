package com.lvgou.distribution.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Constants;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.request.RequestTask;
import com.lvgou.distribution.utils.TGmd5;
import com.xdroid.common.utils.PreferenceHelper;
import com.xdroid.request.extension.XDroidRequest;
import com.xdroid.request.extension.config.DataType;
import com.xdroid.request.extension.impl.OnRequestListenerAdapter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by azerkang on 2016/8/16.
 */

public class Report_Activity extends BaseActivity {
    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.et_search02)
    private EditText et_search02;
    @ViewInject(R.id.iv_amap)
    private ImageView iv_amap;
    private String distributorid = null;
    private int pageindex = 1;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_shop);
        ViewUtils.inject(this);

        distributorid= PreferenceHelper.readString(Report_Activity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        String sign = TGmd5.getMD5(distributorid);
        if (distributorid.equals("") || distributorid.equals("null")) {
            openActivity(LoginActivity.class);
            finish();
        } else {
            if (checkNet()) {
                initData(distributorid, sign);
            }
        }

        final String[] shop_parent = new String[]{"常报备的店：", "最新报备的店："};
//        ArrayList<>new_shops=new ArrayList();
        ExpandableListAdapter adapter = new BaseExpandableListAdapter() {
            @Override
            public int getGroupCount() {
                return shop_parent.length;
            }

            @Override
            public int getChildrenCount(int i) {
                return 0;
            }

            @Override
            public Object getGroup(int i) {
                return null;
            }

            @Override
            public Object getChild(int i, int i1) {
                return null;
            }

            @Override
            public long getGroupId(int i) {
                return 0;
            }

            @Override
            public long getChildId(int i, int i1) {
                return 0;
            }

            @Override
             public boolean hasStableIds() {
                return false;
            }

            @Override
            public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
                View shopping_parent_view = View.inflate(Report_Activity.this, R.layout.report_parent, null);
                TextView parent_textview = (TextView) shopping_parent_view.findViewById(R.id.tv_report_parent);
                ImageView iv_report_parent = (ImageView) shopping_parent_view.findViewById(R.id.iv_report_parent);
                parent_textview.setText(shop_parent[i]);
                //根据位置判断对应imageview
                if (i == 0) {
                    iv_report_parent.setVisibility(View.GONE);
                } else {
                    iv_report_parent.setImageResource(R.mipmap.a_new_shop);
                }
                return view;
            }

            @Override
            public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
                if(i==0){
//                    View shopping_children_view=View.inflate(Report_Activity.this,R.);
                }
//
                return null;
            }

            @Override
            public boolean isChildSelectable(int i, int i1) {
                return false;
            }
        };
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
    }

    private void initData(String distributorid,String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("distributorid", distributorid);
        maps.put("pageindex",pageindex);
        maps.put("sign", sign);
        RequestTask.getInstance().getReport_shop(Report_Activity.this, maps, new OnReportRequestListener());

//
    }
    private class OnReportRequestListener extends OnRequestListenerAdapter<Object>{
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
//            String jsonInfo = response.result;
//                parseJson(jsonInfo);
                Gson gson = new Gson();
//                java.lang.reflect.Type type = new TypeToken<CommonShop>() {}.getType();
//                CommonShop commonShop=gson.fromJson(response, type);
//                commonShop.getResult().
        }
    }



    @OnClick({R.id.rl_back,  R.id.iv_amap, R.id.et_search02})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                Constants.IS_SHOW_ADD = "0";
                finish();
                break;
            case R.id.et_search02:
                break;
            case R.id.iv_amap:

                break;
            default:
                break;
        }
    }


}
