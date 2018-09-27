package com.fast.rxjava2.operator.behavior.retry;

import com.fast.rxjava2.BaseRunClass;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;

/**
 * 重试，即当出现错误时，让被观察者（Observable）重新发射数据
 *
 * @author bowen.yan
 * @date 2018-09-09
 */
public class Retry extends BaseRunClass {
    public static void main(String[] args) {
//        <-- 1. retry（） -->
// 作用：出现错误时，让被观察者重新发送数据
// 注：若一直错误，则一直重新发送

//        Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
//                e.onNext(1);
//                e.onNext(2);
//                e.onError(new Exception("发生错误了"));
//                e.onNext(3);
//            }
//        })
//                .retry() // 遇到错误时，让被观察者重新发射数据（若一直错误，则一直重新发送
//                .subscribe(new Observer<Integer>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(Integer value) {
//                        logger.info("接收到了事件" + value);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        logger.info("对Error事件作出响应");
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        logger.info("对Complete事件作出响应");
//                    }
//                });


////<-- 2. retry（long time） -->
//// 作用：出现错误时，让被观察者重新发送数据（具备重试次数限制
//// 参数 = 重试次数
//        Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
//                e.onNext(1);
//                e.onNext(2);
//                e.onError(new Exception("发生错误了"));
//                e.onNext(3);
//            }
//        })
//                .retry(3) // 设置重试次数 = 3次
//                .subscribe(new Observer<Integer>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(Integer value) {
//                        logger.info("接收到了事件" + value);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        logger.info("对Error事件作出响应");
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        logger.info("对Complete事件作出响应");
//                    }
//                });
//
////<-- 3. retry（Predicate predicate） -->
//// 作用：出现错误后，判断是否需要重新发送数据（若需要重新发送& 持续遇到错误，则持续重试）
//// 参数 = 判断逻辑
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onError(new Exception("发生错误了"));
                e.onNext(3);
            }
        })
                // 拦截错误后，判断是否需要重新发送请求
                .retry(new Predicate<Throwable>() {
                    @Override
                    public boolean test(@NonNull Throwable throwable) throws Exception {
                        // 捕获异常
                        logger.info("retry错误: " + throwable.toString());

                        //返回false = 不重新重新发送数据 & 调用观察者的onError结束
                        //返回true = 重新发送请求（若持续遇到错误，就持续重新发送）
                        return true;
                    }
                })
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
//
////<--  4. retry（new BiPredicate<Integer, Throwable>） -->
//// 作用：出现错误后，判断是否需要重新发送数据（若需要重新发送 & 持续遇到错误，则持续重试
//// 参数 =  判断逻辑（传入当前重试次数 & 异常错误信息）
//        Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
//                e.onNext(1);
//                e.onNext(2);
//                e.onError(new Exception("发生错误了"));
//                e.onNext(3);
//            }
//        })
//                // 拦截错误后，判断是否需要重新发送请求
//                .retry(new BiPredicate<Integer, Throwable>() {
//                    @Override
//                    public boolean test(@NonNull Integer integer, @NonNull Throwable throwable) throws Exception {
//                        // 捕获异常
//                        logger.info("异常错误 =  " + throwable.toString());
//
//                        // 获取当前重试次数
//                        logger.info("当前重试次数 =  " + integer);
//
//                        //返回false = 不重新重新发送数据 & 调用观察者的onError结束
//                        //返回true = 重新发送请求（若持续遇到错误，就持续重新发送）
//                        return true;
//                    }
//                })
//                .subscribe(new Observer<Integer>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(Integer value) {
//                        logger.info("接收到了事件" + value);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        logger.info("对Error事件作出响应");
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        logger.info("对Complete事件作出响应");
//                    }
//                });
//
//
////<-- 5. retry（long time,Predicate predicate） -->
//// 作用：出现错误后，判断是否需要重新发送数据（具备重试次数限制
//// 参数 = 设置重试次数 & 判断逻辑
//        Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
//                e.onNext(1);
//                e.onNext(2);
//                e.onError(new Exception("发生错误了"));
//                e.onNext(3);
//            }
//        })
//                // 拦截错误后，判断是否需要重新发送请求
//                .retry(3, new Predicate<Throwable>() {
//                    @Override
//                    public boolean test(@NonNull Throwable throwable) throws Exception {
//                        // 捕获异常
//                        logger.info("retry错误: " + throwable.toString());
//
//                        //返回false = 不重新重新发送数据 & 调用观察者的onError（）结束
//                        //返回true = 重新发送请求（最多重新发送3次）
//                        return true;
//                    }
//                })
//                .subscribe(new Observer<Integer>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(Integer value) {
//                        logger.info("接收到了事件" + value);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        logger.info("对Error事件作出响应");
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        logger.info("对Complete事件作出响应");
//                    }
//                });

        sleep(3);
    }
}
