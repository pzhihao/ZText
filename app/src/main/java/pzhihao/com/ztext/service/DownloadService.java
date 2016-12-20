package pzhihao.com.ztext.service;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Administrator on 2016/12/20.
 */

public interface DownloadService {
    @GET
    Flowable<ResponseBody> download(@Url String url);
}
