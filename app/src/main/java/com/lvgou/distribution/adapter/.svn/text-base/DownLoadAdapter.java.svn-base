package com.lvgou.distribution.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.http.HttpHandler;
import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.BaseActivity;
import com.lvgou.distribution.activity.CertificationActivity;
import com.lvgou.distribution.activity.MyCourseActivity;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.inter.AdapterToFraImpl;
import com.lvgou.distribution.utils.ACache;
import com.lvgou.distribution.utils.DownloadInfo;
import com.lvgou.distribution.utils.DownloadManager;
import com.lvgou.distribution.utils.DownloadService;
import com.lvgou.distribution.utils.PopWindows;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.utils.ViewHolder;
import com.xdroid.common.utils.PreferenceHelper;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import cn.aigestudio.downloader.bizs.DLManager;
import cn.aigestudio.downloader.interfaces.SimpleDListener;

/**
 * Created by Administrator on 2017/3/28.
 */

public class DownLoadAdapter extends BaseAdapter {
    Context context;
    ArrayList<HashMap<String, Object>> list;
    protected ACache mcache;
    private String distributorid;
    private final DownloadManager downloadManager;

    public DownLoadAdapter(Context _context) {
        this.context = _context;
        downloadManager = new DownloadManager(context);
        mcache = ACache.get(context);
        removeItem = false;
        distributorid = PreferenceHelper.readString(context, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
    }

    public void setData(ArrayList<HashMap<String, Object>> list) {
        this.list = list;
        removeItem = false;
        isdownloading = true;
        iscanhandler = true;
        mHandler.removeCallbacks(mRunnable);
    }

    @Override
    public int getCount() {
        if (list == null) {
            return 0;
        } else {
            return list.size();
        }
    }

    private boolean removeItem = false;

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

    private boolean iscanhandler = true;
    private boolean iscandown = true;
    private boolean isdownloading = true;
    private int mposi = -1;


    private Handler mHandler = new Handler();

    /**
     * count up second
     */
    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            DownloadManager downloadManager = DownloadService.getDownloadManager(context);
            DownloadInfo downloadInfo = downloadManager.getDownLoadInfo(minfo.get("Comment").toString());
            if (downloadInfo != null) {
                if (downloadInfo.getState().toString().endsWith("SUCCESS")) {//如果下载成功
                    ArrayList<HashMap<String, Object>> finishList = (ArrayList<HashMap<String, Object>>) mcache.getAsObject("downloadfinish" + distributorid);
                    if (finishList == null) {
                        finishList = new ArrayList<HashMap<String, Object>>();
                    }
                    //下载完成移除下载队列的数据
                    if (!removeItem) {
                        if (list != null && list.size() > 0) {
                            finishList.add(list.get(mposi));
                        }
                        mcache.put("downloadfinish" + distributorid, finishList);
                        EventBus.getDefault().post("coursedownloadingfinish");
                        if (list != null && list.size() != 0) {
                            list.remove(mposi);
                            removeItem = true;
                            iscandown = false;
                            mcache.put("xiazaiduilie" + distributorid, list);
                            DownLoadAdapter.this.notifyDataSetChanged();
                        }
                    }
                    if (list == null || list.size() == 0) {
                        //如果没有正在下载的
                        adapterToFra.doSomeThing(new HashMap<String, Object>());
                    }
                    isdownloading = true;
                }

                mdown_progress.setMax((int) downloadInfo.getFileLength());
                mdown_progress.setProgress((int) downloadInfo.getProgress());
                String resulemax = "0";
                int maxkb = (int) (downloadInfo.getFileLength() / 1024);
                if (maxkb > 1024) {
                    float im = (float) (maxkb / 1024.0);
                    DecimalFormat fnum = new DecimalFormat("##0.00");
                    String dd = fnum.format(im);
                    resulemax = dd + "M";
                } else {
                    resulemax = maxkb + "kb";
                }
                String result = "0";
                int ikb = (int) (downloadInfo.getProgress() / 1024);
                if (ikb > 1024) {
                    float im = (float) (ikb / 1024.0);
                    DecimalFormat fnum = new DecimalFormat("##0.00");
                    String dd = fnum.format(im);
                    result = dd + "M";
                } else {
                    result = ikb + "kb";
                }
                mtv_size.setText(result + "/" + resulemax);

            }
            if (iscanhandler) {
                mHandler.postDelayed(mRunnable, 1000);
            }

        }
    };
    private ProgressBar mdown_progress;
    private TextView mtv_size;
    private HashMap<String, Object> minfo;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final HashMap<String, Object> info = list.get(position);
//        State=课程状态(0=审核中，1=报名中，2=审核不通过，3=进行中，4=已结束,5=停用 6=录制中)
//        String kjhjkashjss = mcache.getAsString("kjhjkashjss");
//        DownloadInfo downLoadInfo = downloadManager.getDownLoadInfo(kjhjkashjss);
        ViewHolder viewHolder = null;
        viewHolder = ViewHolder.getVH(convertView, context, R.layout.download_calss_item);
        ImageView im_picture = viewHolder.getView(R.id.im_picture, ImageView.class);//图片
        ImageView im_bg = viewHolder.getView(R.id.im_bg, ImageView.class);//图片遮罩
        im_bg.setVisibility(View.VISIBLE);
        TextView tv_title = viewHolder.getView(R.id.tv_title, TextView.class);//标题
        RelativeLayout rl_delete = viewHolder.getView(R.id.rl_delete, RelativeLayout.class);//删除
        RelativeLayout rl_download_state = viewHolder.getView(R.id.rl_download_state, RelativeLayout.class);//下载状态
        final TextView tv_download_state = viewHolder.getView(R.id.tv_download_state, TextView.class);//下载状态
        final ImageView tv_downloading = viewHolder.getView(R.id.tv_downloading, ImageView.class);//下载中或者暂停或者失败
        final TextView tv_size = viewHolder.getView(R.id.tv_size, TextView.class);//大小
        final TextView tv_size2 = viewHolder.getView(R.id.tv_size2, TextView.class);//大小
        tv_size.setVisibility(View.VISIBLE);
        tv_size2.setVisibility(View.GONE);
        final ProgressBar down_progress = viewHolder.getView(R.id.down_progress, ProgressBar.class);//大小
        down_progress.setVisibility(View.VISIBLE);
        final ProgressBar down_progress2 = viewHolder.getView(R.id.down_progress2, ProgressBar.class);//大小
        down_progress2.setVisibility(View.GONE);
        tv_title.setText(info.get("Theme").toString());
        Glide.with(context).load(Url.ROOT + info.get("Banner2").toString()).error(R.mipmap.pictures_no).into(im_picture);
        final DownloadManager downloadManager = DownloadService.getDownloadManager(context);
        final DownloadInfo downloadInfo = downloadManager.getDownLoadInfo(info.get("Comment").toString());
        if (downloadInfo != null) {
            down_progress.setMax((int) downloadInfo.getFileLength());
            down_progress.setProgress((int) downloadInfo.getProgress());

            String resulemax = "0";
            int maxkb = (int) (downloadInfo.getFileLength() / 1024);
            if (maxkb > 1024) {
                float im = (float) (maxkb / 1024.0);
                DecimalFormat fnum = new DecimalFormat("##0.00");
                String dd = fnum.format(im);
                resulemax = dd + "M";
            } else {
                resulemax = maxkb + "kb";
            }
            String result = "0";
            int ikb = (int) (downloadInfo.getProgress() / 1024);
            if (ikb > 1024) {
                float im = (float) (ikb / 1024.0);
                DecimalFormat fnum = new DecimalFormat("##0.00");
                String dd = fnum.format(im);
                result = dd + "M";
            } else {
                result = ikb + "kb";
            }

            tv_size.setText(result + "/" + resulemax);
        }

        if (info.get("isdown").toString().equals("true")) {
            //已经在下载
            tv_downloading.setBackgroundResource(R.mipmap.down_stop_icon);
            tv_download_state.setText("暂停下载");
            iscandown = true;
            if (isdownloading) {
                removeItem=false;
//                mHandler = new Handler();
                down_progress.setVisibility(View.GONE);
                down_progress2.setVisibility(View.VISIBLE);
                tv_size.setVisibility(View.GONE);
                tv_size2.setVisibility(View.VISIBLE);
                isdownloading = false;
                mposi = position;
                mdown_progress = down_progress2;
                mtv_size = tv_size2;
                minfo = info;
                mHandler.postDelayed(mRunnable, 0);
            }
        } else if (info.get("isdown").toString().equals("stop")) {
            //暂停中
            tv_downloading.setBackgroundResource(R.mipmap.downloading_icon);
            tv_download_state.setText("继续下载");
        } else if (info.get("isdown").toString().equals("false")) {
            //下载失败
            tv_downloading.setBackgroundResource(R.mipmap.download_failer_icon);
            tv_download_state.setText("下载失败");
        }


        rl_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View inflate = LayoutInflater.from(context).inflate(R.layout.my_class_alldel, null);
                TextView tv_title = (TextView) inflate.findViewById(R.id.tv_title);
                tv_title.setText("确定删除该下载吗？");
                TextView tv_sure = (TextView) inflate.findViewById(R.id.tv_sure);
                tv_sure.setText("删除");
                TextView tv_cancel = (TextView) inflate.findViewById(R.id.tv_cancel);

                final PopWindows popWindows = new PopWindows((MyCourseActivity) context, context, inflate);
                popWindows.showPopWindowBottom();
                tv_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popWindows.cleanPopAlpha();
                    }
                });
                tv_sure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (downloadInfo != null) {
                            try {
                                downloadManager.removeDownload(downloadInfo);
                                list.remove(position);
                                if (list == null || list.size() == 0) {
                                    //如果没有正在下载的
                                    adapterToFra.doSomeThing(new HashMap<String, Object>());
                                }
                                mcache.put("xiazaiduilie" + distributorid, list);
                                DownLoadAdapter.this.notifyDataSetChanged();
                            } catch (DbException e) {
                                e.printStackTrace();
                            }
                        } else {
                            list.remove(position);
                            mcache.put("xiazaiduilie" + distributorid, list);
                            DownLoadAdapter.this.notifyDataSetChanged();
                        }
                        popWindows.cleanPopAlpha();
                    }
                });


            }
        });
        rl_download_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItem = false;
                //下载中暂停或者失败点击
                if (info.get("isdown").toString().equals("true")) {
                    //正在下载
                    if (downloadInfo != null) {
                        try {
                            downloadManager.stopDownload(downloadInfo);
                            tv_downloading.setBackgroundResource(R.mipmap.downloading_icon);
                            tv_download_state.setText("继续下载");
                            list.get(position).put("isdown", "stop");
                            mcache.put("xiazaiduilie" + distributorid, list);
                            setData(list);
                            DownLoadAdapter.this.notifyDataSetChanged();
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                    }

                } else if (info.get("isdown").toString().equals("stop")) {
                    //处于暂停中
                    if (downloadInfo != null) {
                        try {
                            Log.e("kjashdkf", "======resume=====");
                            downloadManager.resumeDownload(downloadInfo, null);
                            list.get(position).put("isdown", "true");
                            mcache.put("xiazaiduilie" + distributorid, list);
                            tv_downloading.setBackgroundResource(R.mipmap.down_stop_icon);
                            tv_download_state.setText("暂停下载");
                            setData(list);
                            DownLoadAdapter.this.notifyDataSetChanged();
//                        DownLoadAdapter.this.notifyDataSetChanged();
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                    } else {
                        String SDPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/fengyouke/" + distributorid + "/";
                        String downStr = info.get("Comment").toString();
                        String name = TGmd5.getMD5(downStr);
                        if (downStr.endsWith(".mp3") || downStr.endsWith(".MP3")) {
                            name = name + ".mp3";
                        } else {
                            name = name + ".mp4";
                        }
                        try {
                            downloadManager.addNewDownload(downStr, name, SDPath + name, true, true, null);
                            list.get(position).put("isdown", "true");
                            mcache.put("xiazaiduilie" + distributorid, list);
                            tv_downloading.setBackgroundResource(R.mipmap.down_stop_icon);
                            tv_download_state.setText("暂停下载");
                            setData(list);
                            DownLoadAdapter.this.notifyDataSetChanged();
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        });

        return viewHolder.convertView;
    }

    public void showTwoDialog() {

    }

    AdapterToFraImpl adapterToFra;

    public void setAdapterToFraImpl(AdapterToFraImpl adapterToFra) {
        this.adapterToFra = adapterToFra;
    }
}