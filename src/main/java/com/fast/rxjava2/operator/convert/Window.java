package com.fast.rxjava2.operator.convert;

import com.fast.rxjava2.BaseRunClass;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * 按照实际划分窗口，将数据发送给不同的 Observable
 *
 * @author bowen.yan
 * @date 2018-09-09
 */
public class Window extends BaseRunClass {
    public static void main(String[] args) {
        Observable.interval(1, TimeUnit.SECONDS) // 间隔一秒发一次
                .take(15) // 最多接收15个
                .window(3, TimeUnit.SECONDS)
                //.observeOn(AndroidSchedulers.mainThread())
                .blockingSubscribe(new Consumer<Observable<Long>>() {
                    @Override
                    public void accept(@NonNull Observable<Long> longObservable) throws Exception {
                        logger.info("Sub Divide begin...");
                        longObservable.blockingSubscribe(new Consumer<Long>() {
                            @Override
                            public void accept(@NonNull Long aLong) throws Exception {
                                logger.info("Next:" + aLong);
                            }
                        });
                    }
                });
    }
}
