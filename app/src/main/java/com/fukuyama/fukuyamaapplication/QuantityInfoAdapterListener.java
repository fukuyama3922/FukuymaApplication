package com.fukuyama.fukuyamaapplication;

/**
 * Created by fukuyama on 2017/07/12.
 */

import com.fukuyama.fukuyamaapplication.db.QuantityInfoEntity;

/**
 * 数量情報リストアダプタの処理を通知する.
 */
public interface QuantityInfoAdapterListener {

    /**
     * 削除ボタンが押下された.
     *
     * @param quantityInfoEntity　押下されたリストアイテムが保持している数量情報
     */
    void onClickDelete(QuantityInfoEntity quantityInfoEntity);

}


