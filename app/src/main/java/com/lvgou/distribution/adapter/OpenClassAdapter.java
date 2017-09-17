package com.lvgou.distribution.adapter;

import android.content.Context;
import android.graphics.Color;
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
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.inter.AdapterToFraImpl;
import com.lvgou.distribution.inter.AdapterToFraImplvideo;
import com.lvgou.distribution.utils.ACache;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.utils.ViewHolder;
import com.xdroid.common.utils.PreferenceHelper;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/3/24.
 */

public class OpenClassAdapter extends BaseAdapter {
    Context context;
    ArrayList<HashMap<String, Object>> list;
    protected ACache mcache;
    private String distributorid = "";
    private MediaPlayer mediaPlayer;

    public OpenClassAdapter(Context _context) {
        this.context = _context;
        distributorid = PreferenceHelper.readString(context, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        mcache = ACache.get(context);
    }

    private ArrayList<HashMap<String, Object>> finishList;
    private ArrayList<HashMap<String, Object>> downloadList;

    /*public void deletePlaying(HashMap<String, Object> delinfo){
        if (list.get(mposition).get("Content").toString().equals(delinfo.get("Content"))){
            destroyVideo();
        }
    }*/

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
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    public void destroyVideo() {
        mHandler.removeCallbacks(mRunnable);
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        mediaPlayer.stop();
    }

    public void stopVoice() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            list.get(mposition).put("isplay", "false");
            adapterToFravideo.doSomeThing(list.get(mposition));
            mHandler.removeCallbacks(mRunnable);
            OpenClassAdapter.this.notifyDataSetChanged();
        }
    }

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

    private String videoUrl = "";
    private String urlID = "";

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final HashMap<String, Object> info = list.get(position);
//        State=课程状态(0=审核中，1=报名中，2=审核不通过，3=进行中，4=已结束,5=停用 6=录制中)
        ViewHolder viewHolder = null;
        viewHolder = ViewHolder.getVH(convertView, context, R.layout.open_calss_item);
        ImageView im_picture = viewHolder.getView(R.id.im_picture, ImageView.class);//图片
        TextView tv_title = viewHolder.getView(R.id.tv_title, TextView.class);//标题
        TextView tv_state = viewHolder.getView(R.id.tv_state, TextView.class);//状态
        TextView tv_baoming = viewHolder.getView(R.id.tv_baoming, TextView.class);//报名
        TextView tv_fubi = viewHolder.getView(R.id.tv_fubi, TextView.class);//付币
        TextView tv_price = viewHolder.getView(R.id.tv_price, TextView.class);//价格
        RelativeLayout rl_more = viewHolder.getView(R.id.rl_more, RelativeLayout.class);//更多
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
        tv_current_time.setVisibility(View.GONE);
        tv_total_time.setVisibility(View.GONE);
        tv_title.setText(info.get("Theme").toString());
        tv_fubi.setText(info.get("People_Look").toString() + "人付币");
        String cKTuanBi = info.get("CKTuanBi").toString();
        String bmTuanBi = info.get("BMTuanBi").toString();
        String type = info.get("Type").toString();
        String people_Apply = info.get("People_Apply").toString();
        String People_Min = info.get("People_Min").toString();
        String hits = info.get("Hits").toString();
        tv_price.setVisibility(View.VISIBLE);
        if (info.get("State").toString().equals("0")) {
            //审核中
//            tv_price.setVisibility(View.GONE);
            tv_state.setText("审核中");
            tv_state.setTextColor(Color.parseColor("#fc4d30"));
            if (bmTuanBi.equals("0")) {
                tv_price.setText("免费");
            } else {
                tv_price.setText(bmTuanBi + "币");
            }
            if (type.equals("2")) {
                //众筹中
                tv_baoming.setText(people_Apply + "/" + People_Min + "人起");
            } else {
                tv_baoming.setText(people_Apply + "人报名");
            }
        } else if (info.get("State").toString().equals("1")) {
            //报名中
            tv_state.setText("报名中");
            tv_state.setTextColor(Color.parseColor("#ff9900"));
            if (bmTuanBi.equals("0")) {
                tv_price.setText("免费");
            } else {
                tv_price.setText(bmTuanBi + "币");
            }
            if (type.equals("2")) {
                //众筹中
                tv_baoming.setText(people_Apply + "/" + People_Min + "人起");
            } else {
                tv_baoming.setText(people_Apply + "人报名");
            }
        } else if (info.get("State").toString().equals("2")) {
            //审核不通过
            tv_state.setText("审核不通过");
            tv_state.setTextColor(Color.parseColor("#777777"));
            if (cKTuanBi.equals("0")) {
                tv_price.setText("免费");
            } else {
                tv_price.setText(cKTuanBi + "币");
            }
            tv_baoming.setText(people_Apply + "人报名");
        } else if (info.get("State").toString().equals("3")) {
            //进行中
            tv_state.setText("直播中");
            tv_state.setTextColor(Color.parseColor("#66c300"));
            if (bmTuanBi.equals("0")) {
                tv_price.setText("免费");
            } else {
                tv_price.setText(bmTuanBi + "币");
            }
            if (type.equals("2")) {
                //众筹中
                tv_baoming.setText(people_Apply + "/" + People_Min + "人起");
            } else {
                tv_baoming.setText(people_Apply + "人报名");
            }
        } else if (info.get("State").toString().equals("4")) {
            //已结束
            tv_state.setText("已结束");
            tv_state.setTextColor(Color.parseColor("#999999"));
            tv_fubi.setText(info.get("People_Look").toString() + "人付币");
            if (cKTuanBi.equals("0")) {
                tv_price.setText("免费");
            } else {
                tv_price.setText(cKTuanBi + "币");
            }
            tv_baoming.setText(hits + "人次");
        } else if (info.get("State").toString().equals("5")) {
            //停用
            tv_price.setVisibility(View.GONE);
            tv_state.setText("已停用");
            tv_state.setTextColor(Color.parseColor("#999999"));
        } else if (info.get("State").toString().equals("6")) {
            //录制中
            tv_state.setText("录制中");
            tv_state.setTextColor(Color.parseColor("#ff9900"));
            tv_fubi.setText(info.get("People_Look").toString() + "人付币");
            tv_baoming.setText(hits + "人次");
            if (bmTuanBi.equals("0")) {
                tv_price.setText("免费");
            } else {
                tv_price.setText(bmTuanBi + "币");
            }
        }

        Glide.with(context).load(Url.ROOT + info.get("Banner1").toString()).error(R.mipmap.pictures_no).into(im_picture);

        for (HashMap<String, Object> stringObjectHashMap : finishList) {
            if (stringObjectHashMap.get("Comment").toString().equals(info.get("Content").toString())) {
                /*tv_download.setText("已下载 ");
                tv_download.setTextColor(Color.parseColor("#a3a3a3"));*/
                rl_playvideo.setVisibility(View.VISIBLE);
            }
        }
        textureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String SDPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/fengyouke/" + distributorid + "/";
                String downStr = info.get("Content").toString();
                String name = TGmd5.getMD5(downStr);
                if (downStr.endsWith(".mp3") || downStr.endsWith(".MP3")) {
                    name = name + ".mp3";
                } else {
                    name = name + ".mp4";
                }
                if (info.get("isplay").toString().equals("false")) {
                    EventBus.getDefault().post("listenstopvoice");
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
                            OpenClassAdapter.this.setData(list);
                            OpenClassAdapter.this.notifyDataSetChanged();
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
        //----
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
    public void fragmentFresh() {
        mHandler.removeCallbacks(mRunnable);
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }
}