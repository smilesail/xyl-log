package com.xyl;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by lijh on 2017/2/5.
 */

class Logger extends L implements LogFactory {
    private static final int INIT_METHOD_STACK = 1;
    private boolean isLogCurrentThread = true;//默认输出当前线程Log
    private int count;// 默认输出一级调用方法
    private boolean mThreadInfo;//为了保证一行输出, 默认不显示线程信息
    private static final ThreadLocal<L> LOGGER = new ThreadLocal<>();
    private boolean mIsDetailException;

    Logger() {
    }

    @Override
    protected L setDetailException(boolean detail) {
        mIsDetailException = detail;
        return this;
    }

    public static final L get() {
        if (LOGGER.get() == null) {
            synchronized (LOGGER) {
                if (LOGGER.get() == null) {
                    LOGGER.set(new Logger());
                }
            }
        }
        return LOGGER.get();
    }

    public Logger methodCount(int count) {
        this.count = count;
        return this;
    }

    public Logger setThreadInfo(boolean threadInfo) {
        this.mThreadInfo = threadInfo;
        return this;
    }

    public Logger clear() {
        count = INIT_METHOD_STACK;
        return this;
    }

    public Logger verbose(String tag, Object msg) {
        println(Log.VERBOSE, tag, msg, null);
        return this;
    }

    public Logger verbose(Object msg) {
        println(Log.VERBOSE, null, msg, null);
        return this;
    }

    public Logger info(String tag, Object msg) {
        println(Log.INFO, tag, msg, null);
        return this;
    }

    public Logger info(Object msg) {
        println(Log.INFO, null, msg, null);
        return this;
    }

    public Logger debug(String tag, Object msg) {
        println(Log.DEBUG, tag, msg, null);
        return this;
    }

    public Logger debug(Object msg) {
        println(Log.DEBUG, null, msg, null);
        return this;
    }

    public Logger warn(String tag, Object msg) {
        println(Log.WARN, tag, msg, null);
        return this;
    }

    public Logger warn(Object msg) {
        println(Log.WARN, null, msg, null);
        return this;
    }

    public Logger error(String tag, Object msg) {
        println(Log.ERROR, tag, msg, null);
        return this;
    }

    public Logger error(String msg, Throwable tr) {
        println(Log.ERROR, null, msg, tr);
        return this;
    }

    public Logger error(Object msg) {
        println(Log.ERROR, null, msg, null);
        return this;
    }

    private Logger println(int level, String tag, Object msg, Throwable tr) {
        if (!isDebug()) return this;
        Thread currentThread = Thread.currentThread();
        StackTraceElement[] stackTrace = currentThread.getStackTrace();
        String TAG = getTag(tag, stackTrace[STACK_INDEX]);
        StringBuilder msgBuilder = new StringBuilder();
        if (isLogOutput() && (isLoggableIgnore() || Log.isLoggable(TAG, level))) {
            for (int i = 0; i < count && i + STACK_INDEX < stackTrace.length; ++i) {
                if (msgBuilder.length() > 0) {
                    msgBuilder.append("\n");
                }
                StackTraceElement element = stackTrace[STACK_INDEX + i];
                String className = element.getClassName().replaceAll("(?:.*?)([^.]+)$", "$1");
                String fileName = element.getFileName();
                fileName = TextUtils.isEmpty(fileName) ? className : fileName;
                String methodName = element.getMethodName();
                int lineNumber = element.getLineNumber();
                msgBuilder.append("[(").append(fileName).append(":").append(lineNumber).append(")#").append(className).append(".").append(methodName).append("]");
                if (i == 0) {
                    msgBuilder.append(msg);
                    if (tr != null) {
                        msgBuilder.append(mIsDetailException? exception2String2(tr):exception2String(tr));
                    }
                }
            }
            if (mThreadInfo) {
                msgBuilder.append("\n").append(currentThread.getId()).append(":").append(currentThread.getName());
            }
            Log.println(level, TAG, msgBuilder.toString());
        }
        clear();
        return this;
    }

    @NonNull
    private String getTag(String tag, StackTraceElement stackTraceElement) {
        StackTraceElement element = stackTraceElement;
        String className = element.getClassName().replaceAll("(?:.*?)([^.]+)$", "$1");
        String fileName = element.getFileName();
        fileName = TextUtils.isEmpty(fileName) ? className : fileName;
        String TAG = TextUtils.isEmpty(tag) ? fileName : tag;
        TAG = TAG.length() > 23 ? TAG.substring(0, 23) : TAG;
        return TAG;
    }

    public boolean isLogOutput() {
        return isLogCurrentThread;
    }

    public Logger setLogOutput(boolean flag) {
        isLogCurrentThread = flag;
        return this;
    }

    public Logger verbose() {
        println(Log.VERBOSE, null, "", null);
        return this;
    }

    public Logger debug() {
        println(Log.DEBUG, null, "", null);
        return this;
    }

    public Logger info() {
        println(Log.INFO, null, "", null);
        return this;
    }

    public Logger warn() {
        println(Log.WARN, null, "", null);
        return this;
    }

    public Logger error() {
        println(Log.ERROR, null, "", null);
        return this;
    }

    @Override
    public L getLogger() {
        return get();
    }
}
