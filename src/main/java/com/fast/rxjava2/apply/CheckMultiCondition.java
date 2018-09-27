package com.fast.rxjava2.apply;

import com.fast.rxjava2.BaseRunClass;

import org.apache.commons.lang3.StringUtils;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function3;

/**
 * @author bowen.yan
 * @date 2018-09-17
 */
public class CheckMultiCondition extends BaseRunClass {
    public static void main(String[] args) {
//        CountDownLatch countDownLatch = new CountDownLatch(3);
//        Observable.interval(1, 2, TimeUnit.SECONDS)
//                .subscribe(new Consumer<Long>() {
//                    @Override
//                    public void accept(Long aLong) throws Exception {
//                        System.out.println(String.format("-------------->第%s次运行", aLong));
//                        checkValid();
//                        countDownLatch.countDown();
//                    }
//                });
//        await(countDownLatch);
        checkValid();
    }

    public static void checkValid() {
        /*
         * 步骤1：设置控件变量 & 绑定
         **/
        String name = "张三";
        String age = "22";
        String job = "软件开发工程师";

        /*
         * 步骤2：为每个EditText设置被观察者，用于发送监听事件
         * 说明：
         * 1. 此处采用了RxBinding：RxTextView.textChanges(name) = 对对控件数据变更进行监听（功能类似TextWatcher），需要引入依赖：compile 'com.jakewharton.rxbinding2:rxbinding:2.0.0'
         * 2. 传入EditText控件，点击任1个EditText撰写时，都会发送数据事件 = Function3（）的返回值（下面会详细说明）
         * 3. 采用skip(1)原因：跳过 一开始EditText无任何输入时的空值
         **/
        Observable<String> nameObservable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext(name);
                emitter.onComplete();
            }
        });
        Observable<String> ageObservable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext(age);
                emitter.onComplete();
            }
        });
        Observable<String> jobObservable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext(job);
                emitter.onComplete();
            }
        });

        /*
         * 步骤3：通过combineLatest（）合并事件 & 联合判断
         **/
        Observable.combineLatest(nameObservable, ageObservable, jobObservable, new Function3<String, String, String, Boolean>
                () {
            @Override
            public Boolean apply(@NonNull String nameStr, @NonNull String ageStr, @NonNull String jobStr) throws Exception {
                System.out.println(String.format("combineLatest.apply() -> data1:%s, data2:%s, data3:%s", nameStr, ageStr, jobStr));

                //步骤4：规定表单信息输入不能为空
                // 1. 姓名信息
                boolean isUserNameValid = StringUtils.isNotEmpty(nameStr);
                // 2. 年龄信息
                boolean isUserAgeValid = StringUtils.isNotEmpty(ageStr) && "22".compareTo(ageStr) >= 0;
                // 3. 职业信息
                boolean isUserJobValid = StringUtils.isNotEmpty(jobStr);
                //步骤5：返回信息 = 联合判断，即3个信息同时已填写，"提交按钮"才可点击
                return isUserNameValid && isUserAgeValid && isUserJobValid;
            }
        }).subscribe(new Observer<Boolean>() { // 此处已经是主线程在操作，因此onNext()方法执行完成后，能同步返回结果，不需要countdown来同步
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Boolean valid) {
                //步骤6：返回结果 & 设置按钮可点击样式
                logger.info("用户信息校验结果 -> {}", valid ? "通过" : "未通过");
                //      countDownLatch.countDown();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        //await(countDownLatch);
    }
}

/*
new Consumer<Boolean>() {
            @Override
            public void accept(Boolean valid) throws Exception {
                //步骤6：返回结果 & 设置按钮可点击样式
                logger.info("用户信息校验结果 -> {}", valid ? "通过" : "未通过");
                //      countDownLatch.countDown();
            }
        }* */
