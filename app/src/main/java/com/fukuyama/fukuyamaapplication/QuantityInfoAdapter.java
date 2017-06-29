package com.fukuyama.fukuyamaapplication;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class QuantityInfoAdapter extends BaseAdapter implements View.OnClickListener {

    /**
     * {@link LayoutInflater}
     */
    private LayoutInflater mLayoutInflater = null;

    /**
     * 数量情報リスト.
     */
    private ArrayList<QuantityInfo> mQuantityInfoList;

    /**
     * {@link Context}
     */
    private Context mContext;

    /**
     * コンストラクタ.
     *
     * @param context {@link Context}
     */
    public QuantityInfoAdapter(Context context) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * 数量情報をアダプタにセットする.
     *
     * @param quantityInfoList
     */
    public void setQuantityInfoList(ArrayList<QuantityInfo> quantityInfoList) {
        mQuantityInfoList = quantityInfoList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCount() {
        return mQuantityInfoList.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuantityInfo getItem(int position) {
        return mQuantityInfoList.get(position);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getItemId(int position) {
        return mQuantityInfoList.get(position).hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_delete:
                //削除ボタン押下時の処理.

                int position = Integer.valueOf(v.getTag().toString()).intValue();
                mQuantityInfoList.remove(position);

                // listViewの表示更新
                notifyDataSetChanged();

                return;

        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.row_quantity_info, parent, false);
        }

        QuantityInfo info = getItem(position);

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

        ((CheckBox) convertView.findViewById(R.id.check_select)).setChecked(info.isSelected());
        ((TextView) convertView.findViewById(R.id.text_time)).setText(info.getTime());

        ((TextView) convertView.findViewById(R.id.text_quantity)).setText("" + info.getQuantity());
        ((TextView) convertView.findViewById(R.id.text_comment)).setText(info.getComment());

//        ビットマップ表示領域にビットマップをセット
//        if (info.getBitmap() != null) {
//            ((ImageView) convertView.findViewById(R.id.image_bitmap_container)).setImageBitmap(info.getBitmap());
//        }

        return convertView;
            }
}
