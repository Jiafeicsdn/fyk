package com.lvgou.distribution.adapter;

import android.graphics.Color;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.bean.MoveUp;
import com.lvgou.distribution.view.MyViewPage;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by Administrator on 2017/2/23.
 */

public class MyPagerAdapter extends PagerAdapter {
    private List<ImageView> imageViews;// 滑动的图片集合
    private LinearLayout title_menu;//底部菜单
    private RelativeLayout title_long_pass;//长按上方倒计时
    private MyViewPage voice_viewpager;
    private RelativeLayout title_short_pass;//单击上方
    private TextView short_cancel;//单击上方取消
    public int isClick;
    private ImageView bg_vp;
    private TextView tv_danji;
    private TextView tv_changan;
    private TextView tv_daojishi;
    private TextView tv_daojishinum;

    public MyPagerAdapter(List<ImageView> imageViews, LinearLayout title_menu, RelativeLayout title_long_pass, MyViewPage voice_viewpager, RelativeLayout title_short_pass, TextView short_cancel, ImageView bg_vp, TextView tv_danji, TextView tv_changan, TextView tv_daojishi, TextView tv_daojishinum) {
        this.imageViews = imageViews;
        this.title_menu = title_menu;
        this.title_long_pass = title_long_pass;
        this.voice_viewpager = voice_viewpager;
        this.title_short_pass = title_short_pass;
        this.short_cancel = short_cancel;
        this.bg_vp = bg_vp;
        this.tv_danji = tv_danji;
        this.tv_changan = tv_changan;
        this.tv_daojishi = tv_daojishi;
        this.tv_daojishinum = tv_daojishinum;
        isClick = 0;
    }

    public void resetClick() {
        isClick = 0;
        imageViews.get(0).setImageResource(R.mipmap.voice_not_use);
        imageViews.get(1).setImageResource(R.mipmap.voice_not_use);
        voice_viewpager.setTouchIntercept(true);
        tv_danji.setClickable(true);
        tv_changan.setClickable(true);
    }

    @Override
    public int getCount() {
        return imageViews.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        final ImageView iv = imageViews.get(position);
        iv.setImageResource(R.mipmap.voice_not_use);
        ((ViewPager) container).addView(iv);
        // 在这个方法里面设置图片的点击事件


        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == 0) {
                    EventBus.getDefault().post("teacherluyinjieshu");
                    EventBus.getDefault().post("studentluyinjieshu");
                    //单击模式
                    Log.e("khaskfdhkasdf", "------------------");
                    if (isClick == 0) {
                        //第一次点击
                        iv.setImageResource(R.mipmap.on_voice_icon);
//                        bg_vp
                        //初始化
                        bg_vp.setVisibility(View.VISIBLE);

                        Animation scaleAnimation = new ScaleAnimation(1, 1.2f, 1,
                                1.2f, Animation.RELATIVE_TO_SELF, 0.5f,
                                Animation.RELATIVE_TO_SELF, 0.5f);//设置动画时间
                        scaleAnimation.setDuration(1000);
                        scaleAnimation.setRepeatCount(-1);
                        scaleAnimation.setRepeatMode(ScaleAnimation.REVERSE);
                        bg_vp.startAnimation(scaleAnimation);
                        title_short_pass.setVisibility(View.VISIBLE);
                        title_menu.setVisibility(View.GONE);
                        EventBus.getDefault().post("oneclickvoice");
                        voice_viewpager.setTouchIntercept(false);
                        tv_danji.setClickable(false);
                        tv_changan.setClickable(false);
                       /* voice_viewpager.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return true;
                            }
                        });*/
                        isClick = 1;
                        short_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                title_menu.setVisibility(View.VISIBLE);
                                title_short_pass.setVisibility(View.GONE);
                                iv.setImageResource(R.mipmap.voice_not_use);
                                EventBus.getDefault().post("oneclickvoicecancel");
                                bg_vp.clearAnimation();
                                bg_vp.setVisibility(View.GONE);
                                isClick = 0;


                                voice_viewpager.setTouchIntercept(true);
                                tv_danji.setClickable(true);
                                tv_changan.setClickable(true);
                               /* voice_viewpager.setOnTouchListener(new View.OnTouchListener() {
                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        return false;
                                    }
                                });*/
                            }
                        });


                    } else if (isClick == 1) {
                        //暂停
                        iv.setImageResource(R.mipmap.voice_short_send);
                        title_short_pass.setVisibility(View.VISIBLE);
                        title_menu.setVisibility(View.GONE);
                        EventBus.getDefault().post("zantingclickvoice");
                        bg_vp.setVisibility(View.GONE);
                        bg_vp.clearAnimation();
                        short_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                title_menu.setVisibility(View.VISIBLE);
                                title_short_pass.setVisibility(View.GONE);
                                iv.setImageResource(R.mipmap.voice_not_use);
                                EventBus.getDefault().post("oneclickvoicecancel");
                                isClick = 0;
                                voice_viewpager.setTouchIntercept(true);
                                tv_danji.setClickable(true);
                                tv_changan.setClickable(true);
                               /* voice_viewpager.setOnTouchListener(new View.OnTouchListener() {
                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        return false;
                                    }
                                });*/
                            }
                        });
                        isClick = 2;
                    } else if (isClick == 2) {
                        //第二次点击
                        iv.setImageResource(R.mipmap.voice_not_use);
                        title_short_pass.setVisibility(View.GONE);
                        title_menu.setVisibility(View.VISIBLE);
                        EventBus.getDefault().post("twoclickvoice");
                        voice_viewpager.setTouchIntercept(true);
                        tv_danji.setClickable(true);
                        tv_changan.setClickable(true);
                        /*voice_viewpager.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return false;
                            }
                        });*/
                        isClick = 0;
                    }
//                    isClick = !isClick;

                }

            }
        });


        iv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (position == 1) {
                    //长按模式
//                    title_menu.setVisibility(View.GONE);
//                    title_long_pass.setVisibility(View.VISIBLE);
                    //让viewpager不能滑动
                    voice_viewpager.setTouchIntercept(false);
                    tv_danji.setClickable(false);
                    tv_changan.setClickable(false);

                    tv_danji.setClickable(false);
                    iv.setImageResource(R.mipmap.voice_changan);
                    bg_vp.setVisibility(View.VISIBLE);
                    Animation scaleAnimation = new ScaleAnimation(1, 1.2f, 1,
                            1.2f, Animation.RELATIVE_TO_SELF, 0.5f,
                            Animation.RELATIVE_TO_SELF, 0.5f);//设置动画时间
                    scaleAnimation.setDuration(1000);
                    scaleAnimation.setRepeatCount(-1);
                    scaleAnimation.setRepeatMode(ScaleAnimation.REVERSE);
                    bg_vp.startAnimation(scaleAnimation);

                    EventBus.getDefault().post("luyinanxia");
                    EventBus.getDefault().post("teacherluyinjieshu");
                    EventBus.getDefault().post("studentluyinjieshu");
                    iv.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            switch (event.getAction()) {
                                case MotionEvent.ACTION_DOWN:
//                                    EventBus.getDefault().post("luyinanxia");
//                                    return true;

                                case MotionEvent.ACTION_MOVE:
//
                                    if (event.getY() < (100 - iv.getMeasuredHeight())) {
                                        //上滑了，需要取消
                                        tv_daojishi.setTextColor(Color.parseColor("#fc4d30"));
                                        tv_daojishi.setText("松开取消");
                                        tv_daojishinum.setTextColor(Color.parseColor("#fc4d30"));
                                    } else {
                                        tv_daojishi.setTextColor(Color.parseColor("#d5aa5f"));
                                        tv_daojishi.setText("松开发送");
                                        tv_daojishinum.setTextColor(Color.parseColor("#d5aa5f"));
                                        EventBus.getDefault().post(new MoveUp(event.getX(), event.getY()));
                                        EventBus.getDefault().post("luyinyidong");
                                    }
                                    return false;
                                case MotionEvent.ACTION_UP:
                                    if (event.getY() < (100 - iv.getMeasuredHeight())) {
                                        EventBus.getDefault().post("changanvoicecancel");
                                    } else {
                                        EventBus.getDefault().post("luyintaiqi");
                                    }
                                    tv_daojishi.setTextColor(Color.parseColor("#d5aa5f"));
                                    tv_daojishi.setText("松开发送");
                                    tv_daojishinum.setTextColor(Color.parseColor("#d5aa5f"));
                                    //让viewpager可以滑动
                                    voice_viewpager.setTouchIntercept(true);
                                    tv_danji.setClickable(true);
                                    tv_changan.setClickable(true);
                                    iv.setImageResource(R.mipmap.voice_not_use);
                                   /* voice_viewpager.setOnTouchListener(new View.OnTouchListener() {
                                        @Override
                                        public boolean onTouch(View v, MotionEvent event) {
                                            return false;
                                        }
                                    });*/
                                    bg_vp.clearAnimation();
                                    bg_vp.setVisibility(View.GONE);
                                    return false;
                                default:
                                    return false;
                            }
                        }
                    });

                }
                return true;
            }
        });
        return iv;
    }

    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager) arg0).removeView((View) arg2);
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void startUpdate(View arg0) {

    }

    @Override
    public void finishUpdate(View arg0) {
    }

}
