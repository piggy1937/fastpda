package com.step.fastpda;

import android.app.Application;

import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.step.pda.app.Pda;
import com.step.pda.app.net.interceptor.DebugInterceptor;
import com.step.pda.ec.database.DatabaseManager;

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
                .withInterceptor(new DebugInterceptor("LoginUser",R.raw.login_user))
                .withInterceptor(new DebugInterceptor("bigpack",R.raw.big_pack))
                .withIcon(new FontAwesomeModule())
                .configure();

        DatabaseManager.getInstance().init(this);
    }
}
