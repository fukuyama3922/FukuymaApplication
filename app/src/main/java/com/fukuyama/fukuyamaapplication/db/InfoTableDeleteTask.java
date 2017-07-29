package com.fukuyama.fukuyamaapplication.db;

/**
 * Created by fukuyama on 2017/07/08.
 */

import android.content.Context;
import android.os.AsyncTask;


import com.fukuyama.fukuyamaapplication.AppBean;
import com.fukuyama.fukuyamaapplication.Observer;

/**
 * 追加処理用のタスク
 */
public class InfoTableDeleteTask extends AsyncTask<QuantityInfoEntity, Void, Long> {

    /**
     * {@link Context}
     */
    private Context mContext;

    // コンストラクタ
    public InfoTableDeleteTask(Context context) {
        mContext = context;
    }



    /**
     * バックグラウンドでの追加処理.
     *
     * @param quantityInfoEntity
     * @return
     */
    @Override
    protected Long doInBackground(QuantityInfoEntity... quantityInfoEntity) {
        QuantityInfoDao quantityInfoDao = new QuantityInfoDao(mContext);
        return quantityInfoDao.deleteQuantity(quantityInfoEntity[0]);
   }

    /**
     * オブザーバーに通知
     *
     * @param result
     */
    @Override
   public void onPostExecute(Long result) {
        AppBean.notifyObservers(Observer.NOTIFICATION_CODE_DELETE_QUERY_COMPLETE, new Object[]{null, result, null});
    }

//    @Override
//    protected void onCancelled() {
//        AppBean.notifyObservers(Observer.NOTIFICATION_CODE_INSERT_QUERY_COMPLETE, new Object[]{null, null});
//    }
}
