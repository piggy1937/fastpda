package com.step.pda.ec.main.index;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.ScannerNotClaimedException;
import com.honeywell.aidc.ScannerUnavailableException;
import com.honeywell.aidc.TriggerStateChangeEvent;
import com.honeywell.aidc.UnsupportedPropertyException;
import com.step.pda.app.AccountManager;
import com.step.pda.app.Pda;
import com.step.pda.app.delegate.PdaDelegate;
import com.step.pda.app.util.callback.CallbackManager;
import com.step.pda.app.util.callback.CallbackType;
import com.step.pda.app.util.callback.IGlobalCallback;
import com.step.pda.ec.R;
import com.step.pda.ec.R2;
import com.step.pda.ec.contract.IMiniPackScanContract;
import com.step.pda.ec.database.PackageInfo;
import com.step.pda.ec.presenter.MiniPackScanPresenter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.step.pda.app.Configurator.ConfigType.BARCODE_READER;

/**
 * Created by user on 2019-08-07.
 *包装
 */

public class MiniPackingDelegateScan extends PdaDelegate implements View.OnClickListener,BarcodeReader.BarcodeListener, BarcodeReader.TriggerListener, IMiniPackScanContract.View  {
    private static final  int RES_CODE = 101;//保存
    private static final  int  MIN_MARK =0;
    private static final  int MAX_MARK =999999;
    @BindView(R2.id.ed_packing_sn)
    TextInputEditText mEdPackingSn;//小包标签
    @BindView(R2.id.ed_packing_quantity)
    TextInputEditText mEdPackingQuantity;//小包标签数量
    @BindView(R2.id.btn_packing_submit)
    AppCompatButton  mbtnPackingSubmit; //保存
    @BindView(R2.id.btn_packing_submit_next)
    AppCompatButton  mbtnPackingSubmitNext;//保存并继续
    @BindView(R2.id.btn_packing_submit_print)
    AppCompatButton  mbtnPackingSubmitPrint;//保存并继续
    @BindView(R2.id.cb_attach)
    CheckBox mcbAttach;
    @OnClick(R2.id.icon_packing_close)
    void onIconPackingClose(){
        onDestroy();
    }
    private  volatile  String type="";
    //小包标签
    private IMiniPackScanContract.Presenter mPresenter;
    @Override
    public Object setLayout() {
        return R.layout.delegate_packing_mini_add;
    }
    private BarcodeReader mBarcodeReader;
    private String lastModifyTime;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstance, View rootViw) {

        mPresenter = new MiniPackScanPresenter(this,getContext());
        mEdPackingSn.setCursorVisible(true);//隐藏光标
        mEdPackingSn.setFocusable(true);//失去焦点
        mEdPackingSn.requestFocus();
        mEdPackingSn.setFocusableInTouchMode(true);

        mbtnPackingSubmit.setOnClickListener(this);
        mbtnPackingSubmitNext.setOnClickListener(this);
        mEdPackingQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (start > 1) {
                    if (MIN_MARK != -1 && MAX_MARK != -1) {
                        Integer num =Integer.parseInt(s.toString());
                        if (num > MAX_MARK) {
                            s = String.valueOf(MAX_MARK);
                            mEdPackingQuantity.setText(s);
                        } else if (num < MIN_MARK) {
                            s = String.valueOf(MIN_MARK);
                            mEdPackingQuantity.setText(s);
                        }
                        mEdPackingQuantity.setSelection(s.length());
                    }
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && !s.equals("")) {
                    if (MIN_MARK != -1 && MAX_MARK != -1) {
                        int markVal = 0;
                        try {
                            markVal = Integer.parseInt(s.toString());
                        } catch (NumberFormatException e) {
                            markVal = 0;
                        }
                        if (markVal > MAX_MARK) {
                            mEdPackingQuantity.setText(String.valueOf(MAX_MARK));
                            mEdPackingQuantity.setSelection(String.valueOf(MAX_MARK).length());
                        }
                        return;
                    }
                    }
                }




        });


//        mbtnPackingSubmitPrint.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                 String filePath="";
//                PrintManager printManager = (PrintManager) getActivity().getSystemService(Context.PRINT_SERVICE);
//                PrintAttributes.Builder builder = new PrintAttributes.Builder();
//                builder.setColorMode(PrintAttributes.COLOR_MODE_COLOR);
//                printManager.print("barcode print", new MyPrintAdapter(getActivity(),filePath), builder.build());
//            }
//        });
        mBarcodeReader = (BarcodeReader) Pda.getConfigurations().get(BARCODE_READER.name());
        if(mBarcodeReader!=null){
            initBarcodeReader(mBarcodeReader);
        }
        mcbAttach.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    type="attach";
                }else{
                    type = "normal";
                }
            }
        });
        CallbackManager.getInstance()
                .addCallback(CallbackType.ON_SCAN_NORMAL, new IGlobalCallback<String>() {
                    @Override
                    public void executeCallback(@Nullable String barcode) {
                     addMiniInfo(barcode);
                    }
                });
    }

    /***
     * 初始化barcode
     * @param barcodeReader
     */
    private void initBarcodeReader(final BarcodeReader barcodeReader) {

        barcodeReader.addBarcodeListener(this);

        // set the trigger mode to client control
        try {
            barcodeReader.setProperty(BarcodeReader.PROPERTY_TRIGGER_CONTROL_MODE,
                    BarcodeReader.TRIGGER_CONTROL_MODE_AUTO_CONTROL);
        } catch (UnsupportedPropertyException e) {
            Toast.makeText(getContext(), "Failed to apply properties", Toast.LENGTH_SHORT).show();
        }
        // register trigger state change listener
        barcodeReader.addTriggerListener(this);

        Map<String, Object> properties = new HashMap<String, Object>();
        // Set Symbologies On/Off
        properties.put(BarcodeReader.PROPERTY_CODE_128_ENABLED, true);
        properties.put(BarcodeReader.PROPERTY_GS1_128_ENABLED, true);
        properties.put(BarcodeReader.PROPERTY_QR_CODE_ENABLED, true);
        properties.put(BarcodeReader.PROPERTY_CODE_39_ENABLED, true);
        properties.put(BarcodeReader.PROPERTY_DATAMATRIX_ENABLED, true);
        properties.put(BarcodeReader.PROPERTY_UPC_A_ENABLE, true);
        properties.put(BarcodeReader.PROPERTY_EAN_13_ENABLED, false);
        properties.put(BarcodeReader.PROPERTY_AZTEC_ENABLED, false);
        properties.put(BarcodeReader.PROPERTY_CODABAR_ENABLED, false);
        properties.put(BarcodeReader.PROPERTY_INTERLEAVED_25_ENABLED, false);
        properties.put(BarcodeReader.PROPERTY_PDF_417_ENABLED, false);
        // Set Max Code 39 barcode length
        properties.put(BarcodeReader.PROPERTY_CODE_39_MAXIMUM_LENGTH, 10);
        // Turn on center decoding
        properties.put(BarcodeReader.PROPERTY_CENTER_DECODE, true);
        // Enable bad read response
        properties.put(BarcodeReader.PROPERTY_NOTIFICATION_BAD_READ_ENABLED, true);
        // Apply the settings
        barcodeReader.setProperties(properties);
    }

    /***
     * 新增打印标签
     */
    private void addMiniInfo(String sn) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        if (checkForm()) {
            PackageInfo packageInfo = new PackageInfo();
            packageInfo.setSn(sn);
            packageInfo.setQuantity(Integer.parseInt(mEdPackingQuantity.getText().toString()));
            packageInfo.setCreator(AccountManager.getCreater());
            if(type.isEmpty()){
                type="normal";
            }
            packageInfo.setType(type);
            if (lastModifyTime != null && !lastModifyTime.isEmpty()) {
                try {
                    packageInfo.setLastModifyTime(simpleDateFormat.parse(lastModifyTime));
                } catch (ParseException e) {
                    Log.e("packing_delegate", e.getMessage());
                }
            }

            mPresenter.addMiniPackPrintTask(packageInfo);
        }
    }


    @Override
    public void onClick(View v) {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
//        Integer id = v.getId();
//        if(id==R.id.btn_packing_submit){
//            //保存数据到数据库
//            if(checkForm()) {
//                PackageInfo packageInfo = new PackageInfo();
//                packageInfo.setSn(mEdPackingSn.getText().toString());
//                packageInfo.setQuantity(Integer.parseInt(mEdPackingQuantity.getText().toString()));
//                packageInfo.setCreator(AccountManager.getCreater());
//                if(lastModifyTime!=null&&!lastModifyTime.isEmpty()){
//                    try {
//                        packageInfo.setLastModifyTime(new Date());
//                    } catch (Exception e) {
//                        Log.e("packing_delegate",e.getMessage());
//                    }
//                }
//                mPresenter.addMiniPackPrintTask(packageInfo);
//
////                if(rowId>0) {
////                    Toast.makeText(getContext(), "操作成功", Toast.LENGTH_SHORT).show();
////                }else{
////                    Toast.makeText(getContext(), "操作失败", Toast.LENGTH_SHORT).show();
////                }
////                Bundle bundle = new Bundle();
////                bundle.putSerializable("package_info",packageInfo);
////                 setFragmentResult(RES_CODE,bundle);
////              //  getSupportDelegate().pop();
////                getSupportDelegate().startWithPop(new IndexDelegate());
//
//            }
//        }else if(id==R.id.btn_packing_submit_next){
//            if(checkForm()) {
//                PackageInfo packageInfo = new PackageInfo();
//                packageInfo.setSn(mEdPackingSn.getText().toString());
//                packageInfo.setQuantity(Integer.parseInt(mEdPackingQuantity.getText().toString()));
//                if(lastModifyTime!=null&&!lastModifyTime.isEmpty()){
//                    try {
//                        packageInfo.setLastModifyTime(simpleDateFormat.parse(lastModifyTime));
//                    } catch (ParseException e) {
//                        Log.e("packing_delegate",e.getMessage());
//                    }
//                }
//                PackageInfoService packageInfoService = new PackageInfoService();
//                long rowId= packageInfoService.save(packageInfo);
//                if(rowId>0) {
//                    Toast.makeText(getContext(), "操作成功", Toast.LENGTH_SHORT).show();
//                }else{
//                    Toast.makeText(getContext(), "操作失败", Toast.LENGTH_SHORT).show();
//                }
//                mEdPackingSn.setText("");
//                mEdPackingQuantity.setText("");
//                mEdPackingQuantity.requestFocus();
//            }
//        }

    }

    private boolean checkForm(){
        String sn = mEdPackingSn.getText().toString();
        String quantity = mEdPackingQuantity.getText().toString();
        //mPresenter.existSn(sn);
        boolean isPass = true;
        if(sn==null||sn.isEmpty()){
            mEdPackingSn.setError("编号不允许为空");
            isPass = false;
        }
        Boolean flag =false;
//        PackageInfoService packageInfoService = new PackageInfoService();
//        Boolean flag = packageInfoService.existSn(sn);
        if(flag){
            mEdPackingSn.setError("编号已存在");
            Toast.makeText(getContext(), "编号已存在", Toast.LENGTH_SHORT).show();
            isPass = false;
        }
        if(quantity==null||quantity.isEmpty()){
            mEdPackingQuantity.setError("数量不允许为空");
            isPass = false;
        }
        return isPass;
    }

    public static MiniPackingDelegateScan newInstance() {
        return new MiniPackingDelegateScan();
    }
    @Override
    public void onResume() {
        super.onResume();
        if (mBarcodeReader != null) {
            try {
                mBarcodeReader.claim();
            } catch (ScannerUnavailableException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Scanner unavailable", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        if (mBarcodeReader != null) {
            // release the scanner claim so we don't get any scanner
            // notifications while paused.
            mBarcodeReader.release();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mBarcodeReader != null) {
            // unregister barcode event listener
            mBarcodeReader.removeBarcodeListener(this);

            // unregister trigger state change listener
            mBarcodeReader.removeTriggerListener(this);
        }
    }

    @Override
    public void onBarcodeEvent(BarcodeReadEvent event) {
        final String barcodeData = event.getBarcodeData();
        lastModifyTime= event.getTimestamp();

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mEdPackingSn.setText(barcodeData);
                final IGlobalCallback<String> callback = CallbackManager
                        .getInstance()
                        .getCallback(CallbackType.ON_SCAN_NORMAL);
                    if (callback != null) {
                    callback.executeCallback(barcodeData);
                }

            }
        });
    }

    @Override
    public void onFailureEvent(BarcodeFailureEvent barcodeFailureEvent) {
        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(getContext(), "No data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onTriggerEvent(TriggerStateChangeEvent event) {
        try {
            // only handle trigger presses
            // turn on/off aimer, illumination and decoding
            mBarcodeReader.aim(event.getState());
            mBarcodeReader.light(event.getState());
            mBarcodeReader.decode(event.getState());

        } catch (ScannerNotClaimedException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Scanner is not claimed", Toast.LENGTH_SHORT).show();
        } catch (ScannerUnavailableException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Scanner unavailable", Toast.LENGTH_SHORT).show();
        }
    }

    /***
     * presenter 回调函数
     * @param packageInfo
     */
    @Override
    public void onSuccess(PackageInfo packageInfo) {
        Bundle bundle = new Bundle();
        //bundle.putSerializable("package_info", packageInfo);
        //PackageInfoService packageInfoService = new PackageInfoService();
        //long rowId= packageInfoService.save(packageInfo);
        setFragmentResult(RES_CODE, bundle);
        mEdPackingQuantity.setText("0");
        mEdPackingSn.setText("");
        mEdPackingSn.requestFocus();
        //onDestroy();

    }

    /***
     * 失败回调函数
     * @param packageInfo
     * @param errmsg
     */
    @Override
    public void onError(PackageInfo packageInfo, String errmsg) {
        mEdPackingQuantity.setText("0");
        mEdPackingSn.setText("");
        mEdPackingSn.requestFocus();
        if(!errmsg.isEmpty()) {
            Toast.makeText(getContext(), errmsg, Toast.LENGTH_SHORT).show();
        }
    }
}
