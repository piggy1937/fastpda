package com.step.pda.ec.presenter;

import android.content.Context;
import android.os.Handler;

import com.alibaba.fastjson.JSONObject;
import com.step.pda.app.net.rx.RxRestClient;
import com.step.pda.app.ui.PdaLoader;
import com.step.pda.ec.contract.IMiniPackScanContract;
import com.step.pda.ec.database.PackageInfo;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author zhushubin
 * @date 2019-08-15.
 * GitHub：
 * email： 604580436@qq.com
 * description：  小包标签新增presenter
 */
public class MiniPackScanPresenter extends IMiniPackScanContract.Presenter  {
    public MiniPackScanPresenter(IMiniPackScanContract.View view, Context context) {
        super(view, context);
    }
    private static  final Handler HANDLER = new Handler() ;


    private void stopLoading(){
        HANDLER.postDelayed(new Runnable() {
            @Override
            public void run() {
                PdaLoader.stopLoading();
            }
        },1000);
    }

    @Override
    public void addMiniPackPrintTask(final PackageInfo packageInfo) {
        Map<String,Object> params = new HashMap<>();
        params.put("barcode",packageInfo.getSn());
        params.put("txtSL",packageInfo.getQuantity());
        params.put("creater",packageInfo.getCreator());
        RxRestClient.builder().url("/data/parsebarcode")
                .params("", "")
                .raw(JSONObject.toJSONString(params))
                .loader(mContext)
                .build().post().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String data) {
                        JSONObject jObj = JSONObject.parseObject(data);
                        int errcode = jObj.getIntValue("errCode");
                        if(errcode==1){
                            //成功
                            mView.onSuccess(packageInfo);
                        }else{
                            String errmsg =  jObj.getString("errMsg");

                            mView.onError(packageInfo,errmsg);
                        }


                    }

                    @Override
                    public void onError(Throwable e) {
                        stopLoading();
                        mView.onError(packageInfo,"");
                    }

                    @Override
                    public void onComplete() {
                        stopLoading();
                    }
                });
    }
}
