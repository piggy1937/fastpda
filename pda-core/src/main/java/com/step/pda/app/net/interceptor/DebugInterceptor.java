package com.step.pda.app.net.interceptor;

import android.support.annotation.RawRes;

import com.step.pda.app.util.file.FileUtil;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by user on 2019-07-31.
 */

public class DebugInterceptor  extends  BaseInterceptor{
    private final  String DEBUG_URL;
    private final int DEBUG_RAW_ID;

    public DebugInterceptor(String DEBUG_URL, int DEBUG_RAW_ID) {
        this.DEBUG_URL = DEBUG_URL;
        this.DEBUG_RAW_ID = DEBUG_RAW_ID;
    }

    private Response getResponse(Chain chain,String json){
        return new Response.Builder()
                .code(200)
                .addHeader("Content-Type","application/json")
                .body(ResponseBody.create(MediaType.parse("application/json"),json))
                .message("SUCCESS")
                .request(chain.request())
                .protocol(Protocol.HTTP_1_1)
                .build();
    }
  private Response debugResponse(Chain chain,@RawRes int rawId){
        final String json = FileUtil.getRawFile(rawId);
        return getResponse(chain,json);
  }
    @Override
    public Response intercept(Chain chain) throws IOException {

       final String url = chain.request().url().toString();
       if(url.contains(DEBUG_URL)){
           return debugResponse(chain,DEBUG_RAW_ID);
       }

        return chain.proceed(chain.request());
    }
}
