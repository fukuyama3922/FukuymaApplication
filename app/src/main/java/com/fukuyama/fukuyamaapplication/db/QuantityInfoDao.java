package com.fukuyama.fukuyamaapplication.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * 数量情報Daoクラス.
 */
public class QuantityInfoDao {

    /**
     * DB処理結果：失敗.
     */
    public static final long RESULT_FAILURE = -1;

    //テーブル名
    public static final String DB_TABLE = "mSheet";

    //IDカラム
    public static final String COL_ID = "col_id";

    //数量カラム
    public static final String COL_UPDATED_QUANTITY = "col_updatedQuantity";

    //日時カラム
    public static final String COL_DATE = "col_date";

    //タイトルカラム
    public static final String COL_COMMENT = "col_comment";

    //画像カラム
    public static final String COL_PICTURE = "col_picture";

    private static final String[] COLUMNS = {
            COL_ID,
            COL_UPDATED_QUANTITY,
            COL_DATE,
            COL_COMMENT,
            COL_PICTURE
    };

    /**
     * {@link SQLiteDatabase}
     */
    private SQLiteDatabase mDb;

    /**
     * {@link DbOpenHelper}
     */
    private DbOpenHelper mDbOpenHelper;

    /**
     * コンストラクタ.
     *
     * @param db {@link SQLiteDatabase}
     */
    public QuantityInfoDao(SQLiteDatabase db) {
        mDb = db;
    }

    /**
     * コンストラクタ.
     *
     * @param context {@link Context}
     */
    public QuantityInfoDao(Context context) {
        mDbOpenHelper = new DbOpenHelper(context);

    }

    /**
     * DB(読み込み)を開く.
     */
    private void openReadableDb() {
        if (mDb == null) {
            mDb = mDbOpenHelper.getReadableDatabase();
        }
    }

    /**
     * DB(読み込み/書き込み)を開く.
     */
    private void openWritableDb() {
        if (mDb == null) {
            mDb = mDbOpenHelper.getWritableDatabase();
        }
    }

    /**
     * DBを閉じる.
     */
    private void closeDb() {
        if (mDb != null) {
            mDb.close();
        }
    }

    /**
     * トランザクションを開始する.
     */
    private void beginTransaction() {
        if (mDb != null) {
            mDb.beginTransaction();
        }
    }

    /**
     * トランザクションを終了する.
     */
    private void endTransaction() {
        if (mDb != null) {
            mDb.endTransaction();
        }
    }

    /**
     * トランザクションをコミットする.
     */
    private void setTransactionSuccessful() {
        if (mDb != null) {
            mDb.setTransactionSuccessful();
        }
    }

    /**
     * テーブル全件リストを取得する.
     *
     * @return テーブル全件リスト
     */
    public ArrayList<QuantityInfoEntity> findAll() {
        ArrayList<QuantityInfoEntity> quantityInfoEntityList = new ArrayList<>();
        Cursor cursor = mDb.query(DB_TABLE, null, null, null, null, null, COL_ID);
        while (cursor.moveToNext()) {
            QuantityInfoEntity quantityInfoEntity = new QuantityInfoEntity();
            quantityInfoEntity.setId(cursor.getInt(0));
            quantityInfoEntity.setQuantity(cursor.getInt(1));
            quantityInfoEntity.setDate(cursor.getString(2));
            quantityInfoEntity.setComment(cursor.getString(3));
            quantityInfoEntityList.add(quantityInfoEntity);
        }
        return quantityInfoEntityList;
    }

    /**
     * テーブルに数量情報を追加する.
     *
     * @param quantityInfoEntity {@link QuantityInfoEntity}
     */
    public long insertQuantity(QuantityInfoEntity quantityInfoEntity) {

        long result;

        try {

            openWritableDb();
            beginTransaction();

            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_UPDATED_QUANTITY, quantityInfoEntity.getQuantity());
            contentValues.put(COL_DATE, quantityInfoEntity.getDate());
            contentValues.put(COL_COMMENT, quantityInfoEntity.getComment());
            // TODO:後で修正
            contentValues.put(COL_PICTURE, 1);

            result = mDb.insert(DB_TABLE, null, contentValues);
            setTransactionSuccessful();

        } catch (Exception e) {
            Log.e("aaa", e.getMessage());
            result = RESULT_FAILURE;
        } finally {
            endTransaction();
            closeDb();
        }

        return result;
    }

    public void saveDB(int updatedQuantity, String date, String comment, String picture) {
        //トランザクション開始.
        mDb.beginTransaction();

        try {
            ContentValues values = new ContentValues();
            values.put(COL_UPDATED_QUANTITY, updatedQuantity);
            values.put(COL_DATE, date);
            values.put(COL_COMMENT, comment);
            values.put(COL_PICTURE, picture);

            //レコードへ登録
            //insertメソッド　データ登録
            //第1引数：DBのテーブル名
            //第2引数：更新する条件式
            //第3引数：ContentValues
            mDb.insert(DB_TABLE, null, values);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            mDb.endTransaction();
        }
    }

    public Cursor getDB(String[] columns) {

        //queryメソッド　DBのデータを取得
        //第1引数：DBのテーブル名.
        //第2引数：所得するカラム名.
        //第3引数：選択条件(WHERE句).
        //第4引数：第3引数のWERE句において？を使用した場合に使用.
        //第5引数：集計条件(GROUP BY句).
        //第6引数：選択条件(HAVING句).
        //第7引数：ソート条件(ODERBY句).
        return mDb.query(DB_TABLE, columns, null, null, null, null, null);
    }

    public Cursor searchDB(String[] columns, String column, String[] name) {
        return mDb.query(DB_TABLE, columns, column + "like ?", name, null, null, null);
    }

    /**
     * DBのレコードを全削除.
     * allDelete().
     */
    public void allDelete() {
        //トランザクションの開始.
        mDb.beginTransaction();
        try {
            //deleteメソッド.
            //第1引数：テーブル名.
            //第2引数：削除する条件式 nullの場合は全レコードを削除.
            //第3引数：第2引数で?を使用した場合に使用
            mDb.delete(DB_TABLE, null, null);
            //トランザクションへコミット.
            mDb.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //トランザクションの終了.
            mDb.endTransaction();
        }
    }

    /**
     * DBのレコードの単一削除.
     * selectDelete.
     *
     * @param quantityInfoEntity
     */
    public long deleteQuantity(QuantityInfoEntity quantityInfoEntity) {
        long result;

        try {

            openWritableDb();
            beginTransaction();

            String whereClause = COL_ID + "=?";
            String[] whereArgs = new String[]{String.valueOf(quantityInfoEntity.getId())};

            result = mDb.delete(DB_TABLE, whereClause, whereArgs);
            setTransactionSuccessful();

        } catch (Exception e) {
            Log.e("aaa", e.getMessage());
            result = RESULT_FAILURE;
        } finally {
            endTransaction();
            closeDb();
        }

        return result;
    }
}
//   -