package com.lvgou.distribution.utils;

import android.content.Context;
import android.text.Spannable;
import android.text.style.ImageSpan;

import com.lvgou.distribution.R;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/9/18.
 */
public class SmileUtils {

    private static final Map<Pattern, Integer> emoticons;
    public static final String f00 = "[0]";
    public static final String f01 = "[1]";
    public static final String f010 = "[10]";
    public static final String f011 = "[11]";
    public static final String f012 = "[12]";
    public static final String f013 = "[13]";
    public static final String f014 = "[14]";
    public static final String f015 = "[15]";
    public static final String f016 = "[16]";
    public static final String f017 = "[17]";
    public static final String f018 = "[18]";
    public static final String f019 = "[19]";
    public static final String f02 = "[2]";
    public static final String f020 = "[20]";
    public static final String f021 = "[21]";
    public static final String f022 = "[22]";
    public static final String f023 = "[23]";
    public static final String f024 = "[24]";
    public static final String f03 = "[3]";
    public static final String f04 = "[4]";
    public static final String f05 = "[5]";
    public static final String f06 = "[6]";
    public static final String f07 = "[7]";
    public static final String f08 = "[8]";
    public static final String f09 = "[9]";
    private static final Spannable.Factory spannableFactory = Spannable.Factory.getInstance();

    static
    {
        emoticons = new HashMap();
        addPattern(emoticons, "[0]", R.mipmap.emoji_00);
        addPattern(emoticons, "[1]", R.mipmap.emoji_00);
        addPattern(emoticons, "[2]", R.mipmap.emoji_00);
        addPattern(emoticons, "[3]", R.mipmap.emoji_00);
        addPattern(emoticons, "[4]", R.mipmap.emoji_00);
        addPattern(emoticons, "[5]", R.mipmap.emoji_00);
        addPattern(emoticons, "[6]", R.mipmap.emoji_00);
        addPattern(emoticons, "[7]", R.mipmap.emoji_00);
        addPattern(emoticons, "[8]", R.mipmap.emoji_00);
        addPattern(emoticons, "[9]", R.mipmap.emoji_00);
        addPattern(emoticons, "[10]", R.mipmap.emoji_00);
        addPattern(emoticons, "[11]", R.mipmap.emoji_00);
        addPattern(emoticons, "[12]", R.mipmap.emoji_00);
        addPattern(emoticons, "[13]", R.mipmap.emoji_00);
        addPattern(emoticons, "[14]", R.mipmap.emoji_00);
        addPattern(emoticons, "[15]", R.mipmap.emoji_00);
        addPattern(emoticons, "[16]", R.mipmap.emoji_00);
        addPattern(emoticons, "[17]", R.mipmap.emoji_00);
        addPattern(emoticons, "[18]", R.mipmap.emoji_00);
        addPattern(emoticons, "[19]", R.mipmap.emoji_00);
        addPattern(emoticons, "[20]", R.mipmap.emoji_00);
        addPattern(emoticons, "[21]", R.mipmap.emoji_00);
        addPattern(emoticons, "[22]", R.mipmap.emoji_00);
        addPattern(emoticons, "[23]", R.mipmap.emoji_00);

    }

    private static void addPattern(Map<Pattern, Integer> paramMap, String paramString, int paramInt)
    {
        paramMap.put(Pattern.compile(Pattern.quote(paramString)), Integer.valueOf(paramInt));
    }

    public static boolean addSmiles(Context paramContext, Spannable spannable)
    {
        boolean hasChanges = false;
        for (Map.Entry<Pattern, Integer> entry : emoticons.entrySet()) {
            Matcher matcher = entry.getKey().matcher(spannable);
            while (matcher.find()) {
                boolean set = true;
                for (ImageSpan span : spannable.getSpans(matcher.start(),
                        matcher.end(), ImageSpan.class))
                    if (spannable.getSpanStart(span) >= matcher.start()
                            && spannable.getSpanEnd(span) <= matcher.end())
                        spannable.removeSpan(span);
                    else {
                        set = false;
                        break;
                    }
                if (set) {
                    hasChanges = true;
                    spannable.setSpan(new ImageSpan(paramContext, entry.getValue()),
                            matcher.start(), matcher.end(),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        }
        return hasChanges;
    }

    public static boolean containsKey(String paramString)
    {
        Iterator localIterator = emoticons.entrySet().iterator();
        do
            if (!localIterator.hasNext())
                return false;
        while (!((Pattern)((Map.Entry)localIterator.next()).getKey()).matcher(paramString).find());
        return true;
    }

    public static Spannable getSmiledText(Context paramContext, CharSequence paramCharSequence)
    {
        Spannable localSpannable = spannableFactory.newSpannable(paramCharSequence);
        addSmiles(paramContext, localSpannable);
        return localSpannable;
    }
}
