package com.fast.rxjava2.operator.create;

import com.fast.rxjava2.BaseRunClass;

import io.reactivex.Observable;

/**
 * @author bowen.yan
 * @date 2018-09-09
 */
public class Misc extends BaseRunClass {
    public static void main(String[] args) {
        // 下列方法一般用于测试使用

//<-- empty()  -->
// 该方法创建的被观察者对象发送事件的特点：仅发送Complete事件，直接通知完成
        Observable observable1 = Observable.empty();
        observable1.subscribe(data -> {
            System.out.println("observable1 -> " + data);
        });
// 即观察者接收后会直接调用onCompleted（）

//<-- error()  -->
// 该方法创建的被观察者对象发送事件的特点：仅发送Error事件，直接通知异常
// 可自定义异常
        Observable observable2 = Observable.error(new RuntimeException());
//        observable2.subscribe(data -> {
//            System.out.println("observable2 -> " + data);
//        });

// 即观察者接收后会直接调用onError（）

//                <-- never()  -->
// 该方法创建的被观察者对象发送事件的特点：不发送任何事件
        Observable observable3 = Observable.never();
        observable3.subscribe(data -> {
            System.out.println("observable3 -> " + data);
        });

// 即观察者接收后什么都不调用

    }
}
