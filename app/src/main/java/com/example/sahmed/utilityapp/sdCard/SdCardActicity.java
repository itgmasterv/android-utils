package com.example.sahmed.utilityapp.sdCard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.sahmed.utilityapp.R;

public class SdCardActicity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sd_card_acticity);

        //getToolBar().setTitle(getString(R.string.demo_sdcard));

        TextView tvAboutSdcard = (TextView) findViewById(R.id.tv_about_sdcard);
        tvAboutSdcard.setText("isSDCardEnable: " + SDCardUtils.isSDCardEnable(this)
                + "\ngetSDCardPaths: " + SDCardUtils.getSDCardPaths(this)
                + "\ngetInnerSDCardPaths: " + SDCardUtils.getSDCardPaths(true,this)
                + "\ngetOuterSDCardPaths: " + SDCardUtils.getSDCardPaths(false,this)
        );

    }
}
