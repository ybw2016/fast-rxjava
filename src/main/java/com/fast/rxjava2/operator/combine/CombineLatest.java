package com.fast.rxjava2.operator.combine;

import com.fast.rxjava2.BaseRunClass;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;

/**
 * @author bowen.yan
 * @date 2018-09-09
 */
public class CombineLatest extends BaseRunClass {
    public static void main(String[] args) {
        Observable.combineLatest(
                // DIY 取的最后的值，如：1L, 2L, 3L, 4L -> 4L
                //Observable.just(1L, 2L, 3L), // 第1个发送数据事件的Observable
                Observable.intervalRange(11, 4, 1, 1, TimeUnit.SECONDS), //
                //Observable.just(1L, 2L, 3L, 4L), // 第1个发送数据事件的Observable
                Observable.intervalRange(11, 4, 1, 1, TimeUnit.SECONDS), //
                // 第2个发送数据事件的Observable：从0开始发送、共发送3个数据、第1次事件延迟发送时间 = 1s、间隔时间 = 1s
                new BiFunction<Long, Long, Long>() {
                    @Override
                    public Long apply(Long o1, Long o2) throws Exception {
                        // o1 = 第1个Observable发送的最新（最后）1个数据
                        // o2 = 第2个Observable发送的每1个数据
                        logger.info("合并的数据是： " + o1 + " " + o2);
                        return o1 + o2;
                        // 合并的逻辑 = 相加
                        // 即第1个Observable发送的最后1个数据 与 第2个Observable发送的每1个数据进行相加
                    }
                }).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long s) throws Exception {
                logger.info("合并的结果是： " + s);
            }
        });

        sleep(3);
        /*
                [2018-09-20 22:29:39,248][INFO ][][] [com.fast.rxjava2.operator.convert.Map.apply():@27] - 合并的数据是： 3 0
                [2018-09-20 22:29:39,249][INFO ][][] [com.fast.rxjava2.operator.convert.Map.accept():@35] - 合并的结果是： 3
                [2018-09-20 22:29:40,245][INFO ][][] [com.fast.rxjava2.operator.convert.Map.apply():@27] - 合并的数据是： 3 1
                [2018-09-20 22:29:40,245][INFO ][][] [com.fast.rxjava2.operator.convert.Map.accept():@35] - 合并的结果是： 4
                [2018-09-20 22:29:41,243][INFO ][][] [com.fast.rxjava2.operator.convert.Map.apply():@27] - 合并的数据是： 3 2
                [2018-09-20 22:29:41,244][INFO ][][] [com.fast.rxjava2.operator.convert.Map.accept():@35] - 合并的结果是： 5
        */

        /*
                [2018-09-21 10:38:45,300][INFO ][][] [com.fast.rxjava2.operator.convert.Map.apply():@29] - 合并的数据是： 11 11
                [2018-09-21 10:38:45,301][INFO ][][] [com.fast.rxjava2.operator.convert.Map.accept():@37] - 合并的结果是： 22
                [2018-09-21 10:38:46,296][INFO ][][] [com.fast.rxjava2.operator.convert.Map.apply():@29] - 合并的数据是： 11 12
                [2018-09-21 10:38:46,296][INFO ][][] [com.fast.rxjava2.operator.convert.Map.accept():@37] - 合并的结果是： 23
                [2018-09-21 10:38:46,297][INFO ][][] [com.fast.rxjava2.operator.convert.Map.apply():@29] - 合并的数据是： 12 12
                [2018-09-21 10:38:46,297][INFO ][][] [com.fast.rxjava2.operator.convert.Map.accept():@37] - 合并的结果是： 24
                [2018-09-21 10:38:47,293][INFO ][][] [com.fast.rxjava2.operator.convert.Map.apply():@29] - 合并的数据是： 13 12
                [2018-09-21 10:38:47,294][INFO ][][] [com.fast.rxjava2.operator.convert.Map.accept():@37] - 合并的结果是： 25
                [2018-09-21 10:38:47,296][INFO ][][] [com.fast.rxjava2.operator.convert.Map.apply():@29] - 合并的数据是： 13 13
                [2018-09-21 10:38:47,297][INFO ][][] [com.fast.rxjava2.operator.convert.Map.accept():@37] - 合并的结果是： 26
        */
    }
}
