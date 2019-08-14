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

            multipleItemEntity = ExpandableMultipleItemEntity._builder()
                    .setField(MultipleFields.ITEM_TYPE,tEntity.getItemType())
                    .setField(MultipleFields.TEXT,sn)
                    .build();
            multipleItemEntity.setExpanded(false);

            List<ExpandableMultipleItemEntity> subItems = convertSubItems(tEntity.getSubItems());
            multipleItemEntity.setSubItems(subItems);
            ENTITIES.add(multipleItemEntity);

        }
        return (ArrayList<ExpandableMultipleItemEntity>) ENTITIES.clone();
    }

    private List<ExpandableMultipleItemEntity> convertSubItems(List<BigPackItem> subItems) {
        List<ExpandableMultipleItemEntity> result = new ArrayList<>(subItems.size());
        for(BigPackItem item:subItems){
            result.add(ExpandableMultipleItemEntity._builder()
                    .setField(MultipleFields.ITEM_TYPE,item.getItemType())
                    .setField(MultipleFields.TEXT,item.getSn())
                    .build());
        }
        return result;

    }





}
