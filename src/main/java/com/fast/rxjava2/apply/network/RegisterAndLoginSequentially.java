package com.fast.rxjava2.apply.network;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.fast.rxjava2.BaseRunClass;
import com.fast.rxjava2.apply.retrofit.RequestInterface;
import com.fast.rxjava2.apply.retrofit.model.LoginResp;
import com.fast.rxjava2.apply.retrofit.model.RegisterResp;

import java.util.concurrent.CountDownLatch;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author bowen.yan
 * @date 2018-09-17
 */
public class RegisterAndLoginSequentially extends BaseRunClass {
    public static void main(String[] args) {

        CountDownLatch countDownLatch = new CountDownLatch(1);
        // 定义Observable接口类型的网络请求对象
        // 步骤1：创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
                .build();

        // 步骤2：创建 网络请求接口 的实例
        RequestInterface request = retrofit.create(RequestInterface.class);

        // 步骤3：采用Observable<...>形式 对 网络请求 进行封装
        Observable<RegisterResp> observable1 = request.register();
        Observable<LoginResp> observable2 = request.login();

        observable1.subscribeOn(Schedulers.io())               // （初始被观察者）切换到IO线程进行网络请求1
                //.observeOn(AndroidSchedulers.mainThread())  // （新观察者）切换到主线程 处理网络请求1的结果
                .doOnNext(new Consumer<RegisterResp>() {
                    @Override
                    public void accept(RegisterResp result) throws Exception {
                        System.out.println(String.format("%s -> %s", Thread.currentThread(), "第1次网络请求成功"));
                        System.out.println(String.format("%s -> %s", Thread.currentThread(), "result ------> " + result));
                        // 对第1次网络请求返回的结果进行操作 = 显示翻译结果
                        //sleep(3);
                    }
                })

                .observeOn(Schedulers.io())                 // （新被观察者，同时也是新观察者）切换到IO线程去发起登录请求
                // 特别注意：因为flatMap是对初始被观察者作变换，所以对于旧被观察者，它是新观察者，所以通过observeOn切换线程
                // 但对于初始观察者，它则是新的被观察者
                .flatMap(new Function<RegisterResp, ObservableSource<LoginResp>>() { // 作变换，即作嵌套网络请求
                    @Override
                    public ObservableSource<LoginResp> apply(RegisterResp result) throws Exception {
                        // 将网络请求1转换成网络请求2，即发送网络请求2
                        return observable2;
                    }
                })

                //.observeOn(AndroidSchedulers.mainThread())  // （初始观察者）切换到主线程 处理网络请求2的结果
                .subscribe(new Consumer<LoginResp>() {
                    @Override
                    public void accept(LoginResp result) throws Exception {
                        System.out.println(String.format("%s -> %s", Thread.currentThread(), "第1次网络请求成功"));
                        System.out.println(String.format("%s -> %s", Thread.currentThread(), "result ------> " + result));
                        // 对第2次网络请求返回的结果进行操作 = 显示翻译结果
                        countDownLatch.countDown();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        System.out.println("登录失败");
                        countDownLatch.countDown();
                    }
                });

        await(countDownLatch);
    }
}
