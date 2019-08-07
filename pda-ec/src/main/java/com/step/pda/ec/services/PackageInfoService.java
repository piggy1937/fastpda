package com.step.pda.ec.services;

import com.step.pda.ec.database.DatabaseManager;
import com.step.pda.ec.database.PackageInfo;

/**
 * Created by user on 2019-08-07.
 */

public class PackageInfoService {
   public long save(PackageInfo packageInfo){
      return  DatabaseManager.getInstance().getmPackageInfoDao().insert(packageInfo) ;
   }

}
