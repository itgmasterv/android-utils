package com.example.sahmed.utilityapp.utility.snackbar;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sahmed.utilityapp.R;
import com.example.sahmed.utilityapp.utility.toast.ToastUtils;

public class SnackbarActivityUtils extends AppCompatActivity implements View.OnClickListener {
    View snackBarRootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snackbar_utils);

        snackBarRootView = findViewById(android.R.id.content);

        findViewById(R.id.btn_short_snackbar).setOnClickListener(this);
        findViewById(R.id.btn_short_snackbar_with_action).setOnClickListener(this);
        findViewById(R.id.btn_long_snackbar).setOnClickListener(this);
        findViewById(R.id.btn_long_snackbar_with_action).setOnClickListener(this);
        findViewById(R.id.btn_indefinite_snackbar).setOnClickListener(this);
        findViewById(R.id.btn_indefinite_snackbar_with_action).setOnClickListener(this);
        findViewById(R.id.btn_add_view).setOnClickListener(this);
        findViewById(R.id.btn_add_view_with_action).setOnClickListener(this);
        findViewById(R.id.btn_show_success).setOnClickListener(this);
        findViewById(R.id.btn_show_warning).setOnClickListener(this);
        findViewById(R.id.btn_show_error).setOnClickListener(this);
        findViewById(R.id.btn_dismiss_snackbar).setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_short_snackbar:
                SnackbarUtils.with(snackBarRootView)
                        .setMessage("snackbar_short")
                        .setMessageColor(Color.WHITE)
                        .setBgResource(R.drawable.shape_top_round_rect)
                        .show();
                break;

            case R.id.btn_short_snackbar_with_action:
                SnackbarUtils.with(snackBarRootView)
                        .setMessage("snackbar_short")
                        .setMessageColor(Color.WHITE)
                        .setBgResource(R.drawable.shape_top_round_rect)
                        .setAction(getString(R.string.snackbar_click), Color.YELLOW, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ToastUtils.showShortToast(getString(R.string.snackbar_click),SnackbarActivityUtils.this);
                            }
                        })
                        .show();
                break;

            case R.id.btn_long_snackbar:
                SnackbarUtils.with(snackBarRootView)
                        .setMessage("snackbar_long")
                        .setMessageColor(Color.WHITE)
                        .setDuration(SnackbarUtils.LENGTH_LONG)
                        .setBgResource(R.drawable.shape_top_round_rect)
                        .show();
                break;

            case R.id.btn_long_snackbar_with_action:
                SnackbarUtils.with(snackBarRootView)
                        .setMessage("snackbar_long")
                        .setMessageColor(Color.WHITE)
                        .setBgResource(R.drawable.shape_top_round_rect)
                        .setDuration(SnackbarUtils.LENGTH_LONG)
                        .setAction(getString(R.string.snackbar_click), Color.YELLOW, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ToastUtils.showShortToast(getString(R.string.snackbar_click),SnackbarActivityUtils.this);
                            }
                        })
                        .show();
                break;

            case R.id.btn_indefinite_snackbar:
                SnackbarUtils.with(snackBarRootView)
                        .setMessage("snackbar_indefinite")
                        .setMessageColor(Color.WHITE)
                        .setDuration(SnackbarUtils.LENGTH_INDEFINITE)
                        .setBgResource(R.drawable.shape_top_round_rect)
                        .show();
                break;

            case R.id.btn_indefinite_snackbar_with_action:
                SnackbarUtils.with(snackBarRootView)
                        .setMessage("snackbar_indefinite")
                        .setMessageColor(Color.WHITE)
                        .setDuration(SnackbarUtils.LENGTH_INDEFINITE)
                        .setBgResource(R.drawable.shape_top_round_rect)
                        .setAction(getString(R.string.snackbar_click), Color.YELLOW, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ToastUtils.showShortToast(getString(R.string.snackbar_click),SnackbarActivityUtils.this);
                            }
                        })
                        .show();
                break;

            case R.id.btn_add_view:
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                SnackbarUtils.with(snackBarRootView)
                        .setBgColor(Color.TRANSPARENT)
                        .setDuration(SnackbarUtils.LENGTH_INDEFINITE)
                        .show();
                SnackbarUtils.addView(R.layout.snackbar_custom, params);
                break;

            case R.id.btn_add_view_with_action:
                SnackbarUtils.with(snackBarRootView)
                        .setBgColor(Color.TRANSPARENT)
                        .setDuration(SnackbarUtils.LENGTH_INDEFINITE)
                        .show();
                params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                SnackbarUtils.addView(R.layout.snackbar_custom, params);
                View snackbarView = SnackbarUtils.getView();
                if (snackbarView != null) {
                    TextView tvSnackbarCustom = (TextView) snackbarView.findViewById(R.id.tv_snackbar_custom);
                    tvSnackbarCustom.setText("I can disappear");
                    snackbarView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SnackbarUtils.dismiss();
                        }
                    });
                }
                break;

            case R.id.btn_show_success:
                SnackbarUtils.with(snackBarRootView)
                        .setMessage("snackbar_success")
                        .showSuccess();
                break;

            case R.id.btn_show_warning:
                SnackbarUtils.with(snackBarRootView)
                        .setMessage("snackbar_warning")
                        .showWarning();
                break;

            case R.id.btn_show_error:
                SnackbarUtils.with(snackBarRootView)
                        .setMessage("snackbar_error")
                        .showError();
                break;

            case R.id.btn_dismiss_snackbar:
                SnackbarUtils.dismiss();
                break;
        }
    }

//    private SpannableStringBuilder getMsg(@StringRes int resId) {
//        return new SpanUtils()
//                .appendLine(getString(resId))
//                .setFontSize(24, true)
//                .setIconMargin(R.mipmap.ic_launcher, 32, SpanUtils.ALIGN_CENTER)
//                .append(" ").setFontSize(0)
//                .create();
//    }

}
