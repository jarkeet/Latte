package com.cmcc.hy.latte.net;

import android.content.Context;

import com.cmcc.hy.latte.net.callback.IError;
import com.cmcc.hy.latte.net.callback.IFailure;
import com.cmcc.hy.latte.net.callback.IRequest;
import com.cmcc.hy.latte.net.callback.ISuccess;
import com.cmcc.hy.latte.ui.loader.LoaderStyle;

import java.io.File;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by jeff on 2018/5/11.
 */

public class RestClientBuilder {

    private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();

    private  String mUrl;
    private  IRequest mIRequest;
    private  ISuccess mISuccess;
    private  IFailure mIFailure;
    private  IError mIError;
    private  RequestBody mRequestBody;
    private  LoaderStyle mLoaderStyle;
    private  File mFile;
    private  Context mContext;
    private  String mDownloadDir;
    private  String mExtension;
    private  String mName;

    RestClientBuilder() {
    }

    public final RestClientBuilder url(String url) {
        this.mUrl = url;
        return this;
    }

    public final RestClientBuilder params(WeakHashMap<String, Object> params) {
        PARAMS.putAll(params);
        return this;
    }

    public final RestClientBuilder params(String key, Object value) {
        PARAMS.put(key, value);
        return this;
    }

    public final RestClientBuilder file(File file) {
        this.mFile = file;
        return this;
    }

    public final RestClientBuilder file(String file) {
        this.mFile = new File(file);
        return this;
    }

    public final RestClientBuilder name(String name) {
        this.mName = name;
        return this;
    }

    public final RestClientBuilder dir(String dir) {
        this.mDownloadDir = dir;
        return this;
    }

    public final RestClientBuilder extension(String extension) {
        this.mExtension = extension;
        return this;
    }

    public final RestClientBuilder raw(String raw) {
        this.mRequestBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }

    public final RestClientBuilder onRequest(IRequest iRequest) {
        this.mIRequest = iRequest;
        return this;
    }

    public final RestClientBuilder success(ISuccess iSuccess) {
        this.mISuccess = iSuccess;
        return this;
    }

    public final RestClientBuilder failure(IFailure iFailure) {
        this.mIFailure = iFailure;
        return this;
    }

    public final RestClientBuilder error(IError iError) {
        this.mIError = iError;
        return this;
    }

    public final RestClientBuilder loader(Context context, LoaderStyle style) {
        this.mContext = context;
        this.mLoaderStyle = style;
        return this;
    }

    public final RestClientBuilder loader(Context context) {
        this.mContext = context;
        this.mLoaderStyle = LoaderStyle.BallClipRotatePulseIndicator;
        return this;
    }

    public final RestClient build() {
        return new RestClient(mUrl, PARAMS,
                mDownloadDir, mExtension, mName,
                mIRequest, mISuccess, mIFailure,
                mIError, mRequestBody, mFile, mContext,
                mLoaderStyle);
    }


}
