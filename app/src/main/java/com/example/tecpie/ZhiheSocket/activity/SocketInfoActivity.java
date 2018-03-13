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

import com.example.tecpie.ZhiheSocket.Cutil.bean.BaseResponse;
import com.example.tecpie.ZhiheSocket.Cutil.bean.ChannelBean;
import com.example.tecpie.ZhiheSocket.Cutil.bean.ReadSocketResponse;
import com.example.tecpie.ZhiheSocket.Cutil.bean.SocketBean;
import com.example.tecpie.ZhiheSocket.Cutil.bean.SocketDataResponse;
import com.example.tecpie.ZhiheSocket.Cutil.service.RequestService;
import com.example.tecpie.ZhiheSocket.Cutil.serviceImpl.RequestServiceImpl;
import com.example.tecpie.ZhiheSocket.R;
import com.example.tecpie.ZhiheSocket.entity.NetEntity;
import com.example.tecpie.ZhiheSocket.entity.SocketEntity;
import com.example.tecpie.ZhiheSocket.utils.ParseGson;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by AmeKing on 2016/7/27.
 */
@ContentView(R.layout.activity_socket_info)
public class SocketInfoActivity extends BaseActivity{
    private final String url = "file:///android_asset/recordinfo.html";
    @ViewInject(R.id.socketinfo)
    private WebView webView;
    private WebSettings webSettings;
    private File sdCardDir;
    private File saveFile;
    private File readFile;
    private FileOutputStream outStream;
    private FileInputStream inStream;
    private String AbsolutePath;
    private RequestService requestService = new RequestServiceImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webView.loadUrl(url);
        webSettings = webView.getSettings();
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new LoginJsInterface(),"socketInfo");
        //得到SD卡路径
        sdCardDir = Environment.getExternalStorageDirectory();
        AbsolutePath = (String) sdCardDir.getAbsolutePath();
        //Toast.makeText(this, "路径为"+AbsolutePath+"sdCardDir::"+sdCardDir, Toast.LENGTH_LONG).show();
        File saveDir = new File(AbsolutePath+"/zigBeeOutput");
        if(!saveDir.exists()){
            saveDir.mkdirs();
        }
        File readDir = new File(AbsolutePath+"/zigBeeInput");
        if(!readDir.exists()){
            readDir.mkdirs();
        }
        saveFile = new File(saveDir,"test.txt");
        readFile = new File(readDir,"test.txt");
    }


    public class LoginJsInterface{


        @JavascriptInterface
        public void open(){
            SharedPreferences preferences=getSharedPreferences("zigBee", Context.MODE_PRIVATE);
            if(preferences.getString("current-mac","").equals("")){
                Toast.makeText(SocketInfoActivity.this,"请先建立连接并通讯",Toast.LENGTH_SHORT).show();
                return;
            }
           // Toast.makeText(SocketInfoActivity.this,"开网中",Toast.LENGTH_SHORT).show();

            //获取单个插座
            ChannelHandlerContext ctx = ChannelBean.getChannel("channel");
            requestService.baseRequest(ctx, "0002", "gateway");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            BaseResponse baseResponse = ChannelBean.getBaseResponse();
            if(baseResponse.getResult()==1){
                Toast.makeText(SocketInfoActivity.this,"开网成功",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(SocketInfoActivity.this,"开网失败",Toast.LENGTH_SHORT).show();
            }
        }

        @JavascriptInterface
        public void close(){
            SharedPreferences preferences=getSharedPreferences("zigBee", Context.MODE_PRIVATE);
            if(preferences.getString("current-mac","").equals("")){
                Toast.makeText(SocketInfoActivity.this,"请先建立连接并通讯",Toast.LENGTH_SHORT).show();
                return;
            }
            // Toast.makeText(SocketInfoActivity.this,"开网中",Toast.LENGTH_SHORT).show();

            //获取单个插座
            ChannelHandlerContext ctx = ChannelBean.getChannel("channel");
            requestService.baseRequest(ctx, "0003", "gateway");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            BaseResponse baseResponse = ChannelBean.getBaseResponse();
            if(baseResponse.getResult()==1){
                Toast.makeText(SocketInfoActivity.this,"关网成功",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(SocketInfoActivity.this,"关网失败",Toast.LENGTH_SHORT).show();
            }
        }


        @JavascriptInterface
        public void detail(String id){
            Toast.makeText(SocketInfoActivity.this,id,Toast.LENGTH_SHORT).show();
            //获取单个插座
            ChannelHandlerContext ctx = ChannelBean.getChannel("channel");
            requestService.baseRequest(ctx, "0007", "gateway");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Gson gson = new Gson();
            SocketDataResponse socketDataResponse = ChannelBean.getSocketDataResponse();
            //Log.i("debugs","ieee"+socketDataResponse.getIeee());
            String socketDataResponseJsonArray = gson.toJson(socketDataResponse);
            Log.i("debugs",socketDataResponseJsonArray);
            Intent intent = new Intent(SocketInfoActivity.this,SocketDetailActivity.class);
            intent.putExtra("socketData",socketDataResponseJsonArray);
            startActivity(intent);
            JPushInterface.setAliasAndTags(SocketInfoActivity.this,"test",null);




        }

        //获取
        @JavascriptInterface
        public void upload(){
            Toast.makeText(SocketInfoActivity.this,"开始获取",Toast.LENGTH_SHORT).show();
            SharedPreferences preferences=getSharedPreferences("zigBee", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=preferences.edit();
            Gson gson = new Gson();
            List<SocketEntity> sockets = new ArrayList<SocketEntity>();
            if(preferences.getString("current-mac","").equals("")){
                Toast.makeText(SocketInfoActivity.this,"请先建立连接并通讯",Toast.LENGTH_SHORT).show();
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
                //socketEntity.setId(sb.getDisplay());
                socketEntity.setId(1);
                socketEntity.setMac(preferences.getString("current-mac",""));
                socketEntity.setName(sb.getName());
                String ieee = sb.getIeee();

                socketEntity.setSerialNumber(transfer(ieee.substring(2,ieee.length())));
                socketEntity.setState(sb.getOnline());
                String type ="";
                /*if(sb.getDeviceid()==1001){
                    type = "10";
                }else if(sb.getDeviceid()==1002){
                    type ="16";
                }*/
                socketEntity.setType(type);
                sockets.add(socketEntity);
            }
            String macAddress = preferences.getString("current-mac","");
            List<NetEntity> netEntities = ParseGson.parseNetEntityJasonArray(preferences.getString("collection-netEntities",""));
            for(int i=0;i<netEntities.size();i++){
                if(netEntities.get(i).getMac().equals(macAddress)){
                    netEntities.get(i).setSockets(sockets);
                    break;
                }
            }
            editor.putString("collection-netEntities",gson.toJson(netEntities));


            if(readSocketResponse.getResult()==1){
                Toast.makeText(SocketInfoActivity.this,"读取成功",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(SocketInfoActivity.this,"读取失败",Toast.LENGTH_SHORT).show();
                return;
            }


            Log.i("upload",netEntities.toString());
            editor.commit();
            SocketInfoActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    webView.reload();
                }
            });


        }
        @JavascriptInterface
        public String getTitleSockets(){
            Intent intent = getIntent();
            String title = intent.getStringExtra("title");
            int index= Integer.valueOf(title).intValue();
            Gson gson = new Gson();
            SharedPreferences preferences=getSharedPreferences("zigBee", Context.MODE_PRIVATE);
            String netsJsonArray = preferences.getString("collection-netEntities", "");

            JsonParser parser = new JsonParser();
            JsonArray jsonArray = parser.parse(netsJsonArray).getAsJsonArray();
            List<NetEntity> netEntities = new ArrayList<NetEntity>();
            for (JsonElement n : jsonArray) {
                //使用GSON，直接转成Bean对象
                NetEntity netEn = gson.fromJson(n, NetEntity.class);
                netEntities.add(netEn);
            }
            List<SocketEntity> socketEntities = netEntities.get(index).getSockets();
            Log.i("socketEntities",gson.toJson(socketEntities));
           return gson.toJson(socketEntities);



        }
        @JavascriptInterface
        public void netinfo(){

            Intent intent = new Intent(SocketInfoActivity.this,NetDetailActivity.class);
            startActivity(intent);
        }
        @JavascriptInterface
        public void save(){
            Write_Files("test1231456778",saveFile);
            Toast.makeText(SocketInfoActivity.this,"读写完毕",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SocketInfoActivity.this,LoginActivity.class);
            startActivity(intent);

        }





        @JavascriptInterface
        public void back() {
            SocketInfoActivity.this.finish();
        }

        @JavascriptInterface
        public void add(){




            Intent intent = new Intent(SocketInfoActivity.this,AddActivity.class);
            startActivity(intent);
            JPushInterface.setAliasAndTags(SocketInfoActivity.this,"test",null);




        }

        @JavascriptInterface
        public void toast(final String s){
            webView.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(SocketInfoActivity.this,s,Toast.LENGTH_SHORT).show();
                }
            });
        }
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
                Toast.makeText(SocketInfoActivity.this, "Start SD writing operation", Toast.LENGTH_SHORT).show();
                return ;
            }
            //写入文件
            try {
                outStream.write(content.getBytes());
            }catch(FileNotFoundException e) {
                // TODO Auto-generated catch block
                Toast.makeText(SocketInfoActivity.this, "FileNotFoundException!", Toast.LENGTH_SHORT).show();
            } catch (IOException e){
                // TODO Auto-generated catch block
                Toast.makeText(SocketInfoActivity.this, "IOException!", Toast.LENGTH_SHORT).show();
            } catch (NullPointerException e){
                Toast.makeText(SocketInfoActivity.this, "NullPointerException!", Toast.LENGTH_SHORT).show();
            }
            finally{
                try{
                    //关闭输出流
                    outStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(SocketInfoActivity.this, "关闭 IO 异常", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(SocketInfoActivity.this, "写入完毕", Toast.LENGTH_SHORT).show();
            }

        }else {
            Toast.makeText(SocketInfoActivity.this, "SD 没有挂载或不存在", Toast.LENGTH_SHORT).show();
            return ;
        }
    }

    private void Read_Files()
    {

        int len;
        //创建一个字节数组输出流
        byte[] buffer = new byte[1024];
        ByteArrayOutputStream ostream = new ByteArrayOutputStream();
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            //初始化 流对象
            try{
                inStream = new FileInputStream(readFile);
                Log.v("Instance inStream", "ʵ��inStream��");
            }catch (FileNotFoundException e){
                Toast.makeText(SocketInfoActivity.this, "实例化inStream！", Toast.LENGTH_SHORT).show();
                return ;
            }
            try{
                Log.v("Read", "读数据");
                //�� buffer
                while((
                        len=inStream.read(buffer)
                )!=-1){
                    ostream.write(buffer, 0, len);
                }
                Log.v("Read", "读成功");
            }catch (IOException e){
                Toast.makeText(SocketInfoActivity.this, "IOException", Toast.LENGTH_SHORT).show();
            }
            try{
                //关闭输入流和输出流
                inStream.close();
                ostream.close();
                Log.v("Read", "读成功！");
            }catch (IOException e){
                Toast.makeText(SocketInfoActivity.this, "读 异常！", Toast.LENGTH_SHORT).show();
            }

            String str = new String(ostream.toByteArray());
            Log.v("Disp", "文本显示！");
            Toast.makeText(SocketInfoActivity.this, str, Toast.LENGTH_SHORT).show();


        }else{
            Toast.makeText(SocketInfoActivity.this, "内存卡不存在！", Toast.LENGTH_SHORT).show();
            return ;
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
