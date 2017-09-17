package com.lvgou.distribution.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.distribution.activity.LiveChatActivity;
import com.lvgou.distribution.utils.MyToast;

import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lvgou.distribution.R;
import com.lvgou.distribution.activity.BaseActivity;
import com.lvgou.distribution.activity.CertificationActivity;
import com.lvgou.distribution.activity.CourseIntrActivity;
import com.lvgou.distribution.activity.CourseVoucherActivity;
import com.lvgou.distribution.activity.DistributorHomeActivity;
import com.lvgou.distribution.activity.LiveDirectActivity;
import com.lvgou.distribution.activity.MyAcActivity;
import com.lvgou.distribution.activity.MyCourseActivity;
import com.lvgou.distribution.activity.RechargeMoneyActivity;
import com.lvgou.distribution.activity.SeriesClassActivity;
import com.lvgou.distribution.activity.TeacherHomeActivity;
import com.lvgou.distribution.activity.TuanYuanActivity;
import com.lvgou.distribution.adapter.ApplyViewAdapter;
import com.lvgou.distribution.adapter.CourseIntrAdapter;
import com.lvgou.distribution.constants.SPConstants;
import com.lvgou.distribution.constants.Url;
import com.lvgou.distribution.presenter.BaomingTeacherPresenter;
import com.lvgou.distribution.presenter.DistributorHomePresenter;
import com.lvgou.distribution.presenter.LookTeacherPresenter;
import com.lvgou.distribution.presenter.UpDoDateStatePresenter;
import com.lvgou.distribution.utils.DownloadManager;
import com.lvgou.distribution.utils.DownloadService;
import com.lvgou.distribution.utils.PopWindows;
import com.lvgou.distribution.utils.TGmd5;
import com.lvgou.distribution.utils.WorksGridView;
import com.lvgou.distribution.view.BaomingTeacherView;
import com.lvgou.distribution.view.DistributorHomeView;
import com.lvgou.distribution.view.LookTeacherView;
import com.lvgou.distribution.view.UpDoDateStateView;
import com.lvgou.distribution.widget.XListView;
import com.xdroid.common.utils.PreferenceHelper;
import com.lvgou.distribution.utils.MyToast;
import com.xdroid.common.utils.ScreenUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Administrator on 2017/4/5.
 * 课程详情-简介
 */

public class CourseIntrFragment extends BaseFragment implements XListView.IXListViewListener, View.OnClickListener, BaomingTeacherView, LookTeacherView, UpDoDateStateView, DistributorHomeView {
    private BaomingTeacherPresenter baomingTeacherPresenter;
    private LookTeacherPresenter lookTeacherPresenter;
    private UpDoDateStatePresenter upDoDateStatePresenter;
    private View view;
    private View courseHeader;
    private View courseFooter;
    private String state;
    private TextView stateTV;
    private String tuanBi = "";
    private String bMTuanBi = "";
    private String cKTuanBi = "";
    private String distributorid = "";
    private String courseID = "";
    private String videoUrl;
    private DistributorHomePresenter distributorHomePresenter;
    private String isstudy;
    private String reviewType;
    private String type;
    private int seriesId;
    private String banner3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_course_intr, container, false);
        baomingTeacherPresenter = new BaomingTeacherPresenter(this);
        lookTeacherPresenter = new LookTeacherPresenter(this);
        upDoDateStatePresenter = new UpDoDateStatePresenter(this);
        distributorHomePresenter = new DistributorHomePresenter(this);
        ((CourseIntrActivity) getActivity()).mcache.remove("coursevoucherList");
        ((CourseIntrActivity) getActivity()).mcache.remove("isselectvoucher");
        distributorid = PreferenceHelper.readString(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.LOGIN_USERID);

        initView();
        initDatas();
        initClick();
        return view;
    }

    private void initDatas() {
        //下载队列
        array = (ArrayList<HashMap<String, Object>>) ((CourseIntrActivity) getActivity()).mcache.getAsObject("xiazaiduilie" + distributorid);
        if (array == null) {
            array = new ArrayList<>();
        }
        //已经下载完成
        finishList = (ArrayList<HashMap<String, Object>>) ((CourseIntrActivity) getActivity()).mcache.getAsObject("downloadfinish" + distributorid);
        if (null == finishList) {
            finishList = new ArrayList<HashMap<String, Object>>();
        }
    }

    private XListView mListView;
    private CourseIntrAdapter courseIntrAdapter;
    private TextView tv_apply_num;//已报名人数
    private ImageView im_teacher_head;//讲师头像
    private TextView tv_teacher_name;//讲师名称
    private TextView tv_teacher_intr;//讲师介绍
    private RelativeLayout rl_teacher;//讲师，这堂课的讲师
    private RelativeLayout rl_baoming_people;
    //    private ImageView im_gettuanbi;
    private ImageView im_time;
    private TextView tv_course_detail;
    private RelativeLayout rl_more_themeinfo;
    private ImageView im_moreinfo;
    private ImageView im_state;
    private TextView tv_similar_course;
    private RelativeLayout tv_similar_more;
    private View view_line;
    private ImageView im_sanjiao;
    private View view_apply_all;

    private void initView() {

        tuanBi = PreferenceHelper.readString(getActivity(), SPConstants.SHARED_PREFERENCE_NAME, SPConstants.TUANBI);
//        im_gettuanbi = (ImageView) view.findViewById(R.id.im_gettuanbi);
        mListView = (XListView) view.findViewById(R.id.list_view);
        courseIntrAdapter = new CourseIntrAdapter(getActivity());
        mListView.setPullRefreshEnable(false);
        mListView.setPullLoadEnable(false);
//        mListView.stopLoadMore();
        mListView.setAutoLoadEnable(false);
        mListView.setXListViewListener(this);
        mListView.setRefreshTime(((CourseIntrActivity) getActivity()).getTime());
        mListView.setDivider(null);
        courseIntrAdapter.setData(new ArrayList<HashMap<String, Object>>());
        mListView.setAdapter(courseIntrAdapter);
        courseHeader = LayoutInflater.from(getActivity()).inflate(R.layout.course_intr_header, null);
        mListView.addHeaderView(courseHeader);
        courseFooter = LayoutInflater.from(getActivity()).inflate(R.layout.course_intr_footer, null);
        tv_title = (TextView) courseHeader.findViewById(R.id.tv_title);
        tv_time = (TextView) courseHeader.findViewById(R.id.tv_time);
        tv_playnum = (TextView) courseHeader.findViewById(R.id.tv_playnum);
        tv_people = (TextView) courseHeader.findViewById(R.id.tv_people);
        im_state = (ImageView) courseHeader.findViewById(R.id.im_state);
        mListView.addFooterView(courseFooter);
        tv_apply_num = (TextView) courseHeader.findViewById(R.id.tv_apply_num);//0人/200人报名
        im_teacher_head = (ImageView) courseHeader.findViewById(R.id.im_teacher_head);
        im_sanjiao = (ImageView) courseHeader.findViewById(R.id.im_sanjiao);
        tv_teacher_name = (TextView) courseHeader.findViewById(R.id.tv_teacher_name);
        tv_teacher_intr = (TextView) courseHeader.findViewById(R.id.tv_teacher_intr);
        rl_teacher = (RelativeLayout) courseHeader.findViewById(R.id.rl_teacher);
        rl_baoming_people = (RelativeLayout) courseHeader.findViewById(R.id.rl_baoming_people);
        tv_course_detail = (TextView) courseHeader.findViewById(R.id.tv_course_detail);
        rl_more_themeinfo = (RelativeLayout) courseHeader.findViewById(R.id.rl_more_themeinfo);
        im_moreinfo = (ImageView) courseHeader.findViewById(R.id.im_moreinfo);
        view_line = courseHeader.findViewById(R.id.view_line);
        im_time = (ImageView) courseHeader.findViewById(R.id.im_time);
        tv_similar_course = (TextView) courseHeader.findViewById(R.id.tv_similar_course);
        tv_similar_more = (RelativeLayout) courseHeader.findViewById(R.id.tv_similar_more);
        view_apply_all = courseHeader.findViewById(R.id.view_apply_all);
        stateTV = ((CourseIntrActivity) getActivity()).getStateTV();
        ll_chakan = ((CourseIntrActivity) getActivity()).getLLChakan();
        ll_download = ((CourseIntrActivity) getActivity()).getLLDownload();
        im_download = ((CourseIntrActivity) getActivity()).getImDownload();
        tv_download = ((CourseIntrActivity) getActivity()).getTVDownload();
        tv_fubichakan = ((CourseIntrActivity) getActivity()).getTvFBchakan();
        img_play = ((CourseIntrActivity) getActivity()).getImgPlay();
        tv_shikan = ((CourseIntrActivity) getActivity()).getShikan();
        rl_introduction = ((CourseIntrActivity) getActivity()).getIntroduction();

    }

    private RelativeLayout rl_introduction;//底部最外层

    private LinearLayout ll_chakan;//查看
    private LinearLayout ll_download;//下载
    private ImageView im_download;//下载图标
    private TextView tv_download;//下载
    private TextView tv_fubichakan;//付币查看
    private RelativeLayout img_play;//播放
    private TextView tv_shikan;//试看

    private TextView tv_title;
    private TextView tv_time;
    private TextView tv_playnum;
    private TextView tv_people;
    private ArrayList<HashMap<String, Object>> courseStuInfo = new ArrayList<>();
    private String teacherDistributorID = "";
    private String KDBDistributorID = "";
    private String theme = "";
    private String GID = "";

    private HashMap<String, Object> info = new HashMap<String, Object>();
    private String isLeaning;

    public void setDatas(JSONArray jsa) {
        try {
            isLeaning = jsa.get(7).toString();
            JSONObject jsonObject = jsa.getJSONObject(0);
            Iterator its = jsonObject.keys();
            while (its.hasNext()) {
                String key = (String) its.next();
                String value = jsonObject.getString(key);
                info.put(key, value);
            }
            String themeInfo = jsonObject.get("ThemeInfo").toString();
            tv_course_detail.setText(themeInfo);
            MyOpenTask myOpenTast = new MyOpenTask();
            myOpenTast.execute();
            tv_title.setText(jsonObject.get("Theme").toString());
            bMTuanBi = jsonObject.get("BMTuanBi").toString();
            cKTuanBi = jsonObject.get("CKTuanBi").toString();
            courseID = jsonObject.get("ID").toString();
            videoUrl = jsonObject.get("Content").toString();
            teacherDistributorID = jsonObject.get("TeacherDistributorID").toString();
            KDBDistributorID = jsonObject.get("KDBDistributorID").toString();
            theme = jsonObject.getString("Theme");
            GID = jsonObject.getString("GID");
            state = jsonObject.get("State").toString();
            type = jsonObject.get("Type").toString();
            String people_apply = jsonObject.get("People_Apply").toString();
            String people_all = jsonObject.get("People_All").toString();
            tv_people.setText("适应人群：" + jsonObject.get("Crowd").toString());
            if (people_all.equals("0")) {
                SpannableString spanString = new SpannableString(people_apply + "人/" + "不限");
                ForegroundColorSpan span = new ForegroundColorSpan(Color.parseColor("#333333"));
                spanString.setSpan(span, 0, people_apply.length() + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                tv_apply_num.append(spanString);
//                tv_apply_num.setText(jsonObject.get("People_Apply").toString() + "人/" + "不限");
            } else {
                SpannableString spanString = new SpannableString(people_apply + "人/" + people_all + "人");
                ForegroundColorSpan span = new ForegroundColorSpan(Color.parseColor("#333333"));
                spanString.setSpan(span, 0, people_apply.length() + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                tv_apply_num.append(spanString);
//                tv_apply_num.setText(people_apply + "人/" + people_all + "人");
            }
            Glide.with(getActivity()).load(Url.ROOT + jsonObject.get("PicUrl").toString()).into(im_teacher_head);
            tv_teacher_name.setText("讲师：" + jsonObject.get("TeacherName").toString());
            tv_teacher_intr.setText(jsonObject.get("TeacherInfo").toString());
            //回顾类型1视频2音频3直播间
            reviewType = jsonObject.get("ReviewType").toString();
            //是否已学习 0=否，1=是
            isstudy = jsa.get(1).toString();
            String zbTime = jsonObject.get("ZBTime").toString();
            String people_Look = jsonObject.get("People_Look").toString();
//            String people_apply = jsonObject.get("People_Apply").toString();
            String People_Min = jsonObject.get("People_Min").toString();
            String Hits = jsonObject.get("Hits").toString();
            if (state.equals("0")) {
                //审核中
                ll_chakan.setVisibility(View.GONE);
                stateTV.setVisibility(View.VISIBLE);
                stateTV.setBackgroundColor(Color.parseColor("#EAD4AF"));
                String[] ts = zbTime.split("T");
                tv_time.setText(ts[0].substring(5) + " " + ts[1].substring(0, 5) + " 开课");
                tv_playnum.setText(people_apply + "人报名");
                stateTV.setText("审核中");
            } else if (state.equals("1")) {
                //报名中
                im_state.setBackgroundResource(R.mipmap.live_renshu_icon);
                ll_chakan.setVisibility(View.GONE);
                stateTV.setVisibility(View.VISIBLE);
                String[] ts = zbTime.split("T");
                tv_time.setText(ts[0].substring(5) + " " + ts[1].substring(0, 5) + " 开课");
                if (isstudy.equals("0")) {
                    if (type.equals("2")) {
                        stateTV.setBackgroundColor(Color.parseColor("#d5aa5f"));
                        if (bMTuanBi.equals("0")) {
                            stateTV.setText("免费众筹");
                        } else {
                            stateTV.setText("付币众筹(" + bMTuanBi + "币)");
                        }
                        im_time.setBackgroundResource(R.mipmap.zhibo_time_icon);
                        tv_time.setText(ts[0].substring(5) + " " + ts[1].substring(0, 5) + " 开课");
//                        tv_time.setText("众筹中");
                        tv_time.setTextColor(Color.parseColor("#d5aa5f"));
                    } else {
                        im_time.setBackgroundResource(R.mipmap.zhibo_time_icon);
                        tv_time.setTextColor(Color.parseColor("#d5aa5f"));
                        stateTV.setBackgroundColor(Color.parseColor("#d5aa5f"));
                        if (bMTuanBi.equals("0")) {
                            stateTV.setText("免费报名");
                        } else {
                            stateTV.setText("付币报名(" + bMTuanBi + "币)");
                        }
//                         videoUrl = jsonObject.get("ClassSound").toString();
                    }
                    if (!jsonObject.get("People_All").toString().equals("0")) {
                        if (jsonObject.get("People_Apply").toString().equals(jsonObject.get("People_All").toString())) {
                            stateTV.setText("人数已满");
                            stateTV.setBackgroundColor(Color.parseColor("#EAD4AF"));
                        }
                    }

                } else if (isstudy.equals("1")) {
                    im_time.setBackgroundResource(R.mipmap.zhibo_time_icon);
                    tv_time.setTextColor(Color.parseColor("#d5aa5f"));
                    if (type.equals("1")) {
                        stateTV.setText("已报名，等待开课");
                        stateTV.setBackgroundColor(Color.parseColor("#EAD4AF"));
                    } else if (type.equals("2")) {
                        stateTV.setText("已众筹，等待开课");
                        stateTV.setBackgroundColor(Color.parseColor("#EAD4AF"));
                    }

                }
                if (type.equals("2")) {
                    //众筹中
                    tv_playnum.setText(people_apply + "/" + People_Min + "人起");
                } else {
                    //报名中
                    tv_playnum.setText(people_apply + "人报名");
                }

            } else if (state.equals("2")) {
                String[] ts = zbTime.split("T");
                tv_time.setText(ts[0].substring(5) + " " + ts[1].substring(0, 5) + " 开课");
                tv_playnum.setText(people_apply + "人报名");
                //审核 不通过
                ll_chakan.setVisibility(View.GONE);
                stateTV.setVisibility(View.VISIBLE);
                stateTV.setBackgroundColor(Color.parseColor("#EAD4AF"));
                im_state.setBackgroundResource(R.mipmap.live_renshu_icon);
                stateTV.setText("审核失败");
            } else if (state.equals("3")) {
                ll_chakan.setVisibility(View.GONE);
                stateTV.setVisibility(View.VISIBLE);
                //进行中
                if (isstudy.equals("0")) {
                    //未学习
                    stateTV.setBackgroundColor(Color.parseColor("#d5aa5f"));
                    if (type.equals("2")) {
                        if (bMTuanBi.equals("0")) {
                            stateTV.setText("免费众筹");
                        } else {
                            stateTV.setText("付币众筹(" + bMTuanBi + "币)");
                        }
                    } else {
                        if (bMTuanBi.equals("0")) {
                            stateTV.setText("免费报名");
                        } else {
                            stateTV.setText("付币报名(" + bMTuanBi + "币)");
                        }
                    }

//                    videoUrl = jsonObject.get("ClassSound").toString();
                    if (!jsonObject.get("People_All").toString().equals("0")) {
                        if (jsonObject.get("People_Apply").toString().equals(jsonObject.get("People_All").toString())) {
                            stateTV.setText("人数已满");
                            stateTV.setBackgroundColor(Color.parseColor("#EAD4AF"));
                        }
                    }
                } else if (isstudy.equals("1")) {
                    //已学习
                    if (GID == null || GID.equals("")) {
                        //无GID
                        ll_chakan.setVisibility(View.GONE);
                        stateTV.setVisibility(View.VISIBLE);
                        stateTV.setBackgroundColor(Color.parseColor("#EAD4AF"));
                        stateTV.setText("微信开课中，联系客服");
                    } else {
                        //有GID
                        stateTV.setText("进入直播间");
                        ll_chakan.setVisibility(View.GONE);
                        stateTV.setVisibility(View.VISIBLE);
                        stateTV.setBackgroundColor(Color.parseColor("#66c300"));
                    }
                }
                im_time.setBackgroundResource(R.mipmap.zhibo_time_icon);
//                tv_playnum.setText(people_Apply + "人报名");
                im_state.setBackgroundResource(R.mipmap.live_renshu_icon);
                tv_time.setText("直播中");
                tv_time.setTextColor(Color.parseColor("#66C300"));
                if (type.equals("2")) {
                    //众筹中
                    tv_playnum.setText(people_apply + "/" + People_Min + "人起");
                } else {
                    //进行中
                    tv_playnum.setText(people_apply + "人报名");
                }
            } else if (state.equals("4")) {
                String[] ts = zbTime.split("T");
                tv_time.setText(ts[0].substring(5) + " " + ts[1].substring(0, 5) + " 开课");
//                tv_playnum.setText(Hits + "人次");
                //已结束
                ll_chakan.setVisibility(View.VISIBLE);
                stateTV.setVisibility(View.GONE);

                if (isstudy.equals("0")) {
                    if (reviewType.equals("3")) {
                        //回顾类型是直播间
                        ll_chakan.setVisibility(View.GONE);
                        stateTV.setVisibility(View.VISIBLE);
                        if (cKTuanBi.equals("0")) {
                            stateTV.setText("免费进入直播间");
                        } else {
                            stateTV.setText("付币进入直播间(" + cKTuanBi + "币)");
                        }
                        stateTV.setBackgroundColor(Color.parseColor("#d5aa5f"));
                    } else {
                        tv_shikan.setVisibility(View.VISIBLE);
                        img_play.setVisibility(View.VISIBLE);
                        if (videoUrl.contains("mp3") || videoUrl.contains("MP3") || videoUrl.contains("mp4") || videoUrl.contains("MP4")) {
                            int i = videoUrl.lastIndexOf(".");
                            String substring1 = videoUrl.substring(i, videoUrl.length());
                            String substring = videoUrl.substring(0, i);
                            String replaceVideoUrl = substring + "_10" + substring1;
                            ((CourseIntrActivity) getActivity()).setVideoUrl(replaceVideoUrl);
                        } else {
                            ((CourseIntrActivity) getActivity()).setVideoUrl(videoUrl);
                        }

                        //没有学习
                        im_download.setBackgroundResource(R.mipmap.download_clickab_icon);
                        tv_download.setTextColor(Color.parseColor("#000000"));
                        if (cKTuanBi.equals("0")) {
                            tv_fubichakan.setText("免费听课");
                        } else {
                            tv_fubichakan.setText("付币听课(" + cKTuanBi + "币)");
                        }
                        tv_fubichakan.setBackgroundColor(Color.parseColor("#d5aa5f"));
                    }

                } else if (isstudy.equals("1")) {
                    ((CourseIntrActivity) getActivity()).setVideoUrl(videoUrl);
                    //已学习
                    im_download.setBackgroundResource(R.mipmap.download_clickab_icon);
                    tv_download.setTextColor(Color.parseColor("#000000"));
                    if (reviewType.equals("3")) {
                        //回顾类型是直播间
                        stateTV.setText("进入直播间");
                        ll_chakan.setVisibility(View.GONE);
                        stateTV.setVisibility(View.VISIBLE);
                        stateTV.setBackgroundColor(Color.parseColor("#66c300"));
                    } else {
                        img_play.setVisibility(View.VISIBLE);
                        ll_chakan.setVisibility(View.VISIBLE);
                        stateTV.setVisibility(View.GONE);
                        tv_fubichakan.setText("已购买");
                        tv_fubichakan.setBackgroundColor(Color.parseColor("#EAD4AF"));
                        if (jsonObject.get("Content") != null && !jsonObject.get("Content").toString().equals("")) {
                            for (HashMap<String, Object> stringObjectHashMap : array) {
                                if (stringObjectHashMap.get("Comment").toString().equals(jsonObject.get("Content").toString())) {
                                    //如果在下载中包含这个课程
                                    if (stringObjectHashMap.get("isdown").toString().equals("false")) {
                                        //如果是下载失败的课程
                                        tv_download.setTextColor(Color.parseColor("#fc4d30"));
                                        tv_download.setText("下载失败");
                                        im_download.setBackgroundResource(R.mipmap.download_failer);
                                        ViewGroup.LayoutParams para = im_download.getLayoutParams();
                                        int iheight = (int) ScreenUtils.dpToPx(getActivity(), 12);
                                        para.height = iheight;// 控件的高强制设成
                                        im_download.setLayoutParams(para);
                                    } else {
                                        tv_download.setText("下载中");
                                        im_download.setBackgroundResource(R.mipmap.course_downloading_icon);
                                        ViewGroup.LayoutParams para = im_download.getLayoutParams();
                                        int iheight = (int) ScreenUtils.dpToPx(getActivity(), 12);
                                        para.height = iheight;// 控件的高强制设成
                                        im_download.setLayoutParams(para);
                                        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_loading_anim);
                                        animation.setInterpolator(new LinearInterpolator());
                                        im_download.setAnimation(animation);
                                    }

                                    break;
                                }
                            }
                            for (HashMap<String, Object> stringObjectHashMap : finishList) {
                                if (stringObjectHashMap.get("Comment").toString().equals(jsonObject.get("Content").toString())) {
                                    //如果在下载完成中包含这个课程
                                    rl_introduction.setVisibility(View.GONE);
//                                    ll_chakan.setVisibility(View.GONE);
                                    tv_download.setTextColor(Color.parseColor("#B6B6B6"));
                                    im_download.setBackgroundResource(R.mipmap.download_yes_icon);
                                    break;
                                }
                            }
                        } else {
                            //不能下载的
                            tv_download.setTextColor(Color.parseColor("#B6B6B6"));
                            im_download.setBackgroundResource(R.mipmap.download_yes_icon);
                        }
                    }

                }
                tv_playnum.setText(Hits + "人次");
            } else if (state.equals("5")) {
                ll_chakan.setVisibility(View.GONE);
                stateTV.setVisibility(View.VISIBLE);
                //停用
                stateTV.setBackgroundColor(Color.parseColor("#EAD4AF"));
                stateTV.setText("停用");
                tv_playnum.setText(Hits + "人次");
            } else if (state.equals("6")) {
                ll_chakan.setVisibility(View.GONE);
                stateTV.setVisibility(View.VISIBLE);
                /*String[] ts = zbTime.split("T");
                tv_time.setText(ts[0].substring(5) + " " + ts[1].substring(0, 5) + " 开课");*/
                tv_playnum.setText(Hits + "人次");
                //录制中
                stateTV.setBackgroundColor(Color.parseColor("#EAD4AF"));
                stateTV.setText("课程制作中");
                im_time.setBackgroundResource(R.mipmap.nostart_time_icon);
                tv_time.setText("课程制作中");
                tv_time.setTextColor(Color.parseColor("#fc4d30"));
            }
            /*if (teacherDistributorID.equals(distributorid)) {
                //自己开的课不出现下载框
                ll_download.setVisibility(View.GONE);
            } else {
                ll_download.setVisibility(View.VISIBLE);
            }*/
            courseStuInfo.clear();
            JSONArray jsonArray = jsa.getJSONArray(5);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsoo = jsonArray.getJSONObject(i);
                HashMap<String, Object> map1 = new HashMap<String, Object>();
                Iterator it = jsoo.keys();
                while (it.hasNext()) {
                    String key = (String) it.next();
                    String value = jsoo.getString(key);
                    map1.put(key, value);
                }
                courseStuInfo.add(map1);
            }
            courseIntrAdapter.setData(courseStuInfo);
            courseIntrAdapter.notifyDataSetChanged();
            JSONArray jsongrid = jsa.getJSONArray(3);
            for (int i = 0; i < jsongrid.length(); i++) {
                JSONObject jsoo = jsongrid.getJSONObject(i);
                HashMap<String, Object> map1 = new HashMap<String, Object>();
                Iterator it = jsoo.keys();
                while (it.hasNext()) {
                    String key = (String) it.next();
                    String value = jsoo.getString(key);
                    map1.put(key, value);
                }
                applyLists.add(map1);
            }
            initApplyGtideView();

            JSONArray jsongift = jsa.getJSONArray(2);//可用听课券列表
            giftLists.clear();
            for (int i = 0; i < jsongift.length(); i++) {
                JSONObject jsoo = jsongift.getJSONObject(i);
                HashMap<String, Object> map1 = new HashMap<String, Object>();
                Iterator it = jsoo.keys();
                while (it.hasNext()) {
                    String key = (String) it.next();
                    String value = jsoo.getString(key);
                    map1.put(key, value);
                }
                map1.put("isCheck", "false");
                giftLists.add(map1);
            }
            seriesId = jsa.getInt(6);
            if (seriesId > 0) {
                tv_similar_course.setText("同系课程");
                tv_similar_more.setVisibility(View.VISIBLE);
                im_sanjiao.setVisibility(View.VISIBLE);
            } else {
                tv_similar_course.setText("相关课程");
                tv_similar_more.setVisibility(View.GONE);
                im_sanjiao.setVisibility(View.GONE);
            }

        } catch (JSONException e) {


        }

    }

    private void initApplyGtideView() {
        applyGridView = (WorksGridView) courseHeader.findViewById(R.id.grid_teacher);
        setApplyGridView();
        applyGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        applyGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("id", courseID);
                ((CourseIntrActivity) getActivity()).openActivity(TuanYuanActivity.class, bundle);
            }
        });
    }

    private WorksGridView applyGridView;
    private ArrayList<HashMap<String, Object>> applyLists = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> giftLists = new ArrayList<>();

    private void setApplyGridView() {
        int size = applyLists.size();
//        int size = 5;
        int length = 35;
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;
        int gridviewWidth = (int) (size * (length) * density);
        int itemWidth = (int) (length * density);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                gridviewWidth, LinearLayout.LayoutParams.MATCH_PARENT);
        applyGridView.setLayoutParams(params); // 设置GirdView布局参数,横向布局的关键
        applyGridView.setColumnWidth(itemWidth); // 设置列表项宽
//        applyGridView.setHorizontalSpacing(20); // 设置列表项水平间距
        applyGridView.setStretchMode(GridView.NO_STRETCH);
        applyGridView.setNumColumns(size); // 设置列数量=列表集合数
        ApplyViewAdapter adapter = new ApplyViewAdapter(getActivity(), applyLists);
        applyGridView.setAdapter(adapter);
    }

    private void initClick() {
        rl_teacher.setOnClickListener(this);
        stateTV.setOnClickListener(this);
        ll_download.setOnClickListener(this);
        tv_fubichakan.setOnClickListener(this);
        rl_baoming_people.setOnClickListener(this);
//        im_gettuanbi.setOnClickListener(this);
        rl_more_themeinfo.setOnClickListener(this);
        tv_apply_num.setOnClickListener(this);
        tv_similar_more.setOnClickListener(this);
        view_apply_all.setOnClickListener(this);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_teacher:
                //调转到讲师主页
                String sign2 = TGmd5.getMD5("" + distributorid + teacherDistributorID);
                ((CourseIntrActivity) getActivity()).showLoadingProgressDialog(getActivity(), "");
                distributorHomePresenter.distributorHome(distributorid, teacherDistributorID, sign2);
                break;
            case R.id.tv_apply:
                if (state.equals("0")) {
                    //审核中
                    showOneDialog("课程审核中！");
                } else if (state.equals("1")) {
                    //报名中
                    if (isstudy.equals("0")) {
                        if (!info.get("People_All").toString().equals("0") && info.get("People_Apply").toString().equals(info.get("People_All").toString())) {
                            showOneDialog("人数已满！");
                        } else if (type.equals("1")) {
                            if (bMTuanBi.equals("0")) {
                                //免费报名
                                String sign = TGmd5.getMD5(distributorid + courseID + "0");
                                ((CourseIntrActivity) getActivity()).showLoadingProgressDialog(getActivity(), "");
                                baomingTeacherPresenter.applyTeacher(distributorid, courseID, "0", sign);
                            } else {
                                //付币报名
                                showBMDialog();
                            }
                        } else if (type.equals("2")) {
                            stateTV.setBackgroundColor(Color.parseColor("#d5aa5f"));
                            if (bMTuanBi.equals("0")) {
                                //免费众筹
                                String sign = TGmd5.getMD5(distributorid + courseID + "0");
                                ((CourseIntrActivity) getActivity()).showLoadingProgressDialog(getActivity(), "");
                                baomingTeacherPresenter.applyTeacher(distributorid, courseID, "0", sign);
                            } else {
                                //付币众筹
                                showBMDialog();
                            }
                        }

                    } else if (isstudy.equals("1")) {
                        /*if (GID == null || GID.equals("")) {
                            MyToast.makeText(getActivity(), "微信开课中，联系客服", Toast.LENGTH_SHORT).show();
                            break;
                        }*/
                        if (type.equals("1")) {
                            MyToast.makeText(getActivity(), "已报名，等待开课", Toast.LENGTH_SHORT).show();
                        } else if (type.equals("2")) {
                            MyToast.makeText(getActivity(), "已众筹，等待开课", Toast.LENGTH_SHORT).show();
                        }

                    }
                    //-----
                } else if (state.equals("2")) {
                    //审核 不通过
                    MyToast.makeText(getActivity(), "课程审核不通过", Toast.LENGTH_SHORT).show();
                } else if (state.equals("3")) {
                    //进行中
                    if (isstudy.equals("1")) {
                        //已经学习
                        if (GID == null || GID.equals("")) {
                            MyToast.makeText(getActivity(), "微信开课中，联系客服", Toast.LENGTH_SHORT).show();
//                            MyToast.makeText(getActivity(), "已报名等待开课", Toast.LENGTH_SHORT).show();
                        } else {
                            //可以进入直播间
                            if (isLeaning.equals("0")) {
                                ((CourseIntrActivity) getActivity()).showLoadingProgressDialog(getActivity(), "");
                                String sign = TGmd5.getMD5(distributorid + courseID + "");
                                upDoDateStatePresenter.upDoDateState(distributorid, courseID, sign);
                            } else {
                                Bundle bundle1 = new Bundle();
                                bundle1.putString("teacherId", teacherDistributorID);
                                bundle1.putInt("ktID", Integer.parseInt(courseID));
                                bundle1.putInt("KDBDistributorID", Integer.parseInt(KDBDistributorID));
                                bundle1.putString("GId", GID);
                                bundle1.putString("theme", theme);
                                bundle1.putString("courseState", state);
                                ((CourseIntrActivity) getActivity()).openActivity(LiveChatActivity.class, bundle1);
//                                ((CourseIntrActivity) getActivity()).openActivity(LiveDirectActivity.class, bundle1);
                            }

                        }
                       /* if (reviewType.equals("3")) {
                            //回顾类型类型是直播间
                            ((CourseIntrActivity) getActivity()).showLoadingProgressDialog(getActivity(), "");
                            String sign = TGmd5.getMD5(distributorid + courseID + "");
                            upDoDateStatePresenter.upDoDateState(distributorid, courseID, sign);
                        } else {
                            MyToast.makeText(getActivity(), "已报名等待开课", Toast.LENGTH_SHORT).show();
                        }*/
                    } else {
                        //没有学习
                        if (!info.get("People_All").toString().equals("0") && info.get("People_Apply").toString().equals(info.get("People_All").toString())) {
//                            showOneDialog("人数已满！");
                            MyToast.makeText(getActivity(), "人数已满", Toast.LENGTH_SHORT).show();
                        } else {
                            if (bMTuanBi.equals("0")) {
                                //免费报名
                                String sign = TGmd5.getMD5(distributorid + courseID + "0");
                                ((CourseIntrActivity) getActivity()).showLoadingProgressDialog(getActivity(), "");
                                baomingTeacherPresenter.applyTeacher(distributorid, courseID, "0", sign);
                            } else {
                                //付币报名
                                showBMDialog();
                            }
                        }
                    }
                } else if (state.equals("4")) {
                    //已结束
                    if (isstudy.equals("1")) {
                        //已学习
                        if (reviewType.equals("3")) {
                            if (GID == null || GID.equals("")) {
                                MyToast.show(getActivity(), "直播间未配置，请联系客服 ");
                            } else {
                                if (isLeaning.equals("0")) {
                                    ((CourseIntrActivity) getActivity()).showLoadingProgressDialog(getActivity(), "");
                                    String sign = TGmd5.getMD5(distributorid + courseID + "");
                                    upDoDateStatePresenter.upDoDateState(distributorid, courseID, sign);
                                } else {
                                    Bundle bundle1 = new Bundle();
                                    bundle1.putString("teacherId", teacherDistributorID);
                                    bundle1.putInt("ktID", Integer.parseInt(courseID));
                                    bundle1.putInt("KDBDistributorID", Integer.parseInt(KDBDistributorID));
                                    bundle1.putString("GId", GID);
                                    bundle1.putString("theme", theme);
                                    bundle1.putString("courseState", state);
                                    ((CourseIntrActivity) getActivity()).openActivity(LiveChatActivity.class, bundle1);
//                                    ((CourseIntrActivity) getActivity()).openActivity(LiveDirectActivity.class, bundle1);
                                }
                            }

                        } else {
                            MyToast.makeText(getActivity(), "课程已经结束了", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        //没有学习，就应该去查看
                        if (cKTuanBi.equals("0")) {
                            //直接走查看
                            String sign1 = TGmd5.getMD5(distributorid + courseID + "0");
                            ((CourseIntrActivity) getActivity()).showLoadingProgressDialog(getActivity(), "");
                            lookTeacherPresenter.lookTeacher(distributorid, courseID, "0", sign1);
                        } else {
                            showCKDialog();
                        }
                    }


                } else if (state.equals("5")) {
                    //停用
                    MyToast.makeText(getActivity(), "课程已经停用了", Toast.LENGTH_SHORT).show();
//                    MyToast.makeText(getActivity(), "停用", Toast.LENGTH_SHORT).show();
                } else if (state.equals("6")) {
                    //录制中
                    MyToast.makeText(getActivity(), "课程制作中", Toast.LENGTH_SHORT).show();
//                    MyToast.makeText(getActivity(), "录制中", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ll_download://下载课程
                if (state.equals("0")) {
                    //审核中
                } else if (state.equals("1")) {
                    //报名中
                } else if (state.equals("3")) {
                    //进行中
                } else if (state.equals("4") && !reviewType.equals("3")) {
                    //已经结束的课程，回顾类型不是直播间
                    if (isstudy.equals("0")) {
                        //没有学习
                        //你确定要购买并下载课程吗？
                        showDownDialog();
                    } else if (isstudy.equals("1")) {
                        //已学习
                        if (info.get("Content") != null && !info.get("Content").toString().equals("")) {
                            boolean iscandown = true;
                            for (HashMap<String, Object> stringObjectHashMap : array) {
                                if (stringObjectHashMap.get("Comment").toString().equals(info.get("Content").toString())) {
                                    //如果在下载中包含这个课程
//                                    MyToast.makeText(getActivity(), "该课程正在下载", Toast.LENGTH_SHORT).show();
                                    showDownloadingDialog();
                                    iscandown = false;
                                    break;
                                }
                            }
                            /*for (HashMap<String, Object> stringObjectHashMap : finishList) {
                                if (stringObjectHashMap.get("Comment").toString().equals(info.get("Content").toString())) {
                                    //如果在下载完成中包含这个课程
//                                    showOneDialog("该课程已下载");
                                    MyToast.makeText(getActivity(), "该课程已下载", Toast.LENGTH_SHORT).show();
                                    iscandown = false;
                                    break;
                                }
                            }*/
                            if (iscandown) {
                                try {
                                    HashMap<String, Object> hashMap = new HashMap<String, Object>();
                                    hashMap.put("Theme", info.get("Theme"));
                                    hashMap.put("Banner2", info.get("Banner1"));
                                    hashMap.put("Comment", info.get("Content"));
                                    hashMap.put("isdown", "true");
                                    array.add(hashMap);
                                    ((CourseIntrActivity) getActivity()).mcache.put("xiazaiduilie" + distributorid, array);
                                    String SDPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/fengyouke/" + distributorid + "/";
                                    String downStr = info.get("Content").toString();
                                    String name = TGmd5.getMD5(downStr);
                                    if (downStr.endsWith(".mp3") || downStr.endsWith(".MP3")) {
                                        name = name + ".mp3";
                                    } else {
                                        name = name + ".mp4";
                                    }

                                    DownloadManager downloadManager = DownloadService.getDownloadManager(getActivity());
                                    downloadManager.addNewDownload(downStr, name, SDPath + name, true, true, new RequestCallBack<File>() {
                                        @Override
                                        public void onSuccess(ResponseInfo<File> responseInfo) {

                                        }

                                        @Override
                                        public void onFailure(HttpException error, String msg) {
                                            //如果下载失败了
                                            View toastRoot = getActivity().getLayoutInflater().inflate(R.layout.toast_download_sign, null);
                                            TextView tv_toast_text = (TextView) toastRoot.findViewById(R.id.tv_toast_text);
                                            tv_toast_text.setText("下载失败");
                                            Toast toast = new Toast(getActivity());
                                            toast.setView(toastRoot);
                                            toast.setGravity(Gravity.CENTER, 0, 0);
                                            toast.show();
                                            tv_download.setText("下载失败");
                                            tv_download.setTextColor(Color.parseColor("#fc4d30"));
                                            im_download.setBackgroundResource(R.mipmap.download_failer);
                                            array = (ArrayList<HashMap<String, Object>>) ((CourseIntrActivity) getActivity()).mcache.getAsObject("xiazaiduilie" + distributorid);
                                            if (array == null) {
                                                array = new ArrayList<>();
                                            }
                                            for (int i = 0; i < array.size(); i++) {
                                                if (array.get(i).get("Comment").toString().equals(info.get("Content").toString())) {
                                                    array.get(i).put("isdown", "false");
                                                    break;
                                                }
                                            }
                                            ((CourseIntrActivity) getActivity()).mcache.put("xiazaiduilie" + distributorid, array);
                                        }
                                    });
                                    View toastRoot = getActivity().getLayoutInflater().inflate(R.layout.toast_download_sign, null);
                                    Toast toast = new Toast(getActivity());
                                    toast.setView(toastRoot);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();
                                    tv_download.setText("下载中");
                                    im_download.setBackgroundResource(R.mipmap.course_downloading_icon);
                                    ViewGroup.LayoutParams para = im_download.getLayoutParams();
                                    int iheight = (int) ScreenUtils.dpToPx(getActivity(), 12);
                                    para.height = iheight;// 控件的高强制设成
                                    im_download.setLayoutParams(para);
                                    Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_loading_anim);
                                    animation.setInterpolator(new LinearInterpolator());
                                    im_download.setAnimation(animation);
                                    EventBus.getDefault().post("downloadclass");

                                } catch (DbException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            //不能下载的
                            MyToast.makeText(getActivity(), "暂时不能下载哦", Toast.LENGTH_SHORT).show();
//                            showOneDialog("暂时不能下载哦！");
                        }

                    }
                }
                break;
            case R.id.tv_fubichakan://付币查看
                if (state.equals("4")) {
                    //如果是已结束的
                    if (isstudy.equals("1")) {
                        //已学习
                        MyToast.makeText(getActivity(), "已购买，可直接查看", Toast.LENGTH_SHORT).show();
                    } else {
                        //没有学习
                        if (cKTuanBi.equals("0")) {
                            //直接走查看
                            String sign1 = TGmd5.getMD5(distributorid + courseID + "0");
                            ((CourseIntrActivity) getActivity()).showLoadingProgressDialog(getActivity(), "");
                            lookTeacherPresenter.lookTeacher(distributorid, courseID, "0", sign1);
                        } else {
                            showCKDialog();
                        }
                    }
                } else if (isstudy.equals("1") && reviewType.equals("3")) {
                    if (GID == null || GID.equals("")) {
                        MyToast.show(getActivity(), "直播间未配置，请联系客服 ");
                    } else {
                        //如果已经购买并且回顾类型是直播间 进入直播间
                        if (isLeaning.equals("0")) {
                            ((CourseIntrActivity) getActivity()).showLoadingProgressDialog(getActivity(), "");
                            String sign = TGmd5.getMD5(distributorid + courseID + "");
                            upDoDateStatePresenter.upDoDateState(distributorid, courseID, sign);
                        } else {
                            Bundle bundle1 = new Bundle();
                            bundle1.putString("teacherId", teacherDistributorID);
                            bundle1.putInt("ktID", Integer.parseInt(courseID));
                            bundle1.putInt("KDBDistributorID", Integer.parseInt(KDBDistributorID));
                            bundle1.putString("GId", GID);
                            bundle1.putString("theme", theme);
                            bundle1.putString("courseState", state);
                            ((CourseIntrActivity) getActivity()).openActivity(LiveChatActivity.class, bundle1);
//                            ((CourseIntrActivity) getActivity()).openActivity(LiveDirectActivity.class, bundle1);
                        }

                    }


                } else if (isstudy.equals("0")) {
                    //没有学习
                    if (!info.get("People_All").toString().equals("0") && info.get("People_Apply").toString().equals(info.get("People_All").toString())) {
                        MyToast.makeText(getActivity(), "人数已满", Toast.LENGTH_SHORT).show();
                    } else if (cKTuanBi.equals("0")) {
                        //直接走查看
                        String sign1 = TGmd5.getMD5(distributorid + courseID + "0");
                        ((CourseIntrActivity) getActivity()).showLoadingProgressDialog(getActivity(), "");
                        lookTeacherPresenter.lookTeacher(distributorid, courseID, "0", sign1);
                    } else {
                        showCKDialog();
                    }
                } else if (isstudy.equals("1")) {
                    //已经学习
                    MyToast.makeText(getActivity(), "您已经购买了哦", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.tv_apply_num:
            case R.id.view_apply_all:
            case R.id.rl_baoming_people:
                Bundle bundle = new Bundle();
                bundle.putString("id", courseID);
                ((CourseIntrActivity) getActivity()).openActivity(TuanYuanActivity.class, bundle);
                break;
           /* case R.id.im_gettuanbi:
                //获取团币
                showPopWindow();
                break;*/
            case R.id.rl_more_themeinfo://课程内容显示更多
                if (flag) {
                    tv_course_detail.setMaxLines(3);
                    im_moreinfo.setBackgroundResource(R.mipmap.xiala_icon);
                } else {
                    tv_course_detail.setMaxLines(Integer.MAX_VALUE);
                    im_moreinfo.setBackgroundResource(R.mipmap.shangla_icon);
                }
                flag = !flag;
                break;
            case R.id.tv_similar_more:
                Intent intent = new Intent(getActivity(), SeriesClassActivity.class);
                intent.putExtra("linkUrl", seriesId + "");
                startActivity(intent);
                break;


        }
    }

    private void showDownloadingDialog() {
        LinearLayout.LayoutParams pm = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.dialog_yesorno, null);//这里的R.layout.alertdialog即为你自定义的布局文件
        TextView text1 = (TextView) view.findViewById(R.id.text1);
        text1.setText("该课程正在下载中");
        TextView yes = (TextView) view.findViewById(R.id.yes);
        yes.setText("去看看");
        TextView cancle = (TextView) view.findViewById(R.id.cancle);
        final AlertDialog mAlertDialog = builder.create();
        mAlertDialog.getWindow().setBackgroundDrawableResource(R.color.touming);
        mAlertDialog.show();
        preDownload = false;
        isdownLoading = false;
        mAlertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        mAlertDialog.getWindow().setContentView(view, pm);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.dismiss();
            }
        });
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyCourseActivity.class);
                intent.putExtra("currentItem", 2);
                startActivity(intent);
                mAlertDialog.dismiss();
            }
        });
    }

    public void showDownDialog() {
        LinearLayout.LayoutParams pm = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.dialog_yesorno, null);//这里的R.layout.alertdialog即为你自定义的布局文件
        TextView text1 = (TextView) view.findViewById(R.id.text1);
        text1.setText("确定购买并下载该课程吗？");
        TextView yes = (TextView) view.findViewById(R.id.yes);
        TextView cancle = (TextView) view.findViewById(R.id.cancle);
        cancle.setText("再想想");
        final AlertDialog mAlertDialog = builder.create();
        mAlertDialog.getWindow().setBackgroundDrawableResource(R.color.touming);
        mAlertDialog.show();
        preDownload = false;
        isdownLoading = false;
        mAlertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        mAlertDialog.getWindow().setContentView(view, pm);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.dismiss();
            }
        });
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cKTuanBi.equals("0")) {
                    //直接走查看
                    isdownLoading = true;
                    String sign1 = TGmd5.getMD5(distributorid + courseID + "0");
                    ((CourseIntrActivity) getActivity()).showLoadingProgressDialog(getActivity(), "");
                    lookTeacherPresenter.lookTeacher(distributorid, courseID, "0", sign1);
                } else {
                    preDownload = true;
                    showCKDialog();
                }
                mAlertDialog.dismiss();
            }
        });
    }

    private boolean preDownload = false;
    private boolean isdownLoading = false;

    public void showOneDialog(String str) {
        MyToast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
        /*LinearLayout.LayoutParams pm = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.dialog_oneclick, null);
        TextView yes = (TextView) view.findViewById(R.id.yes);
        TextView text1 = (TextView) view.findViewById(R.id.text1);
        text1.setText(str);
        final AlertDialog mAlertDialog = builder.create();
        mAlertDialog.getWindow().setBackgroundDrawableResource(R.color.touming);
        mAlertDialog.show();
        mAlertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        mAlertDialog.getWindow().setContentView(view, pm);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.dismiss();
            }
        });*/
    }


    private boolean flag = false;

  /*  private void showPopWindow() {
        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.pop_gettuanbi, null);
        TextView tv_recharge = (TextView) inflate.findViewById(R.id.tv_recharge);//标题

        RelativeLayout rl_cancel = (RelativeLayout) inflate.findViewById(R.id.rl_cancel);
        final PopWindows popWindows = new PopWindows(getActivity(), getActivity(), inflate);
        popWindows.showPopWindowBottom();
        rl_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindows.cleanPopAlpha();
            }
        });
        tv_recharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RechargeMoneyActivity.class);
                startActivity(intent);
                popWindows.cleanPopAlpha();
            }
        });

    }*/

    private ArrayList<HashMap<String, Object>> array;
    private ArrayList<HashMap<String, Object>> finishList;
    private TextView cktv_realnum;
    private TextView cktv_pay;
    private TextView cktv_tuanbibuzu;
    private TextView tv_ckavailable;

    private void showCKDialog() {
        LinearLayout.LayoutParams pm = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.dialog_danbiapply, null);//这里的R.layout.alertdialog即为你自定义的布局文件
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);//头部
        tv_title.setText("您剩余团币：" + tuanBi);
        RelativeLayout rl_delete = (RelativeLayout) view.findViewById(R.id.rl_delete);//关闭
        rl_delete.setVisibility(View.VISIBLE);
        cktv_tuanbibuzu = (TextView) view.findViewById(R.id.tv_tuanbibuzu);//团币不足
        RelativeLayout rl_tingkequan = (RelativeLayout) view.findViewById(R.id.rl_tingkequan);//听课券
        tv_ckavailable = (TextView) view.findViewById(R.id.tv_available);//可用听课券
        cktv_realnum = (TextView) view.findViewById(R.id.tv_realnum);//实付
        TextView tv_recharge = (TextView) view.findViewById(R.id.tv_recharge);//去充值
        cktv_pay = (TextView) view.findViewById(R.id.tv_pay);//去支付
        TextView tv_shouldnum = (TextView) view.findViewById(R.id.tv_shouldnum);//应付bMTuanBi
        if (voucher.containsKey("ID") && cktv_realnum != null) {
            int tuanbiquan = Integer.parseInt(voucher.get("TuanBi").toString());
            if (tuanbiquan == 0) {
                if (giftLists.size() == 0) {
                    tv_ckavailable.setText("暂无可用听课券");
                } else {
                    tv_ckavailable.setText("有" + giftLists.size() + "张可用");
                }
            } else {
                tv_ckavailable.setText("-" + tuanbiquan);
            }
            int yinggai = Integer.parseInt(bMTuanBi) - tuanbiquan;
            if (yinggai >= 0) {
                cktv_realnum.setText("实付" + yinggai + "币");
            } else {
                cktv_realnum.setText("实付" + 0 + "币");
            }
            if (Integer.parseInt(tuanBi) < yinggai) {
                cktv_tuanbibuzu.setVisibility(View.VISIBLE);
                cktv_pay.setClickable(false);
                cktv_pay.setEnabled(false);
                cktv_pay.setBackgroundResource(R.drawable.bg_radius_apply_no);
            } else {
                cktv_pay.setBackgroundResource(R.drawable.bg_radius_apply);
                cktv_tuanbibuzu.setVisibility(View.GONE);
                cktv_pay.setClickable(true);
                cktv_pay.setEnabled(true);
            }

        }else {
            if (giftLists.size() == 0) {
                tv_ckavailable.setText("暂无可用听课券");
            } else {
                tv_ckavailable.setText("有" + giftLists.size() + "张可用");
            }

            tv_shouldnum.setText("应付" + cKTuanBi + "币");

            cktv_realnum.setText("实付" + cKTuanBi + "币");

            if (Integer.parseInt(tuanBi) < Integer.parseInt(cKTuanBi)) {
                cktv_tuanbibuzu.setVisibility(View.VISIBLE);
                cktv_pay.setClickable(false);
                cktv_pay.setEnabled(false);
                cktv_pay.setBackgroundResource(R.drawable.bg_radius_apply_no);
            } else {
                cktv_pay.setBackgroundResource(R.drawable.bg_radius_apply);
                cktv_tuanbibuzu.setVisibility(View.GONE);
                cktv_pay.setClickable(true);
                cktv_pay.setEnabled(true);
            }
        }

        final AlertDialog mAlertDialog = builder.create();
        mAlertDialog.getWindow().setBackgroundDrawableResource(R.color.touming);
        mAlertDialog.show();
        mAlertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        mAlertDialog.getWindow().setContentView(view, pm);
        rl_tingkequan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //去听课券列表
                ((CourseIntrActivity) getActivity()).mcache.put("coursevoucherList", giftLists);
                Intent intent = new Intent(getActivity(), CourseVoucherActivity.class);
                startActivity(intent);
            }
        });
        tv_recharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //去充值
                Intent intent = new Intent(getActivity(), RechargeMoneyActivity.class);
                startActivity(intent);
                mAlertDialog.dismiss();
            }
        });
        rl_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.dismiss();
            }
        });
        cktv_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //去支付
                mAlertDialog.dismiss();
                String couponid = "0";
                if (voucher != null && voucher.containsKey("ID")) {
                    couponid = voucher.get("ID").toString();
                } else {
                    couponid = "0";
                }
                if (preDownload) {
                    isdownLoading = true;
                }
                String sign = TGmd5.getMD5(distributorid + courseID + couponid);
                ((CourseIntrActivity) getActivity()).showLoadingProgressDialog(getActivity(), "");
                lookTeacherPresenter.lookTeacher(distributorid, courseID, couponid, sign);
            }
        });
    }


    private TextView tv_realnum;
    private TextView tv_pay;
    private TextView tv_tuanbibuzu;
    private TextView tv_bmavailable;

    private void showBMDialog() {
        LinearLayout.LayoutParams pm = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.dialog_danbiapply, null);//这里的R.layout.alertdialog即为你自定义的布局文件
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);//头部
        tv_title.setText("您剩余团币：" + tuanBi);
        RelativeLayout rl_delete = (RelativeLayout) view.findViewById(R.id.rl_delete);//关闭
        rl_delete.setVisibility(View.VISIBLE);
        tv_tuanbibuzu = (TextView) view.findViewById(R.id.tv_tuanbibuzu);//团币不足
        RelativeLayout rl_tingkequan = (RelativeLayout) view.findViewById(R.id.rl_tingkequan);//听课券
        tv_bmavailable = (TextView) view.findViewById(R.id.tv_available);//可用听课券
        TextView tv_shouldnum = (TextView) view.findViewById(R.id.tv_shouldnum);//应付bMTuanBi
        tv_realnum = (TextView) view.findViewById(R.id.tv_realnum);//实付
        TextView tv_recharge = (TextView) view.findViewById(R.id.tv_recharge);//去充值
        tv_pay = (TextView) view.findViewById(R.id.tv_pay);//去支付
        Log.e("kahsdkfjhajsdf", "--------"+voucher );
        if (voucher.containsKey("ID") && tv_realnum != null) {
            int tuanbiquan = Integer.parseInt(voucher.get("TuanBi").toString());
            if (tuanbiquan == 0) {
                if (giftLists.size() == 0) {
                    tv_bmavailable.setText("暂无可用听课券");
                } else {
                    tv_bmavailable.setText("有" + giftLists.size() + "张可用");
                }
            } else {
                tv_bmavailable.setText("-" + tuanbiquan);
            }
            int yinggai = Integer.parseInt(bMTuanBi) - tuanbiquan;
            if (yinggai >= 0) {
                tv_realnum.setText("实付" + yinggai + "币");
            } else {
                tv_realnum.setText("实付" + 0 + "币");
            }
            if (Integer.parseInt(tuanBi) < yinggai) {
                tv_tuanbibuzu.setVisibility(View.VISIBLE);
                tv_pay.setClickable(false);
                tv_pay.setEnabled(false);
                tv_pay.setBackgroundResource(R.drawable.bg_radius_apply_no);
            } else {
                tv_pay.setBackgroundResource(R.drawable.bg_radius_apply);
                tv_tuanbibuzu.setVisibility(View.GONE);
                tv_pay.setClickable(true);
                tv_pay.setEnabled(true);
            }
        }else {
            if (giftLists.size() == 0) {
                tv_bmavailable.setText("暂无可用听课券");
            } else {
                tv_bmavailable.setText("有" + giftLists.size() + "张可用");
            }
            tv_shouldnum.setText("应付" + bMTuanBi + "币");
            tv_realnum.setText("实付" + bMTuanBi + "币");
            if (Integer.parseInt(tuanBi) < Integer.parseInt(bMTuanBi)) {
                tv_tuanbibuzu.setVisibility(View.VISIBLE);
                tv_pay.setClickable(false);
                tv_pay.setEnabled(false);
                tv_pay.setBackgroundResource(R.drawable.bg_radius_apply_no);
            } else {
                tv_pay.setBackgroundResource(R.drawable.bg_radius_apply);
                tv_tuanbibuzu.setVisibility(View.GONE);
                tv_pay.setClickable(true);
                tv_pay.setEnabled(true);
            }
        }

        final AlertDialog mAlertDialog = builder.create();
        mAlertDialog.getWindow().setBackgroundDrawableResource(R.color.touming);
        mAlertDialog.show();
        mAlertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        mAlertDialog.getWindow().setContentView(view, pm);
        rl_tingkequan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //去听课券列表
                ((CourseIntrActivity) getActivity()).mcache.put("coursevoucherList", giftLists);
                Intent intent = new Intent(getActivity(), CourseVoucherActivity.class);
                startActivity(intent);
            }
        });
        tv_recharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //去充值
                Intent intent = new Intent(getActivity(), RechargeMoneyActivity.class);
                startActivity(intent);
                mAlertDialog.dismiss();
            }
        });
        rl_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.dismiss();
            }
        });
        tv_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //去支付
                mAlertDialog.dismiss();
                String couponid = "0";
                if (voucher != null && voucher.containsKey("ID")) {
                    couponid = voucher.get("ID").toString();
                } else {
                    couponid = "0";
                }
                String sign = TGmd5.getMD5(distributorid + courseID + couponid);
                ((CourseIntrActivity) getActivity()).showLoadingProgressDialog(getActivity(), "");
                baomingTeacherPresenter.applyTeacher(distributorid, courseID, couponid, sign);
            }
        });
    }

    private HashMap<String, Object> voucher;

    @Override
    public void onResume() {
        super.onResume();
        voucher = (HashMap<String, Object>) ((CourseIntrActivity) getActivity()).mcache.getAsObject("isselectvoucher");
        if (voucher == null) {
            voucher = new HashMap<>();
        }
        if (voucher.containsKey("ID") && tv_realnum != null) {
            int tuanbiquan = Integer.parseInt(voucher.get("TuanBi").toString());
            if (tuanbiquan==0){
                if (giftLists.size() == 0) {
                    tv_bmavailable.setText("暂无可用听课券");
                } else {
                    tv_bmavailable.setText("有" + giftLists.size() + "张可用");
                }
            }else {
                tv_bmavailable.setText("-"+tuanbiquan);
            }
            int yinggai = Integer.parseInt(bMTuanBi) - tuanbiquan;
            if (yinggai >= 0) {
                tv_realnum.setText("实付" + yinggai + "币");
            } else {
                tv_realnum.setText("实付" + 0 + "币");
            }
            if (Integer.parseInt(tuanBi) < yinggai) {
                tv_tuanbibuzu.setVisibility(View.VISIBLE);
                tv_pay.setClickable(false);
                tv_pay.setEnabled(false);
                tv_pay.setBackgroundResource(R.drawable.bg_radius_apply_no);
            } else {
                tv_pay.setBackgroundResource(R.drawable.bg_radius_apply);
                tv_tuanbibuzu.setVisibility(View.GONE);
                tv_pay.setClickable(true);
                tv_pay.setEnabled(true);
            }

        }
        if (voucher.containsKey("ID") && cktv_realnum != null) {
            int tuanbiquan = Integer.parseInt(voucher.get("TuanBi").toString());
            if (tuanbiquan == 0) {
                if (giftLists.size() == 0) {
                    tv_ckavailable.setText("暂无可用听课券");
                } else {
                    tv_ckavailable.setText("有" + giftLists.size() + "张可用");
                }
            } else {
                tv_ckavailable.setText("-" + tuanbiquan);
            }
            int yinggai = Integer.parseInt(bMTuanBi) - tuanbiquan;
            if (yinggai >= 0) {
                cktv_realnum.setText("实付" + yinggai + "币");
            } else {
                cktv_realnum.setText("实付" + 0 + "币");
            }
            if (Integer.parseInt(tuanBi) < yinggai) {
                cktv_tuanbibuzu.setVisibility(View.VISIBLE);
                cktv_pay.setClickable(false);
                cktv_pay.setEnabled(false);
                cktv_pay.setBackgroundResource(R.drawable.bg_radius_apply_no);
            } else {
                cktv_pay.setBackgroundResource(R.drawable.bg_radius_apply);
                cktv_tuanbibuzu.setVisibility(View.GONE);
                cktv_pay.setClickable(true);
                cktv_pay.setEnabled(true);
            }

        }
    }

    @Override
    public void OnBaomingTeacherSuccCallBack(String state23, String respanse) {
        ((CourseIntrActivity) getActivity()).closeLoadingProgressDialog();
        //报名成功
        isstudy = "1";
        MyToast.makeText(getActivity(), "报名成功", Toast.LENGTH_SHORT).show();
        if (state.equals("3")) {
            //报名或者众筹中
            if (GID == null || GID.equals("")) {
                stateTV.setText("微信开课中，联系客服");
                stateTV.setBackgroundColor(Color.parseColor("#EAD4AF"));
            } else {
                stateTV.setText("进入直播间");
                stateTV.setBackgroundColor(Color.parseColor("#66c300"));
            }
            /*if (reviewType.equals("3")) {
                //回顾类型是直播间
                stateTV.setText("进入直播间");
                stateTV.setBackgroundColor(Color.parseColor("#66c300"));
            } else {
                stateTV.setText("已报名，等待开课");
                stateTV.setBackgroundColor(Color.parseColor("#EAD4AF"));
            }*/
        } else if (state.equals("4")) {
            //已结束
            if (reviewType.equals("3")) {
                //回顾类型是直播间
                stateTV.setText("进入直播间");
                stateTV.setBackgroundColor(Color.parseColor("#66c300"));
            } else {
                //已结束，刷新页面
                ((CourseIntrActivity) getActivity()).updataUrl(videoUrl);
                stateTV.setText("已购买");
                stateTV.setBackgroundColor(Color.parseColor("#EAD4AF"));
            }
        } else {
            stateTV.setText("已报名，等待开课");
            stateTV.setBackgroundColor(Color.parseColor("#EAD4AF"));
        }


    }

    @Override
    public void OnBaomingTeacherFialCallBack(String state, String respanse) {
        ((CourseIntrActivity) getActivity()).closeLoadingProgressDialog();
        MyToast.makeText(getActivity(), "报名失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void closeBaomingTeacherProgress() {

    }

    @Override
    public void OnLookTeacherSuccCallBack(String state23, String respanse) {
        ((CourseIntrActivity) getActivity()).closeLoadingProgressDialog();
        //付币查看成功
        isstudy = "1";
        if (state.equals("4")) {
            //已结束
            if (reviewType.equals("3")) {
                //回顾类型是直播间
                stateTV.setText("进入直播间");
                stateTV.setBackgroundColor(Color.parseColor("#66c300"));
            } else {
                ((CourseIntrActivity) getActivity()).updataUrl(videoUrl);
                tv_fubichakan.setText("已购买");
                tv_fubichakan.setBackgroundColor(Color.parseColor("#EAD4AF"));
            }
        } else {
            tv_fubichakan.setText("已购买");
            tv_fubichakan.setBackgroundColor(Color.parseColor("#EAD4AF"));
        }
        if (isdownLoading) {
            //如果是购买并下载的
            try {
                HashMap<String, Object> hashMap = new HashMap<String, Object>();
                hashMap.put("Theme", info.get("Theme"));
                hashMap.put("Banner2", info.get("Banner1"));
                hashMap.put("Comment", info.get("Content"));
                hashMap.put("isdown", "true");
                array.add(hashMap);
                ((CourseIntrActivity) getActivity()).mcache.put("xiazaiduilie" + distributorid, array);
                String SDPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/fengyouke/" + distributorid + "/";
                String downStr = info.get("Content").toString();
                String name = TGmd5.getMD5(downStr);
                if (downStr.endsWith(".mp3") || downStr.endsWith(".MP3")) {
                    name = name + ".mp3";
                } else {
                    name = name + ".mp4";
                }

                DownloadManager downloadManager = DownloadService.getDownloadManager(getActivity());
                downloadManager.addNewDownload(downStr, name, SDPath + name, true, true, new RequestCallBack<File>() {
                    @Override
                    public void onSuccess(ResponseInfo<File> responseInfo) {

                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        //如果下载失败了
                        View toastRoot = getActivity().getLayoutInflater().inflate(R.layout.toast_download_sign, null);
                        TextView tv_toast_text = (TextView) toastRoot.findViewById(R.id.tv_toast_text);
                        tv_toast_text.setText("下载失败");
                        Toast toast = new Toast(getActivity());
                        toast.setView(toastRoot);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        tv_download.setText("下载失败");
                        tv_download.setTextColor(Color.parseColor("#fc4d30"));
                        im_download.setBackgroundResource(R.mipmap.download_failer);
                        array = (ArrayList<HashMap<String, Object>>) ((CourseIntrActivity) getActivity()).mcache.getAsObject("xiazaiduilie" + distributorid);
                        if (array == null) {
                            array = new ArrayList<>();
                        }
                        for (int i = 0; i < array.size(); i++) {
                            if (array.get(i).get("Comment").toString().equals(info.get("Content").toString())) {
                                array.get(i).put("isdown", "false");
                                break;
                            }
                        }
                        ((CourseIntrActivity) getActivity()).mcache.put("xiazaiduilie" + distributorid, array);
                    }
                });
                View toastRoot = getActivity().getLayoutInflater().inflate(R.layout.toast_download_sign, null);
                Toast toast = new Toast(getActivity());
                toast.setView(toastRoot);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                tv_download.setText("下载中");
                im_download.setBackgroundResource(R.mipmap.course_downloading_icon);
                ViewGroup.LayoutParams para = im_download.getLayoutParams();
                int iheight = (int) ScreenUtils.dpToPx(getActivity(), 12);
                para.height = iheight;// 控件的高强制设成
                im_download.setLayoutParams(para);
                Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_loading_anim);
                animation.setInterpolator(new LinearInterpolator());
                im_download.setAnimation(animation);
                EventBus.getDefault().post("downloadclass");

            } catch (DbException e) {
                e.printStackTrace();
            }
        }
        ((CourseIntrActivity) getActivity()).setVideoUrl(videoUrl);
    }

    @Override
    public void OnLookTeacherFialCallBack(String state, String respanse) {
        ((CourseIntrActivity) getActivity()).closeLoadingProgressDialog();
    }

    @Override
    public void closeLookTeacherProgress() {

    }

    @Override
    public void OnUpDoDateStateSuccCallBack(String statess, String respanse) {
        //更改学习状态成功
        ((CourseIntrActivity) getActivity()).closeLoadingProgressDialog();
        Bundle bundle1 = new Bundle();
        bundle1.putString("teacherId", teacherDistributorID);
        bundle1.putInt("ktID", Integer.parseInt(courseID));
        bundle1.putInt("KDBDistributorID", Integer.parseInt(KDBDistributorID));
        bundle1.putString("GId", GID);
        bundle1.putString("theme", theme);
        bundle1.putString("courseState", state);
        ((CourseIntrActivity) getActivity()).openActivity(LiveChatActivity.class, bundle1);
//        ((CourseIntrActivity) getActivity()).openActivity(LiveDirectActivity.class, bundle1);
    }

    @Override
    public void OnUpDoDateStateFialCallBack(String state, String respanse) {
        ((CourseIntrActivity) getActivity()).closeLoadingProgressDialog();
    }

    @Override
    public void closeUpDoDateStateProgress() {

    }

    @Override
    public void OnDistributorHomeSuccCallBack(String state, String respanse) {
        ((CourseIntrActivity) getActivity()).closeLoadingProgressDialog();
        try {
            JSONObject jsb = new JSONObject(respanse);
            JSONArray jsa = new JSONArray(jsb.getString("result"));
            JSONObject distributorModeljsa = jsa.getJSONObject(0);
            if (distributorModeljsa.get("UserType").toString().equals("3")) {
                //如果是讲师
                Intent intent1 = new Intent(getActivity(), TeacherHomeActivity.class);
                intent1.putExtra("seeDistributorId", distributorModeljsa.get("ID").toString());
                startActivity(intent1);
            } else {
                //普通导游
                Intent intent1 = new Intent(getActivity(), DistributorHomeActivity.class);
                intent1.putExtra("seeDistributorId", distributorModeljsa.get("ID").toString());
                startActivity(intent1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void OnDistributorHomeFialCallBack(String state, String respanse) {
        ((CourseIntrActivity) getActivity()).closeLoadingProgressDialog();
    }

    @Override
    public void closeDistributorHomeProgress() {

    }


    private class MyOpenTask extends AsyncTask<Integer, Integer, Integer> {
        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        public void start() {
            execute(0);
        }

        @Override
        protected Integer doInBackground(Integer... params) {
            return 1;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            int lineCount = tv_course_detail.getLineCount();
            if (lineCount > 3) {
                tv_course_detail.setMaxLines(3);
                rl_more_themeinfo.setVisibility(View.VISIBLE);
                view_line.setVisibility(View.GONE);
            } else {
                rl_more_themeinfo.setVisibility(View.GONE);
                view_line.setVisibility(View.VISIBLE);
            }
        }

    }


    /**
     * 在主线程接受 eventbus发送的事件
     *
     * @param action
     * @Subscribe 这个注解必须加上
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUIEvent(String action) {
        if ("coursedownloadingfinish".equals(action)) {
            //有课程已经下载完成
            finishList = (ArrayList<HashMap<String, Object>>) ((CourseIntrActivity) getActivity()).mcache.getAsObject("downloadfinish" + distributorid);
            if (null == finishList) {
                finishList = new ArrayList<HashMap<String, Object>>();
            }
            for (HashMap<String, Object> hashMap : finishList) {
                if (hashMap.get("Comment").toString().equals(info.get("Content").toString())) {
                    //这堂课已经下载完成了
                    //将下面的隐藏掉
                    rl_introduction.setVisibility(View.GONE);
//                    ll_chakan.setVisibility(View.GONE);
                    break;
                }
            }
        }
    }

    @Override
    public void pullToRefresh() {

    }

    @Override
    public void refreshComplete() {

    }

    @Override
    public View getScrollableView() {
        return mListView;
    }
}
