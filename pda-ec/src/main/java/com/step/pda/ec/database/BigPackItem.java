package com.step.pda.ec.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

import java.io.Serializable;

/**
 * Created by user on 2019-08-06.
 * 包装信息
 */
@Entity(nameInDb = "big_pack_item")
public class BigPackItem implements Serializable{
    private static final long serialVersionUID = 5856117153409995488L;
    @Id(autoincrement = true)
    private Long id ;
    /***
     * 产品品号
     */
    private String productSn;

    @NotNull
    private Long parentId;
    /***
     * 是否已扫描
     */
    private int tag = 0;
    @Generated(hash = 1999750123)
    public BigPackItem(Long id, String productSn, @NotNull Long parentId, int tag) {
        this.id = id;
        this.productSn = productSn;
        this.parentId = parentId;
        this.tag = tag;
    }

    @Generated(hash = 1175545505)
    public BigPackItem() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductSn() {
        return this.productSn;
    }

    public void setProductSn(String productSn) {
        this.productSn = productSn;
    }

    public Long getParentId() {
        return this.parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }
}
