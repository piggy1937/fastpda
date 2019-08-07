package com.step.pda.ec.launcher;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.step.pda.app.AccountManager;
import com.step.pda.app.IUserChecker;
import com.step.pda.app.delegate.PdaDelegate;
import com.step.pda.app.ui.launcher.ILauncherListener;
import com.step.pda.app.ui.launcher.LauncherHolderCreator;
import com.step.pda.app.ui.launcher.ScrollLauncherTag;
import com.step.pda.app.util.storage.PreferenceUtils;
import com.step.pda.ec.R;

import java.util.ArrayList;

/**
 * Created by user on 2019-07-31.
 */

public class LauncherScrollDelegate extends PdaDelegate
        implements OnItemClickListener{
    private ConvenientBanner<Integer> mConvenientBanner=null;
    private ILauncherListener mILauncherListener = null;
    private static final ArrayList<Integer>  INTEGERS = new ArrayList<>();
    private void initBanner(){
        INTEGERS.add(R.mipmap.launcher_01);
        INTEGERS.add(R.mipmap.launcher_02);
        INTEGERS.add(R.mipmap.launcher_03);
        INTEGERS.add(R.mipmap.launcher_04);
        INTEGERS.add(R.mipmap.launcher_05);
        mConvenientBanner
                .setPages(new LauncherHolderCreator(),INTEGERS)
                .setPageIndicator(new int[]{R.drawable.dot_normal,R.drawable.dot_focus})
                 .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(this)
                .setCanLoop(false);
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ILauncherListener) {
            mILauncherListener = (ILauncherListener) activity;
        }
    }
    @Override
    public Object setLayout() {
        mConvenientBanner = new ConvenientBanner<Integer>(getContext());
        return mConvenientBanner;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstance, View rootViw) {
        initBanner();
    }

    @Override
    public void onItemClick(int position) {
        //如果点击点是最后一个
       if(position == INTEGERS.size()-1){
           PreferenceUtils.setAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCH_APP.name(),true);
           //检测用户是否已登录
           AccountManager.checkAccount(new IUserChecker() {
               @Override
               public void onSignIn() {
                   if (mILauncherListener != null) {
                       mILauncherListener.onLauncherFinish(ILauncherListener.LaunchFinishTag.SIGNED);
                   }
               }

               @Override
               public void onNotSignIn() {
                   if (mILauncherListener != null) {
                       mILauncherListener.onLauncherFinish(ILauncherListener.LaunchFinishTag.NOT_SIGNED);
                   }
               }
           });
       }
    }


    @Override
    public void post(Runnable runnable) {

    }
}
