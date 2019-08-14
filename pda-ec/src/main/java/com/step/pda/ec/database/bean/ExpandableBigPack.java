package com.step.pda.ec.database.bean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.step.pda.app.ui.recycler.ItemType;

import java.util.List;

/**
 * @author zhushubin
 * @email 604580436@qq.com
 * @date 2019/8/13 0013 下午 9:11
 */
public class ExpandableBigPack extends AbstractExpandableItem<BigPackItem> implements MultiItemEntity {
    /***
     * 编号
     */
    private String sn;

    private List<BigPackItem> list;
    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getItemType() {
        return ItemType.TYPE_LEVEL_0;
    }

    @Override
    public void setSubItems(List<BigPackItem> list) {
        super.setSubItems(list);
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }
}
