package com.fast.rxjava2.apply.thread;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.fast.rxjava2.BaseRunClass;
import com.fast.rxjava2.apply.retrofit.RequestInterface;
import com.fast.rxjava2.apply.retrofit.model.Translation;

import java.util.concurrent.CountDownLatch;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author bowen.yan
 * @date 2018-09-17
 */
public class SwitchThreadByQuery extends BaseRunClass {
    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        //步骤4：创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // ------>支持RxJava
                .build();

        // 步骤5：创建 网络请求接口 的实例
        RequestInterface request = retrofit.create(RequestInterface.class);

        // 步骤6：采用Observable<...>形式 对 网络请求 进行封装
        Observable<Translation> observable = request.query();

        // 步骤7：发送网络请求
        observable.subscribeOn(Schedulers.io())               // 在IO线程进行网络请求
                //.observeOn(AndroidSchedulers.mainThread())  // 回到主线程 处理请求结果
                .observeOn(Schedulers.newThread())  // 回到主线程 处理请求结果
                .subscribe(new Observer<Translation>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        logger.info("开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(Translation result) {
                        // 步骤8：对返回的数据进行处理
                        System.out.println(String.format("%s -> %s", Thread.currentThread(), "result ------> " + result));
                        countDownLatch.countDown();
                    }

                    @Override
                    public void onError(Throwable e) {
                        logger.info("请求失败");
                    }

                    @Override
                    public void onComplete() {
                        logger.info("请求成功");
                    }
                });
        await(countDownLatch);
    }
}
