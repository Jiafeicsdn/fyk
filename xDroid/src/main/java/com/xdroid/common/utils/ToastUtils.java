package com.xdroid.common.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;

import com.xdroid.view.XDroidCustomToast;
import com.xdroid.widget.slidetoast.Configuration;
import com.xdroid.widget.slidetoast.SlideToast;
import com.xdroid.widget.slidetoast.Style;

/**
 * ToastUtils
 * 
 * @author Robin
 *  time 2014-12-30 17:24:16
 */
public class ToastUtils {
	
    public static void show(Context context, int resId) {
        show(context, context.getResources().getText(resId), Toast.LENGTH_SHORT);
    }

    public static void show(Context context, int resId, int duration) {
        show(context, context.getResources().getText(resId), duration);
    }

    public static void show(Context context, CharSequence text) {
        show(context, text, Toast.LENGTH_SHORT);
    }

    public static void show(Context context, CharSequence text, int duration) {
        Toast.makeText(context, text, duration).show();
    }

    public static void show(Context context, int resId, Object... args) {
        show(context, String.format(context.getResources().getString(resId), args), Toast.LENGTH_SHORT);
    }

    public static void show(Context context, String format, Object... args) {
        show(context, String.format(format, args), Toast.LENGTH_SHORT);
    }

    public static void show(Context context, int resId, int duration, Object... args) {
        show(context, String.format(context.getResources().getString(resId), args), duration);
    }

    public static void show(Context context, String format, int duration, Object... args) {
        show(context, String.format(format, args), duration);
    }
    
    /**
     * 弹出自定义视图toast，
     * @param context 上下文
     * @param v 自定义视图
     */
    public static void showCustomToast(Context context,View v){
    	XDroidCustomToast.makeText(context, v).show();
    }
    
    /**
     * 弹出自定义视图，自定义时长的Toast
     * @param context 上下文
     * @param v 自定义视图
     * @param duration 显示时间
     */
    public static void showCustomToast(Context context,View v,int duration){
    	XDroidCustomToast.makeText(context, v, duration).show();
    }
    
    /*———————————————————————————————————SlideToast显示文字相关静态函数—————————————————————————————————————————————*/   
    
    /**
     * 简单显示SlideToast（默认蓝色）
     * @param context 上下文
     * @param text 显示内容
     */
    public static void showSlideToast(Context context,String text) {
//		showSlideToast(context, text, Style.INFO);
    	showSlideToast(context,text,Style.INFO,1000);
	}
    
    /**
     * 显示SlideToast,可选style颜色风格
     * @param context 上下文
     * @param text 显示内容
     * @param style 风格（Style.ALERT 红色，Style.CONFIRM 绿色，Style.INFO 蓝色）
     */
    public static void showSlideToast(Context context,String text,Style style) {
//    	SlideToast.makeText((Activity)context, text, style).show();
    	showSlideToast(context,text,style,1000);
	}
    
    /**
     * 显示SlideToast,可指定在哪个ViewGroup中显示
     * @param context 上下文
     * @param text 显示文字
     * @param style 风格（Style.ALERT 红色，Style.CONFIRM 绿色，Style.INFO 蓝色）
     * @param viewGroup 显示在此viewGroup中
     */
    public static void showSlideToast(Context context,String text,Style style,ViewGroup viewGroup) {
    	showSlideToast(context, style, text, null, viewGroup, 0, false, null);
	}
    
    /**
     * 显示SlideToast,可设置显示时长
     * @param context 上下文
     * @param text 显示文字
     * @param style 风格（Style.ALERT 红色，Style.CONFIRM 绿色，Style.INFO 蓝色）
     * @param duration 时长
     */
    public static void showSlideToast(Context context,String text,Style style,int duration) {
    	showSlideToast(context, style, text, null, null, duration, false, null);
	}
    
    /**
     *  显示SlideToast,可设置是否无限显示，不消失（注意：设置infinite为true时，最好在Activity的onDestroy( )生命周期函数中调用hideSlideToast( )函数）
     * @param context 上下文
     * @param text 显示文字
     * @param style 风格（Style.ALERT 红色，Style.CONFIRM 绿色，Style.INFO 蓝色）
     * @param infinite 是否无限显示
     * @return slideToast SlideToast对象
     */
    public static SlideToast showSlideToast(Context context,String text,Style style,Boolean infinite) {
    	SlideToast slideToast=showSlideToast(context, style, text, null, null, 0, infinite, null);
    	return slideToast;
	}
    
    /**
     * 显示SlideToast,可设置是否无限显示，不消失，同时设置SlideToast的点击事件（注意：设置infinite为true时，最好在Activity的onDestroy( )生命周期函数中调用hideSlideToast( )函数）
     * @param context 上下文
     * @param text 显示文字
     * @param style 风格（Style.ALERT 红色，Style.CONFIRM 绿色，Style.INFO 蓝色）
     * @param infinite 是否无限显示
     * @param onSlideToastClickListener SlideToast点击回调函数
     * @returnSlideToast对象，若需要销毁SlideToast,可调用hideSlideToast( )函数即可
     */
    public static SlideToast showSlideToast(Context context,String text,Style style,Boolean infinite,OnSlideToastClickListener onSlideToastClickListener) {
    	SlideToast slideToast=showSlideToast(context, style, text, null, null, 0, infinite, onSlideToastClickListener);
    	return slideToast;
	}
    
    /**
     * 显示SlideToast,可设置显示时长，在哪个ViewGroup中显示
     * @param context 上下文
     * @param text 显示文字
     * @param style  风格（Style.ALERT 红色，Style.CONFIRM 绿色，Style.INFO 蓝色）
     * @param duration 显示时长
     * @param viewGroup 显示在此 viewGroup 中
     */
    public static void showSlideToast(Context context,String text,Style style,int duration,ViewGroup viewGroup) {
    	showSlideToast(context, style, text, null, viewGroup, duration, false, null);
	}
    
    /**
     * 显示SlideToast,可设置是否无限显示，不消失，同时设置哪个ViewGroup中显示（注意：设置infinite为true时，最好在Activity的onDestroy( )生命周期函数中调用hideSlideToast( )函数）
     * @param context 上下文
     * @param text 显示文字
     * @param style 风格（Style.ALERT 红色，Style.CONFIRM 绿色，Style.INFO 蓝色）
     * @param infinite 是否无限显示
     * @param viewGroup 显示在此 viewGroup 中
     */
    public static void showSlideToast(Context context,String text,Style style,Boolean infinite,ViewGroup viewGroup) {
    	showSlideToast(context, style, text, null, viewGroup, 0, infinite, null);
	}
    
    /**
     * 显示SlideToast,可设置是否无限显示，不消失，同时设置哪个ViewGroup中显示与SlideToast的点击事件（注意：设置infinite为true时，最好在Activity的onDestroy( )生命周期函数中调用hideSlideToast( )函数）
     * @param context 上下文
     * @param text 显示文字
     * @param style 风格（Style.ALERT 红色，Style.CONFIRM 绿色，Style.INFO 蓝色）
     * @param infinite  是否无限显示
     * @param viewGroup 显示在此 viewGroup 中
     * @param onSlideToastClickListener SlideToast点击回调函数
     * @return SlideToast对象，若需要销毁SlideToast,可调用hideSlideToast( )函数即可
     */
    public static SlideToast showSlideToast(Context context,String text,Style style,Boolean infinite,ViewGroup viewGroup,OnSlideToastClickListener onSlideToastClickListener) {
    	SlideToast slideToast=showSlideToast(context, style, text, null, viewGroup, 0, infinite, onSlideToastClickListener);
    	return slideToast;
	}
    
    
    /*———————————————————————————————————SlideToast显示自定义View相关静态函数—————————————————————————————————————————————*/
    
    /**
     * 显示SlideToast,设置自定义view
     * @param context 上下文
     * @param customView 自定义View
     */
    public static void showSlideToast(Context context,View customView) {
    	SlideToast.make((Activity)context, customView).show();
	}
    
    /**
     * 显示SlideToast,设置自定义view，可指定在哪个ViewGroup中显示
     * @param context 上下文
     * @param customView 自定义View
     * @param style 风格
     * @param viewGroup 显示在此ViewGroup中
     */
    public static void showSlideToast(Context context,View customView,Style style,ViewGroup viewGroup) {
    	showSlideToast(context, style, null, customView, viewGroup, 0, false, null);
	}
    
    /**
     * 显示SlideToast,设置自定义view，可指定显示时长
     * @param context 上下文
     * @param customView 自定义view
     * @param style 风格
     * @param duration 显示时长
     */
    public static void showSlideToast(Context context,View customView,Style style,int duration) {
    	showSlideToast(context, style, null, customView, null, duration, false, null);
	}
    
    /**
     * 显示SlideToast,设置自定义view，可设置是否无限显示不消失（注意：设置infinite为true时，最好在Activity的onDestroy( )生命周期函数中调用hideSlideToast( )函数）
     * @param context 上下文
     * @param customView 自定义view
     * @param style 风格
     * @param infinite 是否无限显示，不消失\
     * @return slideToast SlideToast对象
     */
    public static SlideToast showSlideToast(Context context,View customView,Style style,Boolean infinite) {
    	SlideToast slideToast=showSlideToast(context, style, null, customView, null, 0, infinite, null);
    	return slideToast;
	}
    
    /**
     *  显示SlideToast,设置自定义view，可设置是否无限显示不消失，可设置点击回调（注意：1.点击事件最好直接对customView设置点击事件 2.设置infinite为true时，最好在Activity的onDestroy( )生命周期函数中调用hideSlideToast( )函数）
     * @param context 上下文
     * @param customView 自定义view
     * @param style 风格
     * @param infinite 是否无限显示，不消失
     * @param onSlideToastClickListener  SlideToast点击回调函数
     * @return SlideToast对象，若需要销毁SlideToast,可调用hideSlideToast( )函数即可
     */
    public static SlideToast showSlideToast(Context context,View customView,Style style,Boolean infinite,OnSlideToastClickListener onSlideToastClickListener) {
    	SlideToast slideToast=showSlideToast(context, style, null, customView, null, 0, infinite, onSlideToastClickListener);
    	return slideToast;
	}
    
    /**
     *   显示SlideToast,设置自定义view，可设置显示时长，指定在哪个ViewGroup中显示
     * @param context 上下文
     * @param customView 自定义view
     * @param style 风格
     * @param duration 显示时长
     * @param viewGroup 显示在此viewgroup中
     */
    public static void showSlideToast(Context context,View customView,Style style,int duration,ViewGroup viewGroup) {
    	showSlideToast(context, style, null, customView, viewGroup, duration, false, null);
	}
    
    /**
     *  显示SlideToast,设置自定义view，可设置是否无限显示，指定在哪个ViewGroup中显示（注意：设置infinite为true时，最好在Activity的onDestroy( )生命周期函数中调用hideSlideToast( )函数）
     * @param context 上下文
     * @param customView 自定义view
     * @param style 风格
     * @param infinite 是否无限显示
     * @param viewGroup 显示在此viewgroup中
     */
    public static void showSlideToast(Context context,View customView,Style style,Boolean infinite,ViewGroup viewGroup) {
    	showSlideToast(context, style, null, customView, viewGroup, 0, infinite, null);
	}
    
    /**
     *  显示SlideToast,设置自定义view，可设置是否无限显示，指定在哪个ViewGroup中显示，SlideToast点击回调（注意：1.点击事件最好直接对customView设置点击事件 2.设置infinite为true时，最好在Activity的onDestroy( )生命周期函数中调用hideSlideToast( )函数）
     * @param context 上下文
     * @param customView 自定义view
     * @param style 风格
     * @param infinite 是否无限显示
     * @param viewGroup 显示在此viewgroup中
     * @param onSlideToastClickListener  SlideToast点击回调函数
     * @return SlideToast对象，若需要销毁SlideToast,可调用hideSlideToast( )函数即可
     */
    public static SlideToast showSlideToast(Context context,View customView,Style style,Boolean infinite,ViewGroup viewGroup,OnSlideToastClickListener onSlideToastClickListener) {
    	SlideToast slideToast=showSlideToast(context, style, null, customView, viewGroup, 0, infinite, onSlideToastClickListener);
    	return slideToast;
	}
    
    
    /**
     * 显示SlideToast，重载根函数，其他静态函数由此派生
     * @param context 上下文
     * @param style 风格（Style.ALERT 红色，Style.CONFIRM 绿色，Style.INFO 蓝色）
     * @param text 显示文字，与customView只能存其一
     * @param customView 显示自定义view，与text只能存其一
     * @param viewGroup 显示在哪个viewgroup里，为null则显示在最顶部
     * @param duration 显示时长
     * @param infinite 是否无限显示，弱设置为true，则duration时长无效
     * @param onSlideToastClickListener SlideToast点击回调
     * @return SlideToast对象，若需要销毁SlideToast,可调用hideSlideToast( )函数即可
     */
    public static SlideToast showSlideToast(Context context,Style style,String text,View customView,ViewGroup viewGroup,int duration,Boolean infinite,final OnSlideToastClickListener onSlideToastClickListener) {
    	if (style==null) {
    	  	style = new Style.Builder().build();
		}
    	
    	SlideToast slideToast=null;
    	if (text==null&&customView!=null) {
    		if (viewGroup==null) {
    			slideToast=SlideToast.make((Activity)context, customView);
			}else {
				slideToast=SlideToast.make((Activity)context, customView,viewGroup);
			}
    		
		}else if (customView==null&&text!=null) {
			if (viewGroup==null) {
				slideToast=SlideToast.makeText((Activity)context, text,style);
			}else {
				slideToast=SlideToast.makeText((Activity)context, text,style,viewGroup);
			}
			
		}
    	
    	if (onSlideToastClickListener!=null) {
    		slideToast.setOnClickListener(new OnClickListener() {
    			
    			@Override
    			public void onClick(View v) {
    				onSlideToastClickListener.onSlideToastClick();
    			}
    		});
		}
    	
    	if (infinite) {  //如果无限显示则直接显示，否则判断时间
    		Configuration configuration = new Configuration.Builder().setDuration(Configuration.DURATION_INFINITE).build();
    		slideToast.setConfiguration(configuration);
		}else {
			if (duration!=0) {
	    		Configuration configuration = new Configuration.Builder().setDuration(duration).build();
	    		slideToast.setConfiguration(configuration);
			}
		}
	
    	slideToast.show();
    	return slideToast;
	}
    
    /**
     * 销毁SlideToast
     * @param slideToast SlideToast对象
     */
    public static void hideSlideToast(SlideToast  slideToast){
    	if (slideToast!=null) {
			SlideToast.hide(slideToast);
			slideToast=null;
		}
    }
    
    /**
     * SlideToast点击回调接口
     * @author Robin
     * time 2015-03-02 15:53:54
     *
     */
    public interface OnSlideToastClickListener{
    	public void onSlideToastClick();
    }
    
}
