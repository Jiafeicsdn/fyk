package com.lvgou.distribution.utils;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lvgou.distribution.R;

/**
 * Created by Administrator on 2017/5/16.
 */

public class MyToast {
    public static Toast toast;

    public static Toast makeText(Context activity, String msg, int show_length) {
        View toastRoot = LayoutInflater.from(activity).inflate(R.layout.activity_mytoast, null);
        TextView tv = (TextView) toastRoot.findViewById(R.id.textviewinfo);
        if (toast==null){
            toast = new Toast(activity);
        }
//        Toast toast = new Toast(activity);
        tv.setText(msg);
        toast.setView(toastRoot);
        toast.setDuration(show_length);
        toast.setGravity(Gravity.CENTER, 0, 0);
        return toast;
//        toast.show();
    }
    public static void show(Context activity, String msg){
        View toastRoot = LayoutInflater.from(activity).inflate(R.layout.activity_mytoast, null);
        TextView tv = (TextView) toastRoot.findViewById(R.id.textviewinfo);
        if (toast==null){
            toast = new Toast(activity);
        }
//        Toast toast = new Toast(activity);
        tv.setText(msg);
        toast.setView(toastRoot);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
