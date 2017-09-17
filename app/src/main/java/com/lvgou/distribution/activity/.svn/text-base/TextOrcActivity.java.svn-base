package com.lvgou.distribution.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lvgou.distribution.R;
import com.lvgou.distribution.orc.Tesseract;
import com.lvgou.distribution.utils.PermissionManager;
import com.xdroid.common.utils.FunctionUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;

import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;

/**
 * Created by Snow on 2016/6/15 0015.
 */
public class TextOrcActivity extends BaseActivity {

    private static final String tag = "TextOrcActivity";
    /**
     * 当前的图片路径
     */
    private String imagePath = null;
    private final static int REQUEST_CODE_GALLERY = 0x33;
    private final static int REQUEST_CODE_CAMERA = 0x44;
    private boolean keyBack;
    private Button btnGetpic = null;
    private Button btnCamera = null;
    private Button btnRecognition = null;
    private ImageView imageView = null;
    private TextView textView = null;
    private Bitmap oriPicture = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_orc);
        mainInitialize();
    }
    private PermissionManager permissionManager;
    //添加照片
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    public void mainInitialize() {
        btnCamera = (Button) findViewById(R.id.getCam);
        btnGetpic = (Button) findViewById(R.id.getPic);
        keyBack = false;
        Log.i(tag, "initiate successfully");

        btnGetpic.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
              /*  FunctionUtils.chooseImageFromGallery(TextOrcActivity.this,
                        REQUEST_CODE_GALLERY);*/


                // 权限管理工具类
                permissionManager = new PermissionManager(new PermissionManager.PermissionListener() {
                    @Override
                    public void permissionGranted(int requestCode) {
                        if (requestCode == 99) {
                            PhotoPickerIntent intent = new PhotoPickerIntent(TextOrcActivity.this);
                            intent.setPhotoCount(1);
                            intent.setShowCamera(true);
                            intent.setShowGif(true);
                            startActivityForResult(intent, 101);
                        }
                    }
                });
                //申请读权限，和照相机权限
                permissionManager.checkPermission(99, TextOrcActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA);
            }
        });

        btnCamera.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                imagePath = FunctionUtils.chooseImageFromCamera(
                        TextOrcActivity.this, REQUEST_CODE_CAMERA, "cameraImage");
            }
        });
    }

    private ArrayList<String> data_list = new ArrayList<String>();

    @Override
    // Called when an activity you launched exits
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            setContentView(R.layout.show_image);
            btnRecognition = (Button) findViewById(R.id.getResult);
            textView = (TextView) findViewById(R.id.result);
            imageView = (ImageView) findViewById(R.id.showImage);
            textView.setVisibility(View.INVISIBLE);
            keyBack = true;

            if (requestCode == 101) {
                /*imagePath = FunctionUtils
                        .onActivityResultForChooseImageFromGallery(
                                TextOrcActivity.this, requestCode, resultCode,
                                data);*/
                if (data != null) {
                    data_list = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
                    imagePath = data_list.get(0);
                }

                oriPicture = imageReceive(imagePath); // get the image and
                oriPicture = oriPicture.copy(Bitmap.Config.ARGB_8888, true);
                imageView.setImageBitmap(oriPicture);

                btnRecognition.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        textView.setText("text recognized(pic):\n"+ Tesseract.getText(TextOrcActivity.this, oriPicture));
                        textView.setVisibility(View.VISIBLE);
                        imageView.setImageBitmap(oriPicture);
                    }
                });
            }
            if (requestCode == REQUEST_CODE_CAMERA) {
                oriPicture = imageReceive(imagePath); // get the image and
                oriPicture = oriPicture.copy(Bitmap.Config.ARGB_8888, true);
                imageView.setImageBitmap(oriPicture);
                btnRecognition.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        textView.setText("text recognized(cam):\n"+ Tesseract.getText(TextOrcActivity.this,oriPicture));
                        textView.setVisibility(View.VISIBLE);
                        imageView.setImageBitmap(oriPicture);
                    }
                });
            }
        }
    }

    // get the image and resize it if it is too large for screen
    public Bitmap imageReceive(String image_path) {
        Bitmap bmp = null;
        try {
            // get the screen size
            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            int dw = dm.widthPixels;
            int dh = dm.heightPixels;
            // Load up the image's dimensions not the image itself
            BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
            bmpFactoryOptions.inJustDecodeBounds = true;
            bmp = BitmapFactory.decodeFile(image_path, bmpFactoryOptions);
            int heightRatio = (int) Math.ceil(bmpFactoryOptions.outHeight
                    / (float) dh);
            int widthRatio = (int) Math.ceil(bmpFactoryOptions.outWidth
                    / (float) dw);

            // If both of the ratios are greater than 1,
            // one of the sides of the image is greater than the screen
            if (heightRatio > 1 && widthRatio > 1) {
                if (heightRatio > widthRatio) {
                    // Height ratio is larger, scale according to it
                    bmpFactoryOptions.inSampleSize = heightRatio;
                } else {
                    // Width ratio is larger, scale according to it
                    bmpFactoryOptions.inSampleSize = widthRatio;
                }
            }
            bmpFactoryOptions.inJustDecodeBounds = false; // Decode it for real
            bmp = BitmapFactory.decodeFile(image_path, bmpFactoryOptions);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return bmp;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Monitoring the return key
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (keyBack) {
                setContentView(R.layout.activity_main);
                mainInitialize();
            } else {
                finish();
                Log.i(tag, "app is over");
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }

    public String getFromAssets(String fileName) {
        try {
            InputStreamReader inputReader = new InputStreamReader(
                    getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String Result = "";
            while ((line = bufReader.readLine()) != null) {
                Result += line;
            }

            Log.e(tag, Result);
            return Result;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}