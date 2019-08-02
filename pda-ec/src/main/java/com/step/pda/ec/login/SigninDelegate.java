package com.step.pda.ec.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.View;

import com.step.pda.app.delegate.PdaDelagete;
import com.step.pda.ec.R;
import com.step.pda.ec.R2;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by user on 2019-08-02.
 * 登录
 */

public class SigninDelegate  extends PdaDelagete{
    @BindView(R2.id.edit_sign_in_email)
    TextInputEditText mEmail;
    @OnClick(R2.id.btn_sign_in)
    public void doLogin(){
        checkForm();
    }
    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_in;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstance, View rootViw) {

    }
    private boolean checkForm(){
        String email = mEmail.getText().toString();
        boolean isPass = true;
        if(email==null||email.isEmpty()){
            mEmail.setError("密码不允许为空");
            isPass = false;
        }

        return isPass;
    }
}
