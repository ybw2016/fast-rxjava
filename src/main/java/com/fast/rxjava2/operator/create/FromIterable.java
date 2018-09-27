package com.fast.rxjava2.operator.create;

import com.fast.rxjava2.BaseRunClass;

import java.util.Arrays;

import io.reactivex.Observable;

/**
 * @author bowen.yan
 * @date 2018-09-09
 */
public class FromIterable extends BaseRunClass {
    public static void main(String[] args) {
        Integer[] items = {0, 1, 2, 3, 4, 5};
        printTitle("fromArray");
        Observable.fromArray(items).subscribe(
                integer -> System.out.println(integer));

        printLine("fromCallable");
        Observable.fromCallable(() -> Arrays.asList("hello", "gaga"))
                .subscribe(strings -> System.out.println(strings));

        printLine("fromIterable");
        Observable.fromIterable(Arrays.<String>asList("one", "two", "three"))
                .subscribe(integer -> System.out.println(integer));

        printLine("fromFuture");
        Observable.fromFuture(Observable.just(1).toFuture())
                .doOnComplete(() -> System.out.println("complete"))
                .subscribe();

    }
}
