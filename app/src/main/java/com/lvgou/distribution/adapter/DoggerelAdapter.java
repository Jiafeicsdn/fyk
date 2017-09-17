package com.lvgou.distribution.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.lvgou.distribution.utils.MyToast;import android.widget.Toast;

import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.DoggerelActivity;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.inter.AdapterToFraImpl;
import com.lvgou.distribution.presenter.CancelJokeCollectionPersenter;
import com.lvgou.distribution.presenter.JokeCollectionPresenter;
import com.lvgou.distribution.presenter.JokeOperationPresenter;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.utils.ViewHolder;
import com.lvgou.distribution.view.CancelJokeCollectionView;
import com.lvgou.distribution.view.JokeCollectionView;
import com.lvgou.distribution.view.JokeOperationView;
import com.wx.goodview.GoodView;
import com.xdroid.common.utils.PreferenceHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/3/31.
 */

public class DoggerelAdapter extends BaseAdapter implements JokeOperationView, JokeCollectionView, CancelJokeCollectionView {
    Context context;
    ArrayList<HashMap<String, Object>> list;
    private JokeOperationPresenter jokeOperationPresenter;
    private JokeCollectionPresenter jokeCollectionPresenter;
    private CancelJokeCollectionPersenter cancelJokeCollectionPersenter;
    GoodView goodView;

    public DoggerelAdapter(Context _context) {
        this.context = _context;
        goodView = new GoodView(context);
        jokeOperationPresenter = new JokeOperationPresenter(this);
        jokeCollectionPresenter = new JokeCollectionPresenter(this);
        cancelJokeCollectionPersenter = new CancelJokeCollectionPersenter(this);
    }

    public void setData(ArrayList<HashMap<String, Object>> list) {
        this.list = list;
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
        viewHolder = ViewHolder.getVH(convertView, context, R.layout.doggerel_list_item);
        TextView tv_content = viewHolder.getView(R.id.tv_content, TextView.class);//内容
        RelativeLayout rl_ding = viewHolder.getView(R.id.rl_ding, RelativeLayout.class);//顶
        //顶的图标
        final ImageView im_ding = viewHolder.getView(R.id.im_ding, ImageView.class);
        //顶的数量
        final TextView tv_ding_num = viewHolder.getView(R.id.tv_ding_num, TextView.class);
        RelativeLayout rl_cai = viewHolder.getView(R.id.rl_cai, RelativeLayout.class);//踩
        //踩的图标
        final ImageView im_cai = viewHolder.getView(R.id.im_cai, ImageView.class);
        //踩的数量
        final TextView tv_cai_num = viewHolder.getView(R.id.tv_cai_num, TextView.class);
        RelativeLayout rl_collect = viewHolder.getView(R.id.rl_collect, RelativeLayout.class);//收藏
        final ImageView im_collect = viewHolder.getView(R.id.im_collect, ImageView.class);//收藏的图标
        final TextView tv_collect = viewHolder.getView(R.id.tv_collect, TextView.class);//收藏的文字
        tv_content.setText(info.get("Content").toString());
        tv_ding_num.setText(info.get("Ding").toString().equals("0")?"顶":info.get("Ding").toString());
        tv_cai_num.setText(info.get("Cai").toString().equals("0")?"踩":info.get("Cai").toString());
        if (Integer.parseInt(info.get("Hits").toString()) > 0) {
            //表示已经收藏
            im_collect.setBackgroundResource(R.mipmap.collect_select_icon);
            tv_collect.setTextColor(Color.parseColor("#D5AA5F"));
        } else {
            //表示没有收藏
            im_collect.setBackgroundResource(R.mipmap.collect_normal_icon);
            tv_collect.setTextColor(Color.parseColor("#bababa"));
        }
        if (info.get("State").toString().equals("0")) {
            //没有顶又没有踩
            im_ding.setBackgroundResource(R.mipmap.ding_normal_icon);
            tv_ding_num.setTextColor(Color.parseColor("#bababa"));
            im_cai.setBackgroundResource(R.mipmap.cai_normal_icon);
            tv_cai_num.setTextColor(Color.parseColor("#bababa"));
        } else if (info.get("State").toString().equals("1")) {
            //顶
            im_ding.setBackgroundResource(R.mipmap.ding_select_icon);
            tv_ding_num.setTextColor(Color.parseColor("#d5aa5f"));
            im_cai.setBackgroundResource(R.mipmap.cai_normal_icon);
            tv_cai_num.setTextColor(Color.parseColor("#bababa"));
        } else if (info.get("State").toString().equals("2")) {
            //踩
            im_ding.setBackgroundResource(R.mipmap.ding_normal_icon);
            tv_ding_num.setTextColor(Color.parseColor("#bababa"));
            im_cai.setBackgroundResource(R.mipmap.cai_selecte_icon);
            tv_cai_num.setTextColor(Color.parseColor("#d5aa5f"));
        }
        rl_ding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //顶
                if (info.get("State").toString().equals("1")) {
                    MyToast.makeText(context, "已经顶过了！", Toast.LENGTH_SHORT).show();
                } else {
                    String jokeid = info.get("ID").toString();
                    String distributorid = PreferenceHelper.readString(context, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
                    String sign = TGmd5.getMD5("" + distributorid + jokeid + 1);
                    dingStr = "dingsuccess";
                    caiStr = "caifail";
                    opePosition = position;
                    mim_ding = im_ding;
                    mtv_ding_num = tv_ding_num;
                    mim_cai = im_cai;
                    mtv_cai_num = tv_cai_num;
                    ((DoggerelActivity) context).showLoadingProgressDialog(context, "");
                    jokeOperationPresenter.jokeOperation(distributorid, jokeid, 1, sign);
                }
            }
        });
        rl_cai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (info.get("State").toString().equals("2")) {
                    MyToast.makeText(context, "已经踩过了！", Toast.LENGTH_SHORT).show();
                } else {
                    String jokeid = info.get("ID").toString();
                    String distributorid = PreferenceHelper.readString(context, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
                    String sign = TGmd5.getMD5("" + distributorid + jokeid + 2);
                    dingStr = "dingfail";
                    caiStr = "caisuccess";
                    opePosition = position;
                    mim_ding = im_ding;
                    mtv_ding_num = tv_ding_num;
                    mim_cai = im_cai;
                    mtv_cai_num = tv_cai_num;
                    ((DoggerelActivity) context).showLoadingProgressDialog(context, "");
                    jokeOperationPresenter.jokeOperation(distributorid, jokeid, 2, sign);
                }
            }
        });
        rl_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //收藏点击
                String jokeid = info.get("ID").toString();
                String distributorid = PreferenceHelper.readString(context, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
                String sign = TGmd5.getMD5("" + distributorid + jokeid);
                mim_collect = im_collect;
                mtv_collect = tv_collect;
                opePosition = position;
                if (Integer.parseInt(info.get("Hits").toString()) > 0) {
                    //如果已经收藏过了 现在去取消收藏
                    ((DoggerelActivity) context).showLoadingProgressDialog(context, "");
                    cancelJokeCollectionPersenter.cancelJokeCollection(distributorid, jokeid, sign);
                } else {
                    //如果之前没有收藏过 现在去收藏

                    ((DoggerelActivity) context).showLoadingProgressDialog(context, "");
                    jokeCollectionPresenter.jokeCollection(distributorid, jokeid, sign);
                }
            }
        });


        return viewHolder.convertView;
    }

    private String dingStr = "dingfail";
    private int opePosition = 0;
    private String caiStr = "caifail";
    private ImageView mim_ding;
    private TextView mtv_ding_num;
    private ImageView mim_cai;
    private TextView mtv_cai_num;

    @Override
    public void OnJokeOperationSuccCallBack(String state, String respanse) {
        ((DoggerelActivity) context).closeLoadingProgressDialog();
        //踩或者顶成功
        Log.e("khaskhfds", "--------" + respanse);
        HashMap<String, Object> stringObjectHashMap = list.get(opePosition);
        if (dingStr.equals("dingsuccess")) {
            //如果顶的成功了
            int dingnum = Integer.parseInt(stringObjectHashMap.get("Ding").toString()) + 1;
            if (stringObjectHashMap.get("State").equals("0")) {
                //原先没有顶又没有踩
                stringObjectHashMap.put("State", "1");
                list.get(opePosition).put("State", "1");
                stringObjectHashMap.put("Ding", dingnum + "");
                list.get(opePosition).put("Ding", dingnum + "");

                mim_ding.setBackgroundResource(R.mipmap.ding_select_icon);
                mtv_ding_num.setTextColor(Color.parseColor("#d5aa5f"));
                mtv_ding_num.setText(dingnum>0?dingnum + "":"顶");
                mim_cai.setBackgroundResource(R.mipmap.cai_normal_icon);
                mtv_cai_num.setTextColor(Color.parseColor("#bababa"));

            } else if (stringObjectHashMap.get("State").equals("1")) {
                //原先是顶
                Log.e("kjajsds", "--------------");

            } else if (stringObjectHashMap.get("State").equals("2")) {
                //原先是踩
                stringObjectHashMap.put("State", "1");
                list.get(opePosition).put("State", "1");
                int cainum = Integer.parseInt(stringObjectHashMap.get("Cai").toString()) - 1;
                stringObjectHashMap.put("Ding", dingnum + "");
                stringObjectHashMap.put("Cai", cainum + "");
                list.get(opePosition).put("Ding", dingnum + "");
                list.get(opePosition).put("Cai", cainum + "");

                mim_ding.setBackgroundResource(R.mipmap.ding_select_icon);
                mtv_ding_num.setTextColor(Color.parseColor("#d5aa5f"));
                mtv_ding_num.setText(dingnum>0?dingnum + "":"顶");
                mim_cai.setBackgroundResource(R.mipmap.cai_normal_icon);
                mtv_cai_num.setTextColor(Color.parseColor("#bababa"));
                mtv_cai_num.setText(cainum>0?cainum + "":"踩");
            }
            goodView.setImage(R.mipmap.ding_select_icon);
            goodView.show(mim_ding);

        } else if (caiStr.equals("caisuccess")) {
            int cainum = Integer.parseInt(stringObjectHashMap.get("Cai").toString()) + 1;
            if (stringObjectHashMap.get("State").equals("0")) {
                Log.e("khakshjssdss", "****111111*** ");
                //原先没有顶又没有踩
                stringObjectHashMap.put("State", "2");
                list.get(opePosition).put("State", "2");
                stringObjectHashMap.put("Cai", cainum + "");
                list.get(opePosition).put("Cai", cainum + "");

                mim_ding.setBackgroundResource(R.mipmap.ding_normal_icon);
                mtv_ding_num.setTextColor(Color.parseColor("#bababa"));
                mim_cai.setBackgroundResource(R.mipmap.cai_selecte_icon);
                mtv_cai_num.setTextColor(Color.parseColor("#d5aa5f"));
                mtv_cai_num.setText(cainum>0?cainum + "":"踩");
            } else if (stringObjectHashMap.get("State").equals("1")) {
                //原先是顶
                int dingnum = Integer.parseInt(stringObjectHashMap.get("Ding").toString()) - 1;
                Log.e("khakshjssdss", "******* " + dingnum);
                stringObjectHashMap.put("State", "2");
                list.get(opePosition).put("State", "2");
                stringObjectHashMap.put("Ding", dingnum + "");
                stringObjectHashMap.put("Cai", cainum + "");
                list.get(opePosition).put("Ding", dingnum + "");
                list.get(opePosition).put("Cai", cainum + "");

                mim_ding.setBackgroundResource(R.mipmap.ding_normal_icon);
                mtv_ding_num.setTextColor(Color.parseColor("#bababa"));
                mtv_ding_num.setText(dingnum>0?dingnum + "":"顶");
                mim_cai.setBackgroundResource(R.mipmap.cai_selecte_icon);
                mtv_cai_num.setTextColor(Color.parseColor("#d5aa5f"));
                mtv_cai_num.setText(cainum>0?cainum + "":"踩");

            } else if (stringObjectHashMap.get("State").equals("2")) {
                //原先是踩
                Log.e("khakshjssdss", "****22222222*** ");

            }
            goodView.setImage(R.mipmap.cai_selecte_icon);
            goodView.show(mim_cai);
        }
        adapterToFra.doSomeThing(stringObjectHashMap);
        caiStr = "caifail";
        dingStr = "dingfail";
        opePosition = 0;
    }

    AdapterToFraImpl adapterToFra;

    public void setAdapterToFraImpl(AdapterToFraImpl adapterToFra) {
        this.adapterToFra = adapterToFra;
    }

    @Override
    public void OnJokeOperationFialCallBack(String state, String respanse) {
        ((DoggerelActivity) context).closeLoadingProgressDialog();
        //踩或者顶失败
        /*if (dingStr.equals("dingsucces")) {
            //顶的失败了
        }*/
        dingStr = "dingfail";
        opePosition = 0;
    }

    @Override
    public void closeJokeOperationProgress() {

    }

    private ImageView mim_collect;
    private TextView mtv_collect;

    @Override
    public void OnJokeCollectionSuccCallBack(String state, String respanse) {
        //收藏成功
        ((DoggerelActivity) context).closeLoadingProgressDialog();
        mim_collect.setBackgroundResource(R.mipmap.collect_select_icon);
        mtv_collect.setTextColor(Color.parseColor("#D5AA5F"));
        goodView.setImage(R.mipmap.collect_select_icon);
        goodView.show(mim_collect);
        list.get(opePosition).put("Hits", "1");
        adapterToFra.doSomeThing(list.get(opePosition));
        opePosition = 0;
    }

    @Override
    public void OnJokeCollectionFialCallBack(String state, String respanse) {
        //收藏失败
        ((DoggerelActivity) context).closeLoadingProgressDialog();
        opePosition = 0;
    }

    @Override
    public void closeJokeCollectionProgress() {

    }

    @Override
    public void OnCancelJokeCollectionSuccCallBack(String state, String respanse) {
        //取消收藏成功
        ((DoggerelActivity) context).closeLoadingProgressDialog();
        mim_collect.setBackgroundResource(R.mipmap.collect_normal_icon);
        mtv_collect.setTextColor(Color.parseColor("#bababa"));
        goodView.setImage(R.mipmap.collect_normal_icon);
        goodView.show(mim_collect);
        list.get(opePosition).put("Hits", "0");
        adapterToFra.doSomeThing(list.get(opePosition));
        opePosition = 0;
    }

    @Override
    public void OnCancelJokeCollectionFialCallBack(String state, String respanse) {
        //取消收藏失败
        ((DoggerelActivity) context).closeLoadingProgressDialog();
        opePosition = 0;
    }

    @Override
    public void closeCancelJokeCollectionProgress() {

    }
}