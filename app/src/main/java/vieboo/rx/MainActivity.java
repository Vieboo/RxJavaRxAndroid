package vieboo.rx;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.observers.SubscriberCompletableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    String TAG = "RxJava";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        Log.e(TAG, Thread.currentThread().getName());

//        testRxJava();
//        testRxJava2();
//        testRxJava3();

//        testRxJava4();


//        testRxJava5();
//        testRxJava6();
//        testRxJava7();


//        testRxJava9();
//        testRxJava10();
//        testRxJava11();
//        testRxJava12();
//        testRxJava13();
//        testRxJava14();
//        testRxJava15();
//        testRxJava16();
//        testRxJava17();
//        testRxJava18();
        testRxJava19();
    }


    private void testRxJava() {
        //创建上游 Observable
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> observableEmitter) throws Exception {
                observableEmitter.onNext(1);
                observableEmitter.onNext(2);
                observableEmitter.onNext(3);
                observableEmitter.onComplete();
            }
        });

        //创建下游 Observer
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable disposable) {
                Log.e(TAG, "---->onSubscribe: " + disposable);
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                Log.e(TAG, "---->onNext: " + integer);
            }

            @Override
            public void onError(@NonNull Throwable throwable) {
                Log.e(TAG, "---->onError: " + throwable);
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "---->onComplete: ");
            }
        };

        //建立连接
        observable.subscribe(observer);
    }

    private void testRxJava2() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> observableEmitter) throws Exception {
                observableEmitter.onNext(4);
                observableEmitter.onNext(5);
                observableEmitter.onNext(6);
                observableEmitter.onComplete();
            }
        }).subscribe(new Observer<Integer>() {
            long time;
            @Override
            public void onSubscribe(@NonNull Disposable disposable) {
                Log.e(TAG, "~~~~>onSubscribe: " + disposable);
                time = System.currentTimeMillis();
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                Log.e(TAG, "~~~~>onNext: " + integer);
            }

            @Override
            public void onError(@NonNull Throwable throwable) {
                Log.e(TAG, "~~~~>onError: " + throwable);
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "~~~~>onComplete: " + (System.currentTimeMillis() - time));
            }
        });
    }



    private void testRxJava3() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> observableEmitter) throws Exception {
                observableEmitter.onNext(7);
                observableEmitter.onNext(8);
                observableEmitter.onNext(9);
                observableEmitter.onComplete();
            }
        }).subscribe(new Observer<Integer>() {

            Disposable mDisposable;

            @Override
            public void onSubscribe(@NonNull Disposable disposable) {
                Log.e(TAG, "---->onSubscribe: "+ disposable );
                mDisposable = disposable;
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                Log.e(TAG, "---->onNext: " + integer);
                if(8 == integer) {
                    mDisposable.dispose();
                }
                Log.e(TAG, "---->mDisposable: " + mDisposable.isDisposed() );
            }

            @Override
            public void onError(@NonNull Throwable throwable) {
                Log.e(TAG, "---->onError: " + throwable);
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "---->onComplete: ");
            }
        });
    }


    private void testRxJava4() {
        Observable<Integer> mObservable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> observableEmitter) throws Exception {
                Log.e(TAG, "subscribe: " + Thread.currentThread().getName());
                observableEmitter.onNext(1);
            }
        });
        Consumer<Integer> mConsumer = new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.e(TAG, "accept: " + integer + "Thread: " + Thread.currentThread().getName());
            }
        };
//        mObservable.subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(mConsumer);
        mObservable.subscribe(mConsumer);
    }



    private void testRxJava5() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> observableEmitter) throws Exception {
                observableEmitter.onNext(10086);
                observableEmitter.onComplete();
            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(@NonNull Integer integer) throws Exception {
                return "map--->" + integer;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e(TAG, "accept: " + s);
            }
        });
    }


    private void testRxJava6() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> observableEmitter) throws Exception {
                observableEmitter.onNext(11);
                observableEmitter.onNext(12);
                observableEmitter.onNext(13);
                observableEmitter.onNext(14);
                observableEmitter.onComplete();
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(@NonNull final Integer integer) throws Exception {
                return Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<String> observableEmitter) throws Exception {
                        observableEmitter.onNext("flatMap-->" + integer);
                    }
                });
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e(TAG, "accept: " + s);
            }
        });
    }


    private void testRxJava7() {
        Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> observableEmitter) throws Exception {
                observableEmitter.onNext(1);
                observableEmitter.onNext(2);
                observableEmitter.onNext(3);
                observableEmitter.onNext(4);
            }
        });

        Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> observableEmitter) throws Exception {
                observableEmitter.onNext("A");
                observableEmitter.onNext("B");
                observableEmitter.onNext("C");
            }
        });

        Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(@NonNull Integer integer, @NonNull String s) throws Exception {
                return s + "~" + integer;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e(TAG, "accept: " + s);
            }
        });
    }

    private void testRxJava8() {
//        Flowable<Integer> flowable = Flowable.create(new FlowableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(@NonNull FlowableEmitter<Integer> flowableEmitter) throws Exception {
//                flowableEmitter.onNext(1);
//                flowableEmitter.onNext(2);
//                flowableEmitter.onNext(3);
//                flowableEmitter.onNext(4);
//                flowableEmitter.onComplete();
//            }
//        }, BackpressureStrategy.ERROR);
//
//        flowable.subscribe();
    }

    private void testRxJava9() {
        String str[] = {"A", "B", "C", "D"};
//        Observable<String> observable = Observable.just("String", "Vieboo", "Sjzx");
        Observable observable = Observable.fromArray(str);
        observable.subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e(TAG, "accept: -->" + s);
            }
        });
    }

    /**
     * 延迟创建
     * defer()
     * 直到有观察者（Observer）订阅时，才动态创建被观察对象（Observable）和发送事件
     * 1.通过Observable工厂方法创建被观察者对象（Observable）
     * 2.每次订阅后都会得到一个刚创建的新的Observable对象，这可以确保Observable对象里的数据是最新的
     *
     * 应用场景
     * 动态创建被观察者对象（Observable） & 获取最新的Observable对象数据
     */
    int i = 10;
    private void testRxJava10() {
        Observable<Integer> observable = Observable.defer(new Callable<ObservableSource<? extends Integer>>() {
            @Override
            public ObservableSource call() throws Exception {
                return Observable.just(i);
            }
        });
        i = 11;
        observable.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer i) throws Exception {
                Log.e(TAG, "accept: --->" + i);
            }
        });
    }

    /**
     * 延迟发送
     * timer()
     * 1.快速创建1个被观察者对象（Observable）
     * 2.发送事件的特点：延迟指定时间后，发送1个数值0（Long类型）
     * 本质：延迟指定时间后，调用一次 onNext(0)
     *
     * 应用场景
     * 延迟指定事件，发送一个0，一般用于检测
     */
    private void testRxJava11() {
        //参数：延迟的时间   时间单位
        Observable.timer(2, TimeUnit.SECONDS).subscribe(new Observer<Long>() {
            long time;
            @Override
            public void onSubscribe(@NonNull Disposable disposable) {
                Log.e(TAG, "onSubscribe: ");
                time = System.currentTimeMillis();
            }

            @Override
            public void onNext(@NonNull Long aLong) {
                Log.e(TAG, "onNext: --->" + aLong);
            }

            @Override
            public void onError(@NonNull Throwable throwable) {
                Log.e(TAG, "onError: " );
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete: -->" + (System.currentTimeMillis() - time));
            }
        });
    }

    /**
     * 延迟循环发送
     * interval()
     * 1.快速创建1个被观察者对象（Observable）
     * 2.发送事件的特点：每隔指定时间 就发送 事件
     * 发送的事件序列 = 从0开始、无限递增1的的整数序列
     */
    private void testRxJava12() {
        //参数：第一次延迟的时间   间隔的时间   时间单位
        Observable.interval(5, 2, TimeUnit.SECONDS).subscribe(new Observer<Long>() {
            long time;
            @Override
            public void onSubscribe(@NonNull Disposable disposable) {
                Log.e(TAG, "onSubscribe: ");
                time = System.currentTimeMillis();
            }

            @Override
            public void onNext(@NonNull Long aLong) {
                Log.e(TAG, "onNext: --->" + aLong + "---->" + (System.currentTimeMillis() - time));
                time = System.currentTimeMillis();
            }

            @Override
            public void onError(@NonNull Throwable throwable) {

            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete: -->" + (System.currentTimeMillis() - time));
            }
        });
    }

    /**
     * 有数量的延迟循环发送
     * intervalRange()
     * 1.快速创建1个被观察者对象（Observable）
     * 2.发送事件的特点：每隔指定时间 就发送 事件，可指定发送的数据的数量
     * 发送的事件序列 = 从0开始、无限递增1的的整数序列
     * 作用类似于interval（），但可指定发送的数据的数量
     */
    private void testRxJava13() {
        //参数：事件序列起始点    事件数量    第一次延迟时间     间隔时间    时间单位
        Observable.intervalRange(5, 10, 5, 2, TimeUnit.SECONDS).subscribe(new Observer<Long>() {
            long time;
            @Override
            public void onSubscribe(@NonNull Disposable disposable) {
                Log.e(TAG, "onSubscribe: ");
                time = System.currentTimeMillis();
            }

            @Override
            public void onNext(@NonNull Long aLong) {
                Log.e(TAG, "onNext: --->" + aLong + "---->" + (System.currentTimeMillis() - time));
                time = System.currentTimeMillis();
            }

            @Override
            public void onError(@NonNull Throwable throwable) {

            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete: -->" + (System.currentTimeMillis() - time));
            }
        });
    }

    /**
     * 区间循环发送
     * range()
     * 1.快速创建1个被观察者对象（Observable）
     * 2.发送事件的特点：连续发送 1个事件序列，可指定范围
     * 发送的事件序列 = 从0开始、无限递增1的的整数序列
     * 作用类似于intervalRange（），但区别在于：无延迟发送事件
     */
    private void testRxJava14() {
        //参数：事件起点   事件总数
        Observable.range(0, 10).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable disposable) {
                Log.e(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                Log.e(TAG, "onNext: --->" + integer);
            }

            @Override
            public void onError(@NonNull Throwable throwable) {

            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete: ");
            }
        });
    }

    /**
     * 区间循环发送
     * rangeLong()
     * 同range()方法，区别支持数据类型为long
     */
    private void testRxJava15() {
        //参数：事件起点   事件总数
        Observable.rangeLong(0, 10).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(@NonNull Disposable disposable) {
                Log.e(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(@NonNull Long aLong) {
                Log.e(TAG, "onNext: --->" + aLong);
            }

            @Override
            public void onError(@NonNull Throwable throwable) {

            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete: ");
            }
        });
    }


    /**
     * map()
     * 对 被观察者发送的每1个事件都通过 指定的函数 处理，从而变换成另外一种事件即，
     * 将被观察者发送的事件转换为任意的类型事件。
     * 应用场景
     * 数据类型转换
     */
    private void testRxJava16() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> observableEmitter) throws Exception {
                observableEmitter.onNext(1);
                observableEmitter.onNext(2);
                observableEmitter.onNext(3);
            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(@NonNull Integer integer) throws Exception {
                return "Convert->" + integer;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e(TAG, "accept: --->" + s);
            }
        });

    }

    /**
     * flatMap()
     * 将被观察者发送的事件序列进行 拆分 & 单独转换，再合并成一个新的事件序列，最后再进行发送
     * 原理
     * 1.为事件序列中每个事件都创建一个 Observable 对象；
     * 2.将对每个 原始事件 转换后的 新事件 都放入到对应 Observable对象；
     * 3.将新建的每个Observable 都合并到一个 新建的、总的Observable 对象；
     * 4.新建的、总的Observable 对象 将 新合并的事件序列 发送给观察者（Observer）
     * 应用场景
     * 无序的将被观察者发送的整个事件序列进行变换
     */
    private void testRxJava17() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> observableEmitter) throws Exception {
                observableEmitter.onNext(1);
                observableEmitter.onNext(2);
                observableEmitter.onNext(3);
                observableEmitter.onNext(4);
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(@NonNull Integer integer) throws Exception {
                final List<String> list = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    list.add("我是事件 " + integer + "拆分后的子事件" + i);
                    // 通过flatMap中将被观察者生产的事件序列先进行拆分，再将每个事件转换为一个新的发送三个String事件
                    // 最终合并，再发送给被观察者
                }
                return Observable.fromIterable(list);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e(TAG, "accept: -->" + s);
            }
        });
    }


    /**
     * concatMap()
     * 类似flatMap()，区别在于：拆分 & 重新合并生成的事件序列 的顺序 = 被观察者旧序列生产的顺序
     * 应用场景
     * 有序的将被观察者发送的整个事件序列进行变换
     */
    private void testRxJava18() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onNext(4);
            }
        }).concatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(@NonNull Integer integer) throws Exception {
                final List<String> list = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    list.add("我是事件 " + integer + "拆分后的子事件" + i);
                    // 通过flatMap中将被观察者生产的事件序列先进行拆分，再将每个事件转换为一个新的发送三个String事件
                    // 最终合并，再发送给被观察者
                }
                return Observable.fromIterable(list);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e(TAG, "accept: -->" + s);
            }
        });
    }


    /**
     * buffer()
     * 缓存被观察者发送的事件
     *
     */
    private void testRxJava19() {
        Observable.just(1, 2, 3, 4, 5)
                //参数：缓存区大小      每次重新获取事件的数量
                .buffer(2, 1)
                .subscribe(new Observer<List<Integer>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.e(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(@NonNull List<Integer> integers) {
                        Log.e(TAG, " 缓存区里的事件数量 = " +  integers.size());
                        for (Integer i : integers) {
                            Log.e(TAG, "onNext: ---->" + i);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError: ");
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete: ");
                    }
                });
    }
}
