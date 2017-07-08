package com.fukuyama.fukuyamaapplication.db;

import android.content.Context;
import android.os.AsyncTask;

import com.fukuyama.fukuyamaapplication.Application;
import com.fukuyama.fukuyamaapplication.Observer;

/**
 * Created by fukuyama on 2017/07/07.
 */

/**
 * 追加処理用のタスク
 *
 */
public class InsertTask extends AsyncTask<QuantityInfoEntity, Void, Long> {

    /**
     * {@link Context}
     */
    private Context mContext;

    // コンストラクタ
    public InsertTask(Context context) {
        mContext = context;
    }

    /**
     * バックグラウンドでの追加処理.
     *
     * @param quantityInfoEntities
     * @return
     */
    @Override
    protected Long doInBackground(QuantityInfoEntity... quantityInfoEntities) {

        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {

        }

        QuantityInfoDao quantityInfoDao = new QuantityInfoDao(mContext);
        return quantityInfoDao.insertQuantity(quantityInfoEntities[0]);
    }

    /**
     * オブザーバーに通知
     *
     * @param result
     */
    @Override
    protected void onPostExecute(Long result) {
        Application.notifityObservers(Observer.NOTIFICATION_CODE_INSERT_QUERY_COMPLETE, new Object[]{result, null});
    }

//    @Override
//    protected void onCancelled() {
//        Application.notifityObservers(Observer.NOTIFICATION_CODE_INSERT_QUERY_COMPLETE, new Object[]{null, null});
//    }
}
