package com.lvgou.distribution.widget;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.BaseActivity;
import com.lvgou.distribution.bean.BannerBean;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * @author xiansenxuan
 *         首页轮播图
 */
public class LamaAdViewPage extends RelativeLayout {
    public static final int OPEN_AD_REQUEST_CODE = 110;
    BaseActivity activity;
    private Context context;
    private BannerItemCallback bannerItemCallback;
    SawtoothView sawtooh;
    /**
     * 包含 指示点的布局
     */
    private LinearLayout pointGroup;
    private ViewPager viewPager;
    private int width;// 屏幕宽度
    /**
     * 圆点列表
     */
    private ArrayList<ImageView> imageList = new ArrayList<ImageView>();

    /**
     * 图片列表
     */
    private ArrayList<ImageView> picList = new ArrayList<ImageView>();

    // private DisplayImageOptions options = new DisplayImageOptions.Builder()
    // .showImageOnLoading(R.color.lmh_bg_color) //设置图片在下载期间显示的图片
    // .showImageForEmptyUri(R.color.lmh_bg_color)//设置图片Uri为空或是错误的时候显示的图片
    // .showImageOnFail(R.color.lmh_bg_color) //设置图片加载/解码过程中错误时候显示的图片
    // .cacheInMemory(true)//设置下载的图片是否缓存在内存中
    // .cacheOnDisk(true)//设置下载的图片是否缓存在SD卡中
    // .imageScaleType(ImageScaleType.IN_SAMPLE_INT)//设置图片以如何的编码方式显示
    // .bitmapConfig(Config.RGB_565)//设置图片的解码类型
    // .build();//构建完成

    /**
     * 上一个指示点的位置
     */

    public LamaAdViewPage(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public LamaAdViewPage(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init();
    }

    public LamaAdViewPage(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public void setBannerItemListener(BannerItemCallback bannerItemListener) {
        bannerItemCallback = bannerItemListener;
    }

    List<BannerBean> listAdvert = new ArrayList<BannerBean>();

    public void setViewPageHeight() {
        // 根据比例进行计算 先获取到屏幕的宽度

        android.view.ViewGroup.LayoutParams params = viewPager
                .getLayoutParams();
        params.height = width * 334 / 720;
        viewPager.setLayoutParams(params);

    }

    /**
     * 控件的初始化
     */
    private void init() {
        View layoutHeader = View.inflate(getContext(),
                R.layout.activity_ad_top, this);
        WindowManager wm = (WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE);
        findViewById(R.id.view_pager).getLayoutParams().height = (int) (wm
                .getDefaultDisplay().getWidth() * 0.31f);
        width = wm.getDefaultDisplay().getWidth();
        // setVisibility(View.GONE);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        setViewPageHeight();
        pointGroup = (LinearLayout) findViewById(R.id.point_group);
//		setViewPagerScrollSpeed();// 设置滑动速度
//		lmHander = new LaMaHander(viewPager, imageList);
    }

    public void hideAdView() {
        viewPager.setVisibility(View.GONE);
    }

    /**
     * 设置页面下方有齿轮效果
     */
    public void setSawtooh() {
        sawtooh.setVisibility(VISIBLE);
    }

    /*
     * 设置ViewPager的滑动速度
     */
    private void setViewPagerScrollSpeed() {
        try {
            Field mScroller = null;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(
                    viewPager.getContext());
            mScroller.set(viewPager, scroller);
        } catch (NoSuchFieldException e) {

        } catch (IllegalArgumentException e) {

        } catch (IllegalAccessException e) {

        }
    }

    public void setActivity(BaseActivity activity) {
        this.activity = activity;

    }

    /**
     * 初始化数据 设置适配器
     */
    private void initAdapter() {
        //清空点的容器
        if (pointGroup != null && pointGroup.getChildCount() > 0) {
            for (int i = 0; i < pointGroup.getChildCount(); i++) {
                ((ImageView) pointGroup.getChildAt(i)).setImageDrawable(null);
            }
            pointGroup.removeAllViews();
        }
        if (listAdvert.size() == 2) {
            //此时需要+2
            for (int i = 0; i < listAdvert.size(); i++) {
                // 创建指示点
                ImageView imageView1 = new ImageView(getContext());
                initView(imageView1, 0);
                ImageLoader.getInstance().displayImage(
                        listAdvert.get(0).getPicUrl(), imageView1,
                        new AnimateFirstDisplayListener());
                imageList.add(imageView1);

                ImageView imageView2 = new ImageView(getContext());
                initView(imageView2, 1);
                ImageLoader.getInstance().displayImage(
                        listAdvert.get(1).getPicUrl(), imageView2,
                        new AnimateFirstDisplayListener());
                imageList.add(imageView2);

                ImageView point = new ImageView(context);
                point.setBackgroundResource(R.drawable.point_bg);
                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, -2);
                param.leftMargin = 15;
                param.rightMargin = 15;
                point.setLayoutParams(param);
                if (i == 0) {
                    point.setEnabled(true);
                } else {
                    point.setEnabled(false);
                }
                pointGroup.addView(point);
            }
        } else {
            for (int i = 0; i < listAdvert.size(); i++) {
                // 创建指示点
                ImageView imageView = new ImageView(getContext());
                initView(imageView, i);
                ImageLoader.getInstance().displayImage(
                        listAdvert.get(i).getPicUrl(), imageView,
                        new AnimateFirstDisplayListener());
                imageList.add(imageView);
                ImageView point = new ImageView(context);
                point.setBackgroundResource(R.drawable.point_bg);
                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, -2);
                param.leftMargin = 15;
                param.rightMargin = 15;
                point.setLayoutParams(param);
                if (i == 0) {
                    point.setEnabled(true);
                } else {
                    point.setEnabled(false);
                }
                pointGroup.addView(point);
            }
        }

        adapter = new MyPagerAdapter();
        //设置监听
        setViewPagerListen();
        viewPager.setAdapter(adapter);
//		int itemid = Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2
//				% imageList.size();

        int itemid = listAdvert.size() * 100 / 2;
        // 设置当前显示的位置
        if (listAdvert.size() == 1) {
            viewPager.setCurrentItem(0);
        } else {
            viewPager.setCurrentItem(itemid);
        }
    }

    private void initView(ImageView view, int position) {
        view.setScaleType(ScaleType.CENTER_CROP);
        if (listAdvert.size() == 2) {
            if (position == 2) {
                position = 0;
                view.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
//						Intent intent = new Intent(activity, AdActivity.class);
//						intent.putExtra("data",
//								new Gson().toJson(listAdvert2.get(0)));
//						activity.startActivity(intent);
                        bannerItemCallback.ItemClick(listAdvert2.get(0).getLinkUrl());
                    }
                });
            } else if (position == 3) {
                position = 1;
                view.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
//						Intent intent = new Intent(activity, AdActivity.class);
//						intent.putExtra("data",
//								new Gson().toJson(listAdvert2.get(1)));
//						activity.startActivity(intent);
                        bannerItemCallback.ItemClick(listAdvert2.get(1).getLinkUrl());
                    }
                });
            } else if (position == 0) {// 二张图0 1的情况
                view.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
//						Intent intent = new Intent(activity, AdActivity.class);
//						intent.putExtra("data",
//								new Gson().toJson(listAdvert2.get(0)));
//						activity.startActivity(intent);
                        bannerItemCallback.ItemClick(listAdvert2.get(0).getLinkUrl());
                    }
                });

            } else if (position == 1) {// 二张图0 1的情况
                view.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
//						Intent intent = new Intent(activity, AdActivity.class);
//						intent.putExtra("data",
//								new Gson().toJson(listAdvert2.get(1)));
//						activity.startActivity(intent);
                        bannerItemCallback.ItemClick(listAdvert2.get(1).getLinkUrl());
                    }
                });
            }
            //正常情况，非2张图
        } else {
            final int index = position;
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
//					Intent intent = new Intent(activity, AdActivity.class);
//					intent.putExtra("data",
//							new Gson().toJson(listAdvert2.get(index)));
//					activity.startActivityForResult(intent,OPEN_AD_REQUEST_CODE);
                    bannerItemCallback.ItemClick(listAdvert2.get(index).getLinkUrl());
                }
            });
        }
    }


    Timer mTimer;
    TimerTask mTask;
    int pageIndex = 1;
    boolean isTaskRun;

    /**
     * 设置viewpager监听
     */
    private void setViewPagerListen() {
        viewPager.setOnPageChangeListener(new OnPageChangeListener() {

            /* 更新手动滑动时的位置 */
            @Override
            public void onPageSelected(int position) {
                pageIndex = position;

                if (position > listAdvert.size() - 1) {
                    position = position % listAdvert.size();
                }

                for (int j = 0; j < pointGroup.getChildCount(); j++) {
                    pointGroup.getChildAt(j).setEnabled(false);
                }
                if (listAdvert.size() == 2) {
                    if (position == 2) {
                        position = 0;
                    }
                    if (position == 3) {
                        position = 1;
                    }
                    pointGroup.getChildAt(position).setEnabled(true);
                } else {
                    pointGroup.getChildAt(position).setEnabled(true);
                }

            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            /* state: 0空闲，1是滑行中，2加载完毕 */
            @Override
            public void onPageScrollStateChanged(int state) {
                // TODO Auto-generated method stub
				if (state == 0 && !isTaskRun) {
					setCurrentIndex();
					startTask();
				} else if (state == 1 && isTaskRun)
					stopTask();
            }
        });

    }

    /**
     * 开启定时任务
     */
    private void startTask() {
        if (listAdvert.size() > 1) {
            stopTask();
            // TODO Auto-generated method stub
            isTaskRun = true;
            if (mTimer == null) {
                mTimer = new Timer();
            }
            if (mTask == null) {
                mTask = new TimerTask() {
                    @Override
                    public void run() {
                        pageIndex++;
                        mHandler.sendEmptyMessage(0);
                    }
                };
            }
            mTimer.schedule(mTask, 4 * 1000, 4 * 1000);// 这里设置自动切换的时间，单位是毫秒，2*1000表示2秒
        }


    }

    // 处理EmptyMessage(0)
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            setCurrentIndex();
        }
    };

    /**
     * 处理Page的切换逻辑
     */
    public void setCurrentIndex() {
        if (pageIndex == 0 || pageIndex >= listAdvert.size() * 100 - 1) {
            pageIndex = listAdvert.size() * 100 / 2;
        }
//		viewPager.setCurrentItem(pageIndex);
        viewPager.setCurrentItem(pageIndex, false);// 取消动画
    }

    /**
     * 停止定时任务
     */
    private void stopTask() {
        // TODO Auto-generated method stub
        isTaskRun = false;
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        if (mTask != null) {
            mTask.cancel();
            mTask = null;
        }
    }

    public void onFree() {
        //stopTask();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    public void startCarousel() {
        startTask();
    }


    class MyPagerAdapter extends PagerAdapter {

        @Override
        /**
         * 返回页面的个数
         */
        public int getCount() {
            if (listAdvert.size() == 1) {
                return listAdvert.size();
            }
            return listAdvert.size() * 100;
        }

        @Override
        /**
         * 获得指定位置上的view
         * container 就是viewPager自身
         * position 是指定的位置
         */
        public Object instantiateItem(ViewGroup container, int position) {
            // 给container添加一个指定位置上的view对象
            position %= imageList.size();
            if (position < 0) {
                position = imageList.size() + position;
            }

            ImageView view = imageList.get(position);
            // 如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
            ViewParent vp = view.getParent();
            if (vp != null) {
                ViewGroup parent = (ViewGroup) vp;
                parent.removeView(view);
            }
            container.addView(view);
            return view;
        }

        @Override
        /**
         * 判断指定的的view和object是否有关联关系
         * view 某一位置上的显示的页面
         * object 某一位置上返回的object 就是instantiateItem返回的object
         */
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        /**
         * 销毁指定位置上的view
         * object 就是instantiateItem 返回的object
         */
        public void destroyItem(ViewGroup container, int position, Object object) {
//			container.removeView((View) object);
        }
    }

    List<BannerBean> listAdvert2 = new ArrayList<BannerBean>();
//	private LaMaHander lmHander;

    /**
     * @param listAdverts 设置当前页面下 图片 点 AdvertBean 数据
     */
    public void setViewData(List<BannerBean> listAdverts) {
        //停止先前的任务
        stopTask();
        //清空之前集合
        this.listAdvert2.clear();
        //清空之前集合
        this.listAdvert.clear();
//		//清空图片集合
//		if (picList != null && picList.size() > 0) {
//			for (ImageView imageView : picList) {
//				imageView.setImageDrawable(null);
//			}
//			picList.clear();
//		}
        //清空圆点集合
        if (imageList != null && imageList.size() > 0) {
            for (ImageView imageView : imageList) {
                imageView.setImageDrawable(null);
            }
            imageList.clear();
        }
        //循环传过来的值，添加到listAdvert2 listAdvert
        if (listAdverts != null && listAdverts.size() > 0) {
            // this.listAdvert2 = listAdvert2;
            // // 广告数据存在
            // this.listAdvert = listAdvert2;
            this.listAdvert2.addAll(listAdverts);
            this.listAdvert.addAll(listAdverts);
            setVisibility(View.VISIBLE);
            initAdapter();
        }
    }

    private MyPagerAdapter adapter;

    public interface BannerItemCallback {
        public void ItemClick(String url);
    }
}
