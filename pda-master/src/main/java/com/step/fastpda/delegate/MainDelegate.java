package com.step.fastpda.delegate;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.step.fastpda.R;
import com.step.pda.app.delegate.PdaDelegate;
import com.step.pda.app.net.rx.RxRestClient;
import com.step.pda.app.ui.PdaLoader;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by user on 2019-07-30.
 */

public class MainDelegate extends PdaDelegate {
    private static  final Handler HANDLER = new Handler() ;
    @Override
    public Object setLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstance, View rootViw) {
        testRestClient();
    }

    private void testRestClient() {
        RxRestClient.builder().url("http://127.0.0.1/index")
                .params("", "")
                .loader(getContext())
                .build().get().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        Toast.makeText(getContext(),s,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        stopLoading();
                        Toast.makeText(getContext(),"eeeeee",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        stopLoading();
                        Toast.makeText(getContext(),"eeeeee",Toast.LENGTH_SHORT).show();
                    }
                });
    }
            private void stopLoading(){
                    HANDLER.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            PdaLoader.stopLoading();
                        }
                    },1000);
                }

    @Override
    public void post(Runnable runnable) {

    }
}
