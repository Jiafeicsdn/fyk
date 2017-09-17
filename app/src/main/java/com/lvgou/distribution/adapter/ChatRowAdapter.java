package com.lvgou.distribution.adapter;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.ImagePagerActivity;
import com.lvgou.distribution.activity.LiveChatActivity;
import com.lvgou.distribution.activity.VideoPlayActivity;
import com.lvgou.distribution.bean.GroupMessageExtData;
import com.lvgou.distribution.bean.NickNameBean;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.emoji.ParseEmojiMsgUtil;
import com.lvgou.distribution.utils.ACache;
import com.lvgou.distribution.utils.AESUtils;
import com.lvgou.distribution.utils.ImageUtils;
import com.lvgou.distribution.utils.PathUtil;
import com.lvgou.distribution.view.CircleImageView;
import com.lvgou.distribution.view.CircleProgress;
import com.lvgou.distribution.view.PowerImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xdroid.common.utils.PreferenceHelper;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/6/1.
 */

public class ChatRowAdapter extends BaseAdapter {
    protected ACache mcache;
    Context context;
    private LayoutInflater inflater;
    private List<GroupMessageExtData> chatRowMessageEntities;
    private String type;//类型，判断是讲师还是所有
    private final int TYPE_COUNT = 2;//item类型的总数
    private final int TYPE_LEFT = 0;// 左边显示
    private final int TYPE_RIGHT = 1;// 右边显示
    private String distributorid;
    private ImageView imageView2;
    private ImageView imageView1;
    private ImageView imageView3;
    private ArrayList<String> array = new ArrayList<String>();
    private MediaPlayer mediaPlayer;
    private AnimationDrawable animationDrawable;
    private int indext;
    private ImageView beforeImageView;
    private ImageView beforImageViewRight;
    private boolean isPlayVoice = false;
    private boolean isOnePlayVoice = false;
    private int voicepos = 0;
    private String chatId = "";
    private String teacherDistributorID;

    public ChatRowAdapter(Context _context, String type,String teacherDistributorID) {
        this.context = _context;
        distributorid = PreferenceHelper.readString(context, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
        inflater = LayoutInflater.from(context);
        mediaPlayer = new MediaPlayer();
        mcache = ACache.get(context);
        isPlayVoice = false;
        isOnePlayVoice = false;
        voicepos = 0;
        this.type = type;
        this.teacherDistributorID=teacherDistributorID;
    }

    public void setData(List<GroupMessageExtData> chatRowMessageEntities) {
        this.chatRowMessageEntities = chatRowMessageEntities;
    }
    public List<GroupMessageExtData> getData() {
        if (chatRowMessageEntities==null){
            chatRowMessageEntities=new ArrayList<>();
        }
        return chatRowMessageEntities;
    }

    private Map<String, NickNameBean> hashMap;

    public void setGroupMemberList(Map<String, NickNameBean> map) {
        //获取聊天室用户信息
        this.hashMap = map;
        notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        if (chatRowMessageEntities == null) {
            return 0;
        } else {
            return chatRowMessageEntities.size();
        }
    }

    @Override
    public GroupMessageExtData getItem(int position) {
        return chatRowMessageEntities.get(position);
    }

    /**
     * 判断 item 左边还是右边显示
     * 注意： getItemViewType 返回值不能大于等于 getViewTypeCount 否则会出现数组越界的错误
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        String si = chatRowMessageEntities.get(position).getSI();
        try {
            String si_id = AESUtils.Decrypt(si);
            if ("teacher".equals(type)||si_id.equals(teacherDistributorID)) {
                return TYPE_LEFT;
            }
            if (si_id.equals(distributorid)) {
                return TYPE_RIGHT;
            } else {
                return TYPE_LEFT;
            }

        } catch (Exception e) {

        }
        return 0;
    }
    @Override
    public int getViewTypeCount() {
        return TYPE_COUNT;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    ViewHolder viewHolder = null;
    ViewHolderRight viewHolderRight = null;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        int currentType = getItemViewType(position);
        if (convertView == null) {
            switch (currentType) {
                case TYPE_LEFT:
                    viewHolder = new ViewHolder();
                    convertView = inflater.inflate(R.layout.chat_left_row, parent, false);
                    viewHolder.progressBar = (ProgressBar) convertView.findViewById(R.id.progressbar);
                    viewHolder.tv_text = (TextView) convertView.findViewById(R.id.tv_text);
                    viewHolder.tv_teacher = (TextView) convertView.findViewById(R.id.tv_teacher);
                    viewHolder.rl_dashang_text_left = (RelativeLayout) convertView.findViewById(R.id.rl_dashang_text_left);
                    viewHolder.tv_dashang_name_left = (TextView) convertView.findViewById(R.id.tv_dashang_name_left);
                    viewHolder.tv_dashang_number_left = (TextView) convertView.findViewById(R.id.tv_dashang_number_left);
                    viewHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_timestamp);
                    viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                    viewHolder.tv_name.setVisibility(View.VISIBLE);
                    viewHolder.img_head = (CircleImageView) convertView.findViewById(R.id.img_head);
                    viewHolder.img_head.setVisibility(View.VISIBLE);
                    viewHolder.pic_frmlayout = (FrameLayout) convertView.findViewById(R.id.pic_frmlayout);
                    viewHolder.img_picature = (PowerImageView) convertView.findViewById(R.id.img_picature);
                    viewHolder.pic_circleProgress = (CircleProgress) convertView.findViewById(R.id.pic_circleProgress);
                    viewHolder.img_video_cover = (ImageView) convertView.findViewById(R.id.img_video_cover);
                    viewHolder.rl_backround = (LinearLayout) convertView.findViewById(R.id.rl_backround);
                    viewHolder.img_voice = (ImageView) convertView.findViewById(R.id.img_voice);
                    viewHolder.img_unread = (ImageView) convertView.findViewById(R.id.img_unread);
                    viewHolder.txt_voice_length = (TextView) convertView.findViewById(R.id.txt_voice_length);
                    viewHolder.fm_video = (FrameLayout) convertView.findViewById(R.id.fm_video);
                    viewHolder.img_video_play = (ImageView) convertView.findViewById(R.id.img_video_play);
                    viewHolder.circleProgress = (CircleProgress) convertView.findViewById(R.id.circleProgress);
                    viewHolder.voice_lin_layout = (LinearLayout) convertView.findViewById(R.id.voice_lin_layout);
                    viewHolder.voice_layout_bg = (LinearLayout) convertView.findViewById(R.id.voice_layout_bg);
                    viewHolder.img_voice2 = (ImageView) convertView.findViewById(R.id.img_voice2);
                    viewHolder.rl_appreciates = (RelativeLayout) convertView.findViewById(R.id.rl_appreciates);
                    convertView.setTag(viewHolder);
                    break;
                case TYPE_RIGHT:
                    viewHolderRight = new ViewHolderRight();
                    convertView = inflater.inflate(R.layout.chat_right_row, parent, false);
                    viewHolderRight.tv_text_right = (TextView) convertView.findViewById(R.id.tv_text);
                    viewHolderRight.tv_teacher=(TextView) convertView.findViewById(R.id.tv_teacher);
                    viewHolderRight.rl_dashang_text_right = (RelativeLayout) convertView.findViewById(R.id.rl_dashang_text_right);
                    viewHolderRight.tv_dashang_name_right = (TextView) convertView.findViewById(R.id.tv_dashang_name_right);
                    viewHolderRight.tv_dashang_number_right = (TextView) convertView.findViewById(R.id.tv_dashang_number_right);
                    viewHolderRight.tv_time_right = (TextView) convertView.findViewById(R.id.tv_timestamp);
                    viewHolderRight.tv_name_right = (TextView) convertView.findViewById(R.id.tv_name);
                    viewHolderRight.tv_name_right.setVisibility(View.VISIBLE);
                    viewHolderRight.img_head_right = (CircleImageView) convertView.findViewById(R.id.img_head);
                    viewHolderRight.img_head_right.setVisibility(View.VISIBLE);
                    viewHolderRight.img_video_cover_right = (ImageView) convertView.findViewById(R.id.img_video_cover);
                    viewHolderRight.rl_backround_right = (LinearLayout) convertView.findViewById(R.id.rl_backround);
                    viewHolderRight.img_voice_right = (ImageView) convertView.findViewById(R.id.img_voice);
                    viewHolderRight.txt_voice_length_right = (TextView) convertView.findViewById(R.id.txt_voice_length);
                    viewHolderRight.img_unread_right = (ImageView) convertView.findViewById(R.id.img_unread);
                    viewHolderRight.fm_video_right = (FrameLayout) convertView.findViewById(R.id.fm_video);
                    viewHolderRight.img_picature_right = (PowerImageView) convertView.findViewById(R.id.img_picature);
                    viewHolderRight.circleProgress_right = (CircleProgress) convertView.findViewById(R.id.circleProgress);
                    viewHolderRight.img_video_play_right = (ImageView) convertView.findViewById(R.id.img_video_play);
                    viewHolderRight.pic_circleProgress = (CircleProgress) convertView.findViewById(R.id.pic_circleProgress);
                    viewHolderRight.pic_frmlayout = (FrameLayout) convertView.findViewById(R.id.pic_frmlayout);
                    viewHolderRight.voice_lin_layout_right = (LinearLayout) convertView.findViewById(R.id.voice_lin_layout);
                    convertView.setTag(viewHolderRight);
                    break;
                default:
                    break;
            }
        } else {
            switch (currentType) {
                case TYPE_LEFT:
                    viewHolder = (ViewHolder) convertView.getTag();
                    break;
                case TYPE_RIGHT:
                    viewHolderRight = (ViewHolderRight) convertView.getTag();
                    break;
            }
        }
        switch (currentType) {
            case TYPE_LEFT:
                viewHolder.img_head.setVisibility(View.VISIBLE);
                viewHolder.tv_name.setVisibility(View.VISIBLE);
                viewHolder.tv_name.setText("导游");
                if (chatRowMessageEntities != null) {
                    try {
                        String left_uid = AESUtils.Decrypt(chatRowMessageEntities.get(position).getSI());
                        viewHolder.img_head.setTag(left_uid);

                        //设置左边名字
                        viewHolder.img_head.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String uid = String.valueOf(v.getTag());
                                //头像点击
                                ((LiveChatActivity)context).showHeaderDialog(uid);
                            }
                        });
                        String path = ImageUtils.getInstance().getPath2(left_uid);
                        ImageLoader.getInstance().displayImage(path, viewHolder.img_head);
                        if (hashMap != null && hashMap.get(left_uid) != null) {
                            viewHolder.tv_name.setText(hashMap.get(left_uid).getRealName());
                            if (teacherDistributorID.equals(hashMap.get(left_uid).getID()+"")){
                                //如果是讲师
                                viewHolder.rl_appreciates.setVisibility(View.VISIBLE);
                                viewHolder.tv_teacher.setVisibility(View.VISIBLE);
                                viewHolder.tv_text.setBackgroundResource(R.drawable.bg_chat_teacher_radius);
                                viewHolder.pic_frmlayout.setBackgroundResource(R.drawable.bg_chat_teacher_radius);
                                viewHolder.fm_video.setBackgroundResource(R.drawable.bg_chat_teacher_radius);
                                viewHolder.voice_lin_layout.setBackgroundResource(R.drawable.bg_chat_teacher_radius);
                            }else {
                                viewHolder.rl_appreciates.setVisibility(View.GONE);
                                viewHolder.tv_text.setBackgroundResource(R.drawable.bg_chat_radius);
                                viewHolder.pic_frmlayout.setBackgroundResource(R.drawable.bg_chat_radius);
                                viewHolder.fm_video.setBackgroundResource(R.drawable.bg_chat_radius);
                                viewHolder.voice_lin_layout.setBackgroundResource(R.drawable.bg_chat_radius);
                                viewHolder.tv_teacher.setVisibility(View.GONE);
                            }
                        } /*else {
                            if (listnerNickNameCallback != null) {
                                listnerNickNameCallback.getNickName(left_uid);
                            }
                        }*/

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    viewHolder.rl_dashang_text_left.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String str = chatRowMessageEntities.get(position).getC().replaceFirst("7f3cbcc68541d35f72feb0e4914e9b82", "");
                            ((LiveChatActivity)context).showDaShangDetailDialog(str,chatRowMessageEntities.get(position).getSI());
                        }
                    });
                    viewHolder.rl_appreciates.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //头像下面的打赏点击
                            ((LiveChatActivity)context).showDaShangDialog();
                        }
                    });
                    if (chatRowMessageEntities.get(position).isSendSuccess()) {
                        viewHolder.progressBar.setVisibility(View.GONE);
                    } else {
                        viewHolder.progressBar.setVisibility(View.VISIBLE);
                    }
                    String ct = chatRowMessageEntities.get(position).getCT();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    try {
                        Calendar c = Calendar.getInstance();
                        c.setTime(simpleDateFormat.parse(ct));
                        long millionSeconds1 = 0;
                        long millionSeconds = c.getTimeInMillis();
                        if (position > 0) {
                            String ct1 = chatRowMessageEntities.get(position - 1).getCT();
                            c.setTime(simpleDateFormat.parse(ct1));
                            millionSeconds1 = c.getTimeInMillis();
                        }
                        if (millionSeconds - millionSeconds1 > 60 * 1000) {
                            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("HH:mm");
                            viewHolder.tv_time.setText(simpleDateFormat2.format(millionSeconds));
                            viewHolder.tv_time.setVisibility(View.VISIBLE);
                        } else if (millionSeconds - millionSeconds1 > 60 * 1000 * 60 * 24) {
                            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("HH:mm");
                            viewHolder.tv_time.setText("昨天" + simpleDateFormat2.format(millionSeconds));
                            viewHolder.tv_time.setVisibility(View.VISIBLE);
                        } else if (millionSeconds - millionSeconds1 > 60 * 1000 * 60 * 24 * 2) {
                            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
                            viewHolder.tv_time.setText(simpleDateFormat2.format(millionSeconds));
                            viewHolder.tv_time.setVisibility(View.VISIBLE);
                        } else {
                            viewHolder.tv_time.setVisibility(View.GONE);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    if (chatRowMessageEntities != null) {
                        switch (chatRowMessageEntities.get(position).getMT()) {
                            case 0://文字
                                viewHolder.tv_text.setVisibility(View.VISIBLE);
                                viewHolder.pic_frmlayout.setVisibility(View.GONE);
                                viewHolder.img_picature.setVisibility(View.GONE);
                                viewHolder.img_voice.setVisibility(View.GONE);
                                viewHolder.fm_video.setVisibility(View.GONE);
                                viewHolder.voice_lin_layout.setVisibility(View.GONE);
                                List<String> spiltList = zhengzeSplit(chatRowMessageEntities.get(position).getC());
                                if (chatRowMessageEntities.get(position).getC().startsWith("7f3cbcc68541d35f72feb0e4914e9b82")) {
                                    viewHolder.img_head.setVisibility(View.GONE);
                                    viewHolder.tv_name.setVisibility(View.GONE);
                                    final String str = chatRowMessageEntities.get(position).getC().replaceFirst("7f3cbcc68541d35f72feb0e4914e9b82", "");
                                    viewHolder.rl_dashang_text_left.setVisibility(View.VISIBLE);
                                    viewHolder.tv_text.setVisibility(View.GONE);
                                    viewHolder.tv_dashang_name_left.setText(hashMap.get(AESUtils.Decrypt(chatRowMessageEntities.get(position).getSI())).getRealName() + "，赞赏了");
                                    viewHolder.tv_dashang_number_left.setText(str);

                                } else if (spiltList.size() > 0) {
                                    viewHolder.tv_text.setText("");
                                    viewHolder.rl_dashang_text_left.setVisibility(View.GONE);
                                    viewHolder.tv_text.setVisibility(View.VISIBLE);
                                    ParseEmojiMsgUtil.getExpressionString(context, chatRowMessageEntities.get(position).getI(), viewHolder.tv_text, chatRowMessageEntities.get(position).getC());
                                } else {
                                    viewHolder.rl_dashang_text_left.setVisibility(View.GONE);
                                    viewHolder.tv_text.setVisibility(View.VISIBLE);
                                    viewHolder.tv_text.setText(chatRowMessageEntities.get(position).getC());
                                }
                                viewHolder.tv_text.setTag(chatRowMessageEntities.get(position).getC());
                                break;
                            case 1://图片
                                viewHolder.rl_dashang_text_left.setVisibility(View.GONE);
                                viewHolder.tv_text.setVisibility(View.GONE);
                                viewHolder.img_voice.setVisibility(View.GONE);
                                viewHolder.fm_video.setVisibility(View.GONE);
                                viewHolder.pic_frmlayout.setVisibility(View.VISIBLE);
                                viewHolder.img_picature.setVisibility(View.VISIBLE);
                                viewHolder.voice_lin_layout.setVisibility(View.GONE);
                                DisplayImageOptions optionsone = new DisplayImageOptions.Builder()
                                        .showStubImage(R.mipmap.daoyouzheng)            // 设置图片下载期间显示的图片
                                        .showImageForEmptyUri(R.mipmap.daoyouzheng)    // 设置图片Uri为空或是错误的时候显示的图片
                                        .showImageOnFail(R.mipmap.daoyouzheng)            // 设置图片加载或解码过程中发生错误显示的图片
                                        .cacheInMemory(false)                        // 设置下载的图片是否缓存在内存中
                                        .cacheOnDisc(false)                            // 设置下载的图片是否缓存在SD卡中
                                        .build();
                                ImageLoader.getInstance().displayImage(chatRowMessageEntities.get(position).getT(), viewHolder.img_picature, optionsone);
//                                ImageLoader.getInstance().displayImage(chatRowMessageEntities.get(position).getT(), viewHolder.img_picature);
                                break;
                            case 2://语音
                                array = (ArrayList<String>) mcache.getAsObject("stuvoiceisread");
                                viewHolder.rl_dashang_text_left.setVisibility(View.GONE);
                                viewHolder.tv_text.setVisibility(View.GONE);
                                viewHolder.pic_frmlayout.setVisibility(View.GONE);
                                viewHolder.img_picature.setVisibility(View.GONE);
                                viewHolder.img_voice.setVisibility(View.VISIBLE);
                                viewHolder.fm_video.setVisibility(View.GONE);
                                viewHolder.voice_lin_layout.setVisibility(View.VISIBLE);
                                img_voice_bg_length(chatRowMessageEntities.get(position).getL(), viewHolder.voice_layout_bg);
                                chatRowMessageEntities.get(position).getCT();
                                viewHolder.img_unread.setVisibility(View.VISIBLE);
                                if (array == null) {
                                    array = new ArrayList<>();
                                }
                                if (array.contains(chatRowMessageEntities.get(position).getI())) {
                                    viewHolder.img_unread.setVisibility(View.GONE);
                                }
                                viewHolder.txt_voice_length.setText(chatRowMessageEntities.get(position).getL() + "''");
                                viewHolder.voice_lin_layout.setTag(position);

                                if (position > 0 && isPlayVoice && isOnePlayVoice && position > voicepos && chatId.equals(chatRowMessageEntities.get(position - 1).getI())) {
                                    isOnePlayVoice = false;
                                    voiceRead(viewHolder.voice_lin_layout, position);
                                }
                                if (chatRowMessageEntities.get(position).isplayVoice()) {
                                    viewHolder.img_voice.setVisibility(View.GONE);
                                    viewHolder.img_voice2.setVisibility(View.VISIBLE);

                                } else {
                                    viewHolder.img_voice.setVisibility(View.VISIBLE);
                                    viewHolder.img_voice2.setVisibility(View.GONE);
                                }
                                break;
                            case 3://视频
                                viewHolder.rl_dashang_text_left.setVisibility(View.GONE);
                                viewHolder.tv_text.setVisibility(View.GONE);
                                viewHolder.pic_frmlayout.setVisibility(View.GONE);
                                viewHolder.img_picature.setVisibility(View.GONE);
                                viewHolder.img_voice.setVisibility(View.GONE);
                                viewHolder.fm_video.setVisibility(View.VISIBLE);
                                viewHolder.img_video_play.setVisibility(View.VISIBLE);
                                viewHolder.voice_lin_layout.setVisibility(View.GONE);
                                String path = chatRowMessageEntities.get(position).getU();
                                String name = chatRowMessageEntities.get(position).getU().substring(path.lastIndexOf("/"));
                                File file = new File(PathUtil.getInstance().getVideoPath(), name);
                                if (file.exists()) {
                                    viewHolder.img_video_play.setBackgroundResource(R.mipmap.media_palyer);
                                } else {
                                    viewHolder.img_video_play.setBackgroundResource(R.mipmap.school_chat_vedio_download);
                                }
                                if (chatRowMessageEntities.get(position).getCI() != null) {
                                    Glide.with(context).load(chatRowMessageEntities.get(position).getCI())
                                            .placeholder(R.mipmap.home_loading)
                                            .error(R.mipmap.home_loading)
                                            .into(viewHolder.img_video_cover);
                                } else {
                                    viewHolder.img_video_cover.setImageBitmap(chatRowMessageEntities.get(position).getBitmap());
                                }
                                break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                viewHolder.img_picature.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String path = chatRowMessageEntities.get(position).getO();
                        ArrayList<String> list = new ArrayList<String>();
                        list.add(path);
                        Bundle bundle = new Bundle();
                        bundle.putStringArrayList("image_urls", list);
                        bundle.putString("image_index", 0 + "");
                        Intent intent = new Intent(context, ImagePagerActivity.class);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }
                });
                viewHolder.tv_text.setOnLongClickListener(
                        new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {
                                TextView textView = (TextView) v;
                                showpopUpWindow(textView, textView.getTag().toString());
                                return false;
                            }
                        }
                );

                viewHolder.voice_lin_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
//                        voiceRead(v);
                        array = (ArrayList<String>) mcache.getAsObject("stuvoiceisread");
                        if (array == null) {
                            array = new ArrayList<String>();
                        }
                        if (!array.contains(chatRowMessageEntities.get((int) v.getTag()).getI())) {
                            array.add(chatRowMessageEntities.get((int) v.getTag()).getI());
                        }
                        chatId = chatRowMessageEntities.get((int) v.getTag()).getI();
                        mcache.put("stuvoiceisread", array);
                        EventBus.getDefault().post("teacherluyinjieshu");
                        LinearLayout linearLayout = (LinearLayout) v;
                        if (null != viewHolder) {
                            if (null != viewHolder.img_voice2) {
                                viewHolder.img_voice2.setVisibility(View.GONE);
                            }
                            if (null != viewHolder.img_voice) {
                                viewHolder.img_voice.setVisibility(View.VISIBLE);
                            }
                        }

                        imageView1 = (ImageView) linearLayout.findViewById(R.id.img_voice);
                        imageView3 = (ImageView) linearLayout.findViewById(R.id.img_voice2);
                        imageView3.setVisibility(View.GONE);
                        imageView1.setVisibility(View.VISIBLE);
                        ImageView img_unread = (ImageView) linearLayout.findViewById(R.id.img_unread);
                        img_unread.setVisibility(View.GONE);
                        chatRowMessageEntities.get((int) v.getTag()).setIsread(true);
                        chatRowMessageEntities.get((Integer) v.getTag()).setIsplayVoice(false);

                        try {
                            if (animationDrawable != null) {
                                animationDrawable.stop();
                                if (beforeImageView != null)
                                    beforeImageView.setImageResource(R.mipmap.school_chat_left_playaudio);
                            }
                            beforeImageView = imageView1;
                            imageView1.setImageResource(R.drawable.voice_play_anim_left);
//                            v.setBackgroundResource(R.drawable.voice_play_anim_left);
                            animationDrawable = (AnimationDrawable) imageView1.getDrawable();
                            if (indext == (Integer) v.getTag()) {
                                if (mediaPlayer.isPlaying()) {
                                    mediaPlayer.pause();
                                    animationDrawable.stop();
                                    imageView1.setImageResource(R.mipmap.school_chat_left_playaudio);
                                } else {
                                    mediaPlayer.reset();
                                    mediaPlayer.setDataSource(chatRowMessageEntities.get((Integer) v.getTag()).getU());
                                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                                    // prepare 通过异步的方式装载媒体资源
                                    mediaPlayer.prepare();
                                    mediaPlayer.start();
                                    animationDrawable.start();
                                }
                            } else {
                                mediaPlayer.pause();
                                animationDrawable.stop();
                                mediaPlayer.reset();
                                mediaPlayer.setDataSource(chatRowMessageEntities.get((Integer) v.getTag()).getU());
                                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                                // prepare 通过异步的方式装载媒体资源
                                mediaPlayer.prepare();
                                mediaPlayer.start();
                                animationDrawable.start();
                            }
                            indext = (Integer) v.getTag();
                            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    if (animationDrawable != null) {
                                        animationDrawable.stop();
                                        beforeImageView.setImageResource(R.mipmap.school_chat_left_playaudio);
                                        boolean isnextboo = true;
                                        chatRowMessageEntities.get((Integer) v.getTag()).setIsplayVoice(true);
                                        if (chatRowMessageEntities.size() > position + 1) {
                                            if (array.contains(chatRowMessageEntities.get(position + 1).getI())) {
                                                isnextboo = false;
                                                isPlayVoice = false;
                                                isOnePlayVoice = false;
                                                voicepos = 0;
                                            }
                                        }
                                        if (isnextboo) {
                                            ChatRowAdapter.this.notifyDataSetChanged();
                                            isPlayVoice = true;
                                            isOnePlayVoice = true;
                                            voicepos = (int) v.getTag();
                                            if (position == chatRowMessageEntities.size() - 1) {
                                                isPlayVoice = false;
                                                isOnePlayVoice = false;
                                                voicepos = 0;
                                            }
                                        }

                                    }
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                final ViewHolder finalViewHolder = viewHolder;
                viewHolder.img_video_cover.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //下载视频
                        String path = chatRowMessageEntities.get(position).getU();
                        if (path != null && path.lastIndexOf("/") > 0 && path.contains("http")) {
                            String name = path.substring(path.lastIndexOf("/"));
                            //判断视频是否存在
                            File file = new File(PathUtil.getInstance().getVideoPath(), name);
                            if (!file.exists()) {
                                finalViewHolder.circleProgress.setVisibility(View.VISIBLE);
                                finalViewHolder.img_video_play.setVisibility(View.GONE);
                                downLoad(path, name, new Handler(), finalViewHolder.circleProgress, finalViewHolder.img_video_play);
                            } else {
                                Intent intent = new Intent(context, VideoPlayActivity.class);
                                intent.putExtra("path", file.toString());
                                context.startActivity(intent);
                            }
                        } else {
                            Intent intent = new Intent(context, VideoPlayActivity.class);
                            intent.putExtra("path", path);
                            context.startActivity(intent);
                        }
                    }
                });
                break;
            case TYPE_RIGHT:
                viewHolderRight.tv_name_right.setVisibility(View.VISIBLE);
                viewHolderRight.img_head_right.setVisibility(View.VISIBLE);
                viewHolderRight.tv_name_right.setText("导游");
                if (chatRowMessageEntities != null) {
                    try {
                        final String right_uid = AESUtils.Decrypt(chatRowMessageEntities.get(position).getSI());
                        viewHolderRight.img_head_right.setTag(right_uid);
                        //设置自己名字
                        if (hashMap != null && hashMap.get(right_uid)!=null) {
                            viewHolderRight.tv_name_right.setText(hashMap.get(right_uid).getRealName());
                            viewHolderRight.img_head_right.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String uid = String.valueOf(v.getTag());
                                    //头像点击
                                    ((LiveChatActivity)context).showHeaderDialog(uid);
                                }
                            });
                            String path = ImageUtils.getInstance().getPath2(right_uid);
                            ImageLoader.getInstance().displayImage(path, viewHolderRight.img_head_right);
                            if (teacherDistributorID.equals(hashMap.get(right_uid).getID()+"")){
                                //如果是讲师
                                viewHolderRight.tv_text_right.setBackgroundResource(R.drawable.bg_chat_teacher_radius);
                                viewHolderRight.pic_frmlayout.setBackgroundResource(R.drawable.bg_chat_teacher_radius);
                                viewHolderRight.fm_video_right.setBackgroundResource(R.drawable.bg_chat_teacher_radius);
                                viewHolderRight.voice_lin_layout_right.setBackgroundResource(R.drawable.bg_chat_teacher_radius);
                                viewHolderRight.tv_teacher.setVisibility(View.VISIBLE);
                            }else {
                                viewHolderRight.tv_teacher.setVisibility(View.GONE);
                                viewHolderRight.tv_text_right.setBackgroundResource(R.drawable.bg_chat_radius);
                                viewHolderRight.pic_frmlayout.setBackgroundResource(R.drawable.bg_chat_radius);
                                viewHolderRight.fm_video_right.setBackgroundResource(R.drawable.bg_chat_radius);
                                viewHolderRight.voice_lin_layout_right.setBackgroundResource(R.drawable.bg_chat_radius);

                            }
                        } /*else {
                            if (listnerNickNameCallback != null) {
                                listnerNickNameCallback.getNickName(right_uid);
                            }
                        }*/

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    viewHolderRight.rl_dashang_text_right.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String str = chatRowMessageEntities.get(position).getC().replaceFirst("7f3cbcc68541d35f72feb0e4914e9b82", "");
                            ((LiveChatActivity)context).showDaShangDetailDialog(str,chatRowMessageEntities.get(position).getSI());
                        }
                    });
                    String ct = chatRowMessageEntities.get(position).getCT();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    try {
                        Calendar c = Calendar.getInstance();
                        c.setTime(simpleDateFormat.parse(ct));
                        long millionSeconds1 = 0;
                        long millionSeconds = c.getTimeInMillis();
                        if (position > 0) {
                            String ct1 = chatRowMessageEntities.get(position - 1).getCT();
                            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            c.setTime(simpleDateFormat.parse(ct1));
                            millionSeconds1 = c.getTimeInMillis();
                        }
                        if (millionSeconds - millionSeconds1 > 60 * 1000) {
                            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("HH:mm");
                            viewHolderRight.tv_time_right.setText(simpleDateFormat2.format(millionSeconds));
                            viewHolderRight.tv_time_right.setVisibility(View.VISIBLE);
                        } else if (millionSeconds - millionSeconds1 > 60 * 1000 * 60 * 24) {
                            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("HH:mm");
                            viewHolderRight.tv_time_right.setText("昨天" + simpleDateFormat2.format(millionSeconds));
                            viewHolderRight.tv_time_right.setVisibility(View.VISIBLE);
                        } else if (millionSeconds - millionSeconds1 > 60 * 1000 * 60 * 24 * 2) {
                            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
                            viewHolderRight.tv_time_right.setText(simpleDateFormat2.format(millionSeconds));
                            viewHolderRight.tv_time_right.setVisibility(View.VISIBLE);
                        } else {
                            viewHolderRight.tv_time_right.setVisibility(View.GONE);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    if (chatRowMessageEntities != null) {
                        switch (chatRowMessageEntities.get(position).getMT()) {
                            case 0:
                                if (chatRowMessageEntities.get(position).getC().startsWith("7f3cbcc68541d35f72feb0e4914e9b82")) {
                                    viewHolderRight.img_head_right.setVisibility(View.GONE);
                                    viewHolderRight.tv_name_right.setVisibility(View.GONE);
                                    final String str = chatRowMessageEntities.get(position).getC().replaceFirst("7f3cbcc68541d35f72feb0e4914e9b82", "");
                                    viewHolderRight.rl_dashang_text_right.setVisibility(View.VISIBLE);
                                    viewHolderRight.tv_text_right.setVisibility(View.GONE);
                                    viewHolderRight.tv_dashang_name_right.setText(hashMap.get(AESUtils.Decrypt(chatRowMessageEntities.get(position).getSI())).getRealName() + "，赞赏了");
                                    viewHolderRight.tv_dashang_number_right.setText(str);
//                                        viewHolderRight.tv_text_right.setText("你好啊！" + str);

                                } else if (zhengzeSplit(chatRowMessageEntities.get(position).getC()).size() > 0) {
                                    viewHolderRight.rl_dashang_text_right.setVisibility(View.GONE);
                                    viewHolderRight.tv_text_right.setVisibility(View.VISIBLE);
                                    ParseEmojiMsgUtil.getExpressionString(context, chatRowMessageEntities.get(position).getI(), viewHolderRight.tv_text_right, chatRowMessageEntities.get(position).getC());
                                } else {
                                    viewHolderRight.rl_dashang_text_right.setVisibility(View.GONE);
                                    viewHolderRight.tv_text_right.setVisibility(View.VISIBLE);
                                    viewHolderRight.tv_text_right.setText(chatRowMessageEntities.get(position).getC());
                                }
                                viewHolderRight.tv_text_right.setTag(chatRowMessageEntities.get(position).getC());
                                viewHolderRight.pic_frmlayout.setVisibility(View.GONE);
                                viewHolderRight.img_voice_right.setVisibility(View.GONE);
                                viewHolderRight.fm_video_right.setVisibility(View.GONE);
                                viewHolderRight.voice_lin_layout_right.setVisibility(View.GONE);
                                viewHolderRight.txt_voice_length_right.setVisibility(View.GONE);
                                viewHolderRight.img_unread_right.setVisibility(View.GONE);

                                break;
                            case 1:
                                viewHolderRight.rl_dashang_text_right.setVisibility(View.GONE);
                                viewHolderRight.tv_text_right.setVisibility(View.GONE);
                                viewHolderRight.img_voice_right.setVisibility(View.GONE);
                                viewHolderRight.fm_video_right.setVisibility(View.GONE);
                                viewHolderRight.pic_frmlayout.setVisibility(View.VISIBLE);
                                viewHolderRight.img_unread_right.setVisibility(View.GONE);
                                viewHolderRight.voice_lin_layout_right.setVisibility(View.GONE);
                                viewHolderRight.txt_voice_length_right.setVisibility(View.GONE);
//                                Glide.with(context).load(chatRowMessageEntities.get(position).getT()).into(viewHolderRight.img_picature_right);
                                DisplayImageOptions optionsone = new DisplayImageOptions.Builder()
                                        .showStubImage(R.mipmap.daoyouzheng)            // 设置图片下载期间显示的图片
                                        .showImageForEmptyUri(R.mipmap.daoyouzheng)    // 设置图片Uri为空或是错误的时候显示的图片
                                        .showImageOnFail(R.mipmap.daoyouzheng)            // 设置图片加载或解码过程中发生错误显示的图片
                                        .cacheInMemory(false)                        // 设置下载的图片是否缓存在内存中
                                        .cacheOnDisc(false)                            // 设置下载的图片是否缓存在SD卡中
                                        .build();
                                ImageLoader.getInstance().displayImage(chatRowMessageEntities.get(position).getT(), viewHolderRight.img_picature_right, optionsone);
//                                ImageLoader.getInstance().displayImage(chatRowMessageEntities.get(position).getT(), viewHolderRight.img_picature_right);
                                break;
                            case 2:
                                viewHolderRight.rl_dashang_text_right.setVisibility(View.GONE);
                                viewHolderRight.tv_text_right.setVisibility(View.GONE);
                                viewHolderRight.pic_frmlayout.setVisibility(View.GONE);
                                viewHolderRight.fm_video_right.setVisibility(View.GONE);
                                viewHolderRight.img_voice_right.setVisibility(View.VISIBLE);
                                viewHolderRight.voice_lin_layout_right.setVisibility(View.VISIBLE);
                                viewHolderRight.txt_voice_length_right.setVisibility(View.VISIBLE);
                                img_voice_bg_length_right(chatRowMessageEntities.get(position).getL(), viewHolderRight.voice_lin_layout_right);
                                viewHolderRight.img_unread_right.setVisibility(View.GONE);
                                viewHolderRight.txt_voice_length_right.setText(chatRowMessageEntities.get(position).getL() + "''");
                                viewHolderRight.voice_lin_layout_right.setTag(position);
                                break;
                            case 3:
                                viewHolderRight.rl_dashang_text_right.setVisibility(View.GONE);
                                viewHolderRight.tv_text_right.setVisibility(View.GONE);
                                viewHolderRight.pic_frmlayout.setVisibility(View.GONE);
                                viewHolderRight.img_voice_right.setVisibility(View.GONE);
                                viewHolderRight.fm_video_right.setVisibility(View.VISIBLE);
                                viewHolderRight.voice_lin_layout_right.setVisibility(View.GONE);
                                viewHolderRight.txt_voice_length_right.setVisibility(View.GONE);
                                viewHolderRight.img_unread_right.setVisibility(View.GONE);
                                viewHolderRight.img_video_play_right.setVisibility(View.VISIBLE);
                                String path = chatRowMessageEntities.get(position).getU();
                                String name = chatRowMessageEntities.get(position).getU().substring(path.lastIndexOf("/"));
                                File file = new File(PathUtil.getInstance().getVideoPath(), name);
                                if (file.exists()) {
                                    viewHolderRight.img_video_play_right.setBackgroundResource(R.mipmap.media_palyer);
                                } else {
                                    viewHolderRight.img_video_play_right.setBackgroundResource(R.mipmap.school_chat_vedio_download);
                                }
                                if (chatRowMessageEntities.get(position).getCI() != null) {
                                    Glide.with(context).load(chatRowMessageEntities.get(position).getCI())//
                                            .placeholder(R.mipmap.home_loading)//
                                            .error(R.mipmap.home_loading)//
                                            .into(viewHolderRight.img_video_cover_right);
                                } else {
                                    viewHolderRight.img_video_cover_right.setImageBitmap(chatRowMessageEntities.get(position).getBitmap());
                                }

                                break;
                        }
                    }
                } catch (Exception e) {
                }
                viewHolderRight.img_picature_right.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String path = chatRowMessageEntities.get(position).getO();
                        ArrayList<String> list = new ArrayList<String>();
                        list.add(path);
                        Bundle bundle = new Bundle();
                        bundle.putStringArrayList("image_urls", list);
                        bundle.putString("image_index", 0 + "");
                        Intent intent = new Intent(context, ImagePagerActivity.class);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }
                });
                viewHolderRight.tv_text_right.setOnLongClickListener(
                        new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {
                                TextView textView = (TextView) v;
                                showpopUpWindow(textView, textView.getTag().toString());
                                return false;
                            }
                        }
                );
                viewHolderRight.voice_lin_layout_right.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EventBus.getDefault().post("teacherluyinjieshu");
//                        if(){}else{}
                        LinearLayout linearLayout = (LinearLayout) v;
                        imageView2 = (ImageView) linearLayout.findViewById(R.id.img_voice);
                        try {
                            if (animationDrawable != null) {
                                animationDrawable.stop();
                                if (beforImageViewRight != null)
                                    beforImageViewRight.setImageResource(R.mipmap.school_chat_right_playaudio);
                            }
                            beforImageViewRight = imageView2;
                            imageView2.setImageResource(R.drawable.voice_play_anim_right);
                            animationDrawable = (AnimationDrawable) imageView2.getDrawable();
                            if (indext == (Integer) v.getTag()) {
                                if (mediaPlayer.isPlaying()) {
                                    mediaPlayer.pause();
                                    imageView2.setImageResource(R.mipmap.school_chat_right_playaudio);
                                    animationDrawable.stop();
                                } else {
                                    mediaPlayer.reset();
                                    mediaPlayer.setDataSource(chatRowMessageEntities.get((Integer) v.getTag()).getU());
                                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                                    // prepare 通过异步的方式装载媒体资源
                                    mediaPlayer.prepare();
                                    mediaPlayer.start();
                                    animationDrawable.start();
                                }
                            } else {
                                mediaPlayer.pause();
                                animationDrawable.stop();
                                mediaPlayer.reset();
                                mediaPlayer.setDataSource(chatRowMessageEntities.get((Integer) v.getTag()).getU());
                                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                                // prepare 通过异步的方式装载媒体资源
                                mediaPlayer.prepare();
                                mediaPlayer.start();
                                animationDrawable.start();
                            }
                            indext = (Integer) v.getTag();
                            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    if (animationDrawable != null) {
                                        animationDrawable.stop();
                                        beforImageViewRight.setImageResource(R.mipmap.school_chat_right_playaudio);
                                    }
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                final ViewHolderRight finalViewHolderRight = viewHolderRight;
                viewHolderRight.img_video_cover_right.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //下载视频
                        String path = chatRowMessageEntities.get(position).getU();
                        if (path != null && path.lastIndexOf("/") > 0 && path.contains("http")) {
                            String name = path.substring(path.lastIndexOf("/"));
                            String SDPath = Environment.getExternalStorageDirectory().getAbsolutePath();
                            //判断视频是否存在
                            File file = new File(PathUtil.getInstance().getVideoPath(), name);
                            if (!file.exists()) {
                                finalViewHolderRight.circleProgress_right.setVisibility(View.VISIBLE);
                                finalViewHolderRight.img_video_play_right.setVisibility(View.GONE);
                                downLoad(path, name, new Handler(), finalViewHolderRight.circleProgress_right, finalViewHolderRight.img_video_play_right);
                            } else {
                                Intent intent = new Intent(context, VideoPlayActivity.class);
                                intent.putExtra("path", file.toString());
                                context.startActivity(intent);
                            }
                        } else {
                            Intent intent = new Intent(context, VideoPlayActivity.class);
                            intent.putExtra("path", path);
                            context.startActivity(intent);
                        }
                    }
                });
                break;
        }
        if (position == chatRowMessageEntities.size() - 1) {
            isPlayVoice = false;
            isOnePlayVoice = false;
            voicepos = 0;
        }
        return convertView;
    }

    /**
     * 左边布局
     */
    public class ViewHolder {
        ProgressBar progressBar;
        TextView tv_time;
        TextView tv_name;
        CircleImageView img_head;
        ImageView img_video_cover;
        LinearLayout rl_backround;
        TextView tv_text;
        ImageView img_voice;
        ImageView img_unread;
        TextView txt_voice_length;
        LinearLayout voice_lin_layout;
        FrameLayout fm_video;
        PowerImageView img_picature;
        ImageView img_video_play;
        CircleProgress circleProgress;
        FrameLayout pic_frmlayout;
        CircleProgress pic_circleProgress;
        LinearLayout voice_layout_bg;
        RelativeLayout rl_dashang_text_left;
        TextView tv_dashang_name_left;
        TextView tv_dashang_number_left;
        ImageView img_voice2;
        TextView tv_teacher;
        RelativeLayout rl_appreciates;
    }

    /**
     * 右边布局
     */
    public class ViewHolderRight {
        TextView tv_time_right;
        TextView tv_name_right;
        CircleImageView img_head_right;
        ImageView img_video_cover_right;
        LinearLayout rl_backround_right;
        TextView tv_text_right;
        LinearLayout voice_lin_layout_right;
        ImageView img_voice_right;
        TextView txt_voice_length_right;
        ImageView img_unread_right;
        FrameLayout fm_video_right;
        PowerImageView img_picature_right;
        CircleProgress circleProgress_right;
        ImageView img_video_play_right;
        CircleProgress pic_circleProgress;
        FrameLayout pic_frmlayout;
        RelativeLayout rl_dashang_text_right;
        TextView tv_dashang_name_right;
        TextView tv_dashang_number_right;
        TextView tv_teacher;
    }

    private List<String> zhengzeSplit(String content) {
        String regex = "\\[\\d+\\]";
        Matcher matcher = Pattern.compile(regex).matcher(content);
        ArrayList<String> values = new ArrayList<String>();
        while (matcher.find()) {
            values.add(String.valueOf(matcher.group(0)));
        }
        return values;
    }

    private void voiceRead(final View v, final int position) {
        if (!array.contains(chatRowMessageEntities.get((int) v.getTag()).getI())) {
            array.add(chatRowMessageEntities.get((int) v.getTag()).getI());
        }
        chatId = chatRowMessageEntities.get((int) v.getTag()).getI();
        mcache.put("stuvoiceisread", array);
        LinearLayout linearLayout = (LinearLayout) v;
        ImageView imageView = (ImageView) linearLayout.findViewById(R.id.img_voice);
        viewHolder.img_voice2.setVisibility(View.GONE);
        viewHolder.img_voice.setVisibility(View.VISIBLE);
        imageView3 = (ImageView) linearLayout.findViewById(R.id.img_voice2);
        imageView3.setVisibility(View.GONE);
        imageView.setVisibility(View.VISIBLE);
        ImageView img_unread = (ImageView) linearLayout.findViewById(R.id.img_unread);
        img_unread.setVisibility(View.GONE);
        chatRowMessageEntities.get((int) v.getTag()).setIsread(true);
        chatRowMessageEntities.get((Integer) v.getTag()).setIsplayVoice(false);
        try {
            if (animationDrawable != null) {
                animationDrawable.stop();
                if (beforeImageView != null)
                    beforeImageView.setImageResource(R.mipmap.school_chat_left_playaudio);
            }
            beforeImageView = imageView;
            imageView.setImageResource(R.drawable.voice_play_anim_left);
//                            v.setBackgroundResource(R.drawable.voice_play_anim_left);
            animationDrawable = (AnimationDrawable) imageView.getDrawable();
            if (indext == (Integer) v.getTag()) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    animationDrawable.stop();
                    imageView.setImageResource(R.mipmap.school_chat_left_playaudio);
                } else {
                    mediaPlayer.reset();
                    mediaPlayer.setDataSource(chatRowMessageEntities.get((Integer) v.getTag()).getU());
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    // prepare 通过异步的方式装载媒体资源
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    animationDrawable.start();
                }
            } else {
                mediaPlayer.pause();
                animationDrawable.stop();
                mediaPlayer.reset();
                mediaPlayer.setDataSource(chatRowMessageEntities.get((Integer) v.getTag()).getU());
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                // prepare 通过异步的方式装载媒体资源
                mediaPlayer.prepare();
                mediaPlayer.start();
                animationDrawable.start();
            }
            indext = (Integer) v.getTag();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if (animationDrawable != null) {
                        animationDrawable.stop();
                        beforeImageView.setImageResource(R.mipmap.school_chat_left_playaudio);
                        chatRowMessageEntities.get((Integer) v.getTag()).setIsplayVoice(true);
                        boolean isnextboo = true;
                        if (chatRowMessageEntities.size() > position + 1) {
                            if (array.contains(chatRowMessageEntities.get(position + 1).getI())) {
                                isnextboo = false;
                                isPlayVoice = false;
                                isOnePlayVoice = false;
                                voicepos = 0;
                            }
                        }
                        if (isnextboo) {
                            //一条语音读完之后
                            ChatRowAdapter.this.notifyDataSetChanged();
                            isPlayVoice = true;
                            isOnePlayVoice = true;
                            voicepos = (int) v.getTag();
                            if (position == chatRowMessageEntities.size() - 1) {//防止最后一条语音后再没有语音了。
                                isPlayVoice = false;
                                isOnePlayVoice = false;
                                voicepos = 0;
                            }
                        }

                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void img_voice_bg_length(int length, View view) {
        switch (length) {
            case 1:
                view.setPadding(20, 0, 60, 0);
                break;
            case 2:
                view.setPadding(20, 0, 70, 0);
                break;
            case 3:
                view.setPadding(20, 0, 80, 0);
                break;
            case 4:
                view.setPadding(20, 0, 90, 0);
                break;
            case 5:
                view.setPadding(20, 0, 100, 0);
                break;
            case 6:
                view.setPadding(20, 0, 110, 0);
                break;
            case 7:
                view.setPadding(20, 0, 120, 0);
                break;
            case 8:
                view.setPadding(20, 0, 130, 0);
                break;
            case 9:
                view.setPadding(20, 0, 140, 0);
                break;
            case 10:
                view.setPadding(20, 0, 150, 0);
                break;
            case 11:
                view.setPadding(20, 0, 160, 0);
                break;
            case 12:
                view.setPadding(20, 0, 170, 0);
                break;
            case 13:
                view.setPadding(20, 0, 180, 0);
                break;
            case 14:
                view.setPadding(20, 0, 190, 0);
                break;
            case 16:
                view.setPadding(20, 0, 200, 0);
                break;
            case 18:
                view.setPadding(20, 0, 210, 0);
                break;
            default:
                view.setPadding(20, 0, 250, 0);
                break;
        }
    }

    PopupWindow popupWindow;

    public void showpopUpWindow(TextView view, final String copyStr) {
        View contentview = inflater.inflate(R.layout.im_copy_dialog, null);
        popupWindow = new PopupWindow(contentview, LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView textView = (TextView) contentview.findViewById(R.id.txt_copy);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //复制
                ClipboardManager clip = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                clip.setText(copyStr); // 复制
                popupWindow.dismiss();
            }
        });
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAsDropDown(view, view.getWidth() / 2, -(view.getHeight() + 50));
    }

    private void downLoad(String path, final String name, final Handler handler, final CircleProgress circleProgress, final ImageView imageView) {
        Request request = new Request.Builder().url(path).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                try {
                    is = response.body().byteStream();
                    long total = response.body().contentLength();
                    File file = new File(PathUtil.getInstance().getVideoPath(), name);
                    fos = new FileOutputStream(file);
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum += len;
                        final int progress = (int) (sum * 1.0f / total * 100);
                        Log.d("h_bl", "progress=" + progress);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                circleProgress.setValue(progress);
                                circleProgress.setVisibility(View.GONE);
                                imageView.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                    fos.flush();
                    Log.d("h_bl", "文件下载成功");
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            circleProgress.setVisibility(View.GONE);
                            imageView.setVisibility(View.VISIBLE);
                            imageView.setBackgroundResource(R.mipmap.media_palyer);
                        }
                    });
                } catch (Exception e) {
                    Log.d("h_bl", "文件下载失败");
                } finally {

                }
            }
        });
    }

    public void img_voice_bg_length_right(int length, View view) {
        switch (length) {
            case 1:
                view.setPadding(60, 0, 20, 0);
                break;
            case 2:
                view.setPadding(70, 0, 20, 0);
                break;
            case 3:
                view.setPadding(80, 0, 20, 0);
                break;
            case 4:
                view.setPadding(90, 0, 20, 0);
                break;
            case 5:
                view.setPadding(100, 0, 20, 0);
                break;
            case 6:
                view.setPadding(110, 0, 20, 0);
                break;
            case 7:
                view.setPadding(120, 0, 20, 0);
                break;
            case 8:
                view.setPadding(130, 0, 20, 0);
                break;
            case 9:
                view.setPadding(140, 0, 20, 0);
                break;
            case 10:
                view.setPadding(150, 0, 20, 0);
                break;
            case 11:
                view.setPadding(160, 0, 20, 0);
                break;
            case 12:
                view.setPadding(170, 0, 20, 0);
                break;
            case 13:
                view.setPadding(180, 0, 20, 0);
                break;
            case 14:
                view.setPadding(190, 0, 20, 0);
                break;
            case 16:
                view.setPadding(200, 0, 20, 0);
                break;
            case 18:
                view.setPadding(210, 0, 20, 0);
                break;
            default:
                view.setPadding(250, 0, 20, 0);
                break;
        }
    }
    public void onStopVoice() {
        isPlayVoice = false;
        isOnePlayVoice = false;
        voicepos = 0;
        if (mediaPlayer != null && animationDrawable != null) {
            mediaPlayer.pause();
            animationDrawable.stop();
        }
        if (imageView2 != null) {
            imageView2.setImageResource(R.mipmap.school_chat_right_playaudio);
        }
        if (imageView1 != null) {
            imageView1.setImageResource(R.mipmap.school_chat_left_playaudio);
        }
    }
}