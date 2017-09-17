package com.lvgou.distribution.utils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.lidroid.xutils.util.LogUtils;
import com.lvgou.distribution.constants.Constants;


public class Tools {
    private final static String ALBUM_PATH = Environment.getExternalStorageDirectory() + "/tugou/";

    public int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    public int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    public ArrayList<Double> getintfrommap(HashMap<Double, Double> map) {
        ArrayList<Double> dlk = new ArrayList<Double>();
        int position = 0;
        @SuppressWarnings("rawtypes")
        Set set = map.entrySet();
        @SuppressWarnings("rawtypes")
        Iterator iterator = set.iterator();

        while (iterator.hasNext()) {
            @SuppressWarnings("rawtypes")
            Map.Entry mapentry = (Map.Entry) iterator.next();
            dlk.add((Double) mapentry.getKey());
        }
        for (int i = 0; i < dlk.size(); i++) {
            int j = i + 1;
            position = i;
            Double temp = dlk.get(i);
            for (; j < dlk.size(); j++) {
                if (dlk.get(j) < temp) {
                    temp = dlk.get(j);
                    position = j;
                }
            }

            dlk.set(position, dlk.get(i));
            dlk.set(i, temp);
        }
        return dlk;

    }

    /**
     * @param bm
     * @param fileName
     * @throws IOException
     */
    public Boolean saveFile(Bitmap bm, String fileName) throws IOException {
        File dirFile = new File(ALBUM_PATH);
        fileName = fileName.replace("/", "_");
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        File myCaptureFile = new File(ALBUM_PATH + fileName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        Boolean b = bm.compress(Bitmap.CompressFormat.PNG, 80, bos);
        bos.flush();
        bos.close();
        return b;
    }

    public static String SavePhoto(Bitmap bm, String filePath) {
        try {
            File root = Environment.getExternalStorageDirectory();
            File dir = new File(root.getAbsolutePath() + File.separator
                    + Constants.BASE_PATH + "/" + "Image");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            // 创建文件
            File file = new File(dir, filePath);
            FileOutputStream out = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 20, out); //20  792
            out.flush();
            out.close();
            LogUtils.e("=====file size====" + (file.length() / 1024) + "kb");
            return dir.getPath() + "/" + filePath;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

//    private static Bitmap compressBmpFromBmp(Bitmap image) {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        int options = 100;
//        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        while (baos.toByteArray().length / 1024 > 500) {
//            baos.reset();
//            options -= 10;
//            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
//        }
//        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
//        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
//        return bitmap;
//    }

}
