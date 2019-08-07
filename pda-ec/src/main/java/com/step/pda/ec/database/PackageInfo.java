package com.step.pda.ec.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

/**
 * Created by user on 2019-08-06.
 * 包装信息
 */
@Entity(nameInDb = "package_info")
public class PackageInfo  implements Serializable{
    private static final long serialVersionUID = 9057399777770511208L;
    @Id(autoincrement = true)
    private Long id ;
    /***
     * 编号
     */
    private String sn = null;
    /***
     * 标题
     */
    private String title = null;
    private Integer quantity = 0;
    @Generated(hash = 2065995351)
    public PackageInfo(Long id, String sn, String title, Integer quantity) {
        this.id = id;
        this.sn = sn;
        this.title = title;
        this.quantity = quantity;
    }
    @Generated(hash = 1854842808)
    public PackageInfo() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getSn() {
        return this.sn;
    }
    public void setSn(String sn) {
        this.sn = sn;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Integer getQuantity() {
        return this.quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }



}
