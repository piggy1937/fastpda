package com.step.pda.app.net;

import android.content.Context;

import com.step.pda.app.callback.IError;
import com.step.pda.app.callback.IFailure;
import com.step.pda.app.callback.IRequest;
import com.step.pda.app.callback.ISuccess;
import com.step.pda.app.ui.LoadingStyle;
import com.step.pda.app.ui.PdaLoader;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by user on 2019-07-30.
 */

public class RestClient {
    private final String URL;
    private final Map<String, Object> PARAMS;
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final RequestBody BODY;
    private final LoadingStyle LOADING_STYLE;
    private final Context context;
    public RestClient(String URL, Map<String, Object> PARAMS,
                      IRequest REQUEST,
                      ISuccess SUCCESS,
                      IFailure FAILURE,
                      IError ERROR,
                      RequestBody BODY,
                      Context context,
                      LoadingStyle loadingStyle
                      ) {
        this.URL = URL;
        this.PARAMS = PARAMS;
        this.REQUEST = REQUEST;
        this.SUCCESS = SUCCESS;
        this.FAILURE = FAILURE;
        this.ERROR = ERROR;
        this.BODY = BODY;
        this.context = context;
        this.LOADING_STYLE = loadingStyle;
    }

    public static RestClientBuilder builder() {
        return new RestClientBuilder();
    }

    private void request(HttpMethod method) {
        final RestService restService = RestCreatorFactory.INSTANCE.getInstance().getRestService();
        Call<String> call = null;
        if (REQUEST != null) {
            REQUEST.onRequestStart();
        }
        if(LOADING_STYLE !=null){
            PdaLoader.showLoading(context);
        }
        switch (method) {
            case GET:
                call = restService.get(URL,PARAMS);
                break;
            case POST:
                call = restService.post(URL,PARAMS);
                break;
            case PUT:
                call = restService.put(URL,PARAMS);
                break;
            case DELETE:
                call = restService.delete(URL,PARAMS);
                break;
            default:
                break;
        }
        if(call !=null){
            call.enqueue(getRequestCallback());
        }

    }

        private Callback<String> getRequestCallback(){
            return new RequestCallback(REQUEST,SUCCESS,FAILURE,ERROR,LOADING_STYLE);
        }
    public final  void get(){
            request(HttpMethod.GET);
    }
    public final  void post(){
            request(HttpMethod.POST);
    }
    public final  void put(){
            request(HttpMethod.PUT);
    }
    public final  void delete(){
            request(HttpMethod.DELETE);
    }

}