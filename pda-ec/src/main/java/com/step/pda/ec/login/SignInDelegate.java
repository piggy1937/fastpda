package com.step.pda.ec.login;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.View;

import com.step.pda.app.delegate.PdaDelegate;
import com.step.pda.ec.R;
import com.step.pda.ec.R2;
import com.step.pda.ec.contract.ISignContract;
import com.step.pda.ec.presenter.SignPresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by user on 2019-08-02.
 * 登录
 */

public class SignInDelegate  extends PdaDelegate{
    @BindView(R2.id.edit_sign_in_username)
    TextInputEditText mUsername;
    @BindView(R2.id.edit_sign_in_password)
    TextInputEditText mPassword;
    private ISignContract.Presenter mPresenter;

    @OnClick(R2.id.btn_sign_in)
    public void doLogin(){
        if(checkForm()){
            mPresenter.requestSignIn(mUsername.getText().toString(),mPassword.getText().toString());
        }
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ISignContract.View) {
            mPresenter = new SignPresenter((ISignContract.View)activity,getContext());
        }
    }
    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_in;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstance, View rootViw) {
    }
    private boolean checkForm(){
        String username = mUsername.getText().toString();
        String password = mPassword.getText().toString();
        boolean isPass = true;
        if(username==null||username.isEmpty()){
            mUsername.setError("用户名不允许为空");
            isPass = false;
        }
        if(password==null||password.isEmpty()){
            mPassword.setError("密码不允许为空");
            isPass = false;
        }

        return isPass;
    }

    @Override
    public void post(Runnable runnable) {

    }
}
