package com.lvgou.distribution.emoji;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.Log;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.utils.ImageLoaderEmoji;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.List;
import java.util.logging.Handler;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseEmojiMsgUtil {
    private static final String TAG = ParseEmojiMsgUtil.class.getSimpleName();
    //	private static final String REGEX_STR = "\\[e\\](.*?)\\[/e\\]";
    private static final String REGEX_STR = "\\[\\d+\\]";

    /**
     * 对spanableString进行正则判断，如果符合要求，则以表情图片代替
     */
    public static void dealExpression(Context context, SpannableString spannableString, Pattern patten, int start)
            throws Exception {
        int k = 0;
        Matcher matcher = patten.matcher(spannableString);
        while (matcher.find()) {
            k++;
            String key = matcher.group();
            Log.d("Key", key);
            if (matcher.start() < start) {
                continue;
            }


//			Field field = R.mipmap.class.getDeclaredField("emoji_" + key.substring(key.indexOf("]") + 1, key.lastIndexOf("[")));
//			int resId = Integer.parseInt(field.get(null).toString());
            int signageId = Integer.parseInt(key.replace('[', ' ').replace(']', ' ').trim());
            Field field = R.mipmap.class.getDeclaredField("emoji_" + signageId);
            int resId = Integer.parseInt(field.get(null).toString());
            if (resId != 0) {
                Bitmap bitmap = readBitMap(context, resId);
//				Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resId);
                ImageSpan imageSpan = new ImageSpan(bitmap);
                int end = matcher.start() + key.length();
                spannableString.setSpan(imageSpan, matcher.start(), end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
//                if (end < spannableString.length()) {
//                    dealExpression(context, spannableString, patten, end);
//                }
//                break;
            }
        }
    }

    /**
     * 以最省内存的方式读取本地资源的图片
     *
     * @param context
     * @param resId
     * @return
     */
    public static Bitmap readBitMap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        //获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }

    /**
     * @param context
     * @param str
     * @return
     * @desc <pre>
     * 解析字符串中的表情字符串替换成表情图片
     * </pre>
     * @author Weiliang Hu
     * @date 2013-12-17
     */
    public static SpannableString getExpressionString(Context context, String str) {
        SpannableString spannableString = new SpannableString(str);
        Pattern sinaPatten = Pattern.compile(REGEX_STR, Pattern.CASE_INSENSITIVE);
        try {
            dealExpression(context, spannableString, sinaPatten, 0);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return spannableString;
    }

    public static void getExpressionString(Context context,String messageId, TextView textView, String str) {
        SpannableString spannableString = new SpannableString(str);
        Pattern sinaPatten = Pattern.compile(REGEX_STR, Pattern.CASE_INSENSITIVE);
        try {
            ImageLoaderEmoji.getInstance().bindBitmap(messageId,spannableString,textView,sinaPatten, 0);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    /**
     * @param cs
     * @param mContext
     * @return
     * @desc <pre>表情解析,转成unicode字符</pre>
     * @author Weiliang Hu
     * @date 2013-12-17
     */
    public static String convertToMsg(CharSequence cs, Context mContext) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(cs);
        ImageSpan[] spans = ssb.getSpans(0, cs.length(), ImageSpan.class);
        for (int i = 0; i < spans.length; i++) {
            ImageSpan span = spans[i];
            String c = span.getSource();
            int a = ssb.getSpanStart(span);
            int b = ssb.getSpanEnd(span);
            if (c.contains("[")) {
                ssb.replace(a, b, convertUnicode(c));
            }
        }
        ssb.clearSpans();
        return ssb.toString();
    }

    public static String convertToMsg2(String cs, List<String> list, Context mContext) {
        String unicode = "";
        unicode = cs;
        for (int i = 0; i < list.size(); i++) {
            unicode = unicode.replace(list.get(i), convertUnicode(list.get(i)));
        }
        return unicode;
    }

    private static String convertUnicode(String emo) {
        emo = emo.substring(1, emo.length() - 1);
        if (emo.length() < 6) {
            return new String(Character.toChars(Integer.parseInt(emo, 16)));
        }
        String[] emos = emo.split("_");
        char[] char0 = Character.toChars(Integer.parseInt(emos[0], 16));
        char[] char1 = Character.toChars(Integer.parseInt(emos[1], 16));
        char[] emoji = new char[char0.length + char1.length];
        for (int i = 0; i < char0.length; i++) {
            emoji[i] = char0[i];
        }
        for (int i = char0.length; i < emoji.length; i++) {
            emoji[i] = char1[i - char0.length];
        }
        return new String(emoji);
    }

}
