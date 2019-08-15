package com.step.pda.ec.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.joanzapata.iconify.widget.IconTextView;
import com.step.pda.app.Pda;
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
        addItemType(ItemType.TYPE_LEVEL_0, R.layout.rec_item_level_0);
        addItemType(ItemType.TYPE_LEVEL_1, R.layout.rec_item_level_1);
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
        final String customerSn;
        final String customerName;
        final String workOrderSn;
        final String customerOrderSn;
        final int tag;
        final AppCompatButton btnSubmit;
        final IconTextView iconTag;
        switch (holder.getItemViewType()) {
            case ItemType.TYPE_LEVEL_0:
                text = entity.getField(MultipleFields.TEXT);
                customerSn = entity.getField(MultipleFields.CUSTOMER_SN);
                customerName = entity.getField(MultipleFields.CUSTOMER_NAME);
                workOrderSn = entity.getField(MultipleFields.WORK_ORDER_SN);
                customerOrderSn = entity.getField(MultipleFields.CUSTOMER_ORDER_SN);
                tag = entity.getField(MultipleFields.TAG);
                btnSubmit=holder.getView(R.id.btn_big_pack_submit);
                if(tag==0){
                    btnSubmit.setVisibility(View.GONE);
                }else{
                    btnSubmit.setVisibility(View.VISIBLE);
                }
                holder.setText(R.id.tv_big_pack_sn,text );
                holder.setText(R.id.tv_big_pack_customer_sn,customerSn );
                holder.setText(R.id.tv_big_pack_customer_name,customerName );
                holder.setText(R.id.tv_big_pack_work_order_sn,workOrderSn );
                holder.setText(R.id.tv_big_pack_customer_order_sn,customerOrderSn );
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
                        } else {
                            expand(pos, false);
                        }
                    }
                });
                break;
            case ItemType.TYPE_LEVEL_1:
                text = entity.getField(MultipleFields.TEXT);
                tag  = entity.getField(MultipleFields.TAG);
                holder.setText(R.id.tv_big_pack_product_sn, text);
                iconTag = holder.getView(R.id.tv_big_pack_icon);
                if(tag == 0){
                    iconTag.setText("{fa-close}");
                    iconTag.setTextColor(Color.GRAY);
                }else{
                    iconTag.setTextColor(Pda.getApplicationContext().getResources().getColor(R.color.colorAccent));
                    iconTag.setText("{fa-check}");
                }

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
