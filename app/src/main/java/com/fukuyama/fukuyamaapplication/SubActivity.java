package com.fukuyama.fukuyamaapplication;

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
import java.util.Date;
import java.util.TimerTask;

/**
 * Created by fukuyama on 2017/06/10.
 */

public class SubActivity extends AppCompatActivity {

    private static final int RESULT_PICK_IMAGEFILE = 1000;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        setTitle("SubActivity");


//    @Override
//    protected void onResume() {
//        super.onResume();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//    }


        imageView = (ImageView) findViewById(R.id.image_view);

        Intent intent = getIntent();
        //MainActivityから値を受け取る
//        QuantityInfo info = (QuantityInfo) getIntent().getSerializableExtra("QuantityInfo");
        int getint = intent.getIntExtra("quantity", 0);
        CharSequence getstring1 = intent.getCharSequenceExtra("time");
        CharSequence getstring2 = intent.getCharSequenceExtra("comment");

        TextView t1 = (TextView) findViewById(R.id.textView5);
        TextView t2 = (TextView) findViewById(R.id.textView6);
        TextView t3 = (TextView) findViewById(R.id.textView7);

//        TextView time = (TextView) findViewById(R.id.textView5);
//        TextView comment = (TextView) findViewById(R.id.textView6);
//        TextView quantity = (TextView) findViewById(R.id.textView7);

//        //受け取った値を表示
//        time.setText(info.getTime());
//        comment.setText(info.getComment());
//        quantity.setText("" + info.getQuantity());

        t1.setText("受け取った時間は" + getstring1);
        t2.setText("受け取ったコメントは" + getstring2);
        t3.setText("受け取った数量は" + String.valueOf(getint));

        // ボタン押下後の処理
        Button selectButton = (Button) findViewById(R.id.activity_detail_select_button);
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, RESULT_PICK_IMAGEFILE);
            }
        });


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
            if (requestCode == RESULT_PICK_IMAGEFILE && resultCode == RESULT_OK) {
                Uri uri = null;
                if (resultData != null) {
                    uri = resultData.getData();

                    try {
                        Bitmap bmp = getBitmapFromUri(uri);
                        imageView.setImageBitmap(bmp);
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






