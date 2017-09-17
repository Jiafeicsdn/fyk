package com.lvgou.distribution.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.entity.ExchangeTuanbiRecorderEntity;
import com.lvgou.distribution.inter.OnListItemClickListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.xdroid.functions.holder.ViewHolderBase;

/**
 * Created by Administrator on 2016/11/3.
 */
public class ExchangeTunabiRecorderViewHolder extends ViewHolderBase<ExchangeTuanbiRecorderEntity> {

    private RelativeLayout rl_item;
    private Context context;
    private TextView tv_time;
    private TextView tv_state;
    private ImageView img_icon;
    private TextView tv_title;
    private TextView tv_tuanbi;
    private TextView tv_num;
    private TextView tv_price;
    DisplayImageOptions options;

    private static OnListItemClickListener<ExchangeTuanbiRecorderEntity> exchangeTuanbiRecorderEntityOnListItemClickListener;

    @Override
    public View createView(LayoutInflater layoutInflater) {
        context = layoutInflater.getContext();
        View view = layoutInflater.inflate(R.layout.item_exchange_tuanbi_recoder, null);
        rl_item = (RelativeLayout) view.findViewById(R.id.rl_item);
        tv_time = (TextView) view.findViewById(R.id.tv_time);
        tv_state = (TextView) view.findViewById(R.id.tv_state);
        img_icon = (ImageView) view.findViewById(R.id.img_icon);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_tuanbi = (TextView) view.findViewById(R.id.tv_seek_tuanbi);
        tv_num = (TextView) view.findViewById(R.id.tv_num);
        tv_price = (TextView) view.findViewById(R.id.tv_tuanbi);
        return view;
    }

    @Override
    public void showData(int position, final ExchangeTuanbiRecorderEntity itemData) {
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.bg_none_good)            // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.bg_none_good)    // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.bg_none_good)            // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)                            // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(8))    // 设置成圆角图片
                .build();
        ImageLoader.getInstance().displayImage(Url.ROOT + itemData.getImg_path(), img_icon, options);

        tv_time.setText(itemData.getTime().split("T")[0]);

        if (itemData.getState().equals("1")) {
            tv_state.setText("待发货");
            tv_state.setTextColor(context.getResources().getColor(R.color.bg_code_number));
        } else if (itemData.getState().equals("2")) {
            tv_state.setText("已完成");
            tv_state.setTextColor(context.getResources().getColor(R.color.bg_balck_three));
        }
        tv_title.setText(itemData.getTitle());
        tv_tuanbi.setText(itemData.getTuanbi() + "团币");
        tv_num.setText("x" + itemData.getNum());
        tv_price.setText(itemData.getPrice() + "团币");


        rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (exchangeTuanbiRecorderEntityOnListItemClickListener != null) {
                    exchangeTuanbiRecorderEntityOnListItemClickListener.onListItemClick(itemData);
                }
            }
        });
    }

    public static void setExchangeTuanbiRecorderEntityOnListItemClickListener(OnListItemClickListener<ExchangeTuanbiRecorderEntity> exchangeTuanbiRecorderEntityOnListItemClickListener) {
        ExchangeTunabiRecorderViewHolder.exchangeTuanbiRecorderEntityOnListItemClickListener = exchangeTuanbiRecorderEntityOnListItemClickListener;
    }
}
