package vn.tale.architecture.common.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import vn.tale.architecture.common.mvp.MvpLifecycleDelegate;
import vn.tale.architecture.common.mvp.MvpView;

/**
 * Created by Giang Nguyen on 3/20/17.
 */

public abstract class MvpActivity<DaggerComponent, ViewState, View extends MvpView<ViewState>>
    extends BaseActivity<DaggerComponent> {

  private MvpLifecycleDelegate<ViewState, View> mvpLifecycleDelegate;

  protected abstract vn.tale.architecture.common.mvp.MvpPresenter<ViewState, View> presenter();

  protected abstract View mvpView();

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mvpLifecycleDelegate = new MvpLifecycleDelegate<>(presenter(), mvpView());
  }

  @Override protected void onStart() {
    super.onStart();
    mvpLifecycleDelegate.onStart();
  }

  @Override protected void onStop() {
    super.onStop();
    mvpLifecycleDelegate.onStop(isFinishing());
  }
}
