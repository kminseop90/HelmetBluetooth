package com.sample.helmetble.util;

import android.text.TextUtils;

public class Utils {

    public static Integer parseInt(String value) {

        int result = 0;

        if(!TextUtils.isEmpty(value)) {
            try {
                result = Integer.parseInt(value);
            } catch (Exception e) {

            }
        }
        return result;
    }

}
