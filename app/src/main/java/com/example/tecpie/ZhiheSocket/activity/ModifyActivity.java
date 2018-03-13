package com.example.tecpie.ZhiheSocket.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.tecpie.ZhiheSocket.R;
import com.example.tecpie.ZhiheSocket.entity.SocketEntity;
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
@ContentView(R.layout.activity_modify)
public class ModifyActivity extends BaseActivity{
    private final String url = "file:///android_asset/modify.html";
    private static final int REQUEST_QRCODE = 0x03;
    @ViewInject(R.id.modify)
    private WebView webView;
    private WebSettings webSettings;
    private  String SerialNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webView.loadUrl(url);
        webSettings = webView.getSettings();
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new LoginJsInterface(),"modify");
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
        public void confirm(String SN,String name,String type){
            //Toast.makeText(AddActivity.this,"进入confirm",Toast.LENGTH_SHORT).show();
            SharedPreferences preferences=getSharedPreferences("zigBee", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=preferences.edit();
            Gson gson = new Gson();
            int id = preferences.getInt("current-selectedId",0);
            String socketsJsonArray = preferences.getString("current-sockets", "");
            JsonParser parser = new JsonParser();
            JsonArray jsonArray = parser.parse(socketsJsonArray).getAsJsonArray();
            List<SocketEntity> sockets = new ArrayList<SocketEntity>();
            for (JsonElement s : jsonArray) {
                //使用GSON，直接转成Bean对象
                SocketEntity socketEntity = gson.fromJson(s, SocketEntity.class);
                if(socketEntity.getId()==id){
                    socketEntity.setName(name);
                    socketEntity.setSerialNumber(SN);
                    socketEntity.setMac(preferences.getString("current-wifiName",""));
                    socketEntity.setType(type);
                    socketEntity.setState(0);
                }
                sockets.add(socketEntity);
            }
            String newSocketsJsonArray = gson.toJson(sockets);
            editor.putString("current-sockets",newSocketsJsonArray);
            editor.commit();
            //Toast.makeText(AddActivity.this,"离开confirm",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ModifyActivity.this,SocketManageActivity.class);
            startActivity(intent);
            JPushInterface.setAliasAndTags(ModifyActivity.this,"test",null);
        }

        @JavascriptInterface
        public void scan(){
            //new NettyClient("192.168.1.199", 10086, "com.example.tecpie.ZhiheSocket.utils.ModbusClientHandle", "ModbusClient").start();
            //Toast.makeText(ModifyActivity.this,"scan",Toast.LENGTH_SHORT).show();
            startActivityForResult(new Intent(ModifyActivity.this, CaptureActivity.class),REQUEST_QRCODE);
            JPushInterface.setAliasAndTags(ModifyActivity.this,"test",null);

        }

        @JavascriptInterface
        public void delete(){
            //Toast.makeText(AddActivity.this,"进入confirm",Toast.LENGTH_SHORT).show();
            SharedPreferences preferences=getSharedPreferences("zigBee", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=preferences.edit();
            Gson gson = new Gson();
            int id = preferences.getInt("current-selectedId",0);
            int index = preferences.getInt("current-id",0);
            editor.putInt("current-id",index-1);
            String socketsJsonArray = preferences.getString("current-sockets", "");
            JsonParser parser = new JsonParser();
            JsonArray jsonArray = parser.parse(socketsJsonArray).getAsJsonArray();
            List<SocketEntity> sockets = new ArrayList<SocketEntity>();
            for (JsonElement s : jsonArray) {
                //使用GSON，直接转成Bean对象
                SocketEntity socketEntity = gson.fromJson(s, SocketEntity.class);
                if(socketEntity.getId()==id){
                   continue;
                }
                sockets.add(socketEntity);
            }
            List<SocketEntity> socketsRe = new ArrayList<SocketEntity>();
            for(SocketEntity s:sockets){
                if(s.getId()>id){
                   s.setId(s.getId()-1);
                }
                socketsRe.add(s);
            }
            String newSocketsJsonArray = gson.toJson(socketsRe);
            editor.putString("current-sockets",newSocketsJsonArray);
            editor.commit();
            //Toast.makeText(AddActivity.this,"离开confirm",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ModifyActivity.this,SocketManageActivity.class);
            startActivity(intent);
            JPushInterface.setAliasAndTags(ModifyActivity.this,"test",null);
        }

        @JavascriptInterface
        public String getSN(){
            SharedPreferences preferences=getSharedPreferences("zigBee", Context.MODE_PRIVATE);
            return preferences.getString("current-selectedSN","");

        }

        @JavascriptInterface
        public String getScanSN(){
            if(SerialNumber!=null){
                return SerialNumber;
            }
            return "";
        }
        @JavascriptInterface
        public String getName(){
            SharedPreferences preferences=getSharedPreferences("zigBee", Context.MODE_PRIVATE);
            return preferences.getString("current-selectedName","");

        }
        @JavascriptInterface
        public String getType(){
            SharedPreferences preferences=getSharedPreferences("zigBee", Context.MODE_PRIVATE);
            return preferences.getString("current-selectedType","");

        }


        @JavascriptInterface
        public void back() {
            ModifyActivity.this.finish();
        }



        @JavascriptInterface
        public void toast(final String s){
            webView.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(ModifyActivity.this,s,Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


}
