package com.fukuyama.fukuyamaapplication;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class QuantityInfoAdapter extends BaseAdapter implements View.OnClickListener{

    private LayoutInflater layoutInflater = null;
    private ArrayList<QuantityInfo> quantityInfoList;
    private Context context;

    public QuantityInfoAdapter(Context context) {
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setQuantityInfoList(ArrayList<QuantityInfo> quantityInfoList) {
        this.quantityInfoList = quantityInfoList;
    }

    @Override
    public int getCount() {
        return quantityInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return quantityInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return quantityInfoList.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.row_quantity_info, parent, false);
        }

        QuantityInfo info = quantityInfoList.get(position);

//        // 行が選択されていたら
//        if (info.isSelected()) {
//            // 行を緑に変更
//            convertView.setBackgroundColor(Color.GREEN);
//        } else {
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


        Button deleteButton = (Button) convertView.findViewById(R.id.button_delete);
        deleteButton.setOnClickListener(this);
        deleteButton.setTag(position);

        //((CheckBox) convertView.findViewById(R.id.chk_select)).setChecked(info.isSelected());
        ((TextView) convertView.findViewById(R.id.textview_time)).setText(info.getTime());

        ((TextView) convertView.findViewById(R.id.textview_quantity)).setText("" + info.getQuantity());
        ((TextView) convertView.findViewById(R.id.textview_comment)).setText(info.getComment());

        return convertView;
    }

    @Override
    // 削除ボタン押下後の処理
    public void onClick(View v) {

        // リストの中から選択したinfoを削除
        int position = Integer.valueOf(v.getTag().toString()).intValue();
        quantityInfoList.remove(position);
        // listViewの表示更新
        notifyDataSetChanged();

    }
}
