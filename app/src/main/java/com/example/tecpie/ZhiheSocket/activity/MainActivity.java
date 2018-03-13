package com.example.tecpie.ZhiheSocket.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.WriterException;
import com.example.tecpie.ZhiheSocket.zxing.app.CaptureActivity;
import com.example.tecpie.ZhiheSocket.zxing.util.Utils;
import com.example.tecpie.ZhiheSocket.R;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_QRCODE = 0x01;
    public static MainActivity instance;


    private TextView qrcodeView;
    private ImageView qrcodeImg;
    private Button qrcodeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);

        qrcodeView = (TextView) findViewById(R.id.qrcode_view);
        qrcodeImg = (ImageView) findViewById(R.id.qrcode_img);
        qrcodeBtn = (Button) findViewById(R.id.btn_generate_qrcode);

        qrcodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //生成二维码
                qrCodeGenerated();
            }
        });
        qrcodeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, CaptureActivity.class),REQUEST_QRCODE);
            }
        });
    }


    /**
     * 二维码生成
     */
    private void qrCodeGenerated() {

        String str = "点个赞噻";

        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;
        try {

            Bitmap qrCode = Utils.createQRCode(str, width / 2);
            qrcodeImg.setImageBitmap(qrCode);

        } catch (WriterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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
                        Toast.makeText(this, code, Toast.LENGTH_SHORT).show();
                    }
                }else if(resultCode == 300){
                    //从本地相册扫描后的业务逻辑
                    String code = data.getStringExtra("LOCAL_PHOTO_RESULT");
                    Toast.makeText(this, code, Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }
}
