package com.fukuyama.fukuyamaapplication.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.fukuyama.fukuyamaapplication.activity.MainActivity;

/**
 * DBオープンヘルパー.
 */
public class DbOpenHelper extends SQLiteOpenHelper {

    // TODO:後で中身を適切なものに修正
    /**
     * DB名.
     */
    private static String DB_NAME = "db_detaile";

    // TODO:後で中身を適切なものに修正
    /**
     * テーブル名.
     */
    private static String DB_TABLE = "mSheet";

    /**
     * DBバージョン.
     */
    private static int DB_VERSION = 1;

    //IDカラム
    private static final String COL_ID = "col_id";

    //数量カラム
    private static final String COL_UPDATED_QUANTITY = "col_updatedQuantity";

    //日時カラム
    private static final String COL_DATE = "col_date";

    //タイトルカラム
    private static final String COL_COMMENT = "col_comment";

    //画像カラム
    private static final String COL_PICTURE = "col_picture";

    /**
     * コンストラクタ.
     *
     * @param context {@link Context}
     */
    public DbOpenHelper(MainActivity context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        //テーブルを作成するSQL文の定義　※スペースに気をつける.
        String createTb1 = "CREATE TABLE " + DB_TABLE + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_UPDATED_QUANTITY + " INTEGER,"
                + COL_DATE + " TEXT NOT NULL,"
                + COL_COMMENT + " TEXT,"
                + COL_PICTURE + " INTEGER"
                + ");";

        //SQL文の実行
        db.execSQL(createTb1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // DBからテーブル削除
        String dropTableSql = "DROP TABLE IF EXIST" + DB_TABLE;
        db.execSQL(dropTableSql);
        // テーブル生成
        onCreate(db);
    }
}
