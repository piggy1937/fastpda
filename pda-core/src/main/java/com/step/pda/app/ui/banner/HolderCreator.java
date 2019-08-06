package com.step.pda.app.ui.banner;

import android.view.View;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;

/**
 * Created by user on 2019-08-06.
 */

public class HolderCreator  implements CBViewHolderCreator {

    @Override
    public Holder createHolder(View itemView) {
        return new ImageHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return 0;
    }
}
