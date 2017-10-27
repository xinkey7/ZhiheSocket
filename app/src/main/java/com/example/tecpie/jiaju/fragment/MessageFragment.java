package com.example.tecpie.jiaju.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.tecpie.jiaju.Jiajuapp;
import com.example.tecpie.jiaju.R;
import com.example.tecpie.jiaju.pojo.CustomMessage;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.logging.LogRecord;

/**
 * Created by AmeKing on 2016/7/27.
 */
@ContentView(R.layout.fragment_message)
public class MessageFragment extends BaseFragment {
    public static final int MSG_RELOAD = 1;
    private final String url = "file:///android_asset/message.html";
    @ViewInject(R.id.messagewv)
    private WebView webView;
    private WebSettings webSettings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view  = super.onCreateView(inflater, container, savedInstanceState);
        webView.loadUrl(url);
        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new MessageJsInterface(),"message");
        return view;
    }

    public static class MessageHandler extends Handler{
        private  WeakReference<MessageFragment> mFragment;

        public MessageHandler(MessageFragment fragment) {
            this.mFragment = new WeakReference<MessageFragment>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            MessageFragment fragment = mFragment.get();
            switch (msg.what){
                case MSG_RELOAD:
                    if(fragment.webView!=null) {
                        fragment.webView.reload();
                    }
            }
        }
    }

    class MessageJsInterface{
        @JavascriptInterface
        public String loadMessage(){
            ArrayList<CustomMessage> list = Jiajuapp.messageList;
            StringBuffer htmlBuffer = new StringBuffer();
            for(int i=0;i< list.size();i++){
                htmlBuffer.append("<li>"+
                "<div class=\"avatar\"><img src=\"img/logo.png\"></div>"+
                "<div class=\"body\">"+
                "<h5 class=\"name\">"+list.get(i).getTitle()+"</h5>"+
                "<span class=\"date\">"+list.get(i).getDate()+"</span>"+
                "<p class=\"message\">"+list.get(i).getMessage()+"!</p>"+
                "</div>"+
                "</li>");
            }
            return htmlBuffer.toString();
        }
    }

}
