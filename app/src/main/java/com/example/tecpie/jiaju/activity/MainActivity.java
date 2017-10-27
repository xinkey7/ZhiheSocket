package com.example.tecpie.jiaju.activity;

import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.tecpie.jiaju.R;
import com.example.tecpie.jiaju.fragment.IndexFragment;
import com.example.tecpie.jiaju.fragment.MessageFragment;
import com.example.tecpie.jiaju.fragment.RecordFragment;
import com.example.tecpie.jiaju.fragment.SettingFragment;
import com.example.tecpie.jiaju.holder.MainTabItemHolder;
import com.example.tecpie.jiaju.utils.WebServiceUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity{
    public static MainActivity instance;
    private String[] ids = {"index","record","message","setting"};
    private String[] labels = {"首页","记录","消息","设置"};
    private int[] draws ={R.drawable.tab_index,R.drawable.tab_record,R.drawable.tab_message,R.drawable.tab_setting};
    private Class[] classes = {IndexFragment.class, RecordFragment.class,MessageFragment.class, SettingFragment.class};

    @ViewInject(R.id.content)
    private FrameLayout content;
    @ViewInject(R.id.tabhost)
    private FragmentTabHost tabHost;

    private LayoutInflater mLayoutInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        instance = this;
        initTab();
    }

    private void initTab(){
        mLayoutInflater = LayoutInflater.from(this);
        tabHost.setup(this,getSupportFragmentManager(),R.id.content);
        for(int i=0;i<ids.length;i++){
            tabHost.addTab(tabHost.newTabSpec(ids[i]).setIndicator(getTabItemView(i)),classes[i],null);
        }
    }

    private View getTabItemView(int index) {
        MainTabItemHolder holder = new MainTabItemHolder();
        View view = mLayoutInflater.inflate(R.layout.widget_tabhost_item, null);
        x.view().inject(holder, view);
        view.setTag(holder);

        ImageView tabImage = holder.tabImage;
        tabImage.setImageResource(draws[index]);

        TextView tabText = holder.tabText;
        tabText.setText(labels[index]);
        return view;
    }
   /* class WebServiceAsyncTask extends AsyncTask<String,Void,String>{
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

        }

        @Override
        protected String doInBackground(String... params) {
            WebServiceUtil.webService(params[0],params[1]);
            return null;
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.qingqiu){
            String action = "queryDevice";
            String message = "{"+
                    "\"joinuser\":{"+
                    "\"id\":\"1\"" +
                    "}" +
                    "}";
            new WebServiceAsyncTask().execute(action,message);
        }
    }*/
}
