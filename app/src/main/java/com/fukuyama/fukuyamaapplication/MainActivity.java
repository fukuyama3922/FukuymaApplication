package com.fukuyama.fukuyamaapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import static android.R.id.list;
import static com.fukuyama.fukuyamaapplication.R.id.label_time;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getName();
    private static final int QUANTITY_MAX = 9999;
    private static final int QUANTITY_MIN = 0;
    private static final int QUANTITY_ADD = 1;
    private int quantity = QUANTITY_MIN;

    private final SimpleDateFormat formatter = new SimpleDateFormat("HH:MM:ss.");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //viewの初期化
        initView();
    }

    //initView
    private void initView() {
        showQuantity();

        Button buttonPlus = (Button) findViewById(R.id.button_plus);
        buttonPlus.setOnClickListener(this);

        Button buttonMinus = (Button) findViewById(R.id.button_minus);
        buttonMinus.setOnClickListener(this);

        Button buttonAdd = (Button) findViewById(R.id.button_add);
        buttonAdd.setOnClickListener(this);

        final TextView label_time = (TextView)findViewById (R.id.label_time);
        Timer timer;

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        label_time.setText(formatter.format(new Date()));
                    }
                });
            }
        }, 0, 10);
    }






    @Override
    public void onClick(View view) {
        Log.d(TAG, "onClick");
        switch (view.getId()) {
            case R.id.button_plus:
                culcQuantityPlus();
                break;

            case R.id.button_minus:
                culcQuantityMinus();
                break;
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
        TextView textView = (TextView) findViewById(R.id.label_time);
        String time = textView.getText().toString();
        //コメントを取得する
        EditText edittext = (EditText) findViewById(R.id.edit_comment);
        String comment = edittext.getText().toString();
        //数量情報＝1リストを一件追加する
        QuantityInfo quantityInfo = new QuantityInfo();
        quantityInfo.setTime(time);
        quantityInfo.setComment(comment);
        quantityInfo.setQuantity(quantity);
//        list.add(quantityInfo);
//        //リストの表示更新
//        adapter.notifyDataSetChanged();
    }


  }


