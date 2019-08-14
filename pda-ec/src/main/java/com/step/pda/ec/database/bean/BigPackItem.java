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
    /***
     * 产品品号
     */
    private String productSn;
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

    public String getProductSn() {
        return productSn;
    }

    public void setProductSn(String productSn) {
        this.productSn = productSn;
    }
}
