package com.lvgou.distribution.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lvgou.distribution.R;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.lvgou.distribution.utils.MyToast;


/**
 * Created by Snow on 2016/3/21 0021.
 * 新手指南 老版 可删
 */
public class NewGuidersActivity extends BaseActivity {

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.rl_operation)
    private RelativeLayout rl_operation;//操作指南
    @ViewInject(R.id.rl_shopping)
    private RelativeLayout rl_shopping;// 购物需知
    @ViewInject(R.id.tv_operation)
    private TextView tv_operation;
    @ViewInject(R.id.tv_shopping)
    private TextView tv_shopping;
    @ViewInject(R.id.view_operation)
    private View view_operation;
    @ViewInject(R.id.view_shopping)
    private View view_shopping;

    @ViewInject(R.id.ll_opreation)
    private LinearLayout ll_operation;//操作指南
    @ViewInject(R.id.ll_shopping_know)
    private LinearLayout ll_shopping_know;// 购物需知
    @ViewInject(R.id.rl_function)
    private RelativeLayout rl_function;//APP功能介绍
    @ViewInject(R.id.rl_operation_guide)
    private RelativeLayout rl_operation_guide;// APP 操作指南
    @ViewInject(R.id.ll_opreation_guide)
    private LinearLayout ll_opreation_guide;
    @ViewInject(R.id.ll_function)
    private LinearLayout ll_function;
    @ViewInject(R.id.tv_function)
    private TextView tv_function;
    @ViewInject(R.id.tv_operation_guide)
    private TextView tv_operation_guide;
    @ViewInject(R.id.view_function)
    private View view_function;
    @ViewInject(R.id.view_operation_guide)
    private View view_operation_guide;

    @ViewInject(R.id.rl_create_shop)
    private RelativeLayout rl_create_shop;// 创建店铺
    @ViewInject(R.id.rl_push_goods)
    private RelativeLayout rl_push_goods;//推送店铺及单品
    @ViewInject(R.id.rl_query_logistics)
    private RelativeLayout rl_query_logistics;//查询物流信息
    @ViewInject(R.id.rl_withdrawals)
    private RelativeLayout rl_withdrawals;// 佣金及提现
    @ViewInject(R.id.rl_order_process)
    private RelativeLayout rl_order_process;//新手下单流程
    @ViewInject(R.id.rl_update_info)
    private RelativeLayout rl_update_info;// 修改信息

    @ViewInject(R.id.rl_shopping_manger)
    private RelativeLayout rl_shopping_manger;// 商品管理
    @ViewInject(R.id.rl_order_manger)
    private RelativeLayout rl_order_manger;// 订单管理
    @ViewInject(R.id.rl_push_speed)
    private RelativeLayout rl_push_speed;//爆品速推
    @ViewInject(R.id.rl_sms_free)
    private RelativeLayout rl_sms_free;//免费短信
    @ViewInject(R.id.rl_my_task)
    private RelativeLayout rl_my_task;// 我的任务
    @ViewInject(R.id.rl_more)
    private RelativeLayout rl_more;//更多
    @ViewInject(R.id.rl_share)
    private RelativeLayout rl_share;// 分享
    @ViewInject(R.id.rl_dialog_share_root)
    private RelativeLayout rl_dialog_share_root;
    @ViewInject(R.id.ll_dialog_share_cotent)
    private LinearLayout ll_dialog_share_cotent;
    @ViewInject(R.id.rl_qq)
    private RelativeLayout rl_qq;
    @ViewInject(R.id.rl_qzone)
    private RelativeLayout rl_qzone;
    @ViewInject(R.id.rl_weixin)
    private RelativeLayout rl_weixin;
    @ViewInject(R.id.rl_pengyou)
    private RelativeLayout rl_pengyou;
    @ViewInject(R.id.rl_dismiss)
    private RelativeLayout rl_dismiss;

    private String index = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_guiders);
        ViewUtils.inject(this);
        index = getTextFromBundle("index");
        initTitle();
        initGuide();
        ll_operation.setVisibility(View.VISIBLE);
        ll_function.setVisibility(View.VISIBLE);
        rl_share.setVisibility(View.GONE);
        tv_operation.setTextColor(getResources().getColor(R.color.bg_code_number));
        view_operation.setVisibility(View.VISIBLE);
        tv_function.setTextColor(getResources().getColor(R.color.bg_code_number));
        view_function.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.rl_back, R.id.rl_operation, R.id.rl_shopping, R.id.rl_function, R.id.rl_operation_guide, R.id.rl_create_shop, R.id.rl_push_goods, R.id.rl_query_logistics
            , R.id.rl_withdrawals, R.id.rl_order_process, R.id.rl_update_info, R.id.rl_shopping_manger, R.id.rl_order_manger, R.id.rl_push_speed,
            R.id.rl_sms_free, R.id.rl_my_task, R.id.rl_more, R.id.rl_share, R.id.rl_qq, R.id.rl_qzone, R.id.rl_weixin, R.id.rl_pengyou, R.id.rl_dismiss})
    public void OnClick(View view) {
        UMImage image = new UMImage(NewGuidersActivity.this, R.mipmap.fenxiangon);
        Bundle pBundle = new Bundle();
        switch (view.getId()) {
            case R.id.rl_back:
                if (index.equals("1")) {
                    pBundle.putString("selection_postion", "2");
                    openActivity(HomeActivity.class, pBundle);
                    finish();
                } else {
                    finish();
                }
                break;
            case R.id.rl_operation:
                initTitle();
                initGuide();
                rl_share.setVisibility(View.GONE);
                ll_operation.setVisibility(View.VISIBLE);
                ll_function.setVisibility(View.VISIBLE);
                tv_operation.setTextColor(getResources().getColor(R.color.bg_code_number));
                view_operation.setVisibility(View.VISIBLE);
                tv_function.setTextColor(getResources().getColor(R.color.bg_code_number));
                view_function.setVisibility(View.VISIBLE);
                break;
            case R.id.rl_shopping:
                initTitle();
                initGuide();
                rl_share.setVisibility(View.VISIBLE);
                ll_shopping_know.setVisibility(View.VISIBLE);
                tv_shopping.setTextColor(getResources().getColor(R.color.bg_code_number));
                view_shopping.setVisibility(View.VISIBLE);
                break;
            case R.id.rl_function:
                initGuide();
                ll_function.setVisibility(View.VISIBLE);
                tv_function.setTextColor(getResources().getColor(R.color.bg_code_number));
                view_function.setVisibility(View.VISIBLE);
                break;
            case R.id.rl_operation_guide:
                initGuide();
                ll_opreation_guide.setVisibility(View.VISIBLE);
                tv_operation_guide.setTextColor(getResources().getColor(R.color.bg_code_number));
                view_operation_guide.setVisibility(View.VISIBLE);
                break;
            case R.id.rl_create_shop:
                pBundle.putString("page_id", "36");
                pBundle.putString("name", "新手如何创建店铺？");
                openActivity(NewGuidersDetialActivity.class, pBundle);
                break;
            case R.id.rl_push_goods:
                pBundle.putString("page_id", "43");
                pBundle.putString("name", "如何推送店铺及单品？");
                openActivity(NewGuidersDetialActivity.class, pBundle);
                break;
            case R.id.rl_query_logistics:
                pBundle.putString("page_id", "71");
                pBundle.putString("name", "如何查询物流信息？");
                openActivity(NewGuidersDetialActivity.class, pBundle);
                break;
            case R.id.rl_withdrawals:
                pBundle.putString("page_id", "37");
                pBundle.putString("name", "佣金及提现");
                openActivity(NewGuidersDetialActivity.class, pBundle);
                break;
            case R.id.rl_order_process:
                pBundle.putString("page_id", "44");
                pBundle.putString("name", "游客下单流程");
                openActivity(NewGuidersDetialActivity.class, pBundle);
                break;
            case R.id.rl_update_info:
                pBundle.putString("page_id", "42");
                pBundle.putString("name", "修改店铺名称及头像");
                openActivity(NewGuidersDetialActivity.class, pBundle);
                break;
            case R.id.rl_shopping_manger:
                pBundle.putString("page_id", "36");
                pBundle.putString("name", "商品管理");
                openActivity(NewGuidersDetialActivity.class, pBundle);
                break;
            case R.id.rl_order_manger:
                pBundle.putString("page_id", "38");
                pBundle.putString("name", "订单管理");
                openActivity(NewGuidersDetialActivity.class, pBundle);
                break;
            case R.id.rl_push_speed:
                pBundle.putString("page_id", "92");
                pBundle.putString("name", "爆品速推");
                openActivity(NewGuidersDetialActivity.class, pBundle);
                break;
            case R.id.rl_sms_free:
                pBundle.putString("page_id", "40");
                pBundle.putString("name", "免费短信");
                openActivity(NewGuidersDetialActivity.class, pBundle);
                break;
            case R.id.rl_my_task:
                pBundle.putString("page_id", "93");
                pBundle.putString("name", "我都任务");
                openActivity(NewGuidersDetialActivity.class, pBundle);
                break;
            case R.id.rl_more:
                MyToast.show(NewGuidersActivity.this, "更多期待");
                break;
            case R.id.rl_share:
                openDialog();
                break;
            case R.id.rl_qq:
                UMWeb web3 = new UMWeb("http://agent.quygt.com/study/buyerreading");
                web3.setTitle("蜂优客-购物须知");//标题
                web3.setThumb(image);  //缩略图
                web3.setDescription("国内商品均采自地方优质商家，老字号品牌商；品质、价格皆有保证；商品保质期见商品详情页或包装；");//描述
                new ShareAction(NewGuidersActivity.this)
                        .setPlatform(SHARE_MEDIA.QQ)
                        .setCallback(umShareListener)
                        .withMedia(web3)
                        .share();
                /*new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.QQ)
                        .setCallback(umShareListener)
                        .withTitle("蜂优客-购物须知")
                        .withText("国内商品均采自地方优质商家，老字号品牌商；品质、价格皆有保证；商品保质期见商品详情页或包装；")
                        .withTargetUrl("http://agent.quygt.com/study/buyerreading")
                        .withMedia(image)
                        .share();*/
                closeDialog();
                break;
            case R.id.rl_qzone:
                UMWeb web2 = new UMWeb("http://agent.quygt.com/study/buyerreading");
                web2.setTitle("蜂优客-购物须知");//标题
                web2.setThumb(image);  //缩略图
                web2.setDescription("国内商品均采自地方优质商家，老字号品牌商；品质、价格皆有保证；商品保质期见商品详情页或包装；");//描述
                new ShareAction(NewGuidersActivity.this)
                        .setPlatform(SHARE_MEDIA.QZONE)
                        .setCallback(umShareListener)
                        .withMedia(web2)
                        .share();
                /*new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.QZONE)
                        .setCallback(umShareListener)
                        .withTitle("蜂优客-购物须知")
                        .withText("国内商品均采自地方优质商家，老字号品牌商；品质、价格皆有保证；商品保质期见商品详情页或包装；")
                        .withTargetUrl("http://agent.quygt.com/study/buyerreading")
                        .withMedia(image)
                        .share();*/
                closeDialog();
                break;
            case R.id.rl_weixin:
                UMWeb web1 = new UMWeb("http://agent.quygt.com/study/buyerreading");
                web1.setTitle("蜂优客-购物须知");//标题
                web1.setThumb(image);  //缩略图
                web1.setDescription("国内商品均采自地方优质商家，老字号品牌商；品质、价格皆有保证；商品保质期见商品详情页或包装；");//描述
                new ShareAction(NewGuidersActivity.this)
                        .setPlatform(SHARE_MEDIA.WEIXIN)
                        .setCallback(umShareListener)
                        .withMedia(web1)
                        .share();
                /*new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.WEIXIN)
                        .setCallback(umShareListener)
                        .withTitle("蜂优客-购物须知")
                        .withText("国内商品均采自地方优质商家，老字号品牌商；品质、价格皆有保证；商品保质期见商品详情页或包装；")
                        .withTargetUrl("http://agent.quygt.com/study/buyerreading")
                        .withMedia(image)
                        .share();*/
                closeDialog();
                break;
            case R.id.rl_pengyou:
                UMWeb web = new UMWeb("http://agent.quygt.com/study/buyerreading");
                web.setTitle("蜂优客-购物须知");//标题
                web.setThumb(image);  //缩略图
                web.setDescription("国内商品均采自地方优质商家，老字号品牌商；品质、价格皆有保证；商品保质期见商品详情页或包装；");//描述
                new ShareAction(NewGuidersActivity.this)
                        .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setCallback(umShareListener)
                        .withMedia(web)
                        .share();
                /*new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setCallback(umShareListener)
                        .withTitle("蜂优客-购物须知")
                        .withText("国内商品均采自地方优质商家，老字号品牌商；品质、价格皆有保证；商品保质期见商品详情页或包装；")
                        .withTargetUrl("http://agent.quygt.com/study/buyerreading")
                        .withMedia(image)
                        .share();*/
                closeDialog();
                break;
            case R.id.rl_dismiss:
                closeDialog();
                break;
        }
    }

    /**
     * title 状态初始化
     */
    public void initTitle() {
        tv_operation.setTextColor(getResources().getColor(R.color.bg_new_guide_black));
        tv_shopping.setTextColor(getResources().getColor(R.color.bg_new_guide_black));
        view_operation.setVisibility(View.GONE);
        view_shopping.setVisibility(View.GONE);
        ll_operation.setVisibility(View.GONE);
        ll_shopping_know.setVisibility(View.GONE);
    }

    /**
     * 操作指南 状态初始化
     */
    public void initGuide() {
        tv_function.setTextColor(getResources().getColor(R.color.bg_new_guide_black));
        tv_operation_guide.setTextColor(getResources().getColor(R.color.bg_new_guide_black));
        view_function.setVisibility(View.GONE);
        view_operation_guide.setVisibility(View.GONE);
        ll_function.setVisibility(View.GONE);
        ll_opreation_guide.setVisibility(View.GONE);
    }

    // 弹出对话框
    private void openDialog() {
        performDialogAnimation(rl_dialog_share_root,
                ll_dialog_share_cotent, true);
    }

    // 关闭对话框
    private void closeDialog() {
        performDialogAnimation(rl_dialog_share_root,
                ll_dialog_share_cotent, false);
    }

    public void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
    }
}
