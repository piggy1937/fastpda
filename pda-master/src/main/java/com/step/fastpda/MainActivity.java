package com.step.fastpda;

import com.step.fastpda.delegate.MainDelegate;
import com.step.pda.app.activity.ProxyActivity;
import com.step.pda.app.delegate.PdaDelagete;

public class MainActivity extends ProxyActivity {


    @Override
    public PdaDelagete setRootDelegate() {
        return new MainDelegate();
    }
}
