package com.step.fastpda;

import android.app.Application;

import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.step.pda.app.Pda;

/**
 * Created by user on 2019-07-30.
 */

public class PdaApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Pda.init(this)
                .withApiHost("http://127.0.0.1")
                .withIcon(new FontAwesomeModule())
                .configure();

    }
}
