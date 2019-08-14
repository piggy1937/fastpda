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
    private static final long serialVersionUID = -8160717989516782205L;
    @Id(autoincrement = true)
    private Long id ;
    /***
     * 产品品号
     */
    private String productSn;

    @NotNull
    private Long parentId;

    @Generated(hash = 2010757569)
    public BigPackItem(Long id, String productSn, @NotNull Long parentId) {
        this.id = id;
        this.productSn = productSn;
        this.parentId = parentId;
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


}
