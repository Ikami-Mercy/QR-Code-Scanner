package com.ikami.constants;

import com.google.zxing.integration.android.IntentResult;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberFromString {

    public static String convertNumber(IntentResult result){
        Pattern p = Pattern.compile("-?\\d+");
        Matcher m = p.matcher(result.getContents());
        String number = "";
        while (m.find()) {
            number = m.group();
        }

        return number;
    }

    public static String convertString(IntentResult result){
        Pattern p = Pattern.compile("[a-zA-Z]+");
        Matcher m = p.matcher(result.getContents());
        String string = "";
        while (m.find()) {
            string = m.group();
        }

        return string;
    }
}
