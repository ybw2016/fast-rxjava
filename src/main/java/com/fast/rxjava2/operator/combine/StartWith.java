package com.fast.rxjava2.operator.combine;

import com.fast.rxjava2.BaseRunClass;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * startWith(iterable) -> 优先将iterable中的数据发射出
 *
 * @author bowen.yan
 * @date 2018-09-09
 */
public class StartWith extends BaseRunClass {
    public static void main(String[] args) {
//        <-- 在一个被观察者发送事件前，追加发送一些数据 -->
        // 注：追加数据顺序 = 后调用先追加
        Observable.just(4, 5, 6)
                .startWith(0)  // 追加单个数据 = startWith()
                .startWithArray(1, 2) // 追加多个数据 = startWithArray()
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
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

        printLine();

//<-- 在一个被观察者发送事件前，追加发送被观察者 & 发送数据 -->
        // 注：追加数据顺序 = 后调用先追加
        Observable.just(4, 5, 6)
                .startWith(Observable.just(-999, 1, 2, 3))
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

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
    }
/*
[2018-09-25 17:33:43,250][INFO ][][] [com.fast.rxjava2.operator.convert.Map.onNext():@27] - 接收到了事件1
[2018-09-25 17:33:43,251][INFO ][][] [com.fast.rxjava2.operator.convert.Map.onNext():@27] - 接收到了事件2
[2018-09-25 17:33:43,252][INFO ][][] [com.fast.rxjava2.operator.convert.Map.onNext():@27] - 接收到了事件0
[2018-09-25 17:33:43,252][INFO ][][] [com.fast.rxjava2.operator.convert.Map.onNext():@27] - 接收到了事件4
[2018-09-25 17:33:43,252][INFO ][][] [com.fast.rxjava2.operator.convert.Map.onNext():@27] - 接收到了事件5
[2018-09-25 17:33:43,252][INFO ][][] [com.fast.rxjava2.operator.convert.Map.onNext():@27] - 接收到了事件6
[2018-09-25 17:33:43,253][INFO ][][] [com.fast.rxjava2.operator.convert.Map.onComplete():@37] - 对Complete事件作出响应
=======================================
[2018-09-25 17:33:43,254][INFO ][][] [com.fast.rxjava2.operator.convert.Map.onNext():@55] - 接收到了事件-999
[2018-09-25 17:33:43,254][INFO ][][] [com.fast.rxjava2.operator.convert.Map.onNext():@55] - 接收到了事件1
[2018-09-25 17:33:43,254][INFO ][][] [com.fast.rxjava2.operator.convert.Map.onNext():@55] - 接收到了事件2
[2018-09-25 17:33:43,254][INFO ][][] [com.fast.rxjava2.operator.convert.Map.onNext():@55] - 接收到了事件3
[2018-09-25 17:33:43,254][INFO ][][] [com.fast.rxjava2.operator.convert.Map.onNext():@55] - 接收到了事件4
[2018-09-25 17:33:43,254][INFO ][][] [com.fast.rxjava2.operator.convert.Map.onNext():@55] - 接收到了事件5
[2018-09-25 17:33:43,254][INFO ][][] [com.fast.rxjava2.operator.convert.Map.onNext():@55] - 接收到了事件6
[2018-09-25 17:33:43,255][INFO ][][] [com.fast.rxjava2.operator.convert.Map.onComplete():@65] - 对Complete事件作出响应
*/
}
