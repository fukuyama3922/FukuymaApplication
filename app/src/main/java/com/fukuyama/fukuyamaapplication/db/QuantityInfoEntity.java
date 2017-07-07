package com.fukuyama.fukuyamaapplication.db;

import android.graphics.Bitmap;

import com.fukuyama.fukuyamaapplication.util.BitmapUtil;

import java.io.Serializable;

/**
 * 数量情報Entityクラス.
 */
public class QuantityInfoEntity implements Serializable {

    /**
     *
     */
    private int mId;

    /**
     * 数量.
     */
    private int mQuantity;

    /**
     * 時刻.
     */
    private String mDate;

    /**
     * コメント.
     */
    private String mComment;

    /**
     * 選択状態.
     */
    private boolean mIsSelected;

    // TODO:不要になった段階で削除予定
    /**
     * 編集されているインデックス.
     */
    private int mEditIndex;

    /**
     * ビットマップ(文字列）保持用.
     */
    private String mBitmapString;

    /**
     * コンストラクタ.
     */
    public QuantityInfoEntity() {
        // 処理なし
    }

    /**
     * 選択されているか.
     *
     * @return true = 選択されている, false = 選択されていない
     */
    public boolean isSelected() {
        return mIsSelected;
    }

    /**
     * 選択状態をセットする.
     *
     * @param selected 選択状態
     */
    public void setSelected(boolean selected) {
        mIsSelected = selected;
    }


    /**
     * IDを取得する.
     *
     * @return
     */
    public int getId() {
        return mId;
    }

    /**
     * IDをセットする.
     *
     * @param id id
     */
    public void setId(int id) {
        mId = id;
    }

    /**
     * 数量を取得する.
     *
     * @return 数量
     */
    public int getQuantity() {
        return mQuantity;
    }

    /**
     * 数量をセットする.
     *
     * @param quantity 数量
     */
    public void setQuantity(int quantity) {
        mQuantity = quantity;
    }

    /**
     * 時刻を取得する.
     *
     * @return 時刻
     */
    public String getDate() {
        return mDate;
    }

    /**
     * 時刻をセットする.
     *
     * @param date
     */
    public void setDate(String date) {
        mDate = date;
    }

    /**
     * コメントを取得する.
     *
     * @return コメント
     */
    public String getComment() {
        return mComment;
    }

    /**
     * コメントをセットする.
     *
     * @param comment コメント
     */
    public void setComment(String comment) {
        mComment = comment;
    }

    /**
     * 編集しているインデックスを取得する.
     */
    public int getEditIndex() {
        return mEditIndex;
    }

    /**
     * 編集しているインデックスをセットする.
     *
     * @param editIndex
     */
    public void setEditIndex(int editIndex) {
        mEditIndex = editIndex;
    }

    /**
     * ビットマップ(文字列）を取得する.
     */
    public String getBitmapString() {
        return mBitmapString;
    }

    /**
     * ビットマップ(文字列）セットをする.
     */
    public void setBitmapString(String bitmapString) {
        mBitmapString = bitmapString;
    }

    /**
     * ビットマップを取得する
     */
    public Bitmap getBitmap() {
        return BitmapUtil.StringToBitMap(mBitmapString);
    }

    /**
     * 数量情報をセットする
     */
    public void setQuantityInfo(QuantityInfoEntity quantityInfoEntity) {
        mQuantity = quantityInfoEntity.getQuantity();
        mIsSelected = quantityInfoEntity.isSelected();
        mComment = quantityInfoEntity.getComment();
        mBitmapString = quantityInfoEntity.getBitmapString();
    }
}




