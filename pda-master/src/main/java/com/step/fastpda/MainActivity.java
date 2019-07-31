package com.step.fastpda;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;

import com.step.pda.app.activity.ProxyActivity;
import com.step.pda.app.delegate.PdaDelagete;
import com.step.pda.ec.launcher.LauncherDelegate;

public class MainActivity extends ProxyActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
    }

    @Override
    public PdaDelagete setRootDelegate() {
        return new LauncherDelegate();
    }
}
