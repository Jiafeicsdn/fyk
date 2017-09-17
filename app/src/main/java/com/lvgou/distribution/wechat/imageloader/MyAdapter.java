package com.lvgou.distribution.wechat.imageloader;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.inter.OnSaveUrlsListener;
import com.lvgou.distribution.wechat.utils.CommonAdapter;
import com.lvgou.distribution.wechat.utils.ViewHolder;
import com.lvgou.distribution.utils.MyToast;


public class MyAdapter extends CommonAdapter<String> {

    /**
     * 用户选择的图片，存储为图片的完整路径
     */
    public static List<String> mSelectedImage = new ArrayList<String>();

    private String num_size;
    /**
     * 文件夹路径
     */
    private String mDirPath;

    private static OnSaveUrlsListener onSaveUrlsListener;

    public MyAdapter(Context context, List<String> mDatas, int itemLayoutId, String dirPath, String num_size) {
        super(context, mDatas, itemLayoutId);
        this.mDirPath = dirPath;
        this.num_size = num_size;
    }



    public static void clearData() {
        mSelectedImage.clear();
    }

    @Override
    public void convert(final ViewHolder helper, final String item) {
        //设置no_pic
        helper.setImageResource(R.id.id_item_image, R.mipmap.pictures_no);
        //设置no_selected
        helper.setImageResource(R.id.id_item_select, R.mipmap.picture_unselected);
        //设置图片
        helper.setImageByUrl(R.id.id_item_image, mDirPath + "/" + item);

        final ImageView mImageView = helper.getView(R.id.id_item_image);
        final ImageView mSelect = helper.getView(R.id.id_item_select);

        mImageView.setColorFilter(null);
        //设置ImageView的点击事件
        mImageView.setOnClickListener(new OnClickListener() {
            //选择，则将图片变暗，反之则反之
            @Override
            public void onClick(View v) {
                // 已经选择过该图片
                if (mSelectedImage.contains(mDirPath + "/" + item)) {
                    mSelectedImage.remove(mDirPath + "/" + item);
                    mSelect.setImageResource(R.mipmap.picture_unselected);
                    mImageView.setColorFilter(null);
                } else {
                    // 未选择该图片
                    if (mSelectedImage.size() < Integer.parseInt(num_size)) {
                        mSelectedImage.add(mDirPath + "/" + item);
                        mSelect.setImageResource(R.mipmap.pictures_selected);
                        mImageView.setColorFilter(Color.parseColor("#77000000"));
                    } else {
                        MyToast.show(mContext, "最多可选" + mSelectedImage.size() + "张照片");
                    }
                }

                if (onSaveUrlsListener != null) {
                    onSaveUrlsListener.saveUrlsListener(mSelectedImage);
                }
            }
        });

        /**
         * 已经选择过的图片，显示出选择过的效果
         */
        if (mSelectedImage.contains(mDirPath + "/" + item)) {
            mSelect.setImageResource(R.mipmap.pictures_selected);
            mImageView.setColorFilter(Color.parseColor("#77000000"));
        }

    }

    public static void setOnSaveUrlsListener(OnSaveUrlsListener onSaveUrlsListener) {
        MyAdapter.onSaveUrlsListener = onSaveUrlsListener;
    }

}