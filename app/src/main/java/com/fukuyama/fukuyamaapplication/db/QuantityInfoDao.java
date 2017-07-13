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

        try {
            openReadableDb();
            beginTransaction();

            Cursor cursor = mDb.query(
                    DbConst.DB_TABLE,
                    null,
                    null,
                    null,
                    null,
                    null,
                    DbConst.COL_ID);

            while (cursor.moveToNext()) {
                QuantityInfoEntity quantityInfoEntity = new QuantityInfoEntity();
                quantityInfoEntity.setId(cursor.getInt(0));
                quantityInfoEntity.setQuantity(cursor.getInt(1));
                quantityInfoEntity.setDate(cursor.getString(2));
                quantityInfoEntity.setComment(cursor.getString(3));
                quantityInfoEntity.setUriString(cursor.getString(4));
                quantityInfoEntityList.add(quantityInfoEntity);
            }
        } catch (Exception e) {
            Log.e("aaa", e.getMessage());
        } finally {
            endTransaction();
            closeDb();
        }
        return quantityInfoEntityList;
    }

    /**
     * 任意のIDのレコードを取得する.
     *
     * @return テーブル全件リスト
     */
    public QuantityInfoEntity findById(QuantityInfoEntity quantityInfoEntity) {
        QuantityInfoEntity findValue = new QuantityInfoEntity();
        try {
            openReadableDb();
            beginTransaction();

            String selection = DbConst.COL_ID + "=?";
            String[] selectionArgs = new String[]{String.valueOf(quantityInfoEntity.getId())};
            Cursor cursor = mDb.query(
                    DbConst.DB_TABLE,
                    null,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    DbConst.COL_ID);

            while (cursor.moveToNext()) {

                findValue.setId(cursor.getInt(0));
                findValue.setQuantity(cursor.getInt(1));
                findValue.setDate(cursor.getString(2));
                findValue.setComment(cursor.getString(3));
                findValue.setUriString(cursor.getString(4));
            }
        } catch (Exception e) {
            // TODO：ログを出力する
        } finally {
            endTransaction();
            closeDb();
        }
        return findValue;
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
            contentValues.put(DbConst.COL_UPDATED_QUANTITY, quantityInfoEntity.getQuantity());
            contentValues.put(DbConst.COL_DATE, quantityInfoEntity.getDate());
            contentValues.put(DbConst.COL_COMMENT, quantityInfoEntity.getComment());
            contentValues.put(DbConst.COL_IMAGE_URI, quantityInfoEntity.getUriString());

            result = mDb.insert(DbConst.DB_TABLE, null, contentValues);
            setTransactionSuccessful();

        } catch (Exception e) {
            // TODO:ログ出力する
            result = RESULT_FAILURE;
        } finally {
            endTransaction();
            closeDb();
        }
        return result;
    }

    /**
     * DBのレコードを全削除.
     * allDelete().
     */
    public void clearQuantity() {
        //トランザクションの開始.
        mDb.beginTransaction();
        try {
            mDb.delete(DbConst.DB_TABLE, null, null);
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
     * @param quantityInfoEntity 数量情報
     */
    public long deleteQuantity(QuantityInfoEntity quantityInfoEntity) {
        long result;

        try {

            openWritableDb();
            beginTransaction();

            String whereClause = DbConst.COL_ID + "=?";
            String[] whereArgs = new String[]{String.valueOf(quantityInfoEntity.getId())};

            result = mDb.delete(DbConst.DB_TABLE, whereClause, whereArgs);
            setTransactionSuccessful();

        } catch (Exception e) {
            // TODO:ログ出力する
            result = RESULT_FAILURE;
        } finally {
            endTransaction();
            closeDb();
        }
        return result;
    }

    /**
     * テーブルの数量情報を更新する.
     *
     * @param quantityInfoEntity {@link QuantityInfoEntity}
     */
    public long updateQuantity(QuantityInfoEntity quantityInfoEntity) {

        long result;

        try {

            openWritableDb();
            beginTransaction();

            ContentValues contentValues = new ContentValues();
            contentValues.put(DbConst.COL_UPDATED_QUANTITY, quantityInfoEntity.getQuantity());
            contentValues.put(DbConst.COL_DATE, quantityInfoEntity.getDate());
            contentValues.put(DbConst.COL_COMMENT, quantityInfoEntity.getComment());
            contentValues.put(DbConst.COL_IMAGE_URI, quantityInfoEntity.getUriString());

            String whereClause = DbConst.COL_ID + "=?";
            String whereArgs[] = new String[]{String.valueOf(quantityInfoEntity.getId())};

            result = mDb.update(DbConst.DB_TABLE, contentValues, whereClause, whereArgs);
            setTransactionSuccessful();

        } catch (Exception e) {
            // TODO:ログ出力する
            result = RESULT_FAILURE;
        } finally {
            endTransaction();
            closeDb();
        }
        return result;
    }
}