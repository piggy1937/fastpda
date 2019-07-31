package com.step.pda.app.ui;

import android.content.Context;

import com.wang.avi.AVLoadingIndicatorView;
import com.wang.avi.Indicator;

import java.util.WeakHashMap;

/**
 * Created by user on 2019-07-31.
 */

public  final class LoaderCreator {
    private static final WeakHashMap<String,Indicator> loading_map = new WeakHashMap<>();
    public static AVLoadingIndicatorView create( Context context,String type){
        final AVLoadingIndicatorView avLoadingIndicatorView = new AVLoadingIndicatorView(context);
        if(loading_map.get(type) == null){
            final  Indicator indicator = getIndicator(type);
            loading_map.put(type,indicator);
        }
         avLoadingIndicatorView.setIndicator(loading_map.get(type));
         return avLoadingIndicatorView;
    }
    private  static  Indicator getIndicator(String name){
        if(name ==null || name.isEmpty()){
            return null;
        }
        StringBuilder drawableClassName = new StringBuilder();
        if(!name.contains(".")){
           final  String defaultPackageName = AVLoadingIndicatorView.class.getPackage().getName();
            drawableClassName.
                    append(defaultPackageName)
                    .append(".indicators")
                    .append(".");
        }

        drawableClassName.append(name);
        try {
            final  Class<?> drawbleClass = Class.forName(drawableClassName.toString());
            return (Indicator) drawbleClass.newInstance();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        } catch (InstantiationException e) {
            e.printStackTrace();
            return null;
        }
    }
}
