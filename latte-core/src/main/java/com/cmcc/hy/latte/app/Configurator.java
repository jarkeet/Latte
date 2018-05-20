package com.cmcc.hy.latte.app;

import android.app.Activity;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.blankj.utilcode.util.Utils;
import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Interceptor;


/**
 * Created by jeff on 2018/5/11.
 */

public class Configurator {

    private static final HashMap<Object, Object> LATTTE_CONFIGS = new HashMap<>();
    private static final Handler HANDLER = new Handler();
    private static final ArrayList<IconFontDescriptor> ICONS = new ArrayList<>();
    private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();

    private Configurator() {
        LATTTE_CONFIGS.put(ConfigKeys.CONFIG_READY, false);
        LATTTE_CONFIGS.put(ConfigKeys.HANDLER, HANDLER);
    }

    static Configurator getInstance() {
        return Holder.INSTANCE;
    }

    final HashMap<Object, Object> getLatteConfigs() {
        return LATTTE_CONFIGS;
    }

    private static class Holder {
        private static final Configurator INSTANCE = new Configurator();
    }

    public final void configure() {
        initIcons();
        LATTTE_CONFIGS.put(ConfigKeys.CONFIG_READY, true);
        Utils.init(Latte.getApplicationContext());
    }

    public final Configurator withApiHost(String host) {
        LATTTE_CONFIGS.put(ConfigKeys.API_HOST, host);
        return this;
    }

    public final Configurator withLoaderDelay(long delayed) {
        LATTTE_CONFIGS.put(ConfigKeys.LOADER_DELAY, delayed);
        return this;
    }

    private void initIcons() {
        if (ICONS.size() > 0) {
            final Iconify.IconifyInitializer initializer = Iconify.with(ICONS.get(0));
            for (int i = 1; i < ICONS.size(); i++) {
                initializer.with(ICONS.get(i));
            }
        }
    }


    public final Configurator withIcon(IconFontDescriptor descriptor) {
        ICONS.add(descriptor);
        return this;
    }

    public final Configurator withInterceptor(Interceptor interceptor) {
        INTERCEPTORS.add(interceptor);
        LATTTE_CONFIGS.put(ConfigKeys.INTERCEPTORS, INTERCEPTORS);
        return this;
    }

    public final Configurator withInterceptors(ArrayList<Interceptor> interceptors) {
        INTERCEPTORS.addAll(interceptors);
        LATTTE_CONFIGS.put(ConfigKeys.INTERCEPTORS, INTERCEPTORS);
        return this;
    }

    public final Configurator withWeChatAppId(String appId) {
        LATTTE_CONFIGS.put(ConfigKeys.WE_CHAT_APP_ID, appId);
        return this;
    }

    public final Configurator withWeChatAppSecret(String appSecret) {
        LATTTE_CONFIGS.put(ConfigKeys.WE_CHAT_APP_SECRET, appSecret);
        return this;
    }

    public final Configurator withActivity(Activity activity) {
        LATTTE_CONFIGS.put(ConfigKeys.ACTIVITY, activity);
        return this;
    }

    public Configurator withJavascriptInterface(@Nullable String name) {
        LATTTE_CONFIGS.put(ConfigKeys.JAVASCRIPT_INTERFACE, name);
        return this;
    }

    private void checkConfigration() {
        final boolean isReady = (boolean) LATTTE_CONFIGS.get(ConfigKeys.CONFIG_READY);
        if (!isReady) {
            throw new RuntimeException("Configuration is not ready, call configure!!");
        }
    }

    final <T> T getConfigration(Object key) {
        checkConfigration();
        final Object value = LATTTE_CONFIGS.get(key);
        if (value == null) {
            throw new NullPointerException(key.toString() + " is NULL");

        }
        return (T) LATTTE_CONFIGS.get(key);
    }

}
