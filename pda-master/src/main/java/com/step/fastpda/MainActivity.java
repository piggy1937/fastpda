package com.step.fastpda;

import com.step.pda.app.activity.ProxyActivity;
import com.step.pda.app.delegate.PdaDelagete;
import com.step.pda.ec.launcher.LauncherScrollDelegate;

public class MainActivity extends ProxyActivity {


    @Override
    public PdaDelagete setRootDelegate() {
        return new LauncherScrollDelegate();
    }
}
