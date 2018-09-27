package com.fast.rxjava2.operator.misc;

/**
 * 概况一下就是：Error是系统级别的异常（如OOM, 类初始化ExceptionInInitializerError异常），程序员不应该去捕捉。
 * Throwable: 所有异常的父类
 * Exception: 一般的业务异常
 * RuntimeException: 运行的异常
 *
 * @author bowen.yan
 * @date 2018-09-17
 */
public class ExceptionTest {
    public static void main(String[] args) {
        // https://www.cnblogs.com/smile361/p/5521278.html
        /*
    Throwable: Java中所有异常和错误类的父类。只有这个类的实例（或者子类的实例）可以被虚拟机抛出或者被java的throw关键字抛出。同样，只有其或其子类可以出现在catch子句里面。
    Error: Throwable的子类，表示严重的问题发生了，而且这种错误是不可恢复的。
    Exception: Throwable的子类，应用程序应该要捕获其或其子类（RuntimeException例外），称为checked exception。比如：IOException, NoSuchMethodException...
    RuntimeException: Exception的子类，运行时异常，程序可以不捕获，称为unchecked exception。比如：NullPointException.
        * */
        Exception ex;
        Throwable throwable;
        RuntimeException runtimeException;
        Error error;

    }
}
