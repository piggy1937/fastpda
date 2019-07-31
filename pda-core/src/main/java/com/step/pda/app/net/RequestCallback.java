package com.step.pda.app.net;

import android.os.Handler;

import com.step.pda.app.callback.IError;
import com.step.pda.app.callback.IFailure;
import com.step.pda.app.callback.IRequest;
import com.step.pda.app.callback.ISuccess;
import com.step.pda.app.ui.LoadingStyle;
import com.step.pda.app.ui.PdaLoader;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by user on 2019-07-31.
 */

public class RequestCallback  implements Callback<String>{
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final LoadingStyle LOADING_STYLE;
    private static  final Handler HANDLER = new Handler() ;
    public RequestCallback(IRequest request, ISuccess success, IFailure failure, IError error,LoadingStyle loadingStyle) {
        this.REQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.LOADING_STYLE = loadingStyle;
    }


    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        if(response.isSuccessful()){
            if(call.isExecuted()){
                if(SUCCESS!=null){
                    SUCCESS.onSuccess(response.body());
                }
            }
        }else{
            if(ERROR!=null){
                ERROR.onError(response.code(),response.message());
            }
        }
        stopLoading();
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
         if(FAILURE!=null){
             FAILURE.onFailure();
         }
         if(REQUEST!=null){
             REQUEST.onRequestEnd();
         }
        stopLoading();
    }
    private void stopLoading(){
        if(LOADING_STYLE!=null){
            HANDLER.postDelayed(new Runnable() {
                @Override
                public void run() {
                    PdaLoader.stopLoading();
                }
            },1000);
        }
    }
}
