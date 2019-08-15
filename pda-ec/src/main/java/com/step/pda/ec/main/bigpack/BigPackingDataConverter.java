package com.step.pda.ec.main.bigpack;

import com.step.pda.app.ui.recycler.DataConverter;
import com.step.pda.app.ui.recycler.MultipleFields;
import com.step.pda.ec.database.bean.BigPackItem;
import com.step.pda.ec.database.bean.ExpandableBigPack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2019-08-06.
 */

public class BigPackingDataConverter extends DataConverter<ExpandableBigPack,ExpandableMultipleItemEntity> {

    @Override
    public ArrayList<ExpandableMultipleItemEntity> convert() {
        int size =items!=null?items.size():0;
        ExpandableBigPack tEntity =null;
        ExpandableMultipleItemEntity multipleItemEntity = null;
        for(int i=0;i<size;i++){
            tEntity = items.get(i);
            String sn = tEntity.getSn();
            String customerSn = tEntity.getCustomerSn();
            String customerName = tEntity.getCustomerName();
            String workOrderSn = tEntity.getWorkOrderSn();
            String customerOrderSn = tEntity.getCustomerOrderSn();
            multipleItemEntity = ExpandableMultipleItemEntity._builder()
                    .setField(MultipleFields.ITEM_TYPE,tEntity.getItemType())
                    .setField(MultipleFields.TEXT,sn)
                    .setField(MultipleFields.CUSTOMER_SN,customerSn)
                    .setField(MultipleFields.CUSTOMER_NAME,customerName)
                    .setField(MultipleFields.WORK_ORDER_SN,workOrderSn)
                    .setField(MultipleFields.CUSTOMER_ORDER_SN,customerOrderSn)
                    .setField(MultipleFields.TAG,tEntity.getTag())
                    .build();
            multipleItemEntity.setExpanded(false);

            List<ExpandableMultipleItemEntity> subItems = convertSubItems(tEntity.getSubItems());
            multipleItemEntity.setSubItems(subItems);
            ENTITIES.add(multipleItemEntity);

        }
        return (ArrayList<ExpandableMultipleItemEntity>) ENTITIES.clone();
    }

    private List<ExpandableMultipleItemEntity> convertSubItems(List<BigPackItem> subItems) {
        if(subItems==null){
            return null;
        }
        List<ExpandableMultipleItemEntity> result = new ArrayList<>(subItems.size());
        for(BigPackItem item:subItems){
            result.add(ExpandableMultipleItemEntity._builder()
                    .setField(MultipleFields.ITEM_TYPE,item.getItemType())
                    .setField(MultipleFields.TEXT,item.getProductSn())
                    .setField(MultipleFields.TAG,item.getTag())
                    .build());
        }
        return result;

    }





}
