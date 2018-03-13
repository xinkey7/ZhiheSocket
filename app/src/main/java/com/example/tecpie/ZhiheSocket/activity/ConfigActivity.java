package com.example.tecpie.ZhiheSocket.activity;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;


import com.example.tecpie.ZhiheSocket.R;
import com.example.tecpie.ZhiheSocket.utils.ProDialogUtil;
import com.example.tecpie.ZhiheSocket.utils.Util;
import com.idelan.BasicSDK.ConfigResponseDevice;
import com.idelan.BasicSDK.ResponseMethod;
import com.idelan.ConfigSDK.DeviceConfigSDK;
import com.idelan.bean.SmartDevice;

//import com.idelan.BasicSDK.DeviceConfig;

public class ConfigActivity extends Activity {

	private EditText wifiName, wifiPswd;
	private TextView start,stop;
	private String ssid;
	private DeviceConfigSDK deviceConfig;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_config);
		init();
	}

	private void init() {
		findViewById(R.id.iv_left).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		((TextView) findViewById(R.id.tv_left)).setText("返回");
		findViewById(R.id.tv_right).setVisibility(View.GONE);
		((TextView) findViewById(R.id.tv_title)).setText("网络配置");

		deviceConfig = DeviceConfigSDK.getInstance(ConfigActivity.this);

		wifiName = (EditText) findViewById(R.id.edit_name);
		wifiPswd = (EditText) findViewById(R.id.edit_serial);
		wifiName.setText(showSsid());

		start = (TextView) findViewById(R.id.tv_start);
		start.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
				inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
				linkStart();
			}
		});
		stop = (TextView) findViewById(R.id.tv_stop);
		stop.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				linkStop();
			}
		});
	}

	private void linkStart() {
		String password = wifiPswd.getText().toString().trim();
		ProDialogUtil.showProDialog(ConfigActivity.this, "正在配置...", false);

		deviceConfig.link_start(showSsid(), password, "", 0, new ResponseMethod<Object>() {

			@Override
			public void failure(int arg0) {
				Util.showToast(ConfigActivity.this, "调用接口失败");
			}

			@Override
			public void success(int arg0, Object arg1) {
				Util.showToast(ConfigActivity.this, "调用接口成功");
//				ProDialogUtil.showProDialog(ConfigActivity.this, "正在配置...", false);
			}

		}, new ConfigResponseDevice() {

			@Override
			public void onConfiging(SmartDevice arg0) {
				ProDialogUtil.cancel();
				Util.showToast(ConfigActivity.this, "配置成功"+arg0.toString());
			}
		});
	}

	private void linkStop() {
		ProDialogUtil.showProDialog(ConfigActivity.this, "正在停止配置...", false);
		deviceConfig.link_stop(new ResponseMethod<Object>() {

			@Override
			public void success(int retCode, Object retObject) {
				ProDialogUtil.cancel();
				Util.showToast(ConfigActivity.this, "停止成功");
			}

			@Override
			public void failure(int retCode) {
				ProDialogUtil.cancel();
				Util.showToast(ConfigActivity.this, "failure" + retCode);
			}
		});

	}

	private String showSsid() {
		ssid = getSSID(this);
		if (null != ssid) {
			// 网络:<Unknown ssid>
			if (ssid.equals("") || ssid.contains("Unknown") || ssid.contains("unknown") || ssid.contains("0x")
					|| ssid.contains("0X")) {
				return "请连接wifi网络";
			} else {
				if ("\"".equals(ssid.substring(0, 1))) {
					ssid = ssid.substring(1, ssid.length() - 1);
				}
				return ssid;
			}
		} else {
			return "请连接wifi网络";
		}
	}

	public static String getSSID(Context context) {
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		return wifiInfo.getSSID();
	}
}
