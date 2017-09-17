package com.lvgou.distribution.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.CornerPathEffect;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.lidroid.xutils.ViewUtils;
import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.Constants;
import com.lvgou.distribution.cutView.CutListener;
import com.lvgou.distribution.cutView.CutView;

/**
 * Created by Snow on 2016/8/5
 * 头像裁剪
 */
public class CutHeadActivity extends BaseActivity implements View.OnClickListener, CutListener {

    private CutView cutView;
    private Button cutBtn, cancelBtn;
    private Handler handler;
    public static Bitmap bitmap;

    private CutView.PathType cutPathType;
    private Bitmap result_bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fagment_cut);
        ViewUtils.inject(this);
    }


    private void init(View view) {
        cutView = (CutView) view.findViewById(R.id.cutview);
        result_bitmap = BitmapFactory.decodeFile(Constants.IMGPARH);
        cutView.setImageBitmap(result_bitmap);
        cutView.setCutListener(this);//设置剪裁监听，用于剪裁完成后获取圆形头像
        //自定义CutView的一些属性
        cutView.setShadeColor(0X6674d6b5);//色值要使用argb，带透明度的
        cutView.setPathColor(0Xfff9771e);//argb
        cutView.setCutRadius(150);//这里的单位是px
        cutView.setPathEffect(new CornerPathEffect(3.0f));//将默认的圆形虚线线条改为实线
        cutView.setPathWidth(2);//设置线条宽度，默认是2，单位是px
        cutView.setCutFillColor(0xffffffff);//图片剪切空白部分颜色设置为白色，argb
        cutView.setPathType(cutPathType);
        cutView.setRoundRectRadius(10.0f);//如果剪切类型为圆角矩形，需要设置圆角矩形的圆角半径，可不设置默认是3.0f
//        cutView.setPathType(CutView.PathType.RECT);//设置剪切类型为矩形，那么矩形的宽高就是上面的设置的CutRadius的2倍，也就是300px。
//        cutView.setPathType(CutView.PathType.ROUNDRECT);//设置剪切类型为矩形，那么矩形的宽高就是上面的设置的CutRadius的2倍，也就是300px。
//        cutView.setRoundRectRadius(10.0f);//如果剪切类型为圆角矩形，需要设置圆角矩形的圆角半径，可不设置默认是3.0f

        cutBtn = (Button) view.findViewById(R.id.cutBtn);
        cutBtn.setOnClickListener(this);
        cancelBtn = (Button) view.findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(this);
    }

    @Override
    public void cutResultWithBitmap(Bitmap bitmap) {

        //将剪裁的得来的Bitmap通过handler发送到MainActivity
        handler.obtainMessage(0, bitmap).sendToTarget();
        getFragmentManager().popBackStack();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cutBtn:
                if (bitmap != null) {
                    bitmap.recycle();
                }
                cutView.clipImage();
                break;
            case R.id.cancelBtn:
                getFragmentManager().popBackStack();
                break;
        }
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public void setCutPathType(CutView.PathType cutPathType) {
        this.cutPathType = cutPathType;
    }
}
