package com.fukuyama.fukuyamaapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.fukuyama.fukuyamaapplication.AppBean;
import com.fukuyama.fukuyamaapplication.Observer;
import com.fukuyama.fukuyamaapplication.QuantityInfoAdapter;
import com.fukuyama.fukuyamaapplication.QuantityInfoAdapterListener;
import com.fukuyama.fukuyamaapplication.R;
import com.fukuyama.fukuyamaapplication.db.InfoTableDeleteTask;
import com.fukuyama.fukuyamaapplication.db.InfoTableInsertTask;
import com.fukuyama.fukuyamaapplication.db.QuantityInfoDao;
import com.fukuyama.fukuyamaapplication.db.QuantityInfoEntity;
import com.fukuyama.fukuyamaapplication.util.MessageUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.TimerTask;

/**
 * メインアクティビティ.
 */
public class MainActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener, QuantityInfoAdapterListener {

    /**
     * ログ出力用のタグ.
     */
    private static final String TAG = MainActivity.class.getName();


    /**
     * オブザーバ.
     */
    private Observer mObserver = new Observer() {

        /**
         * {@inheritDoc}
         */
        @Override
        public void notification(int notificationCode, Object[] options) {



            switch (notificationCode) {
                case Observer.NOTIFICATION_CODE_INSERT_QUERY_COMPLETE:
                    // DB追加処理完了時

                    if ((long) options[Observer.OPTION_INDEX_INSERT_QUERY_RESULT] == -1) {
                        MessageUtil.showToast(getApplicationContext(), "DB追加処理失敗");
                        return;
                    }
                    delay();


                    return;

                case Observer.NOTIFICATION_CODE_DELETE_QUERY_COMPLETE:
                    // DB削除処理完了時

                    if ((long) options[Observer.OPTION_INDEX_DELETE_QUERY_RESULT] == -1) {
                        MessageUtil.showToast(getApplicationContext(), "DB削除処理失敗");
                        return;
                    }
                    delay();

                    return;

                default:

                    return;
            }
        }
    };

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // viewの初期化
        initView();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onResume() {
        super.onResume();

        ActivityConst.mTimerTask = new TimerTask() {

            /**
             * {@inheritDoc}
             */
            @Override
            public void run() {
                runOnUiThread(new Runnable() {

                    /**
                     * {@inheritDoc}
                     */
                    @Override
                    public void run() {
                        //現在時刻の表示
                        updateDateText(new Date());
                    }
                });
            }
        };
        ActivityConst.mTimer.scheduleAtFixedRate(ActivityConst.mTimerTask, 0, 1000);

        // オブザーバ登録
        AppBean.addObserver(mObserver);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onPause() {
        super.onPause();
        //バックグラウンドでの時刻表示の廃止.
        ActivityConst.mTimerTask.cancel();

        // オブザーバを削除
        AppBean.removeObserver(mObserver);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        updateView();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        // Applicationに数量情報を保持
        AppBean.setQuantityInfoEntity(ActivityConst.mQuantityInfoList.get(position));

        //詳細画面の表示.
        showSubActivity();
    }

    /**
     * 詳細画面の表示.
     */
    private void showSubActivity() {
        Intent intent = SubActivity.getNewIntent(this);
        startActivityForResult(intent, ActivityConst.RESULT_CODE_SUB_ACTIVITY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onClickDelete(QuantityInfoEntity quantityInfoEntity) {
        executeDeleteTask(quantityInfoEntity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.button_plus:
                //プラスボタン押下時の処理
                onClickPlusButton();
                return;
            case R.id.button_minus:
                //マイナスボタン押下時の処理
                onClickMinusButton();
                return;
            case R.id.button_add:
                // 追加ボタン押下時の処理
                onClickAddButton();
                return;
            case R.id.button_clear:
                // クリアボタン押下時の処理
                onClickClearButton();
                return;
            case R.id.select_button:
                // 選択された合計数量ボタンを押下された場合
                onClickSelectButton();
                return;
            default:
                return;
        }
    }

    /**
     * 初期表示.
     */
    private void initView() {

        // 各Viewの初期設定
        initSetting();

        //初期表示される数量.
        updateQuantityText(ActivityConst.mQuantity);

        // 数量情報リストの表示
        showQuantityInfoList();
    }

    /**
     * 表示更新.
     */
    private void updateView() {
        showQuantityInfoList();
    }

    /**
     * 時刻の表示を更新する.
     */
    private void updateDateText(Date date) {
        TextView dateTextView = (TextView) findViewById(R.id.text_time);
        dateTextView.setText(ActivityConst.mFormatter.format(date));
    }

    /**
     * 数量の表示を更新する.
     *
     * @param quantity 　数量
     */
    private void updateQuantityText(int quantity) {
        TextView textViewQuantity = (TextView) findViewById(R.id.text_quantity);
        textViewQuantity.setText(String.valueOf(quantity));
    }

    /**
     * 各Viewの初期設定.
     */
    private void initSetting() {
        // 数量情報リストの設定
        ActivityConst.mQuantityInfoListView = (ListView) findViewById(R.id.listview_quantity_info);
        ActivityConst.mAdapter = new QuantityInfoAdapter(MainActivity.this);
        ActivityConst.mAdapter.setQuantityInfoAdapterListener(this);

        //プラスボタンの設定.
        final Button plusButton = (Button) findViewById(R.id.button_plus);
        plusButton.setOnClickListener(this);

        //マイナスボタンの設定.
        final Button minusButton = (Button) findViewById(R.id.button_minus);
        minusButton.setOnClickListener(this);

        //追加ボタンの設定.
        final Button addButton = (Button) findViewById(R.id.button_add);
        addButton.setOnClickListener(this);

        //クリアボタンの設定.
        final Button clearButton = (Button) findViewById(R.id.button_clear);
        clearButton.setOnClickListener(this);

        // 選択された合計数量ボタンの設定.
        final Button selectButton = (Button) findViewById(R.id.select_button);
        selectButton.setOnClickListener(this);
    }

    /**
     * 数量情報リストを表示する.
     */
    private void showQuantityInfoList() {
        ActivityConst.mAdapter.setQuantityInfoList(getQuantityInfoList());
        ActivityConst.mQuantityInfoListView.setAdapter(ActivityConst.mAdapter);
        ActivityConst.mQuantityInfoListView.setOnItemClickListener(this);
    }

    /**
     * プラスボタン押下時の処理.
     */
    private void onClickPlusButton() {
        calcQuantityPlus();
    }

    /**
     * マイナスボタン押下時の処理.
     */
    private void onClickMinusButton() {
        calcQuantityMinus();
    }

    /**
     * 追加ボタン押下時の処理.
     */
    private void onClickAddButton() {
        // リストを1件追加
        addQuantityInfo();
    }

    /**
     * クリアボタン押下時の処理.
     */
    private void onClickClearButton() {
        // TODO:処理未実装
        executeDeleteTask(null);
    }

    /**
     * 選択された合計数量ボタン押下時の処理.
     */
    private void onClickSelectButton() {
        // TODO:処理未実装
    }

    /**
     * 数量が以下の場合数量を加算する.
     */
    private void calcQuantityPlus() {
        //上限の場合、警告を表示して処理を抜ける
        if (ActivityConst.mQuantity >= ActivityConst.QUANTITY_MAX) {
            MessageUtil.showToast(this, getString(R.string.message_input_error));
            return;
        }

        ActivityConst.mQuantity += ActivityConst.QUANTITY_ADD;
        updateQuantityText(ActivityConst.mQuantity);
    }

    /**
     * 数量が以下の場合数量を減算する.
     */
    private void calcQuantityMinus() {
        //下限の場合、警告を表示して処理を抜ける
        if (ActivityConst.mQuantity <= ActivityConst.QUANTITY_MIN) {
            MessageUtil.showToast(this, getString(R.string.message_input_error));
            return;
        }

        ActivityConst.mQuantity -= ActivityConst.QUANTITY_ADD;
        updateQuantityText(ActivityConst.mQuantity);
    }

    /**
     * 時刻表示欄に表示されている時刻を取得.
     *
     * @return 時刻表示欄に表示されている文字列
     */
    private String getDate() {
        TextView textView = (TextView) findViewById(R.id.text_time);
        return textView.getText().toString();
    }

    /**
     * 編集したコメントを取得.
     *
     * @return コメント入力欄に入力された文字列
     */
    private String getComment() {
        TextView textView = (TextView) findViewById(R.id.text_comment);
        return textView.getText().toString();
    }

    /**
     * 追加ボタン押下時に編集されていた数量情報を取得する.
     *
     * @return {@link QuantityInfoEntity}
     */
    private QuantityInfoEntity getEditQuantityInfo() {
        QuantityInfoEntity quantityInfoEntity = new QuantityInfoEntity();
        quantityInfoEntity.setQuantity(ActivityConst.mQuantity);
        quantityInfoEntity.setComment(getComment());
        quantityInfoEntity.setDate(getDate());
        return quantityInfoEntity;
    }

    /**
     * 数量情報リストを取得する.
     *
     * @return 数量情報リスト
     */
    private ArrayList<QuantityInfoEntity> getQuantityInfoList() {
        QuantityInfoDao quantityInfoDao = new QuantityInfoDao(this);
        ArrayList<QuantityInfoEntity> quantityInfoList = quantityInfoDao.findAll();
        // DBから数量情報リストを取得する際にメモリに保持
        ActivityConst.mQuantityInfoList = quantityInfoList;
        return quantityInfoList;
    }

    /**
     * リストを一件追加する.
     */
    private void addQuantityInfo() {

        showProgressDialog(getString(R.string.progress_message_db_insert));
        InfoTableInsertTask task = new InfoTableInsertTask(this);
        task.execute(getEditQuantityInfo());
    }

//    /**
//     * DB追加タスクを実行する.
//     * TODO:採用するかどうか未定.
//     *
//     *
//     * @param quantityInfoEntity 数量情報リスト
//     */
//    private void executeInsertTask(QuantityInfoEntity quantityInfoEntity) {
//        showProgressDialog("DB追加処理実行中");
//        InfoTableInsertTask insertTask = new InfoTableInsertTask(this);
//
//        insertTask.execute(quantityInfoEntity);
//    }

    /**
     * DB削除タスクを実行する.
     *
     * @param quantityInfoEntity 数量情報リスト
     */
    private void executeDeleteTask(QuantityInfoEntity quantityInfoEntity) {
        showProgressDialog("DB削除処理実行中");
        InfoTableDeleteTask infoTableDeleteTask = new InfoTableDeleteTask(this);
        infoTableDeleteTask.execute(quantityInfoEntity);
    }

    private void delay() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                updateView();
                dismissProgressDialog();
                // ここに３秒後に実行したい処理
            }
        }, 1000L);
    }
}


