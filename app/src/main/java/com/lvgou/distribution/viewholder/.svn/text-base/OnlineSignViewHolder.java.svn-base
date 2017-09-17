package com.lvgou.distribution.viewholder;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Constants;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.entity.OnlineSignEntity;
import com.lvgou.distribution.inter.OnListItemClickListener;
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
 * Created by Snow on 2016/7/27 0027.
 */
public class OnlineSignViewHolder extends ViewHolderBase<OnlineSignEntity> {

    private Context context;
    private RelativeLayout rl_item;
    private TextView tv_time;
    private ImageView img_icon;
    private TextView tv_content;
    private TextView tv_dctb;
    private TextView tv_peoplr_num;
    private TextView tv_comment_num;
    private RelativeLayout rl_daojishi;
    DisplayImageOptions options;
    private long between;

    private static OnListItemClickListener<OnlineSignEntity> onlineSignEntityOnListItemClickListener;

    //处理进度条更新
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.EXECUTE_LOADING:
                    if (between == 0) {
                        closeTimer();
                    } else if (between < 0) {
                        closeTimer();
                        rl_daojishi.setVisibility(View.GONE);
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
        View view = layoutInflater.inflate(R.layout.item_signup_online, null);
        rl_item = (RelativeLayout) view.findViewById(R.id.rl_item);
        tv_time = (TextView) view.findViewById(R.id.tv_time);
        img_icon = (ImageView) view.findViewById(R.id.img_binner);
        tv_content = (TextView) view.findViewById(R.id.tv_content);
        tv_dctb = (TextView) view.findViewById(R.id.tv_tuanbi_num);
        tv_peoplr_num = (TextView) view.findViewById(R.id.tv_people_num);
        tv_comment_num = (TextView) view.findViewById(R.id.tv_comment_num);
        rl_daojishi = (RelativeLayout) view.findViewById(R.id.rl_daojishi);
        return view;
    }

    @Override
    public void showData(int position, final OnlineSignEntity itemData) {
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.daojishi_fail_bg)           // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.daojishi_fail_bg)    // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.daojishi_fail_bg)         // 设置图片加载或解码过程中发生错误显示的图片
                .build();
        ImageLoader.getInstance().displayImage(Url.ROOT + itemData.getImg_icon(), img_icon, options);
        if (itemData.getState().equals("1") || itemData.getState().equals("2")) {
            judgeTime(itemData.getTime());
            rl_daojishi.setVisibility(View.VISIBLE);
        } else {
            rl_daojishi.setVisibility(View.GONE);
        }
        tv_content.setText(itemData.getContent());
        if (Integer.parseInt(itemData.getDctb()) > 0) {
            tv_dctb.setText(itemData.getDctb() + "团币");
            tv_dctb.setTextColor(context.getResources().getColor(R.color.bg_baoming_yellow));
        } else {
            tv_dctb.setText("免费");
            tv_dctb.setTextColor(context.getResources().getColor(R.color.bg_baoming_yellow));
        }

        tv_peoplr_num.setText(itemData.getPeople_num());
        tv_comment_num.setText(itemData.getComment_num());
        rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onlineSignEntityOnListItemClickListener != null) {
                    onlineSignEntityOnListItemClickListener.onListItemClick(itemData);
                }
            }
        });
    }

    public static void setOnlineSignEntityOnListItemClickListener(OnListItemClickListener<OnlineSignEntity> onlineSignEntityOnListItemClickListener) {
        OnlineSignViewHolder.onlineSignEntityOnListItemClickListener = onlineSignEntityOnListItemClickListener;
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
