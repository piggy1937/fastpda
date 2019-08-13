package com.step.pda.ec.database.bean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

import static com.step.pda.ec.adapter.BigPackRecyclerAdapter.TYPE_LEVEL_0;

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
        return TYPE_LEVEL_0;
    }

    @Override
    public void setSubItems(List<BigPackItem> list) {
        super.setSubItems(list);
    }

}
