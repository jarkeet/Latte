package com.cmcc.hy.latte.net.callback;

import android.os.Handler;

import com.cmcc.hy.latte.app.ConfigKeys;
import com.cmcc.hy.latte.app.Latte;
import com.cmcc.hy.latte.net.RestCreator;
import com.cmcc.hy.latte.ui.loader.LatteLoader;
import com.cmcc.hy.latte.ui.loader.LoaderStyle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jeff on 2018/5/11.
 */

public final class RequestCallbacks implements Callback<String> {

    private final IRequest mIRequest;
    private final ISuccess mISuccess;
    private final IError mIError;
    private final IFailure mIFailure;
    private final LoaderStyle mLoaderStyle;
    private static final Handler HANDLER = Latte.getHandler();

    public RequestCallbacks(IRequest IRequest, ISuccess ISuccess, IError IError, IFailure IFailure, LoaderStyle loaderStyle) {
        mIRequest = IRequest;
        mISuccess = ISuccess;
        mIError = IError;
        mIFailure = IFailure;
        mLoaderStyle = loaderStyle;
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        if(response.isSuccessful()) {
            if(call.isExecuted()) {
                if(mISuccess != null) {
                    mISuccess.onSuccess(response.body());
                }
            }
        } else {
            if(mIError != null) {
                mIError.onError(response.code(), response.message());
            }
        }

        onRequestFinished();
    }



    @Override
    public void onFailure(Call<String> call, Throwable t) {
        if(mIFailure != null) {
            mIFailure.onFailure();
        }

        if(mIRequest != null) {
            mIRequest.onRequestEnd();
        }
        onRequestFinished();

    }

    private void onRequestFinished() {

        final long delay = Latte.getConfiguration(ConfigKeys.LOADER_DELAY);
        if(mLoaderStyle != null) {
            HANDLER.postDelayed(new Runnable() {
                @Override
                public void run() {
                    RestCreator.getParams().clear();
                    LatteLoader.stopLoading();
                }
            }, delay);
        }
    }
}
