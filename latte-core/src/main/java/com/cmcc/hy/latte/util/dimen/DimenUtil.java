package com.cmcc.hy.latte.util.dimen;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.cmcc.hy.latte.app.Latte;

/**
 * Created by jeff on 2018/5/11.
 */

public class DimenUtil {

    public static int getScreenWidth() {
        final Resources resources = Latte.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getScreenHeight() {
        final Resources resources = Latte.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
    }
}
