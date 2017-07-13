package com.fukuyama.fukuyamaapplication.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * ベースアクティビティ.
 */
public class BaseActivity extends AppCompatActivity {

    /**
     * プログレスダイアログ.
     */
    private ProgressDialog mProgressDialog;

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
        if (mProgressDialog != null) {
            return;
        }
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage(progressMessage);
        mProgressDialog.setCancelable(true);
        mProgressDialog.show();
    }

    /**
     * プログレスを非表示にする.
     */
    protected void dismissProgressDialog() {
        if (mProgressDialog == null) {
            return;
        }
        mProgressDialog.dismiss();
        mProgressDialog = null;
    }
}
