package com.lvgou.distribution.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.DistributorHomeActivity;
import com.lvgou.distribution.activity.OfficialHomePageActivity;
import com.lvgou.distribution.activity.TeacherHomeActivity;
import com.lvgou.distribution.activity.UserPersonalCenterActivity;
import com.lvgou.distribution.bean.FengCircleDynamicBean;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.entity.CircleUserEntity;
import com.lvgou.distribution.utils.ImageUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.xdroid.common.utils.PreferenceHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/14.
 */
public class Concern_FansAdapter extends BaseAdapter {
    private Context mContext;
private String distributorId;
    private List<CircleUserEntity> circleUserEntities;
    public OnClassifyPostionClickListener onClassifyPostionClick;
    public Concern_FansAdapter(Context context) {
        mContext = context;
        distributorId = PreferenceHelper.readString(mContext, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        circleUserEntities = new ArrayList<>();
    }
    public void setOnClassifyPostionClick(OnClassifyPostionClickListener classifyPostionClick){
        onClassifyPostionClick=classifyPostionClick;
    }
    public void setFengcircleData(List<CircleUserEntity> circleUserEntityList) {
        this.circleUserEntities.addAll(circleUserEntityList);
    }

    public List<CircleUserEntity> getFengcircleData() {
        return circleUserEntities;
    }
    @Override
    public int getCount() {
        return circleUserEntities.size();
    }

    @Override
    public Object getItem(int position) {
        return circleUserEntities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_circle_user, null);
            viewHolder.rl_item = (RelativeLayout) convertView.findViewById(R.id.rl_item);
            viewHolder.img_head = (ImageView) convertView.findViewById(R.id.img_teacher_head);
            viewHolder.img_user_identify = (ImageView) convertView.findViewById(R.id.img_user_identify);
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tv_fegnwen_num = (TextView) convertView.findViewById(R.id.tv_fengwen_num);
            viewHolder.tv_fangs_num = (TextView) convertView.findViewById(R.id.tv_fans_num);
            viewHolder.img_follow = (ImageView) convertView.findViewById(R.id.img_follow);
            viewHolder.img_follow.setVisibility(View.GONE);
            viewHolder.img_sex = (ImageView) convertView.findViewById(R.id.img_sex);
            viewHolder.img_sex.setVisibility(View.GONE);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.teacher_default_head)            // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.teacher_default_head)    // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.teacher_default_head)            // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(false)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(false)                            // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(360))    // 设置成圆角图片
                .build();
        ImageLoader.getInstance().displayImage(ImageUtils.getInstance().getPath(circleUserEntities.get(position).getID()), viewHolder.img_head, options);
        viewHolder.tv_name.setText(circleUserEntities.get(position).getRealName());
        viewHolder.tv_fegnwen_num.setText(circleUserEntities.get(position).getFengwenCount());
        viewHolder.tv_fangs_num.setText(circleUserEntities.get(position).getFansCount());

        /*if (distributorId.equals(circleUserEntities.get(position).getID())) {
            viewHolder.img_follow.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.img_follow.setVisibility(View.VISIBLE);
        }*/

        if (circleUserEntities.get(position).getTuanBi().equals("1")) {
            viewHolder.img_follow.setBackgroundResource(R.mipmap.circle_add_follow);
        } else if (circleUserEntities.get(position).getTuanBi().equals("2")) {
            viewHolder.img_follow.setBackgroundResource(R.mipmap.yiguanzhu);
        } else if (circleUserEntities.get(position).getTuanBi().equals("3")) {
            viewHolder.img_follow.setBackgroundResource(R.mipmap.huxiangguanzhu);
        }

        if (circleUserEntities.get(position).getAttr().equals("1")) {
            viewHolder.img_sex.setImageResource(R.mipmap.icon_man);
        } else if (circleUserEntities.get(position).getAttr().equals("2")) {
            viewHolder.img_sex.setImageResource(R.mipmap.icon_woman);
        }
        if (circleUserEntities.get(position).getUserType() == 3) {
            viewHolder.img_user_identify.setImageResource(R.mipmap.bg_tecaher_authentication);
            viewHolder.tv_name.setTextColor(mContext.getResources().getColor(R.color.text_color_ff9900));
        } else if(circleUserEntities.get(position).getUserType() == 2){
            /*if(circleUserEntities.get(position).getState()==5){
                viewHolder.img_user_identify.setImageResource(R.mipmap.icon_certified);
                viewHolder.tv_name.setTextColor(mContext.getResources().getColor(R.color.text_color_ff9900));
            }else{
                viewHolder.img_user_identify.setVisibility(View.GONE);
                viewHolder.tv_name.setTextColor(mContext.getResources().getColor(R.color.text_color_333333));
            }*/
            viewHolder.img_user_identify.setVisibility(View.GONE);
            viewHolder.tv_name.setTextColor(mContext.getResources().getColor(R.color.text_color_333333));
        }else if(circleUserEntities.get(position).getUserType() == 1){
            viewHolder.img_user_identify.setImageResource(R.mipmap.icon_official_certified);
            viewHolder.tv_name.setTextColor(mContext.getResources().getColor(R.color.text_color_ff9900));
        }
        viewHolder.rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (onClassifyPostionClick != null) {
//                    onClassifyPostionClick.onClassifyPostionClick(circleUserEntities.get(position), 1);
//                }
                if (circleUserEntities.get(position).getUserType()==3){
                    Intent intent1 = new Intent(mContext, TeacherHomeActivity.class);
                    intent1.putExtra("seeDistributorId",  circleUserEntities.get(position).getID());
                    mContext.startActivity(intent1);
                }else {
                    Intent intent1 = new Intent(mContext, DistributorHomeActivity.class);
                    intent1.putExtra("seeDistributorId",  circleUserEntities.get(position).getID()+"");
                    mContext.startActivity(intent1);
                }
                /*if(circleUserEntities.get(position).getUserType()==1) {
                    Intent intent = new Intent(mContext, OfficialHomePageActivity.class);
                    intent.putExtra("seeDistributorId",circleUserEntities.get(position).getID());
                    mContext.startActivity(intent);
                }else{
                    Bundle bundle = new Bundle();
                    bundle.putInt("distributorid", Integer.parseInt(circleUserEntities.get(position).getID()));
                    Intent intent = new Intent(mContext, UserPersonalCenterActivity.class);
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }*/
            }
        });

        viewHolder.img_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClassifyPostionClick != null) {
                    onClassifyPostionClick.onClassifyPostionClick(circleUserEntities.get(position), 2);
                }
            }
        });

        return convertView;
    }

    private class ViewHolder {
        private RelativeLayout rl_item;
        private ImageView img_head;
        private ImageView img_user_identify;
        private TextView tv_name;
        private TextView tv_fegnwen_num;
        private TextView tv_fangs_num;
        private ImageView img_follow;
        private ImageView img_sex;
    }

    public interface OnClassifyPostionClickListener{
        public void onClassifyPostionClick(CircleUserEntity circleUserEntity,int type);
    }
}
