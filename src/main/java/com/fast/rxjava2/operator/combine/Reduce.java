package com.fast.rxjava2.operator.combine;

import com.fast.rxjava2.BaseRunClass;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;

/**
 * 跟函数式编程中的.reduce()方法类似
 * int result = List<T>.stream().reduce(0,(current,next)-> current+next);
 *
 * @author bowen.yan
 * @date 2018-09-09
 */
public class Reduce extends BaseRunClass {
    public static void main(String[] args) {
        Observable.just(1, 2, 3, 4)
                .reduce(new BiFunction<Integer, Integer, Integer>() {
                    // 在该复写方法中复写聚合的逻辑
                    @Override
                    public Integer apply(@NonNull Integer s1, @NonNull Integer s2) throws Exception {
                        logger.info("本次计算的数据是： " + s1 + " 乘 " + s2);
                        return s1 * s2;
                        // 本次聚合的逻辑是：全部数据相乘起来
                        // 原理：第1次取前2个数据相乘，之后每次获取到的数据 = 返回的数据x原始下1个数据每
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer s) throws Exception {
                logger.info("最终计算的结果是： " + s);
            }
        });
    }
}
