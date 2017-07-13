package com.fukuyama.fukuyamaapplication.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * DBオープンヘルパー.
 */
public class DbOpenHelper extends SQLiteOpenHelper {

    /**
     * コンストラクタ.
     *
     * @param context {@link Context}
     */
    public DbOpenHelper(Context context) {
        super(context, DbConst.DB_NAME, null, DbConst.DB_VERSION);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        //テーブルを作成するSQL文の定義　※スペースに気をつける.
        String createSql = "CREATE TABLE " + DbConst.DB_TABLE + " ("
                + DbConst.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DbConst.COL_UPDATED_QUANTITY + " INTEGER,"
                + DbConst.COL_DATE + " TEXT NOT NULL,"
                + DbConst.COL_COMMENT + " TEXT,"
                + DbConst.COL_IMAGE_URI + " TEXT"
                + ");";

        //SQL文の実行
        db.execSQL(createSql);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // DBからテーブル削除
        String dropTableSql = "DROP TABLE IF EXIST" + DbConst.DB_TABLE;
        db.execSQL(dropTableSql);
        // テーブル生成
        onCreate(db);
    }
}

