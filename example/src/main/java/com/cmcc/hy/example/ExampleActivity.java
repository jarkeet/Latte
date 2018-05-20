package com.cmcc.hy.example;

import com.cmcc.hy.ec.launcher.LauncherScrollDelegate;
import com.cmcc.hy.latte.activity.ProxyActivity;
import com.cmcc.hy.latte.delegates.LatteDelegate;

/**
 * Created by jeff on 2018/5/16.
 */

public class ExampleActivity extends ProxyActivity {

    @Override
    public LatteDelegate setRootDelegate() {
        return new LauncherScrollDelegate();
    }
}
