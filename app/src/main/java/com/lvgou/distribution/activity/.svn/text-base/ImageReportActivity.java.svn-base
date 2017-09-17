package com.lvgou.distribution.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.lvgou.distribution.utils.MyToast;import android.widget.Toast;

import com.lidroid.xutils.util.LogUtils;
import com.lvgou.distribution.R;
import com.lvgou.distribution.bean.DelPic;
import com.lvgou.distribution.constants.Constants;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.event.EventFactory;
import com.lvgou.distribution.presenter.DelImgPresenter;
import com.lvgou.distribution.request.RequestTask;
import com.lvgou.distribution.showimage.HackyViewPager;
import com.lvgou.distribution.showimage.ImageDetailFragment;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.DelImgView;
import com.xdroid.common.utils.PreferenceHelper;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.request.extension.XDroidRequest;
import com.xdroid.request.extension.config.DataType;
import com.xdroid.request.extension.impl.OnRequestListenerAdapter;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Snow on 2016/7/11 0011.
 * 大图查看
 */
public class ImageReportActivity extends FragmentActivity implements DelImgView {
    private static final String STATE_POSITION = "STATE_POSITION";
    public static final String EXTRA_IMAGE_INDEX = "image_index";
    public static final String EXTRA_IMAGE_URLS = "image_urls";

    private TextView tv_save;
    private HackyViewPager mPager;
    private int pagerPosition;
    private TextView indicator;
    private List<String> urls;
    private String img_path;
    private Dialog dialog;
    private List<String> imgs = new ArrayList<String>();
    private String distributorid = "";
    private DelImgPresenter delImgPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_report_pager);
        delImgPresenter = new DelImgPresenter(this);
        pagerPosition = Integer.parseInt(getIntent().getStringExtra(EXTRA_IMAGE_INDEX));
        String paths = getIntent().getStringExtra(EXTRA_IMAGE_URLS);
        distributorid = PreferenceHelper.readString(ImageReportActivity.this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);

        if (paths.trim().contains(",")) {
            String path_ = paths.substring(1, paths.length() - 1).toString().trim();
            String img_path[] = path_.split(", ");
            LogUtils.e(path_.toString());
            urls = new ArrayList<String>();
            for (int i = 0; i < img_path.length; i++) {
                String path_1 = Url.ROOT + img_path[i];
                urls.add(path_1);
            }
        } else {
            urls = new ArrayList<String>();
            String path = Url.ROOT + paths.substring(1, paths.length() - 1);
            urls.add(path);
        }
        mPager = (HackyViewPager) findViewById(R.id.pager);
        ImagePagerAdapter mAdapter = new ImagePagerAdapter(getSupportFragmentManager(), urls);
        mPager.setAdapter(mAdapter);
        indicator = (TextView) findViewById(R.id.indicator);
        tv_save = (TextView) findViewById(R.id.tv_save);
        if (Constants.IS_SHOW_ADD.equals("1")) {
            tv_save.setVisibility(View.GONE);
        }
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(ImageReportActivity.this,
                        R.style.Mydialog);
                View view1 = View.inflate(ImageReportActivity.this,
                        R.layout.delete_shop_dialog, null);
                TextView tv_content = (TextView) view1.findViewById(R.id.tv_content);
                Button sure = (Button) view1.findViewById(R.id.sure);
                Button cancle = (Button) view1.findViewById(R.id.cancle);
                tv_content.setText("确定删除吗？");
                sure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String path = "/UploadFile/" + urls.get(mPager.getCurrentItem()).split("/UploadFile/")[1];
                        String sign = TGmd5.getMD5(path);
                        delImgPresenter.delImg(path, sign);
                        dialog.dismiss();
                    }
                });
                cancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.setContentView(view1);
                dialog.show();
            }
        });

        CharSequence text = getString(R.string.viewpager_indicator, 1, mPager
                .getAdapter().getCount());
        indicator.setText(text);
        // 更新下标
        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

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

    @Override
    public void OnDelImgSuccCallBack(String state, String respanse) {
        EventBus.getDefault().post(new DelPic(mPager.getCurrentItem()));
        MyToast.makeText(this, "删除成功！", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void OnDelImgFialCallBack(String state, String respanse) {
        MyToast.makeText(this, "" + respanse, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void closeDelImgProgress() {

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

    private void deleteImage(String distributorid, String path, String sign) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("distributorid", distributorid);
        maps.put("path", path);
        maps.put("sign", sign);

        RequestTask.getInstance().doDeleteImage(ImageReportActivity.this, maps, new OnRequestListener());
    }

    private class OnRequestListener extends OnRequestListenerAdapter<Object> {
        @Override
        public void onDone(XDroidRequest<?> request, String response, DataType dataType) {
            super.onDone(request, response, dataType);
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if (status.equals("1")) {
                    MyToast.show(ImageReportActivity.this, "删除成功");
                    EventFactory.removeImagePostion(mPager.getCurrentItem());
                } else {
                    MyToast.show(ImageReportActivity.this, "删除失败");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}