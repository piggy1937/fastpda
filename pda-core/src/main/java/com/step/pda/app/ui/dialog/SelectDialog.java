package com.step.pda.app.ui.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import com.step.pda.R;

/**
 * Created by user on 2019-08-06.
 * 选择dialog
 */
public class SelectDialog extends AlertDialog {
    public SelectDialog(Context context) {
        super(context);
    }

    public SelectDialog(Context context, int style) {
        super(context,style);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_select);
    }
}
