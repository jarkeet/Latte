package com.cmcc.hy.latte.util.timer;

import java.util.TimerTask;

/**
 * Created by jeff on 2018/5/16.
 */

public class BaseTimerTask extends TimerTask{

    private ITimerListener mITimerListener = null;


    public BaseTimerTask(ITimerListener ITimerListener) {
        mITimerListener = ITimerListener;
    }

    @Override
    public void run() {
        if(mITimerListener != null) {
            mITimerListener.onTimer();
        }
    }
}
