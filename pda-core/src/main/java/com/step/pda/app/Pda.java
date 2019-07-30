package com.step.pda.app;

import android.content.Context;

import java.util.WeakHashMap;

/**
 * Created by user on 2019-07-30.
 */

public final class  Pda {


    public static Configurator init(Context context){
        getConfigurations().put(Configurator.ConfigType.APPLICATION_CONTEXT.name(),context.getApplicationContext());
        return Configurator.getInstance();
    }
    private static WeakHashMap<String,Object> getConfigurations(){
        return  Configurator.getInstance().getPdaConfigs();
    }


}
