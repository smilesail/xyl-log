package com.xyl;

import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by LiJunhua on 2016/5/20.
 * Android Log工具类
 * 默认只Release版本, 不输出Log
 * 使用方法:
 *  1.执行初始化: L.init(boolean isDebug, boolean isLoggableIgnore);参见{@link #init(boolean, boolean) init}
 *  2.执行L.d()等方法, 参见{@link #d(String, Object) v/d/i/w/e}
 */
public abstract class L {

    private static LogFactory mLogFactory;
    private static boolean mIsDebug;//默认Release, 不输出Log
    private static boolean mIsLoggableIgnore;//默认检查Tag的级别 Log.isLoggable(TAB, LEVEL)

    protected L() {
    }

    /**
     * 改变输出的函数调用栈的数量
     * @param count 默认为1
     * @return
     */
    public static L t(int count) {
        return getL().methodCount(count);
    }

    /**
     * 是否输出线程信息 以ID:ThreadName形式输出
     * @param threadInfo true:输出 false: 不输出 默认为false
     * @return
     */
    public static L threadInfo(boolean threadInfo) {
        return getL().setThreadInfo(threadInfo);
    }

    /**
     * 当前线程是否输出Log
     * @param flag true: 输出当前线程的Log, false: 不输出当前线程的Log, 默认true
     * @return
     */
    public static L logOutput(boolean flag) {
        return getL().setLogOutput(flag);
    }

    /**
     * 初始化Log工具类
     * @param isDebug 是否开启Debug模式, true: 输出Log, false:不输出Log
     * @param isLoggableIgnore 是否忽略Loggable检查, true: 忽略, 输出所有Log, false: 不忽略,
     *                         可以通过setprop log.tag.<YOUR_LOG_TAG> <LEVEL>来改变log的默认level，如adb shell setprop log.tag.InCall D。
     *                         也可以将这些属性按照log.tag.InCall=D的形式，写入/data/local.prop中。
     * @return
     */
    public static L init(boolean isDebug, boolean isLoggableIgnore) {
        mIsDebug = isDebug;
        mIsLoggableIgnore = isLoggableIgnore;
        return getL();
    }

    public static boolean isLoggableIgnore() {
        return mIsLoggableIgnore;
    }

    public static boolean isDebug() {
        return mIsDebug;
    }

    /**
     * 输出精简异常原因
     * @param exception
     * @return
     */
    public static String exception2String(Throwable exception) {
        return Log.getStackTraceString(exception);
    }

    /**
     * 输出详细异常信息
     * @param exception
     * @return
     */
    public static String exception2String2(Throwable exception) {
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        exception.printStackTrace(printWriter);
        Throwable cause = exception.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        return writer.toString();
    }

    public static L v(String tag, Object msg) {
        return getL().verbose(tag, msg);
    }

    public static L v(Object msg) {
        return getL().verbose(msg);
    }

    private static L getL() {
        if (mLogFactory != null) {
            return mLogFactory.getLogger();
        }
        return Logger.get();
    }

    /**
     * 自定义Log工厂, 默认使用{@link Logger Logger}
     * @param logFactory
     */
    public static void setLogFactory(LogFactory logFactory) {
        mLogFactory = logFactory;
    }

    public static L v() {
        return getL().verbose();
    }

    public static L i(String tag, Object msg) {
        return getL().info(tag, msg);
    }

    public static L i(Object msg) {
        return getL().info(msg);
    }

    public static L i() {
        return getL().info();
    }

    public static L d(String tag, Object msg) {
        return getL().debug(tag, msg);
    }

    public static L d(Object msg) {
        return getL().debug(msg);
    }

    public static L d() {
        return getL().debug();
    }

    public static L w(String tag, Object msg) {
        return getL().warn(tag, msg);
    }

    public static L w(Object msg) {
        return getL().warn(msg);
    }

    public static L w() {
        return getL().warn();
    }

    public static L e(String tag, Object msg) {
        return getL().error(tag, msg);
    }

    public static L e(String msg, Throwable tr) {
        return getL().error(msg, tr);
    }

    public static L e(Object msg) {
        return getL().error(msg);
    }

    public static L e() {
        return getL().error();
    }

    public static L detailException(boolean detail){
        return getL().setDetailException(detail);
    }

    protected abstract L setDetailException(boolean detail);

    protected abstract L methodCount(int count);

    protected abstract L setThreadInfo(boolean threadInfo);

    protected abstract L setLogOutput(boolean flag);

    protected abstract L verbose(String tag, Object msg);

    protected abstract L verbose(Object msg);

    protected abstract L verbose();

    protected abstract L info(String tag, Object msg);

    protected abstract L info(Object msg);

    protected abstract L info();

    protected abstract L debug(String tag, Object msg);

    protected abstract L debug(Object msg);

    protected abstract L debug();

    protected abstract L warn(String tag, Object msg);

    protected abstract L warn(Object msg);

    protected abstract L warn();

    protected abstract L error(String tag, Object msg);

    protected abstract L error(Object msg);

    protected abstract L error();

}
