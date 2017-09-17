package com.lvgou.distribution.utils;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;

import com.lvgou.distribution.base.BaseApplication;

/**
 * Created by Administrator on 2016/8/18.
 */
public class UIUtils {

        /** dip转换px */
        public static int dip2px(int dip) {
            final float scale = getContext().getResources().getDisplayMetrics().density;
            return (int) (dip * scale + 0.5f);
        }

        /** pxz转换dip */

        public static int px2dip(int px) {
            final float scale = getContext().getResources().getDisplayMetrics().density;
            return (int) (px / scale + 0.5f);
        }

        /**
         * 根据资源id  获取 字符串数组
         *
         * @param id
         * @return
         */
        public static String[] getStringArray(int id) {
            return getResources().getStringArray(id);
        }

        /**
         * 获取Resources 对象
         *
         * @return
         */
        public static Resources getResources() {
            return getContext().getResources();
        }

        /**
         * 上下文对象
         *
         * @return
         */
        public static Context getContext() {
            return BaseApplication.context;
        }

        /**
         * 根据布局id  返回view 对象
         *
         * @param id
         * @return
         */
        public static View inflate(int id) {
            return View.inflate(getContext(), id, null);
        }

//    /**
//     * 根据 资源id 返回 Drawable 对象
//     *
//     * @param id
//     * @return
//     */
//    public static Drawable getDrawable(int id) {
//        return getResources().getDrawable(id);
//    }

        /**
         * id --->  px
         *
         * @param id
         * @return
         */
        public static int getDimens(int id) {
            return getResources().getDimensionPixelSize(id);
        }

//    /**
//     * 启动一个新的Activity
//     *
//     * @param intent
//     */
//    public static void startActivity(Intent intent) {
//        //TODO  坑
//        //  指定任务栈
//        if (BaseActivity.activity != null) {
//            BaseActivity.activity.startActivity(intent);
//        } else {
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            getContext().startActivity(intent);
//        }
//    }

        /**
         * 通过id 获取字符串
         * @param id
         * @return
         */
        public static String getString(int id) {
            return getResources().getString(id);
        }


}
