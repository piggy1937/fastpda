package com.step.pda.ec.database;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;

import java.io.Serializable;
import java.util.List;

/**
 * Created by user on 2019-08-06.
 * 包装信息
 */
@Entity(nameInDb = "big_pack")
public class BigPack implements Serializable{
    private static final long serialVersionUID = -8160717989516782205L;
    @Id(autoincrement = true)
    private Long id ;
    /***
     * 销售订单
     */
    private String  saleOrderSn;
    /***
     * 客户编号
     */
    private String  customerSn;
    /***
     * 客户名称
     */
    private String customerName;
    /***
     * 工单单号
     */
    private String wordOrderSn;
    /***
     * 客户单号
     */
    private String customerOrderSn;
    /***
     * 是否可以提交
     * 0 不可提交
     * 1 可提交
     */
    private int tag;
    @ToMany(referencedJoinProperty = "parentId")
    private List<BigPackItem> itemList;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1630218915)
    private transient BigPackDao myDao;
    @Generated(hash = 299665638)
    public BigPack(Long id, String saleOrderSn, String customerSn, String customerName,
            String wordOrderSn, String customerOrderSn, int tag) {
        this.id = id;
        this.saleOrderSn = saleOrderSn;
        this.customerSn = customerSn;
        this.customerName = customerName;
        this.wordOrderSn = wordOrderSn;
        this.customerOrderSn = customerOrderSn;
        this.tag = tag;
    }
    @Generated(hash = 84606953)
    public BigPack() {
    }


    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getSaleOrderSn() {
        return this.saleOrderSn;
    }
    public void setSaleOrderSn(String saleOrderSn) {
        this.saleOrderSn = saleOrderSn;
    }
    public String getCustomerSn() {
        return this.customerSn;
    }
    public void setCustomerSn(String customerSn) {
        this.customerSn = customerSn;
    }
    public String getCustomerName() {
        return this.customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public String getWordOrderSn() {
        return this.wordOrderSn;
    }
    public void setWordOrderSn(String wordOrderSn) {
        this.wordOrderSn = wordOrderSn;
    }
    public String getCustomerOrderSn() {
        return this.customerOrderSn;
    }
    public void setCustomerOrderSn(String customerOrderSn) {
        this.customerOrderSn = customerOrderSn;
    }

    public void setItemList(List<BigPackItem> itemList) {
        this.itemList = itemList;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1327860728)
    public List<BigPackItem> getItemList() {
        if (itemList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            BigPackItemDao targetDao = daoSession.getBigPackItemDao();
            List<BigPackItem> itemListNew = targetDao._queryBigPack_ItemList(id);
            synchronized (this) {
                if (itemList == null) {
                    itemList = itemListNew;
                }
            }
        }
        return itemList;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 41475973)
    public synchronized void resetItemList() {
        itemList = null;
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 120568168)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getBigPackDao() : null;
    }
    public int getTag() {
        return this.tag;
    }
    public void setTag(int tag) {
        this.tag = tag;
    }

}
