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
    /***
     * 客户号
     */
    private String customerSn;
    /***
     * 客户名称
     */
    private String customerName;
    /***
     * 工单单号
     */
    private String workOrderSn;
    /***
     * 客户单号
     */
    private String customerOrderSn;

    private List<BigPackItem> list;
    /***
     * 手否可以提交
     * 0 不可
     * 1 可以
     */
    private int  tag;
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

    public String getCustomerSn() {
        return customerSn;
    }

    public void setCustomerSn(String customerSn) {
        this.customerSn = customerSn;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getWorkOrderSn() {
        return workOrderSn;
    }

    public void setWorkOrderSn(String workOrderSn) {
        this.workOrderSn = workOrderSn;
    }

    public String getCustomerOrderSn() {
        return customerOrderSn;
    }

    public void setCustomerOrderSn(String customerOrderSn) {
        this.customerOrderSn = customerOrderSn;
    }


    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }
}
