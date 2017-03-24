package vn.tale.architecture.common.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import vn.tale.architecture.common.mvvm.LifecycleDelegate;
import vn.tale.architecture.common.mvvm.ViewModel;

/**
 * Created by Giang Nguyen on 3/23/17.
 */

public abstract class MvvmActivity<DaggerComponent, UiModel> extends BaseActivity<DaggerComponent> {

  private LifecycleDelegate<UiModel> lifecycleDelegate;
  private CompositeDisposable disposables = new CompositeDisposable();

  protected abstract void injectDependencies();

  protected abstract ViewModel<UiModel> viewModel();

  protected void disposeOnStop(Disposable disposable) {
    disposables.add(disposable);
  }

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    injectDependencies();
    lifecycleDelegate = new LifecycleDelegate<>(viewModel());
  }

  @Override protected void onStart() {
    super.onStart();
    lifecycleDelegate.onStart();
  }

  @Override protected void onStop() {
    super.onStop();
    lifecycleDelegate.onStop(isFinishing());
    disposables.clear();
  }
}
