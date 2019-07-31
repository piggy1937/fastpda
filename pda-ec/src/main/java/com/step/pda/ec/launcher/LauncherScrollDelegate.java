package com.step.pda.ec.launcher;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.step.pda.app.delegate.PdaDelagete;
import com.step.pda.app.ui.launcher.LauncherHolderCreator;
import com.step.pda.ec.R;

import java.util.ArrayList;

/**
 * Created by user on 2019-07-31.
 */

public class LauncherScrollDelegate extends PdaDelagete implements OnItemClickListener {
    private ConvenientBanner<Integer> mConvenientBanner=null;
    private static final ArrayList<Integer>  INTEGERS = new ArrayList<>();
    private void initBanner(){
        INTEGERS.add(R.mipmap.launcher_01);
        INTEGERS.add(R.mipmap.launcher_02);
        INTEGERS.add(R.mipmap.launcher_03);
        INTEGERS.add(R.mipmap.launcher_04);
        INTEGERS.add(R.mipmap.launcher_05);
        mConvenientBanner
                .setPages(new LauncherHolderCreator(),INTEGERS)
                .setPageIndicator(new int[]{R.drawable.dot_normal,R.drawable.dot_focus})
                 .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(this)
                .setCanLoop(false);
    }
    @Override
    public Object setLayout() {
        mConvenientBanner = new ConvenientBanner<Integer>(getContext());
        return mConvenientBanner;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstance, View rootViw) {
        initBanner();
    }

    @Override
    public void onItemClick(int position) {

    }
}
