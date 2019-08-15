package com.step.pda.ec.presenter;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.step.pda.app.AccountManager;
import com.step.pda.app.net.rx.RxRestClient;
import com.step.pda.app.ui.PdaLoader;
import com.step.pda.ec.contract.ISignContract;
import com.step.pda.ec.database.DatabaseManager;
import com.step.pda.ec.database.UserProfile;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by user on 2019-08-08.
 * mvp p 层 登录
 */

public class SignPresenter extends ISignContract.Presenter {
    private static  final Handler HANDLER = new Handler() ;

    public SignPresenter(ISignContract.View view, Context context) {
        super(view, context);
    }

    @Override
    public void requestSignIn(final String username, final String password) {
        RxRestClient.builder().url("/data/LoginUser")
                .params("id", "0")
                .loader(mContext)
                .build().get().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String data) {
                         JSONArray dataArray =null;
                         try {
                             dataArray= JSON.parseArray(data);
                         }catch (Exception e){
                             Log.e("SignPresenter",e.getMessage());
                         }
                        int size = dataArray.size();
                        String tUsername = "";
                        String tPassword = "";
                        JSONObject jobj = null;
                        boolean flag = false;
                        for (int i = 0; i < size; i++) {
                            jobj = dataArray.getJSONObject(i);
                            tUsername = jobj.getString("UserName");
                            tPassword = jobj.getString("UserPwd");
                            if(tUsername.equals(username)&&tPassword.equals(password)){
                                flag = true;
                                break;
                            }
                        }
                        //账号密码一致
                        if(flag){

                            final UserProfile userProfile = new UserProfile();
                            userProfile.setName(tUsername);
                            userProfile.setUserId(1L);

                            try {
                                DatabaseManager.getInstance().getDao().insert(userProfile);
                            }catch (Exception e){
                                Log.e("login",e.getLocalizedMessage());
                            }
                            ////保存用户状态
                            AccountManager.setSignState(true);
                            mView.onSignInSuccess();
                        }else{
                            mView.onSignInError("用户名密码错误");
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
    //登出当前系统
    @Override
    public void requestSignOut() {
        try {
            DatabaseManager.getInstance().getDao().deleteByKey(1L);
            mView.onSignOutSuccess();
        }catch (Exception e){
            Log.e("login",e.getLocalizedMessage());
        }
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
