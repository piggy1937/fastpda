package com.step.pda.app.net;

import com.step.pda.app.callback.IError;
import com.step.pda.app.callback.IFailure;
import com.step.pda.app.callback.IRequest;
import com.step.pda.app.callback.ISuccess;

import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by user on 2019-07-30.
 */

public class RestClientBuilder {
    private  String mUrl;
    private  Map<String,Object> mParams;
    private  IRequest mRequest;
    private  ISuccess mSuccess;
    private  IFailure mFailure;
    private  IError mError;
    private  RequestBody mBody;
    RestClientBuilder(){

    }
    public final RestClientBuilder url(String url){
        this.mUrl = url;
        return this;
    }
    public final RestClientBuilder params(Map<String,Object> mParams){
        this.mParams = mParams;
        return this;
    }
    public final RestClientBuilder params(String key,String value){
       if(mParams==null){
           mParams = new WeakHashMap<>();
       }
        mParams.put(key,value);
        return this;
    }
    public final RestClientBuilder raw(String raw){
        this.mBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"),raw);
        return this;
    }
    public final RestClientBuilder onRequest(IRequest iRequest){
        this.mRequest =iRequest;
        return this;
    }
    public final RestClientBuilder success(ISuccess iSuccess){
        this.mSuccess = iSuccess;
        return this;
    }
    public final RestClientBuilder failure(IFailure iFailure){
        this.mFailure = iFailure;
        return this;
    }
    public final RestClientBuilder error(IError iError){
        this.mError = iError;
        return this;
    }
    public final RestClient build(){
        return new RestClient(mUrl,mParams,mRequest,mSuccess,mFailure,mError,mBody);
    }
}
