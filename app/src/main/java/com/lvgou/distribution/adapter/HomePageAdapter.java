package com.lvgou.distribution.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.CirclrFengActivity;
import com.lvgou.distribution.activity.DistributorHomeActivity;
import com.lvgou.distribution.activity.ImagePagerActivity;
import com.lvgou.distribution.activity.NewDynamicDetailsActivity;
import com.lvgou.distribution.activity.TeacherHomeActivity;
import com.lvgou.distribution.activity.UserPersonalCenterActivity;
import com.lvgou.distribution.bean.FengCircleDynamicBean;
import com.lvgou.distribution.bean.FengCircleImageBean;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.emoji.FaXianParseEmojiMsgUtil;
import com.lvgou.distribution.emoji.ParseEmojiMsgUtil;
import com.lvgou.distribution.ninegridview.NineGridView;
import com.lvgou.distribution.ninegridview.NineGridViewAdapter;
import com.lvgou.distribution.presenter.BasePresenter;
import com.lvgou.distribution.presenter.DistributorHomePresenter;
import com.lvgou.distribution.presenter.FengwenSearchPresenter;
import com.lvgou.distribution.presenter.HomePagePresenter;
import com.lvgou.distribution.utils.ImageUtils;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.CircleImageView;
import com.lvgou.distribution.view.DistributorHomeView;
import com.lvgou.distribution.widget.CellLayout;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.wx.goodview.GoodView;
import com.xdroid.common.utils.PreferenceHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Snow on 2016/8/11
 */
public class HomePageAdapter extends BaseAdapter implements DistributorHomeView {
    private DistributorHomePresenter distributorHomePresenter;
    private Context context;
    private LayoutInflater inflater;
    private List<FengCircleDynamicBean> circleDynamicEntities;
    GoodView goodView;
    private String distributorid;
    private AnimationDrawable animationDrawable;
    private int indext;
    private ImageView beforeImageView;
    private List<ImageView> imageViewList;
    private BasePresenter homePagePresenter;
    private AdapterCallBack adapterCallBack;
    private Dialog dialog_del_can;// 取消，删除弹框

    public HomePageAdapter(Context context, BasePresenter homePagePresenter) {
        this.context = context;
        goodView = new GoodView(context);
        distributorHomePresenter = new DistributorHomePresenter(this);
        distributorid = PreferenceHelper.readString(context, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        inflater = LayoutInflater.from(context);
        imageViewList = new ArrayList<>();
        circleDynamicEntities = new ArrayList<>();
        this.homePagePresenter = homePagePresenter;
    }

    public void setmAdapterListener(AdapterCallBack adapterCallBack) {
        this.adapterCallBack = adapterCallBack;
    }

    public void setFengcircleData(List<FengCircleDynamicBean> circleDynamicEntities) {
        this.circleDynamicEntities.addAll(circleDynamicEntities);
    }

    public List<FengCircleDynamicBean> getFengcircleData() {
        return circleDynamicEntities;
    }

    @Override
    public int getCount() {
        return circleDynamicEntities.size();
    }

    @Override
    public FengCircleDynamicBean getItem(int position) {
        return circleDynamicEntities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_news, null);
            viewHolder.container = (LinearLayout) convertView.findViewById(R.id.container);
            viewHolder.img_head_pic = (CircleImageView) convertView.findViewById(R.id.img_head_pic);
            viewHolder.img_teacher_label = (ImageView) convertView.findViewById(R.id.img_teacher_label);
            viewHolder.txt_user_name = (TextView) convertView.findViewById(R.id.txt_user_name);
            viewHolder.txt_issue_time = (TextView) convertView.findViewById(R.id.txt_issue_time);
            viewHolder.img_concern = (TextView) convertView.findViewById(R.id.img_concern);
            viewHolder.txt_title = (TextView) convertView.findViewById(R.id.txt_title);
            viewHolder.layout_desc = (LinearLayout) convertView.findViewById(R.id.layout_desc);
            viewHolder.txt_desc = (TextView) convertView.findViewById(R.id.txt_desc);
            viewHolder.txt_praise = (TextView) convertView.findViewById(R.id.txt_praise);
            viewHolder.txt_comment = (TextView) convertView.findViewById(R.id.txt_comment);
            viewHolder.nineGrid = (NineGridView) convertView.findViewById(R.id.nineGrid);
            viewHolder.nineGrid.setVisibility(View.GONE);
            viewHolder.layout = (CellLayout) convertView.findViewById(R.id.layout);
            viewHolder.layout.setVisibility(View.VISIBLE);
            viewHolder.img_sex = (ImageView) convertView.findViewById(R.id.img_sex);
            viewHolder.tv_address = (TextView) convertView.findViewById(R.id.tv_address);
            viewHolder.categoryNames_layout = (LinearLayout) convertView.findViewById(R.id.categoryNames_layout);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (circleDynamicEntities.get(position) != null) {
            viewHolder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, NewDynamicDetailsActivity.class);
                    intent.putExtra("talkId", circleDynamicEntities.get(position).getID());
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                }
            });
            if (String.valueOf(circleDynamicEntities.get(position).getDistributorID()).equals(distributorid)) {
                viewHolder.img_concern.setVisibility(View.GONE);
            } else {
                viewHolder.img_concern.setVisibility(View.GONE);
            }
            if ("1".equals(circleDynamicEntities.get(position).getFollowed())) {
                viewHolder.img_concern.setText("已关注");
//                viewHolder.img_concern.setImageResource(R.mipmap.yiguanzhu);
            } else {
                viewHolder.img_concern.setText("+ 关注");
//                viewHolder.img_concern.setImageResource(R.mipmap.circle_add_follow);
            }
            if (circleDynamicEntities.get(position).getZaned() == 1) {
                Drawable drawable = context.getResources().getDrawable(R.mipmap.ding_select_icon);
                /// 这一步必须要做,否则不会显示.
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                viewHolder.txt_praise.setCompoundDrawables(drawable, null, null, null);
                viewHolder.txt_praise.setTextColor(Color.parseColor("#d5aa5f"));
            } else {
                Drawable drawable = context.getResources().getDrawable(R.mipmap.ding_normal_icon);
                /// 这一步必须要做,否则不会显示.
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                viewHolder.txt_praise.setCompoundDrawables(drawable, null, null, null);
                viewHolder.txt_praise.setTextColor(Color.parseColor("#a3a3a3"));
            }
            if (circleDynamicEntities.get(position).getZanCount() > 0) {
                viewHolder.txt_praise.setText(String.valueOf(circleDynamicEntities.get(position).getZanCount()));
            } else {
                viewHolder.txt_praise.setText("赞");
            }
            if (circleDynamicEntities.get(position).getCommentCount() > 0) {
                viewHolder.txt_comment.setText(String.valueOf(circleDynamicEntities.get(position).getCommentCount()));
            } else {
                viewHolder.txt_comment.setText("评论");
            }
            viewHolder.txt_user_name.setText(circleDynamicEntities.get(position).getDistributorName());

            /*if (circleDynamicEntities.get(position).getSex().equals("1")) {
                viewHolder.img_sex.setImageResource(R.mipmap.icon_man);
            } else if (circleDynamicEntities.get(position).getSex().equals("2")) {
                viewHolder.img_sex.setImageResource(R.mipmap.icon_woman);
            }*/
            if (!"null".equals(circleDynamicEntities.get(position).getCurrentLocation()) && circleDynamicEntities.get(position).getCurrentLocation() != null && circleDynamicEntities.get(position).getCurrentLocation().length() > 0) {
                viewHolder.tv_address.setVisibility(View.VISIBLE);
            } else {
                viewHolder.tv_address.setVisibility(View.GONE);
            }
            viewHolder.tv_address.setText(circleDynamicEntities.get(position).getCurrentLocation());
            //判断是否是转发
            if (circleDynamicEntities.get(position).getSourceDistributorID() > 0) {
                viewHolder.txt_title.setVisibility(View.GONE);
                int soureceLength = circleDynamicEntities.get(position).getSourceDistributorName().length();
                StringBuffer stringBuffer = new StringBuffer();
//                stringBuffer.append("@");
//                stringBuffer.append(circleDynamicEntities.get(position).getSourceDistributorName());
//                stringBuffer.append(": ");
                stringBuffer.append(circleDynamicEntities.get(position).getSourceTitle());
                stringBuffer.append(",");
                stringBuffer.append(circleDynamicEntities.get(position).getContent());
//                SpannableStringBuilder style = new SpannableStringBuilder(stringBuffer);
//                style.setSpan(new ClickableSpan() {
//                    @Override
//                    public void onClick(View widget) {
//                        Intent intent = new Intent(context, UserPersonalCenterActivity.class);
//                        intent.putExtra("distributorid", circleDynamicEntities.get(position).getSourceDistributorID());
//                        context.startActivity(intent);
//                    }
//
//                    @Override
//                    public void updateDrawState(TextPaint ds) {
//                        super.updateDrawState(ds);
//                        ds.setColor(context.getResources().getColor(R.color.bg_daoliu_yellow_two));
//                        // 去掉下划线
//                        ds.setUnderlineText(false);
//                    }
//                }, 0, soureceLength + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                viewHolder.txt_desc.setText(stringBuffer);
//                viewHolder.layout_desc.setBackgroundColor(Color.parseColor("#f2f2f2"));
                viewHolder.txt_desc.setMovementMethod(LinkMovementMethod.getInstance());
                viewHolder.txt_desc.setVisibility(View.VISIBLE);
            } else {
                viewHolder.txt_title.setVisibility(View.VISIBLE);
//                viewHolder.layout_desc.setBackgroundColor(Color.parseColor("#00000000"));
                viewHolder.txt_desc.setVisibility(View.GONE);
                if (null != circleDynamicEntities.get(position).getTopicTitle() && !circleDynamicEntities.get(position).getTopicTitle().equals("") && !circleDynamicEntities.get(position).getTopicTitle().toString().equals("null")) {
                    FaXianParseEmojiMsgUtil.getExpressionString(context, circleDynamicEntities.get(position).getID(), viewHolder.txt_title, circleDynamicEntities.get(position).getContent(), circleDynamicEntities.get(position).getTopicTitle(), circleDynamicEntities.get(position).getTopicID());
                } else {
                    ParseEmojiMsgUtil.getExpressionString(context, circleDynamicEntities.get(position).getID(), viewHolder.txt_title, circleDynamicEntities.get(position).getContent());
                }
                viewHolder.txt_title.setText(circleDynamicEntities.get(position).getContent());
            }
            viewHolder.txt_desc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, NewDynamicDetailsActivity.class);
                    intent.putExtra("talkId", circleDynamicEntities.get(position).getID());
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                }
            });
            if (circleDynamicEntities.get(position).getCreateTime() != null && circleDynamicEntities.get(position).getCreateTime().length() > 0) {
                String[] str = circleDynamicEntities.get(position).getCreateTime().split("T");
                //2016-04-22 16:32:50
                SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String date_e = dfs.format(new Date());
                String date_b = str[0] + " " + str[1];
                try {
                    Date begin = dfs.parse(date_b);
                    Date end = dfs.parse(date_e);
                    long between = (end.getTime() - begin.getTime()) / 1000;//除以1000是为了转换成秒
                    long month1 = between / (30 * 24 * 3600);
                    long day1 = between / (24 * 3600);
                    long hour1 = between / 3600;
                    long minute1 = between / 60;
                    if (minute1 == 0) {
                        viewHolder.txt_issue_time.setText("刚刚");
                    } else if (minute1 < 60) {
                        viewHolder.txt_issue_time.setText(minute1 + "分钟前");
                    } else if (hour1 < 24) {
                        viewHolder.txt_issue_time.setText(hour1 + "小时前");
                    } else if (day1 < 30) {
                        viewHolder.txt_issue_time.setText(day1 + "天前");
                    } else if (month1 < 12) {
                        viewHolder.txt_issue_time.setText(month1 + "月前");
                    } else {
                        viewHolder.txt_issue_time.setText(str[0] + " " + str[1].split(":")[0] + ":" + str[1].split(":")[1]);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            List<FengCircleImageBean> images = circleDynamicEntities.get(position).getmImgUrlList();
            /*if (images == null || images.size() < 1) {
                viewHolder.nineGrid.setVisibility(View.GONE);
            } else {
                viewHolder.nineGrid.setVisibility(View.VISIBLE);
            }*/
            /*if (viewHolder.nineGrid.getVisibility() == View.GONE && viewHolder.txt_desc.getVisibility() == View.GONE) {
                viewHolder.layout_desc.setVisibility(View.GONE);
            } else {
                viewHolder.layout_desc.setVisibility(View.VISIBLE);
            }
            viewHolder.nineGrid.setAdapter(mAdapter);*/

            /*if (images != null && images.size() == 1) {
                viewHolder.nineGrid.setSingleImageRatio(images.get(0).getWidth() * 1.0f / images.get(0).getHeight());
                viewHolder.nineGrid.setSingleImageSize(images.get(0).getWidth());
            }
            viewHolder.nineGrid.setImagesData(images);*/
            ArrayList<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();
            for (FengCircleImageBean image : images) {
                HashMap<String, Object> map1 = new HashMap<String, Object>();
                map1.put("smallPicUrl", image.getSmallPicUrl().replace(Url.ROOT,""));
                map1.put("picUrl", image.getPicUrl().replace(Url.ROOT,""));
                dataList.add(map1);
            }
            if (dataList.size() == 0) {
                viewHolder.layout.setVisibility(View.GONE);
            } else {
                viewHolder.layout.setVisibility(View.VISIBLE);
                viewHolder.layout.setData(dataList, context);
            }
            String path = ImageUtils.getInstance().getPath(String.valueOf(circleDynamicEntities.get(position).getDistributorID()));
            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.mipmap.faxian_user_head) //设置图片在下载期间显示的图片
                    .showImageForEmptyUri(R.mipmap.faxian_user_head)//设置图片Uri为空或是错误的时候显示的图片
                    .showImageOnFail(R.mipmap.faxian_user_head).build(); //设置图片加载/解码过程中错误时候显示的图片


            ImageLoader.getInstance().displayImage(path, viewHolder.img_head_pic, options);
            viewHolder.img_teacher_label.setVisibility(View.VISIBLE);
            if (circleDynamicEntities.get(position).getUserType() == 3) {
                viewHolder.img_teacher_label.setImageResource(R.mipmap.bg_tecaher_authentication);
                viewHolder.txt_user_name.setTextColor(context.getResources().getColor(R.color.text_color_ff9900));
            } else if (circleDynamicEntities.get(position).getUserType() == 2) {
                /*if (circleDynamicEntities.get(position).getIsRZ() == 1) {
                    viewHolder.img_teacher_label.setImageResource(R.mipmap.icon_certified);
                    viewHolder.txt_user_name.setTextColor(context.getResources().getColor(R.color.text_color_ff9900));
                } else {
                    viewHolder.img_teacher_label.setVisibility(View.GONE);
                    viewHolder.txt_user_name.setTextColor(context.getResources().getColor(R.color.text_color_333333));
                }*/
                viewHolder.img_teacher_label.setVisibility(View.GONE);
                viewHolder.txt_user_name.setTextColor(Color.parseColor("#7b7b7b"));
            } else if (circleDynamicEntities.get(position).getUserType() == 1) {
                viewHolder.img_teacher_label.setImageResource(R.mipmap.icon_official_certified);
                viewHolder.txt_user_name.setTextColor(context.getResources().getColor(R.color.text_color_ff9900));
            }
            viewHolder.categoryNames_layout.removeAllViews();
            if (circleDynamicEntities.get(position).getCategoryNames().size() > 0) {
                for (int i = 0; i < circleDynamicEntities.get(position).getCategoryNames().size(); i++) {
                    TextView textView = new TextView(context);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(0, 0, 20, 0);
                    textView.setLayoutParams(layoutParams);
                    textView.setPadding(14, 6, 14, 6);
                    textView.setTextSize(10);
                    textView.setTextColor(context.getResources().getColor(R.color.bg_gray_three));
                    textView.setBackgroundResource(R.drawable.bg_tag_shape);
                    textView.setText(circleDynamicEntities.get(position).getCategoryNames().get(i));
                    viewHolder.categoryNames_layout.addView(textView);
                }
            }
        }
        viewHolder.img_head_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sign2 = TGmd5.getMD5("" + distributorid + circleDynamicEntities.get(position).getDistributorID() + "");
//                ((CirclrFengActivity) context).showLoadingProgressDialog(context, "");
                distributorHomePresenter.distributorHome(distributorid, circleDynamicEntities.get(position).getDistributorID() + "", sign2);
                //个人中心
                /*if (circleDynamicEntities.get(position).getUserType()==3) {
                    //如果是讲师
                    Intent intent1 = new Intent(context, TeacherHomeActivity.class);
                    intent1.putExtra("seeDistributorId",circleDynamicEntities.get(position).getDistributorID()+"");
                    context.startActivity(intent1);
                } else {
                    //普通导游
                    Intent intent1 = new Intent(context, DistributorHomeActivity.class);
                    intent1.putExtra("seeDistributorId",circleDynamicEntities.get(position).getDistributorID()+"");
                    context.startActivity(intent1);
                }*/
               /* Intent intent = new Intent(context, UserPersonalCenterActivity.class);
                intent.putExtra("distributorid", circleDynamicEntities.get(position).getDistributorID());
                context.startActivity(intent);*/
            }
        });

        viewHolder.layout_desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewDynamicDetailsActivity.class);
                intent.putExtra("talkId", circleDynamicEntities.get(position).getID());
                intent.putExtra("position", position);
                context.startActivity(intent);
            }
        });

        //关注
        viewHolder.img_concern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterCallBack.getItemData(circleDynamicEntities.get(position));
                int friendId = circleDynamicEntities.get(position).getDistributorID();
                String sign = TGmd5.getMD5(distributorid + friendId);
                if (circleDynamicEntities.get(position).getFollowed().equals("1")) {
                    showQuitDialog(String.valueOf(friendId), sign, position);
                } else {
                    if (homePagePresenter instanceof HomePagePresenter) {
                        ((HomePagePresenter) homePagePresenter).CircleFollow(distributorid, String.valueOf(friendId), sign, position);
                    } else if (homePagePresenter instanceof FengwenSearchPresenter) {
                        ((FengwenSearchPresenter) homePagePresenter).doFollow(distributorid, String.valueOf(friendId), sign);
                    }
                }


            }
        });
        //点赞
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.txt_praise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = (TextView) v;
                adapterCallBack.getItemData(circleDynamicEntities.get(position));
                String talkId = circleDynamicEntities.get(position).getID();
                String sign = TGmd5.getMD5(distributorid + talkId);
                if (circleDynamicEntities.get(position).getZaned() == 1) {
                    if (homePagePresenter instanceof HomePagePresenter) {
                        ((HomePagePresenter) homePagePresenter).CircleunZan(distributorid, talkId, sign, position);
                    } else if (homePagePresenter instanceof FengwenSearchPresenter) {
                        ((FengwenSearchPresenter) homePagePresenter).cancleZan(distributorid, talkId, sign);
                    }
                    Drawable drawable = context.getResources().getDrawable(R.mipmap.ding_normal_icon);
                    /// 这一步必须要做,否则不会显示.
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    finalViewHolder.txt_praise.setTextColor(Color.parseColor("#a3a3a3"));
                    textView.setCompoundDrawables(drawable, null, null, null);
                    goodView.setImage(R.mipmap.ding_normal_icon);
                    goodView.show(finalViewHolder.txt_praise);
                } else {
                    if (homePagePresenter instanceof HomePagePresenter) {
                        ((HomePagePresenter) homePagePresenter).CircleZan(distributorid, talkId, sign, position);
                    } else if (homePagePresenter instanceof FengwenSearchPresenter) {
                        ((FengwenSearchPresenter) homePagePresenter).doZan(distributorid, talkId, sign);
                    }
                    Drawable drawable = context.getResources().getDrawable(R.mipmap.ding_select_icon);
                    finalViewHolder.txt_praise.setTextColor(Color.parseColor("#d5aa5f"));
                    /// 这一步必须要做,否则不会显示.
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    textView.setCompoundDrawables(drawable, null, null, null);
                    goodView.setImage(R.mipmap.ding_select_icon);
                    goodView.show(finalViewHolder.txt_praise);
                }
                /*textView.startAnimation(AnimationUtils.loadAnimation(
                        context, R.anim.dianzan_anim));*/

            }
        });
        //评论
        viewHolder.txt_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewDynamicDetailsActivity.class);
                intent.putExtra("talkId", circleDynamicEntities.get(position).getID());
                intent.putExtra("position", position);
                context.startActivity(intent);
            }
        });
        /*viewHolder.txt_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewDynamicDetailsActivity.class);
                intent.putExtra("talkId", circleDynamicEntities.get(position).getID());
                intent.putExtra("position", position);
                context.startActivity(intent);
            }
        });*/
        return convertView;
    }

    /**
     * id
     * 删除取消弹框
     */
    public void showQuitDialog(final String friendId, final String sign, final int position) {
        dialog_del_can = new Dialog(context, R.style.Mydialog);
        View view1 = View.inflate(context,
                R.layout.dialog_quit_show, null);
        Button sure = (Button) view1.findViewById(R.id.sure);
        Button cancle = (Button) view1.findViewById(R.id.cancle);
        TextView tv_title = (TextView) view1.findViewById(R.id.tv_title);
        tv_title.setText(context.getResources().getString(R.string.text_cancel_follow_hint));
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_del_can.dismiss();
                if (homePagePresenter instanceof HomePagePresenter) {
                    ((HomePagePresenter) homePagePresenter).CircleUnFollow(distributorid, friendId, sign, position);
                } else if (homePagePresenter instanceof FengwenSearchPresenter) {
                    ((FengwenSearchPresenter) homePagePresenter).cancleFollow(distributorid, friendId, sign);
                }
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_del_can.dismiss();
            }
        });
        dialog_del_can.setContentView(view1);
        dialog_del_can.show();
    }

    private NineGridViewAdapter<FengCircleImageBean> mAdapter = new NineGridViewAdapter<FengCircleImageBean>() {
        @Override
        public void onDisplayImage(Context context, ImageView imageView, int size, FengCircleImageBean imageInfo) {
            if (size > 1) {
                if (null != imageInfo.getSmallPicUrl() && !"".equals(imageInfo.getSmallPicUrl())) {
                    Glide.with(context).load(imageInfo.getSmallPicUrl())
                            .placeholder(R.mipmap.home_loading)//
                            .error(R.mipmap.home_loading)//
                            .into(imageView);
                } else {
                    Glide.with(context).load(imageInfo.getPicUrl())
                            .placeholder(R.mipmap.home_loading)//
                            .error(R.mipmap.home_loading)//
                            .into(imageView);
                }
            } else {
                Glide.with(context).load(imageInfo.getPicUrl())
                        .placeholder(R.mipmap.home_loading)//
                        .error(R.mipmap.home_loading)//
                        .into(imageView);
            }

//            DisplayImageOptions options = new DisplayImageOptions.Builder()
//                    .showImageOnLoading(R.mipmap.home_loading) //设置图片在下载期间显示的图片
//                    .showImageForEmptyUri(R.mipmap.home_loading)//设置图片Uri为空或是错误的时候显示的图片
//                    .showImageOnFail(R.mipmap.home_loading).build(); //设置图片加载/解码过程中错误时候显示的图片
//
//            ImageLoader.getInstance().displayImage(imageInfo.getUrl(), imageView, options);
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

    @Override
    public void OnDistributorHomeSuccCallBack(String state, String respanse) {
//        ((CirclrFengActivity) context).closeLoadingProgressDialog();
        try {
            JSONObject jsb = new JSONObject(respanse);
            JSONArray jsa = new JSONArray(jsb.getString("result"));
            JSONObject distributorModeljsa = jsa.getJSONObject(0);
            if (distributorModeljsa.get("UserType").toString().equals("3")) {
                //如果是讲师
                Intent intent1 = new Intent(context, TeacherHomeActivity.class);
                intent1.putExtra("seeDistributorId", distributorModeljsa.get("ID").toString());
                context.startActivity(intent1);
            } else {
                //普通导游
                Intent intent1 = new Intent(context, DistributorHomeActivity.class);
                intent1.putExtra("seeDistributorId", distributorModeljsa.get("ID").toString());
                context.startActivity(intent1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void OnDistributorHomeFialCallBack(String state, String respanse) {
//        ((CirclrFengActivity) context).closeLoadingProgressDialog();
    }

    @Override
    public void closeDistributorHomeProgress() {

    }

    /**
     * 左边布局
     */
    public class ViewHolder {
        LinearLayout container;
        CircleImageView img_head_pic;
        ImageView img_teacher_label;
        TextView txt_user_name;
        TextView txt_issue_time;
        TextView img_concern;
        TextView txt_title;
        TextView txt_desc;
        NineGridView nineGrid;
        CellLayout layout;
        LinearLayout categoryNames_layout;
        TextView txt_praise;
        TextView txt_comment;
        ImageView img_sex;
        TextView tv_address;
        LinearLayout layout_desc;

    }

    public interface AdapterCallBack {
        public void getItemData(FengCircleDynamicBean circleDynamicBean);
    }
}