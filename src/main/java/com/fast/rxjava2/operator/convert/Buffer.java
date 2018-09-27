package com.fast.rxjava2.operator.convert;

import com.fast.rxjava2.BaseRunClass;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author bowen.yan
 * @date 2018-09-09
 */
public class Buffer extends BaseRunClass {
    public static void main(String[] args) {
        // 被观察者 需要发送5个数字
        Observable.just(1, 2, 3, 4, 5, 6, 7)
                .buffer(1, 3) // 设置缓存区大小 & 步长
                // 缓存区大小 = 每次从被观察者中获取的事件数量
                // 步长 = 每次获取新事件的数量
                .subscribe(new Observer<List<Integer>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Integer> stringList) {
                        logger.info("缓存区里的事件数量 = " + stringList.size());
                        for (Integer value : stringList) {
                            logger.info(" 事件 = " + value);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        logger.info("对Error事件作出响应", e);
                    }

                    @Override
                    public void onComplete() {
                        logger.info("对Complete事件作出响应");
                    }
                });
    }
}
