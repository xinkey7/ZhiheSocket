package com.example.tecpie.ZhiheSocket.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.tecpie.ZhiheSocket.R;
import com.example.tecpie.ZhiheSocket.entity.NetEntity;
import com.example.tecpie.ZhiheSocket.entity.SocketEntity;
import com.example.tecpie.ZhiheSocket.utils.ParseGson;
import com.example.tecpie.ZhiheSocket.zxing.app.CaptureActivity;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by AmeKing on 2016/7/27.
 */
@ContentView(R.layout.activity_add2)
public class Add2Activity extends BaseActivity{
    private final String url = "file:///android_asset/add2.html";
    private static final int REQUEST_QRCODE = 0x05;
    private  String SerialNumber;

    @ViewInject(R.id.add2)
    private WebView webView;
    private WebSettings webSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webView.loadUrl(url);
        webSettings = webView.getSettings();
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new LoginJsInterface(),"add");

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
        public void back() {
            Add2Activity.this.finish();
        }

        @JavascriptInterface
        public void scan(){
            //new NettyClient("192.168.1.199", 10086, "com.example.tecpie.ZhiheSocket.utils.ModbusClientHandle", "ModbusClient").start();

            startActivityForResult(new Intent(Add2Activity.this, CaptureActivity.class),REQUEST_QRCODE);
            JPushInterface.setAliasAndTags(Add2Activity.this,"test",null);

        }

        @JavascriptInterface
        public String getSN(){
            if(SerialNumber!=null){
                return SerialNumber;
            }
            return "";
        }

        @JavascriptInterface
        public void confirm(String SN,String name,String type){
            //Toast.makeText(AddActivity.this,"进入confirm",Toast.LENGTH_SHORT).show();
            SharedPreferences preferences=getSharedPreferences("zigBee", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=preferences.edit();
            Gson gson = new Gson();
            List<NetEntity> netEntities = ParseGson.parseNetEntityJasonArray(preferences.getString("collection-netEntities",""));
            List<SocketEntity> sockets = new ArrayList<SocketEntity>();
            for(int i=0;i<netEntities.size();i++){
                if(netEntities.get(i).getMac().equals(preferences.getString("current-mac",""))){
                    sockets = netEntities.get(i).getSockets();
                    break;
                }
            }
            int maxid = 1;
            for(int i=0;i<sockets.size();i++){
                if(sockets.get(i).getId()>maxid){
                    maxid = sockets.get(i).getId();
                }
            }
            maxid = maxid+1;
            SocketEntity socket = new SocketEntity();

            socket.setId(maxid);
            socket.setName(name);
            socket.setSerialNumber(SN);
            socket.setMac(preferences.getString("current-mac",""));
            socket.setType(type);
            socket.setState(0);
            sockets.add(socket);
            for(int i=0;i<netEntities.size();i++){
                if(netEntities.get(i).getMac().equals(preferences.getString("current-mac",""))){
                    netEntities.get(i).setSockets(sockets);
                    break;
                }
            }
            Log.i("add2",gson.toJson(netEntities));
            editor.putString("collection-netEntities",gson.toJson(netEntities));

            editor.commit();
            //Toast.makeText(AddActivity.this,"离开confirm",Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();




        }



        @JavascriptInterface
        public void toast(final String s){
            webView.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(Add2Activity.this,s,Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


}
