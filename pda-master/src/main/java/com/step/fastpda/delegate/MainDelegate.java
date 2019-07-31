package com.step.fastpda.delegate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.step.fastpda.R;
import com.step.pda.app.callback.IError;
import com.step.pda.app.callback.IFailure;
import com.step.pda.app.callback.ISuccess;
import com.step.pda.app.delegate.PdaDelagete;
import com.step.pda.app.net.RestClient;

/**
 * Created by user on 2019-07-30.
 */

public class MainDelegate extends PdaDelagete {
    @Override
    public Object setLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstance, View rootViw) {
        testRestClient();
    }

    private void testRestClient() {
        RestClient.builder().url("https://news.baidu.com/")
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Toast.makeText(getContext(),response,Toast.LENGTH_SHORT).show();
                    }
                }).failure(new IFailure() {
            @Override
            public void onFailure() {
                Toast.makeText(getContext(),"faliure",Toast.LENGTH_SHORT).show();
            }
        }).error(new IError() {
            @Override
            public void onError(int code, String message) {
                Toast.makeText(getContext(),"error",Toast.LENGTH_SHORT).show();
            }
        }).build().get();
    }
}
