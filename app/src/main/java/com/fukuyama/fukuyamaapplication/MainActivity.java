package com.fukuyama.fukuyamaapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * メインアクティビティ.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    /**
     * ログ出力用のタグ.
     */
    private static final String TAG = MainActivity.class.getName();

    /**
     * 数量の上限.
     */
    private static final int QUANTITY_MAX = 9999;

    /**
     * 数量の下限.
     */
    private static final int QUANTITY_MIN = 0;

    /**
     * 加算減算される値.
     */
    private static final int QUANTITY_ADD = 1;

    /**
     * 表示される初期値.
     */
    private int quantity = QUANTITY_MIN;

    /**
     * 数量、コメント、時刻情報.
     */
    private ArrayList<QuantityInfo> mList = new ArrayList<>();

    /**
     * {@limk Timer}
     */
    private Timer mTimer = new Timer();

    private TextView mTimerTextView = null;

    private TimerTask mTimerTask = null;
    /**
     * 数量情報リストアダプタ保持用.
     */
    private QuantityInfoAdapter mAdapter = null;

    private final SimpleDateFormat mFormatter = new SimpleDateFormat("HH:MM:ss.");

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //viewの初期化
        initView();
    }

    //initView

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onResume() {
        super.onResume();

        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //現在時刻の表示
                        mTimerTextView.setText(mFormatter.format(new Date()));
                    }
                });
            }
        };
        mTimer.scheduleAtFixedRate(mTimerTask, 0, 1000);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onPause() {
        super.onPause();
        //バックグラウンドでの時刻表示の廃止.
        mTimerTask.cancel();
    }

    /**
     * initView
     */
    private void initView() {

        //初期表示される数量.
        updateQuantityText(quantity);

        //数量表示.
        TextView quantityTextView = (TextView) findViewById(R.id.textview_quantity);
        quantityTextView.setText("" + quantity);

        //プラスボタンの設定.
        final Button plusButton = (Button) findViewById(R.id.button_plus);
        plusButton.setOnClickListener(this);
        //マイナスボタンの設定.
        final Button minusButton = (Button) findViewById(R.id.button_minus);
        minusButton.setOnClickListener(this);
        //追加ボタンの設定.
        final Button addButton = (Button) findViewById(R.id.button_add);
        addButton.setOnClickListener(this);
        //送信ボタンの設定.
        final Button sendButton = (Button) findViewById(R.id.button_send);
        sendButton.setOnClickListener(this);
        //クリアボタンの設定.
        final Button clearButton = (Button) findViewById(R.id.button_clear);
        clearButton.setOnClickListener(this);

        mTimerTextView = (TextView) findViewById(R.id.textview_time);

        ListView quantityInfoListView = (ListView) findViewById(R.id.listview_quantity_info);
        mAdapter = new QuantityInfoAdapter(MainActivity.this);
        mAdapter.setmQuantityInfoList(mList);
        quantityInfoListView.setAdapter(mAdapter);
        quantityInfoListView.setOnItemClickListener(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.button_plus:
                //プラスボタン押下時の処理.
                culcQuantityPlus();
                return;

            case R.id.button_minus:
                //マイナスボタン押下時の処理.
                culcQuantityMinus();
                return;

            case R.id.button_add:
                // 追加ボタン押下時の処理.
                addQuantityInfo();
                return;

            case R.id.button_clear:
                // クリアボタン押下時の処理.
                clearList();
                return;

            case R.id.button_send:
                //送信ボタン押下時の処理.
                send();
                return;

            default:
                return;
        }
    }

    /**
     * 数量が以下の場合数量を加算する.
     */
    private void culcQuantityPlus() {
        //上限の場合、警告を表示して処理を抜ける.
        if (quantity >= QUANTITY_MAX) {
            MessageUtil.showToast(this, getString(R.string.message_input_error));
            return;
        }

        //textviewに表示する.
        quantity += QUANTITY_ADD;
        updateQuantityText(quantity);
    }


    /**
     * 数量が以下の場合数量を減算する.
     */
    private void culcQuantityMinus() {
        //下限の場合、警告を表示して処理を抜ける.
        if (quantity <= QUANTITY_MIN) {
            MessageUtil.showToast(this, getString(R.string.message_input_error));
            return;
        }

        //textviewに表示する.
        quantity -= QUANTITY_ADD;
        //textviewに表示する.
        updateQuantityText(quantity);
    }

    /**
     * リストを一件追加する.
     */
    private void addQuantityInfo() {
        QuantityInfo quantityInfo = new QuantityInfo();
        quantityInfo.setmTime(getDate());
        quantityInfo.setmComment(getComment());
        quantityInfo.setmQuantity(quantity);
        mList.add(quantityInfo);
//        //リストの表示更新
        mAdapter.notifyDataSetChanged();
    }

    /**
     * リストをクリアする.
     */
    private void clearList() {
        mList.clear();
        // リストの表示更新.
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 詳細画面を呼び出し、時刻、コメント、数量の情報を受け渡す.
     */
    public void send() {
        Intent intent = SubActivity.getNewIntent(
                this,
                getDate(),
                getComment(),
                quantity);
//        int requestCode = RESULT_SUBACTIVITY;
//        startActivityForResult( intent, requestCode );
        startActivity(intent);
    }

    protected void onActivityResult( int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        }


    /**
     * {@inheritDoc}
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        QuantityInfo info = mList.get(position);
        info.setSelected(!info.isSelected());
        mAdapter.notifyDataSetChanged();
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

    /**
     * 編集した時刻を取得.
     *
     * @return
     */
    private String getDate() {
        TextView textView = (TextView) findViewById(R.id.textview_time);
        return textView.getText().toString();
    }

    /**
     * 編集したコメントを取得.
     *
     * @return
     */
    private String getComment() {
        TextView textView = (TextView) findViewById(R.id.textview_comment);
        return textView.getText().toString();
    }

    /**
     * 数量の表示を更新する.
     *
     * @param quantity 　数量
     */
    private void updateQuantityText(int quantity) {
        TextView textViewQuantity = (TextView) findViewById(R.id.textview_quantity);
        textViewQuantity.setText(String.valueOf(quantity));
    }
}


