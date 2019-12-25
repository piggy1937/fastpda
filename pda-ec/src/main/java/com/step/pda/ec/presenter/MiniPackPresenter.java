package com.step.pda.ec.presenter;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.step.pda.app.AccountManager;
import com.step.pda.app.net.rx.RxRestClient;
import com.step.pda.app.ui.PdaLoader;
import com.step.pda.ec.contract.IMiniPackContract;
import com.step.pda.ec.database.PackageInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
 * description：  小包标签presenter
 */
public class MiniPackPresenter extends IMiniPackContract.Presenter {
    private static  final Handler HANDLER = new Handler() ;

    public MiniPackPresenter(IMiniPackContract.View view, Context context) {
        super(view, context);
    }

    private void stopLoading(){
        HANDLER.postDelayed(new Runnable() {
            @Override
            public void run() {
                PdaLoader.stopLoading();
            }
        },1000);
    }
   //获取第一页信息
    @Override
    public void firstPage() {
        RxRestClient.builder().url("/data/getbarcode?rwdate=2019-10-3&creater=admin")
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
                        JSONObject jObj = JSONObject.parseObject(data);
                        int errcode = jObj.getIntValue("errCode");
                        if(errcode == 1){
                          //成功
//                            JSONObject content= jObj.getJSONObject("content");
//                            JSONObject pageInfo = content.getJSONObject("pageInfo");
//                            int pageNo = pageInfo.getIntValue("pageNo");
//                            int pageSize = pageInfo.getIntValue("pageSize");
//                            int total = pageInfo.getIntValue("total");
                            JSONArray content= jObj.getJSONArray("BarCodeList");
                            JSONObject obj=null;
                            PackageInfo packageInfo=null;
                            List<PackageInfo> packageInfos = new ArrayList<>();
                            for(int i=0;i<content.size();i++){
                                obj= (JSONObject) content.get(i);
                                packageInfo = new PackageInfo();
                                packageInfo.setId(Long.valueOf(i));
                                packageInfo.setLastModifyTime(new Date());
                                packageInfo.setCreator(AccountManager.getCreater());
                                packageInfo.setTitle(obj.getString("BarCode"));
                                packageInfo.setQuantity(2);
                                packageInfo.setSn(obj.getString("BarCode"));
                                packageInfos.add(packageInfo);
                            }


                           // List<PackageInfo>  packageInfoList=content.toJavaList(PackageInfo.class);
                           mView.onFirstPageSuccess(1,packageInfos.size(),packageInfos.size(),packageInfos);
                        }else{
                            String errmsg =  jObj.getString("errmsg");
                            Toast.makeText(mContext, errmsg, Toast.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onError(Throwable e) {
                        stopLoading();
                        mView.onPageError();
                        Toast.makeText(mContext,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        stopLoading();
                        Toast.makeText(mContext,"登录成功",Toast.LENGTH_SHORT).show();
                    }
                });



    }

    /***
     * 分页获取数据
     * @param pageNo 请求页码
     */
    @Override
    public void page(int pageNo) {
             mView.onPageSuccess();
    }

    @Override
    public void requestAddBarCodeSignIn(String sn, Integer quantity) {
        Map<String,Object> params = new HashMap<>();
        params.put("barcode", sn);
        params.put("txtSl",quantity);
        RxRestClient.builder().url("/Data/ParseBarcode")
                .params(params)
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
                        int errcode = jObj.getIntValue("errcode");
                        if(errcode == 0){
                            //成功
                            JSONObject content= jObj.getJSONObject("content");
                            JSONObject pageInfo = content.getJSONObject("pageInfo");
                            int pageNo = pageInfo.getIntValue("pageNo");
                            int pageSize = pageInfo.getIntValue("pageSize");
                            int total = pageInfo.getIntValue("total");
                            List<PackageInfo>  packageInfoList= pageInfo.getJSONArray("list").toJavaList(PackageInfo.class);
                            mView.onFirstPageSuccess(pageNo,pageSize,total,packageInfoList);
                        }else{
                            String errmsg =  jObj.getString("errmsg");
                            Toast.makeText(mContext, errmsg, Toast.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onError(Throwable e) {
                        stopLoading();
                        Toast.makeText(mContext,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        stopLoading();
                        Toast.makeText(mContext,"登录成功",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
