package com.fast.rxjava2.operator.misc;

import com.fast.rxjava2.BaseRunClass;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.subscribers.ResourceSubscriber;

/**
 * @author bowen.yan
 * @date 2018-09-20
 */
public class ResourceSubscriberTest extends BaseRunClass {
    public static void main(String[] args) {
        ResourceSubscriber<Integer> subscriber = new ResourceSubscriber<Integer>() {
            @Override
            public void onStart() {
                request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Integer t) {
                System.out.println("ResourceSubscriber: current data ------> " + t);
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
            }

            @Override
            public void onComplete() {
                System.out.println("Done");
            }
        };

        Flowable.range(1, 10).delay(1, TimeUnit.SECONDS).subscribe(subscriber);
        //subscriber.dispose();

        sleep(3);
    }
}
