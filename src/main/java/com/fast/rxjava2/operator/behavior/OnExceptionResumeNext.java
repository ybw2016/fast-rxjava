package com.fast.rxjava2.operator.behavior;

import com.fast.rxjava2.BaseRunClass;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author bowen.yan
 * @date 2018-09-09
 */
public class OnExceptionResumeNext extends BaseRunClass {
    public static void main(String[] args) {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onError(new Exception("发生错误了"));
            }
        })
                .onExceptionResumeNext(new Observable<Integer>() {
                    @Override
                    protected void subscribeActual(Observer<? super Integer> observer) {
                        observer.onNext(11);
                        observer.onNext(22);
                        observer.onComplete();
                    }
                })
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
}
