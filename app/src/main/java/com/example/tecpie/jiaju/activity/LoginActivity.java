package com.example.tecpie.jiaju.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.tecpie.jiaju.R;
import com.example.tecpie.jiaju.pojo.LoginBean;
import com.example.tecpie.jiaju.utils.HonyarWebServiceUtil;
import com.google.gson.Gson;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by AmeKing on 2016/7/27.
 */
@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity{
    private final String url = "file:///android_asset/login.html";
    @ViewInject(R.id.loginwv)
    private WebView webView;
    private WebSettings webSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webView.loadUrl(url);
        webSettings = webView.getSettings();
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new LoginJsInterface(),"login");
    }


    public class LoginJsInterface{
        @JavascriptInterface
        public void login(String userId,String psw){

            Gson gson = new Gson();
            HonyarWebServiceUtil webServiceUtil = new HonyarWebServiceUtil();
            String action = "login";
            String message = "{" +
                    "\"userid\":\"" + userId + "\"," +
                    "\"psw\":\"" + psw + "\"" +
                    "}";
            Log.i("Login",message);
            String result = webServiceUtil.webService(action,message);
            LoginBean bean = null;
            try {
                bean = gson.fromJson(result, LoginBean.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
            String msg = null;
            /*if( result==null){
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
            }*/
            if(result!=null){
                msg=bean.getMsg();
                Log.i("Login",msg);
            }
            //if(bean.getResult()){
            if(true){
                //Toast.makeText(getApplicationContext(), msg,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                JPushInterface.setAliasAndTags(LoginActivity.this,"test",null);
            }else{
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                Toast.makeText(LoginActivity.this,msg,Toast.LENGTH_SHORT).show();
            }

            Log.i("Login",result);

        }

        @JavascriptInterface
        public void toast(final String s){
            webView.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(LoginActivity.this,s,Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


}
