package com.fast.rxjava2.operator.convert;

import com.fast.rxjava2.BaseRunClass;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * @author bowen.yan
 * @date 2018-09-09
 */
public class FlatMap2 extends BaseRunClass {
    public static void main(String[] args) {
        flatMap4Time();
    }

    public static void flatMap1Time() {
        // 采用RxJava基于事件流的链式操作
        Observable.create(new ObservableOnSubscribe<List<List<Integer>>>() {
            @Override
            public void subscribe(ObservableEmitter<List<List<Integer>>> emitter) throws Exception {
                List<Integer> list1 = Arrays.asList(1, 2, 3);
                List<Integer> list2 = Arrays.asList(777, 888, 999);
                List<List<Integer>> list = Arrays.asList(list1, list2);
                emitter.onNext(list);
            }
            // 采用flatMap（）变换操作符
        }).flatMap(new Function<List<List<Integer>>, ObservableSource<List<Integer>>>() {
            @Override
            public ObservableSource<List<Integer>> apply(List<List<Integer>> lists) throws Exception {
                return Observable.fromIterable(lists);
            }
        }).subscribe(new Consumer<List<Integer>>() {
            @Override
            public void accept(List<Integer> list) throws Exception {
                logger.info("accept data ->" + list);
            }
        });
    }

    public static void flatMap2Time() {
        // 采用RxJava基于事件流的链式操作
        Observable.create(new ObservableOnSubscribe<List<List<Integer>>>() {
            @Override
            public void subscribe(ObservableEmitter<List<List<Integer>>> emitter) throws Exception {
                List<Integer> list1 = Arrays.asList(1, 2, 3);
                List<Integer> list2 = Arrays.asList(777, 888, 999);
                List<List<Integer>> list = Arrays.asList(list1, list2);
                emitter.onNext(list);
            }
            // 采用flatMap（）变换操作符
        }).flatMap(new Function<List<List<Integer>>, ObservableSource<List<Integer>>>() {
            @Override
            public ObservableSource<List<Integer>> apply(List<List<Integer>> lists) throws Exception {
                return Observable.fromIterable(lists);
            }
        }).flatMap(new Function<List<Integer>, ObservableSource<Integer>>() {
            @Override
            public ObservableSource<Integer> apply(List<Integer> integers) throws Exception {
                return Observable.fromIterable(integers);
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                logger.info("accept data ->" + integer);
            }
        });
    }

    public static void flatMap3Time() {
        // 采用RxJava基于事件流的链式操作
        Observable.create(new ObservableOnSubscribe<List<List<Integer>>>() {
            @Override
            public void subscribe(ObservableEmitter<List<List<Integer>>> emitter) throws Exception {
                List<Integer> list1 = Arrays.asList(1, 2, 3);
                List<Integer> list2 = Arrays.asList(777, 888, 999);
                List<List<Integer>> list = Arrays.asList(list1, list2);
                emitter.onNext(list);
            }
            // 采用flatMap（）变换操作符
        }).flatMap(new Function<List<List<Integer>>, ObservableSource<List<Integer>>>() {
            @Override
            public ObservableSource<List<Integer>> apply(List<List<Integer>> lists) throws Exception {
                return Observable.fromIterable(lists);
            }
        }).flatMap(new Function<List<Integer>, ObservableSource<Integer>>() {
            @Override
            public ObservableSource<Integer> apply(List<Integer> integers) throws Exception {
                return Observable.fromIterable(integers);
            }
        }).reduce(new BiFunction<Integer, Integer, Integer>() {
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

    public static void flatMap4Time() {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        // 采用RxJava基于事件流的链式操作
        // 在该复写方法中复写聚合的逻辑
        Observable.create(new ObservableOnSubscribe<List<List<Integer>>>() {
            @Override
            public void subscribe(ObservableEmitter<List<List<Integer>>> emitter) throws Exception {
                List<Integer> list1 = Arrays.asList(1, 2, 3);
                List<Integer> list2 = Arrays.asList(777, 888, 999);
                List<List<Integer>> list = Arrays.asList(list1, list2);
                emitter.onNext(list);
            }
            // 采用flatMap（）变换操作符
        }).flatMap(new Function<List<List<Integer>>, ObservableSource<List<Integer>>>() {
            @Override
            public ObservableSource<List<Integer>> apply(List<List<Integer>> lists) throws Exception {
                return Observable.fromIterable(lists);
            }
        }).flatMap(new Function<List<Integer>, ObservableSource<Integer>>() {
            @Override
            public ObservableSource<Integer> apply(List<Integer> integers) throws Exception {
                return Observable.fromIterable(integers);
            }
        }).reduce(2, new BiFunction<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer integer, Integer integer2) throws Exception {
                return integer * integer2;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer s) throws Exception {
                logger.info("最终计算的结果是： " + s);
                countDownLatch.countDown();
            }
        });
        await(countDownLatch);
    }
}
