package com.step.pda.ec.main.personal.child;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.step.pda.ec.R;
import com.step.pda.ec.contract.ISignContract;
import com.step.pda.ec.main.personal.PersonalDelegate;
import com.step.pda.ec.presenter.SignPresenter;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by YoKeyword on 16/6/6.
 */
public class MeFragment extends SupportFragment {

    private ISignContract.Presenter mPresenter;
    private TextView mTvBtnSettings;
    //登出按钮
   private AppCompatButton mBtnSignOut;

   private void onSignOut(){
        //退出系统
        mPresenter.requestSignOut();


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ISignContract.View) {
            mPresenter = new SignPresenter((ISignContract.View)activity,getContext());
        }
    }

    public static MeFragment newInstance() {

        Bundle args = new Bundle();

        MeFragment fragment = new MeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.delegate_fragment_person_me, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mTvBtnSettings = (TextView) view.findViewById(R.id.tv_btn_settings);
        mTvBtnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(SettingsFragment.newInstance());
            }
        });
        mBtnSignOut = view.findViewById(R.id.btn_person_sign_out);
        mBtnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSignOut();
            }
        });
    }

    @Override
    public boolean onBackPressedSupport() {
        // 这里实际项目中推荐使用 EventBus接耦
        ((PersonalDelegate)getParentFragment()).onBackToFirstFragment();
        return true;
    }
}
