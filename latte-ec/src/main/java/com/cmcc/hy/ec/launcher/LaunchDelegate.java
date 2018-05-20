package com.cmcc.hy.ec.launcher;

import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.cmcc.hy.ec.R;
import com.cmcc.hy.ec.R2;
import com.cmcc.hy.latte.delegates.LatteDelegate;
import com.cmcc.hy.latte.util.timer.BaseTimerTask;
import com.cmcc.hy.latte.util.timer.ITimerListener;

import java.text.MessageFormat;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by jeff on 2018/5/16.
 */

public class LaunchDelegate extends LatteDelegate implements ITimerListener {

    @BindView(R2.id.tv_launcher_timer)
    AppCompatTextView mTvTimer;

    private Timer mTimer;
    private int mCount = 5;

    @OnClick(R2.id.tv_launcher_timer)
    void onClickTimerView() {

    }

    private void initTimer() {
        mTimer = new Timer();
        TimerTask timerTask = new BaseTimerTask(this);
        mTimer.schedule(timerTask, 0, 1000);
    }


    @Override
    protected Object setLayout() {
        return R.layout.delegate_lancher;
    }

    @Override
    protected void onBindView(Bundle savedInstanceState, View rootView) {
        initTimer();
    }

    @Override
    public void onTimer() {
        getProxyActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mTvTimer != null) {
                    mTvTimer.setText(MessageFormat.format("跳过\n {0}", mCount));
                    mCount--;
                    if (mCount < 0 && mTimer != null) {
                        mTimer.cancel();
                        mTimer = null;
                    }
                }
            }
        });
    }
}
