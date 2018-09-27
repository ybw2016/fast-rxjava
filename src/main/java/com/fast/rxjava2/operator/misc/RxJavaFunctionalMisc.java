package com.fast.rxjava2.operator.misc;


import io.reactivex.Observable;

/**
 * 概况一下就是：Error是系统级别的异常（如OOM, 类初始化ExceptionInInitializerError异常），程序员不应该去捕捉。
 * Throwable: 所有异常的父类
 * Exception: 一般的业务异常
 * RuntimeException: 运行的异常
 *
 * @author bowen.yan
 * @date 2018-09-17
 */
public class RxJavaFunctionalMisc {
    public static void main(String[] args) {
        Observable.fromArray(1, 2, 3, 5, 8)
                .filter(data -> data > 3)
                .map(integer -> "Str" + integer)
                .subscribe(System.out::println);
    }

    public static void runMisc() {
//        Observable.fromCallable(() -> null)
//                .subscribe(System.out::println, Throwable::printStackTrace);
    }
}
