package pzhihao.com.ztext;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;


import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import pzhihao.com.ztext.constant.Constant;

import pzhihao.com.ztext.service.WeatherService;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Flowable<String> flowable=Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> e) throws Exception {
                e.onNext("Hello RXJava2");
                e.onComplete();
            }
        }, BackpressureStrategy.BUFFER);

        Subscriber<String> subscriber=new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription s) {
                Log.v("Main","onSubscribe");
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(String s) {
                Log.v("Main",s);
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {
                Log.v("Main","onComplete");
            }
        };

        flowable.subscribe(subscriber);*/

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Constant.BAIDUBASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        WeatherService weatherService = retrofit.create(WeatherService.class);
        Flowable<String> chengdu = weatherService.getweather("成都");

        Subscriber<String> subscriber=new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription s) {
                Log.v("Main","开始请求");
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(String s) {
                Log.v("Main","数据："+s);
            }

            @Override
            public void onError(Throwable t) {
                    Log.v("Main","错误："+t.getMessage()+t.toString());
            }

            @Override
            public void onComplete() {
                Log.v("Main","数据完成");
            }
        };

        chengdu.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);


        Flowable<String> fs=Flowable.just("Hello Rx2 2");

        Consumer<String> consumer  = new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.v("Main","带一个参数"+s);
            }
        };

        fs.map(new Function<String, String>() {
            @Override
            public String apply(String s) throws Exception {
                return s+"哈哈";
            }
        }).subscribe(consumer);



    }
}
