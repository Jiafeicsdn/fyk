package com.lvgou.distribution.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.MyCourseActivity;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.inter.AdapterToFraImpl;
import com.lvgou.distribution.inter.AdapterToFraImplvideo;
import com.lvgou.distribution.utils.ACache;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.utils.ViewHolder;
import com.lvgou.distribution.video.TextureVideoView;
import com.superplayer.library.SuperPlayer;
import com.xdroid.common.utils.PreferenceHelper;

import org.greenrobot.eventbus.EventBus;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/3/24.
 */

public class ListenClassAdapter extends BaseAdapter {
    Context context;
    ArrayList<HashMap<String, Object>> list;
    protected ACache mcache;
    private String distributorid = "";
    private ArrayList<HashMap<String, Object>> finishList;
    private MediaPlayer mediaPlayer;


    public ListenClassAdapter(Context _context) {
        this.context = _context;
        distributorid = PreferenceHelper.readString(context, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        mcache = ACache.get(context);
    }

    private ArrayList<HashMap<String, Object>> downloadList;

    public void setData(ArrayList<HashMap<String, Object>> list) {
        this.list = list;
        downloadList = (ArrayList<HashMap<String, Object>>) mcache.getAsObject("xiazaiduilie" + distributorid);
        if (downloadList == null) {
            downloadList = new ArrayList<HashMap<String, Object>>();
        }
        finishList = (ArrayList<HashMap<String, Object>>) mcache.getAsObject("downloadfinish" + distributorid);
        if (null == finishList) {
            finishList = new ArrayList<HashMap<String, Object>>();
        }
       /* playingList = (ArrayList<HashMap<String, Object>>) mcache.getAsObject("classplayingList" + distributorid);
        if (null == playingList) {
            playingList = new ArrayList<HashMap<String, Object>>();
        }*/
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    @Override
    public int getCount() {
        if (list == null) {
            return 0;
        } else {
            return list.size();
        }
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void stopVoice() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            list.get(mposition).put("isplay", "false");
            adapterToFravideo.doSomeThing(list.get(mposition));
            mHandler.removeCallbacks(mRunnable);
            ListenClassAdapter.this.notifyDataSetChanged();
        }
    }

    private String videoUrl = "";
    private String urlID = "";

    //    private ArrayList<HashMap<String, Object>> playingList = new ArrayList<>();
    private Handler mHandler = new Handler();

    /**
     * count up second
     */
    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (mediaPlayer != null && mposition != -1) {
                list.get(mposition).put("progress", mediaPlayer.getCurrentPosition());
                list.get(mposition).put("totalprogress", mediaPlayer.getDuration());
                mvideo_progress.setMax(mediaPlayer.getDuration());
                mvideo_progress.setProgress(mediaPlayer.getCurrentPosition());
                int musicTime = mediaPlayer.getCurrentPosition() / 1000;
                String show = musicTime / 60 + ":" + musicTime % 60;
                mtv_current_time.setVisibility(View.VISIBLE);
                mvideo_progress.setVisibility(View.VISIBLE);
                mtv_total_time.setVisibility(View.VISIBLE);
                mtv_current_time.setText(show);
                int musicTime2 = mediaPlayer.getDuration() / 1000;
                String show2 = musicTime2 / 60 + ":" + musicTime2 % 60;
                mtv_total_time.setText(show2);
                /*for (int i = 0; i < playingList.size(); i++) {
                    if (playingList.get(i).get("ID").toString().equals(list.get(mposition).get("ID").toString())) {
                        playingList.get(i).put("progress", mediaPlayer.getCurrentPosition());
                        playingList.get(i).put("totalprogress", mediaPlayer.getDuration());
                        break;
                    }
                }
                mcache.put("classplayingList", playingList);*/
                mHandler.postDelayed(mRunnable, 1000);
            }

        }
    };
    private ProgressBar mvideo_progress;
    private TextView mtv_current_time, mtv_total_time;
    private int mposition = -1;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final HashMap<String, Object> info = list.get(position);
        //State=课程状态(0=审核中，1=报名中，2=审核不通过，3=进行中，4=已结束,5=停用 6=录制中)
        ViewHolder viewHolder = null;
        viewHolder = ViewHolder.getVH(convertView, context, R.layout.listen_calss_item);
        ImageView im_picture = viewHolder.getView(R.id.im_picture, ImageView.class);
        TextView tv_title = viewHolder.getView(R.id.tv_title, TextView.class);
        LinearLayout ll_score = viewHolder.getView(R.id.ll_score, LinearLayout.class);
        ImageView im_score_one = viewHolder.getView(R.id.im_score_one, ImageView.class);
        ImageView im_score_two = viewHolder.getView(R.id.im_score_two, ImageView.class);
        ImageView im_score_three = viewHolder.getView(R.id.im_score_three, ImageView.class);
        ImageView im_score_four = viewHolder.getView(R.id.im_score_four, ImageView.class);
        ImageView im_score_five = viewHolder.getView(R.id.im_score_five, ImageView.class);
        TextView tv_score = viewHolder.getView(R.id.tv_score, TextView.class);
        TextView tv_state = viewHolder.getView(R.id.tv_state, TextView.class);
        TextView tv_download = viewHolder.getView(R.id.tv_download, TextView.class);
        TextView tv_study = viewHolder.getView(R.id.tv_study, TextView.class);
        RelativeLayout rl_more = viewHolder.getView(R.id.rl_more, RelativeLayout.class);
        RelativeLayout rl_playvideo = viewHolder.getView(R.id.rl_playvideo, RelativeLayout.class);
        rl_playvideo.setVisibility(View.GONE);
        final ImageView im_video_play = viewHolder.getView(R.id.im_video_play, ImageView.class);
        if (info.get("isplay").toString().equals("true")) {
            im_video_play.setBackgroundResource(R.mipmap.video_pause_icon);
        } else {
            im_video_play.setBackgroundResource(R.mipmap.video_play_icon);
        }

        final ImageView textureView = viewHolder.getView(R.id.videoView, ImageView.class);
        final ProgressBar video_progress = viewHolder.getView(R.id.video_progress, ProgressBar.class);
        video_progress.setVisibility(View.GONE);
        final TextView tv_current_time = viewHolder.getView(R.id.tv_current_time, TextView.class);
        final TextView tv_total_time = viewHolder.getView(R.id.tv_total_time, TextView.class);
        tv_total_time.setVisibility(View.GONE);
        tv_current_time.setVisibility(View.GONE);
        ll_score.setVisibility(View.GONE);
        tv_score.setVisibility(View.GONE);
        Glide.with(context).load(Url.ROOT + info.get("Banner2").toString()).error(R.mipmap.pictures_no).into(im_picture);
        tv_title.setText(info.get("Theme").toString());
        tv_download.setText("未下载");
        tv_download.setTextColor(Color.parseColor("#fc573c"));
        /*for (HashMap<String, Object> hashMap : playingList) {
            if (hashMap.get("ID").toString().equals(info.get("ID").toString())) {
//                int progress = Integer.parseInt(hashMap.get("progress").toString());
//                int totalprogress = Integer.parseInt(hashMap.get("totalprogress").toString());
                tv_total_time.setText(hashMap.get("totalprogress").toString());
                tv_current_time.setVisibility(View.VISIBLE);
                tv_current_time.setText(hashMap.get("progress").toString());
                tv_current_time.setVisibility(View.VISIBLE);

            }
        }*/


        for (HashMap<String, Object> stringObjectHashMap : downloadList) {
            if (stringObjectHashMap.get("Comment").toString().equals(info.get("Comment").toString())) {
                //如果在下载队列中存在
                tv_download.setText("下载中");
            }
        }
        for (HashMap<String, Object> stringObjectHashMap : finishList) {
            if (stringObjectHashMap.get("Comment").toString().equals(info.get("Comment").toString())) {
                tv_download.setText("已下载 ");
                tv_download.setTextColor(Color.parseColor("#a3a3a3"));
                rl_playvideo.setVisibility(View.VISIBLE);
            }
        }
        textureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String SDPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/fengyouke/" + distributorid + "/";
                String downStr = info.get("Comment").toString();
                String name = TGmd5.getMD5(downStr);
                if (downStr.endsWith(".mp3") || downStr.endsWith(".MP3")) {
                    name = name + ".mp3";
                } else {
                    name = name + ".mp4";
                }
                if (info.get("isplay").toString().equals("false")) {
                    EventBus.getDefault().post("openstopvoice");
                    video_progress.setVisibility(View.VISIBLE);
                    tv_current_time.setVisibility(View.VISIBLE);
                    tv_total_time.setVisibility(View.VISIBLE);
                    im_video_play.setBackgroundResource(R.mipmap.video_pause_icon);
                    list.get(position).put("isplay", "true");
                    if (mediaPlayer == null) {
                        mediaPlayer = new MediaPlayer();
                    } else {
                        if (mediaPlayer.isPlaying()) {
                            list.get(mposition).put("isplay", "false");
                            ListenClassAdapter.this.setData(list);
                            ListenClassAdapter.this.notifyDataSetChanged();
                        } else {
                            mediaPlayer.pause();
                        }

                    }
                    if (!videoUrl.equals(SDPath + name)) {
                        try {
                            mediaPlayer.reset();
                            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                            mediaPlayer.setDataSource(context, Uri.parse(SDPath + name));
                            videoUrl = SDPath + name;
                            urlID = list.get(position).get("ID").toString();
                            mediaPlayer.prepare();
                            mediaPlayer.start();
                            int musicTime = mediaPlayer.getDuration() / 1000;
                            String show = musicTime / 60 + ":" + musicTime % 60;
                            tv_total_time.setText(show);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        mediaPlayer.start();
                    }
                    mvideo_progress = video_progress;
                    mtv_current_time = tv_current_time;
                    mtv_total_time = tv_total_time;
                    mposition = position;
                    mHandler.postDelayed(mRunnable, 0);
                } else {
                    mHandler.removeCallbacks(mRunnable);
                    video_progress.setVisibility(View.GONE);
                    tv_current_time.setVisibility(View.GONE);
                    tv_total_time.setVisibility(View.GONE);
                    im_video_play.setBackgroundResource(R.mipmap.video_play_icon);
                    list.get(position).put("isplay", "false");
                    adapterToFravideo.doSomeThing(list.get(position));
                    if (mediaPlayer == null) {
                        mediaPlayer = new MediaPlayer();
                    }
                    mediaPlayer.pause();
                }
                adapterToFravideo.doSomeThing(list.get(position));
            }
        });
        //----------
        String state = info.get("State").toString();
        String tuanBi = info.get("TuanBi").toString();
        String source = info.get("Source").toString();
        //0=审核中，1=报名中，2=审核不通过，3=进行中，4=已结束,5=停用，6=录制中
        if (tuanBi.equals("0")) {
            //审核中
            tv_state.setVisibility(View.VISIBLE);
            tv_study.setVisibility(View.GONE);
            tv_state.setText("审核中");
            tv_state.setBackgroundResource(R.drawable.bg_text_selecter);
            tv_state.setTextColor(Color.parseColor("#d5aa5f"));
            tv_download.setTextColor(Color.parseColor("#a3a3a3"));
            tv_download.setText(info.get("TeacherName").toString());
        } else if (tuanBi.equals("1")) {
            //报名中
            tv_state.setVisibility(View.VISIBLE);
            tv_study.setVisibility(View.GONE);
            if (source.equals("1")) {
                tv_state.setBackgroundResource(R.drawable.bg_text_selecter);
                tv_state.setText("报名中");
                tv_state.setTextColor(Color.parseColor("#66C300"));
            } else if (source.equals("2")) {
                tv_state.setBackgroundResource(R.drawable.bg_text_selecter);
                tv_state.setText("众筹中");
                tv_state.setTextColor(Color.parseColor("#66C300"));
            }
            tv_download.setTextColor(Color.parseColor("#a3a3a3"));
            tv_download.setText(info.get("TeacherName").toString());
        } else if (tuanBi.equals("2")) {
            //审核不通过
            tv_state.setVisibility(View.VISIBLE);
            tv_study.setVisibility(View.GONE);
            tv_state.setBackgroundResource(R.drawable.bg_text_selecter);
            tv_state.setText("审核不通过");
            tv_state.setTextColor(Color.parseColor("#d5aa5f"));
            tv_download.setTextColor(Color.parseColor("#a3a3a3"));
            tv_download.setText(info.get("TeacherName").toString());
        } else if (tuanBi.equals("3")) {
            //进行中
            tv_state.setVisibility(View.VISIBLE);
            tv_study.setVisibility(View.GONE);
            tv_state.setBackgroundResource(R.drawable.bg_text_green);
            tv_state.setText("直播中");
            tv_state.setTextColor(Color.parseColor("#66C300"));
            tv_download.setTextColor(Color.parseColor("#a3a3a3"));
            tv_download.setText(info.get("TeacherName").toString());
        } else if (tuanBi.equals("4")) {
            tv_state.setVisibility(View.GONE);
            tv_study.setVisibility(View.VISIBLE);
            //已结束
            if (info.get("State").toString().equals("1")) {
                //未学习  不显示评分信息
                tv_study.setText("/未学习");
                tv_study.setTextColor(Color.parseColor("#fc573c"));
                ll_score.setVisibility(View.GONE);
                tv_score.setVisibility(View.GONE);
            } else if (info.get("State").toString().equals("2")) {
                //已学习
                tv_study.setText("/已学习");
                tv_study.setTextColor(Color.parseColor("#a3a3a3"));
                if (info.get("Grade").toString().equals("0")) {
                    //未评分
                    ll_score.setVisibility(View.GONE);
                    tv_score.setVisibility(View.VISIBLE);
                } else if (info.get("Grade").toString().equals("1")) {
                    ll_score.setVisibility(View.VISIBLE);
                    tv_score.setVisibility(View.GONE);
                    im_score_one.setBackgroundResource(R.mipmap.score_icon);
                    im_score_two.setBackgroundResource(R.mipmap.unscore_icon);
                    im_score_three.setBackgroundResource(R.mipmap.unscore_icon);
                    im_score_four.setBackgroundResource(R.mipmap.unscore_icon);
                    im_score_five.setBackgroundResource(R.mipmap.unscore_icon);
                } else if (info.get("Grade").toString().equals("2")) {
                    ll_score.setVisibility(View.VISIBLE);
                    tv_score.setVisibility(View.GONE);
                    im_score_one.setBackgroundResource(R.mipmap.score_icon);
                    im_score_two.setBackgroundResource(R.mipmap.score_icon);
                    im_score_three.setBackgroundResource(R.mipmap.unscore_icon);
                    im_score_four.setBackgroundResource(R.mipmap.unscore_icon);
                    im_score_five.setBackgroundResource(R.mipmap.unscore_icon);
                } else if (info.get("Grade").toString().equals("3")) {
                    ll_score.setVisibility(View.VISIBLE);
                    tv_score.setVisibility(View.GONE);
                    im_score_one.setBackgroundResource(R.mipmap.score_icon);
                    im_score_two.setBackgroundResource(R.mipmap.score_icon);
                    im_score_three.setBackgroundResource(R.mipmap.score_icon);
                    im_score_four.setBackgroundResource(R.mipmap.unscore_icon);
                    im_score_five.setBackgroundResource(R.mipmap.unscore_icon);
                } else if (info.get("Grade").toString().equals("4")) {
                    ll_score.setVisibility(View.VISIBLE);
                    tv_score.setVisibility(View.GONE);
                    im_score_one.setBackgroundResource(R.mipmap.score_icon);
                    im_score_two.setBackgroundResource(R.mipmap.score_icon);
                    im_score_three.setBackgroundResource(R.mipmap.score_icon);
                    im_score_four.setBackgroundResource(R.mipmap.score_icon);
                    im_score_five.setBackgroundResource(R.mipmap.unscore_icon);
                } else if (info.get("Grade").toString().equals("5")) {
                    ll_score.setVisibility(View.VISIBLE);
                    tv_score.setVisibility(View.GONE);
                    im_score_one.setBackgroundResource(R.mipmap.score_icon);
                    im_score_two.setBackgroundResource(R.mipmap.score_icon);
                    im_score_three.setBackgroundResource(R.mipmap.score_icon);
                    im_score_four.setBackgroundResource(R.mipmap.score_icon);
                    im_score_five.setBackgroundResource(R.mipmap.score_icon);
                }
            }
        } else if (tuanBi.equals("5")) {
            //停用
            tv_state.setVisibility(View.VISIBLE);
            tv_study.setVisibility(View.GONE);
            tv_state.setBackgroundResource(R.drawable.bg_text_selecter);
            tv_state.setText("停用");
            tv_state.setTextColor(Color.parseColor("#d5aa5f"));
            tv_download.setTextColor(Color.parseColor("#a3a3a3"));
            tv_download.setText(info.get("TeacherName").toString());
        } else if (tuanBi.equals("6")) {
            //录制中
            tv_state.setVisibility(View.VISIBLE);
            tv_study.setVisibility(View.GONE);
            tv_state.setBackgroundResource(R.drawable.live_text_red_norad_bg);
            tv_state.setText("录制中");
            tv_state.setTextColor(Color.parseColor("#fc4d30"));
            tv_download.setTextColor(Color.parseColor("#a3a3a3"));
            tv_download.setText(info.get("TeacherName").toString());
        }


        /*String downStr = "";
        if (null != info.get("Comment")) {
            downStr = info.get("Comment").toString();
        }*/
        rl_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterToFra.doSomeThing(info);
            }
        });
        return viewHolder.convertView;
    }

    AdapterToFraImpl adapterToFra;

    public void setAdapterToFraImpl(AdapterToFraImpl adapterToFra) {
        this.adapterToFra = adapterToFra;
    }

    AdapterToFraImplvideo adapterToFravideo;

    public void setAdapterToFraImplvodeo(AdapterToFraImplvideo adapterToFravideo) {
        this.adapterToFravideo = adapterToFravideo;
    }

    public void destroyVideo() {
        mHandler.removeCallbacks(mRunnable);
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        mediaPlayer.stop();
        mediaPlayer = null;
        /*mediaPlayer = null;
        list.get(mposition).put("isplay", "false");
        adapterToFravideo.doSomeThing(list.get(mposition));
        ListenClassAdapter.this.notifyDataSetChanged();*/
    }

    public void fragmentFresh() {
        mHandler.removeCallbacks(mRunnable);
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }
}