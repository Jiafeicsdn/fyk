package com.lvgou.distribution.viewholder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lidroid.xutils.util.LogUtils;
import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.DynamicDetailsActivity;
import com.lvgou.distribution.activity.ImagePagerActivity;
import com.lvgou.distribution.activity.UserPersonalCenterActivity;
import com.lvgou.distribution.bean.FengCircleDynamicBean;
import com.lvgou.distribution.bean.FengCircleImageBean;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.inter.OnFengWenPostionClickListener;
import com.lvgou.distribution.ninegridview.NineGridView;
import com.lvgou.distribution.ninegridview.NineGridViewAdapter;
import com.lvgou.distribution.utils.ImageUtils;
import com.lvgou.distribution.view.CircleImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xdroid.common.utils.PreferenceHelper;
import com.xdroid.functions.holder.ViewHolderBase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/10/25.
 */
public class FengWenSearcherViewHolder extends ViewHolderBase<FengCircleDynamicBean> {

    private Context context;
    CircleImageView img_head_pic;
    ImageView img_teacher_label;
    TextView txt_user_name;
    TextView txt_issue_time;
    ImageView img_concern;
    TextView txt_title;
    TextView txt_desc;
    NineGridView nineGrid;
    LinearLayout categoryNames_layout;
    TextView txt_praise;
    TextView txt_comment;
    LinearLayout layout_desc;
    ImageView img_sex;
    TextView tv_address;
    private String distributorid = "";

    private static OnFengWenPostionClickListener<FengCircleDynamicBean> onFengWenPostionClickListener;

    @Override
    public View createView(LayoutInflater layoutInflater) {
        context = layoutInflater.getContext();
        View view = layoutInflater.inflate(R.layout.item_news, null);
        img_head_pic = (CircleImageView) view.findViewById(R.id.img_head_pic);
        img_teacher_label = (ImageView) view.findViewById(R.id.img_teacher_label);
        txt_user_name = (TextView) view.findViewById(R.id.txt_user_name);
        txt_issue_time = (TextView) view.findViewById(R.id.txt_issue_time);
        img_concern = (ImageView) view.findViewById(R.id.img_concern);
        img_sex = (ImageView) view.findViewById(R.id.img_sex);
        tv_address = (TextView) view.findViewById(R.id.tv_address);
        txt_title = (TextView) view.findViewById(R.id.txt_title);
        txt_desc = (TextView) view.findViewById(R.id.txt_desc);
        nineGrid = (NineGridView) view.findViewById(R.id.nineGrid);
        categoryNames_layout = (LinearLayout) view.findViewById(R.id.categoryNames_layout);
        txt_praise = (TextView) view.findViewById(R.id.txt_praise);
        txt_comment = (TextView) view.findViewById(R.id.txt_comment);
        layout_desc = (LinearLayout) view.findViewById(R.id.layout_desc);
        return view;
    }

    @Override
    public void showData(final int position, final FengCircleDynamicBean itemData) {
        distributorid = PreferenceHelper.readString(context, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        if (String.valueOf(itemData.getDistributorID()).equals(distributorid)) {
            img_concern.setVisibility(View.GONE);
        } else {
            img_concern.setVisibility(View.VISIBLE);
        }

        if (itemData.getUserType() == 3) {
            img_teacher_label.setImageResource(R.mipmap.bg_tecaher_authentication);
            txt_user_name.setTextColor(context.getResources().getColor(R.color.text_color_ff9900));
        } else if(itemData.getUserType() == 2){
            if(itemData.getIsRZ()==1){
                img_teacher_label.setImageResource(R.mipmap.icon_certified);
                txt_user_name.setTextColor(context.getResources().getColor(R.color.text_color_ff9900));
            }else{
                img_teacher_label.setVisibility(View.GONE);
                txt_user_name.setTextColor(context.getResources().getColor(R.color.text_color_333333));
            }
        }else if(itemData.getUserType() == 1){
            img_teacher_label.setImageResource(R.mipmap.icon_official_certified);
            txt_user_name.setTextColor(context.getResources().getColor(R.color.text_color_ff9900));
        }
        if (!"null".equals(itemData.getCurrentLocation()) &&itemData.getCurrentLocation() != null && itemData.getCurrentLocation().length() > 0) {
            tv_address.setVisibility(View.VISIBLE);
        } else {
            tv_address.setVisibility(View.GONE);
        }
        tv_address.setText(itemData.getCurrentLocation());

        if (itemData.getSex().equals("1")) {
            img_sex.setImageResource(R.mipmap.icon_man);
        } else if (itemData.getSex().equals("2")) {
            img_sex.setImageResource(R.mipmap.icon_woman);
        }


        if (itemData.getFollowed().equals("1")) {
            img_concern.setImageResource(R.mipmap.yiguanzhu);
        } else {
            img_concern.setImageResource(R.mipmap.circle_add_follow);
        }

        if (itemData.getZaned() == 1) {
            Drawable drawable = context.getResources().getDrawable(R.mipmap.icon_fengwen_zaned);
            /// 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            txt_praise.setCompoundDrawables(drawable, null, null, null);
        } else {
            Drawable drawable = context.getResources().getDrawable(R.mipmap.icon_fengwen_zan);
            /// 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            txt_praise.setCompoundDrawables(drawable, null, null, null);
        }
        if (itemData.getZanCount() > 0) {
            txt_praise.setText(String.valueOf(itemData.getZanCount()));
        } else {
            txt_praise.setText("赞");
        }
        if (itemData.getCommentCount() > 0) {
            txt_comment.setText(String.valueOf(itemData.getCommentCount()));
        } else {
            txt_comment.setText("评论");
        }
        txt_user_name.setText(itemData.getDistributorName());

        if (itemData.getSourceDistributorID() > 0) {
//            txt_title.setText(itemData.getSourceTitle());
            txt_title.setVisibility(View.GONE);
            int soureceLength = itemData.getSourceDistributorName().length();
            StringBuffer stringBuffer = new StringBuffer();
//            stringBuffer.append("@");
//            stringBuffer.append(itemData.getSourceDistributorName());
//            stringBuffer.append(":");
//            stringBuffer.append(itemData.getContent());
            stringBuffer.append(itemData.getSourceTitle());
            stringBuffer.append(",");
            stringBuffer.append(itemData.getContent());
//            SpannableStringBuilder style = new SpannableStringBuilder(stringBuffer);
//            style.setSpan(new ClickableSpan() {
//                @Override
//                public void onClick(View widget) {
//                    if (onFengWenPostionClickListener != null) {
//                        onFengWenPostionClickListener.onFengWenPostionClick(itemData, 1);
//                    }
//                }
//
//                @Override
//                public void updateDrawState(TextPaint ds) {
//                    super.updateDrawState(ds);
//                    ds.setColor(context.getResources().getColor(R.color.bg_daoliu_yellow_two));
//                    // 去掉下划线
//                    ds.setUnderlineText(false);
//                }
//            }, 0, soureceLength + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            txt_desc.setText(stringBuffer);
            txt_desc.setMovementMethod(LinkMovementMethod.getInstance());
            txt_desc.setVisibility(View.VISIBLE);
//            layout_desc.setBackgroundColor(Color.parseColor("#f2f2f2"));
        } else {
            txt_desc.setVisibility(View.GONE);
            txt_title.setVisibility(View.VISIBLE);
            txt_title.setText(itemData.getTitle());
//            layout_desc.setBackgroundColor(Color.parseColor("#00000000"));
        }
        if (itemData.getCreateTime() != null && itemData.getCreateTime().length() > 0) {
            String[] str = itemData.getCreateTime().split("T");
            SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date_e = dfs.format(new Date());
            String date_b = str[0] + " " + str[1];
            try {
                Date begin = dfs.parse(date_b);
                Date end = dfs.parse(date_e);
                long between = (end.getTime() - begin.getTime()) / 1000;//除以1000是为了转换成秒
                long month1= between / (30*24 * 3600);
                long day1 = between / (24 * 3600);
                long hour1 = between / 3600;
                long minute1 = between / 60;
                if (minute1 == 0) {
                    txt_issue_time.setText("刚刚");
                } else if (minute1 < 60) {
                    txt_issue_time.setText(minute1 + "分钟前");
                } else if (hour1 < 24) {
                    txt_issue_time.setText(hour1 + "小时前");
                } else if (day1 < 30) {
                    txt_issue_time.setText(day1 + "天前");
                } else if (month1<12){
                    txt_issue_time.setText(month1 + "月前");
                }else {
                    txt_issue_time.setText(str[0] + " " + str[1].split(":")[0] + ":" + str[1].split(":")[1]);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        List<FengCircleImageBean> images = itemData.getmImgUrlList();
        if (images == null || images.size() < 1) {
            nineGrid.setVisibility(View.GONE);
        } else {
            nineGrid.setVisibility(View.VISIBLE);
        }
        if (nineGrid.getVisibility() == View.GONE && txt_desc.getVisibility() == View.GONE) {
            layout_desc.setVisibility(View.GONE);
        } else {
            layout_desc.setVisibility(View.VISIBLE);
        }
        nineGrid.setAdapter(mAdapter);

        if (images != null && images.size() == 1) {
            nineGrid.setSingleImageRatio(images.get(0).getWidth() * 1.0f / images.get(0).getHeight());
            nineGrid.setSingleImageSize(images.get(0).getWidth());
        }
        nineGrid.setImagesData(images);

        String path = ImageUtils.getInstance().getPath(String.valueOf(itemData.getDistributorID()));
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.faxian_user_head) //设置图片在下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.faxian_user_head)//设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.faxian_user_head).build(); //设置图片加载/解码过程中错误时候显示的图片

        ImageLoader.getInstance().displayImage(path, img_head_pic, options);
        categoryNames_layout.removeAllViews();
        if (itemData.getCategoryNames().size() > 0) {
            for (int i = 0; i < itemData.getCategoryNames().size(); i++) {
                TextView textView = new TextView(context);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0, 0, 10, 0);
                textView.setLayoutParams(layoutParams);
                textView.setPadding(12, 6, 12, 6);
                textView.setTextSize(10);
                textView.setBackgroundResource(R.drawable.bg_tag_shape);
                textView.setText(itemData.getCategoryNames().get(i));
                categoryNames_layout.addView(textView);
            }
        }

        img_head_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //个人中心
                Intent intent = new Intent(context, UserPersonalCenterActivity.class);
                intent.putExtra("usertype", itemData.getUserType());
                intent.putExtra("distributorid", itemData.getDistributorID());
                context.startActivity(intent);
            }
        });
        //关注
        img_concern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onFengWenPostionClickListener != null) {
                    onFengWenPostionClickListener.onFengWenPostionClick(itemData, 2);
                }
            }
        });
        //点赞
        txt_praise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onFengWenPostionClickListener != null) {
                    onFengWenPostionClickListener.onFengWenPostionClick(itemData, 3);
                }
            }
        });
        //评论
        txt_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DynamicDetailsActivity.class);
                intent.putExtra("talkId", itemData.getID());
                intent.putExtra("position", position);
                context.startActivity(intent);
            }
        });
        txt_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DynamicDetailsActivity.class);
                intent.putExtra("talkId", itemData.getID());
                intent.putExtra("position", position);
                context.startActivity(intent);
            }
        });
    }


    public String getPath(String distributorid) {
        int a = Integer.parseInt(distributorid) / 250000;
        int b = Integer.parseInt(distributorid) % 250000;
        int c = b / 500;
        int d = Integer.parseInt(distributorid) % 500;
        String path = Url.ROOT + "/UploadFile/Face/Distributor/" + a + "/" + c + "/" + d + ".jpg";
        return path;
    }


    private NineGridViewAdapter<FengCircleImageBean> mAdapter = new NineGridViewAdapter<FengCircleImageBean>() {
        @Override
        public void onDisplayImage(Context context, ImageView imageView, int size, FengCircleImageBean imageInfo) {
            if (size > 1) {
                if(null!=imageInfo.getSmallPicUrl()&&!"".equals(imageInfo.getSmallPicUrl())){
                    Glide.with(context).load(imageInfo.getSmallPicUrl())
                            .placeholder(R.mipmap.home_loading)//
                            .error(R.mipmap.home_loading)//
                            .into(imageView);
                }else{
                    Glide.with(context).load(imageInfo.getPicUrl())
                            .placeholder(R.mipmap.home_loading)//
                            .error(R.mipmap.home_loading)//
                            .into(imageView);
                }
            } else {
                Glide.with(context).load(imageInfo.getPicUrl())//
                        .placeholder(R.mipmap.home_loading)//
                        .error(R.mipmap.home_loading)//
                        .into(imageView);
            }
        }

        @Override
        protected void onImageItemClick(Context context, NineGridView nineGridView, int index, List<FengCircleImageBean> imageInfo) {

            Intent intent = new Intent(context, ImagePagerActivity.class);
            Bundle bundle = new Bundle();
            ArrayList<String> list = new ArrayList<String>();
            for (int i = 0; i < imageInfo.size(); i++) {
                list.add(imageInfo.get(i).getPicUrl());
            }
            bundle.putStringArrayList("image_urls", list);
            bundle.putInt("image_index", index);
            intent.putExtras(bundle);
            context.startActivity(intent);
        }

        @Override
        protected ImageView generateImageView(Context context) {
            return super.generateImageView(context);
        }
    };


    public static void setOnFengWenPostionClickListener(OnFengWenPostionClickListener<FengCircleDynamicBean> onFengWenPostionClickListener) {
        FengWenSearcherViewHolder.onFengWenPostionClickListener = onFengWenPostionClickListener;
    }
}
