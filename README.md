# fast-rxjava
快速入门 - RxJava2

### 内容说明
* 本示例大量借鉴原著，请参考：
1. https://www.jianshu.com/p/cd984dd5aae8
2. http://www.cnblogs.com/smartbetter/p/7723773.html
3. http://www.jcodecraeer.com/a/chengxusheji/java/2017/0731/8315.html


### -----------------------------常用操作符-----------------------------
|操作符类型       |API描述                                                         |API备注
| ------ | ------ | ------ |
|功能            |功能描述                                                         |方法
|创建操作	        |create、defer、from、just、start、repeat、range                   |用于创建Observable的操作符
|变换操作	        |buffer、window、map、flatMap、groupBy、scan                       |用于对Observable发射的数据进行变换
|过滤操作	        |debounce、distinct、filter、sample、skip、take                    |用于从Observable发射的数据中进行选择
|组合操作	        |and、startWith、join、merge、switch、zip                          |用于将多个Observable组合成一个单一的Observable
|异常处理	        |catch、retry                                                     |用于从错误通知中恢复
|辅助操作	        |delay、do、observeOn、subscribeOn、subscribe                      |用于处理Observable的操作符
|条件和布尔操作	|all、amb、contains、skipUntil、takeUntil							  |
|算法和聚合操作	|average、concat、count、max、min、sum、reduce					  |
|异步操作		    |start、toAsync、startFuture、fromAction、fromCallable、runAsync	  |
|连接操作		    |connect、publish、share、refcount、replay						  |
|转换操作		    |toFuture、toList、toIterable、toMap、toMultiMap					  |
|阻塞操作		    |forEach、first、last、mostRecent、next、single					  |
|字符串操作		|byLine、decode、encode、from、join、split、stringConcat			  |
