package com.lvgou.distribution.orc;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.googlecode.tesseract.android.TessBaseAPI;
import com.lidroid.xutils.util.LogUtils;

//get the words from picture through Tesseract engine
public class Tesseract {

    private static final String tag = "Tesseract";
    private static final String DEFAULT_LANGUAGE = "eng";

    public static String getText(Context context, Bitmap bmSrc) {

        TessBaseAPI baseApi = new TessBaseAPI();
        baseApi.init(Environment.getExternalStorageDirectory() + "", DEFAULT_LANGUAGE);//读取SDcard 文件下语言包文件
        baseApi.setImage(bmSrc);
        String text = baseApi.getUTF8Text();
        baseApi.clear();
        baseApi.end();
        return getPhoneOne(filterNumber(text)).toString();
    }

    public static String filterNumber(String number) {
        number = number.replaceAll("[^0-9a-zA-Z]", "");
        return number;
    }

    public static List<String> getPhoneOne(String phone) {
        List<String> list_phone = new ArrayList<String>();
        String telRegex = "[1][3578]\\d{9}";
        Pattern p = Pattern.compile(telRegex);
        Matcher m = p.matcher(filterNumber(phone));
        while (m.find()) {
            String ss = m.group();
            list_phone.add(ss);
        }
        return list_phone;
    }
}