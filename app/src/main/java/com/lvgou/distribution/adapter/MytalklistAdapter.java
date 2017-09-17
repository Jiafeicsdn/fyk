package com.lvgou.distribution.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.lvgou.distribution.utils.MyToast;import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.DynamicDetailsActivity;
import com.lvgou.distribution.activity.ImagePagerActivity;
import com.lvgou.distribution.activity.NewDynamicDetailsActivity;
import com.lvgou.distribution.activity.UserPersonalCenterActivity;
import com.lvgou.distribution.bean.FengCircleDynamicBean;
import com.lvgou.distribution.bean.FengCircleImageBean;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.ninegridview.NineGridView;
import com.lvgou.distribution.ninegridview.NineGridViewAdapter;
import com.lvgou.distribution.presenter.HomePagePresenter;
import com.lvgou.distribution.presenter.MyTaskListPresenter;
import com.lvgou.distribution.presenter.UserDynamicPresenter;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.CircleImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xdroid.common.utils.PreferenceHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Snow on 2016/8/11
 */
public class MytalklistAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<FengCircleDynamicBean> circleDynamicEntities;
    private int type = 0;
    private String distributorid;
    private List<ImageView> imageViewList;
    private UserDynamicPresenter homePagePresenter;
    private AdapterCallBack adapterCallBack;
    private FengwenDelListener fengwenDelListener;
    private Dialog dialog_del_can;// 取消，删除弹框

    public MytalklistAdapter(Context context, UserDynamicPresenter homePagePresenter) {
        this.context = context;
        distributorid = PreferenceHelper.readString(context, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        inflater = LayoutInflater.from(context);
        imageViewList = new ArrayList<>();
        circleDynamicEntities = new ArrayList<>();
        this.homePagePresenter = homePagePresenter;
    }

    public void setmAdapterListener(AdapterCallBack adapterCallBack) {
        this.adapterCallBack = adapterCallBack;
    }

    public void setFengwenDelListener(FengwenDelListener fengwenDelListener) {
        this.fengwenDelListener = fengwenDelListener;
    }

    public void setFengcircleData(List<FengCircleDynamicBean> circleDynamicEntities) {
        this.circleDynamicEntities.addAll(circleDynamicEntities);
    }

    public void setPageType(int type) {
        this.type = type;
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
            convertView = inflater.inflate(R.layout.item_mytalklist, null);
            viewHolder.txt_issue_time = (TextView) convertView.findViewById(R.id.txt_issue_time);
            viewHolder.txt_title = (TextView) convertView.findViewById(R.id.txt_title);
            viewHolder.txt_desc = (TextView) convertView.findViewById(R.id.txt_desc);
            viewHolder.layout_desc = (LinearLayout) convertView.findViewById(R.id.layout_desc);
            viewHolder.txt_praise = (TextView) convertView.findViewById(R.id.txt_praise);
            viewHolder.txt_comment = (TextView) convertView.findViewById(R.id.txt_comment);
            viewHolder.nineGrid = (NineGridView) convertView.findViewById(R.id.nineGrid);
            viewHolder.img_delete_fengwen = (ImageView) convertView.findViewById(R.id.img_delete_fengwen);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (type > 0) {
            viewHolder.img_delete_fengwen.setVisibility(View.VISIBLE);
        } else {
            viewHolder.img_delete_fengwen.setVisibility(View.GONE);
        }
        if (circleDynamicEntities.get(position) != null) {
            if (circleDynamicEntities.get(position).getZaned() == 1) {
                Drawable drawable = context.getResources().getDrawable(R.mipmap.icon_fengwen_zaned);
                /// 这一步必须要做,否则不会显示.
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                viewHolder.txt_praise.setCompoundDrawables(drawable, null, null, null);
            } else {
                Drawable drawable = context.getResources().getDrawable(R.mipmap.icon_fengwen_zan);
                /// 这一步必须要做,否则不会显示.
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                viewHolder.txt_praise.setCompoundDrawables(drawable, null, null, null);
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
            if (circleDynamicEntities.get(position).getSourceDistributorID() > 0) {
                viewHolder.txt_title.setVisibility(View.GONE);
                int soureceLength = circleDynamicEntities.get(position).getSourceDistributorName().length();
                StringBuffer stringBuffer = new StringBuffer();
//                stringBuffer.append("@");
//                stringBuffer.append(circleDynamicEntities.get(position).getSourceDistributorName());
//                stringBuffer.append(":");
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
//                        ds.setColor(context.getResources().getColor(R.color.bg_daoliu_yellow_one));
//                        // 去掉下划线
//                        ds.setUnderlineText(false);
//                    }
//                }, 0, soureceLength + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                viewHolder.txt_desc.setText(stringBuffer.toString().trim());
//                viewHolder.layout_desc.setBackgroundColor(Color.parseColor("#f2f2f2"));
                viewHolder.txt_desc.setMovementMethod(LinkMovementMethod.getInstance());
                viewHolder.txt_desc.setVisibility(View.VISIBLE);
            } else {
//                viewHolder.layout_desc.setBackgroundColor(Color.parseColor("#00000000"));
                viewHolder.txt_desc.setVisibility(View.GONE);
                viewHolder.txt_title.setText(circleDynamicEntities.get(position).getTitle());
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
                    long month1= between / (30*24 * 3600);
                    long day1 = between / (24 * 3600);
                    long hour1 = between / 3600;
                    long minute1 = between / 60;
                    if(minute1==0){
                        viewHolder.txt_issue_time.setText("刚刚");
                    }else
                    if (minute1 < 60) {
                        viewHolder.txt_issue_time.setText(minute1 + "分钟前");
                    } else if (hour1 < 24) {
                        viewHolder.txt_issue_time.setText(hour1 + "小时前");
                    } else if (day1 < 30) {
                        viewHolder.txt_issue_time.setText(day1 + "天前");
                    } else if (month1<12){
                        viewHolder.txt_issue_time.setText(month1 + "月前");
                    }else {
                        viewHolder.txt_issue_time.setText(str[0] + " " + str[1].split(":")[0] + ":" + str[1].split(":")[1]);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            List<FengCircleImageBean> images = circleDynamicEntities.get(position).getmImgUrlList();
            if (images == null || images.size() < 1) {
                viewHolder.nineGrid.setVisibility(View.GONE);
            } else {
                viewHolder.nineGrid.setVisibility(View.VISIBLE);
            }
            if(viewHolder.nineGrid.getVisibility()==View.GONE&&viewHolder.txt_desc.getVisibility()==View.GONE){
                viewHolder.layout_desc.setVisibility(View.GONE);
            }else{
                viewHolder.layout_desc.setVisibility(View.VISIBLE);
            }
            viewHolder.nineGrid.setAdapter(mAdapter);

            if (images != null && images.size() == 1) {
                viewHolder.nineGrid.setSingleImageRatio(images.get(0).getWidth() * 1.0f / images.get(0).getHeight());
            }
            viewHolder.nineGrid.setImagesData(images);

        }
        //点赞
        viewHolder.txt_praise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView=(TextView)v;
                adapterCallBack.getItemData(circleDynamicEntities.get(position));
                String talkId = circleDynamicEntities.get(position).getID();
                String sign = TGmd5.getMD5(distributorid + talkId);
                if (circleDynamicEntities.get(position).getZaned() == 1) {
                    Drawable drawable = context.getResources().getDrawable(R.mipmap.icon_fengwen_zan);
                    /// 这一步必须要做,否则不会显示.
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    textView.setCompoundDrawables(drawable, null, null, null);
                    homePagePresenter.CircleunZan(distributorid, talkId, sign, position);
                } else {
                    Drawable drawable = context.getResources().getDrawable(R.mipmap.icon_fengwen_zaned);
                    /// 这一步必须要做,否则不会显示.
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    textView.setCompoundDrawables(drawable, null, null, null);
                    homePagePresenter.CircleZan(distributorid, talkId, sign, position);
                }
                textView.startAnimation(AnimationUtils.loadAnimation(
                        context, R.anim.dianzan_anim));
            }
        });
        //评论
        viewHolder.txt_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewDynamicDetailsActivity.class);
                intent.putExtra("talkId", circleDynamicEntities.get(position).getID());
                intent.putExtra("position", position);
                ((Activity) context).startActivityForResult(intent, 0);
            }
        });
        viewHolder.txt_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewDynamicDetailsActivity.class);
                intent.putExtra("talkId", circleDynamicEntities.get(position).getID());
                intent.putExtra("position", position);
                ((Activity) context).startActivityForResult(intent, 0);
            }
        });
        viewHolder.img_delete_fengwen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showQuitDialog(position);
            }
        });
        return convertView;
    }

    private NineGridViewAdapter<FengCircleImageBean> mAdapter = new NineGridViewAdapter<FengCircleImageBean>() {
        @Override
        public void onDisplayImage(Context context, ImageView imageView,int size, FengCircleImageBean imageInfo) {
            if(size>1){
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
            }else{
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

    /**
     * 左边布局
     */
    public class ViewHolder {
        TextView txt_issue_time;
        TextView txt_title;
        TextView txt_desc;
        NineGridView nineGrid;
        TextView txt_praise;
        TextView txt_comment;
        LinearLayout layout_desc;
        ImageView img_delete_fengwen;
    }

    public interface AdapterCallBack {
        public void getItemData(FengCircleDynamicBean circleDynamicBean);
    }

    /**
     * id
     * 删除取消弹框
     */
    public void showQuitDialog(final int position) {
        dialog_del_can = new Dialog(context, R.style.Mydialog);
        View view1 = View.inflate(context,
                R.layout.dialog_quit_show, null);
        Button sure = (Button) view1.findViewById(R.id.sure);
        Button cancle = (Button) view1.findViewById(R.id.cancle);
        TextView tv_title = (TextView) view1.findViewById(R.id.tv_title);
        tv_title.setText("确定删除这条动态吗?");
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_del_can.dismiss();
                fengwenDelListener.getFengwenId(circleDynamicEntities.get(position).getID());
                String del_sign = TGmd5.getMD5(distributorid + circleDynamicEntities.get(position).getID());
                homePagePresenter.talkdel(distributorid, circleDynamicEntities.get(position).getID(), del_sign);
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

    public interface FengwenDelListener {
        public void getFengwenId(String fengwenId);
    }
}