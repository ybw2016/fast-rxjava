package com.fast.rxjava2.operator.combine;

import com.fast.rxjava2.BaseRunClass;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * @author bowen.yan
 * @date 2018-09-09
 */
public class Count extends BaseRunClass {
    public static void main(String[] args) {
// 注：返回结果 = Long类型
        Observable.just(1, 2, 3, 4)
                .count()
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        logger.info("发送的事件数量 =  " + aLong);
                    }
                });
    }
}
