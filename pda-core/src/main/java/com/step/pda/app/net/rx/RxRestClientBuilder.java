package com.step.pda.app.net.rx;

import android.content.Context;

import com.step.pda.app.ui.LoadingStyle;

import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by user on 2019-07-30.
 */

public class RxRestClientBuilder {
    private  String mUrl;
    private  Map<String,Object> mParams;

    private  RequestBody mBody;
    private  LoadingStyle mLoadingStyle;
    private  Context mContext;
    RxRestClientBuilder(){

    }
    public final RxRestClientBuilder url(String url){
        this.mUrl = url;
        return this;
    }
    public final RxRestClientBuilder params(Map<String,Object> mParams){
        this.mParams = mParams;
        return this;
    }
    public final RxRestClientBuilder params(String key, String value){
       if(mParams==null){
           mParams = new WeakHashMap<>();
       }
        mParams.put(key,value);
        return this;
    }
    public final RxRestClientBuilder raw(String raw){
        this.mBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"),raw);
        return this;
    }

    public final RxRestClientBuilder loader(Context context, LoadingStyle loadingStyle){
        this.mContext = context;
        this.mLoadingStyle = loadingStyle;
        return this;
    }
    public final RxRestClientBuilder loader(Context context){
        this.mContext = context;
        this.mLoadingStyle = LoadingStyle.BallClipRotateIndicator;
        return this;
    }
    public final RxRestClient build(){
        return new RxRestClient(mUrl,mParams,mBody,mContext,mLoadingStyle);
    }

}
