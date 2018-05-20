package com.cmcc.hy.latte.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.ContentFrameLayout;

import com.cmcc.hy.latte.R;
import com.cmcc.hy.latte.delegates.LatteDelegate;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by jeff on 2018/5/11.
 */

public abstract class ProxyActivity extends SupportActivity {


    public abstract LatteDelegate setRootDelegate();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContainer(savedInstanceState);

    }

    private void initContainer(Bundle savedInstanceState) {
        final ContentFrameLayout container = new ContentFrameLayout(this);
        container.setId(R.id.delegate_container_id);
        setContentView(container);
        if(savedInstanceState == null) {
            loadRootFragment(R.id.delegate_container_id, setRootDelegate());
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
        System.runFinalization();
    }
}
