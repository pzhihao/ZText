package pzhihao.com.ztext.service;

import io.reactivex.Flowable;

import pzhihao.com.ztext.constant.Constant;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2016/12/19.
 */

public interface WeatherService {
    @Headers({
            "apikey:"+ Constant.BAIDUKEY
    })
    @GET("weatherservice/citylist")
    Flowable<String> getweather(@Query("cityname") String cityname);
}
