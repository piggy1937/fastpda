package com.step.pda.ec.launcher;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.step.pda.app.AccountManager;
import com.step.pda.app.IUserChecker;
import com.step.pda.app.delegate.PdaDelegate;
import com.step.pda.app.ui.launcher.ILauncherListener;
import com.step.pda.app.ui.launcher.ScrollLauncherTag;
import com.step.pda.app.util.storage.PreferenceUtils;
import com.step.pda.app.util.timer.BaseTimerTask;
import com.step.pda.app.util.timer.ITimerListner;
import com.step.pda.ec.R;
import com.step.pda.ec.R2;

import java.text.MessageFormat;
import java.util.Timer;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by user on 2019-07-31.
 */

public class LauncherDelegate extends PdaDelegate implements ITimerListner{
    private  int mCount = 6;
    private Timer mTimer=null;
    private ILauncherListener mLauncherListener;
    @BindView(R2.id.tv_launcher_timer)
    AppCompatTextView  mTvTimer;

    @OnClick(R2.id.tv_launcher_timer)
    public void onClickTvLauncherTimer(){
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
            checkIsShowScroller();
        }
    }
    private void  initTimer()
    {
        mTimer = new Timer();
        final BaseTimerTask task = new BaseTimerTask(this);
        mTimer.schedule(task,0,1000);
    }
    @Override
    public Object setLayout() {
        return R.layout.delegate_launcher;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstance, View rootViw) {
       // mTvTimer = rootViw.findViewById(R.id.tv_launcher_timer);
        initTimer();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof ILauncherListener){
            mLauncherListener = (ILauncherListener) activity;
        }
    }

    //判断是否显示启动滚动页
    private  void checkIsShowScroller(){
        if(!PreferenceUtils.getAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCH_APP.name())){
            start(new LauncherScrollDelegate(),SINGLETASK);
        }else{
            //检测用户是否登录了APP
            AccountManager.checkAccount(new IUserChecker() {
                @Override
                public void onSignIn() {
                    //已登录操作
                    if(mLauncherListener!=null){
                        mLauncherListener.onLauncherFinish(ILauncherListener.LaunchFinishTag.SIGNED);
                    }


                }

                @Override
                public void onNotSignIn() {
                    //没有登录操作
                    if(mLauncherListener!=null){
                        mLauncherListener.onLauncherFinish(ILauncherListener.LaunchFinishTag.NOT_SIGNED);
                    }
                }
            });


        }

    }
    @Override
    public void onTimer() {
             getProxyActivity().runOnUiThread(new Runnable() {
                 @Override
                 public void run() {
                     if(mTvTimer!=null){
                         mTvTimer.setText(MessageFormat.format("跳过\n{0}s",mCount));
                         mCount--;
                         if(mCount<0){
                             if(mTimer!=null){
                                 mTimer.cancel();
                                 mTimer=null;
                                 checkIsShowScroller();
                             }
                         }
                     }
                 }
             });
    }


}
