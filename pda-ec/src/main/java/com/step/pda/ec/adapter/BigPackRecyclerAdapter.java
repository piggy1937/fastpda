package com.step.pda.ec.adapter;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.step.pda.app.ui.recycler.DataConverter;
import com.step.pda.app.ui.recycler.ItemType;
import com.step.pda.app.ui.recycler.MultipleFields;
import com.step.pda.ec.R;
import com.step.pda.ec.main.bigpack.ExpandableMultipleItemEntity;

import java.util.List;

/**
 * @author zhushubin
 * @email 604580436@qq.com
 * @date 2019/8/13 0013 下午 9:15
 */
public class BigPackRecyclerAdapter extends BaseMultiItemQuickAdapter<ExpandableMultipleItemEntity, BaseViewHolder> {

    public BigPackRecyclerAdapter(List<ExpandableMultipleItemEntity> data) {
        super(data);
        init();

    }

    private void init() {
        addItemType(ItemType.TYPE_LEVEL_0, R.layout.item_title);
        addItemType(ItemType.TYPE_LEVEL_1, R.layout.item_message);
    }


    public static BigPackRecyclerAdapter create(DataConverter converter) {
        return new BigPackRecyclerAdapter(converter.convert());
    }

    @NonNull
    @Override
    public List<ExpandableMultipleItemEntity> getData() {
        return super.getData();
    }
    @Override
    protected void convert(final BaseViewHolder holder, final ExpandableMultipleItemEntity entity) {
        final String text;
        switch (holder.getItemViewType()) {
            case ItemType.TYPE_LEVEL_0:
                text = entity.getField(MultipleFields.TEXT);
                holder.setText(R.id.item_title1,text );
//                // TODO: 2019/4/16 关键代码，添加子view的点击事件
//                holder.addOnClickListener(R.id.item_title1);
//                holder.addOnClickListener(R.id.item_title2);
//                //添加该条目的点击事件
              holder.itemView.setOnClickListener(new View.OnClickListener() {
                   @Override
                    public void onClick(View v) {
                        int pos = holder.getAdapterPosition();
                        if (entity.isExpanded()) {
                            collapse(pos, false);
                            Toast.makeText(mContext, "收起：" + text,
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            expand(pos, false);
                            Toast.makeText(mContext, "展开：" + text,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case ItemType.TYPE_LEVEL_1:
                text = entity.getField(MultipleFields.TEXT);
                holder.setText(R.id.item_message1, text);
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
