package com.lvgou.distribution.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.entity.GoodMarketChildEntity;
import com.lvgou.distribution.entity.GoodMarketEntity;
import com.lvgou.distribution.inter.OnGoodsClickListener;
import com.lvgou.distribution.view.MyListView;
import com.xdroid.common.utils.PreferenceHelper;
import com.xdroid.functions.holder.ListViewDataAdapter;
import com.xdroid.functions.holder.ViewHolderBase;


/**
 * Created by Sonw on 2016/3/28 0028.
 */
public class GuoNeiViewHolder extends ViewHolderBase<GoodMarketEntity> {
    private TextView tv_name;
    private ImageView img_te;
    private ImageView img_jian;
    private ImageView img_huo;
    private TextView tv_status;
    private MyListView lv_list;
    private RelativeLayout rl_load_more;
    private TextView tv_load_more;
    private Context context;

    private ListViewDataAdapter<GoodMarketChildEntity> goodMarketChildEntityListViewDataAdapter;
    private String openType = "";

    private static OnGoodsClickListener<GoodMarketEntity> onGoodsClickListener;

    @Override
    public View createView(LayoutInflater layoutInflater) {
        context = layoutInflater.getContext();
        openType = PreferenceHelper.readString(context, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.OPENTYPE);
        View view = layoutInflater.inflate(R.layout.item_guonei, null);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        img_te = (ImageView) view.findViewById(R.id.img_te);
        img_jian = (ImageView) view.findViewById(R.id.img_jian);
        img_huo = (ImageView) view.findViewById(R.id.img_huo);
        tv_status = (TextView) view.findViewById(R.id.tv_remove_in);

        lv_list = (MyListView) view.findViewById(R.id.rl_list);
        rl_load_more = (RelativeLayout) view.findViewById(R.id.rl_load_more);
        tv_load_more = (TextView) view.findViewById(R.id.tv_load_more);
        return view;
    }

    @Override
    public void showData(final int position, final GoodMarketEntity itemData) {
        tv_status.setVisibility(View.VISIBLE);
        switch (Integer.parseInt(itemData.getStatue_img())) {
            case 1:
                img_te.setVisibility(View.GONE);
                img_jian.setVisibility(View.GONE);
                img_huo.setVisibility(View.GONE);
                break;
            case 2:
                img_te.setVisibility(View.GONE);
                img_jian.setVisibility(View.GONE);
                img_huo.setVisibility(View.VISIBLE);
                break;
            case 4:
                img_te.setVisibility(View.GONE);
                img_jian.setVisibility(View.GONE);
                img_huo.setVisibility(View.GONE);
                break;
            case 3:
                img_te.setVisibility(View.GONE);
                img_jian.setVisibility(View.GONE);
                img_huo.setVisibility(View.VISIBLE);
                break;
            case 5:
                img_te.setVisibility(View.GONE);
                img_jian.setVisibility(View.GONE);
                img_huo.setVisibility(View.GONE);
                break;
            case 6:
                img_te.setVisibility(View.GONE);
                img_jian.setVisibility(View.GONE);
                img_huo.setVisibility(View.VISIBLE);
                break;
            case 7:
                img_te.setVisibility(View.GONE);
                img_jian.setVisibility(View.GONE);
                img_huo.setVisibility(View.VISIBLE);
                break;
        }
        tv_name.setText(itemData.getName());

        if (itemData.getStatus_load_more().equals("true")) {
            rl_load_more.setVisibility(View.VISIBLE);
        } else if (itemData.getStatus_load_more().equals("false")) {
            rl_load_more.setVisibility(View.GONE);
        }

        if (itemData.getGuide_status().equals("1")) {
            tv_load_more.setText("点击查看更多...");
        } else if (itemData.getGuide_status().equals("2")) {
            tv_load_more.setText("↑收起");
        }


        goodMarketChildEntityListViewDataAdapter = itemData.getGoodMarketChildEntityListViewDataAdapter();
        goodMarketChildEntityListViewDataAdapter.setViewHolderClass(context, GoodMarketChildViewHolder.class);
        lv_list.setAdapter(goodMarketChildEntityListViewDataAdapter);

        tv_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onGoodsClickListener != null) {
                    onGoodsClickListener.onGoodsClick(itemData, position, v, 1);
                }
            }
        });

        tv_load_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemData.getGuide_status().equals("1")) {
                    if (onGoodsClickListener != null) {
                        onGoodsClickListener.onGoodsClick(itemData, position, v, 2);
                        tv_load_more.setText("↑收起");
                    }
                } else if (itemData.getGuide_status().equals("2")) {
                    if (onGoodsClickListener != null) {
                        onGoodsClickListener.onGoodsClick(itemData, position, v, 3);
                        tv_load_more.setText("点击查看更多...");
                    }
                }
            }
        });
    }

    public static void setOnGoodsClickListener(OnGoodsClickListener<GoodMarketEntity> onGoodsClickListener) {
        GuoNeiViewHolder.onGoodsClickListener = onGoodsClickListener;
    }
}