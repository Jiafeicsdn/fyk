package com.lvgou.distribution.utils;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;


import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.db.converter.ColumnConverter;
import com.lidroid.xutils.db.converter.ColumnConverterFactory;
import com.lidroid.xutils.db.sqlite.ColumnDbType;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.HttpHandler.State;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.util.LogUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 下载管理器
 */
public class DownloadManager {

    private List<DownloadInfo> downloadInfoList; // 下载列表
    private List<DownloadInfo> alreadyDownLoadList; // 已经下载完成的
    private List<DownloadInfo> downLoadingList; // 正在下载
    private int maxDownloadThread = 1; // 最大下载线程数

    private Context mContext;
    private DbUtils db;

    private long mLastTime;
    private long mLastFileSize;

    /* package */
    public DownloadManager(Context appContext) {
        ColumnConverterFactory.registerColumnConverter(HttpHandler.State.class,
                new HttpHandlerStateConverter());
        mContext = appContext;
        db = DbUtils.create(mContext);
        alreadyDownLoadList = new ArrayList<DownloadInfo>();
        downLoadingList = new ArrayList<DownloadInfo>();
        try {
            downloadInfoList = db.findAll(Selector.from(DownloadInfo.class)); // 查询所有的下载
        } catch (DbException e) {
            LogUtils.e(e.getMessage(), e);
        }
        if (downloadInfoList == null) {
            downloadInfoList = new ArrayList<DownloadInfo>();

        } else {
            for (int i = 0; i < downloadInfoList.size(); i++) {
                if (downloadInfoList
                        .get(i)
                        .getState()
                        .equals(com.lidroid.xutils.http.HttpHandler.State.SUCCESS)) {
                    alreadyDownLoadList.add(downloadInfoList.get(i));
                } else {
                    downLoadingList.add(downloadInfoList.get(i));
                }
            }
        }

    }

    public int getDownloadInfoListCount() {
        return downloadInfoList.size();
    }

    public DownloadInfo getDownloadInfo(int index) {
        return downloadInfoList.get(index);
    }

    public int getDownloadInfoLoadingCount() {
        return downLoadingList.size();

    }

    public List<DownloadInfo> getDownLoadingList() {

        return downLoadingList;
    }

    public DownloadInfo getDownLoadinginfo(int index) {
        downLoadingList.get(index);
        return downLoadingList.get(index);
    }

    public int getAlreadyDownLoadCount() {
        return alreadyDownLoadList.size();

    }

    public List<DownloadInfo> getAlreadyDownLoadList() {

        return alreadyDownLoadList;
    }

    public DownloadInfo getAlreadyDownLoadInfo(int index) {
        alreadyDownLoadList.get(index);
        return alreadyDownLoadList.get(index);
    }

    /**
     * 获取当前url的文件是否在下载列表里，或去的时候想确保把改下载添加到了下载列表中
     *
     * @param downloadUrl
     * @return
     */
    public DownloadInfo getDownLoadInfo(String downloadUrl) {
        DownloadInfo downloadInfo = null;
        for (DownloadInfo doInfo : downloadInfoList) {
            if (doInfo.getDownloadUrl().equals(downloadUrl)) {
                downloadInfo = doInfo;
                return downloadInfo;
            }
        }
        return downloadInfo;
    }

    /**
     * 添加下载任务
     *
     * @param url        下载链接
     * @param fileName   文件名
     * @param target     文件存放路径
     * @param autoResume
     * @param autoRename
     * @param callback   回调
     * @throws DbException
     */
    public void addNewDownload(String url, String fileName, String target,
                               boolean autoResume, boolean autoRename,
                               final RequestCallBack<File> callback) throws DbException {
        if (Utils.fileIsExists(target)) {// 下载文件已经存在不在添加下载任务
        } else {
            final DownloadInfo downloadInfo = new DownloadInfo();
            downloadInfo.setDownloadUrl(url);
            downloadInfo.setAutoRename(autoRename);
            downloadInfo.setAutoResume(autoResume);
            downloadInfo.setFileName(fileName);
            downloadInfo.setFileSavePath(target);
            HttpUtils http = new HttpUtils();
            http.configRequestThreadPoolSize(maxDownloadThread);
            HttpHandler<File> handler = http.download(url, target, autoResume,
                    autoRename, new ManagerCallBack(downloadInfo, callback));
            downloadInfo.setHandler(handler);
            downloadInfo.setState(handler.getState());
            downloadInfoList.add(downloadInfo);
            db.saveBindingId(downloadInfo);
        }
    }

    public void resumeDownload(int index, final RequestCallBack<File> callback)
            throws DbException {
        final DownloadInfo downloadInfo = downloadInfoList.get(index);
        resumeDownload(downloadInfo, callback);
    }

    /**
     * 重新下载
     *
     * @param downloadInfo downLoadinfo信息
     * @param callback     回调
     * @throws DbException
     */
    public void resumeDownload(DownloadInfo downloadInfo,
                               final RequestCallBack<File> callback) throws DbException {
        HttpUtils http = new HttpUtils();
        http.configRequestThreadPoolSize(maxDownloadThread);
        HttpHandler<File> handler = http.download(
                downloadInfo.getDownloadUrl(), downloadInfo.getFileSavePath(),
                downloadInfo.isAutoResume(), downloadInfo.isAutoRename(),
                new ManagerCallBack(downloadInfo, callback));
        downloadInfo.setHandler(handler);
        downloadInfo.setState(handler.getState());
        db.saveOrUpdate(downloadInfo);
    }

    /**
     * 移除下载任务
     *
     * @param index 要移除的下表
     * @throws DbException
     */
    public void removeDownload(int index) throws DbException {
        DownloadInfo downloadInfo = downloadInfoList.get(index);
        removeDownload(downloadInfo);
    }
    public void removeAllDownload() throws DbException {
        for (DownloadInfo downloadInfo : downloadInfoList) {
            removeDownload(downloadInfo);
        }
        for (DownloadInfo downloadInfo : alreadyDownLoadList) {
            removeAlreadyDownLoad(downloadInfo);
        }
    }

    /**
     * 移除下载任务
     *
     * @param downloadInfo 下载的downloadInfo对象
     * @throws DbException
     */
    public void removeDownload(DownloadInfo downloadInfo) throws DbException {
        HttpHandler<File> handler = downloadInfo.getHandler();
        if (handler != null && !handler.isCancelled()) {
            handler.cancel();
        }
        downloadInfoList.remove(downloadInfo);
        downLoadingList.remove(downloadInfo);
        alreadyDownLoadList.remove(downloadInfo);
        Utils.deleteFile(new File(downloadInfo.getFileSavePath()));
        db.delete(downloadInfo);
    }

    public void removeAlreadyDownLoad(DownloadInfo downloadInfo) throws DbException {
        HttpHandler<File> handler = downloadInfo.getHandler();
        if (handler != null && !handler.isCancelled()) {
            handler.cancel();
        }

        alreadyDownLoadList.remove(downloadInfo);
        Utils.deleteFile(new File(downloadInfo.getFileSavePath()));
        db.delete(downloadInfo);
    }

    /**
     * 停止下载
     *
     * @param index 下载的下表
     * @throws DbException
     */
    public void stopDownload(int index) throws DbException {
        DownloadInfo downloadInfo = downloadInfoList.get(index);
        stopDownload(downloadInfo);
    }

    /**
     * 停止下载
     *
     * @param downloadInfo 下载的downloadInfo对象
     * @throws DbException
     */
    public void stopDownload(DownloadInfo downloadInfo) throws DbException {
        HttpHandler<File> handler = downloadInfo.getHandler();
        if (handler != null && !handler.isCancelled()) {
            handler.cancel();
        } else {
            downloadInfo.setState(HttpHandler.State.CANCELLED);
        }
        db.saveOrUpdate(downloadInfo);
    }

    /**
     * 停止所有的下载任务
     *
     * @throws DbException
     */
    public void stopAllDownload() throws DbException {
        for (DownloadInfo downloadInfo : downloadInfoList) {
            HttpHandler<File> handler = downloadInfo.getHandler();
            if (handler != null && !handler.isCancelled()) {
                handler.cancel();
            } else {
                downloadInfo.setState(HttpHandler.State.CANCELLED);
            }
        }
        db.saveOrUpdateAll(downloadInfoList);
    }

    /**
     * 改变数据库中下载状态
     *
     * @throws DbException
     */
    public void backupDownloadInfoList() throws DbException {
        for (DownloadInfo downloadInfo : downloadInfoList) {
            HttpHandler<File> handler = downloadInfo.getHandler();
            if (handler != null) {
                switch (handler.getState()) {
                    case WAITING:
                        downloadInfo.setState(State.CANCELLED);
                        break;
                    case SUCCESS:

                        break;
                    case FAILURE:
                        break;
                    case LOADING:
                        downloadInfo.setState(State.CANCELLED);
                        break;
                    case CANCELLED:
                        downloadInfo.setState(State.CANCELLED);
                        break;
                    case STARTED:
                        downloadInfo.setState(State.CANCELLED);
                        break;
                    default:
                        break;
                }

            }
        }
        db.saveOrUpdateAll(downloadInfoList);
    }

    public int getMaxDownloadThread() {
        return maxDownloadThread;
    }

    /**
     * 设置最大下载线程
     *
     * @param maxDownloadThread
     */
    public void setMaxDownloadThread(int maxDownloadThread) {
        this.maxDownloadThread = maxDownloadThread;
    }

    public class ManagerCallBack extends RequestCallBack<File> {
        private DownloadInfo downloadInfo;
        private RequestCallBack<File> baseCallBack;

        public RequestCallBack<File> getBaseCallBack() {
            return baseCallBack;
        }

        public void setBaseCallBack(RequestCallBack<File> baseCallBack) {
            this.baseCallBack = baseCallBack;
        }

        private ManagerCallBack(DownloadInfo downloadInfo,
                                RequestCallBack<File> baseCallBack) {
            this.baseCallBack = baseCallBack;
            this.downloadInfo = downloadInfo;
        }

        @Override
        public Object getUserTag() {
            if (baseCallBack == null)
                return null;
            return baseCallBack.getUserTag();
        }

        @Override
        public void setUserTag(Object userTag) {
            if (baseCallBack == null)
                return;
            baseCallBack.setUserTag(userTag);
        }

        @Override
        public void onStart() {
            HttpHandler<File> handler = downloadInfo.getHandler();
            if (handler != null) {
                downloadInfo.setState(handler.getState());
            }
            try {
                db.saveOrUpdate(downloadInfo);
                if (downLoadingList.size() == 0) {
                    downLoadingList.add(downloadInfo);
                }
            } catch (DbException e) {
                LogUtils.e(e.getMessage(), e);
            }
            if (baseCallBack != null) {
                baseCallBack.onStart();
            }
        }

        @Override
        public void onCancelled() {
            Log.e("kahskdfh", "-----------onCancelled");
            HttpHandler<File> handler = downloadInfo.getHandler();
            if (handler != null) {
                downloadInfo.setState(handler.getState());
            }
            try {
                db.saveOrUpdate(downloadInfo);
            } catch (DbException e) {
                LogUtils.e(e.getMessage(), e);
            }
            if (baseCallBack != null) {
                baseCallBack.onCancelled();
            }
        }

        @Override
        public void onLoading(long total, long current, boolean isUploading) {
            HttpHandler<File> handler = downloadInfo.getHandler();
            if (handler != null) {
                downloadInfo.setState(handler.getState());
            }
            long nowTime = System.currentTimeMillis();
            Log.e("SPEED1", nowTime + "");
            Log.e("SPEED2", mLastTime + "");
            Log.e("SPEED1_file", current + "");
            Log.e("SPEED2_file", mLastFileSize + "");
            float speed = 0;
            float speedContent = current - mLastFileSize;
            float speedTimer = nowTime - mLastTime;
            if (mLastTime > 0 && current != 0) {

                if (speedContent >= 1024) {
                    speedContent = (float) ((speedContent) / (1024 + 0.0));
                    speedContent = (float) (((int) (speedContent * 10) % 10 + 0.0) / 10 + (int) speedContent);
                    speedTimer = (float) ((speedTimer) / (1000 + 0.0));
                    speed = speedContent / speedTimer;
                }
                Log.e("SPEED", speed + "");
            }

            mLastTime = nowTime;
            mLastFileSize = current;
            downloadInfo.setSpeed(speed);
            downloadInfo.setFileLength(total);

            downloadInfo.setFileAllSize(((float) total) / ((float) 1048576));
            ;
            downloadInfo.setProgress(current);
            try {
                db.saveOrUpdate(downloadInfo);
            } catch (DbException e) {
                LogUtils.e(e.getMessage(), e);
            }
            if (baseCallBack != null) {
                baseCallBack.onLoading(total, current, isUploading);
            }
        }

        @Override
        public void onSuccess(ResponseInfo<File> responseInfo) {
            HttpHandler<File> handler = downloadInfo.getHandler();
            if (handler != null) {
                downloadInfo.setState(handler.getState());
                downLoadingList.remove(downloadInfo);
                alreadyDownLoadList.add(downloadInfo);
            }
            try {
                db.saveOrUpdate(downloadInfo);
            } catch (DbException e) {
                LogUtils.e(e.getMessage(), e);
            }
            if (baseCallBack != null) {
                baseCallBack.onSuccess(responseInfo);
            }
        }

        @Override
        public void onFailure(HttpException error, String msg) {
            Log.e("kahskdfh", "-----------onfilure");
            HttpHandler<File> handler = downloadInfo.getHandler();
            if (handler != null) {
                downloadInfo.setState(handler.getState());
            }
            try {
                db.saveOrUpdate(downloadInfo);
            } catch (DbException e) {
                LogUtils.e(e.getMessage(), e);
            }
            if (baseCallBack != null) {
                baseCallBack.onFailure(error, msg);
            }
        }
    }

    private class HttpHandlerStateConverter implements
            ColumnConverter<HttpHandler.State> {

        @Override
        public HttpHandler.State getFieldValue(Cursor cursor, int index) {
            return HttpHandler.State.valueOf(cursor.getInt(index));
        }

        @Override
        public HttpHandler.State getFieldValue(String fieldStringValue) {
            if (fieldStringValue == null)
                return null;
            return HttpHandler.State.valueOf(fieldStringValue);
        }

        @Override
        public Object fieldValue2ColumnValue(HttpHandler.State fieldValue) {
            return fieldValue.value();
        }

        @Override
        public ColumnDbType getColumnDbType() {
            return ColumnDbType.INTEGER;
        }
    }
}