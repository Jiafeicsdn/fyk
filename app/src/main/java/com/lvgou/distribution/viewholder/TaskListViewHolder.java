package com.lvgou.distribution.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.entity.TaskItemEntity;
import com.lvgou.distribution.inter.OnClassifyPostionClickListener;
import com.xdroid.functions.holder.ViewHolderBase;

/**
 * Created by Snow on 2016/10/17.
 */
public class TaskListViewHolder extends ViewHolderBase<TaskItemEntity> {

    private LinearLayout ll_item;
    private RelativeLayout rl_head_view;
    private TextView tv_title;
    private ImageView img_icon;
    private TextView tv_name;
    private LinearLayout ll_right;
    private TextView tv_lingqu;
    private TextView tv_status;
    private ImageView img_complete_item;
    private View view_line;


    private static OnClassifyPostionClickListener<TaskItemEntity> onClassifyPostionClickListener;


    @Override
    public View createView(LayoutInflater layoutInflater) {
        View view = layoutInflater.inflate(R.layout.item_task_list, null);
        ll_item = (LinearLayout) view.findViewById(R.id.ll_item);
        rl_head_view = (RelativeLayout) view.findViewById(R.id.rl_head_view);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        img_icon = (ImageView) view.findViewById(R.id.img_icon);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        ll_right = (LinearLayout) view.findViewById(R.id.ll_right);
        tv_status = (TextView) view.findViewById(R.id.tv_status);
        tv_lingqu = (TextView) view.findViewById(R.id.lingqu);
        img_complete_item = (ImageView) view.findViewById(R.id.img_complete);
        view_line = (View) view.findViewById(R.id.view_line);
        return view;
    }

    @Override
    public void showData(int position, final TaskItemEntity itemData) {

        // [7,2,2,[[1,2],[1,2],[1,2],[1,3],[1,2],[1,2],[5,0],[2,0],[5,0]],[[10,2],[2,2],[5,2],[2,3],[2,3],[2,3],[5,0]]]
        // 新手任务,每日任务显示
        if (position == 1) {
            rl_head_view.setVisibility(View.GONE);
            tv_title.setText("我的任务");
        } else if (position == 10) {
            rl_head_view.setVisibility(View.GONE);
            tv_title.setText("新手任务");
        } else {
            rl_head_view.setVisibility(View.GONE);
        }


        if (itemData.getImg_status().equals("0")) {
            img_icon.setBackgroundResource(R.mipmap.add_two_ten_circle);
        } else if (itemData.getImg_status().equals("1")) {
            img_icon.setBackgroundResource(R.mipmap.add_one_circle);
        } else if (itemData.getImg_status().equals("2")) {
            img_icon.setBackgroundResource(R.mipmap.add_two_circle);
        } else if (itemData.getImg_status().equals("5")) {
            img_icon.setBackgroundResource(R.mipmap.add_five_circle);
        } else if (itemData.getImg_status().equals("10")) {
            img_icon.setBackgroundResource(R.mipmap.add_ten_circle);
        }

        tv_name.setText(itemData.getName());

        if (itemData.getStstus().equals("0")) {
            ll_right.setVisibility(View.GONE);
            img_complete_item.setVisibility(View.GONE);
            tv_lingqu.setVisibility(View.GONE);
        } else if (itemData.getStstus().equals("1")) {
            ll_right.setVisibility(View.GONE);
            img_complete_item.setVisibility(View.VISIBLE);
            tv_lingqu.setVisibility(View.GONE);
        } else if (itemData.getStstus().equals("2")) {
            ll_right.setVisibility(View.VISIBLE);
            img_complete_item.setVisibility(View.GONE);
            tv_lingqu.setVisibility(View.GONE);
            switch (itemData.getName()) {
                case "分享学院课程":
                    tv_status.setText("去分享");
                    break;
                case "发布蜂圈动态":
                    tv_status.setText("去发布");
                    break;
                case "每日蜂圈评论":
                    tv_status.setText("去评论");
                    break;
                case "蜂圈点赞":
                    tv_status.setText("去点赞");
                    break;
                case "蜂圈转发":
                    tv_status.setText("去转发");
                    break;
                case "实名认证":
                    tv_status.setText("去认证");
                    break;
                case "首次课程评分":
                    tv_status.setText("去评分");
                    break;
                case "首次完成随时赚":
                    tv_status.setText("去赚钱");
                    break;
                case "首次发布蜂圈动态":
                    tv_status.setText("去发布");
                    break;
                case "首次蜂圈评论":
                    tv_status.setText("去评论");
                    break;
                case "首次蜂圈点赞":
                    tv_status.setText("去点赞");
                    break;
            }
        } else if (itemData.getStstus().equals("3")) {
            ll_right.setVisibility(View.GONE);
            img_complete_item.setVisibility(View.GONE);
            tv_lingqu.setVisibility(View.VISIBLE);
        } else if (itemData.getStstus().equals("4")) {
            ll_right.setVisibility(View.VISIBLE);
            tv_status.setText("去邀请");
            img_complete_item.setVisibility(View.GONE);
            tv_lingqu.setVisibility(View.GONE);
        }

        ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClassifyPostionClickListener != null) {
                    onClassifyPostionClickListener.onClassifyPostionClick(itemData, 1);
                }
            }
        });

        tv_lingqu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClassifyPostionClickListener != null) {
                    onClassifyPostionClickListener.onClassifyPostionClick(itemData, 2);
                }
            }
        });
    }

    public static void setOnClassifyPostionClickListener(OnClassifyPostionClickListener<TaskItemEntity> onClassifyPostionClickListener) {
        TaskListViewHolder.onClassifyPostionClickListener = onClassifyPostionClickListener;
    }
}
