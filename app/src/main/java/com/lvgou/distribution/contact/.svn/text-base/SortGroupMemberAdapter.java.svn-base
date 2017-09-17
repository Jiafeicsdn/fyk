package com.lvgou.distribution.contact;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.lidroid.xutils.util.LogUtils;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.entity.ContactsListEntity;
import com.lvgou.distribution.inter.OnClassifyPostionClickListener;
import com.lvgou.distribution.utils.ImageUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.List;

public class SortGroupMemberAdapter extends BaseAdapter implements SectionIndexer {
    private List<ContactsListEntity> list = null;
    private Context mContext;


    private static OnClassifyPostionClickListener<ContactsListEntity> onClassifyPostionClickListener;


    public SortGroupMemberAdapter(Context mContext, List<ContactsListEntity> list) {
        this.mContext = mContext;
        this.list = list;
    }

    /**
     * listView�ListView
     *
     * @param list
     */
    public void updateListView(List<ContactsListEntity> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public int getCount() {
        return this.list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup arg2) {
        ViewHolder viewHolder = null;
        final ContactsListEntity contactsListEntity = list.get(position);
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.activity_group_member_item, null);
            viewHolder.ll_item = (LinearLayout) view.findViewById(R.id.ll_item);
            viewHolder.name = (TextView) view.findViewById(R.id.tv_name);
            viewHolder.tvLetter = (TextView) view.findViewById(R.id.catalog);
            viewHolder.img_teacher_head = (ImageView) view.findViewById(R.id.img_teacher_head);
            viewHolder.tv_fengwen_num = (TextView) view.findViewById(R.id.tv_fengwen_num);
            viewHolder.tv_fans_num = (TextView) view.findViewById(R.id.tv_fans_num);
            viewHolder.img_follow = (ImageView) view.findViewById(R.id.img_follow);
            viewHolder.img_follow.setVisibility(View.GONE);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.options = new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.teacher_default_head)            // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.teacher_default_head)    // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.teacher_default_head)            // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(false)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(false)                            // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(360))    // 设置成圆角图片
                .build();
        ImageLoader.getInstance().displayImage(ImageUtils.getInstance().getPath(contactsListEntity.getId()), viewHolder.img_teacher_head, viewHolder.options);
        viewHolder.name.setText(contactsListEntity.getName());
        viewHolder.tv_fengwen_num.setText(contactsListEntity.getFengwen_num());
        viewHolder.tv_fans_num.setText(contactsListEntity.getFangs_num());
        if (contactsListEntity.getIsFollow().equals("1")) {
            viewHolder.img_follow.setBackgroundResource(R.mipmap.circle_add_follow);
        } else if (contactsListEntity.getIsFollow().equals("2")) {
            viewHolder.img_follow.setBackgroundResource(R.mipmap.grey_follow_already);
        } else if (contactsListEntity.getIsFollow().equals("3")) {
            viewHolder.img_follow.setBackgroundResource(R.mipmap.huxiang_follow);
        }

        int section = getSectionForPosition(position);
        if (position == getPositionForSection(section)) {
            viewHolder.tvLetter.setVisibility(View.VISIBLE);
            viewHolder.tvLetter.setText(contactsListEntity.getSortLetters());
        } else {
            viewHolder.tvLetter.setVisibility(View.GONE);
        }

        viewHolder.img_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClassifyPostionClickListener != null) {
                    onClassifyPostionClickListener.onClassifyPostionClick(contactsListEntity, 2);
                }
            }
        });

        viewHolder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClassifyPostionClickListener != null) {
                    onClassifyPostionClickListener.onClassifyPostionClick(contactsListEntity, 1);
                }
            }
        });

        return view;

    }

    final static class ViewHolder {
        DisplayImageOptions options;
        TextView tvLetter;
        LinearLayout ll_item;
        TextView name;
        ImageView img_teacher_head;
        TextView tv_fengwen_num;
        TextView tv_fans_num;
        ImageView img_follow;
    }


    public int getSectionForPosition(int position) {
        return list.get(position).getSortLetters().charAt(0);
    }


    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = list.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }

        return -1;
    }


    private String getAlpha(String str) {
        String sortStr = str.trim().substring(0, 1).toUpperCase();
        if (sortStr.matches("[A-Z]")) {
            return sortStr;
        } else {
            return "#";
        }
    }

    @Override
    public Object[] getSections() {
        return null;
    }

    public static void setOnClassifyPostionClickListener(OnClassifyPostionClickListener<ContactsListEntity> onClassifyPostionClickListener) {
        SortGroupMemberAdapter.onClassifyPostionClickListener = onClassifyPostionClickListener;
    }
}