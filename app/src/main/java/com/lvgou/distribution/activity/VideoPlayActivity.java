package com.lvgou.distribution.activity;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.lvgou.distribution.R;

/**
 * Created by Administrator on 2016/9/23.
 * 视频播放
 */
public class VideoPlayActivity extends Activity {
    String urlPath;
//    private SurfaceView surface;
//    private SurfaceHolder surfaceHolder;
private VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surfaceview);
        urlPath = getIntent().getStringExtra("path");

//        surface = (SurfaceView) findViewById(R.id.surface);
//        surfaceHolder = surface.getHolder();//SurfaceHolder是SurfaceView的控制接口
//        surfaceHolder.addCallback(callbackListener);//因为这个类实现了SurfaceHolder.Callback接口，所以回调参数直接this
//        surfaceHolder.setFixedSize(320, 220);//显示的分辨率,不设置为视频默认
//        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);//Surface类型

        Uri uri = Uri.parse( urlPath );

        videoView = (VideoView)this.findViewById(R.id.videoView);

        //设置视频控制器
        videoView.setMediaController(new MediaController(this));

        //播放完成回调
        videoView.setOnCompletionListener( new MyPlayerOnCompletionListener());

        //设置视频路径
        videoView.setVideoURI(uri);

        //开始播放视频
        videoView.start();
    }
    class MyPlayerOnCompletionListener implements MediaPlayer.OnCompletionListener {

        @Override
        public void onCompletion(MediaPlayer mp) {
            finish();
        }
    }
//    SurfaceHolder.Callback callbackListener = new SurfaceHolder.Callback() {
//        @Override
//        public void surfaceCreated(SurfaceHolder holder) {
//
//        }
//
//        @Override
//        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//
//        }
//
//        @Override
//        public void surfaceDestroyed(SurfaceHolder holder) {
//
//        }
//    };

}
