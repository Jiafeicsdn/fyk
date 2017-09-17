package com.lvgou.distribution.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.bean.BannedUserBean;
import com.lvgou.distribution.bean.FengCircleDynamicBean;
import com.lvgou.distribution.utils.ImageUtils;
import com.lvgou.distribution.view.CircleImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/9.
 */
public class BannedListAdapter extends BaseAdapter {
    private Context mContext;
    private List<BannedUserBean> bannedUserBeans;
    private BannedAdapterListener bannedAdapterListener;

    public BannedListAdapter(Context context) {
        mContext = context;
        bannedUserBeans = new ArrayList<>();
    }

    public void setmAdapterListener(BannedAdapterListener adapterListener) {
        bannedAdapterListener = adapterListener;
    }

    public void setUserData(List<BannedUserBean> bannedUserBeans) {
        this.bannedUserBeans.addAll(bannedUserBeans);
    }

    @Override
    public int getCount() {
        return bannedUserBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return bannedUserBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.banned_item_view, null);
            viewHolder = new ViewHolder();
            viewHolder.img_head_pic = (CircleImageView) convertView.findViewById(R.id.img_head_pic);
            viewHolder.img_teacher_label = (ImageView) convertView.findViewById(R.id.img_teacher_label);
            viewHolder.txt_user_name = (TextView) convertView.findViewById(R.id.txt_user_name);
            viewHolder.img_sex = (ImageView) convertView.findViewById(R.id.img_sex);
            viewHolder.img_del_banned = (ImageView) convertView.findViewById(R.id.img_del_banned);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String path = ImageUtils.getInstance().getPath(String.valueOf(bannedUserBeans.get(position).getDistributorID()));
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.faxian_user_head) //设置图片在下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.faxian_user_head)//设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.faxian_user_head).build(); //设置图片加载/解码过程中错误时候显示的图片


        ImageLoader.getInstance().displayImage(path, viewHolder.img_head_pic, options);
        viewHolder.img_teacher_label.setVisibility(View.VISIBLE);
        if (bannedUserBeans.get(position).getUserType() == 3) {
            viewHolder.img_teacher_label.setImageResource(R.mipmap.bg_tecaher_authentication);
            viewHolder.txt_user_name.setTextColor(mContext.getResources().getColor(R.color.text_color_ff9900));
        } else if (bannedUserBeans.get(position).getUserType() == 2) {
            if (bannedUserBeans.get(position).getIsRZ() == 1) {
                viewHolder.img_teacher_label.setImageResource(R.mipmap.icon_certified);
                viewHolder.txt_user_name.setTextColor(mContext.getResources().getColor(R.color.text_color_ff9900));
            } else {
                viewHolder.img_teacher_label.setVisibility(View.GONE);
                viewHolder.txt_user_name.setTextColor(mContext.getResources().getColor(R.color.text_color_333333));
            }
        } else if (bannedUserBeans.get(position).getUserType() == 1) {
            viewHolder.img_teacher_label.setImageResource(R.mipmap.icon_official_certified);
            viewHolder.txt_user_name.setTextColor(mContext.getResources().getColor(R.color.text_color_ff9900));
        }
        if (bannedUserBeans.get(position).getSex() == 1) {
            viewHolder.img_sex.setImageResource(R.mipmap.icon_man);
        } else if (bannedUserBeans.get(position).getSex() == 2) {
            viewHolder.img_sex.setImageResource(R.mipmap.icon_woman);
        }
        viewHolder.txt_user_name.setText(bannedUserBeans.get(position).getDistributorName());
        viewHolder.img_del_banned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bannedAdapterListener.releaseBanned(bannedUserBeans.get(position).getDistributorID());
            }
        });
        return convertView;
    }

    public class ViewHolder {
        private CircleImageView img_head_pic;
        private ImageView img_teacher_label;
        private TextView txt_user_name;
        private ImageView img_sex;
        private ImageView img_del_banned;
    }

    public interface BannedAdapterListener {
        public void releaseBanned(int distributId);
    }
}
