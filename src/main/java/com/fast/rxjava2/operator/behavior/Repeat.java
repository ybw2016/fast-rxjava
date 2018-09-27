package com.fast.rxjava2.operator.behavior;

import com.fast.rxjava2.BaseRunClass;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 重复发送数据
 *
 * @author bowen.yan
 * @date 2018-09-09
 */
public class Repeat extends BaseRunClass {
    public static void main(String[] args) {
// 不传入参数 = 重复发送次数 = 无限次
//        repeat（）；
//        // 传入参数 = 重复发送次数有限
//        repeatWhen（Integer int ）；

// 注：
        // 1. 接收到.onCompleted()事件后，触发重新订阅 & 发送
        // 2. 默认运行在一个新的线程上

        // 具体使用
        Observable.just(1, 2, 3, 4)
                .repeat(3) // 重复创建次数 =- 3次
                .subscribe(new Observer<Integer>() {
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
    }
}
