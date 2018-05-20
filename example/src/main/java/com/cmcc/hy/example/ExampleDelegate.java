package com.cmcc.hy.example;

import android.os.Bundle;
import android.view.View;

import com.cmcc.hy.latte.delegates.LatteDelegate;

/**
 * Created by jeff on 2018/5/14.
 */

public class ExampleDelegate extends LatteDelegate {

    @Override
    protected Object setLayout() {
        return R.layout.delegate_fragment;
    }

    @Override
    protected void onBindView(Bundle savedInstanceState, View rootView) {

    }
}
