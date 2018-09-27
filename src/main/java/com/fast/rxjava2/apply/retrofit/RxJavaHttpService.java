package com.fast.rxjava2.apply.retrofit;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.fast.rxjava2.BaseRunClass;
import com.fast.rxjava2.apply.retrofit.model.Translation;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author bowen.yan
 * @date 2018-09-18
 */
public class RxJavaHttpService extends BaseRunClass {
    public static void main(String[] args) {

        CountDownLatch countDownLatch = new CountDownLatch(10);

        // a. 创建Retrofit对象
        //步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // ------>支持RxJava
                .build();

        /*
         * 步骤1：采用interval（）延迟发送
         * 注：此处主要展示无限次轮询，若要实现有限次轮询，仅需将interval（）改成intervalRange（）即可
         **/
        Observable.interval(2, 1, TimeUnit.SECONDS)
                // 参数说明：
                // 参数1 = 第1次延迟时间；
                // 参数2 = 间隔时间数字；
                // 参数3 = 时间单位；
                // 该例子发送的事件特点：延迟2s后发送事件，每隔1秒产生1个数字（从0开始递增1，无限个）

                /*
                 * 步骤2：每次发送数字前发送1次网络请求（doOnNext（）在执行Next事件前调用）
                 * 即每隔1秒产生1个数字前，就发送1次网络请求，从而实现轮询需求
                 **/
                // 默认线程类型为:computation线程
                //.observeOn(Schedulers.computation())
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long integer) throws Exception {
                        System.out.println(String.format("%s -> 第%s次轮询", Thread.currentThread(), integer));

                        /*
                         * 步骤3：通过Retrofit发送网络请求
                         **/

                        // b. 创建 网络请求接口 的实例
                        RequestInterface request = retrofit.create(RequestInterface.class);

                        // c. 采用Observable<...>形式 对 网络请求 进行封装
                        Observable<Translation> observable = request.query();
                        // d. 通过线程切换发送网络请求
                        observable.subscribeOn(Schedulers.io())               // 切换到IO线程进行网络请求
                                //.observeOn(AndroidSchedulers.mainThread())  // 切换回到主线程 处理请求结果
                                .subscribe(new Observer<Translation>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {
                                    }

                                    @Override
                                    public void onNext(Translation result) {
                                        // e.接收服务器返回的数据
                                        // Translation{status=1, content=content{from='en-EU', to='zh-CN', vendor='ciba', out='示例', errNo=0}}
                                        // Translation{status=1, content=content{from='en-EU', to='zh-CN', vendor='ciba', out='示例', errNo=0}}
                                        // Translation{status=1, content=content{from='en-EU', to='zh-CN', vendor='ciba', out='示例', errNo=0}}
                                        // Translation{status=1, content=content{from='en-EU', to='zh-CN', vendor='ciba', out='示例', errNo=0}}
                                        System.out.println(String.format("%s -> %s", Thread.currentThread(), "result ------> " + result));
                                        countDownLatch.countDown();
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        logger.info("请求失败");
                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });

                    }
                })
                .observeOn(Schedulers.newThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long value) {

                        System.out.println(String.format("%s -> %s", Thread.currentThread(), "返回的结果 ------> " + value));
                        // 返回的结果 ------> 0
                        // 返回的结果 ------> 1
                        // 返回的结果 ------> 2
                        // 返回的结果 ------> 3
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

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //sleep(5);
    }
}
