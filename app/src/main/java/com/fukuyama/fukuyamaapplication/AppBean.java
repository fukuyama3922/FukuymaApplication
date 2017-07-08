package com.fukuyama.fukuyamaapplication;

import com.fukuyama.fukuyamaapplication.db.QuantityInfoDao;
import com.fukuyama.fukuyamaapplication.db.QuantityInfoEntity;

import java.util.ArrayList;

/**
 * Created by fukuyama on 2017/07/08.
 */

// TODO:AppBeanから情報取得
public class AppBean extends Application{
    ArrayList<QuantityInfoEntity> list = new ArrayList<> ();
//
//
//    public long getQuantityInfoList(){
//    QuantityInfoDao quantityInfoDao = new QuantityInfoDao(this);
//    ArrayList<QuantityInfoEntity> InfoList = quantityInfoDao.findAll();
//        return quantityInfoList;}
}
