package com.fukuyama.fukuyamaapplication.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

import java.io.FileDescriptor;
import java.io.IOException;

/**
 * Created by fukuyama on 2017/06/30.
 */

public class BitmapUtil {

    /**
     * ビットマップを取得する.
     *
     * @param context {@link Context}
     * @param uri     {@link Uri}
     * @return ビットマップ
     * @throws IOException
     */
    public static Bitmap getBitmapFromUri(Context context, Uri uri) throws IOException {
        if (uri == null) {
            return null;
        }

        ParcelFileDescriptor parcelFileDescriptor =
                context.getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

    /**
     * URIを文字列に変換する.
     *
     * @param uri URI
     * @return 文字列に変換したURI
     */
    public static String uriToString(Uri uri) {
        return uri.toString();
    }

    /**
     * 文字列に変換したURIをURIに戻す.
     *
     * @param uriString 文字列に変換したURI
     * @return URI
     */
    public static Uri stringToUri(String uriString) {
        return Uri.parse(uriString);
    }
}
