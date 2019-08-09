package com.step.pda.ec.main.pack;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.step.pda.app.delegate.PdaDelegate;
import com.step.pda.ec.R;
import com.step.pda.ec.R2;
import com.step.pda.ec.database.PackageInfo;
import com.step.pda.ec.main.index.IndexDelegate;
import com.step.pda.ec.services.PackageInfoService;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by user on 2019-08-07.
 *包装
 */

public class PackingDelegate extends PdaDelegate implements View.OnClickListener {
    private static final  int RES_CODE = 101;//保存
    private static final  int  MIN_MARK =0;
    private static final  int MAX_MARK =100;
    @BindView(R2.id.ed_packing_sn)
    TextInputEditText mEdPackingSn;//小包标签
    @BindView(R2.id.ed_packing_quantity)
    TextInputEditText mEdPackingQuantity;//小包标签数量
    @BindView(R2.id.btn_packing_submit)
    AppCompatButton  mbtnPackingSubmit; //保存
    @BindView(R2.id.btn_packing_submit_next)
    AppCompatButton  mbtnPackingSubmitNext;//保存并继续
    @OnClick(R2.id.icon_packing_close)
    void onIconPackingClose(){
       getSupportDelegate().startWithPop(new IndexDelegate());
    }
    @Override
    public Object setLayout() {
        return R.layout.delegate_packing;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstance, View rootViw) {
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
    }

    @Override
    public void onClick(View v) {
        Integer id = v.getId();
        if(id==R.id.btn_packing_submit){
            //保存数据到数据库
            if(checkForm()) {
                PackageInfo packageInfo = new PackageInfo();
                packageInfo.setSn(mEdPackingSn.getText().toString());
                packageInfo.setQuantity(Integer.parseInt(mEdPackingQuantity.getText().toString()));
                PackageInfoService packageInfoService = new PackageInfoService();
                long rowId= packageInfoService.save(packageInfo);
                if(rowId>0) {
                    Toast.makeText(getContext(), "操作成功", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), "操作失败", Toast.LENGTH_SHORT).show();
                }
                Bundle bundle = new Bundle();
                bundle.putSerializable("package_info",packageInfo);
                 setFragmentResult(RES_CODE,bundle);
              //  getSupportDelegate().pop();
                getSupportDelegate().startWithPop(new IndexDelegate());

            }
        }else if(id==R.id.btn_packing_submit_next){
            if(checkForm()) {
                PackageInfo packageInfo = new PackageInfo();
                packageInfo.setSn(mEdPackingSn.getText().toString());
                packageInfo.setQuantity(Integer.parseInt(mEdPackingQuantity.getText().toString()));
                PackageInfoService packageInfoService = new PackageInfoService();
                long rowId= packageInfoService.save(packageInfo);
                if(rowId>0) {
                    Toast.makeText(getContext(), "操作成功", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), "操作失败", Toast.LENGTH_SHORT).show();
                }
                mEdPackingSn.setText("");
                mEdPackingQuantity.setText("");
                mEdPackingSn.requestFocus();
            }
        }

    }

    private boolean checkForm(){
        String sn = mEdPackingSn.getText().toString();
        String quantity = mEdPackingQuantity.getText().toString();
        boolean isPass = true;
        if(sn==null||sn.isEmpty()){
            mEdPackingSn.setError("编号不允许为空");
            isPass = false;
        }
        if(quantity==null||quantity.isEmpty()){
            mEdPackingQuantity.setError("数量不允许为空");
            isPass = false;
        }
        return isPass;
    }

    public static PackingDelegate newInstance() {
        return new PackingDelegate();
    }
}
