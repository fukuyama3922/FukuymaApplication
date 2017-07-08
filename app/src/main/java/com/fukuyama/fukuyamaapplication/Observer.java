package com.fukuyama.fukuyamaapplication;

/**
 * オブザーバ
 */
public interface Observer {

    /**
     * 通知コード:DB追加処理完了.
     */
    public  static final int NOTIFICATION_CODE_INSERT_QUERY_COMPLETE = 1001;

    /**
     * 通知コード:DB削除処理完了.
     */
    public  static final int NOTIFICATION_CODE_DELETE_QUERY_COMPLETE = 1002;

    /**
     * オプションインデックス:DB追加処理結果.
     */
    public  static final int OPTION_INDEX_INSERT_QUERY_RESULT = 0;

    /**
     * オプションインデックス:DB削除処理結果.
     */
    public static final int OPTION_INDEX_DELETE_QUERY_RESULT = 1;

    /**
     * 通知用メソッド.
     *
     * @param notificationbCode 通知コード
     * @param options　　　　　　 オプション:[0]DB追加処理結果、[1]DB削除処理結果
     */
    void notification(int notificationbCode, Object[] options);
}
