package com.developerxy.sqli_test.utils;

import android.content.Context;

import com.developerxy.sqli_test.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by Mohammed Aouf ZOUAG on 12/1/2017.
 */

public class DateUtils {
    /**
     * @param tzDate the TZ date to be formatted
     * @return the converted date in a readable format.
     */
    public static String parseTZ(Context context, String tzDate) {
        try {
            SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yyyy, HH:mm");
            return outputDateFormat.format(inputDateFormat.parse(tzDate));
        } catch (ParseException e) {
            return context.getString(R.string.invalid_date);
        }
    }
}
