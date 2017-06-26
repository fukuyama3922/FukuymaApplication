package com.fukuyama.fukuyamaapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileDescriptor;
import java.io.IOException;

/**
 * Created by fukuyama on 2017/06/10.
 */

/**
 * サブアクティビティ.
 */
public class SubActivity extends AppCompatActivity {

    /**
     *サブアクティビティから呼び出したことを認識するコード.
     */
    private static final int REQUEST_CODE_SUBACTIVITY = 1000;

    /**
     * イメージビュー.
     */
    private ImageView mImageView;

    /**
     * インテントキー：日付.
     */
    private static final String INTENT_KEY_DATE_TIME = "intent_key_date_time";

    /**
     * インテントキー：コメント.
     */
    private static final String INTENT_KEY_COMMENT ="intent_key_comment";

    /**
     * インテントキー：数量.
     */
    private static final String INTENT_KEY_QUANTITY = "intent_key_quantity";

    /**
     * 時刻保持用
     */
    private String mDateTimeString;

    /**
     * コメント保持用.
     */
    private String mComment;

    /**
     * 数量保持用.
     */
    private int mQuantity;

    /**
     * インテント生成.
     * @param activity　{@link Activity}
     * @param dateTime 時刻
     * @param comment　コメント
     * @param quantity　数量
     * @return　{@link Intent}
     */
    public static Intent getNewIntent(Activity activity, String dateTime, String comment, int quantity) {

        Intent intent = new Intent(activity, SubActivity.class);

        // 時刻
        intent.putExtra(INTENT_KEY_DATE_TIME, dateTime);
        // コメント
        intent.putExtra(INTENT_KEY_COMMENT, comment);
        // 数量
        intent.putExtra(INTENT_KEY_QUANTITY, quantity);

        return intent;
    }

    /**
     * {@inheritDoc}
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        setTitle("SubActivity");

        mImageView = (ImageView) findViewById(R.id.image_view);

        Intent intent = getIntent();
        mDateTimeString = intent.getStringExtra(INTENT_KEY_DATE_TIME);
        mComment = intent.getStringExtra(INTENT_KEY_COMMENT);
        mQuantity = intent.getIntExtra(INTENT_KEY_QUANTITY, 0);

        StringBuilder sb = new StringBuilder();
        sb.append("時刻:");
        sb.append(mDateTimeString).append("\n");
        sb.append("コメント:");
        sb.append(mComment).append(("\n"));
        sb.append("数量:");
        sb.append(String.valueOf(mQuantity));

        //MainActivityから値を受け取る
//        QuantityInfo info = (QuantityInfo) getIntent().getSerializableExtra("QuantityInfo");
//        int getint = intent.getIntExtra("quantity", 0);
//        CharSequence getstring1 = intent.getCharSequenceExtra("time");
//        CharSequence getstring2 = intent.getCharSequenceExtra("comment");

        TextView time = (TextView) findViewById(R.id.textView5);
        TextView comment = (TextView) findViewById(R.id.textView6);
        TextView quantity = (TextView) findViewById(R.id.textView7);

        time.setText("受け取った時間は" + mDateTimeString);
        comment.setText("受け取ったコメントは" + mComment);
        quantity.setText("受け取った数量は" + String.valueOf(mQuantity));

        // ボタン押下後の処理
        Button selectButton = (Button) findViewById(R.id.activity_detail_select_button);
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_SUBACTIVITY);
            }
        });

        //リターンボタン押下時の処理
        Button returnButton = (Button) findViewById(R.id.return_button);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getApplication(), MainActivity.class);
//                intent.setType("image/*");
//                startActivityForResult(intent);
//                Intent intent = new Intent();
//                intent.putExtra();



                    // finish() で終わらせて
                    // Intent data を送る
                    finish();


//            Intent intent = new Intent(SubActivity.this, MainActivity.class);
//                startActivity(intent);
//                onPause();
//                Intent intent = new Intent(SubActivity.this, MainActivity.class);
//                startActivity(intent);


            }
        });
    }

        // 画像選択、取得
        @Override
        public void onActivityResult ( int requestCode, int resultCode, Intent resultData){
            if (requestCode == REQUEST_CODE_SUBACTIVITY && resultCode == RESULT_OK) {
                Uri uri = null;
                if (resultData != null) {
                    uri = resultData.getData();

                    try {
                        Bitmap bmp = getBitmapFromUri(uri);
                        mImageView.setImageBitmap(bmp);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        // 画像表示
    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }
}






