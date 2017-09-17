package com.lvgou.distribution.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.entity.FamousTeacherEntity;
import com.lvgou.distribution.inter.OnClassifyClickListener;
import com.lvgou.distribution.inter.OnListItemClickListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.xdroid.functions.holder.ViewHolderBase;

/**
 * Created by Snow on 2016/5/16 0016.
 */
public class FamousTeacherViewHolder extends ViewHolderBase<FamousTeacherEntity> {

    private RelativeLayout rl_item;
    private ImageView img_stats;
    private ImageView img_head;
    private TextView tv_name;
    private TextView tv_fee;
    private TextView tv_people_num;
    private TextView tv_theme;
    private TextView tv_time;
    private TextView tv_people;
    DisplayImageOptions options_head;

    private static OnClassifyClickListener<FamousTeacherEntity> onListItemClickListener;


    @Override
    public View createView(LayoutInflater layoutInflater) {
        View view = layoutInflater.inflate(R.layout.item_famous_teacher, null);
        rl_item = (RelativeLayout) view.findViewById(R.id.rl_item);
        img_stats = (ImageView) view.findViewById(R.id.img_status);
        img_head = (ImageView) view.findViewById(R.id.img_head);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_fee = (TextView) view.findViewById(R.id.tv_fee);
        tv_people_num = (TextView) view.findViewById(R.id.tv_num);
        tv_theme = (TextView) view.findViewById(R.id.tv_theme);
        tv_time = (TextView) view.findViewById(R.id.tv_time);
        tv_people = (TextView) view.findViewById(R.id.tv_fit);
        return view;
    }

    @Override
    public void showData(int position, final FamousTeacherEntity itemData) {
        tv_name.setText(itemData.getName());
        tv_people.setText(itemData.getFit_people());
        tv_theme.setText(itemData.getTheme());
        tv_time.setText(itemData.getTime());
        if (Integer.parseInt(itemData.getFee()) > 0) {
            tv_fee.setText(itemData.getFee() + "团币/人");
        } else {
            tv_fee.setText("免费");
        }
        tv_people_num.setText(itemData.getPeople_num());
        options_head = new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.bg_none_good)            // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.bg_none_good)    // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.bg_none_good)            // 设置图片加载或解码过程中发生错误显示的图片
                .displayer(new RoundedBitmapDisplayer(360))    // 设置成圆角图片
                .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)                            // 设置下载的图片是否缓存在SD卡中
                .build();
        ImageLoader.getInstance().displayImage(Url.ROOT + itemData.getImg_path(), img_head, options_head);
        //1=报名中，2=报名截止，3=进行中，4=已结束
        if (itemData.getState().equals("1")) {
            img_stats.setImageResource(R.mipmap.bg_zhong);
        } else if (itemData.getState().equals("2")) {
            img_stats.setImageResource(R.mipmap.bg_jiezhi);
        } else if (itemData.getState().equals("3")) {
            img_stats.setImageResource(R.mipmap.bg_jinxing);
        } else if (itemData.getState().equals("4")) {
            img_stats.setImageResource(R.mipmap.bg_over);
        }

        rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onListItemClickListener != null) {
                    onListItemClickListener.onClassifyClick(itemData);
                }
            }
        });
    }

    public static void setOnListItemClickListener(OnClassifyClickListener<FamousTeacherEntity> onListItemClickListener) {
        FamousTeacherViewHolder.onListItemClickListener = onListItemClickListener;
    }
}
