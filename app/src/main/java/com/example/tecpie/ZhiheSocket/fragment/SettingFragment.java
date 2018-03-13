package com.example.tecpie.ZhiheSocket.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.tecpie.ZhiheSocket.R;
import com.example.tecpie.ZhiheSocket.activity.DeviceInfoActivity;
import com.example.tecpie.ZhiheSocket.activity.LoginActivity;
import com.example.tecpie.ZhiheSocket.activity.SceneActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by AmeKing on 2016/7/27.
 */
@ContentView(R.layout.fragment_setting)
public class SettingFragment extends BaseFragment {
    private final String log="SettingFragment";
    private final String url = "file:///android_asset/setting.html";
    @ViewInject(R.id.settingwv)
    private WebView webView;
    private WebSettings webSettings;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view  = super.onCreateView(inflater, container, savedInstanceState);
        webView.loadUrl(url);
        webSettings = webView.getSettings();
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient() {

        });
        webView.addJavascriptInterface(new IndexJsInterface(), "setting");
        return view;

    }


    public class IndexJsInterface {
        @JavascriptInterface
        public void scene() {
            Log.i(log,"进入scene");
            Intent intent = new Intent(getActivity(),SceneActivity.class );
            startActivity(intent);
        }

        @JavascriptInterface
        public void automation() {
            Log.i(log,"进入automation");
            Intent intent = new Intent(getActivity(), DeviceInfoActivity.class);
            startActivity(intent);
        }

        @JavascriptInterface
        public void logout() {
            Log.i(log,"进入logout");
            SharedPreferences preferences= getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
            preferences.edit().clear().commit();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }

        @JavascriptInterface
        public void toast(final String s) {
            webView.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
