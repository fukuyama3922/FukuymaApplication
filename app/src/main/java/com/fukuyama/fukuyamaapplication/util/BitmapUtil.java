package com.fukuyama.fukuyamaapplication.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Created by fukuyama on 2017/06/30.
 */

public class BitmapUtil {

    /**
     * ビットマップをストリング型に変換する.
     *
     * @param bitmap
     * @return
     */
    public static String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    /**
     * ストリング型をビットマップに変換.
     *
     * @param bitMapToString
     * @return
     */
    public static Bitmap StringToBitMap(String bitMapToString) {
        byte[] encodeByte = Base64.decode(bitMapToString, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0,
                encodeByte.length);
        return bitmap;
    }

    public static int getIntValue(int value) {
        switch (value) {
            case 1:
                return 100;
            case 2:
                return 200;
            default:
                return 300;
        }
    }

}
