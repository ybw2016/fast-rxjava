package com.fast.rxjava2.apply.retrofit;

import com.fast.rxjava2.apply.retrofit.model.LoginResp;
import com.fast.rxjava2.apply.retrofit.model.RegisterResp;
import com.fast.rxjava2.apply.retrofit.model.Translation;

import io.reactivex.Observable;
import retrofit2.http.GET;
/*
http://fy.iciba.com/ajax.php?a=fy&f=auto&t=auto&w=hello%20world
{
  "status": 1,
  "content": {
    "from": "en-EU",
    "to": "zh-CN",
    "out": "示例",
    "vendor": "ciba",
    "err_no": 0
  }
}
* */

/**
 * @author bowen.yan
 * @date 2018-09-18
 */
public interface RequestInterface {
    @GET("ajax.php?a=fy&f=auto&t=auto&w=hello%20world")
    Observable<Translation> query();
    // 注解里传入 网络请求 的部分URL地址
    // Retrofit把网络请求的URL分成了两部分：一部分放在Retrofit对象里，另一部分放在网络请求接口里
    // 如果接口里的url是一个完整的网址，那么放在Retrofit对象里的URL可以忽略
    // query()是接受网络请求数据的方法

    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20register")
    Observable<RegisterResp> register();

    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20login")
    Observable<LoginResp> login();
}
