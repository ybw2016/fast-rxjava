package com.fast.rxjava2.operator.combine;

import com.fast.rxjava2.BaseRunClass;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

/**
 * @author bowen.yan
 * @date 2018-09-09
 */
public class Join extends BaseRunClass {
    public static void main(String[] args) {
        Observable<String> observable1 = Observable.fromArray("aaa", "bbb", "ccc");
        Observable<Integer> observable2 = Observable.fromArray(111, 222, 333);
        observable1.join(observable2,
                chars -> Observable.timer(2, TimeUnit.SECONDS),
                integers -> Observable.timer(2, TimeUnit.SECONDS),
                (charItem, intergerItem) -> {
                    return charItem + "_" + intergerItem;
                }
        ).blockingSubscribe(System.out::println);
    }
}
