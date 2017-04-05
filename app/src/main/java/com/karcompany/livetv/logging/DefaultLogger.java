package com.karcompany.livetv.logging;

/**
 * Created by pvkarthik on 2017-02-15.
 *
 * Main Logger class.
 * Every class should use these functions instead of default Android logging.
 */

import android.util.Log;

public class DefaultLogger implements ILogger {

    private static final String TAG_PREFIX = "LiveTV.";

    @Override
    public void log(int priority, String tag, String msg) {
        Log.println(priority, tag, msg);
    }

    @Override
    public void log(int priority, String tag, String msg, Throwable throwable) {
        if (throwable != null && msg != null) msg += "\n" + Log.getStackTraceString(throwable);
        log(priority, tag, msg);
    }

    public static String makeLogTag(Class<?> cls) {
        return TAG_PREFIX + cls.getSimpleName();
    }

    public static void i(String tag, String message) {
        Log.i(tag, message);
    }

    public static void d(String tag, String message) {
        Log.d(tag, message);
    }

    public static void e(String tag, String message) {
        Log.e(tag, message);
    }

    public static void e(String tag, String message, Throwable throwable) {
        Log.e(tag, message, throwable);
    }

    public static void e(String tag, Throwable throwable) {
        Log.e(tag, "", throwable);
    }

    public static void w(String tag, String message) {
        Log.w(tag, message);
    }
}

