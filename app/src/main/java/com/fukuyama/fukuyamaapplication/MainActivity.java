package com.fukuyama.fukuyamaapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.FileDescriptor;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import static android.R.id.list;
import static com.fukuyama.fukuyamaapplication.R.id.textview_time;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private ArrayList<QuantityInfo> list = new ArrayList<>();
    private static final String TAG = MainActivity.class.getName();
    private static final int QUANTITY_MAX = 9999;
    private static final int QUANTITY_MIN = 0;
    private static final int QUANTITY_ADD = 1;
    private int quantity = QUANTITY_MIN;
    private final Timer timer = new Timer();
    private TextView timerTextView = null;
    private TimerTask timerTask = null;
    private QuantityInfoAdapter adapter = null;

    private final SimpleDateFormat formatter = new SimpleDateFormat("HH:MM:ss.");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //viewの初期化
        initView();
    }

    //initView

    @Override
    protected void onResume() {
        super.onResume();

        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //現在時刻の表示
                        timerTextView.setText(formatter.format(new Date()));
                    }
                });
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //バックグラウンドでの時刻表示の廃止
        timerTask.cancel();
    }


    private void initView() {

        showQuantity();

        TextView quantityTextView = (TextView) findViewById(R.id.textview_quantity);
        quantityTextView.setText("" + quantity);


        final Button buttonPlus = (Button) findViewById(R.id.button_plus);
        buttonPlus.setOnClickListener(this);

        final Button buttonMinus = (Button) findViewById(R.id.button_minus);
        buttonMinus.setOnClickListener(this);

        final Button addButton = (Button) findViewById(R.id.button_add);
        addButton.setOnClickListener(this);

        final Button sendButton = (Button) findViewById(R.id.button_send);
        sendButton.setOnClickListener(this);

        final Button clearButton = (Button) findViewById(R.id.button_clear);
        clearButton.setOnClickListener(this);


        timerTextView = (TextView) findViewById(R.id.textview_time);

        ListView quantityInfoListView = (ListView) findViewById(R.id.listview_quantity_info);
        adapter = new QuantityInfoAdapter(MainActivity.this);
        adapter.setQuantityInfoList(list);
        quantityInfoListView.setAdapter(adapter);
        quantityInfoListView.setOnItemClickListener(this);




    }

    @Override
    public void onClick(View view) {
//
//        Intent intent = new Intent(this, SubActivity.class);
//        int requestCode = 123;
//        startActivityForResult(intent, requestCode);

        switch (view.getId()) {
            case R.id.button_plus:
                culcQuantityPlus();
                return;

            case R.id.button_minus:
                culcQuantityMinus();
                return;

            case R.id.button_add:
                // 追加ボタンを押下された場合
                // 数量情報を1件追加
                addQuantityInfo();
                return;

            case R.id.button_clear:
                // クリアボタンを押下された場合
                // 全てのチェック項目をクリア
                clearList();
                return;

            case R.id.button_send:
                send();
                return;
            default:
                return;

        }
    }

    private void culcQuantityPlus() {
        //数量が以下のとき数量に1を足す
        if (quantity < QUANTITY_MAX) {
            quantity += QUANTITY_ADD;
            //textviewに表示する
            showQuantity();
        }
    }

    private void culcQuantityMinus() {
        //数量が0を超える場合は-1する
        if (quantity > QUANTITY_MIN) {
            quantity -= QUANTITY_ADD;
            //textviewに表示する
            showQuantity();
        }
    }

    private void showQuantity() {
        TextView textViewQuantity = (TextView) findViewById(R.id.textview_quantity);
        textViewQuantity.setText(String.valueOf(quantity));
    }

    public void addQuantityInfo() {
        //時刻を取得
        TextView textView = (TextView) findViewById(R.id.textview_time);
        String time = textView.getText().toString();
        //コメントを取得する
        EditText edittext = (EditText) findViewById(R.id.textview_comment);
        String comment = edittext.getText().toString();
        //数量情報＝1リストを一件追加する
        QuantityInfo quantityInfo = new QuantityInfo();
        quantityInfo.setTime(time);
        quantityInfo.setComment(comment);
        quantityInfo.setQuantity(quantity);
        list.add(quantityInfo);
//        //リストの表示更新
        adapter.notifyDataSetChanged();
    }

    // クリアボタン押下後の処理
    private void clearList() {
        // リストを全てクリア
        list.clear();
        // リストの表示更新
        adapter.notifyDataSetChanged();
    }

    public void send() {
//    Intent intent = new Intent(this.getApplicationContext(), SubActivity.class);
//    QuantityInfo info =;
//            intent.putExtra("QuantityInfo", info);
//    startActivity(intent);
//}
        Intent intent = new Intent(getApplication(), SubActivity.class);
        //時刻を取得
        TextView textView = (TextView) findViewById(R.id.textview_time);
        String time = textView.getText().toString();
        //コメントを取得する
        EditText edittext = (EditText) findViewById(R.id.textview_comment);
        String comment = edittext.getText().toString();

        //intentの作成
        intent.putExtra("time", time);
        intent.putExtra("coment", comment);
        intent.putExtra("quantity", quantity);
        startActivity(intent);

        int requestCode = 123;
        startActivityForResult(intent, requestCode);
        //        intent.setType("image/*");
    }




    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        QuantityInfo info = list.get(position);
        info.setSelected(!info.isSelected());
        adapter.notifyDataSetChanged();

//            Intent intent = new Intent(this.getApplicationContext(), SubActivity.class);
//            QuantityInfo info =list.get(position);
//            intent.putExtra("QuantityInfo", info);
//        //時刻を取得
//        TextView textView = (TextView) findViewById(R.id.textview_time);
//        String time = textView.getText().toString();
//        //コメントを取得する
//        EditText edittext = (EditText) findViewById(R.id.textview_comment);
//        String comment = edittext.getText().toString();
//
//        //intentの作成
//        intent.putExtra("time", time);
//        intent.putExtra("coment", comment);
//        intent.putExtra("quantity", quantity);
        //intentをスタート
//            startActivity(intent);
//    }
    }

//    protected void onActivityResult( int requestCode, int resultCode, Intent intent) {
//        super.onActivityResult(requestCode, resultCode, intent);
//
//        if(resultCode == RESULT_OK && requestCode == RESULT_SUBACTIVITY && null != intent) {
//            int res = intent.getIntExtra("RESULT", 0);
//            String message = intent.getStringExtra("Message");
//            resultData.setText(message +" ... Result = "+String.valueOf(res));
//        }
//    }

    // 画像選択、取得


//    Intent intent = getIntent();
//    //MainActivityから値を受け取る
//
//    CharSequence getstring1 = intent.getCharSequenceExtra("image/*");
//    @Override
//    public void onActivityResult ( int requestCode, int resultCode, Intent resultData){
//        if (requestCode == RESULT_PICK_IMAGEFILE && resultCode == RESULT_OK) {
//            Uri uri = null;
//            if (resultData != null) {
//                uri = resultData.getData();
//
//                try {
//                    Bitmap bmp = getBitmapFromUri(uri);
//                    imageView2.setImageBitmap(bmp);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//    // 画像表示
//
//    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
//        ParcelFileDescriptor parcelFileDescriptor =
//                getContentResolver().openFileDescriptor(uri, "r");
//        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
//        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
//        parcelFileDescriptor.close();
//        return image;
//    }
//

}


