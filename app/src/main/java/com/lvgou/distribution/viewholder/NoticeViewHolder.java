package com.lvgou.distribution.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.util.LogUtils;
import com.lvgou.distribution.R;
import com.lvgou.distribution.entity.NoticeEntity;
import com.lvgou.distribution.inter.OnListItemClickListener;
import com.xdroid.functions.holder.ViewHolderBase;


/**
 * Created by Snow on 2016/3/28 0028.
 */
public class NoticeViewHolder extends ViewHolderBase<NoticeEntity> {

    private Context context;
    private ImageView img_icon;
    private TextView tv_title;
    private TextView tv_content;
    private TextView tv_time;
    private LinearLayout ll_item;

    private static OnListItemClickListener<NoticeEntity> noticeEntityOnListItemClickListener;

    @Override
    public View createView(LayoutInflater layoutInflater) {
        context = layoutInflater.getContext();
        View view = layoutInflater.inflate(R.layout.item_notice, null);
        img_icon = (ImageView) view.findViewById(R.id.img_icon);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_content = (TextView) view.findViewById(R.id.tv_production);
        tv_time = (TextView) view.findViewById(R.id.tv_time);
        ll_item = (LinearLayout) view.findViewById(R.id.ll_item);

        return view;
    }

    @Override
    public void showData(int position, final NoticeEntity itemData) {
        // 1=公告，2=学院
//        if (itemData.getStatus().equals("1")) {
//            img_icon.setBackgroundResource(R.mipmap.guangfang_tongzhi);
//        } else if (itemData.getStatus().equals("2")) {
//            img_icon.setBackgroundResource(R.mipmap.xueyuan_tongzhi);
//        }
switch (itemData.getIconType()){
    case 1:
        img_icon.setBackgroundResource(R.mipmap.icon_msg_ds);
        break;
    case 2:
        img_icon.setBackgroundResource(R.mipmap.icon_msg_kk);
        break;
    case 3:
        img_icon.setBackgroundResource(R.mipmap.icon_msg_fq);
        break;
    case 4:
        img_icon.setBackgroundResource(R.mipmap.icon_msg_yh);
        break;
    case 5:
        img_icon.setBackgroundResource(R.mipmap.icon_msg_tb);
        break;
    case 6:
        img_icon.setBackgroundResource(R.mipmap.icon_msg_kc_ts);
        break;
    case 7:
        img_icon.setBackgroundResource(R.mipmap.icon_msg_kc_hg);
        break;
    case 8:
        img_icon.setBackgroundResource(R.mipmap.icon_msg_kc);
        break;

}
        if (Integer.parseInt(itemData.getUser_id()) > 0) {
            tv_title.setTextColor(context.getResources().getColor(R.color.bg_notice_gray));
            tv_content.setTextColor(context.getResources().getColor(R.color.bg_notice_gray));
        } else {
            tv_title.setTextColor(context.getResources().getColor(R.color.bg_balck_three));
            tv_content.setTextColor(context.getResources().getColor(R.color.bg_balck_three));
        }

        tv_title.setText(itemData.getName());
        tv_content.setText(itemData.getContent());
        String[] str = itemData.getTime().split("T");
        tv_time.setText(str[0].substring(2, str[0].length()));


        ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noticeEntityOnListItemClickListener != null) {
                    noticeEntityOnListItemClickListener.onListItemClick(itemData);
                }
            }
        });
    }

    public static void setNoticeEntityOnListItemClickListener(OnListItemClickListener<NoticeEntity> noticeEntityOnListItemClickListener) {
        NoticeViewHolder.noticeEntityOnListItemClickListener = noticeEntityOnListItemClickListener;
    }
}
