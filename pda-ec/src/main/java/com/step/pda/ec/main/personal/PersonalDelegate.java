package com.step.pda.ec.main.personal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.step.pda.app.delegate.bottom.BottomItemDelegate;
import com.step.pda.ec.R;
import com.step.pda.ec.R2;
import com.step.pda.ec.main.personal.child.AvatarFragment;
import com.step.pda.ec.main.personal.child.MeFragment;

import butterknife.BindView;

/**
 * Created by user on 2019-08-06.
 */

public class PersonalDelegate extends BottomItemDelegate {
    @BindView(R2.id.toolbar)
    Toolbar mToolbar = null;

    @Override
    public Object setLayout() {
         return R.layout.delegate_personal;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstance, View rootViw) {

    }


    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        if (findChildFragment(AvatarFragment.class) == null) {
            loadFragment();
        }
    }
    private void loadFragment() {
        loadRootFragment(R.id.fl_fourth_container_upper, AvatarFragment.newInstance());
        loadRootFragment(R.id.fl_fourth_container_lower, MeFragment.newInstance());
        mToolbar.setTitle(R.string.me);
    }
    public void onBackToFirstFragment() {

        //返回
    }

}
