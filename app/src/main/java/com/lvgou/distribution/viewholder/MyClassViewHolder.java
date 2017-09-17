package com.lvgou.distribution.viewholder;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Constants;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.entity.MyClassEntity;
import com.lvgou.distribution.view.ReportShopView;
import com.lvgou.distribution.view.ReportView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.xdroid.functions.holder.ViewHolderBase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Snow on 2016/9/23.
 */
public class MyClassViewHolder extends ViewHolderBase<MyClassEntity> {


    private Context context;
    private RelativeLayout rl_item;
    private ImageView img_icon;
    private TextView tv_name;
    private TextView tv_title;
    private ImageView img_state_icon;
    private TextView tv_1;
    private TextView tv_time;
    private TextView tv_state;
    private TextView tv_evaluate;
    private LinearLayout rl_score;
    private ImageView img_one;
    private ImageView img_two;
    private ImageView img_three;
    private ImageView img_four;
    private ImageView img_five;

    DisplayImageOptions options;

    private long between;


    //倒计时
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.EXECUTE_LOADING:
                    if (between == 0) {
                        closeTimer();
                    } else if (between < 0) {
//                        closeTimer();
                    }
                    long day1 = between / (24 * 3600);
                    long hour1 = between / 3600 - day1 * 24;
                    long minute1 = between / 60 - day1 * 24 * 60 - hour1 * 60;
                    long second = between - day1 * 24 * 60 * 60 - hour1 * 60 * 60 - minute1 * 60;
                    if (day1 > 0) {
                        tv_time.setText(day1 + "天" + hour1 + "小时" + minute1 + "分钟" + second + "秒");
                    } else if (hour1 > 0) {
                        tv_time.setText(hour1 + "小时" + minute1 + "分钟" + second + "秒");
                    } else if (minute1 > 0) {
                        tv_time.setText(minute1 + "分钟" + second + "秒");
                    } else {
                        tv_time.setText(second + "秒");
                    }
                    between = between - 1;
                    break;
                case Constants.EXECUTE_FINISH:

                    break;
            }
        }
    };


    @Override
    public View createView(LayoutInflater layoutInflater) {
        context = layoutInflater.getContext();
        View view = layoutInflater.inflate(R.layout.item_personal_classes, null);
        rl_item = (RelativeLayout) view.findViewById(R.id.rl_item);
        img_icon = (ImageView) view.findViewById(R.id.img_head);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_title = (TextView) view.findViewById(R.id.tv_production);
        img_state_icon = (ImageView) view.findViewById(R.id.img_class_icon);
        tv_1 = (TextView) view.findViewById(R.id.tv_02);
        tv_time = (TextView) view.findViewById(R.id.tv_time);
        tv_state = (TextView) view.findViewById(R.id.tv_state);
        tv_evaluate = (TextView) view.findViewById(R.id.tv_evaluate);
        rl_score = (LinearLayout) view.findViewById(R.id.ll_score);
        img_one = (ImageView) view.findViewById(R.id.img_one);
        img_two = (ImageView) view.findViewById(R.id.img_two);
        img_three = (ImageView) view.findViewById(R.id.img_three);
        img_four = (ImageView) view.findViewById(R.id.img_four);
        img_five = (ImageView) view.findViewById(R.id.img_five);
        return view;
    }


    @Override
    public void showData(int position, MyClassEntity itemData) {
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.bg_none_good)            // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.bg_none_good)    // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.bg_none_good)            // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)                            // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(8))    // 设置成圆角图片
                .build();
        ImageLoader.getInstance().displayImage(Url.ROOT + itemData.getImg_path(), img_icon, options);
        tv_name.setText(itemData.getName());
        tv_title.setText(itemData.getTitle());
        judgeTime(itemData.getTime());


    }

    /**
     * 计算倒计时时间
     *
     * @param zbTime
     */
    public void judgeTime(String zbTime) {
        String[] str = zbTime.split("T");
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date_e = dfs.format(new Date());// 当前时间
        String date_b = str[0] + " " + str[1];
        try {
            Date begin = dfs.parse(date_b);
            Date end = dfs.parse(date_e);
            between = (begin.getTime() - end.getTime()) / 1000;//除以1000是为了转换成秒

            Message message = new Message();
            message.what = 1000;
            mHandler.sendMessage(message);

            startTimer();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    // =======================倒计时模块================================//

    private int mTimerId = 120;

    private TimerTask timerTask;

    private Timer timer;


    /**
     * 开始倒计时
     */
    private void startTimer() {
        if (timerTask == null) {
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    Message msg = new Message();
                    msg.what = Constants.EXECUTE_LOADING;
                    msg.arg1 = (int) (--mTimerId);
                    mHandler.sendMessage(msg);
                }
            };
            timer = new Timer();
            // schedule(TimerTask task, long delay, long period)
            // 安排指定的任务从指定的延迟后开始进行重复的固定延迟执行。
            // task - 所要安排的任务。
            // delay - 执行任务前的延迟时间，单位是毫秒。
            // period - 执行各后续任务之间的时间间隔，单位是毫秒。
            timer.schedule(timerTask, 1000, 1000);
        }
    }

    /**
     * 结束计时
     */
    private void closeTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (timerTask != null) {
            timerTask = null;
        }
        mTimerId = 120;
        mHandler.sendEmptyMessage(Constants.EXECUTE_FINISH);
    }


}
