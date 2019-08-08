package com.step.pda.presenter;


import android.content.Context;

import com.step.pda.view.IView;

/**
 * Created by user on 2018-08-02.
 */

public abstract class BasePresenter<T extends IView>{
   protected  final T mView;
    protected final Context mContext;
   public BasePresenter(T view,Context context){
       this.mView = view;
       this.mContext = context;
    }
}
