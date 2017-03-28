package vn.tale.architecture.common.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import vn.tale.architecture.common.mvvm.LifecycleDelegate;
import vn.tale.architecture.common.mvvm.Store;

/**
 * Created by Giang Nguyen on 3/23/17.
 */

public abstract class ReduxActivity<DaggerComponent, UiState>
    extends BaseActivity<DaggerComponent> {

  private LifecycleDelegate<UiState> lifecycleDelegate;
  private CompositeDisposable disposables = new CompositeDisposable();

  protected abstract void injectDependencies();

  protected abstract Store<UiState> store();

  protected void disposeOnStop(Disposable disposable) {
    disposables.add(disposable);
  }

  private Consumer<? super UiState> render() {
    return (state) -> runOnUiThread(() -> {
      try {
        render(state).run();
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
  }

  @NonNull protected abstract Action render(UiState state);

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    injectDependencies();
    lifecycleDelegate = new LifecycleDelegate<>(store());
  }

  @Override protected void onStart() {
    super.onStart();
    lifecycleDelegate.onStart();
    disposeOnStop(store().state$()
        .distinctUntilChanged()
        .subscribe(render()));
  }

  @Override protected void onStop() {
    super.onStop();
    lifecycleDelegate.onStop(isFinishing());
    disposables.clear();
  }
}
