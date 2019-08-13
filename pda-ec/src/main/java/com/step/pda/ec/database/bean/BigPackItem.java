package com.step.pda.ec.database.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import static com.step.pda.ec.adapter.BigPackRecyclerAdapter.TYPE_LEVEL_1;

/**
 * @author zhushubin
 * @email 604580436@qq.com
 * @date 2019/8/13 0013 下午 9:10
 */
public class BigPackItem implements MultiItemEntity {
    private String sn;
    private String customer;
    @Override
    public int getItemType() {
        return TYPE_LEVEL_1;
    }
}
