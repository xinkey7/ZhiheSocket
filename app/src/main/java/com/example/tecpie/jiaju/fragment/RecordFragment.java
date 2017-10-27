package com.example.tecpie.jiaju.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.tecpie.jiaju.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by AmeKing on 2016/7/27.
 */
@ContentView(R.layout.fragment_record)
public class RecordFragment extends BaseFragment {
    private final String url = "file:///android_asset/record.html";
    @ViewInject(R.id.recordwv)
    private WebView webView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view  = super.onCreateView(inflater, container, savedInstanceState);
        webView.loadUrl(url);
        return view;
    }
}
