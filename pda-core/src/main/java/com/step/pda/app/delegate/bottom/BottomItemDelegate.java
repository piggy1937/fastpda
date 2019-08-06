package com.step.pda.app.delegate.bottom;

import android.widget.Toast;

import com.step.pda.R;
import com.step.pda.app.Pda;
import com.step.pda.app.delegate.PdaDelagete;

/**
 * Created by user on 2019-08-06.
 * 页面
 */

public abstract class BottomItemDelegate extends PdaDelagete {
    // 再点一次退出程序时间设置
    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;

    @Override
    public boolean onBackPressedSupport() {
        if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
            _mActivity.finish();
        } else {
            TOUCH_TIME = System.currentTimeMillis();
            Toast.makeText(_mActivity, "双击退出" + Pda.getApplicationContext().getString(R.string.app_name), Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}
