package com.lvgou.distribution.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.adapter.MyPagerAdapter;
import com.lvgou.distribution.driect.OnBottomMenuClickListener;
import com.lvgou.distribution.emoji.SelectFaceHelper;
import com.lvgou.distribution.utils.MyToast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/31.
 */

public class ButtomChatMenu extends LinearLayout {
    private EditText et_content;
    private LinearLayout mIndexContainer;
    /**
     * 游标点集合
     */
    public static OnBottomMenuClickListener onBottomMenuClickListener;
    private Context context;
    private SelectFaceHelper mFaceHelper;
    private View view;
    private MyViewPage voice_viewpager;
    private MyPagerAdapter myPagerAdapter;

    public ButtomChatMenu(Context context) {
        super(context);
        this.context = context;
        initView(context);
    }

    public ButtomChatMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView(context);
    }

    public ButtomChatMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView(context);
    }

    //新的布局
    private RelativeLayout rl_voice_small;//小录音
    private RelativeLayout rl_emoji_small;//小的emoji
    private RelativeLayout rl_pic_small;//小的照片
    private RelativeLayout rl_shoot_small;//小的摄影
    private ImageView ima_emoji;//emoji小图标
    private ImageView img_keyboard;//键盘小图标
    private TextView btn_sendmesage;//发送按钮
    private LinearLayout rl_emoji;//emoji的viewpager
    private ImageView voice_small_icon;//语音小图标
    private LinearLayout ll_voice_buttom;//底部语音

    private int isUseVoic = 1;
    private TextView tv_danji;
    private TextView tv_changan;
    private LinearLayout ll_bottom_voice_text;

    private List<ImageView> imageViews;// 滑动的图片集合
    private LinearLayout title_menu;//底部菜单
    private RelativeLayout title_long_pass;//长按上方计时
    private TextView tv_daojishi;//倒计时
    private RelativeLayout title_short_pass;//单击上方
    private TextView tv_short_miao;//单击读秒
    private TextView short_cancel;//单击上方的取消
    private ProgressBar changan_progress;//长按的进度条
    private ProgressBar danji_progress;//单击的进度条
    private ImageView bg_vp;
    private ImageView danji_animation;
    private TextView tv_daojishinum;//倒计时数值
    private LinearLayout ll_menu;//菜单
    private LinearLayout ll_banned;


    public void initView(Context context) {
        view = View.inflate(context, R.layout.dialog_show_menubottom2, this);


        //新的布局
        ll_banned = (LinearLayout) view.findViewById(R.id.ll_banned);
        ll_banned.setVisibility(GONE);
        ll_menu = (LinearLayout) view.findViewById(R.id.ll_menu);
        rl_voice_small = (RelativeLayout) view.findViewById(R.id.rl_voice_small);
        rl_emoji_small = (RelativeLayout) view.findViewById(R.id.rl_emoji_small);
        rl_pic_small = (RelativeLayout) view.findViewById(R.id.rl_pic_small);
        rl_shoot_small = (RelativeLayout) view.findViewById(R.id.rl_shoot_small);
        ima_emoji = (ImageView) view.findViewById(R.id.ima_emoji);
        img_keyboard = (ImageView) view.findViewById(R.id.img_keyboard);
        et_content = (EditText) view.findViewById(R.id.et_content);
        btn_sendmesage = (TextView) view.findViewById(R.id.btn_sendmesage);
        btn_sendmesage.setClickable(false);
        btn_sendmesage.setEnabled(false);
        rl_emoji = (LinearLayout) view.findViewById(R.id.rl_emoji);
        mIndexContainer = (LinearLayout) view.findViewById(R.id.msg_face_index_view);
        voice_small_icon = (ImageView) view.findViewById(R.id.voice_small_icon);
        voice_small_icon.setBackgroundResource(R.mipmap.send_message_voice_icon);
        isUseVoic = 1;
        tv_danji = (TextView) view.findViewById(R.id.tv_danji);
        tv_changan = (TextView) view.findViewById(R.id.tv_changan);
        ll_bottom_voice_text = (LinearLayout) view.findViewById(R.id.ll_bottom_voice_text);
        ll_voice_buttom = (LinearLayout) view.findViewById(R.id.ll_voice_buttom);
        ll_voice_buttom.setVisibility(View.GONE);
        title_menu = (LinearLayout) view.findViewById(R.id.title_menu);
        title_long_pass = (RelativeLayout) view.findViewById(R.id.title_long_pass);
        tv_daojishi = (TextView) view.findViewById(R.id.tv_daojishi);
        title_short_pass = (RelativeLayout) view.findViewById(R.id.title_short_pass);
        tv_short_miao = (TextView) view.findViewById(R.id.tv_short_miao);
        short_cancel = (TextView) view.findViewById(R.id.short_cancel);
        changan_progress = (ProgressBar) view.findViewById(R.id.changan_progress);
        danji_progress = (ProgressBar) view.findViewById(R.id.danji_progress);
        danji_animation = (ImageView) view.findViewById(R.id.danji_animation);
        tv_daojishinum = (TextView) view.findViewById(R.id.tv_daojishinum);

        et_content.setSingleLine(false);
        et_content.setHorizontallyScrolling(false);
        imageViews = new ArrayList<ImageView>();
//        PowerImageView imageView=new PowerImageView(context);
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(R.mipmap.voice_not_use);

//        PowerImageView imageView1=new PowerImageView(context);
        ImageView imageView1 = new ImageView(context);
        imageView1.setImageResource(R.mipmap.voice_not_use);
        imageViews.add(imageView);
        imageViews.add(imageView1);

        voice_viewpager = (MyViewPage) view.findViewById(R.id.voice_viewpager);
        bg_vp = (ImageView) view.findViewById(R.id.bg_vp);
        myPagerAdapter = new MyPagerAdapter(imageViews, title_menu, title_long_pass, voice_viewpager, title_short_pass, short_cancel, bg_vp, tv_danji, tv_changan, tv_daojishi, tv_daojishinum);
        voice_viewpager.setAdapter(myPagerAdapter);
        voice_viewpager.setCurrentItem(0);
        voice_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    tv_danji.setTextColor(Color.parseColor("#d5aa5f"));
                    tv_changan.setTextColor(Color.parseColor("#777777"));
                } else if (position == 1) {
                    tv_changan.setTextColor(Color.parseColor("#d5aa5f"));
                    tv_danji.setTextColor(Color.parseColor("#777777"));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        OnClick();
    }
    public void setisUseVoic(){
        isUseVoic=1;
    }

    public void isBanned(boolean isbanned) {
        if (isbanned) {
            //如果是禁言中
            ll_banned.setVisibility(VISIBLE);
            et_content.setText("");
            et_content.setHint("");
            btn_sendmesage.setBackgroundColor(Color.parseColor("#CCCCCC"));
            btn_sendmesage.setClickable(false);
            btn_sendmesage.setEnabled(false);
        } else {
            ll_banned.setVisibility(GONE);
            et_content.setText("");
            et_content.setHint("发表下你的想法吧...");
        }
    }

    public void OnClick() {
        ll_banned.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                MyToast.show(context, "禁言中");
            }
        });
        tv_danji.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                voice_viewpager.setCurrentItem(0);
                tv_danji.setTextColor(Color.parseColor("#d5aa5f"));
                tv_changan.setTextColor(Color.parseColor("#777777"));
            }
        });
        tv_changan.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                voice_viewpager.setCurrentItem(1);
                tv_changan.setTextColor(Color.parseColor("#d5aa5f"));
                tv_danji.setTextColor(Color.parseColor("#777777"));
            }
        });


        //表情按鈕
        ima_emoji.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null == mFaceHelper) {
                    mFaceHelper = new SelectFaceHelper(context, rl_emoji);
                    mFaceHelper.setFaceOpreateListener(mOnFaceOprateListener);
                }
                img_keyboard.setVisibility(View.VISIBLE);
                ima_emoji.setVisibility(View.GONE);
                et_content.setVisibility(View.VISIBLE);
                rl_emoji.setVisibility(View.VISIBLE);
                ll_voice_buttom.setVisibility(View.GONE);
                isUseVoic = 1;
                voice_small_icon.setBackgroundResource(R.mipmap.send_message_voice_icon);
                if (onBottomMenuClickListener != null) {
                    onBottomMenuClickListener.OnBottomMenuClickListener(6, "表情");
                }
            }
        });
        /**
         * 键盘按钮
         */
        img_keyboard.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ima_emoji.setVisibility(View.VISIBLE);
                img_keyboard.setVisibility(View.GONE);
                rl_emoji.setVisibility(View.GONE);
                ll_voice_buttom.setVisibility(View.GONE);
                isUseVoic = 1;
                voice_small_icon.setBackgroundResource(R.mipmap.send_message_voice_icon);
                if (onBottomMenuClickListener != null) {
                    onBottomMenuClickListener.OnBottomMenuClickListener(6, "键盘");
                }
            }
        });

        /**
         *语音按钮
         */
        rl_voice_small.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("aksdjfhkahdf", "=========="+isUseVoic );
                if (isUseVoic == 1) {
                    isUseVoic = 2;
                    voice_small_icon.setBackgroundResource(R.mipmap.send_message_voice_icon_use);
                    ll_voice_buttom.setVisibility(View.VISIBLE);
                    Log.e("aksdjfhkahdf", "=====================" +ll_voice_buttom.getVisibility());
                } else {
                    isUseVoic = 1;
                    voice_small_icon.setBackgroundResource(R.mipmap.send_message_voice_icon);
                    ll_voice_buttom.setVisibility(View.GONE);
                }
                rl_emoji.setVisibility(View.GONE);
                ima_emoji.setVisibility(View.VISIBLE);
                img_keyboard.setVisibility(View.GONE);
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(et_content.getWindowToken(), 0);
                if (onBottomMenuClickListener != null) {
                    onBottomMenuClickListener.OnBottomMenuClickListener(6, "语音");
                }

            }
        });

        /**
         * 发送按钮
         */
        btn_sendmesage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {// 2 发送操作
                String msg = et_content.getText().toString();
                ll_voice_buttom.setVisibility(View.GONE);
                isUseVoic = 1;
                voice_small_icon.setBackgroundResource(R.mipmap.send_message_voice_icon);
                if (onBottomMenuClickListener != null) {
                    onBottomMenuClickListener.OnBottomMenuClickListener(2, msg);
                }
                et_content.setText("");
            }
        });

        /**
         * 拍摄视频
         */
        rl_shoot_small.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) { // 5 拍摄视频
                ll_voice_buttom.setVisibility(View.GONE);
                isUseVoic = 1;
                voice_small_icon.setBackgroundResource(R.mipmap.send_message_voice_icon);
                if (onBottomMenuClickListener != null) {
                    onBottomMenuClickListener.OnBottomMenuClickListener(5, "");
                }
            }
        });
        rl_pic_small.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //自定义的弹出框类
                ll_voice_buttom.setVisibility(View.GONE);
                isUseVoic = 1;
                voice_small_icon.setBackgroundResource(R.mipmap.send_message_voice_icon);
                menuWindow = new SelectPicPopupWindow((Activity) context, itemsOnClick);
                //显示窗口
                menuWindow.showAtLocation(view.findViewById(R.id.rl_pic_small), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

            }
        });
/*
        *//**
         * 语音按钮
         *//*
        img_voice.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isShowVoice) {
                    isShowVoice = true;
                    et_content.setVisibility(View.GONE);
                    tv_voice.setVisibility(View.VISIBLE);
                } else {
                    isShowVoice = false;
                    et_content.setVisibility(View.VISIBLE);
                    tv_voice.setVisibility(View.GONE);
                }
                rl_emoji.setVisibility(View.GONE);
                rl_menu.setVisibility(View.GONE);
                img_emoji.setVisibility(View.VISIBLE);
                img_keyboard.setVisibility(View.GONE);
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(et_content.getWindowToken(), 0);
            }
        });
        *//**
         * 发送语音按钮
         *//*
        tv_voice.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {// 1  发送语音 操作
                if (onBottomMenuClickListener != null) {
                    onBottomMenuClickListener.OnBottomMenuClickListener(1, "");
                }
                rl_emoji.setVisibility(View.GONE);
                img_emoji.setVisibility(View.VISIBLE);
                img_keyboard.setVisibility(View.GONE);
                rl_menu.setVisibility(View.GONE);

            }
        });
*/
        /**
         * 文本框
         */
        et_content.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                rl_emoji.setVisibility(View.GONE);
                rl_emoji_small.setVisibility(View.VISIBLE);
                ima_emoji.setVisibility(View.VISIBLE);
                img_keyboard.setVisibility(View.GONE);
                ll_voice_buttom.setVisibility(View.GONE);
                isUseVoic = 1;
                voice_small_icon.setBackgroundResource(R.mipmap.send_message_voice_icon);
                if (onBottomMenuClickListener != null) {
                    onBottomMenuClickListener.OnBottomMenuClickListener(6, "文本框");
                }
                return false;
            }
        });
        et_content.addTextChangedListener(watcher);

    }

    SelectPicPopupWindow menuWindow;
    //为弹出窗口实现监听类
    private OnClickListener itemsOnClick = new OnClickListener() {

        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                case R.id.btn_take_photo:
                    if (onBottomMenuClickListener != null) {
                        onBottomMenuClickListener.OnBottomMenuClickListener(4, "");
                    }

                    break;
                case R.id.btn_pick_photo:
                    if (onBottomMenuClickListener != null) {
                        onBottomMenuClickListener.OnBottomMenuClickListener(3, "");
                    }

                    break;
                default:
                    break;
            }


        }

    };


    private TextWatcher watcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String edit_s = et_content.getText().toString().trim();
            if (edit_s.length() > 0) {
                btn_sendmesage.setBackgroundColor(Color.parseColor("#d5aa5f"));
//                btn_sendmesage.setBackgroundResource(R.drawable.bg_send_message_yes);
                btn_sendmesage.setClickable(true);
                btn_sendmesage.setEnabled(true);
            } else {
                btn_sendmesage.setBackgroundColor(Color.parseColor("#CCCCCC"));
//                btn_sendmesage.setBackgroundResource(R.drawable.bg_send_message_no);
                btn_sendmesage.setClickable(false);
                btn_sendmesage.setEnabled(false);
            }
        }
    };

    /**
     * 获取长按的进度条
     *
     * @return
     */
    public ProgressBar getChanganProgress() {
        return changan_progress;
    }

    /**
     * 获取音律
     *
     * @return
     */
    public ImageView getDanjiAnimation() {
        return danji_animation;
    }

    /**
     * 获取单击的进度条
     *
     * @return
     */
    public ProgressBar getDanjiProgress() {
        return danji_progress;
    }

    /**
     * 获取输入框内容
     *
     * @return
     */
    public String getText() {
        return et_content.getText().toString().trim();
    }

    public LinearLayout getLlbottomVoiceText() {
        return ll_bottom_voice_text;
    }

    /**
     * 返回文本框
     *
     * @return
     */
    public EditText getEtContent() {
        return et_content;
    }

    public LinearLayout getLLMenu() {
        return ll_menu;
    }

    /**
     * 返回 录音按钮
     *
     * @return
     */
//    public TextView getVoiceButton() {
//        return tv_voice;
//    }

    /**
     * 返回底部菜单
     *
     * @return
     */
    public LinearLayout getTitlemenu() {
        return title_menu;
    }

    /**
     * 单击读秒
     *
     * @return
     */
    public TextView getShortMiao() {
        return tv_short_miao;
    }

    /**
     * 获取长按上方的计时
     *
     * @return
     */
    public RelativeLayout getTitleLongpass() {
        return title_long_pass;
    }

    /**
     * 获取单击上方
     *
     * @return
     */
    public RelativeLayout getTitleShortPass() {
        return title_short_pass;
    }

    public MyPagerAdapter getMyPagerAdapter() {
        return myPagerAdapter;
    }

    /**
     * 倒计时
     *
     * @return
     */
    public TextView getDaojishiNum() {
        return tv_daojishinum;
    }

    public static void setOnBottomMenuClickListener(OnBottomMenuClickListener onBottomMenuClickListener) {
        ButtomChatMenu.onBottomMenuClickListener = onBottomMenuClickListener;
    }

    public void hidekeyboard() {
        if (rl_emoji.getVisibility() == VISIBLE) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(et_content.getWindowToken(), 0);
            et_content.clearFocus();
        }
    }

    SelectFaceHelper.OnFaceOprateListener mOnFaceOprateListener = new SelectFaceHelper.OnFaceOprateListener() {
        @Override
        public void onFaceSelected(SpannableString spanEmojiStr) {
            if (null != spanEmojiStr) {
                et_content.append(spanEmojiStr);
            }
        }

        @Override
        public void onFaceDeleted() {
            int selection = et_content.getSelectionStart();
            String text = et_content.getText().toString();
            if (selection > 0) {
                String text2 = text.substring(selection - 1);
                if ("]".equals(text2)) {
                    int start = text.lastIndexOf("[");
                    int end = selection;
                    et_content.getText().delete(start, end);
                    return;
                }
                et_content.getText().delete(selection - 1, selection);
            }
        }

    };

    public void HideView() {
        rl_emoji.setVisibility(View.GONE);
        rl_emoji_small.setVisibility(View.VISIBLE);
        ima_emoji.setVisibility(View.VISIBLE);
        img_keyboard.setVisibility(View.GONE);
        ll_voice_buttom.setVisibility(View.GONE);
        changan_progress.setVisibility(View.GONE);
        danji_progress.setVisibility(View.GONE);
        title_short_pass.setVisibility(View.GONE);
        title_long_pass.setVisibility(View.GONE);
        title_menu.setVisibility(View.VISIBLE);
        voice_small_icon.setBackgroundResource(R.mipmap.send_message_voice_icon);
        bg_vp.setVisibility(View.GONE);
        bg_vp.clearAnimation();
        myPagerAdapter.resetClick();
        ll_bottom_voice_text.setVisibility(View.VISIBLE);
        et_content.clearFocus();
    }


}
