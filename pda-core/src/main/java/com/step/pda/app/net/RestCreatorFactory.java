package com.step.pda.app.net;

import com.step.pda.app.Configurator;
import com.step.pda.app.Pda;
import com.step.pda.app.net.rx.RxRestService;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by user on 2019-07-30.
 */

public enum  RestCreatorFactory {
    INSTANCE;
    private RestCreator instance;

    RestCreatorFactory() {
        this.instance = new RestCreator();
    }

    public RestCreator getInstance() {
        return instance;
    }

 public class RestCreator{
        private Retrofit mRetrofit = null;
        private OkHttpClient okHttpClient = null;
      //  GsonConverterFactory factory = null;
        private static  final  int TIME_OUT = 60;
        public RestCreator(){
            String BaseUrl = (String) Pda.getConfigurations().get(Configurator.ConfigType.API_HOST.name());
            OkHttpClient.Builder okHttpBuilder   =new OkHttpClient.Builder();
            List<Interceptor > interceptors = (List<Interceptor>) Pda.getConfigurations().get(Configurator.ConfigType.INTERCEPTOR.name());
            if(interceptors!=null) {
                for (Interceptor interceptor : interceptors) {
                    okHttpBuilder.addInterceptor(interceptor);
                }
            }
            okHttpClient = okHttpBuilder.connectTimeout(TIME_OUT, TimeUnit.SECONDS).build();



            // factory = GsonConverterFactory.create(new GsonBuilder().setLenient().create());
            mRetrofit = new Retrofit.Builder()
                   .baseUrl(BaseUrl)
                    .client(okHttpClient)
                   .addConverterFactory(ScalarsConverterFactory.create())
                   .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }

       public RestService getRestService(){
            return mRetrofit.create(RestService.class);
       }
       public RxRestService getRxRestService(){
            return mRetrofit.create(RxRestService.class);
       }
    }
}
