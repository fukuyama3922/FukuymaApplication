package com.fukuyama.fukuyamaapplication.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * ベースアクティビティ.
 */
public class BaseActivity extends AppCompatActivity {

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * プログレスを表示する.
     *
     * @param progressMessage プログレスメッセージ
     */
    protected void showProgressDialog(String progressMessage) {
        if (ActivityConst.mProgressDialog != null) {
            return;
        }
        ActivityConst.mProgressDialog = new ProgressDialog(this);
        ActivityConst.mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        ActivityConst.mProgressDialog.setMessage(progressMessage);
        ActivityConst.mProgressDialog.setCancelable(false);
        ActivityConst.mProgressDialog.show();
    }

    /**
     * プログレスを非表示にする.
     */
    protected void dismissProgressDialog() {
        if (ActivityConst.mProgressDialog == null) {
            return;
        }
        ActivityConst.mProgressDialog.dismiss();
        ActivityConst.mProgressDialog = null;
    }
}
