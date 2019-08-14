package com.step.pda.ec.adapter;

import android.support.annotation.NonNull;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.step.pda.ec.R;
import com.step.pda.ec.main.bigpack.BigPackingDataConverter;

import java.util.List;

/**
 * @author zhushubin
 * @email 604580436@qq.com
 * @date 2019/8/13 0013 下午 9:15
 */
public class BigPackRecyclerAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;
    public BigPackRecyclerAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_LEVEL_0, R.layout.item_title);
        addItemType(TYPE_LEVEL_1, R.layout.item_message);
    }

    public static BigPackRecyclerAdapter create(BigPackingDataConverter setItems) {
        return null;
    }

    @NonNull
    @Override
    public List<MultiItemEntity> getData() {
        return super.getData();
    }
    @Override
    protected void convert(final BaseViewHolder holder, final MultiItemEntity item) {
        switch (holder.getItemViewType()) {
            case TYPE_LEVEL_0:
//                final TestEntity.ResultBean resultBean = (TestEntity.ResultBean) item;
//                holder.setText(R.id.item_title1, resultBean.getTitle1());
//                holder.setText(R.id.item_title2, resultBean.getTitle2());
//                // TODO: 2019/4/16 关键代码，添加子view的点击事件
//                holder.addOnClickListener(R.id.item_title1);
//                holder.addOnClickListener(R.id.item_title2);
//                //添加该条目的点击事件
//                holder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        int pos = holder.getAdapterPosition();
//                        if (resultBean.isExpanded()) {
//                            collapse(pos, false);
//                            Toast.makeText(mContext, "收起：" + resultBean.getTitle1(),
//                                    Toast.LENGTH_SHORT).show();
//                        } else {
//                            expand(pos, false);
//                            Toast.makeText(mContext, "展开：" + resultBean.getTitle1(),
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
                break;
            case TYPE_LEVEL_1:
//                final TestEntity.ResultBean.ListBean listBean = (TestEntity.ResultBean.ListBean) item;
//                holder.setText(R.id.item_message1, listBean.getMessage1());
//                holder.setText(R.id.item_message2, listBean.getMessage2());
//                // TODO: 2019/4/16 关键代码，添加子view的点击事件
//                holder.addOnClickListener(R.id.item_message1);
//                holder.addOnClickListener(R.id.item_message2);
//                //添加该条目的点击事件
//                holder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Toast.makeText(mContext, "点击了：" + listBean.getMessage1() + listBean.getMessage2(),
//                                Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//                break;
        }
    }
}
