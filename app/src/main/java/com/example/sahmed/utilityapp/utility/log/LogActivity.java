package com.example.sahmed.utilityapp.utility.log;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.sahmed.utilityapp.R;

public class LogActivity extends AppCompatActivity implements View.OnClickListener {

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            LogUtils.v("verbose");
            LogUtils.d("debug");
            LogUtils.i("info");
            LogUtils.w("warn");
            LogUtils.e("Success");
            LogUtils.a("assert");
        }
    };

    private static final String longStr;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append("len = 10400\ncontent = \"");
        for (int i = 0; i < 800; ++i) {
            sb.append("Hello world. ");
        }
        sb.append("\"");
        longStr = sb.toString();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        findViewById(R.id.btn_log_no_tag).setOnClickListener(this);
        findViewById(R.id.btn_log_with_tag).setOnClickListener(this);
        findViewById(R.id.btn_log_in_new_thread).setOnClickListener(this);
        findViewById(R.id.btn_log_null).setOnClickListener(this);
        findViewById(R.id.btn_log_many_params).setOnClickListener(this);
        findViewById(R.id.btn_log_long).setOnClickListener(this);
        findViewById(R.id.btn_log_json).setOnClickListener(this);
        findViewById(R.id.btn_log_xml).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_log_no_tag:
                LogUtils.v("verbose");
                LogUtils.d("debug");
                LogUtils.i("info");
                LogUtils.w("warn");
                LogUtils.e("error");
                LogUtils.a("assert");
                break;
            case R.id.btn_log_with_tag:
                LogUtils.v("customTag", "verbose");
                LogUtils.d("customTag", "debug");
                LogUtils.i("customTag", "info");
                LogUtils.w("customTag", "warn");
                LogUtils.e("customTag", "error");
                LogUtils.a("customTag", "assert");
                break;
            case R.id.btn_log_in_new_thread:
                Thread thread = new Thread(mRunnable);
                thread.start();
                break;
            case R.id.btn_log_null:
                LogUtils.v(null);
                LogUtils.d(null);
                LogUtils.i(null);
                LogUtils.w(null);
                LogUtils.e(null);
                LogUtils.a(null);
                break;
            case R.id.btn_log_many_params:
                LogUtils.v("customTag", "verbose0", "verbose1");
                LogUtils.d("customTag", "debug0", "debug1");
                LogUtils.i("customTag", "info0", "info1");
                LogUtils.w("customTag", "warn0", "warn1");
                LogUtils.e("customTag", "error0", "error1");
                LogUtils.a("customTag", "assert0", "assert1");
                break;
            case R.id.btn_log_long:
                LogUtils.d(longStr);
                break;
            case R.id.btn_log_json:
                String json = "{\"tools\": [{ \"name\":\"css format\" , \"site\":\"http://tools.w3cschool.cn/code/css\" },{ \"name\":\"json format\" , \"site\":\"http://tools.w3cschool.cn/code/json\" },{ \"name\":\"pwd check\" , \"site\":\"http://tools.w3cschool.cn/password/my_password_safe\" }]}";
                LogUtils.json(json);
                LogUtils.json(LogUtils.I, json);
                break;
            case R.id.btn_log_xml:
                String xml = "<books><book><author>Jack Herrington</author><title>PHP Hacks</title><publisher>O'Reilly</publisher></book><book><author>Jack Herrington</author><title>Podcasting Hacks</title><publisher>O'Reilly</publisher></book></books>";
                LogUtils.xml(xml);
                LogUtils.xml(LogUtils.I, xml);
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    }

