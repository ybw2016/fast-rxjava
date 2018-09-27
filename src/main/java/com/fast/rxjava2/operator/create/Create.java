package com.fast.rxjava2.operator.create;

import com.fast.rxjava2.BaseRunClass;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 完整创建1个被观察者对象（Observable）
 *
 * @author bowen.yan
 * @date 2018-09-09
 */
public class Create extends BaseRunClass {
    public static void main(String[] args) {
        // 1. 通过creat（）创建被观察者对象
        Observable.create(new ObservableOnSubscribe<Integer>() {
            // 2. 在复写的subscribe（）里定义需要发送的事件
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                String data = "a" + "b";
                if (data != null) {
                    throw new RuntimeException("observable exception ");
                }
                emitter.onNext(3);

                emitter.onComplete();
            }  // 至此，一个被观察者对象（Observable）就创建完毕
        }).subscribe(new Observer<Integer>() {
            // 以下步骤仅为展示一个完整demo，可以忽略
            // 3. 通过通过订阅（subscribe）连接观察者和被观察者
            // 4. 创建观察者 & 定义响应事件的行为
            @Override
            public void onSubscribe(Disposable d) {
//                sleep(2);
                logger.info("开始采用subscribe连接");
            }

            // 默认最先调用复写的 onSubscribe（）
            @Override
            public void onNext(Integer value) {
                logger.info("接收到了事件" + value);
            }

            @Override
            public void onError(Throwable e) {
                logger.error("对Error事件作出响应", e);
            }

            @Override
            public void onComplete() {
                logger.info("对Complete事件作出响应");
            }
        });
    }
}
