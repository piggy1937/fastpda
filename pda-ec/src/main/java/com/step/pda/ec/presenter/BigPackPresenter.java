package com.step.pda.ec.presenter;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.step.pda.app.net.rx.RxRestClient;
import com.step.pda.app.ui.PdaLoader;
import com.step.pda.ec.contract.IBigPackContract;
import com.step.pda.ec.database.BigPack;
import com.step.pda.ec.database.BigPackItem;
import com.step.pda.ec.services.BigPackService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author zhushubin
 * @date 2019-08-15.
 * GitHub：
 * email： 604580436@qq.com
 * description：
 */
public class BigPackPresenter extends IBigPackContract.Presenter {
    private static  final Handler HANDLER = new Handler() ;
    public BigPackPresenter(IBigPackContract.View view, Context context) {
        super(view, context);
    }

    /***
     * 刷新获取新数据
     */
    @Override
    public void refresh() {

        RxRestClient.builder().url("/data/bigpack")
                .params("", "")
                .loader(mContext)
                .build().get().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }
                    @Override
                    public void onNext(String data) {
                        JSONObject jsonObject = JSON.parseObject(data);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        int length = jsonArray.size();
                        List<BigPack> results = new ArrayList<>();
                        BigPack bigPack = null;
                        JSONObject obj = null;
                        List<BigPackItem> itemList =null;
                        for(int i=0;i<length;i++){
                            obj= jsonArray.getJSONObject(i);
                            String saleOrderSn = obj.getString("saleOrderSn");
                            String customerSn = obj.getString("customerSn");
                            String customerName = obj.getString("customerName");
                            String wordOrderSn = obj.getString("wordOrderSn");
                            String customerOrderSn = obj.getString("customerOrderSn");
                            int tag = obj.getInteger("tag");
                            try {
                                itemList = obj.getJSONArray("itemList").toJavaList(BigPackItem.class);
                            }catch (Exception e){
                                Log.e("bigpackdelegate",e.getMessage());
                            }
                            bigPack = new BigPack();
                            bigPack.setSaleOrderSn(saleOrderSn);
                            bigPack.setCustomerSn(customerSn);
                            bigPack.setCustomerName(customerName);
                            bigPack.setWordOrderSn(wordOrderSn);
                            bigPack.setCustomerOrderSn(customerOrderSn);
                            bigPack.setItemList(itemList);
                            bigPack.setTag(tag);
                            results.add(bigPack);
                        }
                        if(results!=null&&results.size()>0){
                            try {
                                new BigPackService().saveBigPackInTx(results);
                            }catch (Exception e){
                                Log.e("bigpackdelegate",e.getMessage());
                            }
                        }
                        mView.onRefreshSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        stopLoading();
                        Toast.makeText(mContext,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        stopLoading();
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
}
