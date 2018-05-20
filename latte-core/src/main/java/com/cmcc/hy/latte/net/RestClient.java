package com.cmcc.hy.latte.net;

import android.content.Context;

import com.cmcc.hy.latte.net.callback.IError;
import com.cmcc.hy.latte.net.callback.IFailure;
import com.cmcc.hy.latte.net.callback.IRequest;
import com.cmcc.hy.latte.net.callback.ISuccess;
import com.cmcc.hy.latte.net.callback.RequestCallbacks;
import com.cmcc.hy.latte.net.download.DownloadHandler;
import com.cmcc.hy.latte.ui.loader.LatteLoader;
import com.cmcc.hy.latte.ui.loader.LoaderStyle;

import java.io.File;
import java.util.WeakHashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by jeff on 2018/5/11.
 */

public class RestClient {

    private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();

    private final String url;
    private final IRequest mIRequest;
    private final String mDownloadDir;
    private final String extension;
    private final String name;
    private final ISuccess mISuccess;
    private final IFailure mIFailure;
    private final IError mIError;
    private final RequestBody mRequestBody;
    private final LoaderStyle mLoaderStyle;
    private final File mFile;
    private final Context mContext;

    public RestClient(String url,
                      WeakHashMap<String, Object> params,
                      String downloadDir,
                      String extension,
                      String name,
                      IRequest IRequest,
                      ISuccess ISuccess,
                      IFailure IFailure,
                      IError IError,
                      RequestBody requestBody,
                      File file,
                      Context context,
                      LoaderStyle loaderStyle) {
        this.url = url;
        PARAMS.putAll(params);
        mDownloadDir = downloadDir;
        this.extension = extension;
        this.name = name;
        mIRequest = IRequest;
        mISuccess = ISuccess;
        mIFailure = IFailure;
        mIError = IError;
        mRequestBody = requestBody;
        mFile = file;
        mContext = context;
        mLoaderStyle = loaderStyle;

    }

    public static RestClientBuilder builder() {
        return new RestClientBuilder();
    }

    private void request(HttpMethod method) {
        final RestService service = RestCreator.getRestService();
        Call<String> call = null;
        if(mIRequest != null) {
            mIRequest.onRequestStart();
        }
        if(mLoaderStyle != null) {
            LatteLoader.showLoading(mContext, mLoaderStyle);
        }

        switch (method) {
            case GET:
                call = service.get(url, PARAMS);
                break;
            case POST:
                call = service.post(url, PARAMS);
                break;
            case POST_RAW:
                call = service.postRaw(url, mRequestBody);
                break;
            case PUT:
                call = service.put(url, PARAMS);
                break;
            case PUT_RAW:
                call = service.putRaw(url, mRequestBody);
                break;
            case DELETE:
                call = service.delete(url, PARAMS);
                break;
            case UPLOAD:
                final RequestBody requestBody =  RequestBody.create(MultipartBody.FORM, mFile);
                final MultipartBody.Part body =
                        MultipartBody.Part.createFormData("file", mFile.getName(), requestBody);
                call = service.upload(url, body);
                break;
            default:
                break;
        }
        if(call != null) {
            call.enqueue(getRequestCallBack());
        }
    }


    public Callback<String> getRequestCallBack() {
        return new RequestCallbacks(mIRequest, mISuccess, mIError, mIFailure, mLoaderStyle);
    }

    public final void get() {
        request(HttpMethod.GET);
    }

    public final void post() {
        if(mRequestBody == null) {
            request(HttpMethod.POST);
        } else {
            if(!PARAMS.isEmpty()) {
                throw new RuntimeException("already has requestbody, params must be null");
            }
            request(HttpMethod.POST_RAW);
        }
    }

    public final void put() {
        if(mRequestBody == null) {
            request(HttpMethod.PUT);
        } else {
            if(!PARAMS.isEmpty()) {
                throw new RuntimeException("already has requestbody, params must be null");
            }
            request(HttpMethod.PUT_RAW);
        }
    }

    public final void delete() {
        request(HttpMethod.DELETE);
    }

    public final void upload() {
        request(HttpMethod.UPLOAD);
    }

    public final void download() {
        new DownloadHandler(url, mIRequest, mDownloadDir, extension,
                name, mISuccess, mIFailure, mIError).handlerDownload();
    }

}
