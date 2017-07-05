package com.fukuyama.fukuyamaapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



/**
 * Created by fukuyama on 2017/06/24.
 */

public class DetaileDBHelper extends SQLiteOpenHelper {

    //データベース名
    private static final String DB_NAME ="detaile.db";

    //データベースバージョン
    private static final int DB_VERSION = 1;

    //テーブル名
    public static final String TABLE_NAME ="detaile";

    //IDカラム
    public static final String _ID ="_id";

    //ファイル名カラム
    public static final String TITLE = "title";

    //ファイルパスカラム
    public static final String DATA = "_data";

    //作成日時
    public static final String DATA_ADDED ="data_added";

    //更新日時
    public static final String DATE_MODIFIED ="date_modified";

    public DetaileDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable =
                "CREATE TABLE " + TABLE_NAME + "(" +
                        _ID + "INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        TITLE + " TEXT, " +
                        DATA +  " TEXT, " +
                        DATA_ADDED + " INTEGER NOT NULL, " +
                        DATE_MODIFIED +
                        " TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL " +
                        ")";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //バージョン管理をここで行う

    }
}
