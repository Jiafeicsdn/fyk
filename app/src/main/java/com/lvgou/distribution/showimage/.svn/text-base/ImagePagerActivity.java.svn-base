package com.lvgou.distribution.showimage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.util.LogUtils;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.utils.MyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ImagePagerActivity extends FragmentActivity {
    private static final String STATE_POSITION = "STATE_POSITION";
    public static final String EXTRA_IMAGE_INDEX = "image_index";
    public static final String EXTRA_IMAGE_URLS = "image_urls";

    private TextView tv_save;
    private HackyViewPager mPager;
    private int pagerPosition;
    private TextView indicator;
    private List<String> urls;
    private String img_path;

    private List<String> imgs = new ArrayList<String>();

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int ss = msg.arg1;
            if (ss == 7) {
                // 广播通知图库更新
                MyToast.show(ImagePagerActivity.this, "保存成功");
            } else if (ss == 8) {
                MyToast.show(ImagePagerActivity.this, "保存失败");
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_detail_pager);

        pagerPosition = Integer.parseInt(getIntent().getStringExtra(EXTRA_IMAGE_INDEX));
        String paths = getIntent().getStringExtra(EXTRA_IMAGE_URLS);
        urls = new ArrayList<String>();
        urls.add(paths);
//        try {
//            JSONArray array = new JSONArray(paths);
//            urls = new ArrayList<String>();
//            if (array != null && array.length() > 0) {
//                for (int i = 0; i < array.length(); i++) {
//                    JSONObject jsonObject = array.getJSONObject(i);
//                    String pic_path = Url.ROOT + jsonObject.getString("fileUrl");
//                    urls.add(pic_path);
//                }
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        mPager = (HackyViewPager) findViewById(R.id.pager);
        ImagePagerAdapter mAdapter = new ImagePagerAdapter(
                getSupportFragmentManager(), urls);
        mPager.setAdapter(mAdapter);
        indicator = (TextView) findViewById(R.id.indicator);
        tv_save = (TextView) findViewById(R.id.tv_save);

        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyToast.show(ImagePagerActivity.this, "正在保存");
                imgs.add(img_path);
                downimage(imgs);
            }
        });

        CharSequence text = getString(R.string.viewpager_indicator, 1, mPager
                .getAdapter().getCount());
        indicator.setText(text);
        // 更新下标
        mPager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageSelected(int arg0) {
                CharSequence text = getString(R.string.viewpager_indicator,
                        arg0 + 1, mPager.getAdapter().getCount());
                indicator.setText(text);
            }

        });
        if (savedInstanceState != null) {
            pagerPosition = savedInstanceState.getInt(STATE_POSITION);
        }

        mPager.setCurrentItem(pagerPosition);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_POSITION, mPager.getCurrentItem());
    }

    private class ImagePagerAdapter extends FragmentStatePagerAdapter {

        public List<String> fileList;

        public ImagePagerAdapter(FragmentManager fm, List<String> fileList) {
            super(fm);
            this.fileList = fileList;
        }

        @Override
        public int getCount() {
            return fileList == null ? 0 : fileList.size();
        }

        @Override
        public Fragment getItem(int position) {
            String url = fileList.get(position);
            img_path = fileList.get(position);
            return ImageDetailFragment.newInstance(url);
        }
    }

    // 根据url下载图片，并保存至sd卡
    protected void downimage(final List<String> url) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                Message msg = new Message();

                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    // 获取SDCard根目录
                    String sdcard = Environment.getExternalStorageDirectory() + "/";
                    // 这个是要保存的目录
                    String filepath = sdcard + "LGDistribution" + "/Images/";
                    try {
                        for (int i = 0; i < url.size(); i++) {
                            URL fileUrl = new URL(url.get(i));
                            HttpURLConnection conn = (HttpURLConnection) fileUrl.openConnection();
                            InputStream is = conn.getInputStream();
                            BufferedInputStream bis = new BufferedInputStream(is);
                            // 根据当前时间给下载的文件重新命名
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
                            Date curDate = new Date(System.currentTimeMillis());
                            String fileName = formatter.format(curDate) + "."
                                    + url.get(i).substring(url.get(i).lastIndexOf(".") + 1);
                            File dir = new File(filepath);
                            if (!dir.exists()) {
                                dir.mkdirs();
                            }
                            File file = new File(filepath, fileName);
                            file.createNewFile();
                            FileOutputStream output = new FileOutputStream(file);
                            byte[] buffer = new byte[1024];
                            int len = -1;
                            while ((len = bis.read(buffer)) != -1) {
                                output.write(buffer, 0, len);
                            }
                            output.flush();
                            output.close();
                            is.close();
                            // 广播通知图库更新
                            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                            Uri uri1 = Uri.fromFile(file);
                            intent.setData(uri1);
                            sendBroadcast(intent);
                            // 每下一张图休眠一秒，防止文件命名相同
                            Thread.sleep(1000);
                        }
                        // 下载完成，传递值7代表下载完成
                        msg.arg1 = 7;
                        handler.sendMessage(msg);

                    } catch (Exception e) {
                        // 如果下载出现异常，传递值8代表下载失败
                        e.printStackTrace();
                        msg.arg1 = 8;
                        handler.sendMessage(msg);
                    }
                }
            }
        }).start();
    }
}