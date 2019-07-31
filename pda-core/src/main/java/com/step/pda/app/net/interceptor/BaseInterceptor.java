package com.step.pda.app.net.interceptor;

import java.util.LinkedHashMap;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;

/**
 * Created by user on 2019-07-31.
 */

public  abstract class BaseInterceptor implements Interceptor {
    protected LinkedHashMap getUrlParams = new LinkedHashMap();
    protected LinkedHashMap<String,String> getUrlParams(Chain chain){
        LinkedHashMap<String,String> result = new LinkedHashMap<>();
       final HttpUrl url = chain.request().url();
       int length = url.querySize();
        for(int i = 0 ;i<length;i++){
            result.put(url.queryParameterName(i),url.queryParameterValue(i));
        }
        return result;
    }
    protected String getUrlParam(Chain chain,String key){
       final HttpUrl url = chain.request().url();
        return url.queryParameter(key);
    }
    protected LinkedHashMap<String,String> getBodyParams(Chain chain){
        LinkedHashMap<String,String> result = new LinkedHashMap<>();
       final FormBody formBody = (FormBody) chain.request().body();
        int length = formBody.size();
        for(int i = 0 ;i<length;i++){
            result.put(formBody.name(i),formBody.value(i));
        }
        return result;
    }
    protected String getBodyParams(Chain chain,String key){
       final FormBody formBody = (FormBody) chain.request().body();
        return  getBodyParams(chain).get(key);
    }
}
