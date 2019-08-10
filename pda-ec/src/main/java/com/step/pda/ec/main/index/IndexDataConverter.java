package com.step.pda.ec.main.index;

import com.step.pda.app.ui.recycler.DataConverter;
import com.step.pda.app.ui.recycler.ItemType;
import com.step.pda.app.ui.recycler.MultipleEntityBuilder;
import com.step.pda.app.ui.recycler.MultipleFields;
import com.step.pda.app.ui.recycler.MultipleItemEntity;
import com.step.pda.ec.database.PackageInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by user on 2019-08-06.
 */

public class IndexDataConverter extends DataConverter {
    private List<PackageInfo> packageInfoList =null;
    @Override
    public ArrayList<MultipleItemEntity> convert() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int size =packageInfoList!=null?packageInfoList.size():0;
        for(int i=0;i<size;i++){
            int type = 0;
            int spanSize = 4;
            long id =  packageInfoList.get(i).getId();
            String sn = packageInfoList.get(i).getSn();
            String title = packageInfoList.get(i).getTitle();
            int quantity = packageInfoList.get(i).getQuantity();//数量
            Date lastModifyDate =  packageInfoList.get(i).getLastModifyTime();

            type = ItemType.TEXT;

            MultipleEntityBuilder builder = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE,type)
                    .setField(MultipleFields.SPAN_SIZE,spanSize)
                    .setField(MultipleFields.ID,id)
                    .setField(MultipleFields.TEXT,sn)
                    .setField(MultipleFields.QUANTITY,quantity);
              if(lastModifyDate!=null){
                   builder.setField(MultipleFields.LAST_MODIFY_TIME,simpleDateFormat.format(lastModifyDate));
              }

            final MultipleItemEntity entity =builder.build();
            ENTITIES.add(entity);

        }
        return (ArrayList<MultipleItemEntity>) ENTITIES.clone();
    }


    public DataConverter setPackageInfoList(List<PackageInfo> packageInfoList) {
        if(packageInfoList==null){
            this.packageInfoList =null;
            return this;
        }
        this.packageInfoList = packageInfoList ;
        return this;
    }



}
