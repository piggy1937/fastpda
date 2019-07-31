package com.step.pda.app.util.timer;

import java.util.TimerTask;

/**
 * Created by user on 2019-07-31.
 */

public class BaseTimerTask extends TimerTask{
    private ITimerListner mITimerListner = null;

    public BaseTimerTask(ITimerListner mITimerListner) {
        this.mITimerListner = mITimerListner;
    }

    @Override
    public void run() {
        if(mITimerListner!=null){
            mITimerListner.onTimer();;
        }
    }
}
