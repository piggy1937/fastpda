package com.step.pda.app;

import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

/**
 * Created by user on 2019-07-30.
 */

public class Configurator {
    public enum  ConfigType{
        API_HOST,
        APPLICATION_CONTEXT,
        CONFIG_READY,
        ICON
    }
    private List<IconFontDescriptor> icons = new ArrayList<>();
    private static final WeakHashMap<String,Object> PDA_CONFIGS = new WeakHashMap<>();
     private static Configurator instance;
     private Configurator(){
         initIcons();
         PDA_CONFIGS.put(ConfigType.CONFIG_READY.name(),false);
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
         PDA_CONFIGS.put(ConfigType.CONFIG_READY.name(),true);
     }

    public static WeakHashMap<String, Object> getPdaConfigs() {
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
