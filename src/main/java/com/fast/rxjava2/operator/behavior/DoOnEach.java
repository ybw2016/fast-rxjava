package com.fast.rxjava2.operator.behavior;

import com.fast.rxjava2.BaseRunClass;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Notification;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * doOnEach() 相当于java8 stream中的peek()方法
 *
 * @author bowen.yan
 * @date 2018-09-09
 */
public class DoOnEach extends BaseRunClass {
    public static void main(String[] args) {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onError(new RuntimeException());
                //e.onError(new Throwable("发生错误了"));
            }
        })
                .flatMap(new Function<Integer, ObservableSource<Integer>>() {
                    @Override
                    public ObservableSource<Integer> apply(Integer integer) throws Exception {
                        final List<Integer> list = new ArrayList<>();
                        for (int i = 0; i < 3; i++) {
                            list.add(i * 2);
                            // 通过flatMap中将被观察者生产的事件序列先进行拆分，再将每个事件转换为一个新的发送三个String事件
                            // 最终合并，再发送给被观察者
                        }
                        return Observable.fromIterable(list);
                    }
                })
                // 1. 当Observable每发送1次数据事件就会调用1次
                .doOnEach(new Consumer<Notification<Integer>>() {
                    @Override
                    public void accept(Notification<Integer> integerNotification) throws Exception {
                        logger.info("doOnEach_accept: " + integerNotification.getValue());
                    }
                })
//                // 2. 执行Next事件前调用
//                .doOnNext(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) throws Exception {
//                        logger.info("doOnNext->>: " + integer);
//                    }
//                })
//                // 3. 执行Next事件后调用
//                .doAfterNext(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) throws Exception {
//                        logger.info("doAfterNext: " + integer);
//                    }
//                })
//                // 4. Observable正常发送事件完毕后调用
//                .doOnComplete(new Action() {
//                    @Override
//                    public void run() throws Exception {
//                        logger.info("doOnComplete: ");
//                    }
//                })
//                // 5. Observable发送错误事件时调用
//                .doOnError(new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        logger.info("doOnError: " + throwable.getMessage());
//                    }
//                })
//                // 6. 观察者订阅时调用
//                .doOnSubscribe(new Consumer<Disposable>() {
//                    @Override
//                    public void accept(@NonNull Disposable disposable) throws Exception {
//                        logger.info("doOnSubscribe: ");
//                    }
//                })
//                // 7. Observable发送事件完毕后调用，无论正常发送完毕 / 异常终止
//                .doAfterTerminate(new Action() {
//                    @Override
//                    public void run() throws Exception {
//                        logger.info("doAfterTerminate: ");
//                    }
//                })
//                // 8. 最后执行
//                .doFinally(new Action() {
//                    @Override
//                    public void run() throws Exception {
//                        logger.info("doFinally: ");
//                    }
//                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        logger.info("接收到了事件onSubscribe -> {}", d);
                    }

                    @Override
                    public void onNext(Integer value) {
                        logger.info("接收到了事件onNext -> " + value);
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

        //sleep(3);
    }
}
