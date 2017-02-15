package personal.edu.trading;

import android.app.Application;

/**
 * Created by Administrator on 2017/2/14 0014.
 */
public class EasyShopAppliction extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Local_preservation.init(this);
    }
}
