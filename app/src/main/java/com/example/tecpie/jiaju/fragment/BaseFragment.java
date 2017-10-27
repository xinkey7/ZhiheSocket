package com.example.tecpie.jiaju.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.xutils.x;

/**
 * Created by Think on 2016/1/22.
 */
public class BaseFragment extends Fragment {
    public static final String gateway="0x00124b000cb748d5";
    private boolean injected = false;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        injected = true;
        return x.view().inject(this,inflater,container);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(!injected){
            x.view().inject(this,this.getView());
        }
    }
}
