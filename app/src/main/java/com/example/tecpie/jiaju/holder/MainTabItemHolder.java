package com.example.tecpie.jiaju.holder;

import android.widget.ImageView;
import android.widget.TextView;

import com.example.tecpie.jiaju.R;

import org.xutils.view.annotation.ViewInject;


/**
 * Created by Derek.WX on 3/10/2016.
 */
public class MainTabItemHolder {
    @ViewInject(R.id.tabImage)
    public ImageView tabImage;

    @ViewInject(R.id.tabText)
    public TextView tabText;
}
