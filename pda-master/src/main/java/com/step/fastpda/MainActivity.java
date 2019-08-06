package com.step.fastpda;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;

import com.step.pda.app.activity.ProxyActivity;
import com.step.pda.app.delegate.PdaDelegate;
import com.step.pda.ec.main.EcBottomDelegate;

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
    public PdaDelegate setRootDelegate() {
        return new EcBottomDelegate();
    }
}
