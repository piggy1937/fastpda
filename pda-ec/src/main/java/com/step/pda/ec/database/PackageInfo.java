package com.step.pda.ec.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import java.util.Date;

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
    /***
     * 最近修改时间
     */
    private Date lastModifyTime;
    /***
     * 创建者
     */
    private String creator="";
    private String type="";
    @Generated(hash = 417928841)
    public PackageInfo(Long id, String sn, String title, Integer quantity,
            Date lastModifyTime, String creator, String type) {
        this.id = id;
        this.sn = sn;
        this.title = title;
        this.quantity = quantity;
        this.lastModifyTime = lastModifyTime;
        this.creator = creator;
        this.type = type;
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
    public Date getLastModifyTime() {
        return this.lastModifyTime;
    }
    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }
    public String getCreator() {
        return this.creator;
    }
    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
