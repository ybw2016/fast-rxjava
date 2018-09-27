package com.fast.rxjava2.apply;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.fast.rxjava2.BaseRunClass;
import com.fast.rxjava2.apply.retrofit.RequestInterface;
import com.fast.rxjava2.apply.retrofit.model.Translation;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author bowen.yan
 * @date 2018-09-17
 */
public class NetworkRetry extends BaseRunClass {
    public static void main(String[] args) {
        //CountDownLatch countDownLatch = new CountDownLatch(1);
        NumCounter numCounter = new NumCounter(2, 0, 0);

        //步骤4：创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fy.icibaS.com/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // ------>支持RxJava
                .build();

        // 步骤5：创建 网络请求接口 的实例
        RequestInterface request = retrofit.create(RequestInterface.class);

        // 步骤6：采用Observable<...>形式 对 网络请求 进行封装
        Observable<Translation> observable = request.query();

        // 步骤7：发送网络请求
        // 步骤4：发送网络请求 & 通过retryWhen（）进行重试
        // 注：主要异常才会回调retryWhen（）进行重试
        observable.retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(@NonNull Observable<Throwable> throwableObservable) throws Exception {
                // 参数Observable<Throwable>中的泛型 = 上游操作符抛出的异常，可通过该条件来判断异常的类型
                return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@NonNull Throwable throwable) throws Exception {
                        // 输出异常信息
                        logger.info("发生异常 = " + throwable.toString());

                        /**
                         * 需求1：根据异常类型选择是否重试
                         * 即，当发生的异常 = 网络异常 = IO异常 才选择重试
                         */
                        if (throwable instanceof IOException) {
                            logger.info("属于IO异常，需重试");

                            // 设置变量
                            // 可重试次数
                            int maxConnectCount = numCounter.getMaxConnectCount();
                            // 当前已重试次数
                            int currentRetryCount = numCounter.getCurrentRetryCount();
                            // 重试等待时间
                            int waitRetryTime = numCounter.getWaitRetryTime();

                            /**
                             * 需求2：限制重试次数
                             * 即，当已重试次数 < 设置的重试次数，才选择重试
                             */
                            if (currentRetryCount < maxConnectCount) {
                                // 记录重试次数
                                numCounter.setCurrentRetryCount(currentRetryCount + 1);
                                logger.info("重试次数 = " + numCounter.getCurrentRetryCount());

                                /**
                                 * 需求2：实现重试
                                 * 通过返回的Observable发送的事件 = Next事件，从而使得retryWhen（）重订阅，最终实现重试功能
                                 *
                                 * 需求3：延迟1段时间再重试
                                 * 采用delay操作符 = 延迟一段时间发送，以实现重试间隔设置
                                 *
                                 * 需求4：遇到的异常越多，时间越长
                                 * 在delay操作符的等待时间内设置 = 每重试1次，增多延迟重试时间1s
                                 */
                                // 设置等待时间
                                numCounter.setWaitRetryTime(1000 + currentRetryCount * 1000);
                                logger.info("等待时间 =" + numCounter.getWaitRetryTime());
                                // 延迟一段时间后，进行1次http请求重试
                                return Observable.just(1).delay(numCounter.getWaitRetryTime(), TimeUnit.MILLISECONDS);
                            } else {
                                // 若重试次数已 > 设置重试次数，则不重试
                                // 通过发送error来停止重试（可在观察者的onError（）中获取信息）
                                return Observable.error(new Throwable("重试次数已超过设置次数 = " + currentRetryCount + "，即 不再重试"));
                            }
                        }
                        // 若发生的异常不属于I/O异常，则不重试
                        // 通过返回的Observable发送的事件 = Error事件 实现（可在观察者的onError（）中获取信息）
                        else {
                            return Observable.error(new Throwable("发生了非网络异常（非I/O异常）"));
                        }
                    }
                });
            }
        })//.subscribeOn(Schedulers.io())               // 切换到IO线程进行网络请求
                //.observeOn(AndroidSchedulers.mainThread())  // 切换回到主线程 处理请求结果
                .blockingSubscribe(new Observer<Translation>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Translation result) {
                        // 接收服务器返回的数据
                        logger.info("发送成功");
                        System.out.println(String.format("%s -> %s", Thread.currentThread(), "result ------> " + result));
                    }

                    @Override
                    public void onError(Throwable e) {
                        // 获取停止重试的信息
                        logger.info(e.toString());
                        //countDownLatch.countDown();
                    }

                    @Override
                    public void onComplete() {
                        //countDownLatch.countDown();
                    }
                });

        //await(countDownLatch);
    }

    static class NumCounter {
        private int maxConnectCount;
        // 当前已重试次数
        private int currentRetryCount;
        // 重试等待时间
        private int waitRetryTime;

        public NumCounter(int maxConnectCount, int currentRetryCount, int waitRetryTime) {
            this.maxConnectCount = maxConnectCount;
            this.currentRetryCount = currentRetryCount;
            this.waitRetryTime = waitRetryTime;
        }

        public int getMaxConnectCount() {
            return maxConnectCount;
        }

        public void setMaxConnectCount(int maxConnectCount) {
            this.maxConnectCount = maxConnectCount;
        }

        public int getCurrentRetryCount() {
            return currentRetryCount;
        }

        public void setCurrentRetryCount(int currentRetryCount) {
            this.currentRetryCount = currentRetryCount;
        }

        public int getWaitRetryTime() {
            return waitRetryTime;
        }

        public void setWaitRetryTime(int waitRetryTime) {
            this.waitRetryTime = waitRetryTime;
        }

        @Override
        public String toString() {
            return "NumCounter{" +
                    "maxConnectCount=" + maxConnectCount +
                    ", currentRetryCount=" + currentRetryCount +
                    ", waitRetryTime=" + waitRetryTime +
                    '}';
        }
    }
}
