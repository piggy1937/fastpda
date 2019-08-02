package com.step.pda.ec.login;

import com.step.pda.app.AccountManager;
import com.step.pda.ec.database.DatabaseManager;
import com.step.pda.ec.database.UserProfile;

/**
 * Created by user on 2019-08-02.
 */

public class SignHandler {
    public static void onSignUp(String response,ISiginListener siginListener){
        final UserProfile userProfile = new UserProfile();
        DatabaseManager.getInstance().getDao().insert(userProfile) ;
        ////保存用户状态
        AccountManager.setSignState(true);
        siginListener.onSignUpSuccess();
    }
}
