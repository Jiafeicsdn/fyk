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

import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.MerchantCenterActivity;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.entity.ReportInfoEntitiy;
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
 * Created by Snow on 2016/7/2 0002.
 */
public class ReportSearchHistoryViewHolder extends ViewHolderBase<ReportInfoEntitiy> {

    private Context context;
    private RelativeLayout rl_item;
    private View view01;
    private LinearLayout ll_item_report;
    private TextView tv_tittle_report;
    private ImageView iv_item_report;
    private CircleImageView img_usully_icon;
    private CircleImageView img_report_shop;
    private TextView tv_name;
    private TextView tv_address;
    private TextView tv_km;
    private TextView tv_main;
    private LinearLayout ll_addView;
    private RelativeLayout rl_guider;
private ImageView img_guide_policy;

    private static OnClassifyPostionClickListener<ReportInfoEntitiy> onListItemClickListener;

    @Override
    public View createView(LayoutInflater layoutInflater) {
        context = layoutInflater.getContext();
        View view = layoutInflater.inflate(R.layout.report_children_searchshop, null);
        view01=(View)view.findViewById(R.id.view01);
        rl_item = (RelativeLayout) view.findViewById(R.id.rl_item);
        img_guide_policy=(ImageView)view.findViewById(R.id.img_guide_policy);
        ll_item_report = (LinearLayout) view.findViewById(R.id.ll_item_report);
        tv_tittle_report = (TextView) view.findViewById(R.id.tv_tittle_report);
        iv_item_report = (ImageView) view.findViewById(R.id.iv_item_report);
        img_usully_icon = (CircleImageView) view.findViewById(R.id.img_usully_icon);
        img_report_shop = (CircleImageView) view.findViewById(R.id.iv_report_shop);
        tv_name = (TextView) view.findViewById(R.id.tv_report_shopname);
        tv_address = (TextView) view.findViewById(R.id.tv_report_shopaddress);
        tv_km = (TextView) view.findViewById(R.id.tv_report_juli);
        tv_main = (TextView) view.findViewById(R.id.tv_main);
        ll_addView = (LinearLayout) view.findViewById(R.id.ll_addView);
        rl_guider = (RelativeLayout) view.findViewById(R.id.rl_guider);
        return view;
    }

    @Override
    public void showData(int position, final ReportInfoEntitiy itemData) {
        DecimalFormat df = new DecimalFormat(".##");
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int screenWidth = wm.getDefaultDisplay().getWidth();
        if (itemData.getIs_have().equals("0")) { // 是否为 常报备店
            img_usully_icon.setVisibility(View.VISIBLE);
            img_report_shop.setVisibility(View.GONE);
            if (position == 0) {
                ll_item_report.setVisibility(View.VISIBLE);
                tv_tittle_report.setText("常预约的店：");
                iv_item_report.setVisibility(View.GONE);
            } else {
                ll_item_report.setVisibility(View.GONE);
            }
            view01.setVisibility(View.INVISIBLE);
        } else if (itemData.getIs_have().equals("1")) { // 是否为 新加入报备店
            view01.setVisibility(View.VISIBLE);
            img_usully_icon.setVisibility(View.GONE);
            img_report_shop.setVisibility(View.VISIBLE);
            if (position == 0) { // 表示 常报备店 数据为空
                ll_item_report.setVisibility(View.VISIBLE);
                tv_tittle_report.setText("最新加入的店：");
                iv_item_report.setVisibility(View.VISIBLE);
            } else {
                /*if (position == Integer.parseInt(itemData.getSize())) {
                    ll_item_report.setVisibility(View.VISIBLE);
                    tv_tittle_report.setText("最新加入的店：");
                    iv_item_report.setVisibility(View.VISIBLE);
                } else {
                    ll_item_report.setVisibility(View.GONE);
                }*/
                ll_item_report.setVisibility(View.GONE);
            }
        }
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.faxian_user_head) //设置图片在下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.faxian_user_head)//设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.faxian_user_head).build(); //设置图片加载/解码过程中错误时候显示的图片

        ImageLoader.getInstance().displayImage(Url.ROOT+itemData.getImg_path(), img_usully_icon, options);
        ImageLoader.getInstance().displayImage(Url.ROOT+itemData.getImg_path(), img_report_shop, options);
        tv_name.setText(itemData.getShopname());
        tv_address.setText(itemData.getAddress());
        if (!itemData.getLatitude().equals("") && !itemData.getLongitude().equals("") && !itemData.getLocal_latitude().equals("") && !itemData.getLocal_longitude().equals("")) {
            double juli = DistanceUtil.getDistance(Double.parseDouble(itemData.getLongitude()), Double.parseDouble(itemData.getLatitude()), Double.parseDouble(itemData.getLocal_longitude()), Double.parseDouble(itemData.getLocal_latitude()));
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
        img_usully_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, MerchantCenterActivity.class);
                intent.putExtra("reportid",itemData.getId());
                context.startActivity(intent);
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
        img_report_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, MerchantCenterActivity.class);
                intent.putExtra("reportid",itemData.getId());
                context.startActivity(intent);
            }
        });
        rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onListItemClickListener != null) {
                    onListItemClickListener.onClassifyPostionClick(itemData, 1);
                }
            }
        });

        rl_guider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!itemData.getLatitude().equals("") && !itemData.getLongitude().equals("")) {
                    if (onListItemClickListener != null) {
                        onListItemClickListener.onClassifyPostionClick(itemData, 2);
                    }
                } else {
                    MyToast.show(context, "经纬度获取获取失败");
                }
            }
        });
    }

    public static void setOnListItemClickListener(OnClassifyPostionClickListener<ReportInfoEntitiy> onListItemClickListener) {
        ReportSearchHistoryViewHolder.onListItemClickListener = onListItemClickListener;
    }
}
