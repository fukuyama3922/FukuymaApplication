package com.fukuyama.fukuyamaapplication.db;

import java.io.Serializable;

/**
 * 数量情報Entityクラス.
 */
public class QuantityInfoEntity implements Serializable {

    /**
     * ID.
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

    /**
     * URI(文字列).
     */
    private String mUriString;

    /**
     * コンストラクタ.
     */
    public QuantityInfoEntity() {
        // 処理なし
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
     * URI(文字列)を取得する.
     *
     * @return URI(文字列)
     */
    public String getUriString() {
        return mUriString;
    }

    /**
     * URI(文字列)をセットする.
     *
     * @param uriString URI(文字列)
     */
    public void setUriString(String uriString) {
        mUriString = uriString;
    }
}




