package com.step.pda.ec.database;

import android.content.Context;

import org.greenrobot.greendao.database.Database;

/**
 * Created by user on 2019-08-02.
 */

public class DatabaseManager {
    private DaoSession mDaoSession = null;
    private UserProfileDao mUserProfileDao = null;
    private PackageInfoDao mPackageInfoDao;
    private BigPackDao mBigPackDao=null; //大包标签数据库
    private BigPackItemDao mBigPackItemDao=null; //大包标签小标签数据库
    private static DatabaseManager instance;
    private DatabaseManager(){

    }

    public DatabaseManager init(Context context){
        initDao(context);
        return this;
    }

    public static DatabaseManager getInstance(){
         if(instance==null){
            synchronized (DatabaseManager.class){
                if(instance==null){
                    instance = new DatabaseManager();
                }
            }
        }
        return instance;
    }


    private void initDao(Context context){
         final  ReleaseOpenHelper helper = new ReleaseOpenHelper(context,"fast_pda.db");
         final Database db = helper.getWritableDb();
         mDaoSession = new DaoMaster(db).newSession();
         mUserProfileDao = mDaoSession.getUserProfileDao();
         mPackageInfoDao = mDaoSession.getPackageInfoDao();
         mBigPackDao = mDaoSession.getBigPackDao();
        mBigPackItemDao =  mDaoSession.getBigPackItemDao();
    }

    /***
     * 用户数据库
     * @return
     */
    public UserProfileDao getDao() {
        return mUserProfileDao;
    }

    /***
     * 包装信息数据库
     * @return
     */
    public PackageInfoDao getmPackageInfoDao() {
        return mPackageInfoDao;
    }

    /***
     * 大包标签数据库
     * @return
     */
    public BigPackDao getmBigPackDao() {
        return mBigPackDao;
    }
    /***
     * 大包标签小标签数据库
     * @return
     */
    public BigPackItemDao getmBigPackItemDao() {
        return mBigPackItemDao;
    }
}
