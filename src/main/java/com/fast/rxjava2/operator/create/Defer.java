package com.fast.rxjava2.operator.create;

import com.fast.rxjava2.BaseRunClass;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author bowen.yan
 * @date 2018-09-09
 */
public class Defer extends BaseRunClass {
    public static void main(String[] args) {
//       <-- 1. 第1次对i赋值 ->>
        final Integer i = 10;

        // 2. 通过defer 定义被观察者对象
        // 注：此时被观察者对象还没创建
        Observable<Integer> observable = Observable.defer(new Callable<ObservableSource<? extends Integer>>() {
            @Override
            public ObservableSource<? extends Integer> call() throws Exception {
                return Observable.just(i);
            }
        });

//        <-- 2. 第2次对i赋值 ->>
//        i = 15;

//        <-- 3. 观察者开始订阅 ->>
        // 注：此时，才会调用defer（）创建被观察者对象（Observable）
        observable.subscribe(new Observer<Integer>() {

            @Override
            public void onSubscribe(Disposable d) {
                logger.info("开始采用subscribe连接");
            }

            @Override
            public void onNext(Integer value) {
                logger.info("接收到的整数是" + value);
            }

            @Override
            public void onError(Throwable e) {
                logger.info("对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                logger.info("对Complete事件作出响应");
            }
        });
    }
}
