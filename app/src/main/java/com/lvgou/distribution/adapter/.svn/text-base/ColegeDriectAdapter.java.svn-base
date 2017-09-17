package com.lvgou.distribution.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Constants;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.entity.CollegeDriectEntity;
import com.lvgou.distribution.inter.OnClassifyPostionClickListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2016/11/18.
 */
public class ColegeDriectAdapter extends BaseAdapter {


    private Context context;
    private LayoutInflater inflater;
    private List<CollegeDriectEntity> collegeDriectEntities;
    ViewHolder viewHolder = null;

    private long between;
    private int numCount = 2;
    private static OnClassifyPostionClickListener<CollegeDriectEntity> onClassifyPostionClickListener;

    //倒计时
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.EXECUTE_LOADING:
                    TextView textView= (TextView)msg.obj;
                    between=(long) textView.getTag();
                    if (between == 0) {
                        closeTimer();
                    } else if (between < 0) {
                        closeTimer();
                    }
                    long day1 = between / (24 * 3600);
                    long hour1 = between / 3600 - day1 * 24;
                    long minute1 = between / 60 - day1 * 24 * 60 - hour1 * 60;
                    long second = between - day1 * 24 * 60 * 60 - hour1 * 60 * 60 - minute1 * 60;
                    if (day1 > 0) {
                        textView.setText(day1 + "天" + hour1 + "时" + minute1 + "分" + second + "秒");
                    } else if (hour1 > 0) {
                        textView.setText(hour1 + "时" + minute1 + "分" + second + "秒");
                    } else if (minute1 > 0) {
                        textView.setText(minute1 + "分" + second + "秒");
                    } else {
                        if (second < 0) {
                            textView.setText("0秒");
                        } else {
                            textView.setText(second + "秒");
                        }
                    }
                    between = between - 1;
                    break;
                case Constants.EXECUTE_FINISH:

                    break;
            }
        }
    };


    public ColegeDriectAdapter(Context context, List<CollegeDriectEntity> collegeDriectEntities) {
        this.context = context;
        this.collegeDriectEntities = collegeDriectEntities;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        if (collegeDriectEntities.size() > 2) {
            return numCount;
        } else {
            return collegeDriectEntities.size();
        }
    }

    @Override
    public Object getItem(int position) {
        return collegeDriectEntities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_college_driect_classes, null);
            viewHolder.rl_item = (RelativeLayout) convertView.findViewById(R.id.rl_item);
            viewHolder.img_head = (ImageView) convertView.findViewById(R.id.img_head);
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.tv_signnum = (TextView) convertView.findViewById(R.id.tv_sign_num);
            viewHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            viewHolder.tv_sign = (TextView) convertView.findViewById(R.id.tv_sign_up);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.bg_none_good)            // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.bg_none_good)    // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.bg_none_good)            // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(false)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)                            // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(15))    // 设置成圆角图片
                .build();
        ImageLoader.getInstance().displayImage(Url.ROOT + collegeDriectEntities.get(position).getImg_head(), viewHolder.img_head, options);

        viewHolder.tv_name.setText(collegeDriectEntities.get(position).getName());
        viewHolder.tv_title.setText(collegeDriectEntities.get(position).getTitle());
        viewHolder.tv_signnum.setText("已有" + collegeDriectEntities.get(position).getSign_num() + "人报名");
        judgeTime(collegeDriectEntities.get(position).getTime(),viewHolder.tv_time);
        viewHolder.rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClassifyPostionClickListener != null) {
                    onClassifyPostionClickListener.onClassifyPostionClick(collegeDriectEntities.get(position), 1);
                }
            }
        });
        viewHolder.tv_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClassifyPostionClickListener != null) {
                    onClassifyPostionClickListener.onClassifyPostionClick(collegeDriectEntities.get(position), 1);
                }
            }
        });

        viewHolder.tv_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClassifyPostionClickListener != null) {
                    onClassifyPostionClickListener.onClassifyPostionClick(collegeDriectEntities.get(position), 1);
                }
            }
        });

        return convertView;
    }


    /**
     * 计算倒计时时间
     *
     * @param zbTime
     */
    public void judgeTime(String zbTime,TextView tv_time) {
        String[] str = zbTime.split("T");
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date_e = dfs.format(new Date());// 当前时间
        String date_b = str[0] + " " + str[1];
        try {
            Date begin = dfs.parse(date_b);
            Date end = dfs.parse(date_e);
            between = (begin.getTime() - end.getTime()) / 1000;//除以1000是为了转换成秒
            tv_time.setTag(between);
            startTimer(tv_time);

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
    private void startTimer(final TextView tv_time) {
        if (timerTask == null) {
            timerTask = new TimerTask() {
                @Override
                public void run() {
//                    Message msg = new Message();
//                    msg.what = Constants.EXECUTE_LOADING;
//                    msg.arg1 = (int) (--mTimerId);
//                    msg.obj=tv_time;
//                    mHandler.sendMessage(msg);
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


    public static class ViewHolder {
        private RelativeLayout rl_item;
        private ImageView img_head;
        private TextView tv_name;
        private TextView tv_title;
        private TextView tv_signnum;
        private TextView tv_time;
        private TextView tv_sign;// 报名按钮
    }


    public static void setOnClassifyPostionClickListener(OnClassifyPostionClickListener<CollegeDriectEntity> onClassifyPostionClickListener) {
        ColegeDriectAdapter.onClassifyPostionClickListener = onClassifyPostionClickListener;
    }

    /**
     * 点击后设置Item的数量
     *
     * @param number
     */
    public void addItemNum(int number) {
        numCount = number;
    }
}
