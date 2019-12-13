package com.step.pda.app.delegate.bottom;

import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.step.pda.app.delegate.PdaDelegate;

/**
 * Created by user on 2019-08-06.
 * 导航栏对应的 页面
 */

public abstract class BottomItemDelegate extends PdaDelegate implements View.OnKeyListener{
    // 再点一次退出程序时间设置
    private long mExitTime = 0;
    private static final int EXIT_TIME = 2000;
    @Override
    public void onResume() {
        super.onResume();
        final View rootView = getView();
        if (rootView != null) {
            rootView.setFocusableInTouchMode(true);
            rootView.requestFocus();
        }
    }


    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - mExitTime) > EXIT_TIME) {
                Toast.makeText(_mActivity, "双击退出" + getString(com.wang.avi.R.string.app_name), Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                _mActivity.finish();
                if (mExitTime != 0) {
                    mExitTime = 0;
                }
            }
            return true;
        }
        return false;
    }
}
