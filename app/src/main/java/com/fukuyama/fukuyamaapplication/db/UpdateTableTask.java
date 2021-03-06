package com.fukuyama.fukuyamaapplication.db;

import android.content.Context;
import android.os.AsyncTask;

import com.fukuyama.fukuyamaapplication.AppBean;
import com.fukuyama.fukuyamaapplication.Observer;

/**
 * Created by fukuyama on 2017/07/12.
 */

public class UpdateTableTask extends AsyncTask<QuantityInfoEntity, Void, Long> {

    /**
     * {@link Context}
     */
    private Context mContext;

    // コンストラクタ
    public UpdateTableTask(Context context) {
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
        QuantityInfoDao quantityInfoDao = new QuantityInfoDao(mContext);
        return quantityInfoDao.updateQuantity(quantityInfoEntities[0]);
    }

    /**
     * オブザーバーに通知update
     *
     * @param result
     * */
    @Override
    protected void onPostExecute(Long result) {
        AppBean.notifyObservers(Observer.NOTIFICATION_CODE_UPDATE_QUERY_COMPLETE, new Object[]{null, null, result});
    }

//    @Override
//    protected void onCancelled() {
//        AppBean.notifyObservers(Observer.NOTIFICATION_CODE_INSERT_QUERY_COMPLETE, new Object[]{null, null});
//    }
    }
