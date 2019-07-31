package com.step.fastpda;

import android.app.Application;

import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.step.pda.app.Pda;
import com.step.pda.app.net.interceptor.DebugInterceptor;

/**
 * Created by user on 2019-07-30.
 */

public class PdaApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Pda.init(this)
                .withApiHost("http://127.0.0.1")
                .withInterceptor(new DebugInterceptor("index",R.raw.test))
                .withIcon(new FontAwesomeModule())
                .configure();

    }
}
