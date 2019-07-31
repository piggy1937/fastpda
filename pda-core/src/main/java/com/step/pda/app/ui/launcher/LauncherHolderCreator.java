package com.step.pda.app.ui.launcher;

import android.view.View;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.step.pda.R;

/**
 * Created by user on 2019-07-31.
 */

public class LauncherHolderCreator implements CBViewHolderCreator{
    @Override
    public Holder createHolder(View itemView) {
        return new LauncherHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.launch_item_img;
    }
}
