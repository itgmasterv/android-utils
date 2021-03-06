package com.example.sahmed.utilityapp.utility.process;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.sahmed.utilityapp.R;

import java.util.Iterator;
import java.util.Set;

public class ProcessActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tvAboutProcess;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process);

        findViewById(R.id.btn_kill_all_background_processes).setOnClickListener(this);
        tvAboutProcess = (TextView) findViewById(R.id.tv_about_process);
        Set<String> set = ProcessUtils.getAllBackgroundProcesses(this);
        tvAboutProcess.setText("getForegroundProcessName: " + ProcessUtils.getForegroundProcessName(this)
                + "\n\ngetAllBackgroundProcesses: " + getSetItems(set)
                + "\nsize: " + set.size());

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_kill_all_background_processes:
        Set<String> set = ProcessUtils.getAllBackgroundProcesses(this);
        Set<String> set1 = ProcessUtils.killAllBackgroundProcesses(this);
        tvAboutProcess.setText("getForegroundProcessName: " + ProcessUtils.getForegroundProcessName(this)
                + "\n\ngetAllBackgroundProcesses: " + getSetItems(set)
                + "\nsize: " + set.size()
                + "\n\nkillAllBackgroundProcesses: " + getSetItems(set1)
                + "\nsize: " + set1.size());
        break;
    }
    }
    private String getSetItems(Set<String> set) {
        Iterator<String> iterator = set.iterator();
        StringBuilder sb = new StringBuilder();
        while (iterator.hasNext()) {
            sb.append(iterator.next()).append("\n");
        }
        return sb.toString();
    }

}
