package com.step.pda.ec.main.index;

import com.step.pda.app.ui.recycler.DataConverter;
import com.step.pda.app.ui.recycler.ItemType;
import com.step.pda.app.ui.recycler.MultipleFields;
import com.step.pda.app.ui.recycler.MultipleItemEntity;
import com.step.pda.ec.database.PackageInfo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by user on 2019-08-06.
 */

public class IndexDataConverter extends DataConverter {
    private LinkedList<PackageInfo> packageInfoList =null;
    @Override
    public ArrayList<MultipleItemEntity> convert() {
        int size =packageInfoList!=null?packageInfoList.size():0;
        for(int i=0;i<size;i++){
            int type = 0;
            int spanSize = 4;
            long id =  packageInfoList.get(i).getId();
            String sn = packageInfoList.get(i).getSn();
            String title = packageInfoList.get(i).getTitle();
            type = ItemType.TEXT;

            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE,type)
                    .setField(MultipleFields.SPAN_SIZE,spanSize)
                    .setField(MultipleFields.ID,id)
                    .setField(MultipleFields.TEXT,title)
                    .build();
            ENTITIES.add(entity);

        }
        return ENTITIES;
    }


    public IndexDataConverter setPackageInfoList(List<PackageInfo> packageInfoList) {
        if(packageInfoList==null){
            this.packageInfoList =null;
        }
        this.packageInfoList =new LinkedList<>(packageInfoList) ;
        return this;
    }

    @Override
    public void clearData() {
        super.clearData();
        this.packageInfoList = null;
    }
}
