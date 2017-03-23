package vn.tale.architecture.common.mvp;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Giang Nguyen on 3/20/17.
 */

public class MvpPresenter<UiState, View extends MvpView<UiState>> {

  @VisibleForTesting UiState state;
  private CompositeDisposable disposablesToDisposeOnStop;
  private CompositeDisposable disposablesToDisposeOnDetach;
  private View view;

  public final void attachView(@NonNull View view) {
    this.view = view;
    if (state != null) {
      view.render(state);
    }
    onViewAttached();
  }

  public final void detachView() {
    view = null;
    if (disposablesToDisposeOnDetach != null) {
      disposablesToDisposeOnDetach.clear();
    }
    onViewDetached();
  }

  public final void stop() {
    state = null;
    if (disposablesToDisposeOnStop != null) {
      disposablesToDisposeOnStop.clear();
    }
    onStop();
  }

  protected View getView() {
    return view;
  }

  protected void setState(UiState state) {
    if (state.equals(this.state)) {
      return;
    }
    this.state = state;
    if (view != null) {
      view.render(state);
    }
  }

  protected void onViewAttached() {

  }

  protected void onViewDetached() {

  }

  /**
   * Good place to release resources. This will be called when activity release and not re-create
   */
  protected void onStop() {

  }

  protected void disposeOnDetach(Disposable disposable) {
    if (disposablesToDisposeOnDetach == null) {
      disposablesToDisposeOnDetach = new CompositeDisposable();
    }
    disposablesToDisposeOnDetach.add(disposable);
  }

  protected void disposeOnStop(Disposable disposable) {
    if (disposablesToDisposeOnStop == null) {
      disposablesToDisposeOnStop = new CompositeDisposable();
    }
    disposablesToDisposeOnStop.add(disposable);
  }
}
