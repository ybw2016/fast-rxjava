package com.fast.rxjava2.operator.filter;

import com.fast.rxjava2.BaseRunClass;

import io.reactivex.Observable;

/**
 * @author bowen.yan
 * @date 2018-09-09
 */
public class TakeUntil extends BaseRunClass {
    public static void main(String[] args) {
        Observable.just(2, 3, 4, 5, 6, 7)
                // -> 不满足就重试
                //发送complete的结束条件 当然发送结束之前也会包括这个值
                .takeUntil(integer -> integer > 3)
                .subscribe(o -> System.out.print(o + "t"));//2,3,4
        printLine();

        // -> 不满足直接退出
        Observable.just(2, 3, 4, 5, 6, 7)
                //当不满足这个条件 会发送结束 不会包括这个值
                .takeWhile(integer -> integer > 3)
                .subscribe(o -> System.out.print(o + "t"));//2,3
    }
}
