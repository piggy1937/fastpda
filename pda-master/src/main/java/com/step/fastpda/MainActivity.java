package com.step.fastpda;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.step.pda.app.activity.ProxyActivity;
import com.step.pda.app.ui.launcher.ILauncherListener;
import com.step.pda.ec.contract.ISignContract;
import com.step.pda.ec.launcher.LauncherDelegate;
import com.step.pda.ec.login.SignInDelegate;
import com.step.pda.ec.main.EcBottomDelegate;

import qiu.niorgai.StatusBarCompat;

public class MainActivity extends ProxyActivity implements ISignContract.View,ILauncherListener {
    //去重fragement
    private Fragment[] mFragments = new Fragment[4];
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        StatusBarCompat.translucentStatusBar(this, true);

    }

    /***
     * 初始化将要加载的fragement
     * @param savedInstanceState 保存
     */
    @Override
    protected void initContainer(@Nullable Bundle savedInstanceState) {
        final FrameLayout container = new FrameLayout(this);
        container.setId(R.id.delegate_container);
        setContentView(container);
        if(savedInstanceState ==null){
            getSupportDelegate().loadRootFragment(R.id.delegate_container, new LauncherDelegate());
        }else{





        }
    }



    @Override
    public void onSignInSuccess() {
        Toast.makeText(this, "登录成功", Toast.LENGTH_LONG).show();
        getSupportDelegate().startWithPop(new EcBottomDelegate());
    }

    @Override
    public void onSignInError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSignUpSuccess() {
        Toast.makeText(this, "注册成功", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLauncherFinish(LaunchFinishTag tag) {
        switch (tag) {
            case SIGNED:
                getSupportDelegate().startWithPop(new EcBottomDelegate());
                break;
            case NOT_SIGNED:
                getSupportDelegate().startWithPop(new SignInDelegate());
                break;
            default:
                break;
        }
    }


}
