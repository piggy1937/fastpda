package com.step.fastpda;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.honeywell.aidc.AidcManager;
import com.honeywell.aidc.BarcodeReader;
import com.step.pda.app.AccountManager;
import com.step.pda.app.Pda;
import com.step.pda.app.activity.ProxyActivity;
import com.step.pda.app.ui.launcher.ILauncherListener;
import com.step.pda.ec.contract.ISignContract;
import com.step.pda.ec.launcher.LauncherDelegate;
import com.step.pda.ec.login.SignInDelegate;
import com.step.pda.ec.main.EcBottomDelegate;

import qiu.niorgai.StatusBarCompat;

import static com.step.pda.app.Configurator.ConfigType.BARCODE_READER;
import static com.step.pda.app.Configurator.ConfigType.BARCODE_READER_ATTACH;

public class MainActivity extends ProxyActivity implements ISignContract.View,ILauncherListener {


    private static BarcodeReader barcodeReader;
    private static BarcodeReader barcodeReaderAttach;
    private AidcManager manager;
    //去重fragement
    private Fragment[] mFragments = new Fragment[4];
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        StatusBarCompat.translucentStatusBar(this, true);

    }

    /***
     * 初始化将要加载的fragement
     * @param savedInstanceState 保存
     */
    @Override
    protected void initContainer(@Nullable Bundle savedInstanceState) {
        final FrameLayout container = new FrameLayout(this);
        container.setId(R.id.delegate_container);
        setContentView(container);
        AidcManager.create(this, new AidcManager.CreatedCallback() {

            @Override
            public void onCreated(AidcManager aidcManager) {
                manager = aidcManager;
                barcodeReader = manager.createBarcodeReader();
                barcodeReaderAttach = manager.createBarcodeReader();
                if(barcodeReader == barcodeReaderAttach){
                    Log.e(
                            "aaa","12312");
                }
                Pda.getConfigurations().put(BARCODE_READER.name(),barcodeReader);
                Pda.getConfigurations().put(BARCODE_READER_ATTACH.name(),barcodeReaderAttach);
            }
        });

        if(savedInstanceState ==null){
            getSupportDelegate().loadRootFragment(R.id.delegate_container, new LauncherDelegate());
        }else{
        }
    }



    @Override
    public void onSignInSuccess() {
        Toast.makeText(this, "登录成功", Toast.LENGTH_LONG).show();
        getSupportDelegate().startWithPop(new EcBottomDelegate());
    }

    @Override
    public void onSignInError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSignUpSuccess() {
        Toast.makeText(this, "注册成功", Toast.LENGTH_LONG).show();
    }

    /***
     * 退出当前系统
     */
    @Override
    public void onSignOutSuccess() {
        AccountManager.setSignState(false);
        getSupportDelegate().startWithPop(new SignInDelegate());
    }

    @Override
    public void onLauncherFinish(LaunchFinishTag tag) {
        switch (tag) {
            case SIGNED:
                getSupportDelegate().startWithPop(new EcBottomDelegate());
                break;
            case NOT_SIGNED:
                getSupportDelegate().startWithPop(new SignInDelegate());
                break;
            default:
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (barcodeReader != null) {
            // close BarcodeReader to clean up resources.
            barcodeReader.close();
            barcodeReader = null;
        }
        if (barcodeReaderAttach != null) {
            // close BarcodeReader to clean up resources.
            barcodeReaderAttach.close();
            barcodeReaderAttach = null;
        }
        if (manager != null) {
            // close AidcManager to disconnect from the scanner service.
            // once closed, the object can no longer be used.
            manager.close();
        }
    }
    static BarcodeReader getBarcodeObject() {
        return barcodeReader;
    }

}
