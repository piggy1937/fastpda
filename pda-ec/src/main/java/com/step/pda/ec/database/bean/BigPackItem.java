package com.step.pda.ec.database.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.step.pda.app.ui.recycler.ItemType;

/**
 * @author zhushubin
 * @email 604580436@qq.com
 * @date 2019/8/13 0013 下午 9:10
 */
public class BigPackItem implements MultiItemEntity {
    private Long id;
    private String sn;
    private String customer;
    @Override
    public int getItemType() {
        return ItemType.TYPE_LEVEL_1;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }
}
