package com.fukuyama.fukuyamaapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static android.R.attr.button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getName();
    private static final int QUANTITY_MAX = 9999;
    private static final int QUANTITY_MIN = 0;
    private static final int QUANTITY_ADD = 1;
    private int quantity = QUANTITY_MIN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //viewの初期化
        initView();
    }

    //initView
    private void initView() {
        final TextView textViewQuantity = (TextView) findViewById(R.id.textview_quantity);
        textViewQuantity.setText("" + quantity);

        Button buttonPlus = (Button) findViewById(R.id.button_plus);
        buttonPlus.setOnClickListener(this);

        Button buttonMinus = (Button) findViewById(R.id.button_minus);
        buttonMinus.setOnClickListener(this);
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
        TextView textViewQuantity = (TextView) findViewById(R.id.textview_quantity);
        //数量が9,999未満のとき数量に1を足す
        if (quantity < QUANTITY_MAX) {
            quantity += QUANTITY_ADD;
            //textviewに表示する
            textViewQuantity.setText("" + quantity);
        }
    }

    private void culcQuantityMinus() {
        TextView textViewQuantity = (TextView) findViewById(R.id.textview_quantity);
        //数量が0を超える場合は-1する
        if (quantity > QUANTITY_MIN) {
            quantity -= QUANTITY_ADD;
            //textviewに表示する
            textViewQuantity.setText("" + quantity);
        }
    }
}
