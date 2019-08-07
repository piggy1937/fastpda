package com.step.pda.ec.main.pack;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Toast;

import com.step.pda.app.delegate.PdaDelegate;
import com.step.pda.ec.R;
import com.step.pda.ec.R2;
import com.step.pda.ec.database.PackageInfo;
import com.step.pda.ec.services.PackageInfoService;

import butterknife.BindView;

/**
 * Created by user on 2019-08-07.
 *包装
 */

public class PackingDelegate extends PdaDelegate implements View.OnClickListener {
    private static final  int RES_CODE = 101;//保存
    @BindView(R2.id.ed_packing_sn)
    TextInputEditText mEdPackingSn;//小包标签
    @BindView(R2.id.ed_packing_quantity)
    TextInputEditText mEdPackingQuantity;//小包标签数量
    @BindView(R2.id.btn_packing_submit)
    AppCompatButton  mbtnPackingSubmit; //保存
    @BindView(R2.id.btn_packing_submit_next)
    AppCompatButton  mbtnPackingSubmitNext;//保存并继续


    @Override
    public Object setLayout() {
        return R.layout.delegate_packing;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstance, View rootViw) {
        mbtnPackingSubmit.setOnClickListener(this);
        mbtnPackingSubmitNext.setOnClickListener(this);
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
                bundle.putInt("errCode",0);
                setFragmentResult(RES_CODE,bundle);
                pop();
            }
        }else if(id==R.id.btn_packing_submit_next){
            if(checkForm()) {
                Toast.makeText(getContext(), "submit_next", Toast.LENGTH_SHORT).show();
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


}
