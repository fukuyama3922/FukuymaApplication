package com.fukuyama.fukuyamaapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
     * ビットマップ保持用.
     */
    private transient Bitmap mBitmap;

    private byte[] mBitmapArray;

    private int COMPRESS_QUALITY;

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
     * ビットマップを取得する.
     *
     * @return
     */
    public Bitmap getBitmap() {
        mBitmap = BitmapFactory.decodeByteArray(mBitmapArray, 0, mBitmapArray.length);
        return mBitmap;
    }

    /**
     * ビットマップをセットする.
     *
     * @return
     */
    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
        serializeBitmap(bitmap);
    }

    /**Bitmap bitmap
     * ビットマップをバイト配列に変換する.
     *
     * @return
     */
    public byte[] getmBitmapArray() {
        return mBitmapArray;
    }

    /**
     * ビットマップをバイト列に変換する.
     */
    private final void serializeBitmap(Bitmap bitmap) {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, COMPRESS_QUALITY, bout);
        mBitmapArray = bout.toByteArray();
    }

}




