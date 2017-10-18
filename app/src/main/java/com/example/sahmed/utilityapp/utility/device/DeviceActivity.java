package com.example.sahmed.utilityapp.utility.device;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.sahmed.utilityapp.R;

public class DeviceActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);

        findViewById(R.id.btn_shutdown).setOnClickListener(this);
        findViewById(R.id.btn_reboot).setOnClickListener(this);
        findViewById(R.id.btn_reboot_to_recovery).setOnClickListener(this);
        findViewById(R.id.btn_reboot_to_bootloader).setOnClickListener(this);
        TextView tvAboutDevice = (TextView) findViewById(R.id.tv_about_device);
        tvAboutDevice.setText("isRoot: " + DeviceUtils.isDeviceRooted()
                + "\ngetSDKVersion: " + DeviceUtils.getSDKVersion()
                + "\ngetAndroidID: " + DeviceUtils.getAndroidID(this)
                + "\ngetMacAddress: " + DeviceUtils.getMacAddress(this)
                + "\ngetManufacturer: " + DeviceUtils.getManufacturer()
                + "\ngetModel: " + DeviceUtils.getModel()
        );

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_shutdown:
                DeviceUtils.shutdown(this);
                break;
            case R.id.btn_reboot:
                DeviceUtils.reboot(this);
                break;
            case R.id.btn_reboot_to_recovery:
                DeviceUtils.reboot2Recovery();
                break;
            case R.id.btn_reboot_to_bootloader:
                DeviceUtils.reboot2Bootloader();
                break;
        }
    }


}
