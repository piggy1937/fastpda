package com.step.pda.app;

import android.content.Context;
import android.os.Handler;

import java.util.HashMap;

/**
 * Created by user on 2019-07-30.
 */

public final class  Pda {


    public static Configurator init(Context context){
        getConfigurations().put(Configurator.ConfigType.APPLICATION_CONTEXT.name(),context.getApplicationContext());
        return Configurator.getInstance();
    }
    public static HashMap<String,Object> getConfigurations(){
        return  Configurator.getInstance().getPdaConfigs();
    }
    public static <T> T getConfiguration(Object key) {
        return (T) getConfigurations().get(key);
    }
    public static Context getApplicationContext(){
        return (Context) Configurator.getPdaConfigs().get(Configurator.ConfigType.APPLICATION_CONTEXT.name());
    }
    public static Handler getHandler() {
        return getConfiguration(Configurator.ConfigType.HANDLER.name());
    }
}
