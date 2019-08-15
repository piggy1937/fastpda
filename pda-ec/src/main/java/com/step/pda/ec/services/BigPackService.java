package com.step.pda.ec.services;

import com.step.pda.ec.database.BigPack;
import com.step.pda.ec.database.BigPackDao;
import com.step.pda.ec.database.BigPackItem;
import com.step.pda.ec.database.BigPackItemDao;
import com.step.pda.ec.database.DatabaseManager;

import java.util.List;

/**
 * @author zhushubin
 * @date 2019-08-15.
 * GitHub：
 * email： 604580436@qq.com
 * description： 大包标签数据库处理
 */
public class BigPackService {
    private BigPackDao mBigPackDao;
    private BigPackItemDao  mBigPackItemDao;
    public BigPackService() {
        this.mBigPackDao = DatabaseManager.getInstance().getmBigPackDao();
        this.mBigPackItemDao = DatabaseManager.getInstance().getmBigPackItemDao();
    }
    public void saveBigPackInTx(List<BigPack> list){

        //同步的时候情况所有记录
        mBigPackDao.deleteAll();
        mBigPackItemDao.deleteAll();
        for(BigPack entity:list){
            Long id =  mBigPackDao.insert(entity);
           for(BigPackItem item:entity.getItemList()){
               item.setParentId(id);
           }
            mBigPackItemDao.insertInTx(entity.getItemList());

        }


      //  mBigPackDao.saveInTx(list);
    }

    /***
     * 获取list
     * @param start 开始
     * @param pageSize 每页记录数
     * @return list
     */
    public List<BigPack> findList(Integer start,Integer pageSize){
        return mBigPackDao.queryBuilder().offset(start).limit(pageSize).orderAsc(BigPackDao.Properties.Id).list();
    }

    /***
     * 获取全部记录数
     * @return
     */
    public long total() {
        return mBigPackItemDao.queryBuilder().count();
    }
    /***
     * 获取全部记录数
     * @return
     */
    public long countBigPackItem() {
        return mBigPackItemDao.queryBuilder().count();
    }

    public List<BigPackItem> findItemByPid(Long pid) {
        return mBigPackItemDao.queryBuilder().where(BigPackItemDao.Properties.ParentId.eq(pid)).list();

    }

    /***
     * 根据客户单号及产品品号去查找数据
     * @param customerOrderSn  客户单号
     * @param productSn 产品品号
     * @return  BigPackItem
     */
    public BigPackItem findByParams(String customerOrderSn, String productSn) {
      BigPack bigPack =  mBigPackDao.queryBuilder().where(BigPackDao.Properties.CustomerOrderSn.eq(customerOrderSn)).unique();
      if(bigPack == null){
          return null;
      }
      BigPackItem item= mBigPackItemDao.queryBuilder().where(BigPackItemDao.Properties.ParentId.eq(bigPack.getId()),(BigPackItemDao.Properties.ProductSn.eq(productSn))).unique();
      return item;
    }

    /***
     * 修改小标签状态
     * @param bigPackItem
     */
    public void saveBigPackItem(BigPackItem bigPackItem) {
        bigPackItem.setTag(1);
        mBigPackItemDao.save(bigPackItem);
        long count= mBigPackItemDao.queryBuilder().where(BigPackItemDao.Properties.ParentId.eq(bigPackItem.getParentId()),(BigPackItemDao.Properties.Tag.eq(0))).count();
        if(count<1){
            BigPack bigPack =   mBigPackDao.load(bigPackItem.getParentId());
            bigPack.setTag(1);
            mBigPackDao.save(bigPack);
        }
    }
}
