package com.fukuyama.fukuyamaapplication;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

/**
 * Created by fukuyama on 2017/06/26.
 */

public class MessageUtil {
    /**
     * トーストを表示する
     * @param context {@link Context}
     * @param message トーストメッセージ
     */
    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}
