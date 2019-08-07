package com.step.fastpda;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.widget.Toast;

import com.step.pda.app.activity.ProxyActivity;
import com.step.pda.app.delegate.PdaDelegate;
import com.step.pda.app.ui.launcher.ILauncherListener;
import com.step.pda.ec.launcher.LauncherDelegate;
import com.step.pda.ec.login.ISiginListener;
import com.step.pda.ec.login.SignInDelegate;
import com.step.pda.ec.main.EcBottomDelegate;

import qiu.niorgai.StatusBarCompat;

public class MainActivity extends ProxyActivity implements ISiginListener,ILauncherListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        StatusBarCompat.translucentStatusBar(this, true);
    }

    @Override
    public PdaDelegate setRootDelegate() {
        return new LauncherDelegate();
    }

    @Override
    public void onSignInSuccess() {
        Toast.makeText(this, "登录成功", Toast.LENGTH_LONG).show();
        getSupportDelegate().startWithPop(new EcBottomDelegate());
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
