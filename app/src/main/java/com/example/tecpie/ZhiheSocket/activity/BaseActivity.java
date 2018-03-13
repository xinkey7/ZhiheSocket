package com.example.tecpie.ZhiheSocket.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.tecpie.ZhiheSocket.utils.ActivityController;

import org.xutils.x;

/**
 * Created by AmeKing on 2016/7/27.
 */
public class BaseActivity extends FragmentActivity {

    public static final String gateway="0x00124b000cb748d5";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        Log.d("CurrentActivity", getClass().getSimpleName());
        ActivityController.addActivity(this);
        x.view().inject(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityController.removeActivity(this);
    }

    public void toast(String txt) {
        Toast toast = Toast.makeText(getApplicationContext(), txt, Toast.LENGTH_SHORT);
        toast.show();
    }
}
