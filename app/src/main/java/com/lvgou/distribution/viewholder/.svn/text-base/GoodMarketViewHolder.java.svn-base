package com.lvgou.distribution.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.entity.GoodMarketChildEntity;
import com.lvgou.distribution.entity.GoodMarketEntity;
import com.lvgou.distribution.inter.OnGoodsClickListener;
import com.lvgou.distribution.utils.CommonFunctions;
import com.lvgou.distribution.view.MyListView;
import com.xdroid.functions.holder.ListViewDataAdapter;
import com.xdroid.functions.holder.ViewHolderBase;


/**
 * Created by Snow on 2016/3/24 0024.
 */
public class GoodMarketViewHolder extends ViewHolderBase<GoodMarketEntity> {

    private TextView tv_name;
    private ImageView img_te;
    private ImageView img_jian;
    private ImageView img_huo;
    private TextView tv_status;
    private MyListView lv_list;
    private RelativeLayout rl_load_more;
    private TextView tv_load_more;
    private Context context;
    private RelativeLayout rl_zhankai;
    private RelativeLayout rl_02;
    private TextView tv_zhankai;
    private ImageView img_up;
    private ListViewDataAdapter<GoodMarketChildEntity> goodMarketChildEntityListViewDataAdapter;

    private static OnGoodsClickListener<GoodMarketEntity> goodMarketEntityOnGoodsClickListener;

    @Override
    public View createView(LayoutInflater layoutInflater) {
        context = layoutInflater.getContext();
        View view = layoutInflater.inflate(R.layout.item_goods_market, null);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        img_te = (ImageView) view.findViewById(R.id.img_te);
        img_jian = (ImageView) view.findViewById(R.id.img_jian);
        img_huo = (ImageView) view.findViewById(R.id.img_huo);
        tv_status = (TextView) view.findViewById(R.id.tv_remove_in);
        lv_list = (MyListView) view.findViewById(R.id.rl_list);
        rl_zhankai = (RelativeLayout) view.findViewById(R.id.rl_zhankai);
        rl_02 = (RelativeLayout) view.findViewById(R.id.rl_02);
        tv_zhankai = (TextView) view.findViewById(R.id.tv_zhankai);
        img_up = (ImageView) view.findViewById(R.id.img_up);
        rl_load_more = (RelativeLayout) view.findViewById(R.id.rl_load_more);
        tv_load_more = (TextView) view.findViewById(R.id.tv_load_more);
        return view;
    }


    @Override
    public void showData(final int position, final GoodMarketEntity itemData) {
        if (itemData.getName().length() > 8) {
            tv_name.setText(itemData.getName().substring(0, 8) + "...");
        } else {
            tv_name.setText(itemData.getName());
        }
        if (itemData.getStatus_show().equals("true")) {
            tv_status.setText("已分销");
            tv_status.setTextColor(context.getResources().getColor(R.color.bg_main_gray));
            tv_status.setBackgroundResource(R.drawable.bg_wight_shape);
        } else if (itemData.getStatus_show().equals("false")) {
            tv_status.setText("移入货架");
            tv_status.setTextColor(context.getResources().getColor(R.color.bg_button_text_black));
            tv_status.setBackgroundResource(R.drawable.btn_orange_shpae);
        }
        if (itemData.getStatus_zhankai_show().equals("1")) {
            rl_02.setVisibility(View.VISIBLE);
            rl_zhankai.setVisibility(View.GONE);
            lv_list.setVisibility(View.VISIBLE);
            if (itemData.getStatus_load_more().equals("true")) {
                rl_load_more.setVisibility(View.VISIBLE);
                if (itemData.getGuide_status().equals("1")) {
                    tv_load_more.setText("点击查看更多...");
                } else if (itemData.getGuide_status().equals("2")) {
                    tv_load_more.setText("↑收起");
                }
            } else if (itemData.getStatus_load_more().equals("false")) {
                rl_load_more.setVisibility(View.GONE);
            }
            switch (Integer.parseInt(itemData.getStatue_img())) {
                case 1:
                    img_te.setVisibility(View.GONE);
                    img_jian.setVisibility(View.GONE);
                    img_huo.setVisibility(View.GONE);
                    break;
                case 2:
                    img_te.setVisibility(View.GONE);
                    img_jian.setVisibility(View.GONE);
                    img_huo.setVisibility(View.VISIBLE);
                    break;
                case 4:
                    img_te.setVisibility(View.GONE);
                    img_jian.setVisibility(View.GONE);
                    img_huo.setVisibility(View.GONE);
                    break;
                case 3:
                    img_te.setVisibility(View.GONE);
                    img_jian.setVisibility(View.GONE);
                    img_huo.setVisibility(View.VISIBLE);
                    break;
                case 5:
                    img_te.setVisibility(View.GONE);
                    img_jian.setVisibility(View.GONE);
                    img_huo.setVisibility(View.GONE);
                    break;
                case 6:
                    img_te.setVisibility(View.GONE);
                    img_jian.setVisibility(View.GONE);
                    img_huo.setVisibility(View.VISIBLE);
                    break;
                case 7:
                    img_te.setVisibility(View.GONE);
                    img_jian.setVisibility(View.GONE);
                    img_huo.setVisibility(View.VISIBLE);
                    break;
            }
        } else if (itemData.getStatus_zhankai_show().equals("2")) {
            rl_02.setVisibility(View.GONE);
            rl_zhankai.setVisibility(View.VISIBLE);
            if (itemData.getStatus_zhankai().equals("1")) {
                tv_zhankai.setText("收起商品");
                img_up.setBackgroundResource(R.mipmap.bg_shouqi);
                tv_name.getPaint().setFakeBoldText(true);
                lv_list.setVisibility(View.VISIBLE);
                if (itemData.getStatus_load_more().equals("true")) {
                    rl_load_more.setVisibility(View.VISIBLE);
                    if (itemData.getGuide_status().equals("1")) {
                        tv_load_more.setText("点击查看更多...");
                    } else if (itemData.getGuide_status().equals("2")) {
                        tv_load_more.setText("↑收起");
                    }
                } else if (itemData.getStatus_load_more().equals("false")) {
                    rl_load_more.setVisibility(View.GONE);
                }
            } else if (itemData.getStatus_zhankai().equals("2")) {
                tv_zhankai.setText("展开商品");
                img_up.setBackgroundResource(R.mipmap.bg_xiala);
                tv_name.getPaint().setFakeBoldText(false);
                lv_list.setVisibility(View.GONE);
                rl_load_more.setVisibility(View.GONE);
            }
        }

        goodMarketChildEntityListViewDataAdapter = itemData.getGoodMarketChildEntityListViewDataAdapter();
        goodMarketChildEntityListViewDataAdapter.setViewHolderClass(context, GoodMarketChildViewHolder.class);
        lv_list.setAdapter(goodMarketChildEntityListViewDataAdapter);

        tv_status.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             CommonFunctions.getInstance().setCartImageAnim(v);
                                             if (itemData.getStatus_show().equals("true")) {
                                                 return;
                                             } else if (itemData.getStatus_show().equals("false")) {
                                                 tv_status.setText("已移入");
                                                 itemData.setStatus_show("true");
                                                 tv_status.setTextColor(context.getResources().getColor(R.color.bg_main_gray));
                                                 tv_status.setBackgroundResource(R.drawable.bg_wight_shape);
                                                 if (goodMarketEntityOnGoodsClickListener != null) {
                                                     goodMarketEntityOnGoodsClickListener.onGoodsClick(itemData, position, tv_status, 3);
                                                 }
                                             }
                                         }
                                     }

        );
        tv_load_more.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if (itemData.getGuide_status().equals("1")) {
                                                    if (goodMarketEntityOnGoodsClickListener != null) {
                                                        goodMarketEntityOnGoodsClickListener.onGoodsClick(itemData, position, tv_status, 4);
                                                        tv_load_more.setText("↑收起");
                                                    }
                                                } else if (itemData.getGuide_status().equals("2")) {
                                                    if (goodMarketEntityOnGoodsClickListener != null) {
                                                        goodMarketEntityOnGoodsClickListener.onGoodsClick(itemData, position, tv_status, 5);
                                                        tv_load_more.setText("点击查看更多...");
                                                    }

                                                }
                                            }
                                        }

        );

        rl_zhankai.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              if (itemData.getStatus_zhankai().equals("1")) {
                                                  if (goodMarketEntityOnGoodsClickListener != null) {
                                                      goodMarketEntityOnGoodsClickListener.onGoodsClick(itemData, position, tv_status, 6);
                                                      tv_zhankai.setText("展开商品");
                                                      img_up.setBackgroundResource(R.mipmap.bg_shouqi);
                                                      lv_list.setVisibility(View.GONE);
                                                      rl_load_more.setVisibility(View.GONE);
                                                  }
                                              } else if (itemData.getStatus_zhankai().equals("2")) {
                                                  if (goodMarketEntityOnGoodsClickListener != null) {
                                                      goodMarketEntityOnGoodsClickListener.onGoodsClick(itemData, position, tv_status, 7);
                                                      tv_zhankai.setText("收起商品");
                                                      img_up.setBackgroundResource(R.mipmap.bg_xiala);
                                                      lv_list.setVisibility(View.VISIBLE);
                                                      if (itemData.getStatus_load_more().equals("true")) {
                                                          rl_load_more.setVisibility(View.VISIBLE);
                                                      } else if (itemData.getStatus_load_more().equals("false")) {
                                                          rl_load_more.setVisibility(View.GONE);
                                                      }
                                                  }
                                              }
                                          }
                                      }

        );
    }

    public static void setGoodMarketEntityOnGoodsClickListener(OnGoodsClickListener<GoodMarketEntity> goodMarketEntityOnGoodsClickListener) {
        GoodMarketViewHolder.goodMarketEntityOnGoodsClickListener = goodMarketEntityOnGoodsClickListener;
    }
}
