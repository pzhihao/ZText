package pzhihao.com.ztext;

import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

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
import me.yokeyword.fragmentation.SupportActivity;
import okhttp3.ResponseBody;
import pzhihao.com.ztext.constant.Constant;

import pzhihao.com.ztext.fragment.BlankFragment;
import pzhihao.com.ztext.service.DownloadService;
import pzhihao.com.ztext.service.WeatherService;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;



public class MainActivity extends SupportActivity implements BlankFragment.OnBlankFragmentInteractionListener{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        if (savedInstanceState == null){
            loadRootFragment(R.id.fl_content, BlankFragment.newInstance("111","2222"));
        }


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


        //单文件下载
        /*FileDownloader.getImpl().create("http://dldir1.qq.com/qqfile/qq/QQ8.7/19113/QQ8.7.exe")
                .setPath(Environment.getExternalStorageDirectory()+"/file/QQ.exe")
                .setListener(new FileDownloadListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {

                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        Log.v("Main","已下载："+soFarBytes+"/总大小："+totalBytes);
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {

                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {

                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {

                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {

                    }
                }).start();*/


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
