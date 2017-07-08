package com.fukuyama.fukuyamaapplication;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by fukuyama on 2017/07/07.
 */

public class Application extends android.app.Application{

    private static List<Observer> sObserevers = new CopyOnWriteArrayList<>();

    /**
     * オブザーバに通知する.
     *
     * @param notificationCode 通知コード
     * @param options             オプション
     */
    public static void notifityObservers(int notificationCode, Object[] options ) {
        for (Observer observer : sObserevers) {
            observer.notification(notificationCode, options);
        }
    }

    /**
     * オブザーバを追加する.
     *
     *
     * @param observer {@link Observer}
     */
    public static void addObserver(Observer observer) {
        sObserevers.add(observer);
    }

    /**
     * オブザーバを削除する.
     *
     * @param observer {@link Observer}
     */
    public static void removeObserver(Observer observer) {
        sObserevers.remove(observer);
    }
}