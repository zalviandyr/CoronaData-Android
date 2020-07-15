package com.zukron.coronadataapp.tools;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Project name is Corona Data App
 * Created by Zukron Alviandy R on 7/10/2020
 */
public class Util {
    public static String formatToThousand(Integer integer) {
        DecimalFormat format = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbols = format.getDecimalFormatSymbols();

        symbols.setGroupingSeparator('.');
        format.setDecimalFormatSymbols(symbols);
        return format.format(integer);
    }

    /**
     * Replace space to under score
     */
    public static String replace(String str) {
        return str.replaceAll("\\s", "_");
    }
}
