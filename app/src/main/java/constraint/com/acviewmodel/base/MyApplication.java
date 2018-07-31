package constraint.com.acviewmodel.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by Innovify on 31/7/18.
 */
public class MyApplication extends Application {
    ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent.create();
    }

    public static ApplicationComponent getApplicationComponent(Context context) {
        return ((MyApplication) context.getApplicationContext()).applicationComponent;
    }
}
