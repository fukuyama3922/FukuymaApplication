package com.fukuyama.fukuyamaapplication.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fukuyama.fukuyamaapplication.AppBean;
import com.fukuyama.fukuyamaapplication.Application;
import com.fukuyama.fukuyamaapplication.Observer;
import com.fukuyama.fukuyamaapplication.QuantityInfoAdapter;
import com.fukuyama.fukuyamaapplication.R;
import com.fukuyama.fukuyamaapplication.db.InsertTask;
import com.fukuyama.fukuyamaapplication.db.QuantityInfoDao;
import com.fukuyama.fukuyamaapplication.db.QuantityInfoEntity;
import com.fukuyama.fukuyamaapplication.util.MessageUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * メインアクティビティ.
 */
public class MainActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    /**
     * ログ出力用のタグ.
     */
    private static final String TAG = MainActivity.class.getName();

    // TODO:定数クラスなどにまとめるべき
    /**
     * {@link SubActivity}が破棄された時に送付される識別子.
     */
    static final int RESULT_CODE_SUB_ACTIVITY = 1000;

    /**
     * 数量の上限.
     */
    private static final int QUANTITY_MAX = 9999;

    /**
     * 数量の下限.
     */
    private static final int QUANTITY_MIN = 0;

    /**
     * 加算減算される値.
     */
    private static final int QUANTITY_ADD = 1;

    /**
     * 表示される初期値.
     */
    private int mQuantity = QUANTITY_MIN;

    /**
     * 数量、コメント、時刻情報.
     */
    private ArrayList<QuantityInfoEntity> mQuantityInfoList;

    /**
     * 数量情報リスト(View).
     */
    private ListView mQuantityInfoListView;

    /**
     * {@limk Timer}
     */
    private Timer mTimer = new Timer();

    /**
     * 時刻表示欄.
     */
    private TextView mTimerTextView = null;

    /**
     * {@link TimerTask}
     */
    private TimerTask mTimerTask = null;

    /**
     * 数量情報リストアダプタ保持用.
     */
    private QuantityInfoAdapter mAdapter = null;

    /**
     * 時刻フォーマット(HH:MM:ss.)
     */
    private final SimpleDateFormat mFormatter = new SimpleDateFormat("HH:MM:ss.");


    private ProgressDialog mProgressDialog;
    /**
     * オブザーバ.
     */
    private Observer mObserver = new Observer() {

        /**
         * {@inheritDoc}
         */
        @Override
        public void notification(int notificationbCode, Object[] options) {

            dismissProgressDialog();

            switch (notificationbCode) {
                case Observer.NOTIFICATION_CODE_INSERT_QUERY_COMPLETE:

                    // DB追加処理完了時
                    if ((long) options[Observer.OPTION_INDEX_INSERT_QUERY_RESULT] == -1) {
                        MessageUtil.showToast(getApplicationContext(), "DB追加処理失敗");
                        return;
                    }
                    updateView();

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

        mTimerTask = new TimerTask() {

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
                        mTimerTextView.setText(mFormatter.format(new Date()));
                    }
                });
            }
        };
        mTimer.scheduleAtFixedRate(mTimerTask, 0, 1000);

        // オブザーバ登録
        Application.addObserver(mObserver);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onPause() {
        super.onPause();
        //バックグラウンドでの時刻表示の廃止.
        mTimerTask.cancel();

        // オブザーバを削除
        Application.removeObserver(mObserver);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        //破棄された画面から数量情報を受け取る.

        switch (requestCode) {
            case RESULT_CODE_SUB_ACTIVITY:
                if (resultCode == RESULT_OK) {

                    QuantityInfoEntity quantityInfoEntity = (QuantityInfoEntity) intent.getSerializableExtra("intent-key");
                    mQuantityInfoList.get(quantityInfoEntity.getEditIndex()).setQuantityInfo(quantityInfoEntity);
                    mAdapter.notifyDataSetChanged();
                }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        QuantityInfoEntity quantityInfoEntity = mQuantityInfoList.get(position);
        quantityInfoEntity.setEditIndex(position);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        QuantityInfoEntity quantityInfoEntity = mQuantityInfoList.get(position);
        quantityInfoEntity.setEditIndex(position);
        Intent intent = SubActivity.getNewIntent(this, quantityInfoEntity);
        startActivityForResult(intent, RESULT_CODE_SUB_ACTIVITY);
        return true;
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
            //TODO:選択された合計数量ボタン
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

        //初期表示される数量.
        updateQuantityText(mQuantity);

        //数量表示.
        TextView quantityTextView = (TextView) findViewById(R.id.text_quantity);
        quantityTextView.setText("" + mQuantity);

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
        // TODO:選択された合計数量ボタン
        final Button selectButton = (Button) findViewById(R.id.select_button);
        selectButton.setOnClickListener(this);

        //現在時刻表示欄の設定
        mTimerTextView = (TextView) findViewById(R.id.text_time);

        mQuantityInfoListView = (ListView) findViewById(R.id.listview_quantity_info);
        mAdapter = new QuantityInfoAdapter(MainActivity.this);

        showQuantityInfoList();
    }

    /**
     * 表示更新.
     */
    public void updateView() {
        showQuantityInfoList();
    }

    /**
     * 数量情報リストを表示する.
     */
    private void showQuantityInfoList() {
        mAdapter.setQuantityInfoList(getQuantityInfoList());
        mQuantityInfoListView.setAdapter(mAdapter);
        mQuantityInfoListView.setOnItemClickListener(this);
        mQuantityInfoListView.setOnItemLongClickListener(this);
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
     * 追加ボタン押下時の処理.
     */
    private void onClickAddButton() {
        // リストを1件追加
        addQuantityInfo();
    }

    /**
     * プラスボタン押下時の処理.
     */
    private void onClickPlusButton() {
        culcQuantityPlus();
    }

    /**
     * マイナスボタン押下時の処理.
     */
    private void onClickMinusButton() {
        culcQuantityMinus();
    }

    /**
     * クリアボタン押下時の処理.
     */
    private void onClickClearButton() {
        clearList();
    }

    // TODO:名称後で変更

    /**
     * 選択ボタン押下時の処理.
     */
    private void onClickSelectButton() {
        selectList();
    }


    /**
     * 数量が以下の場合数量を加算する.
     */
    private void culcQuantityPlus() {
        //上限の場合、警告を表示して処理を抜ける.
        if (mQuantity >= QUANTITY_MAX) {
            MessageUtil.showToast(this, getString(R.string.message_input_error));
            return;
        }

        //textviewに表示する.
        mQuantity += QUANTITY_ADD;
        updateQuantityText(mQuantity);
    }

    /**
     * 数量が以下の場合数量を減算する.
     */
    private void culcQuantityMinus() {
        //下限の場合、警告を表示して処理を抜ける.
        if (mQuantity <= QUANTITY_MIN) {
            MessageUtil.showToast(this, getString(R.string.message_input_error));
            return;
        }

        //textviewに表示する.
        mQuantity -= QUANTITY_ADD;
        //textviewに表示する.
        updateQuantityText(mQuantity);
    }

    /**
     * リストを一件追加する.
     */
    private void addQuantityInfo() {
        showProgressDialog("DB追加処理中");
        InsertTask task = new InsertTask(this);
        task.execute(getEditQuantityInfo());
    }

    /**
     * リストをクリアする.
     */
    private void clearList() {
        // リストの表示更新
        mQuantityInfoList.clear();
        mAdapter.notifyDataSetChanged();
    }


    // TODO:メソッドコメント後で起債

    /**
     *
     */
    private void selectList() {
        int sum = 0;

        for (QuantityInfoEntity info : mQuantityInfoList) {
            // チェックボックスにチェックが入っている場合
            if (info.isSelected()) {
                // 数量を加算する
                sum += info.getQuantity();
            }
        }
        // トーストに結果表示
        String viewQuantity = String.valueOf(sum);
        Toast.makeText(MainActivity.this, viewQuantity, Toast.LENGTH_SHORT).show();
    }

    /**
     * 編集した時刻を取得.
     *
     * @return
     */
    private String getDate() {
        TextView textView = (TextView) findViewById(R.id.text_time);
        return textView.getText().toString();
    }

    /**
     * 編集したコメントを取得.
     *
     * @return
     */
    private String getComment() {
        TextView textView = (TextView) findViewById(R.id.text_comment);
        return textView.getText().toString();
    }

    /**
     * @return
     */
    private Bitmap getBitmap() {

        Resources r = getResources();
        Bitmap bmp = BitmapFactory.decodeResource(r, R.drawable.sample);
        return bmp;
    }

    /**
     * 追加ボタン押下時に編集されていた数量情報を取得する.
     *
     * @return {@link QuantityInfoEntity}
     */
    private QuantityInfoEntity getEditQuantityInfo() {
        QuantityInfoEntity quantityInfoEntity = new QuantityInfoEntity();
        quantityInfoEntity.setQuantity(mQuantity);
        quantityInfoEntity.setComment(getComment());
        quantityInfoEntity.setDate(getDate());
        return quantityInfoEntity;
    }

    /**
     * プログレスを表示する.
     *
     * @param progressMessage プログレスメッセージ
     */
    private void showProgressDialog(String progressMessage) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setMessage(progressMessage);
            mProgressDialog.setCancelable(true);
            mProgressDialog.show();
        }
    }

    private void dismissProgressDialog() {
        mProgressDialog.dismiss();
        mProgressDialog = null;
    }

    /**
     * 数量情報リストを取得する.
     *
     * @return 数量情報リスト
     */

    private ArrayList<QuantityInfoEntity> getQuantityInfoList() {
        QuantityInfoDao quantityInfoDao = new QuantityInfoDao(this);
        ArrayList<QuantityInfoEntity> quantityInfoList = quantityInfoDao.findAll();
        return quantityInfoList;
        // TODO:AppBeanから情報取得
//        AppBean appBean = (AppBean) getApplication();
//        return appBean.list;
    }
}


