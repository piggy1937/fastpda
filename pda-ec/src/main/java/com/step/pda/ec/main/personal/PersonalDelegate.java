package com.step.pda.ec.main.personal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.step.pda.app.delegate.bottom.BottomItemDelegate;
import com.step.pda.ec.R;

/**
 * Created by user on 2019-08-06.
 */

public class PersonalDelegate extends BottomItemDelegate {
    @Override
    public Object setLayout() {
         return R.layout.delegate_personal;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstance, View rootViw) {

    }
}
