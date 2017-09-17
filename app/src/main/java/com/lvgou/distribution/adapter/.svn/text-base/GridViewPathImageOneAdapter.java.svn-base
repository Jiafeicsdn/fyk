package com.lvgou.distribution.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.lvgou.distribution.utils.MyToast;import android.widget.Toast;

import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.PublishFengActivity;
import com.lvgou.distribution.bean.DelPic;
import com.lvgou.distribution.constants.Constants;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.event.EventFactory;
import com.lvgou.distribution.presenter.DelImgPresenter;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.DelImgView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.xdroid.common.utils.PreferenceHelper;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by Administrator on 2016/10/19.
 */
public class GridViewPathImageOneAdapter extends BaseAdapter implements DelImgView {
    private DelImgPresenter delImgPresenter;

    private Context context;
    private List<String> lists;
    private LayoutInflater inflater;

    public GridViewPathImageOneAdapter(Context context, List<String> lists) {
        this.context = context;
        this.lists = lists;
        inflater = LayoutInflater.from(context);
        delImgPresenter = new DelImgPresenter(this);
    }

    public GridViewPathImageOneAdapter() {
    }

    /**
     * 获取列表数据
     *
     * @param list
     */
    public void setList(List<String> list) {
        this.lists = list;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (lists == null) {
            return 1;
        } else if (lists.size() == 6) {
            return 6;
        } else {
            return lists.size() + 1;
        }
    }

    @Override
    public Object getItem(int position) {
        if (lists != null && lists.size() == 6) {
            return lists.get(position);
        } else if (lists == null || position - 1 < 0 || position > lists.size()) {
            return null;
        } else {
            return lists.get(position - 1);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_publish_one, null);
            holder.iv = (ImageView) convertView.findViewById(R.id.item_grid_image);
            holder.img_delete_icon = (ImageView) convertView.findViewById(R.id.img_delete_img);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (isShowAddItem(position)) {
            if (Constants.IS_SHOW_ADD.equals("1")) {
                holder.iv.setVisibility(View.GONE);
                holder.img_delete_icon.setVisibility(View.GONE);
            } else {
                ImageLoader.getInstance().displayImage("drawable://" + R.mipmap.bg_add_picture, holder.iv);
                holder.iv.setVisibility(View.VISIBLE);
                holder.img_delete_icon.setVisibility(View.GONE);
            }
        } else {
            holder.img_delete_icon.setVisibility(View.VISIBLE);
            holder.options = new DisplayImageOptions.Builder()
                    .showStubImage(R.mipmap.bg_none_good)            // 设置图片下载期间显示的图片
                    .showImageForEmptyUri(R.mipmap.bg_none_good)    // 设置图片Uri为空或是错误的时候显示的图片
                    .showImageOnFail(R.mipmap.bg_none_good)            // 设置图片加载或解码过程中发生错误显示的图片
                    .cacheInMemory(false)                        // 设置下载的图片是否缓存在内存中
                    .cacheOnDisc(false)                            // 设置下载的图片是否缓存在SD卡中
                    .considerExifParams(true)                     //是否考虑JPEG图像EXIF参数（旋转，翻转）
                    .displayer(new RoundedBitmapDisplayer(10))    // 设置成圆角图片
                    .build();

            ImageLoader.getInstance().displayImage(Url.CIRCLE_URL + lists.get(position), holder.iv, holder.options);

        }

        holder.img_delete_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sign = TGmd5.getMD5(lists.get(position));
                ((PublishFengActivity) context).showLoadingProgressDialog(context, "");
                mposi=position;
                delImgPresenter.delImg(lists.get(position),sign);


            }
        });
        return convertView;
    }
    private int mposi=0;

    private boolean isShowAddItem(int position) {
        int size = lists == null ? 0 : lists.size();
        return position == size;
    }

    @Override
    public void OnDelImgSuccCallBack(String state, String respanse) {
        //删除图片成功
        ((PublishFengActivity) context).closeLoadingProgressDialog();
        EventBus.getDefault().post(new DelPic(mposi));
//        EventFactory.removeImagePostion(mposi);
    }

    @Override
    public void OnDelImgFialCallBack(String state, String respanse) {
        //删除图片失败
        ((PublishFengActivity) context).closeLoadingProgressDialog();
        MyToast.makeText(context, "删除失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void closeDelImgProgress() {

    }

    static class ViewHolder {
        ImageView iv;
        DisplayImageOptions options;
        ImageView img_delete_icon;
    }
}
