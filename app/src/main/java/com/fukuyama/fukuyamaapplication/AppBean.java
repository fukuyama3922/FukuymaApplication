package com.fukuyama.fukuyamaapplication;

import com.fukuyama.fukuyamaapplication.db.QuantityInfoEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by fukuyama on 2017/07/07.
 */

public class AppBean extends android.app.Application {
    //AppBeanの内容
    /**
     * 数量情報リスト.
     */
    private static ArrayList<QuantityInfoEntity> sQuantityInfoEntityList;

    /**
     * 数量情報.
     */
    private static QuantityInfoEntity sQuantityInfoEntity;

    /**
     * オブザーバー.
     */
    private static List<Observer> sObservers = new CopyOnWriteArrayList<>();

    /**
     * 数量情報リストの取得.
     *
     * @return 数量情報リスト
     */
    public static ArrayList<QuantityInfoEntity> getQuantityInfoEntityList() {
        return sQuantityInfoEntityList;
    }

    /**
     * 数量情報リストのセット.
     *
     * @param quantityInfoEntityList 数量情報リスト
     */
    public static void setQuantityInfoEntityList(ArrayList<QuantityInfoEntity> quantityInfoEntityList) {
        sQuantityInfoEntityList = quantityInfoEntityList;
    }

    /**
     * 数量情報の取得.
     *
     * @return 数量情報
     */
    public static QuantityInfoEntity getQuantityInfoEntity() {
        return sQuantityInfoEntity;
    }

    /**
     * 数量情報のセット.
     *
     * @param quantityInfoEntity 数量情報
     */
    public static void setQuantityInfoEntity(QuantityInfoEntity quantityInfoEntity) {
        sQuantityInfoEntity = quantityInfoEntity;
    }

    //  以降オブザーバーデザインスタイル

    /**
     * オブザーバに通知する.
     *
     * @param notificationCode 通知コード
     * @param options          オプション
     */
    public static void notifyObservers(int notificationCode, Object[] options) {
        for (Observer observer : sObservers) {
            observer.notification(notificationCode, options);
        }
    }

    /**
     * オブザーバを追加する.
     *
     * @param observer {@link Observer}
     */
    public static void addObserver(Observer observer) {
        sObservers.add(observer);
    }

    /**
     * オブザーバを削除する.
     *
     * @param observer {@link Observer}
     */
    public static void removeObserver(Observer observer) {
        sObservers.remove(observer);
    }
}