package vn.tale.architecture.common.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import vn.tale.architecture.common.dagger.DaggerComponentFactory;
import vn.tale.architecture.common.dagger.DaggerLifecycleDelegate;

/**
 * Created by Giang Nguyen on 2/22/17.
 */

public abstract class BaseActivity<DaggerComponent> extends AppCompatActivity {

  private DaggerLifecycleDelegate<DaggerComponent> daggerLifecycleDelegate;

  private Unbinder unbinder;

  protected abstract DaggerComponentFactory<DaggerComponent> daggerComponentFactory();

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    daggerLifecycleDelegate = new DaggerLifecycleDelegate<>(daggerComponentFactory());
    daggerLifecycleDelegate.onCreate(this);
  }

  protected DaggerComponent daggerComponent() {
    return daggerLifecycleDelegate.daggerComponent();
  }

  @Override public Object onRetainCustomNonConfigurationInstance() {
    return daggerLifecycleDelegate.onRetainCustomNonConfigurationInstance();
  }

  protected void bindViews(Activity activity) {
    unbinder = ButterKnife.bind(activity);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    unbinder.unbind();
  }
}
