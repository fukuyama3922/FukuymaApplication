package com.fukuyama.fukuyamaapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import static com.fukuyama.fukuyamaapplication.R.id.textView3;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getName();
    private static final int QUANTITY_MAX = 9999;
    private static final int QUANTITY_MIN = 0;
    private static final int QUANTITY_ADD = 1;
    private int quantity = QUANTITY_MIN;

    private static SimpleDateFormat formatter = new SimpleDateFormat("HH:MM:ss.");

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

        final TextView textView3;
        Timer timer1;
        textView3 = (TextView)findViewById(R.id.textView3);
        timer1 = new Timer();
        timer1.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                Log.d("run", "TimerTask Thread id = " + Thread.currentThread().getId());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Log.d("run", "runOnUiThread Thread id = " + Thread.currentThread().getId());
                        textView3.setText(formatter.format(new Date()));
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
        //数量が9,999未満のとき数量に1を足す
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


  public void showTimer() {

  }
}

