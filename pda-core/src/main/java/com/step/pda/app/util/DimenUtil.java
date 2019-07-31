package com.step.pda.app.util;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.step.pda.app.Pda;

/**
 * Created by user on 2019-07-31.
 */

public class DimenUtil {
    /***
     * 获取皮病母宽度
     * @return
     */
    public static int getScreenWidth(){
       final Resources resource  = Pda.getApplicationContext().getResources();
       final DisplayMetrics dm = resource.getDisplayMetrics();
       return dm.widthPixels;
    }

    /***
     * 获取屏幕高度
     * @return
     */
    public static int getScreenHeight(){
       final Resources resource  = Pda.getApplicationContext().getResources();
       final DisplayMetrics dm = resource.getDisplayMetrics();
       return dm.heightPixels;
    }
}
