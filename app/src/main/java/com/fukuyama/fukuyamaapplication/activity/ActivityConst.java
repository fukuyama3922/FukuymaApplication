package com.fukuyama.fukuyamaapplication.activity;

import android.app.ProgressDialog;
import android.widget.ListView;

import com.fukuyama.fukuyamaapplication.QuantityInfoAdapter;
import com.fukuyama.fukuyamaapplication.db.QuantityInfoEntity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by fukuyama on 2017/07/13.
 */

public class ActivityConst {

    // TODO:定数クラスなどにまとめるべき
    /**
     * {@link SubActivity}が破棄された時に送付される識別子.
     */
    public static final int RESULT_CODE_SUB_ACTIVITY = 1000;

    /**
     * 数量の上限.
     */
    public static final int QUANTITY_MAX = 9999;

    /**
     * 数量の下限.
     */
    public static final int QUANTITY_MIN = 0;

    /**
     * 加算減算される値.
     */
    public static final int QUANTITY_ADD = 1;

    /**
     * 表示される初期値.
     */
    public static int mQuantity = QUANTITY_MIN;

    /**
     * 数量情報リスト(数量、コメント、時刻情報).
     */
    public static ArrayList<QuantityInfoEntity> mQuantityInfoList;

    /**
     * 数量情報リスト(View).
     */
    public static ListView mQuantityInfoListView;

    /**
     * {@limk Timer}
     */
    public static Timer mTimer = new Timer();

    /**
     * {@link TimerTask}
     */
    public static TimerTask mTimerTask = null;

    /**
     * 数量情報リストアダプタ保持用.
     */
    public static QuantityInfoAdapter mAdapter = null;

    /**
     * 時刻フォーマット(HH:MM:ss.)
     */
    public static final SimpleDateFormat mFormatter = new SimpleDateFormat("HH:MM:ss.");

    /**
     * プログレスダイアログ.
     */
    public static ProgressDialog mProgressDialog;

}
