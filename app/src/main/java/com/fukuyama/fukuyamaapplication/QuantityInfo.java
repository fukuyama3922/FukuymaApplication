package com.fukuyama.fukuyamaapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;

/**
 * Created by fukuyama on 2017/05/27.
 */

/**
 * 数量情報.
 */
public class QuantityInfo implements Serializable {

    /**
     * 数量.
     */
    private int mQuantity;

    /**
     * 時刻.
     */
    private String mTime;

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
     *
     * @param quantity 数量
     * @param time     時間
     * @param comment  コメント
     */
    public QuantityInfo(int quantity, String time, String comment) {
        mQuantity = quantity;
        mTime = time;
        mComment = comment;
        // TODO:コンストラクタで指定するかは検討
        mIsSelected = false;
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
    public String getTime() {
        return mTime;
    }

    /**
     * 時刻をセットする.
     *
     * @param time
     */
    public void setTime(String time) {
        mTime = time;
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
    public  void setBitmapString(String bitmapString){
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
    public void setQuantityInfo(QuantityInfo quantityInfo) {
        mQuantity = quantityInfo.getQuantity();
        mIsSelected = quantityInfo.isSelected();
        mComment = quantityInfo.getComment();
        mBitmapString = quantityInfo.getBitmapString();
    }
}




