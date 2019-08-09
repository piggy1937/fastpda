package com.step.fastpda;

import android.content.Context;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import com.step.pda.app.activity.ProxyActivity;
import com.step.pda.ec.adapter.MyPrintAdapter;

/**
 * Created by user on 2019-08-09.
 */

public class PrintActivity extends ProxyActivity {
    private AppCompatButton mBtnPrintPrint;
    @Override
    protected void initContainer(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_print);
        mBtnPrintPrint= findViewById(R.id.btn_print_print);
        if(mBtnPrintPrint!=null) {
            mBtnPrintPrint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onPrint(v);//打印
                }
            });
        }
    }

    private void onPrint(View v) {
        String filePath="";
        PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);
        PrintAttributes.Builder builder = new PrintAttributes.Builder();
        builder.setColorMode(PrintAttributes.COLOR_MODE_COLOR);
        printManager.print("test pdf print", new MyPrintAdapter(this,filePath), builder.build());
    }
}
