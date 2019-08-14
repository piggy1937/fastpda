package com.step.pda.ec.main.bigpack;

import com.step.pda.app.ui.recycler.DataConverter;
import com.step.pda.app.ui.recycler.MultipleItemEntity;
import com.step.pda.ec.database.bean.BigPackItem;

import java.util.ArrayList;

/**
 * Created by user on 2019-08-06.
 */

public class BigPackingDataConverter extends DataConverter<BigPackItem> {

    @Override
    public ArrayList<MultipleItemEntity> convert() {
        int size =items!=null?items.size():0;
        for(int i=0;i<size;i++){
            int type = 0;
            int spanSize = 4;
            long id =  items.get(i).getId();
            String sn = items.get(i).getSn();
            String customer = items.get(i).getCustomer();
//            type = ItemType.TEXT;
//
//            final MultipleItemEntity entity = MultipleItemEntity.builder()
//                    .setField(MultipleFields.ITEM_TYPE,type)
//                    .setField(MultipleFields.SPAN_SIZE,spanSize)
//                    .setField(MultipleFields.ID,id)
//                    .setField(MultipleFields.TEXT,sn)
//                    .setField(MultipleFields.QUANTITY,quantity)
//                    .build();
            ENTITIES.add(null);

        }
        return (ArrayList<MultipleItemEntity>) ENTITIES.clone();
    }
}
