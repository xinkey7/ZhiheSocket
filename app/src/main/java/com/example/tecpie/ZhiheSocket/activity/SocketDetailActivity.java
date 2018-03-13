package com.example.tecpie.ZhiheSocket.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.tecpie.ZhiheSocket.Cutil.bean.SocketDataResponse;
import com.example.tecpie.ZhiheSocket.R;
import com.example.tecpie.ZhiheSocket.zxing.app.CaptureActivity;
import com.google.gson.Gson;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by AmeKing on 2016/7/27.
 */
@ContentView(R.layout.activity_socket_detail)
public class SocketDetailActivity extends BaseActivity{
    private final String url = "file:///android_asset/socketdetail.html";
    @ViewInject(R.id.socketdetail)
    private WebView webView;
    private static final int REQUEST_QRCODE = 0x04;
    private WebSettings webSettings;
    private  String SerialNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webView.loadUrl(url);
        webSettings = webView.getSettings();
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new LoginJsInterface(),"socketDetail");
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_QRCODE:
                if (resultCode == Activity.RESULT_OK) {
                    //扫描后的业务逻辑
                    String code = data.getStringExtra("SCAN_RESULT");
                    if (code.contains("http") || code.contains("https")) {
                        //打开链接
                        /*Intent intent = new Intent(this, AdBrowserActivity.class);
                        intent.putExtra(AdBrowserActivity.KEY_URL, code);
                        startActivity(intent);*/
                        Toast.makeText(this, code, Toast.LENGTH_SHORT).show();

                    } else {
                        SerialNumber = code;
                        webView.reload();
                        //Toast.makeText(this, code, Toast.LENGTH_SHORT).show();
                    }
                }else if(resultCode == 300){
                    //从本地相册扫描后的业务逻辑
                    String code = data.getStringExtra("LOCAL_PHOTO_RESULT");
                    Toast.makeText(this, code, Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }


    public class LoginJsInterface{


        @JavascriptInterface
        public void deleteSocket(){
        }
        @JavascriptInterface
        public void scan(){

            startActivityForResult(new Intent(SocketDetailActivity.this, CaptureActivity.class),REQUEST_QRCODE);
            JPushInterface.setAliasAndTags(SocketDetailActivity.this,"test",null);

        }





        @JavascriptInterface
        public void back() {
            SocketDetailActivity.this.finish();
        }

        @JavascriptInterface
        public String getSN(){
            if(SerialNumber!=null){
                return SerialNumber;
            }
            Gson gson = new Gson();
            String responseJson = getIntent().getStringExtra("socketData");
            SocketDataResponse socketDataResponse = gson.fromJson(responseJson,SocketDataResponse.class);
            return transfer(socketDataResponse.getIeee().substring(2,socketDataResponse.getIeee().length()));
        }
        @JavascriptInterface
        public String getName(){

            String responseJson = getIntent().getStringExtra("socketName");
            return getIntent().getStringExtra("socketName");
        }
        @JavascriptInterface
        public String getState(){
            Gson gson = new Gson();
            String responseJson = getIntent().getStringExtra("socketData");
            SocketDataResponse socketDataResponse = gson.fromJson(responseJson,SocketDataResponse.class);
            return ""+socketDataResponse.getOnline();
        }
        @JavascriptInterface
        public String getPower(){
            Gson gson = new Gson();
            String responseJson = getIntent().getStringExtra("socketData");
            SocketDataResponse socketDataResponse = gson.fromJson(responseJson,SocketDataResponse.class);
            return ""+socketDataResponse.getPower();
        }
        @JavascriptInterface
        public String getConsum(){
            Gson gson = new Gson();
            String responseJson = getIntent().getStringExtra("socketData");
            SocketDataResponse socketDataResponse = gson.fromJson(responseJson,SocketDataResponse.class);
            return ""+socketDataResponse.getConsum();
        }
        @JavascriptInterface
        public String getVol(){
            Gson gson = new Gson();
            String responseJson = getIntent().getStringExtra("socketData");
            SocketDataResponse socketDataResponse = gson.fromJson(responseJson,SocketDataResponse.class);
            return ""+socketDataResponse.getVol();
        }
        @JavascriptInterface
        public String getCur(){
            Gson gson = new Gson();
            String responseJson = getIntent().getStringExtra("socketData");
            SocketDataResponse socketDataResponse = gson.fromJson(responseJson,SocketDataResponse.class);
            return ""+socketDataResponse.getCur();
        }

        @JavascriptInterface
        public void toast(final String s){
            webView.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(SocketDetailActivity.this,s,Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    public  String transfer(String str){
        String[] strs = str.split("");
        String result = "";
        for(int i=strs.length;i<=strs.length;i--){
            result +=strs[i-2]+strs[i-1];
            i--;
            if(i<3){
                break;
            }
        }
        return result;
    }


}
