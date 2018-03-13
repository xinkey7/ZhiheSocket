package com.example.tecpie.ZhiheSocket.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.tecpie.ZhiheSocket.Cutil.bean.BaseRequest;
import com.example.tecpie.ZhiheSocket.Cutil.bean.BaseResponse;
import com.example.tecpie.ZhiheSocket.Cutil.bean.ChannelBean;
import com.example.tecpie.ZhiheSocket.Cutil.bean.DispatchSocketRequest;
import com.example.tecpie.ZhiheSocket.Cutil.bean.ReadSocketResponse;
import com.example.tecpie.ZhiheSocket.Cutil.bean.SocketBean;
import com.example.tecpie.ZhiheSocket.Cutil.service.RequestService;
import com.example.tecpie.ZhiheSocket.Cutil.serviceImpl.RequestServiceImpl;
import com.example.tecpie.ZhiheSocket.R;
import com.example.tecpie.ZhiheSocket.entity.IndexEntity;
import com.example.tecpie.ZhiheSocket.entity.NetEntity;
import com.example.tecpie.ZhiheSocket.entity.SocketEntity;
import com.example.tecpie.ZhiheSocket.pojo.FBoxLoginBean;
import com.example.tecpie.ZhiheSocket.utils.FBoxUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by AmeKing on 2016/7/27.
 */
@ContentView(R.layout.activity_socket_manage)
public class SocketManageActivity extends BaseActivity{
    private final String url = "file:///android_asset/record.html";
    @ViewInject(R.id.socketmanage)
    private WebView webView;
    private WebSettings webSettings;
    private File sdCardDir;
    private String AbsolutePath;
    private RequestService requestService = new RequestServiceImpl();
    private FileOutputStream outStream;
    private File saveFile;
    private File saveDir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //得到SD卡路径
        sdCardDir = Environment.getExternalStorageDirectory();
        AbsolutePath = (String) sdCardDir.getAbsolutePath();
        //Toast.makeText(this, "路径为"+AbsolutePath+"sdCardDir::"+sdCardDir, Toast.LENGTH_LONG).show();
        saveDir = new File(AbsolutePath+"/zigBeeOutput");
        webView.loadUrl(url);
        webSettings = webView.getSettings();
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new LoginJsInterface(),"socketManage");
        //得到SD卡路径
        sdCardDir = Environment.getExternalStorageDirectory();
        AbsolutePath = (String) sdCardDir.getAbsolutePath();
        //Toast.makeText(this, "路径为"+AbsolutePath+"sdCardDir::"+sdCardDir, Toast.LENGTH_LONG).show();

        if(!saveDir.exists()){
            saveDir.mkdirs();
        }


    }


    public class LoginJsInterface{

        @JavascriptInterface
        public void netinfo(){

            Intent intent = new Intent(SocketManageActivity.this,NetDetailActivity.class);
            intent.putExtra("current",true);
            startActivity(intent);
        }

        @JavascriptInterface
        public void automation(){
                Intent intent = new Intent(SocketManageActivity.this,MainActivity.class);
                startActivity(intent);
                JPushInterface.setAliasAndTags(SocketManageActivity.this,"test",null);

        }


        @JavascriptInterface
        public void open(){
            SharedPreferences preferences=getSharedPreferences("zigBee", Context.MODE_PRIVATE);
            if(preferences.getString("current-mac","").equals("")){
                Toast.makeText(SocketManageActivity.this,"请先建立连接并通讯",Toast.LENGTH_SHORT).show();
                return;
            }
            // Toast.makeText(SocketInfoActivity.this,"开网中",Toast.LENGTH_SHORT).show();

            //闭网
            ChannelHandlerContext ctx = ChannelBean.getChannel("channel");
            requestService.baseRequest(ctx, "0002", "gateway");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            BaseResponse baseResponse = ChannelBean.getBaseResponse();
            if(baseResponse.getResult()==1){
                Toast.makeText(SocketManageActivity.this,"开网成功",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(SocketManageActivity.this,"开网失败",Toast.LENGTH_SHORT).show();
            }
        }

        @JavascriptInterface
        public void close(){
            SharedPreferences preferences=getSharedPreferences("zigBee", Context.MODE_PRIVATE);
            if(preferences.getString("current-mac","").equals("")){
                Toast.makeText(SocketManageActivity.this,"请先建立连接并通讯",Toast.LENGTH_SHORT).show();
                return;
            }
            // Toast.makeText(SocketInfoActivity.this,"开网中",Toast.LENGTH_SHORT).show();

            //开网
            ChannelHandlerContext ctx = ChannelBean.getChannel("channel");
            requestService.baseRequest(ctx, "0003", "gateway");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            BaseResponse baseResponse = ChannelBean.getBaseResponse();
            if(baseResponse.getResult()==1){
                Toast.makeText(SocketManageActivity.this,"关网成功",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(SocketManageActivity.this,"关网失败",Toast.LENGTH_SHORT).show();
            }
        }

        @JavascriptInterface
        public void handon(String netName){

            SharedPreferences preferences=getSharedPreferences("zigBee", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=preferences.edit();
            editor.putString("current-netName",netName);
            String socketsJsonArray = preferences.getString("current-sockets", "");
            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            JsonArray jsonArray = parser.parse(socketsJsonArray).getAsJsonArray();
            List<SocketEntity> sockets = new ArrayList<SocketEntity>();
            for (JsonElement s : jsonArray) {
                //使用GSON，直接转成Bean对象
                SocketEntity socketEntity = gson.fromJson(s, SocketEntity.class);

                sockets.add(socketEntity);
            }

            //网关下发
            ChannelHandlerContext ctx = ChannelBean.getChannel("channel");
            DispatchSocketRequest request = new DispatchSocketRequest();
            if(preferences.getString("current-mac","").equals("")){
                Toast.makeText(SocketManageActivity.this,"请先建立连接并通讯",Toast.LENGTH_SHORT).show();
                return;
            }
            request.setMac(preferences.getString("current-mac",""));
            request.setName(preferences.getString("current-netName",""));
            request.setGateway(preferences.getString("current-gateway",""));
            request.setCode("0004");
            List<SocketBean> data = new ArrayList<SocketBean>();
            for(SocketEntity s:sockets){
                SocketBean sb = new SocketBean();
                sb.setName(s.getName());
                sb.setIeee("0x"+transfer(s.getSerialNumber()));
                if(s.getType().equals("10")){
                    sb.setDeviceid(1001);
                }
                if(s.getType().equals("16")){
                    sb.setDeviceid(1002);
                }
                sb.setDisplay(s.getId());


                data.add(sb);
            }
            request.setData(data);
            Log.i("xiafa",request.toString());
            requestService.dispatchSocketRequest(ctx, request, "gateway");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            BaseResponse response = ChannelBean.getBaseResponse();
            Log.i("xiafa",response.toString());
            if(response.getResult()==1){
                Toast.makeText(SocketManageActivity.this,"下发成功",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(SocketManageActivity.this,"下发失败",Toast.LENGTH_SHORT).show();
                return;
            }
            //List<IndexEntity> indexs = new ArrayList<IndexEntity>();
            try {

                Date date = new Date();
                SimpleDateFormat  sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateStr = sdf.format(date);
                String dateStrFormat = dateStr.replaceAll(" ","-");
                String dateStrFormatFinished = dateStrFormat.replaceAll(":","-");

                FileWriter fw = new FileWriter(AbsolutePath+"/zigBeeOutput"+"/lines"+dateStrFormatFinished+".txt");

                for(int i=1;i<sockets.size()+1;i++){
                    for(int j=0;j<sockets.size();j++){
                        if(sockets.get(j).getId()==i){
                            String str = ""+sockets.get(j).getId()+","+sockets.get(j).getName()+"," +sockets.get(j).getSerialNumber()
                                    + System.getProperty("line.separator");
                            fw.write(str);
                        }
                    }
                }

                fw.close();
                Toast.makeText(SocketManageActivity.this,"写入完毕",Toast.LENGTH_SHORT).show();

                //写入collection-netEntites
                NetEntity netEntity = new NetEntity();
                netEntity.setSockets(sockets);
                netEntity.setName(preferences.getString("current-netName", ""));
                netEntity.setWifi(preferences.getString("current-wifiName", ""));
                netEntity.setMac(preferences.getString("current-mac", ""));
                String netsJsonArray = preferences.getString("collection-netEntities", "");
                if(netsJsonArray.equals("")){
                    List<NetEntity> netEntities = new ArrayList<NetEntity>();
                    netEntities.add(netEntity);
                    String newNetsJsonArray = gson.toJson(netEntities);
                    editor.putString("collection-netEntities",newNetsJsonArray);
                }else{
                    JsonParser parser2 = new JsonParser();
                    JsonArray jsonArray2 = parser2.parse(netsJsonArray).getAsJsonArray();
                    List<NetEntity> netEntities = new ArrayList<NetEntity>();
                    for (JsonElement n : jsonArray2) {
                        //使用GSON，直接转成Bean对象
                        NetEntity netEn = gson.fromJson(n, NetEntity.class);
                        netEntities.add(netEn);
                    }
                    netEntities.add(netEntity);
                    String newNetsJsonArray = gson.toJson(netEntities);
                    editor.putString("collection-netEntities",newNetsJsonArray);

                }
                editor.commit();
                clearCurrent();

            } catch (IOException e) {
                e.printStackTrace();
            }

            Intent intent = new Intent(SocketManageActivity.this,LoginActivity.class);
            startActivity(intent);
            JPushInterface.setAliasAndTags(SocketManageActivity.this,"test",null);

        }

        @JavascriptInterface
        public void confirm(String netName){
            Log.i("confirm","123");
            SharedPreferences preferences=getSharedPreferences("zigBee", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=preferences.edit();
            editor.putString("current-netName",netName);
            String socketsJsonArray = preferences.getString("current-sockets", "");
            Gson gson = new Gson();
            List<SocketEntity> sockets = new ArrayList<SocketEntity>();
            if(!socketsJsonArray.equals("")){
                JsonParser parser = new JsonParser();
                JsonArray jsonArray = parser.parse(socketsJsonArray).getAsJsonArray();

                for (JsonElement s : jsonArray) {
                    //使用GSON，直接转成Bean对象
                    SocketEntity socketEntity = gson.fromJson(s, SocketEntity.class);

                    sockets.add(socketEntity);
                }
            }



            //List<IndexEntity> indexs = new ArrayList<IndexEntity>();
            try {


                //写入collection-netEntites
                NetEntity netEntity = new NetEntity();
                netEntity.setSockets(sockets);
                netEntity.setName(netName);
                netEntity.setWifi(preferences.getString("current-wifiName", ""));
                netEntity.setMac(preferences.getString("current-mac", ""));
                String netsJsonArray = preferences.getString("collection-netEntities", "");
                if(netsJsonArray.equals("")){
                    List<NetEntity> netEntities = new ArrayList<NetEntity>();
                    netEntities.add(netEntity);
                    String newNetsJsonArray = gson.toJson(netEntities);
                    editor.putString("collection-netEntities",newNetsJsonArray);
                }else{
                    JsonParser parser2 = new JsonParser();
                    JsonArray jsonArray2 = parser2.parse(netsJsonArray).getAsJsonArray();
                    List<NetEntity> netEntities = new ArrayList<NetEntity>();
                    for (JsonElement n : jsonArray2) {
                        //使用GSON，直接转成Bean对象
                        NetEntity netEn = gson.fromJson(n, NetEntity.class);
                        netEntities.add(netEn);
                    }
                    netEntities.add(netEntity);
                    String newNetsJsonArray = gson.toJson(netEntities);
                    editor.putString("collection-netEntities",newNetsJsonArray);

                }
                editor.commit();
                //clearCurrent();

            } catch (Exception e) {
                e.printStackTrace();
            }

            Intent intent = new Intent(SocketManageActivity.this,LoginActivity.class);
            startActivity(intent);
            JPushInterface.setAliasAndTags(SocketManageActivity.this,"test",null);

        }

        //保存
        @JavascriptInterface
        public void save(String netName){
            Gson gson = new Gson();
            SharedPreferences preferences=getSharedPreferences("zigBee", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=preferences.edit();



            /*Date date = new Date();
            SimpleDateFormat  sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateStr = sdf.format(date);
            String dateStrFormat = dateStr.replaceAll(" ","-");
            String dateStrFormatFinished = dateStrFormat.replaceAll(":","-");*/
            String macAddress = preferences.getString("current-mac","");

            saveFile = new File(saveDir,"saveFile"+macAddress+".txt");

            String socketsJsonArray = preferences.getString("current-sockets", "");
            List<SocketEntity> sockets = new ArrayList<SocketEntity>();
            if(!socketsJsonArray.equals("")){
                JsonParser parser = new JsonParser();
                JsonArray jsonArray = parser.parse(socketsJsonArray).getAsJsonArray();
                for (JsonElement s : jsonArray) {
                    //使用GSON，直接转成Bean对象
                    SocketEntity socketEntity = gson.fromJson(s, SocketEntity.class);
                    sockets.add(socketEntity);
                }
            }

            NetEntity netEntity = new NetEntity();
            netEntity.setSockets(sockets);
            netEntity.setName(netName);
            netEntity.setWifi(preferences.getString("current-wifiName", ""));
            netEntity.setMac(preferences.getString("current-mac", ""));
            netEntity.setIsOk(0);
            netEntity.setChannel(preferences.getString("current-channel", ""));
            netEntity.setGateway(preferences.getString("current-gateway", ""));
            netEntity.setPanid(preferences.getString("current-panid",""));
            netEntity.setProfile(preferences.getString("current-profile", ""));

            Write_Files(gson.toJson(netEntity),saveFile);
            Toast.makeText(SocketManageActivity.this,"读写完毕",Toast.LENGTH_SHORT).show();

            //写入collection-netEntites
            String netsJsonArray = preferences.getString("collection-netEntities", "");
            if(netsJsonArray.equals("")){
                List<NetEntity> netEntities = new ArrayList<NetEntity>();
                netEntities.add(netEntity);
                String newNetsJsonArray = gson.toJson(netEntities);
                editor.putString("collection-netEntities",newNetsJsonArray);
            }else{
                JsonParser parser2 = new JsonParser();
                JsonArray jsonArray2 = parser2.parse(netsJsonArray).getAsJsonArray();
                List<NetEntity> netEntities = new ArrayList<NetEntity>();
                for (JsonElement n : jsonArray2) {
                    //使用GSON，直接转成Bean对象
                    NetEntity netEn = gson.fromJson(n, NetEntity.class);
                    netEntities.add(netEn);
                }
                netEntities.add(netEntity);
                String newNetsJsonArray = gson.toJson(netEntities);
                editor.putString("collection-netEntities",newNetsJsonArray);
            }
            editor.commit();
            //clearCurrent();
            Intent intent = new Intent(SocketManageActivity.this,LoginActivity.class);
            startActivity(intent);

        }

        //获取
        @JavascriptInterface
        public void upload(){
            Toast.makeText(SocketManageActivity.this,"开始获取",Toast.LENGTH_SHORT).show();
            SharedPreferences preferences=getSharedPreferences("zigBee", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=preferences.edit();
            Gson gson = new Gson();
            List<SocketEntity> sockets = new ArrayList<SocketEntity>();
            if(preferences.getString("current-mac","").equals("")){
                Toast.makeText(SocketManageActivity.this,"请先建立连接并通讯",Toast.LENGTH_SHORT).show();
                return;
            }

            //网关上传
            ChannelHandlerContext ctx = ChannelBean.getChannel("channel");

            Log.i("debugs","ctx"+ctx.toString());
            requestService.baseRequest(ctx, "0005", "gateway");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ReadSocketResponse readSocketResponse = ChannelBean.getReadSocketResponse();
            List<SocketBean> data = readSocketResponse.getData();
            Log.i("debugs","data"+data.toString());
            for(SocketBean sb : data){
                SocketEntity socketEntity = new SocketEntity();
                socketEntity.setId(sb.getDisplay());
                socketEntity.setMac(preferences.getString("current-mac",""));
                socketEntity.setName(sb.getName());
                String ieee = sb.getIeee();

                socketEntity.setSerialNumber(transfer(ieee.substring(2,ieee.length())));
                String type ="";
                if(sb.getDeviceid()==1001){
                    type = "10";
                }else if(sb.getDeviceid()==1002){
                    type ="16";
                }
                socketEntity.setType(type);
                sockets.add(socketEntity);
            }


            if(readSocketResponse.getResult()==1){
                Toast.makeText(SocketManageActivity.this,"读取成功",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(SocketManageActivity.this,"读取失败",Toast.LENGTH_SHORT).show();
                return;
            }

            String newSocketsJsonArray = gson.toJson(sockets);
            editor.putString("current-sockets",newSocketsJsonArray);
            editor.commit();
            webView.reload();

        }

        @JavascriptInterface
        public void detail(int id){
            Gson gson = new Gson();
            SharedPreferences preferences=getSharedPreferences("zigBee", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=preferences.edit();
            String socketsJsonArray = preferences.getString("current-sockets", "");
            JsonParser parser = new JsonParser();
            JsonArray jsonArray = parser.parse(socketsJsonArray).getAsJsonArray();
            for (JsonElement s : jsonArray) {
                //使用GSON，直接转成Bean对象
                SocketEntity socketEntity = gson.fromJson(s, SocketEntity.class);
                if(socketEntity.getId()==id){
                    editor.putString("current-selectedSN",socketEntity.getSerialNumber());
                    editor.putString("current-selectedName",socketEntity.getName());
                    editor.putString("current-selectedType",socketEntity.getType());
                }
            }
            editor.putInt("current-selectedId",id);
            editor.commit();
            //Toast.makeText(SocketManageActivity.this,""+id,Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SocketManageActivity.this,ModifyActivity.class);
            startActivity(intent);
            JPushInterface.setAliasAndTags(SocketManageActivity.this,"test",null);

        }
        @JavascriptInterface
        public String getCurrentSockets(){
            SharedPreferences preferences=getSharedPreferences("zigBee", Context.MODE_PRIVATE);
            String socketsJsonArray = preferences.getString("current-sockets", "");
            Log.i("socketsJsonArray",socketsJsonArray);
            return socketsJsonArray;
        }


        @JavascriptInterface
        public String getSSID(){
            WifiManager wifiManager = (WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            Log.d("wifiInfo", wifiInfo.toString());
            Log.d("SSID",wifiInfo.getSSID());
            return wifiInfo.getSSID();
        }

        @JavascriptInterface
        public void back(String netName) {
            SharedPreferences preferences=getSharedPreferences("zigBee", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=preferences.edit();
            editor.putString("current-netName",netName);
            editor.commit();
            SocketManageActivity.this.finish();
        }

        @JavascriptInterface
        public String getName(){
            SharedPreferences sharedPreferences = getSharedPreferences("zigBee", Context.MODE_PRIVATE);
            String netName = sharedPreferences.getString("current-netName", "");
            return netName;
        }

        @JavascriptInterface
        public void add(String netName){
            SharedPreferences preferences=getSharedPreferences("zigBee", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=preferences.edit();
            editor.putString("current-netName",netName);
            editor.commit();
            Intent intent = new Intent(SocketManageActivity.this,AddActivity.class);
            startActivity(intent);
            JPushInterface.setAliasAndTags(SocketManageActivity.this,"test",null);
        }

        @JavascriptInterface
        public void toast(final String s){
            webView.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(SocketManageActivity.this,s,Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    private  void clearCurrent() {
        SharedPreferences preferences=getSharedPreferences("zigBee", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putInt("current-id",1);
        editor.putInt("current-selectedId",0);
        editor.putString("current-wifiName","");
        editor.putString("current-netName","");
        editor.putString("current-sockets","");
        editor.putString("current-selectedSN","");
        editor.putString("current-selectedName","");
        editor.putString("current-selectedType","");
        editor.commit();
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

    private void Write_Files(String content,File saveFile)
    {

        //从EditText的到输入的字符串

        //监测SD卡是否挂载
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            //创建流对象
            try{
                outStream = new FileOutputStream(saveFile);
            }catch (FileNotFoundException e){
                Toast.makeText(SocketManageActivity.this, "Start SD writing operation", Toast.LENGTH_SHORT).show();
                return ;
            }
            //写入文件
            try {
                outStream.write(content.getBytes());
            }catch(FileNotFoundException e) {
                // TODO Auto-generated catch block
                Toast.makeText(SocketManageActivity.this, "FileNotFoundException!", Toast.LENGTH_SHORT).show();
            } catch (IOException e){
                // TODO Auto-generated catch block
                Toast.makeText(SocketManageActivity.this, "IOException!", Toast.LENGTH_SHORT).show();
            } catch (NullPointerException e){
                Toast.makeText(SocketManageActivity.this, "NullPointerException!", Toast.LENGTH_SHORT).show();
            }
            finally{
                try{
                    //关闭输出流
                    outStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(SocketManageActivity.this, "关闭 IO 异常", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(SocketManageActivity.this, "写入完毕", Toast.LENGTH_SHORT).show();
            }

        }else {
            Toast.makeText(SocketManageActivity.this, "SD 没有挂载或不存在", Toast.LENGTH_SHORT).show();
            return ;
        }
    }



}
