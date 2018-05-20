package com.cmcc.hy.example;

import android.os.Bundle;
import android.util.Log;

import com.cmcc.hy.latte.activity.ProxyActivity;
import com.cmcc.hy.latte.app.Latte;
import com.cmcc.hy.latte.delegates.LatteDelegate;
import com.cmcc.hy.latte.net.RestClient;
import com.cmcc.hy.latte.net.callback.IError;
import com.cmcc.hy.latte.net.callback.IFailure;
import com.cmcc.hy.latte.net.callback.ISuccess;
import com.cmcc.hy.latte.net.interceptors.DebugInterceptor;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

public class MainActivity extends ProxyActivity {

    @Override
    public LatteDelegate setRootDelegate() {
        return new ExampleDelegate();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Latte.init(this)
                .withApiHost("http://www.baidu.com")
                .withIcon(new FontAwesomeModule())
                .withLoaderDelay(1000)
                .withInterceptor(new DebugInterceptor("http://127.0.0.1", R.raw.test))
                .configure();

        RestClient.builder()
                .url("http://127.0.0.1")
                .loader(this)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Log.i("reponse", response);
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {

                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {

                    }
                })
                .build()
                .get();
    }
}
