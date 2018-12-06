package com.fast.rxjava2.operator.behavior;

import com.fast.rxjava2.BaseRunClass;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

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

                e.onComplete();
                //e.onError(new Throwable("发生错误了"));
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
