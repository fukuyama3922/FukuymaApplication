package com.fukuyama.fukuyamaapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static android.R.attr.button;

public class MainActivity extends AppCompatActivity {
    private static final int QUANTITY_MAX = 9999;
    private static final int QUANTITY_MIN = 0;
    private static final int QUANTITY_ADD = 1;
    private int quantity = QUANTITY_MIN;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView = (TextView) findViewById(R.id.textview_quantity);
        textView.setText("" + quantity);



        Button buttonPlus = (Button) findViewById(R.id.button_plus);
        buttonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //数量が9,999未満のとき数量に1を足す
                if (quantity < QUANTITY_MAX) {
                    quantity += QUANTITY_ADD;
                    //textviewに表示する
                    textView.setText("" + quantity);
                }
            }
        });

        Button buttonMinus = (Button) findViewById(R.id.button_minus);

        buttonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //数量が0を超える場合は-1する
                if (quantity > QUANTITY_MIN) {
                    quantity -= QUANTITY_ADD;
                    //textviewに表示する
                    textView.setText("" + quantity);
                }
            }
        });
    }
}
