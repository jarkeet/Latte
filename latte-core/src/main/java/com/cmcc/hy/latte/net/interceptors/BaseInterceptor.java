package com.cmcc.hy.latte.net.interceptors;

import java.util.LinkedHashMap;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;

/**
 * Created by jeff on 2018/5/14.
 */

public abstract class BaseInterceptor implements Interceptor{

    protected LinkedHashMap<String, String> getUrlParameters(Chain chain) {
        final HttpUrl url = chain.request().url();
        int size = url.querySize();
        final LinkedHashMap<String, String> params = new LinkedHashMap<>();
        for(int i = 0; i < size; ++i) {
            params.put(url.queryParameterName(i), url.queryParameterValue(i));
        }
        return params;
    }

    protected String getUrlParameters(Chain chain, String key) {
        final Request request = chain.request();
        return request.url().queryParameter(key);
    }

    protected LinkedHashMap<String, String> getBodyParamters(Chain chain) {
        final FormBody formBody = (FormBody) chain.request().body();
        final LinkedHashMap<String, String> params = new LinkedHashMap<>();
        int size = 0;
        if(formBody != null) {
            size = formBody.size();
        }
        for(int i = 0; i < size; i++) {
            params.put(formBody.name(i), formBody.value(i));
        }
        return params;
    }

    protected String getBodyParamters(Chain chain, String key) {
        final Request request = chain.request();
        return getBodyParamters(chain).get(key);

    }


}
