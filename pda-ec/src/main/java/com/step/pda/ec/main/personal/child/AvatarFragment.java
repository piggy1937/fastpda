package com.step.pda.ec.main.personal.child;

import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.step.pda.ec.R;
import com.step.pda.ec.database.DatabaseManager;
import com.step.pda.ec.database.UserProfile;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by YoKeyword on 16/6/6.
 */
public class AvatarFragment extends SupportFragment {
    //用户名
    private AppCompatTextView mTvUsername;
    private UserProfile mUserProfile;
    public static AvatarFragment newInstance() {

        Bundle args = new Bundle();

        AvatarFragment fragment = new AvatarFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@android.support.annotation.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserProfile = DatabaseManager.getInstance().getDao().load(1L);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.delegate_fragment_person_avatar, container, false);
        mTvUsername = view.findViewById(R.id.tv_person_username);
        if(mUserProfile!=null) {
            mTvUsername.setText(mUserProfile.getName());
        }
        return view;
    }


}
