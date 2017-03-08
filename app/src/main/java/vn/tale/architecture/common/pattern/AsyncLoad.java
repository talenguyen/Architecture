package vn.tale.architecture.common.pattern;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import java.lang.ref.WeakReference;

/**
 * Created by Giang Nguyen on 3/3/17.
 */

public interface AsyncLoad {

  interface View<T> {

    Observable<Object> onLoad();

    Observable<Object> onDestroy();

    void showLoading();

    void showContent(T content);

    void showError(Throwable error);
  }

  interface Model<T> {

    Observable<T> getData();
  }

  class AsyncLoadPresenter<T> {

    private final AsyncLoad.Model<T> model;
    private final Scheduler threadScheduler;
    private final Scheduler uiScheduler;
    private final boolean cache;
    private T data;
    private WeakReference<View<T>> viewRef;
    private CompositeDisposable compositeDisposable;
    private Observable<T> getData;
    private boolean error;

    public AsyncLoadPresenter(AsyncLoad.Model<T> model, Scheduler threadScheduler,
        Scheduler uiScheduler, boolean cache) {
      this.model = model;
      this.threadScheduler = threadScheduler;
      this.uiScheduler = uiScheduler;
      this.cache = cache;
    }

    public void attachView(@NonNull View<T> view) {
      viewRef = new WeakReference<>(view);
      compositeDisposable = new CompositeDisposable();

      compositeDisposable.add(view.onLoad()
          .subscribe(object -> load()));

      compositeDisposable.add(view.onDestroy()
          .subscribe(object -> detachView()));
    }

    private void load() {
      View<T> view = getView();
      if (view == null) {
        return;
      }
      if (cache && data != null) {
        view.showContent(data);
        return;
      }
      if (getData == null || error) {
        getData = cache ? model.getData().cache() : model.getData();
        error = false;
      }
      view.showLoading();
      compositeDisposable.add(getData
          .subscribeOn(threadScheduler)
          .observeOn(uiScheduler)
          .subscribe(t -> {
            data = t;
            if (getView() != null) {
              getView().showContent(t);
            }
          }, throwable -> {
            error = true;
            if (getView() != null) {
              getView().showError(throwable);
            }
          }));
    }

    @VisibleForTesting View<T> getView() {
      return viewRef == null ? null : viewRef.get();
    }

    private void detachView() {
      if (viewRef != null) {
        viewRef.clear();
      }
      if (compositeDisposable != null) {
        compositeDisposable.clear();
      }
    }
  }
}
