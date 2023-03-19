package com.kanchalk.expensetracker;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public void appLog(String msg){
        Log.e("ExpenseTracker",msg);
    }

    public boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }
}
