package com.lvgou.distribution.viewholder;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.maps2d.model.Circle;
import com.lidroid.xutils.util.LogUtils;
import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.MerchantCenterActivity;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.entity.ReportSearchReasultEntity;
import com.lvgou.distribution.inter.OnClassifyPostionClickListener;
import com.lvgou.distribution.utils.DistanceUtil;
import com.lvgou.distribution.view.CircleImageView;
import com.lvgou.distribution.view.CustomeTextView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.functions.holder.ViewHolderBase;

import java.text.DecimalFormat;

/**
 * Created by Administrator on 2016/8/25.
 */
public class ReportSearchOneViewHolder extends ViewHolderBase<ReportSearchReasultEntity> {

    private TextView tv_name;
    private TextView tv_address;
    private TextView tv_km;
    private RelativeLayout rl_guider;
    private RelativeLayout rl_item;
    private TextView tv_main;
    private LinearLayout ll_addView;
    private Context context;
    private CircleImageView img_usully_icon;

    private ImageView img_guide_policy;
    private static OnClassifyPostionClickListener<ReportSearchReasultEntity> onClassifyPostionClickListener;

    @Override
    public View createView(LayoutInflater layoutInflater) {
        context = layoutInflater.getContext();
        View view = layoutInflater.inflate(R.layout.item_report_search_result_new, null);
        img_guide_policy=(ImageView)view.findViewById(R.id.img_guide_policy);
        tv_name = (TextView) view.findViewById(R.id.tv_report_shopname);
        tv_address = (TextView) view.findViewById(R.id.tv_report_shopaddress);
        tv_km = (TextView) view.findViewById(R.id.tv_report_juli);
        tv_main = (TextView) view.findViewById(R.id.tv_main);
        ll_addView = (LinearLayout) view.findViewById(R.id.ll_addView);
        ll_addView.setVisibility(View.GONE);
        rl_item = (RelativeLayout) view.findViewById(R.id.rl_item);
        rl_guider = (RelativeLayout) view.findViewById(R.id.rl_guider);
        img_usully_icon = (CircleImageView) view.findViewById(R.id.img_usully_icon);

        return view;
    }

    @Override
    public void showData(int position, final ReportSearchReasultEntity itemData) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int screenWidth = wm.getDefaultDisplay().getWidth();
        DecimalFormat df = new DecimalFormat(".##");
        tv_name.setText(itemData.getName());
        tv_address.setText(itemData.getAddress());
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.faxian_user_head) //设置图片在下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.faxian_user_head)//设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.faxian_user_head).build(); //设置图片加载/解码过程中错误时候显示的图片

        ImageLoader.getInstance().displayImage(Url.ROOT + itemData.getLogo(), img_usully_icon, options);
        img_usully_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, MerchantCenterActivity.class);
                intent.putExtra("reportid",itemData.getId());
                context.startActivity(intent);
            }
        });
        rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //  item 点击事件
                if (onClassifyPostionClickListener != null) {
                    onClassifyPostionClickListener.onClassifyPostionClick(itemData, 1);
                }
            }
        });
        img_guide_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, MerchantCenterActivity.class);
                intent.putExtra("reportid",itemData.getId());
                context.startActivity(intent);
            }
        });
        rl_guider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!itemData.getLongitude().equals("") && !itemData.getLatitude().equals("")) {

                    if (onClassifyPostionClickListener != null) { // 导航
                        onClassifyPostionClickListener.onClassifyPostionClick(itemData, 2);
                    }
                } else {
                    MyToast.show(context, "经纬度获取获取失败");
                }
            }
        });

        if (!itemData.getLatitude().equals("") && !itemData.getLongitude().equals("") && !itemData.getWeidu().equals("") && !itemData.getJingdu().equals("")) {
            double juli = DistanceUtil.getDistance(Double.parseDouble(itemData.getLongitude()), Double.parseDouble(itemData.getLatitude()), Double.parseDouble(itemData.getJingdu()), Double.parseDouble(itemData.getWeidu()));
            if (juli < 1000) {
                tv_km.setText(juli + "m");
            } else {
                double a = juli / 1000.00;
                if (!(a + "").equals("")) {
                    tv_km.setText(df.format(a) + "km");
                }
            }
        } else {
            tv_km.setText("");
        }
        ll_addView.removeAllViews();
        int new_width = 0;
        if (itemData.getBusiness().contains(";")) {
            String[] str = itemData.getBusiness().split(";");
            for (int i = 0; i < str.length; i++) {
                CustomeTextView textView = new CustomeTextView(context);
                textView.getText().setText(str[i]);
                int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                textView.measure(w, h);
                tv_main.measure(w, h);
                int width = textView.getMeasuredWidth();
                new_width += width;
                if (new_width < screenWidth - 5 * i - tv_main.getMeasuredWidth() - 10) {
                    ll_addView.addView(textView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                }
            }
        } else if (itemData.getBusiness().length() > 0) {
            CustomeTextView textView = new CustomeTextView(context);
            textView.getText().setText(itemData.getBusiness());
            ll_addView.addView(textView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
    }

    public static void setOnClassifyPostionClickListener(OnClassifyPostionClickListener<ReportSearchReasultEntity> onClassifyPostionClickListener) {
        ReportSearchOneViewHolder.onClassifyPostionClickListener = onClassifyPostionClickListener;
    }
}

