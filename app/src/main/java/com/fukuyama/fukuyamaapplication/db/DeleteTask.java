package com.fukuyama.fukuyamaapplication.db;

/**
 * Created by fukuyama on 2017/07/08.
 */

import android.content.Context;
import android.os.AsyncTask;

import com.fukuyama.fukuyamaapplication.Application;
import com.fukuyama.fukuyamaapplication.Observer;

/**
 * 追加処理用のタスク
 *
 */
public class DeleteTask extends AsyncTask<QuantityInfoEntity, Void, Long> {

    /**
     * {@link Context}
     */
    private Context mContext;

    // コンストラクタ
    public DeleteTask(Context context) {
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
        return quantityInfoDao.deleteQuantity(quantityInfoEntities[1]);
    }

    /**
     * オブザーバーに通知
     *
     * @param result
     */
    @Override
    protected void onPostExecute(Long result) {
        Application.notifityObservers(Observer.NOTIFICATION_CODE_DELETE_QUERY_COMPLETE, new Object[]{result, null});
    }

//    @Override
//    protected void onCancelled() {
//        Application.notifityObservers(Observer.NOTIFICATION_CODE_INSERT_QUERY_COMPLETE, new Object[]{null, null});
//    }
}
