package com.fukuyama.fukuyamaapplication;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.fukuyama.fukuyamaapplication.db.QuantityInfoEntity;

import java.util.ArrayList;

public class QuantityInfoAdapter extends BaseAdapter implements View.OnClickListener {


    /**
     * {@link LayoutInflater}
     */
    private LayoutInflater mLayoutInflater = null;

    /**
     * 数量情報リスト.
     */
    private ArrayList<QuantityInfoEntity> mQuantityInfoEntityList;

    /**
     * {@link QuantityInfoAdapterListener}
     */
    private QuantityInfoAdapterListener mListener;


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
     * @param quantityInfoEntityList
     */
    public void setQuantityInfoList(ArrayList<QuantityInfoEntity> quantityInfoEntityList) {
        mQuantityInfoEntityList = quantityInfoEntityList;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public int getCount() {
        return mQuantityInfoEntityList.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuantityInfoEntity getItem(int position) {
        return mQuantityInfoEntityList.get(position);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getItemId(int position) {
        return mQuantityInfoEntityList.get(position).hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.check_select:
                //チェックボタン押下時の処理
                return;
            case R.id.button_delete:
                //削除ボタン押下時の処理.
                int position = Integer.valueOf(v.getTag().toString()).intValue();
                onClickDeleteButton(position);
//                mQuantityInfoEntityList.remove(position);

//
//                onClickDeleteButton();
                // listViewの表示更新
//                notifyDataSetChanged();
                return;
            default:
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

        QuantityInfoEntity info = getItem(position);

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

        CheckBox check = (CheckBox) convertView.findViewById(R.id.check_select);
        check.setOnClickListener(this);

        ((TextView) convertView.findViewById(R.id.text_time)).setText(info.getDate());
        ((TextView) convertView.findViewById(R.id.text_quantity)).setText("" + info.getQuantity());
        ((TextView) convertView.findViewById(R.id.text_comment)).setText(info.getComment());
        ((CheckBox) convertView.findViewById(R.id.check_select)).setChecked(info.isSelected());
        ((CheckBox) convertView.findViewById(R.id.check_img)).setChecked(!TextUtils.isEmpty(info.getUriString()));

        return convertView;
    }

    /**
     * {@link QuantityInfoAdapterListener} をセットする.
     *
     * @param listener {@link QuantityInfoAdapterListener}
     */
    public void setQuantityInfoAdapterListener(QuantityInfoAdapterListener listener) {
        mListener = listener;
    }

    /**
     * 任意のポジションの数量情報を削除する.
     *
     * @param position リストのポジション
     */
    private void onClickDeleteButton(int position) {
        mListener.onClickDelete(getItem(position));
    }


}

