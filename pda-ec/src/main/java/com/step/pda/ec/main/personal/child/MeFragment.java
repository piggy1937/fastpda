package com.step.pda.ec.main.personal.child;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.step.pda.ec.R;
import com.step.pda.ec.main.personal.PersonalDelegate;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by YoKeyword on 16/6/6.
 */
public class MeFragment extends SupportFragment {
    private TextView mTvBtnSettings;

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
    }

    @Override
    public boolean onBackPressedSupport() {
        // 这里实际项目中推荐使用 EventBus接耦
        ((PersonalDelegate)getParentFragment()).onBackToFirstFragment();
        return true;
    }
}
