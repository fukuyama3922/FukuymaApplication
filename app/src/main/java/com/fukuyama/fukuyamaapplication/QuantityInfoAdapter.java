package com.fukuyama.fukuyamaapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import static com.fukuyama.fukuyamaapplication.MainActivity.RESULT_SUBACTIVITY;

public class QuantityInfoAdapter extends BaseAdapter implements View.OnClickListener {

    private LayoutInflater mLayoutInflater = null;
    private ArrayList<QuantityInfo> mQuantityInfoList;
    private Context mContext;


    public QuantityInfoAdapter(Context context) {
        this.mContext = context;
        this.mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setmQuantityInfoList(ArrayList<QuantityInfo> mQuantityInfoList) {
        this.mQuantityInfoList = mQuantityInfoList;
    }




//    @Override
//    public int getmAdapterQuantity() {
//        return mQuantityInfoList.getmQuantity();
//    }

    @Override
    public int getCount() {
        return mQuantityInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return mQuantityInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mQuantityInfoList.get(position).hashCode();
    }



    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_delete:
                //削除ボタン押下時の処理.
                int position = Integer.valueOf(v.getTag().toString()).intValue();
                mQuantityInfoList.remove(position);
                // listViewの表示更新
                notifyDataSetChanged();

                return;

//            case R.id.button_send:
//                Intent intent = SubActivity.getNewIntent(
//                this,
//                        getCount(),
//                        info.getmQuantity(),
//                        mQuantity(),
//                getDate(),
//                getComment(),
//                quantity);
//        startActivityForResult(intent, RESULT_SUBACTIVITY);
//                return;


        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.row_quantity_info, parent, false);
        }

        QuantityInfo info = mQuantityInfoList.get(position);

        // 行が選択されていたら
        if (info.isSelected()) {
            // 行を緑に変更
            convertView.setBackgroundColor(Color.GREEN);
        } else {
            // 奇数か偶数か
            switch (position % 2) {
                case 0:
                    // 偶数だった場合
                    convertView.setBackgroundColor(Color.BLUE);
                    break;
                case 1:
                    // 奇数だった場合
                    convertView.setBackgroundColor(0);
                    break;

            }
        }


        Button deleteButton = (Button) convertView.findViewById(R.id.button_delete);
        deleteButton.setOnClickListener(this);
        deleteButton.setTag(position);

//        Button sendButton = (Button) convertView.findViewById(R.id.button_send);
//        sendButton.setOnClickListener(this);
//        sendButton.setTag(position);

        ((CheckBox) convertView.findViewById(R.id.chk_select)).setChecked(info.isSelected());
        ((TextView) convertView.findViewById(R.id.textview_time)).setText(info.getmTime());

        ((TextView) convertView.findViewById(R.id.textview_quantity)).setText("" + info.getmQuantity());
        ((TextView) convertView.findViewById(R.id.textview_detaile_comment)).setText(info.getmComment());




        return convertView;


    }

//    @Override
//    // 削除ボタン押下後の処理
//    public void onClick(View v) {
//
//
//        switch (view.getId()) {
//            case R.id.button_delete:
//                //削除ボタン押下時の処理.
//                int position = Integer.valueOf(v.getTag().toString()).intValue();
//                mQuantityInfoList.remove(position);
//                // listViewの表示更新
//                notifyDataSetChanged();
//                delete();
//                return;
//
//            default:
//                return;
//        }
//    }


//    public void delete() {
//        // リストの中から選択したinfoを削除
//        int position = Integer.valueOf(v.getTag().toString()).intValue();
//        mQuantityInfoList.remove(position);
//        // listViewの表示更新
//        notifyDataSetChanged();
//
//    }

//    public void send() {
//        Intent intent = SubActivity.getNewIntent(
//                this,
//                getResultdata(),
//                getDate(),
//                getComment(),
//                quantity);
//        startActivityForResult(intent, RESULT_SUBACTIVITY);
//    }


}
