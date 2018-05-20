package com.cmcc.hy.latte.net.interceptors;

import android.support.annotation.RawRes;

import com.cmcc.hy.latte.util.file.FileUtil;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by jeff on 2018/5/14.
 */

public class DebugInterceptor extends BaseInterceptor {

    private final String mDebugUrl;

    private final int mDebugRawId;

    public DebugInterceptor(String debugUrl, int debugRawId) {
        mDebugUrl = debugUrl;
        mDebugRawId = debugRawId;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        final String url = chain.request().url().toString();
        if(url.contains(mDebugUrl)) {
            return debugResponse(chain, mDebugRawId);
        }
        return chain.proceed(chain.request());
    }

    private Response debugResponse(Chain chain, @RawRes int rawId) {
        String json = FileUtil.getRawFile(rawId);
        return getResponse(chain, json);

    }

    private Response getResponse(Chain chain, String json) {
        return new Response.Builder()
                .code(200)
                .addHeader("Content-Type", "application/json")
                .body(ResponseBody.create(MediaType.parse("application/json"), json))
                .message("OK")
                .request(chain.request())
                .protocol(Protocol.HTTP_1_1)
                .build();

    }


}
