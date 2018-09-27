package com.fast.rxjava2.operator.combine;

import com.fast.rxjava2.BaseRunClass;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observables.GroupedObservable;

/**
 * @author bowen.yan
 * @date 2018-09-09
 */
public class GroupBy extends BaseRunClass {
    public static void main(String[] args) {
        groupByUser();
        //groupByDemo();
    }

    private static void groupByUser() {
        List<User> users = Arrays.asList(
                new User("Zhangsan", "北京"),
                new User("Lisi", "上海"),
                new User("Wangwu", "北京"),
                new User("Zhaoliu", "深圳")
        );

        users.stream().collect(Collectors.groupingBy(
                user -> user.getAddress()
        )).forEach((userKey, userList) -> {
            System.out.println(String.format("userKey:%s, userList:%s", userKey, userList));
        });
        printLine();

        Observable.create(new ObservableOnSubscribe<List<User>>() {
            @Override
            public void subscribe(ObservableEmitter<List<User>> observableEmitter) throws Exception {
                observableEmitter.onNext(users);
                observableEmitter.onComplete();
            }
        }).flatMap(new Function<List<User>, ObservableSource<User>>() {
            @Override
            public ObservableSource<User> apply(List<User> users) throws Exception {
                return Observable.fromIterable(users);
            }
        }).groupBy(new Function<User, String>() {
                       @Override
                       public String apply(User user) throws Exception {
                           return user.getAddress();
                       }
                   }, new Function<User, User>() {
                       @Override
                       public User apply(User user) throws Exception {
                           return user;
                       }
                   }
        ).subscribe(new Consumer<GroupedObservable<String, User>>() {
            @Override
            public void accept(GroupedObservable<String, User> stringUserGroupedObservable) throws Exception {
                stringUserGroupedObservable.subscribe(new Consumer<User>() {
                    @Override
                    public void accept(User user) throws Exception {
                        System.out.println(String.format("user key:%s, user group Result:%s", stringUserGroupedObservable.getKey(), user));
                    }
                });
            }
        });
    }

    private static void groupByDemo() {
        Observable.range(0, 10)
                .groupBy(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer) throws Exception {
                        return integer % 2;
                    }
                }, new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        return "(" + integer + ")";
                    }
                })
                .subscribe(new Consumer<GroupedObservable<Integer, String>>() {
                    @Override
                    public void accept(GroupedObservable<Integer, String> integerStringGroupedObservable) throws Exception {
                        integerStringGroupedObservable.subscribe(new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {
                                System.out.println(String.format("key:%s, sResult:%s", integerStringGroupedObservable.getKey(), s));
                            }
                        });
                    }
                })
        ;
/*
        key:0, sResult:(0)
        key:1, sResult:(1)
        key:0, sResult:(2)
        key:1, sResult:(3)
        key:0, sResult:(4)
        key:1, sResult:(5)
        key:0, sResult:(6)
        key:1, sResult:(7)
        key:0, sResult:(8)
        key:1, sResult:(9)
=======================================
        key:0==>value:(0)
        key:1==>value:(1)
        key:0==>value:(2)
        key:1==>value:(3)
        key:0==>value:(4)
        key:1==>value:(5)
        key:0==>value:(6)
        key:1==>value:(7)
        key:0==>value:(8)
        key:1==>value:(9)
*/
        printLine();

        Observable.range(0, 10)
                .groupBy(integer -> integer % 2, integer -> "(" + integer + ")")
                .subscribe(group -> {
                    group.subscribe(integer -> System.out.println(
                            "key:" + group.getKey() + "==>value:" + integer));
                });
    }

    static class User {
        private String username;
        private String address;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public User(String username, String address) {
            this.username = username;
            this.address = address;
        }

        @Override
        public String toString() {
            return "User{" +
                    "username='" + username + '\'' +
                    ", address='" + address + '\'' +
                    '}';
        }
    }

}
