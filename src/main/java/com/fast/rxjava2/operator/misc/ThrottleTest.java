package com.fast.rxjava2.operator.misc;

import com.fast.rxjava2.BaseRunClass;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * throttleWithTimeout也叫作debounce（去抖），限制两个连续事件的
 * 先后执行时间不得小于某个时间窗口。
 * 如上图所示，throttleWithTimeout限制两个连续事件的最小间隔时
 * 间窗口。throttleFirst/ throttleLast是基于决定时间做的处理，是以固定时
 * 间窗口为基准，对同一个固定时间窗口内的多个连续事件最多只处理一
 * 个。而throttleWithTimeout是基于两个连续事件的相对时间，当两个连
 * 续事件的间隔时间小于最小间隔时间窗口，就会丢弃上一个事件，而如
 * 果最后一个事件等待了最小间隔时间窗口后还没有新的事件到来，那么
 * 会处理最后一个事件。
 * 如搜索关键词自动补全，如果用户每录入一个字就发送一次请求，
 * 而先输入的字的自动补全会被很快到来的下一个字符覆盖，那么会导致
 * 先期的自动补全是无用的。throttleWithTimeout就是来解决这个问题
 * 的，通过它来减少频繁的网络请求，避免每输入一个字就导致一次请
 * 求。
 *
 * @author bowen.yan
 * @date 2018-11-18
 * @see com.fast.rxjava2.operator.filter.Debounce
 */
public class ThrottleTest extends BaseRunClass {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Observable.create(new ObservableOnSubscribe<Integer>() {
                @Override
                public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                    next(emitter, 1, 0); // 0ms
                    next(emitter, 2, 50);// 50ms
                    next(emitter, 3, 100);// 100ms -> 50ms
                    next(emitter, 4, 30);// 130ms
                    next(emitter, 5, 40);// 170ms
                    next(emitter, 6, 105);// 270ms
                    emitter.onComplete();
                }
            })
                    .subscribeOn(Schedulers.newThread())
                    .throttleWithTimeout(100, TimeUnit.MILLISECONDS)
                    .blockingSubscribe(new Consumer<Integer>() {
                        @Override
                        public void accept(Integer integer) throws Exception {
                            System.out.println("out result -> " + integer);
                        }
                    });
            printLine();
            sleep(1);
        }
        // 当间隔时间为100时，时而输出5和6，时而只输出6，到底是为啥？
        // 当最后一条数据发送间隔改成100左右时，同时出现5和6的概率很小；（10次出现1次）
        // 但改成120时，同时出现5和6的概率基本上是100%，可以断定是因为微妙容易出现判断不准确的问题
    }

    private static void next(ObservableEmitter<Integer> emitter, int index, int millSeconds) {
        sleepMillSeconds(millSeconds);
        emitter.onNext(index);
    }
}
