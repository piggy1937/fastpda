package com.step.pda.app;

import android.os.Handler;

import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Interceptor;

/**
 * Created by user on 2019-07-30.
 */

public class Configurator {
    public enum  ConfigType{
        API_HOST,
        APPLICATION_CONTEXT,
        CONFIG_READY,
        ICON,
        HANDLER,
        INTERCEPTOR,
        BARCODE_READER,
        BARCODE_READER_ATTACH
    }
    private List<IconFontDescriptor> icons = new ArrayList<>();
    private static final HashMap<String,Object> PDA_CONFIGS = new HashMap<>();
    private static  final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();
    private static final Handler HANDLER = new Handler();
    private static Configurator instance;


     private Configurator(){

         PDA_CONFIGS.put(ConfigType.CONFIG_READY.name(),false);
         PDA_CONFIGS.put(ConfigType.HANDLER.name(), HANDLER);
     }

     public static Configurator getInstance(){
         if(instance==null) {
             synchronized(Configurator.class){
                 if(instance==null) {
                     instance = new Configurator();
                 }
             }
         }
         return instance;
     }

     public final void configure(){

         initIcons();
         PDA_CONFIGS.put(ConfigType.CONFIG_READY.name(),true);
     }

    public static HashMap<String, Object> getPdaConfigs() {
        return PDA_CONFIGS;
    }

    public final  Configurator withApiHost(String host){
        PDA_CONFIGS.put(ConfigType.API_HOST.name(),host);
        return this;
    }
    public final  Configurator withIcon(IconFontDescriptor iconFontDescriptor){
        icons.add(iconFontDescriptor);
        return this;
    }

    private void initIcons(){
        if(icons.size()>0){
            final Iconify.IconifyInitializer initializer = Iconify.with(icons.get(0));
             for(int i=1;i<icons.size();i++){
                 initializer.with(icons.get(i));
             }
        }
    }
    public Configurator withInterceptor(Interceptor interceptor){
        this.INTERCEPTORS.add(interceptor);
        PDA_CONFIGS.put(ConfigType.INTERCEPTOR.name(),INTERCEPTORS);
        return this;
    }
    public Configurator withInterceptors(List<Interceptor> interceptors){
        this.INTERCEPTORS.addAll(interceptors);
        PDA_CONFIGS.put(ConfigType.INTERCEPTOR.name(),INTERCEPTORS);
        return this;
    }
    private  void checkConfiguration(){
        final boolean isReady = (boolean) PDA_CONFIGS.get(ConfigType.CONFIG_READY.name());
        if(!isReady){
            throw new RuntimeException("configuration is not ready,call configure");
        }
    }
   @SuppressWarnings("unchecked")
    final <T> T getConfiguration(Enum<ConfigType> key){
        checkConfiguration();
        return (T) PDA_CONFIGS.get(key.name());
    }

}
