package com.fast.rxjava2.apply.thread;

import com.fast.rxjava2.BaseRunClass;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 不使用Schedulers.io(), Schedulers.newThread()的话，则默认线程为主线程
 * ===============================类型	含义	应用场景===============================
 * Schedulers.immediate()				当前线程 = 不指定线程	默认
 * AndroidSchedulers.mainThread()		Android主线程	操作UI
 * Schedulers.newThread()				常规新线程	耗时等操作
 * Schedulers.io()						io操作线程	网络请求、读写文件等io密集型操作
 * Schedulers.computation()			    CPU计算操作线程	大量计算操作
 *
 *
 * 执行步骤												线程信息
 * Observable.just().map()								main();
 * .observerOn(Schedulers.newThread())					RxNewThreadScheduler-1
 * .observerOn(Schedulers.io())						    RxCachedThreadScheduler
 * .observerOn(AndroidSchedulers.mainThread())			main();
 * ==============================================================================
 *
 * @author bowen.yan
 * @date 2018-09-17
 */
public class SwitchThread extends BaseRunClass {
    public static void main(String[] args) {
        // 步骤1：创建被观察者 Observable & 发送事件
        // 在主线程创建被观察者 Observable 对象
        // 所以生产事件的线程是：主线程

        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {

                logger.info("被观察者 Observable的工作线程是: " + Thread.currentThread().getName());
                // 打印验证
                emitter.onNext(1);
                emitter.onComplete();
            }
        });

        // 步骤2：创建观察者 Observer 并 定义响应事件行为
        // 在主线程创建观察者 Observer 对象
        // 所以接收 & 响应事件的线程是：主线程
        Observer<Integer> observer = new Observer<Integer>() {

            @Override
            public void onSubscribe(Disposable d) {
                // 打印验证
                logger.info("开始采用subscribe连接");
                logger.info("观察者 Observer的工作线程是: " + Thread.currentThread().getName());
            }

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
    }
}
