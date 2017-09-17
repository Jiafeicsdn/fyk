package com.lvgou.distribution.utils;

import com.lvgou.distribution.constants.Url;

/**
 * Created by Administrator on 2016/9/29.
 */
public class ImageUtils {

    private static ImageUtils imageUtils;

    public static ImageUtils getInstance() {
        if (imageUtils == null) {
            return new ImageUtils();
        }
        return imageUtils;
    }

    public String getPath(String distributorid) {
        int a = Integer.parseInt(distributorid) / 250000;
        int b = Integer.parseInt(distributorid) % 250000;
        int c = b / 500;
        int d = Integer.parseInt(distributorid) % 500;
        String path = Url.ROOT + "/UploadFile/Face/Distributor/" + a + "/" + c + "/" + d + ".jpg";
        path=path+ "?v="+System.currentTimeMillis()/3600000;
        return path;
    }
    public String getPath2(String distributorid) {
        int a = Integer.parseInt(distributorid) / 250000;
        int b = Integer.parseInt(distributorid) % 250000;
        int c = b / 500;
        int d = Integer.parseInt(distributorid) % 500;
        String path = Url.ROOT + "/UploadFile/Face/Distributor/" + a + "/" + c + "/" + d + ".jpg";
        return path;
    }
}
