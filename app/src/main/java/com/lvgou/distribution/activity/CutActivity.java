package com.lvgou.distribution.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import com.lvgou.distribution.R;
import com.lvgou.distribution.view.MyImageView;


public class CutActivity extends Activity implements View.OnClickListener {

    private MyImageView mImageView;
    public static Bitmap bitmap;
    private String img_path;
    private String code = ""; //1：返回 相册  2：返回 相机

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cut);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            img_path = bundle.getString("img_path");
            code = bundle.getString("code_");
        }
        initView();
    }

    private void initView() {
        View back = findViewById(R.id.tv_left);
        View finish = findViewById(R.id.tv_right);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);
        finish.setVisibility(View.VISIBLE);
        finish.setOnClickListener(this);
        mImageView = (MyImageView) findViewById(R.id.my_image_view);
        mImageView.setImageResource(img_path);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left:
                if (code.equals("1")) {
                    setResult(10);
                    onBackPressed();
                } else if (code.equals("2")){
                    setResult(9);
                    onBackPressed();
                }
                break;
            case R.id.tv_right:
                if (bitmap != null) {
                    bitmap.recycle();
                }
                bitmap = mImageView.clipImage();
                setResult(11);
                finish();
                break;
            default:
                break;
        }
    }
}
