package com.step.pda.ec.database;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.step.pda.ec.database.BigPack;
import com.step.pda.ec.database.BigPackItem;
import com.step.pda.ec.database.PackageInfo;
import com.step.pda.ec.database.UserProfile;

import com.step.pda.ec.database.BigPackDao;
import com.step.pda.ec.database.BigPackItemDao;
import com.step.pda.ec.database.PackageInfoDao;
import com.step.pda.ec.database.UserProfileDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig bigPackDaoConfig;
    private final DaoConfig bigPackItemDaoConfig;
    private final DaoConfig packageInfoDaoConfig;
    private final DaoConfig userProfileDaoConfig;

    private final BigPackDao bigPackDao;
    private final BigPackItemDao bigPackItemDao;
    private final PackageInfoDao packageInfoDao;
    private final UserProfileDao userProfileDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        bigPackDaoConfig = daoConfigMap.get(BigPackDao.class).clone();
        bigPackDaoConfig.initIdentityScope(type);

        bigPackItemDaoConfig = daoConfigMap.get(BigPackItemDao.class).clone();
        bigPackItemDaoConfig.initIdentityScope(type);

        packageInfoDaoConfig = daoConfigMap.get(PackageInfoDao.class).clone();
        packageInfoDaoConfig.initIdentityScope(type);

        userProfileDaoConfig = daoConfigMap.get(UserProfileDao.class).clone();
        userProfileDaoConfig.initIdentityScope(type);

        bigPackDao = new BigPackDao(bigPackDaoConfig, this);
        bigPackItemDao = new BigPackItemDao(bigPackItemDaoConfig, this);
        packageInfoDao = new PackageInfoDao(packageInfoDaoConfig, this);
        userProfileDao = new UserProfileDao(userProfileDaoConfig, this);

        registerDao(BigPack.class, bigPackDao);
        registerDao(BigPackItem.class, bigPackItemDao);
        registerDao(PackageInfo.class, packageInfoDao);
        registerDao(UserProfile.class, userProfileDao);
    }
    
    public void clear() {
        bigPackDaoConfig.clearIdentityScope();
        bigPackItemDaoConfig.clearIdentityScope();
        packageInfoDaoConfig.clearIdentityScope();
        userProfileDaoConfig.clearIdentityScope();
    }

    public BigPackDao getBigPackDao() {
        return bigPackDao;
    }

    public BigPackItemDao getBigPackItemDao() {
        return bigPackItemDao;
    }

    public PackageInfoDao getPackageInfoDao() {
        return packageInfoDao;
    }

    public UserProfileDao getUserProfileDao() {
        return userProfileDao;
    }

}
