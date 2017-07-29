package com.fukuyama.fukuyamaapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fukuyama.fukuyamaapplication.AppBean;
import com.fukuyama.fukuyamaapplication.Observer;
import com.fukuyama.fukuyamaapplication.R;
import com.fukuyama.fukuyamaapplication.db.QuantityInfoEntity;
import com.fukuyama.fukuyamaapplication.db.UpdateTableTask;
import com.fukuyama.fukuyamaapplication.util.BitmapUtil;
import com.fukuyama.fukuyamaapplication.util.MessageUtil;

import java.io.IOException;

/**
 * サブアクティビティ.
 */
public class SubActivity extends BaseActivity implements View.OnClickListener {

    /**
     * サブアクティビティから呼び出したことを認識するコード.
     */
    private static final int REQUEST_CODE_SUB_ACTIVITY = 1001;

    /**
     * インテントタイプ.
     */
    private static final String INTENT_TYPE = "image/*";

    /**
     * {@link QuantityInfoEntity}
     */
    private QuantityInfoEntity mQuantityInfoEntity;

    /**
     * {@link Observer}
     */
    private Observer mObserver = new Observer() {

        /**
         * {@inheritDoc}
         */
        @Override
        public void notification(int notificationCode, Object[] options) {

            switch (notificationCode) {
                case Observer.NOTIFICATION_CODE_UPDATE_QUERY_COMPLETE:
                    // DB更新処理完了時

                    if ((long) options[Observer.OPTION_INDEX_UPDATE_QUERY_RESULT] == -1) {
                        MessageUtil.showToast(getApplicationContext(), "DB更新処理失敗");
                        return;
                    }
                    delay();

                    return;
                default:
                    return;
            }
        }
    };

    private void delay() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                dismissProgressDialog();
                finish();
            }
        }, 1000L);
    }

    /**
     * インテント生成.
     *
     * @param context {@link Context}
     *                * @return {@link Intent}
     */
    public static Intent getNewIntent(Context context) {
        Intent intent = new Intent(context, SubActivity.class);
        return intent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        // 数量情報をメモリに保持
        mQuantityInfoEntity = AppBean.getQuantityInfoEntity();

        // 画面初期表示
        initView();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onResume() {
        super.onResume();
        AppBean.addObserver(mObserver);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onPause() {
        super.onPause();
        AppBean.removeObserver(mObserver);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        if (requestCode == REQUEST_CODE_SUB_ACTIVITY && resultCode == RESULT_OK) {
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                String stringUri = BitmapUtil.uriToString(uri);
                mQuantityInfoEntity.setUriString(stringUri);
                updateImageView(stringUri);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.activity_detail_select_button:
                onClickSelectButton();
                return;
            default:
                return;
        }
    }

    /**
     * バックキーを押したときの処理.
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            executeUpdateTable(mQuantityInfoEntity);
            return true;
        }
        return false;
    }

    /**
     * 初期表示.
     */
    private void initView() {
        // 初期設定
        initSetting();

        // コメント表示欄の表示更新
        updateCommentText(mQuantityInfoEntity.getComment());

        // 時刻表示欄の表示更新
        updateDateText(mQuantityInfoEntity.getDate());

        // 数量表示欄の表示更新
        updateQuantity(String.valueOf(mQuantityInfoEntity.getQuantity()));

        // 画僧表示領域の表示更新
        if (!TextUtils.isEmpty(mQuantityInfoEntity.getUriString())) {
            updateImageView(mQuantityInfoEntity.getUriString());
        }

    }

    /**
     * コメント表示欄の表示を更新する.
     *
     * @param text コメント表示欄に表示する文字列
     */
    private void updateCommentText(String text) {
        updateText(R.id.text_comment, text);
    }

    /**
     * 時刻表示欄の表示を更新する.
     *
     * @param text 時刻表示欄に表示する文字列.
     */
    private void updateDateText(String text) {
        updateText(R.id.text_date, text);
    }

    /**
     * 数量表示欄の表示を更新する.
     *
     * @param text 数量表示欄に表示する文字列
     */
    private void updateQuantity(String text) {
        updateText(R.id.text_quantity, text);
    }

    /***
     * 任意のTextViewの表示を更新する.
     *
     * @param resId リソースID
     * @param text TextViewに表示させる文字列
     */
    private void updateText(int resId, String text) {
        TextView textView = (TextView) findViewById(resId);
        textView.setText(text);
    }

    /**
     * 画像表示領域の表示を更新する.
     */
    private void updateImageView(String uriString) {
        Bitmap bitmap = null;
        Uri uri = BitmapUtil.stringToUri(uriString);

        try {
            bitmap = BitmapUtil.getBitmapFromUri(this, uri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (bitmap == null) {
            return;
        }
        ImageView imageView = (ImageView) findViewById(R.id.image_view);
        imageView.setImageBitmap(bitmap);
    }

    /**
     * 初期設定.
     */
    private void initSetting() {

        // タイトルをセット
        setTitle("SubActivity");

        // TODO:仮のボタン
        // 選択ボタン設定
        Button selectButton = (Button) findViewById(R.id.activity_detail_select_button);
        selectButton.setOnClickListener(this);
    }

    // TODO:仮処理

    /**
     * 選択ボタン押下時の処理.
     */
    private void onClickSelectButton() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType(INTENT_TYPE);
        startActivityForResult(intent, REQUEST_CODE_SUB_ACTIVITY);
    }

    /**
     * DB更新処理を実行する.
     *
     * @param quantityInfoEntity {@link QuantityInfoEntity}
     */
    private void executeUpdateTable(QuantityInfoEntity quantityInfoEntity) {
        showProgressDialog(getString(R.string.progress_message_db_update));
        UpdateTableTask Task = new UpdateTableTask(this);
        Task.execute(quantityInfoEntity);
    }


}







