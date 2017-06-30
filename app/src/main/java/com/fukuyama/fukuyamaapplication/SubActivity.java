package com.fukuyama.fukuyamaapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;

/**
 * Created by fukuyama on 2017/06/10.
 */

/**
 * サブアクティビティ.
 */
public class SubActivity extends BaseActivity implements View.OnClickListener {

    /**
     * サブアクティビティから呼び出したことを認識するコード.
     */
    private static final int REQUEST_CODE_SUBACTIVITY = 1000;

    /**
     * インテントキー:数量情報.
     */
    private static final String INTENT_KEY_QUANTITY_INFO_LIST = "intent_key_quantity_info_list";

    /**
     * 数量情報リスト保持用.
     */
    private QuantityInfo mQuantityInfo;

    /**
     * イメージビュー.
     */
    private ImageView mImageView;

    /**
     * インテント生成.
     *
     * @param activity     {@link Activity}
     * @param quantityInfo 数量情報
     * @return {@link Intent}
     */
    public static Intent getNewIntent(Activity activity, QuantityInfo quantityInfo) {

        Intent intent = new Intent(activity, SubActivity.class);
        intent.putExtra(INTENT_KEY_QUANTITY_INFO_LIST, quantityInfo);
        return intent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        // 画面初期表示
        initView();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        if (requestCode == REQUEST_CODE_SUBACTIVITY && resultCode == RESULT_OK) {
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();

                try {
                    Bitmap bitmap1 = getBitmapFromUri(uri);
                    //画像のサイズを取得するメソッド
                    int width  = bitmap1.getWidth();
                    int height = bitmap1.getHeight();
                    // 画像サイズの文字列を返す
                    String size = "w:" + width + ",h:" + height;

                    MessageUtil.showToast(this,size);
                    //選択した画像をサムネイルとしてアルバム上にずらっと何枚も表示
                    //サムネイルをクリックすることでギャラリー内の元解像度の画像を表示
                    //巣ワイプなどで詳細画面ないの画像を切り替え

                    bitmap1 = Bitmap.createScaledBitmap(getBitmapFromUri(uri), 500, 500, false);

                    mImageView.setImageBitmap(bitmap1);
//                    bitmap1 = Bitmap.createScaledBitmap(getBitmapFromUri(uri), 500, 500, false);
                    String convertBitmap = BitmapUtil.BitMapToString(bitmap1);
                    mQuantityInfo.setBitmapString(convertBitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }
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

            case R.id.return_button:
                onClickReturnButton();
                return;

            default:
                return;
        }
    }

    /**
     * 初期表示.
     */
    private void initView() {
        setTitle("SubActivity");

        mQuantityInfo = (QuantityInfo) getIntent().getSerializableExtra(INTENT_KEY_QUANTITY_INFO_LIST);


        mImageView = (ImageView) findViewById(R.id.image_view);
//       Bitmap bitmap2 =Bitmap.createScaledBitmap(mQuantityInfo.getBitmap(), 500, 500, false);
        mImageView.setImageBitmap(mQuantityInfo.getBitmap());



        // TODO:仮のボタン
        // 選択ボタン設定
        Button selectButton = (Button) findViewById(R.id.activity_detail_select_button);
        selectButton.setOnClickListener(this);

        // TODO:仮のボタン
        // リターンボタン押下時の処理
        Button returnButton = (Button) findViewById(R.id.return_button);
        returnButton.setOnClickListener(this);
    }

    // TODO:仮処理

    /**
     * 選択ボタン押下時の処理.
     */
    private void onClickSelectButton() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_SUBACTIVITY);
    }

    // TODO:仮処理

    /**
     * 戻るボタン押下時の処理.
     */
    private void onClickReturnButton() {
        Intent intent = new Intent();
        intent.putExtra("intent-key", mQuantityInfo);
        setResult(RESULT_OK, intent);
        finish();
    }
}







