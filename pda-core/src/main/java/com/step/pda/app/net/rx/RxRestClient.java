package com.step.pda.app.net.rx;

import android.content.Context;

import com.step.pda.app.net.HttpMethod;
import com.step.pda.app.net.RestCreatorFactory;
import com.step.pda.app.ui.LoadingStyle;
import com.step.pda.app.ui.PdaLoader;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * Created by user on 2019-07-30.
 */

public class RxRestClient {
    private final String URL;
    private final Map<String, Object> PARAMS;
    private final RequestBody BODY;
    private final LoadingStyle LOADING_STYLE;
    private final Context context;
    public RxRestClient(String URL, Map<String, Object> PARAMS,
                      RequestBody BODY,
                      Context context,
                      LoadingStyle loadingStyle
                      ) {
        this.URL = URL;
        this.PARAMS = PARAMS;

        this.BODY = BODY;
        this.context = context;
        this.LOADING_STYLE = loadingStyle;
    }

    public static RxRestClientBuilder builder() {
        return new RxRestClientBuilder();
    }

    private Observable<String> request(HttpMethod method) {
        final RxRestService restService = RestCreatorFactory.INSTANCE.getInstance().getRxRestService();
        Observable<String> observable = null;

        if(LOADING_STYLE !=null){
            PdaLoader.showLoading(context);
        }
        switch (method) {
            case GET:
                observable = restService.get(URL,PARAMS);
                break;
            case POST:
                observable = restService.post(URL,BODY);
                break;
            case PUT:
                observable = restService.put(URL,PARAMS);
                break;
            case DELETE:
                observable = restService.delete(URL,PARAMS);
                break;
            default:
                break;
        }
       return observable;

    }

    public final  Observable<String> get(){
        return    request(HttpMethod.GET);
    }
    public final  Observable<String> post()
    {
        return request(HttpMethod.POST);
    }
    public final  Observable<String> put(){
        return request(HttpMethod.PUT);
    }
    public final  Observable<String> delete(){
        return request(HttpMethod.DELETE);
    }

}