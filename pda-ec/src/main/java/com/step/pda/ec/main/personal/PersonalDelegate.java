package com.step.pda.ec.main.personal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.step.pda.app.delegate.bottom.BottomItemDelegate;
import com.step.pda.ec.R;
import com.step.pda.ec.R2;

import butterknife.OnClick;

/**
 * Created by user on 2019-08-06.
 */

public class PersonalDelegate extends BottomItemDelegate {

    @OnClick(R2.id.btn_person_sign_out)
    void onSignOut(){
        Toast.makeText(getContext(),"退出",Toast.LENGTH_SHORT).show();
    }
    @Override
    public Object setLayout() {
         return R.layout.delegate_personal;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstance, View rootViw) {

    }
}
