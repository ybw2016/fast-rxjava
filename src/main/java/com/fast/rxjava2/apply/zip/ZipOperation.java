package com.fast.rxjava2.apply.zip;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.fast.rxjava2.BaseRunClass;
import com.fast.rxjava2.apply.retrofit.RequestInterface;
import com.fast.rxjava2.apply.retrofit.model.LoginResp;
import com.fast.rxjava2.apply.retrofit.model.RegisterResp;

import java.util.concurrent.CountDownLatch;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author bowen.yan
 * @date 2018-09-17
 */
public class ZipOperation extends BaseRunClass {
    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
                .build();

        // 步骤2：创建 网络请求接口 的实例
        RequestInterface request = retrofit.create(RequestInterface.class);

        // 步骤3：采用Observable<...>形式 对 网络请求 进行封装
        //Observable<RegisterResp> observable1 = request.register().subscribeOn(Schedulers.io());
        //Observable<LoginResp> observable2 = request.login().subscribeOn(Schedulers.io());
        Observable<RegisterResp> observable1 = request.register();
        Observable<LoginResp> observable2 = request.login();

        // 步骤4：通过使用Zip（）对两个网络请求进行合并再发送
        Observable.zip(observable1, observable2,
                new BiFunction<RegisterResp, LoginResp, String>() {
                    // 注：创建BiFunction对象传入的第3个参数 = 合并后数据的数据类型
                    @Override
                    public String apply(RegisterResp RegisterResp,
                                        LoginResp LoginResp) throws Exception {
                        return RegisterResp.toString() + " & " + LoginResp.toString();
                    }
                })//.observeOn(AndroidSchedulers.mainThread()) // 在主线程接收 & 处理数据
                .subscribe(new Consumer<String>() {
                    // 成功返回数据时调用
                    @Override
                    public void accept(String returnResult) throws Exception {
                        // 结合显示2个网络请求的数据结果
                        logger.info("最终接收到的数据是：" + returnResult);
                        countDownLatch.countDown();
                    }
                }, new Consumer<Throwable>() {
                    // 网络请求错误时调用
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        System.out.println("登录失败");
                    }
                });
        await(countDownLatch);
    }
}
