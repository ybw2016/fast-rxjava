package com.fast.rxjava2.operator.convert;

import com.fast.rxjava2.BaseRunClass;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;

/**
 * @author bowen.yan
 * @date 2018-09-09
 */
public class Scan extends BaseRunClass {
    public static void main(String[] args) {
        Observable.just(1, 2, 3, 4, 5)
                .scan(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer, Integer integer2) throws Exception {

                        return integer + integer2;
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                logger.info("accept: " + integer);
            }
        });
    }
}
