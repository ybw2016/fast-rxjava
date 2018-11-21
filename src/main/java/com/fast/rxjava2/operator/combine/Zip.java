package com.fast.rxjava2.operator.combine;

import com.fast.rxjava2.BaseRunClass;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;

/**
 * 该类型的操作符主要是对多个被观察者中的事件进行合并处理。
 * 简而言之：就是从第一个数据源中获取1个数据，从第2个数据源获取数据2，然后使用BiFunction(d1,d2,r)，返回r
 *
 * @author bowen.yan
 * @date 2018-09-09
 */
public class Zip extends BaseRunClass {
    public static void main(String[] args) {
        //<-- 创建第1个被观察者 -->
        Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                logger.info("被观察者1发送了事件1");
                emitter.onNext(1);
                // 为了方便展示效果，所以在发送事件后加入2s的延迟
//                Thread.sleep(1000);

                logger.info("被观察者1发送了事件2");
                emitter.onNext(2);
//                Thread.sleep(1000);

                logger.info("被观察者1发送了事件3");
                emitter.onNext(3);
//                Thread.sleep(1000);

                // DIY 用来测试是否会取少的数据源
                logger.info("被观察者1发送了事件4");
                emitter.onNext(4);
                logger.info("被观察者1发送了事件5");
                emitter.onNext(5);

                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()); // 设置被观察者1在工作线程1中工作

//<-- 创建第2个被观察者 -->
        Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                logger.info("被观察者2发送了事件A");
                emitter.onNext("A");
//                Thread.sleep(1000);

                logger.info("被观察者2发送了事件B");
                emitter.onNext("B");
//                Thread.sleep(1000);

                logger.info("被观察者2发送了事件C");
                emitter.onNext("C");
//                Thread.sleep(1000);

                logger.info("被观察者2发送了事件D");
                emitter.onNext("D");
//                Thread.sleep(1000);

                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.newThread());// 设置被观察者2在工作线程2中工作
        // 假设不作线程控制，则该两个被观察者会在同一个线程中工作，即发送事件存在先后顺序，而不是同时发送

//<-- 使用zip变换操作符进行事件合并 -->
// 注：创建BiFunction对象传入的第3个参数 = 合并后数据的数据类型
        Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer integer, String string) throws Exception {
                return integer + "|" + string + "#";
            }
        }).blockingSubscribe(System.out::println);
//        }).subscribe(new Observer<String>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//                logger.info("onSubscribe");
//            }
//
//            @Override
//            public void onNext(String value) {
//                logger.info("最终接收到的事件 =  " + value);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                logger.info("onError");
//            }
//
//            @Override
//            public void onComplete() {
//                logger.info("onComplete");
//            }
//        });

        sleep(6);

    }
}
