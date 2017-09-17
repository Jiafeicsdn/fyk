package com.lvgou.distribution.bean;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/12/29.
 */
public class SysMsgData {
    private TextView txt_content;
    private ImageView img_up_down;
private int text_lines;

    public int getText_lines() {
        return text_lines;
    }

    public void setText_lines(int text_lines) {
        this.text_lines = text_lines;
    }

    public TextView getTxt_content() {
        return txt_content;
    }

    public void setTxt_content(TextView txt_content) {
        this.txt_content = txt_content;
    }

    public ImageView getImg_up_down() {
        return img_up_down;
    }

    public void setImg_up_down(ImageView img_up_down) {
        this.img_up_down = img_up_down;
    }
}
