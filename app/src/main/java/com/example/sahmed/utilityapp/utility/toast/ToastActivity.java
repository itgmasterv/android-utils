package com.example.sahmed.utilityapp.utility.toast;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import com.example.sahmed.utilityapp.R;

public class ToastActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shaimaa);

        findViewById(R.id.btn_show_short_toast).setOnClickListener(this);
        findViewById(R.id.btn_show_long_toast).setOnClickListener(this);
        findViewById(R.id.btn_show_green_font).setOnClickListener(this);
        findViewById(R.id.btn_show_bg_color).setOnClickListener(this);
        findViewById(R.id.btn_show_bg_resource).setOnClickListener(this);
        findViewById(R.id.btn_show_custom_view).setOnClickListener(this);
        findViewById(R.id.btn_show_middle).setOnClickListener(this);
        findViewById(R.id.btn_cancel_toast).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_show_short_toast:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showShortToast("toast short", ToastActivity.this);
                    }
                }).start();
                break;
            case R.id.btn_show_long_toast:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showLongToast("toast_long", ToastActivity.this);
                    }
                }).start();
                break;
            case R.id.btn_show_green_font:
                ToastUtils.setMsgColor(Color.GREEN);
                ToastUtils.showLongToast("toast_green_font",ToastActivity.this );
                break;
            case R.id.btn_show_bg_color:
                ToastUtils.setBgColor(ContextCompat.getColor(this, R.color.colorAccent));
                ToastUtils.showLongToast("toast_bg_color", ToastActivity.this);
                break;
            case R.id.btn_show_bg_resource:
                ToastUtils.setBgResource(R.drawable.shape_round_rect);
                ToastUtils.showLongToast("toast_custom_bg", ToastActivity.this);
                break;
            case R.id.btn_show_custom_view:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        CustomToast.showLongToast(getApplicationContext(), "toast_custom_view");
                    }
                }).start();
                break;
            case R.id.btn_show_middle:
                ToastUtils.setGravity(Gravity.CENTER, 0, 0);
                ToastUtils.showLongToast("toast_middle", ToastActivity.this);
                break;
            case R.id.btn_cancel_toast:
                ToastUtils.cancel();
                break;
        }

    }
}
