package com.step.pda.app.net;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by user on 2018-08-02.
 */

public class RetrofitHelper {


    OkHttpClient client = new OkHttpClient();
    //GsonConverterFactory factory = GsonConverterFactory.create(new GsonBuilder().setLenient().create());

    private static RetrofitHelper instance = null;
    private Retrofit mRetrofit = null;
    public static RetrofitHelper getInstance(){
        if (instance == null){
            instance = new RetrofitHelper();
        }
        return instance;
    }
    private RetrofitHelper(){
        init();
    }

    private void init() {
        resetApp();
    }

    private void resetApp() {

//        mRetrofit = new Retrofit.Builder()
//                .baseUrl("http://222.71.220.31:5000/")
//                .client(client)
//                .addConverterFactory(factory)
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .build();
    }
    public Object getServer(Class clazz){
        return (Object) mRetrofit.create(clazz);
    }
}


