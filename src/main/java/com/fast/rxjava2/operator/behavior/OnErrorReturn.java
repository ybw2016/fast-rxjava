package com.fast.rxjava2.operator.behavior;

import com.fast.rxjava2.BaseRunClass;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * 跟java8函数式编程中的orElseGet()类似
 * List<T>.stream().filter(t-> t.getName().equals("abc").findFirst().orElseGet("");
 *
 * @author bowen.yan
 * @date 2018-09-09
 */
public class OnErrorReturn extends BaseRunClass {
    public static void main(String[] args) {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onError(new Throwable("手动抛出一个错误"));
            }
        })
                .onErrorReturn(new Function<Throwable, Integer>() {
                    @Override
                    public Integer apply(@NonNull Throwable throwable) throws Exception {
                        // 捕捉错误异常
                        logger.info("在onErrorReturn处理了错误: " + throwable.toString());
                        return 666;
                        // 发生错误事件后，发送一个"666"事件，最终正常结束
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer value) {
                        logger.info("接收到了事件 -> " + value);
                    }

                    // DIY ---> 报错，应该打印所有的错误信息
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
