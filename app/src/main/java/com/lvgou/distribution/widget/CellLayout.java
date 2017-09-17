package com.lvgou.distribution.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.utils.DensityUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/4/13.
 */

public class CellLayout extends RelativeLayout {

    int width;
    float unitWidth;
    int px = 5;
    List<ImageView> imgs = new ArrayList<>();
    int img_num = 0;
    private Context context;
    private ArrayList<Object> images = new ArrayList<>();
    private ArrayList<String> images2 = new ArrayList<>();

    public CellLayout(Context context) {
        super(context);
    }

    public CellLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CellLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        unitWidth = w * 1f / 3;
    }

    public void setData(ArrayList<HashMap<String, Object>> data, Context context) {

        this.context = context;
        images.clear();
        images2.clear();
        for (HashMap<String, Object> stringObjectHashMap : data) {
            images.add(Url.ROOT + stringObjectHashMap.get("smallPicUrl"));
            images2.add(Url.ROOT + stringObjectHashMap.get("picUrl"));
        }

        Log.i("DSfdsafasdfdsafdsadsaf", "------------3------------" + images.toString());
        img_num = images.size();
        int i = DensityUtil.dip2px(context, 10);
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        width = metrics.widthPixels-DensityUtil.dip2px(context, 38);
        unitWidth = (width - 2 * i) * 1f / 3;
        removeAllViews();
        imgs.clear();
        if (data != null && !data.isEmpty()) {
            if (img_num == 1) {
                //一张图片
                LayoutParams params1 = new LayoutParams((int) unitWidth*3 - 2 * px, (int) (unitWidth*3));
                params1.topMargin = 0;
                params1.leftMargin = 0;
                ImageView imageView1 = new ImageView(getContext());
                imageView1.setLayoutParams(params1);
                imgs.add(imageView1);
                imageView1.setScaleType(ImageView.ScaleType.CENTER_CROP);

                Glide.with(context)
                        .load(images2.get(0))
                        /*.placeholder(R.drawable.gridview_addpic)*/
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL) // 设置本地缓存,缓存源文件和目标图像
                        .error(R.mipmap.pictures_no)
                        .into(imageView1);

                imageView1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(images2, 0);
                        }
                    }
                });


            } else if (img_num == 2) {

                LayoutParams params1 = new LayoutParams((int) unitWidth*3/2 - 2 * px, (int) (unitWidth*3/2));
                params1.topMargin = 0;
                params1.leftMargin = 0;
                ImageView imageView1 = new ImageView(getContext());
                imageView1.setLayoutParams(params1);
                imgs.add(imageView1);
                imageView1.setScaleType(ImageView.ScaleType.CENTER_CROP);


                Glide.with(context)
                        .load(images2.get(0))
                        /*.placeholder(R.drawable.gridview_addpic)*/
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL) // 设置本地缓存,缓存源文件和目标图像
                        .error(R.mipmap.pictures_no)
                        .into(imageView1);

                imageView1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(images2, 0);
                        }
                    }
                });

                LayoutParams params2 = new LayoutParams((int) unitWidth*3/2 - 2 * px, (int) (unitWidth*3/2));
                params2.topMargin = 0;
                params2.leftMargin = (int) unitWidth*3/2;
                ImageView imageView2 = new ImageView(getContext());
                imageView2.setLayoutParams(params2);
                imgs.add(imageView2);
                imageView2.setScaleType(ImageView.ScaleType.CENTER_CROP);


                Glide.with(context)
                        .load(images2.get(1))
                        /*.placeholder(R.drawable.gridview_addpic)*/
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL) // 设置本地缓存,缓存源文件和目标图像
                        .error(R.mipmap.pictures_no)
                        .into(imageView2);

                imageView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(images2, 1);
                        }
                    }
                });


            } else if (img_num == 3) {

                LayoutParams params1 = new LayoutParams((int) unitWidth - 2 * px, (int) (unitWidth));
                params1.topMargin = 0;
                params1.leftMargin = 0;
                ImageView imageView1 = new ImageView(getContext());
                imageView1.setLayoutParams(params1);
                imgs.add(imageView1);
                imageView1.setScaleType(ImageView.ScaleType.CENTER_CROP);

                Glide.with(context)
                        .load(images.get(0))
                        /*.placeholder(R.drawable.gridview_addpic)*/
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL) // 设置本地缓存,缓存源文件和目标图像
                        .error(R.mipmap.pictures_no)
                        .into(imageView1);

                imageView1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(images2, 0);
                        }
                    }
                });

                LayoutParams params2 = new LayoutParams((int) unitWidth - 2 * px, (int) (unitWidth));
                params2.topMargin = 0;
                params2.leftMargin = (int) unitWidth;
                ImageView imageView2 = new ImageView(getContext());
                imageView2.setLayoutParams(params2);
                imgs.add(imageView2);
                imageView2.setScaleType(ImageView.ScaleType.CENTER_CROP);

                Glide.with(context)
                        .load(images.get(1))
                       /* .placeholder(R.drawable.gridview_addpic)*/
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL) // 设置本地缓存,缓存源文件和目标图像
                        .error(R.mipmap.pictures_no)
                        .into(imageView2);

                imageView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(images2, 1);
                        }
                    }
                });

                LayoutParams params3 = new LayoutParams((int) unitWidth - 2 * px, (int) (unitWidth));
                params3.topMargin = 0;
                params3.leftMargin = (int) (2 * unitWidth);
                ImageView imageView3 = new ImageView(getContext());
                imageView3.setLayoutParams(params3);
                imgs.add(imageView3);
                imageView3.setScaleType(ImageView.ScaleType.CENTER_CROP);

                Glide.with(context)
                        .load(images.get(2))
                        /*.placeholder(R.drawable.gridview_addpic)*/
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL) // 设置本地缓存,缓存源文件和目标图像
                        .error(R.mipmap.pictures_no)
                        .into(imageView3);

                imageView3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(images2, 2);
                        }
                    }
                });

            } else if (img_num == 4) {

                LayoutParams params1 = new LayoutParams((int) unitWidth*3/2 - 2 * px, (int) (unitWidth*3/2));
                params1.topMargin = 0;
                params1.leftMargin = 0;
                ImageView imageView1 = new ImageView(getContext());
                imageView1.setLayoutParams(params1);
                imgs.add(imageView1);
                imageView1.setScaleType(ImageView.ScaleType.CENTER_CROP);

                Glide.with(context)
                        .load(images2.get(0))
                        /*.placeholder(R.drawable.gridview_addpic)*/
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL) // 设置本地缓存,缓存源文件和目标图像
                        .error(R.mipmap.pictures_no)
                        .into(imageView1);

                imageView1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(images2, 0);
                        }
                    }
                });

                LayoutParams params2 = new LayoutParams((int) unitWidth*3/2 - 2 * px, (int) (unitWidth*3/2));
                params2.topMargin = 0;
                params2.leftMargin = (int) unitWidth*3/2;
                ImageView imageView2 = new ImageView(getContext());
                imageView2.setLayoutParams(params2);
                imgs.add(imageView2);
                imageView2.setScaleType(ImageView.ScaleType.CENTER_CROP);

                Glide.with(context)
                        .load(images2.get(1))
                        /*.placeholder(R.drawable.gridview_addpic)*/
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL) // 设置本地缓存,缓存源文件和目标图像
                        .error(R.mipmap.pictures_no)
                        .into(imageView2);

                imageView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(images2, 1);
                        }
                    }
                });

                LayoutParams params3 = new LayoutParams((int) unitWidth*3/2 - 2 * px, (int) (unitWidth*3/2));
                params3.topMargin = (int) unitWidth*3/2+2 * px;
                params3.leftMargin = 0;
                ImageView imageView3 = new ImageView(getContext());
                imageView3.setLayoutParams(params3);
                imgs.add(imageView3);
                imageView3.setScaleType(ImageView.ScaleType.CENTER_CROP);

                Glide.with(context)
                        .load(images2.get(2))
                        /*.placeholder(R.drawable.gridview_addpic)*/
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL) // 设置本地缓存,缓存源文件和目标图像
                        .error(R.mipmap.pictures_no)
                        .into(imageView3);

                imageView3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(images2, 2);
                        }
                    }
                });

                LayoutParams params4 = new LayoutParams((int) unitWidth*3/2 - 2 * px, (int) (unitWidth*3/2));
                params4.topMargin = (int) unitWidth*3/2 + 2 * px;
                params4.leftMargin = (int) unitWidth*3/2;
                ImageView imageView4 = new ImageView(getContext());
                imageView4.setLayoutParams(params4);
                imgs.add(imageView4);
                imageView4.setScaleType(ImageView.ScaleType.CENTER_CROP);


                Glide.with(context)
                        .load(images2.get(3))
                       /* .placeholder(R.drawable.gridview_addpic)*/
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL) // 设置本地缓存,缓存源文件和目标图像
                        .error(R.mipmap.pictures_no)
                        .into(imageView4);

                imageView4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(images2, 3);
                        }
                    }
                });

            } else if (img_num == 5) {

                LayoutParams params1 = new LayoutParams((int) unitWidth - 2 * px, (int) (unitWidth));
                params1.topMargin = 0;
                params1.leftMargin = 0;
                ImageView imageView1 = new ImageView(getContext());
                imageView1.setLayoutParams(params1);
                imgs.add(imageView1);
                imageView1.setScaleType(ImageView.ScaleType.CENTER_CROP);


                Glide.with(context)
                        .load(images.get(0))
                        /*.placeholder(R.drawable.gridview_addpic)*/
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL) // 设置本地缓存,缓存源文件和目标图像
                        .error(R.mipmap.pictures_no)
                        .into(imageView1);

                imageView1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(images2, 0);
                        }
                    }
                });

                LayoutParams params2 = new LayoutParams((int) unitWidth - 2 * px, (int) (unitWidth));
                params2.topMargin = 0;
                params2.leftMargin = (int) unitWidth;
                ImageView imageView2 = new ImageView(getContext());
                imageView2.setLayoutParams(params2);
                imgs.add(imageView2);
                imageView2.setScaleType(ImageView.ScaleType.CENTER_CROP);

                Glide.with(context)
                        .load(images.get(1))
                        /*.placeholder(R.drawable.gridview_addpic)*/
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL) // 设置本地缓存,缓存源文件和目标图像
                        .error(R.mipmap.pictures_no)
                        .into(imageView2);

                imageView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(images2, 1);
                        }
                    }
                });

                LayoutParams params3 = new LayoutParams((int) unitWidth - 2 * px, (int) (unitWidth));
                params3.topMargin = 0;
                params3.leftMargin = (int) (2 * unitWidth);
                ImageView imageView3 = new ImageView(getContext());
                imageView3.setLayoutParams(params3);
                imgs.add(imageView3);
                imageView3.setScaleType(ImageView.ScaleType.CENTER_CROP);

                Glide.with(context)
                        .load(images.get(2))
                       /* .placeholder(R.drawable.gridview_addpic)*/
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL) // 设置本地缓存,缓存源文件和目标图像
                        .error(R.mipmap.pictures_no)
                        .into(imageView3);

                imageView3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(images2, 2);
                        }
                    }
                });

                LayoutParams params4 = new LayoutParams((int) unitWidth - 2 * px, (int) (unitWidth));
                params4.topMargin = (int) unitWidth + 2 * px;
                params4.leftMargin = 0;
                ImageView imageView4 = new ImageView(getContext());
                imageView4.setLayoutParams(params4);
                imgs.add(imageView4);
                imageView4.setScaleType(ImageView.ScaleType.CENTER_CROP);

                Glide.with(context)
                        .load(images.get(3))
                        /*.placeholder(R.drawable.gridview_addpic)*/
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL) // 设置本地缓存,缓存源文件和目标图像
                        .error(R.mipmap.pictures_no)
                        .into(imageView4);

                imageView4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(images2, 3);
                        }
                    }
                });

                LayoutParams params5 = new LayoutParams((int) unitWidth - 2 * px, (int) (unitWidth));
                params5.topMargin = (int) unitWidth + 2 * px;
                params5.leftMargin = (int) unitWidth;
                ImageView imageView5 = new ImageView(getContext());
                imageView5.setLayoutParams(params5);
                imgs.add(imageView5);
                imageView5.setScaleType(ImageView.ScaleType.CENTER_CROP);

                Glide.with(context)
                        .load(images.get(4))
                        /*.placeholder(R.drawable.gridview_addpic)*/
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL) // 设置本地缓存,缓存源文件和目标图像
                        .error(R.mipmap.pictures_no)
                        .into(imageView5);

                imageView5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(images2, 4);
                        }
                    }
                });


            } else if (img_num >= 6) {

                LayoutParams params1 = new LayoutParams((int) unitWidth - 2 * px, (int) (unitWidth));
                params1.topMargin = 0;
                params1.leftMargin = 0;
                ImageView imageView1 = new ImageView(getContext());
                imageView1.setLayoutParams(params1);
                imgs.add(imageView1);
                imageView1.setScaleType(ImageView.ScaleType.CENTER_CROP);

                Glide.with(context)
                        .load(images.get(0))
                        /*.placeholder(R.drawable.gridview_addpic)*/
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL) // 设置本地缓存,缓存源文件和目标图像
                        .error(R.mipmap.pictures_no)
                        .into(imageView1);

                imageView1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(images2, 0);
                        }
                    }
                });

                LayoutParams params2 = new LayoutParams((int) unitWidth - 2 * px, (int) (unitWidth));
                params2.topMargin = 0;
                params2.leftMargin = (int) unitWidth;
                ImageView imageView2 = new ImageView(getContext());
                imageView2.setLayoutParams(params2);
                imgs.add(imageView2);
                imageView2.setScaleType(ImageView.ScaleType.CENTER_CROP);

                Glide.with(context)
                        .load(images.get(1))
                        /*.placeholder(R.drawable.gridview_addpic)*/
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL) // 设置本地缓存,缓存源文件和目标图像
                        .error(R.mipmap.pictures_no)
                        .into(imageView2);

                imageView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(images2, 1);
                        }
                    }
                });

                LayoutParams params3 = new LayoutParams((int) unitWidth - 2 * px, (int) (unitWidth));
                params3.topMargin = 0;
                params3.leftMargin = (int) (2 * unitWidth);
                ImageView imageView3 = new ImageView(getContext());
                imageView3.setLayoutParams(params3);
                imgs.add(imageView3);
                imageView3.setScaleType(ImageView.ScaleType.CENTER_CROP);

                Glide.with(context)
                        .load(images.get(2))
                       /* .placeholder(R.drawable.gridview_addpic)*/
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL) // 设置本地缓存,缓存源文件和目标图像
                        .error(R.mipmap.pictures_no)
                        .into(imageView3);

                imageView3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(images2, 2);
                        }
                    }
                });

                LayoutParams params4 = new LayoutParams((int) unitWidth - 2 * px, (int) (unitWidth));
                params4.topMargin = (int) unitWidth + 2 * px;
                params4.leftMargin = 0;
                ImageView imageView4 = new ImageView(getContext());
                imageView4.setLayoutParams(params4);
                imgs.add(imageView4);
                imageView4.setScaleType(ImageView.ScaleType.CENTER_CROP);

                Glide.with(context)
                        .load(images.get(3))
                        /*.placeholder(R.drawable.gridview_addpic)*/
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL) // 设置本地缓存,缓存源文件和目标图像
                        .error(R.mipmap.pictures_no)
                        .into(imageView4);

                imageView4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(images2, 3);
                        }
                    }
                });

                LayoutParams params5 = new LayoutParams((int) unitWidth - 2 * px, (int) (unitWidth));
                params5.topMargin = (int) unitWidth + 2 * px;
                params5.leftMargin = (int) unitWidth;
                ImageView imageView5 = new ImageView(getContext());
                imageView5.setLayoutParams(params5);
                imgs.add(imageView5);
                imageView5.setScaleType(ImageView.ScaleType.CENTER_CROP);

                Glide.with(context)
                        .load(images.get(4))
                       /* .placeholder(R.drawable.gridview_addpic)*/
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL) // 设置本地缓存,缓存源文件和目标图像
                        .error(R.mipmap.pictures_no)
                        .into(imageView5);

                imageView5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(images2, 4);
                        }
                    }
                });

                LayoutParams params6 = new LayoutParams((int) unitWidth - 2 * px, (int) (unitWidth));
                params6.topMargin = (int) unitWidth + 2 * px;
                params6.leftMargin = (int) (2 * unitWidth);
                ImageView imageView6 = new ImageView(getContext());
                imageView6.setLayoutParams(params6);
                imgs.add(imageView6);
                imageView6.setScaleType(ImageView.ScaleType.CENTER_CROP);

                Glide.with(context)
                        .load(images.get(5))
                        /*.placeholder(R.drawable.gridview_addpic)*/
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL) // 设置本地缓存,缓存源文件和目标图像
                        .error(R.mipmap.pictures_no)
                        .into(imageView6);

                imageView6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(images2, 5);
                        }
                    }
                });

            }

            for (ImageView img : imgs) {
                img.setBackgroundColor(Color.blue(0xB2ADAD));
                addView(img);
            }


        }
    }


    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 定义接口
     */
    public interface OnItemClickListener {
        void onItemClick(ArrayList<String> images, int position);
    }

}