package com.lvgou.distribution.viewholder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.FamousTeacherDetialActivity;
import com.lvgou.distribution.activity.UserPersonalCenterActivity;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.entity.ImageViewEntity;
import com.lvgou.distribution.entity.TeacherSeatEntity;
import com.lvgou.distribution.inter.OnClassifyPostionClickListener;
import com.lvgou.distribution.inter.OnListItemClickListener;
import com.lvgou.distribution.utils.ImageUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.functions.holder.ListViewDataAdapter;
import com.xdroid.functions.holder.ViewHolderBase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

/**
 * Created by Snow on 2016/7/27 0027.
 */
public class TeacherSeatViewHolder extends ViewHolderBase<TeacherSeatEntity> {


    private RelativeLayout rl_item;
    private Context context;
    private ImageView img_head;
    private TextView tv_name;
    private ImageView img_icon;
    private TextView tv_student_num;
    private TextView tv_classes_num;
    private TextView tv_to_main;
    private LinearLayout hsview_layout;
    DisplayImageOptions options_head;
    DisplayImageOptions options_classes;


    private static OnClassifyPostionClickListener<TeacherSeatEntity> onClassifyPostionClickListener;


    @Override
    public View createView(LayoutInflater layoutInflater) {
        View view = layoutInflater.inflate(R.layout.item_college_teacher, null);
        context = layoutInflater.getContext();
        rl_item = (RelativeLayout) view.findViewById(R.id.rl_item);
        img_head = (ImageView) view.findViewById(R.id.img_teacher_head);
        img_icon = (ImageView) view.findViewById(R.id.img_icon);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_classes_num = (TextView) view.findViewById(R.id.tv_classes_num);
        tv_student_num = (TextView) view.findViewById(R.id.tv_student_num);
        tv_to_main = (TextView) view.findViewById(R.id.tv_to_main);
        hsview_layout = (LinearLayout) view.findViewById(R.id.hsview_layout);
        return view;

    }

    @Override
    public void showData(int position, final TeacherSeatEntity itemData) {
        options_head = new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.icon_none_bee)            // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.icon_none_bee)    // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.icon_none_bee)            // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(false)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(false)                            // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(360))    // 设置成圆角图片
                .build();
        ImageLoader.getInstance().displayImage(ImageUtils.getInstance().getPath(itemData.getId()), img_head, options_head);

        tv_name.setText(itemData.getName());
        tv_student_num.setText(itemData.getStudent_num());
        tv_classes_num.setText(itemData.getClass_num());

        try {
            JSONArray array_one = new JSONArray(itemData.getCalsses_data());
            if (array_one != null && array_one.length() > 0) {
                if (hsview_layout != null) {
                    hsview_layout.removeAllViews();
                }
                if (array_one.length() > 5) {
                    for (int i = 0; i < 5; i++) {
                        JSONObject jsonObject = array_one.getJSONObject(i);
                        final String id = jsonObject.getString("ID");
                        String img_path = jsonObject.getString("Banner1");
                        String title = jsonObject.getString("Theme");
                        String State = jsonObject.getString("State");
                        View view_grid_clasess = LayoutInflater.from(context).inflate(R.layout.item_grid_clasess, null);
                        RelativeLayout rl_item = (RelativeLayout) view_grid_clasess.findViewById(R.id.rl_item);
                        ImageView img_icon = (ImageView) view_grid_clasess.findViewById(R.id.img_icon);
                        TextView tv_title = (TextView) view_grid_clasess.findViewById(R.id.tv_production);
                        ImageView img_course_state=(ImageView)view_grid_clasess.findViewById(R.id.img_course_state);
                        tv_title.setLines(2);
                        tv_title.setEllipsize(TextUtils.TruncateAt.END);
                        options_classes = new DisplayImageOptions.Builder()
                                .showStubImage(R.mipmap.bg_none_good)            // 设置图片下载期间显示的图片
                                .showImageForEmptyUri(R.mipmap.bg_none_good)    // 设置图片Uri为空或是错误的时候显示的图片
                                .showImageOnFail(R.mipmap.bg_none_good)            // 设置图片加载或解码过程中发生错误显示的图片
                                .cacheInMemory(false)                        // 设置下载的图片是否缓存在内存中
                                .cacheOnDisc(false)                            // 设置下载的图片是否缓存在SD卡中
                                .displayer(new RoundedBitmapDisplayer(8))    // 设置成圆角图片
                                .build();
                        ImageLoader.getInstance().displayImage(Url.ROOT + img_path, img_icon, options_classes);
                        tv_title.setText(title);
                        img_course_state.setVisibility(View.GONE);
                        if("1".equals(State)){
                            img_course_state.setVisibility(View.VISIBLE);
                            img_course_state.setImageResource(R.mipmap.icon_course_apply_state);
                        }else if("3".equals(State)){
                            img_course_state.setVisibility(View.VISIBLE);
                            img_course_state.setImageResource(R.mipmap.icon_course_start_state);
                        }
                        rl_item.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Bundle bundle = new Bundle();
                                bundle.putString("id", id);
                                bundle.putString("index", "0");
                                Intent intent = new Intent(context, FamousTeacherDetialActivity.class);
                                intent.putExtras(bundle);
                                context.startActivity(intent);
                            }
                        });
                        hsview_layout.addView(view_grid_clasess);
                    }
                    View view_clasess_more = LayoutInflater.from(context).inflate(R.layout.item_teacherseat_load_more, null);
                    view_clasess_more.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (onClassifyPostionClickListener != null) {
                                onClassifyPostionClickListener.onClassifyPostionClick(itemData, 1);
                            }
                        }
                    });
                    hsview_layout.addView(view_clasess_more);
                } else if (array_one.length() > 2) {
                    for (int i = 0; i < array_one.length(); i++) {
                        JSONObject jsonObject = array_one.getJSONObject(i);
                        final String id = jsonObject.getString("ID");
                        String img_path = jsonObject.getString("Banner1");
                        String title = jsonObject.getString("Theme");
                        String State = jsonObject.getString("State");
                        View view_grid_clasess = LayoutInflater.from(context).inflate(R.layout.item_grid_clasess, null);
                        RelativeLayout rl_item = (RelativeLayout) view_grid_clasess.findViewById(R.id.rl_item);
                        ImageView img_icon = (ImageView) view_grid_clasess.findViewById(R.id.img_icon);
                        TextView tv_title = (TextView) view_grid_clasess.findViewById(R.id.tv_production);
                        ImageView img_course_state=(ImageView)view_grid_clasess.findViewById(R.id.img_course_state);
                        tv_title.setLines(2);
                        tv_title.setEllipsize(TextUtils.TruncateAt.END);
                        options_classes = new DisplayImageOptions.Builder()
                                .showStubImage(R.mipmap.bg_none_good)            // 设置图片下载期间显示的图片
                                .showImageForEmptyUri(R.mipmap.bg_none_good)    // 设置图片Uri为空或是错误的时候显示的图片
                                .showImageOnFail(R.mipmap.bg_none_good)            // 设置图片加载或解码过程中发生错误显示的图片
                                .cacheInMemory(false)                        // 设置下载的图片是否缓存在内存中
                                .cacheOnDisc(false)                            // 设置下载的图片是否缓存在SD卡中
                                .displayer(new RoundedBitmapDisplayer(8))    // 设置成圆角图片
                                .build();
                        ImageLoader.getInstance().displayImage(Url.ROOT + img_path, img_icon, options_classes);
                        tv_title.setText(title);
                        img_course_state.setVisibility(View.GONE);
                        if("1".equals(State)){
                            img_course_state.setVisibility(View.VISIBLE);
                            img_course_state.setImageResource(R.mipmap.icon_course_apply_state);
                        }else if("3".equals(State)){
                            img_course_state.setVisibility(View.VISIBLE);
                            img_course_state.setImageResource(R.mipmap.icon_course_start_state);
                        }
                        rl_item.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Bundle bundle = new Bundle();
                                bundle.putString("id", id);
                                bundle.putString("index", "0");
                                Intent intent = new Intent(context, FamousTeacherDetialActivity.class);
                                intent.putExtras(bundle);
                                context.startActivity(intent);
                            }
                        });
                        hsview_layout.addView(view_grid_clasess);
                    }
                } else {
                    for (int i = 0; i < array_one.length(); i++) {
                        JSONObject jsonObject = array_one.getJSONObject(i);
                        final String id = jsonObject.getString("ID");
                        String img_path = jsonObject.getString("Banner1");
                        String title = jsonObject.getString("Theme");
                        String State = jsonObject.getString("State");
                        View view_grid_clasess = LayoutInflater.from(context).inflate(R.layout.item_grid_clasess, null);
                        RelativeLayout rl_item = (RelativeLayout) view_grid_clasess.findViewById(R.id.rl_item);
                        ImageView img_icon = (ImageView) view_grid_clasess.findViewById(R.id.img_icon);
                        TextView tv_title = (TextView) view_grid_clasess.findViewById(R.id.tv_production);
                        ImageView img_course_state=(ImageView)view_grid_clasess.findViewById(R.id.img_course_state);
                        tv_title.setLines(2);
                        tv_title.setEllipsize(TextUtils.TruncateAt.END);
                        options_classes = new DisplayImageOptions.Builder()
                                .showStubImage(R.mipmap.bg_none_good)            // 设置图片下载期间显示的图片
                                .showImageForEmptyUri(R.mipmap.bg_none_good)    // 设置图片Uri为空或是错误的时候显示的图片
                                .showImageOnFail(R.mipmap.bg_none_good)            // 设置图片加载或解码过程中发生错误显示的图片
                                .cacheInMemory(false)                        // 设置下载的图片是否缓存在内存中
                                .cacheOnDisc(false)                            // 设置下载的图片是否缓存在SD卡中
                                .displayer(new RoundedBitmapDisplayer(8))    // 设置成圆角图片
                                .build();
                        ImageLoader.getInstance().displayImage(Url.ROOT + img_path, img_icon, options_classes);
                        tv_title.setText(title);
                        img_course_state.setVisibility(View.GONE);
                        if("1".equals(State)){
                            img_course_state.setVisibility(View.VISIBLE);
                            img_course_state.setImageResource(R.mipmap.icon_course_apply_state);
                        }else if("3".equals(State)){
                            img_course_state.setVisibility(View.VISIBLE);
                            img_course_state.setImageResource(R.mipmap.icon_course_start_state);
                        }
                        rl_item.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Bundle bundle = new Bundle();
                                bundle.putString("id", id);
                                bundle.putString("index", "0");
                                Intent intent = new Intent(context, FamousTeacherDetialActivity.class);
                                intent.putExtras(bundle);
                                context.startActivity(intent);
                            }
                        });
                        hsview_layout.addView(view_grid_clasess);
                    }
                    View view_clasess_comming_soon = LayoutInflater.from(context).inflate(R.layout.iteme_teacherseat_comming_soon, null);
                    hsview_layout.addView(view_clasess_comming_soon);
                }
            } else {
                if (hsview_layout != null) {
                    hsview_layout.removeAllViews();
                }
                View view_clasess_comming_soon = LayoutInflater.from(context).inflate(R.layout.iteme_teacherseat_comming_soon, null);
                hsview_layout.addView(view_clasess_comming_soon);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        img_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClassifyPostionClickListener != null) {
                    onClassifyPostionClickListener.onClassifyPostionClick(itemData, 1);
                }
            }
        });
        tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClassifyPostionClickListener != null) {
                    onClassifyPostionClickListener.onClassifyPostionClick(itemData, 1);
                }
            }
        });
        tv_to_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClassifyPostionClickListener != null) {
                    onClassifyPostionClickListener.onClassifyPostionClick(itemData, 1);
                }
            }
        });
    }

    public static void setOnClassifyPostionClickListener(OnClassifyPostionClickListener<TeacherSeatEntity> onClassifyPostionClickListener) {
        TeacherSeatViewHolder.onClassifyPostionClickListener = onClassifyPostionClickListener;
    }
}
