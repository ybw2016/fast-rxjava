package com.fast.rxjava2.operator.create;

import com.fast.rxjava2.BaseRunClass;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author bowen.yan
 * @date 2018-09-09
 */
public class Timer extends BaseRunClass {
    public static void main(String[] args) {
// 该例子 = 延迟2s后，发送一个long类型数值
        Observable.timer(2, TimeUnit.SECONDS)
                .create(new ObservableOnSubscribe<Integer>() {
                    // 2. 在复写的subscribe（）里定义需要发送的事件
                    @Override
                    public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                        emitter.onNext(1);
                        emitter.onComplete();
                    }  // 至此，一个被观察者对象（Observable）就创建完毕
                }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                logger.info("开始采用subscribe连接");
            }

            @Override
            public void onNext(Integer value) {
                logger.info("接收到了事件" + value);
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

// 注：timer操作符默认运行在一个新线程上
// 也可自定义线程调度器（第3个参数）：timer(long,TimeUnit,Scheduler)
    }
}
