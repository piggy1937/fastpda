package com.step.pda.ec.main.index;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.step.pda.app.delegate.PdaDelegate;
import com.step.pda.app.ui.recycler.MultipleItemEntity;

/**
 * Created by user on 2019-08-06.
 */

public class IndexItemClickListener extends SimpleClickListener {

    private final PdaDelegate DELEGATE;

    private IndexItemClickListener(PdaDelegate delegate) {
        this.DELEGATE = delegate;
    }

    public static SimpleClickListener create(PdaDelegate delegate) {
        return new IndexItemClickListener(delegate);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        final MultipleItemEntity entity = (MultipleItemEntity) baseQuickAdapter.getData().get(position);
      //  final int goodsId = entity.getField(MultipleFields.ID);
//        final GoodsDetailDelegate delegate = GoodsDetailDelegate.create(goodsId);
//        DELEGATE.getSupportDelegate().start(delegate);
    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {
    }
}
