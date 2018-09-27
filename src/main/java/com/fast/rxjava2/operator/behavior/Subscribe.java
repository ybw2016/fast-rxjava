package com.fast.rxjava2.operator.behavior;

import com.fast.rxjava2.BaseRunClass;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 订阅，即连接观察者 & 被观察者 -> observable.subscribe(observer);
 *
 * @author bowen.yan
 * @date 2018-09-09
 */
public class Subscribe extends BaseRunClass {
    public static void main(String[] args) {

// 前者 = 被观察者（observable）；后者 = 观察者（observer 或 subscriber）

//<--1. 分步骤的完整调用-- >
//  步骤1： 创建被观察者 Observable 对象
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        });

// 步骤2：创建观察者 Observer 并 定义响应事件行为
        Observer<Integer> observer = new Observer<Integer>() {
            // 通过复写对应方法来 响应 被观察者
            @Override
            public void onSubscribe(Disposable d) {
                logger.info("开始采用subscribe连接");
            }

            // 默认最先调用复写的 onSubscribe（）
            @Override
            public void onNext(Integer value) {
                logger.info("对Next事件" + value + "作出响应");
            }

            @Override
            public void onError(Throwable e) {
                logger.info("对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                logger.info("对Complete事件作出响应");
            }
        };

        // 步骤3：通过订阅（subscribe）连接观察者和被观察者
        observable.subscribe(observer);

        printLine();

//<--2. 基于事件流的链式调用-- >
        Observable.create(new ObservableOnSubscribe<Integer>() {
            // 1. 创建被观察者 & 生产事件
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        }).subscribe(new Observer<Integer>() {
            // 2. 通过通过订阅（subscribe）连接观察者和被观察者
            // 3. 创建观察者 & 定义响应事件的行为
            @Override
            public void onSubscribe(Disposable d) {
                logger.info("开始采用subscribe连接");
            }
            // 默认最先调用复写的 onSubscribe（）

            @Override
            public void onNext(Integer value) {
                logger.info("对Next事件" + value + "作出响应");
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
