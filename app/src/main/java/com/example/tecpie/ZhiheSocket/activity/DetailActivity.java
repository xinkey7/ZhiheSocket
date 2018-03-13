package com.example.tecpie.ZhiheSocket.activity;

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
import com.example.tecpie.ZhiheSocket.bean.Detail;
import com.example.tecpie.ZhiheSocket.bean.ValueSetBody;
import com.example.tecpie.ZhiheSocket.pojo.DMonEntrieBean;
import com.example.tecpie.ZhiheSocket.pojo.DMonEntries;
import com.example.tecpie.ZhiheSocket.utils.FBoxUtil;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.List;




@ContentView(R.layout.activity_detail)
public class DetailActivity extends BaseActivity {
    private final String log="DlActivity";
    private final String url ="file:///android_asset/mdetail.html";
    @ViewInject(R.id.fhwv)
    private WebView webView;
    private WebSettings webSettings;
    private String name;
    private String val;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        name = getIntent().getStringExtra("dname");
        val = getIntent().getStringExtra("dval");

        webView.loadUrl(url);
        webSettings = webView.getSettings();
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new DetailJsInterface(),"mdetail");
    }

    public class DetailJsInterface{
        @JavascriptInterface
        public void toast(final String s){
            webView.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(DetailActivity.this,s,Toast.LENGTH_SHORT).show();
                }
            });
        }

        @JavascriptInterface
        public void back() {
            DetailActivity.this.finish();
        }

        @JavascriptInterface
        public String getDetails(){
            Gson gson = new Gson();
            SharedPreferences preferences=getSharedPreferences("user", Context.MODE_PRIVATE);
            String dmonEntriesArray = preferences.getString("dmonEntries", "");
            try {
                JSONArray jsonArray = new JSONArray(dmonEntriesArray) ;
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject objDMonEntrie = jsonArray.getJSONObject(i);
                    DMonEntrieBean dMonEntrie = gson.fromJson(objDMonEntrie.toString(), DMonEntrieBean.class);

                    List<DMonEntries> dMonEntries = dMonEntrie.getdMonEntries();
                    for(DMonEntries dMonEntry :dMonEntries){
                        if(name.equals(dMonEntry.getName())){
                            Detail d = new Detail();
                            d.setDataType(dMonEntry.getDataType());
                            d.setName(dMonEntry.getName());
                            d.setDesc(dMonEntry.getDesc());
                            d.setMainAddr(dMonEntry.getSrc().getMainAddr());
                            d.setPrivilege(dMonEntry.getPrivilege());
                            d.setSubAddr(dMonEntry.getSrc().getSubAddr());
                            d.setStationNo(dMonEntry.getSrc().getStationNo());
                            return gson.toJson(d);
                        }
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }


        @JavascriptInterface
        public String val(){
            Log.i("val",val);
          return val;

        }




    }
}
