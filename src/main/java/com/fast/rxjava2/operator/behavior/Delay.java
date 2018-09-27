package com.fast.rxjava2.operator.behavior;

import com.fast.rxjava2.BaseRunClass;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author bowen.yan
 * @date 2018-09-09
 */
public class Delay extends BaseRunClass {
    public static void main(String[] args) {
        Observable.just(1, 2, 3)
                .delay(2, TimeUnit.SECONDS) // 延迟3s再发送，由于使用类似，所以此处不作全部展示
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

        sleep(3);
    }
}
