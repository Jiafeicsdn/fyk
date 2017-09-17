package com.lvgou.distribution.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.lvgou.distribution.utils.MyToast;import android.widget.Toast;

import com.lvgou.distribution.R;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.presenter.FeedBackPresenter;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.view.FeedBackView;
import com.xdroid.common.utils.PreferenceHelper;

/**
 * Created by Administrator on 2017/3/30.
 */

public class FeedBackActivity extends BaseActivity implements View.OnClickListener, FeedBackView {
    private FeedBackPresenter feedBackPresenterp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        feedBackPresenterp = new FeedBackPresenter(this);
        initView();
        initClick();
    }

    private RelativeLayout rl_back;
    private TextView tv_title;
    private EditText et_feedback;//意见反馈
    private TextView tv_text_num;
    private TextView tv_feedback;

    private void initView() {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("意见反馈");
        et_feedback = (EditText) findViewById(R.id.et_feedback);
        tv_text_num = (TextView) findViewById(R.id.tv_text_num);
        //改变默认的单行模式
        et_feedback.setSingleLine(false);
        //水平滚动设置为False
        et_feedback.setHorizontallyScrolling(false);
        TextChangeListener();
        tv_feedback = (TextView) findViewById(R.id.tv_feedback);
    }

    private void initClick() {
        rl_back.setOnClickListener(this);
        tv_feedback.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_feedback://提交反馈
                String feedback = et_feedback.getText().toString().trim();
                if (feedback == null || feedback.equals("")) {
                    MyToast.makeText(this, "请输入您的反馈！", Toast.LENGTH_SHORT).show();
                    break;
                }

                String distributorid = PreferenceHelper.readString(this, SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);
                String sign = TGmd5.getMD5(distributorid + feedback);
                showLoadingProgressDialog(this, "");
                feedBackPresenterp.feedBack(distributorid, feedback, sign);
                break;
        }
    }

    public void TextChangeListener() {
        et_feedback.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String length = et_feedback.getText().length() + "";
                if (Integer.parseInt(length) >= 0 && Integer.parseInt(length) <= 200) {
                    tv_text_num.setText(length + "/200");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void OnFeedBackSuccCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
        MyToast.makeText(this, "感谢您的反馈!", Toast.LENGTH_SHORT).show();
        FeedBackActivity.this.finish();
    }

    @Override
    public void OnFeedBackFialCallBack(String state, String respanse) {
        closeLoadingProgressDialog();
        MyToast.makeText(this, "" + respanse, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void closeFeedBackProgress() {

    }
}
