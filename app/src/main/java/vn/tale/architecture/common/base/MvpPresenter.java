package vn.tale.architecture.common.base;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import java.lang.ref.WeakReference;

/**
 * Created by Giang Nguyen on 2/21/17.
 */

public class MvpPresenter<MvpView> {

  private WeakReference<MvpView> viewReference;
  private CompositeDisposable compositeDisposable;

  public final void attachView(@NonNull MvpView view) {
    viewReference = new WeakReference<>(view);
    onViewAttached();
  }

  public final void detachView() {
    if (viewReference != null) {
      viewReference.clear();
      viewReference = null;
    }
    if (compositeDisposable != null) {
      compositeDisposable.clear();
      compositeDisposable = null;
    }
    onViewDetached();
  }

  @Nullable protected MvpView getView() {
    if (viewReference == null) {
      return null;
    }
    return viewReference.get();
  }

  protected void disposeOnDetach(Disposable disposable) {
    if (compositeDisposable == null) {
      compositeDisposable = new CompositeDisposable();
    }
    compositeDisposable.add(disposable);
  }

  protected void onViewAttached() {
    // No-op
  }

  protected void onViewDetached() {
    // No-op
  }
}
