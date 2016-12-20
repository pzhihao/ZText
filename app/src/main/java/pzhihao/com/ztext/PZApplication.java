package pzhihao.com.ztext;

import android.app.Application;

import com.liulishuo.filedownloader.FileDownloader;

/**
 * Created by Administrator on 2016/12/20.
 */

public class PZApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FileDownloader.init(getApplicationContext());
    }

}
