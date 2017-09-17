package com.lvgou.distribution.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.utils.MyToast;

import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lvgou.distribution.R;
import com.lvgou.distribution.adapter.RecommendFragmentAdapter;
import com.lvgou.distribution.adapter.SkillsAdapter;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.emoji.FaXianParseEmojiMsgUtil;
import com.lvgou.distribution.emoji.ParseEmojiMsgUtil;
import com.lvgou.distribution.entity.CircleRecommentEntity;
import com.lvgou.distribution.fragment.FaXianFragment;
import com.lvgou.distribution.inter.AdapterToFraImpl;
import com.lvgou.distribution.presenter.DistributorHomePresenter;
import com.lvgou.distribution.presenter.FengwenUnZanPresenter;
import com.lvgou.distribution.presenter.FengwenZanPresenter;
import com.lvgou.distribution.presenter.UseFollowPresenter;
import com.lvgou.distribution.utils.FaXianImageLoaderEmoji;
import com.lvgou.distribution.utils.ImageUtils;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.utils.ViewHolder;
import com.lvgou.distribution.view.DistributorHomeView;
import com.lvgou.distribution.view.FengwenUnZanView;
import com.lvgou.distribution.view.FengwenZanView;
import com.lvgou.distribution.view.UseFollowView;
import com.lvgou.distribution.widget.CellLayout;
import com.wx.goodview.GoodView;
import com.xdroid.common.utils.PreferenceHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Administrator on 2017/4/14.
 */

public class FaXianAdapter extends BaseAdapter implements FengwenZanView, FengwenUnZanView, UseFollowView, DistributorHomeView {
    private DistributorHomePresenter distributorHomePresenter;
    Context context;
    ArrayList<HashMap<String, Object>> list;
    private String lastSelectId;
    GoodView goodView;
    private FengwenZanPresenter fengwenZanPresenter;
    private FengwenUnZanPresenter fengwenUnZanPresenter;
    private UseFollowPresenter useFollowPresenter;
    private JSONArray jsonObject;
    private String distributorid;

    public FaXianAdapter(Context _context) {
        this.context = _context;
        distributorHomePresenter = new DistributorHomePresenter(this);
    }

    public void setData(ArrayList<HashMap<String, Object>> list, String lastSelectId, JSONArray jsonObject3) {
        this.list = list;
        this.lastSelectId = lastSelectId;
        this.jsonObject = jsonObject3;
        distributorid = PreferenceHelper.readString(context, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);

        goodView = new GoodView(context);
        fengwenZanPresenter = new FengwenZanPresenter(this);
        fengwenUnZanPresenter = new FengwenUnZanPresenter(this);
        useFollowPresenter = new UseFollowPresenter(this);
    }

    @Override
    public int getCount() {
        if (list == null) {
            return 0;
        } else {
            return list.size();
        }
    }


    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final HashMap<String, Object> info = list.get(position);
        ViewHolder viewHolder = null;
        viewHolder = ViewHolder.getVH(convertView, context, R.layout.faxian_list_item);
        LinearLayout ll_findmore = viewHolder.getView(R.id.ll_findmore, LinearLayout.class);//发现更多
        ll_findmore.setVisibility(View.GONE);
        ll_findmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //处理点击，防止item条目点击
            }
        });
        ImageView image = viewHolder.getView(R.id.image, ImageView.class);//头像
        TextView author = viewHolder.getView(R.id.author, TextView.class);//作者
        TextView tv_time = viewHolder.getView(R.id.dtime, TextView.class);//时间
        final TextView attension = viewHolder.getView(R.id.attension, TextView.class);//关注
        TextView summary = viewHolder.getView(R.id.summary, TextView.class);//内容
        CellLayout layout = viewHolder.getView(R.id.layout, CellLayout.class);//图片
        TextView tv_markone = viewHolder.getView(R.id.tv_markone, TextView.class);//第一个标签
        tv_markone.setVisibility(View.GONE);
        TextView tv_marktwo = viewHolder.getView(R.id.tv_marktwo, TextView.class);//第二个标签
        tv_marktwo.setVisibility(View.GONE);
        RelativeLayout rl_ding = viewHolder.getView(R.id.rl_ding, RelativeLayout.class);//点赞
        final ImageView im_ding = viewHolder.getView(R.id.im_ding, ImageView.class);//点赞的icon
        final TextView tv_ding_num = viewHolder.getView(R.id.tv_ding_num, TextView.class);//点赞数量
        TextView tv_pinglun_num = viewHolder.getView(R.id.tv_pinglun_num, TextView.class);//评论数量
        ImageView im_guanggao = viewHolder.getView(R.id.im_guanggao, ImageView.class);//广告头条图片
        TextView tv_address = viewHolder.getView(R.id.tv_address, TextView.class);//地理位置
        ImageView img_teacher_label = viewHolder.getView(R.id.img_teacher_label, ImageView.class);
        img_teacher_label.setVisibility(View.VISIBLE);


        if (info.get("UserType").toString().equals("3")) {
            img_teacher_label.setImageResource(R.mipmap.bg_tecaher_authentication);
            author.setTextColor(context.getResources().getColor(R.color.text_color_ff9900));
        } else if (info.get("UserType").toString().equals("2")) {
                /*if (circleDynamicEntities.get(position).getIsRZ() == 1) {
                    viewHolder.img_teacher_label.setImageResource(R.mipmap.icon_certified);
                    viewHolder.txt_user_name.setTextColor(context.getResources().getColor(R.color.text_color_ff9900));
                } else {
                    viewHolder.img_teacher_label.setVisibility(View.GONE);
                    viewHolder.txt_user_name.setTextColor(context.getResources().getColor(R.color.text_color_333333));
                }*/
            img_teacher_label.setVisibility(View.GONE);
            author.setTextColor(Color.parseColor("#7b7b7b"));
        } else if (info.get("UserType").toString().equals("1")) {
            img_teacher_label.setImageResource(R.mipmap.icon_official_certified);
            author.setTextColor(context.getResources().getColor(R.color.text_color_ff9900));
        }
        if (info.get("CurrentLocation") == null || info.get("CurrentLocation").toString().equals("") || info.get("CurrentLocation").toString().equals("null")) {
            tv_address.setVisibility(View.GONE);
        } else {
            tv_address.setVisibility(View.VISIBLE);
            tv_address.setText(info.get("CurrentLocation").toString());
        }
       /* ArrayList<String> CategoryNames;
        CategoryNames = info.get("CategoryNames");
        Log.e("kahskfdf", "=======" + CategoryNames);*/
        try {
            JSONArray jsa = new JSONArray(info.get("CategoryNames").toString());
            if (null == jsa || jsa.length() == 0) {
                tv_markone.setVisibility(View.GONE);
                tv_marktwo.setVisibility(View.GONE);
            } else if (jsa.length() == 1) {
                tv_markone.setVisibility(View.VISIBLE);
                tv_marktwo.setVisibility(View.GONE);
                tv_markone.setText(jsa.get(0).toString());
            } else if (jsa.length() == 2) {
                tv_markone.setVisibility(View.VISIBLE);
                tv_marktwo.setVisibility(View.VISIBLE);
                tv_markone.setText(jsa.get(0).toString());
                tv_marktwo.setText(jsa.get(1).toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (info.get("Extra").equals(lastSelectId)) {
            ll_findmore.setVisibility(View.VISIBLE);
            if (jsonObject.length() > 0) {
                WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                int width = wm.getDefaultDisplay().getWidth();
                ViewGroup.LayoutParams para = im_guanggao.getLayoutParams();
                int iheight = (int) (width * 49 / 150);
                para.height = iheight;
                im_guanggao.setLayoutParams(para);

                im_guanggao.setVisibility(View.VISIBLE);
                try {
                    JSONObject jsa = this.jsonObject.getJSONObject(0);
                    Glide.with(context).load(Url.ROOT + jsa.get("PicUrl").toString()).error(R.mipmap.pictures_no).into(im_guanggao);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                im_guanggao.setVisibility(View.GONE);
            }
        }
        im_guanggao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JSONObject jsa = FaXianAdapter.this.jsonObject.getJSONObject(0);
                    turnView(jsa.get("LinkUrl").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Glide.with(context).load(ImageUtils.getInstance().getPath(info.get("DistributorID").toString())).error(R.mipmap.teacher_default_head).into(image);
        author.setText(info.get("DistributorName").toString());
        if (info.get("Followed").toString().equals("0")) {
            //未关注
            attension.setVisibility(View.VISIBLE);
        } else if (info.get("Followed").toString().equals("1")) {
            //已关注
            attension.setVisibility(View.GONE);
        }
        if (distributorid.equals(info.get("DistributorID").toString())) {
            //如果是自己
            attension.setVisibility(View.GONE);
        }
        //-------------

        //-----TopicTitle
//        ParseEmojiMsgUtil.getExpressionString(context, info.get("ID").toString(), summary, info.get("Title").toString());
        if (null != info.get("TopicTitle") && !info.get("TopicTitle").toString().equals("") && !info.get("TopicTitle").toString().equals("null")) {
            FaXianParseEmojiMsgUtil.getExpressionString(context, info.get("ID").toString(), summary, info.get("Title").toString(), info.get("TopicTitle").toString(), info.get("TopicID").toString());
        } else {
            ParseEmojiMsgUtil.getExpressionString(context, info.get("ID").toString(), summary, info.get("Title").toString());
        }
//        summary.setText(info.get("Title").toString());
        tv_ding_num.setText(info.get("ZanCount").toString().equals("0")?"赞":info.get("ZanCount").toString());
        if (info.get("CommentCount").toString().equals("0")) {
            tv_pinglun_num.setText("评论");
        } else {
            tv_pinglun_num.setText(info.get("CommentCount").toString());
        }


        String[] str = info.get("CreateTime").toString().split("T");
        //2016-04-22 16:32:50
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date_e = dfs.format(new Date());
        String date_b = str[0] + " " + str[1];
        try {
            Date begin = dfs.parse(date_b);
            Date end = dfs.parse(date_e);
            long between = (end.getTime() - begin.getTime()) / 1000;//除以1000是为了转换成秒
            long month1 = between / (30 * 24 * 3600);
            long day1 = between / (24 * 3600);
            long hour1 = between / 3600;
            long minute1 = between / 60;
            if (minute1 == 0) {
                tv_time.setText("刚刚");
            } else if (minute1 < 60) {
                tv_time.setText(minute1 + "分钟前");
            } else if (hour1 < 24) {
                tv_time.setText(hour1 + "小时前");
            } else if (day1 < 30) {
                tv_time.setText(day1 + "天前");
            } else if (month1 < 12) {
                tv_time.setText(month1 + "月前");
            } else {
                tv_time.setText(str[0] + " " + str[1].split(":")[0] + ":" + str[1].split(":")[1]);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String picJson = info.get("PicJson").toString();
        ArrayList<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();
        dataList.clear();
        try {
            JSONArray jsonArray = new JSONArray(picJson);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsoo = new JSONObject(jsonArray.get(i).toString());
//                JSONObject jsoo = jsonArray.getJSONObject(i);
                HashMap<String, Object> map1 = new HashMap<String, Object>();
                Iterator it = jsoo.keys();
                while (it.hasNext()) {
                    String key = (String) it.next();
                    String value = jsoo.getString(key);
                    map1.put(key, value);
                }
                dataList.add(map1);
            }
            if (dataList.size() == 0) {
                layout.setVisibility(View.GONE);
            } else {
                layout.setVisibility(View.VISIBLE);
                layout.setData(dataList, context);
            }
        } catch (JSONException e) {
        }
        if (info.get("Zaned").toString().equals("0")) {
            //表示没有赞
            im_ding.setBackgroundResource(R.mipmap.ding_normal_icon);
            tv_ding_num.setTextColor(Color.parseColor("#bababa"));
        } else if (info.get("Zaned").toString().equals("1")) {
            //表示已经赞赞
            im_ding.setBackgroundResource(R.mipmap.ding_select_icon);
            tv_ding_num.setTextColor(Color.parseColor("#d5aa5f"));
        }
        layout.setOnItemClickListener(new CellLayout.OnItemClickListener() {
            @Override
            public void onItemClick(ArrayList<String> images, int position) {
                Intent intent = new Intent(context, ImagePagerActivity.class);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("image_urls", images);
                bundle.putInt("image_index", position);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        rl_ding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //顶
                opePosition = position;
                mim_ding = im_ding;
                mtv_ding_num = tv_ding_num;
                String talkId = info.get("ID").toString();
                String distributorid = PreferenceHelper.readString(context, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);

                if (info.get("Zaned").toString().equals("0")) {
                    //表示没有赞
                    String sign = TGmd5.getMD5("" + distributorid + talkId);
                    ((CirclrFengActivity) context).showLoadingProgressDialog(context, "");
                    fengwenZanPresenter.fengwenZan(distributorid, talkId, sign);
                } else if (info.get("Zaned").toString().equals("1")) {
                    //如果已经赞了
                    String sign = TGmd5.getMD5("" + distributorid + talkId);
                    ((CirclrFengActivity) context).showLoadingProgressDialog(context, "");
                    fengwenUnZanPresenter.fengwenUnZan(distributorid, talkId, sign);

                }
            }
        });
        attension.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //关注
                mattension = attension;
                opePosition = position;
                String friendId = info.get("DistributorID").toString();
                String distributorid = PreferenceHelper.readString(context, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
                String sign = TGmd5.getMD5("" + distributorid + friendId);
                ((CirclrFengActivity) context).showLoadingProgressDialog(context, "");
                useFollowPresenter.useFollow(distributorid, friendId, sign);
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sign2 = TGmd5.getMD5("" + distributorid + info.get("DistributorID").toString());
                ((CirclrFengActivity) context).showLoadingProgressDialog(context, "");
                distributorHomePresenter.distributorHome(distributorid, info.get("DistributorID").toString() + "", sign2);
                //头像点击
               /* if (info.get("UserType").toString().equals("3")) {
                    //如果是讲师
                    Intent intent1 = new Intent(context, TeacherHomeActivity.class);
                    intent1.putExtra("seeDistributorId", info.get("DistributorID").toString());
                    context.startActivity(intent1);
                } else {
                    //普通导游
                    Intent intent1 = new Intent(context, DistributorHomeActivity.class);
                    intent1.putExtra("seeDistributorId", info.get("DistributorID").toString());
                    context.startActivity(intent1);
                }*/
            }
        });
        author.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sign2 = TGmd5.getMD5("" + distributorid + info.get("DistributorID").toString());
                ((CirclrFengActivity) context).showLoadingProgressDialog(context, "");
                distributorHomePresenter.distributorHome(distributorid, info.get("DistributorID").toString() + "", sign2);
                /*//名字点击
                if (info.get("UserType").toString().equals("3")) {
                    //如果是讲师
                    Intent intent1 = new Intent(context, TeacherHomeActivity.class);
                    intent1.putExtra("seeDistributorId", info.get("DistributorID").toString());
                    context.startActivity(intent1);
                } else {
                    //普通导游
                    Intent intent1 = new Intent(context, DistributorHomeActivity.class);
                    intent1.putExtra("seeDistributorId", info.get("DistributorID").toString());
                    context.startActivity(intent1);
                }*/
            }
        });

        return viewHolder.convertView;
    }

    private ImageView mim_ding;
    private TextView mtv_ding_num;
    private int opePosition = 0;
    private TextView mattension;

    @Override
    public void OnFengwenZanSuccCallBack(String state, String respanse) {
        ((CirclrFengActivity) context).closeLoadingProgressDialog();
        HashMap<String, Object> stringObjectHashMap = list.get(opePosition);
        int dingnum = Integer.parseInt(stringObjectHashMap.get("ZanCount").toString()) + 1;
        mim_ding.setBackgroundResource(R.mipmap.ding_select_icon);
        mtv_ding_num.setTextColor(Color.parseColor("#d5aa5f"));
        mtv_ding_num.setText(dingnum + "");
        for (int i = 0; i < list.size(); i++) {
            if (list.get(opePosition).get("ID").toString().equals(list.get(i).get("ID").toString())) {
                list.get(i).put("Zaned", "1");
                list.get(i).put("ZanCount", dingnum + "");
            }
        }
        /*list.get(opePosition).put("Zaned", "1");
        list.get(opePosition).put("ZanCount", dingnum + "");*/
        stringObjectHashMap.put("Zaned", "1");
        stringObjectHashMap.put("ZanCount", dingnum + "");
        adapterToFra.doSomeThing(stringObjectHashMap);
        goodView.setImage(R.mipmap.ding_select_icon);
        goodView.show(mim_ding);
    }

    @Override
    public void OnFengwenZanFialCallBack(String state, String respanse) {
        ((CirclrFengActivity) context).closeLoadingProgressDialog();

        MyToast.makeText(context, "" + respanse, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void closeFengwenZanProgress() {

    }

    AdapterToFraImpl adapterToFra;

    public void setAdapterToFraImpl(AdapterToFraImpl adapterToFra) {
        this.adapterToFra = adapterToFra;
    }

    @Override
    public void OnFengwenUnZanSuccCallBack(String state, String respanse) {
        ((CirclrFengActivity) context).closeLoadingProgressDialog();
        //取消赞
        HashMap<String, Object> stringObjectHashMap = list.get(opePosition);
        int dingnum = Integer.parseInt(stringObjectHashMap.get("ZanCount").toString()) - 1;
        mim_ding.setBackgroundResource(R.mipmap.ding_normal_icon);
        mtv_ding_num.setTextColor(Color.parseColor("#bababa"));
        mtv_ding_num.setText(dingnum>0?dingnum + "":"赞");
        for (int i = 0; i < list.size(); i++) {
            if (list.get(opePosition).get("ID").toString().equals(list.get(i).get("ID").toString())) {
                list.get(i).put("Zaned", "0");
                list.get(i).put("ZanCount", dingnum + "");
            }
        }
      /*  list.get(opePosition).put("Zaned", "0");
        list.get(opePosition).put("ZanCount", dingnum + "");*/
        stringObjectHashMap.put("Zaned", "0");
        stringObjectHashMap.put("ZanCount", dingnum + "");
        adapterToFra.doSomeThing(stringObjectHashMap);
        goodView.setImage(R.mipmap.ding_normal_icon);
        goodView.show(mim_ding);
    }

    @Override
    public void OnFengwenUnZanFialCallBack(String state, String respanse) {
        ((CirclrFengActivity) context).closeLoadingProgressDialog();
        MyToast.makeText(context, "" + respanse, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void closeFengwenUnZanProgress() {

    }

    @Override
    public void OnUseFollowSuccCallBack(String state, String respanse) {
        ((CirclrFengActivity) context).closeLoadingProgressDialog();
        //关注
        mattension.setVisibility(View.GONE);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(opePosition).get("ID").toString().equals(list.get(i).get("ID").toString())) {
                list.get(i).put("Followed", "1");
            }
        }
        HashMap<String, Object> stringObjectHashMap = list.get(opePosition);
        stringObjectHashMap.put("Followed", "1");
        adapterToFra.doSomeThing(stringObjectHashMap);
    }

    @Override
    public void OnUseFollowFialCallBack(String state, String respanse) {
        ((CirclrFengActivity) context).closeLoadingProgressDialog();
    }

    @Override
    public void closeUseFollowProgress() {

    }

    @Override
    public void OnDistributorHomeSuccCallBack(String state, String respanse) {
        ((CirclrFengActivity) context).closeLoadingProgressDialog();
        try {
            JSONObject jsb = new JSONObject(respanse);
            JSONArray jsa = new JSONArray(jsb.getString("result"));
            JSONObject distributorModeljsa = jsa.getJSONObject(0);
            if (distributorModeljsa.get("UserType").toString().equals("3")) {
                //如果是讲师
                Intent intent1 = new Intent(context, TeacherHomeActivity.class);
                intent1.putExtra("seeDistributorId", distributorModeljsa.get("ID").toString());
                context.startActivity(intent1);
            } else {
                //普通导游
                Intent intent1 = new Intent(context, DistributorHomeActivity.class);
                intent1.putExtra("seeDistributorId", distributorModeljsa.get("ID").toString());
                context.startActivity(intent1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void OnDistributorHomeFialCallBack(String state, String respanse) {
        ((CirclrFengActivity) context).closeLoadingProgressDialog();
    }

    @Override
    public void closeDistributorHomeProgress() {

    }


    /**
     * 蜂优客4.0推荐的轮播Banner/4.0的精品对应页面链接地址所填的值
     * 注：除以下固定外的页面，请填写正确的地址（#表示不进行跳转
     * 1、公告详情：http://agent.quygt.com/user/messagedetail/77，77为公告文章编号
     * 2、商品详情页：http://m.quygt.com/product/details/5660，5660为商品ID，跳转时APP中会带上导游ID
     * 3、品牌商品页：http://m.quygt.com/product/sellerproduct?sellerid=813，813为品牌商家ID，跳转时会带上导游ID
     * 4、随时赚：http://agent.quygt.com/supply/selectsupply
     * 5、我的任务：http://agent.quygt.com/tuanbi/mytasklist
     * 6、名师讲堂详情页：http://agent.quygt.com/study/teacherdetail?id=36，36为当前名师讲堂ID
     * 7、专题列表：Series+专题的编号 例：Series1
     * 8、活动详情：Activity+活动编号 例：Activity1
     * 9、申请开课：ApplyForStudy
     * 10、申请讲师：ApplyForTeacher
     *
     * @param url
     */
    public void turnView(String url) {
        Bundle bundle = new Bundle();
        if (url != null && url.length() > 0) {
            try {
                if (url.equals("#")) {

                } else if (url.contains("user/messagedetail")) {//公告详情
                    String[] str_urls = url.split("/");
                    bundle.putString("id", str_urls[5]);
                    bundle.putString("index", "0");
                    ((CirclrFengActivity) context).openActivity(NoticeDetialActivity.class, bundle);//公告详情 webview 打开
                } else if (url.contains("product/details")) {//商品详情页
                    String[] str_urls = url.split("/");
                    String id_ = str_urls[5];
                    bundle.putString("type_share", "1");
                    bundle.putString("goods_id", id_);
                    bundle.putString("shop_name", "");
                    ((CirclrFengActivity) context).openActivity(PushSpeedDetialActivity.class, bundle);//  商品详情页  webview打开
                } else if (url.contains("product/sellerproduct")) {//品牌商品页
                    /*String[] str_urls = url.split("=");
                    String id_ = str_urls[1];*/
                    bundle.putString("url", url);
                    bundle.putString("index", "0");
                    ((CirclrFengActivity) context).openActivity(WebViewActivity.class, bundle);//  商品详情页  webview打开

                } else if (url.contains("supply/selectsupply")) {//随时赚  原生态页面打开

                    bundle.putString("index", "1");
                    ((CirclrFengActivity) context).openActivity(ApplicationActivity.class, bundle);
                } else if (url.contains("tuanbi/mytasklist")) {//我的任务
                    ((CirclrFengActivity) context).openActivity(MyTuanBiActivity.class);//  我的任务 原生态打开打开
                } else if (url.contains("study/teacherdetail")) {
                    String[] ids_ = url.split("=");
//                    bundle.putString("index", "1");
//                    bundle.putString("id", ids_[1].substring(3, ids_[1].length()));
//                    ((CirclrFengActivity)context).openActivity(FamousTeacherDetialActivity.class, bundle);//  名师讲堂 详情页 原生态打开打开

                    Intent intent1 = new Intent(context, CourseIntrActivity.class);
                    intent1.putExtra("id", ids_[1]);
                    ((CirclrFengActivity) context).startActivity(intent1);
                } else if (url.contains("Series")) {//专题列表
                    String linkUrl = url.replace("Series", "");
                    Intent intent = new Intent(context, SeriesClassActivity.class);
                    intent.putExtra("linkUrl", linkUrl);
                    ((CirclrFengActivity) context).startActivity(intent);
                } else if (url.contains("Activity")) {//活动详情
                    String activityId = url.replace("Activity", "");
                    Intent intent = new Intent(context, ActDetailActivity.class);
                    intent.putExtra("activityid", activityId);
                    context.startActivity(intent);

                } else if (url.contains("ApplyForStudy")) {//申请开课
                    Intent intent = new Intent(context, ApplyCourseActivity.class);
                    context.startActivity(intent);
                } else if (url.contains("ApplyForTeacher")) {//申请讲师
                    Intent intent2 = new Intent(context, ApplyTeacherActivity.class);
                    ((CirclrFengActivity) context).startActivity(intent2);
                } else if (url.contains("UserFengWen")) {//普通蜂文
                    Bundle pBundle = new Bundle();
                    String fengId = url.replace("UserFengWen", "");
                    Intent intent_one = new Intent(context, NewDynamicDetailsActivity.class);
                    intent_one.putExtras(pBundle);
                    intent_one.putExtra("position", "0");
                    intent_one.putExtra("talkId", fengId);
                    ((CirclrFengActivity) context).startActivity(intent_one);
                } else if (url.contains("FengWen")) {//（官方蜂文）
                    Bundle pBundle = new Bundle();
                    String fengId = url.replace("FengWen", "");
                    CircleRecommentEntity circleRecommentEntity = new CircleRecommentEntity();
                    circleRecommentEntity.setID(fengId);
                    Intent intent_one = new Intent(context, NewRecomFengWenDetailsActivity.class);
                    intent_one.putExtras(pBundle);
                    intent_one.putExtra("talkId", fengId);
                    ((CirclrFengActivity) context).startActivity(intent_one);
                } else if (url.contains("Topic")) {//（话题）
                    String topicId = url.replace("Topic", "");
                    Intent intent_one = new Intent(context, TopicDetailsActivity.class);
                    intent_one.putExtra("topicId", topicId);
                    ((CirclrFengActivity) context).startActivity(intent_one);
                } else {
                    bundle.putString("url", url);
                    bundle.putString("index", "0");
                    ((CirclrFengActivity) context).openActivity(WebViewActivity.class, bundle);// 其余均是 url 文网页打开
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}