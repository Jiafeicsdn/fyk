package com.lvgou.distribution.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.lvgou.distribution.R;


/**
 * Created by Administrator on 2016/6/22.
 */
public class PopWindows {
    PopupWindow popupWindow;
    int[] locatin;
    Activity activity;
    Context context;
    View view;
    private int screenHeight;
    private int screenWidth;
    WindowManager wm;
    private WindowManager.LayoutParams params;

    public PopWindows(Activity activity, Context context, View view) {
        super();
        this.activity = activity;
        this.context = context;
        this.view = view;
        wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    }

    public void showPopWindow(final View v) {
        v.setVisibility(View.VISIBLE);
        params = activity.getWindow().getAttributes();
//        screenWidth = CommonUtils.getScreenWidth(activity);
        screenWidth = wm.getDefaultDisplay().getWidth();
        popupWindow = new PopupWindow(view, screenWidth, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);// 设置后点击其他位置PopupWindow将消失
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        params.alpha = 0.7f;
        locatin = new int[2];
        activity.getWindow().setAttributes(params);
        view.getLocationInWindow(locatin);
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(view, Gravity.CENTER, locatin[0], locatin[1]);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                v.setVisibility(View.GONE);
                cleanPopAlpha();
            }
        });
    }

    public void showPopWindowHorizoonatal() {
        params = activity.getWindow().getAttributes();
//        screenWidth = CommonUtils.getScreenWidth(activity);
        screenWidth = wm.getDefaultDisplay().getWidth();
        popupWindow = new PopupWindow(view, (screenWidth / 6) * 5, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);// 设置后点击其他位置PopupWindow将消失
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        params.alpha = 0.4f;
        locatin = new int[2];
        activity.getWindow().setAttributes(params);
        view.getLocationInWindow(locatin);
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(view, Gravity.CENTER, locatin[0], locatin[1]);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                cleanPopAlpha();
            }
        });
    }

    public void showPopWindowBottom() {
        params = activity.getWindow().getAttributes();
//        screenWidth = CommonUtils.getScreenWidth(activity);
        screenWidth = wm.getDefaultDisplay().getWidth();
//        int screenHeight = CommonUtils.getScreenHeight(activity);
        int screenHeight = wm.getDefaultDisplay().getHeight();
        popupWindow = new PopupWindow(view, screenWidth, LinearLayout.LayoutParams.WRAP_CONTENT);
//        popupWindow = new PopupWindow(view, screenWidth, (screenHeight / 6) * 2);
        popupWindow.setAnimationStyle(R.style.popwin_anim_style_up);
        popupWindow.setOutsideTouchable(true);// 设置后点击其他位置PopupWindow将消失
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        params.alpha = 0.7f;
        locatin = new int[2];
        activity.getWindow().setAttributes(params);
        view.getLocationInWindow(locatin);
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(view, Gravity.BOTTOM, locatin[0], locatin[1]);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                cleanPopAlpha();
            }
        });
    }


    public void showWholeasaleTypePopWindowView(View views) {
        params = activity.getWindow().getAttributes();
        screenWidth = wm.getDefaultDisplay().getWidth();
        popupWindow = new PopupWindow(view, screenWidth, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);// 设置后点击其他位置PopupWindow将消失
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        params.alpha = 0.7f;
        locatin = new int[2];
        activity.getWindow().setAttributes(params);
        views.getLocationInWindow(locatin);
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, locatin[0], locatin[1]);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                cleanPopAlpha();
            }
        });
    }

    //右上角的Pop
    public void showRightTopPopWindow(View views) {
        params = activity.getWindow().getAttributes();
//        screenWidth = CommonUtils.getScreenWidth(activity);
        screenWidth = wm.getDefaultDisplay().getWidth();
//        int screenHeight = CommonUtils.getScreenHeight(activity);
        screenHeight = wm.getDefaultDisplay().getHeight();
        popupWindow = new PopupWindow(view, screenWidth / 20 * 8, screenHeight / 4);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popupWindow.showAsDropDown(views, screenWidth - screenWidth / 3, 0);
        locatin = new int[2];
        activity.getWindow().setAttributes(params);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                cleanPopAlpha();
            }
        });
    }

    //三级分类的POP
    public void showSubclassPopWindow(View views) {
        params = activity.getWindow().getAttributes();
//        screenWidth = CommonUtils.getScreenWidth(activity);
        screenWidth = wm.getDefaultDisplay().getWidth();
        popupWindow = new PopupWindow(view, screenWidth, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);// 设置后点击其他位置PopupWindow将消失
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        params.alpha = 0.7f;
        locatin = new int[2];
        activity.getWindow().setAttributes(params);
        popupWindow.setFocusable(true);
        popupWindow.showAsDropDown(views, locatin[0], locatin[1]);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                cleanPopAlpha();
            }
        });
    }

    public interface NoNetPopOnClickListener {
        void settingClick();

        void refreshClick();
    }

    //清除关闭POP后的背景
    public void cleanPopAlpha() {
        popupWindow.dismiss();
        params.alpha = 1f;
        activity.getWindow().setAttributes(params);
    }

    public int dpTpPx(float value) {
        DisplayMetrics dm = view.getResources().getDisplayMetrics();
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, dm) + 0.5);
    }

    //获取Pop的状态
    public boolean isShowing() {
        PopupWindow popupWindow = this.popupWindow;
        boolean isShow = popupWindow.isShowing();
        return isShow;
    }

}
