package vn.tale.architecture;

import android.app.Application;
import android.content.Context;

/**
 * Created by Giang Nguyen on 2/21/17.
 */

public class App extends Application {

  private AppComponent appComponent;

  public static App get(Context context) {
    return ((App) context.getApplicationContext());
  }

  @Override public void onCreate() {
    super.onCreate();
    appComponent = DaggerAppComponent.builder()
        .appModule(new AppModule())
        .appSingletonModule(new AppSingletonModule())
        .build();
  }

  public AppComponent getAppComponent() {
    return appComponent;
  }
}
