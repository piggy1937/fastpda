package com.step.pda.ec.services;

import com.step.pda.ec.database.DatabaseManager;
import com.step.pda.ec.database.PackageInfo;
import com.step.pda.ec.database.PackageInfoDao;

import java.util.List;

/**
 * Created by user on 2019-08-07.
 */

public class PackageInfoService {
   public long save(PackageInfo packageInfo){
      return  DatabaseManager.getInstance().getmPackageInfoDao().insert(packageInfo) ;
   }

    public void delete(long id) {
         DatabaseManager.getInstance().getmPackageInfoDao().deleteByKey(id); ;
    }
    public long count(){
        return DatabaseManager.getInstance().getmPackageInfoDao().queryBuilder().count();
    }

    public List<PackageInfo> page(int pageNo,int pageSize){
        List<PackageInfo> packageInfoList= DatabaseManager.getInstance().getmPackageInfoDao(

        ).queryBuilder().offset(pageNo*pageSize).limit(pageSize).orderAsc(PackageInfoDao.Properties.Id).list();
        return packageInfoList;
    }

    /***
     * 修改数量
     * @param id
     * @param quantity
     */
    public void update(long id, int quantity) {
        PackageInfoDao packageInfoDao= DatabaseManager.getInstance().getmPackageInfoDao();
        PackageInfo packageInfo =  packageInfoDao.load(id);
        if(packageInfo!=null) {
            packageInfo.setQuantity(quantity);
            packageInfoDao.update(packageInfo);
        }
    }

    public Boolean existSn(String sn) {
        PackageInfoDao packageInfoDao= DatabaseManager.getInstance().getmPackageInfoDao();
       long couunt= packageInfoDao.queryBuilder().where(PackageInfoDao.Properties.Sn.eq(sn)).count();
       if(couunt>0){
           return true;
       }else{
           return false;
       }
    }
}
