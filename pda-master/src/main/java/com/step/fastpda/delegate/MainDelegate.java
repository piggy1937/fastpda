package com.step.fastpda.delegate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.step.fastpda.R;
import com.step.pda.app.delegate.PdaDelagete;

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

    }
}
