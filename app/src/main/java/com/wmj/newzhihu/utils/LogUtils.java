package com.wmj.newzhihu.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by wmj on 2017-3-9.
 */

public class LogUtils {
    private static Toast toast;

    public static void showToast(Context context, String content) {
        if (toast == null) {
            toast = Toast.makeText(context,content,Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }
}
