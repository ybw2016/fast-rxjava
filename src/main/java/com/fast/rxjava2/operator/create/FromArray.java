package com.fast.rxjava2.operator.create;

import com.fast.rxjava2.BaseRunClass;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author bowen.yan
 * @date 2018-09-09
 */
public class FromArray extends BaseRunClass {
    public static void main(String[] args) {
        /*
         * 数组遍历
         **/
        // 1. 设置需要传入的数组
        Integer[] items = getNumbers();

        // 2. 创建被观察者对象（Observable）时传入数组
        // 在创建后就会将该数组转换成Observable & 发送该对象中的所有数据
        Observable.fromArray(items)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        logger.info("数组遍历");
                    }

                    @Override
                    public void onNext(Integer value) {
                        logger.info("数组中的元素 = " + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        logger.info("对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        logger.info("遍历结束");
                    }
                });
    }

    public static Integer[] getNumbers() {
        return new Integer[]{getData(0), getData(1), getData(2),
                getData(3), getData(4), getData(5)};
    }

    static Integer getData(Integer number) {
        sleep(number % 2);
        return number;
    }
}
