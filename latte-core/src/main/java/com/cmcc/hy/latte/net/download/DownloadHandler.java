package com.cmcc.hy.latte.net.download;

import android.os.AsyncTask;

import com.cmcc.hy.latte.net.RestCreator;
import com.cmcc.hy.latte.net.callback.IError;
import com.cmcc.hy.latte.net.callback.IFailure;
import com.cmcc.hy.latte.net.callback.IRequest;
import com.cmcc.hy.latte.net.callback.ISuccess;

import java.util.WeakHashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jeff on 2018/5/11.
 */

public class DownloadHandler {

    private final String url;
    private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private final IRequest mIRequest;
    private final String mDownloadDir;
    private final String extension;
    private final String name;
    private final ISuccess mISuccess;
    private final IFailure mIFailure;
    private final IError mIError;

    public DownloadHandler(String url,
                           IRequest IRequest,
                           String downloadDir,
                           String extension,
                           String name,
                           ISuccess ISuccess,
                           IFailure IFailure,
                           IError IError) {
        this.url = url;
        mIRequest = IRequest;
        mDownloadDir = downloadDir;
        this.extension = extension;
        this.name = name;
        mISuccess = ISuccess;
        mIFailure = IFailure;
        mIError = IError;
    }

    public final void handlerDownload() {
        if (mIRequest != null) {
            mIRequest.onRequestStart();
        }

        RestCreator.getRestService().download(url, PARAMS)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            final ResponseBody responseBody = response.body();
                            final SaveFileTask task = new SaveFileTask(mIRequest, mISuccess);
                            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
                                    mDownloadDir, extension, responseBody, name);
                            //这里一定要判断，否则文件容易下载不全
                            if (task.isCancelled()) {
                                if (mIRequest != null) {
                                    mIRequest.onRequestEnd();
                                }
                            }
                        } else {
                            if (mIError != null) {
                                mIError.onError(response.code(), response.message());
                            }
                        }
                        RestCreator.getParams().clear();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        if (mIFailure != null) {
                            mIFailure.onFailure();
                            RestCreator.getParams().clear();
                        }
                    }
                });
    }
}
