package com.step.pda.app.ui.banner;

import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.bigkoo.convenientbanner.holder.Holder;

/**
 * Created by zhushunin
 */

public class ImageHolder extends Holder<Integer> {

    private AppCompatImageView mImageView = null;

    public ImageHolder(View itemView) {
        super(itemView);
    }


    @Override
    protected void initView(View itemView) {

    }

    @Override
    public void updateUI(Integer data) {

    }
//    private static final RequestOptions BANNER_OPTIONS = new RequestOptions()
//            .diskCacheStrategy(DiskCacheStrategy.ALL)
//            .dontAnimate()
//            .centerCrop();


}
