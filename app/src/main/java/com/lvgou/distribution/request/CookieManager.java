package com.lvgou.distribution.request;

import android.content.Context;
import android.text.TextUtils;

import com.lidroid.xutils.util.LogUtils;
import com.lvgou.distribution.cache.DiskCacheReadTask;
import com.lvgou.distribution.cache.DiskCacheWriteTask;
import com.xdroid.common.utils.StringUtils;

import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;



/**
 * Cookie 管理
 * @author Robin
 * @since 2015-05-28 09:16:05
 */
public class CookieManager{

	private CookieManager() {
	}

	private static class CookieManagerHolder {
		public static final CookieManager INSTANCE = new CookieManager();
	}

	public static CookieManager getInstance() {
		return CookieManagerHolder.INSTANCE;
	}

	static final String COOKIES_HEADER = "Set-Cookie";
	HttpURLConnection connection;
	static java.net.CookieManager msCookieManager = new java.net.CookieManager();

	/**
	 * 保存Cookie至CookieManager
	 * @param connection
	 */
	public void saveCookies(Context context,HttpURLConnection connection) {
		this.connection = connection;
		Map<String, List<String>> headerFields = connection.getHeaderFields();
		List<String> cookiesHeader = headerFields.get(COOKIES_HEADER);

		if (cookiesHeader != null) {
			for (String cookie : cookiesHeader) {
				msCookieManager.getCookieStore().add(null,
						HttpCookie.parse(cookie).get(0));
			}
		}

        //Save to Disk
        if (!StringUtils.isEmpty(getCookies(context))){
            DiskCacheWriteTask.getInstance().saveCookie(context,getCookies(context));
        }

        LogUtils.i("存储Cookie:"+getCookies(context));
	}

	/**
	 * 从CookieManager中获取Cookie
	 * @return
	 */
	public String getCookies(Context context) {
		if (msCookieManager.getCookieStore().getCookies().size() > 0) {
//			connection.setRequestProperty("Cookie", TextUtils.join(",",msCookieManager.getCookieStore().getCookies()));
            String cookie=TextUtils.join(";",msCookieManager.getCookieStore().getCookies()).toString();
			return cookie;
		}

        String cookie= DiskCacheReadTask.getInstance().readCookie(context);
        if (!StringUtils.isEmpty(cookie)){
            return cookie;
        }
		return "";
	}

    /**
     * 清除Cookie
     */
    public void clearCookies(){
        msCookieManager.getCookieStore().removeAll();
    }

}